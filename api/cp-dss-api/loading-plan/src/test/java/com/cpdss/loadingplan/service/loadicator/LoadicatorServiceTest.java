/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.loadicator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.domain.algo.*;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.LoadingPlanCommunicationService;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(
    properties = {"cpdss.communication.enable = false", "cpdss.judgement.enable = true"})
@SpringJUnitConfig(
    classes = {
      LoadicatorService.class,
    })
public class LoadicatorServiceTest {
  @Autowired LoadicatorService loadicatorService;

  @MockBean LoadingInformationRepository loadingInformationRepository;
  @MockBean LoadingSequenceRepository loadingSequenceRepository;
  @MockBean LoadingPlanPortWiseDetailsRepository loadingPlanPortWiseDetailsRepository;
  @MockBean LoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;
  @MockBean LoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
  @MockBean LoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;
  @MockBean LoadingSequenceStabiltyParametersRepository loadingSequenceStabiltyParametersRepository;

  @MockBean
  PortLoadingPlanStabilityParametersRepository portLoadingPlanStabilityParametersRepository;

  @MockBean LoadingPlanCommingleDetailsRepository loadingPlanCommingleDetailsRepository;
  @MockBean LoadingPlanAlgoService loadingPlanAlgoService;
  @MockBean LoadingPlanService loadingPlanService;
  @MockBean UllageUpdateLoadicatorService ullageUpdateLoadicatorService;
  @MockBean RestTemplate restTemplate;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @MockBean private LoadicatorServiceGrpc.LoadicatorServiceBlockingStub loadicatorGrpcService;
  @MockBean private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean LoadingPlanStagingService loadingPlanStagingService;
  @MockBean LoadingPlanCommunicationStatusRepository loadingPlanCommunicationStatusRepository;
  @MockBean private LoadingPlanCommunicationService communicationService;

  @MockBean AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean AlgoErrorsRepository algoErrorsRepository;

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

  @Value("${cpdss.judgement.enable}")
  private boolean judgementEnabled;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Test
  void testSaveLoadicatorInfo() throws GenericServiceException {
    LoadicatorService spyService = spy(LoadicatorService.class);
    List<LoadingSequence> loadingSequences = new ArrayList<>();
    Map<Long, LoadableStudy.CargoNominationDetail> details =
        new HashMap<Long, LoadableStudy.CargoNominationDetail>();
    LoadableStudy.CargoNominationDetail cargoNominationDetail =
        LoadableStudy.CargoNominationDetail.newBuilder()
            .setCargoId(1l)
            .setAbbreviation("1")
            .build();
    details.put(1l, cargoNominationDetail);

    when(loadingPlanPortWiseDetailsRepository.findByLoadingInformationIdAndToLoadicatorAndIsActive(
            anyLong(), anyBoolean(), anyBoolean()))
        .thenReturn(getPortWiseDetailsList());
    when(loadingSequenceRepository.findByLoadingInformationAndIsActiveOrderBySequenceNumber(
            any(LoadingInformation.class), anyBoolean()))
        .thenReturn(loadingSequences);
    when(loadingPlanStowageDetailsRepository.findByPortWiseDetailIdsAndIsActive(
            anyList(), anyBoolean()))
        .thenReturn(getStowageDetailsList());
    when(loadingPlanBallastDetailsRepository.findByLoadingPlanPortWiseDetailIdsAndIsActive(
            anyList(), anyBoolean()))
        .thenReturn(getBallastDetailsList());
    when(loadingPlanRobDetailsRepository.findByPortWiseDetailIdsAndIsActive(
            anyList(), anyBoolean()))
        .thenReturn(getRobDetailsList());
    when(loadingPlanCommingleDetailsRepository.findByPortWiseDetailIdsAndIsActive(
            anyList(), anyBoolean()))
        .thenReturn(getCommingleDetailsList());
    doReturn(details).when(spyService).getCargoNominationDetails(anySet());
    doReturn(getCargoReply())
        .when(spyService)
        .getCargoInfoForLoadicator(any(LoadingInformation.class));
    doReturn(getVesselReply())
        .when(spyService)
        .getVesselDetailsForLoadicator(any(LoadingInformation.class));
    doReturn(getPortReply())
        .when(spyService)
        .getPortInfoForLoadicator(any(LoadingInformation.class));
    doNothing()
        .when(spyService)
        .buildStowagePlan(
            any(LoadingInformation.class),
            anyInt(),
            anyString(),
            any(CargoInfo.CargoReply.class),
            any(VesselInfo.VesselReply.class),
            any(PortInfo.PortReply.class),
            any(Loadicator.StowagePlan.Builder.class),
            any(BigDecimal.class));
    doReturn(getLoadicatorReply())
        .when(spyService)
        .saveLoadicatorInfo(any(Loadicator.LoadicatorRequest.class));

    ReflectionTestUtils.setField(
        spyService, "loadingPlanPortWiseDetailsRepository", loadingPlanPortWiseDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingSequenceRepository", loadingSequenceRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanStowageDetailsRepository", loadingPlanStowageDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanBallastDetailsRepository", loadingPlanBallastDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanRobDetailsRepository", loadingPlanRobDetailsRepository);
    ReflectionTestUtils.setField(
        spyService, "loadingPlanCommingleDetailsRepository", loadingPlanCommingleDetailsRepository);

    spyService.saveLoadicatorInfo(getLoadingInfoEntity(), "1");
  }

  @Test
  void testSaveLoadicatorInfoGrpc() {
    LoadicatorService spyService = spy(LoadicatorService.class);
    Loadicator.LoadicatorRequest request = Loadicator.LoadicatorRequest.newBuilder().build();
    doReturn(getLoadicatorReply())
        .when(loadicatorGrpcService)
        .saveLoadicatorInfo(any(Loadicator.LoadicatorRequest.class));
    ReflectionTestUtils.setField(spyService, "loadicatorGrpcService", loadicatorGrpcService);

    var result = spyService.saveLoadicatorInfo(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoNominationDetails() throws GenericServiceException {
    LoadicatorService spyService = spy(LoadicatorService.class);
    Set<Long> cargoNominationIds = new HashSet<>();
    cargoNominationIds.add(1l);
    LoadableStudy.CargoNominationDetailReply reply =
        LoadableStudy.CargoNominationDetailReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setCargoNominationdetail(
                LoadableStudy.CargoNominationDetail.newBuilder().setCargoId(1l).build())
            .build();

    doReturn(reply)
        .when(loadableStudyGrpcService)
        .getCargoNominationByCargoNominationId(any(LoadableStudy.CargoNominationRequest.class));
    ReflectionTestUtils.setField(spyService, "loadableStudyGrpcService", loadableStudyGrpcService);

    var result = spyService.getCargoNominationDetails(cargoNominationIds);
    assertEquals(1l, result.get(1l).getCargoId());
  }

  @Test
  void buildStowagePlan() {
    Loadicator.StowagePlan.Builder stowagePlanBuilder = Loadicator.StowagePlan.newBuilder();
    loadicatorService.buildStowagePlan(
        getLoadingInfoEntity(),
        1,
        "1",
        getCargoReply(),
        getVesselReply(),
        getPortReply(),
        stowagePlanBuilder,
        new BigDecimal(1));
    assertEquals(1l, stowagePlanBuilder.getSynopticalId());
  }

  @Test
  void testgetPortInfoForLoadicator() throws GenericServiceException {
    LoadicatorService spyService = spy(LoadicatorService.class);
    doReturn(getPortReply()).when(spyService).GetPortInfo(any(PortInfo.PortRequest.class));

    var result = spyService.getPortInfoForLoadicator(getLoadingInfoEntity());
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetPortInfo() {
    LoadicatorService spyService = spy(LoadicatorService.class);
    PortInfo.PortRequest request = PortInfo.PortRequest.newBuilder().build();
    when(portInfoGrpcService.getPortInfo(any(PortInfo.PortRequest.class)))
        .thenReturn(getPortReply());
    ReflectionTestUtils.setField(spyService, "portInfoGrpcService", portInfoGrpcService);

    var result = spyService.GetPortInfo(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailsForLoadicator() throws GenericServiceException {
    LoadicatorService spyService = spy(LoadicatorService.class);
    doReturn(getVesselReply())
        .when(spyService)
        .getVesselDetailByVesselId(any(VesselInfo.VesselRequest.class));

    var result = spyService.getVesselDetailsForLoadicator(getLoadingInfoEntity());
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetVesselDetailByVesselId() {
    LoadicatorService spyService = spy(LoadicatorService.class);
    VesselInfo.VesselRequest request = VesselInfo.VesselRequest.newBuilder().build();
    when(vesselInfoGrpcService.getVesselDetailByVesselId(any(VesselInfo.VesselRequest.class)))
        .thenReturn(getVesselReply());
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);

    var result = spyService.getVesselDetailByVesselId(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfoForLoadicator() throws GenericServiceException {
    LoadicatorService spyService = spy(LoadicatorService.class);
    doReturn(getCargoReply()).when(spyService).getCargoInfo(any(CargoInfo.CargoRequest.class));

    var result = spyService.getCargoInfoForLoadicator(getLoadingInfoEntity());
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  @Test
  void testGetCargoInfo() {
    LoadicatorService spyService = spy(LoadicatorService.class);
    CargoInfo.CargoRequest request = CargoInfo.CargoRequest.newBuilder().build();
    when(cargoInfoGrpcService.getCargoInfo(any(CargoInfo.CargoRequest.class)))
        .thenReturn(getCargoReply());
    ReflectionTestUtils.setField(spyService, "cargoInfoGrpcService", cargoInfoGrpcService);

    var result = spyService.getCargoInfo(request);
    assertEquals(SUCCESS, result.getResponseStatus().getStatus());
  }

  private LoadicatorAlgoResponse getAlgoResponse() {
    List<LoadicatorResult> resultList = new ArrayList<>();
    LoadicatorResult loadicatorResult = new LoadicatorResult();
    loadicatorResult.setJudgement(Arrays.asList("1"));
    loadicatorResult.setErrorDetails(Arrays.asList("1"));
    resultList.add(loadicatorResult);
    LoadicatorAlgoResponse algoResponse = new LoadicatorAlgoResponse();
    algoResponse.setLoadicatorResults(resultList);
    return algoResponse;
  }

  @Test
  void testGetLoadicatorDataElse()
      throws GenericServiceException, InvocationTargetException, IllegalAccessException {
    LoadingPlanModels.LoadingInfoLoadicatorDataRequest request =
        LoadingPlanModels.LoadingInfoLoadicatorDataRequest.newBuilder()
            .setIsUllageUpdate(true)
            .setLoadingInformationId(1l)
            .build();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply.Builder
        reply =
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply
                .newBuilder();
    when(loadingInformationRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    doNothing()
        .when(ullageUpdateLoadicatorService)
        .getLoadicatorData(any(LoadingPlanModels.LoadingInfoLoadicatorDataRequest.class));

    loadicatorService.getLoadicatorData(request, reply);
  }

  @Test
  void testSaveJson() {
    LoadicatorService spyService = spy(LoadicatorService.class);
    LoadableStudy.JsonRequest.Builder jsonBuilder = LoadableStudy.JsonRequest.newBuilder();
    when(loadableStudyGrpcService.saveJson(any(LoadableStudy.JsonRequest.class)))
        .thenReturn(LoadableStudy.StatusReply.newBuilder().build());
    ReflectionTestUtils.setField(spyService, "loadableStudyGrpcService", loadableStudyGrpcService);
    spyService.saveJson(jsonBuilder);
  }

  @Test
  void testBuildLdStrength() {
    LoadicatorStage loadicatorStage = new LoadicatorStage();

    loadicatorService.buildLdStrength(getLdStrength(), loadicatorStage);
    assertEquals("1", loadicatorStage.getLdStrength().getErrorDetails());
  }

  @Test
  void testBuildLdIntactStability() {
    LoadicatorStage loadicatorStage = new LoadicatorStage();

    loadicatorService.buildLdIntactStability(getLdIntactStability(), loadicatorStage);
    assertEquals("1", loadicatorStage.getLdIntactStability().getErrorDetails());
  }

  @Test
  void testBuildLdTrim() {
    LoadicatorStage loadicatorStage = new LoadicatorStage();

    loadicatorService.buildLdTrim(getLDtrim(), loadicatorStage);
    assertEquals("1", loadicatorStage.getLdTrim().getErrorDetails());
  }

  @Test
  void testBuildPortStabilityParams() {
    PortLoadingPlanStabilityParameters portStabilityParameters =
        new PortLoadingPlanStabilityParameters();
    portStabilityParameters.setFreeboard(new BigDecimal(1));
    portStabilityParameters.setManifoldHeight(new BigDecimal(1));

    loadicatorService.buildPortStabilityParams(
        getLoadingInfoEntity(),
        getLoadicatorResult(),
        portStabilityParameters,
        1,
        1,
        Optional.of(portStabilityParameters));
    assertEquals(new BigDecimal(1), portStabilityParameters.getShearingForce());
    assertEquals(new BigDecimal(1), portStabilityParameters.getFreeboard());
  }

  @Test
  void testBuildLoadingSequenceStabilityParameters() {
    LoadingSequenceStabilityParameters parameters = new LoadingSequenceStabilityParameters();

    loadicatorService.buildLoadingSequenceStabilityParameters(
        getLoadingInfoEntity(), getLoadicatorResult(), parameters);
    assertEquals(new BigDecimal(1), parameters.getTrim());
  }

  private LoadicatorResult getLoadicatorResult() {
    LoadicatorResult result = new LoadicatorResult();
    result.setCalculatedDraftAftPlanned("1");
    result.setBendingMoment("1");
    result.setCalculatedDraftFwdPlanned("1");
    result.setList("1");
    result.setCalculatedDraftMidPlanned("1");
    result.setShearingForce("1");
    result.setCalculatedTrimPlanned("1");
    result.setCalculatedDraftAftPlanned("1");
    result.setCalculatedDraftAftPlanned("1");
    result.setCalculatedDraftAftPlanned("1");
    result.setCalculatedDraftAftPlanned("1");
    result.setCalculatedDraftAftPlanned("1");
    result.setCalculatedDraftAftPlanned("1");
    result.setCalculatedDraftAftPlanned("1");
    result.setCalculatedDraftAftPlanned("1");
    result.setCalculatedDraftAftPlanned("1");
    return result;
  }

  private LoadableStudy.LDtrim getLDtrim() {
    LoadableStudy.LDtrim lDtrim =
        LoadableStudy.LDtrim.newBuilder()
            .setAftDraftValue("1")
            .setAirDraftJudgement("1")
            .setAirDraftValue("1")
            .setDisplacementJudgement("1")
            .setDisplacementValue("1")
            .setErrorDetails("1")
            .setErrorStatus(true)
            .setForeDraftValue("1")
            .setHeelValue("1")
            .setId(1l)
            .setMeanDraftJudgement("1")
            .setMaximumAllowableVisibility("1")
            .setMaximumDraftJudgement("1")
            .setMeanDraftValue("1")
            .setMaximumDraftValue("1")
            .setMeanDraftJudgement("1")
            .setMaximumAllowableJudement("1")
            .setMessageText("1")
            .setMinimumForeDraftInRoughWeatherJudgement("1")
            .setMinimumForeDraftInRoughWeatherValue("1")
            .setTrimValue("1")
            .setDeflection("1")
            .build();
    return lDtrim;
  }

  private LoadableStudy.LDIntactStability getLdIntactStability() {
    LoadableStudy.LDIntactStability ldIntactStability =
        LoadableStudy.LDIntactStability.newBuilder()
            .setAngleatmaxrleverJudgement("1")
            .setAngleatmaxrleverValue("1")
            .setAreaofStability030Judgement("1")
            .setAreaofStability030Value("1")
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
            .setMaximumRightingLeverValue("1")
            .setMessageText("1")
            .setStabilityAreaBaJudgement("1")
            .setStabilityAreaBaValue("1")
            .build();
    return ldIntactStability;
  }

  private LoadableStudy.LDStrength getLdStrength() {
    LoadableStudy.LDStrength ldStrength =
        LoadableStudy.LDStrength.newBuilder()
            .setBendingMomentPersentFrameNumber("1")
            .setBendingMomentPersentJudgement("1")
            .setBendingMomentPersentValue("1")
            .setErrorDetails("1")
            .setInnerLongiBhdFrameNumber("1")
            .setInnerLongiBhdJudgement("1")
            .setInnerLongiBhdValue("1")
            .setMessageText("1")
            .setOuterLongiBhdFrameNumber("1")
            .setOuterLongiBhdValue("1")
            .setSfFrameNumber("1")
            .setSfHopperFrameNumber("1")
            .setSfHopperJudgement("1")
            .setSfHopperValue("1")
            .setSfSideShellFrameNumber("1")
            .setSfSideShellJudgement("1")
            .setSfSideShellValue("1")
            .setShearingForceJudgement("1")
            .setShearingForcePersentValue("1")
            .build();
    return ldStrength;
  }

  private Loadicator.LoadicatorReply getLoadicatorReply() {
    Loadicator.LoadicatorReply reply =
        Loadicator.LoadicatorReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private PortInfo.PortReply getPortReply() {
    List<PortInfo.PortDetail> portDetailList = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder().setCode("1").setWaterDensity("1").build();
    portDetailList.add(portDetail);
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllPorts(portDetailList)
            .build();
    return portReply;
  }

  private VesselInfo.VesselReply getVesselReply() {
    List<VesselInfo.VesselDetail> vesselDetailList = new ArrayList<>();
    VesselInfo.VesselDetail vesselDetail =
        VesselInfo.VesselDetail.newBuilder()
            .setId(1l)
            .setImoNumber("1")
            .setTypeOfShip("1")
            .setCode("1")
            .setProvisionalConstant("1")
            .setDeadweightConstant("1")
            .build();
    vesselDetailList.add(vesselDetail);
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllVessels(vesselDetailList)
            .build();
    return vesselReply;
  }

  private CargoInfo.CargoReply getCargoReply() {
    CargoInfo.CargoReply cargoReply =
        CargoInfo.CargoReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return cargoReply;
  }

  private List<LoadingPlanCommingleDetails> getCommingleDetailsList() {
    List<LoadingPlanCommingleDetails> loadingPlanCommingleDetails = new ArrayList<>();
    LoadingPlanCommingleDetails commingleDetails = new LoadingPlanCommingleDetails();
    commingleDetails.setLoadingPlanPortWiseDetails(getPortWiseDetailsList().get(0));
    commingleDetails.setAbbreviation("1");
    loadingPlanCommingleDetails.add(commingleDetails);
    return loadingPlanCommingleDetails;
  }

  private List<LoadingPlanRobDetails> getRobDetailsList() {
    List<LoadingPlanRobDetails> loadingPlanRobDetails = new ArrayList<>();
    LoadingPlanRobDetails robDetails = new LoadingPlanRobDetails();
    robDetails.setLoadingPlanPortWiseDetails(getPortWiseDetailsList().get(0));
    robDetails.setTankXId(1l);
    robDetails.setQuantity(new BigDecimal(1));
    loadingPlanRobDetails.add(robDetails);
    return loadingPlanRobDetails;
  }

  private List<LoadingPlanBallastDetails> getBallastDetailsList() {
    List<LoadingPlanBallastDetails> loadingPlanBallastDetails = new ArrayList<>();
    LoadingPlanBallastDetails ballastDetails = new LoadingPlanBallastDetails();
    ballastDetails.setSg(new BigDecimal(1));
    ballastDetails.setLoadingPlanPortWiseDetails(getPortWiseDetailsList().get(0));
    loadingPlanBallastDetails.add(ballastDetails);
    return loadingPlanBallastDetails;
  }

  private List<LoadingPlanPortWiseDetails> getPortWiseDetailsList() {
    List<LoadingPlanPortWiseDetails> portWiseDetailsList = new ArrayList<>();
    LoadingPlanPortWiseDetails portWiseDetails = new LoadingPlanPortWiseDetails();
    portWiseDetails.setId(1l);
    portWiseDetails.setTime(1);
    LoadingSequence sequence = new LoadingSequence();
    sequence.setSequenceNumber(1);
    portWiseDetails.setLoadingSequence(sequence);
    portWiseDetailsList.add(portWiseDetails);
    return portWiseDetailsList;
  }

  private List<LoadingPlanStowageDetails> getStowageDetailsList() {
    List<LoadingPlanStowageDetails> stowageDetailsList = new ArrayList<>();
    LoadingPlanStowageDetails stowageDetails = new LoadingPlanStowageDetails();
    stowageDetails.setCargoNominationId(1l);
    stowageDetails.setTankXId(1l);
    stowageDetails.setQuantity(new BigDecimal(1));
    stowageDetails.setLoadingPlanPortWiseDetails(getPortWiseDetailsList().get(0));
    stowageDetailsList.add(stowageDetails);
    return stowageDetailsList;
  }

  private LoadingInformation getLoadingInfoEntity() {
    LoadingInformation loadingInfoOpt = new LoadingInformation();
    loadingInfoOpt.setVoyageId(1l);
    loadingInfoOpt.setVesselXId(1l);
    loadingInfoOpt.setPortXId(1l);
    loadingInfoOpt.setSynopticalTableXId(1l);
    loadingInfoOpt.setLoadablePatternXId(1l);
    loadingInfoOpt.setPortRotationXId(1l);
    loadingInfoOpt.setId(1l);
    return loadingInfoOpt;
  }
}
