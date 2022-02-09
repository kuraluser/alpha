/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.discharge_plan.DischargeRuleReply;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {DischargeInformationBuilderService.class})
public class DischargeInformationBuilderServiceTest {

  @Autowired DischargeInformationBuilderService dischargeInformationBuilderService;

  @MockBean DischargeStageDurationRepository dischargeStageDurationRepository;

  @MockBean DischargeStageMinAmountRepository dischargeStageMinAmountRepository;

  @MockBean ReasonForDelayRepository reasonForDelayRepository;

  @MockBean DischargingDelayRepository dischargingDelayRepository;

  @MockBean DischargingDelayReasonRepository dischargingDelayReasonRepository;

  @MockBean DischargingMachineryInUseRepository dischargingMachineryInUseRepository;

  @MockBean CowPlanDetailRepository cowPlanDetailRepository;
  @MockBean CowWithDifferentCargoRepository cowWithDifferentCargoRepository;

  @Test
  void testBuildLoadingDetailsMessage() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setSunriseTime(Timestamp.valueOf("2021-03-02 11:23:12"));
    dischargeInformation.setSunsetTime(Timestamp.valueOf("2021-03-02 11:23:12"));
    dischargeInformation.setStartTime(LocalTime.NOON);
    dischargeInformation.setInitialTrim(new BigDecimal(1));
    dischargeInformation.setMaximumTrim(new BigDecimal(1));
    dischargeInformation.setFinalTrim(new BigDecimal(1));
    var loadingDetails =
        this.dischargeInformationBuilderService.buildLoadingDetailsMessage(dischargeInformation);
    assertEquals(1L, loadingDetails.getId());
  }

  @Test
  void testbuildLoadingRateMessage() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setInitialDischargingRate(new BigDecimal(1));
    dischargeInformation.setMaxDischargingRate(new BigDecimal(1));
    dischargeInformation.setReducedDischargingRate(new BigDecimal(1));
    dischargeInformation.setMinBallastRate(new BigDecimal(1));
    dischargeInformation.setMaxBallastRate(new BigDecimal(1));
    dischargeInformation.setNoticeTimeForRateReduction(1);
    dischargeInformation.setNoticeTimeForStopDischarging(1);
    dischargeInformation.setLineContentRemaining(new BigDecimal(1));
    dischargeInformation.setMinDischargingRate(new BigDecimal(1));
    var loadingRates =
        this.dischargeInformationBuilderService.buildLoadingRateMessage(dischargeInformation);
    assertEquals("1", loadingRates.getNoticeTimeRateReduction());
  }

  @Test
  void testBuildLoadingBerthsMessage() {
    List<DischargingBerthDetail> list = new ArrayList<>();
    DischargingBerthDetail dischargingBerthDetail = new DischargingBerthDetail();
    dischargingBerthDetail.setId(1L);
    dischargingBerthDetail.setDischargingInformation(getDI());
    dischargingBerthDetail.setBerthXid(1L);
    dischargingBerthDetail.setDepth(new BigDecimal(1));
    dischargingBerthDetail.setSeaDraftLimitation(new BigDecimal(1));
    dischargingBerthDetail.setAirDraftLimitation(new BigDecimal(1));
    dischargingBerthDetail.setMaxManifoldHeight(new BigDecimal(1));
    dischargingBerthDetail.setSpecialRegulationRestriction("1");
    dischargingBerthDetail.setItemToBeAgreed("1");
    dischargingBerthDetail.setHoseConnections("1");
    dischargingBerthDetail.setLineContentDisplacement(new BigDecimal(1));
    list.add(dischargingBerthDetail);
    var loadingBerths = this.dischargeInformationBuilderService.buildLoadingBerthsMessage(list);
    assertEquals(1L, loadingBerths.get(0).getBerthId());
  }

  private DischargeInformation getDI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    return dischargeInformation;
  }

  @Test
  void testBuildLoadingMachineryInUseMessage() {
    List<DischargingMachineryInUse> list = new ArrayList<>();
    DischargingMachineryInUse dischargingMachineryInUse = new DischargingMachineryInUse();
    dischargingMachineryInUse.setId(1L);
    dischargingMachineryInUse.setDischargingInformation(getDI());
    dischargingMachineryInUse.setMachineXid(1L);
    dischargingMachineryInUse.setMachineTypeXid(1);
    dischargingMachineryInUse.setCapacity(new BigDecimal(1));
    dischargingMachineryInUse.setIsUsing(true);
    list.add(dischargingMachineryInUse);
    var loadingMachinesInUse =
        this.dischargeInformationBuilderService.buildLoadingMachineryInUseMessage(list);
    assertEquals(1L, loadingMachinesInUse.get(0).getMachineId());
  }

  @Test
  void testBuildLoadingStageMessage() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setDischargingStagesMinAmount(getDSMA());
    dischargeInformation.setDischargingStagesDuration(getDSD());
    List<DischargingStagesMinAmount> list = new ArrayList<>();
    DischargingStagesMinAmount dischargingStagesMinAmount = new DischargingStagesMinAmount();
    dischargingStagesMinAmount.setId(1L);
    dischargingStagesMinAmount.setMinAmount(1);
    list.add(dischargingStagesMinAmount);
    List<DischargingStagesDuration> list1 = new ArrayList<>();
    DischargingStagesDuration dischargingStagesDuration = new DischargingStagesDuration();
    dischargingStagesDuration.setId(1L);
    dischargingStagesDuration.setDuration(1);
    list1.add(dischargingStagesDuration);
    var loadingStages =
        this.dischargeInformationBuilderService.buildLoadingStageMessage(
            dischargeInformation, list, list1);
    assertEquals(1L, loadingStages.getStageDuration());
  }

  private DischargingStagesMinAmount getDSMA() {
    DischargingStagesMinAmount dischargingStagesMinAmount = new DischargingStagesMinAmount();
    dischargingStagesMinAmount.setId(1L);
    return dischargingStagesMinAmount;
  }

  private DischargingStagesDuration getDSD() {
    DischargingStagesDuration dischargingStagesDuration = new DischargingStagesDuration();
    dischargingStagesDuration.setId(1L);
    return dischargingStagesDuration;
  }

  @Test
  void testBuildStageDurationMasterMessage() {
    List<DischargingStagesDuration> list = new ArrayList<>();
    DischargingStagesDuration dischargingStagesDuration = new DischargingStagesDuration();
    dischargingStagesDuration.setId(1L);
    dischargingStagesDuration.setDuration(1);
    list.add(dischargingStagesDuration);
    var stageDuration =
        this.dischargeInformationBuilderService.buildStageDurationMasterMessage(list);
    assertEquals(1L, stageDuration.get(0).getDuration());
  }

  @Test
  void testBuildStageOffsetMasterMessage() {
    List<DischargingStagesMinAmount> list = new ArrayList<>();
    DischargingStagesMinAmount dischargingStagesMinAmount = new DischargingStagesMinAmount();
    dischargingStagesMinAmount.setId(1L);
    dischargingStagesMinAmount.setMinAmount(1);
    list.add(dischargingStagesMinAmount);
    var stageOffsets = this.dischargeInformationBuilderService.buildStageOffsetMasterMessage(list);
    assertEquals(1L, stageOffsets.get(0).getStageOffsetVal());
  }

  @Test
  void testBuildLoadingDelayMessage() {
    List<DischargingDelayReason> list = new ArrayList<>();
    DischargingDelayReason dischargingDelayReason = new DischargingDelayReason();
    dischargingDelayReason.setId(1L);
    dischargingDelayReason.setIsActive(true);
    dischargingDelayReason.setReasonForDelay(getRD());
    list.add(dischargingDelayReason);
    List<DischargingDelay> list1 = new ArrayList<>();
    DischargingDelay dischargingDelay = new DischargingDelay();
    dischargingDelay.setIsActive(true);
    dischargingDelay.setId(1L);
    dischargingDelay.setDuration(new BigDecimal(1));
    dischargingDelay.setCargoXid(1L);
    dischargingDelay.setQuantity(new BigDecimal(1));
    dischargingDelay.setCargoNominationXid(1L);
    dischargingDelay.setDischargingInformation(getDI());
    dischargingDelay.setDischargingDelayReasons(getDDR());
    list1.add(dischargingDelay);
    var loadingDelay =
        this.dischargeInformationBuilderService.buildLoadingDelayMessage(list, list1);
    assertEquals(1L, loadingDelay.getDelays(0).getCargoId());
  }

  private ReasonForDelay getRD() {
    ReasonForDelay reasonForDelay = new ReasonForDelay();
    reasonForDelay.setReason("1");
    reasonForDelay.setId(1L);
    return reasonForDelay;
  }

  private List<DischargingDelayReason> getDDR() {
    List<DischargingDelayReason> list = new ArrayList<>();
    DischargingDelayReason dischargingDelayReason = new DischargingDelayReason();
    dischargingDelayReason.setIsActive(true);
    dischargingDelayReason.setId(1L);
    dischargingDelayReason.setReasonForDelay(getRD());
    list.add(dischargingDelayReason);
    return list;
  }

  @Test
  void testBuildDischargingPlanTankBallastMessage() {
    List<PortDischargingPlanBallastDetails> list = new ArrayList<>();
    PortDischargingPlanBallastDetails portDischargingPlanBallastDetails =
        new PortDischargingPlanBallastDetails();
    portDischargingPlanBallastDetails.setId(1L);
    portDischargingPlanBallastDetails.setQuantity(new BigDecimal(1));
    portDischargingPlanBallastDetails.setTankXId(1L);
    portDischargingPlanBallastDetails.setQuantityM3(new BigDecimal(1));
    portDischargingPlanBallastDetails.setSounding(new BigDecimal(1));
    portDischargingPlanBallastDetails.setConditionType(1);
    portDischargingPlanBallastDetails.setValueType(1);
    list.add(portDischargingPlanBallastDetails);
    try {
      var loadingPlanTankDetails =
          this.dischargeInformationBuilderService.buildDischargingPlanTankBallastMessage(list);
      assertEquals(1L, loadingPlanTankDetails.get(0).getTankId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildDischargingPlanTankStowageMessage() {
    List<PortDischargingPlanStowageDetails> list = new ArrayList<>();
    PortDischargingPlanStowageDetails planStowageDetails = new PortDischargingPlanStowageDetails();
    planStowageDetails.setId(1L);
    planStowageDetails.setApi(new BigDecimal(1));
    planStowageDetails.setTemperature(new BigDecimal(1));
    planStowageDetails.setCargoNominationXId(1L);
    planStowageDetails.setQuantity(new BigDecimal(1));
    planStowageDetails.setTankXId(1L);
    planStowageDetails.setUllage(new BigDecimal(1));
    planStowageDetails.setQuantityM3(new BigDecimal(1));
    planStowageDetails.setConditionType(1);
    planStowageDetails.setValueType(1);
    list.add(planStowageDetails);
    try {
      var loadingPlanTankDetails =
          this.dischargeInformationBuilderService.buildDischargingPlanTankStowageMessage(list);
      assertEquals(1L, loadingPlanTankDetails.get(0).getTankId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildDischargingPlanTankRobMessage() {
    List<PortDischargingPlanRobDetails> list = new ArrayList<>();
    PortDischargingPlanRobDetails planRobDetails = new PortDischargingPlanRobDetails();
    planRobDetails.setId(1L);
    planRobDetails.setQuantity(new BigDecimal(1));
    planRobDetails.setTankXId(1L);
    planRobDetails.setQuantityM3(new BigDecimal(1));
    planRobDetails.setConditionType(1);
    planRobDetails.setValueType(1);
    list.add(planRobDetails);
    try {
      var loadingPlanTankDetails =
          this.dischargeInformationBuilderService.buildDischargingPlanTankRobMessage(list);
      assertEquals(1L, loadingPlanTankDetails.get(0).getTankId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildDischargingPlanTankStabilityMessage() {
    List<PortDischargingPlanStabilityParameters> list = new ArrayList<>();
    PortDischargingPlanStabilityParameters parameters =
        new PortDischargingPlanStabilityParameters();
    parameters.setAftDraft(new BigDecimal(1));
    parameters.setForeDraft(new BigDecimal(1));
    parameters.setMeanDraft(new BigDecimal(1));
    parameters.setTrim(new BigDecimal(1));
    parameters.setBendingMoment(new BigDecimal(1));
    parameters.setShearingForce(new BigDecimal(1));
    parameters.setConditionType(1);
    parameters.setValueType(1);
    list.add(parameters);
    try {
      var loadingPlanStabilityParameters =
          this.dischargeInformationBuilderService.buildDischargingPlanTankStabilityMessage(list);
      assertEquals(1, loadingPlanStabilityParameters.get(0).getConditionType());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testBuildDischargeDetailsMessageFromEntity() {
    DischargeInformation disEntity = new DischargeInformation();
    disEntity.setId(1L);
    disEntity.setSunsetTime(Timestamp.valueOf("1998-09-22 10:23:34"));
    disEntity.setSunriseTime(Timestamp.valueOf("1998-09-22 10:23:34"));
    disEntity.setVoyageXid(1L);
    disEntity.setStartTime(LocalTime.NOON);
    disEntity.setInitialTrim(new BigDecimal(1));
    disEntity.setFinalTrim(new BigDecimal(1));
    disEntity.setMaximumTrim(new BigDecimal(1));
    com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder();
    this.dischargeInformationBuilderService.buildDischargeDetailsMessageFromEntity(
        disEntity, builder);
    assertEquals("1", builder.getDischargeDetails().getTrimAllowed().getFinalTrim());
  }

  @Test
  void testBuildDischargeRateMessageFromEntity() {
    DischargeInformation disEntity = new DischargeInformation();
    disEntity.setInitialDischargingRate(new BigDecimal(1));
    disEntity.setMaxDischargingRate(new BigDecimal(1));
    disEntity.setMaxBallastRate(new BigDecimal(1));
    disEntity.setMinBallastRate(new BigDecimal(1));
    disEntity.setId(1L);
    com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder();
    this.dischargeInformationBuilderService.buildDischargeRateMessageFromEntity(disEntity, builder);
    assertEquals(1L, builder.getDischargeRate().getId());
  }

  @Test
  void testBuildDischargeBerthMessageFromEntity() {
    DischargeInformation disEntity = new DischargeInformation();
    disEntity.setId(1L);
    List<DischargingBerthDetail> listVarB = new ArrayList<>();
    DischargingBerthDetail detail = new DischargingBerthDetail();
    detail.setId(1L);
    detail.setBerthXid(1L);
    detail.setDepth(new BigDecimal(1));
    detail.setMaxManifoldHeight(new BigDecimal(1));
    detail.setMaxManifoldPressure(new BigDecimal(1));
    detail.setHoseConnections("1");
    detail.setSeaDraftLimitation(new BigDecimal(1));
    detail.setAirDraftLimitation(new BigDecimal(1));
    detail.setIsAirPurge(true);
    detail.setIsCargoCirculation(true);
    detail.setLineContentDisplacement(new BigDecimal(1));
    detail.setSpecialRegulationRestriction("1");
    detail.setItemToBeAgreed("1");
    listVarB.add(detail);
    com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder();
    this.dischargeInformationBuilderService.buildDischargeBerthMessageFromEntity(
        disEntity, listVarB, builder);
    assertEquals(1L, builder.getBerthDetails(0).getBerthId());
  }

  @Test
  void testBuildDischargeStageMessageFromEntity() {
    DischargeInformation disEntity = new DischargeInformation();
    disEntity.setId(1L);
    disEntity.setDischargingStagesMinAmount(getDSMA());
    disEntity.setDischargingStagesDuration(getDSD());
    disEntity.setIsTrackStartEndStage(true);
    disEntity.setIsTrackGradeSwitching(true);
    com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder();
    Mockito.when(dischargeStageDurationRepository.findAllByIsActiveTrueOrderByDuration())
        .thenReturn(getLDSD());
    Mockito.when(dischargeStageMinAmountRepository.findAllByIsActiveTrueOrderByMinAmount())
        .thenReturn(getLDSMA());
    this.dischargeInformationBuilderService.buildDischargeStageMessageFromEntity(
        disEntity, builder);
    assertEquals(1L, builder.getDischargeStage().getId());
  }

  private List<DischargingStagesDuration> getLDSD() {
    List<DischargingStagesDuration> list = new ArrayList<>();
    DischargingStagesDuration dischargingStagesDuration = new DischargingStagesDuration();
    dischargingStagesDuration.setId(1L);
    dischargingStagesDuration.setDuration(1);
    list.add(dischargingStagesDuration);
    return list;
  }

  private List<DischargingStagesMinAmount> getLDSMA() {
    List<DischargingStagesMinAmount> list = new ArrayList<>();
    DischargingStagesMinAmount dischargingStagesMinAmount = new DischargingStagesMinAmount();
    dischargingStagesMinAmount.setId(1L);
    dischargingStagesMinAmount.setMinAmount(1);
    list.add(dischargingStagesMinAmount);
    return list;
  }

  @Test
  void testBuildDischargeDelaysMessageFromEntity() {
    DischargeInformation disEntity = new DischargeInformation();
    disEntity.setId(1L);
    com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder();
    Mockito.when(this.reasonForDelayRepository.findAllByIsActiveTrue()).thenReturn(getLRD());
    Mockito.when(
            this.dischargingDelayRepository.findAllByDischargingInformation_IdAndIsActiveOrderById(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLDD());
    this.dischargeInformationBuilderService.buildDischargeDelaysMessageFromEntity(
        disEntity, builder);
    assertEquals(1L, builder.getDischargeDelay().getDelays(0).getCargoId());
  }

  private List<ReasonForDelay> getLRD() {
    List<ReasonForDelay> reasonForDelays = new ArrayList<>();
    ReasonForDelay reason = new ReasonForDelay();
    reason.setId(1L);
    reason.setReason("1");
    reasonForDelays.add(reason);
    return reasonForDelays;
  }

  private List<DischargingDelay> getLDD() {
    List<DischargingDelay> list = new ArrayList<>();
    DischargingDelay dischargingDelay = new DischargingDelay();
    dischargingDelay.setId(1L);
    dischargingDelay.setDuration(new BigDecimal(1));
    dischargingDelay.setQuantity(new BigDecimal(1));
    dischargingDelay.setCargoXid(1L);
    dischargingDelay.setCargoNominationXid(1L);
    dischargingDelay.setDischargingDelayReasons(getDDR());
    list.add(dischargingDelay);
    return list;
  }

  @Test
  void testBuildPostDischargeStageMessageFromEntity() {
    DischargeInformation disEntity = new DischargeInformation();
    disEntity.setTimeForDryCheck(new BigDecimal(1));
    disEntity.setTimeForSlopDischarging(new BigDecimal(1));
    disEntity.setTimeForFinalStripping(new BigDecimal(1));
    disEntity.setFreshOilWashing(new BigDecimal(1));
    com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder();
    this.dischargeInformationBuilderService.buildPostDischargeStageMessageFromEntity(
        disEntity, builder);
    assertEquals("1", builder.getPostDischargeStageTime().getSlopDischarging());
  }

  @Test
  void testbuildMachineInUseMessageFromEntity() {
    DischargeInformation disEntity = new DischargeInformation();
    disEntity.setId(1L);
    com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder();
    Mockito.when(dischargingMachineryInUseRepository.findAllByDischargingInfoId(Mockito.anyLong()))
        .thenReturn(getLDMU());
    this.dischargeInformationBuilderService.buildMachineInUseMessageFromEntity(disEntity, builder);
    assertEquals("1", builder.getMachineInUse(0).getCapacity());
  }

  private List<DischargingMachineryInUse> getLDMU() {
    List<DischargingMachineryInUse> list = new ArrayList<>();
    DischargingMachineryInUse dischargingMachineryInUse = new DischargingMachineryInUse();
    dischargingMachineryInUse.setId(1L);
    dischargingMachineryInUse.setMachineXid(1L);
    dischargingMachineryInUse.setMachineTypeXid(1);
    dischargingMachineryInUse.setCapacity(new BigDecimal(1));
    dischargingMachineryInUse.setIsUsing(true);
    list.add(dischargingMachineryInUse);
    return list;
  }

  @Test
  void testBuildCowPlanMessageFromEntity() {
    DischargeInformation disEntity = new DischargeInformation();
    disEntity.setId(1L);
    com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder();
    Mockito.when(this.cowPlanDetailRepository.findByDischargingId(Mockito.anyLong()))
        .thenReturn(getCPD());
    this.dischargeInformationBuilderService.buildCowPlanMessageFromEntity(disEntity, builder);
    assertEquals("1", builder.getCowPlan().getCowEndTime());
  }

  private Optional<CowPlanDetail> getCPD() {
    CowPlanDetail cowPlanDetail = new CowPlanDetail();
    cowPlanDetail.setCowOperationType(1);
    cowPlanDetail.setCowPercentage(new BigDecimal(1));
    cowPlanDetail.setCowStartTime(new BigDecimal(1));
    cowPlanDetail.setCowEndTime(new BigDecimal(1));
    cowPlanDetail.setEstimatedCowDuration(new BigDecimal(1));
    cowPlanDetail.setCowMinTrim(new BigDecimal(1));
    cowPlanDetail.setCowMaxTrim(new BigDecimal(1));
    cowPlanDetail.setNeedFreshCrudeStorage(true);
    cowPlanDetail.setNeedFlushingOil(true);
    cowPlanDetail.setWashTankWithDifferentCargo(true);
    cowPlanDetail.setCowTankDetails(getCTD());
    cowPlanDetail.setCowWithDifferentCargos(getCDC());
    return Optional.of(cowPlanDetail);
  }

  private Set<CowTankDetail> getCTD() {
    Set<CowTankDetail> set = new HashSet<>();
    CowTankDetail cowTankDetail = new CowTankDetail();
    cowTankDetail.setCowTypeXid(3);
    cowTankDetail.setTankXid(1L);
    cowTankDetail.setIsActive(true);
    set.add(cowTankDetail);
    return set;
  }

  private Set<CowWithDifferentCargo> getCDC() {
    Set<CowWithDifferentCargo> set = new HashSet<>();
    CowWithDifferentCargo cowWithDifferentCargo = new CowWithDifferentCargo();
    cowWithDifferentCargo.setIsActive(true);
    cowWithDifferentCargo.setCargoXid(1L);
    cowWithDifferentCargo.setCargoNominationXid(1L);
    cowWithDifferentCargo.setWashingCargoXid(1L);
    cowWithDifferentCargo.setWashingCargoNominationXid(1L);
    cowWithDifferentCargo.setTankXid(1L);
    set.add(cowWithDifferentCargo);
    return set;
  }

  @Test
  void testBuildResponseForDSRules() {
    List<DischargePlanRules> dStudyRulesList = new ArrayList<>();
    DischargePlanRules dischargePlanRules = new DischargePlanRules();
    dischargePlanRules.setIsEnable(false);
    dischargePlanRules.setDisplayInSettings(false);
    dischargePlanRules.setId(1L);
    dischargePlanRules.setRuleTypeXId(1L);
    dischargePlanRules.setIsHardRule(false);
    dischargePlanRules.setVesselRuleXId(1L);
    dischargePlanRules.setParentRuleXId(1L);
    dischargePlanRules.setNumericPrecision(1L);
    dischargePlanRules.setNumericScale(1L);
    dischargePlanRules.setDischargePlanRuleInputList(getDPRI());
    dStudyRulesList.add(dischargePlanRules);
    Common.RulePlans.Builder rulePlanBuilder = Common.RulePlans.newBuilder();
    DischargeRuleReply.Builder builder = DischargeRuleReply.newBuilder();
    VesselInfo.VesselRuleReply vesselRuleReply = VesselInfo.VesselRuleReply.newBuilder().build();
    this.dischargeInformationBuilderService.buildResponseForDSRules(
        dStudyRulesList, rulePlanBuilder, builder, vesselRuleReply);
    assertEquals(false, builder.getRulePlan(0).getRules(0).getDisplayInSettings());
  }

  private List<DischargePlanRuleInput> getDPRI() {
    List<DischargePlanRuleInput> list = new ArrayList<>();
    DischargePlanRuleInput dischargePlanRuleInput = new DischargePlanRuleInput();
    dischargePlanRuleInput.setId(1L);
    dischargePlanRuleInput.setDefaultValue("1");
    dischargePlanRuleInput.setPrefix("1");
    dischargePlanRuleInput.setMinValue("1");
    dischargePlanRuleInput.setMaxValue("1");
    dischargePlanRuleInput.setTypeValue("1");
    dischargePlanRuleInput.setSuffix("1");
    dischargePlanRuleInput.setIsMandatory(true);
    list.add(dischargePlanRuleInput);
    return list;
  }

  @Test
  void testBuildResponseForDefaultDSRules() {
    List<Common.Rules> list1 = new ArrayList<>();
    Common.Rules rules = Common.Rules.newBuilder().setRuleType("1").build();
    list1.add(rules);
    List<Common.RulePlans> list = new ArrayList<>();
    Common.RulePlans rulePlans =
        Common.RulePlans.newBuilder().setHeader("1").addAllRules(list1).build();
    list.add(rulePlans);
    VesselInfo.VesselRuleReply vesselRuleReply =
        VesselInfo.VesselRuleReply.newBuilder().addAllRulePlan(list).build();
    DischargeRuleReply.Builder builder = DischargeRuleReply.newBuilder();
    this.dischargeInformationBuilderService.buildResponseForDefaultDSRules(
        vesselRuleReply, builder);
    assertEquals("1", builder.getRulePlan(0).getRules(0).getRuleType());
  }
}
