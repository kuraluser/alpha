/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.gateway.domain.LoadableQuantity;
import com.cpdss.gateway.domain.LoadableQuantityResponse;
import com.cpdss.gateway.domain.LoadableStudy;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.Voyage;
import com.cpdss.gateway.domain.VoyageResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.multipart.MultipartFile;

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
  
  private static final String VOYAGE = "VOYAGE";
  private static final String VOYAGE_ERROR = "Error in calling voyage service";
  private static final String LOADABLE_QUANTITY_DUMMY = "100";
  private static final String LOADABLE_QUANTITY_ERROR = "Error in calling loadable quantity service";

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
            .setDraftMark(DRAFT_MARK)
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
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                    .setMessage("Failure")
                    .setStatus(FAILED)
                    .build());
    Mockito.when(spy.getloadableStudyList(ArgumentMatchers.any(LoadableStudyRequest.class)))
        .thenReturn(replyBuilder.build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                spy.getLoadableStudies(
                    TEST_COMPANY_ID, TEST_VESSEL_ID, TEST_VOYAGE_ID, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /**
   * Test loadable study positive scenario
   *
   * @throws Exception
   */
  @Test
  void testSaveLoadableStudy() throws Exception {
    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    Mockito.when(spy.saveLoadableStudy(any(LoadableStudy.class), anyString(), any()))
        .thenCallRealMethod();
    Builder replyBuilder =
        LoadableStudyReply.newBuilder()
            .setId(1L)
            .setResponseStatus(
                StatusReply.newBuilder().setMessage("Success").setStatus(SUCCESS).build());
    Mockito.when(spy.saveLoadableStudy(ArgumentMatchers.any(LoadableStudyDetail.class)))
        .thenReturn(replyBuilder.build());
    MockMultipartFile file =
        new MockMultipartFile("data", "filename.pdf", "text/plain", "test content".getBytes());
    LoadableStudyResponse response =
        spy.saveLoadableStudy(
            this.generateLoadableStudySaveRequest(),
            CORRELATION_ID_HEADER_VALUE,
            new MultipartFile[] {file});
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatus.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"),
        () -> assertNotNull(response.getLoadableStudyId()));
  }

  /**
   * Test saveLoadableStudy - GRPC failure
   *
   * @throws Exception
   */
  @Test
  void testSaveLoadableStudyGrpcFailure() throws Exception {
    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    Mockito.when(spy.saveLoadableStudy(any(LoadableStudy.class), anyString(), any()))
        .thenCallRealMethod();
    Builder replyBuilder =
        LoadableStudyReply.newBuilder()
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                    .setMessage("Failure")
                    .setStatus(FAILED)
                    .build());
    Mockito.when(spy.saveLoadableStudy(ArgumentMatchers.any(LoadableStudyDetail.class)))
        .thenReturn(replyBuilder.build());
    MockMultipartFile file =
        new MockMultipartFile("data", "filename.pdf", "text/plain", "test content".getBytes());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                spy.saveLoadableStudy(
                    this.generateLoadableStudySaveRequest(),
                    CORRELATION_ID_HEADER_VALUE,
                    new MultipartFile[] {file}));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /**
   * Test saveLoadableStudy - unsupported file type
   *
   * @throws Exception
   */
  @Test
  void testSaveLoadableStudyTestUnSupportedFile() throws Exception {
    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    Mockito.when(spy.saveLoadableStudy(any(LoadableStudy.class), anyString(), any()))
        .thenCallRealMethod();
    MockMultipartFile file =
        new MockMultipartFile("data", "filename.xvf", "text/plain", "test content".getBytes());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                spy.saveLoadableStudy(
                    this.generateLoadableStudySaveRequest(),
                    CORRELATION_ID_HEADER_VALUE,
                    new MultipartFile[] {file}));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /**
   * Test saveLoadableStudy - large file size
   *
   * @throws Exception
   */
  @Test
  void testSaveLoadableStudyTestLargeFile() throws Exception {
    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    Mockito.when(spy.saveLoadableStudy(any(LoadableStudy.class), anyString(), any()))
        .thenCallRealMethod();
    MockMultipartFile file = Mockito.mock(MockMultipartFile.class);
    Mockito.when(file.getSize()).thenReturn(99999999999L);
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                spy.saveLoadableStudy(
                    this.generateLoadableStudySaveRequest(),
                    CORRELATION_ID_HEADER_VALUE,
                    new MultipartFile[] {file}));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private LoadableStudy generateLoadableStudySaveRequest() {
    final LoadableStudy request = new LoadableStudy();
    request.setName(LOADABLE_STUDY_NAME);
    request.setDetail(LOADABLE_STUDY_DETAIL);
    request.setCharterer(CHARTERER);
    request.setSubCharterer(SUB_CHARTERER);
    request.setDraftMark(new BigDecimal(DRAFT_MARK));
    request.setLoadLineXId(LOAD_LINE_ID);
    request.setDraftRestriction(new BigDecimal(DRAFT_RESTRICTION));
    request.setMaxTempExpected(new BigDecimal(MAX_TEMP_EXPECTED));
    return request;
  }
  
  
  
	/**
	 * Save voyage - positive scenario
	 *
	 * @throws GenericServiceException
	 */
	@Test
	void testSaveVoyagePositiveTestCase() throws GenericServiceException {

		LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);

		VoyageReply voyageReply = VoyageReply.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).setVoyageId(1)
				.build();

		Mockito.when(spy.saveVoyage(ArgumentMatchers.any(Voyage.class),  anyLong(), anyLong())).thenCallRealMethod();
		
		Mockito.when(spy.saveVoyage(ArgumentMatchers.any(VoyageRequest.class))).thenReturn(voyageReply);
		Voyage voyage = new Voyage();
		voyage.setVoyageNo(VOYAGE);
		voyage.setCaptainId((long) 1);
		voyage.setChiefOfficerId((long) 1);

		VoyageResponse voyageResponse = spy.saveVoyage(voyage, (long) 1, (long) 1);
		
		Assert.assertEquals(
				SUCCESS,voyageResponse.getResponseStatus().getStatus());
	}
	
	/**
	 * Save voyage - negative scenario
	 *
	 * @throws GenericServiceException
	 */
	@Test
	void testSaveVoyageNegativeTestCase() throws GenericServiceException {

		LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);

		VoyageReply voyageReply = VoyageReply.newBuilder().setMessage(SUCCESS).setStatus(FAILED).setVoyageId(1).build();

		Mockito.when(spy.saveVoyage(ArgumentMatchers.any(Voyage.class), anyLong(), anyLong())).thenCallRealMethod();

		Mockito.when(spy.saveVoyage(ArgumentMatchers.any(VoyageRequest.class))).thenReturn(voyageReply);
		Voyage voyage = new Voyage();
		voyage.setVoyageNo(VOYAGE);
		voyage.setCaptainId((long) 1);
		voyage.setChiefOfficerId((long) 1);

		final GenericServiceException ex = assertThrows(GenericServiceException.class,
				() -> spy.saveVoyage(voyage, (long) 1, (long) 1));

		assertAll(() -> Assert.assertEquals(VOYAGE_ERROR, ex.getMessage()),
				() -> Assert.assertEquals(CommonErrorCodes.E_GEN_INTERNAL_ERR, ex.getCode()),
				() -> Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus()));

	}
	
	/**
	 * Save loadable quantity - positive scenario
	 *
	 * @throws GenericServiceException
	 */
	@Test
	void testSaveLoadableQuantityPositiveTestCase() throws GenericServiceException {

		LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);

		LoadableQuantityReply loadableQuantityReply = LoadableQuantityReply.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).setLoadableQuantityId(1)
				.build();

		Mockito.when(spy.saveLoadableQuantity(ArgumentMatchers.any(LoadableQuantity.class),  anyLong())).thenCallRealMethod();
		
		Mockito.when(spy.saveLoadableQuantity(ArgumentMatchers.any(LoadableQuantityRequest.class))).thenReturn(loadableQuantityReply);
		LoadableQuantity loadableQuantity = new LoadableQuantity();
		loadableQuantity.setLoadableStudyId(1);
		loadableQuantity.setConstant(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setDisplacmentDraftRestriction(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setDistanceFromLastPort(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setDwt(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstDOOnBoard(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstFOOnBoard(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstFreshWaterOnBoard(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstSagging(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstSeaDensity(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstTotalFOConsumption(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setFoConsumptionPerDay(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setLimitingDraft(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setOtherIfAny(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setSaggingDeduction(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setSgCorrection(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setTotalQuantity(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setTpc(LOADABLE_QUANTITY_DUMMY);
        loadableQuantity.setVesselAverageSpeed(LOADABLE_QUANTITY_DUMMY);
        loadableQuantity.setVesselLightWeight(LOADABLE_QUANTITY_DUMMY);
	
		LoadableQuantityResponse loadableQuantityResponse = spy.saveLoadableQuantity(loadableQuantity, (long) 1);
		
		
		Assert.assertEquals(
				SUCCESS,loadableQuantityResponse.getResponseStatus().getStatus());
	}
	
	/**
	 * Save loadable quantity - negative scenario
	 *
	 * @throws GenericServiceException
	 */
	@Test
	void testSaveLoadableQuantityNegativeTestCase() throws GenericServiceException {

		LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);

		LoadableQuantityReply loadableQuantityReply = LoadableQuantityReply.newBuilder().setMessage(SUCCESS).setStatus(FAILED).setLoadableQuantityId(1)
				.build();

		Mockito.when(spy.saveLoadableQuantity(ArgumentMatchers.any(LoadableQuantity.class),  anyLong())).thenCallRealMethod();

		Mockito.when(spy.saveLoadableQuantity(ArgumentMatchers.any(LoadableQuantity.class),  anyLong())).thenCallRealMethod();
		
		Mockito.when(spy.saveLoadableQuantity(ArgumentMatchers.any(LoadableQuantityRequest.class))).thenReturn(loadableQuantityReply);
		LoadableQuantity loadableQuantity = new LoadableQuantity();
		loadableQuantity.setLoadableStudyId(1);
		loadableQuantity.setConstant(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setDisplacmentDraftRestriction(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setDistanceFromLastPort(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setDwt(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstDOOnBoard(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstFOOnBoard(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstFreshWaterOnBoard(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstSagging(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstSeaDensity(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setEstTotalFOConsumption(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setFoConsumptionPerDay(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setLimitingDraft(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setOtherIfAny(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setSaggingDeduction(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setSgCorrection(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setTotalQuantity(LOADABLE_QUANTITY_DUMMY);
		loadableQuantity.setTpc(LOADABLE_QUANTITY_DUMMY);
        loadableQuantity.setVesselAverageSpeed(LOADABLE_QUANTITY_DUMMY);
        loadableQuantity.setVesselLightWeight(LOADABLE_QUANTITY_DUMMY);

		final GenericServiceException ex = assertThrows(GenericServiceException.class,
				() -> spy.saveLoadableQuantity(loadableQuantity, (long) 1));

		assertAll(() -> Assert.assertEquals(LOADABLE_QUANTITY_ERROR, ex.getMessage()),
				() -> Assert.assertEquals(CommonErrorCodes.E_GEN_INTERNAL_ERR, ex.getCode()),
				() -> Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus()));

	}
}
