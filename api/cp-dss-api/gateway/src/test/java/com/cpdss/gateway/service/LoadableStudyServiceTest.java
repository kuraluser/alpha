/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.Operation;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.gateway.domain.LoadableQuantity;
import com.cpdss.gateway.domain.LoadableQuantityResponse;
import com.cpdss.gateway.domain.LoadableStudy;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.domain.Voyage;
import com.cpdss.gateway.domain.VoyageResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
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

  private LoadableStudyService loadableStudyService;

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
  private static final String LOADABLE_QUANTITY_ERROR =
      "Error in calling loadable quantity service";
  private static final String INVALID_LOADABLE_QUANTITY = "INVALID_LOADABLE_QUANTITY";

  @BeforeEach
  public void init() {
    this.loadableStudyService = Mockito.mock(LoadableStudyService.class);
  }

  /**
   * Loadable study listing - positive scenario
   *
   * @throws GenericServiceException
   */
  @Test
  void testGetLoadableStudies() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getLoadableStudies(
                anyLong(), anyLong(), anyLong(), anyString()))
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
    Mockito.when(
            this.loadableStudyService.getloadableStudyList(
                ArgumentMatchers.any(LoadableStudyRequest.class)))
        .thenReturn(replyBuilder.build());
    LoadableStudyResponse response =
        this.loadableStudyService.getLoadableStudies(
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
    Mockito.when(
            this.loadableStudyService.getLoadableStudies(
                anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Builder replyBuilder =
        LoadableStudyReply.newBuilder()
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                    .setMessage("Failure")
                    .setStatus(FAILED)
                    .build());
    Mockito.when(
            this.loadableStudyService.getloadableStudyList(
                ArgumentMatchers.any(LoadableStudyRequest.class)))
        .thenReturn(replyBuilder.build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.getLoadableStudies(
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
    Mockito.when(
            this.loadableStudyService.saveLoadableStudy(
                any(LoadableStudy.class), anyString(), any()))
        .thenCallRealMethod();
    Builder replyBuilder =
        LoadableStudyReply.newBuilder()
            .setId(1L)
            .setResponseStatus(
                StatusReply.newBuilder().setMessage("Success").setStatus(SUCCESS).build());
    Mockito.when(
            this.loadableStudyService.saveLoadableStudy(
                ArgumentMatchers.any(LoadableStudyDetail.class)))
        .thenReturn(replyBuilder.build());
    MockMultipartFile file =
        new MockMultipartFile("data", "filename.pdf", "text/plain", "test content".getBytes());
    LoadableStudyResponse response =
        this.loadableStudyService.saveLoadableStudy(
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
    Mockito.when(
            this.loadableStudyService.saveLoadableStudy(
                any(LoadableStudy.class), anyString(), any()))
        .thenCallRealMethod();
    Builder replyBuilder =
        LoadableStudyReply.newBuilder()
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                    .setMessage("Failure")
                    .setStatus(FAILED)
                    .build());
    Mockito.when(
            this.loadableStudyService.saveLoadableStudy(
                ArgumentMatchers.any(LoadableStudyDetail.class)))
        .thenReturn(replyBuilder.build());
    MockMultipartFile file =
        new MockMultipartFile("data", "filename.pdf", "text/plain", "test content".getBytes());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveLoadableStudy(
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
    Mockito.when(
            this.loadableStudyService.saveLoadableStudy(
                any(LoadableStudy.class), anyString(), any()))
        .thenCallRealMethod();
    MockMultipartFile file =
        new MockMultipartFile("data", "filename.xvf", "text/plain", "test content".getBytes());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveLoadableStudy(
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
    Mockito.when(
            this.loadableStudyService.saveLoadableStudy(
                any(LoadableStudy.class), anyString(), any()))
        .thenCallRealMethod();
    MockMultipartFile file = Mockito.mock(MockMultipartFile.class);
    Mockito.when(file.getSize()).thenReturn(99999999999L);
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveLoadableStudy(
                    this.generateLoadableStudySaveRequest(),
                    CORRELATION_ID_HEADER_VALUE,
                    new MultipartFile[] {file}));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void TestGetLoadableStudyPortList() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getLoadableStudyPortRotationList(
                anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.getLoadableStudyPortRotationList(
                any(PortRotationRequest.class)))
        .thenReturn(this.generatePortRotationReply(false).build());
    PortRotationResponse response =
        this.loadableStudyService.getLoadableStudyPortRotationList(
            1L, 1L, 1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatus.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"),
        () -> assertNotNull(response.getPortList()),
        () -> assertNotNull(response.getOperations()));
  }

  @Test
  void TestGetLoadableStudyPortListFailureScenario() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getLoadableStudyPortRotationList(
                anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    PortRotationReply.Builder reply = this.generatePortRotationReply(false);
    reply.setResponseStatus(
        StatusReply.newBuilder()
            .setStatus(FAILED)
            .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
            .build());
    Mockito.when(
            this.loadableStudyService.getLoadableStudyPortRotationList(
                any(PortRotationRequest.class)))
        .thenReturn(reply.build());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.getLoadableStudyPortRotationList(
                    1L, 1L, 1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void TestGetLoadableStudyPortListWithEmptyData() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getLoadableStudyPortRotationList(
                anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    PortRotationReply.Builder reply = this.generatePortRotationReply(true);
    Mockito.when(
            this.loadableStudyService.getLoadableStudyPortRotationList(
                any(PortRotationRequest.class)))
        .thenReturn(reply.build());

    PortRotationResponse response =
        this.loadableStudyService.getLoadableStudyPortRotationList(
            1L, 1L, 1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatus.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"),
        () -> assertNotNull(response.getPortList()),
        () -> assertNotNull(response.getOperations()));
  }

  /**
   * Create Port reply mock object
   *
   * @return
   */
  private PortRotationReply.Builder generatePortRotationReply(boolean empty) {
    PortRotationReply.Builder replyBuilder = PortRotationReply.newBuilder();
    IntStream.of(0, 5)
        .forEach(
            i -> {
              PortRotationDetail.Builder builder = PortRotationDetail.newBuilder();
              builder.setId(Long.valueOf(i));
              builder.setPortId(Long.valueOf(i));
              builder.setLoadableStudyId(Long.valueOf(i));
              builder.setOperationId(Long.valueOf(i));
              builder.setBirthId(Long.valueOf(i));
              builder.setSeaWaterDensity(!empty ? "1.025" : "");
              builder.setDistanceBetweenPorts(!empty ? "100" : "");
              builder.setTimeOfStay(!empty ? "2.5" : "");
              builder.setMaxDraft(!empty ? "100" : "");
              builder.setMaxAirDraft(!empty ? "101" : "");
              builder.setEta(String.valueOf(LocalDate.now()));
              builder.setEtd(String.valueOf(LocalDate.now()));
              builder.setLayCanFrom(String.valueOf(LocalDateTime.now()));
              builder.setLayCanTo(String.valueOf(LocalDateTime.now()));
              replyBuilder.addPorts(builder.build());
            });
    IntStream.of(0, 2)
        .forEach(
            i -> {
              Operation.Builder builder = Operation.newBuilder();
              builder.setId(Long.valueOf(i));
              builder.setOperationName("op-" + i);
              replyBuilder.addOperations(builder.build());
            });
    replyBuilder.setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
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

    VoyageReply voyageReply =
        VoyageReply.newBuilder()
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setMessage(SUCCESS)
                    .setStatus(SUCCESS)
                    .setCode(String.valueOf(HttpStatus.OK.value())))
            .setVoyageId(1)
            .build();

    Mockito.when(
            spy.saveVoyage(ArgumentMatchers.any(Voyage.class), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();

    Mockito.when(spy.saveVoyage(ArgumentMatchers.any(VoyageRequest.class))).thenReturn(voyageReply);
    Voyage voyage = new Voyage();
    voyage.setVoyageNo(VOYAGE);
    voyage.setCaptainId((long) 1);
    voyage.setChiefOfficerId((long) 1);

    VoyageResponse voyageResponse = spy.saveVoyage(voyage, (long) 1, (long) 1, "123");

    Assert.assertEquals(
        String.valueOf(HttpStatus.OK.value()), voyageResponse.getResponseStatus().getStatus());
  }

  /**
   * Save voyage - negative scenario
   *
   * @throws GenericServiceException
   */
  @Test
  void testSaveVoyageNegativeTestCase() throws GenericServiceException {

    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);

    VoyageReply voyageReply =
        VoyageReply.newBuilder()
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setStatus(SUCCESS)
                    .setMessage(INVALID_LOADABLE_QUANTITY)
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
            .setVoyageId(1)
            .build();

    Mockito.when(
            spy.saveVoyage(ArgumentMatchers.any(Voyage.class), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();

    Mockito.when(spy.saveVoyage(ArgumentMatchers.any(VoyageRequest.class))).thenReturn(voyageReply);
    Voyage voyage = new Voyage();
    voyage.setVoyageNo(VOYAGE);
    voyage.setCaptainId((long) 1);
    voyage.setChiefOfficerId((long) 1);

    VoyageResponse voyageResponse = spy.saveVoyage(voyage, (long) 1, (long) 1, "123");

    Assert.assertEquals(
        String.valueOf(HttpStatus.BAD_REQUEST.value()),
        voyageResponse.getResponseStatus().getStatus());
  }
  /**
   * Save loadable quantity - positive scenario
   *
   * @throws GenericServiceException
   */
  @Test
  void testSaveLoadableQuantityPositiveTestCase() throws GenericServiceException {

    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);

    LoadableQuantityReply loadableQuantityReply =
        LoadableQuantityReply.newBuilder()
            .setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
            .setLoadableQuantityId(1)
            .build();

    Mockito.when(
            spy.saveLoadableQuantity(
                ArgumentMatchers.any(LoadableQuantity.class), anyLong(), anyString()))
        .thenCallRealMethod();

    Mockito.when(spy.saveLoadableQuantity(ArgumentMatchers.any(LoadableQuantityRequest.class)))
        .thenReturn(loadableQuantityReply);
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

    LoadableQuantityResponse loadableQuantityResponse =
        spy.saveLoadableQuantity(loadableQuantity, (long) 1, "123");

    Assert.assertEquals(
        String.valueOf(HttpStatus.OK.value()),
        loadableQuantityResponse.getResponseStatus().getStatus());
  }

  /**
   * Save loadable quantity - negative scenario
   *
   * @throws GenericServiceException
   */
  @Test
  void testSaveLoadableQuantityNegativeTestCase() throws GenericServiceException {

    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);

    LoadableQuantityReply loadableQuantityReply =
        LoadableQuantityReply.newBuilder()
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setStatus(SUCCESS)
                    .setMessage(INVALID_LOADABLE_QUANTITY)
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
            .build();

    Mockito.when(
            spy.saveLoadableQuantity(
                ArgumentMatchers.any(LoadableQuantity.class), anyLong(), anyString()))
        .thenCallRealMethod();

    Mockito.when(spy.saveLoadableQuantity(ArgumentMatchers.any(LoadableQuantityRequest.class)))
        .thenReturn(loadableQuantityReply);
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

    LoadableQuantityResponse loadableQuantityResponse =
        spy.saveLoadableQuantity(loadableQuantity, (long) 1, "123");

    Assert.assertEquals(
        String.valueOf(HttpStatus.BAD_REQUEST.value()),
        loadableQuantityResponse.getResponseStatus().getStatus());
  }

  /**
   * positive test case for get loadable study
   *
   * @throws GenericServiceException void
   */
  @Test
  void testGetLoadableQuantityPositiveTestCase() throws GenericServiceException {

    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);

    LoadableQuantityRequest loadableQuantityRequest =
        LoadableQuantityRequest.newBuilder()
            .setConstant(LOADABLE_QUANTITY_DUMMY)
            .setDisplacmentDraftRestriction(LOADABLE_QUANTITY_DUMMY)
            .setDistanceFromLastPort(LOADABLE_QUANTITY_DUMMY)
            .setDwt(LOADABLE_QUANTITY_DUMMY)
            .setEstDOOnBoard(LOADABLE_QUANTITY_DUMMY)
            .setEstFOOnBoard(LOADABLE_QUANTITY_DUMMY)
            .setEstFreshWaterOnBoard(LOADABLE_QUANTITY_DUMMY)
            .setEstSagging(LOADABLE_QUANTITY_DUMMY)
            .setEstSeaDensity(LOADABLE_QUANTITY_DUMMY)
            .setEstTotalFOConsumption(LOADABLE_QUANTITY_DUMMY)
            .setFoConsumptionPerDay(LOADABLE_QUANTITY_DUMMY)
            .setLimitingDraft(LOADABLE_QUANTITY_DUMMY)
            .setOtherIfAny(LOADABLE_QUANTITY_DUMMY)
            .setSaggingDeduction(LOADABLE_QUANTITY_DUMMY)
            .setSgCorrection(LOADABLE_QUANTITY_DUMMY)
            .setTotalQuantity(LOADABLE_QUANTITY_DUMMY)
            .setTpc(LOADABLE_QUANTITY_DUMMY)
            .setVesselAverageSpeed(LOADABLE_QUANTITY_DUMMY)
            .setVesselLightWeight(LOADABLE_QUANTITY_DUMMY)
            .build();

    Mockito.when(spy.getLoadableQuantity((anyLong()), anyString())).thenCallRealMethod();

    com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse.newBuilder()
            .setLoadableQuantityRequest(loadableQuantityRequest)
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setMessage(SUCCESS)
                    .setStatus(SUCCESS)
                    .setCode(String.valueOf(HttpStatus.OK.value()))
                    .build());

    LoadableQuantityReply loadableQuantityReply =
        LoadableQuantityReply.newBuilder().setLoadableQuantityId(1).build();
    Mockito.when(spy.getLoadableQuantityResponse(loadableQuantityReply))
        .thenReturn(replyBuilder.build());

    LoadableQuantityResponse loadableQuantityResponse = spy.getLoadableQuantity((long) 1, "123");

    Assert.assertEquals(
        String.valueOf(HttpStatus.OK.value()),
        loadableQuantityResponse.getResponseStatus().getStatus());
  }

  @Test
  public void negativeTestCaseForLoadableQuantity() throws GenericServiceException {

    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);

    Mockito.when(spy.getLoadableQuantity((anyLong()), anyString())).thenCallRealMethod();

    LoadableQuantityRequest loadableQuantityRequest =
        LoadableQuantityRequest.newBuilder().setVesselLightWeight(LOADABLE_QUANTITY_DUMMY).build();

    com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse.newBuilder()
            .setLoadableQuantityRequest(loadableQuantityRequest)
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setStatus(SUCCESS)
                    .setMessage(INVALID_LOADABLE_QUANTITY)
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
            .build();

    LoadableQuantityReply loadableQuantityReply =
        LoadableQuantityReply.newBuilder().setLoadableQuantityId(1).build();
    Mockito.when(spy.getLoadableQuantityResponse(loadableQuantityReply)).thenReturn(replyBuilder);

    LoadableQuantityResponse loadableQuantityResponse = spy.getLoadableQuantity((long) 1, "123");

    Assert.assertEquals("400", loadableQuantityResponse.getResponseStatus().getStatus());
  }
}
