/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachment;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.LoadingPortDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudy.VoyageListReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.VesselInfo.VesselLoadableQuantityDetails;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.LoadableStudyStatus;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.CargoNominationOperationDetailsRepository;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.CargoNominationValveSegregationRepository;
import com.cpdss.loadablestudy.repository.CargoOperationRepository;
import com.cpdss.loadablestudy.repository.LoadableQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRoationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyStatusRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import com.google.protobuf.ByteString;
import io.grpc.internal.testing.StreamRecorder;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @Author jerin.g
 *
 * <p>Class for writing test cases for loadable study
 */
@SpringJUnitConfig(classes = {LoadableStudyService.class})
class LoadableStudyServiceTest {

  @Autowired private LoadableStudyService loadableStudyService;
  @MockBean private VoyageRepository voyageRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private LoadableQuantityRepository loadableQuantityRepository;
  @MockBean private CargoNominationRepository cargoNominationRepository;
  @MockBean private LoadableStudyPortRoationRepository loadableStudyPortRoationRepository;
  @MockBean private CargoOperationRepository cargoOperationRepository;
  @MockBean private LoadableStudyStatusRepository loadableStudyStatusRepository;

  @MockBean
  private CargoNominationValveSegregationRepository cargoNominationValveSegregationRepository;

  @MockBean
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String VOYAGE = "VOYAGE";
  private static final String VOYAGEEXISTS = "VOYAGE_EXISTS";
  private static final String FAILED = "FAILED";
  private static final String LOADABLE_STUDY_NAME = "LS";
  private static final String LOADABLE_STUDY_DETAILS = "details";

  private static final String CHARTERER = "charterer";
  private static final String SUB_CHARTERER = "sub-chartere";
  private static final String DRAFT_MARK = "1000";
  private static final Long LOAD_LINE_ID = 1L;
  private static final String DRAFT_RESTRICTION = "1000";
  private static final String MAX_TEMP_EXPECTED = "100";
  private static final String LOADABLE_QUANTITY_DUMMY = "100";
  private static final String LOADABLE_QUANTITY_DUMMY_VALUE = "100";
  private static final String INVALID_LOADABLE_QUANTITY = "INVALID_LOADABLE_QUANTITY";
  private static final String NUMERICAL_TEST_VALUE = "100";
  private static final Long ID_TEST_VALUE = 1L;
  private static final String DATE_TEST_VALUE = "2020-10-10";
  private static final String DATE_TIME_TEST_VALUE = "2020-10-10 12:20";
  private static final Long LOADING_OPERATION_ID = 1L;
  private static final Long DISCHARGING_OPERATION_ID = 2L;
  private static final Long LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID = 3L;
  private static final String INVALID_LOADABLE_STUDY_ID = "INVALID_LOADABLE_STUDY_ID";

  @BeforeAll
  public static void beforeAll() {
    MockedStatic<Files> mockedStatic = Mockito.mockStatic(Files.class);
    Path pathMock = Mockito.mock(Path.class);
    mockedStatic.when(() -> Files.createDirectories(any(Path.class))).thenReturn(pathMock);
    mockedStatic.when(() -> Files.createFile(any(Path.class))).thenReturn(pathMock);
    mockedStatic.when(() -> Files.write(any(Path.class), any(byte[].class))).thenReturn(pathMock);
    mockedStatic
        .when(() -> Files.deleteIfExists(any(Path.class)))
        .thenReturn(true)
        .thenThrow(IOException.class);
  }

  /**
   * method for positive test case of save voyage
   *
   * @throws GenericServiceException
   * @returns void
   */
  @Test
  public void testSaveVoyage() throws GenericServiceException {
    VoyageRequest request =
        VoyageRequest.newBuilder()
            .setCaptainId(1)
            .setChiefOfficerId(1)
            .setCompanyId(1)
            .setVesselId(1)
            .setVoyageNo(VOYAGE)
            .build();
    /* used for grpc testing */
    StreamRecorder<VoyageReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    voyage.setId((long) 1);

    Mockito.when(
            this.voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNo(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyString()))
        .thenReturn(new ArrayList<Voyage>());

    Mockito.when(this.voyageRepository.save(ArgumentMatchers.any(Voyage.class))).thenReturn(voyage);
    loadableStudyService.saveVoyage(request, responseObserver);

    assertNull(responseObserver.getError());
    List<VoyageReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    VoyageReply response = results.get(0);
    assertEquals(
        StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
        response.getResponseStatus());
  }

  /**
   * method for negative test case for voyage save
   *
   * @throws GenericServiceException
   * @returns void
   */
  @Test
  public void testSaveVoyageFailure() throws GenericServiceException {
    VoyageRequest request =
        VoyageRequest.newBuilder()
            .setCaptainId(1)
            .setChiefOfficerId(1)
            .setCompanyId(1)
            .setVesselId(1)
            .setVoyageNo(VOYAGE)
            .build();
    /* used for grpc testing */
    StreamRecorder<VoyageReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    Mockito.when(this.voyageRepository.save(voyage)).thenReturn(voyage);
    List<Voyage> voyages = new ArrayList<Voyage>();
    voyages.add(voyage);
    Mockito.when(
            this.voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNo(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyString()))
        .thenReturn(voyages);

    loadableStudyService.saveVoyage(request, responseObserver);

    assertNull(responseObserver.getError());
    List<VoyageReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    VoyageReply response = results.get(0);
    assertEquals(
        StatusReply.newBuilder()
            .setStatus(FAILED)
            .setCode(CommonErrorCodes.E_CPDSS_VOYAGE_EXISTS)
            .setMessage(VOYAGEEXISTS)
            .build(),
        response.getResponseStatus());
  }

  /** @throws GenericServiceException void */
  @Test
  public void testLoadableQuantity() throws GenericServiceException {

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
            .setPortId(1)
            .setBoilerWaterOnBoard(LOADABLE_QUANTITY_DUMMY)
            .setBallast(LOADABLE_QUANTITY_DUMMY)
            .setRunningHours(LOADABLE_QUANTITY_DUMMY)
            .setRunningDays(LOADABLE_QUANTITY_DUMMY)
            .setFoConInSZ(LOADABLE_QUANTITY_DUMMY)
            .setDraftRestriction(LOADABLE_QUANTITY_DUMMY)
            .setLoadableStudyId(1)
            .build();

    StreamRecorder<LoadableQuantityReply> responseObserver = StreamRecorder.create();

    LoadableStudy loadableStudy = Mockito.mock(LoadableStudy.class);
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setId((long) 1);

    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(loadableStudy));
    Mockito.when(this.loadableQuantityRepository.save(ArgumentMatchers.any(LoadableQuantity.class)))
        .thenReturn(loadableQuantity);

    loadableStudyService.saveLoadableQuantity(loadableQuantityRequest, responseObserver);

    assertNull(responseObserver.getError());
    List<LoadableQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    LoadableQuantityReply response = results.get(0);
    assertEquals(
        StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
        response.getResponseStatus());
  }

  /**
   * loadabe study saving failing test case
   *
   * @throws GenericServiceException void
   */
  @Test
  public void testLoadableQuantityFailure() throws GenericServiceException {

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
            .setLoadableStudyId(1)
            .build();

    StreamRecorder<LoadableQuantityReply> responseObserver = StreamRecorder.create();

    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.<LoadableStudy>empty());

    loadableStudyService.saveLoadableQuantity(loadableQuantityRequest, responseObserver);

    assertNull(responseObserver.getError());
    List<LoadableQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    LoadableQuantityReply response = results.get(0);
    assertEquals(
        StatusReply.newBuilder()
            .setStatus(FAILED)
            .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
            .setMessage(INVALID_LOADABLE_QUANTITY)
            .build(),
        response.getResponseStatus());
  }

  @Test
  void testFindLoadableStudiesByVesselAndVoyage() {
    LoadableStudyRequest request = this.createLoadableStudyRequest();
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.of(new Voyage()));
    when(this.loadableStudyRepository.findByVesselXIdAndVoyageAndIsActive(
            anyLong(), any(Voyage.class), anyBoolean()))
        .thenReturn(this.createLoadableStudyEntityList());
    this.loadableStudyService.findLoadableStudiesByVesselAndVoyage(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testFindLoadableStudiesServiceException() {
    LoadableStudyRequest request = this.createLoadableStudyRequest();
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.of(new Voyage()));
    when(this.loadableStudyRepository.findByVesselXIdAndVoyageAndIsActive(
            anyLong(), any(Voyage.class), anyBoolean()))
        .thenThrow(RuntimeException.class);
    this.loadableStudyService.findLoadableStudiesByVesselAndVoyage(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testFindLoadableStudiesInvalidVoyage() {
    LoadableStudyRequest request = this.createLoadableStudyRequest();
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.empty());
    this.loadableStudyService.findLoadableStudiesByVesselAndVoyage(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  /** Test loadable study saving */
  @Test
  void testSaveLoadableStudy() {
    LoadableStudyDetail request = this.createLoadableStudySaveRequest();
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getId());
  }

  @Test
  void testSaveLoadableStudyWithNullValues() {
    LoadableStudyDetail request =
        LoadableStudyDetail.newBuilder()
            .setName(LOADABLE_STUDY_NAME)
            .setVesselId(1L)
            .setVoyageId(1L)
            .setLoadLineXId(LOAD_LINE_ID)
            .addAttachments(
                LoadableStudyAttachment.newBuilder()
                    .setByteString(ByteString.copyFrom("test content".getBytes()))
                    .setFileName("test name")
                    .build())
            .build();
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getId());
  }

  @Test
  void testSaveLoadableStudyInvalidVoyage() {
    LoadableStudyDetail request = this.createLoadableStudySaveRequest();
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
    assertEquals(
        String.valueOf(HttpStatusCode.BAD_REQUEST.value()),
        replies.get(0).getResponseStatus().getCode());
  }

  @Test
  void testSaveLoadableStudyInvalidCreatedFromStudy() {
    LoadableStudyDetail request = this.createLoadableStudySaveRequest();
    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
    assertEquals(
        String.valueOf(HttpStatusCode.BAD_REQUEST.value()),
        replies.get(0).getResponseStatus().getCode());
  }

  @RepeatedTest(2)
  void testSaveLoadableStudyRuntimeException() {
    LoadableStudyDetail request = this.createLoadableStudySaveRequest();
    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.loadableStudyRepository.save(any(LoadableStudy.class)))
        .thenThrow(RuntimeException.class);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
    assertEquals(
        String.valueOf(HttpStatusCode.INTERNAL_SERVER_ERROR.value()),
        replies.get(0).getResponseStatus().getCode());
  }

  @Test
  void testGetLoadableStudyPorts() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(loadableStudy));
    when(this.loadableStudyPortRoationRepository.findByLoadableStudyAndOperationNotAndIsActive(
            any(LoadableStudy.class), any(CargoOperation.class), anyBoolean()))
        .thenReturn(this.createPortEntityList());
    when(this.cargoOperationRepository.findAll()).thenReturn(this.createCargoOperationList());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.getLoadableStudyPortRotation(
        this.createPortRequest(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadableStudyPortsInvalidLoadableStudy() {
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.getLoadableStudyPortRotation(
        this.createPortRequest(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadableStudyPortsRuntimeException() {
    when(this.loadableStudyRepository.findById(anyLong())).thenThrow(RuntimeException.class);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.getLoadableStudyPortRotation(
        this.createPortRequest(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<CargoOperation> createCargoOperationList() {
    List<CargoOperation> entityList = new ArrayList<>();
    IntStream.of(0, 5)
        .forEach(
            i -> {
              CargoOperation entity = new CargoOperation();
              entity.setId(Long.valueOf(i));
              entity.setName("op" + i);
              entityList.add(entity);
            });
    return entityList;
  }

  private List<LoadableStudyPortRotation> createPortEntityList() {
    List<LoadableStudyPortRotation> entityList = new ArrayList<>();
    LoadableStudy study = new LoadableStudy();
    study.setId(1L);
    CargoOperation operation = new CargoOperation();
    operation.setId(1L);
    IntStream.range(0, 10)
        .forEach(
            i -> {
              LoadableStudyPortRotation entity = new LoadableStudyPortRotation();
              entity.setId(Long.valueOf(i));
              entity.setPortXId(Long.valueOf(i));
              entity.setBerthXId(Long.valueOf(i));
              entity.setAirDraftRestriction(BigDecimal.TEN);
              entity.setDistanceBetweenPorts(BigDecimal.ONE);
              entity.setEta(LocalDateTime.now());
              entity.setEtd(LocalDateTime.now());
              entity.setLayCanFrom(LocalDate.now());
              entity.setLayCanTo(LocalDate.now());
              entity.setMaxDraft(BigDecimal.TEN);
              entity.setSeaWaterDensity(BigDecimal.ONE);
              entity.setTimeOfStay(BigDecimal.ONE);
              entity.setLoadableStudy(study);
              entity.setOperation(operation);
              entityList.add(entity);
            });

    return entityList;
  }

  private PortRotationRequest createPortRequest() {
    return PortRotationRequest.newBuilder()
        .setLoadableStudyId(1L)
        .setVesselId(1L)
        .setVoyageId(1L)
        .build();
  }

  /**
   * Create save request for loadable study
   *
   * @return {@link LoadableStudyDetail}
   */
  private LoadableStudyDetail createLoadableStudySaveRequest() {
    LoadableStudyDetail request =
        LoadableStudyDetail.newBuilder()
            .setName(LOADABLE_STUDY_NAME)
            .setDetail(LOADABLE_STUDY_DETAILS)
            .setCharterer(CHARTERER)
            .setSubCharterer(SUB_CHARTERER)
            .setVesselId(1L)
            .setVoyageId(1L)
            .setDraftMark(DRAFT_MARK)
            .setDraftRestriction(DRAFT_RESTRICTION)
            .setMaxAirTemperature(MAX_TEMP_EXPECTED)
            .setMaxWaterTemperature(MAX_TEMP_EXPECTED)
            .setLoadLineXId(LOAD_LINE_ID)
            .setDuplicatedFromId(1L)
            .addAttachments(
                LoadableStudyAttachment.newBuilder()
                    .setByteString(ByteString.copyFrom("test content".getBytes()))
                    .setFileName("test name")
                    .build())
            .build();
    return request;
  }

  private Voyage createVoyageEntity() {
    Voyage voyage = new Voyage();
    voyage.setVoyageNo(VOYAGE);
    voyage.setCompanyXId(1L);
    return voyage;
  }

  private LoadableStudyRequest createLoadableStudyRequest() {
    return LoadableStudyRequest.newBuilder().setVesselId(1L).setVoyageId(1L).build();
  }

  private List<LoadableStudy> createLoadableStudyEntityList() {
    List<LoadableStudy> entityList = new ArrayList<LoadableStudy>();
    LoadableStudyStatus status = new LoadableStudyStatus();
    status.setId(1L);
    status.setName("Open");
    IntStream.range(0, 10)
        .forEach(
            i -> {
              LoadableStudy entity = new LoadableStudy();
              entity.setId(Long.valueOf(i));
              entity.setName(LOADABLE_STUDY_NAME + i);
              entity.setDetails(LOADABLE_STUDY_DETAILS + i);
              entity.setCreatedDate(LocalDate.now());
              entity.setLoadableStudyStatus(status);
              entity.setCharterer(CHARTERER);
              entity.setSubCharterer(SUB_CHARTERER);
              entity.setDraftMark(new BigDecimal(DRAFT_MARK));
              entity.setLoadLineXId(LOAD_LINE_ID);
              entity.setDraftRestriction(new BigDecimal(DRAFT_RESTRICTION));
              entity.setMaxAirTemperature(new BigDecimal(MAX_TEMP_EXPECTED));
              entity.setMaxWaterTemperature(new BigDecimal(MAX_TEMP_EXPECTED));
              LoadableStudyPortRotation port = new LoadableStudyPortRotation();
              port.setActive(true);
              CargoOperation operation = new CargoOperation();
              operation.setId(DISCHARGING_OPERATION_ID);
              port.setOperation(operation);
              port.setPortXId(1L);
              Set<LoadableStudyPortRotation> set = new HashSet<>();
              set.add(port);
              entity.setPortRotations(set);
              entityList.add(entity);
            });
    return entityList;
  }

  @Test
  void testSaveCargoNomination() throws Exception {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest();
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudy()));
    loadableStudyService.saveCargoNomination(cargoNominationRequest, responseObserver);
    assertNull(responseObserver.getError());
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
    CargoNominationReply returnedCargoNominationReply = results.get(0);
    CargoNominationReply.Builder cargoNominationReply = CargoNominationReply.newBuilder();
    //		CargoDetail.Builder cargoDetail = CargoDetail.newBuilder();
    //		cargoDetail.setAbbreviation("testAbbr");
    //		cargoDetail.setApi("testApi");
    //		cargoReply.addCargos(cargoDetail);
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus("SUCCESS");
    cargoNominationReply.setResponseStatus(responseStatus);
    assertEquals(
        cargoNominationReply.getResponseStatus(), returnedCargoNominationReply.getResponseStatus());
  }

  private CargoNominationRequest createSaveCargoNominationRequest() {
    CargoNominationRequest request =
        CargoNominationRequest.newBuilder()
            .setLoadableStudyId(30)
            .setCargoNominationDetail(
                CargoNominationDetail.newBuilder()
                    .setPriority(3L)
                    .setLoadableStudyId(30)
                    .setCargoId(1L)
                    .setAbbreviation("ABBREV")
                    .setColor("testColor")
                    .setMaxTolerance("10.0")
                    .setMinTolerance("20.0")
                    .setApiEst("5.0")
                    .setTempEst("6.0")
                    .setSegregationId(2L)
                    .addLoadingPortDetails(
                        LoadingPortDetail.newBuilder().setPortId(2L).setQuantity("100.00"))
                    .build())
            .build();
    return request;
  }

  /**
   * positive test case for get loadable study after saving first loadable quantity
   *
   * @throws GenericServiceException void
   */
  @Test
  public void postiveTestCaseForGetLoadableQuantity() throws GenericServiceException {
    LoadableStudy loadableStudy = new LoadableStudy();
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setConstant(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setDisplacementAtDraftRestriction(
        new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setDistanceFromLastPort(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setDeadWeight(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setEstimatedDOOnBoard(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setEstimatedFOOnBoard(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setEstimatedFWOnBoard(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setEstimatedSagging(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setEstimatedSeaDensity(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setTotalFoConsumption(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));

    loadableQuantity.setFoConsumptionPerDay(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setDraftRestriction(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setOtherIfAny(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setSaggingDeduction(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setSgCorrection(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setTotalQuantity(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setTpcatDraft(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setVesselAverageSpeed(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setLightWeight(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setLastModifiedDateTime(LocalDateTime.now());
    loadableQuantity.setPortId(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setBoilerWaterOnBoard(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setBallast(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setRunningHours(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setRunningDays(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setFoConsumptionInSZ(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));

    Mockito.when(loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(loadableStudy));
    List<LoadableQuantity> loadableQuantities = new ArrayList<LoadableQuantity>();
    loadableQuantities.add(loadableQuantity);
    Mockito.when(loadableQuantityRepository.findByLoadableStudyXId(ArgumentMatchers.anyLong()))
        .thenReturn(loadableQuantities);

    StreamRecorder<LoadableQuantityResponse> responseObserver = StreamRecorder.create();

    LoadableQuantityReply request =
        LoadableQuantityReply.newBuilder()
            .setLoadableQuantityId(ArgumentMatchers.anyLong())
            .build();

    loadableStudyService.getLoadableQuantity(request, responseObserver);
    assertNull(responseObserver.getError());
    List<LoadableQuantityResponse> results = responseObserver.getValues();
    assertEquals(1, results.size());
    LoadableQuantityResponse response = results.get(0);

    assertEquals(
        StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
        response.getResponseStatus());
  }

  /**
   * positive test case for get loadable study before saving first loadable quantity
   *
   * @throws GenericServiceException void
   */
  @Test
  public void postiveTestCaseForGetLoadableQuantityBeforeSave() throws GenericServiceException {
    LoadableStudyService spy = Mockito.spy(this.loadableStudyService);

    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setDisplacementAtDraftRestriction(
        new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));
    loadableQuantity.setDeadWeight(new BigDecimal(LOADABLE_QUANTITY_DUMMY_VALUE));

    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setLoadLineXId((long) 1);
    loadableStudy.setDraftMark(new BigDecimal(1));
    loadableStudy.setVesselXId((long) 1);

    Mockito.when(loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(loadableStudy));
    Mockito.when(loadableQuantityRepository.findByLoadableStudyXId(ArgumentMatchers.anyLong()))
        .thenReturn(new ArrayList<LoadableQuantity>());
    VesselReply.Builder replyBuilderVessel = VesselReply.newBuilder();
    VesselLoadableQuantityDetails.Builder builder = VesselLoadableQuantityDetails.newBuilder();
    builder.setDisplacmentDraftRestriction(LOADABLE_QUANTITY_DUMMY_VALUE);
    builder.setVesselLightWeight(LOADABLE_QUANTITY_DUMMY_VALUE);
    builder.setConstant(LOADABLE_QUANTITY_DUMMY_VALUE);
    replyBuilderVessel.setVesselLoadableQuantityDetails(builder.build());
    VesselRequest.Builder replyBuilder = VesselRequest.newBuilder();
    replyBuilder.setVesselId((long) 1);
    replyBuilder.setVesselDraftConditionId((long) 1);
    replyBuilder.setDraftExtreme(LOADABLE_QUANTITY_DUMMY_VALUE);

    Mockito.doReturn(replyBuilderVessel.build())
        .when(spy)
        .getVesselDetailsById(any(VesselRequest.class));

    StreamRecorder<LoadableQuantityResponse> responseObserver = StreamRecorder.create();

    LoadableQuantityReply request =
        LoadableQuantityReply.newBuilder().setLoadableStudyId(1L).build();
    Mockito.doCallRealMethod().when(spy).getLoadableQuantity(request, responseObserver);
    spy.getLoadableQuantity(request, responseObserver);
    Mockito.verify(spy, Mockito.times(1)).getLoadableQuantity(any(), any());

    assertNull(responseObserver.getError());
    List<LoadableQuantityResponse> results = responseObserver.getValues();
    assertEquals(1, results.size());
    LoadableQuantityResponse response = results.get(0);

    assertEquals(
        StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
        response.getResponseStatus());
  }

  /**
   * negative test case for get loadable quantity api
   *
   * @throws GenericServiceException void
   */
  @Test
  public void negativeTestCaseForGetLoadableQuantity() throws GenericServiceException {
    StreamRecorder<LoadableQuantityResponse> responseObserver = StreamRecorder.create();

    Mockito.when(loadableQuantityRepository.findByLoadableStudyXId(ArgumentMatchers.anyLong()))
        .thenReturn((new ArrayList<LoadableQuantity>()));
    LoadableQuantityReply request =
        LoadableQuantityReply.newBuilder()
            .setLoadableQuantityId(ArgumentMatchers.anyLong())
            .build();

    loadableStudyService.getLoadableQuantity(request, responseObserver);

    assertNull(responseObserver.getError());
    List<LoadableQuantityResponse> results = responseObserver.getValues();
    assertEquals(1, results.size());
    LoadableQuantityResponse response = results.get(0);

    assertEquals(
        StatusReply.newBuilder()
            .setStatus(FAILED)
            .setMessage(INVALID_LOADABLE_QUANTITY)
            .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
            .build(),
        response.getResponseStatus());
  }

  @Test
  void testGetVoyagesByVessel() {
    when(this.voyageRepository.findByVesselXIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(this.createVoyageEntities());
    StreamRecorder<VoyageListReply> responseObserver = StreamRecorder.create();
    VoyageRequest request = VoyageRequest.newBuilder().setVesselId(1L).build();
    this.loadableStudyService.getVoyagesByVessel(request, responseObserver);
    List<VoyageListReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVoyagesByVesselRuntimeException() {
    when(this.voyageRepository.findByVesselXIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<VoyageListReply> responseObserver = StreamRecorder.create();
    VoyageRequest request = VoyageRequest.newBuilder().setVesselId(1L).build();
    this.loadableStudyService.getVoyagesByVessel(request, responseObserver);
    List<VoyageListReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<Voyage> createVoyageEntities() {
    List<Voyage> entityList = new ArrayList<>();
    IntStream.of(1, 10)
        .forEach(
            i -> {
              Voyage voyage = new Voyage();
              voyage.setId(Long.valueOf(i));
              voyage.setVoyageNo("VYG-" + i);
              entityList.add(voyage);
            });
    return entityList;
  }

  @ParameterizedTest
  @ValueSource(longs = {0, 1})
  void testSaveLoadableStudyPortRotation(Long id) {
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadableStudy()));
    LoadableStudyPortRotation entity = new LoadableStudyPortRotation();
    entity.setId(1L);
    when(this.loadableStudyPortRoationRepository.save(any(LoadableStudyPortRotation.class)))
        .thenReturn(entity);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    if (id.equals(1L)) {
      when(this.loadableStudyPortRoationRepository.findById(id))
          .thenReturn(Optional.of(new LoadableStudyPortRotation()));
    }
    this.loadableStudyService.saveLoadableStudyPortRotation(
        this.createPortRotationRequest().setId(id).build(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadableStudyPortRotationInvalidLoadableStudy() {
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudyPortRotation(
        this.createPortRotationRequest().build(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadableStudyPortRotationInvalidId() {
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadableStudy()));
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyPortRoationRepository.findById(anyLong())).thenReturn(Optional.empty());
    this.loadableStudyService.saveLoadableStudyPortRotation(
        this.createPortRotationRequest().setId(1L).build(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadableStudyPortRotationRuntimeException() {
    when(this.loadableStudyRepository.findById(anyLong())).thenThrow(RuntimeException.class);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudyPortRotation(
        this.createPortRotationRequest().setId(1L).build(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveDischargingPorts() {
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadableStudy()));
    when(this.cargoOperationRepository.getOne(anyLong())).thenReturn(new CargoOperation());
    when(this.loadableStudyPortRoationRepository.findByLoadableStudyAndOperationAndIsActive(
            any(LoadableStudy.class), any(CargoOperation.class), anyBoolean()))
        .thenReturn(this.createPortRotationEntityList());
    when(this.loadableStudyPortRoationRepository.saveAll(any()))
        .thenReturn(this.createPortRotationEntityList());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveDischargingPorts(
        this.createDischargingPortsSaveRequest(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveDischargingPortsInvalidLoadableStudy() {
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveDischargingPorts(
        this.createDischargingPortsSaveRequest(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveDischargingPortsRuntimeException() {
    when(this.loadableStudyRepository.findById(anyLong())).thenThrow(RuntimeException.class);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveDischargingPorts(
        this.createDischargingPortsSaveRequest(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private PortRotationRequest createDischargingPortsSaveRequest() {
    return PortRotationRequest.newBuilder()
        .setLoadableStudyId(1L)
        .addAllDischargingPortIds(Arrays.asList(new Long(1L), new Long(2L)))
        .build();
  }

  private List<LoadableStudyPortRotation> createPortRotationEntityList() {
    List<LoadableStudyPortRotation> entityList = new ArrayList<>();
    LoadableStudyPortRotation entity = new LoadableStudyPortRotation();
    entity.setPortXId(1L);
    entityList.add(entity);
    entity = new LoadableStudyPortRotation();
    entity.setPortXId(10L);
    entityList.add(entity);
    return entityList;
  }

  private PortRotationDetail.Builder createPortRotationRequest() {
    PortRotationDetail.Builder builder = PortRotationDetail.newBuilder();
    builder.setId(0);
    builder.setLoadableStudyId(ID_TEST_VALUE);
    builder.setOperationId(ID_TEST_VALUE);
    builder.setDistanceBetweenPorts(NUMERICAL_TEST_VALUE);
    builder.setEta(DATE_TIME_TEST_VALUE);
    builder.setEtd(DATE_TIME_TEST_VALUE);
    builder.setLayCanFrom(DATE_TEST_VALUE);
    builder.setLayCanTo(DATE_TEST_VALUE);
    builder.setMaxAirDraft(NUMERICAL_TEST_VALUE);
    builder.setMaxDraft(NUMERICAL_TEST_VALUE);
    builder.setPortId(ID_TEST_VALUE);
    builder.setSeaWaterDensity(NUMERICAL_TEST_VALUE);
    builder.setTimeOfStay(NUMERICAL_TEST_VALUE);
    return builder;
  }

  @Test
  void testDeleteLoadableStudy() {
    LoadableStudy entity = new LoadableStudy();
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    LoadableStudyRequest request = LoadableStudyRequest.newBuilder().setLoadableStudyId(1L).build();
    this.loadableStudyService.deleteLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testDeleteLoadableStudyInvalidLoadableStudy() {
    LoadableStudy entity = new LoadableStudy();
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    LoadableStudyRequest request = LoadableStudyRequest.newBuilder().setLoadableStudyId(1L).build();
    this.loadableStudyService.deleteLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testDeleteLoadableStudyInvalidStatus() {
    LoadableStudy entity = new LoadableStudy();
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setId(LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID);
    entity.setLoadableStudyStatus(loadableStudyStatus);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    LoadableStudyRequest request = LoadableStudyRequest.newBuilder().setLoadableStudyId(1L).build();
    this.loadableStudyService.deleteLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testDeleteLoadableStudyRuntimeException() {
    when(this.loadableStudyRepository.findById(anyLong())).thenThrow(RuntimeException.class);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    LoadableStudyRequest request = LoadableStudyRequest.newBuilder().setLoadableStudyId(1L).build();
    this.loadableStudyService.deleteLoadableStudy(request, responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testDeletePortRotation() {
    LoadableStudyPortRotation entity = new LoadableStudyPortRotation();
    LoadableStudy study = new LoadableStudy();
    study.setActive(true);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(study));
    when(this.loadableStudyPortRoationRepository.findById(anyLong()))
        .thenReturn(Optional.of(entity));
    when(this.loadableStudyPortRoationRepository.save(any(LoadableStudyPortRotation.class)))
        .thenReturn(entity);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    PortRotationRequest request = PortRotationRequest.newBuilder().setId(1L).build();
    this.loadableStudyService.deletePortRotation(request, responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3})
  void testDeletePortRotationInvalidLoadableStudy(int param) {
    if (1 == param) {
      when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    } else if (2 == param) {
      when(this.loadableStudyRepository.findById(anyLong()))
          .thenReturn(Optional.of(new LoadableStudy()));
    } else {
      LoadableStudy study = new LoadableStudy();
      study.setActive(true);
      LoadableStudyStatus status = new LoadableStudyStatus();
      status.setId(LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID);
      study.setLoadableStudyStatus(status);
      when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(study));
    }
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    PortRotationRequest request = PortRotationRequest.newBuilder().setId(1L).build();
    this.loadableStudyService.deletePortRotation(request, responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @ValueSource(longs = {1L, 2L})
  @ParameterizedTest
  void testDeletePortRotationInvalidOperation(Long opId) {
    LoadableStudyPortRotation entity = new LoadableStudyPortRotation();
    CargoOperation operation = new CargoOperation();
    operation.setId(opId);
    entity.setOperation(operation);
    LoadableStudy study = new LoadableStudy();
    study.setActive(true);
    LoadableStudyStatus status = new LoadableStudyStatus();
    status.setId(100L);
    study.setLoadableStudyStatus(status);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(study));
    when(this.loadableStudyPortRoationRepository.findById(anyLong()))
        .thenReturn(Optional.of(entity));
    when(this.loadableStudyPortRoationRepository.save(any(LoadableStudyPortRotation.class)))
        .thenReturn(entity);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    PortRotationRequest request = PortRotationRequest.newBuilder().setId(1L).build();
    this.loadableStudyService.deletePortRotation(request, responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testDeletePortRotationInvalidId() {
    LoadableStudyPortRotation entity = new LoadableStudyPortRotation();
    CargoOperation operation = new CargoOperation();
    operation.setId(100L);
    entity.setOperation(operation);
    LoadableStudy study = new LoadableStudy();
    study.setActive(true);
    LoadableStudyStatus status = new LoadableStudyStatus();
    status.setId(100L);
    study.setLoadableStudyStatus(status);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(study));
    when(this.loadableStudyPortRoationRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    PortRotationRequest request = PortRotationRequest.newBuilder().setId(1L).build();
    this.loadableStudyService.deletePortRotation(request, responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testDeletePortRotationRuntimException() {
    LoadableStudyPortRotation entity = new LoadableStudyPortRotation();
    CargoOperation operation = new CargoOperation();
    operation.setId(100L);
    entity.setOperation(operation);
    LoadableStudy study = new LoadableStudy();
    study.setActive(true);
    LoadableStudyStatus status = new LoadableStudyStatus();
    status.setId(100L);
    study.setLoadableStudyStatus(status);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(study));
    when(this.loadableStudyPortRoationRepository.findById(anyLong()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    PortRotationRequest request = PortRotationRequest.newBuilder().setId(1L).build();
    this.loadableStudyService.deletePortRotation(request, responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  /**
   * positive test case
   *
   * @throws GenericServiceException void
   */
  @Test
  public void testGetPortRotation() throws GenericServiceException {

    PortRotationRequest portRotationRequest =
        PortRotationRequest.newBuilder().setLoadableStudyId(1).build();

    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();

    LoadableStudy loadableStudy = Mockito.mock(LoadableStudy.class);
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setId((long) 1);

    List<LoadableStudyPortRotation> loadableStudyPortRotations =
        new ArrayList<LoadableStudyPortRotation>();

    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setPortXId(1L);
    loadableStudyPortRotations.add(loadableStudyPortRotation);

    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(loadableStudy));
    Mockito.when(this.cargoOperationRepository.getOne(ArgumentMatchers.anyLong()))
        .thenReturn(new CargoOperation());
    Mockito.when(
            this.loadableStudyPortRoationRepository.findByLoadableStudyAndOperationAndIsActive(
                ArgumentMatchers.any(LoadableStudy.class),
                ArgumentMatchers.any(CargoOperation.class),
                ArgumentMatchers.anyBoolean()))
        .thenReturn(loadableStudyPortRotations);

    loadableStudyService.getPortRotationByLoadableStudyId(portRotationRequest, responseObserver);

    assertNull(responseObserver.getError());
    List<PortRotationReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    PortRotationReply response = results.get(0);
    assertEquals(
        ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
        response.getResponseStatus());
  }

  /**
   * negative test case
   *
   * @throws GenericServiceException void
   */
  @Test
  public void testGetPortRotationInvalidLoadableStudy() throws GenericServiceException {

    PortRotationRequest portRotationRequest =
        PortRotationRequest.newBuilder().setLoadableStudyId(1).build();

    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();

    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setId((long) 1);

    List<LoadableStudyPortRotation> loadableStudyPortRotations =
        new ArrayList<LoadableStudyPortRotation>();

    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setPortXId(1L);
    loadableStudyPortRotations.add(loadableStudyPortRotation);

    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.<LoadableStudy>empty());
    Mockito.when(this.cargoOperationRepository.getOne(ArgumentMatchers.anyLong()))
        .thenReturn(new CargoOperation());
    Mockito.when(
            this.loadableStudyPortRoationRepository.findByLoadableStudyAndOperationAndIsActive(
                ArgumentMatchers.any(LoadableStudy.class),
                ArgumentMatchers.any(CargoOperation.class),
                ArgumentMatchers.anyBoolean()))
        .thenReturn(loadableStudyPortRotations);

    loadableStudyService.getPortRotationByLoadableStudyId(portRotationRequest, responseObserver);

    assertNull(responseObserver.getError());
    List<PortRotationReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    PortRotationReply response = results.get(0);
    assertEquals(
        ResponseStatus.newBuilder()
            .setStatus(FAILED)
            .setMessage(INVALID_LOADABLE_STUDY_ID)
            .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
            .build(),
        response.getResponseStatus());
  }
  /**
   * negative test case
   *
   * @throws GenericServiceException void
   */
  @Test
  public void testGetPortRotationEmptyPortRotation() throws GenericServiceException {

    PortRotationRequest portRotationRequest =
        PortRotationRequest.newBuilder().setLoadableStudyId(1).build();

    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();

    LoadableStudy loadableStudy = Mockito.mock(LoadableStudy.class);
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setId((long) 1);

    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(loadableStudy));
    Mockito.when(this.cargoOperationRepository.getOne(ArgumentMatchers.anyLong()))
        .thenReturn(new CargoOperation());
    Mockito.when(
            this.loadableStudyPortRoationRepository.findByLoadableStudyAndOperationAndIsActive(
                ArgumentMatchers.any(LoadableStudy.class),
                ArgumentMatchers.any(CargoOperation.class),
                ArgumentMatchers.anyBoolean()))
        .thenReturn(new ArrayList<LoadableStudyPortRotation>());

    loadableStudyService.getPortRotationByLoadableStudyId(portRotationRequest, responseObserver);

    assertNull(responseObserver.getError());
    List<PortRotationReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    PortRotationReply response = results.get(0);
    assertEquals(
        ResponseStatus.newBuilder()
            .setStatus(FAILED)
            .setMessage(INVALID_LOADABLE_STUDY_ID)
            .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
            .build(),
        response.getResponseStatus());
  }

  @Test
  void testGetPortRotationException() {

    PortRotationRequest portRotationRequest =
        PortRotationRequest.newBuilder().setLoadableStudyId(1).build();

    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();

    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenThrow(RuntimeException.class);
    Mockito.when(this.cargoOperationRepository.getOne(ArgumentMatchers.anyLong()))
        .thenReturn(new CargoOperation());
    Mockito.when(
            this.loadableStudyPortRoationRepository.findByLoadableStudyAndOperationAndIsActive(
                ArgumentMatchers.any(LoadableStudy.class),
                ArgumentMatchers.any(CargoOperation.class),
                ArgumentMatchers.anyBoolean()))
        .thenReturn(new ArrayList<LoadableStudyPortRotation>());

    loadableStudyService.getPortRotationByLoadableStudyId(portRotationRequest, responseObserver);

    List<PortRotationReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }
}
