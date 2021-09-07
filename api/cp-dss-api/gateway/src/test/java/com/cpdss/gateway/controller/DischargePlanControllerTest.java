/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.dischargeplan.DischargeInformation;
import com.cpdss.gateway.service.dischargeplan.DischargeInformationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@MockitoSettings
@WebMvcTest(controllers = DischargePlanController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {GatewayTestConfiguration.class, DischargeInformationService.class})
@TestPropertySource(properties = {"cpdss.build.env=none"})
public class DischargePlanControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean DischargeInformationService dischargeInformationService;

  // Common for discharge plan controller
  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";
  private static final Long TEST_VESSEL_ID = 1L;
  private static final Long TEST_DISCHARGE_INFO_ID = 1L;
  private static final Long TEST_PORT_ROTATION_ID = 1L;
  private static final Long TEST_VOYAGE_ID = 1L;

  // API URLS
  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/ship";

  private static final String DIS_INFO_GET_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_RULE_GET_URL =
      "/vessels/{vesselId}/discharge-info-rule/{dischargingInfoId}";

  @Test
  public void contextLoads() throws Exception {
    assertThat(dischargeInformationService).isNotNull();
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + DIS_INFO_GET_URL, SHIP_API_URL_PREFIX + DIS_INFO_GET_URL})
  @ParameterizedTest
  public void getDischargeInformationByPortRIdTestCase1(String url) throws Exception {
    when(this.dischargeInformationService.getDischargeInformation(anyLong(), anyLong(), anyLong()))
        .thenReturn(new DischargeInformation());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    TEST_DISCHARGE_INFO_ID,
                    TEST_PORT_ROTATION_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }
}
