/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.discharge_plan.*;
import com.cpdss.common.generated.discharge_plan.CowPlan;
import com.cpdss.common.generated.discharge_plan.DischargeRates;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.gateway.domain.RulePlans;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.dischargeplan.*;
import com.cpdss.gateway.domain.dischargeplan.DischargeInformation;
import com.cpdss.gateway.domain.loadingplan.*;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import com.cpdss.gateway.utility.AdminRuleValueExtract;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {DischargeInformationBuilderService.class})
public class DischargeInformationBuilderServiceTest {

  @Autowired DischargeInformationBuilderService dischargeInformationBuilderService;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean VesselInfoService vesselInfoService;

  @MockBean LoadingPlanGrpcService loadingPlanGrpcService;

  @MockBean
  DischargeInformationServiceGrpc.DischargeInformationServiceBlockingStub dischargeInfoServiceStub;

  private static final String SUCCESS = "SUCCESS";

  @Test
  void testBuildDischargeDetailFromMessage() throws GenericServiceException {
    DischargeDetails var1 =
        DischargeDetails.newBuilder()
            .setStartTime("1")
            .setTrimAllowed(
                LoadingPlanModels.TrimAllowed.newBuilder()
                    .setInitialTrim("1")
                    .setMaximumTrim("1")
                    .setFinalTrim("1")
                    .build())
            .build();
    Long portId = 1L;
    Long portRId = 1L;
    AdminRuleValueExtract extract = AdminRuleValueExtract.builder().build();
    Mockito.when(
            loadingPlanGrpcService.fetchSynopticRecordForPortRotation(
                Mockito.anyLong(), Mockito.any()))
        .thenReturn(getLSR());
    ReflectionTestUtils.setField(
        dischargeInformationBuilderService, "loadingPlanGrpcService", this.loadingPlanGrpcService);
    var response =
        this.dischargeInformationBuilderService.buildDischargeDetailFromMessage(
            var1, portId, portRId, extract);
    assertEquals("1", response.getTimeOfSunrise());
  }

  private LoadableStudy.LoadingSynopticResponse getLSR() {
    LoadableStudy.LoadingSynopticResponse response =
        LoadableStudy.LoadingSynopticResponse.newBuilder()
            .setTimeOfSunrise("1")
            .setTimeOfSunset("1")
            .build();
    return response;
  }

  @Test
  void testBuildDischargeDetailFromMessage1() throws GenericServiceException {
    DischargeDetails var1 =
        DischargeDetails.newBuilder()
            .setStartTime("1")
            .setTrimAllowed(
                LoadingPlanModels.TrimAllowed.newBuilder()
                    .setInitialTrim("1")
                    .setMaximumTrim("1")
                    .setFinalTrim("1")
                    .build())
            .build();
    Long portId = 1L;
    Long portRId = 1L;
    AdminRuleValueExtract extract = AdminRuleValueExtract.builder().build();
    Mockito.when(
            loadingPlanGrpcService.fetchSynopticRecordForPortRotation(
                Mockito.anyLong(), Mockito.any()))
        .thenReturn(getLSRNS());
    Mockito.when(this.loadingPlanGrpcService.fetchPortDetailByPortId(Mockito.anyLong()))
        .thenReturn(getPD());
    ReflectionTestUtils.setField(
        dischargeInformationBuilderService, "loadingPlanGrpcService", this.loadingPlanGrpcService);
    var response =
        this.dischargeInformationBuilderService.buildDischargeDetailFromMessage(
            var1, portId, portRId, extract);
    assertEquals("1", response.getTimeOfSunrise());
  }

  private LoadableStudy.LoadingSynopticResponse getLSRNS() {
    LoadableStudy.LoadingSynopticResponse response =
        LoadableStudy.LoadingSynopticResponse.newBuilder().build();
    return response;
  }

  private PortInfo.PortDetail getPD() {
    PortInfo.PortDetail detail =
        PortInfo.PortDetail.newBuilder().setSunriseTime("1").setSunsetTime("1").build();
    return detail;
  }

  @Test
  void testBuildDischargeRatesFromMessage() {
    com.cpdss.common.generated.discharge_plan.DischargeRates var1 =
        DischargeRates.newBuilder()
            .setInitialDischargeRate("1")
            .setMaxDischargeRate("1")
            .setMinBallastRate("1")
            .setMaxBallastRate("1")
            .setId(1L)
            .build();
    AdminRuleValueExtract extract = AdminRuleValueExtract.builder().build();
    var response =
        this.dischargeInformationBuilderService.buildDischargeRatesFromMessage(var1, extract);
    assertEquals(new BigDecimal(1), response.getInitialDischargingRate());
  }

  @Test
  void testBuildDischargeRatesFromMessage1() {
    com.cpdss.common.generated.discharge_plan.DischargeRates var1 =
        DischargeRates.newBuilder().setId(1L).build();
    AdminRuleValueExtract extract = AdminRuleValueExtract.builder().plan(getRP()).build();
    var response =
        this.dischargeInformationBuilderService.buildDischargeRatesFromMessage(var1, extract);
    assertEquals(1L, response.getId());
  }

  @Test
  void testBuildDischargeBerthsFromMessage() {
    List<DischargeBerths> var1 = new ArrayList<>();
    DischargeBerths berths =
        DischargeBerths.newBuilder()
            .setId(1L)
            .setDischargeInfoId(1L)
            .setBerthId(1L)
            .setDepth("1")
            .setSeaDraftLimitation("1")
            .setAirDraftLimitation("1")
            .setMaxManifoldHeight("1")
            .setSpecialRegulationRestriction("1")
            .setItemsToBeAgreedWith("1")
            .setHoseConnections("1")
            .setLineDisplacement("1")
            .setAirPurge(true)
            .setCargoCirculation(true)
            .setMaxManifoldPressure("1")
            .build();
    var1.add(berths);
    var response = this.dischargeInformationBuilderService.buildDischargeBerthsFromMessage(var1);
    assertEquals("1", response.get(0).getHoseConnections());
  }

  @Test
  void testBuildDischargeMachinesFromMessage() {
    List<LoadingPlanModels.LoadingMachinesInUse> var1 = new ArrayList<>();
    LoadingPlanModels.LoadingMachinesInUse machinesInUse =
        LoadingPlanModels.LoadingMachinesInUse.newBuilder()
            .setId(1L)
            .setMachineId(1L)
            .setLoadingInfoId(1L)
            .setMachineType(Common.MachineType.EMPTY)
            .setCapacity("1")
            .setIsUsing(true)
            .build();
    var1.add(machinesInUse);
    Long vesselId = 1L;
    Mockito.when(vesselInfoService.getVesselPumpsFromVesselInfo(Mockito.anyLong()))
        .thenReturn(getVPR());
    var response =
        this.dischargeInformationBuilderService.buildDischargeMachinesFromMessage(var1, vesselId);
    assertEquals(1L, response.getDischargeMachinesInUses().get(0).getLoadingInfoId());
  }

  private VesselInfo.VesselPumpsResponse getVPR() {
    List<VesselInfo.TankType> list4 = new ArrayList<>();
    VesselInfo.TankType tankType =
        VesselInfo.TankType.newBuilder().setId(1L).setTypeName("1").build();
    list4.add(tankType);
    List<VesselInfo.VesselComponent> list3 = new ArrayList<>();
    VesselInfo.VesselComponent component =
        VesselInfo.VesselComponent.newBuilder()
            .setId(1L)
            .setComponentName("1")
            .setComponentCode("1")
            .setVesselId(1L)
            .setComponentType(1L)
            .build();
    list3.add(component);
    List<VesselInfo.VesselComponent> list2 = new ArrayList<>();
    VesselInfo.VesselComponent vesselComponent =
        VesselInfo.VesselComponent.newBuilder()
            .setId(1L)
            .setVesselId(1L)
            .setComponentCode("1")
            .setComponentName("1")
            .setComponentType(1L)
            .build();
    list2.add(vesselComponent);
    List<VesselInfo.VesselPump> list1 = new ArrayList<>();
    VesselInfo.VesselPump vesselPump =
        VesselInfo.VesselPump.newBuilder().setPumpTypeId(1L).setPumpCapacity("1").build();
    list1.add(vesselPump);
    List<VesselInfo.PumpType> list = new ArrayList<>();
    VesselInfo.PumpType pumpType = VesselInfo.PumpType.newBuilder().build();
    list.add(pumpType);
    VesselInfo.VesselPumpsResponse response =
        VesselInfo.VesselPumpsResponse.newBuilder()
            .addAllTankType(list4)
            .addAllVesselBottomLine(list3)
            .addAllVesselManifold(list2)
            .addAllVesselPump(list1)
            .addAllPumpType(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  @Test
  void testBuildDischargeMachinesFromMessageFailed() {
    List<LoadingPlanModels.LoadingMachinesInUse> var1 = new ArrayList<>();
    LoadingPlanModels.LoadingMachinesInUse machinesInUse =
        LoadingPlanModels.LoadingMachinesInUse.newBuilder()
            .setId(1L)
            .setMachineId(1L)
            .setLoadingInfoId(1L)
            .setMachineType(Common.MachineType.EMPTY)
            .setCapacity("1")
            .setIsUsing(true)
            .build();
    var1.add(machinesInUse);
    Long vesselId = 1L;
    Mockito.when(vesselInfoService.getVesselPumpsFromVesselInfo(Mockito.anyLong()))
        .thenReturn(getVPRNS());
    var response =
        this.dischargeInformationBuilderService.buildDischargeMachinesFromMessage(var1, vesselId);
  }

  private VesselInfo.VesselPumpsResponse getVPRNS() {
    VesselInfo.VesselPumpsResponse response =
        VesselInfo.VesselPumpsResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  @Test
  void testBuildDischargeSequencesAndDelayFromMessage() {
    List<Long> list2 = new ArrayList<>();
    list2.add(1L);
    List<DischargeDelays> list1 = new ArrayList<>();
    DischargeDelays dischargeDelays =
        DischargeDelays.newBuilder()
            .setDuration("1")
            .setQuantity("1")
            .setSequenceNo(1L)
            .setDischargeInfoId(1L)
            .addAllReasonForDelayIds(list2)
            .build();
    list1.add(dischargeDelays);
    List<LoadingPlanModels.DelayReasons> list = new ArrayList<>();
    LoadingPlanModels.DelayReasons delayReasons =
        LoadingPlanModels.DelayReasons.newBuilder().build();
    list.add(delayReasons);
    DischargeDelay dischargeDelay =
        DischargeDelay.newBuilder().addAllDelays(list1).addAllReasons(list).build();
    var loadingSequences =
        this.dischargeInformationBuilderService.buildDischargeSequencesAndDelayFromMessage(
            dischargeDelay);
    assertEquals(1L, loadingSequences.getDischargingDelays().get(0).getSequenceNo());
  }

  @Test
  void testBuildDischargeCowPlan() {
    List<Long> list1 = new ArrayList<>();
    list1.add(1L);
    List<CowTankDetails> list = new ArrayList<>();
    CowTankDetails details =
        CowTankDetails.newBuilder()
            .addAllTankIds(list1)
            .setCowType(Common.COW_TYPE.TOP_COW)
            .build();
    list.add(details);
    CowPlan cowPlan =
        CowPlan.newBuilder()
            .setCowOptionTypeValue(1)
            .addAllCowTankDetails(list)
            .setCowTankPercent("1")
            .setCowStartTime("1")
            .setCowEndTime("1")
            .setEstCowDuration("1")
            .setCowWithCargoEnable(true)
            .setTrimCowMin("1")
            .setTrimCowMax("1")
            .build();
    AdminRuleValueExtract extract = AdminRuleValueExtract.builder().build();
    var response = this.dischargeInformationBuilderService.buildDischargeCowPlan(cowPlan, extract);
    assertEquals("1", response.getCowTrimMax());
  }

  @Test
  void testBuildDischargeCowPlan1() {
    List<Long> list3 = new ArrayList<>();
    list3.add(1L);
    List<CargoForCow> list2 = new ArrayList<>();
    CargoForCow cargoForCow =
        CargoForCow.newBuilder()
            .addAllTankIds(list3)
            .setCargoId(1L)
            .setCargoNominationId(1L)
            .setWashingCargoId(1L)
            .setWashingCargoNominationId(1L)
            .build();
    list2.add(cargoForCow);
    List<Long> list1 = new ArrayList<>();
    list1.add(1L);
    List<CowTankDetails> list = new ArrayList<>();
    CowTankDetails details =
        CowTankDetails.newBuilder()
            .addAllCargoForCow(list2)
            .addAllTankIds(list1)
            .setCowType(Common.COW_TYPE.CARGO)
            .build();
    list.add(details);
    CowPlan cowPlan =
        CowPlan.newBuilder()
            .setCowOptionTypeValue(1)
            .addAllCowTankDetails(list)
            .setCowTankPercent("1")
            .setCowStartTime("1")
            .setCowEndTime("1")
            .setEstCowDuration("1")
            .setCowWithCargoEnable(true)
            .build();
    AdminRuleValueExtract extract = AdminRuleValueExtract.builder().plan(getRP()).build();
    var response = this.dischargeInformationBuilderService.buildDischargeCowPlan(cowPlan, extract);
    assertEquals(1L, response.getCargoCow().get(0).getWashingCargoId());
  }

  private List<RulePlans> getRP() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String obj =
          "{\"plan\":[{\"header\":\"Vessel Stability Rules\",\"rules\":[{\"id\":null,\"enable\":true,\"disable\":null,\"displayInSettings\":true,\"ruleType\":\"Preferable\",\"inputs\":[{\"prefix\":\"Trim at COW should be min\",\"defaultValue\":\"4\",\"type\":\"Number\",\"max\":\"6\",\"min\":\"4\",\"value\":null,\"suffix\":null,\"id\":null,\"ruleDropDownMaster\":null,\"isMandatory\":true},{\"prefix\":\" m and max\",\"defaultValue\":\"6\",\"type\":\"Number\",\"max\":\"6\",\"min\":\"4\",\"value\":null,\"suffix\":\"m\",\"id\":null,\"ruleDropDownMaster\":null,\"isMandatory\":true}],\"ruleTemplateId\":\"906\",\"vesselRuleXId\":null,\"isHardRule\":false,\"numericPrecision\":3,\"numericScale\":2}]}]}";
      RuleResponse response = objectMapper.readValue(obj, RuleResponse.class);
      return response.getPlan();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  //    @Test
  //      void testSaveDataAsync() {
  //        DischargingInformationRequest request = new DischargingInformationRequest();
  //        request.setDischargeInfoId(1L);
  //        request.setSynopticTableId(1L);
  //        request.setIsDischargeInfoComplete(true);
  //        request.setDischargeDetails(getLD());
  //        request.setDischargeStages(getLSRR());
  //        request.setDischargeRates(getDR());
  //        request.setDischargingBerths(getDB());
  //        request.setDischargingDelays(getDD());
  //        request.setDischargingMachineries(getLM());
  //        request.setCowPlan(getCP());
  //        request.setPostDischargeStageTime(getPDST());
  //        request.setCargoToBeDischarged(getPC());
  //        request.setVesselId(1L);
  //        request.setVoyageId(1L);
  //        request.setPortRotationId(1L);
  //     //
  // Mockito.when(dischargeInfoServiceStub.saveDischargingInformation(Mockito.any())).thenReturn(getR());
  //
  // ReflectionTestUtils.setField(dischargeInformationBuilderService,"dischargeInfoServiceStub",this.dischargeInfoServiceStub);
  //        try {
  //            var reply = this.dischargeInformationBuilderService.saveDataAsync(request);
  //        } catch (InterruptedException e) {
  //            e.printStackTrace();
  //        } catch (ExecutionException e) {
  //            e.printStackTrace();
  //        }
  //    }

  private DischargingInfoSaveResponse getR() {
    DischargingInfoSaveResponse response =
        DischargingInfoSaveResponse.newBuilder().setDischargingInfoId(1L).build();
    return response;
  }

  private PlannedCargo getPC() {
    PlannedCargo plannedCargo = new PlannedCargo();
    plannedCargo.setDischargeCommingledCargoSeparately(true);
    plannedCargo.setDischargeSlopTanksFirst(true);
    return plannedCargo;
  }

  private PostDischargeStage getPDST() {
    PostDischargeStage postDischargeStage = new PostDischargeStage();
    postDischargeStage.setFinalStrippingTime(new BigDecimal(1));
    postDischargeStage.setFreshOilWashingTime(new BigDecimal(1));
    postDischargeStage.setSlopDischargingTime(new BigDecimal(1));
    postDischargeStage.setDryCheckTime(new BigDecimal(1));
    return postDischargeStage;
  }

  private com.cpdss.gateway.domain.dischargeplan.CowPlan getCP() {
    com.cpdss.gateway.domain.dischargeplan.CowPlan cowPlan =
        new com.cpdss.gateway.domain.dischargeplan.CowPlan();
    cowPlan.setCowOption(1);
    cowPlan.setAllCow(getL());
    cowPlan.setBottomCow(getL());
    cowPlan.setTopCow(getL());
    cowPlan.setCargoCow(getLCCD());
    cowPlan.setCowDuration("1");
    cowPlan.setCowEnd("1");
    cowPlan.setCowPercentage("1");
    cowPlan.setCowStart("1");
    cowPlan.setCowTrimMax("1");
    cowPlan.setCowTrimMin("1");
    cowPlan.setNeedFreshCrudeStorage(true);
    cowPlan.setNeedFlushingOil(true);
    cowPlan.setWashTanksWithDifferentCargo(true);
    return cowPlan;
  }

  private List<CargoForCowDetails> getLCCD() {
    List<CargoForCowDetails> list = new ArrayList<>();
    CargoForCowDetails cargoForCowDetails = new CargoForCowDetails();
    cargoForCowDetails.setCargoId(1L);
    cargoForCowDetails.setCargoNominationId(1L);
    cargoForCowDetails.setWashingCargoId(1L);
    cargoForCowDetails.setWashingCargoNominationId(1L);
    cargoForCowDetails.setTankIds(getL());
    list.add(cargoForCowDetails);
    return list;
  }

  private List<LoadingMachinesInUse> getLM() {
    List<LoadingMachinesInUse> list = new ArrayList<>();
    LoadingMachinesInUse loadingMachinesInUse = new LoadingMachinesInUse();
    loadingMachinesInUse.setCapacity(new BigDecimal(1));
    loadingMachinesInUse.setId(1L);
    loadingMachinesInUse.setMachineId(1L);
    loadingMachinesInUse.setMachineTypeId(1);
    loadingMachinesInUse.setIsUsing(true);
    list.add(loadingMachinesInUse);
    return list;
  }

  private List<DischargingDelays> getDD() {
    List<DischargingDelays> list = new ArrayList<>();
    DischargingDelays dischargingDelays = new DischargingDelays();
    dischargingDelays.setCargoId(1L);
    dischargingDelays.setDuration(new BigDecimal(1));
    dischargingDelays.setId(1L);
    dischargingDelays.setQuantity(new BigDecimal(1));
    dischargingDelays.setCargoNominationId(1L);
    dischargingDelays.setReasonForDelayIds(getL());
    dischargingDelays.setSequenceNo(1L);
    list.add(dischargingDelays);
    return list;
  }

  private List<Long> getL() {
    List<Long> lo = new ArrayList<>();
    lo.add(1L);
    return lo;
  }

  private List<BerthDetails> getDB() {
    List<BerthDetails> list = new ArrayList<>();
    BerthDetails berthDetails = new BerthDetails();
    berthDetails.setAirDraftLimitation(new BigDecimal(1));
    berthDetails.setHoseConnections("1");
    berthDetails.setBerthId(1L);
    berthDetails.setLoadingBerthId(1L);
    berthDetails.setDischargingBerthId(1L);
    berthDetails.setMaxManifoldHeight(new BigDecimal(1));
    berthDetails.setMaxManifoldPressure("1");
    berthDetails.setRegulationAndRestriction("1");
    berthDetails.setSeaDraftLimitation(new BigDecimal(1));
    berthDetails.setItemsToBeAgreedWith("1");
    berthDetails.setMaxShipDepth(new BigDecimal(1));
    berthDetails.setLineDisplacement("1");
    berthDetails.setAirPurge(true);
    berthDetails.setCargoCirculation(true);
    list.add(berthDetails);
    return list;
  }

  private com.cpdss.gateway.domain.dischargeplan.DischargeRates getDR() {
    com.cpdss.gateway.domain.dischargeplan.DischargeRates rates =
        new com.cpdss.gateway.domain.dischargeplan.DischargeRates();
    rates.setMaxBallastRate(new BigDecimal(1));
    rates.setMaxDischargingRate(new BigDecimal(1));
    rates.setMinBallastRate(new BigDecimal(1));
    rates.setInitialDischargingRate(new BigDecimal(1));
    return rates;
  }

  private LoadingStagesRequest getLSRR() {
    LoadingStagesRequest request = new LoadingStagesRequest();
    request.setStageDuration(getSD());
    request.setStageOffset(getSO());
    request.setTrackGradeSwitch(true);
    request.setTrackStartEndStage(true);
    return request;
  }

  private StageOffset getSO() {
    StageOffset stageOffset = new StageOffset();
    stageOffset.setId(1L);
    stageOffset.setStageOffsetVal(1L);
    return stageOffset;
  }

  private StageDuration getSD() {
    StageDuration stageDuration = new StageDuration();
    stageDuration.setId(1L);
    stageDuration.setDuration(1L);
    return stageDuration;
  }

  private LoadingDetails getLD() {
    LoadingDetails loadingDetails = new LoadingDetails();
    loadingDetails.setStartTime("1");
    loadingDetails.setTimeOfSunrise("1");
    loadingDetails.setTimeOfSunset("1");
    loadingDetails.setTrimAllowed(getTA());
    return loadingDetails;
  }

  private TrimAllowed getTA() {
    TrimAllowed trimAllowed = new TrimAllowed();
    trimAllowed.setFinalTrim(new BigDecimal(1));
    trimAllowed.setInitialTrim(new BigDecimal(1));
    trimAllowed.setMaximumTrim(new BigDecimal(1));
    return trimAllowed;
  }

  @Test
  void testBuildDischargingDetails() {
    com.cpdss.gateway.domain.loadingplan.LoadingDetails dischargingDetails = new LoadingDetails();
    dischargingDetails.setStartTime("1");
    dischargingDetails.setTimeOfSunset("1");
    dischargingDetails.setTimeOfSunrise("1");
    dischargingDetails.setTrimAllowed(getTA());
    Long dischargingInfoId = 1L;
    var response =
        this.dischargeInformationBuilderService.buildDischargingDetails(
            dischargingDetails, dischargingInfoId);
    assertEquals("1", response.getTimeOfSunrise());
  }

  @Test
  void testBuildDischargingStage() {
    DischargingInformationRequest request = new DischargingInformationRequest();
    request.setDischargeStages(getLSRR());
    var reply = this.dischargeInformationBuilderService.buildDischargingStage(request);
    assertEquals(1L, reply.getDuration().getDuration());
  }

  @Test
  void testBuildPostDischargeRates() {
    PostDischargeStageTime var1Rpc =
        PostDischargeStageTime.newBuilder()
            .setSlopDischarging("1")
            .setFinalStripping("1")
            .setFreshOilWashing("1")
            .setTimeForDryCheck("1")
            .build();
    AdminRuleValueExtract extract = AdminRuleValueExtract.builder().build();
    com.cpdss.gateway.domain.dischargeplan.DischargeInformation var2Entity =
        new DischargeInformation();
    this.dischargeInformationBuilderService.buildPostDischargeRates(var1Rpc, extract, var2Entity);
    assertEquals(new BigDecimal(1), var2Entity.getPostDischargeStageTime().getDryCheckTime());
  }

  //    @Test
  //    void testBuildPostDischargeRates1() {
  //        PostDischargeStageTime var1Rpc = PostDischargeStageTime.newBuilder().build();
  //        AdminRuleValueExtract extract = AdminRuleValueExtract.builder().plan(getRP()).build();
  //        com.cpdss.gateway.domain.dischargeplan.DischargeInformation var2Entity = new
  // DischargeInformation();
  //
  // this.dischargeInformationBuilderService.buildPostDischargeRates(var1Rpc,extract,var2Entity);
  //        assertEquals(new
  // BigDecimal(1),var2Entity.getPostDischargeStageTime().getDryCheckTime());
  //    }

}
