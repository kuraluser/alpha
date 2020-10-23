/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.LoadableStudy;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.domain.VoyageResponse;
import com.cpdss.gateway.service.LoadableStudyService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
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
class LoadableStudyControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private LoadableStudyService loadableStudyService;

  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";
  private static final Long TEST_VESSEL_ID = 1L;
  private static final Long TEST_VOYAGE_ID = 1L;

  private static final String CHARTERER = "charterer";
  private static final String SUB_CHARTERER = "sub-chartere";
  private static final String DRAFT_MARK = "1000";
  private static final Long LOAD_LINE_ID = 1L;
  private static final String DRAFT_RESTRICTION = "1000";
  private static final String MAX_TEMP_EXPECTED = "100";
  private static final String LOADABLE_STUDY_NAME = "LS-01";
  private static final String LOADABLE_STUDY_DETAIL = "detail-1";
  private static final String CREATED_DATE_FORMAT = "dd-MM-yyyy";
  private static final String LOADABLE_STUDY_STATUS = "pending";

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

  private static final String NAME = "name";
  private static final String CHARTERER_LITERAL = "charterer";
  private static final String DRAFT_MARK_LITERAL = "draftMark";
  private static final String LOAD_LINE_ID_LITERAL = "loadLineXId";
  private static final Long TEST_LODABLE_STUDY_ID = 1L;

  /**
   * Positive test case. Test method for positive response scenario
   *
   * @throws Exception
   */
  @ValueSource(strings = {LOADABLE_STUDY_LIST_CLOUD_API_URL, LOADABLE_STUDY_LIST_SHIP_API_URL})
  @ParameterizedTest
  void testGetLoadableStudyByVoyage(String url) throws Exception {
    when(this.loadableStudyService.getLoadableStudies(anyLong(), anyLong(), anyLong(), anyString()))
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
    when(this.loadableStudyService.getLoadableStudies(anyLong(), anyLong(), anyLong(), anyString()))
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
    when(this.loadableStudyService.getLoadableStudies(anyLong(), anyLong(), anyLong(), anyString()))
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
}
