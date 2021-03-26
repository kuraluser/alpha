/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static com.cpdss.gateway.TestUtils.createDummyObject;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.AlgoReply;
import com.cpdss.common.generated.LoadableStudy.AlgoRequest;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusReply;
import com.cpdss.common.generated.LoadableStudy.CargoDetails;
import com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply;
import com.cpdss.common.generated.LoadableStudy.DischargingPortDetail;
import com.cpdss.common.generated.LoadableStudy.LoadablePattern;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCargoDetails;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanComments;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.LoadingPortDetail;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.Operation;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.LoadableStudy.SaveCommentReply;
import com.cpdss.common.generated.LoadableStudy.SaveCommentRequest;
import com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest;
import com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply;
import com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalBallastRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalCargoRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalOhqRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest;
import com.cpdss.common.generated.LoadableStudy.TankDetail;
import com.cpdss.common.generated.LoadableStudy.TankList;
import com.cpdss.common.generated.LoadableStudy.VoyageDetail;
import com.cpdss.common.generated.LoadableStudy.VoyageListReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.AlgoPatternResponse;
import com.cpdss.gateway.domain.AlgoStatusRequest;
import com.cpdss.gateway.domain.AlgoStatusResponse;
import com.cpdss.gateway.domain.Comment;
import com.cpdss.gateway.domain.CommonResponse;
import com.cpdss.gateway.domain.DischargingPortRequest;
import com.cpdss.gateway.domain.LoadOnTopRequest;
import com.cpdss.gateway.domain.LoadablePatternDetailsResponse;
import com.cpdss.gateway.domain.LoadablePatternResponse;
import com.cpdss.gateway.domain.LoadablePlanDetails;
import com.cpdss.gateway.domain.LoadablePlanDetailsResponse;
import com.cpdss.gateway.domain.LoadablePlanPortWiseDetails;
import com.cpdss.gateway.domain.LoadablePlanRequest;
import com.cpdss.gateway.domain.LoadableQuantity;
import com.cpdss.gateway.domain.LoadableQuantityResponse;
import com.cpdss.gateway.domain.LoadableStudy;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.LoadicatorPatternDetailsResults;
import com.cpdss.gateway.domain.LoadicatorResultDetails;
import com.cpdss.gateway.domain.LoadicatorResultsRequest;
import com.cpdss.gateway.domain.OnBoardQuantity;
import com.cpdss.gateway.domain.OnBoardQuantityResponse;
import com.cpdss.gateway.domain.OnHandQuantity;
import com.cpdss.gateway.domain.OnHandQuantityResponse;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.PortRotationResponse;
import com.cpdss.gateway.domain.SaveCommentResponse;
import com.cpdss.gateway.domain.StabilityParameter;
import com.cpdss.gateway.domain.SynopticalRecord;
import com.cpdss.gateway.domain.SynopticalTableResponse;
import com.cpdss.gateway.domain.Voyage;
import com.cpdss.gateway.domain.VoyageActionRequest;
import com.cpdss.gateway.domain.VoyageActionResponse;
import com.cpdss.gateway.domain.VoyageResponse;
import com.cpdss.gateway.entity.RoleUserMapping;
import com.cpdss.gateway.entity.Roles;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.UsersRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
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
  private static final Long ID_TEST_VALUE = 1L;
  private static final String STRING_TEST_VALUE = "1";

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String CHARTERER = "charterer";
  private static final String SUB_CHARTERER = "sub-chartere";
  private static final String DRAFT_MARK = "1000";
  private static final Long LOAD_LINE_ID = 1L;
  private static final String DRAFT_RESTRICTION = "1000";
  private static final String MAX_TEMP_EXPECTED = "100";

  private static final String VOYAGE = "VOYAGE";
  private static final String LOADICATOR_DATA = "LOADICATOR";
  private static final String LOADABLE_QUANTITY_DUMMY = "100";
  private static final String INVALID_LOADABLE_QUANTITY = "INVALID_LOADABLE_QUANTITY";
  private static final BigDecimal TEST_BIGDECIMAL_VALUE = new BigDecimal(100);

  private static final Long FRESH_WATER_TANK_CATEGORY_ID = 3L;
  private static final Long FUEL_OIL_TANK_CATEGORY_ID = 5L;
  private static final Long DIESEL_OIL_TANK_CATEGORY_ID = 6L;
  private static final Long LUBRICATING_OIL_TANK_CATEGORY_ID = 14L;
  private static final Long LUBRICANT_OIL_TANK_CATEGORY_ID = 19L;
  private static final Long FUEL_VOID_TANK_CATEGORY_ID = 22L;
  private static final Long FRESH_WATER_VOID_TANK_CATEGORY_ID = 23L;

  private static final List<Long> OHQ_TANK_CATEGORIES =
      Arrays.asList(
          FRESH_WATER_TANK_CATEGORY_ID,
          FUEL_OIL_TANK_CATEGORY_ID,
          DIESEL_OIL_TANK_CATEGORY_ID,
          LUBRICATING_OIL_TANK_CATEGORY_ID,
          LUBRICANT_OIL_TANK_CATEGORY_ID,
          FUEL_VOID_TANK_CATEGORY_ID,
          FRESH_WATER_VOID_TANK_CATEGORY_ID);

  @MockBean private UsersRepository usersRepository;

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
            .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
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
            .setMaxAirTemperature(MAX_TEMP_EXPECTED)
            .setMaxWaterTemperature(MAX_TEMP_EXPECTED)
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
                String.valueOf(HttpStatusCode.OK.value()),
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
                ResponseStatus.newBuilder()
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
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
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
                ResponseStatus.newBuilder().setMessage("Success").setStatus(SUCCESS).build());
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
                String.valueOf(HttpStatusCode.OK.value()),
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
                ResponseStatus.newBuilder()
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
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
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
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
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
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testGetLoadableStudyPortList() throws GenericServiceException {
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
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"),
        () -> assertNotNull(response.getPortList()),
        () -> assertNotNull(response.getOperations()));
  }

  @Test
  void testGetLoadableStudyPortListFailureScenario() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getLoadableStudyPortRotationList(
                anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    PortRotationReply.Builder reply = this.generatePortRotationReply(false);
    reply.setResponseStatus(
        ResponseStatus.newBuilder()
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
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testGetLoadableStudyPortListWithEmptyData() throws GenericServiceException {
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
                String.valueOf(HttpStatusCode.OK.value()),
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
              builder.setBerthId(Long.valueOf(i));
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
    replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
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
    request.setMaxAirTemperature(new BigDecimal(MAX_TEMP_EXPECTED));
    request.setMaxWaterTemperature(new BigDecimal(MAX_TEMP_EXPECTED));
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
                    .setCode(String.valueOf(HttpStatusCode.OK.value())))
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
    voyage.setStartDate("30-12-2020 01:01");
    voyage.setEndDate("31-12-2020 23:58");
    VoyageResponse voyageResponse = spy.saveVoyage(voyage, (long) 1, (long) 1, "123");

    Assert.assertEquals(
        String.valueOf(HttpStatusCode.OK.value()), voyageResponse.getResponseStatus().getStatus());
  }

  /**
   * Save voyage - negative scenario
   *
   * @throws GenericServiceException
   */
  @Test
  void testSaveVoyageNegativeTestCase() throws GenericServiceException {

    VoyageReply voyageReply =
        VoyageReply.newBuilder()
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setStatus(FAILED)
                    .setMessage(INVALID_LOADABLE_QUANTITY)
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
            .setVoyageId(1)
            .build();

    Mockito.when(
            loadableStudyService.saveVoyage(
                ArgumentMatchers.any(Voyage.class), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();

    Mockito.when(loadableStudyService.saveVoyage(ArgumentMatchers.any(VoyageRequest.class)))
        .thenReturn(voyageReply);
    Voyage voyage = new Voyage();
    voyage.setVoyageNo(VOYAGE);
    voyage.setCaptainId((long) 1);
    voyage.setChiefOfficerId((long) 1);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveVoyage(
                    voyage, (long) 1, (long) 1, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
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
    loadableQuantity.setOtherIfAny(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setSaggingDeduction(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setSgCorrection(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setTotalQuantity(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setTpc(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setVesselAverageSpeed(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setVesselLightWeight(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setSubTotal(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setFoConInSZ(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setPortId(1);
    loadableQuantity.setBoilerWaterOnBoard(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setBallast(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setRunningHours(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setRunningDays(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setFoConsumptionPerDay(LOADABLE_QUANTITY_DUMMY);

    LoadableQuantityResponse loadableQuantityResponse =
        spy.saveLoadableQuantity(loadableQuantity, (long) 1, CORRELATION_ID_HEADER_VALUE);

    Assert.assertEquals(
        String.valueOf(HttpStatusCode.OK.value()),
        loadableQuantityResponse.getResponseStatus().getStatus());
  }

  /**
   * Save loadable quantity - negative scenario
   *
   * @throws GenericServiceException
   */
  @Test
  void testSaveLoadableQuantityNegativeTestCase() throws GenericServiceException {

    LoadableQuantityReply loadableQuantityReply =
        LoadableQuantityReply.newBuilder()
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setStatus(FAILED)
                    .setMessage(INVALID_LOADABLE_QUANTITY)
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
            .build();

    Mockito.when(
            loadableStudyService.saveLoadableQuantity(
                ArgumentMatchers.any(LoadableQuantity.class), anyLong(), anyString()))
        .thenCallRealMethod();

    Mockito.when(
            loadableStudyService.saveLoadableQuantity(
                ArgumentMatchers.any(LoadableQuantityRequest.class)))
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
    loadableQuantity.setOtherIfAny(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setSaggingDeduction(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setSgCorrection(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setTotalQuantity(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setTpc(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setVesselAverageSpeed(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setVesselLightWeight(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setSubTotal(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setFoConInSZ(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setPortId(1);
    loadableQuantity.setBoilerWaterOnBoard(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setBallast(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setRunningHours(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setRunningDays(LOADABLE_QUANTITY_DUMMY);
    loadableQuantity.setFoConsumptionPerDay(LOADABLE_QUANTITY_DUMMY);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveLoadableQuantity(
                    loadableQuantity, (long) 1, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /**
   * positive test case for get loadable Quantity
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
            .setOtherIfAny(LOADABLE_QUANTITY_DUMMY)
            .setSaggingDeduction(LOADABLE_QUANTITY_DUMMY)
            .setSgCorrection(LOADABLE_QUANTITY_DUMMY)
            .setTotalQuantity(LOADABLE_QUANTITY_DUMMY)
            .setTpc(LOADABLE_QUANTITY_DUMMY)
            .setVesselAverageSpeed(LOADABLE_QUANTITY_DUMMY)
            .setVesselLightWeight(LOADABLE_QUANTITY_DUMMY)
            .setSubTotal(LOADABLE_QUANTITY_DUMMY)
            .setFoConInSZ(LOADABLE_QUANTITY_DUMMY)
            .setPortId(1)
            .setBoilerWaterOnBoard(LOADABLE_QUANTITY_DUMMY)
            .setBallast(LOADABLE_QUANTITY_DUMMY)
            .setRunningHours(LOADABLE_QUANTITY_DUMMY)
            .setRunningDays(LOADABLE_QUANTITY_DUMMY)
            .setFoConsumptionPerDay(LOADABLE_QUANTITY_DUMMY)
            .build();

    Mockito.when(spy.getLoadableQuantity((anyLong()), anyString())).thenCallRealMethod();

    com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse.newBuilder()
            .setLoadableQuantityRequest(loadableQuantityRequest)
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setMessage(SUCCESS)
                    .setStatus(SUCCESS)
                    .setCode(String.valueOf(HttpStatusCode.OK.value()))
                    .build());

    LoadableQuantityReply loadableQuantityReply =
        LoadableQuantityReply.newBuilder().setLoadableStudyId(1).build();
    Mockito.when(spy.getLoadableQuantityResponse(loadableQuantityReply))
        .thenReturn(replyBuilder.build());
    spy.getLoadableQuantity((long) 1, CORRELATION_ID_HEADER_VALUE);
    LoadableQuantityResponse loadableQuantityResponse =
        spy.getLoadableQuantity((long) 1, CORRELATION_ID_HEADER_VALUE);

    Assert.assertEquals(
        String.valueOf(HttpStatusCode.OK.value()),
        loadableQuantityResponse.getResponseStatus().getStatus());
  }

  /**
   * negative test case for get loadable Quantity
   *
   * @throws GenericServiceException void
   */
  @Test
  public void negativeTestCaseForLoadableQuantity() throws GenericServiceException {

    Mockito.when(loadableStudyService.getLoadableQuantity((anyLong()), anyString()))
        .thenCallRealMethod();

    LoadableQuantityRequest loadableQuantityRequest =
        LoadableQuantityRequest.newBuilder().setVesselLightWeight(LOADABLE_QUANTITY_DUMMY).build();

    com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse.newBuilder()
            .setLoadableQuantityRequest(loadableQuantityRequest)
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setStatus(FAILED)
                    .setMessage(INVALID_LOADABLE_QUANTITY)
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
            .build();

    LoadableQuantityReply loadableQuantityReply =
        LoadableQuantityReply.newBuilder().setLoadableStudyId(1).build();
    Mockito.when(loadableStudyService.getLoadableQuantityResponse(loadableQuantityReply))
        .thenReturn(replyBuilder);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.getLoadableQuantity(
                    (long) 1, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testGetVoyageListByVessel() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.getVoyageListByVessel(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.getVoyageListByVessel(any(VoyageRequest.class)))
        .thenReturn(this.generateVoyageListResponse().build());
    VoyageResponse response =
        this.loadableStudyService.getVoyageListByVessel(1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"),
        () -> assertNotNull(response.getVoyages()));
  }

  @Test
  void testGetVoyageListByVesselFailureScenario() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.getVoyageListByVessel(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.getVoyageListByVessel(any(VoyageRequest.class)))
        .thenReturn(
            this.generateVoyageListResponse()
                .setResponseStatus(
                    StatusReply.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
                .build());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> this.loadableStudyService.getVoyageListByVessel(1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private VoyageListReply.Builder generateVoyageListResponse() {
    VoyageListReply.Builder builder = VoyageListReply.newBuilder();
    VoyageDetail detail = VoyageDetail.newBuilder().setId(1L).setVoyageNumber(VOYAGE).build();
    builder.addVoyages(detail);
    builder.setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS));
    return builder;
  }

  @Test
  void testSavePortRotation() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.savePortRotation(any(PortRotation.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.savePortRotation(any(PortRotationDetail.class)))
        .thenReturn(this.generatePortRotationReply(false).build());
    PortRotation request = this.createPortRotationRequest();
    request.setOperationId(null);
    PortRotationResponse response =
        this.loadableStudyService.savePortRotation(request, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"),
        () -> assertNotNull(response.getId()));
  }

  @Test
  void testSavePortRotationGrpcFailure() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.savePortRotation(any(PortRotation.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.savePortRotation(any(PortRotationDetail.class)))
        .thenReturn(
            this.generatePortRotationReply(false)
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
                .build());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.savePortRotation(
                    this.createPortRotationRequest(), CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private PortRotation createPortRotationRequest() {
    PortRotation request = new PortRotation();
    request.setId(1L);
    request.setLoadableStudyId(1L);
    request.setDistanceBetweenPorts(TEST_BIGDECIMAL_VALUE);
    request.setEta(LocalDateTime.now().toString());
    request.setEtd(request.getEta());
    request.setLayCanFrom(LocalDate.now().toString());
    request.setLayCanTo(request.getLayCanFrom());
    request.setLoadableStudyId(1L);
    request.setOperationId(3L);
    return request;
  }

  @Test
  void testSaveDischargingPorts() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.saveDischargingPorts(
                any(DischargingPortRequest.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.saveDischargingPorts(any(PortRotationRequest.class)))
        .thenReturn(this.generatePortRotationReply(false).build());
    PortRotationResponse response =
        this.loadableStudyService.saveDischargingPorts(
            this.createDischargingPortRequest(), CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"));
  }

  @Test
  void testSaveDischargingPortsGrpcFailure() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.saveDischargingPorts(
                any(DischargingPortRequest.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.saveDischargingPorts(any(PortRotationRequest.class)))
        .thenReturn(
            this.generatePortRotationReply(false)
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveDischargingPorts(
                    this.createDischargingPortRequest(), CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private DischargingPortRequest createDischargingPortRequest() {
    DischargingPortRequest request = new DischargingPortRequest();
    List<Long> ids = new ArrayList<>();
    ids.add(1L);
    ids.add(2L);
    request.setPortIds(ids);
    request.setLoadableStudyId(1L);
    return request;
  }

  @Test
  void testDeleteLoadableStudy() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.deleteLoadableStudy(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.deleteLoadableStudy(any(LoadableStudyRequest.class)))
        .thenReturn(
            LoadableStudyReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    LoadableStudyResponse response =
        this.loadableStudyService.deleteLoadableStudy(1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"));
  }

  @Test
  void testDeleteLoadableStudyGrpcFailure() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.deleteLoadableStudy(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.deleteLoadableStudy(any(LoadableStudyRequest.class)))
        .thenReturn(
            LoadableStudyReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> this.loadableStudyService.deleteLoadableStudy(1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testDeletePortRotation() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.deletePortRotation(anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.deletePortRotation(any(PortRotationRequest.class)))
        .thenReturn(
            PortRotationReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    PortRotationResponse response =
        this.loadableStudyService.deletePortRotation(1L, 1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"));
  }

  @Test
  void testDeletePortRotationGrpcFailure() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.deletePortRotation(anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.deletePortRotation(any(PortRotationRequest.class)))
        .thenReturn(
            PortRotationReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.deletePortRotation(1L, 1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testGetOnHandQuantity() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getOnHandQuantity(
                anyLong(), anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.getOnHandQuantity(any(OnHandQuantityRequest.class)))
        .thenReturn(
            OnHandQuantityReply.newBuilder()
                .addAllOnHandQuantity(this.createOnhandQuantityDetail())
                .addRearTanks(
                    TankList.newBuilder().addAllVesselTank(this.createTankDetail()).build())
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    OnHandQuantityResponse response =
        this.loadableStudyService.getOnHandQuantity(1L, 1L, 1L, 1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"));
  }

  private List<OnHandQuantityDetail> createOnhandQuantityDetail() {
    List<OnHandQuantityDetail> list = new ArrayList<>();
    IntStream.range(1, 5)
        .forEach(
            i -> {
              OnHandQuantityDetail detail =
                  OnHandQuantityDetail.newBuilder()
                      .setTankId(Long.valueOf(i))
                      .setId(Long.valueOf(i))
                      .setTankName("tank-" + i)
                      .setFuelType("fuel-" + i)
                      .setFuelTypeId(Long.valueOf(i))
                      .setArrivalQuantity(String.valueOf(i))
                      .setDepartureQuantity(String.valueOf(i))
                      .setArrivalVolume(String.valueOf(i))
                      .setDepartureVolume(String.valueOf(i))
                      .build();
              list.add(detail);
            });
    return list;
  }

  private List<TankDetail> createTankDetail() {
    List<TankDetail> list = new ArrayList<>();
    IntStream.range(1, 5)
        .forEach(
            i -> {
              TankDetail detail =
                  TankDetail.newBuilder()
                      .setTankId(Long.valueOf(i))
                      .setTankName("tank-" + i)
                      .setTankCategoryId(Long.valueOf(i))
                      .setTankCategoryName("cat-" + i)
                      .setFrameNumberFrom(String.valueOf(i))
                      .setFrameNumberTo(String.valueOf(i))
                      .setShortName("short-" + i)
                      .setFillCapacityCubm(String.valueOf(i))
                      .setDensity(String.valueOf(i))
                      .setIsSlopTank(false)
                      .setHeightFrom(String.valueOf(i))
                      .setHeightTo(String.valueOf(i))
                      .setTankOrder(i)
                      .setTankGroup(i)
                      .build();
              list.add(detail);
            });
    return list;
  }

  @Test
  void testGetOnHandQuantityGrpcFailure() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getOnHandQuantity(
                anyLong(), anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.getOnHandQuantity(any(OnHandQuantityRequest.class)))
        .thenReturn(
            OnHandQuantityReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.getOnHandQuantity(
                    1L, 1L, 1L, 1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testSaveOnHandQuantity() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.saveOnHandQuantity(any(OnHandQuantity.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.saveOnHandQuantity(any(OnHandQuantityDetail.class)))
        .thenReturn(
            OnHandQuantityReply.newBuilder()
                .addAllOnHandQuantity(this.createOnhandQuantityDetail())
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    OnHandQuantityResponse response =
        this.loadableStudyService.saveOnHandQuantity(
            this.createOnHandQuantityRequest(), CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"));
  }

  @Test
  void testSaveOnHandQuantityGrpcFailure() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.saveOnHandQuantity(any(OnHandQuantity.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.saveOnHandQuantity(any(OnHandQuantityDetail.class)))
        .thenReturn(
            OnHandQuantityReply.newBuilder()
                .addAllOnHandQuantity(this.createOnhandQuantityDetail())
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveOnHandQuantity(
                    this.createOnHandQuantityRequest(), CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private OnHandQuantity createOnHandQuantityRequest() {
    OnHandQuantity request = new OnHandQuantity();
    request.setArrivalVolume(TEST_BIGDECIMAL_VALUE);
    request.setArrivalQuantity(TEST_BIGDECIMAL_VALUE);
    request.setDepartureQuantity(TEST_BIGDECIMAL_VALUE);
    request.setDepartureVolume(TEST_BIGDECIMAL_VALUE);
    request.setFuelTypeId(1L);
    request.setTankId(1L);
    request.setLoadableStudyId(1L);
    request.setPortId(1L);
    request.setId(0L);
    return request;
  }

  /** @throws GenericServiceException void */
  @Test
  void testGenerateLoadablePatterns() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.generateLoadablePatterns(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.generateLoadablePatterns(any(AlgoRequest.class)))
        .thenReturn(
            AlgoReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    AlgoPatternResponse response =
        this.loadableStudyService.generateLoadablePatterns(1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "response valid"));
  }

  /** @throws GenericServiceException void */
  @Test
  void testGenerateLoadablePatternsGrpcFailure() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.generateLoadablePatterns(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.generateLoadablePatterns(any(AlgoRequest.class)))
        .thenReturn(
            AlgoReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.generateLoadablePatterns(
                    1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /** @throws GenericServiceException void */
  @Test
  void testGenerateLoadablePatternsALGOFailure() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.generateLoadablePatterns(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.generateLoadablePatterns(any(AlgoRequest.class)))
        .thenReturn(
            AlgoReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_CPDSS_ALGO_ISSUE)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.generateLoadablePatterns(
                    1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () ->
            assertEquals(CommonErrorCodes.E_CPDSS_ALGO_ISSUE, ex.getCode(), "Error calling ALGO"));
  }

  /** @throws GenericServiceException void */
  @Test
  void testGetLoadablePatterns() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.getLoadablePatterns(anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.getLoadablePattern(any(LoadablePatternRequest.class)))
        .thenReturn(
            LoadablePatternReply.newBuilder()
                .addLoadablePattern(createLoadablePatternBuild())
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    LoadablePatternResponse response =
        this.loadableStudyService.getLoadablePatterns(1L, anyLong(), CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "response valid"));
  }

  /** @return LoadablePattern */
  private LoadablePattern createLoadablePatternBuild() {
    com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder loadablePatternBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();

    IntStream.range(1, 5)
        .forEach(
            i -> {
              loadablePatternBuilder.setLoadablePatternId(Long.valueOf(i));
              IntStream.range(1, 5)
                  .forEach(
                      j -> {
                        LoadablePatternCargoDetails.Builder loadablePatternCargoDetailsBuilder =
                            LoadablePatternCargoDetails.newBuilder();
                        loadablePatternCargoDetailsBuilder.setPriority(Long.valueOf(j));
                        loadablePatternBuilder.addLoadablePlanStowageDetails(
                            buildLoadablePlanStowageDetails());
                        loadablePatternBuilder.addLoadablePatternCargoDetails(
                            loadablePatternCargoDetailsBuilder);
                      });
            });

    return loadablePatternBuilder.build();
  }

  /** @throws GenericServiceException void */
  @Test
  void testGetLoadablePatternsGrpcFailure() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.getLoadablePatterns(anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.getLoadablePattern(any(LoadablePatternRequest.class)))
        .thenReturn(
            LoadablePatternReply.newBuilder()
                .addLoadablePattern(createLoadablePatternBuild())
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.getLoadablePatterns(
                    1L, anyLong(), CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /** @throws GenericServiceException void */
  @Test
  void testSaveLoadicatorResult() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.saveLoadicatorResult(
                any(LoadicatorResultsRequest.class), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.saveLoadicatorResult(
                any(
                    com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest.Builder
                        .class)))
        .thenReturn(
            AlgoReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    AlgoPatternResponse response =
        this.loadableStudyService.saveLoadicatorResult(
            createLoadicatorResultsRequest(), 1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "response valid"));
  }

  /** @throws GenericServiceException void */
  @Test
  void testSaveLoadicatorResultGrpcFailure() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.saveLoadicatorResult(
                any(LoadicatorResultsRequest.class), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.saveLoadicatorResult(
                any(
                    com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest.Builder
                        .class)))
        .thenReturn(
            AlgoReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveLoadicatorResult(
                    createLoadicatorResultsRequest(), 1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /** @return LoadicatorResultsRequest */
  private LoadicatorResultsRequest createLoadicatorResultsRequest() {
    LoadicatorResultsRequest request = new LoadicatorResultsRequest();
    request.setProcessId("");
    request.setLoadicatorResultsPatternWise(createLoadicatorResultsPatternWise());
    return request;
  }

  /** @return List<LoadicatorPatternDetailsResults> */
  private List<LoadicatorPatternDetailsResults> createLoadicatorResultsPatternWise() {
    List<LoadicatorPatternDetailsResults> patternDetailsResults =
        new ArrayList<LoadicatorPatternDetailsResults>();
    LoadicatorPatternDetailsResults detailsResults = new LoadicatorPatternDetailsResults();
    patternDetailsResults.add(detailsResults);
    detailsResults.setLoadablePatternId(1L);
    detailsResults.setLoadicatorResultDetails(createLodicatorResultDetails());
    return patternDetailsResults;
  }

  /** @return List<LodicatorResultDetails> */
  private List<LoadicatorResultDetails> createLodicatorResultDetails() {
    List<LoadicatorResultDetails> details = new ArrayList<LoadicatorResultDetails>();
    LoadicatorResultDetails lodicatorResultDetails = new LoadicatorResultDetails();
    lodicatorResultDetails.setBlindSector(LOADICATOR_DATA);
    lodicatorResultDetails.setCalculatedDraftAftPlanned(LOADICATOR_DATA);
    lodicatorResultDetails.setCalculatedDraftFwdPlanned(LOADICATOR_DATA);
    lodicatorResultDetails.setCalculatedDraftMidPlanned(LOADICATOR_DATA);
    lodicatorResultDetails.setCalculatedTrimPlanned(LOADICATOR_DATA);
    lodicatorResultDetails.setHog(LOADICATOR_DATA);
    lodicatorResultDetails.setList(LOADICATOR_DATA);
    lodicatorResultDetails.setPortId(1L);
    lodicatorResultDetails.setOperationId(1L);
    details.add(lodicatorResultDetails);
    return details;
  }

  /** @throws GenericServiceException void */
  @Test
  void testSaveAlgoLoadableStudyStatus() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.saveAlgoLoadableStudyStatus(
                any(AlgoStatusRequest.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.saveAlgoLoadableStudyStatus(
                any(com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest.class)))
        .thenReturn(
            AlgoStatusReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    AlgoStatusRequest request = new AlgoStatusRequest();
    request.setLoadableStudyStatusId(1L);
    request.setProcessId("ID");
    AlgoStatusResponse response =
        this.loadableStudyService.saveAlgoLoadableStudyStatus(request, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "response valid"));
  }

  /** @throws GenericServiceException void */
  @Test
  void testSaveAlgoLoadableStudyStatusGrpcFailure() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.saveAlgoLoadableStudyStatus(
                any(AlgoStatusRequest.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.saveAlgoLoadableStudyStatus(
                any(com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest.class)))
        .thenReturn(
            AlgoStatusReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    AlgoStatusRequest request = new AlgoStatusRequest();
    request.setLoadableStudyStatusId(1L);
    request.setProcessId("ID");
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveAlgoLoadableStudyStatus(
                    request, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /** @throws GenericServiceException void */
  @Test
  void testConfirmPlan() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.confirmPlan(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.confirmPlan(
                any(com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.Builder.class)))
        .thenReturn(
            ConfirmPlanReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    CommonResponse response =
        this.loadableStudyService.confirmPlan(1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "response valid"));
  }

  /** @throws GenericServiceException void */
  @Test
  void testConfirmPlanGrpcFailure() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.confirmPlan(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.confirmPlan(
                any(com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.Builder.class)))
        .thenReturn(
            ConfirmPlanReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> this.loadableStudyService.confirmPlan(1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testSaveLoadablePatternDetailsGrpcFailure() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.saveLoadablePatterns(
                any(LoadablePlanRequest.class), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.saveJson(anyLong(), anyLong(), anyString()))
        .thenReturn(StatusReply.newBuilder().setStatus(SUCCESS).setCode(SUCCESS).build());

    Mockito.when(
            this.loadableStudyService.saveLoadablePatterns(
                any(
                    com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.Builder
                        .class)))
        .thenReturn(
            AlgoReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveLoadablePatterns(
                    createAlgoPatternResponse(), 1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /** @throws GenericServiceException void */
  @Test
  void testSaveLoadablePatternDetails() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.saveJson(anyLong(), anyLong(), anyString()))
        .thenReturn(StatusReply.newBuilder().setStatus(SUCCESS).setCode(SUCCESS).build());
    Mockito.when(
            this.loadableStudyService.saveLoadablePatterns(
                any(LoadablePlanRequest.class), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.saveLoadablePatterns(
                any(
                    com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.Builder
                        .class)))
        .thenReturn(
            AlgoReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());

    AlgoPatternResponse response =
        this.loadableStudyService.saveLoadablePatterns(
            createAlgoPatternResponse(), 1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "response valid"));
  }

  /** @return LoadablePlanRequest */
  private LoadablePlanRequest createAlgoPatternResponse() {
    LoadablePlanRequest loadablePlanRequest = new LoadablePlanRequest();
    loadablePlanRequest.setProcessId("ID");
    loadablePlanRequest.setLoadablePlanDetails(createLoadablePlanDetails());
    return loadablePlanRequest;
  }

  /** @return List<LoadablePlanDetails> */
  private List<LoadablePlanDetails> createLoadablePlanDetails() {
    List<LoadablePlanDetails> loadablePlanDetails = new ArrayList<LoadablePlanDetails>();
    LoadablePlanDetails planDetails = new LoadablePlanDetails();
    planDetails.setLoadablePlanPortWiseDetails(createLoadablePlanPortWiseDetails());
    planDetails.setCaseNumber(1);
    planDetails.setStabilityParameters(createStabilityParameters());
    loadablePlanDetails.add(planDetails);
    return loadablePlanDetails;
  }

  /** @return StabilityParameter */
  private StabilityParameter createStabilityParameters() {
    StabilityParameter stabilityParameter = new StabilityParameter();
    return stabilityParameter;
  }

  /** @return List<LoadablePlanPortWiseDetails> */
  private List<LoadablePlanPortWiseDetails> createLoadablePlanPortWiseDetails() {
    List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails =
        new ArrayList<LoadablePlanPortWiseDetails>();
    LoadablePlanPortWiseDetails details = new LoadablePlanPortWiseDetails();
    details.setDepartureCondition(createArrivalDepartureCondition());
    details.setArrivalCondition(createArrivalDepartureCondition());
    details.setPortId(1L);
    loadablePlanPortWiseDetails.add(details);
    return loadablePlanPortWiseDetails;
  }

  /** @return LoadablePlanDetailsResponse */
  private LoadablePlanDetailsResponse createArrivalDepartureCondition() {
    LoadablePlanDetailsResponse loadablePlanDetailsResponse = new LoadablePlanDetailsResponse();
    loadablePlanDetailsResponse.setLoadableQuantityCommingleCargoDetails(
        buildLoadableQuantityCommingleCargoDetail());
    loadablePlanDetailsResponse.setLoadableQuantityCargoDetails(buildLoadableQuantityCargoDetail());
    loadablePlanDetailsResponse.setLoadablePlanStowageDetails(createLoadablePlanStowageDetails());
    loadablePlanDetailsResponse.setLoadablePlanBallastDetails(createLoadablePlanBallastDetails());
    return loadablePlanDetailsResponse;
  }

  /** @return List<com.cpdss.gateway.domain.LoadablePlanBallastDetails> */
  private List<com.cpdss.gateway.domain.LoadablePlanBallastDetails>
      createLoadablePlanBallastDetails() {
    List<com.cpdss.gateway.domain.LoadablePlanBallastDetails> details =
        new ArrayList<com.cpdss.gateway.domain.LoadablePlanBallastDetails>();
    com.cpdss.gateway.domain.LoadablePlanBallastDetails ballastDetails =
        new com.cpdss.gateway.domain.LoadablePlanBallastDetails();
    details.add(ballastDetails);
    return details;
  }

  /** @return List<com.cpdss.gateway.domain.LoadablePlanStowageDetails> */
  private List<com.cpdss.gateway.domain.LoadablePlanStowageDetails>
      createLoadablePlanStowageDetails() {
    List<com.cpdss.gateway.domain.LoadablePlanStowageDetails> details =
        new ArrayList<com.cpdss.gateway.domain.LoadablePlanStowageDetails>();
    com.cpdss.gateway.domain.LoadablePlanStowageDetails stowageDetails =
        new com.cpdss.gateway.domain.LoadablePlanStowageDetails();
    details.add(stowageDetails);
    return details;
  }

  /** @return List<com.cpdss.gateway.domain.LoadableQuantityCargoDetails> */
  private List<com.cpdss.gateway.domain.LoadableQuantityCargoDetails>
      buildLoadableQuantityCargoDetail() {
    List<com.cpdss.gateway.domain.LoadableQuantityCargoDetails> details =
        new ArrayList<com.cpdss.gateway.domain.LoadableQuantityCargoDetails>();
    com.cpdss.gateway.domain.LoadableQuantityCargoDetails cargoDetails =
        new com.cpdss.gateway.domain.LoadableQuantityCargoDetails();
    details.add(cargoDetails);
    return details;
  }

  /** @return List<com.cpdss.gateway.domain.LoadableQuantityCommingleCargoDetails> */
  private List<com.cpdss.gateway.domain.LoadableQuantityCommingleCargoDetails>
      buildLoadableQuantityCommingleCargoDetail() {
    List<com.cpdss.gateway.domain.LoadableQuantityCommingleCargoDetails> details =
        new ArrayList<com.cpdss.gateway.domain.LoadableQuantityCommingleCargoDetails>();
    com.cpdss.gateway.domain.LoadableQuantityCommingleCargoDetails commingleCargoDetails =
        new com.cpdss.gateway.domain.LoadableQuantityCommingleCargoDetails();
    details.add(commingleCargoDetails);
    return details;
  }

  /** @throws GenericServiceException void */
  @Test
  void testGetLoadablePatternDetails() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getLoadablePatternDetails(
                anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.getLoadablePatternDetails(
                any(
                    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.Builder
                        .class)))
        .thenReturn(
            LoadablePlanDetailsReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .addLoadableQuantityCargoDetails(buildLoadableQuantityCargoDetails())
                .addLoadableQuantityCommingleCargoDetails(
                    buildLoadableQuantityCommingleCargoDetails())
                .addLoadablePlanStowageDetails(buildLoadablePlanStowageDetails())
                .addTanks(buildCargoTanks())
                .addLoadablePlanBallastDetails(buildLoadablePlanBallastDetails())
                .addLoadablePlanComments(buildLoadablePlanComments())
                .build());

    Mockito.when(this.loadableStudyService.getSynopticalTable(anyLong(), anyLong(), anyLong()))
        .thenReturn(buildLoadablePlanSynopticalResponse());

    LoadablePlanDetailsResponse response =
        this.loadableStudyService.getLoadablePatternDetails(
            1L, 1L, 1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "response valid"));
  }

  /** @return LoadablePlanComments */
  private LoadablePlanComments buildLoadablePlanComments() {
    LoadablePlanComments.Builder builder = LoadablePlanComments.newBuilder();
    return builder.build();
  }

  /** @throws GenericServiceException void */
  @Test
  void testGetLoadablePatternDetailsSynopticalFail() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getLoadablePatternDetails(
                anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.getLoadablePatternDetails(
                any(
                    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.Builder
                        .class)))
        .thenReturn(
            LoadablePlanDetailsReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .addLoadableQuantityCargoDetails(buildLoadableQuantityCargoDetails())
                .addLoadableQuantityCommingleCargoDetails(
                    buildLoadableQuantityCommingleCargoDetails())
                .addLoadablePlanStowageDetails(buildLoadablePlanStowageDetails())
                .addTanks(buildCargoTanks())
                .addLoadablePlanBallastDetails(buildLoadablePlanBallastDetails())
                .build());

    Mockito.when(this.loadableStudyService.getSynopticalTable(anyLong(), anyLong(), anyLong()))
        .thenReturn(buildLoadablePlanSynopticalFailResponse());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.getLoadablePatternDetails(
                    1L, 1L, 1L, CORRELATION_ID_HEADER_VALUE));

    assertAll(
        () ->
            assertEquals(
                HttpStatusCode.INTERNAL_SERVER_ERROR, ex.getStatus(), "Invternal server error"));
  }

  /** @return SynopticalTableResponse */
  private SynopticalTableResponse buildLoadablePlanSynopticalFailResponse() {
    SynopticalTableResponse synopticalResponse = new SynopticalTableResponse();
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    synopticalResponse.setResponseStatus(commonSuccessResponse);
    return synopticalResponse;
  }

  /** @return SynopticalTableResponse */
  private SynopticalTableResponse buildLoadablePlanSynopticalResponse() {
    SynopticalTableResponse synopticalResponse = new SynopticalTableResponse();
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    synopticalResponse.setResponseStatus(commonSuccessResponse);
    List<SynopticalRecord> synopticalRecords = new ArrayList<SynopticalRecord>();
    synopticalRecords.add(new SynopticalRecord());
    synopticalResponse.setSynopticalRecords(synopticalRecords);
    return synopticalResponse;
  }

  /** @return LoadablePlanBallastDetails */
  private LoadablePlanBallastDetails buildLoadablePlanBallastDetails() {
    LoadablePlanBallastDetails.Builder builder = LoadablePlanBallastDetails.newBuilder();
    return builder.build();
  }

  /** @return TankList */
  private TankList buildCargoTanks() {
    TankList.Builder builder = TankList.newBuilder();
    TankDetail.Builder buildTankDetail = TankDetail.newBuilder();
    builder.addVesselTank(buildTankDetail.build());
    return builder.build();
  }

  /** @throws GenericServiceException void */
  @Test
  void testGetLoadablePatternDetailsGrpcFailure() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getLoadablePatternDetails(
                anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.getLoadablePatternDetails(
                any(
                    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.Builder
                        .class)))
        .thenReturn(
            LoadablePlanDetailsReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.getLoadablePatternDetails(
                    1L, 1L, 1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /** @return LoadablePlanStowageDetails */
  private LoadablePlanStowageDetails buildLoadablePlanStowageDetails() {
    LoadablePlanStowageDetails.Builder builder = LoadablePlanStowageDetails.newBuilder();
    return builder.build();
  }

  /** @return LoadableQuantityCommingleCargoDetails */
  private LoadableQuantityCommingleCargoDetails buildLoadableQuantityCommingleCargoDetails() {
    LoadableQuantityCommingleCargoDetails.Builder builder =
        LoadableQuantityCommingleCargoDetails.newBuilder();
    return builder.build();
  }

  /** @return LoadableQuantityCargoDetails */
  private LoadableQuantityCargoDetails buildLoadableQuantityCargoDetails() {
    LoadableQuantityCargoDetails.Builder builder = LoadableQuantityCargoDetails.newBuilder();
    return builder.build();
  }

  /** @throws GenericServiceException void */
  @Test
  void testGetLoadablePatternCommingleDetails() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getLoadablePatternCommingleDetails(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.getLoadablePatternCommingleDetails(
                any(
                    com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest
                        .class)))
        .thenReturn(
            LoadablePatternCommingleDetailsReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    LoadablePatternDetailsResponse response =
        this.loadableStudyService.getLoadablePatternCommingleDetails(
            1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "response valid"));
  }

  /** @throws GenericServiceException void */
  @Test
  void testGetLoadablePatternCommingleDetailsGrpcFailure() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getLoadablePatternCommingleDetails(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.getLoadablePatternCommingleDetails(
                any(
                    com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest
                        .class)))
        .thenReturn(
            LoadablePatternCommingleDetailsReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.getLoadablePatternCommingleDetails(
                    1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testGetOnBoardQuantity() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getOnBoardQuantites(
                anyLong(), anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.getOnBoardQuantites(any(OnBoardQuantityRequest.class)))
        .thenReturn(
            OnBoardQuantityReply.newBuilder()
                .addAllOnBoardQuantity(this.createOnBoardQuantityDetail())
                .addTanks(TankList.newBuilder().addAllVesselTank(this.createTankDetail()).build())
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    OnBoardQuantityResponse response =
        this.loadableStudyService.getOnBoardQuantites(1L, 1L, 1L, 1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"));
  }

  @Test
  void testGetOnBoardQuantityGrpcFailure() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.getOnBoardQuantites(
                anyLong(), anyLong(), anyLong(), anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.getOnBoardQuantites(any(OnBoardQuantityRequest.class)))
        .thenReturn(
            OnBoardQuantityReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.getOnBoardQuantites(
                    1L, 1L, 1L, 1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private List<OnBoardQuantityDetail> createOnBoardQuantityDetail() {
    List<OnBoardQuantityDetail> list = new ArrayList<>();
    IntStream.range(1, 5)
        .forEach(
            i -> {
              OnBoardQuantityDetail detail =
                  OnBoardQuantityDetail.newBuilder()
                      .setTankId(Long.valueOf(i))
                      .setId(Long.valueOf(i))
                      .setTankName("tank-" + i)
                      .setPortId(Long.valueOf(i))
                      .setCargoName(String.valueOf(i))
                      .setWeight(String.valueOf(i))
                      .setSounding(String.valueOf(i))
                      .setVolume(String.valueOf(i))
                      .setCargoId(Long.valueOf(i))
                      .setLoadableStudyId(Long.valueOf(i))
                      .setColorCode(String.valueOf(i))
                      .setAbbreviation(String.valueOf(i))
                      .build();
              list.add(detail);
            });
    return list;
  }

  @Test
  void testSaveComment() throws GenericServiceException {

    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);

    SaveCommentReply saveCommentReply =
        SaveCommentReply.newBuilder()
            .setResponseStatus(
                ResponseStatus.newBuilder().setMessage("Success").setStatus(SUCCESS).build())
            .build();

    Mockito.when(spy.saveComment(ArgumentMatchers.any(Comment.class), anyString(), anyLong()))
        .thenCallRealMethod();

    Mockito.when(
            this.usersRepository.findByKeycloakIdAndIsActive(any(String.class), any(Boolean.class)))
        .thenReturn(createUser());

    Mockito.when(spy.getUsersEntity()).thenReturn(createUser());
    Mockito.when(spy.saveComment(ArgumentMatchers.any(SaveCommentRequest.class)))
        .thenReturn(saveCommentReply);

    final Comment comment = new Comment();
    comment.setComment("comment");
    comment.setUser(1L);

    SaveCommentResponse commentResponse = spy.saveComment(comment, "corelationId", (long) 1);

    Assert.assertEquals(
        String.valueOf(HttpStatusCode.OK.value()), commentResponse.getResponseStatus().getStatus());
  }

  private Users createUser() {
    Users user = new Users();
    user.setId(1L);
    RoleUserMapping roleUserMapping = new RoleUserMapping();
    Roles roles = new Roles();
    roles.setId(1L);
    roles.setName("CHIEF_OFFICER");
    roleUserMapping.setRoles(roles);
    List<RoleUserMapping> roleUserMappingList = new ArrayList();
    roleUserMappingList.add(roleUserMapping);
    user.setRoleUserMappings(roleUserMappingList);
    return user;
  }

  @Test
  void testGetLoadablePatternList() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.getLoadablePatternList(anyLong(), anyString()))
        .thenCallRealMethod();

    Mockito.when(
            this.loadableStudyService.getLoadablePatternList(any(LoadablePatternRequest.class)))
        .thenReturn(
            LoadablePatternReply.newBuilder()
                .addAllLoadablePattern(this.createLoadablePatternList())
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    LoadablePatternResponse response =
        this.loadableStudyService.getLoadablePatternList(
            ID_TEST_VALUE, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"));
  }

  @Test
  void testGetLoadablePatternListGrpcFailure() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.getLoadablePatternList(anyLong(), anyString()))
        .thenCallRealMethod();

    Mockito.when(
            this.loadableStudyService.getLoadablePatternList(any(LoadablePatternRequest.class)))
        .thenReturn(
            LoadablePatternReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.getLoadablePatternList(
                    ID_TEST_VALUE, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private List<LoadablePattern> createLoadablePatternList() {
    List<LoadablePattern> list = new ArrayList<>();
    IntStream.of(1, 3)
        .forEach(
            i -> {
              list.add(
                  LoadablePattern.newBuilder()
                      .setLoadablePatternId(ID_TEST_VALUE)
                      .setCaseNumber(1)
                      .build());
            });
    return list;
  }

  @Test
  void testSaveOnBoardQuantityGrpcFailure() throws GenericServiceException {
    Mockito.when(
            this.loadableStudyService.saveOnBoardQuantites(any(OnBoardQuantity.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(
            this.loadableStudyService.saveOnBoardQuantites(
                any(OnBoardQuantityDetail.class), anyString()))
        .thenReturn(
            OnBoardQuantityReply.newBuilder()
                .addAllOnBoardQuantity(this.createOnBoardQuantityDetail())
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveOnBoardQuantites(
                    this.createOnBoardQuantityRequest(), CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testGetSynopticalTable() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.getSynopticalTable(anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();
    Mockito.when(this.loadableStudyService.getSynopticalTable(any(SynopticalTableRequest.class)))
        .thenReturn(this.createSynopticalTableReply());
    SynopticalTableResponse response = this.loadableStudyService.getSynopticalTable(1L, 1L, 1L);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"));
  }

  @Test
  void testGetSynopticalTableGrpcFailure() throws GenericServiceException {
    Mockito.when(this.loadableStudyService.getSynopticalTable(anyLong(), anyLong(), anyLong()))
        .thenCallRealMethod();

    Mockito.when(this.loadableStudyService.getSynopticalTable(any(SynopticalTableRequest.class)))
        .thenReturn(
            SynopticalTableReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> this.loadableStudyService.getSynopticalTable(1L, 1L, 1L));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  private OnBoardQuantity createOnBoardQuantityRequest() {
    OnBoardQuantity request = new OnBoardQuantity();
    request.setVolume(TEST_BIGDECIMAL_VALUE);
    request.setQuantity(TEST_BIGDECIMAL_VALUE);
    request.setTankId(1L);
    request.setLoadableStudyId(1L);
    request.setPortId(1L);
    request.setId(0L);
    return request;
  }

  private SynopticalTableReply createSynopticalTableReply() {
    SynopticalTableReply.Builder builder = SynopticalTableReply.newBuilder();
    com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder record =
        com.cpdss.common.generated.LoadableStudy.SynopticalRecord.newBuilder();
    record.addAllOhq(this.createSynopticalOhqRecords());
    record.addAllCargo(this.createSynopticalCargoRecord());
    record.addAllBallast(this.createSynopticalBallastRecords());
    builder.addSynopticalRecords(record.build());
    builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return builder.build();
  }

  private List<SynopticalBallastRecord> createSynopticalBallastRecords() {
    List<SynopticalBallastRecord> records = new ArrayList<>();
    IntStream.range(0, 5)
        .forEach(
            i -> {
              records.add(SynopticalBallastRecord.newBuilder().setTankId(Long.valueOf(i)).build());
            });
    return records;
  }

  private List<SynopticalOhqRecord> createSynopticalOhqRecords() {
    List<SynopticalOhqRecord> records = new ArrayList<>();
    OHQ_TANK_CATEGORIES.forEach(
        cat -> {
          records.add(
              SynopticalOhqRecord.newBuilder()
                  .setActualWeight(STRING_TEST_VALUE)
                  .setFuelTypeId(cat)
                  .build());
        });
    return records;
  }

  private List<SynopticalCargoRecord> createSynopticalCargoRecord() {
    List<SynopticalCargoRecord> records = new ArrayList<>();
    IntStream.range(0, 5)
        .forEach(
            i -> {
              records.add(SynopticalCargoRecord.newBuilder().setTankId(Long.valueOf(i)).build());
            });
    return records;
  }

  @Test
  void testSaveSynopticalTable() throws Exception {
    com.cpdss.gateway.domain.SynopticalTableRequest request = this.createSynopticalSaveRequest();
    LoadableStudyService spy = Mockito.spy(this.loadableStudyService);
    Mockito.when(
            spy.saveSynopticalTable(
                any(com.cpdss.gateway.domain.SynopticalTableRequest.class),
                anyLong(),
                anyLong(),
                anyLong(),
                anyString()))
        .thenCallRealMethod();
    Mockito.when(spy.saveSynopticalTable(any(SynopticalTableRequest.class)))
        .thenReturn(
            SynopticalTableReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    SynopticalTableResponse response =
        spy.saveSynopticalTable(
            request, ID_TEST_VALUE, ID_TEST_VALUE, ID_TEST_VALUE, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "Invalid response status"));
  }

  private com.cpdss.gateway.domain.SynopticalTableRequest createSynopticalSaveRequest()
      throws InstantiationException, IllegalAccessException {
    com.cpdss.gateway.domain.SynopticalTableRequest request =
        new com.cpdss.gateway.domain.SynopticalTableRequest();
    request.setSynopticalRecords(new ArrayList<>());
    for (int i = 0; i < 2; i++) {
      SynopticalRecord record = (SynopticalRecord) createDummyObject(SynopticalRecord.class);
      record.setCargos(new ArrayList<>());
      record
          .getCargos()
          .add(
              (com.cpdss.gateway.domain.SynopticalCargoBallastRecord)
                  createDummyObject(com.cpdss.gateway.domain.SynopticalCargoBallastRecord.class));
      record.setFoList(new ArrayList<>());
      record.setDoList(new ArrayList<>());
      record.setFwList(new ArrayList<>());
      record.setLubeList(new ArrayList<>());
      com.cpdss.gateway.domain.SynopticalOhqRecord ohq =
          (com.cpdss.gateway.domain.SynopticalOhqRecord)
              createDummyObject(com.cpdss.gateway.domain.SynopticalOhqRecord.class);
      record.getFoList().add(ohq);
      record.getDoList().add(ohq);
      record.getFwList().add(ohq);
      record.getLubeList().add(ohq);
      request.getSynopticalRecords().add(record);
    }
    return request;
  }

  @Test
  void testSaveLoadOnTop() throws GenericServiceException {
    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    SaveCommentReply saveCommentReply =
        SaveCommentReply.newBuilder()
            .setResponseStatus(
                ResponseStatus.newBuilder().setMessage("Success").setStatus(SUCCESS).build())
            .build();

    Mockito.when(
            spy.saveLoadOnTop(ArgumentMatchers.any(LoadOnTopRequest.class), anyString(), anyLong()))
        .thenCallRealMethod();
    Mockito.when(spy.saveLoadOnTop(ArgumentMatchers.any(SaveLoadOnTopRequest.class)))
        .thenReturn(saveCommentReply);

    LoadOnTopRequest loadOnTopRequest = new LoadOnTopRequest();
    loadOnTopRequest.setIsLoadOnTop(true);

    SaveCommentResponse commentResponse =
        spy.saveLoadOnTop(loadOnTopRequest, "corelationId", (long) 1);

    Assert.assertEquals(
        String.valueOf(HttpStatusCode.OK.value()), commentResponse.getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadOnTopExceptionCase() throws GenericServiceException {
    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    SaveCommentReply saveCommentReply =
        SaveCommentReply.newBuilder()
            .setResponseStatus(
                ResponseStatus.newBuilder()
                    .setMessage("Failure")
                    .setStatus(FAILED)
                    .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                    .build())
            .build();
    Mockito.when(
            loadableStudyService.saveLoadOnTop(
                ArgumentMatchers.any(LoadOnTopRequest.class), anyString(), anyLong()))
        .thenCallRealMethod();
    Mockito.when(
            loadableStudyService.saveLoadOnTop(ArgumentMatchers.any(SaveLoadOnTopRequest.class)))
        .thenReturn(saveCommentReply);
    LoadOnTopRequest loadOnTopRequest = new LoadOnTopRequest();
    loadOnTopRequest.setIsLoadOnTop(true);
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadableStudyService.saveLoadOnTop(
                    loadOnTopRequest, "corelationId", (long) 1));

    assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code");
  }

  @Test
  void testGetVoyageList() throws GenericServiceException {

    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    VoyageListReply.Builder voyageListReply =
        VoyageListReply.newBuilder()
            .setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).build());
    VoyageDetail.Builder voyageDetail = VoyageDetail.newBuilder();
    voyageDetail.setVoyageNumber("test");
    voyageDetail.setCharterer("charterer");
    voyageDetail.setStatus("test");
    voyageDetail.setStartDate("18-02-2021 10:10");
    voyageDetail.setEndDate("18-02-2021 10:10");
    voyageDetail.setActualStartDate("18-02-2021 10:10");
    voyageDetail.setActualEndDate("18-02-2021 10:10");

    LoadingPortDetail.Builder loadingPortBuilder = LoadingPortDetail.newBuilder();
    loadingPortBuilder.setPortId(1L);
    loadingPortBuilder.setName("test");
    voyageDetail.addLoadingPorts(loadingPortBuilder.build());

    DischargingPortDetail.Builder dischargingPortBuilder = DischargingPortDetail.newBuilder();
    dischargingPortBuilder.setPortId(1L);
    dischargingPortBuilder.setName("test");
    voyageDetail.addDischargingPorts(dischargingPortBuilder.build());

    CargoDetails.Builder cargoBuilder = CargoDetails.newBuilder();
    cargoBuilder.setCargoId(1L);
    cargoBuilder.setName("test");
    voyageDetail.addCargos(cargoBuilder.build());

    voyageListReply.addVoyages(voyageDetail.build());

    Mockito.when(
            spy.getVoyageList(
                anyLong(),
                anyString(),
                any(),
                Mockito.anyInt(),
                Mockito.anyInt(),
                anyString(),
                anyString(),
                anyString(),
                anyString()))
        .thenCallRealMethod();

    Mockito.when(spy.getVoyageList(ArgumentMatchers.any(VoyageRequest.class)))
        .thenReturn(voyageListReply.build());

    Map<String, String> filterParams = new HashMap<String, String>();
    filterParams.put("voyageNo", "test");
    filterParams.put("charterer", "ch");
    filterParams.put("cargos", "test");
    filterParams.put("dischargingPorts", "test");
    filterParams.put("loadingPorts", "test");
    filterParams.put("status", "test");
    filterParams.put("plannedStartDate", "2021");
    filterParams.put("plannedEndDate", "2021");
    filterParams.put("actualStartDate", "2021");
    filterParams.put("actualEndDate", "2021");

    VoyageResponse response =
        spy.getVoyageList(1L, "corelationId", filterParams, 1, 1, "", "", "", "charterer");

    Assert.assertEquals(
        String.valueOf(HttpStatusCode.OK.value()), response.getResponseStatus().getStatus());
  }

  @Test
  void testGetVoyageListGrpcFailure() throws GenericServiceException {

    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    VoyageListReply.Builder voyageListReply =
        VoyageListReply.newBuilder()
            .setResponseStatus(
                StatusReply.newBuilder()
                    .setStatus(FAILED)
                    .setCode(String.valueOf(HttpStatusCode.BAD_REQUEST.value()))
                    .build());
    VoyageDetail.Builder voyageDetail = VoyageDetail.newBuilder();
    voyageDetail.setVoyageNumber("test");
    voyageDetail.setCharterer("charterer");
    voyageDetail.setStatus("test");
    voyageDetail.setStartDate("18-02-2021 10:10");
    voyageDetail.setEndDate("18-02-2021 10:10");
    voyageDetail.setActualStartDate("18-02-2021 10:10");
    voyageDetail.setActualEndDate("18-02-2021 10:10");

    Map<String, String> filterParams = new HashMap<String, String>();

    Mockito.when(
            spy.getVoyageList(
                anyLong(),
                anyString(),
                any(),
                Mockito.anyInt(),
                Mockito.anyInt(),
                anyString(),
                anyString(),
                anyString(),
                anyString()))
        .thenCallRealMethod();
    Mockito.when(spy.getVoyageList(ArgumentMatchers.any(VoyageRequest.class)))
        .thenReturn(voyageListReply.build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                spy.getVoyageList(1L, "corelationId", filterParams, 1, 1, "", "", "", "charterer"));
    assertAll(
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  @Test
  void testSaveVoyageStatus() throws GenericServiceException {

    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    SaveVoyageStatusReply saveVoyageStatusReply =
        SaveVoyageStatusReply.newBuilder()
            .setResponseStatus(
                ResponseStatus.newBuilder().setMessage("Success").setStatus(SUCCESS).build())
            .build();
    Mockito.when(spy.saveVoyageStatus(ArgumentMatchers.any(VoyageActionRequest.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(spy.saveVoyageStatus(ArgumentMatchers.any(SaveVoyageStatusRequest.class)))
        .thenReturn(saveVoyageStatusReply);
    VoyageActionRequest request = new VoyageActionRequest();
    request.setVoyageId(1L);
    request.setStatus("test");
    request.setActualStartDate("test");
    request.setActualEndDate("test");
    VoyageActionResponse response = spy.saveVoyageStatus(request, "corelationId");
    Assert.assertEquals(
        String.valueOf(HttpStatusCode.OK.value()), response.getResponseStatus().getStatus());
  }

  @Test
  void testSaveVoyageStatusGrpcFailure() throws GenericServiceException {

    LoadableStudyService spy = Mockito.mock(LoadableStudyService.class);
    SaveVoyageStatusReply saveVoyageStatusReply =
        SaveVoyageStatusReply.newBuilder()
            .setResponseStatus(
                ResponseStatus.newBuilder().setMessage("Failed").setStatus(FAILED).build())
            .build();
    Mockito.when(spy.saveVoyageStatus(ArgumentMatchers.any(VoyageActionRequest.class), anyString()))
        .thenCallRealMethod();
    Mockito.when(spy.saveVoyageStatus(ArgumentMatchers.any(SaveVoyageStatusRequest.class)))
        .thenReturn(saveVoyageStatusReply);
    VoyageActionRequest request = new VoyageActionRequest();
    request.setVoyageId(1L);
    request.setStatus("test");
    request.setActualStartDate("test");
    request.setActualEndDate("test");

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> spy.saveVoyageStatus(request, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }
}
