/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.DATE_FORMAT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy.AlgoReply;
import com.cpdss.common.generated.LoadableStudy.AlgoRequest;
import com.cpdss.common.generated.discharge_plan.DischargePlanServiceGrpc;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.repository.*;
import io.grpc.internal.testing.StreamRecorder;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * @Author jerin.g
 *
 * <p>Class for writing test cases for discharge study
 */
@SpringJUnitConfig(classes = {DischargeStudyService.class})
class DischargeStudyServiceTest {

  @Autowired private DischargeStudyService dischargeStudyService;
  @MockBean LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;
  @MockBean LoadableStudyRuleRepository loadableStudyRuleRepository;
  @MockBean SynopticService synopticService;
  @MockBean private VoyageRepository voyageRepository;
  @MockBean private LoadablePatternRepository loadablePatternRepository;

  @MockBean private VoyageService voyageService;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private LoadableQuantityRepository loadableQuantityRepository;

  @MockBean private CargoNominationRepository cargoNominationRepository;
  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private CargoOperationRepository cargoOperationRepository;
  @MockBean private LoadableStudyStatusRepository loadableStudyStatusRepository;

  @MockBean private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @MockBean private LoadableStudyAttachmentsRepository loadableStudyAttachmentsRepository;
  @MockBean private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  @MockBean private LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @MockBean private LoadablePatternDetailsRepository loadablePatternDetailsRepository;
  @MockBean private LoadablePlanConstraintsRespository loadablePlanConstraintsRespository;
  @MockBean private PurposeOfCommingleRepository purposeOfCommingleRepository;
  @MockBean private CargoNominationService cargoNominationService;
  @MockBean private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean private LoadablePatternService loadablePatternService;
  @MockBean private CowDetailService cowDetailService;
  @MockBean private PortInstructionService portInstructionService;
  @MockBean private BackLoadingService backLoadingService;
  @MockBean private DischargePlanCommingleDetailsService dischargePlanCommingleDetailsService;

  @MockBean
  private CommingleCargoToDischargePortwiseDetailsRepository
      commingleCargoToDischargePortwiseDetailsRepository;

  @MockBean
  private DischargePatternQuantityCargoPortwiseRepository
      dischargePatternQuantityCargoPortwiseRepository;

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

  @MockBean private OnHandQuantityRepository onHandQuantityRepository;

  @MockBean private OnBoardQuantityRepository onBoardQuantityRepository;
  @MockBean private CargoHistoryRepository cargoHistoryRepository;
  @MockBean private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @MockBean private VoyageHistoryRepository voyageHistoryRepository;
  @MockBean private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
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
  @MockBean private ApiTempHistoryRepository apiTempHistoryRepository;

  @MockBean private StabilityParameterRepository stabilityParameterRepository;

  @MockBean private JsonDataRepository jsonDataRepository;
  @MockBean private JsonTypeRepository jsonTypeRepository;
  @MockBean private LoadablePlanService loadablePlanService;
  @MockBean private LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;
  @MockBean private LoadableQuantityService loadableQuantityService;
  @MockBean private GenerateDischargeStudyJson generateDischargeStudyJson;
  @MockBean private DischargeStudyCowDetailRepository dischargeStudyCowDetailRepository;
  @MockBean private CowHistoryRepository cowHistoryRepository;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;
  private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanServiceBlockingStub =
      Mockito.mock(LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub.class);

  @MockBean
  private DischargePlanServiceGrpc.DischargePlanServiceBlockingStub
      dischargePlanServiceBlockingStub;

  @MockBean JsonDataService jsonDataService;

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @BeforeAll
  public static void beforeAll() {
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
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  // TODO to be fixed before merge
  @Disabled("Disabled for branch test")
  @Test
  void testSaveDischargeStudy() throws GenericServiceException {
    DischargeStudyDetail request =
        DischargeStudyDetail.newBuilder().setName("DS").setVesselId(1L).setVoyageId(1L).build();
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(new com.cpdss.loadablestudy.entity.Voyage());
    when(this.loadableStudyRepository
            .findByVesselXIdAndVoyageAndIsActiveAndLoadableStudyStatus_idAndPlanningTypeXId(
                anyLong(), any(Voyage.class), anyBoolean(), anyLong(), anyInt()))
        .thenReturn(new ArrayList<LoadableStudy>())
        .thenReturn(createLoadableList());
    ReflectionTestUtils.setField(
        this.dischargeStudyService, "dischargeStudyRepository", loadableStudyRepository);

    List<SynopticalTable> synopticalTableList = createLoadableSynopticalTableList();

    when(this.synopticalTableRepository
            .findByLoadableStudyXIdAndLoadableStudyPortRotation_idAndIsActive(
                anyLong(), anyLong(), anyBoolean()))
        .thenReturn(synopticalTableList);
    when(this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            any(LoadableStudy.class), any(LoadableStudyPortRotation.class), anyBoolean()))
        .thenReturn(createOnHandQuantityList());
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CargoNomination()));
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndOperation_idNotAndIsActive(
            any(LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndOperation_idAndIsActive(
            any(LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setPortXId(1l);
    CargoOperation operation = new CargoOperation();
    operation.setId(1l);
    portRotation.setOperation(operation);
    portRotation.setLoadableStudy(entity);
    when(this.loadableStudyPortRotationRepository.save(any(LoadableStudyPortRotation.class)))
        .thenReturn(portRotation);
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(portRotation));
    when(this.synopticalTableRepository.saveAll(anyCollection())).thenReturn(synopticalTableList);

    when(this.loadableStudyStatusRepository.getOne(anyLong()))
        .thenReturn(new LoadableStudyStatus());

    when(cargoNominationService.getCargoNominationByLoadableStudyId(anyLong()))
        .thenReturn(getCargoNominationList());
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());
    when(loadingPlanServiceBlockingStub.validateStowageAndBillOfLadding(
            any(LoadingPlanModels.StowageAndBillOfLaddingValidationRequest.class)))
        .thenReturn(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());

    ReflectionTestUtils.setField(
        this.dischargeStudyService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);

    StreamRecorder<DischargeStudyReply> responseObserver = StreamRecorder.create();
    this.dischargeStudyService.saveDischargeStudy(request, responseObserver);
    List<DischargeStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getId());
  }

  private List<CargoNomination> getCargoNominationList() {
    List<CargoNomination> cargoNominationList = new ArrayList<>();
    CargoNomination cargo = new CargoNomination();
    Set<CargoNominationPortDetails> portDetailsSet = new HashSet<>();
    CargoNominationPortDetails portDetails = new CargoNominationPortDetails();
    portDetails.setPortId(1l);
    CargoNomination cargo1 = new CargoNomination();
    cargo1.setId(1l);
    Set<CargoNominationPortDetails> portDetailsSet2 = new HashSet<>();
    CargoNominationPortDetails portDetails2 = new CargoNominationPortDetails();
    portDetails2.setPortId(1l);
    portDetailsSet2.add(portDetails2);
    cargo1.setCargoNominationPortDetails(portDetailsSet2);
    portDetails.setCargoNomination(cargo1);
    portDetails.setSequenceNo(1l);
    portDetails.setEmptyMaxNoOfTanks(true);
    portDetails.setIsActive(true);
    portDetails.setQuantity(new BigDecimal(1));
    portDetails.setMode(1l);
    portDetails.setId(1l);
    portDetails.setPortRotation(getPortRotation());
    portDetailsSet.add(portDetails);
    cargo.setCargoNominationPortDetails(portDetailsSet);
    cargo.setId(1l);
    cargo.setIsBackloading(false);
    cargo.setLsCargoNominationId(1l);
    cargoNominationList.add(cargo);
    return cargoNominationList;
  }

  private LoadableStudyPortRotation getPortRotation() {
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setId(1L);
    return portRotation;
  }

  @Test
  void testSaveDischargingPorts() throws GenericServiceException {
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(createLoadableList().get(0)));
    com.cpdss.common.generated.LoadableStudy.PortRotationRequest request =
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest.newBuilder()
            .setLoadableStudyId(1l)
            .setCargoNominationId(1l)
            .setIsDischargingPortsComplete(true)
            .addAllDischargingPortIds(Arrays.asList(1l, 2l))
            .build();
    com.cpdss.common.generated.LoadableStudy.PortRotationReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.PortRotationReply.newBuilder();

    doNothing().when(this.voyageService).checkIfVoyageClosed(anyLong());
    doNothing()
        .when(this.loadablePatternService)
        .isPatternGeneratedOrConfirmed(any(LoadableStudy.class));
    when(this.cargoOperationRepository.getOne(anyLong()))
        .thenReturn(createLoadableStudyPortRotationList().get(0).getOperation());
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
            any(LoadableStudy.class), any(CargoOperation.class), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());
    doNothing()
        .when(this.loadableStudyPortRotationService)
        .validateTransitPorts(any(LoadableStudy.class), anyList());
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    when(loadableStudyPortRotationService.findMaxPortOrderForLoadableStudy(
            any(LoadableStudy.class)))
        .thenReturn(1l);
    doNothing()
        .when(synopticService)
        .buildPortsInfoSynopticalTable(any(LoadableStudyPortRotation.class), anyLong(), anyLong());
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());

    ReflectionTestUtils.setField(dischargeStudyService, "portInfoGrpcService", portInfoGrpcService);
    dischargeStudyService.saveDischargingPorts(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  private PortInfo.PortReply getPortReply() {
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllPorts(
                Arrays.asList(
                    PortInfo.PortDetail.newBuilder()
                        .setId(2l)
                        .setWaterDensity("1")
                        .setMaxAirDraft("1")
                        .setMaxDraft("1")
                        .build()))
            .build();
    return portReply;
  }

  @Test
  void testGetPortInfo() {
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(dischargeStudyService, "portInfoGrpcService", portInfoGrpcService);

    var result =
        dischargeStudyService.getPortInfo(
            PortInfo.GetPortInfoByPortIdsRequest.newBuilder().build());
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private com.cpdss.common.generated.LoadableStudy.CargoNominationDetail
      getCargoNominationDetail() {
    com.cpdss.common.generated.LoadableStudy.CargoNominationDetail detail =
        com.cpdss.common.generated.LoadableStudy.CargoNominationDetail.newBuilder()
            .setId(1l)
            .setSequenceNo(1l)
            .setEmptyMaxNoOfTanks(true)
            .setAbbreviation("1")
            .setApi("1")
            .setCargoId(1l)
            .setColor("1")
            .setQuantity("1")
            .setMode(1l)
            .setTemperature("1")
            .build();
    return detail;
  }

  private com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingDetail
      getBackLoadingDetail() {
    com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingDetail detail =
        com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingDetail.newBuilder()
            .setPortDetails(
                com.cpdss.common.generated.LoadableStudy.PortRotationDetail.newBuilder()
                    .setId(1l)
                    .setMaxAirDraft("1")
                    .setIsBackLoadingEnabled(true)
                    .setFreshCrudeOil(true)
                    .setFreshCrudeOilQuantity("1")
                    .setFreshCrudeOilTime("1")
                    .setMaxDraft("1")
                    .addAllInstructionId(Arrays.asList(1l))
                    .addAllBackLoading(
                        Arrays.asList(
                            LoadableStudyModels.BackLoading.newBuilder()
                                .setId(1l)
                                .setAbbreviation("1")
                                .setApi("1")
                                .setCargoId(1l)
                                .setColour("1")
                                .setQuantity("1")
                                .setTemperature("1")
                                .build()))
                    .build())
            .addAllPortCargoDetails(Arrays.asList(getCargoNominationDetail()))
            .build();
    return detail;
  }

  private com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest
      getRequest() {
    List<com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingDetail>
        backLoadingDetailList = new ArrayList<>();
    backLoadingDetailList.add(getBackLoadingDetail());
    com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest request =
        com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest.newBuilder()
            .setDischargeStudyId(1l)
            .addAllDsBackLoading(backLoadingDetailList)
            .build();
    return request;
  }

  @Test
  void testSaveDischargeStudyBackLoadingElse() {
    Map<Long, DischargeStudyCowDetail> cowDetailForThePort = new HashMap<>();
    DischargeStudyCowDetail cowDetail = new DischargeStudyCowDetail();
    cowDetail.setCowType(1l);
    cowDetailForThePort.put(1l, cowDetail);
    Map<Long, List<DischargeStudyPortInstruction>> portWiseInstructions = new HashMap<>();
    DischargeStudyPortInstruction portInstruction = new DischargeStudyPortInstruction();
    portInstruction.setPortInstructionId(1l);
    portWiseInstructions.put(1l, Arrays.asList(portInstruction));
    Map<Long, List<BackLoading>> backloadingData = new HashMap<>();
    backloadingData.put(1l, Arrays.asList(getBackLoadingList().get(0)));

    StreamRecorder<DischargeStudyReply> responseObserver = StreamRecorder.create();
    Set<CargoNominationPortDetails> portDetails = new HashSet<>();
    portDetails.add(new CargoNominationPortDetails());

    when(backLoadingService.getBackloadingDataByportIds(anyLong(), anyList()))
        .thenReturn(backloadingData);
    when(portInstructionService.getPortWiseInstructions(anyLong(), anyList()))
        .thenReturn(portWiseInstructions);
    when(cowDetailService.getCowDetailForDS(anyLong())).thenReturn(cowDetail);
    when(cowDetailService.getCowDetailForThePort(anyLong(), anyList()))
        .thenReturn(cowDetailForThePort);
    when(cargoNominationService.getCargoNominations(anyLong()))
        .thenReturn(getCargoNominationList());
    when(cargoNominationService.createCargoNominationPortDetails(
            any(CargoNomination.class),
            any(CargoNomination.class),
            any(LoadableStudyPortRotation.class),
            anyInt()))
        .thenReturn(portDetails);

    when(loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(createLoadableList().get(0)));
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());

    ReflectionTestUtils.setField(
        dischargeStudyService, "dischargeStudyRepository", loadableStudyRepository);
    com.cpdss.common.generated.LoadableStudy.CargoNominationDetail detail =
        getCargoNominationDetail();
    detail.toBuilder().setId(-3l).build();
    com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingDetail backLoadingDetail =
        getBackLoadingDetail();
    backLoadingDetail.toBuilder().addAllPortCargoDetails(Arrays.asList(detail)).build();
    com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest request =
        getRequest();
    request.toBuilder().addAllDsBackLoading(Arrays.asList(backLoadingDetail)).build();
    this.dischargeStudyService.saveDischargeStudyBackLoading(request, responseObserver);
    List<DischargeStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testSaveDischargeStudyBackLoading() {
    Map<Long, DischargeStudyCowDetail> cowDetailForThePort = new HashMap<>();
    DischargeStudyCowDetail cowDetail = new DischargeStudyCowDetail();
    cowDetail.setCowType(1l);
    cowDetailForThePort.put(1l, cowDetail);
    Map<Long, List<DischargeStudyPortInstruction>> portWiseInstructions = new HashMap<>();
    DischargeStudyPortInstruction portInstruction = new DischargeStudyPortInstruction();
    portInstruction.setPortInstructionId(1l);
    portWiseInstructions.put(1l, Arrays.asList(portInstruction));
    Map<Long, List<BackLoading>> backloadingData = new HashMap<>();
    backloadingData.put(1l, Arrays.asList(getBackLoadingList().get(0)));

    StreamRecorder<DischargeStudyReply> responseObserver = StreamRecorder.create();

    when(backLoadingService.getBackloadingDataByportIds(anyLong(), anyList()))
        .thenReturn(backloadingData);
    when(portInstructionService.getPortWiseInstructions(anyLong(), anyList()))
        .thenReturn(portWiseInstructions);
    when(cowDetailService.getCowDetailForDS(anyLong())).thenReturn(cowDetail);
    when(cowDetailService.getCowDetailForThePort(anyLong(), anyList()))
        .thenReturn(cowDetailForThePort);
    when(cargoNominationService.getCargoNominations(anyLong()))
        .thenReturn(getCargoNominationList());

    when(loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(createLoadableList().get(0)));
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());

    ReflectionTestUtils.setField(
        dischargeStudyService, "dischargeStudyRepository", loadableStudyRepository);

    this.dischargeStudyService.saveDischargeStudyBackLoading(getRequest(), responseObserver);
    List<DischargeStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetDischargeStudyPortWiseCargos() {
    DischargeStudyRequest request =
        DischargeStudyRequest.newBuilder().setDischargeStudyId(1l).build();
    StreamRecorder<LoadableStudyModels.DishargeStudyCargoReply> responseObserver =
        StreamRecorder.create();

    when(loadableStudyRepository.existsById(anyLong())).thenReturn(true);
    when(loadableStudyPortRotationRepository.findByLoadableStudyIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());
    when(portInfoGrpcService.getCargoInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(
            PortInfo.CargoInfos.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .addAllCargoPorts(
                    Arrays.asList(
                        PortInfo.CargoPortMapping.newBuilder()
                            .setCargoId(1l)
                            .setPortId(1l)
                            .build()))
                .build());
    when(cargoInfoGrpcService.getCargoInfosByCargoIds(any(CargoInfo.CargoListRequest.class)))
        .thenReturn(
            CargoInfo.CargoReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .addAllCargos(
                    Arrays.asList(
                        CargoInfo.CargoDetail.newBuilder()
                            .setId(1l)
                            .setApi("1")
                            .setAbbreviation("1")
                            .setIsCondensateCargo(true)
                            .setIsHrvpCargo(true)
                            .setCrudeType("1")
                            .build()))
                .build());

    ReflectionTestUtils.setField(
        dischargeStudyService, "dischargeStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        dischargeStudyService, "cargoInfoGrpcService", cargoInfoGrpcService);
    ReflectionTestUtils.setField(dischargeStudyService, "portInfoGrpcService", portInfoGrpcService);

    this.dischargeStudyService.getDischargeStudyPortWiseCargos(request, responseObserver);
    List<LoadableStudyModels.DishargeStudyCargoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetDischargeStudyPortWiseCargosWithGenericException() {
    DischargeStudyRequest request =
        DischargeStudyRequest.newBuilder().setDischargeStudyId(1l).build();
    StreamRecorder<LoadableStudyModels.DishargeStudyCargoReply> responseObserver =
        StreamRecorder.create();

    when(loadableStudyRepository.existsById(anyLong())).thenReturn(false);

    this.dischargeStudyService.getDischargeStudyPortWiseCargos(request, responseObserver);
    List<LoadableStudyModels.DishargeStudyCargoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetDischargeStudyPortWiseCargosWithException() {
    DischargeStudyRequest request =
        DischargeStudyRequest.newBuilder().setDischargeStudyId(1l).build();
    StreamRecorder<LoadableStudyModels.DishargeStudyCargoReply> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1")).when(loadableStudyRepository).existsById(anyLong());

    this.dischargeStudyService.getDischargeStudyPortWiseCargos(request, responseObserver);
    List<LoadableStudyModels.DishargeStudyCargoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<BackLoading> getBackLoadingList() {
    List<BackLoading> backLoadingList = new ArrayList<>();
    BackLoading backLoading = new BackLoading();
    backLoading.setId(1l);
    backLoading.setCargoId(1l);
    backLoading.setApi(new BigDecimal(1));
    backLoading.setAbbreviation("1");
    backLoading.setDischargeStudyId(1l);
    backLoading.setColour("1");
    backLoading.setTemperature(new BigDecimal(1));
    backLoading.setCargoId(1l);
    backLoadingList.add(backLoading);
    return backLoadingList;
  }

  @Test
  void testAddCargoNominationForPortRotation() throws GenericServiceException {
    Map<Long, List<BackLoading>> backloadingData = new HashMap<>();
    backloadingData.put(1l, Arrays.asList(getBackLoadingList().get(0)));

    when(loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(createLoadableList().get(0)));
    when(loadableStudyPortRotationRepository.findByLoadableStudyIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());
    when(cargoNominationService.getCargoNominations(anyLong()))
        .thenReturn(getCargoNominationList());
    when(backLoadingService.getBackloadingDataByportIds(anyLong(), anyList()))
        .thenReturn(backloadingData);
    when(cargoNominationService.createCargoNominationPortDetails(
            any(CargoNomination.class),
            any(CargoNomination.class),
            any(LoadableStudyPortRotation.class),
            anyInt()))
        .thenReturn(getCargoNominationList().get(0).getCargoNominationPortDetails());
    when(cargoNominationOperationDetailsRepository.findByCargoNominationAndPortIdAndIsActiveTrue(
            any(CargoNomination.class), anyLong()))
        .thenReturn(new CargoNominationPortDetails());

    this.dischargeStudyService.addCargoNominationForPortRotation(1l, 1l);
    verify(cargoNominationService).saveAll(anyList());
  }

  @Test
  void testResetCargoNominationQuantityAndBackLoading() throws GenericServiceException {
    when(loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(createLoadableList().get(0)));
    when(loadableStudyPortRotationRepository.findByLoadableStudyIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());
    when(cargoNominationService.getCargoNominations(anyLong()))
        .thenReturn(getCargoNominationList());
    when(cargoNominationService.createCargoNominationPortDetails(
            any(CargoNomination.class),
            any(CargoNomination.class),
            any(LoadableStudyPortRotation.class),
            anyInt()))
        .thenReturn(getCargoNominationList().get(0).getCargoNominationPortDetails());
    when(cargoNominationOperationDetailsRepository.findByCargoNominationAndPortIdAndIsActiveTrue(
            any(CargoNomination.class), anyLong()))
        .thenReturn(new CargoNominationPortDetails());
    when(cargoNominationService.getMaxQuantityForCargoNomination(anyList(), anySet()))
        .thenReturn(getCargoNominationList());
    when(backLoadingService.getBackLoadings(anyLong(), anyList())).thenReturn(getBackLoadingList());

    dischargeStudyService.resetCargoNominationQuantityAndBackLoading(1l, 1l);
    verify(loadableStudyPortRotationRepository).saveAll(anyList());
  }

  private DischargePatternQuantityCargoPortwiseDetails getPortwiseDetails() {
    DischargePatternQuantityCargoPortwiseDetails portWiseDetails =
        new DischargePatternQuantityCargoPortwiseDetails();
    portWiseDetails.setPortRotationId(1l);
    portWiseDetails.setCargoAbbreviation("1");
    portWiseDetails.setEstimatedAPI(new BigDecimal(1));
    portWiseDetails.setCargoId(1l);
    portWiseDetails.setDischargeCargoNominationId(1l);
    portWiseDetails.setEstimatedTemp(new BigDecimal(1));
    portWiseDetails.setColorCode("1");
    portWiseDetails.setDischargeMT(new BigDecimal(1));
    portWiseDetails.setDischargingRate(new BigDecimal(1));
    portWiseDetails.setTimeRequiredForDischarging(new BigDecimal(1));
    return portWiseDetails;
  }

  @Test
  void testGetDischargePlanDetails() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.newBuilder()
            .setLoadablePatternId(1l)
            .build();
    StreamRecorder<com.cpdss.common.generated.LoadableStudy.CargoNominationReply> responseObserver =
        StreamRecorder.create();
    LoadablePattern pattern = new LoadablePattern();
    pattern.setId(1l);

    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(createLoadableList().get(0)));
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());
    when(cargoNominationService.getCargoNominationByLoadableStudyId(anyLong()))
        .thenReturn(getCargoNominationList());
    when(dischargePatternQuantityCargoPortwiseRepository
            .findByDischargeCargoNominationIdInAndOperationTypeAndIsActiveTrue(
                anyList(), anyString()))
        .thenReturn(Arrays.asList(getPortwiseDetails()));
    when(loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
            any(LoadableStudy.class), anyBoolean()))
        .thenReturn(Arrays.asList(pattern));

    ReflectionTestUtils.setField(
        dischargeStudyService, "dischargeStudyRepository", loadableStudyRepository);

    dischargeStudyService.getDischargePlanDetails(request, responseObserver);
    List<com.cpdss.common.generated.LoadableStudy.CargoNominationReply> replies =
        responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(1l, replies.get(0).getPatternId());
    assertEquals(1l, replies.get(0).getCargoNominations(0).getLoadingPortDetails(0).getPortId());
  }

  @Test
  void testGetDischargePlanDetailsWithGenericException() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.newBuilder()
            .setLoadablePatternId(1l)
            .build();
    StreamRecorder<com.cpdss.common.generated.LoadableStudy.CargoNominationReply> responseObserver =
        StreamRecorder.create();

    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());

    ReflectionTestUtils.setField(
        dischargeStudyService, "dischargeStudyRepository", loadableStudyRepository);

    dischargeStudyService.getDischargePlanDetails(request, responseObserver);
    List<com.cpdss.common.generated.LoadableStudy.CargoNominationReply> replies =
        responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetDischargePlanDetailsWithException() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.newBuilder()
            .setLoadablePatternId(1l)
            .build();
    StreamRecorder<com.cpdss.common.generated.LoadableStudy.CargoNominationReply> responseObserver =
        StreamRecorder.create();

    doThrow(new RuntimeException("1"))
        .when(loadableStudyRepository)
        .findByIdAndIsActive(anyLong(), anyBoolean());

    ReflectionTestUtils.setField(
        dischargeStudyService, "dischargeStudyRepository", loadableStudyRepository);

    dischargeStudyService.getDischargePlanDetails(request, responseObserver);
    List<com.cpdss.common.generated.LoadableStudy.CargoNominationReply> replies =
        responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testConfirmPlan() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.newBuilder()
            .setLoadablePatternId(1l)
            .build();
    StreamRecorder<com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply> responseObserver =
        StreamRecorder.create();
    LoadablePattern pattern = new LoadablePattern();
    pattern.setId(1l);
    pattern.setLoadableStudy(createLoadableList().get(0));
    LoadableStudyAlgoStatus algoStatus = new LoadableStudyAlgoStatus();
    algoStatus.setCreatedDateTime(LocalDateTime.now());
    algoStatus.setProcessId("1");
    DischargeStudyCowDetail dsCow = new DischargeStudyCowDetail();
    dsCow.setPercentage(1l);
    dsCow.setTankIds("1");
    dsCow.setCowType(1l);
    when(loadablePatternService.confirmPlan(
            any(com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.class),
            any(com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.Builder.class)))
        .thenReturn(
            com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.newBuilder()
                .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build()));
    when(loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(pattern));
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());
    when(loadableStudyAlgoStatusRepository.findByLoadableStudyIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(algoStatus));
    when(this.loadablePatternRepository.findByLoadableStudyAndLoadableStudyStatusAndIsActive(
            any(LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(Optional.of(pattern));
    when(this.dischargeStudyCowDetailRepository.findByDischargeStudyStudyIdAndPortIdAndIsActive(
            anyLong(), anyLong(), anyBoolean()))
        .thenReturn(dsCow);
    when(dischargePlanServiceBlockingStub.dischargePlanSynchronization(
            any(com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest.class)))
        .thenReturn(Common.ResponseStatus.newBuilder().build());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargePlanServiceBlockingStub",
        dischargePlanServiceBlockingStub);
    dischargeStudyService.confirmPlan(request, responseObserver);
    List<com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply> replies =
        responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
  }

  @Test
  void testConfirmPlanWithException() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request =
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.newBuilder()
            .setLoadablePatternId(1l)
            .build();
    StreamRecorder<com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply> responseObserver =
        StreamRecorder.create();

    when(loadablePatternService.confirmPlan(
            any(com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.class),
            any(com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.Builder.class)))
        .thenThrow(new RuntimeException("1"));

    dischargeStudyService.confirmPlan(request, responseObserver);
    List<com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply> replies =
        responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGetCowHistoryByVesselId() {
    com.cpdss.common.generated.LoadableStudy.CowHistoryRequest request =
        com.cpdss.common.generated.LoadableStudy.CowHistoryRequest.newBuilder()
            .setVesselId(1l)
            .build();
    StreamRecorder<com.cpdss.common.generated.LoadableStudy.CowHistoryReply> responseObserver =
        StreamRecorder.create();
    CowHistory cowHistory = new CowHistory();
    cowHistory.setId(1l);
    cowHistory.setVesselId(1l);
    cowHistory.setVoyageId(1l);
    cowHistory.setPortId(1l);
    cowHistory.setTankId(1l);
    cowHistory.setCowTypeId(1l);

    when(cowHistoryRepository.findAllByVesselIdAndIsActiveTrue(anyLong(), any(Pageable.class)))
        .thenReturn(Arrays.asList(cowHistory));
    when(voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(createLoadableList().get(0).getVoyage());

    dischargeStudyService.getCowHistoryByVesselId(request, responseObserver);
    List<com.cpdss.common.generated.LoadableStudy.CowHistoryReply> replies =
        responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(1l, replies.get(0).getCowHistory(0).getId());
  }

  @Test
  void testGetCowHistoryByVesselIdWithException() {
    com.cpdss.common.generated.LoadableStudy.CowHistoryRequest request =
        com.cpdss.common.generated.LoadableStudy.CowHistoryRequest.newBuilder()
            .setVesselId(1l)
            .build();
    StreamRecorder<com.cpdss.common.generated.LoadableStudy.CowHistoryReply> responseObserver =
        StreamRecorder.create();

    when(cowHistoryRepository.findAllByVesselIdAndIsActiveTrue(anyLong(), any(Pageable.class)))
        .thenThrow(new RuntimeException("1"));

    dischargeStudyService.getCowHistoryByVesselId(request, responseObserver);
    List<com.cpdss.common.generated.LoadableStudy.CowHistoryReply> replies =
        responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testUpdateDischargeStudy() {
    DischargeStudyDetail request =
        DischargeStudyDetail.newBuilder().setName("update DS").setEnquiryDetails("details").build();
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    entity.setName("update DS");
    entity.setDetails("details");
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadableStudy()));

    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<UpdateDischargeStudyReply> responseObserver = StreamRecorder.create();
    this.dischargeStudyService.updateDischargeStudy(request, responseObserver);
    List<UpdateDischargeStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getDischargeStudy().getId());
  }

  @Test
  void testDeleteDischargeStudy() throws GenericServiceException {
    DischargeStudyRequest request =
        DischargeStudyRequest.newBuilder().setDischargeStudyId(1L).build();
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    entity.setName("update DS");
    entity.setDetails("details");
    entity.setActive(false);
    Voyage voyage = new com.cpdss.loadablestudy.entity.Voyage();
    VoyageStatus status = new VoyageStatus();
    status.setId(3L);
    status.setName("ACTIVE");
    voyage.setVoyageStatus(status);
    voyage.setId(1L);
    entity.setVoyage(voyage);
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(voyage);
    doNothing().when(this.voyageService).checkIfVoyageClosed(anyLong());
    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    ReflectionTestUtils.setField(
        dischargeStudyService, "dischargeStudyRepository", loadableStudyRepository);

    StreamRecorder<DischargeStudyReply> responseObserver = StreamRecorder.create();
    this.dischargeStudyService.deleteDischargeStudy(request, responseObserver);
    List<DischargeStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testDeleteDischargeStudyWithGenericException() {
    DischargeStudyRequest request =
        DischargeStudyRequest.newBuilder().setDischargeStudyId(1L).build();
    StreamRecorder<DischargeStudyReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findById(anyLong())).thenReturn(Optional.empty());
    ReflectionTestUtils.setField(
        dischargeStudyService, "dischargeStudyRepository", loadableStudyRepository);

    this.dischargeStudyService.deleteDischargeStudy(request, responseObserver);
    List<DischargeStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testDeleteDischargeStudyWithException() {
    DischargeStudyRequest request =
        DischargeStudyRequest.newBuilder().setDischargeStudyId(1L).build();
    StreamRecorder<DischargeStudyReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findById(anyLong())).thenThrow(new RuntimeException("1"));
    ReflectionTestUtils.setField(
        dischargeStudyService, "dischargeStudyRepository", loadableStudyRepository);

    this.dischargeStudyService.deleteDischargeStudy(request, responseObserver);
    List<DischargeStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGenerateDischargeStudy() throws GenericServiceException, Exception {
    AlgoRequest request = AlgoRequest.newBuilder().setLoadableStudyId(1L).build();
    AlgoReply.Builder reply = AlgoReply.newBuilder().setProcesssId("123");
    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();

    when(this.generateDischargeStudyJson.generateDischargePatterns(
            any(AlgoRequest.class), any(AlgoReply.Builder.class)))
        .thenReturn(reply);

    this.dischargeStudyService.generateDischargePatterns(request, responseObserver);
    List<AlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGenerateDischargeStudyGenericException() throws GenericServiceException, Exception {
    AlgoRequest request = AlgoRequest.newBuilder().setLoadableStudyId(1L).build();
    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();

    when(this.generateDischargeStudyJson.generateDischargePatterns(
            any(AlgoRequest.class), any(AlgoReply.Builder.class)))
        .thenThrow(
            new GenericServiceException(
                "Failed",
                CommonErrorCodes.E_CPDSS_NO_DISCHARGE_STUDY_FOUND,
                HttpStatusCode.INTERNAL_SERVER_ERROR));

    this.dischargeStudyService.generateDischargePatterns(request, responseObserver);
    List<AlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGenerateDischargeStudyResourceAccessException()
      throws GenericServiceException, Exception {
    AlgoRequest request = AlgoRequest.newBuilder().setLoadableStudyId(1L).build();
    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();

    when(this.generateDischargeStudyJson.generateDischargePatterns(
            any(AlgoRequest.class), any(AlgoReply.Builder.class)))
        .thenThrow(ResourceAccessException.class);

    this.dischargeStudyService.generateDischargePatterns(request, responseObserver);
    List<AlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  @Test
  void testGenerateDischargeStudyException() throws GenericServiceException, Exception {
    AlgoRequest request = AlgoRequest.newBuilder().setLoadableStudyId(1L).build();
    StreamRecorder<AlgoReply> responseObserver = StreamRecorder.create();

    when(this.generateDischargeStudyJson.generateDischargePatterns(
            any(AlgoRequest.class), any(AlgoReply.Builder.class)))
        .thenThrow(RuntimeException.class);

    this.dischargeStudyService.generateDischargePatterns(request, responseObserver);
    List<AlgoReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(FAILED, replies.get(0).getResponseStatus().getStatus());
  }

  private List<OnHandQuantity> createOnHandQuantityList() {
    OnHandQuantity onHandQuantity = new OnHandQuantity();
    return Arrays.asList(onHandQuantity);
  }

  private List<SynopticalTable> createLoadableSynopticalTableList() {
    List<SynopticalTable> synopticalTableList = new ArrayList<>();
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setEtdActual(LocalDateTime.now());
    synopticalTable.setEtaActual(LocalDateTime.now());
    synopticalTable.setIsActive(true);
    synopticalTable.setOperationType("ARR");
    synopticalTable.setId(1l);
    synopticalTableList.add(synopticalTable);
    return synopticalTableList;
  }

  private List<LoadableStudyPortRotation> createLoadableStudyPortRotationList() {
    List<LoadableStudyPortRotation> portRotationList = new ArrayList<>();
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1l);
    loadableStudyPortRotation.setPortOrder(1L);
    loadableStudyPortRotation.setPortXId(1l);
    CargoOperation operation = new CargoOperation();
    operation.setId(2l);
    loadableStudyPortRotation.setOperation(operation);
    loadableStudyPortRotation.setSynopticalTable(createLoadableSynopticalTableList());
    portRotationList.add(loadableStudyPortRotation);
    return portRotationList;
  }

  private List<LoadableStudy> createLoadableList() {
    LoadableStudy entity = new LoadableStudy();
    entity.setVesselXId(1l);
    entity.setId(2L);
    entity.setName("update DS");
    entity.setDetails("details");
    entity.setActive(false);
    Voyage voyage = new com.cpdss.loadablestudy.entity.Voyage();
    VoyageStatus status = new VoyageStatus();
    status.setId(3L);
    status.setName("ACTIVE");
    voyage.setVoyageStatus(status);
    voyage.setId(1L);
    voyage.setActualEndDate(LocalDateTime.now());
    entity.setVoyage(voyage);
    entity.setPlanningTypeXId(2);
    return Arrays.asList(entity);
  }
}
