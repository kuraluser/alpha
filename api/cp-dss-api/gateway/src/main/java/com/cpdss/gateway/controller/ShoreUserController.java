/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.common.exception.CommonRestException;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.AdminNotificationsResponse;
import com.cpdss.gateway.domain.user.RejectUserAccessResponse;
import com.cpdss.gateway.domain.user.RequestAccessResponse;
import com.cpdss.gateway.service.ShoreUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller for shore user related operations
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/api/cloud")
public class ShoreUserController {

  @Autowired private ShoreUserService shoreUserService;

  /**
   * API for user to request access to the application
   *
   * @param headers Headers object with authorization header to get token - userId extracted from
   *     token
   * @return RequestAccessResponse object
   * @throws CommonRestException Exception object
   */
  @GetMapping("/user/access")
  public RequestAccessResponse requestUserAccess(@RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("Request in requestUserAccess controller");
      return shoreUserService.requestAccess(headers.getFirst(HttpHeaders.AUTHORIZATION));
    } catch (GenericServiceException e) {
      log.error("Error in requestUserAccess: ", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in requestUserAccess: ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * API for admin to reject user access request
   *
   * @param headers Headers object with authorization header to get token - userId extracted from
   *     token
   * @param userId userId to be rejected
   * @return RejectUserAccessResponse object
   * @throws CommonRestException Exception object
   */
  @PostMapping("/user/reject/{userId}")
  public RejectUserAccessResponse rejectUserAccess(
      @RequestHeader HttpHeaders headers, @PathVariable Long userId) throws CommonRestException {
    try {
      log.info("Request in rejectUserAccess controller");
      return shoreUserService.rejectAccess(userId);
    } catch (GenericServiceException e) {
      log.error("Error in rejectUserAccess: ", e);
      throw new CommonRestException(e.getCode(), headers, e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      log.error("Error in rejectUserAccess: ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }

  /**
   * API to get approval request notifications for admin
   *
   * @param headers Headers object
   * @return AdminNotificationsResponse response object
   * @throws CommonRestException Exception object
   */
  @GetMapping("/user/admin/notifications")
  public AdminNotificationsResponse getAdminNotifications(@RequestHeader HttpHeaders headers)
      throws CommonRestException {
    try {
      log.info("Request in getAdminNotifications controller");
      return shoreUserService.getAdminNotifications();
    } catch (Exception e) {
      log.error("Error in getAdminNotifications: ", e);
      throw new CommonRestException(
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          headers,
          HttpStatusCode.INTERNAL_SERVER_ERROR,
          e.getMessage(),
          e);
    }
  }
}
