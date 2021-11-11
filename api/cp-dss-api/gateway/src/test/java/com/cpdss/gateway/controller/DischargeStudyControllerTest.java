/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.security.ship.*;
import com.cpdss.gateway.service.*;
import com.cpdss.gateway.service.redis.RedisMasterSyncService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Test class for {@link LoadingPlanController}
 *
 * @author suhail.k
 */
@MockitoSettings
@WebMvcTest(controllers = DischargeStudyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
@TestPropertySource(properties = {"cpdss.build.env=ship"})
class DischargeStudyControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private DischargeStudyService dischargeStudyService;

  @MockBean private CargoNominationResponse cargoNominationResponse;

  @MockBean private PortRotationResponse portRotationResponse;

  @MockBean private CommingleCargoResponse commingleCargoResponse;

  @MockBean private VoyageStatusResponse voyageStatusResponse;

  @MockBean private LoadableStudyCargoService loadableStudyCargoService;

  @MockBean private LoadableStudyService loadableStudyService;

  @MockBean private ShipResponseBodyAdvice shipResponseBodyAdvice;

  @MockBean private ShipJwtService shipJwtService;

  @MockBean private ShipAuthenticationProvider jwtAuthenticationProvider;

  @MockBean private ShipUserAuthenticationProvider shipUserAuthenticationProvider;

  @MockBean private ShipUserDetailService userDetailService;

  @MockBean private ShipTokenExtractor jwtTokenExtractor;

  @MockBean private AuthenticationFailureHandler failureHandler;

  @MockBean AlgoErrorService algoErrorService;

  @MockBean
  @Qualifier("cargoRedisSyncService")
  private RedisMasterSyncService redisCargoService;

  @MockBean
  @Qualifier("vesselRedisSyncService")
  private RedisMasterSyncService redisVesselService;

  @MockBean
  @Qualifier("portRedisSyncService")
  private RedisMasterSyncService redisPortService;

  @MockBean SyncRedisMasterService syncRedisMasterService;

  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";
  private static final Long TEST_VESSEL_ID = 1L;
  private static final Long TEST_DISCHARGE_STUDY_ID = 1L;
  private static final Long TEST_VOYAGE_ID = 1L;

  // API URLS
  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/ship";

  private static final String DISCHARGE_STUDY_GENERATE_PATTERN_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudiesId}/generate-discharge-patterns";

  /**
   * Positive test case. Test method for positive response scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DISCHARGE_STUDY_GENERATE_PATTERN_URL,
        SHIP_API_URL_PREFIX + DISCHARGE_STUDY_GENERATE_PATTERN_URL
      })
  @ParameterizedTest
  void testGeneratePattern(String url) throws Exception {
    when(this.dischargeStudyService.generateDischargePatterns(
            anyLong(), anyLong(), anyLong(), any(String.class)))
        .thenReturn(new AlgoPatternResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_DISCHARGE_STUDY_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Negative test case. Test method for generic service exception scenario
   *
   * @param url
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DISCHARGE_STUDY_GENERATE_PATTERN_URL,
        SHIP_API_URL_PREFIX + DISCHARGE_STUDY_GENERATE_PATTERN_URL
      })
  @ParameterizedTest
  void testGeneratePatternException(String url) throws Exception {
    when(this.dischargeStudyService.generateDischargePatterns(
            anyLong(), anyLong(), anyLong(), any(String.class)))
        .thenThrow(this.getGenericException());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_DISCHARGE_STUDY_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * Negative test case. Test method for generic service exception scenario
   *
   * @param url
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DISCHARGE_STUDY_GENERATE_PATTERN_URL,
        SHIP_API_URL_PREFIX + DISCHARGE_STUDY_GENERATE_PATTERN_URL
      })
  @ParameterizedTest
  void testGeneratePatternRuntimeException(String url) throws Exception {
    when(this.dischargeStudyService.generateDischargePatterns(
            anyLong(), anyLong(), anyLong(), any(String.class)))
        .thenThrow(RuntimeException.class);
    ResultActions actions =
        this.mockMvc.perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_DISCHARGE_STUDY_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE));

    actions.andExpect(status().isServiceUnavailable());
  }

  private GenericServiceException getGenericException() {
    return new GenericServiceException(
        "service exception",
        CommonErrorCodes.E_GEN_INTERNAL_ERR,
        HttpStatusCode.INTERNAL_SERVER_ERROR);
  }
}
