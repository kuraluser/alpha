/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.CargoNominationResponse;
import com.cpdss.gateway.domain.CommingleCargoResponse;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.domain.VoyageStatusResponse;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.service.AlgoErrorService;
import com.cpdss.gateway.service.LoadableStudyCargoService;
import com.cpdss.gateway.service.SyncRedisMasterService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import com.cpdss.gateway.service.loadingplan.impl.LoadingInstructionService;
import com.cpdss.gateway.service.redis.RedisMasterSyncService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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

  @MockBean private LoadingInstructionService loadingInstructionService;

  @MockBean private CargoNominationResponse cargoNominationResponse;

  @MockBean private PortRotationResponse portRotationResponse;

  @MockBean private CommingleCargoResponse commingleCargoResponse;

  @MockBean private VoyageStatusResponse voyageStatusResponse;

  @MockBean private LoadableStudyCargoService loadableStudyCargoService;

  @MockBean private LoadingPlanService loadingPlanService;

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
    when(this.loadingPlanService.getLoadingPlan(anyLong(), anyLong(), anyLong(), anyLong()))
        .thenReturn(new LoadingPlanResponse(new CommonSuccessResponse("SUCCESS", "")));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                url, TEST_VESSEL_ID, TEST_VOYAGE_ID, TEST_LOADING_INFO_ID, TEST_PORT_ROTATION_ID))
        .andExpect(status().isOk());
  }
}
