/* Licensed under Apache-2.0 */
package com.cpdss.gateway.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.service.LoadableStudyService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

  // API URLS
  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/cloud";
  private static final String LOADABLE_STUDY_LIST_API_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loadable-studies";
  private static final String LOADABLE_STUDY_LIST_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + LOADABLE_STUDY_LIST_API_URL;
  private static final String LOADABLE_STUDY_LIST_SHIP_API_URL =
      SHIP_API_URL_PREFIX + LOADABLE_STUDY_LIST_API_URL;

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
                HttpStatus.INTERNAL_SERVER_ERROR));
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
}
