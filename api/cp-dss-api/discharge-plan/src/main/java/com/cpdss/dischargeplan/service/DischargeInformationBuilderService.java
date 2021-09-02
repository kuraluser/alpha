/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.discharge_plan.*;
import com.cpdss.common.generated.discharge_plan.DischargeDetails;
import com.cpdss.common.generated.discharge_plan.DischargeRates;
import com.cpdss.common.generated.discharge_plan.DischargeRuleReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.dischargeplan.domain.RuleType;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargePlanRuleInput;
import com.cpdss.dischargeplan.entity.DischargePlanRules;
import com.cpdss.dischargeplan.repository.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeInformationBuilderService {

  @Autowired DischargeStageDurationRepository dischargeStageDurationRepository;

  @Autowired DischargeStageMinAmountRepository dischargeStageMinAmountRepository;

  @Autowired ReasonForDelayRepository reasonForDelayRepository;

  @Autowired DischargingDelayRepository dischargingDelayRepository;

  @Autowired DischargingDelayReasonRepository dischargingDelayReasonRepository;

  @Autowired DischargingMachineryInUseRepository dischargingMachineryInUseRepository;

  @Autowired CowPlanDetailRepository cowPlanDetailRepository;

  public void buildDischargeDetailsMessageFromEntity(
      DischargeInformation disEntity,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    try {
      DischargeDetails.Builder builder1 = DischargeDetails.newBuilder();
      builder1.setId(disEntity.getId());
      Optional.ofNullable(disEntity.getSunriseTime())
          .ifPresent(v -> builder1.setTimeOfSunrise(v.toString()));
      Optional.ofNullable(disEntity.getSunsetTime())
          .ifPresent(v -> builder1.setTimeOfSunset(v.toString()));
      Optional.ofNullable(disEntity.getStartTime())
          .ifPresent(v -> builder1.setStartTime(v.toString()));
      builder1.setVoyageId(disEntity.getVoyageXid());

      // Set Trim Values
      LoadingPlanModels.TrimAllowed.Builder trimAllowed =
          LoadingPlanModels.TrimAllowed.newBuilder();
      Optional.ofNullable(disEntity.getInitialTrim())
          .ifPresent(v -> trimAllowed.setInitialTrim(v.toString()));
      Optional.ofNullable(disEntity.getMaximumTrim())
          .ifPresent(v -> trimAllowed.setMaximumTrim(v.toString()));
      Optional.ofNullable(disEntity.getFinalTrim())
          .ifPresent(v -> trimAllowed.setFinalTrim(v.toString()));

      builder1.setTrimAllowed(trimAllowed.build());
      builder.setDischargeDetails(builder1.build());
      log.info("Setting Discharge Details");
    } catch (Exception e) {
      log.error("Failed to set Discharge Details {}", e.getMessage());
      e.printStackTrace();
    }
  }

  public void buildDischargeRateMessageFromEntity(
      DischargeInformation disEntity,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    try {
      DischargeRates.Builder builder1 = DischargeRates.newBuilder();
      Optional.ofNullable(disEntity.getInitialDischargingRate())
          .ifPresent(v -> builder1.setInitialDischargeRate(v.toString()));
      Optional.ofNullable(disEntity.getMaxDischargingRate())
          .ifPresent(v -> builder1.setMaxDischargeRate(v.toString()));
      Optional.ofNullable(disEntity.getMinBallastRate())
          .ifPresent(v -> builder1.setMinBallastRate(v.toString()));
      Optional.ofNullable(disEntity.getMaxBallastRate())
          .ifPresent(v -> builder1.setMaxBallastRate(v.toString()));

      builder.setDischargeRate(builder1.build());
      log.info("Setting Discharge Rates");
    } catch (Exception e) {
      log.error("Failed to set Discharge Rates {}", e.getMessage());
      e.printStackTrace();
    }
  }

  public void buildDischargeBerthMessageFromEntity(
      DischargeInformation disEntity,
      List<DischargingBerthDetail> listVarB,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    if (!listVarB.isEmpty()) {
      try {
        for (DischargingBerthDetail source : listVarB) {
          DischargeBerths.Builder builder1 = DischargeBerths.newBuilder();
          Optional.ofNullable(disEntity.getId()).ifPresent(builder1::setDischargeInfoId);

          Optional.ofNullable(source.getId()).ifPresent(builder1::setId);
          Optional.ofNullable(source.getBerthXid()).ifPresent(builder1::setBerthId);
          Optional.ofNullable(source.getDepth()).ifPresent(v -> builder1.setDepth(v.toString()));
          Optional.ofNullable(source.getMaxManifoldHeight())
              .ifPresent(v -> builder1.setMaxManifoldHeight(v.toString()));
          Optional.ofNullable(source.getMaxManifoldPressure())
              .ifPresent(v -> builder1.setMaxManifoldPressure(v.toString()));
          Optional.ofNullable(source.getHoseConnections()).ifPresent(builder1::setHoseConnections);
          Optional.ofNullable(source.getSeaDraftLimitation())
              .ifPresent(v -> builder1.setSeaDraftLimitation(v.toString()));

          Optional.ofNullable(source.getSeaDraftLimitation())
              .ifPresent(v -> builder1.setSeaDraftLimitation(v.toString()));
          Optional.ofNullable(source.getAirDraftLimitation())
              .ifPresent(v -> builder1.setAirDraftLimitation(v.toString()));
          Optional.ofNullable(source.getIsAirPurge()).ifPresent(builder1::setAirPurge);
          Optional.ofNullable(source.getIsCargoCirculation())
              .ifPresent(builder1::setCargoCirculation);
          Optional.ofNullable(source.getLineContentDisplacement())
              .ifPresent(v -> builder1.setLineDisplacement(v.toString()));
          Optional.ofNullable(source.getSpecialRegulationRestriction())
              .ifPresent(builder1::setSpecialRegulationRestriction);
          Optional.ofNullable(source.getItemToBeAgreed())
              .ifPresent(builder1::setItemsToBeAgreedWith);

          builder.addBerthDetails(builder1.build());
        }
        log.info("Setting Discharge berths");
      } catch (Exception e) {
        log.error("Failed to set Discharge Berths {}", e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void buildDischargeStageMessageFromEntity(
      DischargeInformation disEntity,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    try {
      LoadingPlanModels.LoadingStages.Builder builder1 =
          LoadingPlanModels.LoadingStages.newBuilder();
      Optional.ofNullable(disEntity.getId()).ifPresent(builder1::setId);
      Optional.ofNullable(disEntity.getDischargingStagesMinAmount().getId())
          .ifPresent(v -> builder1.setStageOffset(v.intValue()));
      Optional.ofNullable(disEntity.getDischargingStagesDuration().getId())
          .ifPresent(v -> builder1.setStageDuration(v.intValue()));
      Optional.ofNullable(disEntity.getIsTrackStartEndStage())
          .ifPresent(builder1::setTrackStartEndStage);
      Optional.ofNullable(disEntity.getIsTrackGradeSwitching())
          .ifPresent(builder1::setTrackGradeSwitch);

      // Master data Offset
      List<DischargingStagesDuration> var1 =
          dischargeStageDurationRepository.findAllByIsActiveTrue();
      this.buildStageDurationMasterDataToMessage(var1, builder1);

      // Master data duration
      List<DischargingStagesMinAmount> var2 =
          dischargeStageMinAmountRepository.findAllByIsActiveTrue();
      this.buildStageMinAmountMasterDataToMessage(var2, builder1);

      builder.setDischargeStage(builder1.build());
      log.info("Setting Discharge Stages");
    } catch (Exception e) {
      log.error("Failed to set Discharge Berths {}", e.getMessage());
      e.printStackTrace();
    }
  }

  // Offset Value
  private void buildStageMinAmountMasterDataToMessage(
      List<DischargingStagesMinAmount> var1, LoadingPlanModels.LoadingStages.Builder builder1) {
    for (DischargingStagesMinAmount var2 : var1) {
      LoadingPlanModels.StageOffsets.Builder offsets = LoadingPlanModels.StageOffsets.newBuilder();
      offsets.setId(var2.getId());
      offsets.setStageOffsetVal(var2.getMinAmount());
      builder1.addStageOffsets(offsets.build());
    }
  }

  // Duration
  private void buildStageDurationMasterDataToMessage(
      List<DischargingStagesDuration> var2, LoadingPlanModels.LoadingStages.Builder builder1) {
    for (DischargingStagesDuration dr : var2) {
      LoadingPlanModels.StageDuration.Builder builder =
          LoadingPlanModels.StageDuration.newBuilder();
      builder.setId(dr.getId());
      builder.setDuration(dr.getDuration());
      builder1.addStageDurations(builder.build());
    }
  }

  public void buildDischargeDelaysMessageFromEntity(
      DischargeInformation disEntity,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {

    DischargeDelay.Builder builder1 = DischargeDelay.newBuilder();

    try {
      // Set Master data of Reasons
      List<ReasonForDelay> list = this.reasonForDelayRepository.findAllByIsActiveTrue();
      for (ReasonForDelay var : list) {
        LoadingPlanModels.DelayReasons.Builder reason = LoadingPlanModels.DelayReasons.newBuilder();
        reason.setId(var.getId());
        reason.setReason(var.getReason());
        builder1.addReasons(reason.build());
      }
      log.info("Setting Delay Reasons (master)");
    } catch (Exception e) {
      log.error("Failed to set Discharge Delay Reasons (master) {}", e.getMessage());
      e.printStackTrace();
    }

    try {
      List<DischargingDelay> delays =
          this.dischargingDelayRepository.findAllByDischargeInfoId(disEntity.getId());
      for (DischargingDelay source : delays) {
        DischargeDelays.Builder builder2 = DischargeDelays.newBuilder();
        builder2.setId(source.getId());
        builder2.setDischargeInfoId(disEntity.getId());
        builder2.setDuration(source.getDuration().toString());
        builder2.setQuantity(source.getQuantity().toString());
        Optional.ofNullable(source.getCargoXid())
            .ifPresent(builder2::setCargoId); // can empty of initial delay
        Optional.ofNullable(source.getCargoNominationXid())
            .ifPresent(builder2::setCargoNominationId); // can empty of initial delay
        builder2.addAllReasonForDelayIds(
            source.getDischargingDelayReasons().stream()
                .map(DischargingDelayReason::getId)
                .collect(Collectors.toList()));
        builder1.addDelays(builder2.build());
      }
      log.info("Setting Delay Reasons");
    } catch (Exception e) {
      log.error("Failed to set Discharge Delay Reasons {}", e.getMessage());
      e.printStackTrace();
    }
    builder.setDischargeDelay(builder1.build());
  }

  public void buildPostDischargeStageMessageFromEntity(
      DischargeInformation disEntity,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    try {
      PostDischargeStageTime.Builder builder1 = PostDischargeStageTime.newBuilder();
      Optional.ofNullable(disEntity.getTimeForDryCheck())
          .ifPresent(v -> builder1.setTimeForDryCheck(v.toString()));
      Optional.ofNullable(disEntity.getTimeForSlopDischarging())
          .ifPresent(v -> builder1.setSlopDischarging(v.toString()));
      Optional.ofNullable(disEntity.getTimeForFinalStripping())
          .ifPresent(v -> builder1.setFinalStripping(v.toString()));
      Optional.ofNullable(disEntity.getFreshOilWashing())
          .ifPresent(v -> builder1.setFreshOilWashing(v.toString()));
      builder.setPostDischargeStageTime(builder1.build());
      log.info("Setting Post Discharge stage");
    } catch (Exception e) {
      log.error("Failed to Set Post Discharge stage");
      e.printStackTrace();
    }
  }

  public void buildMachineInUseMessageFromEntity(
      DischargeInformation disEntity,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    try {
      List<DischargingMachineryInUse> list = disEntity.getDischargingMachineryInUses();
      for (DischargingMachineryInUse var1 : list) {
        LoadingPlanModels.LoadingMachinesInUse.Builder builder1 =
            LoadingPlanModels.LoadingMachinesInUse.newBuilder();
        builder1.setId(var1.getId());
        builder1.setLoadingInfoId(disEntity.getId());
        builder1.setMachineId(var1.getMachineXid());
        builder1.setMachineTypeValue(var1.getMachineTypeXid());
        Optional.ofNullable(var1.getCapacity())
            .ifPresent(value -> builder1.setCapacity(value.toString()));
        builder1.setIsUsing(var1.getIsUsing());
        builder.addMachineInUse(builder1.build());
      }
      log.info("Setting Machine in use");
    } catch (Exception e) {
      log.error("Failed to Set Machine In Use");
      e.printStackTrace();
    }
  }

  @Autowired CowWithDifferentCargoRepository cowWithDifferentCargoRepository;

  public void buildCowPlanMessageFromEntity(
      DischargeInformation disEntity,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    try {
      CowPlan.Builder builder1 = CowPlan.newBuilder();
      CowPlanDetail cpd = this.cowPlanDetailRepository.findByDischargingId(disEntity.getId()).get();
      Optional.ofNullable(cpd.getCowOperationType())
          .ifPresent(v -> builder1.setCowOptionType(Common.COW_OPTION_TYPE.forNumber(v)));
      Optional.ofNullable(cpd.getCowPercentage())
          .ifPresent(v -> builder1.setCowTankPercent(v.toString()));

      Optional.ofNullable(cpd.getCowPercentage())
          .ifPresent(v -> builder1.setCowTankPercent(v.toString()));
      Optional.ofNullable(cpd.getCowStartTime())
          .ifPresent(v -> builder1.setCowStartTime(v.toString()));
      Optional.ofNullable(cpd.getCowEndTime()).ifPresent(v -> builder1.setCowEndTime(v.toString()));
      Optional.ofNullable(cpd.getEstimatedCowDuration())
          .ifPresent(v -> builder1.setEstCowDuration(v.toString()));

      Optional.ofNullable(cpd.getCowMinTrim()).ifPresent(v -> builder1.setTrimCowMin(v.toString()));
      Optional.ofNullable(cpd.getCowMaxTrim()).ifPresent(v -> builder1.setTrimCowMax(v.toString()));

      Optional.ofNullable(cpd.getNeedFreshCrudeStorage())
          .ifPresent(builder1::setNeedFreshCrudeStorage);
      Optional.ofNullable(cpd.getNeedFlushingOil()).ifPresent(builder1::setNeedFlushingOil);

      // tank wise details
      if (!cpd.getCowTankDetails().isEmpty()) {
        this.buildCowTankDetails(
            Common.COW_TYPE.TOP_COW,
            builder1,
            cpd.getCowTankDetails().stream()
                .filter(v -> v.getCowTypeXid().equals(Common.COW_TYPE.TOP_COW_VALUE))
                .collect(Collectors.toList()));
        this.buildCowTankDetails(
            Common.COW_TYPE.BOTTOM_COW,
            builder1,
            cpd.getCowTankDetails().stream()
                .filter(v -> v.getCowTypeXid().equals(Common.COW_TYPE.BOTTOM_COW_VALUE))
                .collect(Collectors.toList()));
        this.buildCowTankDetails(
            Common.COW_TYPE.ALL_COW,
            builder1,
            cpd.getCowTankDetails().stream()
                .filter(v -> v.getCowTypeXid().equals(Common.COW_TYPE.ALL_COW_VALUE))
                .collect(Collectors.toList()));
      }
      if (!cpd.getCowWithDifferentCargos().isEmpty()) {
        this.buildCowTankDetails(Common.COW_TYPE.CARGO, builder1, cpd.getCowWithDifferentCargos());
      }

      builder.setCowPlan(builder1.build());
    } catch (Exception e) {
      log.error("Failed to set cow plan");
      e.printStackTrace();
    }
  }

  private void buildCowTankDetails(
      final Common.COW_TYPE cow_type, CowPlan.Builder builder, List list) {
    CowTankDetails.Builder tankDetails = CowTankDetails.newBuilder();
    tankDetails.setCowType(cow_type);
    switch (cow_type) {
      case TOP_COW:
      case BOTTOM_COW:
      case ALL_COW:
        {
          List<CowTankDetail> ls1 = new ArrayList<>(list); // cast from generic list
          var ids = ls1.stream().map(CowTankDetail::getTankXid).collect(Collectors.toList());
          tankDetails.addAllTankIds(ids);
        }
        break;
      case CARGO:
        {
          List<CowWithDifferentCargo> ls1 = new ArrayList<>(list); // cast from generic list
          var gp1 =
              ls1.stream()
                  .collect(
                      Collectors.groupingBy(CowWithDifferentCargo::getCargoXid)); // group by cargo
          for (Map.Entry<Long, List<CowWithDifferentCargo>> map1 : gp1.entrySet()) {
            CowWithDifferentCargo firstItem = map1.getValue().stream().findFirst().get();
            CargoForCow.Builder cargoForCow = CargoForCow.newBuilder();
            cargoForCow.setCargoId(firstItem.getCargoXid());
            cargoForCow.setCargoNominationId(firstItem.getCargoNominationXid());
            cargoForCow.setWashingCargoId(firstItem.getWashingCargoXid());
            cargoForCow.setWashingCargoNominationId(firstItem.getWashingCargoNominationXid());
            cargoForCow.addAllTankIds(
                map1.getValue().stream()
                    .map(CowWithDifferentCargo::getTankXid)
                    .collect(Collectors.toList()));
            tankDetails.addCargoForCow(cargoForCow.build());
          }
        }
        break;
      default:
        {
          log.info("Default case for build cow tank details");
        }
    }
    builder.addCowTankDetails(tankDetails.build());
  }

  /**
   * @param dStudyRulesList
   * @param rulePlanBuilder
   * @param builder
   * @param vesselRuleReply
   */
  public void buildResponseForDSRules(
      List<DischargePlanRules> dStudyRulesList,
      Common.RulePlans.Builder rulePlanBuilder,
      DischargeRuleReply.Builder builder,
      VesselInfo.VesselRuleReply vesselRuleReply) {
    dStudyRulesList.forEach(
        ruleList -> {
          // for (int ruleIndex = 0; ruleIndex < lStudyRulesList.size(); ruleIndex++) {
          Common.Rules.Builder rulesBuilder = Common.Rules.newBuilder();
          Optional.ofNullable(ruleList.getIsEnable())
              .ifPresentOrElse(rulesBuilder::setEnable, () -> rulesBuilder.setEnable(false));
          Optional.ofNullable(ruleList.getDisplayInSettings())
              .ifPresentOrElse(
                  rulesBuilder::setDisplayInSettings,
                  () -> rulesBuilder.setDisplayInSettings(false));
          Optional.ofNullable(ruleList.getId())
              .ifPresentOrElse(
                  item -> rulesBuilder.setId(String.valueOf(item)), () -> rulesBuilder.setId(""));
          Optional.ofNullable(ruleList.getRuleTypeXId())
              .filter(item -> item.equals(RuleType.ABSOLUTE.getId()))
              .ifPresentOrElse(
                  item -> rulesBuilder.setRuleType(RuleType.ABSOLUTE.getRuleType()),
                  () -> rulesBuilder.setRuleType(RuleType.PREFERABLE.getRuleType()));
          Optional.ofNullable(ruleList.getIsHardRule())
              .ifPresentOrElse(
                  rulesBuilder::setIsHardRule, () -> rulesBuilder.setIsHardRule(false));
          Optional.ofNullable(ruleList.getVesselRuleXId())
              .ifPresentOrElse(
                  item -> rulesBuilder.setVesselRuleXId(String.valueOf(item)),
                  () -> rulesBuilder.setVesselRuleXId(""));
          Optional.ofNullable(ruleList.getParentRuleXId())
              .ifPresentOrElse(
                  item -> rulesBuilder.setRuleTemplateId(String.valueOf(item)),
                  () -> rulesBuilder.setRuleTemplateId(""));
          Optional.ofNullable(ruleList.getNumericPrecision())
              .ifPresentOrElse(
                  rulesBuilder::setNumericPrecision, () -> rulesBuilder.setNumericPrecision(0));
          Optional.ofNullable(ruleList.getNumericScale())
              .ifPresentOrElse(
                  rulesBuilder::setNumericScale, () -> rulesBuilder.setNumericScale(0));
          Common.RulesInputs.Builder ruleInput = Common.RulesInputs.newBuilder();
          List<DischargePlanRuleInput> loadableStudyRuleInputs;
          if (ruleList != null && ruleList.getDischargePlanRuleInputList().size() != 0) {
            loadableStudyRuleInputs =
                ruleList.getDischargePlanRuleInputList().stream()
                    .sorted(Comparator.comparingLong(DischargePlanRuleInput::getId))
                    .collect(Collectors.toList());
          } else {
            loadableStudyRuleInputs = new ArrayList<>();
          }
          for (int inputIndex = 0; inputIndex < loadableStudyRuleInputs.size(); inputIndex++) {
            Common.RulesInputs.Builder finalRuleInput = ruleInput;
            DischargePlanRuleInput input = loadableStudyRuleInputs.get(inputIndex);
            Optional.ofNullable(input.getDefaultValue())
                .filter(item -> item.trim().length() != 0)
                .ifPresentOrElse(
                    finalRuleInput::setDefaultValue, () -> finalRuleInput.setDefaultValue(""));
            Optional.ofNullable(input.getPrefix())
                .filter(item -> item.trim().length() != 0)
                .ifPresentOrElse(finalRuleInput::setPrefix, () -> finalRuleInput.setPrefix(""));
            Optional.ofNullable(input.getMinValue())
                .filter(item -> item.trim().length() != 0)
                .ifPresentOrElse(finalRuleInput::setMin, () -> finalRuleInput.setMin(""));
            Optional.ofNullable(input.getMaxValue())
                .filter(item -> item.trim().length() != 0)
                .ifPresentOrElse(finalRuleInput::setMax, () -> finalRuleInput.setMax(""));
            Optional.ofNullable(input.getTypeValue())
                .filter(item -> item.trim().length() != 0)
                .ifPresentOrElse(finalRuleInput::setType, () -> finalRuleInput.setType(""));
            Optional.ofNullable(input.getSuffix())
                .filter(item -> item.trim().length() != 0)
                .ifPresentOrElse(finalRuleInput::setSuffix, () -> finalRuleInput.setSuffix(""));
            Optional.ofNullable(input.getId())
                .ifPresentOrElse(
                    item -> finalRuleInput.setId(String.valueOf(item)),
                    () -> finalRuleInput.setId(""));
            Optional.ofNullable(input.getIsMandatory())
                .ifPresentOrElse(
                    finalRuleInput::setIsMandatory, () -> finalRuleInput.setIsMandatory(false));
            Optional<String> isTypeBoolean =
                Optional.ofNullable(input.getTypeValue())
                    .filter(
                        item ->
                            item.trim().length() != 0
                                && item.trim()
                                    .equalsIgnoreCase(
                                        com.cpdss.dischargeplan.domain.TypeValue.BOOLEAN
                                            .getType()));
            if (isTypeBoolean.isPresent()) {
              Optional.ofNullable(input.getDefaultValue())
                  .filter(item -> item.trim().length() != 0 && item.trim().equalsIgnoreCase("true"))
                  .ifPresentOrElse(
                      ruleInput::setDefaultValue, () -> ruleInput.setDefaultValue("false"));
            }
            Optional<String> isTypeDropDownOrMultiSelect =
                Optional.ofNullable(input.getTypeValue())
                    .filter(
                        item ->
                            item.trim().length() != 0
                                    && item.trim()
                                        .equalsIgnoreCase(
                                            com.cpdss.dischargeplan.domain.TypeValue.DROPDOWN
                                                .getType())
                                || item.trim()
                                    .equalsIgnoreCase(
                                        com.cpdss.dischargeplan.domain.TypeValue.MULTISELECT
                                            .getType()));
            if (isTypeDropDownOrMultiSelect.isPresent()) {
              Optional<String> isPrefixExist =
                  Optional.ofNullable(input.getPrefix())
                      .filter(
                          item ->
                              item.trim().length() != 0
                                  && item.trim()
                                      .equalsIgnoreCase(
                                          com.cpdss.dischargeplan.domain.RuleMasterData.CargoTank
                                              .getPrefix()));
              Optional<String> isSuffixExist =
                  Optional.ofNullable(input.getSuffix())
                      .filter(
                          item ->
                              item.trim().length() != 0
                                  && item.trim()
                                      .equalsIgnoreCase(
                                          com.cpdss.dischargeplan.domain.RuleMasterData.CargoTank
                                              .getSuffix()));
              Common.RuleDropDownMaster.Builder ruleDropDownMaster =
                  Common.RuleDropDownMaster.newBuilder();
              if (isSuffixExist.isPresent() && isPrefixExist.isPresent()) {
                vesselRuleReply
                    .getCargoTankMasterList()
                    .forEach(
                        cargoTank -> {
                          Optional.ofNullable(cargoTank.getId())
                              .ifPresent(ruleDropDownMaster::setId);
                          Optional.ofNullable(cargoTank.getShortName())
                              .ifPresentOrElse(
                                  ruleDropDownMaster::setValue,
                                  () -> ruleDropDownMaster.setValue(""));
                          ruleInput.addRuleDropDownMaster(ruleDropDownMaster.build());
                        });
              } else {
                Optional<Long> ruleTempId = Optional.ofNullable(ruleList.getParentRuleXId());
                if (ruleTempId.isPresent()) {
                  List<VesselInfo.RuleDropDownValueMaster> filterMasterByRule =
                      vesselRuleReply.getRuleDropDownValueMasterList().stream()
                          .filter(
                              rDropDown ->
                                  rDropDown.getRuleTemplateId() != 0
                                      && ruleTempId.get() != null
                                      && rDropDown.getRuleTemplateId() == ruleTempId.get())
                          .collect(Collectors.toList());
                  filterMasterByRule.forEach(
                      masterDropDownRule -> {
                        Optional.ofNullable(masterDropDownRule.getId())
                            .ifPresent(ruleDropDownMaster::setId);
                        Optional.ofNullable(masterDropDownRule.getValue())
                            .ifPresent(ruleDropDownMaster::setValue);
                        ruleInput.addRuleDropDownMaster(ruleDropDownMaster.build());
                      });
                }
              }
            }
            rulesBuilder.addInputs(finalRuleInput.build());
          }
          rulePlanBuilder.addRules(rulesBuilder.build());
        });
    builder.addRulePlan(rulePlanBuilder);
  }

  public void buildResponseForDefaultDSRules(
      VesselInfo.VesselRuleReply vesselRuleReply, DischargeRuleReply.Builder builder) {
    vesselRuleReply
        .getRulePlanList()
        .forEach(
            rulePlans -> {
              Common.RulePlans.Builder rulePlanBuilder = Common.RulePlans.newBuilder();
              Optional.ofNullable(rulePlans.getHeader()).ifPresent(rulePlanBuilder::setHeader);
              List<Common.Rules> ruleList =
                  rulePlans.getRulesList().stream()
                      .filter(item -> item.getEnable())
                      .collect(Collectors.toList());
              if (ruleList != null && ruleList.size() > 0) {
                rulePlanBuilder.addAllRules(ruleList);
                builder.addRulePlan(rulePlanBuilder);
              }
            });
  }
}
