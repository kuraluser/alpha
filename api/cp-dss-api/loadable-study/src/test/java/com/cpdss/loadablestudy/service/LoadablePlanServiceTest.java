/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.domain.*;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.repository.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = "cpdss.communication.enable = false")
@SpringJUnitConfig(classes = {LoadablePlanService.class})
public class LoadablePlanServiceTest {

  @Autowired LoadablePlanService loadablePlanService;
  @MockBean CargoNominationRepository cargoNominationRepository;
  @MockBean LoadablePatternCargoDetailsRepository lpCargoDetailsRepository;
  @MockBean LoadableStudyPortRotationRepository portRotationRepository;
  @Mock private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean private CargoOperationRepository cargoOperationRepository;
  @MockBean private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;
  @Mock private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean private SynopticService synopticService;
  @MockBean private LoadableQuantityRepository loadableQuantityRepository;
  @MockBean private LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @MockBean MessageTypes messageTypes;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @MockBean private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @MockBean private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @MockBean private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @MockBean private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterService;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean LoadableStudyStatusRepository loadableStudyStatusRepository;
  @MockBean private RestTemplate restTemplate;
  @MockBean private LoadableStudyService loadableStudyService;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private JsonDataService jsonDataService;

  @MockBean
  private LoadablePatternCargoToppingOffSequenceRepository
      loadablePatternCargoToppingOffSequenceRepository;

  @MockBean private CommunicationService communicationService;
  @MockBean private VoyageService voyageService;

  @MockBean
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @Value("${algo.stowage.edit.api.url}")
  private String algoUpdateUllageUrl;

  @MockBean CargoNominationService cargoNominationService;
  @MockBean CargoService cargoService;
  @MockBean LoadableQuantityService loadableQuantityService;
  @MockBean OnHandQuantityService onHandQuantityService;
  @MockBean OnBoardQuantityService onBoardQuantityService;
  @MockBean LoadableStudyRuleService loadableStudyRuleService;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  private static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";
  @MockBean AlgoService algoService;
  @MockBean private VoyageRepository voyageRepository;
  @MockBean private LoadableStudyStagingService loadableStudyStagingService;
  @MockBean OnBoardQuantityRepository onBoardQuantityRepository;

  @Test
  void testBuildLoadablePlanQuantity() {
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();

    Mockito.when(
            this.cargoNominationRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getCargoNomination());
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "cargoNominationRepository", cargoNominationRepository);

    spyService.buildLoadablePlanQuantity(getLoadablePlanQuantities(), replyBuilder);
    assertEquals("1", replyBuilder.getLoadableQuantityCargoDetails(0).getLoadingPorts(0).getName());
  }

  @Test
  void testSetLoadingPortForLoadableQuantityCargoDetails() {
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    LoadableStudy.LoadableQuantityCargoDetails.Builder builder =
        LoadableStudy.LoadableQuantityCargoDetails.newBuilder();
    List<Long> lpPortRotationIds = new ArrayList<>(Arrays.asList(1l));

    when(loadablePatternCargoDetailsRepository.findAllPortRotationIdByCargoNomination(anyLong()))
        .thenReturn(lpPortRotationIds);
    when(loadableStudyPortRotationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getPortRotation());
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternCargoDetailsRepository", loadablePatternCargoDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);

    var result =
        spyService.setLoadingPortForLoadableQuantityCargoDetails(
            getLoadablePlanQuantities().get(0), builder);
    assertEquals(false, result);
  }

  @Test
  void testFetchPortNameFromPortService() {
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    var result = spyService.fetchPortNameFromPortService(1l);
    assertEquals("1", result.getName());
  }

  private SynopticalTableLoadicatorData getLoadicatorData() {
    SynopticalTableLoadicatorData data = new SynopticalTableLoadicatorData();
    data.setBendingMoment(new BigDecimal(1));
    data.setCalculatedDraftAftActual(new BigDecimal(1));
    data.setCalculatedDraftFwdPlanned(new BigDecimal(1));
    data.setCalculatedDraftMidPlanned(new BigDecimal(1));
    data.setCalculatedTrimPlanned(new BigDecimal(1));
    data.setFreeboard(new BigDecimal(1));
    data.setManifoldHeight(new BigDecimal(1));
    data.setShearingForce(new BigDecimal(1));
    return data;
  }

  private List<com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails> getStowageDetailsList() {
    List<com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails> stowageDetailsList =
        new ArrayList<>();
    com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails loadablePlanStowageDetails =
        new com.cpdss.loadablestudy.domain.LoadablePlanStowageDetails();
    loadablePlanStowageDetails.setId(1L);
    loadablePlanStowageDetails.setTankId(1l);
    stowageDetailsList.add(loadablePlanStowageDetails);
    return stowageDetailsList;
  }

  private SynopticalTable getSynopticalTable() {
    SynopticalTable synopticalTable = new SynopticalTable();
    return synopticalTable;
  }

  @Test
  void testBuildLoadablePlanPortWiseDetails() {
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    LoadabalePatternValidateRequest loadabalePatternValidateRequest =
        new LoadabalePatternValidateRequest();
    LoadablePattern pattern = new LoadablePattern();
    pattern.setLoadableStudy(getLS());
    pattern.setId(1l);
    List<LoadableStudyPortRotation> rotationList = new ArrayList<>();
    rotationList.add(getPortRotation());
    List<LoadablePatternCargoDetails> cargoDetailsList = new ArrayList<>();
    List<LoadablePlanStowageBallastDetails> ballastDetailsList = new ArrayList<>();
    LoadablePlanStowageBallastDetails ballastDetails = new LoadablePlanStowageBallastDetails();
    ballastDetails.setId(1l);
    ballastDetailsList.add(ballastDetails);
    List<LoadablePlanComminglePortwiseDetails> portwiseDetailsList = new ArrayList<>();

    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(rotationList);
    when(loadableStudyPortRotationService.getLastPortRotationId(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), any(CargoOperation.class)))
        .thenReturn(1l);
    when(this.cargoOperationRepository.getOne(anyLong())).thenReturn(getCargoOp());
    when(loadableStudyPortRotationService.getLastPort(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), any(CargoOperation.class)))
        .thenReturn(1l);
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    doReturn(getStowageDetailsList())
        .when(spyService)
        .addLoadablePatternsStowageDetails(anyList(), anyBoolean(), anyLong());
    doReturn(getLoadablePlanBallastDetails())
        .when(spyService)
        .addLoadablePlanBallastDetails(anyList(), anyBoolean(), anyLong());
    when(loadablePatternCargoDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(cargoDetailsList);
    when(loadablePlanStowageBallastDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(ballastDetailsList);
    doReturn(getStowageDetailsList())
        .when(spyService)
        .addLoadablePlanCommingleDetails(anyList(), anyBoolean(), anyLong());
    when(loadablePlanCommingleDetailsPortwiseRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(portwiseDetailsList);
    when(synopticalTableRepository.findByLoadableStudyAndPortRotationAndOperationTypeAndIsActive(
            anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(Optional.of(getSynopticalTable()));
    when(synopticalTableLoadicatorDataRepository
            .findByloadablePatternIdAndSynopticalTableAndIsActive(
                anyLong(), any(SynopticalTable.class), anyBoolean()))
        .thenReturn(getLoadicatorData());
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);
    ReflectionTestUtils.setField(spyService, "cargoOperationRepository", cargoOperationRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationService", loadableStudyPortRotationService);
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternCargoDetailsRepository", loadablePatternCargoDetailsRepository);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanStowageBallastDetailsRepository",
        loadablePlanStowageBallastDetailsRepository);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanCommingleDetailsPortwiseRepository",
        loadablePlanCommingleDetailsPortwiseRepository);
    ReflectionTestUtils.setField(
        spyService, "synopticalTableRepository", synopticalTableRepository);
    ReflectionTestUtils.setField(
        spyService,
        "synopticalTableLoadicatorDataRepository",
        synopticalTableLoadicatorDataRepository);
    spyService.buildLoadablePlanPortWiseDetails(pattern, loadabalePatternValidateRequest);
    assertEquals(
        1l,
        loadabalePatternValidateRequest
            .getLoadablePlanPortWiseDetails()
            .get(0)
            .getArrivalCondition()
            .getLoadablePlanStowageDetails()
            .get(0)
            .getId());
    assertEquals(
        1l, loadabalePatternValidateRequest.getLoadablePlanPortWiseDetails().get(0).getPortId());
  }

  @Test
  void testGetLoadablePlanReport() throws GenericServiceException, IOException {
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    XSSFWorkbook workbook = new XSSFWorkbook();
    LoadableStudy.LoadablePlanReportRequest request =
        LoadableStudy.LoadablePlanReportRequest.newBuilder().build();
    LoadableStudy.LoadablePlanReportReply.Builder dataChunkBuilder =
        LoadableStudy.LoadablePlanReportReply.newBuilder();
    VesselPlanTable vesselPlanTable = VesselPlanTable.builder().build();
    SheetCoordinates cargoDetailsTableCoordinates = new SheetCoordinates(1, 1);
    PortOperationTable portOperationTable = PortOperationTable.builder().build();
    CargoDetailsTable cargoDetailsTable = CargoDetailsTable.builder().build();

    when(synopticService.buildPortOperationsTable(anyLong(), anyLong()))
        .thenReturn(portOperationTable);
    CommingleDetails details =
        new CommingleDetails("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
    when(loadablePlanCommingleDetailsRepository.findByLoadablePatternIdAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(details));
    doReturn(vesselPlanTable).when(spyService).buildVesselPlanTableData(anyLong(), anyLong());
    doReturn(cargoDetailsTableCoordinates)
        .when(spyService)
        .drawCargoDetailsTable(
            any(XSSFSheet.class), any(CargoDetailsTable.class), anyInt(), anyInt());
    doReturn(cargoDetailsTableCoordinates)
        .when(spyService)
        .drawPortOperationTable(
            any(XSSFSheet.class), any(PortOperationTable.class), anyInt(), anyInt());
    doReturn(cargoDetailsTableCoordinates)
        .when(spyService)
        .drawVesselPlanTable(any(XSSFSheet.class), any(VesselPlanTable.class), anyInt(), anyInt());
    doReturn(cargoDetailsTable).when(spyService).buildCargoDetailsTable(anyLong(), anyLong());
    ReflectionTestUtils.setField(spyService, "synopticService", synopticService);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanCommingleDetailsRepository",
        loadablePlanCommingleDetailsRepository);

    spyService.getLoadablePlanReport(workbook, request, dataChunkBuilder);
    assertEquals(SUCCESS, dataChunkBuilder.getResponseStatus().getStatus());
  }

  @Test
  void testBuildVesselPlanTableData() throws GenericServiceException, IOException {
    List<LoadablePlanStowageDetails> stowageDetailsList = new ArrayList<>();
    stowageDetailsList.add(getLoadablePSD());

    when(this.vesselInfoGrpcService.getVesselDetailByVesselId(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(this.vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    when(loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLoadablePlanQuantities());
    when(loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLoadablePlanCommingleDetails());
    when(this.vesselInfoGrpcService.getVesselInfoBytankIds(any(VesselInfo.VesselTankRequest.class)))
        .thenReturn(getVesselTankResponse());
    when(loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(stowageDetailsList);
    when(this.stowageDetailsTempRepository.findByLoadablePlanStowageDetailsInAndIsActive(
            anyList(), anyBoolean()))
        .thenReturn(getLPSDT());
    when(loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLoadablePlanBallastDetails());
    when(loadablePlanCommentsRepository.findByLoadablePatternAndIsActiveOrderByIdDesc(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getPlanCommentsList());
    when(loadableQuantityRepository.findFirstByLoadableStudyXIdOrderByLastModifiedDateTimeDesc(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class)))
        .thenReturn(Optional.of(getLoadableQuantity()));
    when(loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getAlgoStatusList());
    when(stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPSDT());
    when(onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(new ArrayList<>());

    ReflectionTestUtils.setField(
        loadablePlanService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = loadablePlanService.buildVesselPlanTableData(1l, 1l);
    assertEquals("1", result.getVesselName());
  }

  @Test
  void testGetVesselDetailByVesselId() {
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    VesselInfo.VesselRequest request = VesselInfo.VesselRequest.newBuilder().build();
    when(this.vesselInfoGrpcService.getVesselDetailByVesselId(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());

    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = spyService.getVesselDetailByVesselId(request);
    assertEquals("SUCCESS", result.getResponseStatus().getStatus());
  }

  @Test
  void testBuildCargoDetailsTable() throws GenericServiceException {
    VesselInfo.VesselRequest request = VesselInfo.VesselRequest.newBuilder().build();
    List<CargoNomination> cargoNominationList = new ArrayList<>();
    cargoNominationList.add(getCargoNomination().get());
    List<LoadablePlanStowageDetails> stowageDetailsList = new ArrayList<>();
    stowageDetailsList.add(getLoadablePSD());

    when(this.vesselInfoGrpcService.getVesselDetailByVesselId(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    when(this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderByCreatedDateTime(
            anyLong(), anyBoolean()))
        .thenReturn(cargoNominationList);
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(this.vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    when(loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLoadablePlanQuantities());
    when(loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLoadablePlanCommingleDetails());
    when(this.vesselInfoGrpcService.getVesselInfoBytankIds(any(VesselInfo.VesselTankRequest.class)))
        .thenReturn(getVesselTankResponse());
    when(loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(stowageDetailsList);
    when(this.stowageDetailsTempRepository.findByLoadablePlanStowageDetailsInAndIsActive(
            anyList(), anyBoolean()))
        .thenReturn(getLPSDT());
    when(loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLoadablePlanBallastDetails());
    when(loadablePlanCommentsRepository.findByLoadablePatternAndIsActiveOrderByIdDesc(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getPlanCommentsList());
    when(loadableQuantityRepository.findFirstByLoadableStudyXIdOrderByLastModifiedDateTimeDesc(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class)))
        .thenReturn(Optional.of(getLoadableQuantity()));
    when(loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getAlgoStatusList());
    when(stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPSDT());
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    when(onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(new ArrayList<>());

    ReflectionTestUtils.setField(
        loadablePlanService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(loadablePlanService, "portInfoGrpcService", portInfoGrpcService);

    var result = loadablePlanService.buildCargoDetailsTable(1l, 1l);
    assertEquals(1.0, result.getCargosTableList().get(0).getApi());
  }

  @Test
  void testBuildLoadablePlanDetails() throws GenericServiceException {
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.newBuilder();
    List<LoadablePlanStowageDetails> stowageDetailsList = new ArrayList<>();
    stowageDetailsList.add(getLoadablePSD());

    when(this.vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLoadablePlanQuantities());
    when(loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLoadablePlanCommingleDetails());
    when(this.vesselInfoGrpcService.getVesselInfoBytankIds(any(VesselInfo.VesselTankRequest.class)))
        .thenReturn(getVesselTankResponse());
    when(loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(stowageDetailsList);
    when(this.stowageDetailsTempRepository.findByLoadablePlanStowageDetailsInAndIsActive(
            anyList(), anyBoolean()))
        .thenReturn(getLPSDT());
    when(loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLoadablePlanBallastDetails());
    when(loadablePlanCommentsRepository.findByLoadablePatternAndIsActiveOrderByIdDesc(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getPlanCommentsList());
    when(loadableQuantityRepository.findFirstByLoadableStudyXIdOrderByLastModifiedDateTimeDesc(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class)))
        .thenReturn(Optional.of(getLoadableQuantity()));
    when(loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getAlgoStatusList());
    when(stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPSDT());
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    when(onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(new ArrayList<>());
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(new ArrayList<>());

    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "cargoNominationRepository", cargoNominationRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePlanQuantityRepository", loadablePlanQuantityRepository);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanCommingleDetailsRepository",
        loadablePlanCommingleDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePlanStowageDetailsRespository", loadablePlanStowageDetailsRespository);
    ReflectionTestUtils.setField(
        spyService, "stowageDetailsTempRepository", stowageDetailsTempRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePlanBallastDetailsRepository", loadablePlanBallastDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePlanCommentsRepository", loadablePlanCommentsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableQuantityRepository", loadableQuantityRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternAlgoStatusRepository", loadablePatternAlgoStatusRepository);
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "onBoardQuantityRepository", onBoardQuantityRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyPortRotationRepository", loadableStudyPortRotationRepository);

    spyService.buildLoadablePlanDetails(getLP(), replyBuilder);
    assertEquals("SUCCESS", replyBuilder.getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTanks() {
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    VesselInfo.VesselRequest request = VesselInfo.VesselRequest.newBuilder().build();
    when(this.vesselInfoGrpcService.getVesselTanks(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = spyService.getVesselTanks(request);
    assertEquals("SUCCESS", result.getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselTankDetailsByTankIds() {
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    VesselInfo.VesselTankRequest request = VesselInfo.VesselTankRequest.newBuilder().build();
    when(this.vesselInfoGrpcService.getVesselInfoBytankIds(any(VesselInfo.VesselTankRequest.class)))
        .thenReturn(getVesselTankResponse());

    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = spyService.getVesselTankDetailsByTankIds(request);
    assertEquals("SUCCESS", result.getResponseStatus().getStatus());
  }

  @Test
  void testBuildLoadablePlanCommingleDetails() {

    com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();
    this.loadablePlanService.buildLoadablePlanCommingleDetails(
        getLoadablePlanCommingleDetails(), replyBuilder);
    assertEquals(1L, replyBuilder.getLoadableQuantityCommingleCargoDetails(0).getId());
    assertEquals("1", replyBuilder.getLoadablePlanStowageDetails(0).getCorrectedUllage());
  }

  @Test
  void testBuildBallastGridDetails() {
    List<LoadablePlanStowageDetailsTemp> ballstTempList = new ArrayList<>();
    LoadablePlanStowageDetailsTemp temp = new LoadablePlanStowageDetailsTemp();
    temp.setRdgUllage(new BigDecimal(1));
    temp.setCorrectionFactor(new BigDecimal(1));
    temp.setCorrectedUllage(new BigDecimal(1));
    temp.setQuantity(new BigDecimal(1));
    temp.setFillingRatio(new BigDecimal(1));
    com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();
    LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp =
        new LoadablePlanStowageDetailsTemp();
    loadablePlanStowageDetailsTemp.setLoadablePlanBallastDetails(
        getLoadablePlanBallastDetails().get(0));
    this.loadablePlanService.buildBallastGridDetails(
        getLoadablePlanBallastDetails(), ballstTempList, replyBuilder);
    assertEquals(1L, replyBuilder.getLoadablePlanBallastDetails(0).getId());
  }

  @Test
  void testSetTempBallastDetails() {
    LoadablePlanBallastDetails lpbd = new LoadablePlanBallastDetails();
    lpbd.setId(1L);
    List<LoadablePlanStowageDetailsTemp> ballastTempList = new ArrayList<>();
    LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp =
        new LoadablePlanStowageDetailsTemp();
    loadablePlanStowageDetailsTemp.setRdgUllage(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setCorrectedUllage(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setCorrectionFactor(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setQuantity(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setFillingRatio(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setLoadablePlanBallastDetails(lpbd);
    ballastTempList.add(loadablePlanStowageDetailsTemp);
    com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.newBuilder();
    this.loadablePlanService.setTempBallastDetails(lpbd, ballastTempList, builder);
    assertEquals("1", builder.getCorrectionFactor());
  }

  @Test
  void testSetLoadingPortNameFromCargoOperation() {
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails.newBuilder();

    Mockito.when(
            this.cargoNominationRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getCargoNomination());
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "cargoNominationRepository", cargoNominationRepository);

    var result =
        spyService.setLoadingPortNameFromCargoOperation(
            getLoadablePlanQuantities().get(0), builder);
    assertEquals(true, result);
  }

  @Test
  void testConvertToBbls() {
    float value = 1f;
    float api = 1f;
    float temperature = 1f;
    ConversionUnit conversionUnit = ConversionUnit.LT;
    var bool = this.loadablePlanService.convertToBbls(value, api, temperature, conversionUnit);
    Assert.assertEquals(5.996119f, bool, 0.0f);
  }

  @Test
  void testGetConversionConstant() {
    ConversionUnit conversionUnit = ConversionUnit.LT;
    float api = 1f;
    float temperature = 1f;
    var bool = this.loadablePlanService.getConversionConstant(conversionUnit, api, temperature);
    Assert.assertEquals(0.16677454f, bool, 0.0f);
  }

  @Test
  void testDrawVesselPlanTable() {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet spreadsheet = workbook.createSheet();
    VesselPlanTable vesselPlanTable = VesselPlanTable.builder().build();
    vesselPlanTable.setVesselName("1");
    vesselPlanTable.setVoyageNo("1");
    vesselPlanTable.setDate("1");
    vesselPlanTable.setFrameFromCellsList(getLF());
    vesselPlanTable.setVesselTanksTableList(getVTTL());
    int startRow = 1;
    int starColumn = 1;
    String vesselPlanTableTitle = VesselPlanTableTitles.VESSEL_NAME.getColumnName();
    String stowagePlanTableTitles = StowagePlanTableTitles.CARGO_CODE.getColumnName();
    var sheetCoordinates =
        this.loadablePlanService.drawVesselPlanTable(
            spreadsheet, vesselPlanTable, startRow, starColumn);
    assertEquals(24, sheetCoordinates.getRow());
    assertEquals(1, sheetCoordinates.getColumn());
  }

  @Test
  void testConvertFromBbls() {
    float value = 1f;
    float api = 1f;
    float temperature = 1f;
    ConversionUnit conversionUnit = ConversionUnit.LT;
    var bool = this.loadablePlanService.convertFromBbls(value, api, temperature, conversionUnit);
    Assert.assertEquals(0.16677454f, bool, 0.0f);
  }

  @Test
  void testDrawPortOperationTable() {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet spreadsheet = workbook.createSheet();
    PortOperationTable portOperationTable = PortOperationTable.builder().build();
    portOperationTable.setOperationsTableList(getLOT());
    int startRow = 1;
    int starColumn = 1;
    String portOperationsTableTitle = PortOperationsTableTitles.PORT_NAME.getColumnName();
    var sheetCoordinates =
        this.loadablePlanService.drawPortOperationTable(
            spreadsheet, portOperationTable, startRow, starColumn);
    assertEquals(2, sheetCoordinates.getColumn());
    assertEquals("1", portOperationTable.getOperationsTableList().get(0).getOperation());
  }

  @Test
  void testDrawCargoDetailsTable() {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet spreadsheet = workbook.createSheet();
    CargoDetailsTable cargoDetailsTable = CargoDetailsTable.builder().build();
    cargoDetailsTable.setCargosTableList(getLCT());
    cargoDetailsTable.setNBblsTotal(1d);
    cargoDetailsTable.setMtTotal(1d);
    cargoDetailsTable.setKl15CTotal(1d);
    cargoDetailsTable.setLtTotal(1d);
    cargoDetailsTable.setDiffBblsTotal(1d);
    cargoDetailsTable.setDiffPercentageTotal(1d);
    cargoDetailsTable.setCargoNominationTotal(1d);
    int startRow = 1;
    int starColumn = 1;
    String cargoTableColumnDetails = CargoDetailsTableTitles.CARGO_CODE.getColumnName();
    var sheetCoordinates =
        this.loadablePlanService.drawCargoDetailsTable(
            spreadsheet, cargoDetailsTable, startRow, starColumn);
    assertEquals(3, sheetCoordinates.getColumn());
    assertEquals(13, sheetCoordinates.getRow());
  }

  // buildLoadablePlanDetails need grpc

  @Test
  void testBuildTankDetail() {
    VesselInfo.VesselTankDetail detail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setFrameNumberFrom("1")
            .setFrameNumberTo("1")
            .setShortName("1")
            .setTankCategoryId(1L)
            .setTankCategoryName("1")
            .setTankId(1L)
            .setTankName("1")
            .setIsSlopTank(true)
            .setDensity("1")
            .setFillCapacityCubm("1")
            .setHeightFrom("1")
            .setHeightTo("1")
            .setTankOrder(1)
            .setTankDisplayOrder(1)
            .setTankGroup(1)
            .setFullCapacityCubm("1")
            .build();
    var tankdetail = this.loadablePlanService.buildTankDetail(detail);
    assertEquals("1", tankdetail.getFrameNumberFrom());
  }

  @Test
  void testBuildLoadablePlanStowageCargoDetails() {
    List<LoadablePlanStowageDetails> loadablePlanStowageDetails = new ArrayList<>();
    LoadablePlanStowageDetails loadablePlanStowageDetails1 = new LoadablePlanStowageDetails();
    loadablePlanStowageDetails1.setId(1L);
    loadablePlanStowageDetails1.setAbbreviation("1");
    loadablePlanStowageDetails1.setApi("1");
    loadablePlanStowageDetails1.setCorrectedUllage("1");
    loadablePlanStowageDetails1.setCorrectionFactor("1");
    loadablePlanStowageDetails1.setFillingPercentage("1");
    loadablePlanStowageDetails1.setObservedBarrels("1");
    loadablePlanStowageDetails1.setObservedBarrelsAt60("1");
    loadablePlanStowageDetails1.setObservedM3("1");
    loadablePlanStowageDetails1.setRdgUllage("1");
    loadablePlanStowageDetails1.setTankname("1");
    loadablePlanStowageDetails1.setTankId(1L);
    loadablePlanStowageDetails1.setCargoNominationTemperature(new BigDecimal(1));
    loadablePlanStowageDetails1.setWeight("1");
    loadablePlanStowageDetails1.setColorCode("1");
    loadablePlanStowageDetails1.setCorrectedUllage("1");
    loadablePlanStowageDetails1.setCorrectionFactor("1");
    loadablePlanStowageDetails1.setFillingPercentage("1");
    loadablePlanStowageDetails1.setCargoNominationId(1L);
    loadablePlanStowageDetails.add(loadablePlanStowageDetails1);
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.newBuilder();
    VesselInfo.VesselTankResponse vesselTankData =
        VesselInfo.VesselTankResponse.newBuilder().addVesselTankOrder(getvesselTO()).build();
    VesselInfo.VesselTankOrder vesselTankOrder =
        VesselInfo.VesselTankOrder.newBuilder()
            .setTankId(1L)
            .setShortName("1")
            .setTankDisplayOrder(1)
            .build();
    Mockito.when(
            this.stowageDetailsTempRepository.findByLoadablePlanStowageDetailsInAndIsActive(
                Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(getLPSDT());
    this.loadablePlanService.buildLoadablePlanStowageCargoDetails(
        loadablePlanStowageDetails, replyBuilder, vesselTankData);
    assertEquals(1L, replyBuilder.getLoadablePlanStowageDetails(0).getCargoNominationId());
  }

  @Test
  void testBuildBallastTankLayout() {
    List<VesselInfo.VesselTankDetail> vesselTankDetails = new ArrayList<>();
    VesselInfo.VesselTankDetail vesselTankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankPositionCategory("FRONT")
            .setTankGroup(1)
            .setTankOrder(1)
            .build();
    vesselTankDetails.add(vesselTankDetail);
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.newBuilder();
    this.loadablePlanService.buildBallastTankLayout(vesselTankDetails, replyBuilder);
    assertEquals(false, replyBuilder.getBallastFrontTanksList().isEmpty());
  }

  @Test
  void testGetLoadablePlanDetails() throws GenericServiceException {
    Long vesselId = 1L;
    List<Long> tankCategory = new ArrayList<>();
    tankCategory.add(1L);
    LoadablePlanService spyService = spy(LoadablePlanService.class);
    LoadableStudy.LoadablePlanDetailsRequest request =
        LoadableStudy.LoadablePlanDetailsRequest.newBuilder().setLoadablePatternId(1L).build();
    LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder =
        LoadableStudy.LoadablePlanDetailsReply.newBuilder();
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getOLS());
    doReturn(true)
        .when(spyService)
        .validateLoadableStudyForConfimPlan(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class));
    doNothing()
        .when(spyService)
        .buildLoadablePlanDetails(
            any(Optional.class), any(LoadableStudy.LoadablePlanDetailsReply.Builder.class));
    ReflectionTestUtils.setField(
        spyService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);

    var result = spyService.getLoadablePlanDetails(request, replyBuilder);
    assertEquals(true, result.getConfirmPlanEligibility());
  }

  @Test
  void testValidateLoadableStudyForConfimPlan() {
    com.cpdss.loadablestudy.entity.LoadableStudy ls =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    ls.setVesselXId(1L);
    ls.setPortRotations(getportR());
    var bool = this.loadablePlanService.validateLoadableStudyForConfimPlan(ls);
    assertEquals(Optional.of(1L), Optional.ofNullable(ls.getVesselXId()));
  }

  @Test
  void testSaveComment() {
    LoadableStudy.SaveCommentRequest request =
        LoadableStudy.SaveCommentRequest.newBuilder()
            .setUser(1L)
            .setComment("1")
            .setLoadablePatternId(1l)
            .build();
    LoadableStudy.SaveCommentReply.Builder replyBuilder =
        LoadableStudy.SaveCommentReply.newBuilder();
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    when(this.loadablePlanCommentsRepository.save(any(LoadablePlanComments.class)))
        .thenReturn(getPlanCommentsList().get(0));
    var saveCommentReply = this.loadablePlanService.saveComment(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  @Test
  void testAddLoadablePlanBallastDetails() {
    List<com.cpdss.loadablestudy.entity.LoadablePlanStowageBallastDetails>
        loadablePatternCargoDetails = new ArrayList<>();
    List<Object> objectArray = new ArrayList<>();
    Object[] obj = new Object[6];
    obj[0] = 1l;
    obj[1] = 1l;
    obj[2] = "1";
    obj[3] = "1";
    obj[4] = "1";
    objectArray.add(obj);

    when(stowageDetailsTempRepository.findByLoadablePlanBallastTempDetailsAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(objectArray);

    var results =
        loadablePlanService.addLoadablePlanBallastDetails(loadablePatternCargoDetails, true, 1l);
    assertEquals(1l, results.get(0).getId());
  }

  @Test
  void testAddLoadablePlanBallastDetailsElse() {
    List<com.cpdss.loadablestudy.entity.LoadablePlanStowageBallastDetails> stowageBallastDetails =
        new ArrayList<>();
    LoadablePlanStowageBallastDetails ballastDetails = new LoadablePlanStowageBallastDetails();
    ballastDetails.setId(1l);
    ballastDetails.setQuantity(new BigDecimal(1));
    ballastDetails.setColorCode("1");
    ballastDetails.setSg("1");
    ballastDetails.setColorCode("1");
    stowageBallastDetails.add(ballastDetails);

    var results =
        loadablePlanService.addLoadablePlanBallastDetails(stowageBallastDetails, false, 1l);
    assertEquals(1l, results.get(0).getId());
  }

  @Test
  void testAddLoadablePlanCommingleDetails() {
    List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails> portwiseDetailsList =
        new ArrayList<>();
    LoadablePlanComminglePortwiseDetails portwiseDetails =
        new LoadablePlanComminglePortwiseDetails();
    portwiseDetails.setId(1l);
    portwiseDetails.setTankId(1l);
    portwiseDetails.setQuantity("1");
    portwiseDetails.setCargo1Mt("1");
    portwiseDetails.setCargo2Mt("1");
    portwiseDetails.setCargo1NominationId(1l);
    portwiseDetails.setCargo2NominationId(1l);
    portwiseDetails.setCommingleColour("1");
    portwiseDetails.setGrade("1");
    portwiseDetailsList.add(portwiseDetails);

    var results =
        loadablePlanService.addLoadablePlanCommingleDetails(portwiseDetailsList, true, 1l);
    assertEquals(1l, results.get(0).getId());
  }

  @Test
  void testvalidateLoadablePlan() throws GenericServiceException, IOException {
    LoadableStudy.LoadablePlanDetailsRequest request =
        LoadableStudy.LoadablePlanDetailsRequest.newBuilder().build();
    LoadableStudy.AlgoReply.Builder replyBuilder = LoadableStudy.AlgoReply.newBuilder();

    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());

    loadablePlanService.validateLoadablePlan(request, replyBuilder);
    assertEquals(FAILED, replyBuilder.getResponseStatus().getStatus());
  }

  @Disabled
  @Test
  void testBuildCommunicationServiceRequest() {
    List<LoadablePlanPortWiseDetails> portWiseDetailsList = new ArrayList<>();
    LoadablePlanPortWiseDetails portWiseDetails = new LoadablePlanPortWiseDetails();
    portWiseDetails.setPortId(1l);
    LoadabalePatternDetails patternDetails = new LoadabalePatternDetails();
    patternDetails.setLoadablePlanStowageDetails(getStowageDetailsList());
    portWiseDetails.setArrivalCondition(patternDetails);
    portWiseDetails.setDepartureCondition(patternDetails);
    portWiseDetailsList.add(portWiseDetails);
    LoadabalePatternValidateRequest request = new LoadabalePatternValidateRequest();
    request.setLoadablePlanPortWiseDetails(portWiseDetailsList);

    List<LoadablePatternCargoToppingOffSequence> toppingOffSequenceList = new ArrayList<>();
    SynopticalTableLoadicatorData data = new SynopticalTableLoadicatorData();
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(stowageDetailsTempRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLPSDT());
    when(loadablePatternCargoDetailsRepository.findAllByPatternIdAndPortId(anyLong(), anyLong()))
        .thenReturn(getCargoDetailsList());
    when(synopticalTableLoadicatorDataRepository.findByLoadablePatternIdAndPortIdAndOperationId(
            anyLong(), anyLong(), anyLong()))
        .thenReturn(data);
    when(loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(getLoadablePlanQuantities());
    when(loadablePatternCargoToppingOffSequenceRepository.findByLoadablePatternAndIsActive(
            any(LoadablePattern.class), anyBoolean()))
        .thenReturn(toppingOffSequenceList);

    loadablePlanService.buildCommunicationServiceRequest(request, getLP().get());
    var id =
        request
            .getLoadablePlanPortWiseDetails()
            .get(0)
            .getArrivalCondition()
            .getLoadableQuantityCargoDetails()
            .get(0)
            .getId();
    assertEquals(1l, id);
  }

  @Test
  void testUpdateProcessIdForLoadablePattern() {
    when(loadableStudyStatusRepository.getOne(anyLong()))
        .thenReturn(getAlgoStatusList().get(0).getLoadableStudyStatus());

    loadablePlanService.updateProcessIdForLoadablePattern("1", getLP().get(), 1l, "1", true);
    verify(loadablePatternAlgoStatusRepository).save(any(LoadablePatternAlgoStatus.class));
  }

  @Test
  void testUpdateUllageWithGenericServiceException() throws GenericServiceException {
    LoadableStudy.UpdateUllageRequest request =
        LoadableStudy.UpdateUllageRequest.newBuilder()
            .setLoadablePlanStowageDetails(
                LoadableStudy.LoadablePlanStowageDetails.newBuilder()
                    .setCorrectedUllage("1")
                    .setId(1l)
                    .setTankId(1l)
                    .setApi("1")
                    .setTemperature("1")
                    .setSg("1")
                    .build())
            .setVesselId(1l)
            .setUpdateUllageForLoadingPlan(true)
            .build();
    LoadableStudy.UpdateUllageReply.Builder replyBuilder =
        LoadableStudy.UpdateUllageReply.newBuilder();
    ResponseEntity<UllageUpdateResponse> responseEntity =
        new ResponseEntity<UllageUpdateResponse>(HttpStatus.ACCEPTED);
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(this.cargoOperationRepository.getOne(anyLong())).thenReturn(getCargoOp());
    when(loadableStudyPortRotationService.getLastPortRotationId(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), any(CargoOperation.class)))
        .thenReturn(1l);
    when(synopticalTableRepository.findByLoadableStudyPortRotationAndOperationTypeAndIsActive(
            anyLong(), anyString(), anyBoolean()))
        .thenReturn(Optional.of(getSynopticalTable()));
    when(synopticalTableLoadicatorDataRepository
            .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                any(SynopticalTable.class), anyLong(), anyBoolean()))
        .thenReturn(getLoadicatorData());
    when(this.restTemplate.postForEntity(
            anyString(), any(UllageUpdateRequest.class), any(Class.class)))
        .thenReturn(responseEntity);

    loadablePlanService.updateUllage(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  @Test
  void testUpdateUllage() throws GenericServiceException {
    LoadableStudy.UpdateUllageRequest request =
        LoadableStudy.UpdateUllageRequest.newBuilder()
            .setLoadablePlanStowageDetails(
                LoadableStudy.LoadablePlanStowageDetails.newBuilder()
                    .setCorrectedUllage("1")
                    .setId(1l)
                    .setTankId(1l)
                    .setApi("1")
                    .setTemperature("1")
                    .setSg("1")
                    .setIsBallast(true)
                    .build())
            .setVesselId(1l)
            .setUpdateUllageForLoadingPlan(false)
            .build();
    LoadableStudy.UpdateUllageReply.Builder replyBuilder =
        LoadableStudy.UpdateUllageReply.newBuilder();
    UllageUpdateResponse response = new UllageUpdateResponse();
    response.setFillingRatio("1");
    response.setId(1l);
    response.setCorrectedUllage("1");
    response.setCorrectionFactor("1");
    response.setQuantityMt("1");

    ResponseEntity<UllageUpdateResponse> responseEntity =
        new ResponseEntity<UllageUpdateResponse>(response, HttpStatus.OK);

    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(this.cargoOperationRepository.getOne(anyLong())).thenReturn(getCargoOp());
    when(loadableStudyPortRotationService.getLastPortRotationId(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), any(CargoOperation.class)))
        .thenReturn(1l);
    when(synopticalTableRepository.findByLoadableStudyPortRotationAndOperationTypeAndIsActive(
            anyLong(), anyString(), anyBoolean()))
        .thenReturn(Optional.of(getSynopticalTable()));
    when(synopticalTableLoadicatorDataRepository
            .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                any(SynopticalTable.class), anyLong(), anyBoolean()))
        .thenReturn(getLoadicatorData());
    when(this.restTemplate.postForEntity(
            anyString(), any(UllageUpdateRequest.class), any(Class.class)))
        .thenReturn(responseEntity);
    when(this.loadablePlanBallastDetailsRepository.getOne(anyLong()))
        .thenReturn(getLoadablePlanBallastDetails().get(0));
    when(this.stowageDetailsTempRepository.findByLoadablePlanBallastDetailsAndIsActive(
            any(LoadablePlanBallastDetails.class), anyBoolean()))
        .thenReturn(getLPSDT().get(0));

    loadablePlanService.updateUllage(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
    verify(this.stowageDetailsTempRepository).save(any(LoadablePlanStowageDetailsTemp.class));
  }

  @Test
  void testAddLoadablePatternsStowageDetails() {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> loadablePatternCargoDetails =
        new ArrayList<>();
    List<Object> objectArray = new ArrayList<>();
    Object[] obj = new Object[6];
    obj[0] = 1l;
    obj[1] = 1l;
    obj[2] = 1l;
    obj[3] = "1";
    obj[4] = "1";
    obj[5] = "1";
    objectArray.add(obj);

    when(stowageDetailsTempRepository.findByLoadablePlanStowageTempDetailsAndIsActive(
            anyLong(), anyBoolean()))
        .thenReturn(objectArray);
    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getCargoNomination());

    var results =
        loadablePlanService.addLoadablePatternsStowageDetails(
            loadablePatternCargoDetails, true, 1l);
    assertEquals(1l, results.get(0).getId());
  }

  private List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> getCargoDetailsList() {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> loadablePatternCargoDetails =
        new ArrayList<>();
    LoadablePatternCargoDetails cargoDetails = new LoadablePatternCargoDetails();
    cargoDetails.setCargoId(1l);
    cargoDetails.setId(1l);
    cargoDetails.setTankId(1l);
    cargoDetails.setCargoNominationId(1l);
    cargoDetails.setPlannedQuantity(new BigDecimal(1));
    cargoDetails.setColorCode("1");
    cargoDetails.setAbbreviation("1");
    cargoDetails.setColorCode("1");
    loadablePatternCargoDetails.add(cargoDetails);
    return loadablePatternCargoDetails;
  }

  @Test
  void testAddLoadablePatternsStowageDetailsElse() {
    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getCargoNomination());

    var results =
        loadablePlanService.addLoadablePatternsStowageDetails(getCargoDetailsList(), false, 1l);
    assertEquals(1l, results.get(0).getId());
  }

  private List<LoadablePlanComments> getPlanCommentsList() {
    List<LoadablePlanComments> planCommentsList = new ArrayList<>();
    LoadablePlanComments entity = new LoadablePlanComments();
    entity.setId(1L);
    entity.setComments("1");
    entity.setCreatedBy("1");
    entity.setCreatedDateTime(LocalDateTime.now());
    entity.setLastModifiedDateTime(LocalDateTime.now());
    planCommentsList.add(entity);
    return planCommentsList;
  }

  private LoadableQuantity getLoadableQuantity() {
    LoadableQuantity quantity = new LoadableQuantity();
    quantity.setTotalQuantity(new BigDecimal(1));
    quantity.setLoadableStudyPortRotation(getPortRotation());
    return quantity;
  }

  private List<Float> getLF() {
    List<Float> fo = new ArrayList<>();
    fo.add(1f);
    return fo;
  }

  private List<VesselTanksTable> getVTTL() {
    List<VesselTanksTable> vesselTanksTableList = new ArrayList<>();
    VesselTanksTable vesselTanksTable = VesselTanksTable.builder().build();
    vesselTanksTable.setTankNo("1");
    vesselTanksTable.setFrameNoTo(1f);
    vesselTanksTable.setFrameNoFrom(1f);
    vesselTanksTable.setColorCode("1");
    vesselTanksTable.setCargoCode("1");
    vesselTanksTable.setUllage(1d);
    vesselTanksTable.setLoadedPercentage(1d);
    vesselTanksTable.setShipsNBbls(1d);
    vesselTanksTable.setShipsMt(1d);
    vesselTanksTable.setShipsKlAt15C(1d);

    vesselTanksTableList.add(vesselTanksTable);
    return vesselTanksTableList;
  }

  private Set<CargoNominationPortDetails> getCNPD() {
    Set<CargoNominationPortDetails> cargoNominationPortDetails = new HashSet<>();
    CargoNominationPortDetails cargoNominationPortDetails1 = new CargoNominationPortDetails();
    cargoNominationPortDetails1.setPortId(1L);
    cargoNominationPortDetails.add(cargoNominationPortDetails1);
    return cargoNominationPortDetails;
  }

  private List<LoadablePatternAlgoStatus> getAlgoStatusList() {
    List<LoadablePatternAlgoStatus> algoStatuses = new ArrayList<>();
    LoadablePatternAlgoStatus algoStatus = new LoadablePatternAlgoStatus();
    LoadableStudyStatus status = new LoadableStudyStatus();
    status.setId(1l);
    algoStatus.setLoadableStudyStatus(status);
    algoStatuses.add(algoStatus);
    return algoStatuses;
  }

  private VesselInfo.VesselReply getVesselReply() {
    List<VesselInfo.VesselDetail> vesselDetailList = new ArrayList<>();
    VesselInfo.VesselDetail vesselDetail =
        VesselInfo.VesselDetail.newBuilder().setName("1").build();
    vesselDetailList.add(vesselDetail);
    List<VesselInfo.VesselTankDetail> tankDetailList = new ArrayList<>();
    VesselInfo.VesselTankDetail tankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankCategoryId(1l)
            .setFrameNumberFrom("1")
            .setTankId(1l)
            .setShortName("1")
            .setFrameNumberTo("1")
            .build();
    tankDetailList.add(tankDetail);
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllVessels(vesselDetailList)
            .addAllVesselTanks(tankDetailList)
            .build();
    return vesselReply;
  }

  private VesselInfo.VesselTankResponse getVesselTankResponse() {
    List<VesselInfo.VesselTankOrder> tankOrderList = new ArrayList<>();
    VesselInfo.VesselTankOrder tankOrder =
        VesselInfo.VesselTankOrder.newBuilder()
            .setShortName("1")
            .setTankDisplayOrder(1)
            .setTankId(1l)
            .build();
    tankOrderList.add(tankOrder);

    VesselInfo.VesselTankResponse response =
        VesselInfo.VesselTankResponse.newBuilder()
            .addAllVesselTankOrder(tankOrderList)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  private Optional<com.cpdss.loadablestudy.entity.LoadableStudy> getOLS() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setPortRotations(getportR());
    return Optional.of(loadableStudy);
  }

  private Set<LoadableStudyPortRotation> getportR() {
    Set<LoadableStudyPortRotation> loadableStudyPortRotations = new HashSet<>();
    loadableStudyPortRotations.add(getPortRotation());
    return loadableStudyPortRotations;
  }

  private LoadableStudyPortRotation getPortRotation() {
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(2L);
    loadableStudyPortRotation.setOperation(getCargoOp());
    loadableStudyPortRotation.setEta(LocalDateTime.now());
    loadableStudyPortRotation.setEtd(LocalDateTime.now());
    loadableStudyPortRotation.setLayCanTo(LocalDate.now());
    loadableStudyPortRotation.setLayCanFrom(LocalDate.now());
    loadableStudyPortRotation.setPortXId(1l);
    return loadableStudyPortRotation;
  }

  private CargoOperation getCargoOp() {
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(1L);
    return cargoOperation;
  }

  private Optional<LoadablePattern> getLP() {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePattern.setLoadableStudy(getLS());
    loadablePattern.setCaseNumber(1);
    loadablePattern.setCreatedDate(LocalDate.now());
    loadablePattern.setCaseNumber(1);
    loadablePattern.setLoadableStudyStatus(1l);
    return Optional.of(loadablePattern);
  }

  private com.cpdss.loadablestudy.entity.LoadableStudy getLS() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setVesselXId(1L);
    loadableStudy.setPlanningTypeXId(1);
    Voyage voyage = new Voyage();
    voyage.setVoyageNo("1");
    VoyageStatus status = new VoyageStatus();
    status.setId(1l);
    voyage.setVoyageStatus(status);
    loadableStudy.setVoyage(voyage);
    return loadableStudy;
  }

  private List<LoadablePlanStowageDetailsTemp> getLPSDT() {
    List<LoadablePlanStowageDetailsTemp> loadablePlanStowageDetailsTemps = new ArrayList<>();
    LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp =
        new LoadablePlanStowageDetailsTemp();
    loadablePlanStowageDetailsTemp.setId(1L);
    loadablePlanStowageDetailsTemp.setCorrectedUllage(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setCorrectionFactor(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setFillingRatio(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setQuantity(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setRdgUllage(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setLoadablePlanStowageDetails(getLoadablePSD());
    loadablePlanStowageDetailsTemps.add(loadablePlanStowageDetailsTemp);
    return loadablePlanStowageDetailsTemps;
  }

  private List<LoadablePlanCommingleDetails> getLoadablePlanCommingleDetails() {
    List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails = new ArrayList<>();
    LoadablePlanCommingleDetails loadablePlanCommingleDetails1 = new LoadablePlanCommingleDetails();
    loadablePlanCommingleDetails1.setId(1L);
    loadablePlanCommingleDetails1.setApi("1");
    loadablePlanCommingleDetails1.setCargo1Abbreviation("1");
    loadablePlanCommingleDetails1.setCargo1Bbls60f("1");
    loadablePlanCommingleDetails1.setCargo1BblsDbs("1");
    loadablePlanCommingleDetails1.setCargo1Kl("1");
    loadablePlanCommingleDetails1.setCargo1Lt("1");
    loadablePlanCommingleDetails1.setCargo1Mt("1");
    loadablePlanCommingleDetails1.setCargo1Percentage("1");
    loadablePlanCommingleDetails1.setCargo2Abbreviation("1");
    loadablePlanCommingleDetails1.setCargo2Bbls60f("1");
    loadablePlanCommingleDetails1.setCargo1BblsDbs("1");
    loadablePlanCommingleDetails1.setCargo2Kl("1");
    loadablePlanCommingleDetails1.setCargo2Lt("1");
    loadablePlanCommingleDetails1.setCargo2Mt("1");
    loadablePlanCommingleDetails1.setCargo2Percentage("1");
    loadablePlanCommingleDetails1.setGrade("1");
    loadablePlanCommingleDetails1.setQuantity("1");
    loadablePlanCommingleDetails1.setTankName("1");
    loadablePlanCommingleDetails1.setSlopQuantity("1");
    loadablePlanCommingleDetails1.setTemperature("1");
    loadablePlanCommingleDetails1.setTankShortName("1");
    loadablePlanCommingleDetails1.setCorrectedUllage("1");
    loadablePlanCommingleDetails1.setCorrectionFactor("1");
    loadablePlanCommingleDetails1.setFillingRatio("1");
    loadablePlanCommingleDetails1.setRdgUllage("1");
    loadablePlanCommingleDetails1.setTankId(1L);
    loadablePlanCommingleDetails.add(loadablePlanCommingleDetails1);
    return loadablePlanCommingleDetails;
  }

  private LoadablePlanStowageDetails getLoadablePSD() {
    LoadablePlanStowageDetails loadablePlanStowageDetails = new LoadablePlanStowageDetails();
    loadablePlanStowageDetails.setId(1L);
    loadablePlanStowageDetails.setTankId(1l);
    return loadablePlanStowageDetails;
  }

  private VesselInfo.VesselTankOrder getvesselTO() {
    VesselInfo.VesselTankOrder vesselTankOrder =
        VesselInfo.VesselTankOrder.newBuilder()
            .setTankId(1L)
            .setShortName("1")
            .setTankDisplayOrder(1)
            .build();
    return vesselTankOrder;
  }

  private PortInfo.PortReply getPortReply() {
    List<PortInfo.PortDetail> portDetailList = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder().setId(1l).setName("1").build();
    portDetailList.add(portDetail);
    PortInfo.PortReply reply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllPorts(portDetailList)
            .build();
    return reply;
  }

  private List<LoadablePlanQuantity> getLoadablePlanQuantities() {
    List<LoadablePlanQuantity> loadablePlanQuantities = new ArrayList<>();
    LoadablePlanQuantity loadablePlanQuantity = new LoadablePlanQuantity();
    loadablePlanQuantity.setId(1L);
    loadablePlanQuantity.setDifferenceColor("1");
    loadablePlanQuantity.setDifferencePercentage("1");
    loadablePlanQuantity.setEstimatedApi(new BigDecimal(1));
    loadablePlanQuantity.setCargoNominationTemperature(new BigDecimal(1));
    loadablePlanQuantity.setGrade("1");
    loadablePlanQuantity.setLoadableBbls60f("1");
    loadablePlanQuantity.setLoadableKl("1");
    loadablePlanQuantity.setLoadableBblsDbs("1");
    loadablePlanQuantity.setLoadableLt("1");
    loadablePlanQuantity.setLoadableMt("1");
    loadablePlanQuantity.setMaxTolerence("1");
    loadablePlanQuantity.setMinTolerence("1");
    loadablePlanQuantity.setOrderBbls60f("1");
    loadablePlanQuantity.setOrderBblsDbs("1");
    loadablePlanQuantity.setCargoXId(1L);
    loadablePlanQuantity.setOrderQuantity(new BigDecimal(1));
    loadablePlanQuantity.setSlopQuantity("1");
    loadablePlanQuantity.setTimeRequiredForLoading("1");
    loadablePlanQuantity.setCargoNominationId(1L);
    loadablePlanQuantity.setCargoColor("1");
    loadablePlanQuantity.setCargoAbbreviation("1");
    loadablePlanQuantities.add(loadablePlanQuantity);
    return loadablePlanQuantities;
  }

  private Optional<CargoNomination> getCargoNomination() {
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setCargoXId(1L);
    cargoNomination.setId(1l);
    cargoNomination.setQuantity(new BigDecimal(1));
    cargoNomination.setApi(new BigDecimal(1));
    cargoNomination.setTemperature(new BigDecimal(1));

    cargoNomination.setCargoNominationPortDetails(getCNPD());
    return Optional.of(cargoNomination);
  }

  private List<OperationsTable> getLOT() {
    List<OperationsTable> operationsTables = new ArrayList<>();
    OperationsTable operationsTable = OperationsTable.builder().build();
    operationsTable.setOperation("1");
    operationsTable.setPortName("1");
    operationsTable.setCountry("1");
    operationsTable.setLaycanRange("1");
    operationsTable.setEta("1");
    operationsTable.setEtd("1");
    operationsTable.setArrFwdDraft(1d);
    operationsTable.setArrAftDraft(1d);
    operationsTable.setArrDisplacement(1d);
    operationsTable.setDepFwdDraft(1d);
    operationsTable.setDepAftDraft(1d);
    operationsTable.setDepDisp(1d);
    operationsTables.add(operationsTable);
    return operationsTables;
  }

  private List<LoadablePlanBallastDetails> getLoadablePlanBallastDetails() {
    List<LoadablePlanBallastDetails> loadablePlanBallastDetails = new ArrayList<>();
    LoadablePlanBallastDetails loadablePlanBallastDetails1 = new LoadablePlanBallastDetails();
    loadablePlanBallastDetails1.setId(1L);
    loadablePlanBallastDetails1.setCorrectedLevel("1");
    loadablePlanBallastDetails1.setCorrectionFactor("1");
    loadablePlanBallastDetails1.setCubicMeter("1");
    loadablePlanBallastDetails1.setInertia("1");
    loadablePlanBallastDetails1.setLcg("1");
    loadablePlanBallastDetails1.setMetricTon("1");
    loadablePlanBallastDetails1.setPercentage("1");
    loadablePlanBallastDetails1.setRdgLevel("1");
    loadablePlanBallastDetails1.setSg("1");
    loadablePlanBallastDetails1.setTankId(1L);
    loadablePlanBallastDetails1.setTcg("1");
    loadablePlanBallastDetails1.setVcg("1");
    loadablePlanBallastDetails1.setTankName("1");
    loadablePlanBallastDetails1.setColorCode("1");
    loadablePlanBallastDetails.add(loadablePlanBallastDetails1);
    return loadablePlanBallastDetails;
  }

  private List<CargosTable> getLCT() {
    List<CargosTable> cargosTables = new ArrayList<>();
    CargosTable cargosTable = CargosTable.builder().build();
    cargosTable.setCargoCode("1");
    cargosTable.setColorCode("1");
    cargosTable.setLoadingPort("1");
    cargosTable.setApi(1d);
    cargosTable.setTemp(1d);
    cargosTable.setCargoNomination(1d);
    cargosTable.setTolerance("1");
    cargosTable.setNBbls(1d);
    cargosTable.setMt(1d);
    cargosTable.setKl15C(1d);
    cargosTable.setLt(1d);
    cargosTable.setDiffBbls(1d);
    cargosTable.setDiffPercentage(1d);
    cargosTables.add(cargosTable);
    return cargosTables;
  }

  private List<CargoNomination> getLCN() {
    List<CargoNomination> cargoNominations = new ArrayList<>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setAbbreviation("1");
    cargoNomination.setQuantity(new BigDecimal(1));
    cargoNomination.setTemperature(new BigDecimal(1));
    cargoNomination.setApi(new BigDecimal(1));
    cargoNomination.setCargoNominationPortDetails(getCNPD());
    cargoNominations.add(cargoNomination);
    return cargoNominations;
  }
}
