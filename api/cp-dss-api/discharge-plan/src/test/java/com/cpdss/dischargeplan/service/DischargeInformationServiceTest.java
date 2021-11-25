/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
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

@SpringJUnitConfig(classes = {DischargeInformationService.class})
public class DischargeInformationServiceTest {

  @Autowired DischargeInformationService dischargeInformationService;
  @MockBean DischargeInformationRepository dischargeInformationRepository;
  @MockBean CowPlanDetailRepository cowPlanDetailRepository;
  @MockBean DischargeStageDurationRepository dischargeStageDurationRepository;
  @MockBean PortDischargingPlanRobDetailsRepository portDischargingPlanRobDetailsRepository;
  @MockBean DischargeStageMinAmountRepository dischargeStageMinAmountRepository;
  @MockBean DischargeInformationBuilderService informationBuilderService;
  @MockBean DischargingInstructionRepository dischargingInstructionRepository;
  @MockBean DischargeBerthDetailRepository dischargeBerthDetailRepository;

  @MockBean DischargeRulesRepository dischargeStudyRulesRepository;
  @MockBean ReasonForDelayRepository reasonForDelayRepository;

  @MockBean DischargingDelayRepository dischargingDelayRepository;
  @MockBean DischargeRulesInputRepository dischargeStudyRulesInputRepository;

  @MockBean PortTideDetailsRepository portTideDetailsRepository;

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Test
  void testGetDischargeInformation1Args() {
    Long primaryKey = 1L;
    Mockito.when(this.dischargeInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getDI());
    var dischargeInformation = this.dischargeInformationService.getDischargeInformation(primaryKey);
    assertEquals(Optional.of(1L), java.util.Optional.ofNullable(dischargeInformation.getId()));
  }

  private Optional<DischargeInformation> getDI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setSynopticTableXid(1L);
    dischargeInformation.setDischargingInformationStatus(getDIS());
    dischargeInformation.setSunriseTime(Timestamp.valueOf("2021-03-02 11:23:12"));
    dischargeInformation.setSunsetTime(Timestamp.valueOf("2021-03-02 11:23:12"));
    dischargeInformation.setStartTime(LocalTime.NOON);
    dischargeInformation.setVoyageXid(1L);
    dischargeInformation.setInitialTrim(new BigDecimal(1));
    dischargeInformation.setFinalTrim(new BigDecimal(1));
    dischargeInformation.setMaximumTrim(new BigDecimal(1));
    dischargeInformation.setInitialDischargingRate(new BigDecimal(1));
    dischargeInformation.setMaxDischargingRate(new BigDecimal(1));
    dischargeInformation.setMinBallastRate(new BigDecimal(1));
    dischargeInformation.setMaxBallastRate(new BigDecimal(1));
    dischargeInformation.setDischargingStagesMinAmount(getDSMA());
    dischargeInformation.setDischargingStagesDuration(getDSD());
    dischargeInformation.setIsTrackStartEndStage(true);
    dischargeInformation.setIsTrackGradeSwitching(true);
    dischargeInformation.setTimeForDryCheck(new BigDecimal(1));
    dischargeInformation.setTimeForSlopDischarging(new BigDecimal(1));
    dischargeInformation.setTimeForFinalStripping(new BigDecimal(1));
    dischargeInformation.setFreshOilWashing(new BigDecimal(1));
    //    dischargeInformation.setDischargingMachineryInUses(getDMU());
    dischargeInformation.setIsDischargeInformationComplete(true);
    dischargeInformation.setDischargeSlopTankFirst(true);
    dischargeInformation.setDischargeCommingleCargoSeparately(true);
    return Optional.of(dischargeInformation);
  }

  private Set<DischargingMachineryInUse> getDMU() {
    Set<DischargingMachineryInUse> set = new HashSet<>();
    DischargingMachineryInUse dischargingMachineryInUse = new DischargingMachineryInUse();
    dischargingMachineryInUse.setId(1L);
    dischargingMachineryInUse.setMachineXid(1L);
    dischargingMachineryInUse.setMachineTypeXid(1);
    dischargingMachineryInUse.setCapacity(new BigDecimal(1));
    dischargingMachineryInUse.setIsUsing(true);
    set.add(dischargingMachineryInUse);
    return set;
  }

  private DischargingStagesDuration getDSD() {
    DischargingStagesDuration dischargingStagesDuration = new DischargingStagesDuration();
    dischargingStagesDuration.setId(1L);
    return dischargingStagesDuration;
  }

  private DischargingStagesMinAmount getDSMA() {
    DischargingStagesMinAmount dischargingStagesMinAmount = new DischargingStagesMinAmount();
    dischargingStagesMinAmount.setId(1L);
    return dischargingStagesMinAmount;
  }

  private DischargingInformationStatus getDIS() {
    DischargingInformationStatus dischargingInformationStatus = new DischargingInformationStatus();
    dischargingInformationStatus.setId(1L);
    return dischargingInformationStatus;
  }

  @Test
  void testGetDischargeInformation3Args() {
    Long vesselId = 1L;
    Long voyage = 1L;
    Long portRotationId = 1L;
    Mockito.when(
            this.dischargeInformationRepository
                .findByVesselXidAndVoyageXidAndPortRotationXidAndIsActiveTrue(
                    Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getDI());
    var dischargeInformation =
        this.dischargeInformationService.getDischargeInformation(vesselId, voyage, portRotationId);
    assertEquals(Optional.of(1L), java.util.Optional.ofNullable(dischargeInformation.getId()));
  }

  @Test
  void testGetDischargeInformation2Args() {
    DischargeInformationRequest request =
        DischargeInformationRequest.newBuilder()
            .setVesselId(1L)
            .setVoyageId(1L)
            .setPortRotationId(1L)
            .build();
    com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder =
        com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder();
    Mockito.when(
            this.dischargeInformationRepository
                .findByVesselXidAndVoyageXidAndPortRotationXidAndIsActiveTrue(
                    Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getDI());
    Mockito.when(
            this.dischargeBerthDetailRepository.findAllByDischargingInformationIdAndIsActiveTrue(
                Mockito.anyLong()))
        .thenReturn(getLDBD());
    Mockito.when(dischargeStageDurationRepository.findAllByIsActiveTrue()).thenReturn(getLDSD());
    Mockito.when(dischargeStageMinAmountRepository.findAllByIsActiveTrue()).thenReturn(getLDSMA());
    Mockito.when(this.reasonForDelayRepository.findAllByIsActiveTrue()).thenReturn(getLRD());
    Mockito.when(
            this.dischargingDelayRepository.findAllByDischargingInformation_IdAndIsActiveOrderById(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLDD());
    Mockito.when(this.cowPlanDetailRepository.findByDischargingId(Mockito.anyLong()))
        .thenReturn(getCPD());
    try {
      this.dischargeInformationService.getDischargeInformation(request, builder);
      assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<DischargingBerthDetail> getLDBD() {
    List<DischargingBerthDetail> list = new ArrayList<>();
    DischargingBerthDetail dischargingBerthDetail = new DischargingBerthDetail();
    dischargingBerthDetail.setId(1L);
    dischargingBerthDetail.setBerthXid(1L);
    dischargingBerthDetail.setDepth(new BigDecimal(1));
    dischargingBerthDetail.setMaxManifoldHeight(new BigDecimal(1));
    dischargingBerthDetail.setHoseConnections("1");
    dischargingBerthDetail.setSeaDraftLimitation(new BigDecimal(1));
    dischargingBerthDetail.setAirDraftLimitation(new BigDecimal(1));
    dischargingBerthDetail.setIsAirPurge(true);
    dischargingBerthDetail.setIsCargoCirculation(true);
    dischargingBerthDetail.setLineContentDisplacement(new BigDecimal(1));
    dischargingBerthDetail.setSpecialRegulationRestriction("1");
    dischargingBerthDetail.setItemToBeAgreed("1");
    list.add(dischargingBerthDetail);
    return list;
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

  private List<DischargingDelayReason> getDDR() {
    List<DischargingDelayReason> list = new ArrayList<>();
    DischargingDelayReason dischargingDelayReason = new DischargingDelayReason();
    dischargingDelayReason.setIsActive(true);
    dischargingDelayReason.setId(1L);
    dischargingDelayReason.setReasonForDelay(getRD());
    list.add(dischargingDelayReason);
    return list;
  }

  private ReasonForDelay getRD() {
    ReasonForDelay reasonForDelay = new ReasonForDelay();
    reasonForDelay.setReason("1");
    reasonForDelay.setId(1L);
    return reasonForDelay;
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

  //   @SpyBean
  //   private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  //
  //    @Test
  //    void testGetOrSaveRulesForDischarge() {
  //        DischargeInformationService spyService = Mockito.spy(DischargeInformationService.class);
  //        List<Common.RulesInputs> list3 = new ArrayList<>();
  //        Common.RulesInputs rulesInputs =
  // Common.RulesInputs.newBuilder().setDefaultValue(",").setMin("1").setMax("1").setSuffix("tank
  // can be filled with commingled cargo").setPrefix("only").setType("Dropdown")
  //                .setIsMandatory(true).setId("1").build();
  //        list3.add(rulesInputs);
  //        List<Common.Rules> list1 = new ArrayList<>();
  //        Common.Rules rules =
  // Common.Rules.newBuilder().addAllInputs(list3).setVesselRuleXId("1").setRuleType("1").setRuleTemplateId("1").setId("1").setDisplayInSettings(true).setEnable(false).setIsHardRule(false)
  //                .setNumericPrecision(1L).setNumericScale(1L).build();
  //        list1.add(rules);
  //        List<Common.RulePlans> list= new ArrayList<>();
  //        Common.RulePlans rulePlans =
  // Common.RulePlans.newBuilder().setHeader("1").addAllRules(list1).build();
  //        list.add(rulePlans);
  //        DischargeRuleRequest request =
  // DischargeRuleRequest.newBuilder().setSectionId(3L).setVesselId(1L).setDischargeInfoId(1L).addAllRulePlan(list).build();
  //        DischargeRuleReply.Builder builder = DischargeRuleReply.newBuilder();
  //
  // Mockito.when(dischargeInformationRepository.findByIdAndIsActiveAndVesselXid(Mockito.anyLong(),Mockito.anyBoolean(),Mockito.anyLong())).thenReturn(getDI());
  //
  // Mockito.when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(Mockito.any())).thenReturn(getVesselRuleReply());
  //
  // Mockito.when(dischargeStudyRulesRepository.findById(Mockito.anyLong())).thenReturn(getDP());
  //
  // Mockito.when(dischargeStudyRulesInputRepository.findById(Mockito.anyLong())).thenReturn(getODPRI());
  //        ReflectionTestUtils.setField(spyService,"vesselInfoGrpcService",vesselInfoGrpcService);
  //        try {
  //            spyService.getOrSaveRulesForDischarge(request,builder);
  //        } catch (GenericServiceException e) {
  //            e.printStackTrace();
  //        }
  //    }

  private VesselInfo.VesselRuleReply getVesselRuleReply() {
    List<VesselInfo.CargoTankMaster> list2 = new ArrayList<>();
    VesselInfo.CargoTankMaster cargoTankMaster = VesselInfo.CargoTankMaster.newBuilder().build();
    list2.add(cargoTankMaster);
    List<VesselInfo.RuleDropDownValueMaster> list1 = new ArrayList<>();
    VesselInfo.RuleDropDownValueMaster ruleDropDownValueMaster =
        VesselInfo.RuleDropDownValueMaster.newBuilder().setRuleTemplateId(1L).build();
    list1.add(ruleDropDownValueMaster);
    List<VesselInfo.RuleTypeMaster> list = new ArrayList<>();
    VesselInfo.RuleTypeMaster ruleTypeMaster =
        VesselInfo.RuleTypeMaster.newBuilder().setRuleType("1").setId(1L).build();
    list.add(ruleTypeMaster);
    VesselInfo.VesselRuleReply vesselRuleReply =
        VesselInfo.VesselRuleReply.newBuilder()
            .addAllCargoTankMaster(list2)
            .addAllRuleDropDownValueMaster(list1)
            .addAllRuleTypeMaster(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return vesselRuleReply;
  }

  private Optional<DischargePlanRules> getDP() {
    DischargePlanRules dischargePlanRules = new DischargePlanRules();
    dischargePlanRules.setId(1L);
    return Optional.of(dischargePlanRules);
  }

  private Optional<DischargePlanRuleInput> getODPRI() {
    DischargePlanRuleInput dischargePlanRuleInput = new DischargePlanRuleInput();
    dischargePlanRuleInput.setId(1L);
    return Optional.of(dischargePlanRuleInput);
  }

  // @Test
  //    void testUploadPortTideDetails() {
  //
  //    DischargingUploadTideDetailRequest request =
  // DischargingUploadTideDetailRequest.newBuilder().setTideDetaildata().build();
  //
  // }

  @Test
  void testUpdateIsDischargingInfoCompeteStatus() {
    Long id = 1L;
    boolean isDischargingInfoComplete = true;
    this.dischargeInformationService.updateIsDischargingInfoCompeteStatus(
        id, isDischargingInfoComplete);
  }

  @Test
  void testUpdateDischargingPlanDetailsFromAlgo() {
    Long id = 1L;
    String dischargingPlanDetailsFromAlgo = "1";
    this.dischargeInformationService.updateDischargingPlanDetailsFromAlgo(
        id, dischargingPlanDetailsFromAlgo);
  }

  @Test
  void testUpdateDischargingInformationStatus() {
    DischargingInformationStatus dischargingInformationStatus = new DischargingInformationStatus();
    Long id = 1L;
    this.dischargeInformationService.updateDischargingInformationStatus(
        dischargingInformationStatus, id);
  }

  @Test
  void testupdateDischargingInformationStatuses() {
    DischargingInformationStatus dischargingInformationStatus = new DischargingInformationStatus();
    DischargingInformationStatus arrivalStatus = new DischargingInformationStatus();
    DischargingInformationStatus departureStatus = new DischargingInformationStatus();
    Long id = 1L;
    this.dischargeInformationService.updateDischargingInformationStatuses(
        dischargingInformationStatus, arrivalStatus, departureStatus, id);
  }

  @Test
  void testUpdateIsDischargingSequenceGeneratedStatus() {
    Long id = 1L;
    boolean sequence = true;
    this.dischargeInformationService.updateIsDischargingSequenceGeneratedStatus(id, sequence);
  }

  @Test
  void testUpdateIsDischargingPlanGeneratedStatus() {
    Long id = 1L;
    boolean isPlanGenerated = true;
    this.dischargeInformationService.updateIsDischargingPlanGeneratedStatus(id, isPlanGenerated);
  }
}
