/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static java.time.temporal.ChronoUnit.DAYS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.keycloak.KeycloakUser;
import com.cpdss.gateway.domain.user.NotificationStatusValue;
import com.cpdss.gateway.domain.user.UserStatusValue;
import com.cpdss.gateway.domain.user.UserType;
import com.cpdss.gateway.entity.NotificationStatus;
import com.cpdss.gateway.entity.Notifications;
import com.cpdss.gateway.entity.RoleUserMapping;
import com.cpdss.gateway.entity.Roles;
import com.cpdss.gateway.entity.Screen;
import com.cpdss.gateway.entity.UserStatus;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.NotificationRepository;
import com.cpdss.gateway.repository.NotificationStatusRepository;
import com.cpdss.gateway.repository.RoleScreenRepository;
import com.cpdss.gateway.repository.RoleUserMappingRepository;
import com.cpdss.gateway.repository.RoleUserRepository;
import com.cpdss.gateway.repository.RolesRepository;
import com.cpdss.gateway.repository.ScreenRepository;
import com.cpdss.gateway.repository.UserStatusRepository;
import com.cpdss.gateway.repository.UsersRepository;
import com.cpdss.gateway.security.cloud.KeycloakDynamicConfigResolver;
import com.cpdss.gateway.security.ship.ShipJwtService;
import com.cpdss.gateway.security.ship.ShipUserContext;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** UserService - service class user related operations */
@Service
@Log4j2
public class UserService {

  @Autowired private UsersRepository usersRepository;

  @Autowired private RoleScreenRepository roleScreenRepository;

  @Autowired private ScreenRepository screenRepository;

  @Autowired private RolesRepository rolesRepository;

  @Autowired private RoleUserRepository roleUserRepository;

  @Autowired private NotificationRepository notificationRepository;

  @Autowired private UserStatusRepository userStatusRepository;

  @Autowired private NotificationStatusRepository notificationStatusRepository;

  @Autowired private UserCachingService userCachingService;

  @Autowired private KeycloakService keycloakService;

  @Autowired(required = false)
  private ShipJwtService jwtService;

  @Autowired(required = false)
  private PasswordEncoder passwordEncoder;

  @Autowired(required = false)
  private KeycloakDynamicConfigResolver keycloakDynamicConfigResolver;

  private static final String SUCCESS = "SUCCESS";

  private static final String FAILED = "FAILED";

  private static final Long DEFAULT_COMPANY_ID = 1L;
  private static final int MAX_REJECTION_COUNT = 3;

  @Autowired private RoleUserMappingRepository roleUserMappingRepository;

  private static final String SHIP_URL_PREFIX = "/api/ship";

  @Value("${ship.max.user.count}")
  private int maxShipUserCount;

  @Value("${ship.user.password-age}")
  private Integer PASSWORD_AGE;

  @Value("${ship.user.password-length-min}")
  private Integer passwordMinLength;

  @Value("${ship.user.password-length-max}")
  private Integer passwordMaxLength;

  @Value("${ship.user.password-reminder}")
  private Integer PASSWORD_EXPIRE_REMINDER;

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
    // if (authorizationToken != null) {
    // AccessToken token = parseKeycloakToken(authorizationToken);
    Users usersEntity;
    User user = new User();
    if (this.isShip()) {
      Long userId =
          ((ShipUserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
              .getUserId();
      usersEntity = this.usersRepository.findByIdAndIsActive(userId, true);
    } else {
      try {
        AccessToken token = parseKeycloakToken(authorizationToken);
        usersEntity = this.usersRepository.findByKeycloakId(token.getSubject());
      } catch (VerificationException e) {
        throw new GenericServiceException(
            "Token parsing failed",
            CommonErrorCodes.E_HTTP_UNAUTHORIZED_RQST,
            HttpStatusCode.FORBIDDEN);
      }
    }

    // Reject API if user not found
    if (null == usersEntity) {
      throw new GenericServiceException(
          "Invalid user in request token",
          CommonErrorCodes.E_CPDSS_INVALID_USER,
          HttpStatusCode.BAD_REQUEST);
    }

    // Set status values
    if (null != usersEntity.getStatus()) {
      user.setStatusCode(usersEntity.getStatus().getId());
      user.setStatusValue(usersEntity.getStatus().getStatusName());
      user.setRejectionCount(usersEntity.getRejectionCount());
    }

    if ((null != usersEntity.getId() && this.isShip())
        || (null != usersEntity.getStatus()
            && UserStatusValue.APPROVED.getId().equals(usersEntity.getStatus().getId()))) {
      user.setId(usersEntity.getId());
      List<RoleUserMapping> roleUserList =
          this.roleUserMappingRepository.findByUsersAndIsActive(
              usersEntity, true); // usersEntity.getRoleUserMappings();
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
    }
    /*
     * } else { throw new GenericServiceException("Invalid user in request token",
     * CommonErrorCodes.E_CPDSS_INVALID_USER,
     * HttpStatusCode.valueOf(Integer.valueOf(CommonErrorCodes.E_HTTP_BAD_REQUEST)))
     * ; }
     */
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(SUCCESS);
    userAuthResponse.setUser(user);
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
  private AccessToken parseKeycloakToken(String token) throws VerificationException {
    return TokenVerifier.create(token.replace("Bearer ", ""), AccessToken.class).getToken();
  }

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

    List<Users> users = this.usersRepository.findByIsActive(true);
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
        continue;
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

  /**
   * Method to get roles
   *
   * @param companyId companyId value
   * @param correlationIdHeader correlationId value
   * @param filterParams filter keys and values
   * @param page page number
   * @param pageSize pageSize value
   * @param sortBy column to be sorted
   * @param orderBy order to be used while sorting
   * @return RoleResponse response object
   */
  public RoleResponse getRoles(
      Long companyId,
      String correlationIdHeader,
      Map<String, String> filterParams,
      int page,
      int pageSize,
      String sortBy,
      String orderBy) {

    // Filter
    Specification<Roles> specification =
        Specification.where(
                new RolesSpecification(new FilterCriteria("companyXId", ":", companyId)))
            .and(new RolesSpecification(new FilterCriteria("isActive", ":", true)));

    for (Map.Entry<String, String> entry : filterParams.entrySet()) {
      String filterKey = entry.getKey();
      String value = entry.getValue();
      specification =
          specification.and(new RolesSpecification(new FilterCriteria(filterKey, "like", value)));
    }

    // Paging and sorting
    Pageable paging =
        PageRequest.of(
            page, pageSize, Sort.by(Sort.Direction.valueOf(orderBy.toUpperCase()), sortBy));
    Page<Roles> pagedResult = this.rolesRepository.findAll(specification, paging);

    RoleResponse roleResponse = new RoleResponse();
    List<Role> roleList = new ArrayList<Role>();
    List<Roles> roles = pagedResult.toList();
    if (roles != null && !roles.isEmpty()) {
      roles.forEach(
          roleEntity -> {
            Role role = new Role();
            role.setId(roleEntity.getId());
            role.setName(roleEntity.getName());
            role.setDescription(roleEntity.getDescription());
            role.setCompanyId(roleEntity.getCompanyXId());
            role.setIsUserMapped(this.isUserMapped(roleEntity));
            roleList.add(role);
          });
    }
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    commonSuccessResponse.setCorrelationId(correlationIdHeader);
    roleResponse.setResponseStatus(commonSuccessResponse);
    roleResponse.setRoles(roleList);
    roleResponse.setTotalElements(pagedResult.getTotalElements());
    return roleResponse;
  }

  /**
   * Get users
   *
   * @param correlationId
   * @param correlationId
   * @return
   */
  public UserResponse getUsers(String correlationId) throws GenericServiceException {
    UserResponse userResponse = new UserResponse();
    if (this.isShip()) {
      userResponse.setUsers(this.findUsers(UserType.SHIP));
      userResponse.setMaxUserCount(this.maxShipUserCount);
    } else {
      userResponse.setUsers(this.findUsers(UserType.CLOUD));
    }
    List<Roles> roles = this.rolesRepository.findByIsActiveOrderByName(true);
    userResponse.setRoles(this.buildRoles(roles));
    userResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return userResponse;
  }

  /**
   * Build role dto list
   *
   * @param roles
   * @return
   */
  private List<Role> buildRoles(List<Roles> roles) {
    List<Role> roleList = new ArrayList<>();
    for (Roles role : roles) {
      Role roleDto = new Role();
      roleDto.setId(role.getId());
      roleDto.setName(role.getName());
      roleList.add(roleDto);
    }
    return roleList;
  }

  /**
   * Method to find users
   *
   * @param userType UserType Ship/Cloud
   * @return List of users
   * @throws GenericServiceException Exception object
   */
  private List<User> findUsers(UserType userType) throws GenericServiceException {
    List<User> userList = new ArrayList<>();
    List<Users> users = new ArrayList<>();
    switch (userType) {
      case SHIP:
        users = this.usersRepository.findByIsActiveOrderById(true);
        if (users != null && !users.isEmpty()) {
          users.forEach(
              userEntity -> {
                User user = new User();
                user.setId(userEntity.getId());
                user.setFirstName(userEntity.getFirstName());
                user.setLastName(userEntity.getLastName());
                user.setUsername(userEntity.getUsername());
                user.setDesignation(userEntity.getDesignation());
                if (null != userEntity.getRoles()) {
                  user.setRole(userEntity.getRoles().getName());
                }
                user.setDefaultUser(userEntity.getIsShipUser());
                userList.add(user);
              });
        }
        break;
      case CLOUD:
        // Get all keycloak users
        KeycloakUser[] keycloakUsersList = keycloakService.getUsers();
        List<String> keyCloakIds =
            Arrays.stream(keycloakUsersList).map(KeycloakUser::getId).collect(Collectors.toList());

        users = this.usersRepository.findByKeycloakIdInOrderById(keyCloakIds);
        users.forEach(
            userEntity -> {
              KeycloakUser keycloakUser = null;
              try {
                keycloakUser = userCachingService.getUser(userEntity.getKeycloakId());
              } catch (GenericServiceException e) {
                e.printStackTrace();
              }
              User user = new User();
              user.setId(userEntity.getId());
              user.setKeycloakId(keycloakUser.getId());
              user.setFirstName(keycloakUser.getFirstName());
              user.setLastName(keycloakUser.getLastName());
              user.setUsername(keycloakUser.getUsername());
              user.setDesignation(userEntity.getDesignation());
              if (null != userEntity.getRoles()) {
                user.setRole(userEntity.getRoles().getName());
              }
              user.setDefaultUser(userEntity.getIsShipUser());
              userList.add(user);
            });
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + userType);
    }
    return userList;
  }

  /**
   * Identify ship or shore based on accessed url
   *
   * @return
   */
  public boolean isShip() {
    if (null != RequestContextHolder.getRequestAttributes()) {
      HttpServletRequest request =
          ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      if (null != request.getRequestURI()) {
        return request.getRequestURI().indexOf(SHIP_URL_PREFIX) != -1;
      }
    }
    return false;
  }

  @Transactional
  public PermissionResponse savePermission(
      RolePermission permission, Long companyId, String correlationId)
      throws GenericServiceException {
    if (permission.getRole().getName() != null) {
      this.validateRoleName(permission.getRoleId(), permission.getRole().getName());
    }

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

    List<Users> users = this.usersRepository.findByIdIn(permission.getUserId());
    List<Long> screenIds = new ArrayList<>();
    for (ScreenInfo screenInfo : permission.getScreens()) {
      screenIds.add(screenInfo.getId());
    }

    if (permission.getDeselectedUserId() != null) {
      List<RoleUserMapping> roleUserList =
          this.roleUserRepository.findByRolesAndIsActive(role.get().getId(), true);
      roleUserList.stream()
          .filter(ru -> permission.getDeselectedUserId().contains(ru.getUsers().getId()))
          .forEach(roleUser -> roleUser.setIsActive(false));
    }

    List<Screen> screens =
        this.screenRepository.findByCompanyXIdAndIdInAndIsActive(companyId, screenIds, true);
    if (users != null && !users.isEmpty()) {
      users.forEach(
          user -> {
            // Activate user if user in requested for approval
            if (null != user.getStatus()
                && user.getStatus().getId().equals(UserStatusValue.REQUESTED.getId())) {
              // Update user
              UserStatus userStatus = userStatusRepository.getOne(UserStatusValue.APPROVED.getId());
              user.setStatus(userStatus);
              user.setActive(true);
              this.usersRepository.save(user);

              // Update notification
              NotificationStatus notificationStatus =
                  notificationStatusRepository.getOne(NotificationStatusValue.CLOSED.getId());
              List<Notifications> notificationsList =
                  this.notificationRepository.findByRequestedByAndIsActive(user.getId(), true);
              notificationsList.forEach(
                  notification -> {
                    notification.setNotificationStatus(notificationStatus);
                    notification.setNotificationType(
                        NotificationStatusValue.CLOSED.getNotificationType());
                    notification.setIsActive(false);
                  });
              this.notificationRepository.saveAll(notificationsList);
            }

            Optional<RoleUserMapping> roleUserOpt =
                this.roleUserRepository.findByUsersAndIsActive(user.getId(), true);
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

    if (screens != null && !screens.isEmpty()) {
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

  /**
   * * Check if role with same name exists
   *
   * @param companyId
   * @param roleId
   * @param roleName
   * @throws GenericServiceException
   */
  private void validateRoleName(Long roleId, String roleName) throws GenericServiceException {
    Roles duplicate = this.rolesRepository.findByNameIgnoreCaseAndIsActive(roleName, true);
    if ((null == roleId && null != duplicate)
        || (null != roleId && null != duplicate && !duplicate.getId().equals(roleId))) {
      throw new GenericServiceException(
          "Role with given name already exist",
          CommonErrorCodes.E_CPDSS_ROLE_NAME_EXISTS,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public RoleResponse saveRole(Role role, Long companyId, String correlationId)
      throws GenericServiceException {
    this.validateRoleName(null, role.getName());
    RoleResponse roleResponse = new RoleResponse();
    Roles roleEntity = new Roles();
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

  /**
   * Generate Jwt Token, Password Expire Notification
   *
   * @param request
   * @param correlationId
   * @return
   * @throws GenericServiceException
   */
  public ShipLoginResponse generateShipUserToken(ShipLoginRequest request, String correlationId)
      throws GenericServiceException {

    ShipLoginResponse response = new ShipLoginResponse();
    Users user = this.usersRepository.findByUsernameAndIsActive(request.getUsername(), true);
    if (null == user) {
      throw new GenericServiceException(
          "User does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
    validatePasswordExpiry(user);
    user.setLastLoginDate(LocalDateTime.now());
    this.usersRepository.save(user);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    response.setToken(this.jwtService.generateToken(user));
    if (user.getPasswordExpiryDate() != null) { // Password expire reminder
      LocalDateTime timeNow = LocalDateTime.now();
      long daysDiff = DAYS.between(timeNow, user.getPasswordExpiryDate());
      if (daysDiff <= PASSWORD_EXPIRE_REMINDER) {
        response.setExpiryReminder(new PasswordExpiryReminder(daysDiff));
      }
    }
    return response;
  }

  private void validatePasswordExpiry(Users users) throws GenericServiceException {
    if (users.getPasswordExpiryDate() != null
        && LocalDateTime.now().isAfter(users.getPasswordExpiryDate())) {
      throw new GenericServiceException(
          "Password expired on " + users.getPasswordExpiryDate(),
          CommonErrorCodes.E_CPDSS_PASSWORD_EXPIRED,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * If user exist by the username from request, update the last attempted date in db
   *
   * @param request
   */
  public void updateLastLoginAttemptedDateForShipUser(ShipLoginRequest request) {
    Users user = this.usersRepository.findByUsernameAndIsActive(request.getUsername(), true);
    if (null != user) {
      user.setLastAttemptedDate(LocalDateTime.now());
      this.usersRepository.save(user);
    } else {
      log.info("User doesn't exist with username: {}", request.getUsername());
    }
  }

  /**
   * Save user
   *
   * @param request
   * @param correlationId
   * @return
   * @throws GenericServiceException
   */
  public UserResponse saveUser(User request, String correlationId, Long companyId)
      throws GenericServiceException {
    UserResponse response = new UserResponse();
    if (this.isShip()) {
      Users entity = null;
      if (0 != request.getId()) {
        entity = this.usersRepository.findByIdAndIsActive(request.getId(), true);
        if (null == entity) {
          throw new GenericServiceException(
              "User does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
      } else {
        this.validateShipMaxUserCount();
        entity = new Users();
        entity.setActive(true);
        entity.setLoginSuspended(false);
        entity.setIsShipUser(false);
      }
      this.checkUsernameDuplicate(request);
      entity.setCompanyXId(companyId);
      entity.setUsername(request.getUsername());
      entity.setFirstName(request.getFirstName());
      entity.setLastName(request.getLastName());
      entity.setDesignation(request.getDesignation());
      entity.setRoles(this.rolesRepository.getOne(request.getRoleId()));

      // Update user status
      UserStatus userStatus = userStatusRepository.getOne(UserStatusValue.APPROVED.getId());
      entity.setStatus(userStatus);

      if (null != request.getIsLoginSuspended()) {
        entity.setLoginSuspended(request.getIsLoginSuspended());
      }
      entity = this.usersRepository.save(entity);

      // Update notification
      NotificationStatus notificationStatus =
          notificationStatusRepository.getOne(NotificationStatusValue.CLOSED.getId());
      List<Notifications> notificationsList =
          this.notificationRepository.findByRequestedByAndIsActive(entity.getId(), true);
      notificationsList.forEach(
          notification -> {
            notification.setNotificationStatus(notificationStatus);
            notification.setNotificationType(NotificationStatusValue.CLOSED.getNotificationType());
            notification.setIsActive(false);
          });
      this.notificationRepository.saveAll(notificationsList);

      response.setId(entity.getId());
    }
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Check if max user count is reached for the given ship
   *
   * @throws GenericServiceException
   */
  private void validateShipMaxUserCount() throws GenericServiceException {
    List<Users> userList = this.usersRepository.findByIsActive(true);
    if (userList.size() >= this.maxShipUserCount) {
      throw new GenericServiceException(
          "Maximum ship user count reached",
          CommonErrorCodes.E_CPDSS_MAX_SHIP_USER_COUNT_ERR,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * Check for duplicate user name
   *
   * @param request
   * @throws GenericServiceException
   */
  private void checkUsernameDuplicate(User request) throws GenericServiceException {
    Users duplicate =
        this.usersRepository.findByUsernameIgnoreCaseAndIsActive(request.getUsername(), true);
    // if new user
    if (request.getId() == 0) {
      if (null != duplicate) {
        throw new GenericServiceException(
            "Username already in use",
            CommonErrorCodes.E_CPDSS_USERNAME_IN_USE,
            HttpStatusCode.BAD_REQUEST);
      }
    } else if (null != duplicate && !request.getId().equals(duplicate.getId())) {
      // if the username has changed, and any users exist with changed username
      throw new GenericServiceException(
          "Username already in use",
          CommonErrorCodes.E_CPDSS_USERNAME_IN_USE,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  @Transactional
  public boolean resetPassword(String password, Long userId) throws GenericServiceException {
    boolean response = false;
    if (validatePasswordPolicies(userId, password)) {
      String encodedPassword = passwordEncoder.encode(password);
      LocalDateTime dateTime = LocalDateTime.now();
      int status =
          this.usersRepository.updateUserPasswordExpireDateAndTime(
              userId, encodedPassword, dateTime.plusDays(PASSWORD_AGE), dateTime);
      response = status > 0;
      log.info("reset password for user id - {}" + (response ? "success" : "failed!"), userId);
    }
    return response;
  }

  /**
   * password policies: DSS-1155 1. Age for each password, from properties (0-9999) 2. Length of
   * password (8 character) 3. Complexity 3.1. Cannot contain username 3.2. At least 3 of the case
   * must have (Lower-case, Upper-case, Number, Symbols)
   *
   * @return
   */
  private boolean validatePasswordPolicies(Long userId, String password)
      throws GenericServiceException {
    Optional<Users> users = usersRepository.findById(userId);
    if (users.isPresent()) {
      boolean firstNameCheck =
          users.get().getFirstName() == null
              ? false
              : password.toLowerCase().contains(users.get().getFirstName().toLowerCase());
      boolean lastNameCheck =
          users.get().getLastName() == null
              ? false
              : password.toLowerCase().contains(users.get().getLastName().toLowerCase());
      boolean userNameCheck =
          users.get().getUsername() == null
              ? false
              : password.toLowerCase().contains(users.get().getUsername().toLowerCase());
      if (!firstNameCheck && !lastNameCheck && !userNameCheck) {
        validateRegularExpression(password);
        return true;
      } else {
        throw new GenericServiceException(
            "Password cannot contain first name/last name",
            CommonErrorCodes.E_CPDSS_PASSWORD_POLICIES_VIOLATION_1,
            HttpStatusCode.BAD_REQUEST);
      }
    } else {
      throw new GenericServiceException(
          "User not found for ID: " + userId,
          CommonErrorCodes.E_CPDSS_INVALID_USER,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public void validateRegularExpression(String password) throws GenericServiceException {
    List<String> regexList =
        Arrays.asList(
            "^(?=.*[0-9])(?=\\S+$).{"
                + passwordMinLength
                + ","
                + passwordMaxLength
                + "}$", // Check for if any
            // number + no white
            // space + length
            "^(?=.*[a-z])(?=\\S+$).{"
                + passwordMinLength
                + ","
                + passwordMaxLength
                + "}$", // Check for if any
            // small letter + no
            // white space + length
            "^(?=.*[A-Z])(?=\\S+$).{"
                + passwordMinLength
                + ","
                + passwordMaxLength
                + "}$", // Check for if any
            // capital letter + no
            // white space + length
            "^(?=.*[@#$%&*])(?=\\S+$).{"
                + passwordMinLength
                + ","
                + passwordMaxLength
                + "}$" // Check for if any
            // special character
            // + no white space
            // + length
            );
    int total = 0;
    for (String regex : regexList) {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(password);
      if (matcher.matches()) {
        total = total + 1;
      }
    }
    if (total < 3) {
      throw new GenericServiceException(
          "Passwords must use at least three of the four available character types: lowercase letters, uppercase letters, numbers, and symbols",
          CommonErrorCodes.E_CPDSS_PASSWORD_POLICIES_VIOLATION_2,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  @Transactional
  public boolean deleteUserByUserId(Long userId) throws GenericServiceException {

    Optional<Users> users = usersRepository.findById(userId);
    if (users.isPresent()) {
      if (!users.get().isActive()) {
        // already deleted user
        log.info("Trying to delete user already deleted, User Id {}", userId);
        throw new GenericServiceException(
            "User is not active!",
            CommonErrorCodes.E_CPDSS_INVALID_USER,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      if (users.get().getIsShipUser() != null && users.get().getIsShipUser().booleanValue()) {
        // cannot delete
        log.info("Trying to delete default user, User Id {}", userId);
        throw new GenericServiceException(
            "Deleting default user is restricted",
            CommonErrorCodes.E_CPDSS_OPERATION_NOT_ALLOWED,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      // update is active to False
      int var1 = usersRepository.updateUserIsActiveToFalse(userId);
      return var1 > 0;
    } else {
      // throw no user found
      log.info("No user found with Id - {} to delete", userId);
      throw new GenericServiceException(
          "User not found for id " + userId,
          CommonErrorCodes.E_CPDSS_INVALID_USER,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  private Boolean isUserMapped(Roles role) {
    Boolean usersExist = false;
    List<RoleUserMapping> roleUserList =
        this.roleUserMappingRepository.findByRolesAndIsActive(role, true);
    if (null != roleUserList && !roleUserList.isEmpty()) {
      usersExist = true;
    }
    return usersExist;
  }

  /**
   * Generic getter for user name, In tables we keep user Id as String.
   *
   * @param id
   * @param authorizationToken
   * @return
   * @throws GenericServiceException
   */
  public String getUserNameFromUserId(String id, String authorizationToken) {

    Users users = null;
    Long userId = null;

    try {
      // user id required
      if (id == null || id.length() <= 0) {
        return null;
      }
      userId = Long.valueOf(id);
    } catch (Exception e) {
      log.error("Get User Name,Failed to parse user id '{}' to Long", id);
      return null;
    }

    // Case 1: Shore api, request must have a keycloak token
    if (authorizationToken != null && authorizationToken.length() > 0) {
      try {
        users =
            this.getUsersEntity(
                keycloakDynamicConfigResolver.parseKeycloakToken(authorizationToken).getSubject());
        String fullName =
            (users.getFirstName() != null ? users.getFirstName() : "")
                + " "
                + (users.getLastName() != null ? users.getLastName() : "");
        log.info("Get User Name, from keycloak token - {}", fullName.trim());
        return fullName.trim();
      } catch (VerificationException e) {
        log.error("Get User Name, Failed to parse token - VerificationException, ", e.getMessage());
      } catch (Exception e) {
        log.error("Get User Name, Failed to parse token - Exception, ", e.getMessage());
      }
    }
    // Case 2: Ship api, find name from user's Table
    if (users == null) {
      users = usersRepository.findByIdAndIsActive(userId, true);
      if (users != null) {
        log.info("Get User Name, from users DB username - {}", users.getUsername().trim());
        return users
            .getUsername()
            .toUpperCase(); // username, because we don't keep First name for Ship user.
      }
    }
    log.error(
        "Get User Name, Failed User Id - {}, isToken avilable - {}",
        userId,
        !StringUtils.isEmpty(authorizationToken));
    return null;
  }

  public Users getUsersEntity(String keyCloakId) {
    return this.usersRepository.findByKeycloakIdAndIsActive(keyCloakId, true);
  }
}
