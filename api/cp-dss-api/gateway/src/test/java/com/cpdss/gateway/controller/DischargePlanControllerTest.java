/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyCargoResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyRequest;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyUpdateResponse;
import com.cpdss.gateway.domain.dischargeplan.*;
import com.cpdss.gateway.domain.loadingplan.LoadingInfoAlgoResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import com.cpdss.gateway.service.DischargeStudyService;
import com.cpdss.gateway.service.dischargeplan.DischargeInformationGrpcService;
import com.cpdss.gateway.service.dischargeplan.DischargeInformationService;
import com.cpdss.gateway.service.dischargeplan.DischargingInstructionService;
import com.cpdss.gateway.service.dischargeplan.GenerateDischargingPlanExcelReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@MockitoSettings
@WebMvcTest(controllers = DischargePlanController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(
    classes = {
      GatewayTestConfiguration.class,
      DischargeInformationService.class,
      DischargingInstructionService.class
    })
@TestPropertySource(properties = {"cpdss.build.env=none"})
public class DischargePlanControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean DischargeInformationService dischargeInformationService;
  @MockBean private DischargeInformationGrpcService dischargeInformationGrpcService;
  @MockBean DischargePlanController dischargePlanController;
  @MockBean DischargingInstructionService dischargingInstructionService;
  @MockBean private DischargeStudyService dischargeStudyService;
  @MockBean GenerateDischargingPlanExcelReportService dischargingPlanExcelReportService;

  // Common for discharge plan controller
  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";
  private static final Long TEST_VESSEL_ID = 1L;
  private static final Long TEST_DISCHARGE_INFO_ID = 1L;
  private static final Long TEST_PORT_ROTATION_ID = 1L;
  private static final Long TEST_VOYAGE_ID = 1L;
  private static final String SUCCESS = "SUCCESS";

  // API URLS
  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/ship";

  private static final String DIS_INFO_GET_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_RULE_GET_URL =
      "/vessels/{vesselId}/discharge-info-rule/{dischargingInfoId}";
  private static final String DIS_ADD_INSTRUCTION =
      "/new-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_UPDATE_INSTRUCTION =
      "/update-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_EDIT_INSTRUCTION =
      "/edit-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_GET_INSTRUCTION =
      "/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_DELETE_INSTRUCTION =
      "/delete-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DELETE_DIS_STUDY = "/discharge-studies/{dischargeStudyId}";
  private static final String DIS_BACKLOADING = "/discharge-studies";
  private static final String DIS_SAVE_STUDY =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-study";
  private static final String DIS_STUDY_VOYAGE =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/cargo-nomination";
  private static final String GET_CARGOS_PORTS =
      "/discharge-studies/{dischargeStudyId}/port-cargos";
  private static final String DIS_STUDY_PORT_VOYAGE =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/ports";
  private static final String SAVE_PORT =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/ports/{id}";
  private static final String DIS_RULE_GET_URL_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DIS_RULE_GET_URL;
  private static final String DIS_RULE_GET_URL_SHIP_API_URL =
      SHIP_API_URL_PREFIX + DIS_RULE_GET_URL;
  private static final String DIS_BACKLOADING_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DIS_BACKLOADING;
  private static final String DIS_BACKLOADING_SHIP_API_URL = SHIP_API_URL_PREFIX + DIS_BACKLOADING;
  private static final String GET_HAND =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/port-rotation/{portRotationId}/on-hand-quantities";
  private static final String SAVE_HAND_DIS =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}";
  private static final String SAVE_HAND_QUN =
      "/port-rotation/{portRotationId}/on-hand-quantities/{id}";
  private static final String DIS_STUDY_CARGO_VOYAGE =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/cargoByPort";
  private static final String DIS_PATTERN =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/discharge-pattern-details";
  private static final String CON_PLAN =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-studies/{dischargeStudyId}/confirm-plan/{dischargePatternId}";
  private static final String UPLOAD_TIDE = "/discharging/{dischargingId}/upload/port-tide-details";
  private static final String DOWNLOAD_DIS_TIDE = "/discharging/download/port-tide-template";
  private static final String DIS_PLAN =
      "/vessels/{vesselId}/voyages/{voyageId}/discharging-info/{infoId}/discharging-plan/{portRotationId}";
  private static final String UPDATE_ULLAGE =
      "/vessels/{vesselId}/pattern/{patternId}/port/{portRotationId}/discharging/ullage/{operationType}";
  private static final String UP_ULL = "/discharge/ullage-update";
  private static final String SAVE_DIS_INFO =
      "/vessels/{vesselId}/voyages/{voyageId}/discharging-info";
  private static final String DIS_PLA =
      "/vessels/{vesselId}/voyages/{voyageId}/discharging-info/{infoId}/discharging-info-status";
  private static final String GEN_DIS =
      "/vessels/{vesselId}/voyages/{voyageId}/discharging-info/{infoId}/generate-discharging-plan";
  private static final String SAVE_DIS_PLAN =
      "/vessels/{vesselId}/voyages/{voyageId}/discharging-info/{infoId}/discharging-plan";
  private static final String GET_DIS_INFO =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-info/{infoId}/algo-status";
  private static final String GET_DIS_SEQ =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-info/{infoId}/discharging-sequence";
  private static final String GET_ALGO =
      "/vessels/{vesselId}/voyages/{voyageId}/discharge-info/{infoId}/algo-errors/{conditionType}";
  private static final String DIS_PLAN_RULE =
      "/vessels/{vesselId}/voyages/{voyageId}/discharging-info/{infoId}/rules";
  private static final String DIS_REPORT =
      "/vessels/{vesselId}/voyages/{voyageId}/discharging-info/{infoId}/port-rotation/{portRotationId}/report";
  private static final String DIS_REPORT_CLOUD_API_URL = CLOUD_API_URL_PREFIX + DIS_REPORT;
  private static final String DIS_REPORT_SHIP_API_URL = SHIP_API_URL_PREFIX + DIS_REPORT;
  private static final String DIS_PLAN_RULE_CLOUD_API_URL = CLOUD_API_URL_PREFIX + DIS_PLAN_RULE;
  private static final String DIS_PLAN_RULE_SHIP_API_URL = SHIP_API_URL_PREFIX + DIS_PLAN_RULE;
  private static final String GET_ALGO_CLOUD_API_URL = CLOUD_API_URL_PREFIX + GET_ALGO;
  private static final String GET_ALGO_SHIP_API_URL = SHIP_API_URL_PREFIX + GET_ALGO;
  private static final String GET_DIS_SEQ_CLOUD_API_URL = CLOUD_API_URL_PREFIX + GET_DIS_SEQ;
  private static final String GET_DIS_SEQ_SHIP_API_URL = SHIP_API_URL_PREFIX + GET_DIS_SEQ;
  private static final String GET_DIS_INFO_CLOUD_API_URL = CLOUD_API_URL_PREFIX + GET_DIS_INFO;
  private static final String GET_DIS_INFO_SHIP_API_URL = SHIP_API_URL_PREFIX + GET_DIS_INFO;
  private static final String SAVE_DIS_PLAN_CLOUD_API_URL = CLOUD_API_URL_PREFIX + SAVE_DIS_PLAN;
  private static final String SAVE_DIS_PLAN_SHIP_API_URL = SHIP_API_URL_PREFIX + SAVE_DIS_PLAN;
  private static final String GEN_DIS_CLOUD_API_URL = CLOUD_API_URL_PREFIX + GEN_DIS;
  private static final String GEN_DIS_SHIP_API_URL = SHIP_API_URL_PREFIX + GEN_DIS;
  private static final String DIS_PLA_CLOUD_API_URL = CLOUD_API_URL_PREFIX + DIS_PLA;
  private static final String DIS_PLA_SHIP_API_URL = SHIP_API_URL_PREFIX + DIS_PLA;
  private static final String SAVE_DIS_INFO_CLOUD_API_URL = CLOUD_API_URL_PREFIX + SAVE_DIS_INFO;
  private static final String SAVE_DIS_INFO_SHIP_API_URL = SHIP_API_URL_PREFIX + SAVE_DIS_INFO;
  private static final String UP_ULL_CLOUD_API_URL = CLOUD_API_URL_PREFIX + UP_ULL;
  private static final String UP_ULL_SHIP_API_URL = SHIP_API_URL_PREFIX + UP_ULL;
  private static final String UPDATE_ULLAGE_CLOUD_API_URL = CLOUD_API_URL_PREFIX + UPDATE_ULLAGE;
  private static final String UPDATE_ULLAGE_SHIP_API_URL = SHIP_API_URL_PREFIX + UPDATE_ULLAGE;
  private static final String DIS_PLAN_CLOUD_API_URL = CLOUD_API_URL_PREFIX + DIS_PLAN;
  private static final String DIS_PLAN_SHIP_API_URL = SHIP_API_URL_PREFIX + DIS_PLAN;
  private static final String DOWNLOAD_DIS_TIDE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DOWNLOAD_DIS_TIDE;
  private static final String DOWNLOAD_DIS_TIDE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + DOWNLOAD_DIS_TIDE;
  private static final String UPLOAD_TIDE_CLOUD_API_URL = CLOUD_API_URL_PREFIX + UPLOAD_TIDE;
  private static final String UPLOAD_TIDE_SHIP_API_URL = SHIP_API_URL_PREFIX + UPLOAD_TIDE;
  private static final String CON_PLAN_CLOUD_API_URL = CLOUD_API_URL_PREFIX + CON_PLAN;
  private static final String CON_PLAN_SHIP_API_URL = SHIP_API_URL_PREFIX + CON_PLAN;
  private static final String DIS_PATTERN_CLOUD_API_URL = CLOUD_API_URL_PREFIX + DIS_PATTERN;
  private static final String DIS_PATTERN_SHIP_API_URL = SHIP_API_URL_PREFIX + DIS_PATTERN;
  private static final String DIS_STUDY_CARGO_VOYAGE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DIS_STUDY_CARGO_VOYAGE;
  private static final String DIS_STUDY_CARGO_VOYAGE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + DIS_STUDY_CARGO_VOYAGE;
  private static final String SAVE_HAND_DIS_QUN_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + SAVE_HAND_DIS + SAVE_HAND_QUN;
  private static final String SAVE_HAND_DIS_QUN_SHIP_API_URL =
      SHIP_API_URL_PREFIX + SAVE_HAND_DIS + SAVE_HAND_QUN;
  private static final String DELETE_DIS_STUDY_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DELETE_DIS_STUDY;
  private static final String DELETE_DIS_STUDY_SHIP_API_URL =
      SHIP_API_URL_PREFIX + DELETE_DIS_STUDY;
  private static final String DIS_SAVE_STUDY_CLOUD_API_URL = CLOUD_API_URL_PREFIX + DIS_SAVE_STUDY;
  private static final String DIS_SAVE_STUDY_SHIP_API_URL = SHIP_API_URL_PREFIX + DIS_SAVE_STUDY;
  private static final String DIS_STUDY_VOYAGE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DIS_STUDY_VOYAGE;
  private static final String DIS_STUDY_VOYAGE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + DIS_STUDY_VOYAGE;
  private static final String GET_CARGOS_PORTS_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_CARGOS_PORTS;
  private static final String GET_CARGOS_PORTS_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_CARGOS_PORTS;
  private static final String DIS_STUDY_PORT_VOYAGE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + DIS_STUDY_PORT_VOYAGE;
  private static final String DIS_STUDY_PORT_VOYAGE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + DIS_STUDY_PORT_VOYAGE;
  private static final String SAVE_PORT_CLOUD_API_URL = CLOUD_API_URL_PREFIX + SAVE_PORT;
  private static final String SAVE_PORT_SHIP_API_URL = SHIP_API_URL_PREFIX + SAVE_PORT;
  private static final String GET_HAND_CLOUD_API_URL = CLOUD_API_URL_PREFIX + GET_HAND;
  private static final String GET_HAND_SHIP_API_URL = SHIP_API_URL_PREFIX + GET_HAND;

  @Test
  public void contextLoads() throws Exception {
    assertThat(dischargeInformationService).isNotNull();
    assertThat(dischargingInstructionService).isNotNull();
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + DIS_INFO_GET_URL, SHIP_API_URL_PREFIX + DIS_INFO_GET_URL})
  @ParameterizedTest
  public void getDischargeInformationByPortRIdTestCase1(String url) throws Exception {
    when(this.dischargeInformationService.getDischargeInformation(anyLong(), anyLong(), anyLong()))
        .thenReturn(new DischargeInformation());
    when(dischargePlanController.getDischargeInformationByPortRId(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
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

  /**
   * Test method for ADD discharging instruction
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_ADD_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_ADD_INSTRUCTION
      })
  @ParameterizedTest
  void testAddDischargingInstruction(String url) throws Exception {
    when(this.dischargingInstructionService.addDischargingInstruction(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsSaveRequest.class)))
        .thenReturn(
            new DischargingInstructionsSaveResponse(new CommonSuccessResponse("SUCCESS", "")));
    DischargingInstructionsSaveRequest request = new DischargingInstructionsSaveRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Test method for ADD discharging instruction- negative scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_ADD_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_ADD_INSTRUCTION
      })
  @ParameterizedTest
  void testAddDischargingInstructionException(String url) throws Exception {
    when(this.dischargingInstructionService.addDischargingInstruction(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsSaveRequest.class)))
        .thenThrow(this.getGenericException());
    DischargingInstructionsSaveRequest request = new DischargingInstructionsSaveRequest();
    ObjectMapper mapper = new ObjectMapper();
    when(dischargePlanController.addDischargingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  /**
   * Positive test case. Test method for positive response scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_GET_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_GET_INSTRUCTION
      })
  @ParameterizedTest
  public void getDischargeInstructionsTestCase1(String url) throws Exception {
    when(this.dischargingInstructionService.getDischargingInstructions(
            anyLong(), anyLong(), anyLong()))
        .thenReturn(new DischargingInstructionResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_PORT_ROTATION_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
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
        CLOUD_API_URL_PREFIX + DIS_GET_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_GET_INSTRUCTION
      })
  @ParameterizedTest
  void testGetAllDischargingInstructionsException(String url) throws Exception {
    when(this.dischargingInstructionService.getDischargingInstructions(
            anyLong(), anyLong(), anyLong()))
        .thenThrow(this.getGenericException());

    when(dischargePlanController.getAllDischargingInstructions(
            any(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
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
        CLOUD_API_URL_PREFIX + DIS_GET_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_GET_INSTRUCTION
      })
  @ParameterizedTest
  void testGetAllDischargingInstructionsRuntimeException(String url) throws Exception {
    when(this.dischargingInstructionService.getDischargingInstructions(
            anyLong(), anyLong(), anyLong()))
        .thenThrow(RuntimeException.class);
    when(dischargePlanController.getAllDischargingInstructions(
            any(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_UPDATE_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_UPDATE_INSTRUCTION
      })
  @ParameterizedTest
  public void updateDischargeInstructionsTestCase1(String url) throws Exception {

    DischargingInstructionsUpdateRequest request = new DischargingInstructionsUpdateRequest();
    DischargingInstructionsStatus insideRequest = new DischargingInstructionsStatus();
    insideRequest.setInstruction("test");
    insideRequest.setInstructionId(1L);
    List<DischargingInstructionsStatus> list = new ArrayList<>();
    list.add(insideRequest);
    insideRequest.setIsChecked(false);
    request.setInstructionList(list);
    String inputJson = this.mapToJson(request);
    when(this.dischargingInstructionService.updateDischargingInstructions(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsUpdateRequest.class)))
        .thenReturn(
            new DischargingInstructionsSaveResponse(new CommonSuccessResponse(SUCCESS, "")));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_PORT_ROTATION_ID)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Test method for UPDATE loading instruction - negative scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_UPDATE_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_UPDATE_INSTRUCTION
      })
  @ParameterizedTest
  void testUpdateDischargingInstructionException(String url) throws Exception {
    when(this.dischargingInstructionService.updateDischargingInstructions(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsUpdateRequest.class)))
        .thenThrow(this.getGenericException());

    when(dischargePlanController.updateDischargingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    DischargingInstructionsUpdateRequest request = new DischargingInstructionsUpdateRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_EDIT_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_EDIT_INSTRUCTION
      })
  @ParameterizedTest
  public void editDischargeInstructionsTestCase1(String url) throws Exception {

    DischargingInstructionsStatus insideRequest = new DischargingInstructionsStatus();
    insideRequest.setInstruction("test");
    insideRequest.setInstructionId(1L);
    String inputJson = this.mapToJson(insideRequest);
    when(this.dischargingInstructionService.editDischargingInstructions(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
        .thenReturn(
            new DischargingInstructionsSaveResponse(new CommonSuccessResponse(SUCCESS, "")));
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_PORT_ROTATION_ID)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_DELETE_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_DELETE_INSTRUCTION
      })
  @ParameterizedTest
  public void deleteDischargeInstructionsTestCase1(String url) throws Exception {

    DischargingInstructionsStatus insideRequest = new DischargingInstructionsStatus();
    insideRequest.setInstruction("test");
    insideRequest.setInstructionId(1L);
    String inputJson = this.mapToJson(insideRequest);
    when(this.dischargingInstructionService.deleteDischargingInstructions(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
        .thenReturn(
            new DischargingInstructionsSaveResponse(new CommonSuccessResponse(SUCCESS, "")));
    when(dischargePlanController.deleteDischargingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_PORT_ROTATION_ID)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  /**
   * Test method for save all loading instruction status changes - negative scenario
   *
   * @throws Exception
   */
  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_DELETE_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_DELETE_INSTRUCTION
      })
  @ParameterizedTest
  void testDeleteDischargingInstructionRuntimeException(String url) throws Exception {
    when(this.dischargingInstructionService.deleteDischargingInstructions(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
        .thenThrow(RuntimeException.class);
    DischargingInstructionsStatus request = new DischargingInstructionsStatus();
    ObjectMapper mapper = new ObjectMapper();
    when(dischargePlanController.deleteDischargingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
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

  private String mapToJson(Object object) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(object);
  }

  @ValueSource(strings = {DELETE_DIS_STUDY_CLOUD_API_URL, DELETE_DIS_STUDY_SHIP_API_URL})
  @ParameterizedTest
  public void testDeleteDischargeStudy(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.deleteDischargeStudy(Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new DischargeStudyResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DELETE_DIS_STUDY_CLOUD_API_URL, DELETE_DIS_STUDY_SHIP_API_URL})
  @ParameterizedTest
  public void testDeleteDischargeStudyRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.deleteDischargeStudy(Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(dischargePlanController.deleteDischargeStudy(Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_BACKLOADING_CLOUD_API_URL, DIS_BACKLOADING_SHIP_API_URL})
  @ParameterizedTest
  public void testSaveDischargeStudyWithBackloading(String url) throws Exception {
    DischargeStudyCargoResponse request = new DischargeStudyCargoResponse();
    request.setDischargeStudyId(1L);
    Mockito.when(
            this.dischargeStudyService.saveDischargeStudyWithBackloaing(
                Mockito.any(), Mockito.anyString()))
        .thenReturn(new LoadableStudyResponse());
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_BACKLOADING_CLOUD_API_URL, DIS_BACKLOADING_SHIP_API_URL})
  @ParameterizedTest
  public void testSaveDischargeStudyWithBackloadingServiceException(String url) throws Exception {
    DischargeStudyCargoResponse request = new DischargeStudyCargoResponse();
    request.setDischargeStudyId(0L);
    Mockito.when(
            this.dischargeStudyService.saveDischargeStudyWithBackloaing(
                Mockito.any(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                //    .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {DIS_BACKLOADING_CLOUD_API_URL, DIS_BACKLOADING_SHIP_API_URL})
  @ParameterizedTest
  public void testSaveDischargeStudyWithBackloadingRuntimeException(String url) throws Exception {
    DischargeStudyCargoResponse request = new DischargeStudyCargoResponse();
    request.setDischargeStudyId(1L);
    Mockito.when(
            this.dischargeStudyService.saveDischargeStudyWithBackloaing(
                Mockito.any(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.saveDischargeStudyWithBackloading(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_SAVE_STUDY_CLOUD_API_URL, DIS_SAVE_STUDY_SHIP_API_URL})
  @ParameterizedTest
  public void testSaveDischargeStudy(String url) throws Exception {
    DischargeStudyRequest request = new DischargeStudyRequest();
    request.setName("1");
    Mockito.when(this.dischargeStudyService.saveDischargeStudy(Mockito.any(), Mockito.anyString()))
        .thenReturn(new LoadableStudyResponse());
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, 1L, 1L)
                .file("file", firstFile.getBytes())
                .param("vesselId", "1")
                .param("voyageId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_SAVE_STUDY_CLOUD_API_URL, DIS_SAVE_STUDY_SHIP_API_URL})
  @ParameterizedTest
  public void testSaveDischargeStudyServiceException(String url) throws Exception {
    DischargeStudyRequest request = new DischargeStudyRequest();
    Mockito.when(this.dischargeStudyService.saveDischargeStudy(Mockito.any(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.saveDischargeStudy(
                Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, 1L, 1L)
                .file("file", firstFile.getBytes())
                .param("vesselId", "1")
                .param("voyageId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {DIS_SAVE_STUDY_CLOUD_API_URL, DIS_SAVE_STUDY_SHIP_API_URL})
  @ParameterizedTest
  public void testSaveDischargeStudyRuntimeException(String url) throws Exception {
    DischargeStudyRequest request = new DischargeStudyRequest();
    Mockito.when(this.dischargeStudyService.saveDischargeStudy(Mockito.any(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.saveDischargeStudy(
                Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, 1L, 1L)
                .file("file", firstFile.getBytes())
                .param("vesselId", "1")
                .param("voyageId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {DELETE_DIS_STUDY_CLOUD_API_URL, DELETE_DIS_STUDY_SHIP_API_URL})
  @ParameterizedTest
  public void testUpdateDischargeStudies(String url) throws Exception {
    DischargeStudyRequest request = new DischargeStudyRequest();
    request.setName("1");
    Mockito.when(
            this.dischargeStudyService.updateDischargeStudy(
                Mockito.any(), Mockito.anyString(), Mockito.anyLong()))
        .thenReturn(new DischargeStudyUpdateResponse());
    ObjectMapper mapper = new ObjectMapper();
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(url, 1L)
                .content(mapper.writeValueAsString(request))
                .param("dischargeStudyId", "1")
                .param("request", String.valueOf(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DELETE_DIS_STUDY_CLOUD_API_URL, DELETE_DIS_STUDY_SHIP_API_URL})
  @ParameterizedTest
  public void testUpdateDischargeStudiesServiceException(String url) throws Exception {
    DischargeStudyRequest request = new DischargeStudyRequest();
    Mockito.when(
            this.dischargeStudyService.updateDischargeStudy(
                Mockito.any(), Mockito.anyString(), Mockito.anyLong()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.updateDischargeStudies(
                Mockito.anyLong(), Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", dischargeStudyService);
    ObjectMapper mapper = new ObjectMapper();
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(url, 1L)
                .content(mapper.writeValueAsString(request))
                .param("dischargeStudyId", "1")
                .param("request", String.valueOf(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {DELETE_DIS_STUDY_CLOUD_API_URL, DELETE_DIS_STUDY_SHIP_API_URL})
  @ParameterizedTest
  public void testUpdateDischargeStudiesRuntimeException(String url) throws Exception {
    DischargeStudyRequest request = new DischargeStudyRequest();
    request.setName("1");
    Mockito.when(
            this.dischargeStudyService.updateDischargeStudy(
                Mockito.any(), Mockito.anyString(), Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.updateDischargeStudies(
                Mockito.anyLong(), Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", dischargeStudyService);
    ObjectMapper mapper = new ObjectMapper();
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(url, 1L)
                .content(mapper.writeValueAsString(request))
                .param("dischargeStudyId", "1")
                .param("request", String.valueOf(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {DIS_STUDY_VOYAGE_CLOUD_API_URL, DIS_STUDY_VOYAGE_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargeStudyByVoyage(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargeStudyByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new DischargeStudyResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_PORT_ROTATION_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_STUDY_VOYAGE_CLOUD_API_URL, DIS_STUDY_VOYAGE_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargeStudyByVoyageServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargeStudyByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.getDischargeStudyByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_PORT_ROTATION_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_STUDY_VOYAGE_CLOUD_API_URL, DIS_STUDY_VOYAGE_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargeStudyByVoyageRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargeStudyByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.getDischargeStudyByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_PORT_ROTATION_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GET_CARGOS_PORTS_CLOUD_API_URL, GET_CARGOS_PORTS_SHIP_API_URL})
  @ParameterizedTest
  public void testGetCargosByPorts(String url) throws Exception {
    Mockito.when(dischargeStudyService.getCargosByPorts(Mockito.anyLong(), Mockito.any()))
        .thenReturn(new PortWiseCargoResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {GET_CARGOS_PORTS_CLOUD_API_URL, GET_CARGOS_PORTS_SHIP_API_URL})
  @ParameterizedTest
  public void testGetCargosByPortsRuntimeException(String url) throws Exception {
    Mockito.when(dischargeStudyService.getCargosByPorts(Mockito.anyLong(), Mockito.any()))
        .thenThrow(RuntimeException.class);
    Mockito.when(dischargePlanController.getCargosByPorts(Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_STUDY_PORT_VOYAGE_CLOUD_API_URL, DIS_STUDY_PORT_VOYAGE_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargeStudyPortByVoyage(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargeStudyPortDataByVoyage(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyString(),
                Mockito.any()))
        .thenReturn(new PortRotationResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_STUDY_PORT_VOYAGE_CLOUD_API_URL, DIS_STUDY_PORT_VOYAGE_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargeStudyPortByVoyageServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargeStudyPortDataByVoyage(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyString(),
                Mockito.any()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.getDischargeStudyPortByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_STUDY_PORT_VOYAGE_CLOUD_API_URL, DIS_STUDY_PORT_VOYAGE_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargeStudyPortByVoyageRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargeStudyPortDataByVoyage(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyString(),
                Mockito.any()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.getDischargeStudyPortByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  //  @ValueSource(strings = {SAVE_PORT_CLOUD_API_URL,SAVE_PORT_SHIP_API_URL })
  //  @ParameterizedTest
  //  public void testSavePortRotation(String url) throws Exception {
  //    PortRotation request = new PortRotation();
  //    request.setId(1L);
  //
  // Mockito.when(this.dischargeStudyService.savePortRotation(Mockito.any(),Mockito.anyString(),Mockito.any())).thenReturn(new PortRotationResponse());
  //   //
  // Mockito.when(dischargePlanController.savePortRotation(Mockito.any(),Mockito.anyLong(),Mockito.anyLong(),Mockito.any())).thenCallRealMethod();
  //
  // ReflectionTestUtils.setField(dischargePlanController,"dischargeStudyService",this.dischargeStudyService);
  //    this.mockMvc
  //            .perform(
  //                    MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID,
  // TEST_VOYAGE_ID,1L)
  //                            .content(String.valueOf(request))
  //                            .param("dischargeStudyId","1")
  //                            .param("id","1")
  //                            .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                            .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                            .accept(MediaType.APPLICATION_JSON_VALUE))
  //            .andExpect(status().isOk());
  //  }

  @ValueSource(strings = {SAVE_PORT_CLOUD_API_URL, SAVE_PORT_SHIP_API_URL})
  @ParameterizedTest
  public void testSavePortRotationServiceException(String url) throws Exception {
    PortRotation request = new PortRotation();
    request.setId(1L);
    Mockito.when(
            this.dischargeStudyService.savePortRotation(
                Mockito.any(), Mockito.anyString(), Mockito.any()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.savePortRotation(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, 1L)
                .content(String.valueOf(request))
                .param("dischargeStudyId", "1")
                .param("id", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {SAVE_PORT_CLOUD_API_URL, SAVE_PORT_SHIP_API_URL})
  @ParameterizedTest
  public void testSavePortRotationRuntimeException(String url) throws Exception {
    PortRotation request = new PortRotation();
    request.setId(1L);
    Mockito.when(
            this.dischargeStudyService.savePortRotation(
                Mockito.any(), Mockito.anyString(), Mockito.any()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.savePortRotation(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, 1L)
                .content(String.valueOf(request))
                .param("dischargeStudyId", "1")
                .param("id", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {SAVE_PORT_CLOUD_API_URL, SAVE_PORT_SHIP_API_URL})
  @ParameterizedTest
  public void testDeletePortRotation(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.deletePortRotation(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new PortRotationResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {SAVE_PORT_CLOUD_API_URL, SAVE_PORT_SHIP_API_URL})
  @ParameterizedTest
  public void testDeletePortRotationRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.deletePortRotation(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.deletePortRotation(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {SAVE_PORT_CLOUD_API_URL, SAVE_PORT_SHIP_API_URL})
  @ParameterizedTest
  public void testDeletePortRotationServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.deletePortRotation(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.deletePortRotation(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GET_HAND_CLOUD_API_URL, GET_HAND_SHIP_API_URL})
  @ParameterizedTest
  public void testGetOnHandQuantity(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getOnHandQuantity(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new OnHandQuantityResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {GET_HAND_CLOUD_API_URL, GET_HAND_SHIP_API_URL})
  @ParameterizedTest
  public void testGetOnHandQuantityServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getOnHandQuantity(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.getOnHandQuantity(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GET_HAND_CLOUD_API_URL, GET_HAND_SHIP_API_URL})
  @ParameterizedTest
  public void testGetOnHandQuantityRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getOnHandQuantity(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.getOnHandQuantity(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  //    @ValueSource(strings = {SAVE_HAND_DIS_QUN_CLOUD_API_URL,SAVE_HAND_DIS_QUN_SHIP_API_URL })
  //    @ParameterizedTest
  //    public void testSaveOnHandQuantity(String url) throws Exception {
  //        OnHandQuantity request = new OnHandQuantity();
  //        ObjectMapper mapper = new ObjectMapper();
  //
  // Mockito.when(this.dischargeStudyService.saveOnHandQuantity(Mockito.any(),Mockito.anyString())).thenReturn(new OnHandQuantityResponse());
  //   //
  // Mockito.when(dischargePlanController.saveOnHandQuantity(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.any(),Mockito.any())).thenCallRealMethod();
  //
  // ReflectionTestUtils.setField(dischargePlanController,"dischargeStudyService",this.dischargeStudyService);
  //        this.mockMvc
  //                .perform(
  //                        MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID,
  // TEST_VOYAGE_ID,1L,1L)
  //                                .content(mapper.writeValueAsString(request))
  //                                .param("dischargeStudyId","1")
  //                                .param("portRotationId","1")
  //                                .param("id","1")
  //                           //     .param("OnHandQuantity","[{\"Id\":1}]")
  //                                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                                .accept(MediaType.APPLICATION_JSON_VALUE))
  //                .andExpect(status().isOk());
  //    }

  @ValueSource(strings = {SAVE_HAND_DIS_QUN_CLOUD_API_URL, SAVE_HAND_DIS_QUN_SHIP_API_URL})
  @ParameterizedTest
  public void testSaveOnHandQuantityRuntimeException(String url) throws Exception {
    OnHandQuantity request = new OnHandQuantity();
    Mockito.when(this.dischargeStudyService.saveOnHandQuantity(Mockito.any(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    //
    // Mockito.when(dischargePlanController.saveOnHandQuantity(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.any(),Mockito.any())).thenCallRealMethod();
    //
    // ReflectionTestUtils.setField(dischargePlanController,"dischargeStudyService",this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, 1L, 1L)
                .content(String.valueOf(request))
                .param("dischargeStudyId", "1")
                .param("portRotationId", "1")
                .param("id", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {SAVE_HAND_DIS_QUN_CLOUD_API_URL, SAVE_HAND_DIS_QUN_SHIP_API_URL})
  @ParameterizedTest
  public void testSaveOnHandQuantityServiceException(String url) throws Exception {
    OnHandQuantity request = new OnHandQuantity();
    Mockito.when(this.dischargeStudyService.saveOnHandQuantity(Mockito.any(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.saveOnHandQuantity(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, 1L, 1L)
                .content(String.valueOf(request))
                .param("dischargeStudyId", "1")
                .param("portRotationId", "1")
                .param("id", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(
      strings = {DIS_STUDY_CARGO_VOYAGE_CLOUD_API_URL, DIS_STUDY_CARGO_VOYAGE_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargeStudyCargoByVoyage(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargeStudyCargoByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new DischargeStudyCargoResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {DIS_STUDY_CARGO_VOYAGE_CLOUD_API_URL, DIS_STUDY_CARGO_VOYAGE_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargeStudyCargoByVoyageServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargeStudyCargoByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.getDischargeStudyCargoByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {DIS_STUDY_CARGO_VOYAGE_CLOUD_API_URL, DIS_STUDY_CARGO_VOYAGE_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargeStudyCargoByVoyageRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargeStudyCargoByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.getDischargeStudyCargoByVoyage(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_PATTERN_CLOUD_API_URL, DIS_PATTERN_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargePatternDetails(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargePatternDetails(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new DischargeStudyCargoResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_PATTERN_CLOUD_API_URL, DIS_PATTERN_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargePatternDetailsServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargePatternDetails(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.getDischargePatternDetails(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_PATTERN_CLOUD_API_URL, DIS_PATTERN_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargePatternDetailsRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.getDischargePatternDetails(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.getDischargePatternDetails(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", this.dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CON_PLAN_CLOUD_API_URL, CON_PLAN_SHIP_API_URL})
  @ParameterizedTest
  public void testConfirmPlan(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.confirmPlan(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(new CommonResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {CON_PLAN_CLOUD_API_URL, CON_PLAN_SHIP_API_URL})
  @ParameterizedTest
  public void testConfirmPlanServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.confirmPlan(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(dischargePlanController.confirmPlan(Mockito.anyLong(), anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {CON_PLAN_CLOUD_API_URL, CON_PLAN_SHIP_API_URL})
  @ParameterizedTest
  public void testConfirmPlanRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeStudyService.confirmPlan(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(dischargePlanController.confirmPlan(Mockito.anyLong(), anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeStudyService", dischargeStudyService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_RULE_GET_URL_CLOUD_API_URL, DIS_RULE_GET_URL_CLOUD_API_URL})
  @ParameterizedTest
  public void testSaveRulesForDischarging(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationGrpcService.getOrSaveRulesForDischarge(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
        .thenReturn(new RuleResponse());
    ReflectionTestUtils.setField(
        dischargePlanController,
        "dischargeInformationGrpcService",
        dischargeInformationGrpcService);
    RuleRequest dischargeRuleRequest = new RuleRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID)
                .content(mapper.writeValueAsString(dischargeRuleRequest))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_RULE_GET_URL_CLOUD_API_URL, DIS_RULE_GET_URL_CLOUD_API_URL})
  @ParameterizedTest
  public void testSaveRulesForDischargingServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationGrpcService.getOrSaveRulesForDischarge(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            dischargePlanController.saveRulesForDischarging(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController,
        "dischargeInformationGrpcService",
        dischargeInformationGrpcService);
    RuleRequest dischargeRuleRequest = new RuleRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID)
                .content(mapper.writeValueAsString(dischargeRuleRequest))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_RULE_GET_URL_CLOUD_API_URL, DIS_RULE_GET_URL_CLOUD_API_URL})
  @ParameterizedTest
  public void testSaveRulesForDischargingRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationGrpcService.getOrSaveRulesForDischarge(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.saveRulesForDischarging(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController,
        "dischargeInformationGrpcService",
        dischargeInformationGrpcService);
    RuleRequest dischargeRuleRequest = new RuleRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID)
                .content(mapper.writeValueAsString(dischargeRuleRequest))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_RULE_GET_URL_CLOUD_API_URL, DIS_RULE_GET_URL_SHIP_API_URL})
  @ParameterizedTest
  public void testGetRulesForDischarging(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationGrpcService.getOrSaveRulesForDischarge(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
        .thenReturn(new RuleResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_RULE_GET_URL_CLOUD_API_URL, DIS_RULE_GET_URL_SHIP_API_URL})
  @ParameterizedTest
  public void testGetRulesForDischargingServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationGrpcService.getOrSaveRulesForDischarge(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            dischargePlanController.getRulesForDischarging(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController,
        "dischargeInformationGrpcService",
        dischargeInformationGrpcService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_RULE_GET_URL_CLOUD_API_URL, DIS_RULE_GET_URL_SHIP_API_URL})
  @ParameterizedTest
  public void testGetRulesForDischargingRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationGrpcService.getOrSaveRulesForDischarge(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.getRulesForDischarging(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController,
        "dischargeInformationGrpcService",
        dischargeInformationGrpcService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + DIS_INFO_GET_URL, SHIP_API_URL_PREFIX + DIS_INFO_GET_URL})
  @ParameterizedTest
  public void testGetDischargeInformationByPortRIdServiceException(String url) throws Exception {
    when(this.dischargeInformationService.getDischargeInformation(anyLong(), anyLong(), anyLong()))
        .thenThrow(this.getGenericException());
    when(dischargePlanController.getDischargeInformationByPortRId(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    TEST_DISCHARGE_INFO_ID,
                    TEST_PORT_ROTATION_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + DIS_INFO_GET_URL, SHIP_API_URL_PREFIX + DIS_INFO_GET_URL})
  @ParameterizedTest
  public void testGetDischargeInformationByPortRIdRuntimeException(String url) throws Exception {
    when(this.dischargeInformationService.getDischargeInformation(anyLong(), anyLong(), anyLong()))
        .thenThrow(RuntimeException.class);
    when(dischargePlanController.getDischargeInformationByPortRId(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    TEST_DISCHARGE_INFO_ID,
                    TEST_PORT_ROTATION_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {UPLOAD_TIDE_CLOUD_API_URL, UPLOAD_TIDE_SHIP_API_URL})
  @ParameterizedTest
  public void testUploadTideDetails(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationGrpcService.dischargeUploadLoadingTideDetails(
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
            MockMvcRequestBuilders.multipart(url, 1L)
                .file(firstFile)
                .param("portName", "1")
                .param("portId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {UPLOAD_TIDE_CLOUD_API_URL, UPLOAD_TIDE_SHIP_API_URL})
  @ParameterizedTest
  public void testUploadTideDetailsServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationGrpcService.dischargeUploadLoadingTideDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.uploadTideDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController,
        "dischargeInformationGrpcService",
        dischargeInformationGrpcService);
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, 1L)
                .file(firstFile)
                .param("portName", "1")
                .param("portId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {UPLOAD_TIDE_CLOUD_API_URL, UPLOAD_TIDE_SHIP_API_URL})
  @ParameterizedTest
  public void testUploadTideDetailsRuntimeException(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationGrpcService.dischargeUploadLoadingTideDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.uploadTideDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any(),
                Mockito.anyString(),
                Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController,
        "dischargeInformationGrpcService",
        dischargeInformationGrpcService);
    MockMultipartFile firstFile =
        new MockMultipartFile("file", "filename.pdf", "text/plain", "test".getBytes());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(url, 1L)
                .file(firstFile)
                .param("portName", "1")
                .param("portId", "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  //    @ValueSource(
  //            strings = {DOWNLOAD_DIS_TIDE_CLOUD_API_URL, DOWNLOAD_DIS_TIDE_SHIP_API_URL})
  //    @ParameterizedTest
  //    public void testDownloadDischargingTideDetails(String url) throws Exception {
  //
  // Mockito.when(dischargeInformationGrpcService.downloadLoadingPortTideDetails(Mockito.anyLong())).thenReturn(new byte[]);
  //
  //    }

  @ValueSource(strings = {DIS_PLAN_CLOUD_API_URL, DIS_PLAN_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargePlan(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingPlan(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyString()))
        .thenReturn(new DischargePlanResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_PLAN_CLOUD_API_URL, DIS_PLAN_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargePlanServiceException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingPlan(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.getDischargePlan(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_PLAN_CLOUD_API_URL, DIS_PLAN_SHIP_API_URL})
  @ParameterizedTest
  public void testGetDischargePlanRuntimeException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingPlan(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.getDischargePlan(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, 1L, 1L, 1L, 1L)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_ADD_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_ADD_INSTRUCTION
      })
  @ParameterizedTest
  void testAddDischargingInstructionRuntimeException(String url) throws Exception {
    when(this.dischargingInstructionService.addDischargingInstruction(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsSaveRequest.class)))
        .thenThrow(RuntimeException.class);
    DischargingInstructionsSaveRequest request = new DischargingInstructionsSaveRequest();
    ObjectMapper mapper = new ObjectMapper();
    when(dischargePlanController.addDischargingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_UPDATE_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_UPDATE_INSTRUCTION
      })
  @ParameterizedTest
  void testUpdateDischargingInstructionRuntimeException(String url) throws Exception {
    when(this.dischargingInstructionService.updateDischargingInstructions(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsUpdateRequest.class)))
        .thenThrow(RuntimeException.class);

    when(dischargePlanController.updateDischargingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    DischargingInstructionsUpdateRequest request = new DischargingInstructionsUpdateRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_EDIT_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_EDIT_INSTRUCTION
      })
  @ParameterizedTest
  public void editDischargeInstructionsTestServiceException(String url) throws Exception {

    DischargingInstructionsStatus insideRequest = new DischargingInstructionsStatus();
    insideRequest.setInstruction("test");
    insideRequest.setInstructionId(1L);
    String inputJson = this.mapToJson(insideRequest);
    when(this.dischargingInstructionService.editDischargingInstructions(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(
            dischargePlanController.editDischargingInstructions(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_PORT_ROTATION_ID)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_EDIT_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_EDIT_INSTRUCTION
      })
  @ParameterizedTest
  public void editDischargeInstructionsTestRuntimeException(String url) throws Exception {

    DischargingInstructionsStatus insideRequest = new DischargingInstructionsStatus();
    insideRequest.setInstruction("test");
    insideRequest.setInstructionId(1L);
    String inputJson = this.mapToJson(insideRequest);
    when(this.dischargingInstructionService.editDischargingInstructions(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.editDischargingInstructions(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_PORT_ROTATION_ID)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_GET_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_GET_INSTRUCTION
      })
  @ParameterizedTest
  void testGetAllDischargingInstructions(String url) throws Exception {
    when(this.dischargingInstructionService.getDischargingInstructions(
            anyLong(), anyLong(), anyLong()))
        .thenReturn(new DischargingInstructionResponse());
    when(dischargePlanController.getAllDischargingInstructions(
            any(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(
      strings = {
        CLOUD_API_URL_PREFIX + DIS_DELETE_INSTRUCTION,
        SHIP_API_URL_PREFIX + DIS_DELETE_INSTRUCTION
      })
  @ParameterizedTest
  void testDeleteDischargingInstructionServiceException(String url) throws Exception {
    when(this.dischargingInstructionService.deleteDischargingInstructions(
            anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    DischargingInstructionsStatus request = new DischargingInstructionsStatus();
    ObjectMapper mapper = new ObjectMapper();
    when(dischargePlanController.deleteDischargingInstructions(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargingInstructionService", dischargingInstructionService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {UPDATE_ULLAGE_CLOUD_API_URL, UPDATE_ULLAGE_SHIP_API_URL})
  @ParameterizedTest
  void testGetUpdateUllageDetails(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getUpdateUllageDetails(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenReturn(new DischargeUpdateUllageResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {UPDATE_ULLAGE_CLOUD_API_URL, UPDATE_ULLAGE_SHIP_API_URL})
  @ParameterizedTest
  void testGetUpdateUllageDetailsServiceException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getUpdateUllageDetails(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    when(dischargePlanController.getUpdateUllageDetails(
            any(), anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {UPDATE_ULLAGE_CLOUD_API_URL, UPDATE_ULLAGE_SHIP_API_URL})
  @ParameterizedTest
  void testGetUpdateUllageDetailsRuntimeException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getUpdateUllageDetails(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenThrow(RuntimeException.class);
    when(dischargePlanController.getUpdateUllageDetails(
            any(), anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, "1")
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(strings = {UP_ULL_CLOUD_API_URL, UP_ULL_SHIP_API_URL})
  @ParameterizedTest
  void testUpdateUllage(String url) throws Exception {
    Mockito.when(dischargeInformationService.updateUllage(Mockito.any(), Mockito.anyString()))
        .thenReturn(new UllageBillReply());
    UllageBillRequest inputData = new UllageBillRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                .content(mapper.writeValueAsString(inputData))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {UP_ULL_CLOUD_API_URL, UP_ULL_SHIP_API_URL})
  @ParameterizedTest
  void testUpdateUllageServiceException(String url) throws Exception {
    Mockito.when(dischargeInformationService.updateUllage(Mockito.any(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    Mockito.when(dischargePlanController.updateUllage(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    UllageBillRequest inputData = new UllageBillRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                .content(mapper.writeValueAsString(inputData))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {UP_ULL_CLOUD_API_URL, UP_ULL_SHIP_API_URL})
  @ParameterizedTest
  void testUpdateUllageRuntimeException(String url) throws Exception {
    Mockito.when(dischargeInformationService.updateUllage(Mockito.any(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    Mockito.when(dischargePlanController.updateUllage(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    UllageBillRequest inputData = new UllageBillRequest();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                .content(mapper.writeValueAsString(inputData))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(strings = {SAVE_DIS_INFO_CLOUD_API_URL, SAVE_DIS_INFO_SHIP_API_URL})
  @ParameterizedTest
  void testSaveDischargingInformation(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationService.saveDischargingInformation(
                Mockito.any(), Mockito.anyString()))
        .thenReturn(new DischargingInformationResponse());
    ObjectMapper mapper = new ObjectMapper();
    DischargingInformationRequest request = new DischargingInformationRequest();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {SAVE_DIS_INFO_CLOUD_API_URL, SAVE_DIS_INFO_SHIP_API_URL})
  @ParameterizedTest
  void testSaveDischargingInformationServiceException(String url) throws Exception {
    Mockito.when(
            this.dischargeInformationService.saveDischargingInformation(
                Mockito.any(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    ObjectMapper mapper = new ObjectMapper();
    DischargingInformationRequest request = new DischargingInformationRequest();
    when(dischargePlanController.saveDischargingInformation(any(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_PLA_CLOUD_API_URL, DIS_PLA_SHIP_API_URL})
  @ParameterizedTest
  void testDischargingPlanStatus(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.saveDischargingInfoStatus(
                Mockito.any(), Mockito.anyString()))
        .thenReturn(new LoadingInfoAlgoResponse());
    ObjectMapper mapper = new ObjectMapper();
    AlgoStatusRequest request = new AlgoStatusRequest();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_PLA_CLOUD_API_URL, DIS_PLA_SHIP_API_URL})
  @ParameterizedTest
  void testDischargingPlanStatusServiceException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.saveDischargingInfoStatus(
                Mockito.any(), Mockito.anyString()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    when(dischargePlanController.dischargingPlanStatus(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    ObjectMapper mapper = new ObjectMapper();
    AlgoStatusRequest request = new AlgoStatusRequest();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_PLA_CLOUD_API_URL, DIS_PLA_SHIP_API_URL})
  @ParameterizedTest
  void testDischargingPlanStatusRuntimeException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.saveDischargingInfoStatus(
                Mockito.any(), Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    when(dischargePlanController.dischargingPlanStatus(
            any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    ObjectMapper mapper = new ObjectMapper();
    AlgoStatusRequest request = new AlgoStatusRequest();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(request))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  @ValueSource(strings = {GEN_DIS_CLOUD_API_URL, GEN_DIS_SHIP_API_URL})
  @ParameterizedTest
  void testGenerateDischargePlan(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.generateDischargingPlan(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(new LoadingInfoAlgoResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {GEN_DIS_CLOUD_API_URL, GEN_DIS_SHIP_API_URL})
  @ParameterizedTest
  void testGenerateDischargePlanServiceException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.generateDischargingPlan(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(
            new GenericServiceException(
                "service exception",
                CommonErrorCodes.E_GEN_INTERNAL_ERR,
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    when(dischargePlanController.generateDischargePlan(any(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GEN_DIS_CLOUD_API_URL, GEN_DIS_SHIP_API_URL})
  @ParameterizedTest
  void testGenerateDischargePlanRuntimeException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.generateDischargingPlan(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    when(dischargePlanController.generateDischargePlan(any(), anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isServiceUnavailable());
  }

  //    @ValueSource(
  //            strings = {
  //                    SAVE_DIS_PLAN_CLOUD_API_URL,
  //                    SAVE_DIS_PLAN_SHIP_API_URL
  //            })
  //    @ParameterizedTest
  //    void testSaveDischargePlan(String url) throws Exception {
  //
  // Mockito.when(dischargeInformationService.saveDischargingPlan(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.any(),Mockito.anyString()))
  //                .thenReturn(new LoadingPlanAlgoResponse());
  //        Object requestJson = new Object();
  //        ObjectMapper mapper = new ObjectMapper();
  //        this.mockMvc
  //                .perform(
  //                        MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID,
  // TEST_VOYAGE_ID)
  //                                .content(String.valueOf(requestJson))
  //                                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                                .accept(MediaType.APPLICATION_JSON_VALUE))
  //                .andExpect(status().isOk());
  //    }

  @ValueSource(strings = {SAVE_DIS_PLAN_CLOUD_API_URL, SAVE_DIS_PLAN_SHIP_API_URL})
  @ParameterizedTest
  void testSaveDischargePlanRuntimeException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.saveDischargingPlan(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyString()))
        .thenThrow(RuntimeException.class);
    when(dischargePlanController.saveDischargePlan(any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    Object requestJson = new Object();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(String.valueOf(requestJson))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {SAVE_DIS_PLAN_CLOUD_API_URL, SAVE_DIS_PLAN_SHIP_API_URL})
  @ParameterizedTest
  void testSaveDischargePlanServiceException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.saveDischargingPlan(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyString()))
        .thenThrow(this.getGenericException());
    when(dischargePlanController.saveDischargePlan(any(), anyLong(), anyLong(), anyLong(), any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    Object requestJson = new Object();
    ObjectMapper mapper = new ObjectMapper();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(String.valueOf(requestJson))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  //    @ValueSource(
  //            strings = {
  //                    GET_DIS_INFO_CLOUD_API_URL,
  //                    GET_DIS_INFO_SHIP_API_URL
  //            })
  //    @ParameterizedTest
  //    void testGetDischargeInfoStatus(String url) throws Exception {
  //        LoadingInfoAlgoStatusRequest request = new LoadingInfoAlgoStatusRequest();
  //        request.setProcessId("1");
  //        request.setConditionType(1);
  //
  // Mockito.when(dischargeInformationService.dischargeInfoStatusCheck(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.any(),Mockito.anyInt()))
  //                .thenReturn(new DischargingInfoAlgoStatus());
  //
  // Mockito.when(dischargePlanController.getDischargeInfoStatus(Mockito.any(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.any())).thenCallRealMethod();
  //
  // ReflectionTestUtils.setField(dischargePlanController,"dischargeInformationService",dischargeInformationService);
  //        this.mockMvc
  //                .perform(
  //                        MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID,
  // TEST_VOYAGE_ID)
  //                                .content(String.valueOf(request))
  //                                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                                .accept(MediaType.APPLICATION_JSON_VALUE))
  //                .andExpect(status().isOk());
  //
  //    }

  @ValueSource(strings = {GET_DIS_SEQ_CLOUD_API_URL, GET_DIS_SEQ_SHIP_API_URL})
  @ParameterizedTest
  void testGetDischargeSequence(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingSequence(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(new LoadingSequenceResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {GET_DIS_SEQ_CLOUD_API_URL, GET_DIS_SEQ_SHIP_API_URL})
  @ParameterizedTest
  void testGetDischargeSequenceServiceException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingSequence(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            dischargePlanController.getDischargeSequence(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {GET_DIS_SEQ_CLOUD_API_URL, GET_DIS_SEQ_SHIP_API_URL})
  @ParameterizedTest
  void testGetDischargeSequenceRuntimeException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingSequence(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.getDischargeSequence(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GET_ALGO_CLOUD_API_URL, GET_ALGO_SHIP_API_URL})
  @ParameterizedTest
  void testGetAlgoErrors(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingInfoAlgoErrors(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(new AlgoErrorResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, 2)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {GET_ALGO_CLOUD_API_URL, GET_ALGO_SHIP_API_URL})
  @ParameterizedTest
  void testGetAlgoErrorsServiceException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingInfoAlgoErrors(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            dischargePlanController.getAlgoErrors(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyInt()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, 2)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @ValueSource(strings = {GET_ALGO_CLOUD_API_URL, GET_ALGO_SHIP_API_URL})
  @ParameterizedTest
  void testGetAlgoErrorsRuntimeException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingInfoAlgoErrors(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt()))
        .thenThrow(RuntimeException.class);
    Mockito.when(
            dischargePlanController.getAlgoErrors(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyInt()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID, 2)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_PLAN_RULE_CLOUD_API_URL, DIS_PLAN_RULE_SHIP_API_URL})
  @ParameterizedTest
  void testGetDischargingPlanRules(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingPlanRules(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(new RuleResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_PLAN_RULE_CLOUD_API_URL, DIS_PLAN_RULE_SHIP_API_URL})
  @ParameterizedTest
  void testGetDischargingPlanRulesServiceException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.getDischargingPlanRules(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenThrow(this.getGenericException());
    Mockito.when(
            dischargePlanController.getDischargingPlanRules(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {DIS_PLAN_RULE_CLOUD_API_URL, DIS_PLAN_RULE_SHIP_API_URL})
  @ParameterizedTest
  void testSaveLoadingPlanRule(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.saveDischargingPlanRules(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenReturn(new RuleResponse());
    ObjectMapper mapper = new ObjectMapper();
    RuleRequest dischargePlanRule = new RuleRequest();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(dischargePlanRule))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {DIS_PLAN_RULE_CLOUD_API_URL, DIS_PLAN_RULE_SHIP_API_URL})
  @ParameterizedTest
  void testSaveLoadingPlanRuleServiceException(String url) throws Exception {
    Mockito.when(
            dischargeInformationService.saveDischargingPlanRules(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenThrow(this.getGenericException());
    ObjectMapper mapper = new ObjectMapper();
    RuleRequest dischargePlanRule = new RuleRequest();
    Mockito.when(
            dischargePlanController.saveLoadingPlanRule(
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargePlanController, "dischargeInformationService", dischargeInformationService);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(url, TEST_VESSEL_ID, TEST_DISCHARGE_INFO_ID, TEST_VOYAGE_ID)
                .content(mapper.writeValueAsString(dischargePlanRule))
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isInternalServerError());
  }
}
