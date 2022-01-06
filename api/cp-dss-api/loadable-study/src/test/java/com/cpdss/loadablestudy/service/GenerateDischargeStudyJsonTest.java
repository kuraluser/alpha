/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = "cpdss.build.env = ship")
@TestPropertySource(properties = "cpdss.communication.enable = false")
@SpringJUnitConfig(
    classes = {
      GenerateDischargeStudyJson.class,
    })
public class GenerateDischargeStudyJsonTest {
  @Autowired GenerateDischargeStudyJson generateDischargeStudyJson;
  @MockBean private PortInstructionRepository portInstructionRepository;
  @MockBean private CargoNominationRepository cargoNominationRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean private CowDetailService cowDetailService;
  @MockBean private RestTemplate restTemplate;
  @MockBean private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @MockBean private OnHandQuantityRepository onHandQuantityRepository;
  @MockBean private LoadableStudyStatusRepository loadableStudyStatusRepository;
  @MockBean private LoadableStudyService loadableStudyService;
  @MockBean VoyageService voyageService;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanGrpcService;
  @MockBean JsonDataService jsonDataService;
  @MockBean VoyageRepository voyageRepository;
  @MockBean LoadablePatternRepository loadablePatternRepository;
  @MockBean CargoNominationService cargoNominationService;
  @MockBean private CowHistoryRepository cowHistoryRepository;
  @MockBean private LoadableStudyRuleService loadableStudyRuleService;
  @MockBean private CommunicationService communicationService;

  @MockBean
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @MockBean LoadableStudyStagingService loadableStudyStagingService;
  @MockBean LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean
  LoadablePlanCommingleDetailsPortwiseRepository loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean SynopticalTableRepository synopticalTableRepository;
  @MockBean SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  @MockBean
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  private static final String SUCCESS = "SUCCESS";

  @Test
  void testUpdateProcessIdForDischargeStudy() {
    when(loadableStudyStatusRepository.getOne(anyLong())).thenReturn(new LoadableStudyStatus());
    generateDischargeStudyJson.updateProcessIdForDischargeStudy("1", getLoadableStudyEntity(), 1l);
    verify(loadableStudyAlgoStatusRepository).save(any(LoadableStudyAlgoStatus.class));
  }

  private DischargeStudyCowDetail getCowDetail() {
    DischargeStudyCowDetail cowDetail = new DischargeStudyCowDetail();
    cowDetail.setId(1l);
    cowDetail.setCowType(1l);
    cowDetail.setPercentage(1l);
    cowDetail.setTankIds("1");
    return cowDetail;
  }

  @Test
  void testGenerateDischargeStudyJson() throws GenericServiceException {
    List<PortInstruction> instructionsDetails = new ArrayList<>();
    PortInstruction instruction = new PortInstruction();
    instruction.setId(1l);
    instruction.setPortInstruction("1");
    instructionsDetails.add(instruction);
    LoadableStudy.PortRotationReply.Builder reply =
        LoadableStudy.PortRotationReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    List<LoadableStudy.PortRotationDetail> detailList = new ArrayList<>();
    LoadableStudy.PortRotationDetail port =
        LoadableStudy.PortRotationDetail.newBuilder()
            .setId(1l)
            .setLoadableStudyId(1l)
            .setPortId(1l)
            .setBerthId(1l)
            .setOperationId(1l)
            .setSeaWaterDensity("1")
            .setDistanceBetweenPorts("1")
            .setTimeOfStay("1")
            .setMaxDraft("1")
            .setMaxAirDraft("1")
            .setEta("1")
            .setEtd("1")
            .setPortOrder(1l)
            .build();
    detailList.add(port);

    List<LoadingPlanModels.LoadingPlanStabilityParameters> stabilityParametersList =
        new ArrayList<>();
    LoadingPlanModels.LoadingPlanStabilityParameters stabilityParameters =
        LoadingPlanModels.LoadingPlanStabilityParameters.newBuilder()
            .setConditionType(1)
            .setValueType(1)
            .setDraft("1")
            .setTrim("1")
            .setBm("1")
            .setSf("1")
            .build();
    stabilityParametersList.add(stabilityParameters);

    List<LoadingPlanModels.LoadingPlanTankDetails> tankDetailsList = new ArrayList<>();
    LoadingPlanModels.LoadingPlanTankDetails tankDetails =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setConditionType(2)
            .setValueType(1)
            .setId(1l)
            .setApi("1")
            .setCargoNominationId(1l)
            .setQuantity("1")
            .setTankId(1l)
            .setTemperature("1")
            .setUllage("1")
            .setQuantityM3("1")
            .setSounding("1")
            .build();
    tankDetailsList.add(tankDetails);

    LoadingPlanModels.LoadingPlanReply loadingPlanReply =
        LoadingPlanModels.LoadingPlanReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllPortLoadingPlanStabilityParameters(stabilityParametersList)
            .addAllPortLoadingPlanBallastDetails(tankDetailsList)
            .addAllPortLoadingPlanStowageDetails(tankDetailsList)
            .build();

    when(loadingPlanGrpcService.getLoadingPlan(
            any(LoadingPlanModels.LoadingInformationRequest.class)))
        .thenReturn(loadingPlanReply);
    when(this.voyageRepository.findActiveVoyagesByVesselId(anyLong(), anyLong()))
        .thenReturn(getLV());
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getLoadableStudyEntity()));

    when(onHandQuantityRepository.findByDischargeStudyIdAndActive(anyLong())).thenReturn(getOhq());
    when(loadableStudyPortRotationService.getPortRotationByLoadableStudyId(
            any(com.cpdss.common.generated.LoadableStudy.PortRotationRequest.class),
            any(LoadableStudy.PortRotationReply.Builder.class)))
        .thenReturn(reply);
    when(portInstructionRepository.findByIsActive(anyBoolean())).thenReturn(instructionsDetails);
    when(cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderByCreatedDateTime(
            anyLong(), anyBoolean()))
        .thenReturn(getLCN());
    when(loadablePatternRepository.findConfirmedPatternByLoadableStudyId(
            Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getLP());
    when(loadablePatternRepository.findByLoadableStudyAndLoadableStudyStatusAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(Optional.of(getLP().get(0)));
    when(cargoNominationService.getCargoNominations(Mockito.anyLong())).thenReturn(getLCN());
    doCallRealMethod()
        .when(voyageService)
        .fetchActiveVoyageByVesselId(
            any(LoadableStudy.ActiveVoyage.Builder.class), anyLong(), anyLong());
    when(cowHistoryRepository.findAllByVesselIdAndIsActiveTrue(anyLong(), any(Pageable.class)))
        .thenReturn(getCowHistoryList());
    when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(
            any(VesselInfo.VesselRuleRequest.class)))
        .thenReturn(getVesselRuleReply());
    when(this.vesselInfoGrpcService.getVesselInfoBytankIds(any(VesselInfo.VesselTankRequest.class)))
        .thenReturn(
            VesselInfo.VesselTankResponse.newBuilder()
                .addAllVesselTankOrder(
                    Arrays.asList(
                        VesselInfo.VesselTankOrder.newBuilder().setShortName("1").build()))
                .build());
    when(voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(getLV().get(0));
    when(cowDetailService.getCowDetailForDS(anyLong())).thenReturn(getCowDetail());
    when(loadablePatternCargoDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(getLLPCD());
    when(loadablePlanStowageBallastDetailsRepository
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(getLLPBD());
    when(synopticalTableRepository.findByLoadableStudyAndPortRotationAndOperationTypeAndIsActive(
            anyLong(), anyLong(), anyString(), anyBoolean()))
        .thenReturn(getSynopticalTable());
    when(synopticalTableLoadicatorDataRepository
            .findByloadablePatternIdAndSynopticalTableAndIsActive(
                anyLong(), any(SynopticalTable.class), anyBoolean()))
        .thenReturn(getLoadicatorData());

    ReflectionTestUtils.setField(
        generateDischargeStudyJson, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        generateDischargeStudyJson, "loadingPlanGrpcService", loadingPlanGrpcService);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    ReflectionTestUtils.setField(voyageService, "voyageRepository", voyageRepository);
    ReflectionTestUtils.setField(
        voyageService, "cargoNominationRepository", cargoNominationRepository);
    ReflectionTestUtils.setField(
        voyageService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(voyageService, "formatter", formatter);

    var result =
        generateDischargeStudyJson.generateDischargeStudyJson(1l, getLoadableStudyEntity());
    assertEquals(1l, result.getId());
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

  private Optional<SynopticalTable> getSynopticalTable() {
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1L);
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setId(1l);
    synopticalTable.setOperationType("DEP");
    synopticalTable.setLoadableStudyPortRotation(portRotation);
    synopticalTable.setEtdActual(LocalDateTime.now());
    synopticalTable.setIsActive(true);
    return Optional.of(synopticalTable);
  }

  private List<LoadablePlanStowageBallastDetails> getLLPBD() {
    List<LoadablePlanStowageBallastDetails> loadablePlanStowageBallastDetails = new ArrayList<>();
    LoadablePlanStowageBallastDetails ballastDetails = new LoadablePlanStowageBallastDetails();
    ballastDetails.setOperationType("1");
    ballastDetails.setPortRotationId(1l);
    ballastDetails.setLoadablePatternId(1l);
    ballastDetails.setTankXId(1l);
    ballastDetails.setPortXId(1l);
    ballastDetails.setQuantity(new BigDecimal(1));
    loadablePlanStowageBallastDetails.add(ballastDetails);
    return loadablePlanStowageBallastDetails;
  }

  private List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> getLLPCD() {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> list = new ArrayList<>();
    com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails cargoDetails =
        new LoadablePatternCargoDetails();
    cargoDetails.setPortRotationId(1l);
    cargoDetails.setLoadablePatternId(1l);
    cargoDetails.setCargoId(1l);
    cargoDetails.setPortId(1l);
    cargoDetails.setOperationType("1");
    cargoDetails.setAbbreviation("1");
    cargoDetails.setApi(new BigDecimal(1));
    cargoDetails.setTemperature(new BigDecimal(1));
    list.add(cargoDetails);
    return list;
  }

  private VesselInfo.VesselRuleReply getVesselRuleReply() {
    VesselInfo.VesselRuleReply vesselRuleReply =
        VesselInfo.VesselRuleReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return vesselRuleReply;
  }

  private List<com.cpdss.loadablestudy.entity.CowHistory> getCowHistoryList() {
    List<com.cpdss.loadablestudy.entity.CowHistory> cowHistoryList = new ArrayList<>();
    com.cpdss.loadablestudy.entity.CowHistory cowHistory = new CowHistory();
    cowHistory.setId(1l);
    cowHistory.setVoyageId(1l);
    cowHistory.setVesselId(1l);
    cowHistory.setCowTypeId(1l);
    cowHistory.setPortId(1l);
    cowHistory.setTankId(1l);
    cowHistoryList.add(cowHistory);
    return cowHistoryList;
  }

  @Test
  void testGetPortInfo() {
    GenerateDischargeStudyJson spyService = spy(GenerateDischargeStudyJson.class);
    List<Long> list = new ArrayList<>(Arrays.asList(1l, 2l));
    PortInfo.PortReply reply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(portInfoGrpcService.getPortInfoByPortIds(any(PortInfo.GetPortInfoByPortIdsRequest.class)))
        .thenReturn(reply);
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    var result = spyService.getPortInfo(list);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private List<com.cpdss.loadablestudy.entity.OnHandQuantity> getOhq() {
    List<com.cpdss.loadablestudy.entity.OnHandQuantity> ohqList = new ArrayList<>();
    OnHandQuantity onHandQuantity = new OnHandQuantity();
    onHandQuantity.setId(1l);
    onHandQuantity.setPortXId(1l);
    onHandQuantity.setFuelTypeXId(1l);
    onHandQuantity.setArrivalQuantity(new BigDecimal(1));
    onHandQuantity.setArrivalVolume(new BigDecimal(1));
    onHandQuantity.setDepartureQuantity(new BigDecimal(1));
    onHandQuantity.setDepartureVolume(new BigDecimal(1));
    ohqList.add(onHandQuantity);
    return ohqList;
  }

  private List<Voyage> getLV() {
    List<Voyage> activeVoyage = new ArrayList<>();
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    voyage.setVoyageNo("1");
    voyage.setActualEndDate(LocalDateTime.now());
    voyage.setActualStartDate(LocalDateTime.now());
    voyage.setVoyageStartDate(LocalDateTime.now());
    voyage.setVoyageEndDate(LocalDateTime.now());

    VoyageStatus status = new VoyageStatus();
    status.setName("1");
    status.setId(1l);
    voyage.setVoyageStatus(status);

    Set<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudies = new HashSet<>();
    loadableStudies.add(getLoadableStudyEntity());
    voyage.setLoadableStudies(loadableStudies);

    voyage.setVoyageNo("1");
    activeVoyage.add(voyage);
    return activeVoyage;
  }

  private List<CargoNomination> getLCN() {
    List<CargoNomination> list = new ArrayList<>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setCargoXId(1l);
    cargoNomination.setId(1l);
    cargoNomination.setLoadableStudyXId(1l);
    cargoNomination.setPriority(1l);
    cargoNomination.setColor("1");
    cargoNomination.setAbbreviation("1");
    cargoNomination.setMaxTolerance(new BigDecimal(1));
    cargoNomination.setMinTolerance(new BigDecimal(1));
    cargoNomination.setApi(new BigDecimal(1));
    cargoNomination.setTemperature(new BigDecimal(1));
    cargoNomination.setSegregationXId(1l);
    Set<CargoNominationPortDetails> portDetailsSet = new HashSet<>();
    CargoNominationPortDetails portDetails = new CargoNominationPortDetails();
    portDetails.setIsActive(true);
    portDetails.setPortId(1l);
    portDetails.setId(1l);
    portDetails.setQuantity(new BigDecimal(1));
    cargoNomination.setCargoNominationPortDetails(portDetailsSet);
    list.add(cargoNomination);
    return list;
  }

  private Set<CargoNominationPortDetails> getSCNPD() {
    Set<CargoNominationPortDetails> set = new HashSet<>();
    CargoNominationPortDetails cargoNominationPortDetails = new CargoNominationPortDetails();
    cargoNominationPortDetails.setPortId(1L);
    set.add(cargoNominationPortDetails);
    return set;
  }

  private List<LoadablePattern> getLP() {
    List<LoadablePattern> list = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePattern.setCaseNumber(1);
    loadablePattern.setLoadableStudyStatus(1L);
    loadablePattern.setLoadableStudy(getLoadableStudyEntity());
    list.add(loadablePattern);
    return list;
  }

  private com.cpdss.loadablestudy.entity.LoadableStudy getLoadableStudyEntity() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    Voyage voyage = new Voyage();
    voyage.setId(1l);
    voyage.setVoyageNo("1");
    loadableStudy.setVoyage(voyage);
    loadableStudy.setId(1l);
    loadableStudy.setVesselXId(1l);
    loadableStudy.setName("1");
    loadableStudy.setLoadableStudyStatus(getLSS());
    loadableStudy.setConfirmedLoadableStudyId(1l);
    loadableStudy.setPortRotations(getPR());
    return loadableStudy;
  }

  private Set<LoadableStudyPortRotation> getPR() {
    Set<LoadableStudyPortRotation> loadableStudyPortRotations = new HashSet<>();
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1L);
    loadableStudyPortRotation.setPortXId(1L);
    loadableStudyPortRotation.setPortOrder(1L);
    loadableStudyPortRotation.setBerthXId(1L);
    loadableStudyPortRotation.setOperation(getCO());
    loadableStudyPortRotation.setSeaWaterDensity(new BigDecimal(1));
    loadableStudyPortRotations.add(loadableStudyPortRotation);
    return loadableStudyPortRotations;
  }

  private CargoOperation getCO() {
    CargoOperation cargoOperation = new CargoOperation();
    cargoOperation.setId(2L);
    return cargoOperation;
  }

  private LoadableStudyStatus getLSS() {
    LoadableStudyStatus loadableStudyStatus = new LoadableStudyStatus();
    loadableStudyStatus.setId(1L);
    loadableStudyStatus.setName("CONFIRMED");
    return loadableStudyStatus;
  }
}
