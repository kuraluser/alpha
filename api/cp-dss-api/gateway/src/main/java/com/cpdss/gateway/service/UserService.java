/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.Permission;
import com.cpdss.gateway.domain.Resource;
import com.cpdss.gateway.domain.RolePermissions;
import com.cpdss.gateway.domain.ScreenInfo;
import com.cpdss.gateway.domain.User;
import com.cpdss.gateway.domain.UserAuthorizationsResponse;
import com.cpdss.gateway.entity.RoleUserMapping;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.RoleScreenRepository;
import com.cpdss.gateway.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** UserService - service class user related operations */
@Service
@Log4j2
public class UserService {

  @Autowired private UsersRepository usersRepository;

  @Autowired private RoleScreenRepository roleScreenRepository;

  private static final String SUCCESS = "SUCCESS";

  /**
   * Retrieves the user information from user database
   *
   * @param headers
   * @return
   * @throws GenericServiceException
   * @throws VerificationException
   */
  public UserAuthorizationsResponse getUserPermissions(HttpHeaders headers)
      throws GenericServiceException {
    UserAuthorizationsResponse userAuthResponse = new UserAuthorizationsResponse();
    // Retrieve user information from user database
    String authorizationToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
    if (authorizationToken != null) {
      //      AccessToken token = parseKeycloakToken(authorizationToken);
      Users usersEntity =
          this.usersRepository.findByKeycloakIdAndIsActive(
              "4b5608ff-b77b-40c6-9645-d69856d4aafa", true);
      if (usersEntity == null) {
        throw new GenericServiceException(
            "Invalid user in request token",
            CommonErrorCodes.E_CPDSS_INVALID_USER,
            HttpStatusCode.valueOf(Integer.valueOf(CommonErrorCodes.E_HTTP_BAD_REQUEST)));
      }
      if (usersEntity.getId() != null) {
        User user = new User();
        user.setId(usersEntity.getId());
        List<RoleUserMapping> roleUserList = usersEntity.getRoleUserMappings();
        if (!CollectionUtils.isEmpty(roleUserList)) {
          roleUserList.forEach(
              roleUser -> {
                if (roleUser.getRoles() != null) {
                  RolePermissions rolePermissions = new RolePermissions();
                  rolePermissions.setId(roleUser.getRoles().getId());
                  rolePermissions.setRole(roleUser.getRoles().getName());
                  List<ScreenInfo> screenInfoList =
                      this.roleScreenRepository.findByRolesAndIsActive(roleUser.getRoles(), true);
                  if (!CollectionUtils.isEmpty(screenInfoList)) {
                    List<Resource> resourceList = new ArrayList<>();
                    screenInfoList.forEach(
                        screenInfo -> {
                          Resource resource = new Resource();
                          resource.setId(screenInfo.getId());
                          resource.setName(screenInfo.getName());
                          resource.setLanguageKey(screenInfo.getLanguageKey());
                          Permission permission = new Permission();
                          permission.setAdd(screenInfo.isAdd());
                          permission.setDelete(screenInfo.isDelete());
                          permission.setEdit(screenInfo.isEdit());
                          permission.setView(screenInfo.isView());
                          resource.setPermission(permission);
                          resourceList.add(resource);
                        });
                    rolePermissions.setResources(resourceList);
                  }
                  user.setRolePermissions(rolePermissions);
                }
              });
        }
        userAuthResponse.setUser(user);
      }
    } else {
      throw new GenericServiceException(
          "Invalid user in request token",
          CommonErrorCodes.E_CPDSS_INVALID_USER,
          HttpStatusCode.valueOf(Integer.valueOf(CommonErrorCodes.E_HTTP_BAD_REQUEST)));
    }
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(SUCCESS);
    userAuthResponse.setResponseStatus(commonSuccessResponse);
    return userAuthResponse;
  }

  /**
   * Parse keycloak token from authorization header
   *
   * @param token - The token in string format
   * @return {@link AccessToken} - The keycloak access token instance
   * @throws VerificationException
   */
  //  private AccessToken parseKeycloakToken(String token) throws VerificationException {
  //    return TokenVerifier.create(token.replace("Bearer ", ""), AccessToken.class).getToken();
  //  }
}
