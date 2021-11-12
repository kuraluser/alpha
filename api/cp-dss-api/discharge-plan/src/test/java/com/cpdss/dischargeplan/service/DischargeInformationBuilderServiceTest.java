/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
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
}
