/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.loadicator;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;
import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.dischargeplan.domain.algo.LoadicatorStage;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import com.cpdss.dischargeplan.service.DischargeInformationService;
import com.cpdss.dischargeplan.service.DischargePlanAlgoService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@SpringJUnitConfig(classes = {LoadicatorService.class})
public class LoadicatorServiceTest {

  @Autowired LoadicatorService loadicatorService;

  @MockBean DischargeInformationRepository dischargeInformationRepository;

  @MockBean DischargeInformationService dischargeInformationService;

  @MockBean DischargeInformationStatusRepository dischargeInformationStatusRepository;

  @MockBean DischargingInformationAlgoStatusRepository dischargingInformationAlgoStatusRepository;
  @MockBean DischargePlanAlgoService dischargePlanAlgoService;

  @MockBean
  DischargingSequenceStabiltyParametersRepository dischargingSequenceStabiltyParametersRepository;

  @MockBean RestTemplate restTemplate;
  @MockBean private DischargingSequenceRepository dischargingSequenceRepository;

  @MockBean
  private DischargingPlanPortWiseDetailsRepository dischargingPlanPortWiseDetailsRepository;

  @MockBean private DischargingPlanStowageDetailsRepository dischargingPlanStowageDetailsRepository;

  @MockBean private DischargingPlanBallastDetailsRepository dischargingPlanBallastDetailsRepository;

  @MockBean
  private PortDischargingPlanStabilityParametersRepository
      portDischargingPlanStabilityParametersRepository;

  @MockBean private DischargingPlanRobDetailsRepository dischargingPlanRobDetailsRepository;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean private UllageUpdateLoadicatorService ullageUpdateLoadicatorService;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @MockBean private LoadicatorServiceGrpc.LoadicatorServiceBlockingStub loadicatorGrpcService;

  @MockBean private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @Test
  void testGetVesselDetailsForLoadicator() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setVesselXid(1L);
    Mockito.when(this.vesselInfoGrpcService.getVesselDetailByVesselId(Mockito.any()))
        .thenReturn(getVR());
    ReflectionTestUtils.setField(
        loadicatorService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    try {
      var vesselReply = this.loadicatorService.getVesselDetailsForLoadicator(dischargeInformation);
      assertEquals(1L, vesselReply.getVesselId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private VesselInfo.VesselReply getVR() {
    List<VesselInfo.VesselDetail> list = new ArrayList<>();
    VesselInfo.VesselDetail vesselDetail =
        VesselInfo.VesselDetail.newBuilder()
            .setId(1L)
            .setImoNumber("1")
            .setTypeOfShip("1")
            .setCode("1")
            .setProvisionalConstant("1")
            .setDeadweightConstant("1")
            .build();
    list.add(vesselDetail);
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .addAllVessels(list)
            .setVesselId(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return vesselReply;
  }

  @Test
  void testGetVesselDetailByVesselId() {
    VesselInfo.VesselRequest replyBuilder = VesselInfo.VesselRequest.newBuilder().build();
    Mockito.when(this.vesselInfoGrpcService.getVesselDetailByVesselId(Mockito.any()))
        .thenReturn(getVR());
    ReflectionTestUtils.setField(
        loadicatorService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    var vesselReply = this.loadicatorService.getVesselDetailByVesselId(replyBuilder);
    assertEquals(1L, vesselReply.getVesselId());
  }

  @Test
  void testBuildLdStrength() {
    com.cpdss.common.generated.LoadableStudy.LDStrength ldStrength =
        LoadableStudy.LDStrength.newBuilder()
            .setBendingMomentPersentFrameNumber("1")
            .setBendingMomentPersentJudgement("1")
            .setBendingMomentPersentValue("1")
            .setErrorDetails("1")
            .setId(1L)
            .setInnerLongiBhdFrameNumber("1")
            .setInnerLongiBhdJudgement("1")
            .setInnerLongiBhdValue("1")
            .setMessageText("1")
            .setOuterLongiBhdFrameNumber("1")
            .setOuterLongiBhdJudgement("1")
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
    LoadicatorStage loadicatorStage = new LoadicatorStage();
    this.loadicatorService.buildLdStrength(ldStrength, loadicatorStage);
    assertEquals("1", loadicatorStage.getLdStrength().getErrorDetails());
  }

  @Test
  void testBuildLdIntactStability() {
    com.cpdss.common.generated.LoadableStudy.LDIntactStability lDIntactStability =
        LoadableStudy.LDIntactStability.newBuilder()
            .setAngleatmaxrleverJudgement("1")
            .setAngleatmaxrleverValue("1")
            .setAreaofStability030Judgement("1")
            .setAreaofStability030Value("1")
            .setAreaofStability040Judgement("1")
            .setAreaofStability040Value("1")
            .setAreaofStability3040Judgement("1")
            .setAreaofStability3040Value("1")
            .setBigintialGomValue("1")
            .setBigIntialGomJudgement("1")
            .setErrorDetails("1")
            .setErrorStatus(true)
            .setGmAllowableCurveCheckJudgement("1")
            .setGmAllowableCurveCheckValue("1")
            .setHeelBySteadyWindJudgement("1")
            .setHeelBySteadyWindValue("1")
            .setId(1L)
            .setMaximumRightingLeverJudgement("1")
            .setMaximumRightingLeverValue("1")
            .setMessageText("1")
            .setStabilityAreaBaJudgement("1")
            .setStabilityAreaBaValue("1")
            .build();
    LoadicatorStage loadicatorStage = new LoadicatorStage();
    this.loadicatorService.buildLdIntactStability(lDIntactStability, loadicatorStage);
    assertEquals("1", loadicatorStage.getLdIntactStability().getAreaofStability030Value());
  }

  @Test
  void testBuildLdTrim() {
    LoadableStudy.LDtrim ldTrim =
        LoadableStudy.LDtrim.newBuilder()
            .setAftDraftValue("1")
            .setAirDraftValue("1")
            .setAirDraftJudgement("1")
            .setDisplacementJudgement("1")
            .setDisplacementValue("1")
            .setErrorDetails("1")
            .setErrorStatus(true)
            .setForeDraftValue("1")
            .setHeelValue("1")
            .setId(1L)
            .setMaximumAllowableJudement("1")
            .setMaximumAllowableVisibility("1")
            .setMaximumDraftValue("1")
            .setMaximumDraftJudgement("1")
            .setMeanDraftJudgement("1")
            .setMeanDraftValue("1")
            .setMessageText("1")
            .setMinimumForeDraftInRoughWeatherJudgement("1")
            .setMinimumForeDraftInRoughWeatherValue("1")
            .setTrimValue("1")
            .setDeflection("1")
            .build();
    LoadicatorStage loadicatorStage = new LoadicatorStage();
    this.loadicatorService.buildLdTrim(ldTrim, loadicatorStage);
    assertEquals("1", loadicatorStage.getLdTrim().getErrorDetails());
  }

  @Test
  void testGetCargoNominationDetails() {
    Set<Long> cargoNominationIds = new HashSet<>();
    cargoNominationIds.add(1L);
    Mockito.when(loadableStudyGrpcService.getCargoNominationByCargoNominationId(Mockito.any()))
        .thenReturn(getCNDR());
    ReflectionTestUtils.setField(
        loadicatorService, "loadableStudyGrpcService", this.loadableStudyGrpcService);
    var details = this.loadicatorService.getCargoNominationDetails(cargoNominationIds);
    assertEquals(1, details.size());
  }

  private LoadableStudy.CargoNominationDetailReply getCNDR() {
    LoadableStudy.CargoNominationDetailReply reply =
        LoadableStudy.CargoNominationDetailReply.newBuilder()
            .setCargoNominationdetail(
                LoadableStudy.CargoNominationDetail.newBuilder()
                    .setId(1L)
                    .setCargoId(1L)
                    .setAbbreviation("1")
                    .build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetCargoInfoForLoadicator() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setVesselXid(1L);
    dischargeInformation.setVoyageXid(1L);
    Mockito.when(cargoInfoGrpcService.getCargoInfo(Mockito.any())).thenReturn(getCR());
    ReflectionTestUtils.setField(
        loadicatorService, "cargoInfoGrpcService", this.cargoInfoGrpcService);
    try {
      var cargoReply = this.loadicatorService.getCargoInfoForLoadicator(dischargeInformation);
      assertEquals(SUCCESS, cargoReply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private CargoInfo.CargoReply getCR() {
    CargoInfo.CargoReply cargoReply =
        CargoInfo.CargoReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return cargoReply;
  }

  @Test
  void testGetCargoInfo() {
    CargoInfo.CargoRequest build = CargoInfo.CargoRequest.newBuilder().build();
    Mockito.when(cargoInfoGrpcService.getCargoInfo(Mockito.any())).thenReturn(getCR());
    ReflectionTestUtils.setField(
        loadicatorService, "cargoInfoGrpcService", this.cargoInfoGrpcService);
    var cargoReply = this.loadicatorService.getCargoInfo(build);
    assertEquals(SUCCESS, cargoReply.getResponseStatus().getStatus());
  }

  @Test
  void testGetPortInfoForLoadicator() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setVesselXid(1L);
    dischargeInformation.setVoyageXid(1L);
    Mockito.when(portInfoGrpcService.getPortInfo(Mockito.any())).thenReturn(getPI());
    ReflectionTestUtils.setField(
        loadicatorService, "portInfoGrpcService", this.portInfoGrpcService);
    try {
      var portReply = this.loadicatorService.getPortInfoForLoadicator(dischargeInformation);
      assertEquals(SUCCESS, portReply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private PortInfo.PortReply getPI() {
    List<PortInfo.PortDetail> list = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder().setId(1L).setCode("1").setWaterDensity("1").build();
    list.add(portDetail);
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .addAllPorts(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return portReply;
  }

  @Test
  void testGetPortInfo() {
    PortInfo.PortRequest build = PortInfo.PortRequest.newBuilder().build();
    Mockito.when(portInfoGrpcService.getPortInfo(Mockito.any())).thenReturn(getPI());
    ReflectionTestUtils.setField(
        loadicatorService, "portInfoGrpcService", this.portInfoGrpcService);
    var portReply = this.loadicatorService.getPortInfo(build);
    assertEquals(SUCCESS, portReply.getResponseStatus().getStatus());
  }

  @Test
  void testBuildStowagePlan() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setPortXid(1L);
    dischargeInformation.setId(1L);
    dischargeInformation.setPortRotationXid(1L);
    dischargeInformation.setSynopticTableXid(1L);
    Integer time = 1;
    String processId = "1";
    CargoInfo.CargoReply cargoReply = CargoInfo.CargoReply.newBuilder().build();
    List<VesselInfo.VesselDetail> list = new ArrayList<>();
    VesselInfo.VesselDetail vesselDetail =
        VesselInfo.VesselDetail.newBuilder()
            .setId(1L)
            .setImoNumber("1")
            .setTypeOfShip("1")
            .setCode("1")
            .setProvisionalConstant("1")
            .setDeadweightConstant("1")
            .build();
    list.add(vesselDetail);
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder().addAllVessels(list).build();
    List<PortInfo.PortDetail> list1 = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder().setId(1L).setCode("1").setWaterDensity("1").build();
    list1.add(portDetail);
    PortInfo.PortReply portReply = PortInfo.PortReply.newBuilder().addAllPorts(list1).build();
    Loadicator.StowagePlan.Builder stowagePlanBuilder = Loadicator.StowagePlan.newBuilder();
    this.loadicatorService.buildStowagePlan(
        dischargeInformation,
        time,
        processId,
        cargoReply,
        vesselReply,
        portReply,
        stowagePlanBuilder);
    assertEquals(1L, stowagePlanBuilder.getVesselId());
  }

  @Test
  void testSaveLoadicatorInfo() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setVoyageXid(1L);
    dischargeInformation.setVesselXid(1L);
    dischargeInformation.setPortXid(1L);
    dischargeInformation.setSynopticTableXid(1L);
    String processId = "1";
    Mockito.when(
            dischargingSequenceRepository
                .findByDischargeInformationAndIsActiveOrderBySequenceNumber(
                    Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLDS());
    Mockito.when(
            dischargingPlanPortWiseDetailsRepository
                .findByDischargeInformationIdAndToLoadicatorAndIsActive(
                    Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyBoolean()))
        .thenReturn(getLDPPD());
    Mockito.when(
            dischargingPlanStowageDetailsRepository.findByPortWiseDetailIdsAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLDPSD());
    Mockito.when(loadableStudyGrpcService.getCargoNominationByCargoNominationId(Mockito.any()))
        .thenReturn(getCNDR());
    Mockito.when(
            dischargingPlanBallastDetailsRepository
                .findByDischargingPlanPortWiseDetailIdsAndIsActive(
                    Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLDPBD());
    Mockito.when(
            dischargingPlanRobDetailsRepository.findByPortWiseDetailIdsAndIsActive(
                Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getLDPRD());
    Mockito.when(cargoInfoGrpcService.getCargoInfo(Mockito.any())).thenReturn(getCR());
    Mockito.when(this.vesselInfoGrpcService.getVesselDetailByVesselId(Mockito.any()))
        .thenReturn(getVR());
    Mockito.when(portInfoGrpcService.getPortInfo(Mockito.any())).thenReturn(getPI());

    ReflectionTestUtils.setField(
        loadicatorService, "loadableStudyGrpcService", this.loadableStudyGrpcService);
    ReflectionTestUtils.setField(
        loadicatorService, "cargoInfoGrpcService", this.cargoInfoGrpcService);
    ReflectionTestUtils.setField(
        loadicatorService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        loadicatorService, "portInfoGrpcService", this.portInfoGrpcService);
  }

  private List<DischargingSequence> getLDS() {
    List<DischargingSequence> list = new ArrayList<>();
    DischargingSequence dischargingSequence = new DischargingSequence();
    list.add(dischargingSequence);
    return list;
  }

  private List<com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails> getLDPPD() {
    List<com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails> list = new ArrayList<>();
    com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails details =
        new DischargingPlanPortWiseDetails();
    details.setId(1L);
    details.setTime(1);
    details.setDischargingSequence(getDS());
    list.add(details);
    return list;
  }

  private DischargingSequence getDS() {
    DischargingSequence dischargingSequence = new DischargingSequence();
    dischargingSequence.setSequenceNumber(1);
    return dischargingSequence;
  }

  private List<DischargingPlanStowageDetails> getLDPSD() {
    List<DischargingPlanStowageDetails> list = new ArrayList<>();
    DischargingPlanStowageDetails details = new DischargingPlanStowageDetails();
    details.setDischargingPlanPortWiseDetails(getDPPWD());
    details.setTankXId(1L);
    details.setQuantity(new BigDecimal(1));
    details.setCargoNominationId(1L);
    list.add(details);
    return list;
  }

  private DischargingPlanPortWiseDetails getDPPWD() {
    DischargingPlanPortWiseDetails details = new DischargingPlanPortWiseDetails();
    details.setTime(1);
    details.setId(1L);
    return details;
  }

  private List<DischargingPlanBallastDetails> getLDPBD() {
    List<DischargingPlanBallastDetails> list = new ArrayList<>();
    DischargingPlanBallastDetails details = new DischargingPlanBallastDetails();
    details.setDischargingPlanPortWiseDetails(getDPPWD());
    list.add(details);
    return list;
  }

  private List<DischargingPlanRobDetails> getLDPRD() {
    List<DischargingPlanRobDetails> list = new ArrayList<>();
    DischargingPlanRobDetails details = new DischargingPlanRobDetails();
    details.setDischargingPlanPortWiseDetails(getDPPWD());
    list.add(details);
    return list;
  }

  @Test
  void testSaveLoadicatorInfo1Arg() {
    Loadicator.LoadicatorRequest loadicatorRequest =
        Loadicator.LoadicatorRequest.newBuilder().build();
    Mockito.when(loadicatorGrpcService.saveLoadicatorInfo(Mockito.any())).thenReturn(getLR());
    ReflectionTestUtils.setField(
        loadicatorService, "loadicatorGrpcService", this.loadicatorGrpcService);
    var reply = this.loadicatorService.saveLoadicatorInfo(loadicatorRequest);
    assertEquals(SUCCESS, reply.getResponseStatus().getStatus());
  }

  private Loadicator.LoadicatorReply getLR() {
    Loadicator.LoadicatorReply reply =
        Loadicator.LoadicatorReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testSaveJson() {
    com.cpdss.common.generated.LoadableStudy.JsonRequest.Builder jsonBuilder =
        LoadableStudy.JsonRequest.newBuilder();
    //  Mockito.when()

  }
}
