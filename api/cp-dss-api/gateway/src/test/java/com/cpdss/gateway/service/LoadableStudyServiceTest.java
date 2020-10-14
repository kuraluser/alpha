/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * Test class for {@link LoadableStudyService}
 *
 * @author suhail.k
 */
@SpringJUnitConfig(classes = {LoadableStudyService.class})
class LoadableStudyServiceTest {

  private static final String LOADABLE_STUDY_NAME = "LS-01";
  private static final String LOADABLE_STUDY_DETAIL = "detail-1";
  private static final String CREATED_DATE_FORMAT = "dd-MM-yyyy";
  private static final String LOADABLE_STUDY_STATUS = "pending";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";
  private static final Long TEST_COMPANY_ID = 1L;
  private static final Long TEST_VESSEL_ID = 1L;
  private static final Long TEST_VOYAGE_ID = 1L;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String CHARTERER = "charterer";
  private static final String SUB_CHARTERER = "sub-chartere";
  private static final String DRAFT_MARK = "1000";
  private static final Long LOAD_LINE_ID = 1L;
  private static final String DRAFT_RESTRICTION = "1000";
  private static final String MAX_TEMP_EXPECTED = "100";

  /**
   * Loadable study listing - positive scenario
   *
   * @throws GenericServiceException
   */
  @Test
  void testGetLoadableStudies() throws GenericServiceException {
    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    Mockito.when(spy.getLoadableStudies(anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Builder replyBuilder =
        LoadableStudyReply.newBuilder()
            .setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).build());
    replyBuilder.addLoadableStudies(
        LoadableStudyDetail.newBuilder()
            .setId(1L)
            .setName(LOADABLE_STUDY_NAME)
            .setDetail(LOADABLE_STUDY_DETAIL)
            .setCreatedDate(
                DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT).format(LocalDate.now()))
            .setStatus(LOADABLE_STUDY_STATUS)
            .setCharterer(CHARTERER)
            .setSubCharterer(SUB_CHARTERER)
            .setDraftMark(DRAFT_MARK) // TODO check for Bigdecimal usage with GRPC
            .setLoadLineXId(LOAD_LINE_ID)
            .setDraftRestriction(DRAFT_RESTRICTION)
            .setMaxTempExpected(MAX_TEMP_EXPECTED)
            .build());
    Mockito.when(spy.getloadableStudyList(ArgumentMatchers.any(LoadableStudyRequest.class)))
        .thenReturn(replyBuilder.build());
    LoadableStudyResponse response =
        spy.getLoadableStudies(
            TEST_COMPANY_ID, TEST_VESSEL_ID, TEST_VOYAGE_ID, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatus.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"),
        () -> assertNotNull(response.getLoadableStudies()));
  }

  /**
   * Loadable study listing - negative scenario.
   *
   * @throws GenericServiceException
   */
  @Test
  void testGetLoadableStudiesNegativeScenario() throws GenericServiceException {
    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    Mockito.when(spy.getLoadableStudies(anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Builder replyBuilder =
        LoadableStudyReply.newBuilder()
            .setResponseStatus(StatusReply.newBuilder().setStatus(FAILED).build());
    Mockito.when(spy.getloadableStudyList(ArgumentMatchers.any(LoadableStudyRequest.class)))
        .thenReturn(replyBuilder.build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                spy.getLoadableStudies(
                    TEST_COMPANY_ID, TEST_VESSEL_ID, TEST_VOYAGE_ID, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_GEN_INTERNAL_ERR, ex.getCode(), "Invalid error code"),
        () ->
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus(), "Invalid http status"));
  }
}
