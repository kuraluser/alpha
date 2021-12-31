/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.ShipLoginRequest;
import com.cpdss.gateway.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@MockitoSettings
@WebMvcTest(ShipAuthenticationController.class)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class ShipAuthenticationControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private AuthenticationManager authenticationManager;

  @MockBean private UserService userService;

  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";

  private static final String AUTHENTICATE = "/api/ship/authenticate";

  //   @ValueSource(strings = AUTHENTICATE)
  //    @ParameterizedTest
  //    void testAuthenticate(String url) throws Exception {
  //        Mockito.when(this.authenticationManager.authenticate(Mockito.any())).thenReturn(new
  // Authentication() {
  //            @Override
  //            public Collection<? extends GrantedAuthority> getAuthorities() {
  //                return null;
  //            }
  //
  //            @Override
  //            public Object getCredentials() {
  //                return null;
  //            }
  //
  //            @Override
  //            public Object getDetails() {
  //                return null;
  //            }
  //
  //            @Override
  //            public Object getPrincipal() {
  //                return null;
  //            }
  //
  //            @Override
  //            public boolean isAuthenticated() {
  //                return false;
  //            }
  //
  //            @Override
  //            public void setAuthenticated(boolean isAuthenticated) throws
  // IllegalArgumentException {
  //
  //            }
  //
  //            @Override
  //            public String getName() {
  //                return null;
  //            }
  //        });
  //
  // Mockito.when(this.userService.generateShipUserToken(Mockito.any(),Mockito.anyString())).thenReturn(new ShipLoginResponse());
  //        this.mockMvc
  //                .perform(
  //                        MockMvcRequestBuilders.post(url)
  //                                .content(this.createPortRotationRequest())
  //                                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                                .accept(MediaType.APPLICATION_JSON_VALUE))
  //                .andExpect(status().isOk());
  //    }

  private String createPortRotationRequest() throws JsonProcessingException {
    ShipLoginRequest request = new ShipLoginRequest();
    request.setUsername("123");
    request.setPassword("123");
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }
}
