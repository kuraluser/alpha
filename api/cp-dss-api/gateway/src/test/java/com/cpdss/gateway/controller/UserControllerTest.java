/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.TestUtils;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.security.ship.*;
import com.cpdss.gateway.service.GroupUserService;
import com.cpdss.gateway.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {"cpdss.build.env=ship"})
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired UserController userController;

  @MockBean private UserService userService;

  @MockBean private UserAuthorizationsResponse userAuthorizationsResponse;

  @MockBean private RoleResponse roleResponse;

  @MockBean private ShipResponseBodyAdvice shipResponseBodyAdvice;

  @MockBean private ShipJwtService shipJwtService;

  @MockBean private ShipAuthenticationProvider jwtAuthenticationProvider;

  @MockBean private ShipUserAuthenticationProvider shipUserAuthenticationProvider;

  @MockBean private ShipUserDetailService userDetailService;

  @MockBean private ShipTokenExtractor jwtTokenExtractor;

  @MockBean private AuthenticationFailureHandler failureHandler;

  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/ship";

  private static final String USER_AUTH = "/user-authorizations";
  private static final String SCREEN = "/screens/role/{roleId}";
  private static final String USERS = "/users";
  private static final String SAVE_USERS = "/users/{userId}";
  private static final String ROLES = "/roles";
  private static final String SAVE_PERM = "/user/role/permission";

  @MockBean private GroupUserService groupUserService;

  @Test
  void getUserAuthorizationsTest() throws Exception {
    when(userService.getUserPermissions(Mockito.any())).thenReturn(userAuthorizationsResponse);
    this.mockMvc
        .perform(
            get("/api/cloud/user-authorizations")
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isOk());
  }

  @Test
  void getUserAuthorizationsWithExceptionTest() throws Exception {
    when(userService.getUserPermissions(Mockito.any()))
        .thenThrow(
            new GenericServiceException(
                "Exception in getUserAuthorizations",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    this.mockMvc
        .perform(
            get("/api/cloud/user-authorizations")
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isInternalServerError())
        .andExpect(
            result ->
                assertEquals(
                    "Exception in getUserAuthorizations",
                    result.getResolvedException().getMessage()));
  }

  /**
   * Get roles API positive test
   *
   * @throws Exception exception object
   */
  @Test
  void getUserRolesTest() throws Exception {
    when(userService.getRoles(
            anyLong(), anyString(), anyMap(), anyInt(), anyInt(), anyString(), anyString()))
        .thenReturn(roleResponse);
    MultiValueMap<String, String> params =
        new LinkedMultiValueMap<String, String>() {
          {
            add("pageSize", "5");
            add("page", "1");
            add("sortBy", "id");
            add("orderBy", "asc");
            add("name", "admin");
          }
        };
    this.mockMvc
        .perform(
            get("/api/cloud/roles")
                .params(params)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isOk());
  }

  @Test
  void resetPasswordTest() throws Exception {
    ResetPasswordRequest request = new ResetPasswordRequest(1l, "Test@1234");
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = ow.writeValueAsString(request);
    when(userService.resetPassword("", 1l)).thenReturn(true);
    this.mockMvc
        .perform(
            post("/api/ship/users/reset-password")
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isOk());
  }

  @Test
  void saveUserTest() throws Exception {
    User dummy = TestUtils.getDummyUser();
    when(userService.saveUser(eq(dummy), eq("test"), anyLong())).thenReturn(new UserResponse());
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = ow.writeValueAsString(dummy);
    this.mockMvc
        .perform(
            post("/api/ship/users/{userId}", 1l)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isOk());
  }

  @Test
  void deleteUserTest() throws Exception {
    this.mockMvc
        .perform(
            delete("/api/ship/users/delete/{userId}", 1l)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + USER_AUTH, SHIP_API_URL_PREFIX + USER_AUTH})
  @ParameterizedTest
  public void testGetUserAuthorizationsRuntimeException(String url) throws Exception {
    when(userService.getUserPermissions(Mockito.any())).thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  private GenericServiceException getGenericException() {
    return new GenericServiceException(
        "service exception",
        CommonErrorCodes.E_GEN_INTERNAL_ERR,
        HttpStatusCode.INTERNAL_SERVER_ERROR);
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + SCREEN, SHIP_API_URL_PREFIX + SCREEN})
  @ParameterizedTest
  public void testGetScreens(String url) throws Exception {
    when(userService.getScreens(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new ScreenResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + SCREEN, SHIP_API_URL_PREFIX + SCREEN})
  @ParameterizedTest
  public void testGetScreensServiceException(String url) throws Exception {
    when(userService.getScreens(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(this.getGenericException());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + SCREEN, SHIP_API_URL_PREFIX + SCREEN})
  @ParameterizedTest
  public void testGetScreensRuntimeException(String url) throws Exception {
    when(userService.getScreens(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + USERS, SHIP_API_URL_PREFIX + USERS})
  @ParameterizedTest
  public void testGetUsers(String url) throws Exception {
    when(userService.getUsers(Mockito.anyString())).thenReturn(new UserResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + USERS, SHIP_API_URL_PREFIX + USERS})
  @ParameterizedTest
  public void testGetUsersServiceException(String url) throws Exception {
    when(userService.getUsers(Mockito.anyString())).thenThrow(this.getGenericException());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + USERS, SHIP_API_URL_PREFIX + USERS})
  @ParameterizedTest
  public void testGetUsersRuntimeException(String url) throws Exception {
    when(userService.getUsers(Mockito.anyString())).thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void getUserRolesTestRuntimeException() throws Exception {
    when(userService.getRoles(
            anyLong(), anyString(), anyMap(), anyInt(), anyInt(), anyString(), anyString()))
        .thenThrow(RuntimeException.class);
    MultiValueMap<String, String> params =
        new LinkedMultiValueMap<String, String>() {
          {
            add("pageSize", "5");
            add("page", "1");
            add("sortBy", "id");
            add("orderBy", "asc");
            add("name", "admin");
          }
        };
    this.mockMvc
        .perform(
            get("/api/cloud/roles")
                .params(params)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isInternalServerError());
  }
}
