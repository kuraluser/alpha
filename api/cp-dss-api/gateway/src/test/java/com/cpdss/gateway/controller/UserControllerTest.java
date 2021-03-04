/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.TestUtils;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserService userService;

  @MockBean private UserAuthorizationsResponse userAuthorizationsResponse;

  @MockBean private RoleResponse roleResponse;

  private static final String AUTHORIZATION_HEADER = "Authorization";

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
        when(userService.saveUser(dummy, "test")).thenReturn(new UserResponse());
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
}
