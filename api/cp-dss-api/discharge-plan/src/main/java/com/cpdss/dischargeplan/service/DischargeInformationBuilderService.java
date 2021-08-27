/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.discharge_plan.DischargeDetails;
import com.cpdss.common.generated.discharge_plan.DischargeRates;
import com.cpdss.common.generated.discharge_plan.DischargeStudyRuleReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.dischargeplan.domain.RuleType;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargeStudyRuleInput;
import com.cpdss.dischargeplan.entity.DischargeStudyRules;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeInformationBuilderService {

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

  /**
   * @param dStudyRulesList
   * @param rulePlanBuilder
   * @param builder
   * @param vesselRuleReply
   */
  public void buildResponseForDSRules(
      List<DischargeStudyRules> dStudyRulesList,
      Common.RulePlans.Builder rulePlanBuilder,
      DischargeStudyRuleReply.Builder builder,
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
          List<DischargeStudyRuleInput> loadableStudyRuleInputs;
          if (ruleList != null && ruleList.getDischargeStudyRuleInputs().size() != 0) {
            loadableStudyRuleInputs =
                ruleList.getDischargeStudyRuleInputs().stream()
                    .sorted(Comparator.comparingLong(DischargeStudyRuleInput::getId))
                    .collect(Collectors.toList());
          } else {
            loadableStudyRuleInputs = new ArrayList<>();
          }
          for (int inputIndex = 0; inputIndex < loadableStudyRuleInputs.size(); inputIndex++) {
            Common.RulesInputs.Builder finalRuleInput = ruleInput;
            DischargeStudyRuleInput input = loadableStudyRuleInputs.get(inputIndex);
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
      VesselInfo.VesselRuleReply vesselRuleReply, DischargeStudyRuleReply.Builder builder) {
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
