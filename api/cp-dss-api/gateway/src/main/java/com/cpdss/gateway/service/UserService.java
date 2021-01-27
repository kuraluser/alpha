/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.Permission;
import com.cpdss.gateway.domain.PermissionResponse;
import com.cpdss.gateway.domain.Resource;
import com.cpdss.gateway.domain.Role;
import com.cpdss.gateway.domain.RolePermission;
import com.cpdss.gateway.domain.RolePermissions;
import com.cpdss.gateway.domain.RoleResponse;
import com.cpdss.gateway.domain.RoleScreen;
import com.cpdss.gateway.domain.ScreenData;
import com.cpdss.gateway.domain.ScreenInfo;
import com.cpdss.gateway.domain.ScreenResponse;
import com.cpdss.gateway.domain.User;
import com.cpdss.gateway.domain.UserAuthorizationsResponse;
import com.cpdss.gateway.domain.UserResponse;
import com.cpdss.gateway.entity.RoleUserMapping;
import com.cpdss.gateway.entity.Roles;
import com.cpdss.gateway.entity.Screen;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.RoleScreenRepository;
import com.cpdss.gateway.repository.RoleUserRepository;
import com.cpdss.gateway.repository.RolesRepository;
import com.cpdss.gateway.repository.ScreenRepository;
import com.cpdss.gateway.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
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

  @Autowired private RolesRepository rolesRepository;

  @Autowired private RoleUserRepository roleUserRepository;

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
    Roles roleEntity =
        this.rolesRepository.findByIdAndCompanyXIdAndIsActive(roleId, companyId, true);
    if (roleEntity != null) {
      Role role = new Role();
      role.setId(roleEntity.getId());
      role.setName(roleEntity.getName());
      role.setDescription(roleEntity.getDescription());
      screenResponse.setRole(role);
    }

    List<ScreenData> list = new ArrayList<>();

    List<Screen> screens =
        (List<Screen>) this.screenRepository.findByCompanyXIdAndIsActive(companyId, true);
    List<ScreenData> info = new ArrayList<>();

    if (screens != null && !screens.isEmpty()) {
      screens.forEach(
          sc -> {
            ScreenData screenInfo = new ScreenData();
            screenInfo.setId(sc.getId());
            screenInfo.setName(sc.getName());
            screenInfo.setModuleId(sc.getModuleId());
            screenInfo.setIsAddVisible(sc.getIsAddVisisble());
            screenInfo.setIsEditVisible(sc.getIsEditVisisble());
            screenInfo.setIsViewVisible(sc.getIsViewVisisble());
            screenInfo.setIsDeleteVisible(sc.getIsDeleteVisisble());
            screenInfo.setChilds(new ArrayList<ScreenData>());

            info.add(screenInfo);
          });
      list.addAll(
          info.stream()
              .filter(in -> in.getId().equals(in.getModuleId()))
              .collect(Collectors.toList()));
      for (ScreenData screen : list) {
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

  private List<ScreenData> findInnerScreens(
      ScreenData parentScreen, List<Screen> screens, Long roleId, Long companyId) {
    RoleScreen roleScreen = this.getRoleScreen(roleId, parentScreen.getId(), 1L);
    List<ScreenData> list =
        screens.stream()
            .filter(s -> s.getModuleId().equals(parentScreen.getId()))
            .map(this::createInfo)
            .collect(Collectors.toList());
    for (ScreenData inner : list) {
      inner.setRoleScreen(roleScreen);
      List<ScreenData> innerList = this.findInnerScreens(inner, screens, roleId, companyId);
      if (!innerList.isEmpty()) {
        inner.getChilds().addAll(innerList);
      } else {
        break;
      }
    }
    return list;
  }

  private ScreenData createInfo(Screen sc) {
    ScreenData screenInfo = new ScreenData();
    screenInfo.setId(sc.getId());
    screenInfo.setName(sc.getName());
    sc.setModuleId(sc.getModuleId());
    screenInfo.setIsAddVisible(sc.getIsAddVisisble());
    screenInfo.setIsEditVisible(sc.getIsEditVisisble());
    screenInfo.setIsViewVisible(sc.getIsViewVisisble());
    screenInfo.setIsDeleteVisible(sc.getIsDeleteVisisble());
    screenInfo.setChilds(new ArrayList<ScreenData>());
    return screenInfo;
  }

  private RoleScreen getRoleScreen(long roleId, long screenId, long companyId) {
    Optional<com.cpdss.gateway.entity.RoleScreen> roleScreenEntity =
        this.roleScreenRepository.findByRolesAndScreenAndCompanyXIdAndIsActive(
            roleId, screenId, companyId, true);

    RoleScreen roleScreenDto = new RoleScreen();
    if (roleScreenEntity.isPresent()) {
      roleScreenDto.setId(roleScreenEntity.get().getId());
      roleScreenDto.setCanAdd(roleScreenEntity.get().getCanAdd());
      roleScreenDto.setCanEdit(roleScreenEntity.get().getCanEdit());
      roleScreenDto.setCanDelete(roleScreenEntity.get().getCanDelete());
      roleScreenDto.setCanView(roleScreenEntity.get().getCanView());
    }
    return roleScreenDto;
  }

  public RoleResponse getRoles(Long companyId, String correlationIdHeader) {
    RoleResponse roleResponse = new RoleResponse();
    List<Role> roleList = new ArrayList<Role>();
    List<Roles> roles = this.rolesRepository.findByCompanyXIdAndIsActive(companyId, true);
    if (roles != null && !roles.isEmpty()) {
      roles.forEach(
          roleEntity -> {
            Role role = new Role();
            role.setId(roleEntity.getId());
            role.setName(roleEntity.getName());
            role.setDescription(roleEntity.getDescription());
            roleList.add(role);
          });
    }

    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    roleResponse.setResponseStatus(commonSuccessResponse);
    roleResponse.setUsers(roleList);
    return roleResponse;
  }

  public UserResponse getUsers(Long companyId, String correlationIdHeader) {
    UserResponse userResponse = new UserResponse();
    List<User> userList = new ArrayList<User>();
    List<Users> users = this.usersRepository.findByCompanyXIdAndIsActive(companyId, true);
    if (users != null && !users.isEmpty()) {
      users.forEach(
          userEntity -> {
            User user = new User();
            user.setId(userEntity.getId());
            user.setFirstName(userEntity.getFirstName());
            user.setLastName(userEntity.getLastName());
            user.setUsername(userEntity.getUsername());
            userList.add(user);
          });
    }

    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    userResponse.setResponseStatus(commonSuccessResponse);
    userResponse.setUsers(userList);
    return userResponse;
  }

  @Transactional
  public PermissionResponse savePermission(RolePermission permission, Long companyId, String first)
      throws GenericServiceException {

    PermissionResponse permissionResponse = new PermissionResponse();
    Optional<Roles> role = this.rolesRepository.findByIdAndIsActive(permission.getRoleId(), true);
    if (!role.isPresent()) {
      throw new GenericServiceException(
          "Role with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Optional<Users> user = this.usersRepository.findByIdIdAndIsActive(permission.getUserId(), true);
    if (!user.isPresent()) {
      throw new GenericServiceException(
          "User with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Optional<Screen> screen =
        this.screenRepository.findByIdIdAndIsActive(permission.getScreenId(), true);
    if (!screen.isPresent()) {
      throw new GenericServiceException(
          "Screen with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Optional<RoleUserMapping> roleUserOpt =
        this.roleUserRepository.findByUsersAndRolesAndIsActive(
            user.get().getId(), role.get().getId(), true);
    RoleUserMapping roleUser = null;
    if (!roleUserOpt.isPresent()) {
      roleUser = new RoleUserMapping();
    } else {
      roleUser = roleUserOpt.get();
    }
    roleUser.setIsActive(true);
    roleUser.setRoles(role.get());
    roleUser.setUsers(user.get());
    this.roleUserRepository.save(roleUser);

    Optional<com.cpdss.gateway.entity.RoleScreen> roleScreenrOpt =
        this.roleScreenRepository.findByScreenAndRolesAndIsActive(
            screen.get().getId(), role.get().getId(), true);
    com.cpdss.gateway.entity.RoleScreen roleScreen = null;
    if (!roleScreenrOpt.isPresent()) {
      roleScreen = new com.cpdss.gateway.entity.RoleScreen();
    } else {
      roleScreen = roleScreenrOpt.get();
    }
    roleScreen.setIsActive(true);
    roleScreen.setScreen(screen.get());
    roleScreen.setRoles(role.get());
    roleScreen.setCanAdd(permission.getCanAdd());
    roleScreen.setCanDelete(permission.getCanDelete());
    roleScreen.setCanEdit(permission.getCanEdit());
    roleScreen.setCanView(permission.getCanView());

    this.roleScreenRepository.save(roleScreen);

    if (permission.getRoleName() != null) {
      role.get().setName(permission.getRoleName());
    }
    if (permission.getRoleDescription() != null) {
      role.get().setDescription(permission.getRoleDescription());
    }
    this.rolesRepository.save(role.get());

    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    permissionResponse.setResponseStatus(commonSuccessResponse);
    permissionResponse.setMessage(SUCCESS);
    permissionResponse.setUserId(user.get().getId());
    permissionResponse.setRoleId(role.get().getId());
    return permissionResponse;
  }

  public RoleResponse saveRole(Role role, Long companyId, String first) {
    RoleResponse roleResponse = new RoleResponse();
    Optional<Roles> roleEntityOpt =
        this.rolesRepository.findByNameAndIsActive(role.getName(), true);
    Roles roleEntity = null;
    if (!roleEntityOpt.isPresent()) {
      roleEntity = new Roles();
    } else {
      roleEntity = roleEntityOpt.get();
    }
    roleEntity.setIsActive(true);
    roleEntity.setName(role.getName());
    roleEntity.setDescription(role.getDescription());
    roleEntity.setCompanyXId(role.getCompanyId());
    roleEntity = this.rolesRepository.save(roleEntity);

    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    roleResponse.setResponseStatus(commonSuccessResponse);
    roleResponse.setMessage(SUCCESS);
    roleResponse.setRoleId(roleEntity.getId());
    return roleResponse;
  }

  public RoleResponse deleteRole(Long roleId, Long companyId, String first)
      throws GenericServiceException {
    RoleResponse roleResponse = new RoleResponse();
    Optional<Roles> roleEntityOpt = this.rolesRepository.findByIdAndIsActive(roleId, true);
    Roles roleEntity = null;
    if (!roleEntityOpt.isPresent()) {
      throw new GenericServiceException(
          "Role with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    } else {
      roleEntity = roleEntityOpt.get();
    }
    roleEntity.setIsActive(false);
    roleEntity = this.rolesRepository.save(roleEntity);

    this.roleScreenRepository.deleteRoles(roleEntity.getId());
    this.roleUserRepository.deleteRoles(roleEntity.getId());

    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    roleResponse.setResponseStatus(commonSuccessResponse);
    roleResponse.setMessage(SUCCESS);
    roleResponse.setRoleId(roleEntity.getId());
    return roleResponse;
  }
}
