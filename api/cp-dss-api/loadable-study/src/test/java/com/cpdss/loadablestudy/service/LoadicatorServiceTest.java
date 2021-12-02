/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.cpdss.common.generated.*;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadablestudy.communication.LoadableStudyStagingService;
import com.cpdss.loadablestudy.domain.*;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(
    properties = {"cpdss.communication.enable = false", "cpdss.judgement.enable = true"})
@SpringJUnitConfig(classes = {LoadicatorService.class})
public class LoadicatorServiceTest {

  @Autowired private LoadicatorService loadicatorService;

  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean JsonTypeRepository jsonTypeRepository;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean private OnHandQuantityRepository onHandQuantityRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;

  @MockBean
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @MockBean JsonDataRepository jsonDataRepository;
  @MockBean private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @MockBean private LoadablePatternService loadablePatternService;
  @MockBean private RestTemplate restTemplate;
  @MockBean private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @MockBean private LoadableStudyService loadableStudyService;
  @MockBean private JsonDataService jsonDataService;
  @MockBean private CommunicationService communicationService;
  @MockBean private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private LoadicatorServiceGrpc.LoadicatorServiceBlockingStub loadicatorServiceGrpc;
  @MockBean private AlgoService algoService;
  @MockBean private LoadableStudyStagingService loadableStudyStagingService;
  @MockBean AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean AlgoErrorsRepository algoErrorsRepository;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Test
  void testSaveLodicatorDataForSynoptical() {

    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    LoadableStudy.LoadablePlanDetailsReply arrivalCondition =
        LoadableStudy.LoadablePlanDetailsReply.newBuilder()
            .setStabilityParameter(
                LoadableStudy.StabilityParameter.newBuilder()
                    .setForwardDraft("1")
                    .setMeanDraft("1")
                    .setTrim("1")
                    .setBendinMoment("1")
                    .setShearForce("1")
                    .setAfterDraft("1")
                    .build())
            .build();
    LoadableStudy.LoadablePlanDetails lpd = LoadableStudy.LoadablePlanDetails.newBuilder().build();
    String portType = "1";
    Long portRotationId = 1L;
    Mockito.when(loadableStudyPortRotationRepository.getOne(Mockito.anyLong()))
        .thenReturn(getLSPortRotation());
    Mockito.when(
            synopticalTableRepository.findByLoadableStudyPortRotationAndOperationTypeAndIsActive(
                Mockito.anyLong(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getSynopticalTable());
    this.loadicatorService.saveLodicatorDataForSynoptical(
        loadablePattern, arrivalCondition, lpd, portType, portRotationId);
  }

  private LoadableStudyPortRotation getLSPortRotation() {
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setId(1l);
    loadableStudyPortRotation.setPortXId(1l);
    loadableStudyPortRotation.setSeaWaterDensity(new BigDecimal(1));
    return loadableStudyPortRotation;
  }

  private Optional<SynopticalTable> getSynopticalTable() {
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1L);
    return Optional.of(synopticalTable);
  }

  private com.cpdss.common.generated.CargoInfo.CargoReply getCargoReply() {
    com.cpdss.common.generated.CargoInfo.CargoReply reply =
        CargoInfo.CargoReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private VesselInfo.VesselReply getVesselReply() {
    List<VesselInfo.VesselDetail> vesselDetailList = new ArrayList<>();
    VesselInfo.VesselDetail vesselDetail =
        VesselInfo.VesselDetail.newBuilder()
            .setImoNumber("1")
            .setId(1l)
            .setTypeOfShip("1")
            .setCode("1")
            .setDeadweightConstant("1")
            .setProvisionalConstant("1")
            .build();
    vesselDetailList.add(vesselDetail);

    List<VesselInfo.VesselTankDetail> vesselTankDetails = new ArrayList<>();
    VesselInfo.VesselTankDetail tankDetail =
        VesselInfo.VesselTankDetail.newBuilder().setTankName("1").setShortName("1").build();
    vesselTankDetails.add(tankDetail);

    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .addAllVessels(vesselDetailList)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return vesselReply;
  }

  private PortInfo.PortReply getPortReply() {
    List<PortInfo.PortDetail> portDetailList = new ArrayList<>();
    PortInfo.PortDetail portDetail = PortInfo.PortDetail.newBuilder().setCode("1").build();
    portDetailList.add(portDetail);
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .addAllPorts(portDetailList)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return portReply;
  }

  private Loadicator.LoadicatorReply getLoadicatorReply() {
    Loadicator.LoadicatorReply loadicatorReply =
        Loadicator.LoadicatorReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return loadicatorReply;
  }

  @Test
  void testSaveLoadicatorInfo() {
    LoadicatorService spyService = spy(LoadicatorService.class);
    List<LoadablePattern> loadablePatternsList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePatternsList.add(loadablePattern);
    Mockito.when(
            this.loadablePatternRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLP());
    Mockito.when(
            this.loadablePatternCargoDetailsRepository.findByLoadablePatternIdInAndIsActive(
                Mockito.anyList(), Mockito.anyBoolean()))
        .thenReturn(getLLPCD());
    Mockito.when(
            this.loadablePlanCommingleDetailsPortwiseRepository
                .findByLoadablePatternIdInAndIsActive(Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLPCPD());
    Mockito.when(
            this.loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdInAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLLPBD());
    Mockito.when(
            this.synopticalTableRepository.findByLoadableStudyXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLST());
    Mockito.when(
            this.onHandQuantityRepository.findByLoadableStudyAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLOHQ());
    when(cargoInfoGrpcService.getCargoInfo(any(CargoInfo.CargoRequest.class)))
        .thenReturn(getCargoReply());
    when(vesselInfoGrpcService.getVesselDetailByVesselId(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    when(portInfoGrpcService.getPortInfo(any(PortInfo.PortRequest.class)))
        .thenReturn(getPortReply());
    when(loadicatorServiceGrpc.saveLoadicatorInfo(any(Loadicator.LoadicatorRequest.class)))
        .thenReturn(getLoadicatorReply());

    ReflectionTestUtils.setField(spyService, "cargoInfoGrpcService", cargoInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "onHandQuantityRepository", onHandQuantityRepository);
    ReflectionTestUtils.setField(
        spyService, "synopticalTableRepository", synopticalTableRepository);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanStowageBallastDetailsRepository",
        loadablePlanStowageBallastDetailsRepository);
    ReflectionTestUtils.setField(
        spyService,
        "loadablePlanCommingleDetailsPortwiseRepository",
        loadablePlanCommingleDetailsPortwiseRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternCargoDetailsRepository", loadablePatternCargoDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadablePatternRepository", loadablePatternRepository);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "loadicatorService", loadicatorServiceGrpc);

    spyService.saveLoadicatorInfo(getLS().get(), "1", 1l, loadablePatternsList);
    verify(loadicatorServiceGrpc).saveLoadicatorInfo(any(Loadicator.LoadicatorRequest.class));
  }

  @Test
  void testGetCargoInfo() {
    LoadicatorService spyService = spy(LoadicatorService.class);
    CargoInfo.CargoRequest cargoRequest = CargoInfo.CargoRequest.newBuilder().build();
    when(cargoInfoGrpcService.getCargoInfo(any(CargoInfo.CargoRequest.class)))
        .thenReturn(getCargoReply());
    ReflectionTestUtils.setField(spyService, "cargoInfoGrpcService", cargoInfoGrpcService);

    var result = spyService.getCargoInfo(cargoRequest);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetvesseldetailbyvesselid() {
    LoadicatorService spyService = spy(LoadicatorService.class);
    VesselInfo.VesselRequest vesselRequest = VesselInfo.VesselRequest.newBuilder().build();
    when(vesselInfoGrpcService.getVesselDetailByVesselId(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = spyService.getVesselDetailByVesselId(vesselRequest);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetPortInfo() {
    LoadicatorService spyService = spy(LoadicatorService.class);
    PortInfo.PortRequest portRequest = PortInfo.PortRequest.newBuilder().build();
    when(portInfoGrpcService.getPortInfo(any(PortInfo.PortRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);

    var result = spyService.GetPortInfo(portRequest);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private List<LoadableStudy.LDStrength> getStrengthList() {
    List<LoadableStudy.LDStrength> strengthList = new ArrayList<>();
    LoadableStudy.LDStrength strength =
        LoadableStudy.LDStrength.newBuilder()
            .setBendingMomentPersentFrameNumber("1")
            .setBendingMomentPersentJudgement("1")
            .setBendingMomentPersentValue("1")
            .setErrorDetails("1")
            .setId(1l)
            .setInnerLongiBhdFrameNumber("1")
            .setInnerLongiBhdJudgement("1")
            .setInnerLongiBhdValue("1")
            .setMessageText("1")
            .setSfFrameNumber("1")
            .setSfHopperFrameNumber("1")
            .setSfHopperJudgement("1")
            .setSfHopperValue("1")
            .setSfSideShellFrameNumber("1")
            .setSfSideShellJudgement("1")
            .setSfSideShellValue("1")
            .setShearingForceJudgement("1")
            .setShearingForcePersentValue("1")
            .setPortId(1l)
            .setSynopticalId(1l)
            .build();
    strengthList.add(strength);
    return strengthList;
  }

  private List<LoadableStudy.LDtrim> getLDtrimList() {
    List<LoadableStudy.LDtrim> lDtrimList = new ArrayList<>();
    LoadableStudy.LDtrim lDtrim =
        LoadableStudy.LDtrim.newBuilder()
            .setAftDraftValue("1")
            .setAirDraftJudgement("1")
            .setAirDraftValue("1")
            .setDisplacementJudgement("1")
            .setErrorDetails("1")
            .setErrorStatus(true)
            .setForeDraftValue("1")
            .setHeelValue("1")
            .setId(1l)
            .setMaximumAllowableJudement("1")
            .setMaximumAllowableJudement("1")
            .setMeanDraftValue("1")
            .setMaximumDraftValue("1")
            .setMeanDraftJudgement("1")
            .setMeanDraftValue("1")
            .setMessageText("1")
            .setMinimumForeDraftInRoughWeatherJudgement("1")
            .setMinimumForeDraftInRoughWeatherValue("1")
            .setTrimValue("1")
            .setPortId(1l)
            .setSynopticalId(1l)
            .setDeflection("1")
            .build();
    lDtrimList.add(lDtrim);
    return lDtrimList;
  }

  private List<LoadableStudy.LDIntactStability> getStabilityList() {
    List<LoadableStudy.LDIntactStability> stabilityList = new ArrayList<>();
    LoadableStudy.LDIntactStability stability =
        LoadableStudy.LDIntactStability.newBuilder()
            .setAngleatmaxrleverJudgement("1")
            .setAngleatmaxrleverValue("1")
            .setAreaofStability030Judgement("1")
            .setAreaofStability030Value("1")
            .setAreaofStability040Judgement("1")
            .setAreaofStability040Value("1")
            .setAreaofStability3040Judgement("1")
            .setAreaofStability3040Value("1")
            .setBigIntialGomJudgement("1")
            .setBigintialGomValue("1")
            .setErrorDetails("1")
            .setErrorStatus(true)
            .setGmAllowableCurveCheckJudgement("1")
            .setGmAllowableCurveCheckValue("1")
            .setHeelBySteadyWindJudgement("1")
            .setHeelBySteadyWindValue("1")
            .setId(1l)
            .setMaximumRightingLeverJudgement("1")
            .setMaximumRightingLeverValue("1")
            .setMessageText("1")
            .setStabilityAreaBaJudgement("1")
            .setStabilityAreaBaValue("1")
            .setPortId(1l)
            .setSynopticalId(1l)
            .build();
    stabilityList.add(stability);
    return stabilityList;
  }

  private LoadicatorAlgoResponse getResponse() {
    LoadicatorAlgoResponse response = new LoadicatorAlgoResponse();
    response.setFeedbackLoop(true);
    response.setFeedbackLoopCount(1);
    LoadicatorPatternDetailsResults results = new LoadicatorPatternDetailsResults();
    List<LoadicatorResultDetails> detailsList = new ArrayList<>();
    LoadicatorResultDetails details = new LoadicatorResultDetails();
    details.setSynopticalId(1l);
    detailsList.add(details);
    results.setLoadicatorResultDetails(detailsList);
    response.setLoadicatorResults(results);
    List<LoadicatorPatternDetailsResults> list = new ArrayList<>();
    LoadicatorPatternDetailsResults patternDetailsResults = new LoadicatorPatternDetailsResults();
    patternDetailsResults.setLoadablePatternId(1l);
    list.add(patternDetailsResults);
    response.setLoadicatorResultsPatternWise(list);
    return response;
  }

  @Test
  void testGetloadicatordata() throws Exception {
    LoadicatorService spyService = spy(LoadicatorService.class);

    LoadableStudy.LoadicatorDataRequest request =
        LoadableStudy.LoadicatorDataRequest.newBuilder()
            .setIsPattern(true)
            .addLoadicatorPatternDetails(
                LoadableStudy.LoadicatorPatternDetails.newBuilder()
                    .setLoadablePatternId(1l)
                    .addAllLDStrength(getStrengthList())
                    .addAllLDtrim(getLDtrimList())
                    .addAllLDIntactStability(getStabilityList())
                    .build())
            .setProcessId("1")
            .setLoadableStudyId(1l)
            .build();
    LoadableStudy.LoadicatorDataReply.Builder replyBuilder =
        LoadableStudy.LoadicatorDataReply.newBuilder();

    LoadableStudyCommunicationStatus status = new LoadableStudyCommunicationStatus();
    status.setMessageUUID("1");
    List<LoadablePattern> loadablePatternsList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePatternsList.add(loadablePattern);
    List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataList = new ArrayList<>();
    SynopticalTableLoadicatorData data = new SynopticalTableLoadicatorData();
    synopticalTableLoadicatorDataList.add(data);

    JsonData jsonData = new JsonData();
    jsonData.setJsonData("1");
    JsonType jsonType = new JsonType();
    EnvoyWriter.WriterReply reply =
        EnvoyWriter.WriterReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    when(algoService.callAlgo(
            anyLong(),
            anyString(),
            any(LoadicatorAlgoRequest.class),
            any(),
            anyBoolean(),
            anyString()))
        .thenReturn(getResponse());
    doNothing().when(jsonDataService).saveJsonToDatabase(anyLong(), anyLong(), anyString());
    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(getLS());
    doNothing()
        .when(loadableStudyService)
        .buildLoadableStudy(
            anyLong(),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(com.cpdss.loadablestudy.domain.LoadableStudy.class),
            any(ModelMapper.class));
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(this.loadableStudyCommunicationStatusRepository
            .findFirstByReferenceIdAndMessageTypeOrderByCreatedDateTimeDesc(anyLong(), anyString()))
        .thenReturn(Optional.of(status));
    doNothing()
        .when(this.loadablePatternRepository)
        .updateLoadablePatternStatus(anyLong(), anyLong());
    doNothing()
        .when(this.loadablePatternRepository)
        .updateLoadablePatternFeedbackLoopAndFeedbackLoopCount(anyBoolean(), anyInt(), anyLong());
    doNothing().when(this.loadableStudyRepository).updateLoadableStudyStatus(anyLong(), anyLong());
    doNothing()
        .when(this.loadableStudyRepository)
        .updateLoadableStudyFeedbackLoopAndFeedbackLoopCount(anyBoolean(), anyInt(), anyLong());
    when(loadablePatternRepository.findByLoadableStudyAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(loadablePatternsList);
    doNothing().when(this.loadablePatternRepository).deleteLoadablePattern(anyLong());
    doNothing().when(loadablePatternService).deleteExistingPlanDetails(any(LoadablePattern.class));
    when(loadablePatternRepository.getOne(anyLong())).thenReturn(getLP().get());
    when(synopticalTableLoadicatorDataRepository.saveAll(anyList()))
        .thenReturn(synopticalTableLoadicatorDataList);
    doNothing()
        .when(loadableStudyAlgoStatusRepository)
        .updateLoadableStudyAlgoStatus(anyLong(), anyString(), anyBoolean());
    when(this.jsonDataService.getJsonData(anyLong(), anyLong())).thenReturn(jsonData);
    when(communicationService.passResultPayloadToEnvoyWriter(
            any(com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder.class),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class)))
        .thenReturn(reply);
    doNothing()
        .when(loadablePatternAlgoStatusRepository)
        .updateLoadablePatternAlgoStatus(anyLong(), anyString(), anyBoolean());
    when(this.jsonTypeRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(jsonType));
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(jsonDataRepository.findTopByReferenceXIdAndJsonTypeXIdOrderByIdDesc(
            anyLong(), any(JsonType.class)))
        .thenReturn(jsonData);
    when(this.loadableStudyCommunicationStatusRepository
            .findFirstByReferenceIdAndMessageTypeOrderByCreatedDateTimeDesc(anyLong(), anyString()))
        .thenReturn(Optional.of(status));
    doNothing()
        .when(loadablePatternService)
        .fetchSavedPatternFromDB(any(PatternDetails.class), any(LoadablePattern.class));
    when(communicationService.passRequestPayloadToEnvoyWriter(anyString(), anyLong(), anyString()))
        .thenReturn(reply);
    when(this.loadableStudyCommunicationStatusRepository.save(
            any(LoadableStudyCommunicationStatus.class)))
        .thenReturn(status);

    ReflectionTestUtils.setField(loadicatorService, "loadicatorUrl", "url");
    ReflectionTestUtils.setField(loadicatorService, "rootFolder", "D:\\");
    ReflectionTestUtils.setField(loadicatorService, "enableCommunication", true);
    ReflectionTestUtils.setField(loadicatorService, "env", "ship");
    ReflectionTestUtils.setField(loadicatorService, "algoService", algoService);

    var result = loadicatorService.getLoadicatorData(request, replyBuilder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetloadicatordataFeedbackLoopFalse() throws Exception {
    LoadicatorService spyService = spy(LoadicatorService.class);

    LoadableStudy.LoadicatorDataRequest request =
        LoadableStudy.LoadicatorDataRequest.newBuilder()
            .setIsPattern(true)
            .addLoadicatorPatternDetails(
                LoadableStudy.LoadicatorPatternDetails.newBuilder()
                    .setLoadablePatternId(1l)
                    .addAllLDStrength(getStrengthList())
                    .addAllLDtrim(getLDtrimList())
                    .addAllLDIntactStability(getStabilityList())
                    .build())
            .setProcessId("1")
            .setLoadableStudyId(1l)
            .build();
    LoadableStudy.LoadicatorDataReply.Builder replyBuilder =
        LoadableStudy.LoadicatorDataReply.newBuilder();

    LoadableStudyCommunicationStatus status = new LoadableStudyCommunicationStatus();
    status.setMessageUUID("1");
    List<LoadablePattern> loadablePatternsList = new ArrayList<>();
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePatternsList.add(loadablePattern);
    List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataList = new ArrayList<>();
    SynopticalTableLoadicatorData data = new SynopticalTableLoadicatorData();
    synopticalTableLoadicatorDataList.add(data);

    JsonData jsonData = new JsonData();
    PatternValidateResultRequest validateResultRequest = new PatternValidateResultRequest();
    validateResultRequest.setValidated(true);
    validateResultRequest.setHasLoadicator(true);
    ObjectMapper mapper = new ObjectMapper();
    String jsonResult = mapper.writeValueAsString(validateResultRequest);
    jsonData.setJsonData(jsonResult);

    JsonType jsonType = new JsonType();
    EnvoyWriter.WriterReply reply =
        EnvoyWriter.WriterReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    LoadicatorAlgoResponse response = getResponse();
    response.setFeedbackLoop(false);
    when(algoService.callAlgo(
            anyLong(),
            anyString(),
            any(LoadicatorAlgoRequest.class),
            any(),
            anyBoolean(),
            anyString()))
        .thenReturn(response);
    doNothing().when(jsonDataService).saveJsonToDatabase(anyLong(), anyLong(), anyString());
    when(loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean())).thenReturn(getLS());
    doNothing()
        .when(loadableStudyService)
        .buildLoadableStudy(
            anyLong(),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class),
            any(com.cpdss.loadablestudy.domain.LoadableStudy.class),
            any(ModelMapper.class));
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(this.loadableStudyCommunicationStatusRepository
            .findFirstByReferenceIdAndMessageTypeOrderByCreatedDateTimeDesc(anyLong(), anyString()))
        .thenReturn(Optional.of(status));
    doNothing()
        .when(this.loadablePatternRepository)
        .updateLoadablePatternStatus(anyLong(), anyLong());
    doNothing()
        .when(this.loadablePatternRepository)
        .updateLoadablePatternFeedbackLoopAndFeedbackLoopCount(anyBoolean(), anyInt(), anyLong());
    doNothing().when(this.loadableStudyRepository).updateLoadableStudyStatus(anyLong(), anyLong());
    doNothing()
        .when(this.loadableStudyRepository)
        .updateLoadableStudyFeedbackLoopAndFeedbackLoopCount(anyBoolean(), anyInt(), anyLong());
    when(loadablePatternRepository.findByLoadableStudyAndIsActive(
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class), anyBoolean()))
        .thenReturn(loadablePatternsList);
    doNothing().when(this.loadablePatternRepository).deleteLoadablePattern(anyLong());
    doNothing().when(loadablePatternService).deleteExistingPlanDetails(any(LoadablePattern.class));
    when(loadablePatternRepository.getOne(anyLong())).thenReturn(getLP().get());
    when(synopticalTableLoadicatorDataRepository.saveAll(anyList()))
        .thenReturn(synopticalTableLoadicatorDataList);
    doNothing()
        .when(loadableStudyAlgoStatusRepository)
        .updateLoadableStudyAlgoStatus(anyLong(), anyString(), anyBoolean());
    when(this.jsonDataService.getJsonData(anyLong(), anyLong())).thenReturn(jsonData);
    when(communicationService.passResultPayloadToEnvoyWriter(
            any(com.cpdss.common.generated.LoadableStudy.AlgoResponseCommunication.Builder.class),
            any(com.cpdss.loadablestudy.entity.LoadableStudy.class)))
        .thenReturn(reply);
    doNothing()
        .when(loadablePatternAlgoStatusRepository)
        .updateLoadablePatternAlgoStatus(anyLong(), anyString(), anyBoolean());
    when(this.jsonTypeRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(jsonType));
    when(this.loadablePatternRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(getLP());
    when(jsonDataRepository.findTopByReferenceXIdAndJsonTypeXIdOrderByIdDesc(
            anyLong(), any(JsonType.class)))
        .thenReturn(jsonData);
    when(this.loadableStudyCommunicationStatusRepository
            .findFirstByReferenceIdAndMessageTypeOrderByCreatedDateTimeDesc(anyLong(), anyString()))
        .thenReturn(Optional.of(status));
    doNothing()
        .when(loadablePatternService)
        .fetchSavedPatternFromDB(any(PatternDetails.class), any(LoadablePattern.class));
    when(communicationService.passRequestPayloadToEnvoyWriter(anyString(), anyLong(), anyString()))
        .thenReturn(reply);
    when(this.loadableStudyCommunicationStatusRepository.save(
            any(LoadableStudyCommunicationStatus.class)))
        .thenReturn(status);
    when(loadableStudyStagingService.getCommunicationData(
            anyList(), anyString(), anyString(), anyLong(), any()))
        .thenReturn(new JsonArray());
    ReflectionTestUtils.setField(loadicatorService, "loadicatorUrl", "url");
    ReflectionTestUtils.setField(loadicatorService, "rootFolder", "D:\\");
    ReflectionTestUtils.setField(loadicatorService, "enableCommunication", true);
    ReflectionTestUtils.setField(loadicatorService, "env", "env");

    var result = loadicatorService.getLoadicatorData(request, replyBuilder);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testSaveLoadicatorInfoGrpc() {
    LoadicatorService spyService = spy(LoadicatorService.class);
    Loadicator.LoadicatorRequest request = Loadicator.LoadicatorRequest.newBuilder().build();
    when(loadicatorServiceGrpc.saveLoadicatorInfo(any(Loadicator.LoadicatorRequest.class)))
        .thenReturn(getLoadicatorReply());
    ReflectionTestUtils.setField(spyService, "loadicatorService", loadicatorServiceGrpc);

    var result = spyService.saveLoadicatorInfo(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private Optional<LoadablePattern> getLP() {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setId(1L);
    loadablePattern.setLoadableStudy(getLS().get());
    return Optional.of(loadablePattern);
  }

  private List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> getLLPCD() {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> list = new ArrayList<>();
    com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails cargoDetails =
        new LoadablePatternCargoDetails();
    cargoDetails.setPortRotationId(1l);
    cargoDetails.setLoadablePatternId(1l);
    cargoDetails.setOperationType("1");
    list.add(cargoDetails);
    return list;
  }

  private List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails> getLLPCPD() {
    List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
        loadablePlanComminglePortwiseDetails = new ArrayList<>();
    com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails
        loadablePlanComminglePortwiseDetails1 = new LoadablePlanComminglePortwiseDetails();
    loadablePlanComminglePortwiseDetails.add(loadablePlanComminglePortwiseDetails1);
    return loadablePlanComminglePortwiseDetails;
  }

  private List<LoadablePlanStowageBallastDetails> getLLPBD() {
    List<LoadablePlanStowageBallastDetails> loadablePlanStowageBallastDetails = new ArrayList<>();
    LoadablePlanStowageBallastDetails loadablePlanStowageBallastDetails1 =
        new LoadablePlanStowageBallastDetails();
    loadablePlanStowageBallastDetails.add(loadablePlanStowageBallastDetails1);
    return loadablePlanStowageBallastDetails;
  }

  private List<SynopticalTable> getLST() {
    List<SynopticalTable> synopticalTables = new ArrayList<>();
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setLoadableStudyPortRotation(getLSPortRotation());
    synopticalTable.setId(1l);
    synopticalTable.setOperationType("1");
    synopticalTables.add(synopticalTable);
    return synopticalTables;
  }

  private List<OnHandQuantity> getLOHQ() {
    List<OnHandQuantity> onHandQuantities = new ArrayList<>();
    OnHandQuantity quantity = new OnHandQuantity();
    quantity.setPortRotation(getLSPortRotation());
    quantity.setTankXId(1l);
    quantity.setArrivalQuantity(new BigDecimal(1));
    quantity.setDepartureQuantity(new BigDecimal(1));
    onHandQuantities.add(quantity);
    return onHandQuantities;
  }

  private Voyage getVoyage() {
    Voyage voyage = new Voyage();
    voyage.setId(1L);
    return voyage;
  }

  @Test
  void testSaveLoadicatorResults() {
    List<LoadableStudy.LodicatorResultDetails> lodicatorResultDetails = new ArrayList<>();
    LoadableStudy.LodicatorResultDetails lodicatorResultDetails1 =
        LoadableStudy.LodicatorResultDetails.newBuilder()
            .setBlindSector("1")
            .setCalculatedDraftAftPlanned("1")
            .setCalculatedDraftFwdPlanned("1")
            .setCalculatedTrimPlanned("1")
            .setCalculatedDraftMidPlanned("1")
            .setDeflection("1")
            .setList("1")
            .setPortId(1L)
            .setOperationId(1L)
            .build();
    lodicatorResultDetails.add(lodicatorResultDetails1);
    List<LoadableStudy.LoadicatorPatternDetailsResults> loadicatorPatternDetailsResults =
        new ArrayList<>();
    LoadableStudy.LoadicatorPatternDetailsResults loadicatorPatternDetailsResults1 =
        LoadableStudy.LoadicatorPatternDetailsResults.newBuilder()
            .setLoadablePatternId(1L)
            .addLoadicatorResultDetails(lodicatorResultDetails1)
            .build();
    loadicatorPatternDetailsResults.add(loadicatorPatternDetailsResults1);
    LoadableStudy.LoadicatorResultsRequest request =
        LoadableStudy.LoadicatorResultsRequest.newBuilder()
            .setLoadableStudyId(1L)
            .addLoadicatorResultsPatternWise(loadicatorPatternDetailsResults1)
            .build();
    LoadableStudy.AlgoReply.Builder replyBuilder = LoadableStudy.AlgoReply.newBuilder();
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLS());
    var algoReply = this.loadicatorService.saveLoadicatorResults(request, replyBuilder);
    assertEquals(SUCCESS, replyBuilder.getResponseStatus().getStatus());
  }

  private Optional<com.cpdss.loadablestudy.entity.LoadableStudy> getLS() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setVesselXId(1L);
    loadableStudy.setVoyage(getVoyage());
    loadableStudy.setLoadLineXId(1l);
    loadableStudy.setDraftMark(new BigDecimal(1));
    return Optional.of(loadableStudy);
  }

  @Test
  void testSaveLoadicatorResults1() {
    List<LoadableStudy.LodicatorResultDetails> lodicatorResultDetails = new ArrayList<>();
    LoadableStudy.LodicatorResultDetails lodicatorResultDetails1 =
        LoadableStudy.LodicatorResultDetails.newBuilder()
            .setBlindSector("1")
            .setCalculatedDraftAftPlanned("1")
            .setCalculatedDraftFwdPlanned("1")
            .setCalculatedTrimPlanned("1")
            .setCalculatedDraftMidPlanned("1")
            .setDeflection("1")
            .setList("1")
            .setPortId(1L)
            .setOperationId(1L)
            .build();
    lodicatorResultDetails.add(lodicatorResultDetails1);
    List<LoadableStudy.LoadicatorPatternDetailsResults> loadicatorPatternDetailsResults =
        new ArrayList<>();
    LoadableStudy.LoadicatorPatternDetailsResults loadicatorPatternDetailsResults1 =
        LoadableStudy.LoadicatorPatternDetailsResults.newBuilder()
            .setLoadablePatternId(1L)
            .addLoadicatorResultDetails(lodicatorResultDetails1)
            .build();
    loadicatorPatternDetailsResults.add(loadicatorPatternDetailsResults1);
    LoadableStudy.LoadicatorResultsRequest request =
        LoadableStudy.LoadicatorResultsRequest.newBuilder()
            .setLoadableStudyId(1L)
            .addLoadicatorResultsPatternWise(loadicatorPatternDetailsResults1)
            .build();
    this.loadicatorService.saveLoadicatorResults(request);
    Mockito.verify(synopticalTableLoadicatorDataRepository)
        .save(Mockito.any(SynopticalTableLoadicatorData.class));
  }
}
