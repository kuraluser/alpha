/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.UserAuthorizationsResponse;
import com.cpdss.gateway.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserService userService;

  @MockBean private UserAuthorizationsResponse userAuthorizationsResponse;

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
}