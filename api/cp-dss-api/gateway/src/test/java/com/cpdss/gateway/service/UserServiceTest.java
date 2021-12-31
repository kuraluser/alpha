/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.TestUtils;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.user.UserType;
import com.cpdss.gateway.entity.*;
import com.cpdss.gateway.repository.*;
import com.cpdss.gateway.security.cloud.KeycloakDynamicConfigResolver;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.keycloak.common.VerificationException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

class UserServiceTest {

  @Mock private UsersRepository usersRepository;

  @Mock private RoleScreenRepository roleScreenRepository;

  @Mock private RoleUserMapping roleUserMapping;

  @Mock private RoleUserMappingRepository roleUserMappingRepository;

  @Mock private List<RoleUserMapping> roleUserMappingList;

  @Mock private NotificationStatusRepository notificationStatusRepository;

  @Mock private NotificationRepository notificationRepository;

  @Mock private List<ScreenInfo> screenInfoList;

  @Mock private Roles roles;

  @Spy @InjectMocks private UserService userService;

  @Mock PasswordEncoder passwordEncoder;

  @Mock private RolesRepository rolesRepository;

  @Mock private ScreenRepository screenRepository;
  @Mock private RoleUserRepository roleUserRepository;
  @Mock private UserStatusRepository userStatusRepository;
  @Mock private KeycloakDynamicConfigResolver keycloakDynamicConfigResolver;
  @Mock private UserCachingService userCachingService;

  private AutoCloseable closeable;

  private static final String SUCCESS = "SUCCESS";
  private static final String STATUS = "200";
  private static final String AUTHORIZATION = "Authorization";
  private static final String AUTHORIZATION_VALUE = "4b5608ff-b77b-40c6-9645-d69856d4aafa";
  private static final String CORRELATION_ID_VALUE = "8b5609cc-b00b-90a8-1085-p69856d4abgf";
  private static final String BEARER_TOKEN =
      "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

  @BeforeEach
  public void init() {
    closeable = MockitoAnnotations.openMocks(this);
    Users users = createUser();
    when(roleUserMapping.getRoles()).thenReturn(roles);
    when(usersRepository.findByKeycloakIdAndIsActive(anyString(), eq(true))).thenReturn(users);
    when(userService.isShip()).thenReturn(true);
  }

  @Test
  void getUserPermissionsTest() throws GenericServiceException, VerificationException {
    HttpHeaders headers = new HttpHeaders();
    headers.set(AUTHORIZATION, BEARER_TOKEN);
    when(userService.isShip()).thenReturn(false);
    when(usersRepository.findByKeycloakId("1234567890")).thenReturn(createUser());
    UserAuthorizationsResponse response = userService.getUserPermissions(headers);
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(SUCCESS);
  }

  @Test
  void getUserPermissionsWithRolesTest() throws GenericServiceException, VerificationException {
    HttpHeaders headers = new HttpHeaders();
    headers.set(AUTHORIZATION, BEARER_TOKEN);
    when(userService.isShip()).thenReturn(false);
    when(roleScreenRepository.findByRolesAndIsActive(any(), eq(true)))
        .thenReturn(createScreenInfoList());
    when(usersRepository.findByKeycloakId("1234567890")).thenReturn(createUser());
    UserAuthorizationsResponse response = userService.getUserPermissions(headers);
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(SUCCESS);
  }

  @Test
  void getUserPermissionsWithoutAuthTokenTest() throws GenericServiceException {
    HttpHeaders headers = new HttpHeaders();
    headers.set(AUTHORIZATION, BEARER_TOKEN);
    when(userService.isShip()).thenReturn(false);
    when(usersRepository.findByKeycloakId("invaliduser")).thenReturn(createUser());
    when(usersRepository.findByKeycloakIdAndIsActive(anyString(), anyBoolean()))
        .thenReturn(createUser());
    when(roleScreenRepository.findByRolesAndIsActive(any(), anyBoolean()))
        .thenReturn(createScreenInfoList());
    Exception exception =
        assertThrows(
            GenericServiceException.class,
            () -> {
              userService.getUserPermissions(headers);
            });
    String expectedMessage = "Invalid user in request token";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void getUserPermissionsWithoutUserTest() throws GenericServiceException {
    HttpHeaders headers = new HttpHeaders();
    headers.set(AUTHORIZATION, BEARER_TOKEN);
    when(userService.isShip()).thenReturn(false);
    when(usersRepository.findByKeycloakId("1234567890")).thenReturn(null);
    Exception exception =
        assertThrows(
            GenericServiceException.class,
            () -> {
              userService.getUserPermissions(headers);
            });
    String expectedMessage = "Invalid user in request token";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @AfterEach
  public void releaseMocks() throws Exception {
    closeable.close();
  }

  private List<ScreenInfo> createScreenInfoList() {
    ScreenInfo screenInfo = new ScreenInfo(1L, "test", "testKey", true, false, true, false);
    List<ScreenInfo> screenInfoList = new ArrayList<>();
    screenInfoList.add(screenInfo);
    return screenInfoList;
  }

  private Users createUser() {
    Users user = new Users();
    user.setId(1L);
    RoleUserMapping roleUserMapping = new RoleUserMapping();
    Roles roles = new Roles();
    roles.setId(1L);
    roles.setName("CHIEF_OFFICER");
    roleUserMapping.setRoles(roles);
    List<RoleUserMapping> roleUserMappingList = new ArrayList();
    roleUserMappingList.add(roleUserMapping);
    user.setRoleUserMappings(roleUserMappingList);
    return user;
  }

  @Test
  public void testGetScreens() throws GenericServiceException {
    ScreenResponse screenResponse = new ScreenResponse();

    Roles roleEntity = new Roles();
    roleEntity.setId(1L);
    roleEntity.setName("test");
    roleEntity.setDescription("test");
    when(this.rolesRepository.findByIdAndCompanyXIdAndIsActive(anyLong(), anyLong(), anyBoolean()))
        .thenReturn(Optional.of(roleEntity));
    List<Screen> screenDataList = this.createScreenDataList();
    when(this.screenRepository.findByCompanyXIdAndIsActiveOrderByScreenOrderAsc(
            anyLong(), anyBoolean()))
        .thenReturn(screenDataList);
    com.cpdss.gateway.entity.RoleScreen roleScreen = new com.cpdss.gateway.entity.RoleScreen();
    roleScreen.setId(1L);
    roleScreen.setCanAdd(true);
    roleScreen.setCanDelete(true);
    roleScreen.setCanEdit(true);
    roleScreen.setCanView(true);
    when(this.roleScreenRepository.findByRolesAndScreenAndCompanyXIdAndIsActive(
            anyLong(), anyLong(), anyLong(), anyBoolean()))
        .thenReturn(Optional.of(roleScreen));

    screenResponse = userService.getScreens(anyLong(), anyLong(), anyString());
    assertEquals(STATUS, screenResponse.getResponseStatus().getStatus());
  }

  private List<Screen> createScreenDataList() {
    Screen screenInfo = new Screen();
    screenInfo.setId(1L);
    screenInfo.setCompanyXId(1L);
    screenInfo.setDescription("test");
    screenInfo.setName("test");
    screenInfo.setIsActive(true);

    Screen screenInfo2 = new Screen();
    screenInfo2.setId(1L);
    screenInfo2.setCompanyXId(1L);
    screenInfo2.setDescription("test");
    screenInfo2.setName("test");
    screenInfo2.setIsActive(true);
    screenInfo2.setModuleId(1L);
    List<Screen> screenDataList = new ArrayList<>();
    screenDataList.add(screenInfo);
    screenDataList.add(screenInfo2);
    return screenDataList;
  }

  // To Do -Rework (userService.findUsers should be private)
  @Test
  void testGetUsers() throws GenericServiceException {
    ReflectionTestUtils.setField(userService, "maxShipUserCount", 100);
    Users users = new Users();
    users.setId(1L);
    List<Users> userList = new ArrayList<Users>();
    userList.add(users);
    when(this.userService.findUsers(UserType.SHIP)).thenReturn(new ArrayList<User>());
    when(this.usersRepository.findByIsActive(anyBoolean())).thenReturn(userList);
    UserResponse userResponse = userService.getUsers(anyString());
    assertEquals(STATUS, userResponse.getResponseStatus().getStatus());
  }

  @Test
  void testSavePermission() throws GenericServiceException {
    PermissionResponse permissionResponse = new PermissionResponse();
    Role role = new Role();
    role.setName(null);
    Roles roleEntity = new Roles();
    roleEntity.setId(1L);
    RolePermission rolePermission = new RolePermission();
    rolePermission.setRoleId(1L);
    rolePermission.setUserId(new ArrayList<Long>());
    rolePermission.setScreens(new ArrayList<ScreenInfo>());
    rolePermission.setRole(role);
    Users users = new Users();
    users.setId(1L);

    Screen screen = new Screen();
    screen.setId(1L);

    List<Users> userList = new ArrayList<Users>();
    userList.add(users);

    RoleUserMapping roleUserMapping = new RoleUserMapping();

    when(this.rolesRepository.findByIdAndCompanyXIdAndIsActive(anyLong(), anyLong(), anyBoolean()))
        .thenReturn(Optional.of(roleEntity));
    //	when(this.usersRepository.findByIdInAndIsActive(List, anyBoolean())).thenReturn(userList);
    when(this.screenRepository.findByIdIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(screen));
    when(this.roleUserRepository.findByUsersAndRolesAndIsActive(anyLong(), anyLong(), anyBoolean()))
        .thenReturn(Optional.of(roleUserMapping));

    permissionResponse = userService.savePermission(rolePermission, 1L, "TEST");

    assertEquals(STATUS, permissionResponse.getResponseStatus().getStatus());
  }

  @Test
  public void testSaveRole() throws GenericServiceException {
    RoleResponse roleResponse = new RoleResponse();

    when(this.rolesRepository.findByCompanyXIdAndNameAndIsActive(
            anyLong(), anyString(), anyBoolean()))
        .thenReturn(Optional.empty());

    Roles roleEntity = new Roles();
    roleEntity.setId(1L);
    when(this.rolesRepository.save(any(Roles.class))).thenReturn(roleEntity);

    Role roleDomain = new Role();
    roleDomain.setId(1L);
    roleDomain.setName("CHIEF_OFFICER");

    roleResponse = userService.saveRole(roleDomain, 1L, "test");
    assertEquals(STATUS, roleResponse.getResponseStatus().getStatus());
  }

  @Test
  public void testDeleteRole() throws GenericServiceException {
    RoleResponse roleResponse = new RoleResponse();
    Roles rolesEntity = new Roles();
    rolesEntity.setId(1L);

    Role roles = new Role();
    roles.setId(1L);
    roles.setName("CHIEF_OFFICER");
    Users users = new Users();
    users.setId(1L);
    List<Users> userList = new ArrayList<Users>();
    userList.add(users);

    when(this.rolesRepository.findByIdAndCompanyXIdAndIsActive(anyLong(), anyLong(), anyBoolean()))
        .thenReturn(Optional.of(rolesEntity));
    when(this.rolesRepository.save(rolesEntity)).thenReturn(rolesEntity);

    roleResponse = userService.deleteRole(1L, 1L, "test");

    assertEquals(STATUS, roleResponse.getResponseStatus().getStatus());
  }

  /** Method to test get roles method - positive test */
  @Test
  void getRolesPositiveTest() {
    when(this.rolesRepository.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(getRoles(new HashMap<>()));
    RoleResponse roleResponse =
        userService.getRoles(1L, CORRELATION_ID_VALUE, new HashMap<>(), 0, 5, "name", "asc");

    assertAll(
        () -> {
          assertEquals(STATUS, roleResponse.getResponseStatus().getStatus());
          assertEquals(
              getRoles(new HashMap<>()).getTotalElements(), roleResponse.getRoles().size());
          assertEquals(CORRELATION_ID_VALUE, roleResponse.getResponseStatus().getCorrelationId());
        });
  }

  /** Method to test get roles method with filter - positive test */
  @Test
  void getRolesFilterPositiveTest() {
    when(this.rolesRepository.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(getRoles(getRolesFilterParams()));
    RoleResponse roleResponse =
        userService.getRoles(1L, CORRELATION_ID_VALUE, getRolesFilterParams(), 0, 5, "name", "asc");

    assertAll(
        () -> {
          assertEquals(STATUS, roleResponse.getResponseStatus().getStatus());
          assertEquals(1, roleResponse.getRoles().size());
          assertEquals(CORRELATION_ID_VALUE, roleResponse.getResponseStatus().getCorrelationId());
        });
  }

  /** Method to test get roles method with filter - negative test */
  @Test
  void getRolesFilterNegativeTest() {
    HashMap<String, String> filterParam =
        new HashMap<String, String>() {
          {
            put("name", "test");
          }
        };
    when(this.rolesRepository.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(getRoles(filterParam));
    RoleResponse roleResponse =
        userService.getRoles(1L, CORRELATION_ID_VALUE, filterParam, 0, 5, "name", "asc");

    assertAll(
        () -> {
          assertEquals(STATUS, roleResponse.getResponseStatus().getStatus());
          assertEquals(0, roleResponse.getRoles().size());
          assertEquals(CORRELATION_ID_VALUE, roleResponse.getResponseStatus().getCorrelationId());
        });
  }

  /**
   * Method to get roles
   *
   * @return Page<Roles> object
   */
  private Page<Roles> getRoles(HashMap<String, String> filterParams) {
    List<Roles> rolesList =
        new ArrayList<Roles>() {
          {
            add(new Roles("admin", null, null, null, true, "AdminDescription", 1L));
            add(new Roles("guest", null, null, null, true, "GuestDescription", 1L));
          }
        };

    rolesList =
        rolesList.stream()
            .filter(
                role ->
                    role.getName().equals(filterParams.get("name"))
                        || role.getDescription().equals(filterParams.get("description")))
            .collect(Collectors.toList());

    return new PageImpl<>(rolesList);
  }

  /**
   * Method to return filterParams for get roles filter
   *
   * @return Map<String, String> filterParams object
   */
  private HashMap<String, String> getRolesFilterParams() {
    return new HashMap<String, String>() {
      {
        put("name", "admin");
      }
    };
  }

  @ParameterizedTest(name = "#{index} - Run test with password = {0}")
  @MethodSource("validPasswordProvider")
  void restPasswordTest(String password) throws GenericServiceException {
    ReflectionTestUtils.setField(userService, "passwordMinLength", 8);
    ReflectionTestUtils.setField(userService, "passwordMaxLength", 16);
    ReflectionTestUtils.setField(userService, "PASSWORD_AGE", 90);
    Users users = new Users();
    users.setId(1l);
    users.setFirstName("test");
    users.setUsername("hello");
    Optional<Users> usersOp = Optional.of(users);
    when(usersRepository.updateUserPasswordExpireDateAndTime(
            eq(1l), eq(password), any(LocalDateTime.class), any(LocalDateTime.class)))
        .thenReturn(1);
    when(usersRepository.findById(1l)).thenReturn(usersOp);
    when(passwordEncoder.encode(eq(password))).thenReturn(password);
    boolean resp = userService.resetPassword(password, usersOp.get().getId());
    assertTrue(resp);
  }

  @ParameterizedTest(name = "#{index} - Run test with password = {0}")
  @MethodSource("validPasswordProvider")
  void validateRegularExpressionPositive(String password) throws GenericServiceException {
    ReflectionTestUtils.setField(userService, "passwordMinLength", 8);
    ReflectionTestUtils.setField(userService, "passwordMaxLength", 16);
    userService.validateRegularExpression(password);
  }

  @ParameterizedTest(name = "#{index} - Run test with password = {0}")
  @MethodSource("inValidPasswordProvider")
  void validateRegularExpressionNegative(String password) {
    ReflectionTestUtils.setField(userService, "passwordMinLength", 8);
    ReflectionTestUtils.setField(userService, "passwordMaxLength", 16);
    try {
      userService.validateRegularExpression(password);
    } catch (GenericServiceException e) {

    }
  }

  static Stream<String> validPasswordProvider() {
    return Stream.of(
        "A~$^+=<>a1", // test symbols
        "Think@123" // test 8 chars
        );
  }

  static Stream<String> inValidPasswordProvider() {
    return Stream.of(
        "12345678", "0123456789$abcdefgAB" // test 20 chars
        );
  }

  // Save user success for dummy Object
  @Test
  public void saveUserTestSuccess() {
    User user = TestUtils.getDummyUser();
    user.setId(0l);

    Users users = new Users();
    users.setId(1l);

    UserResponse response = null;
    try {
      ReflectionTestUtils.setField(userService, "maxShipUserCount", 1);
      when(this.usersRepository.findByIdAndIsActive(1l, true)).thenReturn(null);
      when(this.usersRepository.save(any(Users.class))).thenReturn(users);
      when(this.userStatusRepository.getOne(1L)).thenReturn(new UserStatus());
      when(this.roleUserMappingRepository.save(any(RoleUserMapping.class)))
          .thenReturn(new RoleUserMapping());
      when(this.notificationStatusRepository.getOne(2l)).thenReturn(new NotificationStatus());
      when(this.notificationRepository.findByRequestedByAndIsActive(1l, true))
          .thenReturn(new ArrayList<Notifications>());
      when(this.notificationRepository.saveAll(new ArrayList<Notifications>()))
          .thenReturn(new ArrayList<Notifications>());
      response = this.userService.saveUser(user, RandomString.make(6), 1L);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    assertThat(1l == response.getId()).isTrue();
  }

  // Ship user count Max Test
  @Test
  public void saveUserTestFailure1() {
    try {
      ReflectionTestUtils.setField(userService, "maxShipUserCount", 0);
      when(this.usersRepository.findByIdAndIsActive(1l, true)).thenReturn(null);
      User user = TestUtils.getDummyUser();
      user.setId(1l);
      this.userService.saveUser(user, RandomString.make(6), 1L);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  // User already exist Test
  @Test
  public void saveUserTestFailure2() {
    try {
      ReflectionTestUtils.setField(userService, "maxShipUserCount", 1);
      when(this.usersRepository.findByIdAndIsActive(1l, true)).thenReturn(null);
      User user = TestUtils.getDummyUser();
      user.setId(1l);
      this.userService.saveUser(user, RandomString.make(6), 1L);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  /** Already deleted user cannot Delete again */
  @Test
  public void deleteUserTest1() {
    Users users = new Users();
    users.setActive(false);
    try {
      when(usersRepository.findById(1l)).thenReturn(Optional.of(users));
      userService.deleteUserByUserId(1l);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  /** Ship user cannot be deleted */
  @Test
  public void deleteUserTest2() {
    Users users = new Users();
    users.setActive(true);
    users.setIsShipUser(true);
    try {
      when(usersRepository.findById(1l)).thenReturn(Optional.of(users));
      userService.deleteUserByUserId(1l);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  /** Not a ship user and user is active */
  @Test
  public void deleteUserTest3() {
    Users users = new Users();
    users.setActive(true);
    users.setIsShipUser(false);
    try {
      when(usersRepository.findById(1l)).thenReturn(Optional.of(users));
      when(usersRepository.updateUserIsActiveToFalse(1l)).thenReturn(1);
      boolean a = userService.deleteUserByUserId(1l);
      assertTrue(a);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }
}
