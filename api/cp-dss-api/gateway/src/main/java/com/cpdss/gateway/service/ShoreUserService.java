/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.AdminNotificationsResponse;
import com.cpdss.gateway.domain.keycloak.KeycloakUser;
import com.cpdss.gateway.domain.user.NotificationStatusValue;
import com.cpdss.gateway.domain.user.RejectUserAccessResponse;
import com.cpdss.gateway.domain.user.RequestAccessResponse;
import com.cpdss.gateway.domain.user.UserStatusValue;
import com.cpdss.gateway.entity.NotificationStatus;
import com.cpdss.gateway.entity.Notifications;
import com.cpdss.gateway.entity.UserStatus;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.NotificationRepository;
import com.cpdss.gateway.repository.NotificationStatusRepository;
import com.cpdss.gateway.repository.UserStatusRepository;
import com.cpdss.gateway.repository.UsersRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** UserService - service class shore user related operations */
@Service
@Log4j2
public class ShoreUserService {

  @Value("${maximum.rejection.count}")
  private String MAX_REJECTION_COUNT;

  @Autowired private UsersRepository usersRepository;

  @Autowired private NotificationRepository notificationRepository;

  @Autowired private UserStatusRepository userStatusRepository;

  @Autowired private NotificationStatusRepository notificationStatusRepository;

  @Autowired private UserCachingService userCachingService;

  /**
   * Method to request access to the application using userId
   *
   * @param authorizationToken token to fetch userId
   * @return RequestAccessResponse response object
   * @throws GenericServiceException Exception object
   */
  public RequestAccessResponse requestAccess(String authorizationToken)
      throws GenericServiceException {

    //      Parse token
    AccessToken token;
    try {
      token = parseKeycloakToken(authorizationToken);
    } catch (VerificationException e) {
      throw new GenericServiceException(
          "Token parsing failed",
          CommonErrorCodes.E_HTTP_UNAUTHORIZED_RQST,
          HttpStatusCode.FORBIDDEN);
    }

    //    Get user
    Users usersEntity = this.usersRepository.findByKeycloakId(token.getSubject());

    //      Create and disable user if user not found in DB
    if (null == usersEntity) {
      usersEntity = new Users();
      usersEntity.setKeycloakId(token.getSubject());
      usersEntity.setActive(false);
    }

    //        Reject API on reaching count
    int rejectionCount =
        null != usersEntity.getRejectionCount() ? usersEntity.getRejectionCount() : 0;
    rejectionCount++;

    if (rejectionCount > Integer.valueOf(MAX_REJECTION_COUNT)) {
      throw new GenericServiceException(
          "Rejection count exceeded",
          CommonErrorCodes.E_CPDSS_REJECTION_COUNT_EXCEEDED,
          HttpStatusCode.FORBIDDEN);
    }

    //    Update user status
    UserStatus userStatus = userStatusRepository.getOne(UserStatusValue.REQUESTED.getId());
    usersEntity.setStatus(userStatus);
    this.usersRepository.save(usersEntity);

    //      Create and activate notification
    this.activateNotification(usersEntity);

    //      Return response
    return RequestAccessResponse.builder()
        .responseStatus(new CommonSuccessResponse(HttpStatus.OK.getReasonPhrase(), null))
        .userId(usersEntity.getKeycloakId())
        .status(UserStatusValue.REQUESTED.getId())
        .build();
  }

  /**
   * Method to reject userAccess
   *
   * @param userId userId of user to be rejected
   * @return RejectUserAccessResponse object
   * @throws GenericServiceException Exception object
   */
  public RejectUserAccessResponse rejectAccess(Long userId) throws GenericServiceException {

    Users usersEntity = this.usersRepository.findByIdAndIsActive(userId, false);

    //        Reject API on reaching count
    int rejectionCount =
        null != usersEntity.getRejectionCount() ? usersEntity.getRejectionCount() : 0;
    rejectionCount++;

    if (rejectionCount > 3) {
      throw new GenericServiceException(
          "Rejection count exceeded",
          CommonErrorCodes.E_CPDSS_REJECTION_COUNT_EXCEEDED,
          HttpStatusCode.FORBIDDEN);
    }

    //        Update user
    UserStatus userStatus = userStatusRepository.getOne(UserStatusValue.REJECTED.getId());
    usersEntity.setStatus(userStatus);
    usersEntity.setRejectionCount(rejectionCount);
    this.usersRepository.save(usersEntity);

    //        Update notification
    this.deactivateNotification(usersEntity);

    //      Return response
    return RejectUserAccessResponse.builder()
        .commonSuccessResponse(
            new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), null))
        .rejectionCount(rejectionCount)
        .userId(userId)
        .status(UserStatusValue.REJECTED.getId())
        .statusMessage(UserStatusValue.REJECTED.getStatusName())
        .build();
  }

  /**
   * Method to get admin notifications
   *
   * @return AdminNotificationsResponse response object
   */
  public AdminNotificationsResponse getAdminNotifications() throws GenericServiceException {

    //      Get new and active notifications
    List<Notifications> notificationsList =
        notificationRepository.findByNotificationTypeAndIsActive(
            NotificationStatusValue.NEW.getNotificationType(), true);
    //    Join keycloak user list with notifications
    // changed for code optimization
    Set<KeycloakUser> keycloakUserList = new HashSet<>();
    Set<Long> requestedBy = new HashSet<>();
    for (Notifications notifications : notificationsList) {
      requestedBy.add(notifications.getRequestedBy());
    }
    List<Users> usersEntity = this.usersRepository.findByIdInAndIsActive(requestedBy, false);
    for (Users user : usersEntity) {
      if (user.getKeycloakId() != null && !user.getKeycloakId().isEmpty()) {
        KeycloakUser keycloakUser = userCachingService.getUser(user.getKeycloakId());
        keycloakUser.setUserId(user.getId());
        keycloakUserList.add(keycloakUser);
      }
    }

    //    Return response
    return AdminNotificationsResponse.builder()
        .notifications(keycloakUserList)
        .responseStatus(new CommonSuccessResponse(HttpStatus.OK.getReasonPhrase(), null))
        .build();
  }

  /**
   * Parse keycloak token from authorization header
   *
   * @param token - The token in string format
   * @return {@link AccessToken} - The keycloak access token instance
   * @throws VerificationException
   */
  private AccessToken parseKeycloakToken(String token) throws VerificationException {
    return TokenVerifier.create(token.replace("Bearer ", ""), AccessToken.class).getToken();
  }

  private void activateNotification(Users usersEntity) {
    NotificationStatus notificationStatus =
        this.notificationStatusRepository.getOne(NotificationStatusValue.NEW.getId());

    Notifications notification = new Notifications();
    notification.setRequestedBy(usersEntity.getId());
    notification.setIsActive(true);
    notification.setNotificationStatus(notificationStatus);
    notification.setNotificationType(NotificationStatusValue.NEW.getNotificationType());
    this.notificationRepository.save(notification);
  }

  private void deactivateNotification(Users usersEntity) {
    NotificationStatus notificationStatus =
        notificationStatusRepository.getOne(NotificationStatusValue.CLOSED.getId());
    List<Notifications> notificationsList =
        this.notificationRepository.findByRequestedByAndIsActive(usersEntity.getId(), true);
    notificationsList.forEach(
        notification -> {
          notification.setNotificationStatus(notificationStatus);
          notification.setNotificationType(NotificationStatusValue.CLOSED.getNotificationType());
          notification.setIsActive(false);
        });
    this.notificationRepository.saveAll(notificationsList);
  }
}
