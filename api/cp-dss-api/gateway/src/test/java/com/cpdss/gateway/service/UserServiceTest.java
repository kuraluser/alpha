/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.ScreenInfo;
import com.cpdss.gateway.domain.UserAuthorizationsResponse;
import com.cpdss.gateway.entity.RoleUserMapping;
import com.cpdss.gateway.entity.Roles;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.RoleScreenRepository;
import com.cpdss.gateway.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

  private AutoCloseable closeable;

  private static final String SUCCESS = "SUCCESS";
  private static final String AUTHORIZATION = "Authorization";
  private static final String AUTHORIZATION_VALUE = "4b5608ff-b77b-40c6-9645-d69856d4aafa";

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
}
