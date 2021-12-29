/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.TestUtils.createDummyObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo.CargoDetail;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.EnvoyWriter;
import com.cpdss.common.generated.LoadableStudy.AlgoReply;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusReply;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply;
import com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.LoadableStudy.LDIntactStability;
import com.cpdss.common.generated.LoadableStudy.LDStrength;
import com.cpdss.common.generated.LoadableStudy.LDtrim;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachment;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest;
import com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply;
import com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest;
import com.cpdss.common.generated.LoadableStudy.LoadicatorPatternDetails;
import com.cpdss.common.generated.LoadableStudy.LoadingPortDetail;
import com.cpdss.common.generated.LoadableStudy.LodicatorResultDetails;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest;
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
import com.cpdss.common.generated.LoadableStudy.UpdateUllageReply;
import com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest;
import com.cpdss.common.generated.LoadableStudy.VoyageListReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequest;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.VesselLoadableQuantityDetails;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.*;
import com.cpdss.loadablestudy.domain.CargoHistory;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.CommingleCargo;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.OnBoardQuantity;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.repository.projections.PortRotationIdAndPortId;
import com.google.protobuf.ByteString;
import io.grpc.internal.testing.StreamRecorder;
import java.io.File;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @Author jerin.g
 *
 * <p>Class for writing test cases for loadable study
 */
@TestPropertySource(properties = "cpdss.communication.enable = false")
@SpringJUnitConfig(classes = {LoadableStudyService.class})
class LoadableStudyServiceTest {

  @Autowired private LoadableStudyService loadableStudyService;

  @MockBean private VoyageRepository voyageRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private LoadableQuantityRepository loadableQuantityRepository;
  @MockBean private DischargeStudyService dischargeStudyService;
  @MockBean private CargoNominationRepository cargoNominationRepository;
  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private CargoOperationRepository cargoOperationRepository;
  @MockBean private LoadableStudyStatusRepository loadableStudyStatusRepository;
  @MockBean private VoyageService voyageService;
  @MockBean private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @MockBean private LoadableStudyAttachmentsRepository loadableStudyAttachmentsRepository;
  @MockBean private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;
  @MockBean private SynopticService synopticService;
  @MockBean private BillOfLandingRepository billOfLandingRepository;

  @MockBean private LoadableQuantityService loadableQuantityService;

  @MockBean
  private DischargePatternQuantityCargoPortwiseRepository
      dischargePatternQuantityCargoPortwiseRepository;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  @MockBean private LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @MockBean private LoadablePatternDetailsRepository loadablePatternDetailsRepository;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private LoadablePlanConstraintsRespository loadablePlanConstraintsRespository;
  @MockBean private PurposeOfCommingleRepository purposeOfCommingleRepository;
  @MockBean private LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;
  @MockBean private LoadableStudyRuleRepository loadableStudyRuleRepository;
  @MockBean private LoadablePatternService loadablePatternService;
  @MockBean private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean private LoadableStudyRuleService loadableStudyRuleService;
  @MockBean private LoadicatorService loadicatorService;
  @MockBean private OnBoardQuantityService onBoardQuantityService;
  @MockBean private AlgoService algoService;
  @MockBean private CommunicationService communicationService;
  @SpyBean private JsonDataService jsonDataService;
  @MockBean private OnHandQuantityService onHandQuantityService;
  @MockBean private LoadablePlanService loadablePlanService;
  @MockBean private CargoNominationService cargoNominationService;
  @MockBean private CargoService cargoService;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean private CommingleCargoRepository commingleCargoRepository;
  @MockBean private AlgoErrorService algoErrorService;

  @MockBean
  private LoadablePatternComingleDetailsRepository loadablePatternComingleDetailsRepository;

  @MockBean
  private CargoNominationValveSegregationRepository cargoNominationValveSegregationRepository;

  @MockBean
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @MockBean private LoadableQuantityCommingleCargoDetails loadableQuantityCommingleCargoDetails;
  @MockBean private OnHandQuantityRepository onHandQuantityRepository;

  @MockBean private OnBoardQuantityRepository onBoardQuantityRepository;
  @MockBean private CargoHistoryRepository cargoHistoryRepository;
  @MockBean private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @MockBean private VoyageHistoryRepository voyageHistoryRepository;
  @MockBean private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @MockBean private BackLoadingService backLoadingService;

  @MockBean private PortInstructionService portInstructionService;
  @MockBean private CowDetailService cowDetailService;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @Mock private CargoNomination cargoNomination;
  @Mock private CargoNominationPortDetails cargoNominationPortDetails;
  @MockBean private RestTemplate restTemplate;
  @MockBean private EntityManager entityManager;
  @MockBean private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean private AlgoErrorsRepository algoErrorsRepository;
  @MockBean private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @MockBean private EntityManagerFactory entityManagerFactory;

  @MockBean private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @MockBean private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean private VoyageStatusRepository voyageStatusRepository;
  @MockBean SynopticServiceUtils synpoticServiceUtils;
  @MockBean private ApiTempHistoryRepository apiTempHistoryRepository;
  // @MockBean private BillOfLandingRepository billOfLandingRepository;
  @MockBean private StabilityParameterRepository stabilityParameterRepository;
  @MockBean private PortRotationService portRotationService;
  // @MockBean private LoadableQuantityService loadableQuantityService;
  @MockBean private JsonDataRepository jsonDataRepository;
  @MockBean private JsonTypeRepository jsonTypeRepository;

  @MockBean private LoadableStudyCommunicationData loadableStudyCommunicationData;

  @MockBean
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @Value("${loadablestudy.attachement.rootFolder}")
  private String rootFolder;

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

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
  private static final String STRING_TEST_VALUES = "TEST";
  private static final String DATE_TEST_VALUE = "10-10-2020";
  private static final String DATE_TIME_TEST_VALUE = "10-10-2020 12:20";
  private static final Long LOADING_OPERATION_ID = 1L;
  private static final Long DISCHARGING_OPERATION_ID = 2L;
  private static final Long LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID = 3L;
  private static final String INVALID_LOADABLE_STUDY_ID = "INVALID_LOADABLE_STUDY_ID";

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

  private static final Long CARGO_TANK_CATEGORY_ID = 1L;
  private static final Long CARGO_SLOP_TANK_CATEGORY_ID = 9L;
  private static final Long CARGO_VOID_TANK_CATEGORY_ID = 15L;

  private static final List<Long> CARGO_TANK_CATEGORIES =
      Arrays.asList(
          CARGO_TANK_CATEGORY_ID, CARGO_SLOP_TANK_CATEGORY_ID, CARGO_VOID_TANK_CATEGORY_ID);
  private static final Long BALLAST_VOID_TANK_CATEGORY_ID = 16L;
  private static final Long BALLAST_TANK_CATEGORY_ID = 2L;
  private static final List<Long> BALLAST_TANK_CATEGORIES =
      Arrays.asList(BALLAST_TANK_CATEGORY_ID, BALLAST_VOID_TANK_CATEGORY_ID);
  private static final String OPERATION_TYPE_ARR = "ARR";
  private static final String OPERATION_TYPE_DEP = "DEP";

  @BeforeAll
  public static void beforeAll() throws Exception {

    try (MockedStatic<Files> mockedStatic = Mockito.mockStatic(Files.class)) {
      Path pathMock = Mockito.mock(Path.class);
      mockedStatic.when(() -> Files.createDirectories(any(Path.class))).thenReturn(pathMock);
      mockedStatic.when(() -> Files.createFile(any(Path.class))).thenReturn(pathMock);
      mockedStatic.when(() -> Files.write(any(Path.class), any(byte[].class))).thenReturn(pathMock);
      mockedStatic
          .when(() -> Files.deleteIfExists(any(Path.class)))
          .thenReturn(true)
          .thenThrow(IOException.class);

      TransactionStatus status = Mockito.mock(TransactionStatus.class);
      MockedStatic<TransactionAspectSupport> mockedTransactionStatic =
          Mockito.mockStatic(TransactionAspectSupport.class);
      mockedTransactionStatic
          .when(() -> TransactionAspectSupport.currentTransactionStatus())
          .thenReturn(status);

      MockitoAnnotations.openMocks(LoadableStudyServiceTest.class);

    } catch (Exception e) {
      System.out.println(e);
    }
    ;
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
            .setStartDate("22-09-2021 12:23")
            .setEndDate("22-09-2021 12:23")
            .setStartTimezoneId(1)
            .setEndTimezoneId(1)
            .build();
    Mockito.when(
            this.voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNoIgnoreCase(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(getListvessel());
    Mockito.when(this.voyageStatusRepository.getOne(Mockito.anyLong()))
        .thenReturn(getVoyageStatus());
    Mockito.when(this.voyageRepository.save(Mockito.any(Voyage.class))).thenReturn(getVoyage());
    Mockito.when(this.voyageService.saveVoyage(Mockito.any(VoyageRequest.class), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
    ReflectionTestUtils.setField(
        voyageService, "voyageStatusRepository", this.voyageStatusRepository);
    StreamRecorder<VoyageReply> responseObserver = StreamRecorder.create();
    loadableStudyService.saveVoyage(request, responseObserver);
    List<VoyageReply> results = responseObserver.getValues();
    assertNull(responseObserver.getError());

    assertEquals(1, results.size());
    VoyageReply response = results.get(0);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  private List<Voyage> getListvessel() {
    List<Voyage> voyages = new ArrayList<>();
    return voyages;
  }

  private VoyageStatus getVoyageStatus() {
    VoyageStatus voyageStatus = new VoyageStatus();
    return voyageStatus;
  }

  private Voyage getVoyage() {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    voyage.setVoyageNo("1");
    return voyage;
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
    voyage.setId(1L);
    //   Mockito.when(this.voyageRepository.save(voyage)).thenReturn(voyage);
    List<Voyage> voyages = new ArrayList<Voyage>();
    voyages.add(voyage);
    Mockito.when(
            this.voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNoIgnoreCase(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyString()))
        .thenReturn(voyages);
    Mockito.when(this.voyageService.saveVoyage(Mockito.any(VoyageRequest.class), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
    loadableStudyService.saveVoyage(request, responseObserver);

    assertNull(responseObserver.getError());
    List<VoyageReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    VoyageReply response = results.get(0);
    assertEquals(FAILED, response.getResponseStatus().getStatus());
  }

  /** @throws GenericServiceException void */
  @Test
  public void testLoadableQuantity() throws GenericServiceException {

    LoadableQuantityRequest loadableQuantityRequest =
        LoadableQuantityRequest.newBuilder()
            .setPortRotationId(1L)
            .setConstant(LOADABLE_QUANTITY_DUMMY)
            .setDisplacmentDraftRestriction(LOADABLE_QUANTITY_DUMMY)
            .setDistanceFromLastPort(LOADABLE_QUANTITY_DUMMY)
            .setDwt(LOADABLE_QUANTITY_DUMMY)
            .setEstDOOnBoard(LOADABLE_QUANTITY_DUMMY)
            .setEstFOOnBoard(LOADABLE_QUANTITY_DUMMY)
            .setEstFreshWaterOnBoard(LOADABLE_QUANTITY_DUMMY)
            .setEstSagging(LOADABLE_QUANTITY_DUMMY)
            .setEstSeaDensity(LOADABLE_QUANTITY_DUMMY)
            .setFoConsumptionPerDay(LOADABLE_QUANTITY_DUMMY)
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
    loadableQuantity.setLoadableStudyXId(getLS());

    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(getOLS());
    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVoyage());
    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);

    Mockito.when(
            loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLLQ());
    Mockito.when(
            this.loadablePatternRepository.findLoadablePatterns(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLP());

    ReflectionTestUtils.setField(
        voyageService, "loadablePatternRepository", this.loadablePatternRepository);
    ReflectionTestUtils.setField(
        loadablePatternService, "loadablePatternRepository", this.loadablePatternRepository);
    Mockito.when(
            loadableStudyPortRotationRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLSPR());
    Mockito.when(this.loadableQuantityRepository.save(ArgumentMatchers.any(LoadableQuantity.class)))
        .thenReturn(loadableQuantity);
    Mockito.when(
            loadableQuantityService.saveLoadableQuantity(
                Mockito.any(LoadableQuantityRequest.class), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadableQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadableQuantityService, "loadableQuantityRepository", loadableQuantityRepository);
    ReflectionTestUtils.setField(loadableQuantityService, "voyageService", this.voyageService);
    ReflectionTestUtils.setField(
        loadableQuantityService, "loadablePatternService", this.loadablePatternService);
    ReflectionTestUtils.setField(
        loadableQuantityService,
        "loadableStudyPortRotationRepository",
        this.loadableStudyPortRotationRepository);
    loadableStudyService.saveLoadableQuantity(loadableQuantityRequest, responseObserver);
    assertNull(responseObserver.getError());
    List<LoadableQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    LoadableQuantityReply response = results.get(0);
    assertEquals(
        StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
        response.getResponseStatus());
  }

  private Optional<LoadableStudy> getOLS() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setVoyage(getVoyage());
    loadableStudy.setVesselXId(1L);
    loadableStudy.setCaseNo(1);
    loadableStudy.setLastModifiedDateTime(LocalDateTime.now());
    loadableStudy.setDetails("1");
    loadableStudy.setName("2");
    loadableStudy.setCharterer("1");
    loadableStudy.setSubCharterer("2");
    loadableStudy.setDraftMark(new BigDecimal(1));
    loadableStudy.setDraftRestriction(new BigDecimal(2));
    loadableStudy.setLoadLineXId(1L);
    loadableStudy.setMaxAirTemperature(new BigDecimal(2));
    loadableStudy.setMaxWaterTemperature(new BigDecimal(1));
    loadableStudy.setEstimatedMaxSag(new BigDecimal(1));
    loadableStudy.setLoadOnTop(true);
    loadableStudy.setDischargeCargoNominationId(1L);
    loadableStudy.setFeedbackLoop(true);
    loadableStudy.setFeedbackLoopCount(1);
    loadableStudy.setPortRotations(getSLSPR());
    loadableStudy.setConfirmedLoadableStudyId(1L);
    loadableStudy.setId(1L);
    return Optional.of(loadableStudy);
  }

  private Set<LoadableStudyPortRotation> getSLSPR() {
    Set<LoadableStudyPortRotation> set = new HashSet<>();
    LoadableStudyPortRotation ls = new LoadableStudyPortRotation();
    ls.setId(1L);
    ls.setEtd(LocalDateTime.now());
    set.add(ls);
    return set;
  }

  private List<LoadableQuantity> getLLQ() {
    List<LoadableQuantity> list = new ArrayList<>();
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setLoadableStudyPortRotation(getLSPR());
    loadableQuantity.setTotalQuantity(new BigDecimal(1));
    loadableQuantity.setId(1L);
    list.add(loadableQuantity);
    return list;
  }

  private List<LoadablePattern> getLLP() {
    List<LoadablePattern> list = new ArrayList<>();
    return list;
  }

  private LoadableStudyPortRotation getLSPR() {
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setPortXId(1L);
    loadableStudyPortRotation.setSeaWaterDensity(new BigDecimal(1));
    loadableStudyPortRotation.setOperation(getCO());
    loadableStudyPortRotation.setMaxDraft(new BigDecimal(2));
    loadableStudyPortRotation.setId(1L);
    return loadableStudyPortRotation;
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
            .setFoConsumptionPerDay(LOADABLE_QUANTITY_DUMMY)
            .setOtherIfAny(LOADABLE_QUANTITY_DUMMY)
            .setSaggingDeduction(LOADABLE_QUANTITY_DUMMY)
            .setSgCorrection(LOADABLE_QUANTITY_DUMMY)
            .setTotalQuantity(LOADABLE_QUANTITY_DUMMY)
            .setTpc(LOADABLE_QUANTITY_DUMMY)
            .setVesselAverageSpeed(LOADABLE_QUANTITY_DUMMY)
            .setVesselLightWeight(LOADABLE_QUANTITY_DUMMY)
            .setLoadableStudyId(1)
            .setLoadableStudyId(1l)
            .build();

    StreamRecorder<LoadableQuantityReply> responseObserver = StreamRecorder.create();

    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.<LoadableStudy>empty());

    Mockito.when(
            this.loadableQuantityService.saveLoadableQuantity(
                any(LoadableQuantityRequest.class), any(LoadableQuantityReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadableQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
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
            .build()
            .getMessage(),
        response.getResponseStatus().getMessage());
  }

  @Test
  void testFindLoadableStudiesByVesselAndVoyage() {
    LoadableStudyRequest request = this.createLoadableStudyRequest();
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    when(this.voyageRepository.findById(anyLong())).thenReturn(Optional.of(new Voyage()));
    when(this.loadableStudyRepository.findByVesselXIdAndVoyageAndIsActiveOrderByCreatedDateTimeDesc(
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
    when(this.voyageRepository.findById(anyLong())).thenThrow(RuntimeException.class);
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
  @ParameterizedTest
  @ValueSource(longs = {7L, 0L, 2L})
  void testSaveLoadableStudy(Long loadlineXId) {
    LoadableStudyDetail.Builder requestBuilder = this.createLoadableStudySaveRequest();
    if (loadlineXId.equals(0L)) {
      requestBuilder.setDraftRestriction(NUMERICAL_TEST_VALUE);
    }
    requestBuilder.setDuplicatedFromId(0);
    requestBuilder.setLoadLineXId(loadlineXId);
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(requestBuilder.build(), responseObserver);
    File file = new File(this.rootFolder);
    deleteFolder(file);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getId());
  }

  static void deleteFolder(File file) {
    for (File subFile : file.listFiles()) {
      if (subFile.isDirectory()) {
        deleteFolder(subFile);
      } else {
        subFile.delete();
      }
    }
    file.delete();
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
                    .setFileName("testname.json")
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
    File file = new File(this.rootFolder);
    deleteFolder(file);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getId());
  }

  @Test
  void testSaveLoadableStudyInvalidVoyage() {
    LoadableStudyDetail request = this.createLoadableStudySaveRequest().build();
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
    LoadableStudyDetail request = this.createLoadableStudySaveRequest().build();
    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVoyage());
    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
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
    LoadableStudyDetail request = this.createLoadableStudySaveRequest().build();
    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    when(this.loadableStudyRepository.save(any(LoadableStudy.class)))
        .thenThrow(RuntimeException.class);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(request, responseObserver);
    File file = new File(this.rootFolder);
    deleteFolder(file);
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
    when(this.loadableStudyPortRotationRepository
            .findByLoadableStudyAndOperationNotAndIsActiveOrderByPortOrder(
                any(LoadableStudy.class), any(CargoOperation.class), anyBoolean()))
        .thenReturn(this.createPortEntityList());
    Mockito.when(
            loadableQuantityRepository.findFirstByLoadableStudyXIdOrderByLastModifiedDateTimeDesc(
                Mockito.any()))
        .thenReturn(getOLQ());
    when(this.cargoOperationRepository.findAll()).thenReturn(this.createCargoOperationList());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(
              loadableStudyPortRotationService.getLoadableStudyPortRotation(
                  Mockito.any(PortRotationRequest.class), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableQuantityRepository",
          this.loadableQuantityRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "cargoOperationRepository",
          this.cargoOperationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.loadableStudyService.getLoadableStudyPortRotation(
        this.createPortRequest(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  private Optional<LoadableQuantity> getOLQ() {
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setLoadableStudyPortRotation(getLSPR());
    loadableQuantity.setId(1L);
    loadableQuantity.setLastModifiedDateTime(LocalDateTime.now());
    return Optional.of(loadableQuantity);
  }

  @Test
  void testGetLoadableStudyPortsInvalidLoadableStudy() {
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(
              loadableStudyPortRotationService.getLoadableStudyPortRotation(
                  Mockito.any(PortRotationRequest.class), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableQuantityRepository",
          this.loadableQuantityRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "cargoOperationRepository",
          this.cargoOperationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
    try {
      Mockito.when(
              loadableStudyPortRotationService.getLoadableStudyPortRotation(
                  Mockito.any(PortRotationRequest.class), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableQuantityRepository",
          this.loadableQuantityRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "cargoOperationRepository",
          this.cargoOperationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
  private LoadableStudyDetail.Builder createLoadableStudySaveRequest() {
    LoadableStudyDetail.Builder builder =
        LoadableStudyDetail.newBuilder()
            .setName(LOADABLE_STUDY_NAME)
            .setDetail(LOADABLE_STUDY_DETAILS)
            .setId(1L)
            .setCharterer(CHARTERER)
            .setSubCharterer(SUB_CHARTERER)
            .setVesselId(1L)
            .setVoyageId(1L)
            .setDraftMark(DRAFT_MARK)
            .setDraftRestriction("1")
            .setMaxAirTemperature(MAX_TEMP_EXPECTED)
            .setMaxWaterTemperature(MAX_TEMP_EXPECTED)
            .setLoadLineXId(LOAD_LINE_ID)
            .setDuplicatedFromId(1L)
            .addAttachments(
                LoadableStudyAttachment.newBuilder()
                    .setByteString(ByteString.copyFrom("test content".getBytes()))
                    .setFileName("testname.json")
                    .build());
    return builder;
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
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(false);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudy()));
    loadableStudyService.saveCargoNomination(cargoNominationRequest, responseObserver);
    assertNull(responseObserver.getError());
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
    CargoNominationReply returnedCargoNominationReply = results.get(0);
    CargoNominationReply.Builder cargoNominationReply = CargoNominationReply.newBuilder();
    // ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    // responseStatus.setStatus(SUCCESS);
    // cargoNominationReply.setResponseStatus(responseStatus);
    // assertEquals(
    // cargoNominationReply.getResponseStatus(),
    // returnedCargoNominationReply.getResponseStatus());
  }

  @Test
  void testSaveCargoNominationWithUpdate() throws Exception {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(true);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudy()));
    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CargoNomination()));
    loadableStudyService.saveCargoNomination(cargoNominationRequest, responseObserver);
    assertNull(responseObserver.getError());
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
    // CargoNominationReply returnedCargoNominationReply = results.get(0);
    // CargoNominationReply.Builder cargoNominationReply =
    // CargoNominationReply.newBuilder();
    // ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    // responseStatus.setStatus(SUCCESS);
    // cargoNominationReply.setResponseStatus(responseStatus);
    // assertEquals(
    // cargoNominationReply.getResponseStatus(),
    // returnedCargoNominationReply.getResponseStatus());
  }

  @Test
  void testSaveCargoNominationWithInvalidLoadableStudy() throws Exception {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(true);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudy()));
    Mockito.when(cargoNominationService.saveCargoNomination(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        cargoNominationService, "loadableStudyRepository", this.loadableStudyRepository);
    loadableStudyService.saveCargoNomination(cargoNominationRequest, responseObserver);
    assertNull(responseObserver.getError());
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
    CargoNominationReply returnedCargoNominationReply = results.get(0);
    CargoNominationReply.Builder cargoNominationReply = CargoNominationReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(FAILED);
    cargoNominationReply.setResponseStatus(responseStatus);
    assertEquals(
        cargoNominationReply.getResponseStatus().getStatus(),
        returnedCargoNominationReply.getResponseStatus().getStatus());
  }

  @Test
  void testSaveCargoNominationWithInvalidCargoNomination() throws Exception {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(true);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CargoNomination()));
    Mockito.when(cargoNominationService.saveCargoNomination(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        cargoNominationService, "cargoNominationRepository", this.cargoNominationRepository);
    loadableStudyService.saveCargoNomination(cargoNominationRequest, responseObserver);
    assertNull(responseObserver.getError());
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
    CargoNominationReply returnedCargoNominationReply = results.get(0);
    CargoNominationReply.Builder cargoNominationReply = CargoNominationReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(FAILED);
    cargoNominationReply.setResponseStatus(responseStatus);
    assertEquals(
        cargoNominationReply.getResponseStatus(), returnedCargoNominationReply.getResponseStatus());
  }

  private CargoNominationRequest createSaveCargoNominationRequest(boolean existingRecord) {
    CargoNominationRequest request =
        CargoNominationRequest.newBuilder()
            .setLoadableStudyId(30)
            .setCargoNominationDetail(
                CargoNominationDetail.newBuilder()
                    .setId(existingRecord ? 15 : 0)
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

  @Test
  void testGetCargoNominationById() throws Exception {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(false);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudy()));
    // mock list creation
    List<CargoNomination> cargoNominationList = new ArrayList<>();
    Set<CargoNominationPortDetails> cargoPortDetails = new HashSet<CargoNominationPortDetails>();
    cargoPortDetails.add(cargoNominationPortDetails);
    cargoNomination.setCargoNominationPortDetails(cargoPortDetails);
    cargoNominationList.add(cargoNomination);
    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(cargoNominationList);
    Mockito.when(apiTempHistoryRepository.findByOrderByCreatedDateTimeDesc()).thenReturn(getLATH());
    Mockito.when(cargoNominationService.getCargoNominationById(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        cargoNominationService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        cargoNominationService, "cargoNominationRepository", this.cargoNominationRepository);
    ReflectionTestUtils.setField(
        cargoNominationService, "apiTempHistoryRepository", this.apiTempHistoryRepository);
    loadableStudyService.getCargoNominationById(cargoNominationRequest, responseObserver);
    assertNull(responseObserver.getError());
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
    CargoNominationReply returnedCargoNominationReply = results.get(0);
    CargoNominationReply.Builder cargoNominationReply = CargoNominationReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(SUCCESS);
    cargoNominationReply.setResponseStatus(responseStatus);
    assertEquals(
        cargoNominationReply.getResponseStatus(), returnedCargoNominationReply.getResponseStatus());
  }

  private List<ApiTempHistory> getLATH() {
    List<ApiTempHistory> list = new ArrayList<>();
    ApiTempHistory apiTempHistory = new ApiTempHistory();
    apiTempHistory.setTemp(new BigDecimal(1));
    list.add(apiTempHistory);
    return list;
  }

  @Test
  void testGetCargoNominationByIdWithInvalidLoadableStudy() throws Exception {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(false);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    Mockito.when(cargoNominationService.getCargoNominationById(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        cargoNominationService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        cargoNominationService, "cargoNominationRepository", this.cargoNominationRepository);
    ReflectionTestUtils.setField(
        cargoNominationService, "apiTempHistoryRepository", this.apiTempHistoryRepository);
    loadableStudyService.getCargoNominationById(cargoNominationRequest, responseObserver);
    assertNull(responseObserver.getError());
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
    CargoNominationReply returnedCargoNominationReply = results.get(0);
    CargoNominationReply.Builder cargoNominationReply = CargoNominationReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(FAILED);
    cargoNominationReply.setResponseStatus(responseStatus);
    assertEquals(
        cargoNominationReply.getResponseStatus(), returnedCargoNominationReply.getResponseStatus());
  }

  @Test
  void testGetCargoNominationByIdWithException() throws Exception {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(false);
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenThrow(new RuntimeException("Error calling loadable study service"));
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    Mockito.when(cargoNominationService.getCargoNominationById(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        cargoNominationService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        cargoNominationService, "cargoNominationRepository", this.cargoNominationRepository);
    ReflectionTestUtils.setField(
        cargoNominationService, "apiTempHistoryRepository", this.apiTempHistoryRepository);
    loadableStudyService.getCargoNominationById(cargoNominationRequest, responseObserver);
    assertNull(responseObserver.getError());
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
    CargoNominationReply returnedCargoNominationReply = results.get(0);
    CargoNominationReply.Builder cargoNominationReply = CargoNominationReply.newBuilder();
    ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
    responseStatus.setStatus(FAILED);
    cargoNominationReply.setResponseStatus(responseStatus);
    assertEquals(
        cargoNominationReply.getResponseStatus(), returnedCargoNominationReply.getResponseStatus());
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
    Mockito.when(
            loadableQuantityRepository.findByLSIdAndPortRotationId(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLQ());
    Mockito.when(
            loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(
            this.onHandQuantityRepository.findByLoadableStudyAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLOHQ());
    Mockito.when(loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(loadableStudy));
    List<LoadableQuantity> loadableQuantities = new ArrayList<LoadableQuantity>();
    loadableQuantities.add(loadableQuantity);
    Mockito.when(
            loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                ArgumentMatchers.anyLong(), anyBoolean()))
        .thenReturn(loadableQuantities);
    Mockito.when(portRotationService.findLoadableStudyPortRotationById(Mockito.anyLong()))
        .thenReturn(getLSPR());
    StreamRecorder<LoadableQuantityResponse> responseObserver = StreamRecorder.create();

    LoadableQuantityReply request =
        LoadableQuantityReply.newBuilder()
            .setLoadableQuantityId(1L)
            .setPortRotationId(1L)
            .setLoadableStudyId(1L)
            .build();

    Mockito.when(loadableQuantityService.getVesselDetailsById(Mockito.any()))
        .thenReturn(getVesselReply());

    Mockito.when(
            loadableQuantityService.loadableQuantityByPortId(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadableQuantityService, "loadableQuantityRepository", this.loadableQuantityRepository);
    ReflectionTestUtils.setField(
        loadableQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadableQuantityService, "portRotationService", this.portRotationService);
    ReflectionTestUtils.setField(
        loadableQuantityService, "onHandQuantityRepository", this.onHandQuantityRepository);
    loadableStudyService.getLoadableQuantity(request, responseObserver);
    assertNull(responseObserver.getError());
    List<LoadableQuantityResponse> results = responseObserver.getValues();
    assertEquals(1, results.size());
    LoadableQuantityResponse response = results.get(0);

    assertEquals(
        StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
        response.getResponseStatus());
  }

  private List<OnHandQuantity> getLOHQ() {
    List<OnHandQuantity> list = new ArrayList<>();
    OnHandQuantity onHandQuantity = new OnHandQuantity();
    onHandQuantity.setTankXId(1L);
    list.add(onHandQuantity);
    return list;
  }

  private VesselInfo.VesselReply getVesselReply() {
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(ResponseStatus.newBuilder().setStatus("SUCCESS").build())
            .setVesselLoadableQuantityDetails(
                VesselLoadableQuantityDetails.newBuilder().setDraftConditionName("1").build())
            .build();
    return vesselReply;
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
    loadableStudy.setCaseNo(1);

    Mockito.when(
            loadableQuantityRepository.findByLSIdAndPortRotationId(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLQ());
    Mockito.when(
            loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(
            loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                ArgumentMatchers.anyLong(), anyBoolean()))
        .thenReturn(new ArrayList<LoadableQuantity>());
    Mockito.when(loadableQuantityService.getVesselDetailsById(Mockito.any()))
        .thenReturn(getVesselReply());
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
    Mockito.when(
            loadableQuantityService.loadableQuantityByPortId(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadableQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadableQuantityService, "loadableQuantityRepository", this.loadableQuantityRepository);
    ReflectionTestUtils.setField(
        loadableQuantityService, "portRotationService", this.portRotationService);
    LoadableQuantityReply request =
        LoadableQuantityReply.newBuilder().setLoadableStudyId(1L).setPortRotationId(1L).build();
    doCallRealMethod().when(spy).getLoadableQuantity(request, responseObserver);
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
    Mockito.when(
            loadableQuantityRepository.findByLSIdAndPortRotationId(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLQ());
    Mockito.when(
            loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenThrow(RuntimeException.class);
    LoadableQuantityReply request =
        LoadableQuantityReply.newBuilder()
            .setLoadableQuantityId(1L)
            .setLoadableStudyId(1L)
            .setPortRotationId(1L)
            .build();
    Mockito.when(
            loadableQuantityService.loadableQuantityByPortId(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadableQuantityService, "loadableQuantityRepository", this.loadableQuantityRepository);
    ReflectionTestUtils.setField(
        loadableQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    loadableStudyService.getLoadableQuantity(request, responseObserver);

    assertNull(responseObserver.getError());
    List<LoadableQuantityResponse> results = responseObserver.getValues();
    assertEquals(1, results.size());
    LoadableQuantityResponse response = results.get(0);
    assertEquals(FAILED, response.getResponseStatus().getStatus());
  }

  @Test
  void testGetVoyagesByVessel() {
    when(this.voyageRepository.findByVesselXIdAndIsActiveOrderByIdDesc(anyLong(), eq(true)))
        .thenReturn(this.createVoyageEntities());
    StreamRecorder<VoyageListReply> responseObserver = StreamRecorder.create();
    VoyageRequest request = VoyageRequest.newBuilder().setVesselId(1L).build();
    Mockito.when(voyageService.getVoyagesByVessel(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
    this.loadableStudyService.getVoyagesByVessel(request, responseObserver);
    List<VoyageListReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  //  @Test
  //  void testGetVoyagesByVesselRuntimeException() {
  //    when(this.voyageRepository.findByVesselXIdAndIsActiveOrderByIdDesc(anyLong(), anyBoolean()))
  //        .thenThrow(RuntimeException.class);
  //    StreamRecorder<VoyageListReply> responseObserver = StreamRecorder.create();
  //    VoyageRequest request = VoyageRequest.newBuilder().setVesselId(1L).build();
  //    Mockito.when(voyageService.getVoyagesByVessel(Mockito.any(), Mockito.any()))
  //        .thenCallRealMethod();
  //    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
  //    this.loadableStudyService.getVoyagesByVessel(request, responseObserver);
  //    List<VoyageListReply> replies = responseObserver.getValues();
  //    assertEquals(1, replies.size());
  //    assertNull(responseObserver.getError());
  //    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  //  }

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
  void testSaveLoadableStudyPortRotation(Long id)
      throws InstantiationException, IllegalAccessException, GenericServiceException {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    VoyageStatus status = new VoyageStatus();
    status.setId(2L);
    voyage.setVoyageStatus(status);
    LoadableStudy loadableStudy = this.createLoadableStudyEntity(voyage);

    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(loadableStudy));
    LoadableStudyPortRotation entity = new LoadableStudyPortRotation();
    entity.setId(1L);
    when(this.loadableStudyPortRotationRepository.save(any(LoadableStudyPortRotation.class)))
        .thenReturn(entity);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    if (id.equals(1L)) {
      LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
      loadableStudyPortRotation.setPortXId(1l);
      when(this.loadableStudyPortRotationRepository.findById(id))
          .thenReturn(Optional.of(loadableStudyPortRotation));
    }
    doNothing()
        .when(this.dischargeStudyService)
        .addCargoNominationForPortRotation(anyLong(), anyLong());
    doNothing()
        .when(this.dischargeStudyService)
        .resetCargoNominationQuantityAndBackLoading(anyLong(), anyLong());
    when(loadableStudyPortRotationService.saveLoadableStudyPortRotation(
            any(PortRotationDetail.class), any(PortRotationReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "loadableStudyPortRotationRepository",
        loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(loadableStudyPortRotationService, "voyageService", voyageService);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "synopticService", synopticService);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "loadablePatternService", loadablePatternService);

    this.loadableStudyService.saveLoadableStudyPortRotation(
        this.createPortRotationRequest().setId(id).build(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadableStudyPortRotationClosedVoyage()
      throws InstantiationException, IllegalAccessException {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    VoyageStatus status = new VoyageStatus();
    status.setId(2L);
    voyage.setVoyageStatus(status);
    LoadableStudy loadableStudy = this.createLoadableStudyEntity(voyage);

    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(loadableStudy));
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(voyage);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(
              loadableStudyPortRotationService.saveLoadableStudyPortRotation(
                  Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.loadableStudyService.saveLoadableStudyPortRotation(
        this.createPortRotationRequest().build(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadableStudyPortRotationPatternGenerated()
      throws InstantiationException, IllegalAccessException {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    VoyageStatus status = new VoyageStatus();
    status.setId(1L);
    voyage.setVoyageStatus(status);
    LoadableStudyStatus lsStatus = new LoadableStudyStatus();
    lsStatus.setId(3L);
    LoadableStudy loadableStudy = this.createLoadableStudyEntity(voyage);
    loadableStudy.setLoadableStudyStatus(lsStatus);

    LoadablePattern pattern = new LoadablePattern();
    pattern.setId(1L);
    List<LoadablePattern> patterns = new ArrayList<LoadablePattern>();
    patterns.add(pattern);

    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(loadableStudy));
    when(this.loadablePatternRepository.findLoadablePatterns(anyLong(), any(), anyBoolean()))
        .thenReturn(patterns);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(
              loadableStudyPortRotationService.saveLoadableStudyPortRotation(
                  Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.loadableStudyService.saveLoadableStudyPortRotation(
        this.createPortRotationRequest().build(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadableStudyPortRotationInvalidLoadableStudy() {
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(
              loadableStudyPortRotationService.saveLoadableStudyPortRotation(
                  Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
    try {
      Mockito.when(
              loadableStudyPortRotationService.saveLoadableStudyPortRotation(
                  Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    when(this.loadableStudyPortRotationRepository.findById(anyLong())).thenReturn(Optional.empty());
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
    try {
      Mockito.when(
              loadableStudyPortRotationService.saveLoadableStudyPortRotation(
                  Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.loadableStudyService.saveLoadableStudyPortRotation(
        this.createPortRotationRequest().setId(1L).build(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveDischargingPorts()
      throws GenericServiceException, InstantiationException, IllegalAccessException {
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    LoadableStudy loadableStudy = this.createLoadableStudyEntity(voyage);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(loadableStudy));
    when(this.cargoOperationRepository.getOne(anyLong())).thenReturn(new CargoOperation());
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
            any(LoadableStudy.class), any(CargoOperation.class), anyBoolean()))
        .thenReturn(this.createPortRotationEntityList());
    when(this.loadableStudyPortRotationRepository.saveAll(any()))
        .thenReturn(this.createPortRotationEntityList());
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(
            PortReply.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());
    when(dischargeStudyService.saveDischargingPorts(
            any(PortRotationRequest.class), any(PortRotationReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargeStudyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(dischargeStudyService, "voyageService", voyageService);
    ReflectionTestUtils.setField(
        dischargeStudyService, "loadablePatternService", loadablePatternService);
    ReflectionTestUtils.setField(
        dischargeStudyService, "cargoOperationRepository", cargoOperationRepository);
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadableStudyPortRotationRepository",
        loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(
        dischargeStudyService, "onHandQuantityRepository", onHandQuantityRepository);
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadableStudyPortRotationService",
        loadableStudyPortRotationService);
    ReflectionTestUtils.setField(dischargeStudyService, "portInfoGrpcService", portInfoGrpcService);

    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveDischargingPorts(
        this.createDischargingPortsSaveRequest(), responseObserver);
    List<PortRotationReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  private PortInfo.PortReply getPortReply() {
    PortInfo.PortReply portReply = PortInfo.PortReply.newBuilder().build();
    return portReply;
  }

  @Test
  void testSaveDischargingPortsInvalidLoadableStudy() {
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(dischargeStudyService.saveDischargingPorts(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          dischargeStudyService, "loadableStudyRepository", this.loadableStudyRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
    try {
      Mockito.when(dischargeStudyService.saveDischargingPorts(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          dischargeStudyService, "loadableStudyRepository", this.loadableStudyRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
    entity.setId(ID_TEST_VALUE);
    entity.setPortXId(1L);
    entityList.add(entity);
    entity = new LoadableStudyPortRotation();
    entity.setId(ID_TEST_VALUE);
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
    entity.setVoyage(getVoyage());
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVoyage());
    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
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
  void testDeleteLoadableStudyPatternGeneratedCase() {
    LoadableStudy entity = new LoadableStudy();
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    LoadablePattern pattern = new LoadablePattern();
    pattern.setId(1L);
    List<LoadablePattern> patternList = new ArrayList<LoadablePattern>();
    patternList.add(pattern);
    when(this.loadablePatternRepository.findLoadablePatterns(anyLong(), any(), anyBoolean()))
        .thenReturn(patternList);
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
    study.setVoyage(getVoyage());
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(study));
    when(this.loadableStudyPortRotationRepository.findById(anyLong()))
        .thenReturn(Optional.of(entity));
    when(this.loadableStudyPortRotationRepository.save(any(LoadableStudyPortRotation.class)))
        .thenReturn(entity);
    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVoyage());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    PortRotationRequest request = PortRotationRequest.newBuilder().setId(1L).build();
    try {
      Mockito.when(
              loadableStudyPortRotationService.deletePortRotation(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
      ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService, "voyageService", this.voyageService);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService, "loadablePatternService", this.loadablePatternService);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
    try {
      Mockito.when(
              loadableStudyPortRotationService.deletePortRotation(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
    when(this.loadableStudyPortRotationRepository.findById(anyLong()))
        .thenReturn(Optional.of(entity));
    when(this.loadableStudyPortRotationRepository.save(any(LoadableStudyPortRotation.class)))
        .thenReturn(entity);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    PortRotationRequest request = PortRotationRequest.newBuilder().setId(1L).build();
    try {
      Mockito.when(
              loadableStudyPortRotationService.deletePortRotation(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
    when(this.loadableStudyPortRotationRepository.findById(anyLong())).thenReturn(Optional.empty());
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    PortRotationRequest request = PortRotationRequest.newBuilder().setId(1L).build();
    try {
      Mockito.when(
              loadableStudyPortRotationService.deletePortRotation(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
    when(this.loadableStudyPortRotationRepository.findById(anyLong()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();
    PortRotationRequest request = PortRotationRequest.newBuilder().setId(1L).build();
    try {
      Mockito.when(
              loadableStudyPortRotationService.deletePortRotation(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyRepository",
          this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          loadableStudyPortRotationService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
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
  @Disabled
  public void testGetPortRotation() throws GenericServiceException {

    PortRotationRequest portRotationRequest =
        PortRotationRequest.newBuilder().setLoadableStudyId(1L).build();

    StreamRecorder<PortRotationReply> responseObserver = StreamRecorder.create();

    LoadableStudy loadableStudy = Mockito.mock(LoadableStudy.class);
    LoadableQuantity loadableQuantity = new LoadableQuantity();
    loadableQuantity.setId((long) 1);

    List<LoadableStudyPortRotation> loadableStudyPortRotations =
        new ArrayList<LoadableStudyPortRotation>();

    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setPortXId(1L);
    loadableStudyPortRotations.add(loadableStudyPortRotation);
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    Mockito.when(
            loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLLQ());
    Mockito.when(
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLSPR());
    Mockito.when(this.loadableStudyRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(loadableStudy));
    Mockito.when(this.cargoOperationRepository.getOne(ArgumentMatchers.anyLong()))
        .thenReturn(new CargoOperation());
    Mockito.when(
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
                ArgumentMatchers.any(LoadableStudy.class),
                ArgumentMatchers.any(CargoOperation.class),
                ArgumentMatchers.anyBoolean()))
        .thenReturn(loadableStudyPortRotations);
    Mockito.when(
            loadableStudyPortRotationService.getPortRotationByLoadableStudyId(
                Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "loadableQuantityRepository",
        this.loadableQuantityRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "cargoOperationRepository",
        this.cargoOperationRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "loadableStudyPortRotationRepository",
        this.loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "backLoadingService", this.backLoadingService);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "cowDetailService", this.cowDetailService);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "portInstructionService", this.portInstructionService);
    loadableStudyService.getPortRotationByLoadableStudyId(portRotationRequest, responseObserver);

    assertNull(responseObserver.getError());
    List<PortRotationReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    PortRotationReply response = results.get(0);
    assertEquals(
        ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
        response.getResponseStatus());
  }

  private List<LoadableStudyPortRotation> getLLSPR() {
    List<LoadableStudyPortRotation> list = new ArrayList<>();
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setPortXId(1L);
    loadableStudyPortRotation.setOperation(getCO());
    loadableStudyPortRotation.setId(1L);
    loadableStudyPortRotation.setPortOrder(1L);
    list.add(loadableStudyPortRotation);
    return list;
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
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
                ArgumentMatchers.any(LoadableStudy.class),
                ArgumentMatchers.any(CargoOperation.class),
                ArgumentMatchers.anyBoolean()))
        .thenReturn(loadableStudyPortRotations);
    Mockito.when(
            loadableStudyPortRotationService.getPortRotationByLoadableStudyId(
                Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "cargoOperationRepository",
        this.cargoOperationRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "loadableStudyPortRotationRepository",
        this.loadableStudyPortRotationRepository);
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
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
                ArgumentMatchers.any(LoadableStudy.class),
                ArgumentMatchers.any(CargoOperation.class),
                ArgumentMatchers.anyBoolean()))
        .thenReturn(new ArrayList<LoadableStudyPortRotation>());
    Mockito.when(
            loadableStudyPortRotationService.getPortRotationByLoadableStudyId(
                Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "cargoOperationRepository",
        this.cargoOperationRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "loadableStudyPortRotationRepository",
        this.loadableStudyPortRotationRepository);
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
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
                ArgumentMatchers.any(LoadableStudy.class),
                ArgumentMatchers.any(CargoOperation.class),
                ArgumentMatchers.anyBoolean()))
        .thenReturn(new ArrayList<LoadableStudyPortRotation>());
    Mockito.when(
            loadableStudyPortRotationService.getPortRotationByLoadableStudyId(
                Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "cargoOperationRepository",
        this.cargoOperationRepository);
    ReflectionTestUtils.setField(
        loadableStudyPortRotationService,
        "loadableStudyPortRotationRepository",
        this.loadableStudyPortRotationRepository);
    loadableStudyService.getPortRotationByLoadableStudyId(portRotationRequest, responseObserver);

    List<PortRotationReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetOnHandQuantity() {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setVoyage(new Voyage());

    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(loadableStudy));
    Mockito.when(this.voyageStatusRepository.getOne(Mockito.anyLong()))
        .thenReturn(getVoyageStatus());
    when(this.loadableStudyPortRotationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(new LoadableStudyPortRotation());
    Mockito.doReturn(this.createVesselReply().build())
        .when(spyService)
        .getVesselTanks(any(VesselRequest.class));
    when(this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            any(LoadableStudy.class), any(LoadableStudyPortRotation.class), anyBoolean()))
        .thenReturn(this.prepareOnHandQuantities());
    Mockito.when(onHandQuantityService.getVesselTanks(Mockito.any())).thenReturn(getVesselReply());
    StreamRecorder<OnHandQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onHandQuantityService.getOnHandQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          onHandQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          onHandQuantityService, "voyageStatusRepository", this.voyageStatusRepository);
      ReflectionTestUtils.setField(onHandQuantityService, "synopticService", this.synopticService);
      ReflectionTestUtils.setField(
          onHandQuantityService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
      ReflectionTestUtils.setField(
          onHandQuantityService, "onHandQuantityRepository", this.onHandQuantityRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }

    spyService.getOnHandQuantity(this.createOnHandQuantityRequest(), responseObserver);
    List<OnHandQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetOnHandQuantityInvalidLoadableStudy() {
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    StreamRecorder<OnHandQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onHandQuantityService.getOnHandQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          onHandQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          onHandQuantityService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
      ReflectionTestUtils.setField(
          onHandQuantityService, "onHandQuantityRepository", this.onHandQuantityRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.loadableStudyService.getOnHandQuantity(
        this.createOnHandQuantityRequest(), responseObserver);
    List<OnHandQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetOnHandQuantityVesselServiceFailure() {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setVoyage(new Voyage());
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(loadableStudy));
    when(this.loadableStudyPortRotationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(new LoadableStudyPortRotation());
    Mockito.doReturn(
            VesselReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .setStatus(FAILED)
                        .build())
                .build())
        .when(spyService)
        .getVesselTanks(any(VesselRequest.class));
    StreamRecorder<OnHandQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onHandQuantityService.getOnHandQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          onHandQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          onHandQuantityService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
      ReflectionTestUtils.setField(
          onHandQuantityService, "onHandQuantityRepository", this.onHandQuantityRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    spyService.getOnHandQuantity(this.createOnHandQuantityRequest(), responseObserver);
    List<OnHandQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetOnHandQuantityRunTimeException() {
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<OnHandQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onHandQuantityService.getOnHandQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          onHandQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
      ReflectionTestUtils.setField(
          onHandQuantityService,
          "loadableStudyPortRotationRepository",
          this.loadableStudyPortRotationRepository);
      ReflectionTestUtils.setField(
          onHandQuantityService, "onHandQuantityRepository", this.onHandQuantityRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.loadableStudyService.getOnHandQuantity(
        this.createOnHandQuantityRequest(), responseObserver);
    List<OnHandQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /**
   * Create on hand quantity request
   *
   * @return
   */
  private OnHandQuantityRequest createOnHandQuantityRequest() {
    return OnHandQuantityRequest.newBuilder()
        .setCompanyId(ID_TEST_VALUE)
        .setVesselId(ID_TEST_VALUE)
        .setLoadableStudyId(ID_TEST_VALUE)
        .setPortId(ID_TEST_VALUE)
        .build();
  }

  /**
   * Create vessel reply
   *
   * @return
   */
  private VesselReply.Builder createVesselReply() {
    VesselReply.Builder builder = VesselReply.newBuilder();
    IntStream.range(1, 5)
        .forEach(
            i -> {
              VesselTankDetail.Builder detailBuilder = VesselTankDetail.newBuilder();
              detailBuilder.setTankId(Long.valueOf(i));
              detailBuilder.setTankCategoryId(Long.valueOf(i));
              detailBuilder.setTankCategoryName("category-" + i);
              detailBuilder.setShortName("name" + i);
              detailBuilder.setFrameNumberFrom("from-" + i);
              detailBuilder.setFrameNumberTo("to" + i);
              builder.addVesselTanks(detailBuilder.build());
            });
    builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return builder;
  }

  private List<OnHandQuantity> prepareOnHandQuantities() {
    List<OnHandQuantity> list = new ArrayList<>();
    IntStream.range(1, 5)
        .forEach(
            i -> {
              OnHandQuantity qty = new OnHandQuantity();
              qty.setId(Long.valueOf(i));
              qty.setFuelTypeXId(Long.valueOf(i));
              qty.setTankXId(Long.valueOf(i));
              list.add(qty);
            });
    return list;
  }

  @ParameterizedTest
  @ValueSource(longs = {0L, 1L})
  void testSaveOnHandQuantity(Long id) throws GenericServiceException {
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setVoyage(voyage);
    when(this.loadableStudyPortRotationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(new LoadableStudyPortRotation());
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(loadableStudy));
    OnHandQuantity entity = new OnHandQuantity();
    entity.setId(1L);
    entity.setLoadableStudy(loadableStudy);
    List<LoadablePattern> generatedPatterns = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    when(this.loadablePatternRepository.findLoadablePatterns(
            anyLong(), any(LoadableStudy.class), anyBoolean()))
        .thenReturn(generatedPatterns);
    ReflectionTestUtils.setField(
        loadablePatternService, "loadablePatternRepository", loadablePatternRepository);

    when(this.onHandQuantityRepository.save(any(OnHandQuantity.class))).thenReturn(entity);
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(voyage);
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);

    when(onHandQuantityService.saveOnHandQuantity(
            any(OnHandQuantityDetail.class), any(OnHandQuantityReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        onHandQuantityService, "onHandQuantityRepository", onHandQuantityRepository);
    ReflectionTestUtils.setField(
        onHandQuantityService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        onHandQuantityService,
        "loadableStudyPortRotationRepository",
        loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(onHandQuantityService, "voyageService", voyageService);
    ReflectionTestUtils.setField(
        onHandQuantityService, "loadablePatternService", loadablePatternService);
    if (id.equals(1L)) {
      when(this.onHandQuantityRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
          .thenReturn(entity);
    }
    StreamRecorder<OnHandQuantityReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveOnHandQuantity(
        this.createOnhandQuantitySaveRequest().setId(id).build(), responseObserver);
    List<OnHandQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveOnHandQuantityInvalidLoadableStudy() {
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    StreamRecorder<OnHandQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onHandQuantityService.saveOnHandQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          onHandQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.loadableStudyService.saveOnHandQuantity(
        this.createOnhandQuantitySaveRequest().setId(0L).build(), responseObserver);
    List<OnHandQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveOnHandQuantityInvalidId() {
    when(this.onHandQuantityRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(null);
    StreamRecorder<OnHandQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onHandQuantityService.saveOnHandQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          onHandQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.loadableStudyService.saveOnHandQuantity(
        this.createOnhandQuantitySaveRequest().setId(1L).build(), responseObserver);
    List<OnHandQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveOnHandQuantityRuntimeException() {
    when(this.onHandQuantityRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<OnHandQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onHandQuantityService.saveOnHandQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
      ReflectionTestUtils.setField(
          onHandQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    this.loadableStudyService.saveOnHandQuantity(
        this.createOnhandQuantitySaveRequest().setId(1L).build(), responseObserver);
    List<OnHandQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  private OnHandQuantityDetail.Builder createOnhandQuantitySaveRequest() {
    OnHandQuantityDetail.Builder detail =
        OnHandQuantityDetail.newBuilder()
            .setTankId(1L)
            .setTankName("tank-1")
            .setFuelType("fuel-1")
            .setFuelTypeId(1L)
            .setArrivalQuantity(NUMERICAL_TEST_VALUE)
            .setArrivalVolume(NUMERICAL_TEST_VALUE)
            .setDepartureQuantity(NUMERICAL_TEST_VALUE)
            .setDepartureVolume(NUMERICAL_TEST_VALUE);
    return detail;
  }

  /**
   * test Get Loadable Pattern Details Invalid Loadable Study Id
   *
   * <p>void
   */
  @Test
  void testGetLoadablePatternDetailsInvalidLoadableStudy() {
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    StreamRecorder<LoadablePatternReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePatternService.getLoadablePatternDetails(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePatternService, "loadableStudyRepository", this.loadableStudyRepository);
    this.loadableStudyService.getLoadablePatternDetails(
        this.createGetLoadablePatternDetails(), responseObserver);
    List<LoadablePatternReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /** @return EntityDoc */
  private LoadablePatternRequest createGetLoadablePatternDetails() {
    return LoadablePatternRequest.newBuilder().setLoadableStudyId(0L).build();
  }

  @SuppressWarnings("unchecked")
  //  @Test
  //  void testValidateLoadablePatterns() {
  //    AlgoResponse algoResponse = new AlgoResponse();
  //    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
  //    algoResponse.setProcessId("1");
  //
  //    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
  //        .thenReturn(Optional.of(createLoadablePattern()));
  //
  //    Mockito.doReturn(this.createPortReply())
  //        .when(spyService)
  //        .getPortInfo(any(GetPortInfoByPortIdsRequest.class));
  //    Mockito.when(
  //            restTemplate.postForObject(
  //                anyString(),
  //                any(com.cpdss.loadablestudy.domain.LoadableStudy.class),
  //                any(Class.class)))
  //        .thenReturn(algoResponse);
  //    Mockito.when(this.loadableStudyCommunicationStatusRepository.save(Mockito.any()))
  //        .thenReturn(getLSCS());
  //    try {
  //      Mockito.when(
  //              communicationService.passRequestPayloadToEnvoyWriter(
  //                  Mockito.anyString(), Mockito.anyLong(), Mockito.anyString()))
  //          .thenReturn(getWriterReply());
  //    } catch (GenericServiceException e) {
  //      e.printStackTrace();
  //    }
  //
  //    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();
  //    try {
  //      Mockito.when(loadablePlanService.validateLoadablePlan(Mockito.any(), Mockito.any()))
  //          .thenCallRealMethod();
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    } catch (GenericServiceException e) {
  //      e.printStackTrace();
  //    }
  //    ReflectionTestUtils.setField(
  //        loadablePlanService, "loadablePatternRepository", this.loadablePatternRepository);
  //    ReflectionTestUtils.setField(
  //        loadablePlanService, "loadableStudyService", this.loadableStudyService);
  //    ReflectionTestUtils.setField(loadablePlanService, "voyageService", this.voyageService);
  //    ReflectionTestUtils.setField(
  //        loadablePlanService, "stowageDetailsTempRepository", this.stowageDetailsTempRepository);
  //    ReflectionTestUtils.setField(loadablePlanService, "jsonDataService", this.jsonDataService);
  //    ReflectionTestUtils.setField(
  //        loadablePlanService, "communicationService", this.communicationService);
  //    ReflectionTestUtils.setField(
  //        loadablePlanService,
  //        "loadableStudyCommunicationStatusRepository",
  //        this.loadableStudyCommunicationStatusRepository);
  //    ReflectionTestUtils.setField(loadablePlanService, "restTemplate", this.restTemplate);
  //    ReflectionTestUtils.setField(loadablePlanService, "loadableStudyUrl", "URl");
  //    ReflectionTestUtils.setField(loadablePlanService, "rootFolder", "D:\\Data");
  //    spyService.validateLoadablePlan(
  //        LoadablePlanDetailsRequest.newBuilder().setLoadablePatternId(1L).build(),
  // responseObserver);
  //    List<AlgoReply> results = responseObserver.getValues();
  //    assertEquals(1, results.size());
  //    assertNull(responseObserver.getError());
  //    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  //  }

  private EnvoyWriter.WriterReply getWriterReply() {
    EnvoyWriter.WriterReply writerReply =
        EnvoyWriter.WriterReply.newBuilder()
            .setResponseStatus(ResponseStatus.newBuilder().setStatus("SUCCESS").build())
            .build();
    return writerReply;
  }

  private LoadableStudyCommunicationStatus getLSCS() {
    LoadableStudyCommunicationStatus loadableStudyCommunicationStatus =
        new LoadableStudyCommunicationStatus();
    loadableStudyCommunicationStatus.setId(1L);
    return loadableStudyCommunicationStatus;
  }

  // @SuppressWarnings("unchecked")
  //  @Test
  //  void testGenerateLoadablePatterns() {
  //    Optional<LoadableStudy> optional = Optional.of(new LoadableStudy());
  //    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
  //    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
  //        .thenReturn(getOLS());
  //    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(),
  // Mockito.anyBoolean()))
  //        .thenReturn(getVoyage());
  //    Mockito.when(
  //            loadableStudyPortRotationRepository.findAllIdAndPortIdsByLSId(
  //                Mockito.anyLong(), Mockito.anyBoolean()))
  //        .thenReturn(getPortsid());
  //    Mockito.when(
  //            loadableQuantityRepository.findByLSIdAndPortRotationId(
  //                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
  //        .thenReturn(getOLQ());
  //
  //    Mockito.doReturn(this.createPortReply())
  //        .when(spyService)
  //        .getPortInfo(any(GetPortInfoByPortIdsRequest.class));
  //
  //    Mockito.when(
  //            restTemplate.postForObject(
  //                anyString(),
  //                any(com.cpdss.loadablestudy.domain.LoadableStudy.class),
  //                any(Class.class)))
  //        .thenReturn(getAlgoResponse());
  //
  //    when(this.onBoardQuantityRepository.findByLoadableStudyAndIsActive(any(), anyBoolean()))
  //        .thenReturn(prepareOnBoardQuantity());
  //
  //    when(this.onHandQuantityRepository.findByLoadableStudyAndIsActive(any(), anyBoolean()))
  //        .thenReturn(prepareHandQuantity());
  //    when(this.cargoNominationOperationDetailsRepository.findByCargoNominationAndIsActive(
  //            Mockito.anyList(), anyBoolean()))
  //        .thenReturn(prepareCargoNominationOperationDetails());
  //
  //    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
  //            anyLong(), anyBoolean()))
  //        .thenReturn(prepareLoadableStudyPortRotationDetails());
  //
  //    when(this.loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(anyLong(),
  // anyBoolean()))
  //        .thenReturn(prepareLoadableQuantityDetails());
  //
  //    when(this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(anyLong(),
  // anyBoolean()))
  //        .thenReturn(prepareCommingleCargoDetails());
  //
  //    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(anyLong(),
  // anyBoolean()))
  //        .thenReturn(prepareCargoNomination());
  //
  //    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();
  //    try {
  //      Mockito.when(loadablePatternService.generateLoadablePatterns(Mockito.any(),
  // Mockito.any()))
  //          .thenCallRealMethod();
  //    } catch (GenericServiceException e) {
  //      e.printStackTrace();
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //    ReflectionTestUtils.setField(
  //        loadablePatternService, "loadableStudyRepository", this.loadableStudyRepository);
  //    ReflectionTestUtils.setField(loadablePatternService, "restTemplate", this.restTemplate);
  //    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
  //    ReflectionTestUtils.setField(loadablePatternService, "loadableStudyUrl", "URL");
  //    ReflectionTestUtils.setField(loadablePatternService, "jsonDataService",
  // this.jsonDataService);
  //    ReflectionTestUtils.setField(loadablePatternService, "voyageService", this.voyageService);
  //    ReflectionTestUtils.setField(
  //        loadablePatternService,
  //        "loadableStudyPortRotationRepository",
  //        this.loadableStudyPortRotationRepository);
  //    ReflectionTestUtils.setField(
  //        loadablePatternService, "loadableQuantityRepository", this.loadableQuantityRepository);
  //    ReflectionTestUtils.setField(
  //        loadablePatternService, "cargoNominationService", this.cargoNominationService);
  //    ReflectionTestUtils.setField(
  //        loadablePatternService, "loadableStudyService", this.loadableStudyService);
  //    ReflectionTestUtils.setField(loadablePatternService, "rootFolder", "D:\\Data");
  //    spyService.generateLoadablePatterns(AlgoRequest.newBuilder().build(), responseObserver);
  //    List<AlgoReply> results = responseObserver.getValues();
  //    assertEquals(1, results.size());
  //    assertNull(responseObserver.getError());
  //    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  //  }

  private AlgoResponse getAlgoResponse() {
    AlgoResponse algoResponse = new AlgoResponse();
    algoResponse.setProcessId("1");
    return algoResponse;
  }

  private List<PortRotationIdAndPortId> getPortsid() {
    List<PortRotationIdAndPortId> po = new ArrayList<PortRotationIdAndPortId>();
    PortRotationIdAndPortId ports = new PortRotation();
    po.add(ports);
    return po;
  }

  private class PortRotation implements PortRotationIdAndPortId {

    @Override
    public Long getId() {
      // TODO Auto-generated method stub
      return 1L;
    }

    @Override
    public Long getPortId() {
      // TODO Auto-generated method stub
      return null;
    }
  }

  /** @return List<CargoNomination> */
  private List<CargoNomination> prepareCargoNomination() {
    List<CargoNomination> cargoNominations = new ArrayList<CargoNomination>();
    cargoNominations.add(new CargoNomination());
    return cargoNominations;
  }

  /** @return List<CommingleCargo> */
  private List<CommingleCargo> prepareCommingleCargoDetails() {
    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargos =
        new ArrayList<com.cpdss.loadablestudy.entity.CommingleCargo>();
    commingleCargos.add(new com.cpdss.loadablestudy.entity.CommingleCargo());
    return commingleCargos;
  }

  /** @return List<LoadableQuantity> */
  private List<LoadableQuantity> prepareLoadableQuantityDetails() {
    List<LoadableQuantity> loadableQuantities = new ArrayList<LoadableQuantity>();
    loadableQuantities.add(new LoadableQuantity());
    return loadableQuantities;
  }

  /** @return List<LoadableStudyPortRotationService> */
  private List<LoadableStudyPortRotation> prepareLoadableStudyPortRotationDetails() {
    List<LoadableStudyPortRotation> loadableStudyPortRotations =
        new ArrayList<LoadableStudyPortRotation>();
    loadableStudyPortRotations.add(new LoadableStudyPortRotation());
    return loadableStudyPortRotations;
  }

  /** @return List<CargoNominationPortDetails> */
  private List<CargoNominationPortDetails> prepareCargoNominationOperationDetails() {
    List<CargoNominationPortDetails> cargoNominationPortDetails =
        new ArrayList<CargoNominationPortDetails>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setId(1L);
    CargoNominationPortDetails details = new CargoNominationPortDetails();
    details.setCargoNomination(cargoNomination);
    cargoNominationPortDetails.add(details);
    return cargoNominationPortDetails;
  }

  /** @return List<OnHandQuantity> */
  private List<OnHandQuantity> prepareHandQuantity() {
    List<OnHandQuantity> onHandQuantities = new ArrayList<OnHandQuantity>();
    onHandQuantities.add(new OnHandQuantity());
    return onHandQuantities;
  }

  /** @return List<OnBoardQuantity> */
  private List<OnBoardQuantity> prepareOnBoardQuantity() {
    List<OnBoardQuantity> onBoardQuantities = new ArrayList<OnBoardQuantity>();
    onBoardQuantities.add(new OnBoardQuantity());
    return onBoardQuantities;
  }

  /** @return Builder */
  private PortReply createPortReply() {
    PortReply.Builder builder = PortReply.newBuilder();
    PortDetail.Builder portDetailBuilder = PortDetail.newBuilder();
    builder.addPorts(portDetailBuilder);
    return builder.build();
  }

  /**
   * testSaveLoadicatorResults
   *
   * <p>void
   */
  //  @Test
  //  void testSaveLoadicatorResults() {
  //    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
  //            .thenReturn(Optional.of(new LoadableStudy()));
  //    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();
  //    Mockito.when(loadicatorService.saveLoadicatorResults(Mockito.any(), Mockito.any()))
  //            .thenCallRealMethod();
  //    ReflectionTestUtils.setField(
  //            loadicatorService, "loadableStudyRepository", this.loadableStudyRepository);
  //    ReflectionTestUtils.setField(
  //            loadicatorService,
  //            "loadableStudyAlgoStatusRepository",
  //            this.loadableStudyAlgoStatusRepository);
  //    loadableStudyService.saveLoadicatorResults(this.createLoadicatorResults(),
  // responseObserver);
  //    List<AlgoReply> results = responseObserver.getValues();
  //    assertEquals(1, results.size());
  //    assertNull(responseObserver.getError());
  //    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  //  }

  //  @Test
  //  void testSaveLoadicatorResultsInvalidLoadableStudy() {
  //    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
  //            .thenReturn(Optional.empty());
  //    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();
  //    Mockito.when(loadicatorService.saveLoadicatorResults(Mockito.any(), Mockito.any()))
  //            .thenCallRealMethod();
  //    ReflectionTestUtils.setField(
  //            loadicatorService, "loadableStudyRepository", this.loadableStudyRepository);
  //    loadableStudyService.saveLoadicatorResults(this.createLoadicatorResults(),
  // responseObserver);
  //    List<AlgoReply> results = responseObserver.getValues();
  //    assertEquals(1, results.size());
  //    assertNull(responseObserver.getError());
  //    assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST,
  // results.get(0).getResponseStatus().getCode());
  //  }

  //  @Test
  //  void testSaveLoadicatorResultsRuntimeException() {
  //    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
  //            .thenThrow(RuntimeException.class);
  //    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();
  //    Mockito.when(loadicatorService.saveLoadicatorResults(Mockito.any(), Mockito.any()))
  //            .thenCallRealMethod();
  //    ReflectionTestUtils.setField(
  //            loadicatorService, "loadableStudyRepository", this.loadableStudyRepository);
  //    loadableStudyService.saveLoadicatorResults(this.createLoadicatorResults(),
  // responseObserver);
  //    List<AlgoReply> results = responseObserver.getValues();
  //    assertEquals(1, results.size());
  //    assertNull(responseObserver.getError());
  //    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  //  }

  /** @return LoadicatorResultsRequest */
  //  private LoadicatorResultsRequest createLoadicatorResults() {
  //    LoadicatorResultsRequest.Builder builder =
  //            LoadicatorResultsRequest.newBuilder().setLoadableStudyId(1L);
  //    builder.addLoadicatorPatternDetailsResults(buildLoadicatorPatternDetailsResults());
  //    return builder.build();
  //  }
  //
  //  /** @return LoadicatorPatternDetailsResults */
  //  private LoadicatorPatternDetailsResults buildLoadicatorPatternDetailsResults() {
  //    LoadicatorPatternDetailsResults.Builder builder =
  // LoadicatorPatternDetailsResults.newBuilder();
  //    builder.addLodicatorResultDetails(buildLodicatorResultDetails());
  //    return builder.build();
  //  }

  /** @return LodicatorResultDetails */
  private LodicatorResultDetails buildLodicatorResultDetails() {
    LodicatorResultDetails.Builder builder = LodicatorResultDetails.newBuilder();
    return builder.build();
  }

  /**
   * testGetLoadablePatternDetailsInvalidTankDetails
   *
   * <p>void
   */
  @Test
  void testGetLoadablePatternDetailsInvalidTankDetails() {
    Optional<LoadableStudy> optional = Optional.of(new LoadableStudy());
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    optional.get().setVesselXId(1L);
    Mockito.doReturn(
            this.createVesselReply()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build())
                .build())
        .when(spyService)
        .getVesselTanks(any(VesselRequest.class));
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(optional);
    when(this.loadablePatternComingleDetailsRepository.findByLoadablePatternDetailsIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new LoadablePatternComingleDetails()));
    when(this.loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(prepareLoadablePatterns());
    when(this.loadablePatternDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(prepareLoadablePatternDetails());
    StreamRecorder<LoadablePatternReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePatternService.getLoadablePatternDetails(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePatternService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadablePatternService, "loadablePatternRepository", this.loadablePatternRepository);
    spyService.getLoadablePatternDetails(this.createGetLoadablePatternDetails(), responseObserver);
    List<LoadablePatternReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /**
   * testGetLoadablePatternDetailsRuntimeException
   *
   * <p>void
   */
  @Test
  void testGetLoadablePatternDetailsRuntimeException() {
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<LoadablePatternReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePatternService.getLoadablePatternDetails(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePatternService, "loadableStudyRepository", this.loadableStudyRepository);
    this.loadableStudyService.getLoadablePatternDetails(
        this.createGetLoadablePatternDetails(), responseObserver);
    List<LoadablePatternReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /** @return List<LoadablePatternDetails> */
  private List<LoadablePatternDetails> prepareLoadablePatternDetails() {
    List<LoadablePatternDetails> list = new ArrayList<LoadablePatternDetails>();
    IntStream.range(1, 5)
        .forEach(
            i -> {
              LoadablePatternDetails loadablePatternDetails = new LoadablePatternDetails();
              loadablePatternDetails.setId(Long.valueOf(i));
              loadablePatternDetails.setCargoAbbreviation("ABB");
              loadablePatternDetails.setCargoColor("COL");
              loadablePatternDetails.setCargoNominationId(Long.valueOf(i));
              loadablePatternDetails.setConstraints("CON");
              loadablePatternDetails.setDifference(new BigDecimal(i));
              loadablePatternDetails.setDifferenceColor("DIFFCOL");
              loadablePatternDetails.setPriority(i);
              loadablePatternDetails.setQuantity(new BigDecimal(i));
              loadablePatternDetails.setTankId(Long.valueOf(i));
              loadablePatternDetails.setIsCommingle(true);
              list.add(loadablePatternDetails);
            });
    return list;
  }

  /** @return List<LoadablePattern> */
  private List<LoadablePattern> prepareLoadablePatterns() {
    List<LoadablePattern> list = new ArrayList<LoadablePattern>();
    IntStream.range(1, 5)
        .forEach(
            i -> {
              LoadablePattern loadablePattern = new LoadablePattern();
              loadablePattern.setId(Long.valueOf(i));
              loadablePattern.setCreatedDate(LocalDate.now());
              list.add(loadablePattern);
            });
    return list;
  }

  /** void */
  @Test
  void testSaveLoadablePatternsRuntimeException() {
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePatternService.saveLoadablePatterns(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePatternService, "loadableStudyRepository", this.loadableStudyRepository);
    loadableStudyService.saveLoadablePatterns(
        this.createLoadablePatternRequest(), responseObserver);
    List<AlgoReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /**
   * *
   *
   * <p>void
   */
  @Test
  void testSaveLoadablePatternsInvalidLoadableStudy() {
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePatternService.saveLoadablePatterns(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePatternService, "loadableStudyRepository", this.loadableStudyRepository);
    loadableStudyService.saveLoadablePatterns(
        this.createLoadablePatternRequest(), responseObserver);
    List<AlgoReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, results.get(0).getResponseStatus().getCode());
  }

  /**
   * testSaveLoadablePatterns
   *
   * <p>void
   */
  //  @Test
  //  void testSaveLoadablePatterns() throws GenericServiceException {
  //    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
  //            .thenReturn(Optional.of(new LoadableStudy()));
  //    when(this.loadablePatternService.saveLoadablePatterns(
  //            any(LoadablePatternAlgoRequest.class), any(AlgoReply.Builder.class)))
  //            .thenCallRealMethod();
  //    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();
  //    ReflectionTestUtils.setField(
  //            loadablePatternService, "loadableStudyRepository", loadableStudyRepository);
  //    ReflectionTestUtils.setField(
  //            loadablePatternService, "loadablePatternRepository", loadablePatternRepository);
  //    ReflectionTestUtils.setField(
  //            loadablePatternService,
  //            "loadableStudyPortRotationRepository",
  //            loadableStudyPortRotationRepository);
  //    ReflectionTestUtils.setField(
  //            loadablePatternService,
  //            "loadablePatternCargoDetailsRepository",
  //            loadablePatternCargoDetailsRepository);
  //    ReflectionTestUtils.setField(
  //            loadablePatternService,
  //            "loadablePlanStowageBallastDetailsRepository",
  //            loadablePlanStowageBallastDetailsRepository);
  //    ReflectionTestUtils.setField(loadablePatternService, "loadicatorService",
  // loadicatorService);
  //    ReflectionTestUtils.setField(
  //            loadablePatternService,
  //            "loadableStudyAlgoStatusRepository",
  //            loadableStudyAlgoStatusRepository);
  //
  //    loadableStudyService.saveLoadablePatterns(
  //            this.createLoadablePatternRequest12(), responseObserver);
  //    List<AlgoReply> results = responseObserver.getValues();
  //    assertEquals(1, results.size());
  //    assertNull(responseObserver.getError());
  //    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  //  }

  private LoadablePatternAlgoRequest createLoadablePatternRequest12() {
    LoadablePatternAlgoRequest.Builder builder = LoadablePatternAlgoRequest.newBuilder();
    List<LoadablePlanDetails> list = new ArrayList<>();
    LoadablePlanDetails loadablePlanDetails = LoadablePlanDetails.newBuilder().build();
    list.add(loadablePlanDetails);
    builder.addAllLoadablePlanDetails(list);
    builder.addLoadablePlanDetails(builderLoadablePlanDetails());
    return builder.build();
  }

  private Optional<LoadablePattern> getOLP() {
    LoadablePattern lo = new LoadablePattern();
    lo.setId(1L);
    lo.setFeedbackLoop(true);
    lo.setFeedbackLoopCount(1);
    return Optional.of(lo);
  }

  /** @return LoadablePatternAlgoRequest */
  private LoadablePatternAlgoRequest createLoadablePatternRequest() {

    List<LoadablePlanPortWiseDetails> list1 = new ArrayList<>();
    LoadablePlanPortWiseDetails loadablePlanPortWiseDetails =
        LoadablePlanPortWiseDetails.newBuilder()
            .setPortId(1L)
            .setPortRotationId(1L)
            .setArrivalCondition(LoadablePlanDetailsReply.newBuilder().build())
            .build();
    list1.add(loadablePlanPortWiseDetails);
    List<LoadablePlanDetails> list = new ArrayList<>();
    LoadablePlanDetails lo =
        LoadablePlanDetails.newBuilder()
            .setCaseNumber(1)
            .addAllLoadablePlanPortWiseDetails(list1)
            .build();
    list.add(lo);
    LoadablePatternAlgoRequest.Builder builder =
        LoadablePatternAlgoRequest.newBuilder()
            .setHasLodicator(false)
            .addAllLoadablePlanDetails(list)
            .setRequestType("1");
    builder.addLoadablePlanDetails(builderLoadablePlanDetails());
    return builder.build();
  }

  /** @return LoadablePlanDetails */
  private LoadablePlanDetails builderLoadablePlanDetails() {
    LoadablePlanDetails.Builder builder = LoadablePlanDetails.newBuilder();
    builder.addLoadablePlanPortWiseDetails(builderLoadablePlanPortWiseDetails());
    return builder.build();
  }

  /** @return LoadablePlanPortWiseDetails */
  private LoadablePlanPortWiseDetails builderLoadablePlanPortWiseDetails() {
    LoadablePlanPortWiseDetails.Builder builder = LoadablePlanPortWiseDetails.newBuilder();
    builder.setArrivalCondition(buildArrivalDepartureCondition());
    builder.setDepartureCondition(buildArrivalDepartureCondition());
    return builder.build();
  }

  /** @return LoadablePlanDetailsReply */
  private LoadablePlanDetailsReply buildArrivalDepartureCondition() {
    LoadablePlanDetailsReply.Builder builder = LoadablePlanDetailsReply.newBuilder();
    builder.addLoadablePlanStowageDetails(buildLoadablePlanStowageDetails());
    builder.addLoadablePlanBallastDetails(buildLoadablePlanBallastDetails());
    return builder.build();
  }

  /** @return com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails */
  private com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails
      buildLoadablePlanBallastDetails() {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.newBuilder();
    return builder.build();
  }

  /** @return com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails */
  private com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails
      buildLoadablePlanStowageDetails() {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder()
            .setApi("1")
            .setTemperature("1");
    return builder.build();
  }

  /**
   * test Save Algo Loadable Study Status
   *
   * <p>void
   */
  @Test
  void testSaveAlgoLoadableStudyStatus() {
    when(this.loadableStudyAlgoStatusRepository.findByProcessIdAndIsActive(
            anyString(), anyBoolean()))
        .thenReturn(Optional.of(new LoadableStudyAlgoStatus()));

    StreamRecorder<AlgoStatusReply> responseObserver = StreamRecorder.create();
    Mockito.when(algoService.saveAlgoLoadableStudyStatus(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        algoService, "loadableStudyAlgoStatusRepository", this.loadableStudyAlgoStatusRepository);
    loadableStudyService.saveAlgoLoadableStudyStatus(
        this.createAlgoStatusRequest(), responseObserver);
    List<AlgoStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  }

  /** void */
  @Test
  void testSaveAlgoLoadableStudyStatusInvalidProcessId() {
    when(this.loadableStudyAlgoStatusRepository.findByProcessIdAndIsActive(
            anyString(), anyBoolean()))
        .thenReturn(Optional.empty());
    StreamRecorder<AlgoStatusReply> responseObserver = StreamRecorder.create();
    Mockito.when(algoService.saveAlgoLoadableStudyStatus(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        algoService, "loadableStudyAlgoStatusRepository", this.loadableStudyAlgoStatusRepository);
    this.loadableStudyService.saveAlgoLoadableStudyStatus(
        this.createAlgoStatusRequest(), responseObserver);
    List<AlgoStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, results.get(0).getResponseStatus().getCode());
  }

  /**
   * test Save Algo Loadable Study Status
   *
   * <p>void
   */
  @Test
  void testSaveAlgoLoadableStudyStatusRuntimeException() {
    when(this.loadableStudyAlgoStatusRepository.findByProcessIdAndIsActive(
            anyString(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<AlgoStatusReply> responseObserver = StreamRecorder.create();
    Mockito.when(algoService.saveAlgoLoadableStudyStatus(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        algoService, "loadableStudyAlgoStatusRepository", this.loadableStudyAlgoStatusRepository);
    loadableStudyService.saveAlgoLoadableStudyStatus(
        this.createAlgoStatusRequest(), responseObserver);
    List<AlgoStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /** @return AlgoStatusRequest */
  private AlgoStatusRequest createAlgoStatusRequest() {
    AlgoStatusRequest.Builder builder = AlgoStatusRequest.newBuilder();
    builder.setLoadableStudystatusId(1L);
    builder.setProcesssId("ID");
    return builder.build();
  }

  /**
   * test Confirm Plan
   *
   * <p>void
   */
  @Test
  void testConfirmPlan() {
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(createLoadablePattern()));
    List<LoadablePattern> loadablePatterns = new ArrayList<LoadablePattern>();
    loadablePatterns.add(createLoadablePattern());
    when(this.loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
            anyLong(), anyLong(), anyBoolean()))
        .thenReturn(loadablePatterns);

    StreamRecorder<ConfirmPlanReply> responseObserver = StreamRecorder.create();
    Mockito.when(loadablePatternService.confirmPlan(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePatternService, "loadablePatternRepository", this.loadablePatternRepository);
    ReflectionTestUtils.setField(
        loadablePatternService, "loadableStudyRepository", this.loadableStudyRepository);
    loadableStudyService.confirmPlan(this.createConfirmPlan(), responseObserver);
    List<ConfirmPlanReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testConfirmPlanInvalidLoadablePatternId() {
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    StreamRecorder<ConfirmPlanReply> responseObserver = StreamRecorder.create();
    Mockito.when(loadablePatternService.confirmPlan(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePatternService, "loadablePatternRepository", this.loadablePatternRepository);
    loadableStudyService.confirmPlan(this.createConfirmPlan(), responseObserver);
    List<ConfirmPlanReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, results.get(0).getResponseStatus().getCode());
  }

  @Test
  void testConfirmPlanRuntimeException() {
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<ConfirmPlanReply> responseObserver = StreamRecorder.create();
    Mockito.when(loadablePatternService.confirmPlan(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePatternService, "loadablePatternRepository", this.loadablePatternRepository);
    loadableStudyService.confirmPlan(this.createConfirmPlan(), responseObserver);
    List<ConfirmPlanReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /** @return ConfirmPlanRequest */
  private ConfirmPlanRequest createConfirmPlan() {
    ConfirmPlanRequest.Builder builder = ConfirmPlanRequest.newBuilder();
    builder.setLoadablePatternId(1L);
    return builder.build();
  }

  /** @return Object */
  private LoadablePattern createLoadablePattern() {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePattern.setLoadableStudy(getLS());
    loadablePattern.setLoadableStudyStatus(1L);
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setVesselXId(1L);
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    voyage.setVoyageNo(VOYAGE);
    loadableStudy.setVoyage(voyage);
    loadablePattern.setLoadableStudy(loadableStudy);
    loadablePattern.setCaseNumber(1);
    loadablePattern.setCreatedDate(LocalDate.now());
    return loadablePattern;
  }

  private LoadableStudy getLS() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setName("2");
    loadableStudy.setDetails("1");
    loadableStudy.setVesselXId(1L);
    loadableStudy.setVoyage(getVoyage());
    return loadableStudy;
  }
  /**
   * testGetLoadablePlanDetails
   *
   * <p>void
   */
  @Test
  void testGetLoadablePlanDetails()
      throws GenericServiceException, InstantiationException, IllegalAccessException {

    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    Mockito.doReturn(this.createVesselReply().build())
        .when(spyService)
        .getVesselTanks(any(VesselRequest.class));

    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(createLoadablePattern()));

    when(this.loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(preparePlanQuantity());

    when(this.loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(preparePlanCommingleDetails());

    when(this.loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(preparePlanStowageDetails());

    when(this.loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(preparePlanBallastDetails());

    when(this.loadablePlanCommentsRepository.findByLoadablePatternAndIsActiveOrderByIdDesc(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(preparePlanComments());

    LoadablePlanService spyPlanService = Mockito.spy(this.loadablePlanService);
    List<VesselInfo.VesselTankOrder> tankOrderList = new ArrayList<>();
    VesselInfo.VesselTankOrder tankOrder =
        VesselInfo.VesselTankOrder.newBuilder().setTankId(1l).build();
    tankOrderList.add(tankOrder);
    VesselInfo.VesselTankResponse tankResponse =
        VesselInfo.VesselTankResponse.newBuilder().addAllVesselTankOrder(tankOrderList).build();
    when(loadablePlanService.getVesselTanks(any(VesselRequest.class)))
        .thenReturn(this.createVesselReply12().build());
    when(loadablePlanService.getVesselTankDetailsByTankIds(any(VesselInfo.VesselTankRequest.class)))
        .thenReturn(tankResponse);

    Voyage voyage = new Voyage();
    voyage.setId(1l);
    Set<LoadableStudyPortRotation> portRotationSet = new HashSet<>();
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setId(1l);
    portRotation.setEta(LocalDateTime.now());
    portRotation.setEtd(LocalDateTime.now());
    CargoOperation operation = new CargoOperation();
    portRotation.setOperation(operation);
    portRotation.setLayCanTo(LocalDate.now());
    portRotation.setLayCanFrom(LocalDate.now());

    portRotationSet.add(portRotation);
    LoadableStudy loadableStudy = this.createLoadableStudyEntity(voyage);
    loadableStudy.setPortRotations(portRotationSet);
    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(loadableStudy));
    when(loadablePlanService.getLoadablePlanDetails(
            any(LoadablePlanDetailsRequest.class), any(LoadablePlanDetailsReply.Builder.class)))
        .thenCallRealMethod();
    when(loadablePlanService.validateLoadableStudyForConfimPlan(any(LoadableStudy.class)))
        .thenCallRealMethod();
    doCallRealMethod()
        .when(loadablePlanService)
        .buildLoadablePlanDetails(any(Optional.class), any(LoadablePlanDetailsReply.Builder.class));
    when(onBoardQuantityRepository.findByLoadableStudyAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(new ArrayList<>());

    ReflectionTestUtils.setField(
        loadablePlanService, "onBoardQuantityRepository", onBoardQuantityRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePlanQuantityRepository", loadablePlanQuantityRepository);
    ReflectionTestUtils.setField(
        loadablePlanService,
        "loadablePlanCommingleDetailsRepository",
        loadablePlanCommingleDetailsRepository);
    ReflectionTestUtils.setField(
        loadablePlanService,
        "loadablePlanStowageDetailsRespository",
        loadablePlanStowageDetailsRespository);
    ReflectionTestUtils.setField(
        loadablePlanService,
        "loadablePlanBallastDetailsRepository",
        loadablePlanBallastDetailsRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePlanCommentsRepository", loadablePlanCommentsRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "stowageDetailsTempRepository", stowageDetailsTempRepository);
    ReflectionTestUtils.setField(
        loadablePlanService,
        "loadablePatternAlgoStatusRepository",
        loadablePatternAlgoStatusRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "loadableQuantityRepository", loadableQuantityRepository);
    ReflectionTestUtils.setField(
        loadablePlanService,
        "loadablePatternAlgoStatusRepository",
        loadablePatternAlgoStatusRepository);

    StreamRecorder<LoadablePlanDetailsReply> responseObserver = StreamRecorder.create();
    spyService.getLoadablePlanDetails(this.createGetLoadablePlanDetails(), responseObserver);
    List<LoadablePlanDetailsReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  }

  private VesselReply.Builder createVesselReply12() {
    VesselReply.Builder builder = VesselReply.newBuilder();
    List<VesselInfo.VesselTankDetail> list = new ArrayList<>();
    VesselTankDetail vesselTankDetail = VesselTankDetail.newBuilder().build();
    list.add(vesselTankDetail);
    builder.addAllVesselTanks(list);
    builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return builder;
  }

  /**
   * testGetLoadablePlanDetailsInvalidTankDetails
   *
   * <p>void
   */
  @Test
  void testGetLoadablePlanDetailsInvalidTankDetails() {

    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    Mockito.doReturn(
            this.createVesselReply()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build())
        .when(spyService)
        .getVesselTanks(any(VesselRequest.class));

    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(createLoadablePattern()));

    when(this.loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(preparePlanQuantity());

    when(this.loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(preparePlanCommingleDetails());

    when(this.loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(preparePlanStowageDetails());

    when(this.loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(preparePlanBallastDetails());
    try {
      Mockito.when(loadablePlanService.getLoadablePlanDetails(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", this.loadablePatternRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePlanQuantityRepository", this.loadablePlanQuantityRepository);
    ReflectionTestUtils.setField(
        loadablePlanService,
        "loadablePlanCommingleDetailsRepository",
        this.loadablePlanCommingleDetailsRepository);

    StreamRecorder<LoadablePlanDetailsReply> responseObserver = StreamRecorder.create();
    spyService.getLoadablePlanDetails(this.createGetLoadablePlanDetails(), responseObserver);
    List<LoadablePlanDetailsReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /**
   * testGetLoadablePlanDetailsInvalidLoadablePatternId
   *
   * <p>void
   */
  @Test
  void testGetLoadablePlanDetailsInvalidLoadablePatternId() {
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    StreamRecorder<LoadablePlanDetailsReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePlanService.getLoadablePlanDetails(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", this.loadablePatternRepository);
    loadableStudyService.getLoadablePlanDetails(
        this.createGetLoadablePlanDetails(), responseObserver);
    List<LoadablePlanDetailsReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, results.get(0).getResponseStatus().getCode());
  }

  /**
   * testGetLoadablePlanDetailsRuntimeException
   *
   * <p>void
   */
  @Test
  void testGetLoadablePlanDetailsRuntimeException() {
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<LoadablePlanDetailsReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePlanService.getLoadablePlanDetails(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", this.loadablePatternRepository);
    loadableStudyService.getLoadablePlanDetails(
        this.createGetLoadablePlanDetails(), responseObserver);
    List<LoadablePlanDetailsReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /** @return List<LoadablePlanComments> */
  private List<LoadablePlanComments> preparePlanComments() {
    List<LoadablePlanComments> loadablePlanComments = new ArrayList<LoadablePlanComments>();
    LoadablePlanComments planComments = new LoadablePlanComments();
    planComments.setCreatedDateTime(LocalDateTime.now());
    loadablePlanComments.add(planComments);
    return loadablePlanComments;
  }

  /** @return List<LoadablePlanBallastDetails> */
  private List<LoadablePlanBallastDetails> preparePlanBallastDetails() {
    List<LoadablePlanBallastDetails> loadablePlanBallastDetails =
        new ArrayList<LoadablePlanBallastDetails>();
    LoadablePlanBallastDetails ballastDetails = new LoadablePlanBallastDetails();
    ballastDetails.setTankId(1l);
    loadablePlanBallastDetails.add(ballastDetails);
    return loadablePlanBallastDetails;
  }

  /** @return List<LoadablePlanStowageDetails> */
  private List<LoadablePlanStowageDetails> preparePlanStowageDetails() {
    List<LoadablePlanStowageDetails> loadablePlanStowageDetails =
        new ArrayList<LoadablePlanStowageDetails>();
    LoadablePlanStowageDetails stowageDetails = new LoadablePlanStowageDetails();
    stowageDetails.setTankId(1l);
    loadablePlanStowageDetails.add(stowageDetails);
    return loadablePlanStowageDetails;
  }

  /** @return List<LoadablePlanCommingleDetails> */
  private List<LoadablePlanCommingleDetails> preparePlanCommingleDetails() {
    List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails =
        new ArrayList<LoadablePlanCommingleDetails>();
    LoadablePlanCommingleDetails commingleDetails = new LoadablePlanCommingleDetails();
    commingleDetails.setTankId(1l);
    loadablePlanCommingleDetails.add(commingleDetails);
    return loadablePlanCommingleDetails;
  }

  /** @return List<LoadablePlanQuantity> */
  private List<LoadablePlanQuantity> preparePlanQuantity() {
    List<LoadablePlanQuantity> loadablePlanQuantities = new ArrayList<LoadablePlanQuantity>();
    LoadablePlanQuantity lpq = new LoadablePlanQuantity();
    lpq.setCargoColor("1");
    lpq.setCargoAbbreviation("1");
    lpq.setTimeRequiredForLoading("1");
    lpq.setCargoNominationId(1l);

    loadablePlanQuantities.add(lpq);
    return loadablePlanQuantities;
  }

  /** @return LoadablePlanDetailsRequest */
  private LoadablePlanDetailsRequest createGetLoadablePlanDetails() {
    LoadablePlanDetailsRequest.Builder builder = LoadablePlanDetailsRequest.newBuilder();
    builder.setLoadablePatternId(1L);
    return builder.build();
  }

  /**
   * test GetLoadable Study Status
   *
   * <p>void
   */
  @Test
  void testGetLoadableStudyStatus() {
    when(this.loadableStudyAlgoStatusRepository.findByLoadableStudyIdAndProcessIdAndIsActive(
            anyLong(), anyString(), anyBoolean()))
        .thenReturn(Optional.of(createLoadableStudyAlgoStatus()));

    StreamRecorder<LoadableStudyStatusReply> responseObserver = StreamRecorder.create();

    loadableStudyService.getLoadableStudyStatus(
        this.createGetLoadableStudyStatus(), responseObserver);
    List<LoadableStudyStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  }

  /** @return Object */
  private LoadableStudyAlgoStatus createLoadableStudyAlgoStatus() {
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setId(4L);
    LoadableStudyAlgoStatus loadableStudyAlgoStatus = new LoadableStudyAlgoStatus();
    loadableStudyAlgoStatus.setLastModifiedDateTime(LocalDateTime.now());
    loadableStudyAlgoStatus.setLoadableStudyStatus(loadableStudyStatus);
    return loadableStudyAlgoStatus;
  }

  /**
   * test GetLoadable Study Status Invalid Loadable Study Id
   *
   * <p>void
   */
  @Test
  void testGetLoadableStudyStatusInvalidLoadableStudyId() {
    when(this.loadableStudyAlgoStatusRepository.findByLoadableStudyIdAndProcessIdAndIsActive(
            anyLong(), anyString(), anyBoolean()))
        .thenReturn(Optional.empty());
    StreamRecorder<LoadableStudyStatusReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.getLoadableStudyStatus(
        this.createGetLoadableStudyStatus(), responseObserver);
    List<LoadableStudyStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, results.get(0).getResponseStatus().getCode());
  }

  /** @return LoadableStudyStatusRequest */
  private LoadableStudyStatusRequest createGetLoadableStudyStatus() {
    LoadableStudyStatusRequest.Builder builder = LoadableStudyStatusRequest.newBuilder();
    builder.setLoadableStudyId(1L);
    return builder.build();
  }

  /**
   * test GetLoadable Study Status Runtime Exception
   *
   * <p>void
   */
  @Test
  void testGetLoadableStudyStatusRuntimeException() {
    when(this.loadableStudyAlgoStatusRepository.findByLoadableStudyIdAndProcessIdAndIsActive(
            anyLong(), anyString(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<LoadableStudyStatusReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.getLoadableStudyStatus(
        this.createGetLoadableStudyStatus(), responseObserver);
    List<LoadableStudyStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /**
   * test Get Loadable Pattern Commingle Details
   *
   * <p>void
   */
  @Test
  void testGetLoadablePatternCommingleDetails() {
    when(this.loadablePlanCommingleDetailsRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new LoadablePlanCommingleDetails()));

    StreamRecorder<LoadablePatternCommingleDetailsReply> responseObserver = StreamRecorder.create();
    Mockito.when(
            loadablePatternService.getLoadablePatternCommingleDetails(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePatternService,
        "loadablePlanCommingleDetailsRepository",
        this.loadablePlanCommingleDetailsRepository);
    loadableStudyService.getLoadablePatternCommingleDetails(
        this.createLoadablePatternCommingleDetails(), responseObserver);
    List<LoadablePatternCommingleDetailsReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  }

  /**
   * test Get Loadable Pattern Commingle Details Invalid Loadable Pattern Id
   *
   * <p>void
   */
  @Test
  void testGetLoadablePatternCommingleDetailsInvalidLoadablePatternId() {
    when(this.loadablePlanCommingleDetailsRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    StreamRecorder<LoadablePatternCommingleDetailsReply> responseObserver = StreamRecorder.create();
    Mockito.when(
            loadablePatternService.getLoadablePatternCommingleDetails(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePatternService,
        "loadablePlanCommingleDetailsRepository",
        this.loadablePlanCommingleDetailsRepository);
    loadableStudyService.getLoadablePatternCommingleDetails(
        this.createLoadablePatternCommingleDetails(), responseObserver);
    List<LoadablePatternCommingleDetailsReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /**
   * run time exception
   *
   * <p>void
   */
  @Test
  void testGetLoadablePatternCommingleDetailsRuntimeException() {
    when(this.loadablePlanCommingleDetailsRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<LoadablePatternCommingleDetailsReply> responseObserver = StreamRecorder.create();
    Mockito.when(
            loadablePatternService.getLoadablePatternCommingleDetails(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePatternService,
        "loadablePlanCommingleDetailsRepository",
        this.loadablePlanCommingleDetailsRepository);
    loadableStudyService.getLoadablePatternCommingleDetails(
        this.createLoadablePatternCommingleDetails(), responseObserver);
    List<LoadablePatternCommingleDetailsReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /** @return LoadablePatternCommingleDetailsRequest */
  private LoadablePatternCommingleDetailsRequest createLoadablePatternCommingleDetails() {
    LoadablePatternCommingleDetailsRequest.Builder builder =
        LoadablePatternCommingleDetailsRequest.newBuilder();
    builder.setLoadablePatternCommingleDetailsId(1L);
    return builder.build();
  }

  /** Test loadable study duplicating */
  @ParameterizedTest
  @ValueSource(longs = {7L, 0L, 2L})
  void testDuplicateLoadableStudy(Long loadlineXId) {
    LoadableStudyDetail.Builder requestBuilder = this.createLoadableStudySaveRequest();
    if (loadlineXId.equals(0L)) {
      requestBuilder.setDraftRestriction(NUMERICAL_TEST_VALUE);
    }
    requestBuilder.setLoadLineXId(loadlineXId);
    LoadableStudy entity = new LoadableStudy();

    CargoNomination cargoNomination = new CargoNomination();
    List<CargoNomination> cargoNominationList = new ArrayList<CargoNomination>();

    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    List<LoadableStudyPortRotation> loadableStudyPortRotationList =
        new ArrayList<LoadableStudyPortRotation>();

    OnHandQuantity onHandQuantity = new OnHandQuantity();
    List<OnHandQuantity> onHandQuantityList = new ArrayList<OnHandQuantity>();

    OnBoardQuantity onBoardQuantity = new OnBoardQuantity();
    List<OnBoardQuantity> onBoardQuantityList = new ArrayList<OnBoardQuantity>();

    LoadableQuantity loadableQuantity = new LoadableQuantity();
    List<LoadableQuantity> loadableQuantityList = new ArrayList<LoadableQuantity>();

    SynopticalTable synopticalTable = new SynopticalTable();
    List<SynopticalTable> synopticalTableList = new ArrayList<SynopticalTable>();

    CommingleCargo cargo = new CommingleCargo();
    List<CommingleCargo> cargoList = new ArrayList<CommingleCargo>();

    entity.setId(2L);
    cargoNomination.setLoadableStudyXId(entity.getId());
    cargoNominationList.add(cargoNomination);

    loadableStudyPortRotation.setLoadableStudy(entity);
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setName("1");
    cargoOperation.setId(1l);
    loadableStudyPortRotation.setOperation(cargoOperation);
    loadableStudyPortRotation.setPortXId(1l);
    loadableStudyPortRotation.setPortOrder(1l);
    loadableStudyPortRotationList.add(loadableStudyPortRotation);

    onHandQuantity.setLoadableStudy(entity);
    onHandQuantity.setPortRotation(loadableStudyPortRotation);
    onHandQuantityList.add(onHandQuantity);

    onBoardQuantity.setLoadableStudy(entity);
    onBoardQuantityList.add(onBoardQuantity);

    loadableQuantity.setLoadableStudyXId(entity);
    loadableQuantity.setLoadableStudyPortRotation(loadableStudyPortRotation);
    loadableQuantityList.add(loadableQuantity);

    synopticalTable.setLoadableStudyXId(entity.getId());
    synopticalTable.setLoadableStudyPortRotation(loadableStudyPortRotation);
    synopticalTableList.add(synopticalTable);

    cargo.setLoadableStudyXId(entity.getId());
    cargoList.add(cargo);

    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), Mockito.anyBoolean()))
        .thenReturn(Optional.of(entity));

    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
            anyLong(), Mockito.anyBoolean()))
        .thenReturn(cargoNominationList);
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), Mockito.anyBoolean()))
        .thenReturn(loadableStudyPortRotationList);
    when(this.onHandQuantityRepository.findByLoadableStudyAndIsActive(any(), Mockito.anyBoolean()))
        .thenReturn(onHandQuantityList);
    when(this.onBoardQuantityRepository.findByLoadableStudyAndIsActive(any(), Mockito.anyBoolean()))
        .thenReturn(onBoardQuantityList);
    when(this.loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
            anyLong(), Mockito.anyBoolean()))
        .thenReturn(loadableQuantityList);
    when(this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
            anyLong(), Mockito.anyBoolean()))
        .thenReturn(cargoList);
    when(this.synopticalTableRepository.findByLoadableStudyXIdAndIsActive(
            anyLong(), Mockito.anyBoolean()))
        .thenReturn(synopticalTableList);

    Mockito.doNothing().when(this.entityManager).detach(any(CargoNomination.class));
    Mockito.doNothing().when(this.entityManager).detach(any(LoadableStudyPortRotation.class));
    Mockito.doNothing().when(this.entityManager).detach(any(OnHandQuantity.class));
    Mockito.doNothing().when(this.entityManager).detach(any(OnBoardQuantity.class));
    Mockito.doNothing().when(this.entityManager).detach(any(LoadableQuantity.class));
    Mockito.doNothing().when(this.entityManager).detach(any(SynopticalTable.class));
    Mockito.doNothing().when(this.entityManager).detach(any(CommingleCargo.class));

    when(loadableStudyPortRotationRepository.saveAll(anyList()))
        .thenReturn(loadableStudyPortRotationList);
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(requestBuilder.build(), responseObserver);
    File file = new File(this.rootFolder);
    deleteFolder(file);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getId());
  }

  private CargoOperation getCO() {
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setName("hello");
    cargoOperation.setId(2L);
    return cargoOperation;
  }

  /** Test loadable study editing */
  @ParameterizedTest
  @ValueSource(longs = {7L, 0L, 2L})
  void testEditLoadableStudy(Long loadlineXId) {
    LoadableStudyDetail.Builder requestBuilder = this.createLoadableStudySaveRequest();
    requestBuilder.setId(2L);
    if (loadlineXId.equals(0L)) {
      requestBuilder.setDraftRestriction(NUMERICAL_TEST_VALUE);
    }
    requestBuilder.setLoadLineXId(loadlineXId);
    LoadableStudy entity = new LoadableStudy();

    CargoNomination cargoNomination = new CargoNomination();
    List<CargoNomination> cargoNominationList = new ArrayList<CargoNomination>();

    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    List<LoadableStudyPortRotation> loadableStudyPortRotationList =
        new ArrayList<LoadableStudyPortRotation>();

    OnHandQuantity onHandQuantity = new OnHandQuantity();
    List<OnHandQuantity> onHandQuantityList = new ArrayList<OnHandQuantity>();

    OnBoardQuantity onBoardQuantity = new OnBoardQuantity();
    List<OnBoardQuantity> onBoardQuantityList = new ArrayList<OnBoardQuantity>();

    LoadableQuantity loadableQuantity = new LoadableQuantity();
    List<LoadableQuantity> loadableQuantityList = new ArrayList<LoadableQuantity>();

    SynopticalTable synopticalTable = new SynopticalTable();
    List<SynopticalTable> synopticalTableList = new ArrayList<SynopticalTable>();

    CommingleCargo cargo = new CommingleCargo();
    List<CommingleCargo> cargoList = new ArrayList<CommingleCargo>();

    entity.setId(2L);
    cargoNomination.setLoadableStudyXId(entity.getId());
    cargoNominationList.add(cargoNomination);

    loadableStudyPortRotation.setLoadableStudy(entity);
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setName("1");
    cargoOperation.setId(1l);
    loadableStudyPortRotation.setOperation(cargoOperation);
    loadableStudyPortRotation.setPortXId(1l);
    loadableStudyPortRotation.setPortOrder(1l);
    loadableStudyPortRotationList.add(loadableStudyPortRotation);

    onHandQuantity.setLoadableStudy(entity);
    onHandQuantity.setPortRotation(loadableStudyPortRotation);
    onHandQuantityList.add(onHandQuantity);

    onBoardQuantity.setLoadableStudy(entity);
    onBoardQuantityList.add(onBoardQuantity);

    loadableQuantity.setLoadableStudyXId(entity);
    loadableQuantity.setLoadableStudyPortRotation(loadableStudyPortRotation);
    loadableQuantityList.add(loadableQuantity);

    synopticalTable.setLoadableStudyXId(entity.getId());
    synopticalTable.setLoadableStudyPortRotation(loadableStudyPortRotation);
    synopticalTableList.add(synopticalTable);

    cargo.setLoadableStudyXId(entity.getId());
    cargoList.add(cargo);

    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), Mockito.anyBoolean()))
        .thenReturn(Optional.of(entity));

    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
            anyLong(), Mockito.anyBoolean()))
        .thenReturn(cargoNominationList);
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), Mockito.anyBoolean()))
        .thenReturn(loadableStudyPortRotationList);
    when(this.onHandQuantityRepository.findByLoadableStudyAndIsActive(any(), Mockito.anyBoolean()))
        .thenReturn(onHandQuantityList);
    when(this.onBoardQuantityRepository.findByLoadableStudyAndIsActive(any(), Mockito.anyBoolean()))
        .thenReturn(onBoardQuantityList);
    when(this.loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
            anyLong(), Mockito.anyBoolean()))
        .thenReturn(loadableQuantityList);
    when(this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
            anyLong(), Mockito.anyBoolean()))
        .thenReturn(cargoList);
    when(this.synopticalTableRepository.findByLoadableStudyXIdAndIsActive(
            anyLong(), Mockito.anyBoolean()))
        .thenReturn(synopticalTableList);

    Mockito.doNothing().when(this.entityManager).detach(any(CargoNomination.class));
    Mockito.doNothing().when(this.entityManager).detach(any(LoadableStudyPortRotation.class));
    Mockito.doNothing().when(this.entityManager).detach(any(OnHandQuantity.class));
    Mockito.doNothing().when(this.entityManager).detach(any(OnBoardQuantity.class));
    Mockito.doNothing().when(this.entityManager).detach(any(LoadableQuantity.class));
    Mockito.doNothing().when(this.entityManager).detach(any(SynopticalTable.class));
    Mockito.doNothing().when(this.entityManager).detach(any(CommingleCargo.class));

    when(loadableStudyPortRotationRepository.saveAll(anyList()))
        .thenReturn(loadableStudyPortRotationList);
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(requestBuilder.build(), responseObserver);
    File file = new File(this.rootFolder);
    deleteFolder(file);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getId());
  }

  /** Test loadable study editing when entity is null */
  @ParameterizedTest
  @ValueSource(longs = {7L, 0L, 2L})
  void testEditLoadableStudyWithEntityNull(Long loadlineXId) {
    LoadableStudyDetail.Builder requestBuilder = this.createLoadableStudySaveRequest();
    requestBuilder.setId(2L);
    if (loadlineXId.equals(0L)) {
      requestBuilder.setDraftRestriction(NUMERICAL_TEST_VALUE);
    }
    requestBuilder.setLoadLineXId(loadlineXId);
    LoadableStudy entity = new LoadableStudy();

    entity.setId(2L);

    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));

    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), Mockito.anyBoolean()))
        .thenReturn(Optional.empty());

    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(requestBuilder.build(), responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
    assertEquals(0L, replies.get(0).getId());
  }

  /** Test loadable study editing with exception */
  @ParameterizedTest
  @ValueSource(longs = {7L, 0L, 2L})
  void testEditLoadableStudyWithException(Long loadlineXId) {
    LoadableStudyDetail.Builder requestBuilder = this.createLoadableStudySaveRequest();
    requestBuilder.setId(2L);
    if (loadlineXId.equals(0L)) {
      requestBuilder.setDraftRestriction(NUMERICAL_TEST_VALUE);
    }
    requestBuilder.setLoadLineXId(loadlineXId);
    LoadableStudy entity = new LoadableStudy();

    entity.setId(2L);

    when(this.voyageRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createVoyageEntity()));
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));

    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), Mockito.anyBoolean()))
        .thenThrow(NullPointerException.class);

    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<LoadableStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveLoadableStudy(requestBuilder.build(), responseObserver);
    List<LoadableStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
    assertEquals(0L, replies.get(0).getId());
  }

  @SuppressWarnings("unchecked")
  @Test
  void testGetLoadicatorDataRuntimeException() {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    AlgoResponse algoResponse = new AlgoResponse();
    algoResponse.setProcessId("");
    Mockito.when(
            restTemplate.postForObject(
                anyString(),
                any(com.cpdss.loadablestudy.domain.LoadicatorAlgoRequest.class),
                any(Class.class)))
        .thenThrow(RuntimeException.class);

    StreamRecorder<LoadicatorDataReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadicatorService.getLoadicatorData(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (Exception e) {
      e.printStackTrace();
    }
    spyService.getLoadicatorData(this.createLoadicatorDataRequest(), responseObserver);
    List<LoadicatorDataReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  //  @SuppressWarnings("unchecked")
  //  @Test
  //  void testGetLoadicatorData() {
  //    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
  //    AlgoResponse algoResponse = new AlgoResponse();
  //    algoResponse.setProcessId("");
  //
  //    //    //    Mockito.when(
  //    //                restTemplate.postForObject(
  //    //                        anyString(),
  //    //                        any(com.cpdss.loadablestudy.domain.LoadicatorAlgoRequest.class),
  //    //                        any(Class.class)))
  //    //                .thenReturn(algoResponse);
  //    Mockito.when(
  //                    loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(),
  // Mockito.anyBoolean()))
  //            .thenReturn(getOLS());
  //    Mockito.when(
  //                    restTemplate.postForObject(
  //                            Mockito.anyString(),
  //
  // Mockito.any(com.cpdss.loadablestudy.domain.LoadicatorAlgoRequest.class),
  //                            Mockito.any(Class.class)))
  //            .thenReturn(getLAR());
  //    Mockito.when(
  //                    this.loadablePatternRepository.findByIdAndIsActive(
  //                            Mockito.anyLong(), Mockito.anyBoolean()))
  //            .thenReturn(getOLP());
  //    Mockito.when(
  //
  // this.loadableStudyCommunicationStatusRepository.findByReferenceIdAndMessageType(
  //                            Mockito.anyLong(), Mockito.anyString()))
  //            .thenReturn(getOLSCS());
  //    Mockito.when(
  //                    cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
  //                            Mockito.anyLong(), Mockito.anyBoolean()))
  //            .thenReturn(getLCN());
  //    StreamRecorder<LoadicatorDataReply> responseObserver = StreamRecorder.create();
  //    try {
  //      Mockito.when(loadicatorService.getLoadicatorData(Mockito.any(), Mockito.any()))
  //              .thenCallRealMethod();
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //    }
  //    ReflectionTestUtils.setField(
  //            loadicatorService, "loadableStudyRepository", this.loadableStudyRepository);
  //    ReflectionTestUtils.setField(loadicatorService, "rootFolder", "D:\\Data");
  //    ReflectionTestUtils.setField(
  //            loadicatorService, "loadableStudyService", this.loadableStudyService);
  //    ReflectionTestUtils.setField(
  //            loadicatorService, "loadablePatternRepository", this.loadablePatternRepository);
  //    ReflectionTestUtils.setField(loadicatorService, "restTemplate", this.restTemplate);
  //    ReflectionTestUtils.setField(loadicatorService, "jsonDataService", this.jsonDataService);
  //    ReflectionTestUtils.setField(
  //            loadicatorService, "loadicatorUrl",
  // "http://54.151.227.169:8080/loadicator_results/");
  //    ReflectionTestUtils.setField(
  //            cargoNominationService, "cargoNominationRepository",
  // this.cargoNominationRepository);
  //    ReflectionTestUtils.setField(
  //            loadicatorService,
  //            "loadableStudyCommunicationStatusRepository",
  //            this.loadableStudyCommunicationStatusRepository);
  //    spyService.getLoadicatorData(this.createLoadicatorDataRequest(), responseObserver);
  //    List<LoadicatorDataReply> results = responseObserver.getValues();
  //    assertEquals(1, results.size());
  //    assertNull(responseObserver.getError());
  //    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  //  }

  private List<CargoNomination> getLCN() {
    List<CargoNomination> list = new ArrayList<>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setCargoXId(1L);
    list.add(cargoNomination);
    return list;
  }

  private Optional<LoadableStudyCommunicationStatus> getOLSCS() {
    LoadableStudyCommunicationStatus loadableStudyCommunicationStatus =
        new LoadableStudyCommunicationStatus();
    return Optional.of(loadableStudyCommunicationStatus);
  }

  private LoadicatorAlgoResponse getLAR() {
    LoadicatorAlgoResponse loadicatorAlgoResponse = new LoadicatorAlgoResponse();
    loadicatorAlgoResponse.setFeedbackLoop(true);
    loadicatorAlgoResponse.setProcessId("1");
    loadicatorAlgoResponse.setFeedbackLoopCount(1);
    return loadicatorAlgoResponse;
  }
  /** @return LoadicatorDataRequest */
  private LoadicatorDataRequest createLoadicatorDataRequest() {
    List<LDIntactStability> list2 = new ArrayList<>();
    LDIntactStability ldIntactStability = LDIntactStability.newBuilder().build();
    list2.add(ldIntactStability);
    List<LDStrength> list1 = new ArrayList<>();
    LDStrength ldStrength = LDStrength.newBuilder().build();
    list1.add(ldStrength);
    List<LDtrim> list = new ArrayList<>();
    LDtrim lDtrim = LDtrim.newBuilder().setAftDraftValue("1").build();
    list.add(lDtrim);
    LoadicatorPatternDetails loadicatorPatternDetails =
        LoadicatorPatternDetails.newBuilder()
            .addAllLDIntactStability(list2)
            .setLoadablePatternId(1L)
            .addAllLDStrength(list1)
            .addAllLDtrim(list)
            .build();
    LoadicatorDataRequest.Builder builder =
        LoadicatorDataRequest.newBuilder()
            .addLoadicatorPatternDetails(loadicatorPatternDetails)
            .setProcessId("1")
            .setLoadableStudyId(1L)
            .setIsPattern(false);
    builder.addLoadicatorPatternDetails(buildLoadicatorPatternDetails());
    return builder.build();
  }

  /** @return LoadicatorPatternDetails */
  private LoadicatorPatternDetails buildLoadicatorPatternDetails() {
    LoadicatorPatternDetails.Builder builder = LoadicatorPatternDetails.newBuilder();
    builder.addLDIntactStability(buildLDIntactStability());
    builder.addLDStrength(buildLDStrength());
    builder.addLDtrim(buildLDtrim());
    return builder.build();
  }

  /** @return LDtrim */
  private LDtrim buildLDtrim() {
    LDtrim.Builder builder = LDtrim.newBuilder();
    return builder.build();
  }

  /** @return LDStrength */
  private LDStrength buildLDStrength() {
    LDStrength.Builder builder = LDStrength.newBuilder();
    return builder.build();
  }

  /** @return LDIntactStability */
  private LDIntactStability buildLDIntactStability() {
    LDIntactStability.Builder builder = LDIntactStability.newBuilder();
    return builder.build();
  }

  /** Test GetOnBoardQuantity */
  @Test
  void testGetOnBoardQuantity() {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    LoadableStudy loadableStudy = new LoadableStudy();
    Voyage voyage = new Voyage();
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(voyage);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(loadableStudy));
    Mockito.doReturn(this.createVesselReply().build())
        .when(spyService)
        .getVesselTanks(any(VesselRequest.class));
    Mockito.when(onBoardQuantityService.getVesselTanks(Mockito.any())).thenReturn(getVesselReply());

    StreamRecorder<OnBoardQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onBoardQuantityService.getOnBoardQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(onBoardQuantityService, "voyageRepository", this.voyageRepository);
    ReflectionTestUtils.setField(
        onBoardQuantityService, "onBoardQuantityRepository", this.onBoardQuantityRepository);
    ReflectionTestUtils.setField(
        onBoardQuantityService, "onHandQuantityService", this.onHandQuantityService);
    ReflectionTestUtils.setField(
        onBoardQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    spyService.getOnBoardQuantity(this.createOnBoardQuantityRequest(), responseObserver);
    List<OnBoardQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  }

  /** Test GetOnBoardQuantity when voyage is null */
  @Test
  void testGetOnBoardQuantityWithVoyageNull() {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    LoadableStudy loadableStudy = new LoadableStudy();
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(null);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(loadableStudy));
    Mockito.doReturn(this.createVesselReply().build())
        .when(spyService)
        .getVesselTanks(any(VesselRequest.class));

    StreamRecorder<OnBoardQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onBoardQuantityService.getOnBoardQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(onBoardQuantityService, "voyageRepository", this.voyageRepository);
    ReflectionTestUtils.setField(
        onBoardQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    spyService.getOnBoardQuantity(this.createOnBoardQuantityRequest(), responseObserver);
    List<OnBoardQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  /** Test GetOnBoardQuantity when loadable study is null */
  @Test
  void testGetOnBoardQuantityWithLoadableStudyNull() {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    Voyage voyage = new Voyage();
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(voyage);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    Mockito.doReturn(this.createVesselReply().build())
        .when(spyService)
        .getVesselTanks(any(VesselRequest.class));

    StreamRecorder<OnBoardQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onBoardQuantityService.getOnBoardQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(onBoardQuantityService, "voyageRepository", this.voyageRepository);
    ReflectionTestUtils.setField(
        onBoardQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    spyService.getOnBoardQuantity(this.createOnBoardQuantityRequest(), responseObserver);
    List<OnBoardQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  private OnBoardQuantityRequest createOnBoardQuantityRequest() {
    return OnBoardQuantityRequest.newBuilder()
        .setCompanyId(ID_TEST_VALUE)
        .setVesselId(ID_TEST_VALUE)
        .setLoadableStudyId(ID_TEST_VALUE)
        .setPortId(ID_TEST_VALUE)
        .setVoyageId(ID_TEST_VALUE)
        .build();
  }

  /** Test to save comment */
  @Test
  public void testSaveComment() throws GenericServiceException {
    SaveCommentRequest request =
        SaveCommentRequest.newBuilder().setComment("comment").setLoadablePatternId(1L).build();
    StreamRecorder<SaveCommentReply> responseObserver = StreamRecorder.create();

    LoadablePattern loadablePattern = new LoadablePattern();
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                request.getLoadablePatternId(), true))
        .thenReturn(Optional.of(loadablePattern));
    LoadablePlanComments comments = new LoadablePlanComments();
    Mockito.when(
            this.loadablePlanCommentsRepository.save(
                ArgumentMatchers.any(LoadablePlanComments.class)))
        .thenReturn(comments);
    Mockito.when(loadablePlanService.saveComment(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", this.loadablePatternRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePlanCommentsRepository", this.loadablePlanCommentsRepository);
    loadableStudyService.saveComment(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveCommentReply> results = responseObserver.getValues();

    assertEquals(1, results.size());
    SaveCommentReply response = results.get(0);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  /** Test to save comment with exception */
  @Test
  public void testSaveCommentWithException() throws GenericServiceException {
    SaveCommentRequest request =
        SaveCommentRequest.newBuilder().setComment("comment").setLoadablePatternId(1L).build();
    StreamRecorder<SaveCommentReply> responseObserver = StreamRecorder.create();

    LoadablePattern loadablePattern = new LoadablePattern();
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                request.getLoadablePatternId(), true))
        .thenReturn(Optional.of(loadablePattern));
    Mockito.when(
            this.loadablePlanCommentsRepository.save(
                ArgumentMatchers.any(LoadablePlanComments.class)))
        .thenThrow(NullPointerException.class);
    Mockito.when(loadablePlanService.saveComment(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", this.loadablePatternRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePlanCommentsRepository", this.loadablePlanCommentsRepository);
    loadableStudyService.saveComment(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveCommentReply> results = responseObserver.getValues();

    assertEquals(1, results.size());
    SaveCommentReply response = results.get(0);
    assertEquals(FAILED, response.getResponseStatus().getStatus());
  }

  /** Test to save comment when comment is null */
  @Test
  public void testSaveCommentWhenCommentNull() throws GenericServiceException {
    SaveCommentRequest request = SaveCommentRequest.newBuilder().setLoadablePatternId(1L).build();
    StreamRecorder<SaveCommentReply> responseObserver = StreamRecorder.create();

    LoadablePattern loadablePattern = new LoadablePattern();
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                request.getLoadablePatternId(), true))
        .thenReturn(Optional.of(loadablePattern));
    LoadablePlanComments comments = new LoadablePlanComments();
    Mockito.when(
            this.loadablePlanCommentsRepository.save(
                ArgumentMatchers.any(LoadablePlanComments.class)))
        .thenReturn(comments);
    Mockito.when(loadablePlanService.saveComment(Mockito.any(), Mockito.any()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", this.loadablePatternRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePlanCommentsRepository", this.loadablePlanCommentsRepository);
    loadableStudyService.saveComment(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveCommentReply> results = responseObserver.getValues();

    assertEquals(1, results.size());
    SaveCommentReply response = results.get(0);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadablePatternList() {
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new LoadableStudy()));
    List<LoadablePattern> patternList = new ArrayList<>();
    IntStream.of(1, 3)
        .forEach(
            i -> {
              patternList.add(this.createLoadablePattern());
            });
    when(this.loadablePatternRepository.findByLoadableStudyAndIsActive(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(patternList);
    StreamRecorder<LoadablePatternReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePatternService.getLoadablePatternList(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePatternService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadablePatternService, "loadablePatternRepository", this.loadablePatternRepository);
    this.loadableStudyService.getLoadablePatternList(
        LoadablePatternRequest.newBuilder().setLoadableStudyId(ID_TEST_VALUE).build(),
        responseObserver);
    List<LoadablePatternReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadablePatternListInvalidLs() {
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    StreamRecorder<LoadablePatternReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePatternService.getLoadablePatternList(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePatternService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadablePatternService, "loadablePatternRepository", this.loadablePatternRepository);
    this.loadableStudyService.getLoadablePatternList(
        LoadablePatternRequest.newBuilder().setLoadableStudyId(ID_TEST_VALUE).build(),
        responseObserver);
    List<LoadablePatternReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetLoadablePatternListRuntimeException() {
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    StreamRecorder<LoadablePatternReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePatternService.getLoadablePatternList(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePatternService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        loadablePatternService, "loadablePatternRepository", this.loadablePatternRepository);
    this.loadableStudyService.getLoadablePatternList(
        LoadablePatternRequest.newBuilder().setLoadableStudyId(ID_TEST_VALUE).build(),
        responseObserver);
    List<LoadablePatternReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  @ParameterizedTest
  @ValueSource(longs = {0L, 1L})
  void testSaveOnBoardQuantity(Long id) {
    if (id.equals(1L)) {
      when(this.onBoardQuantityRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
          .thenReturn(new OnBoardQuantity());
    }
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new LoadableStudy()));
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    LoadableStudy ls = new LoadableStudy();
    ls.setVoyage(voyage);
    OnBoardQuantity entity = new OnBoardQuantity();
    entity.setId(1L);
    entity.setLoadableStudy(ls);

    Mockito.when(this.voyageRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getVoyage());
    Mockito.when(
            this.onBoardQuantityRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(entity);
    when(this.onBoardQuantityRepository.save(any(OnBoardQuantity.class))).thenReturn(entity);
    StreamRecorder<OnBoardQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onBoardQuantityService.saveOnBoardQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        onBoardQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        onBoardQuantityService, "onBoardQuantityRepository", this.onBoardQuantityRepository);
    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
    ReflectionTestUtils.setField(onBoardQuantityService, "voyageService", this.voyageService);
    ReflectionTestUtils.setField(
        onBoardQuantityService, "loadablePatternService", this.loadablePatternService);
    this.loadableStudyService.saveOnBoardQuantity(
        this.createOnBoardQuantityDetailSaveRequest().build(), responseObserver);
    List<OnBoardQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveOnBoardQuantityWithLoadableStudyNull() {

    when(this.onBoardQuantityRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(new OnBoardQuantity());

    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.<LoadableStudy>empty());
    OnBoardQuantity entity = new OnBoardQuantity();
    entity.setId(1L);
    when(this.onBoardQuantityRepository.save(any(OnBoardQuantity.class))).thenReturn(entity);
    StreamRecorder<OnBoardQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onBoardQuantityService.saveOnBoardQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        onBoardQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        onBoardQuantityService, "onBoardQuantityRepository", this.onBoardQuantityRepository);
    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
    ReflectionTestUtils.setField(onBoardQuantityService, "voyageService", this.voyageService);
    this.loadableStudyService.saveOnBoardQuantity(
        this.createOnBoardQuantityDetailSaveRequest().build(), responseObserver);
    List<OnBoardQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveOnBoardQuantityWhenOBQNull() {

    when(this.onBoardQuantityRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(null);

    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new LoadableStudy()));
    OnBoardQuantity entity = new OnBoardQuantity();
    entity.setId(1L);
    when(this.onBoardQuantityRepository.save(any(OnBoardQuantity.class))).thenReturn(entity);
    StreamRecorder<OnBoardQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onBoardQuantityService.saveOnBoardQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        onBoardQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        onBoardQuantityService, "onBoardQuantityRepository", this.onBoardQuantityRepository);
    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
    ReflectionTestUtils.setField(onBoardQuantityService, "voyageService", this.voyageService);
    this.loadableStudyService.saveOnBoardQuantity(
        this.createOnBoardQuantityDetailSaveRequest().setId(1L).build(), responseObserver);
    List<OnBoardQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveOnBoardQuantityWithException() {

    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(NullPointerException.class);
    OnBoardQuantity entity = new OnBoardQuantity();
    entity.setId(1L);
    when(this.onBoardQuantityRepository.save(any(OnBoardQuantity.class))).thenReturn(entity);
    StreamRecorder<OnBoardQuantityReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(onBoardQuantityService.saveOnBoardQuantity(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        onBoardQuantityService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        onBoardQuantityService, "onBoardQuantityRepository", this.onBoardQuantityRepository);
    ReflectionTestUtils.setField(voyageService, "voyageRepository", this.voyageRepository);
    ReflectionTestUtils.setField(onBoardQuantityService, "voyageService", this.voyageService);
    this.loadableStudyService.saveOnBoardQuantity(
        this.createOnBoardQuantityDetailSaveRequest().build(), responseObserver);
    List<OnBoardQuantityReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, results.get(0).getResponseStatus().getStatus());
  }

  private OnBoardQuantityDetail.Builder createOnBoardQuantityDetailSaveRequest() {
    OnBoardQuantityDetail.Builder detail =
        OnBoardQuantityDetail.newBuilder()
            .setId(1L)
            .setTankId(1L)
            .setTankName("tank-1")
            .setCargoId(1L)
            .setCargoName("cargo-1")
            .setWeight("100")
            .setVolume("100");
    return detail;
  }

  @ValueSource(longs = {0L, 1L})
  @ParameterizedTest
  void testGetSynopticalTable(Long patternId)
      throws InstantiationException, IllegalAccessException {
    SynopticalTableReply.Builder replyBuilder = SynopticalTableReply.newBuilder();
    LoadableStudyService loadableStudyServiceSpyService = Mockito.spy(this.loadableStudyService);
    SynopticService synopticServiceSpyService = Mockito.spy(this.synopticService);
    this.setSynopticalGetRequestMocks(loadableStudyServiceSpyService, patternId);
    SynopticalTableRequest request =
        SynopticalTableRequest.newBuilder()
            .setLoadableStudyId(ID_TEST_VALUE)
            .setLoadablePatternId(patternId)
            .setVesselId(ID_TEST_VALUE)
            .build();
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    Mockito.when(this.loadableStudyRepository.findById(Mockito.anyLong())).thenReturn(getOLS());

    try {
      synopticService.getSynopticalTable(request, replyBuilder);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }

    ReflectionTestUtils.setField(
        synopticServiceSpyService, "loadableStudyRepository", this.loadableStudyRepository);
    ReflectionTestUtils.setField(
        synopticServiceSpyService, "synpoticServiceUtils", this.synpoticServiceUtils);
    ReflectionTestUtils.setField(
        loadableStudyServiceSpyService, "synopticService", synopticServiceSpyService);
    loadableStudyServiceSpyService.getSynopticalTable(request, responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetSynopticalTableInvalidLs() throws InstantiationException, IllegalAccessException {
    SynopticalTableReply.Builder replyBuilder = SynopticalTableReply.newBuilder();
    LoadableStudyService loadableStudyServiceSpyService = Mockito.spy(this.loadableStudyService);
    SynopticService synopticServiceSpyService = Mockito.spy(this.synopticService);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    SynopticalTableRequest request =
        SynopticalTableRequest.newBuilder()
            .setLoadableStudyId(ID_TEST_VALUE)
            .setLoadablePatternId(ID_TEST_VALUE)
            .setVesselId(ID_TEST_VALUE)
            .build();
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    try {
      synopticService.getSynopticalTable(request, replyBuilder);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadableStudyServiceSpyService, "synopticService", synopticServiceSpyService);
    ReflectionTestUtils.setField(
        synopticServiceSpyService, "loadableStudyRepository", this.loadableStudyRepository);
    loadableStudyServiceSpyService.getSynopticalTable(request, responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetSynopticalTableRuntimeException()
      throws InstantiationException, IllegalAccessException {
    SynopticalTableReply.Builder replyBuilder = SynopticalTableReply.newBuilder();
    LoadableStudyService loadableStudyServiceSpyService = Mockito.spy(this.loadableStudyService);
    SynopticService synopticServiceSpyService = Mockito.spy(this.synopticService);
    when(this.loadableStudyRepository.findById(anyLong())).thenThrow(RuntimeException.class);
    SynopticalTableRequest request =
        SynopticalTableRequest.newBuilder()
            .setLoadableStudyId(ID_TEST_VALUE)
            .setLoadablePatternId(ID_TEST_VALUE)
            .setVesselId(ID_TEST_VALUE)
            .build();
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    try {
      synopticService.getSynopticalTable(request, replyBuilder);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadableStudyServiceSpyService, "synopticService", synopticServiceSpyService);
    ReflectionTestUtils.setField(
        synopticServiceSpyService, "loadableStudyRepository", this.loadableStudyRepository);
    loadableStudyServiceSpyService.getSynopticalTable(request, responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testGetSynopticalTableInvalidPortReply(int repetition)
      throws InstantiationException, IllegalAccessException {
    SynopticalTableReply.Builder replyBuilder = SynopticalTableReply.newBuilder();
    LoadableStudyService loadableStudyServiceSpyService = Mockito.spy(this.loadableStudyService);
    SynopticService synopticServiceSpyService = Mockito.spy(this.synopticService);
    Voyage voyage = (Voyage) createDummyObject(Voyage.class);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(this.synopticalTableRepository.findByLoadableStudyXIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(this.createSynopticalEntities());
    Mockito.doReturn(this.createSynopticalVesselReply().build())
        .when(loadableStudyServiceSpyService)
        .getVesselDetailForSynopticalTable(any(VesselRequest.class));
    if (repetition == 1) {
      Mockito.doReturn(
              PortReply.newBuilder()
                  .setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build())
                  .build())
          .when(loadableStudyServiceSpyService)
          .getPortInfo(any(GetPortInfoByPortIdsRequest.class));
    } else {
      Mockito.doReturn(null)
          .when(loadableStudyServiceSpyService)
          .getPortInfo(any(GetPortInfoByPortIdsRequest.class));
    }
    SynopticalTableRequest request =
        SynopticalTableRequest.newBuilder()
            .setLoadableStudyId(ID_TEST_VALUE)
            .setLoadablePatternId(ID_TEST_VALUE)
            .setVesselId(ID_TEST_VALUE)
            .build();
    try {
      synopticService.getSynopticalTable(request, replyBuilder);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadableStudyServiceSpyService, "synopticService", synopticServiceSpyService);
    ReflectionTestUtils.setField(
        synopticServiceSpyService, "synopticalTableRepository", this.synopticalTableRepository);
    ReflectionTestUtils.setField(
        synopticServiceSpyService, "loadableStudyRepository", this.loadableStudyRepository);
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    loadableStudyServiceSpyService.getSynopticalTable(request, responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private void setSynopticalGetRequestMocks(LoadableStudyService spyService, Long patternId)
      throws InstantiationException, IllegalAccessException {
    Voyage voyage = (Voyage) createDummyObject(Voyage.class);
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.createLoadableStudyEntity(voyage)));
    when(this.synopticalTableRepository.findByLoadableStudyXIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(this.createSynopticalEntities());
    Mockito.doReturn(this.createSynopticalVesselReply().build())
        .when(spyService)
        .getVesselDetailForSynopticalTable(any(VesselRequest.class));
    Mockito.doReturn(this.createSynopticalPortReply())
        .when(spyService)
        .getPortInfo(any(GetPortInfoByPortIdsRequest.class));
    Mockito.when(
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
                any(LoadableStudy.class), anyBoolean()))
        .thenReturn(this.createSynopticalPortRotationEntityList(patternId > 0));
    Mockito.when(
            this.onBoardQuantityRepository.findByLoadableStudyAndIsActive(
                any(LoadableStudy.class), anyBoolean()))
        .thenReturn(this.createObqEntities());

    Mockito.when(
            this.onHandQuantityRepository.findByLoadableStudyAndIsActive(
                any(LoadableStudy.class), anyBoolean()))
        .thenReturn(this.createOhqEntities());

    Mockito.when(
            this.loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdAndIsActive(
                anyLong(), anyBoolean()))
        .thenReturn(this.createBallastEntities());

    //    Mockito.when(
    //            this.voyageRepository
    //
    // .findFirstByVoyageEndDateLessThanAndVesselXIdAndIsActiveAndVoyageStatusOrderByVoyageEndDateDesc(
    //                    any(LocalDateTime.class), anyLong(), anyBoolean(), any()))
    //        .thenReturn(voyage);

    Mockito.when(
            this.voyageRepository
                .findFirstByVesselXIdAndIsActiveAndVoyageStatusOrderByLastModifiedDateDesc(
                    anyLong(), anyBoolean(), any()))
        .thenReturn(voyage);
    Mockito.when(
            this.voyageHistoryRepository.findFirstByVoyageOrderByPortOrderDesc(any(Voyage.class)))
        .thenReturn((VoyageHistory) createDummyObject(VoyageHistory.class));
    Mockito.when(this.cargoHistoryRepository.findCargoHistory(anyLong(), anyLong()))
        .thenReturn(this.createCargoHistory());
    Mockito.when(
            this.loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
                anyLong(), anyBoolean()))
        .thenReturn(this.createSynopticalCargoDetails(patternId));
    Mockito.when(
            this.synopticalTableLoadicatorDataRepository
                .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                    any(SynopticalTable.class), anyLong(), anyBoolean()))
        .thenReturn(
            (com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData)
                createDummyObject(
                    com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData.class));
  }

  private List<LoadablePatternCargoDetails> createSynopticalCargoDetails(Long patternId) {
    List<LoadablePatternCargoDetails> list = new ArrayList<>();
    IntStream.of(1, 4)
        .forEach(
            i -> {
              try {
                LoadablePatternCargoDetails entity =
                    (LoadablePatternCargoDetails)
                        createDummyObject(LoadablePatternCargoDetails.class);
                entity.setOperationType(i % 2 == 0 ? OPERATION_TYPE_ARR : OPERATION_TYPE_DEP);
                list.add(entity);
              } catch (InstantiationException e) {
                e.printStackTrace();
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            });
    return list;
  }

  private VesselReply.Builder createSynopticalVesselReply() {
    List<Long> tankCategories = new ArrayList<>();
    tankCategories.addAll(OHQ_TANK_CATEGORIES);
    tankCategories.addAll(CARGO_TANK_CATEGORIES);
    tankCategories.addAll(BALLAST_TANK_CATEGORIES);
    VesselReply.Builder builder = VesselReply.newBuilder();
    tankCategories.forEach(
        i -> {
          VesselTankDetail.Builder detailBuilder = VesselTankDetail.newBuilder();
          detailBuilder.setTankId(ID_TEST_VALUE);
          detailBuilder.setTankCategoryId(Long.valueOf(i));
          detailBuilder.setTankCategoryName("category-" + i);
          detailBuilder.setShortName("name" + i);
          detailBuilder.setFrameNumberFrom("from-" + i);
          detailBuilder.setFrameNumberTo("to" + i);
          detailBuilder.setShowInOhqObq(true);
          builder.addVesselTanks(detailBuilder.build());
        });
    builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return builder;
  }

  private List<CargoHistory> createCargoHistory() {
    List<CargoHistory> list = new ArrayList<>();
    IntStream.of(1, 3)
        .forEach(
            i -> {
              try {
                list.add((CargoHistory) createDummyObject(CargoHistory.class));
              } catch (InstantiationException e) {
                e.printStackTrace();
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            });
    return list;
  }

  private LoadableStudy createLoadableStudyEntity(Voyage voyage)
      throws InstantiationException, IllegalAccessException {
    LoadableStudy entity = (LoadableStudy) createDummyObject(LoadableStudy.class);
    entity.setVoyage(voyage);
    return entity;
  }

  private List<LoadableStudyPortRotation> createSynopticalPortRotationEntityList(boolean hasEtd) {
    List<LoadableStudyPortRotation> list = new ArrayList<>();
    IntStream.of(1, 3)
        .forEach(
            i -> {
              try {
                LoadableStudyPortRotation entity =
                    (LoadableStudyPortRotation) createDummyObject(LoadableStudyPortRotation.class);
                if (!hasEtd) {
                  entity.setEtd(null);
                } else {
                  entity.setEta(null);
                }
                list.add(entity);
              } catch (InstantiationException e) {
                e.printStackTrace();
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            });
    return list;
  }

  private List<LoadablePlanStowageBallastDetails> createBallastEntities() {
    List<LoadablePlanStowageBallastDetails> list = new ArrayList<>();
    IntStream.of(1, 4)
        .forEach(
            i -> {
              try {

                LoadablePlanStowageBallastDetails entity =
                    (LoadablePlanStowageBallastDetails)
                        createDummyObject(LoadablePlanStowageBallastDetails.class);
                entity.setOperationType(i % 2 == 0 ? OPERATION_TYPE_ARR : OPERATION_TYPE_DEP);
                list.add(entity);

              } catch (InstantiationException e) {
                e.printStackTrace();
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            });
    return list;
  }

  private List<OnHandQuantity> createOhqEntities() {
    List<OnHandQuantity> list = new ArrayList<>();
    IntStream.of(1, 3)
        .forEach(
            i -> {
              try {
                list.add((OnHandQuantity) createDummyObject(OnHandQuantity.class));
              } catch (InstantiationException e) {
                e.printStackTrace();
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            });
    return list;
  }

  private List<OnBoardQuantity> createObqEntities() {
    List<OnBoardQuantity> list = new ArrayList<>();
    IntStream.of(1, 3)
        .forEach(
            i -> {
              try {
                list.add((OnBoardQuantity) createDummyObject(OnBoardQuantity.class));
              } catch (InstantiationException e) {
                e.printStackTrace();
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            });
    return list;
  }

  private PortReply createSynopticalPortReply() {
    PortDetail.Builder b = PortDetail.newBuilder();
    b.setId(ID_TEST_VALUE);
    b.setName(STRING_TEST_VALUES);
    b.setWaterDensity(NUMERICAL_TEST_VALUE);
    return PortReply.newBuilder()
        .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
        .addPorts(b.build())
        .build();
  }

  private List<SynopticalTable> createSynopticalEntities() {
    List<SynopticalTable> list = new ArrayList<>();
    IntStream.of(1, 4)
        .forEach(
            i -> {
              try {
                SynopticalTable entity = (SynopticalTable) createDummyObject(SynopticalTable.class);
                entity.setLoadableStudyPortRotation(
                    (LoadableStudyPortRotation) createDummyObject(LoadableStudyPortRotation.class));
                entity.setOperationType(i % 2 == 0 ? OPERATION_TYPE_ARR : OPERATION_TYPE_DEP);
                list.add(entity);
              } catch (InstantiationException e) {
                e.printStackTrace();
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              }
            });
    return list;
  }

  @ParameterizedTest
  @ValueSource(strings = {OPERATION_TYPE_ARR, OPERATION_TYPE_DEP})
  void testSaveSynopticalTable(String operation)
      throws InstantiationException, IllegalAccessException, GenericServiceException {
    this.setSynopticalPostRequestMocks(this.loadableStudyService, false, operation);
    SynopticalTableRequest.Builder request = this.createSynopticalSaveRequest(false, operation);
    if (OPERATION_TYPE_ARR.equals(operation)) {
      request.setLoadablePatternId(0);
    }
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(voyage);
    when(synopticService.saveSynopticalTable(
            any(SynopticalTableRequest.class), any(SynopticalTableReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        synopticService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(synopticService, "voyageService", voyageService);
    ReflectionTestUtils.setField(
        synopticService, "synopticalTableRepository", synopticalTableRepository);
    ReflectionTestUtils.setField(synopticService, "synpoticServiceUtils", synpoticServiceUtils);
    ReflectionTestUtils.setField(
        synopticService, "loadablePatternRepository", loadablePatternRepository);

    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);

    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveSynopticalTable(request.build(), responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @ParameterizedTest
  @ValueSource(strings = {OPERATION_TYPE_ARR, OPERATION_TYPE_DEP})
  void testSaveSynopticalTableEmptyData(String operation)
      throws InstantiationException, IllegalAccessException, GenericServiceException {
    this.setSynopticalPostRequestMocks(this.loadableStudyService, true, operation);
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(voyage);
    when(synopticService.saveSynopticalTable(
            any(SynopticalTableRequest.class), any(SynopticalTableReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        synopticService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(synopticService, "voyageService", voyageService);
    ReflectionTestUtils.setField(
        synopticService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(
        synopticService, "synopticalTableRepository", synopticalTableRepository);
    ReflectionTestUtils.setField(synopticService, "synpoticServiceUtils", synpoticServiceUtils);
    SynopticalTableRequest.Builder request = this.createSynopticalSaveRequest(true, operation);
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveSynopticalTable(request.build(), responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }
  // completed
  @Test
  void testSaveSynopticalTableEmptyPorts()
      throws InstantiationException, IllegalAccessException, GenericServiceException {
    this.setSynopticalPostRequestMocks(this.loadableStudyService, true, OPERATION_TYPE_ARR);
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(synopticService.saveSynopticalTable(
            any(SynopticalTableRequest.class), any(SynopticalTableReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        synopticService, "loadableStudyRepository", loadableStudyRepository);
    SynopticalTableRequest.Builder request =
        this.createSynopticalSaveRequest(true, OPERATION_TYPE_ARR);
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveSynopticalTable(request.build(), responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveSynopticalTableInvalidLoadableStudy()
      throws InstantiationException, IllegalAccessException, GenericServiceException {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    when(synopticService.saveSynopticalTable(
            any(SynopticalTableRequest.class), any(SynopticalTableReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        synopticService, "loadableStudyRepository", loadableStudyRepository);

    SynopticalTableRequest request =
        this.createSynopticalSaveRequest(false, OPERATION_TYPE_ARR).build();
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    spyService.saveSynopticalTable(request, responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveSynopticalTableInvalidLoadablePattern()
      throws InstantiationException, IllegalAccessException, GenericServiceException {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    Voyage voyage = (Voyage) createDummyObject(Voyage.class);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(this.createLoadableStudyEntity(voyage)));
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    when(synopticService.saveSynopticalTable(
            any(SynopticalTableRequest.class), any(SynopticalTableReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        synopticService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        synopticService, "loadablePatternRepository", loadablePatternRepository);
    SynopticalTableRequest request =
        this.createSynopticalSaveRequest(false, OPERATION_TYPE_ARR).build();
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    spyService.saveSynopticalTable(request, responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveSynopticalTableInvalidEntity()
      throws InstantiationException, IllegalAccessException, GenericServiceException {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    Voyage voyage = (Voyage) createDummyObject(Voyage.class);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(this.createLoadableStudyEntity(voyage)));
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of((LoadablePattern) createDummyObject(LoadablePattern.class)));
    when(this.synopticalTableRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    when(synopticService.saveSynopticalTable(
            any(SynopticalTableRequest.class), any(SynopticalTableReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        synopticService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        synopticService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(
        synopticService, "synopticalTableRepository", synopticalTableRepository);

    SynopticalTableRequest request =
        this.createSynopticalSaveRequest(false, OPERATION_TYPE_ARR).build();
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    spyService.saveSynopticalTable(request, responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveSynopticalTableRuntimeException()
      throws InstantiationException, IllegalAccessException, GenericServiceException {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(RuntimeException.class);
    SynopticalTableRequest request =
        this.createSynopticalSaveRequest(false, OPERATION_TYPE_ARR).build();
    when(synopticService.saveSynopticalTable(
            any(SynopticalTableRequest.class), any(SynopticalTableReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        synopticService, "loadableStudyRepository", loadableStudyRepository);
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    spyService.saveSynopticalTable(request, responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveSynopticalTableEmptyPortRotation()
      throws InstantiationException, IllegalAccessException, GenericServiceException {
    Voyage voyage = (Voyage) createDummyObject(Voyage.class);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of((LoadablePattern) createDummyObject(LoadablePattern.class)));
    SynopticalTable entity = (SynopticalTable) createDummyObject(SynopticalTable.class);
    entity.setLoadableStudyPortRotation(null);
    when(this.synopticalTableRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(entity));
    when(this.synopticalTableRepository.save(any(SynopticalTable.class))).thenReturn(entity);
    SynopticalTableRequest.Builder request =
        this.createSynopticalSaveRequest(true, OPERATION_TYPE_ARR);
    when(synopticService.saveSynopticalTable(
            any(SynopticalTableRequest.class), any(SynopticalTableReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(synopticService, "voyageService", voyageService);
    ReflectionTestUtils.setField(
        synopticService, "loadableStudyRepository", loadableStudyRepository);
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveSynopticalTable(request.build(), responseObserver);
    List<SynopticalTableReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @SuppressWarnings("unchecked")
  private void setSynopticalPostRequestMocks(
      LoadableStudyService spyService, boolean emptyData, String operation)
      throws InstantiationException, IllegalAccessException {
    Voyage voyage = (Voyage) createDummyObject(Voyage.class);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(this.createLoadableStudyEntity(voyage)));
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of((LoadablePattern) createDummyObject(LoadablePattern.class)));
    LoadableStudyPortRotation portRotation =
        (LoadableStudyPortRotation) createDummyObject(LoadableStudyPortRotation.class);
    SynopticalTable entity = (SynopticalTable) createDummyObject(SynopticalTable.class);
    entity.setOperationType(operation);
    entity.setLoadableStudyPortRotation(portRotation);
    when(this.synopticalTableRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(entity));
    when(this.synopticalTableRepository.save(any(SynopticalTable.class))).thenReturn(entity);
    if (!emptyData) {
      SynopticalTableLoadicatorData loadicatorEntity =
          (SynopticalTableLoadicatorData) createDummyObject(SynopticalTableLoadicatorData.class);
      when(this.synopticalTableLoadicatorDataRepository
              .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                  any(SynopticalTable.class), anyLong(), anyBoolean()))
          .thenReturn(loadicatorEntity);
      when(this.synopticalTableLoadicatorDataRepository.save(
              any(SynopticalTableLoadicatorData.class)))
          .thenReturn(loadicatorEntity);
    } else {
      when(this.synopticalTableLoadicatorDataRepository
              .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                  any(SynopticalTable.class), anyLong(), anyBoolean()))
          .thenReturn(null);
    }
    List<OnBoardQuantity> obqEntities = this.createObqEntities();
    when(this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            any(LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(obqEntities);
    when(this.onBoardQuantityRepository.saveAll(any(List.class))).thenReturn(obqEntities);
    List<LoadablePatternCargoDetails> cargoList = this.createLoadablePatternCargoDetails();
    when(this.loadablePatternCargoDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(cargoList);
    when(this.loadablePatternCargoDetailsRepository.saveAll(any(List.class))).thenReturn(cargoList);
    List<OnHandQuantity> ohqEntities = this.createOhqEntities();
    ohqEntities.get(0).setTankXId(2L);
    when(this.onHandQuantityRepository.findByLoadableStudyAndPortXIdAndIsActive(
            any(LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(ohqEntities);
    when(this.onHandQuantityRepository.saveAll(any(List.class))).thenReturn(ohqEntities);
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(Arrays.asList(ID_TEST_VALUE));
    when(this.loadablePlanStowageBallastDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(this.createBallastEntities());
  }

  private List<LoadablePatternCargoDetails> createLoadablePatternCargoDetails()
      throws InstantiationException, IllegalAccessException {
    List<LoadablePatternCargoDetails> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      list.add((LoadablePatternCargoDetails) createDummyObject(LoadablePatternCargoDetails.class));
    }
    return list;
  }

  private SynopticalTableRequest.Builder createSynopticalSaveRequest(
      boolean empty, String operation) {
    String TIME_TEST_VALUE = "19:30";
    String DATE_TEST_VALUE = "12-12-2020 10:20";
    SynopticalTableRequest.Builder requestBuilder = SynopticalTableRequest.newBuilder();
    requestBuilder.setLoadableStudyId(ID_TEST_VALUE);
    requestBuilder.setLoadablePatternId(ID_TEST_VALUE);
    for (int i = 1; i <= 2; i++) {
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder recordBuilder =
          com.cpdss.common.generated.LoadableStudy.SynopticalRecord.newBuilder();
      recordBuilder.setId(ID_TEST_VALUE);
      recordBuilder.setPortId(Long.valueOf(i));
      recordBuilder.setOperationType(operation);
      recordBuilder.setDistance(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setSpeed(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setRunningHours(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setInPortHours(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setSpecificGravity(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setHwTideFrom(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setHwTideTo(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setLwTideFrom(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setLwTideTo(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setTimeOfSunrise(empty ? "" : TIME_TEST_VALUE);
      recordBuilder.setTimeOfSunset(empty ? "" : TIME_TEST_VALUE);
      recordBuilder.setHwTideTimeFrom(empty ? "" : TIME_TEST_VALUE);
      recordBuilder.setHwTideTimeTo(empty ? "" : TIME_TEST_VALUE);
      recordBuilder.setLwTideTimeFrom(empty ? "" : TIME_TEST_VALUE);
      recordBuilder.setLwTideTimeTo(empty ? "" : TIME_TEST_VALUE);
      recordBuilder.setEtaEtdEstimated(empty ? "" : DATE_TEST_VALUE);
      recordBuilder.setEtaEtdActual(empty ? "" : DATE_TEST_VALUE);

      recordBuilder.setOthersPlanned(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setOthersActual(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setConstantPlanned(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setConstantActual(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setTotalDwtPlanned(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setTotalDwtActual(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setDisplacementPlanned(empty ? "" : NUMERICAL_TEST_VALUE);
      recordBuilder.setDisplacementActual(empty ? "" : NUMERICAL_TEST_VALUE);

      recordBuilder.addOhq(
          SynopticalOhqRecord.newBuilder()
              .setFuelTypeId(ID_TEST_VALUE)
              .setTankId(ID_TEST_VALUE)
              .build());
      recordBuilder.addOhq(
          SynopticalOhqRecord.newBuilder()
              .setFuelTypeId(2L)
              .setTankId(2L)
              .setPlannedWeight(empty ? "" : NUMERICAL_TEST_VALUE)
              .setActualWeight(empty ? "" : NUMERICAL_TEST_VALUE)
              .build());
      recordBuilder.addOhq(
          SynopticalOhqRecord.newBuilder()
              .setFuelTypeId(3L)
              .setTankId(3L)
              .setPlannedWeight(empty ? "" : NUMERICAL_TEST_VALUE)
              .setActualWeight(empty ? "" : NUMERICAL_TEST_VALUE)
              .build());

      recordBuilder.addCargo(SynopticalCargoRecord.newBuilder().setTankId(ID_TEST_VALUE).build());
      recordBuilder.addCargo(SynopticalCargoRecord.newBuilder().setTankId(3L).build());
      recordBuilder.addCargo(
          SynopticalCargoRecord.newBuilder()
              .setTankId(ID_TEST_VALUE)
              .setPlannedWeight(empty ? "" : NUMERICAL_TEST_VALUE)
              .setActualWeight(empty ? "" : NUMERICAL_TEST_VALUE)
              .build());

      recordBuilder.addBallast(
          SynopticalBallastRecord.newBuilder().setTankId(ID_TEST_VALUE).build());
      recordBuilder.addBallast(SynopticalBallastRecord.newBuilder().setTankId(13L).build());
      recordBuilder.addBallast(
          SynopticalBallastRecord.newBuilder()
              .setTankId(ID_TEST_VALUE)
              .setPlannedWeight(empty ? "" : NUMERICAL_TEST_VALUE)
              .setActualWeight(empty ? "" : NUMERICAL_TEST_VALUE)
              .build());
      com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData data =
          com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.newBuilder()
              .setDeflection(i == 1 ? "" : NUMERICAL_TEST_VALUE)
              .setCalculatedDraftFwdActual(i == 1 ? "" : NUMERICAL_TEST_VALUE)
              .setCalculatedDraftAftActual(i == 1 ? "" : NUMERICAL_TEST_VALUE)
              .setCalculatedDraftMidActual(i == 1 ? "" : NUMERICAL_TEST_VALUE)
              .setCalculatedTrimActual(i == 1 ? "" : NUMERICAL_TEST_VALUE)
              .setBlindSector(i == 1 ? "" : NUMERICAL_TEST_VALUE)
              .setDeflection(i == 1 ? "" : NUMERICAL_TEST_VALUE)
              .build();
      recordBuilder.setLoadicatorData(data);
      requestBuilder.addSynopticalRecord(recordBuilder.build());
    }
    return requestBuilder;
  }

  @Test
  public void testSaveLoadOnTop() throws GenericServiceException {
    SaveLoadOnTopRequest request =
        SaveLoadOnTopRequest.newBuilder().setLoadableStudyId(1L).setLoadOnTop(true).build();
    StreamRecorder<SaveCommentReply> responseObserver = StreamRecorder.create();
    LoadableStudy entity = new LoadableStudy();
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    entity.setVoyage(voyage);
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(entity));

    Mockito.when(this.loadableStudyRepository.save(ArgumentMatchers.any(LoadableStudy.class)))
        .thenReturn(entity);
    loadableStudyService.saveLoadOnTop(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveCommentReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    SaveCommentReply response = results.get(0);
    assertEquals(SUCCESS, response.getResponseStatus().getMessage());
  }

  @Test
  public void testSaveLoadOnTopWithoutLoadableStudy() throws GenericServiceException {
    SaveLoadOnTopRequest request =
        SaveLoadOnTopRequest.newBuilder().setLoadableStudyId(1L).setLoadOnTop(true).build();
    StreamRecorder<SaveCommentReply> responseObserver = StreamRecorder.create();

    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.<LoadableStudy>empty());
    LoadableStudy entity = new LoadableStudy();

    Mockito.when(this.loadableStudyRepository.save(ArgumentMatchers.any(LoadableStudy.class)))
        .thenReturn(entity);
    loadableStudyService.saveLoadOnTop(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveCommentReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    SaveCommentReply response = results.get(0);
    assertEquals(FAILED, response.getResponseStatus().getStatus());
  }

  @Test
  void testGetVoyages() throws GenericServiceException {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setCharterer("CHARTERER");
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setName("CONFIRMED");
    loadableStudy.setLoadableStudyStatus(loadableStudyStatus);
    Set<LoadableStudy> ls_list = new HashSet();
    ls_list.add(loadableStudy);

    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);

    PortReply.Builder portBuilder = PortReply.newBuilder();
    PortDetail.Builder portDetail = PortDetail.newBuilder().setId(1L).setName("TEST");
    portBuilder.addPorts(portDetail.build());
    portBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());

    StreamRecorder<VoyageListReply> responseObserver = StreamRecorder.create();

    CargoReply.Builder replyBuilderCargo = CargoReply.newBuilder();
    CargoDetail.Builder builder =
        CargoDetail.newBuilder()
            .setId(1L)
            .setAbbreviation("TEST")
            .setApi("TEST")
            .setCrudeType("TEST");
    replyBuilderCargo.addCargos(builder.build());
    replyBuilderCargo.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));

    Voyage voyage = new Voyage();
    voyage.setId(1L);
    voyage.setVoyageNo("VOYAGE NO");
    VoyageStatus status = new VoyageStatus();
    status.setId(3L);
    status.setName("ACTIVE");
    voyage.setVoyageStatus(status);
    voyage.setVoyageStartDate(LocalDateTime.now());
    voyage.setVoyageEndDate(LocalDateTime.now());
    voyage.setActualStartDate(LocalDateTime.now());
    voyage.setActualEndDate(LocalDateTime.now());
    voyage.setLoadableStudies(ls_list);
    List<Voyage> voyages = new ArrayList<Voyage>();
    voyages.add(voyage);
    Mockito.when(
            this.voyageRepository.findByIsActiveAndVesselXIdOrderByLastModifiedDateTimeDesc(
                ArgumentMatchers.anyBoolean(), ArgumentMatchers.anyLong()))
        .thenReturn(voyages);

    List<Long> ids = new ArrayList<Long>();
    ids.add(1L);
    Mockito.when(this.loadableStudyPortRotationRepository.getLoadingPorts(any(LoadableStudy.class)))
        .thenReturn(ids);
    Mockito.when(
            this.loadableStudyPortRotationRepository.getDischarigingPorts(any(LoadableStudy.class)))
        .thenReturn(ids);

    List<CargoNomination> cargoList = new ArrayList<CargoNomination>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setCargoXId(1L);
    cargoList.add(cargoNomination);
    Mockito.when(
            this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                anyLong(), anyBoolean()))
        .thenReturn(cargoList);
    when(voyageService.getCargoInfo(any(CargoRequest.class))).thenReturn(replyBuilderCargo.build());
    when(voyageService.GetPortInfo(any(PortRequest.class))).thenReturn(portBuilder.build());
    when(voyageService.getVoyages(any(VoyageRequest.class), any(VoyageListReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(voyageService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        voyageService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(
        voyageService, "cargoNominationRepository", cargoNominationRepository);

    spyService.getVoyages(this.createVoyageListRequest(), responseObserver);
    List<VoyageListReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVoyagesCargoReplyFailed() throws GenericServiceException {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);

    PortReply.Builder portBuilder = PortReply.newBuilder();
    portBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    Mockito.doReturn(portBuilder.build()).when(spyService).GetPortInfo(any(PortRequest.class));

    CargoReply.Builder replyBuilderCargo = CargoReply.newBuilder();
    CargoDetail.Builder builder = CargoDetail.newBuilder();
    replyBuilderCargo.addCargos(builder.build());
    replyBuilderCargo.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    Mockito.doReturn(replyBuilderCargo.build())
        .when(spyService)
        .getCargoInfo(any(CargoRequest.class));
    when(voyageService.getVoyages(any(VoyageRequest.class), any(VoyageListReply.Builder.class)))
        .thenCallRealMethod();
    StreamRecorder<VoyageListReply> responseObserver = StreamRecorder.create();

    spyService.getVoyages(this.createVoyageListRequest(), responseObserver);
    List<VoyageListReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVoyagesPortReplyFailed() throws GenericServiceException {
    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);

    PortReply.Builder portBuilder = PortReply.newBuilder();
    portBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build());
    Mockito.doReturn(portBuilder.build()).when(spyService).GetPortInfo(any(PortRequest.class));
    when(voyageService.getVoyages(any(VoyageRequest.class), any(VoyageListReply.Builder.class)))
        .thenCallRealMethod();

    StreamRecorder<VoyageListReply> responseObserver = StreamRecorder.create();

    spyService.getVoyages(this.createVoyageListRequest(), responseObserver);
    List<VoyageListReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetVoyagesWithDateRange() throws GenericServiceException {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setCharterer("CHARTERER");
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setName("CONFIRMED");
    loadableStudy.setLoadableStudyStatus(loadableStudyStatus);
    Set<LoadableStudy> ls_list = new HashSet();
    ls_list.add(loadableStudy);

    LoadableStudyService spyService = Mockito.spy(this.loadableStudyService);

    PortReply.Builder portBuilder = PortReply.newBuilder();
    PortDetail.Builder portDetail = PortDetail.newBuilder().setId(1L).setName("TEST");
    portBuilder.addPorts(portDetail.build());
    portBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());

    StreamRecorder<VoyageListReply> responseObserver = StreamRecorder.create();

    CargoReply.Builder replyBuilderCargo = CargoReply.newBuilder();
    CargoDetail.Builder builder =
        CargoDetail.newBuilder()
            .setId(1L)
            .setAbbreviation("TEST")
            .setApi("TEST")
            .setCrudeType("TEST");
    replyBuilderCargo.addCargos(builder.build());
    replyBuilderCargo.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));

    Voyage voyage = new Voyage();
    voyage.setId(1L);
    voyage.setVoyageNo("VOYAGE NO");
    VoyageStatus status = new VoyageStatus();
    status.setId(3L);
    status.setName("ACTIVE");
    voyage.setVoyageStatus(status);
    voyage.setVoyageStartDate(LocalDateTime.now());
    voyage.setVoyageEndDate(LocalDateTime.now());
    voyage.setActualStartDate(LocalDateTime.now());
    voyage.setActualEndDate(LocalDateTime.now());
    voyage.setLoadableStudies(ls_list);
    List<Voyage> voyages = new ArrayList<Voyage>();
    voyages.add(voyage);
    Mockito.when(
            this.voyageRepository.findByIsActiveAndVesselXIdAndActualStartDateBetween(
                ArgumentMatchers.anyBoolean(), ArgumentMatchers.anyLong(), any(), any()))
        .thenReturn(voyages);

    List<Long> ids = new ArrayList<Long>();
    ids.add(1L);
    Mockito.when(this.loadableStudyPortRotationRepository.getLoadingPorts(any(LoadableStudy.class)))
        .thenReturn(ids);
    Mockito.when(
            this.loadableStudyPortRotationRepository.getDischarigingPorts(any(LoadableStudy.class)))
        .thenReturn(ids);

    List<CargoNomination> cargoList = new ArrayList<CargoNomination>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setCargoXId(1L);
    cargoList.add(cargoNomination);
    Mockito.when(
            this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                anyLong(), anyBoolean()))
        .thenReturn(cargoList);
    when(voyageService.getCargoInfo(any(CargoRequest.class))).thenReturn(replyBuilderCargo.build());
    when(voyageService.GetPortInfo(any(PortRequest.class))).thenReturn(portBuilder.build());
    when(voyageService.getVoyages(any(VoyageRequest.class), any(VoyageListReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(voyageService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        voyageService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(
        voyageService, "cargoNominationRepository", cargoNominationRepository);

    spyService.getVoyages(
        VoyageRequest.newBuilder()
            .setVesselId(1L)
            .setFromStartDate("18-02-2021")
            .setToStartDate("18-02-2021")
            .build(),
        responseObserver);
    List<VoyageListReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  private VoyageRequest createVoyageListRequest() {
    return VoyageRequest.newBuilder().setVesselId(1L).build();
  }

  @Test
  public void testSaveVoyageStatusStart() throws GenericServiceException {

    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setCharterer("CHARTERER");
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setName("CONFIRMED");
    loadableStudy.setLoadableStudyStatus(loadableStudyStatus);
    Set<LoadableStudyPortRotation> portRotationSet = new HashSet();
    loadableStudy.setPortRotations(portRotationSet);
    Set<LoadableStudy> ls_list = new HashSet();
    ls_list.add(loadableStudy);

    SaveVoyageStatusRequest request =
        SaveVoyageStatusRequest.newBuilder()
            .setActualEndDate("18-02-2021 10:10")
            .setActualStartDate("18-02-2021 10:10")
            .setStatus("start")
            .setVoyageId(1L)
            .build();
    /* used for grpc testing */
    StreamRecorder<SaveVoyageStatusReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    voyage.setId((long) 1);
    voyage.setLoadableStudies(ls_list);

    VoyageStatus status = new VoyageStatus();
    status.setId(3L);
    Mockito.when(
            this.voyageRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(voyage);

    Mockito.when(
            this.voyageRepository.findByVoyageStatusAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(new ArrayList<Voyage>());
    Mockito.when(
            this.voyageStatusRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(Optional.of(status));
    Mockito.when(this.voyageRepository.save(ArgumentMatchers.any(Voyage.class))).thenReturn(voyage);

    when(voyageService.saveVoyageStatus(
            any(SaveVoyageStatusRequest.class), any(SaveVoyageStatusReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(voyageService, "voyageStatusRepository", voyageStatusRepository);

    loadableStudyService.saveVoyageStatus(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveVoyageStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    SaveVoyageStatusReply response = results.get(0);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  @Test
  public void testSaveVoyageStatusWithMultipleVoyages() throws GenericServiceException {

    SaveVoyageStatusRequest request =
        SaveVoyageStatusRequest.newBuilder()
            .setActualEndDate("18-02-2021 10:10")
            .setActualStartDate("18-02-2021 10:10")
            .setStatus("start")
            .setVoyageId(1L)
            .build();
    /* used for grpc testing */
    StreamRecorder<SaveVoyageStatusReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    voyage.setId((long) 1);
    List<Voyage> voyages = new ArrayList<Voyage>();
    voyages.add(voyage);
    Mockito.when(
            this.voyageRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(voyage);
    Mockito.when(
            this.voyageRepository.findByVoyageStatusAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(voyages);

    Mockito.when(this.voyageRepository.save(ArgumentMatchers.any(Voyage.class))).thenReturn(voyage);
    when(voyageService.saveVoyageStatus(
            any(SaveVoyageStatusRequest.class), any(SaveVoyageStatusReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);

    loadableStudyService.saveVoyageStatus(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveVoyageStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    SaveVoyageStatusReply response = results.get(0);
    assertEquals(FAILED, response.getResponseStatus().getStatus());
  }

  @Test
  public void testSaveVoyageStatusWithoutConfirmedLS() throws GenericServiceException {

    SaveVoyageStatusRequest request =
        SaveVoyageStatusRequest.newBuilder()
            .setActualEndDate("18-02-2021 10:10")
            .setActualStartDate("18-02-2021 10:10")
            .setStatus("start")
            .setVoyageId(1L)
            .build();
    /* used for grpc testing */
    StreamRecorder<SaveVoyageStatusReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    voyage.setId((long) 1);

    Mockito.when(
            this.voyageRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(voyage);
    Mockito.when(
            this.voyageRepository.findByVoyageStatusAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(new ArrayList());

    Mockito.when(this.voyageRepository.save(ArgumentMatchers.any(Voyage.class))).thenReturn(voyage);
    when(voyageService.saveVoyageStatus(
            any(SaveVoyageStatusRequest.class), any(SaveVoyageStatusReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);

    loadableStudyService.saveVoyageStatus(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveVoyageStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    SaveVoyageStatusReply response = results.get(0);
    assertEquals(FAILED, response.getResponseStatus().getStatus());
  }

  @Test
  public void testSaveVoyageStatusWithoutStatus() throws GenericServiceException {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setCharterer("CHARTERER");
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setName("CONFIRMED");
    loadableStudy.setLoadableStudyStatus(loadableStudyStatus);
    Set<LoadableStudy> ls_list = new HashSet();
    ls_list.add(loadableStudy);
    SaveVoyageStatusRequest request =
        SaveVoyageStatusRequest.newBuilder()
            .setActualEndDate("18-02-2021 10:10")
            .setActualStartDate("18-02-2021 10:10")
            .setStatus("start")
            .setVoyageId(1L)
            .build();
    /* used for grpc testing */
    StreamRecorder<SaveVoyageStatusReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    voyage.setId((long) 1);
    voyage.setLoadableStudies(ls_list);
    Mockito.when(
            this.voyageRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(voyage);
    Mockito.when(
            this.voyageRepository.findByVoyageStatusAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(new ArrayList());

    Mockito.when(
            this.voyageStatusRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(Optional.empty());
    Mockito.when(this.voyageRepository.save(ArgumentMatchers.any(Voyage.class))).thenReturn(voyage);
    when(voyageService.saveVoyageStatus(
            any(SaveVoyageStatusRequest.class), any(SaveVoyageStatusReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(voyageService, "voyageStatusRepository", voyageStatusRepository);

    loadableStudyService.saveVoyageStatus(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveVoyageStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    SaveVoyageStatusReply response = results.get(0);
    assertEquals(FAILED, response.getResponseStatus().getStatus());
  }

  //
  //  @Test
  //  public void testSaveVoyageStatusStop() throws GenericServiceException {
  //
  //    LoadableStudy loadableStudy = new LoadableStudy();
  //    loadableStudy.setId(1L);
  //    loadableStudy.setCharterer("CHARTERER");
  //    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
  //    loadableStudyStatus.setName("CONFIRMED");
  //    loadableStudy.setLoadableStudyStatus(loadableStudyStatus);
  //    Set<LoadableStudy> ls_list = new HashSet();
  //    ls_list.add(loadableStudy);
  //
  //    SaveVoyageStatusRequest request =
  //        SaveVoyageStatusRequest.newBuilder()
  //            .setActualEndDate("18-02-2021 10:10")
  //            .setActualStartDate("18-02-2021 10:10")
  //            .setStatus("stop")
  //            .setVoyageId(1L)
  //            .build();
  //    /* used for grpc testing */
  //    StreamRecorder<SaveVoyageStatusReply> responseObserver = StreamRecorder.create();
  //    Voyage voyage = new Voyage();
  //    voyage.setId((long) 1);
  //    voyage.setLoadableStudies(ls_list);
  //
  //    VoyageStatus status = new VoyageStatus();
  //    status.setId(3L);
  //    Mockito.when(
  //            this.voyageRepository.findByIdAndIsActive(
  //                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
  //        .thenReturn(voyage);
  //    Mockito.when(
  //            this.voyageRepository.findByVoyageStatusAndIsActive(
  //                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
  //        .thenReturn(new ArrayList<Voyage>());
  //    Mockito.when(
  //            this.voyageStatusRepository.findByIdAndIsActive(
  //                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
  //        .thenReturn(Optional.of(status));
  //
  // Mockito.when(this.voyageRepository.save(ArgumentMatchers.any(Voyage.class))).thenReturn(voyage);
  //    when(voyageService.saveVoyageStatus(
  //            any(SaveVoyageStatusRequest.class), any(SaveVoyageStatusReply.Builder.class)))
  //        .thenCallRealMethod();
  //    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
  //    ReflectionTestUtils.setField(voyageService, "voyageStatusRepository",
  // voyageStatusRepository);
  //
  //    loadableStudyService.saveVoyageStatus(request, responseObserver);
  //
  //    assertNull(responseObserver.getError());
  //    List<SaveVoyageStatusReply> results = responseObserver.getValues();
  //    assertEquals(1, results.size());
  //    SaveVoyageStatusReply response = results.get(0);
  //    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  //  }

  //  @Test
  //  public void testSaveVoyageStatusStopWithNullEndDate() throws GenericServiceException {
  //
  //    LoadableStudy loadableStudy = new LoadableStudy();
  //    loadableStudy.setId(1L);
  //    loadableStudy.setCharterer("CHARTERER");
  //    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
  //    loadableStudyStatus.setName("CONFIRMED");
  //    loadableStudy.setLoadableStudyStatus(loadableStudyStatus);
  //    Set<LoadableStudy> ls_list = new HashSet();
  //    ls_list.add(loadableStudy);
  //
  //    SaveVoyageStatusRequest request =
  //        SaveVoyageStatusRequest.newBuilder().setStatus("stop").setVoyageId(1L).build();
  //    /* used for grpc testing */
  //    StreamRecorder<SaveVoyageStatusReply> responseObserver = StreamRecorder.create();
  //    Voyage voyage = new Voyage();
  //    voyage.setId((long) 1);
  //    voyage.setLoadableStudies(ls_list);
  //
  //    VoyageStatus status = new VoyageStatus();
  //    status.setId(3L);
  //    Mockito.when(
  //            this.voyageRepository.findByIdAndIsActive(
  //                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
  //        .thenReturn(voyage);
  //    Mockito.when(
  //            this.voyageRepository.findByVoyageStatusAndIsActive(
  //                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
  //        .thenReturn(new ArrayList<Voyage>());
  //    Mockito.doNothing().when(voyageService).checkIfVoyageClosed(Mockito.anyLong());
  //    Mockito.when(
  //            this.voyageStatusRepository.findByIdAndIsActive(
  //                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
  //        .thenReturn(Optional.of(status));
  //    LoadableStudyPortRotation maxPortOrderEntity = new LoadableStudyPortRotation();
  //    maxPortOrderEntity.setId(1L);
  //    SynopticalTable synoptical = new SynopticalTable();
  //    synoptical.setLoadableStudyPortRotation(maxPortOrderEntity);
  //    List<SynopticalTable> synopticalList = new ArrayList<>();
  //    synopticalList.add(synoptical);
  //    maxPortOrderEntity.setSynopticalTable(synopticalList);
  //    Mockito.when(
  //            this.loadableStudyPortRotationRepository
  //                .findFirstByLoadableStudyAndIsActiveOrderByPortOrderDesc(
  //                    ArgumentMatchers.any(), ArgumentMatchers.anyBoolean()))
  //        .thenReturn(maxPortOrderEntity);
  //
  // Mockito.when(this.voyageRepository.save(ArgumentMatchers.any(Voyage.class))).thenReturn(voyage);
  //    when(voyageService.saveVoyageStatus(
  //            any(SaveVoyageStatusRequest.class), any(SaveVoyageStatusReply.Builder.class)))
  //        .thenCallRealMethod();
  //    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
  //    ReflectionTestUtils.setField(voyageService, "voyageStatusRepository",
  // voyageStatusRepository);
  //    ReflectionTestUtils.setField(
  //        voyageService, "loadableStudyPortRotationRepository",
  // loadableStudyPortRotationRepository);
  //
  //    ReflectionTestUtils.setField(loadableStudyService, "voyageService", voyageService);
  //    loadableStudyService.saveVoyageStatus(request, responseObserver);
  //
  //    assertNull(responseObserver.getError());
  //    List<SaveVoyageStatusReply> results = responseObserver.getValues();
  //    assertEquals(1, results.size());
  //    SaveVoyageStatusReply response = results.get(0);
  //    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  //  }

  @Test
  public void testSaveVoyageStatusStopWithNullStartDate() throws GenericServiceException {

    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setCharterer("CHARTERER");
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setName("CONFIRMED");
    loadableStudy.setLoadableStudyStatus(loadableStudyStatus);
    Set<LoadableStudyPortRotation> portRotationSet = new HashSet();
    loadableStudy.setPortRotations(portRotationSet);
    Set<LoadableStudy> ls_list = new HashSet();
    ls_list.add(loadableStudy);

    SaveVoyageStatusRequest request =
        SaveVoyageStatusRequest.newBuilder().setStatus("start").setVoyageId(1L).build();
    /* used for grpc testing */
    StreamRecorder<SaveVoyageStatusReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    voyage.setId((long) 1);
    voyage.setLoadableStudies(ls_list);

    VoyageStatus status = new VoyageStatus();
    status.setId(3L);
    Mockito.when(
            this.voyageRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(voyage);
    Mockito.when(
            this.voyageRepository.findByVoyageStatusAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(new ArrayList<Voyage>());
    Mockito.when(
            this.voyageStatusRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(Optional.of(status));
    LoadableStudyPortRotation maxPortOrderEntity = new LoadableStudyPortRotation();
    maxPortOrderEntity.setId(1L);
    SynopticalTable synoptical = new SynopticalTable();
    synoptical.setLoadableStudyPortRotation(maxPortOrderEntity);
    List<SynopticalTable> synopticalList = new ArrayList<>();
    synopticalList.add(synoptical);
    maxPortOrderEntity.setSynopticalTable(synopticalList);
    Mockito.when(
            this.loadableStudyPortRotationRepository
                .findFirstByLoadableStudyAndIsActiveOrderByPortOrderAsc(
                    ArgumentMatchers.any(), ArgumentMatchers.anyBoolean()))
        .thenReturn(maxPortOrderEntity);
    Mockito.doNothing().when(voyageService).checkIfVoyageClosed(Mockito.anyLong());
    Mockito.when(this.voyageRepository.save(ArgumentMatchers.any(Voyage.class))).thenReturn(voyage);
    when(voyageService.saveVoyageStatus(
            any(SaveVoyageStatusRequest.class), any(SaveVoyageStatusReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(voyageService, "voyageStatusRepository", voyageStatusRepository);
    ReflectionTestUtils.setField(
        voyageService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);

    loadableStudyService.saveVoyageStatus(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveVoyageStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    SaveVoyageStatusReply response = results.get(0);
    assertEquals(SUCCESS, response.getResponseStatus().getStatus());
  }

  @Test
  public void testSaveVoyageStatusStopWithoutStatus() throws GenericServiceException {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setCharterer("CHARTERER");
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setName("CONFIRMED");
    loadableStudy.setLoadableStudyStatus(loadableStudyStatus);
    Set<LoadableStudy> ls_list = new HashSet();
    ls_list.add(loadableStudy);
    SaveVoyageStatusRequest request =
        SaveVoyageStatusRequest.newBuilder()
            .setActualEndDate("18-02-2021 10:10")
            .setActualStartDate("18-02-2021 10:10")
            .setStatus("stop")
            .setVoyageId(1L)
            .build();
    /* used for grpc testing */
    StreamRecorder<SaveVoyageStatusReply> responseObserver = StreamRecorder.create();
    Voyage voyage = new Voyage();
    voyage.setId((long) 1);
    voyage.setLoadableStudies(ls_list);
    Mockito.when(
            this.voyageRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(voyage);
    Mockito.when(
            this.voyageRepository.findByVoyageStatusAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(new ArrayList());

    Mockito.when(
            this.voyageStatusRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(Optional.empty());
    Mockito.when(this.voyageRepository.save(ArgumentMatchers.any(Voyage.class))).thenReturn(voyage);
    when(voyageService.saveVoyageStatus(
            any(SaveVoyageStatusRequest.class), any(SaveVoyageStatusReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(voyageService, "voyageStatusRepository", voyageStatusRepository);
    loadableStudyService.saveVoyageStatus(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveVoyageStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    SaveVoyageStatusReply response = results.get(0);
    assertEquals(FAILED, response.getResponseStatus().getStatus());
  }

  @Test
  public void testSaveVoyageWithVoyageNull() throws GenericServiceException {

    SaveVoyageStatusRequest request =
        SaveVoyageStatusRequest.newBuilder()
            .setActualEndDate("18-02-2021 10:10")
            .setActualStartDate("18-02-2021 10:10")
            .setStatus("stop")
            .setVoyageId(1L)
            .build();
    /* used for grpc testing */
    StreamRecorder<SaveVoyageStatusReply> responseObserver = StreamRecorder.create();

    Mockito.when(
            this.voyageRepository.findByIdAndIsActive(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
        .thenReturn(null);
    when(voyageService.saveVoyageStatus(
            any(SaveVoyageStatusRequest.class), any(SaveVoyageStatusReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);

    loadableStudyService.saveVoyageStatus(request, responseObserver);

    assertNull(responseObserver.getError());
    List<SaveVoyageStatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    SaveVoyageStatusReply response = results.get(0);
    assertEquals(FAILED, response.getResponseStatus().getStatus());
  }

  @Test
  public void testSaveJson() {
    JsonRequest request =
        JsonRequest.newBuilder().setJsonTypeId(1L).setReferenceId(10L).setJson("{}").build();
    JsonType jsonType = new JsonType();
    jsonType.setId(1L);
    JsonData jsonData = new JsonData();
    jsonData.setReferenceXId(10L);
    jsonData.setJsonTypeXId(jsonType);
    jsonData.setJsonData("{}");
    StreamRecorder<StatusReply> responseObserver = StreamRecorder.create();

    Mockito.when(this.jsonTypeRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(jsonType));

    Mockito.when(this.jsonDataRepository.save(any(JsonData.class))).thenReturn(jsonData);

    loadableStudyService.saveJson(request, responseObserver);
    assertNull(responseObserver.getError());
    List<StatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    StatusReply response = results.get(0);
    assertEquals(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(), response);
  }

  @Test
  public void testSaveJsonWithInvalidType() {
    JsonRequest request =
        JsonRequest.newBuilder().setJsonTypeId(1L).setReferenceId(10L).setJson("{}").build();
    JsonType jsonType = new JsonType();
    jsonType.setId(1L);
    JsonData jsonData = new JsonData();
    jsonData.setReferenceXId(10L);
    jsonData.setJsonTypeXId(jsonType);
    jsonData.setJsonData("{}");
    StreamRecorder<StatusReply> responseObserver = StreamRecorder.create();

    Mockito.when(this.jsonTypeRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());

    Mockito.when(this.jsonDataRepository.save(any(JsonData.class))).thenReturn(jsonData);

    loadableStudyService.saveJson(request, responseObserver);
    assertNull(responseObserver.getError());
    List<StatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    StatusReply response = results.get(0);
    assertEquals(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(), response);
  }

  @Test
  public void testSaveJsonWithException() {
    JsonRequest request =
        JsonRequest.newBuilder().setJsonTypeId(1L).setReferenceId(10L).setJson("{}").build();
    JsonType jsonType = new JsonType();
    jsonType.setId(1L);
    JsonData jsonData = new JsonData();
    jsonData.setReferenceXId(10L);
    jsonData.setJsonTypeXId(jsonType);
    jsonData.setJsonData("{}");

    StreamRecorder<StatusReply> responseObserver = StreamRecorder.create();

    Mockito.when(this.jsonTypeRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(new DataRetrievalFailureException("Cannot retrieve JSON type."));

    Mockito.when(this.jsonDataRepository.save(any(JsonData.class)))
        .thenThrow(new DataAccessException("Cannot save JSON") {});

    loadableStudyService.saveJson(request, responseObserver);
    assertNull(responseObserver.getError());
    List<StatusReply> results = responseObserver.getValues();
    assertEquals(1, results.size());
    StatusReply response = results.get(0);
    assertEquals(
        StatusReply.newBuilder()
            .setStatus(FAILED)
            .setMessage(FAILED)
            .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
            .build(),
        response);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  void testUpdateUllage(int iteration)
      throws RestClientException, InstantiationException, IllegalAccessException {
    UpdateUllageRequest request = this.createUpdateUllageRequest();
    LoadablePattern loadablePattern = new LoadablePattern();
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setPlanningTypeXId(1);
    loadablePattern.setLoadableStudy(loadableStudy);
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(loadablePattern));
    when(this.restTemplate.postForEntity(anyString(), any(UllageUpdateRequest.class), any()))
        .thenReturn(
            new ResponseEntity<Object>(this.createAlgoResponseForUpdateUllage(), HttpStatus.OK));
    when(this.loadablePlanStowageDetailsRespository.getOne(anyLong()))
        .thenReturn(new LoadablePlanStowageDetails());
    LoadablePlanStowageDetailsTemp temp = null;
    if (iteration == 2) {
      temp =
          (LoadablePlanStowageDetailsTemp) createDummyObject(LoadablePlanStowageDetailsTemp.class);
    }
    when(this.stowageDetailsTempRepository.findByLoadablePlanStowageDetailsAndIsActive(
            any(LoadablePlanStowageDetails.class), anyBoolean()))
        .thenReturn(temp);
    try {
      Mockito.when(loadablePlanService.callAlgoUllageUpdateApi(Mockito.any()))
          .thenReturn(getAlgo());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    StreamRecorder<UpdateUllageReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePlanService.updateUllage(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", this.loadablePatternRepository);
    ReflectionTestUtils.setField(loadablePlanService, "restTemplate", this.restTemplate);
    ReflectionTestUtils.setField(
        loadablePlanService, "stowageDetailsTempRepository", this.stowageDetailsTempRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "cargoOperationRepository", this.cargoOperationRepository);
    ReflectionTestUtils.setField(
        loadablePlanService,
        "loadableStudyPortRotationService",
        this.loadableStudyPortRotationService);
    ReflectionTestUtils.setField(
        loadablePlanService, "synopticalTableRepository", this.synopticalTableRepository);
    ReflectionTestUtils.setField(
        loadablePlanService,
        "loadablePlanStowageDetailsRespository",
        this.loadablePlanStowageDetailsRespository);
    this.loadableStudyService.updateUllage(request, responseObserver);
    List<UpdateUllageReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  private UllageUpdateResponse getAlgo() {
    UllageUpdateResponse ullageUpdateResponse = new UllageUpdateResponse();
    ullageUpdateResponse.setFillingRatio("");
    return ullageUpdateResponse;
  }

  @Test
  void testUpdateUllageInvalidAlgoResponse()
      throws RestClientException, InstantiationException, IllegalAccessException {
    UpdateUllageRequest request = this.createUpdateUllageRequest();
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(new RuntimeException("error"));
    when(this.restTemplate.postForEntity(anyString(), any(UllageUpdateRequest.class), any()))
        .thenReturn(new ResponseEntity<Object>(HttpStatus.BAD_REQUEST));
    when(this.loadablePlanStowageDetailsRespository.getOne(anyLong()))
        .thenReturn(new LoadablePlanStowageDetails());
    try {
      Mockito.when(loadablePlanService.callAlgoUllageUpdateApi(Mockito.any()))
          .thenReturn(getAlgo());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }

    StreamRecorder<UpdateUllageReply> responseObserver = StreamRecorder.create();
    try {
      Mockito.when(loadablePlanService.updateUllage(Mockito.any(), Mockito.any()))
          .thenCallRealMethod();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", this.loadablePatternRepository);
    ReflectionTestUtils.setField(loadablePlanService, "restTemplate", this.restTemplate);
    ReflectionTestUtils.setField(
        loadablePlanService, "stowageDetailsTempRepository", this.stowageDetailsTempRepository);
    ReflectionTestUtils.setField(
        loadablePlanService, "cargoOperationRepository", this.cargoOperationRepository);
    ReflectionTestUtils.setField(
        loadablePlanService,
        "loadableStudyPortRotationService",
        this.loadableStudyPortRotationService);
    ReflectionTestUtils.setField(
        loadablePlanService, "synopticalTableRepository", this.synopticalTableRepository);
    ReflectionTestUtils.setField(
        loadablePlanService,
        "loadablePlanStowageDetailsRespository",
        this.loadablePlanStowageDetailsRespository);
    this.loadableStudyService.updateUllage(request, responseObserver);
    List<UpdateUllageReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testUpdateUllageInvalidPattern()
      throws RestClientException, InstantiationException, IllegalAccessException,
          GenericServiceException {
    UpdateUllageRequest request = this.createUpdateUllageRequest();
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    when(loadablePlanService.updateUllage(
            any(UpdateUllageRequest.class), any(UpdateUllageReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", loadablePatternRepository);
    StreamRecorder<UpdateUllageReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.updateUllage(request, responseObserver);
    List<UpdateUllageReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testUpdateUllageException()
      throws RestClientException, InstantiationException, IllegalAccessException,
          GenericServiceException {
    UpdateUllageRequest request = this.createUpdateUllageRequest();
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenThrow(new RuntimeException("error"));

    when(loadablePlanService.updateUllage(
            any(UpdateUllageRequest.class), any(UpdateUllageReply.Builder.class)))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        loadablePlanService, "loadablePatternRepository", loadablePatternRepository);
    StreamRecorder<UpdateUllageReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.updateUllage(request, responseObserver);
    List<UpdateUllageReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private UllageUpdateResponse createAlgoResponseForUpdateUllage() {
    UllageUpdateResponse response = new UllageUpdateResponse();
    response.setCorrectedUllage(NUMERICAL_TEST_VALUE);
    response.setQuantityMt(NUMERICAL_TEST_VALUE);
    response.setCorrectionFactor(NUMERICAL_TEST_VALUE);
    return response;
  }

  private UpdateUllageRequest createUpdateUllageRequest() {
    return UpdateUllageRequest.newBuilder()
        .setLoadablePatternId(ID_TEST_VALUE)
        .setUpdateUllageForLoadingPlan(false)
        .setLoadablePlanStowageDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder()
                .setCorrectedUllage(NUMERICAL_TEST_VALUE)
                .setId(ID_TEST_VALUE)
                .setTankId(ID_TEST_VALUE)
                .setIsBallast(true)
                .build())
        .build();
  }
}
