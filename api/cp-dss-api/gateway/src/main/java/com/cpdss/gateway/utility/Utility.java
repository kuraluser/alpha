/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.utility;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.gateway.domain.*;
import java.util.*;
import org.springframework.util.CollectionUtils;

public class Utility {

  public static List<RulePlans> buildAdminRulePlan(VesselInfo.VesselRuleReply vesselRuleReply) {
    List<RulePlans> rulePlans = new ArrayList<>();
    vesselRuleReply
        .getRulePlanList()
        .forEach(
            rulePlanList -> {
              RulePlans rulePlan = new RulePlans();
              Optional.of(rulePlanList.getHeader()).ifPresent(rulePlan::setHeader);
              if (!CollectionUtils.isEmpty(rulePlanList.getRulesList())) {
                rulePlan.setRules(buildRules(rulePlanList.getRulesList()));
              }
              rulePlans.add(rulePlan);
            });
    return rulePlans;
  }

  public static List<RulePlans> buildLoadableRulePlan(LoadableStudy.LoadableRuleReply ruleReply) {
    List<RulePlans> rulePlans = new ArrayList<>();
    ruleReply
        .getRulePlanList()
        .forEach(
            rulePlanList -> {
              RulePlans rulePlan = new RulePlans();
              Optional.of(rulePlanList.getHeader()).ifPresent(rulePlan::setHeader);
              if (!CollectionUtils.isEmpty(rulePlanList.getRulesList())) {
                rulePlan.setRules(buildRules(rulePlanList.getRulesList()));
              }
              rulePlans.add(rulePlan);
            });
    return rulePlans;
  }

  static List<Rules> buildRules(List<com.cpdss.common.generated.Common.Rules> rulesList) {
    List<Rules> rules = new ArrayList<>();
    rulesList.forEach(
        rList -> {
          Rules rule = new Rules();
          Optional.ofNullable(rList.getEnable()).ifPresent(rule::setEnable);
          Optional.ofNullable(rList.getDisplayInSettings()).ifPresent(rule::setDisplayInSettings);
          if (isBlankString(rList.getVesselRuleXId())) {
            rule.setVesselRuleXId(rList.getVesselRuleXId());
          }
          if (isBlankString(rList.getId())) {
            rule.setId(rList.getId());
          }
          Optional.ofNullable(rList.getIsHardRule()).ifPresent(rule::setIsHardRule);
          Optional.ofNullable(rList.getNumericPrecision()).ifPresent(rule::setNumericPrecision);
          Optional.ofNullable(rList.getNumericScale()).ifPresent(rule::setNumericScale);
          Optional.ofNullable(rList.getRuleTemplateId()).ifPresent(rule::setRuleTemplateId);
          Optional.ofNullable(rList.getRuleType()).ifPresent(rule::setRuleType);
          Optional.ofNullable(rList.getEnable()).ifPresent(rule::setEnable);
          if (!CollectionUtils.isEmpty(rList.getInputsList())) {
            rule.setInputs(buildRuleInputs(rList.getInputsList()));
          }
          rules.add(rule);
        });
    return rules;
  }

  static List<RulesInputs> buildRuleInputs(
      List<com.cpdss.common.generated.Common.RulesInputs> inputsList) {
    List<RulesInputs> ruleInputsList = new ArrayList<>();
    inputsList.forEach(
        rInputsList -> {
          RulesInputs rulesInputs = new RulesInputs();
          if (isBlankString(rInputsList.getDefaultValue())) {
            rulesInputs.setDefaultValue(rInputsList.getDefaultValue());
          }
          if (isBlankString(rInputsList.getMax())) {
            rulesInputs.setMax(rInputsList.getMax());
          }
          if (isBlankString(rInputsList.getMin())) {
            rulesInputs.setMin(rInputsList.getMin());
          }
          if (isBlankString(rInputsList.getPrefix())) {
            rulesInputs.setPrefix(rInputsList.getPrefix());
          }
          if (isBlankString(rInputsList.getPrefix())) {
            rulesInputs.setPrefix(rInputsList.getPrefix());
          }
          if (isBlankString(rInputsList.getSuffix())) {
            rulesInputs.setSuffix(rInputsList.getSuffix());
          }
          if (isBlankString(rInputsList.getId())) {
            rulesInputs.setId(rInputsList.getId());
          }
          Optional.ofNullable(rInputsList.getIsMandatory()).ifPresent(rulesInputs::setIsMandatory);
          Optional.ofNullable(rInputsList.getType()).ifPresent(rulesInputs::setType);
          if (rInputsList.getType() != null
              && (rInputsList.getType().trim().equalsIgnoreCase(TypeValue.DROPDOWN.getType())
                  || rInputsList.getType().trim().equalsIgnoreCase(TypeValue.MULTISELECT.getType()))
              && rInputsList.getRuleDropDownMasterList() != null
              && rInputsList.getRuleDropDownMasterList().size() > 0) {
            List<RuleDropDownMaster> ruleDropDownMasters = new ArrayList<>();
            rInputsList
                .getRuleDropDownMasterList()
                .forEach(
                    ruleDropDownValue -> {
                      RuleDropDownMaster ruleDropDownMaster = new RuleDropDownMaster();
                      Optional.ofNullable(ruleDropDownValue.getId())
                          .ifPresent(ruleDropDownMaster::setId);
                      Optional.ofNullable(ruleDropDownValue.getValue())
                          .ifPresent(ruleDropDownMaster::setValue);
                      ruleDropDownMasters.add(ruleDropDownMaster);
                    });
            rulesInputs.setRuleDropDownMaster(ruleDropDownMasters);
          }
          ruleInputsList.add(rulesInputs);
        });
    return ruleInputsList;
  }

  public static void buildRuleListForSave(
      com.cpdss.gateway.domain.RuleRequest vesselRuleRequest,
      VesselInfo.VesselRuleRequest.Builder vesselRuleBuilder,
      LoadableStudy.LoadableRuleRequest.Builder loadableRuleRequestBuilder,
      boolean isAdminRule) {

    if (vesselRuleRequest != null && !CollectionUtils.isEmpty(vesselRuleRequest.getPlan())) {
      vesselRuleRequest
          .getPlan()
          .forEach(
              rulePlan -> {
                if (!CollectionUtils.isEmpty(rulePlan.getRules())) {
                  com.cpdss.common.generated.Common.RulePlans.Builder rulePlanBuilder =
                      com.cpdss.common.generated.Common.RulePlans.newBuilder();
                  rulePlan
                      .getRules()
                      .forEach(
                          rule -> {
                            com.cpdss.common.generated.Common.Rules.Builder ruleBuilder =
                                com.cpdss.common.generated.Common.Rules.newBuilder();
                            Optional.ofNullable(rule.getDisable())
                                .ifPresent(ruleBuilder::setDisable);
                            Optional.ofNullable(rule.getDisplayInSettings())
                                .ifPresent(ruleBuilder::setDisplayInSettings);
                            Optional.ofNullable(rule.getEnable()).ifPresent(ruleBuilder::setEnable);
                            Optional.ofNullable(rule.getId()).ifPresent(ruleBuilder::setId);
                            Optional.ofNullable(rule.getRuleTemplateId())
                                .ifPresent(ruleBuilder::setRuleTemplateId);
                            Optional.ofNullable(rule.getRuleType())
                                .ifPresent(ruleBuilder::setRuleType);
                            Optional.ofNullable(rule.getRuleType())
                                .ifPresent(ruleBuilder::setRuleType);
                            Optional.ofNullable(rule.getVesselRuleXId())
                                .ifPresent(ruleBuilder::setVesselRuleXId);
                            Optional.ofNullable(rule.getIsHardRule())
                                .ifPresent(ruleBuilder::setIsHardRule);
                            Optional.ofNullable(rule.getNumericPrecision())
                            .ifPresent(ruleBuilder::setNumericPrecision);
                            Optional.ofNullable(rule.getNumericScale())
                            .ifPresent(ruleBuilder::setNumericScale);
                            rule.getInputs()
                                .forEach(
                                    input -> {
                                      com.cpdss.common.generated.Common.RulesInputs.Builder
                                          ruleInputBuilder =
                                              com.cpdss.common.generated.Common.RulesInputs
                                                  .newBuilder();
                                      Optional.ofNullable(input.getDefaultValue())
                                          .ifPresent(ruleInputBuilder::setDefaultValue);
                                      Optional.ofNullable(input.getId())
                                          .ifPresent(ruleInputBuilder::setId);
                                      Optional.ofNullable(input.getMax())
                                          .ifPresent(ruleInputBuilder::setMax);
                                      Optional.ofNullable(input.getMin())
                                          .ifPresent(ruleInputBuilder::setMin);
                                      Optional.ofNullable(input.getPrefix())
                                          .ifPresent(ruleInputBuilder::setPrefix);
                                      Optional.ofNullable(input.getSuffix())
                                          .ifPresent(ruleInputBuilder::setSuffix);
                                      Optional.ofNullable(input.getType())
                                          .ifPresent(ruleInputBuilder::setType);
                                      Optional.ofNullable(input.getValue())
                                          .ifPresent(ruleInputBuilder::setValue);
                                      Optional.ofNullable(input.getIsMandatory())
                                          .ifPresent(ruleInputBuilder::setIsMandatory);
                                      ruleBuilder.addInputs(ruleInputBuilder);
                                    });
                            rulePlanBuilder.addRules(ruleBuilder);
                          });
                  if ((isAdminRule)) {
                    vesselRuleBuilder.addRulePlan(rulePlanBuilder);
                  } else {
                    loadableRuleRequestBuilder.addRulePlan(rulePlanBuilder);
                  }
                }
              });
    }
  }

  static Boolean isBlankString(String value) {
    return value != null && value.trim() != "";
  }
}
