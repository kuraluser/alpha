/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.Permission;
import com.cpdss.gateway.domain.Resource;
import com.cpdss.gateway.domain.RolePermissions;
import com.cpdss.gateway.domain.RoleScreen;
import com.cpdss.gateway.domain.ScreenInfo;
import com.cpdss.gateway.domain.ScreenResponse;
import com.cpdss.gateway.domain.User;
import com.cpdss.gateway.domain.UserAuthorizationsResponse;
import com.cpdss.gateway.entity.RoleUserMapping;
import com.cpdss.gateway.entity.Screen;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.RoleScreenRepository;
import com.cpdss.gateway.repository.ScreenRepository;
import com.cpdss.gateway.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** UserService - service class user related operations */
@Service
@Log4j2
public class UserService {

  @Autowired private UsersRepository usersRepository;

  @Autowired private RoleScreenRepository roleScreenRepository;

  @Autowired private ScreenRepository screenRepository;

  private static final String SUCCESS = "SUCCESS";

  private static final String FAILED = "FAILED";

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

  public ScreenResponse getScreens(Long companyId, Long roleId, String corelationId) {
    ScreenResponse screenResponse = new ScreenResponse();

      List<ScreenInfo> list = new ArrayList<>();

      List<Screen> screens =
          (List<Screen>) this.screenRepository.findByCompanyXIdAndIsActive(companyId, true);

      List<ScreenInfo> info = new ArrayList<>();

      if (screens != null && !screens.isEmpty()) {
        screens.forEach(
            sc -> {
              ScreenInfo in =
                  new ScreenInfo(
                      sc.getId(),
                      sc.getName(),
                      sc.getLanguageKey(),
                      sc.getIsAvailableAdd(),
                      sc.getIsAvailableEdit(),
                      sc.getIsAvailableDelete(),
                      sc.getIsAvailableView(),
                      sc.getModuleId(),
                      new ArrayList<>(),
                      new RoleScreen());

              info.add(in);
            });
        list.addAll(
            info.stream()
                .filter(in -> in.getId().equals(in.getModuleId()))
                .collect(Collectors.toList()));

        for (ScreenInfo screen : list) {
          screens.removeAll(
              screens.stream()
                  .filter(s -> s.getId().equals(screen.getId()))
                  .collect(Collectors.toList()));
          screen.getChilds().addAll(this.findInnerScreens(screen, screens, roleId, companyId));
        }
      }
      if (list != null && !list.isEmpty()) {
        list.forEach(
            screen -> {
              screen.setRoleScreen(this.getRoleScreen(roleId, screen.getId(), companyId));
            });
      }
      CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
      commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
      screenResponse.setResponseStatus(commonSuccessResponse);
      screenResponse.setScreens(list);

    return screenResponse;
  }

  private List<ScreenInfo> findInnerScreens(
      ScreenInfo parentScreen, List<Screen> screens, Long roleId, Long companyId) {
    RoleScreen roleScreen = this.getRoleScreen(roleId, parentScreen.getId(), 1L);
    List<ScreenInfo> list =
        screens.stream()
            .filter(s -> s.getModuleId().equals(parentScreen.getId()))
            .map(this::createInfo)
            .collect(Collectors.toList());
    for (ScreenInfo inner : list) {
      inner.setRoleScreen(roleScreen);
      List<ScreenInfo> innerList = this.findInnerScreens(inner, screens, roleId, companyId);
      if (!innerList.isEmpty()) {
        inner.getChilds().addAll(innerList);
      } else {
        break;
      }
    }
    return list;
  }

  private ScreenInfo createInfo(Screen sc) {
    return new ScreenInfo(
        sc.getId(),
        sc.getName(),
        sc.getLanguageKey(),
        sc.getIsAvailableAdd(),
        sc.getIsAvailableEdit(),
        sc.getIsAvailableDelete(),
        sc.getIsAvailableView(),
        sc.getModuleId(),
        new ArrayList<>(),
        new RoleScreen());
  }

  private RoleScreen getRoleScreen(long roleId, long screenId, long companyId) {
    Optional<com.cpdss.gateway.entity.RoleScreen> roleScreenEntity =
        this.roleScreenRepository.findByRolesAndScreenAndCompanyXId(roleId, screenId, companyId);

    RoleScreen roleScreenDto = new RoleScreen();
    if (roleScreenEntity.isPresent()) {
      roleScreenDto.setCanAdd(roleScreenEntity.get().getCanAdd());
      roleScreenDto.setCanEdit(roleScreenEntity.get().getCanEdit());
      roleScreenDto.setCanDelete(roleScreenEntity.get().getCanDelete());
      roleScreenDto.setCanView(roleScreenEntity.get().getCanView());
    }
    return roleScreenDto;
  }
}
