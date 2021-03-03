/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.PermissionResponse;
import com.cpdss.gateway.domain.Role;
import com.cpdss.gateway.domain.RolePermission;
import com.cpdss.gateway.domain.RoleResponse;
import com.cpdss.gateway.domain.ScreenInfo;
import com.cpdss.gateway.domain.ScreenResponse;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

class UserServiceTest {

  @Mock private UsersRepository usersRepository;

  @Mock private RoleScreenRepository roleScreenRepository;

  @Mock private Users users;

  @Mock private RoleUserMapping roleUserMapping;

  @Mock private List<RoleUserMapping> roleUserMappingList;

  @Mock private List<ScreenInfo> screenInfoList;

  @Mock private Roles roles;

  @InjectMocks private UserService userService;

  @Mock private RolesRepository rolesRepository;

  @Mock private ScreenRepository screenRepository;
  @Mock private RoleUserRepository roleUserRepository;
  private AutoCloseable closeable;

  private static final String SUCCESS = "SUCCESS";
  private static final String STATUS = "200";
  private static final String AUTHORIZATION = "Authorization";
  private static final String AUTHORIZATION_VALUE = "4b5608ff-b77b-40c6-9645-d69856d4aafa";
  private static final String CORRELATION_ID_VALUE = "8b5609cc-b00b-90a8-1085-p69856d4abgf";

  @BeforeEach
  public void init() {
    closeable = MockitoAnnotations.openMocks(this);
    when(users.getId()).thenReturn(1L);
    when(roleUserMapping.getRoles()).thenReturn(roles);
    when(users.getRoleUserMappings()).thenReturn(roleUserMappingList);
    when(usersRepository.findByKeycloakIdAndIsActive(anyString(), anyBoolean())).thenReturn(users);
  }

  @Test
  void getUserPermissionsTest() throws GenericServiceException {
    HttpHeaders headers = new HttpHeaders();
    headers.set(AUTHORIZATION, AUTHORIZATION_VALUE);
    UserAuthorizationsResponse response = userService.getUserPermissions(headers);
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(SUCCESS);
  }

  @Test
  void getUserPermissionsWithRolesTest() throws GenericServiceException {
    HttpHeaders headers = new HttpHeaders();
    headers.set(AUTHORIZATION, AUTHORIZATION_VALUE);
    when(usersRepository.findByKeycloakIdAndIsActive(anyString(), anyBoolean()))
        .thenReturn(createUser());
    when(roleScreenRepository.findByRolesAndIsActive(any(), anyBoolean()))
        .thenReturn(createScreenInfoList());
    UserAuthorizationsResponse response = userService.getUserPermissions(headers);
    assertThat(response.getResponseStatus().getStatus()).isEqualTo(SUCCESS);
  }

  @Test
  void getUserPermissionsWithoutAuthTokenTest() throws GenericServiceException {
    HttpHeaders headers = new HttpHeaders();
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
    String expectedMessage = "Invalid user";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void getUserPermissionsWithoutUserTest() throws GenericServiceException {
    HttpHeaders headers = new HttpHeaders();
    headers.set(AUTHORIZATION, AUTHORIZATION_VALUE);
    when(usersRepository.findByKeycloakIdAndIsActive(anyString(), anyBoolean())).thenReturn(null);
    Exception exception =
        assertThrows(
            GenericServiceException.class,
            () -> {
              userService.getUserPermissions(headers);
            });
    String expectedMessage = "Invalid user";
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
    when(this.screenRepository.findByCompanyXIdAndIsActive(anyLong(), anyBoolean()))
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

  @Test
  void testGetUsers() {
    Users users = new Users();
    users.setId(1L);
    List<Users> userList = new ArrayList<Users>();
    userList.add(users);
    when(this.usersRepository.findByIsActive(anyBoolean())).thenReturn(userList);
    UserResponse userResponse = userService.getUsers(anyString());
    assertEquals(STATUS, userResponse.getResponseStatus().getStatus());
  }

  @Test
  void testSavePermission() throws GenericServiceException {
    PermissionResponse permissionResponse = new PermissionResponse();
    Roles roleEntity = new Roles();
    roleEntity.setId(1L);
    RolePermission RolePermission = new RolePermission();
    RolePermission.setRoleId(1L);
    RolePermission.setUserId(new ArrayList<Long>());
    RolePermission.setScreens(new ArrayList<ScreenInfo>());

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

    permissionResponse = userService.savePermission(RolePermission, 1L, "TEST");

    assertEquals(STATUS, permissionResponse.getResponseStatus().getStatus());
  }

  @Test
  public void testSaveRole() throws GenericServiceException {
    RoleResponse roleResponse = new RoleResponse();

    when(this.rolesRepository.findByCompanyXIdAndNameAndIsActive(
            anyLong(), anyString(), anyBoolean()))
        .thenReturn(Optional.empty());

    Roles role = new Roles();
    role.setId(1L);
    when(this.rolesRepository.save(role)).thenReturn(role);

    Role roles = new Role();
    roles.setId(1L);
    roles.setName("CHIEF_OFFICER");

    roleResponse = userService.saveRole(roles, 1L, "test");
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
  void restPasswordTest(String password){
    Long userId = 1l;
  }

  @ParameterizedTest(name = "#{index} - Run test with password = {0}")
  @MethodSource("validPasswordProvider")
  void validateRegularExpression(String password){
    try{
      userService.validateRegularExpression(password);
    }catch (GenericServiceException e){

    }
  }


  static Stream<String> validPasswordProvider() {
    return Stream.of(
            "12345678",
            " AAAbbbccc@123 AAAbbbccc@123 AAAbbbccc@123",
            "Hello world$123",
            "A!@#&()–a1",               // test punctuation part 1
            "A[{}]:;',?/*a1",           // test punctuation part 2
            "A~$^+=<>a1",               // test symbols
            "0123456789$abcdefgAB",     // test 20 chars
            "123Aa$Aa",                 // test 8 chars
            "Test123"
    );
  }
}
