/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.service.ShoreUserService;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@MockitoSettings
@WebMvcTest(ShoreUserController.class)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class ShoreUserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ShoreUserService shoreUserService;

  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";

  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";

  private static final String REQUEST_ACCESS = "/user/access";
  private static final String CLOUD_REQUEST_ACCESS = CLOUD_API_URL_PREFIX + REQUEST_ACCESS;
  private static final String REJECT_ACCESS = "/user/reject/{userId}";
  private static final String CLOUD_REJECT_ACCESS = CLOUD_API_URL_PREFIX + REJECT_ACCESS;
  private static final String ADMIN_NOTIFICATION = "/user/admin/notifications";
  private static final String CLOUD_ADMIN_NOTIFICATION = CLOUD_API_URL_PREFIX + ADMIN_NOTIFICATION;

  //  @ValueSource(strings = CLOUD_REQUEST_ACCESS)
  //  @ParameterizedTest
  //  void testRequestUserAccess(String url) throws Exception {
  //    Mockito.when(shoreUserService.requestAccess(Mockito.anyString()))
  //        .thenReturn(new RequestAccessResponse());
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isOk());
  //  }
  //
  //  @ValueSource(strings = CLOUD_REJECT_ACCESS)
  //  @ParameterizedTest
  //  void testRejectUserAccess(String url) throws Exception {
  //    Mockito.when(shoreUserService.rejectAccess(Mockito.anyLong()))
  //        .thenReturn(new RejectUserAccessResponse());
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.post(url, 1L)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isOk());
  //  }
  //
  //  @ValueSource(strings = CLOUD_REJECT_ACCESS)
  //  @ParameterizedTest
  //  void testRejectUserAccessServiceException(String url) throws Exception {
  //    Mockito.when(shoreUserService.rejectAccess(Mockito.anyLong()))
  //        .thenThrow(
  //            new GenericServiceException(
  //                "service exception",
  //                CommonErrorCodes.E_GEN_INTERNAL_ERR,
  //                HttpStatusCode.INTERNAL_SERVER_ERROR));
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.post(url, 1L)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
  //
  //  @ValueSource(strings = CLOUD_REJECT_ACCESS)
  //  @ParameterizedTest
  //  void testRejectUserAccessRuntimeException(String url) throws Exception {
  //    Mockito.when(shoreUserService.rejectAccess(Mockito.anyLong()))
  //        .thenThrow(RuntimeException.class);
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.post(url, 1L)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
  //
  //  @ValueSource(strings = CLOUD_ADMIN_NOTIFICATION)
  //  @ParameterizedTest
  //  void testGetAdminNotifications(String url) throws Exception {
  //    Mockito.when(shoreUserService.getAdminNotifications())
  //        .thenReturn(new AdminNotificationsResponse());
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isOk());
  //  }
  //
  //  @ValueSource(strings = CLOUD_ADMIN_NOTIFICATION)
  //  @ParameterizedTest
  //  void testGetAdminNotificationsRuntimeException(String url) throws Exception {
  //    Mockito.when(shoreUserService.getAdminNotifications()).thenThrow(RuntimeException.class);
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
}
