/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import static com.cpdss.gateway.common.GatewayConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.discharge_plan.*;
import com.cpdss.common.generated.discharge_plan.CowPlan;
import com.cpdss.common.generated.discharge_plan.DischargeInformation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.dischargeplan.*;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.domain.loadingplan.CargoMachineryInUse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanRobDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanStowageDetails;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.LoadableStudyService;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingInformationService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanBuilderService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.NoTransactionException;

@SpringJUnitConfig(classes = {DischargeInformationService.class})
public class DischargeInformationServiceTest {

  @Autowired DischargeInformationService dischargeInformationService;

  @MockBean DischargeInformationGrpcService dischargeInformationGrpcService;

  @MockBean DischargeInformationBuilderService infoBuilderService;

  @MockBean LoadingPlanGrpcService loadingPlanGrpcService;

  @MockBean LoadingInformationService loadingInformationService;

  @MockBean LoadingPlanBuilderService dischargingPlanBuilderService;

  @MockBean LoadingPlanService loadingPlanService;
  @MockBean DischargingSequenceService dischargingSequenceService;
  @MockBean VesselInfoService vesselInfoService;
  @MockBean LoadableStudyService loadableStudyService;

  @MockBean
  private DischargeInformationServiceGrpc.DischargeInformationServiceBlockingStub
      dischargeInfoServiceStub;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean
  DischargePlanServiceGrpc.DischargePlanServiceBlockingStub dischargePlanServiceBlockingStub;

  @Value("${gateway.attachement.rootFolder}")
  private String rootFolder;

  //  @Test
  //  void testGetDischargeInformation() throws GenericServiceException {
  //    Long vesselId = 1L;
  //    Long voyageId = 1L;
  //    Long portRoId = 1L;
  //    Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
  //        .thenReturn(getVR());
  //    Mockito.when(
  //            this.dischargeInformationGrpcService.getDischargeInfoRpc(
  //                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
  //        .thenReturn(getDI());
  //    Mockito.when(
  //            vesselInfoService.getRulesByVesselIdAndSectionId(
  //                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any()))
  //        .thenReturn(getRP());
  //    Mockito.when(
  //            this.infoBuilderService.buildDischargeDetailFromMessage(
  //                Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
  //        .thenReturn(getLD());
  //    Mockito.when(
  //            this.infoBuilderService.buildDischargeRatesFromMessage(Mockito.any(),
  // Mockito.any()))
  //        .thenReturn(getDR());
  //
  // Mockito.when(this.loadingInformationService.getMasterBerthDetailsByPortId(Mockito.anyLong()))
  //        .thenReturn(getLBD());
  //    Mockito.when(this.infoBuilderService.buildDischargeBerthsFromMessage(Mockito.anyList()))
  //        .thenReturn(getLBD());
  //    Mockito.when(
  //            this.infoBuilderService.buildDischargeMachinesFromMessage(
  //                Mockito.anyList(), Mockito.anyLong()))
  //        .thenReturn(getCMU());
  //    Mockito.when(this.loadingInformationService.getLoadingStagesAndMasters(Mockito.any()))
  //        .thenReturn(getLSS());
  //
  // Mockito.when(this.infoBuilderService.buildDischargeSequencesAndDelayFromMessage(Mockito.any()))
  //        .thenReturn(getLSSS());
  //    Mockito.when(this.infoBuilderService.buildDischargeCowPlan(Mockito.any(), Mockito.any()))
  //        .thenReturn(getCP());
  //    doNothing()
  //        .when(infoBuilderService)
  //        .buildPostDischargeRates(Mockito.any(), Mockito.any(), Mockito.any());
  //    Mockito.when(
  //            this.loadingPlanGrpcService.fetchPortWiseCargoDetails(
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.any()))
  //        .thenReturn(getCVTD());
  //    Mockito.when(
  //            this.loadingInformationService.getDischargePlanCargoDetailsByPort(
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.any(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong()))
  //        .thenReturn(getLDQCD());
  //    Mockito.when(
  //
  // this.loadingInformationService.getLoadingInfoCargoDetailsByPattern(Mockito.anyLong()))
  //        .thenReturn(getLISR());
  //    var reply =
  //        this.dischargeInformationService.getDischargeInformation(vesselId, voyageId, portRoId);
  //    assertEquals(
  //        "1",
  //        reply
  //            .getCargoVesselTankDetails()
  //            .getDischargeQuantityCargoDetails()
  //            .get(0)
  //            .getCargoAbbreviation());
  //  }

  private LoadingInformationSynopticalReply getLISR() {
    List<Common.BillOfLadding> list = new ArrayList<>();
    Common.BillOfLadding bill =
        Common.BillOfLadding.newBuilder().setCargoAbbrevation("1").setQuantityBbls("1").build();
    list.add(bill);
    LoadingInformationSynopticalReply reply =
        LoadingInformationSynopticalReply.newBuilder().addAllBillOfLadding(list).build();
    return reply;
  }

  private List<DischargeQuantityCargoDetails> getLDQCD() {
    List<DischargeQuantityCargoDetails> list = new ArrayList<>();
    DischargeQuantityCargoDetails details = new DischargeQuantityCargoDetails();
    details.setCargoAbbreviation("1");
    details.setDischargeCargoNominationId(1L);
    details.setCargoNominationId(1L);
    list.add(details);
    return list;
  }

  private CargoVesselTankDetails getCVTD() {
    CargoVesselTankDetails cargoVesselTankDetails = new CargoVesselTankDetails();
    cargoVesselTankDetails.setDischargeQuantityCargoDetails(getLDQCD());
    cargoVesselTankDetails.setCargoQuantities(getLSCBR());
    return cargoVesselTankDetails;
  }

  private List<SynopticalCargoBallastRecord> getLSCBR() {
    List<SynopticalCargoBallastRecord> list = new ArrayList<>();
    SynopticalCargoBallastRecord record = new SynopticalCargoBallastRecord();
    record.setCargoNominationId(1L);
    list.add(record);
    return list;
  }

  private com.cpdss.gateway.domain.dischargeplan.CowPlan getCP() {
    com.cpdss.gateway.domain.dischargeplan.CowPlan cowPlan =
        new com.cpdss.gateway.domain.dischargeplan.CowPlan();
    cowPlan.setCowStart("1");
    return cowPlan;
  }

  private LoadingSequences getLSSS() {
    LoadingSequences loadingSequences = new LoadingSequences();
    return loadingSequences;
  }

  private LoadingStages getLSS() {
    LoadingStages loadingStages = new LoadingStages();
    loadingStages.setId(1L);
    return loadingStages;
  }

  private CargoMachineryInUse getCMU() {
    CargoMachineryInUse cargoMachineryInUse = new CargoMachineryInUse();
    return cargoMachineryInUse;
  }

  private List<BerthDetails> getLBD() {
    List<BerthDetails> list = new ArrayList<>();
    BerthDetails berthDetails = new BerthDetails();
    berthDetails.setCargoCirculation(true);
    berthDetails.setLoadingBerthId(1L);
    berthDetails.setLoadingInfoId(1L);
    list.add(berthDetails);
    return list;
  }

  private com.cpdss.gateway.domain.dischargeplan.DischargeRates getDR() {
    com.cpdss.gateway.domain.dischargeplan.DischargeRates dischargeRates =
        new com.cpdss.gateway.domain.dischargeplan.DischargeRates();
    dischargeRates.setMinBallastRate(new BigDecimal(1));
    return dischargeRates;
  }

  private LoadingDetails getLD() {
    LoadingDetails details = new LoadingDetails();
    details.setTimeOfSunrise("1");
    details.setTimeOfSunset("1");
    return details;
  }

  private RuleResponse getRP() {
    RuleResponse response = new RuleResponse();
    response.setPlan(getLRP());
    return response;
  }

  private List<RulePlans> getLRP() {
    List<RulePlans> list = new ArrayList<>();
    RulePlans rulePlans = new RulePlans();
    rulePlans.setHeader("1");
    list.add(rulePlans);
    return list;
  }

  private com.cpdss.common.generated.discharge_plan.DischargeInformation getDI() {
    List<LoadingPlanModels.LoadingMachinesInUse> list1 = new ArrayList<>();
    LoadingPlanModels.LoadingMachinesInUse loadingMachinesInUse =
        LoadingPlanModels.LoadingMachinesInUse.newBuilder().build();
    list1.add(loadingMachinesInUse);
    List<DischargeBerths> list = new ArrayList<>();
    DischargeBerths berths = DischargeBerths.newBuilder().build();
    list.add(berths);
    com.cpdss.common.generated.discharge_plan.DischargeInformation information =
        DischargeInformation.newBuilder()
            .setPostDischargeStageTime(PostDischargeStageTime.newBuilder().build())
            .setCowPlan(CowPlan.newBuilder().build())
            .setDischargeDelay(DischargeDelay.newBuilder().build())
            .setDischargeStage(LoadingPlanModels.LoadingStages.newBuilder().build())
            .addAllMachineInUse(list1)
            .addAllBerthDetails(list)
            .setDischargeInfoId(1L)
            .setSynopticTableId(1L)
            .setDischargeSlopTanksFirst(true)
            .setIsDischargeInfoComplete(true)
            .setIsDischargingInfoComplete(true)
            .setDischargeCommingledCargoSeparately(true)
            .setIsDischargingPlanGenerated(true)
            .setIsDischargingSequenceGenerated(true)
            .setDischargingInfoStatusId(1L)
            .setIsDischargingInstructionsComplete(true)
            .setDischargeRate(
                com.cpdss.common.generated.discharge_plan.DischargeRates.newBuilder().build())
            .setDischargeDetails(DischargeDetails.newBuilder().build())
            .build();
    return information;
  }

  private VoyageResponse getVR() {
    VoyageResponse voyageResponse = new VoyageResponse();
    voyageResponse.setId(1L);
    voyageResponse.setVoyageNumber("1");
    voyageResponse.setActiveDs(getLS());
    voyageResponse.setDischargePatternId(1L);
    voyageResponse.setPatternId(1L);
    voyageResponse.setDischargePortRotations(getLPR());
    return voyageResponse;
  }

  private LoadableStudy getLS() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setName("1");
    return loadableStudy;
  }

  private List<PortRotation> getLPR() {
    List<PortRotation> list = new ArrayList<>();
    PortRotation portRotation = new PortRotation();
    portRotation.setId(1L);
    portRotation.setPortOrder(1L);
    portRotation.setPortId(1L);
    list.add(portRotation);
    return list;
  }

  @Test
  void testGetDischargeInformationException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long portRoId = 1L;

    try {
      Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
          .thenReturn(getVRNS());
      var response =
          this.dischargeInformationService.getDischargeInformation(vesselId, voyageId, portRoId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private VoyageResponse getVRNS() {
    VoyageResponse voyageResponse = new VoyageResponse();
    voyageResponse.setId(1L);
    voyageResponse.setVoyageNumber("1");
    voyageResponse.setDischargePortRotations(getLPRNS());
    return voyageResponse;
  }

  private List<PortRotation> getLPRNS() {
    List<PortRotation> list = new ArrayList<>();
    PortRotation portRotation = new PortRotation();
    portRotation.setId(1L);
    list.add(portRotation);
    return list;
  }

  @Test
  void testSetDischargeCargoNominationId() {
    CargoVesselTankDetails vesselTankDetails = new CargoVesselTankDetails();
    vesselTankDetails.setCargoQuantities(getLSCBR());
    vesselTankDetails.setDischargeQuantityCargoDetails(getLDQCD());
    this.dischargeInformationService.setDischargeCargoNominationId(vesselTankDetails);
  }

  @Test
  void testGetDischargingPlan() throws GenericServiceException {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    String correlationId = "1";
    Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
        .thenReturn(getVR());
    Mockito.when(this.dischargeInfoServiceStub.getDischargingPlan(Mockito.any()))
        .thenReturn(getDPR());
    Mockito.when(
            this.infoBuilderService.buildDischargeRatesFromMessage(Mockito.any(), Mockito.any()))
        .thenReturn(getDR());
    Mockito.when(this.loadingInformationService.getMasterBerthDetailsByPortId(Mockito.anyLong()))
        .thenReturn(getLBD());
    Mockito.when(this.infoBuilderService.buildDischargeBerthsFromMessage(Mockito.anyList()))
        .thenReturn(getLBD());
    Mockito.when(
            this.infoBuilderService.buildDischargeMachinesFromMessage(
                Mockito.anyList(), Mockito.anyLong()))
        .thenReturn(getCMU());
    Mockito.when(this.loadingInformationService.getLoadingStagesAndMasters(Mockito.any()))
        .thenReturn(getLSS());
    Mockito.when(this.loadingInformationService.getPostDischargeStage(Mockito.any()))
        .thenReturn(getPDS());
    Mockito.when(loadingPlanService.getCargoNominationsByStudyId(Mockito.anyLong()))
        .thenReturn(getCNR());
    Mockito.when(
            this.loadingPlanGrpcService.fetchPortWiseCargoDetails(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any()))
        .thenReturn(getCVTD());
    Mockito.when(
            this.loadingInformationService.getDischargePlanCargoDetailsByPort(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong()))
        .thenReturn(getLDQCD());
    Mockito.when(this.infoBuilderService.buildDischargeSequencesAndDelayFromMessage(Mockito.any()))
        .thenReturn(getLSSS());
    Mockito.when(
            this.infoBuilderService.buildDischargeDetailFromMessage(
                Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
        .thenReturn(getLD());
    Mockito.when(
            vesselInfoService.getRulesByVesselIdAndSectionId(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any()))
        .thenReturn(getRP());
    Mockito.when(this.infoBuilderService.buildDischargeCowPlan(Mockito.any(), Mockito.any()))
        .thenReturn(getCP());
    Mockito.when(
            this.loadingPlanGrpcService.fetchLoadablePlanCargoDetails(
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any(),
                Mockito.anyLong()))
        .thenReturn(getLQCD());
    Mockito.when(
            loadingInformationService.buildDischargePlanQuantity(
                Mockito.anyList(), Mockito.anyLong()))
        .thenReturn(getLDQCD());
    Mockito.when(dischargingPlanBuilderService.buildLoadingPlanBallastFromRpc(Mockito.anyList()))
        .thenReturn(getLPBD());
    Mockito.when(dischargingPlanBuilderService.buildLoadingPlanStowageFromRpc(Mockito.anyList()))
        .thenReturn(getLLPSD());
    Mockito.when(dischargingPlanBuilderService.buildLoadingPlanRobFromRpc(Mockito.anyList()))
        .thenReturn(getLLPRD());
    Mockito.when(
            dischargingPlanBuilderService.buildLoadingPlanStabilityParamFromRpc(Mockito.anyList()))
        .thenReturn(getLLPSP());
    Mockito.when(dischargingPlanBuilderService.buildLoadingPlanCommingleFromRpc(Mockito.anyList()))
        .thenReturn(getLLPCD());
    Mockito.when(
            this.loadingInformationService.getLoadingInfoCargoDetailsByPattern(Mockito.anyLong()))
        .thenReturn(getLISR());
    ReflectionTestUtils.setField(
        dischargeInformationService, "dischargeInfoServiceStub", dischargeInfoServiceStub);
    var response =
        this.dischargeInformationService.getDischargingPlan(
            vesselId, voyageId, infoId, portRotationId, correlationId);
    assertEquals("200", response.getResponseStatus().getStatus());
  }

  private List<com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails> getLLPCD() {
    List<com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails> list = new ArrayList<>();
    com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails loadingPlanCommingleDetails =
        new LoadingPlanCommingleDetails();
    list.add(loadingPlanCommingleDetails);
    return list;
  }

  private List<LoadingPlanStabilityParam> getLLPSP() {
    List<LoadingPlanStabilityParam> list = new ArrayList<>();
    LoadingPlanStabilityParam param = new LoadingPlanStabilityParam();
    list.add(param);
    return list;
  }

  private List<LoadingPlanRobDetails> getLLPRD() {
    List<LoadingPlanRobDetails> list = new ArrayList<>();
    LoadingPlanRobDetails loadingPlanRobDetails = new LoadingPlanRobDetails();
    list.add(loadingPlanRobDetails);
    return list;
  }

  private List<LoadingPlanStowageDetails> getLLPSD() {
    List<LoadingPlanStowageDetails> list = new ArrayList<>();
    LoadingPlanStowageDetails loadingPlanStowageDetails = new LoadingPlanStowageDetails();
    list.add(loadingPlanStowageDetails);
    return list;
  }

  private List<LoadingPlanBallastDetails> getLPBD() {
    List<LoadingPlanBallastDetails> list = new ArrayList<>();
    LoadingPlanBallastDetails loadingPlanBallastDetails = new LoadingPlanBallastDetails();
    list.add(loadingPlanBallastDetails);
    return list;
  }

  private List<com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails> getLQCD() {
    List<com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails> list =
        new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails
        loadableQuantityCargoDetails =
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails.newBuilder()
                .build();

    list.add(loadableQuantityCargoDetails);
    return list;
  }

  private com.cpdss.common.generated.LoadableStudy.CargoNominationReply getCNR() {
    com.cpdss.common.generated.LoadableStudy.CargoNominationReply reply =
        com.cpdss.common.generated.LoadableStudy.CargoNominationReply.newBuilder().build();
    return reply;
  }

  private PostDischargeStage getPDS() {
    PostDischargeStage stage = new PostDischargeStage();
    return stage;
  }

  private DischargingPlanReply getDPR() {
    List<LoadingPlanModels.LoadingPlanCommingleDetails> list6 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanCommingleDetails loadingPlanCommingleDetails =
        LoadingPlanModels.LoadingPlanCommingleDetails.newBuilder().build();
    list6.add(loadingPlanCommingleDetails);
    List<LoadingPlanModels.LoadingPlanStabilityParameters> list5 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanStabilityParameters planStabilityParameters =
        LoadingPlanModels.LoadingPlanStabilityParameters.newBuilder().build();
    list5.add(planStabilityParameters);
    List<LoadingPlanModels.LoadingPlanTankDetails> list4 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanTankDetails loadingPlanTank =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder().build();
    list4.add(loadingPlanTank);
    List<LoadingPlanModels.LoadingPlanTankDetails> list3 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanTankDetails loadingPlanTankDetails =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder().build();
    list3.add(loadingPlanTankDetails);
    List<LoadingPlanModels.LoadingPlanTankDetails> list1 = new ArrayList<>();
    LoadingPlanModels.LoadingPlanTankDetails details =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder().build();
    list1.add(details);
    List<LoadingPlanModels.LoadingMachinesInUse> list2 = new ArrayList<>();
    LoadingPlanModels.LoadingMachinesInUse machinesInUse =
        LoadingPlanModels.LoadingMachinesInUse.newBuilder().build();
    list2.add(machinesInUse);
    List<DischargeBerths> list = new ArrayList<>();
    DischargeBerths berths = DischargeBerths.newBuilder().build();
    list.add(berths);
    DischargingPlanReply reply =
        DischargingPlanReply.newBuilder()
            .addAllPortDischargingPlanCommingleDetails(list6)
            .addAllPortDischargingPlanStabilityParameters(list5)
            .addAllPortDischargingPlanRobDetails(list4)
            .addAllPortDischargingPlanStowageDetails(list3)
            .addAllPortDischargingPlanBallastDetails(list1)
            .setDischargingInformation(
                DischargeInformation.newBuilder()
                    .setDischargingInfoStatusId(1L)
                    .setDischargingPlanArrStatusId(1L)
                    .setDischargingPlanDepStatusId(1L)
                    .setIsDischargingInfoComplete(true)
                    .setIsDischargingPlanGenerated(true)
                    .setIsDischargingSequenceGenerated(true)
                    .setDischargeSlopTanksFirst(true)
                    .setDischargeCommingledCargoSeparately(true)
                    .setCowPlan(CowPlan.newBuilder().build())
                    .setDischargeDetails(DischargeDetails.newBuilder().build())
                    .setDischargeDelay(DischargeDelay.newBuilder().build())
                    .setPostDischargeStageTime(PostDischargeStageTime.newBuilder().build())
                    .setDischargeStage(LoadingPlanModels.LoadingStages.newBuilder().build())
                    .addAllMachineInUse(list2)
                    .addAllBerthDetails(list)
                    .setDischargeRate(
                        com.cpdss.common.generated.discharge_plan.DischargeRates.newBuilder()
                            .build())
                    .setDischargeInfoId(1L)
                    .setSynopticTableId(1L)
                    .build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetDischargingPlanException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Long portRotationId = 1L;
    String correlationId = "1";
    try {
      Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
          .thenReturn(getVR());
      Mockito.when(this.dischargeInfoServiceStub.getDischargingPlan(Mockito.any()))
          .thenReturn(getDPRNS());
      ReflectionTestUtils.setField(
          dischargeInformationService, "dischargeInfoServiceStub", dischargeInfoServiceStub);
      var response =
          this.dischargeInformationService.getDischargingPlan(
              vesselId, voyageId, infoId, portRotationId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargingPlanReply getDPRNS() {
    DischargingPlanReply reply = DischargingPlanReply.newBuilder().build();
    return reply;
  }

  @Test
  void testGetUpdateUllageDetails() {
    Long vesselId = 1L;
    Long patternId = 1L;
    Long portRotationId = 1L;
    String operationType = "1";
    try {
      Mockito.when(
              loadingPlanService.getUpdateUllageDetails(
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.anyLong(),
                  Mockito.any(),
                  Mockito.anyBoolean()))
          .thenReturn(getLUUR());
      var response =
          this.dischargeInformationService.getUpdateUllageDetails(
              vesselId, patternId, portRotationId, operationType);
      assertEquals("1", response.getDischargePlanCommingleDetails().get(0).getColorCode());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private LoadingUpdateUllageResponse getLUUR() {
    LoadingUpdateUllageResponse response = new LoadingUpdateUllageResponse();
    response.setPortLoadablePlanBallastDetails(getLPLPBD());
    response.setPortLoadablePlanRobDetails(getLPLPRD());
    response.setPortLoadablePlanStowageDetails(getLPLPSD());
    response.setLoadablePlanCommingleDetails(getLLPCDN());
    return response;
  }

  private List<LoadablePlanCommingleDetails> getLLPCDN() {
    List<LoadablePlanCommingleDetails> list = new ArrayList<>();
    LoadablePlanCommingleDetails details = new LoadablePlanCommingleDetails();
    details.setColorCode("1");
    list.add(details);
    return list;
  }

  private List<PortLoadablePlanStowageDetails> getLPLPSD() {
    List<PortLoadablePlanStowageDetails> list = new ArrayList<>();
    PortLoadablePlanStowageDetails details = new PortLoadablePlanStowageDetails();
    list.add(details);
    return list;
  }

  private List<PortLoadablePlanBallastDetails> getLPLPBD() {
    List<PortLoadablePlanBallastDetails> list = new ArrayList<>();
    PortLoadablePlanBallastDetails details = new PortLoadablePlanBallastDetails();
    list.add(details);
    return list;
  }

  private List<PortLoadablePlanRobDetails> getLPLPRD() {
    List<PortLoadablePlanRobDetails> list = new ArrayList<>();
    PortLoadablePlanRobDetails rob = new PortLoadablePlanRobDetails();
    list.add(rob);
    return list;
  }

  @Test
  void testUpdateUllage() throws GenericServiceException {
    UllageBillRequest request = new UllageBillRequest();
    String correlationId = "1";
    Mockito.when(
            loadingPlanService.getLoadableStudyShoreTwo(
                Mockito.any(), Mockito.any(), Mockito.anyBoolean()))
        .thenReturn(getUBR());
    var response = this.dischargeInformationService.updateUllage(request, correlationId);
    assertEquals("1", response.getProcessId());
  }

  private UllageBillReply getUBR() {
    UllageBillReply billReply = new UllageBillReply();
    billReply.setProcessId("1");
    return billReply;
  }

  //  @Test
  //  void testSaveDischargingInformation() throws Exception {
  //    DischargingInformationRequest request = new DischargingInformationRequest();
  //    request.setPortRotationId(1L);
  //    request.setSynopticTableId(1L);
  //    request.setDischargeDetails(getLD());
  //    request.setCargoToBeDischarged(getPC());
  //    String correlationId = "1";
  //    Mockito.when(infoBuilderService.saveDataAsync(Mockito.any())).thenReturn(getDISR());
  //    doNothing()
  //        .when(loadableStudyService)
  //        .saveLoadingInfoToSynopticalTable(Mockito.anyLong(), Mockito.any(), Mockito.any());
  //
  // Mockito.when(this.loadingPlanGrpcService.updateDischargeQuantityCargoDetails(Mockito.anyList()))
  //        .thenReturn(getRS());
  //    Mockito.when(this.loadingPlanGrpcService.getActiveVoyageDetails(Mockito.anyLong()))
  //        .thenReturn(getVR());
  //    Mockito.when(
  //            this.dischargeInformationGrpcService.getDischargeInfoRpc(
  //                Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
  //        .thenReturn(getDI());
  //    Mockito.when(
  //            vesselInfoService.getRulesByVesselIdAndSectionId(
  //                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any()))
  //        .thenReturn(getRP());
  //    Mockito.when(
  //            this.infoBuilderService.buildDischargeDetailFromMessage(
  //                Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
  //        .thenReturn(getLD());
  //    Mockito.when(
  //            this.infoBuilderService.buildDischargeRatesFromMessage(Mockito.any(),
  // Mockito.any()))
  //        .thenReturn(getDR());
  //
  // Mockito.when(this.loadingInformationService.getMasterBerthDetailsByPortId(Mockito.anyLong()))
  //        .thenReturn(getLBD());
  //    Mockito.when(this.infoBuilderService.buildDischargeBerthsFromMessage(Mockito.anyList()))
  //        .thenReturn(getLBD());
  //    Mockito.when(
  //            this.infoBuilderService.buildDischargeMachinesFromMessage(
  //                Mockito.anyList(), Mockito.anyLong()))
  //        .thenReturn(getCMU());
  //    Mockito.when(this.loadingInformationService.getLoadingStagesAndMasters(Mockito.any()))
  //        .thenReturn(getLSS());
  //
  // Mockito.when(this.infoBuilderService.buildDischargeSequencesAndDelayFromMessage(Mockito.any()))
  //        .thenReturn(getLSSS());
  //    Mockito.when(this.infoBuilderService.buildDischargeCowPlan(Mockito.any(), Mockito.any()))
  //        .thenReturn(getCP());
  //    doNothing()
  //        .when(infoBuilderService)
  //        .buildPostDischargeRates(Mockito.any(), Mockito.any(), Mockito.any());
  //    Mockito.when(
  //            this.loadingPlanGrpcService.fetchPortWiseCargoDetails(
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.any()))
  //        .thenReturn(getCVTD());
  //    Mockito.when(
  //            this.loadingInformationService.getDischargePlanCargoDetailsByPort(
  //                Mockito.anyLong(),
  //                Mockito.anyLong(),
  //                Mockito.any(),
  //                Mockito.anyLong(),
  //                Mockito.anyLong()))
  //        .thenReturn(getLDQCD());
  //    Mockito.when(
  //
  // this.loadingInformationService.getLoadingInfoCargoDetailsByPattern(Mockito.anyLong()))
  //        .thenReturn(getLISR());
  //    var response =
  //        this.dischargeInformationService.saveDischargingInformation(request, correlationId);
  //    assertEquals(1L, response.getDischargingInformation().getDischargeInfoId());
  //  }

  private com.cpdss.gateway.domain.dischargeplan.DischargeInformation getDII() {
    com.cpdss.gateway.domain.dischargeplan.DischargeInformation dischargeInformation =
        new com.cpdss.gateway.domain.dischargeplan.DischargeInformation();
    dischargeInformation.setDischargeInfoId(1L);
    return dischargeInformation;
  }

  private Common.ResponseStatus getRS() {
    Common.ResponseStatus responseStatus = Common.ResponseStatus.newBuilder().build();
    return responseStatus;
  }

  private PlannedCargo getPC() {
    PlannedCargo plannedCargo = new PlannedCargo();
    plannedCargo.setDischargeSlopTanksFirst(true);
    plannedCargo.setDischargeQuantityCargoDetails(getLDQCD());
    return plannedCargo;
  }

  private DischargingInfoSaveResponse getDISR() {
    DischargingInfoSaveResponse response =
        DischargingInfoSaveResponse.newBuilder()
            .setDischargingInfoId(1L)
            .setPortRotationId(1L)
            .setSynopticalTableId(1L)
            .setVesselId(1L)
            .setVoyageId(1L)
            .build();
    return response;
  }

  @Test
  void testSaveDischargingInformationException1() {
    DischargingInformationRequest request = new DischargingInformationRequest();
    request.setDischargeInfoId(1L);
    String correlationId = "1";
    try {
      var response =
          this.dischargeInformationService.saveDischargingInformation(request, correlationId);
    } catch (NoTransactionException | GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveDischargingInformationException2() {
    DischargingInformationRequest request = new DischargingInformationRequest();
    request.setPortRotationId(1L);
    request.setDischargeDetails(getLD());
    request.setSynopticTableId(1L);
    request.setCargoToBeDischarged(getPC());
    String correlationId = "1";
    try {
      Mockito.when(infoBuilderService.saveDataAsync(Mockito.any())).thenReturn(getDISRNS());
      doNothing()
          .when(loadableStudyService)
          .saveLoadingInfoToSynopticalTable(Mockito.anyLong(), Mockito.any(), Mockito.any());
      Mockito.when(
              this.loadingPlanGrpcService.updateDischargeQuantityCargoDetails(Mockito.anyList()))
          .thenReturn(getRS());
      var response =
          this.dischargeInformationService.saveDischargingInformation(request, correlationId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private DischargingInfoSaveResponse getDISRNS() {
    DischargingInfoSaveResponse response = DischargingInfoSaveResponse.newBuilder().build();
    return response;
  }

  @Test
  void testGenerateDischargingPlan() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Mockito.when(dischargePlanServiceBlockingStub.generateDischargePlan(Mockito.any()))
        .thenReturn(getDPAR());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.dischargeInformationService.generateDischargingPlan(vesselId, voyageId, infoId);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargePlanAlgoRequest getDPAR() {
    DischargePlanAlgoRequest dischargePlanAlgoRequest =
        DischargePlanAlgoRequest.newBuilder()
            .setProcessId("1")
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return dischargePlanAlgoRequest;
  }

  @Test
  void testGenerateDischargingPlanException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Mockito.when(dischargePlanServiceBlockingStub.generateDischargePlan(Mockito.any()))
        .thenReturn(getDPARNS());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.dischargeInformationService.generateDischargingPlan(vesselId, voyageId, infoId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargePlanAlgoRequest getDPARNS() {
    DischargePlanAlgoRequest dischargePlanAlgoRequest =
        DischargePlanAlgoRequest.newBuilder().build();
    return dischargePlanAlgoRequest;
  }

  @Test
  void testSaveDischargingPlan() throws GenericServiceException {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    DischargingPlanAlgoRequest dischargingPlanAlgoRequest = new DischargingPlanAlgoRequest();
    dischargingPlanAlgoRequest.setProcessId("1");
    String requestJsonString = "1";
    Mockito.when(this.loadingPlanGrpcService.saveJson(Mockito.any())).thenReturn(getSR());
    doNothing()
        .when(dischargingSequenceService)
        .buildDischargingPlanSaveRequest(
            Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
    Mockito.when(dischargePlanServiceBlockingStub.saveDischargingPlan(Mockito.any()))
        .thenReturn(getDPSR());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    var response =
        this.dischargeInformationService.saveDischargingPlan(
            vesselId, voyageId, infoId, dischargingPlanAlgoRequest, requestJsonString);
    assertEquals("1", response.getProcessId());
  }

  private DischargingPlanSaveResponse getDPSR() {
    DischargingPlanSaveResponse response =
        DischargingPlanSaveResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  private com.cpdss.common.generated.LoadableStudy.StatusReply getSR() {
    com.cpdss.common.generated.LoadableStudy.StatusReply reply =
        com.cpdss.common.generated.LoadableStudy.StatusReply.newBuilder()
            .setStatus(SUCCESS)
            .build();
    return reply;
  }

  @Test
  void testSaveDischargingPlanException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    DischargingPlanAlgoRequest dischargingPlanAlgoRequest = new DischargingPlanAlgoRequest();
    String requestJsonString = "1";
    Mockito.when(this.loadingPlanGrpcService.saveJson(Mockito.any())).thenReturn(getSR());
    doNothing()
        .when(dischargingSequenceService)
        .buildDischargingPlanSaveRequest(
            Mockito.any(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
    Mockito.when(dischargePlanServiceBlockingStub.saveDischargingPlan(Mockito.any()))
        .thenReturn(getDPSRNS());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.dischargeInformationService.saveDischargingPlan(
              vesselId, voyageId, infoId, dischargingPlanAlgoRequest, requestJsonString);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargingPlanSaveResponse getDPSRNS() {
    DischargingPlanSaveResponse response = DischargingPlanSaveResponse.newBuilder().build();
    return response;
  }

  @Test
  void testSaveJson() {
    Long referenceId = 1L;
    Long jsonTypeId = 1L;
    String json = "1";
    Mockito.when(this.loadingPlanGrpcService.saveJson(Mockito.any())).thenReturn(getSR());
    var response = this.dischargeInformationService.saveJson(referenceId, jsonTypeId, json);
    assertEquals(SUCCESS, response.getStatus());
  }

  @Test
  void testDischargeInfoStatusCheck() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    String processId = "1";
    Integer conditionType = 1;
    Mockito.when(dischargePlanServiceBlockingStub.dischargeInfoStatusCheck(Mockito.any()))
        .thenReturn(getDISRR());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.dischargeInformationService.dischargeInfoStatusCheck(
              vesselId, voyageId, infoId, processId, conditionType);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply getDISRR() {
    com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply reply =
        com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply.newBuilder()
            .setDischargeInfoStatusLastModifiedTime("1")
            .setDischargeInfoStatusId(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testDischargeInfoStatusCheckException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    String processId = "1";
    Integer conditionType = 1;
    Mockito.when(dischargePlanServiceBlockingStub.dischargeInfoStatusCheck(Mockito.any()))
        .thenReturn(getDISRRNS());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.dischargeInformationService.dischargeInfoStatusCheck(
              vesselId, voyageId, infoId, processId, conditionType);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply getDISRRNS() {
    com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply reply =
        com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply.newBuilder().build();
    return reply;
  }

  @Test
  void testSaveDischargingInfoStatus() {
    AlgoStatusRequest request = new AlgoStatusRequest();
    request.setDischargingInfoStatusId(1L);
    request.setProcessId("1");
    String correlationId = "1";
    Mockito.when(dischargePlanServiceBlockingStub.saveDischargingPlanAlgoStatus(Mockito.any()))
        .thenReturn(getASR());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.dischargeInformationService.saveDischargingInfoStatus(request, correlationId);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private com.cpdss.common.generated.LoadableStudy.AlgoStatusReply getASR() {
    com.cpdss.common.generated.LoadableStudy.AlgoStatusReply reply =
        com.cpdss.common.generated.LoadableStudy.AlgoStatusReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testSaveDischargingInfoStatusException() {
    AlgoStatusRequest request = new AlgoStatusRequest();
    request.setDischargingInfoStatusId(1L);
    request.setProcessId("1");
    String correlationId = "1";
    Mockito.when(dischargePlanServiceBlockingStub.saveDischargingPlanAlgoStatus(Mockito.any()))
        .thenReturn(getASR());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.dischargeInformationService.saveDischargingInfoStatus(request, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private com.cpdss.common.generated.LoadableStudy.AlgoStatusReply getASRNS() {
    com.cpdss.common.generated.LoadableStudy.AlgoStatusReply reply =
        com.cpdss.common.generated.LoadableStudy.AlgoStatusReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
            .build();
    return reply;
  }

  @Test
  void testGetDischargingSequence() throws GenericServiceException {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Mockito.when(dischargePlanServiceBlockingStub.getDischargingSequences(Mockito.any()))
        .thenReturn(getDSR());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    doNothing()
        .when(dischargingSequenceService)
        .buildDischargingSequence(Mockito.anyLong(), Mockito.any(), Mockito.any());
    var response =
        this.dischargeInformationService.getDischargingSequence(vesselId, voyageId, infoId);
  }

  private DischargeSequenceReply getDSR() {
    DischargeSequenceReply reply =
        DischargeSequenceReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetDischargingSequenceException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Mockito.when(dischargePlanServiceBlockingStub.getDischargingSequences(Mockito.any()))
        .thenReturn(getDSRNS());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.dischargeInformationService.getDischargingSequence(vesselId, voyageId, infoId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargeSequenceReply getDSRNS() {
    DischargeSequenceReply reply =
        DischargeSequenceReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("200").build())
            .build();
    return reply;
  }

  @Test
  void testGetDischargingInfoAlgoErrors() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Integer conditionType = 1;
    Mockito.when(this.dischargePlanServiceBlockingStub.getDischargingInfoAlgoErrors(Mockito.any()))
        .thenReturn(getAER());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.dischargeInformationService.getDischargingInfoAlgoErrors(
              vesselId, voyageId, infoId, conditionType);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private com.cpdss.common.generated.LoadableStudy.AlgoErrorReply getAER() {
    List<String> list1 = new ArrayList<>();
    list1.add("1");
    List<com.cpdss.common.generated.LoadableStudy.AlgoErrors> list = new ArrayList<>();
    com.cpdss.common.generated.LoadableStudy.AlgoErrors algoErrors =
        com.cpdss.common.generated.LoadableStudy.AlgoErrors.newBuilder()
            .addAllErrorMessages(list1)
            .setErrorHeading("1")
            .build();
    list.add(algoErrors);
    com.cpdss.common.generated.LoadableStudy.AlgoErrorReply reply =
        com.cpdss.common.generated.LoadableStudy.AlgoErrorReply.newBuilder()
            .addAllAlgoErrors(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetDischargingInfoAlgoErrorsException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    Integer conditionType = 1;
    Mockito.when(this.dischargePlanServiceBlockingStub.getDischargingInfoAlgoErrors(Mockito.any()))
        .thenReturn(getAERNS());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    try {
      var response =
          this.dischargeInformationService.getDischargingInfoAlgoErrors(
              vesselId, voyageId, infoId, conditionType);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private com.cpdss.common.generated.LoadableStudy.AlgoErrorReply getAERNS() {
    com.cpdss.common.generated.LoadableStudy.AlgoErrorReply reply =
        com.cpdss.common.generated.LoadableStudy.AlgoErrorReply.newBuilder().build();
    return reply;
  }

  @Test
  void testGetDischargingPlanRules() throws GenericServiceException {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long dischargeInfoId = 1L;
    Mockito.when(this.dischargeInformationGrpcService.saveOrGetDischargingPlanRules(Mockito.any()))
        .thenReturn(getRR());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    var response =
        this.dischargeInformationService.getDischargingPlanRules(
            vesselId, voyageId, dischargeInfoId);
    assertEquals("1", response.getPlan().get(0).getHeader());
  }

  private RuleResponse getRR() {
    RuleResponse response = new RuleResponse();
    response.setPlan(getLRP());
    return response;
  }

  @Test
  void testSaveDischargingPlanRules() throws GenericServiceException {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long infoId = 1L;
    RuleRequest ruleRequest = new RuleRequest();
    Mockito.when(this.dischargeInformationGrpcService.saveOrGetDischargingPlanRules(Mockito.any()))
        .thenReturn(getRP());
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargePlanServiceBlockingStub",
        this.dischargePlanServiceBlockingStub);
    var response =
        this.dischargeInformationService.saveDischargingPlanRules(
            vesselId, voyageId, infoId, ruleRequest);
    assertEquals("1", response.getPlan().get(0).getHeader());
  }
}
