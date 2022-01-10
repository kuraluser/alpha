/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import com.cpdss.gateway.service.AlgoErrorService;
import com.cpdss.gateway.service.LoadableStudyCargoService;
import com.cpdss.gateway.service.SyncRedisMasterService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingInstructionService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingPlanServiceImpl;
import com.cpdss.gateway.service.redis.RedisMasterSyncService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Test class for {@link LoadingPlanController}
 *
 * @author suhail.k
 */
@MockitoSettings
@WebMvcTest(controllers = LoadingPlanController.class)
@Import(LoadingPlanController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
@TestPropertySource(properties = {"cpdss.build.env=none"})
class LoadingPlanControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private LoadingPlanController loadingPlanController;
  @MockBean private LoadingInformationService loadingInformationService;
  @MockBean LoadingPlanServiceImpl loadingPlanServiceImpl;

  @MockBean private LoadingInstructionService loadingInstructionService;

  @MockBean private CargoNominationResponse cargoNominationResponse;

  @MockBean private PortRotationResponse portRotationResponse;

  @MockBean private CommingleCargoResponse commingleCargoResponse;

  @MockBean private VoyageStatusResponse voyageStatusResponse;

  @MockBean private LoadableStudyCargoService loadableStudyCargoService;

  // @MockBean private LoadingPlanService loadingPlanService;

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
  private static final Long TEST_LOADING_INFO_ID = 1L;
  private static final Long TEST_PORT_ROTATION_ID = 1L;
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
  private static final String SHIP_API_URL_PREFIX = "/api/ship";

  private static final String LOADING_INSTRUCTION_LIST_API_URL =
      "/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}";
  private static final String LOADING_INSTRUCTION_ADD_API_URL =
      "/new-instruction/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}";
  private static final String LOADING_INSTRUCTION_UPDATE_API_URL =
      "/update-instruction/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}";
  private static final String LOADING_INSTRUCTION_DELETE_API_URL =
      "/delete-instruction/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}";

  private static final String LOADING_GET_PLAN_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/loading-plan/{portRotationId}";
  private static final String GET_PORT_ROT = "/vessels/{vesselId}/loading-info/{id}";
  private static final String GET_LOAD_INFO =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/port-rotation/{portRotationId}";
  private static final String SAVE_LOAD_INFO =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info";
  private static final String UPDATE_ULLAGE =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/update-ullage/{portRotationId}";
  private static final String LOAD_INFO_STA =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/loading-info-status";
  private static final String GEN_LOAD =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/generate-loading-plan";
  private static final String LOAD_PLAN =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/rules";
  private static final String EDIT_LOADING =
      "/edit-instruction/vessels/{vesselId}/loading-info/{infoId}/port-rotation/{portRotationId}";
  private static final String LOAD_SEQ =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/loading-sequence";
  private static final String SAVE_LOAD =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/loading-plan";
  private static final String UPDATE_ULAGE =
      "/vessels/{vesselId}/pattern/{patternId}/port/{portRotationId}/update-ullage/{operationType}";
  private static final String LOAD_STA =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/algo-status";
  private static final String ALGO_ERROR =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/algo-errors/{conditionType}";
  private static final String SAVE_RULES_LS = "/loading/ullage-bill-update";
  private static final String UPLOAD_TIDE = "/loading/{loadingId}/upload/port-tide-details";
  private static final String LOADABLE_PLAN = "/loading/download/port-tide-template";
  private static final String SIM_JSON = "/simulator-json/vessels/{vesselId}/loading-info/{infoId}";

  private static final String AUTHORIZATION_HEADER = "Authorization";

  /**
   * Positive test case. Test method for positive response scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_LIST_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_LIST_API_URL
      })
  @ParameterizedTest
  void testGetAllLoadingInstructions(String url) throws Exception {
    when(this.loadingInstructionService.getLoadingInstructions(anyLong(), anyLong(), anyLong()))
        .thenReturn(new LoadingInstructionResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
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
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_LIST_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_LIST_API_URL
      })
  @ParameterizedTest
  void testGetAllLoadingInstructionsException(String url) throws Exception {
    when(this.loadingInstructionService.getLoadingInstructions(anyLong(), anyLong(), anyLong()))
        .thenThrow(this.getGenericException());
    when(loadingPlanController.getAllLoadingInstructions(any(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInstructionService", loadingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
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
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_LIST_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_LIST_API_URL
      })
  @ParameterizedTest
  void testGetAllLoadingInstructionsRuntimeException(String url) throws Exception {
    when(this.loadingInstructionService.getLoadingInstructions(anyLong(), anyLong(), anyLong()))
        .thenThrow(RuntimeException.class);
    when(loadingPlanController.getAllLoadingInstructions(any(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInstructionService", loadingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  /**
   * Test method for ADD loading instruction
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_ADD_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_ADD_API_URL
      })
  @ParameterizedTest
  void testAddLoadingInstruction(String url) throws Exception {
    when(this.loadingInstructionService.addLoadingInstruction(
            anyLong(), anyLong(), anyLong(), any(LoadingInstructionsSaveRequest.class)))
        .thenReturn(new LoadingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", "")));
    LoadingInstructionsSaveRequest request = new LoadingInstructionsSaveRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Test method for ADD loading instruction- negative scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_ADD_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_ADD_API_URL
      })
  @ParameterizedTest
  void testAddLoadingInstructionException(String url) throws Exception {
    when(this.loadingInstructionService.addLoadingInstruction(
            anyLong(), anyLong(), anyLong(), any(LoadingInstructionsSaveRequest.class)))
        .thenThrow(this.getGenericException());
    LoadingInstructionsSaveRequest request = new LoadingInstructionsSaveRequest();
    ObjectMapper mapper = new ObjectMapper();
    when(loadingPlanController.addLoadingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInstructionService", loadingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * Test method for ADD loading instruction- negative scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_ADD_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_ADD_API_URL
      })
  @ParameterizedTest
  void testAddLoadingInstructionRuntimeException(String url) throws Exception {
    when(this.loadingInstructionService.addLoadingInstruction(
            anyLong(), anyLong(), anyLong(), any(LoadingInstructionsSaveRequest.class)))
        .thenThrow(RuntimeException.class);
    LoadingInstructionsSaveRequest request = new LoadingInstructionsSaveRequest();
    ObjectMapper mapper = new ObjectMapper();
    when(loadingPlanController.addLoadingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInstructionService", loadingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  /**
   * Test method for UPDATE loading instruction
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_UPDATE_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_UPDATE_API_URL
      })
  @ParameterizedTest
  void testUpdateLoadingInstruction(String url) throws Exception {
    when(this.loadingInstructionService.updateLoadingInstructions(
            anyLong(), anyLong(), anyLong(), any(LoadingInstructionsUpdateRequest.class)))
        .thenReturn(new LoadingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", "")));
    LoadingInstructionsUpdateRequest request = new LoadingInstructionsUpdateRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Test method for UPDATE loading instruction - negative scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_UPDATE_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_UPDATE_API_URL
      })
  @ParameterizedTest
  void testUpdateLoadingInstructionException(String url) throws Exception {
    when(this.loadingInstructionService.updateLoadingInstructions(
            anyLong(), anyLong(), anyLong(), any(LoadingInstructionsUpdateRequest.class)))
        .thenThrow(this.getGenericException());
    when(loadingPlanController.updateLoadingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInstructionService", loadingInstructionService);
    LoadingInstructionsUpdateRequest request = new LoadingInstructionsUpdateRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_PORT_ROTATION_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * Test method for UPDATE loading instruction - negative scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_UPDATE_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_UPDATE_API_URL
      })
  @ParameterizedTest
  void testUpdateLoadingInstructionRuntimeException(String url) throws Exception {
    when(this.loadingInstructionService.updateLoadingInstructions(
            anyLong(), anyLong(), anyLong(), any(LoadingInstructionsUpdateRequest.class)))
        .thenThrow(RuntimeException.class);
    LoadingInstructionsUpdateRequest request = new LoadingInstructionsUpdateRequest();
    ObjectMapper mapper = new ObjectMapper();
    when(loadingPlanController.updateLoadingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInstructionService", loadingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  /**
   * Test method for save all loading instruction status changes
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_DELETE_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_DELETE_API_URL
      })
  @ParameterizedTest
  void testDeleteLoadingInstruction(String url) throws Exception {
    when(this.loadingInstructionService.deleteLoadingInstructions(
            anyLong(), anyLong(), anyLong(), any(LoadingInstructionsStatus.class)))
        .thenReturn(new LoadingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", "")));
    LoadingInstructionsStatus request = new LoadingInstructionsStatus();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Test method for save all loading instruction status changes - negative scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_DELETE_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_DELETE_API_URL
      })
  @ParameterizedTest
  void testDeleteLoadingInstructionException(String url) throws Exception {
    when(this.loadingInstructionService.deleteLoadingInstructions(
            anyLong(), anyLong(), anyLong(), any(LoadingInstructionsStatus.class)))
        .thenThrow(this.getGenericException());
    LoadingInstructionsStatus request = new LoadingInstructionsStatus();
    ObjectMapper mapper = new ObjectMapper();
    when(loadingPlanController.deleteLoadingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInstructionService", loadingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * Test method for save all loading instruction status changes - negative scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_INSTRUCTION_DELETE_API_URL,
        SHIP_API_URL_PREFIX + LOADING_INSTRUCTION_DELETE_API_URL
      })
  @ParameterizedTest
  void testDeleteLoadingInstructionRuntimeException(String url) throws Exception {
    when(this.loadingInstructionService.deleteLoadingInstructions(
            anyLong(), anyLong(), anyLong(), any(LoadingInstructionsStatus.class)))
        .thenThrow(RuntimeException.class);
    LoadingInstructionsStatus request = new LoadingInstructionsStatus();
    ObjectMapper mapper = new ObjectMapper();
    when(loadingPlanController.deleteLoadingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInstructionService", loadingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  private GenericServiceException getGenericException() {
    return new GenericServiceException(
        "service exception",
        CommonErrorCodes.E_GEN_INTERNAL_ERR,
        HttpStatusCode.INTERNAL_SERVER_ERROR);
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_GET_PLAN_API_URL,
        SHIP_API_URL_PREFIX + LOADING_GET_PLAN_API_URL
      })
  @ParameterizedTest
  public void getLoadingPlanTest(String url) throws Exception {
    when(this.loadingPlanServiceImpl.getLoadingPlan(anyLong(), anyLong(), anyLong(), anyLong()))
        .thenReturn(new LoadingPlanResponse(new CommonSuccessResponse("SUCCESS", "")));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_LOADING_INFO_ID, TEST_PORT_ROTATION_ID))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + GET_PORT_ROT, SHIP_API_URL_PREFIX + GET_PORT_ROT})
  @ParameterizedTest
  public void testGetPortRotationDetails(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingPortRotationDetails(
                Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(new Object());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + GET_PORT_ROT, SHIP_API_URL_PREFIX + GET_PORT_ROT})
  @ParameterizedTest
  public void testGetPortRotationDetailsServiceException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingPortRotationDetails(
                Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            loadingPlanController.getPortRotationDetails(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + GET_PORT_ROT, SHIP_API_URL_PREFIX + GET_PORT_ROT})
  @ParameterizedTest
  public void testGetPortRotationDetailsRuntimeException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingPortRotationDetails(
                Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            loadingPlanController.getPortRotationDetails(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + GET_LOAD_INFO, SHIP_API_URL_PREFIX + GET_LOAD_INFO})
  @ParameterizedTest
  public void testGetLoadingInformation(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.getLoadingInformationByPortRotation(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(new LoadingInformation());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + GET_LOAD_INFO, SHIP_API_URL_PREFIX + GET_LOAD_INFO})
  @ParameterizedTest
  public void testGetLoadingInformationServiceException(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.getLoadingInformationByPortRotation(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            loadingPlanController.getLoadingInformation(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + GET_LOAD_INFO, SHIP_API_URL_PREFIX + GET_LOAD_INFO})
  @ParameterizedTest
  public void testGetLoadingInformationRuntimeException(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.getLoadingInformationByPortRotation(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            loadingPlanController.getLoadingInformation(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + SAVE_LOAD_INFO, SHIP_API_URL_PREFIX + SAVE_LOAD_INFO})
  @ParameterizedTest
  public void testSaveLoadingInformation(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.saveLoadingInformation(Mockito.any(), Mockito.anyString()))
        .thenReturn(new LoadingInformationResponse());
    ObjectMapper mapper = new ObjectMapper();
    LoadingInformationRequest request = new LoadingInformationRequest();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, 1L, 1L)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + SAVE_LOAD_INFO, SHIP_API_URL_PREFIX + SAVE_LOAD_INFO})
  @ParameterizedTest
  public void testSaveLoadingInformationServiceException(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.saveLoadingInformation(Mockito.any(), Mockito.anyString()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            loadingPlanController.saveLoadingInformation(
                Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    ObjectMapper mapper = new ObjectMapper();
    LoadingInformationRequest request = new LoadingInformationRequest();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, 1L, 1L)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + UPDATE_ULLAGE, SHIP_API_URL_PREFIX + UPDATE_ULLAGE})
  @ParameterizedTest
  public void testUpdateUllage(String url) throws Exception {
    Mockito.when(
            this.loadingInformationService.processUpdateUllage(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyString()))
        .thenReturn(new UpdateUllage());
    ObjectMapper mapper = new ObjectMapper();
    UpdateUllage updateUllageRequest = new UpdateUllage();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, 1L, 1L, 1L, 1L)
                .content(mapper.writeValueAsString(updateUllageRequest))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + UPDATE_ULLAGE, SHIP_API_URL_PREFIX + UPDATE_ULLAGE})
  @ParameterizedTest
  public void testUpdateUllageServiceException(String url) throws Exception {
    Mockito.when(
            this.loadingInformationService.processUpdateUllage(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyString()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            loadingPlanController.updateUllage(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInformationService", loadingInformationService);
    ObjectMapper mapper = new ObjectMapper();
    UpdateUllage updateUllageRequest = new UpdateUllage();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, 1L, 1L, 1L, 1L)
                .content(mapper.writeValueAsString(updateUllageRequest))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + LOAD_INFO_STA, SHIP_API_URL_PREFIX + LOAD_INFO_STA})
  @ParameterizedTest
  public void testLoadingInfoStatus(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.saveLoadingInfoStatus(Mockito.any(), Mockito.anyString()))
        .thenReturn(new LoadingInfoAlgoResponse());
    ObjectMapper mapper = new ObjectMapper();
    AlgoStatusRequest request = new AlgoStatusRequest();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, 1L, 1L, 1L)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + LOAD_INFO_STA, SHIP_API_URL_PREFIX + LOAD_INFO_STA})
  @ParameterizedTest
  public void testLoadingInfoStatusServiceException(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.saveLoadingInfoStatus(Mockito.any(), Mockito.anyString()))
        .thenThrow(this.getGenericException());
    ObjectMapper mapper = new ObjectMapper();
    AlgoStatusRequest request = new AlgoStatusRequest();
    Mockito.when(
            loadingPlanController.loadingInfoStatus(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanServiceImpl", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, 1L, 1L, 1L)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + LOAD_INFO_STA, SHIP_API_URL_PREFIX + LOAD_INFO_STA})
  @ParameterizedTest
  public void testLoadingInfoStatusRuntimeException(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.saveLoadingInfoStatus(Mockito.any(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    ObjectMapper mapper = new ObjectMapper();
    AlgoStatusRequest request = new AlgoStatusRequest();
    Mockito.when(
            loadingPlanController.loadingInfoStatus(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanServiceImpl", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, 1L, 1L, 1L)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + GEN_LOAD, SHIP_API_URL_PREFIX + GEN_LOAD})
  @ParameterizedTest
  public void testGenerateLoadingPlan(String url) throws Exception {
    Mockito.when(this.loadingInformationService.generateLoadingPlan(Mockito.anyLong()))
        .thenReturn(new LoadingInfoAlgoResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + GEN_LOAD, SHIP_API_URL_PREFIX + GEN_LOAD})
  @ParameterizedTest
  public void testGenerateLoadingPlanServiceException(String url) throws Exception {
    Mockito.when(this.loadingInformationService.generateLoadingPlan(Mockito.anyLong()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            loadingPlanController.generateLoadingPlan(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInformationService", loadingInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + LOAD_PLAN, SHIP_API_URL_PREFIX + LOAD_PLAN})
  @ParameterizedTest
  public void testGetLoadingPlanRules(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingPlanRules(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(new RuleResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + LOAD_PLAN, SHIP_API_URL_PREFIX + LOAD_PLAN})
  @ParameterizedTest
  public void testGetLoadingPlanRulesServiceException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingPlanRules(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            loadingPlanController.getLoadingPlanRules(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + LOAD_PLAN, SHIP_API_URL_PREFIX + LOAD_PLAN})
  @ParameterizedTest
  public void testSaveLoadingPlanRule(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.saveLoadingPlanRules(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenReturn(new RuleResponse());
    RuleRequest loadingPlanRule = new RuleRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(loadingPlanRule))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + LOAD_PLAN, SHIP_API_URL_PREFIX + LOAD_PLAN})
  @ParameterizedTest
  public void testSaveLoadingPlanRuleServiceException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.saveLoadingPlanRules(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenThrow(this.getGenericException());
    RuleRequest loadingPlanRule = new RuleRequest();
    ObjectMapper mapper = new ObjectMapper();
    when(loadingPlanController.saveLoadingPlanRule(any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(loadingPlanRule))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + EDIT_LOADING, SHIP_API_URL_PREFIX + EDIT_LOADING})
  @ParameterizedTest
  public void testEditLoadingInstructions(String url) throws Exception {
    Mockito.when(
            loadingInstructionService.editLoadingInstructions(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenReturn(new LoadingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", "")));
    LoadingInstructionsStatus request = new LoadingInstructionsStatus();
    request.setInstructionId(1L);
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + EDIT_LOADING, SHIP_API_URL_PREFIX + EDIT_LOADING})
  @ParameterizedTest
  public void testEditLoadingInstructionsServiceException(String url) throws Exception {
    Mockito.when(
            loadingInstructionService.editLoadingInstructions(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenThrow(this.getGenericException());
    LoadingInstructionsStatus request = new LoadingInstructionsStatus();
    request.setInstructionId(1L);
    when(loadingPlanController.editLoadingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInstructionService", loadingInstructionService);
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + EDIT_LOADING, SHIP_API_URL_PREFIX + EDIT_LOADING})
  @ParameterizedTest
  public void testEditLoadingInstructionsRuntimeException(String url) throws Exception {
    Mockito.when(
            loadingInstructionService.editLoadingInstructions(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenThrow(RuntimeException.class);
    LoadingInstructionsStatus request = new LoadingInstructionsStatus();
    request.setInstructionId(1L);
    when(loadingPlanController.editLoadingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInstructionService", loadingInstructionService);
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + LOAD_SEQ, SHIP_API_URL_PREFIX + LOAD_SEQ})
  @ParameterizedTest
  public void testGetLoadingSequence(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingSequence(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(new LoadingSequenceResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + LOAD_SEQ, SHIP_API_URL_PREFIX + LOAD_SEQ})
  @ParameterizedTest
  public void testGetLoadingSequenceServiceException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingSequence(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(this.getGenericException());
    when(loadingPlanController.getLoadingSequence(any(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + LOAD_SEQ, SHIP_API_URL_PREFIX + LOAD_SEQ})
  @ParameterizedTest
  public void testGetLoadingSequenceRuntimeException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingSequence(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    when(loadingPlanController.getLoadingSequence(any(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  //  @ValueSource(
  //          strings = {
  //                  CLOUD_API_URL_PREFIX + SAVE_LOAD,
  //                  SHIP_API_URL_PREFIX + SAVE_LOAD
  //          })
  //  @ParameterizedTest
  //  public void testSaveLoadingPlan(String url) throws Exception {
  //
  // Mockito.when(loadingPlanServiceImpl.saveLoadingPlan(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.any(),Mockito.anyString())).thenReturn(new LoadingPlanAlgoResponse());
  //    Object requestJson = new Object();
  //   // String requestJsonString = new ObjectMapper().writeValueAsString(requestJson);
  //    ObjectMapper mapper = new
  // ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
  //    this.mockMvc
  //            .perform(
  //                    MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID,
  // TEST_VOYAGE_ID)
  //                            .content(mapper.writeValueAsString(requestJson))
  //                            .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                            .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                            .accept(MediaType.APPLICATION_JSON_VALUE))
  //            .andExpect(status().isOk());
  //  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_GET_PLAN_API_URL,
        SHIP_API_URL_PREFIX + LOADING_GET_PLAN_API_URL
      })
  @ParameterizedTest
  public void testGetLoadingPlan(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingPlan(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(new LoadingPlanResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_GET_PLAN_API_URL,
        SHIP_API_URL_PREFIX + LOADING_GET_PLAN_API_URL
      })
  @ParameterizedTest
  public void testGetLoadingPlanServiceException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingPlan(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(this.getGenericException());
    when(loadingPlanController.getLoadingPlan(any(), anyLong(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + LOADING_GET_PLAN_API_URL,
        SHIP_API_URL_PREFIX + LOADING_GET_PLAN_API_URL
      })
  @ParameterizedTest
  public void testGetLoadingPlanRuntimeException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadingPlan(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    when(loadingPlanController.getLoadingPlan(any(), anyLong(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + UPDATE_ULAGE, SHIP_API_URL_PREFIX + UPDATE_ULAGE})
  @ParameterizedTest
  public void testGetUpdateUllageDetails(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getUpdateUllageDetails(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyBoolean()))
        .thenReturn(new LoadingUpdateUllageResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID, "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + UPDATE_ULAGE, SHIP_API_URL_PREFIX + UPDATE_ULAGE})
  @ParameterizedTest
  public void testGetUpdateUllageDetailsServiceException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getUpdateUllageDetails(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyBoolean()))
        .thenThrow(this.getGenericException());
    when(loadingPlanController.getUpdateUllageDetails(
            any(), anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID, "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + UPDATE_ULAGE, SHIP_API_URL_PREFIX + UPDATE_ULAGE})
  @ParameterizedTest
  public void testGetUpdateUllageDetailsRuntimeException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getUpdateUllageDetails(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyBoolean()))
        .thenThrow(RuntimeException.class);
    when(loadingPlanController.getUpdateUllageDetails(
            any(), anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID, "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + LOAD_STA, SHIP_API_URL_PREFIX + LOAD_STA})
  @ParameterizedTest
  public void testGetLoadingInfoStatus(String url) throws Exception {
    Mockito.when(
            loadingInformationService.getLoadingInfoAlgoStatus(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyInt()))
        .thenReturn(new LoadingInfoAlgoStatus());
    LoadingInfoAlgoStatusRequest request = new LoadingInfoAlgoStatusRequest();
    request.setProcessId("1");
    request.setConditionType(1);
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + LOAD_STA, SHIP_API_URL_PREFIX + LOAD_STA})
  @ParameterizedTest
  public void testGetLoadingInfoStatusServiceException(String url) throws Exception {
    Mockito.when(
            loadingInformationService.getLoadingInfoAlgoStatus(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyInt()))
        .thenThrow(this.getGenericException());
    LoadingInfoAlgoStatusRequest request = new LoadingInfoAlgoStatusRequest();
    request.setProcessId("1");
    request.setConditionType(1);
    when(loadingPlanController.getLoadingInfoStatus(any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInformationService", loadingInformationService);
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + LOAD_STA, SHIP_API_URL_PREFIX + LOAD_STA})
  @ParameterizedTest
  public void testGetLoadingInfoStatusRuntimeException(String url) throws Exception {
    Mockito.when(
            loadingInformationService.getLoadingInfoAlgoStatus(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyInt()))
        .thenThrow(RuntimeException.class);
    LoadingInfoAlgoStatusRequest request = new LoadingInfoAlgoStatusRequest();
    request.setProcessId("1");
    request.setConditionType(1);
    when(loadingPlanController.getLoadingInfoStatus(any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInformationService", loadingInformationService);
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + ALGO_ERROR, SHIP_API_URL_PREFIX + ALGO_ERROR})
  @ParameterizedTest
  public void testGetAlgoErrors(String url) throws Exception {
    Mockito.when(
            loadingInformationService.getLoadingInfoAlgoErrors(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(new AlgoErrorResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + ALGO_ERROR, SHIP_API_URL_PREFIX + ALGO_ERROR})
  @ParameterizedTest
  public void testGetAlgoErrorsServiceException(String url) throws Exception {
    Mockito.when(
            loadingInformationService.getLoadingInfoAlgoErrors(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
        .thenThrow(this.getGenericException());
    when(loadingPlanController.getAlgoErrors(any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInformationService", loadingInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + ALGO_ERROR, SHIP_API_URL_PREFIX + ALGO_ERROR})
  @ParameterizedTest
  public void testGetAlgoErrorsRuntimeException(String url) throws Exception {
    Mockito.when(
            loadingInformationService.getLoadingInfoAlgoErrors(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
        .thenThrow(RuntimeException.class);
    when(loadingPlanController.getAlgoErrors(any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingInformationService", loadingInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID, 1)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + SAVE_RULES_LS, SHIP_API_URL_PREFIX + SAVE_RULES_LS})
  @ParameterizedTest
  public void testSaveRulesForLoadableStudy(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadableStudyShoreTwo(
                Mockito.anyString(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(new UllageBillReply());
    ObjectMapper mapper = new ObjectMapper();
    UllageBillRequest inputData = new UllageBillRequest();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(inputData))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + SAVE_RULES_LS, SHIP_API_URL_PREFIX + SAVE_RULES_LS})
  @ParameterizedTest
  public void testSaveRulesForLoadableStudyServiceException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadableStudyShoreTwo(
                Mockito.anyString(), Mockito.any(), Mockito.anyBoolean()))
        .thenThrow(this.getGenericException());
    ObjectMapper mapper = new ObjectMapper();
    UllageBillRequest inputData = new UllageBillRequest();
    when(loadingPlanController.saveRulesForLoadableStudy(any(), any())).thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(inputData))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + SAVE_RULES_LS, SHIP_API_URL_PREFIX + SAVE_RULES_LS})
  @ParameterizedTest
  public void testSaveRulesForLoadableStudyRuntimeException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.getLoadableStudyShoreTwo(
                Mockito.anyString(), Mockito.any(), Mockito.anyBoolean()))
        .thenThrow(RuntimeException.class);
    ObjectMapper mapper = new ObjectMapper();
    UllageBillRequest inputData = new UllageBillRequest();
    when(loadingPlanController.saveRulesForLoadableStudy(any(), any())).thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(inputData))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + UPLOAD_TIDE, SHIP_API_URL_PREFIX + UPLOAD_TIDE})
  @ParameterizedTest
  public void testUploadTideDetails(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.uploadLoadingTideDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenReturn(new UploadTideDetailResponse());
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, 1L, 1L)
                .file("file", firstFile.getBytes())
                .param("portName", "1")
                .param("portId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + UPLOAD_TIDE, SHIP_API_URL_PREFIX + UPLOAD_TIDE})
  @ParameterizedTest
  public void testUploadTideDetailsServiceException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.uploadLoadingTideDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenThrow(this.getGenericException());
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    Mockito.when(
            loadingPlanController.uploadTideDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", this.loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, 1L, 1L)
                .file("file", firstFile.getBytes())
                .param("portName", "1")
                .param("portId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + UPLOAD_TIDE, SHIP_API_URL_PREFIX + UPLOAD_TIDE})
  @ParameterizedTest
  public void testUploadTideDetailsRuntimeException(String url) throws Exception {
    Mockito.when(
            loadingPlanServiceImpl.uploadLoadingTideDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    Mockito.when(
            loadingPlanController.uploadTideDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", this.loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, 1L, 1L)
                .file("file", firstFile.getBytes())
                .param("portName", "1")
                .param("portId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + SIM_JSON, SHIP_API_URL_PREFIX + SIM_JSON})
  @ParameterizedTest
  public void testGetSimulatorJsonDataForLoading(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.getSimulatorJsonDataForLoading(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new LoadingSimulatorJsonResponse());

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + SIM_JSON, SHIP_API_URL_PREFIX + SIM_JSON})
  @ParameterizedTest
  public void testGetSimulatorJsonDataForLoadingServiceException(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.getSimulatorJsonDataForLoading(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(this.getGenericException());
    when(loadingPlanController.getSimulatorJsonDataForLoading(anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CLOUD_API_URL_PREFIX + SIM_JSON, SHIP_API_URL_PREFIX + SIM_JSON})
  @ParameterizedTest
  public void testGetSimulatorJsonDataForLoadingRuntimeException(String url) throws Exception {
    Mockito.when(
            this.loadingPlanServiceImpl.getSimulatorJsonDataForLoading(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    when(loadingPlanController.getSimulatorJsonDataForLoading(anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadingPlanController, "loadingPlanService", loadingPlanServiceImpl);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_LOADING_INFO_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }
}
