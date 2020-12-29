/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.VesselDetailsResponse;
import com.cpdss.gateway.service.VesselInfoService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/** @Author jerin.g */
@MockitoSettings
@WebMvcTest(controllers = VesselInfoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
public class VesselInfoControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private VesselInfoService vesselInfoService;

  private static final Long TEST_VESSEL_ID = 1L;
  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";

  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/cloud";
  private static final String GET_VESSEL_DETAILS_API_URL = "/vessel-details/{vesselId}";
  private static final String GET_VESSEL_DETAILS_API_URL_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_VESSEL_DETAILS_API_URL;
  private static final String GET_VESSEL_DETAILS_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_VESSEL_DETAILS_API_URL;

  @ValueSource(
      strings = {GET_VESSEL_DETAILS_API_URL_CLOUD_API_URL, GET_VESSEL_DETAILS_SHIP_API_URL})
  @ParameterizedTest
  void testGetVesselsDetails(String url) throws Exception {
    when(this.vesselInfoService.getVesselsDetails(anyLong(), anyString()))
        .thenReturn(new VesselDetailsResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testGetVesselsDetailsRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.vesselInfoService.getVesselsDetails(anyLong(), anyString())).thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(GET_VESSEL_DETAILS_API_URL_CLOUD_API_URL, TEST_VESSEL_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }
}