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
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyStatusResponse;
import com.cpdss.gateway.security.ship.*;
import com.cpdss.gateway.service.*;
import com.cpdss.gateway.service.redis.RedisMasterSyncService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
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

  @Autowired DischargeStudyController dischargeStudyController;

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
  private static final String GET_DIS_STUDY_STA =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/discharge-pattern-status";
  private static final String UPDATE_DIS_STUDY =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/discharge-study-status";
  private static final String ALGO_ERROR =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/algo-errors";
  private static final String GET_DIS_STUDY_STA_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_DIS_STUDY_STA;
  private static final String GET_DIS_STUDY_STA_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_DIS_STUDY_STA;

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

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + GET_DIS_STUDY_STA, SHIP_API_URL_PREFIX + GET_DIS_STUDY_STA})
  @ParameterizedTest
  void testGetDischargeStudyStatus(String url) throws Exception {
    LoadablePlanRequest loadablePlanRequest = new LoadablePlanRequest();
    loadablePlanRequest.setProcessId("1");
    Mockito.when(
            this.loadableStudyService.getDischargeStudyStatus(
                Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
        .thenReturn(new DischargeStudyStatusResponse());
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_DISCHARGE_STUDY_ID)
                .content(mapper.writeValueAsString(loadablePlanRequest))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + GET_DIS_STUDY_STA, SHIP_API_URL_PREFIX + GET_DIS_STUDY_STA})
  @ParameterizedTest
  void testGetDischargeStudyStatusServiceException(String url) throws Exception {
    LoadablePlanRequest loadablePlanRequest = new LoadablePlanRequest();
    loadablePlanRequest.setProcessId("1");
    Mockito.when(
            this.loadableStudyService.getDischargeStudyStatus(
                Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
        .thenThrow(this.getGenericException());
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_DISCHARGE_STUDY_ID)
                .content(mapper.writeValueAsString(loadablePlanRequest))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + GET_DIS_STUDY_STA, SHIP_API_URL_PREFIX + GET_DIS_STUDY_STA})
  @ParameterizedTest
  void testGetDischargeStudyStatusRuntimeException(String url) throws Exception {
    LoadablePlanRequest loadablePlanRequest = new LoadablePlanRequest();
    loadablePlanRequest.setProcessId("1");
    Mockito.when(
            this.loadableStudyService.getDischargeStudyStatus(
                Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_DISCHARGE_STUDY_ID)
                .content(mapper.writeValueAsString(loadablePlanRequest))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  //  @ValueSource(
  //          strings = {
  //                  CLOUD_API_URL_PREFIX + UPDATE_DIS_STUDY,
  //                  SHIP_API_URL_PREFIX + UPDATE_DIS_STUDY
  //          })
  //  @ParameterizedTest
  //  void testUpdateDischargeStudyStatus(String url) throws Exception {
  //    AlgoStatusRequest request = new AlgoStatusRequest();
  //    request.setProcessId("1");
  //
  // Mockito.when(this.loadableStudyService.saveAlgoLoadableStudyStatus(Mockito.any(),Mockito.anyString())).thenReturn(new AlgoStatusResponse());
  ////
  // Mockito.when(dischargeStudyController.updateDischargeStudyStatus(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.any(),Mockito.any())).thenCallRealMethod();
  //  //
  // ReflectionTestUtils.setField(dischargeStudyController,"loadableStudyService",this.loadableStudyService);
  // //   ObjectMapper mapper = new ObjectMapper();
  //    this.mockMvc
  //            .perform(
  //                    MockMvcRequestBuilders.post(
  //                                    url, TEST_VESSEL_ID, TEST_VOYAGE_ID,
  // TEST_DISCHARGE_STUDY_ID)
  //    //                        .content(mapper.writeValueAsString(request))
  //                            .param("vesselId","1")
  //                            .param("voyageId","1")
  //                            .param("loadableStudyId","1")
  //                            .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                            .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                            .accept(MediaType.APPLICATION_JSON_VALUE))
  //            .andExpect(status().isOk());
  //  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + ALGO_ERROR, SHIP_API_URL_PREFIX + ALGO_ERROR})
  @ParameterizedTest
  void testGetAlgoError(String url) throws Exception {
    Mockito.when(
            this.loadableStudyService.getAlgoErrorLoadableStudy(
                Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new AlgoErrorResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + ALGO_ERROR, SHIP_API_URL_PREFIX + ALGO_ERROR})
  @ParameterizedTest
  void testGetAlgoErrorServiceException(String url) throws Exception {
    Mockito.when(
            this.loadableStudyService.getAlgoErrorLoadableStudy(
                Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(this.getGenericException());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + ALGO_ERROR, SHIP_API_URL_PREFIX + ALGO_ERROR})
  @ParameterizedTest
  void testGetAlgoErrorRuntimeException(String url) throws Exception {
    Mockito.when(
            this.loadableStudyService.getAlgoErrorLoadableStudy(
                Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }
}
