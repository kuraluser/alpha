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

  public ScreenResponse getScreens(Long companyId, Long roleId, String corelationId)
      throws GenericServiceException {
    ScreenResponse screenResponse = new ScreenResponse();
    Optional<Roles> roleEntity =
        this.rolesRepository.findByIdAndCompanyXIdAndIsActive(roleId, companyId, true);
    if (!roleEntity.isPresent()) {
      throw new GenericServiceException(
          "Role with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Role role = new Role();
    role.setId(roleEntity.get().getId());
    role.setName(roleEntity.get().getName());
    role.setDescription(roleEntity.get().getDescription());
    screenResponse.setRole(role);

    List<Users> users = this.usersRepository.findByCompanyXIdAndIsActive(companyId, true);
    List<RoleUserMapping> roleUserList =
        this.roleUserRepository.findByRolesAndIsActive(roleId, true);
    List<User> userList = new ArrayList<User>();

    roleUserList.forEach(
        roleUser -> {
          Optional<Users> userEntityOpt =
              users.stream()
                  .filter(user -> user.getId().equals(roleUser.getUsers().getId()))
                  .findAny();
          if (userEntityOpt.isPresent()) {
            Users userEntity = userEntityOpt.get();
            User userDto = new User();
            userDto.setId(userEntity.getId());
            userDto.setFirstName(userEntity.getFirstName());
            userDto.setLastName(userEntity.getLastName());
            userDto.setUsername(userEntity.getUsername());
            userList.add(userDto);
          }
        });

    screenResponse.setUsers(userList);

    List<ScreenData> list = new ArrayList<>();

    List<Screen> screens = this.screenRepository.findByCompanyXIdAndIsActive(companyId, true);

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
            screen.setIsViewVisible(true);
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
    List<ScreenData> list =
        screens.stream()
            .filter(s -> s.getModuleId().equals(parentScreen.getId()))
            .map(s -> this.createInfo(s, roleId, companyId))
            .collect(Collectors.toList());
    for (ScreenData inner : list) {
      List<ScreenData> innerList = this.findInnerScreens(inner, screens, roleId, companyId);
      if (!innerList.isEmpty()) {
        inner.getChilds().addAll(innerList);
      } else {
        break;
      }
    }
    return list;
  }

  private ScreenData createInfo(Screen sc, Long roleId, Long companyId) {
    RoleScreen roleScreen = this.getRoleScreen(roleId, sc.getId(), companyId);
    ScreenData screenInfo = new ScreenData();
    screenInfo.setId(sc.getId());
    screenInfo.setName(sc.getName());
    screenInfo.setModuleId(sc.getModuleId());
    screenInfo.setIsAddVisible(sc.getIsAddVisisble());
    screenInfo.setIsEditVisible(sc.getIsEditVisisble());
    screenInfo.setIsViewVisible(sc.getIsViewVisisble());
    screenInfo.setIsDeleteVisible(sc.getIsDeleteVisisble());
    screenInfo.setChilds(new ArrayList<ScreenData>());
    screenInfo.setRoleScreen(roleScreen);
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
            role.setCompanyId(roleEntity.getCompanyXId());
            roleList.add(role);
          });
    }
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    roleResponse.setResponseStatus(commonSuccessResponse);
    roleResponse.setRoles(roleList);
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
  public PermissionResponse savePermission(
      RolePermission permission, Long companyId, String correlationId)
      throws GenericServiceException {
    PermissionResponse permissionResponse = new PermissionResponse();
    Optional<Roles> role =
        this.rolesRepository.findByIdAndCompanyXIdAndIsActive(
            permission.getRoleId(), companyId, true);
    if (!role.isPresent()) {
      throw new GenericServiceException(
          "Role with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    List<Users> users =
        this.usersRepository.findByCompanyXIdAndIdInAndIsActive(
            companyId, permission.getUserId(), true);

    List<Long> screenIds = new ArrayList<Long>();
    for (ScreenInfo screenInfo : permission.getScreens()) {
      screenIds.add(screenInfo.getId());
    }

    List<RoleUserMapping> roleUserList =
        this.roleUserRepository.findByRolesAndIsActive(role.get().getId(), true);
    if (roleUserList != null && roleUserList.size() != 0) {
      roleUserList.forEach(
          a -> {
            a.setIsActive(false);
          });
    }

    List<Screen> screens =
        this.screenRepository.findByCompanyXIdAndIdInAndIsActive(companyId, screenIds, true);
    if (users != null && users.size() != 0) {
      users.forEach(
          user -> {
            Optional<RoleUserMapping> roleUserOpt =
                this.roleUserRepository.findByUsersAndRolesAndIsActive(
                    user.getId(), role.get().getId(), true);
            RoleUserMapping roleUser = null;
            if (!roleUserOpt.isPresent()) {
              roleUser = new RoleUserMapping();
            } else {
              roleUser = roleUserOpt.get();
            }
            roleUser.setIsActive(true);
            roleUser.setRoles(role.get());
            roleUser.setUsers(user);
            this.roleUserRepository.save(roleUser);
          });
    }

    if (screens != null && screens.size() != 0) {
      screens.forEach(
          screen -> {
            Optional<com.cpdss.gateway.entity.RoleScreen> roleScreenrOpt =
                this.roleScreenRepository.findByCompanyXIdAndScreenAndRolesAndIsActive(
                    companyId, screen.getId(), role.get().getId(), true);
            com.cpdss.gateway.entity.RoleScreen roleScreen = null;
            if (!roleScreenrOpt.isPresent()) {
              roleScreen = new com.cpdss.gateway.entity.RoleScreen();
            } else {
              roleScreen = roleScreenrOpt.get();
            }
            roleScreen.setIsActive(true);
            roleScreen.setScreen(screen);
            roleScreen.setRoles(role.get());
            roleScreen.setCompanyXId(companyId);
            Optional<ScreenInfo> sc =
                permission.getScreens().stream()
                    .filter(scr -> scr.getId().equals(screen.getId()))
                    .findAny();
            if (sc.isPresent()) {
              roleScreen.setCanAdd(sc.get().isAdd());
              roleScreen.setCanDelete(sc.get().isDelete());
              roleScreen.setCanEdit(sc.get().isEdit());
              roleScreen.setCanView(sc.get().isView());
            }
            this.roleScreenRepository.save(roleScreen);
          });
    }

    if (permission.getRole().getName() != null) {
      role.get().setName(permission.getRole().getName());
    }
    if (permission.getRole().getDescription() != null) {
      role.get().setDescription(permission.getRole().getDescription());
    }
    this.rolesRepository.save(role.get());

    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    permissionResponse.setResponseStatus(commonSuccessResponse);
    permissionResponse.setMessage(SUCCESS);
    permissionResponse.setRoleId(role.get().getId());
    return permissionResponse;
  }

  public RoleResponse saveRole(Role role, Long companyId, String first)
      throws GenericServiceException {
    RoleResponse roleResponse = new RoleResponse();
    Optional<Roles> roleEntityOpt =
        this.rolesRepository.findByCompanyXIdAndNameAndIsActive(companyId, role.getName(), true);
    Roles roleEntity = null;
    if (!roleEntityOpt.isPresent()) {
      roleEntity = new Roles();
    } else {
      throw new GenericServiceException(
          "Role already  exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
    roleEntity.setIsActive(true);
    roleEntity.setName(role.getName());
    roleEntity.setDescription(role.getDescription());
    roleEntity.setCompanyXId(companyId);
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
    Optional<Roles> roleEntityOpt =
        this.rolesRepository.findByIdAndCompanyXIdAndIsActive(roleId, companyId, true);
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
