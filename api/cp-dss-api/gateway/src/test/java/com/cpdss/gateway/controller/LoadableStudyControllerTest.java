/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static com.cpdss.gateway.TestUtils.createDummyObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.service.AlgoErrorService;
import com.cpdss.gateway.service.LoadableStudyCargoService;
import com.cpdss.gateway.service.LoadableStudyService;
import com.cpdss.gateway.service.SyncRedisMasterService;
import com.cpdss.gateway.service.redis.RedisMasterSyncService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Test class for {@link LoadableStudyController}
 *
 * @author suhail.k
 */
@MockitoSettings
@WebMvcTest(controllers = LoadableStudyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
@TestPropertySource(properties = {"cpdss.build.env=none"})
class LoadableStudyControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadableStudyService loadableStudyService;

  @MockBean private CargoNominationResponse cargoNominationResponse;

  @MockBean private PortRotationResponse portRotationResponse;

  @MockBean private CommingleCargoResponse commingleCargoResponse;

  @MockBean private VoyageStatusResponse voyageStatusResponse;

  @MockBean private LoadableStudyCargoService loadableStudyCargoService;

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
  private static final Long TEST_VOYAGE_ID = 1L;

  private static final String CHARTERER = "charterer";
  private static final String DRAFT_MARK = "1000";
  private static final Long LOAD_LINE_ID = 1L;
  private static final String LOADABLE_STUDY_NAME = "LS-01";
  private static final String NAME = "name";
  private static final String CHARTERER_LITERAL = "charterer";
  private static final String DRAFT_MARK_LITERAL = "draftMark";
  private static final String LOAD_LINE_ID_LITERAL = "loadLineXId";
  private static final Long TEST_LODABLE_STUDY_ID = 1L;
  private static final Long TEST_LODABLE_PATTERN_ID = 1L;
  private static final BigDecimal TEST_BIGDECIMAL_VALUE = new BigDecimal(100);
  // API URLS
  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/cloud";
  private static final String LOADABLE_STUDY_LIST_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies";
  private static final String LOADABLE_STUDY_LIST_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + LOADABLE_STUDY_LIST_API_URL;
  private static final String LOADABLE_STUDY_LIST_SHIP_API_URL =
      SHIP_API_URL_PREFIX + LOADABLE_STUDY_LIST_API_URL;
  private static final String LOADABLE_STUDY_SAVE_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}";
  private static final String LOADABLE_STUDY_SAVE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + LOADABLE_STUDY_SAVE_API_URL;
  private static final String LOADABLE_STUDY_SAVE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + LOADABLE_STUDY_SAVE_API_URL;

  private static final String PORT_ROTATION_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports";
  private static final String PORT_ROTATION_SHIP_API_URL =
      SHIP_API_URL_PREFIX + PORT_ROTATION_API_URL;
  private static final String PORT_ROTATION_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + PORT_ROTATION_API_URL;
  private static final String GET_VOYAGES_BY_VESSEL_URL = "/vessels/{vesselId}/voyages";
  private static final String GET_VOYAGES_BY_VESSEL_SHIP_URL =
      SHIP_API_URL_PREFIX + GET_VOYAGES_BY_VESSEL_URL;
  private static final String GET_VOYAGES_BY_VESSEL_CLOUD_URL =
      CLOUD_API_URL_PREFIX + GET_VOYAGES_BY_VESSEL_URL;

  private static final String PORT_ROTATION_SAVE_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports/{id}";
  private static final String PORT_ROTATION_SAVE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + PORT_ROTATION_SAVE_API_URL;
  private static final String PORT_ROTATION_SAVE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + PORT_ROTATION_SAVE_API_URL;

  private static final String DISCHARGING_PORTS_SAVE_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/discharging-ports";
  private static final String DISCHARGING_PORTS_SAVE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DISCHARGING_PORTS_SAVE_API_URL;
  private static final String DISCHARGING_PORTS_SAVE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + DISCHARGING_PORTS_SAVE_API_URL;

  private static final String DELETE_LOADABLE_STUDY_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}";
  private static final String DELETE_LOADABLE_STUDY_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DELETE_LOADABLE_STUDY_API_URL;
  private static final String DELETE_LOADABLE_STUDY_SHIP_API_URL =
      SHIP_API_URL_PREFIX + DELETE_LOADABLE_STUDY_API_URL;

  private static final String DELETE_PORT_ROTATION_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports/{id}";
  private static final String DELETE_PORT_ROTATION_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DELETE_PORT_ROTATION_API_URL;
  private static final String DELETE_PORT_ROTATION_SHIP_API_URL =
      SHIP_API_URL_PREFIX + DELETE_PORT_ROTATION_API_URL;

  private static final String GET_ON_HAND_QUANTITIES_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports/{portId}/on-hand-quantities";
  private static final String GET_ON_HAND_QUANTITIES_API_URL_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_ON_HAND_QUANTITIES_API_URL;
  private static final String GET_ON_HAND_QUANTITIES_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_ON_HAND_QUANTITIES_API_URL;
  private static final String SAVE_ON_HAND_QUANTITIES_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports/{portId}/on-hand-quantities/{id}";
  private static final String GET_LOADABLE_PLAN_REPORT_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-patten/{loadablePatternId}/report";
  private static final String SAVE_ON_HAND_QUANTITIES_API_URL_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + SAVE_ON_HAND_QUANTITIES_API_URL;
  private static final String SAVE_ON_HAND_QUANTITIES_SHIP_API_URL =
      SHIP_API_URL_PREFIX + SAVE_ON_HAND_QUANTITIES_API_URL;
  private static final String GET_LOADABLE_PLAN_REPORT_COULD_API_URL =
      CLOUD_API_URL_PREFIX + GET_LOADABLE_PLAN_REPORT_API_URL;
  private static final String GET_LOADABLE_PLAN_REPORT_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_LOADABLE_PLAN_REPORT_API_URL;

  private static final String GENERATE_LOADABLE_PATTERN_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudiesId}/generate-loadable-patterns";
  private static final String GET_GENERATE_LOADABLE_PATTERN_DETAILS_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GENERATE_LOADABLE_PATTERN_API_URL;
  private static final String GET_GENERATE_LOADABLE_PATTERN_DETAILS_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GENERATE_LOADABLE_PATTERN_API_URL;

  private static final String GET_LOADABLE_PATTERN_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-patterns";
  private static final String GET_LOADABLE_PATTERN_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_LOADABLE_PATTERN_API_URL;
  private static final String GET_LOADABLE_PATTERN_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_LOADABLE_PATTERN_API_URL;

  private static final String GET_LOADABLE_STUDY_ALGO_STATUS_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-study-status";
  private static final String GET_LOADABLE_STUDY_ALGO_STATUS_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_LOADABLE_STUDY_ALGO_STATUS_API_URL;
  private static final String GET_LOADABLE_STUDY_ALGO_STATUS_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_LOADABLE_STUDY_ALGO_STATUS_API_URL;

  private static final String GET_LOADABLE_PATTERN_COMMINGLE_DETAILS_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-patterns/{loadablePatternId}/loadable-pattern-commingle-details/{loadablePatternCommingleDetailsId}";
  private static final String GET_LOADABLE_PATTERN_COMMINGLE_DETAILS_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_LOADABLE_PATTERN_COMMINGLE_DETAILS_API_URL;
  private static final String GET_LOADABLE_PATTERN_COMMINGLE_DETAILS_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_LOADABLE_PATTERN_COMMINGLE_DETAILS_API_URL;

  private static final String GET_LOADABLE_PATTERN_DETAILS_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-pattern-details/{loadablePatternId}";
  private static final String GET_LOADABLE_PATTERN_DETAILS_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_LOADABLE_PATTERN_DETAILS_API_URL;
  private static final String GET_LOADABLE_PATTERN_DETAILS_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_LOADABLE_PATTERN_DETAILS_API_URL;

  private static final String GET_CONFIRM_PLAN_STATUS_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/confirm-plan-status/{loadablePatternId}";
  private static final String GET_CONFIRM_PLAN_STATUS_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_CONFIRM_PLAN_STATUS_API_URL;
  private static final String GET_CONFIRM_PLAN_STATUS_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_CONFIRM_PLAN_STATUS_API_URL;

  private static final String GET_CONFIRM_PLAN_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/confirm-plan/{loadablePatternId}";
  private static final String GET_CONFIRM_PLAN_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_CONFIRM_PLAN_API_URL;
  private static final String GET_CONFIRM_PLAN_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_CONFIRM_PLAN_API_URL;

  private static final String LOADICATOR_RESULT_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadicator-result";
  private static final String LOADICATOR_RESULT_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + LOADICATOR_RESULT_API_URL;
  private static final String LOADICATOR_RESULT_SHIP_API_URL =
      SHIP_API_URL_PREFIX + LOADICATOR_RESULT_API_URL;

  private static final String GET_PATTERN_LIST_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudiesId}/patterns";
  private static final String GET_PATTERN_LIST_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_PATTERN_LIST_API_URL;
  private static final String GET_PATTERN_LIST_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_PATTERN_LIST_API_URL;

  private static final String GET_SYNOPTICAL_TABLE_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-pattern/{loadablePatternId}/synoptical-table";
  private static final String GET_SYNOPTICAL_TABLE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_SYNOPTICAL_TABLE_API_URL;
  private static final String GET_SYNOPTICAL_TABLE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_SYNOPTICAL_TABLE_API_URL;

  private static final String UPDATE_ULLAGE_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/loadable-patterns/{loadablePatternId}/update-ullage";
  private static final String UPDATE_ULLAGE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + UPDATE_ULLAGE_API_URL;
  private static final String UPDATE_ULLAGE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + UPDATE_ULLAGE_API_URL;

  private static final String GET_OR_SAVE_RULES_FOR_LOADBLE_STUDY_API_URL =
      "/loadble-study-rule/vessels/{vesselId}/ruleMasterSectionId/{sectionId}/loadableStudyId/{loadableStudyId}";
  private static final String GET_OR_SAVE_RULES_FOR_LOADBLE_STUDY_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_OR_SAVE_RULES_FOR_LOADBLE_STUDY_API_URL;
  private static final String GET_OR_SAVE_RULES_FOR_LOADBLE_STUDY_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_OR_SAVE_RULES_FOR_LOADBLE_STUDY_API_URL;

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private static final Long TEST_RULE_SECTION_ID = 1L;
  private static final Long LOADBLE_STUDY_ID = 44L;

  /**
   * Positive test case. Test method for positive response scenario
   *
   * @throws Exception
   */
  @ValueSource(strings = {LOADABLE_STUDY_LIST_CLOUD_API_URL, LOADABLE_STUDY_LIST_SHIP_API_URL})
  @ParameterizedTest
  void testGetLoadableStudyByVoyage(String url) throws Exception {
    when(this.loadableStudyService.getLoadableStudies(
            anyLong(), anyLong(), anyLong(), anyString(), anyLong()))
        .thenReturn(new LoadableStudyResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID)
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
  @ValueSource(strings = {LOADABLE_STUDY_LIST_CLOUD_API_URL, LOADABLE_STUDY_LIST_SHIP_API_URL})
  @ParameterizedTest
  void testGetLoadableStudyByVoyageServiceException(String url) throws Exception {
    when(this.loadableStudyService.getLoadableStudies(
            anyLong(), anyLong(), anyLong(), anyString(), anyLong()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * Negative test case. Test method for general run time exceptions
   *
   * @param url
   * @throws Exception
   */
  @ValueSource(strings = {LOADABLE_STUDY_LIST_CLOUD_API_URL, LOADABLE_STUDY_LIST_SHIP_API_URL})
  @ParameterizedTest
  void testGetLoadableStudyByVoyageRuntimeException(String url) throws Exception {
    when(this.loadableStudyService.getLoadableStudies(
            anyLong(), anyLong(), anyLong(), anyString(), anyLong()))
        .thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * Test method for loadable study save operation
   *
   * @throws Exception
   */
  @ValueSource(strings = {LOADABLE_STUDY_SAVE_CLOUD_API_URL, LOADABLE_STUDY_SAVE_SHIP_API_URL})
  @ParameterizedTest
  void testSaveLoadableStudy(String url) throws Exception {
    when(this.loadableStudyService.saveLoadableStudy(any(LoadableStudy.class), anyString(), any()))
        .thenReturn(new LoadableStudyResponse());
    MockMultipartFile firstFile =
        new MockMultipartFile("files", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 0L)
                .file(firstFile)
                .param(NAME, LOADABLE_STUDY_NAME)
                .param(CHARTERER_LITERAL, CHARTERER)
                .param(DRAFT_MARK_LITERAL, DRAFT_MARK)
                .param(LOAD_LINE_ID_LITERAL, String.valueOf(LOAD_LINE_ID))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Test method for loadable study save operation
   *
   * @throws Exception
   */
  @ValueSource(strings = {LOADABLE_STUDY_SAVE_CLOUD_API_URL, LOADABLE_STUDY_SAVE_SHIP_API_URL})
  @ParameterizedTest
  void testSaveLoadableStudyServiceException(String url) throws Exception {
    when(this.loadableStudyService.saveLoadableStudy(any(LoadableStudy.class), anyString(), any()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    MockMultipartFile firstFile =
        new MockMultipartFile("files", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 0L)
                .file(firstFile)
                .param(NAME, LOADABLE_STUDY_NAME)
                .param(CHARTERER_LITERAL, CHARTERER)
                .param(DRAFT_MARK_LITERAL, DRAFT_MARK)
                .param(LOAD_LINE_ID_LITERAL, String.valueOf(LOAD_LINE_ID))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * Test method for loadable study save operation
   *
   * @throws Exception
   */
  @ValueSource(strings = {LOADABLE_STUDY_SAVE_CLOUD_API_URL, LOADABLE_STUDY_SAVE_SHIP_API_URL})
  @ParameterizedTest
  void testSaveLoadableStudyRuntimeException(String url) throws Exception {
    when(this.loadableStudyService.saveLoadableStudy(any(LoadableStudy.class), anyString(), any()))
        .thenThrow(RuntimeException.class);
    MockMultipartFile firstFile =
        new MockMultipartFile("files", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 0L)
                .file(firstFile)
                .param(NAME, LOADABLE_STUDY_NAME)
                .param(CHARTERER_LITERAL, CHARTERER)
                .param(DRAFT_MARK_LITERAL, DRAFT_MARK)
                .param(LOAD_LINE_ID_LITERAL, String.valueOf(LOAD_LINE_ID))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * Test method for port rotation
   *
   * @throws Exception
   */
  @ValueSource(strings = {PORT_ROTATION_CLOUD_API_URL, PORT_ROTATION_SHIP_API_URL})
  @ParameterizedTest
  void testLoadableStudyPortList(String url) throws Exception {
    when(this.loadableStudyService.getLoadableStudyPortRotationList(
            anyLong(), anyLong(), anyLong(), anyString()))
        .thenReturn(new PortRotationResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_LODABLE_STUDY_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Test method for port rotation
   *
   * @throws Exception
   */
  @ValueSource(strings = {PORT_ROTATION_CLOUD_API_URL, PORT_ROTATION_SHIP_API_URL})
  @ParameterizedTest
  void testLoadableStudyPortListServiceException(String url) throws Exception {
    when(this.loadableStudyService.getLoadableStudyPortRotationList(
            anyLong(), anyLong(), anyLong(), anyString()))
        .thenThrow(
            new GenericServiceException(
                "test", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_LODABLE_STUDY_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  /**
   * Test method for port rotation
   *
   * @throws Exception
   */
  @ValueSource(strings = {PORT_ROTATION_CLOUD_API_URL, PORT_ROTATION_SHIP_API_URL})
  @ParameterizedTest
  void testLoadableStudyPortListRuntimeException(String url) throws Exception {
    when(this.loadableStudyService.getLoadableStudyPortRotationList(
            anyLong(), anyLong(), anyLong(), anyString()))
        .thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_LODABLE_STUDY_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * Test method for port rotation
   *
   * @throws Exception
   */
  @ValueSource(strings = {GET_VOYAGES_BY_VESSEL_CLOUD_URL, GET_VOYAGES_BY_VESSEL_SHIP_URL})
  @ParameterizedTest
  void testGetVoyageListByVessel(String url) throws Exception {
    when(this.loadableStudyService.getVoyageListByVessel(anyLong(), anyString()))
        .thenReturn(new VoyageResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Test method for port rotation
   *
   * @throws Exception
   */
  @ValueSource(strings = {GET_VOYAGES_BY_VESSEL_CLOUD_URL, GET_VOYAGES_BY_VESSEL_SHIP_URL})
  @ParameterizedTest
  void testGetVoyageListByVesselServiceException(String url) throws Exception {
    when(this.loadableStudyService.getVoyageListByVessel(anyLong(), anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  /**
   * Test method for port rotation
   *
   * @throws Exception
   */
  @ValueSource(strings = {GET_VOYAGES_BY_VESSEL_CLOUD_URL, GET_VOYAGES_BY_VESSEL_SHIP_URL})
  @ParameterizedTest
  void testGetVoyageListByVesselRuntimeException(String url) throws Exception {
    when(this.loadableStudyService.getVoyageListByVessel(anyLong(), anyString()))
        .thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {PORT_ROTATION_SAVE_CLOUD_API_URL, PORT_ROTATION_SAVE_SHIP_API_URL})
  @ParameterizedTest
  void testSavePortRotation(String url) throws Exception {
    when(this.loadableStudyService.savePortRotation(any(PortRotation.class), anyString(), any()))
        .thenReturn(new PortRotationResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 0)
                .content(this.createPortRotationRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
    ;
  }

  @ValueSource(strings = {PORT_ROTATION_SAVE_CLOUD_API_URL, PORT_ROTATION_SAVE_SHIP_API_URL})
  @ParameterizedTest
  void testSavePortRotationServiceException(String url) throws Exception {
    when(this.loadableStudyService.savePortRotation(any(PortRotation.class), anyString(), any()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 0)
                .content(this.createPortRotationRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
    ;
  }

  @ValueSource(strings = {PORT_ROTATION_SAVE_CLOUD_API_URL, PORT_ROTATION_SAVE_SHIP_API_URL})
  @ParameterizedTest
  void testSavePortRotationRuntimeException(String url) throws Exception {
    when(this.loadableStudyService.savePortRotation(any(PortRotation.class), anyString(), any()))
        .thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 0)
                .content(this.createPortRotationRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
    ;
  }

  private String createPortRotationRequest() throws JsonProcessingException {
    PortRotation request = new PortRotation();
    request.setDistanceBetweenPorts(TEST_BIGDECIMAL_VALUE);
    request.setEta(LocalDateTime.now().toString());
    request.setEtd(request.getEta());
    request.setLayCanFrom(LocalDate.now().toString());
    request.setLayCanTo(request.getLayCanFrom());
    request.setLoadableStudyId(1L);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }

  @ValueSource(
      strings = {DISCHARGING_PORTS_SAVE_CLOUD_API_URL, DISCHARGING_PORTS_SAVE_SHIP_API_URL})
  @ParameterizedTest
  void testsaveDischargingPorts(String url) throws Exception {
    when(this.loadableStudyService.saveDischargingPorts(
            any(DischargingPortRequest.class), anyString()))
        .thenReturn(new PortRotationResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1L)
                .content(this.createDischargingPortRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
    ;
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testsaveDischargingPortsException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.saveDischargingPorts(
            any(DischargingPortRequest.class), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    DISCHARGING_PORTS_SAVE_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1L)
                .content(this.createDischargingPortRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
    ;
  }

  private String createDischargingPortRequest() throws JsonProcessingException {
    DischargingPortRequest request = new DischargingPortRequest();
    List<Long> ids = new ArrayList<>();
    ids.add(1L);
    request.setPortIds(ids);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }

  @ValueSource(strings = {DELETE_LOADABLE_STUDY_CLOUD_API_URL, DELETE_LOADABLE_STUDY_SHIP_API_URL})
  @ParameterizedTest
  void testDeleteLoadableStudy(String url) throws Exception {
    when(this.loadableStudyService.deleteLoadableStudy(anyLong(), anyString()))
        .thenReturn(new LoadableStudyResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testDeleteLoadableStudyException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.deleteLoadableStudy(anyLong(), anyString())).thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(
                    DELETE_LOADABLE_STUDY_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DELETE_PORT_ROTATION_CLOUD_API_URL, DELETE_PORT_ROTATION_SHIP_API_URL})
  @ParameterizedTest
  void testDeletePortRotation(String url) throws Exception {
    when(this.loadableStudyService.deletePortRotation(anyLong(), anyLong(), anyString()))
        .thenReturn(new PortRotationResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testDeletePortRotationException(Class<? extends Exception> exceptionClass) throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.deletePortRotation(anyLong(), anyLong(), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(
                    DELETE_PORT_ROTATION_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {GET_ON_HAND_QUANTITIES_API_URL_CLOUD_API_URL, GET_ON_HAND_QUANTITIES_SHIP_API_URL})
  @ParameterizedTest
  void testGetOnHandQuantity(String url) throws Exception {
    when(this.loadableStudyService.getOnHandQuantity(
            anyLong(), anyLong(), anyLong(), anyLong(), anyString()))
        .thenReturn(new OnHandQuantityResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testGetOnHandQuantityRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.getOnHandQuantity(
            anyLong(), anyLong(), anyLong(), anyLong(), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    GET_ON_HAND_QUANTITIES_API_URL_CLOUD_API_URL,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    1,
                    1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {
        SAVE_ON_HAND_QUANTITIES_API_URL_CLOUD_API_URL,
        SAVE_ON_HAND_QUANTITIES_SHIP_API_URL
      })
  @ParameterizedTest
  void testSaveOnHandQuantity(String url) throws Exception {
    when(this.loadableStudyService.saveOnHandQuantity(any(OnHandQuantity.class), anyString()))
        .thenReturn(new OnHandQuantityResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1, 0)
                .content(this.createOnHandQuantityRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testSaveOnHandQuantityRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.saveOnHandQuantity(any(OnHandQuantity.class), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    SAVE_ON_HAND_QUANTITIES_API_URL_CLOUD_API_URL,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    1,
                    1,
                    0)
                .content(this.createOnHandQuantityRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  private String createOnHandQuantityRequest() throws JsonProcessingException {
    OnHandQuantity request = new OnHandQuantity();
    request.setArrivalVolume(TEST_BIGDECIMAL_VALUE);
    request.setArrivalQuantity(TEST_BIGDECIMAL_VALUE);
    request.setDepartureQuantity(TEST_BIGDECIMAL_VALUE);
    request.setDepartureVolume(TEST_BIGDECIMAL_VALUE);
    request.setFuelTypeId(1L);
    request.setTankId(1L);
    request.setLoadableStudyId(1L);
    request.setDensity(TEST_BIGDECIMAL_VALUE);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }

  @Test
  void testGetCargoNomination() throws Exception {
    when(loadableStudyService.getCargoNomination(Mockito.any(), Mockito.any()))
        .thenReturn(cargoNominationResponse);
    this.mockMvc
        .perform(
            get(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/cargo-nominations",
                    1,
                    1,
                    30)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isOk());
  }

  @Test
  void testGetCargoNominationWithException() throws Exception {
    when(loadableStudyService.getCargoNomination(Mockito.any(), Mockito.any()))
        .thenThrow(
            new GenericServiceException(
                "Error in getCargoNomination",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    this.mockMvc
        .perform(
            get(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/cargo-nominations",
                    1,
                    1,
                    30)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isInternalServerError())
        .andExpect(
            result ->
                assertEquals(
                    "Error in getCargoNomination", result.getResolvedException().getMessage()));
  }

  @Test
  void testSaveCargoNomination() throws Exception {
    when(loadableStudyService.saveCargoNomination(
            Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(cargoNominationResponse);
    this.mockMvc
        .perform(
            post(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/cargo-nominations/{id}",
                    1,
                    1,
                    30,
                    0)
                .content(createSaveCargoNominationRequest(false))
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  void testSaveCargoNominationWithException() throws Exception {
    when(loadableStudyService.saveCargoNomination(
            Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenThrow(
            new GenericServiceException(
                "Error in saveCargoNomination",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    this.mockMvc
        .perform(
            post(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/cargo-nominations/{id}",
                    1,
                    1,
                    30,
                    1)
                .content(createSaveCargoNominationRequest(true))
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError())
        .andExpect(
            result ->
                assertEquals(
                    "Error in saveCargoNomination", result.getResolvedException().getMessage()));
  }

  @Test
  void testDeleteCargoNomination() throws Exception {
    when(loadableStudyService.deleteCargoNomination(Mockito.any(), Mockito.any()))
        .thenReturn(cargoNominationResponse);
    this.mockMvc
        .perform(
            delete(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/cargo-nominations/{id}",
                    1,
                    1,
                    30,
                    123)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isOk());
  }

  @Test
  void testDeleteCargoNominationWithException() throws Exception {
    when(loadableStudyService.deleteCargoNomination(Mockito.any(), Mockito.any()))
        .thenThrow(
            new GenericServiceException(
                "Error in deleteCargoNomination",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    this.mockMvc
        .perform(
            delete(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/cargo-nominations/{id}",
                    1,
                    1,
                    30,
                    123)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isInternalServerError())
        .andExpect(
            result ->
                assertEquals(
                    "Error in deleteCargoNomination", result.getResolvedException().getMessage()));
  }

  private String createSaveCargoNominationRequest(boolean existingRecord)
      throws JsonProcessingException {
    CargoNomination request = new CargoNomination();
    request.setLoadableStudyId(30L);
    request.setId(existingRecord ? 15L : 0);
    request.setPriority(3L);
    request.setCargoId(1L);
    request.setAbbreviation("ABBREV");
    request.setColor("testColor");
    request.setMaxTolerance(new BigDecimal("10.0"));
    request.setMinTolerance(new BigDecimal("20.0"));
    request.setApi(new BigDecimal("5.0"));
    request.setTemperature(new BigDecimal("6.0"));
    request.setSegregationId(2L);
    List<LoadingPort> loadingPorts = new ArrayList<>();
    LoadingPort loadingPort = new LoadingPort();
    loadingPort.setId(1L);
    loadingPort.setQuantity(new BigDecimal("100.0"));
    loadingPorts.add(loadingPort);
    request.setLoadingPorts(loadingPorts);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }

  @Test
  void testGetCommingleCargo() throws Exception {
    when(loadableStudyService.getCommingleCargo(Mockito.any(), Mockito.any()))
        .thenReturn(commingleCargoResponse);
    this.mockMvc
        .perform(
            get(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/commingle-cargo",
                    1,
                    1,
                    30)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isOk());
  }

  @Test
  void testGetCommingleCargoWithGenericServiceException() throws Exception {
    when(loadableStudyService.getCommingleCargo(Mockito.any(), Mockito.any()))
        .thenThrow(
            new GenericServiceException(
                "Error in getCommingleCargo",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    this.mockMvc
        .perform(
            get(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/commingle-cargo",
                    1,
                    1,
                    30)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isInternalServerError())
        .andExpect(
            result ->
                assertEquals(
                    "Error in getCommingleCargo", result.getResolvedException().getMessage()));
  }

  @Test
  void testGetCommingleCargoWithException() throws Exception {
    when(loadableStudyService.getCommingleCargo(Mockito.any(), Mockito.any()))
        .thenThrow(new NullPointerException("Error in getCommingleCargo"));
    this.mockMvc
        .perform(
            get(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/commingle-cargo",
                    1,
                    1,
                    30)
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa"))
        .andExpect(status().isInternalServerError())
        .andExpect(
            result ->
                assertEquals(
                    "Error in getCommingleCargo", result.getResolvedException().getMessage()));
  }

  /**
   * @param url
   * @throws Exception void
   */
  @ValueSource(strings = {GET_LOADABLE_PATTERN_CLOUD_API_URL, GET_LOADABLE_PATTERN_SHIP_API_URL})
  @ParameterizedTest
  void testSaveLoadablePatternDetails(String url) throws Exception {
    when(this.loadableStudyService.saveLoadablePatterns(
            any(LoadablePlanRequest.class), anyLong(), anyString()))
        .thenReturn(new AlgoPatternResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .content(this.createLoadablePattern())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * @param exceptionClass
   * @throws Exception void
   */
  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testSaveLoadablePatternDetailsRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.saveLoadablePatterns(
            any(LoadablePlanRequest.class), anyLong(), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    GET_LOADABLE_PATTERN_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .content(this.createLoadablePattern())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /** @return Object */
  private String createLoadablePattern() throws JsonProcessingException {
    LoadablePlanRequest request = new LoadablePlanRequest();
    request.setProcessId("ID");
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }

  /**
   * @param url
   * @throws Exception void
   */
  @ValueSource(strings = {GET_LOADABLE_PATTERN_CLOUD_API_URL, GET_LOADABLE_PATTERN_SHIP_API_URL})
  @ParameterizedTest
  void testGetLoadablePatternDetails(String url) throws Exception {
    when(this.loadableStudyService.getLoadablePatterns(anyLong(), anyLong(), anyString()))
        .thenReturn(new LoadablePatternResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * @param exceptionClass
   * @throws Exception void
   */
  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testGetLoadablePatternDetailsRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.getLoadablePatterns(anyLong(), anyLong(), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    GET_LOADABLE_PATTERN_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * @param url
   * @throws Exception void
   */
  @ValueSource(strings = {LOADICATOR_RESULT_CLOUD_API_URL, LOADICATOR_RESULT_SHIP_API_URL})
  @ParameterizedTest
  void testSaveLoadicatorResult(String url) throws Exception {
    when(this.loadableStudyService.saveLoadicatorResult(
            any(LoadicatorResultsRequest.class), anyLong(), anyString()))
        .thenReturn(new AlgoPatternResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.createLoadicatorResult())
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * @param exceptionClass
   * @throws Exception void
   */
  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testSaveLoadicatorResultRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.saveLoadicatorResult(
            any(LoadicatorResultsRequest.class), anyLong(), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    LOADICATOR_RESULT_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.createLoadicatorResult())
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * @param url
   * @throws Exception void
   */
  @ValueSource(
      strings = {
        GET_LOADABLE_STUDY_ALGO_STATUS_CLOUD_API_URL,
        GET_LOADABLE_STUDY_ALGO_STATUS_SHIP_API_URL
      })
  @ParameterizedTest
  void testUpdateLoadableStudyStatus(String url) throws Exception {
    when(this.loadableStudyService.saveAlgoLoadableStudyStatus(
            any(AlgoStatusRequest.class), anyString()))
        .thenReturn(new AlgoStatusResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .content(this.createAlgoStatusRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * @param exceptionClass
   * @throws Exception void
   */
  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testUpdateLoadableStudyStatusRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.saveAlgoLoadableStudyStatus(
            any(AlgoStatusRequest.class), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    GET_LOADABLE_STUDY_ALGO_STATUS_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .content(this.createAlgoStatusRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * @return
   * @throws JsonProcessingException String
   */
  private String createAlgoStatusRequest() throws JsonProcessingException {
    AlgoStatusRequest request = new AlgoStatusRequest();
    request.setProcessId("ID");
    request.setLoadableStudyStatusId(1L);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }

  /** @return Object */
  private String createLoadicatorResult() throws JsonProcessingException {
    LoadablePlanRequest request = new LoadablePlanRequest();
    request.setProcessId("ID");
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }

  /**
   * @param url
   * @throws Exception void
   */
  @ValueSource(
      strings = {GET_CONFIRM_PLAN_STATUS_CLOUD_API_URL, GET_CONFIRM_PLAN_STATUS_SHIP_API_URL})
  @ParameterizedTest
  void testConfirmPlanStatus(String url) throws Exception {
    when(this.loadableStudyService.confirmPlanStatus(anyLong(), anyLong(), anyString()))
        .thenReturn(new ConfirmPlanStatusResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * @param exceptionClass
   * @throws Exception void
   */
  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testConfirmPlanStatusRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.confirmPlanStatus(anyLong(), anyLong(), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    GET_CONFIRM_PLAN_STATUS_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * @param url
   * @throws Exception void
   */
  @ValueSource(strings = {GET_CONFIRM_PLAN_CLOUD_API_URL, GET_CONFIRM_PLAN_SHIP_API_URL})
  @ParameterizedTest
  void testConfirmPlan(String url) throws Exception {
    when(this.loadableStudyService.confirmPlan(anyLong(), anyString()))
        .thenReturn(new CommonResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * @param exceptionClass
   * @throws Exception void
   */
  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testConfirmPlanRuntimeException(Class<? extends Exception> exceptionClass) throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.confirmPlan(anyLong(), anyString())).thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    GET_CONFIRM_PLAN_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * @param url
   * @throws Exception void
   */
  @ValueSource(
      strings = {
        GET_GENERATE_LOADABLE_PATTERN_DETAILS_CLOUD_API_URL,
        GET_GENERATE_LOADABLE_PATTERN_DETAILS_SHIP_API_URL
      })
  @ParameterizedTest
  void testGenerateLoadablePatterns(String url) throws Exception {
    when(this.loadableStudyService.generateLoadablePatterns(anyLong(), anyString()))
        .thenReturn(new AlgoPatternResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * @param exceptionClass
   * @throws Exception void
   */
  @Test
  void testGenerateLoadablePatternsGenericServiceException() throws Exception {

    when(this.loadableStudyService.generateLoadablePatterns(anyLong(), anyString()))
        .thenThrow(RuntimeException.class);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    GET_GENERATE_LOADABLE_PATTERN_DETAILS_CLOUD_API_URL,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().is(HttpStatusCode.SERVICE_UNAVAILABLE.value()));
  }

  /**
   * @param exceptionClass
   * @throws Exception void
   */
  @Test
  void testGenerateLoadablePatternsRuntimeException() throws Exception {

    when(this.loadableStudyService.generateLoadablePatterns(anyLong(), anyString()))
        .thenThrow(
            new GenericServiceException(
                "exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    GET_GENERATE_LOADABLE_PATTERN_DETAILS_CLOUD_API_URL,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * @param url
   * @throws Exception void
   */
  @ValueSource(
      strings = {
        GET_LOADABLE_PATTERN_DETAILS_CLOUD_API_URL,
        GET_LOADABLE_PATTERN_DETAILS_SHIP_API_URL
      })
  @ParameterizedTest
  void testLoadablePatternDetails(String url) throws Exception {
    when(this.loadableStudyService.getLoadablePatternDetails(
            anyLong(), anyLong(), anyLong(), anyString()))
        .thenReturn(new LoadablePlanDetailsResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * @param exceptionClass
   * @throws Exception void
   */
  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testLoadablePatternDetailsRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.getLoadablePatternDetails(
            anyLong(), anyLong(), anyLong(), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    GET_LOADABLE_PATTERN_DETAILS_CLOUD_API_URL,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    1,
                    1,
                    1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * @param url
   * @throws Exception void
   */
  @ValueSource(
      strings = {
        GET_LOADABLE_PATTERN_COMMINGLE_DETAILS_CLOUD_API_URL,
        GET_LOADABLE_PATTERN_COMMINGLE_DETAILS_SHIP_API_URL
      })
  @ParameterizedTest
  void testGetLoadablePatternCommingleDetails(String url) throws Exception {
    when(this.loadableStudyService.getLoadablePatternCommingleDetails(anyLong(), anyString()))
        .thenReturn(new LoadablePatternDetailsResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * @param exceptionClass
   * @throws Exception void
   */
  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testGetLoadablePatternCommingleDetailsRuntimeException(
      Class<? extends Exception> exceptionClass) throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.getLoadablePatternCommingleDetails(anyLong(), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    GET_LOADABLE_PATTERN_COMMINGLE_DETAILS_CLOUD_API_URL,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    1,
                    1,
                    1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void testSavePortRotationList() throws Exception {
    when(loadableStudyService.savePortRotationList(Mockito.any(), Mockito.any()))
        .thenReturn(portRotationResponse);
    this.mockMvc
        .perform(
            post(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports",
                    1,
                    1,
                    30,
                    0)
                .content(createPortRotationListRequest())
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  void testSavePortRotationListWithException() throws Exception {
    when(loadableStudyService.savePortRotationList(Mockito.any(), Mockito.any()))
        .thenThrow(
            new GenericServiceException(
                "Error in savePortRotationList",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    this.mockMvc
        .perform(
            post(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports",
                    1,
                    1,
                    30,
                    1)
                .content(createPortRotationListRequest())
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError())
        .andExpect(
            result ->
                assertEquals(
                    "Error in savePortRotationList", result.getResolvedException().getMessage()));
  }

  @Test
  void testSavePortRotationListWithGeneralException() throws Exception {
    when(loadableStudyService.savePortRotationList(Mockito.any(), Mockito.any()))
        .thenThrow(new RuntimeException("Error in savePortRotationList"));
    this.mockMvc
        .perform(
            post(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports",
                    1,
                    1,
                    30,
                    1)
                .content(createPortRotationListRequest())
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            result ->
                assertEquals(
                    "Error in savePortRotationList", result.getResolvedException().getMessage()));
  }

  private String createPortRotationListRequest() throws JsonProcessingException {
    PortRotationRequest portRotationRequest = new PortRotationRequest();
    List<PortRotation> portRotationList = new ArrayList<>();
    PortRotation request = new PortRotation();
    request.setDistanceBetweenPorts(TEST_BIGDECIMAL_VALUE);
    request.setEta(LocalDateTime.now().toString());
    request.setEtd(request.getEta());
    request.setLayCanFrom(LocalDate.now().toString());
    request.setLayCanTo(request.getLayCanFrom());
    request.setLoadableStudyId(1L);
    portRotationList.add(request);
    portRotationRequest.setPortList(portRotationList);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }

  @ValueSource(strings = {GET_PATTERN_LIST_CLOUD_API_URL, GET_PATTERN_LIST_SHIP_API_URL})
  @ParameterizedTest
  void testGetLoadablePatternList(String url) throws Exception {
    when(this.loadableStudyService.getLoadablePatternList(anyLong(), anyString()))
        .thenReturn(new LoadablePatternResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testGetLoadablePatternListRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.getLoadablePatternList(anyLong(), anyString())).thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    GET_PATTERN_LIST_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GET_SYNOPTICAL_TABLE_CLOUD_API_URL, GET_SYNOPTICAL_TABLE_SHIP_API_URL})
  @ParameterizedTest
  void testGetSynopticalTable(String url) throws Exception {
    when(this.loadableStudyService.getSynopticalTable(anyLong(), anyLong(), anyLong()))
        .thenReturn(new SynopticalTableResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_LODABLE_STUDY_ID, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testGetSynopticalTableException(Class<? extends Exception> exceptionClass) throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.getSynopticalTable(anyLong(), anyLong(), anyLong()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    GET_SYNOPTICAL_TABLE_CLOUD_API_URL,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    TEST_LODABLE_STUDY_ID,
                    1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GET_SYNOPTICAL_TABLE_CLOUD_API_URL, GET_SYNOPTICAL_TABLE_SHIP_API_URL})
  @ParameterizedTest
  void testSaveSynopticalTable(String url) throws Exception {
    SynopticalTableResponse response = new SynopticalTableResponse();
    response.setResponseStatus(
        new CommonSuccessResponse(
            String.valueOf(HttpStatusCode.OK.value()), CORRELATION_ID_HEADER_VALUE));
    when(this.loadableStudyService.saveSynopticalTable(
            any(SynopticalTableRequest.class), anyLong(), anyLong(), anyLong(), anyString()))
        .thenReturn(response);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_LODABLE_STUDY_ID, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(this.createSynopticalSaveRequest()))
        .andExpect(status().isOk());
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testSaveSynopticalTableException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.saveSynopticalTable(
            any(SynopticalTableRequest.class), anyLong(), anyLong(), anyLong(), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    GET_SYNOPTICAL_TABLE_CLOUD_API_URL,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    TEST_LODABLE_STUDY_ID,
                    1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(this.createSynopticalSaveRequest()))
        .andExpect(status().isInternalServerError());
  }

  private String createSynopticalSaveRequest()
      throws JsonProcessingException, InstantiationException, IllegalAccessException {
    SynopticalTableRequest request = new SynopticalTableRequest();
    request.setSynopticalRecords(new ArrayList<>());
    for (int i = 0; i < 4; i++) {
      SynopticalRecord record = (SynopticalRecord) createDummyObject(SynopticalRecord.class);
      request.getSynopticalRecords().add(record);
    }
    return new ObjectMapper().writeValueAsString(request);
  }

  @Test
  void testGetVoyageStatus() throws Exception {
    when(loadableStudyService.getVoyageStatus(
            Mockito.any(),
            Mockito.any(),
            Mockito.any(),
            Mockito.any(),
            Mockito.any(),
            Mockito.any()))
        .thenReturn(voyageStatusResponse);
    this.mockMvc
        .perform(
            post(
                    "/api/cloud/vessels/{vesselId}/voyages/{voyageId}/loadable-studies/{loadableStudyId}/ports/{portId}/voyage-status",
                    1,
                    1,
                    30,
                    1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .content(createVoyageStatusRequest())
                .header(AUTHORIZATION_HEADER, "4b5608ff-b77b-40c6-9645-d69856d4aafa")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * GetLoadablePlanReportException positive test
   *
   * @param url URL value
   * @throws Exception exception object
   */
  @ValueSource(
      strings = {GET_LOADABLE_PLAN_REPORT_COULD_API_URL, GET_LOADABLE_PLAN_REPORT_SHIP_API_URL})
  @ParameterizedTest
  void testGetLoadablePlanReportPositive(String url) throws Exception {
    when(loadableStudyService.downloadLoadablePlanReport(anyLong(), anyLong(), anyLong()))
        .thenReturn(new byte[1]);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    TEST_LODABLE_STUDY_ID,
                    TEST_LODABLE_PATTERN_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * GetLoadablePlanReportException exception test
   *
   * @param url URL value
   * @throws Exception exception object
   */
  @ValueSource(
      strings = {GET_LOADABLE_PLAN_REPORT_COULD_API_URL, GET_LOADABLE_PLAN_REPORT_SHIP_API_URL})
  @ParameterizedTest
  void testGetLoadablePlanReportException(String url) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    TEST_LODABLE_STUDY_ID,
                    TEST_LODABLE_PATTERN_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * GetLoadablePlanReportException exception test
   *
   * @param url URL value
   * @throws Exception exception object
   */
  @ValueSource(
      strings = {GET_LOADABLE_PLAN_REPORT_COULD_API_URL, GET_LOADABLE_PLAN_REPORT_SHIP_API_URL})
  @ParameterizedTest
  void testGetLoadablePlanReportGenericServiceException(String url) throws Exception {
    Exception ex =
        new GenericServiceException(
            "Failed to generate loadable plan report", "400", HttpStatusCode.BAD_REQUEST);
    when(loadableStudyService.downloadLoadablePlanReport(anyLong(), anyLong(), anyLong()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    TEST_LODABLE_STUDY_ID,
                    TEST_LODABLE_PATTERN_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  private String createVoyageStatusRequest() throws JsonProcessingException {
    VoyageStatusRequest request = new VoyageStatusRequest();
    request.setPortOrder(4L);
    request.setOperationType("ARR");
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }

  // @Test
  public void cargoHistorySuccess1() throws Exception {

    Map<String, String> params = new HashMap<>();
    params.put("loadedYear", "1997");
    List<String> filterKeys =
        Arrays.asList(
            "vesselName",
            "loadingPort",
            "grade",
            "loadedYear",
            "loadedMonth",
            "loadedDate",
            "api",
            "temperature",
            "startDate",
            "endDate");
    Map<String, String> filterParams =
        params.entrySet().stream()
            .filter(e -> filterKeys.contains(e.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    when(loadableStudyCargoService.getAllCargoHistory(
            Mockito.any(),
            Mockito.any(),
            Mockito.any(),
            Mockito.any(),
            Mockito.any(),
            Mockito.any(),
            Mockito.any()))
        .thenReturn(getCargoHistoryResponse());
    // this.loadableStudyCargoService.getAllCargoHistory(filterParams, 0, 10, "year", "desc", null,
    // null);

    mockMvc
        .perform(
            get("/api/ship/cargo-history")
                .param("page", String.valueOf(0))
                .param("pageSize", String.valueOf(10))
                .param("sortBy", "loadingPortName")
                .param("orderBy", "desc")
                .param("page", String.valueOf(0)))
        .andExpect(status().isOk());
  }

  private CargoHistoryResponse getCargoHistoryResponse() {
    CargoHistoryResponse response = new CargoHistoryResponse();
    CargoHistory history1 = new CargoHistory();
    history1.setLoadedYear(1770);
    history1.setVesselName("test");
    history1.setLoadingPortName("test");
    response.setCargoHistory(Arrays.asList(history1));
    return response;
  }

  @Test
  public void saveAlgoError() throws Exception {
    List<AlgoError> list = new ArrayList<>();
    list.add(new AlgoError("test", Arrays.asList("error1", "error2", "error3")));
    mockMvc
        .perform(
            post("/api/ship/save-alog-errors")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(list.toString()))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {UPDATE_ULLAGE_CLOUD_API_URL, UPDATE_ULLAGE_SHIP_API_URL})
  @ParameterizedTest
  void testUpdateUllage(String url) throws Exception {
    when(this.loadableStudyService.updateUllage(any(UpdateUllage.class), anyLong(), anyString()))
        .thenReturn(new UpdateUllage());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1)
                .content(this.createUpdateUllageRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  private String createUpdateUllageRequest()
      throws JsonProcessingException, InstantiationException, IllegalAccessException {
    UpdateUllage request = (UpdateUllage) createDummyObject(UpdateUllage.class);
    return new ObjectMapper().writeValueAsString(request);
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testUpdateUllageException(Class<? extends Exception> exceptionClass) throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.loadableStudyService.updateUllage(any(UpdateUllage.class), anyLong(), anyString()))
        .thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    UPDATE_ULLAGE_CLOUD_API_URL, TEST_VESSEL_ID, TEST_VOYAGE_ID, 1, 1)
                .content(this.createUpdateUllageRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {
        GET_OR_SAVE_RULES_FOR_LOADBLE_STUDY_CLOUD_API_URL,
        GET_OR_SAVE_RULES_FOR_LOADBLE_STUDY_SHIP_API_URL
      })
  @ParameterizedTest
  void testGetRulesAgainstLoadbleStudy(String url) throws Exception {
    when(this.loadableStudyService.getOrSaveRulesForLoadableStudy(
            anyLong(), anyLong(), anyLong(), null, anyString()))
        .thenReturn(new RuleResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_RULE_SECTION_ID, LOADBLE_STUDY_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {
        GET_OR_SAVE_RULES_FOR_LOADBLE_STUDY_CLOUD_API_URL,
        GET_OR_SAVE_RULES_FOR_LOADBLE_STUDY_SHIP_API_URL
      })
  @ParameterizedTest
  void testSaveRulesForVessel(String url) throws Exception {
    when(this.loadableStudyService.getOrSaveRulesForLoadableStudy(
            anyLong(), anyLong(), anyLong(), any(RuleRequest.class), anyString()))
        .thenReturn(new RuleResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_RULE_SECTION_ID, LOADBLE_STUDY_ID)
                .content(createRuleRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  private String createRuleRequest() throws JsonProcessingException {
    RuleRequest request = new RuleRequest();
    List<RulePlans> rulePlanList = new ArrayList<RulePlans>();
    List<Rules> rules = new ArrayList<Rules>();
    List<com.cpdss.gateway.domain.RulesInputs> ruleInputList =
        new ArrayList<com.cpdss.gateway.domain.RulesInputs>();
    RulePlans rulePlan = new RulePlans();
    Rules rule = new Rules();
    rule.setDisplayInSettings(true);
    rule.setEnable(true);
    // rule.setId("1");
    rule.setRuleTemplateId("701");
    rule.setIsHardRule(false);
    rule.setVesselRuleXId("176");
    rule.setRuleType("Absolute");
    com.cpdss.gateway.domain.RulesInputs input = new com.cpdss.gateway.domain.RulesInputs();
    input.setPrefix("Condensate cargo can only be put in a tank for");
    input.setType("Number");
    input.setMax("10");
    input.setMin("1");
    // input.setId("1");
    input.setSuffix("voyages apart");
    ruleInputList.add(input);
    rule.setInputs(ruleInputList);
    rules.add(rule);
    rulePlan.setRules(rules);
    request.setPlan(rulePlanList);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }
}
