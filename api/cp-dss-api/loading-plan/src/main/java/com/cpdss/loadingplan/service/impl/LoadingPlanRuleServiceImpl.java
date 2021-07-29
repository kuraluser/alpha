/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.LOADING_RULE_MASTER_ID;
import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.common.RuleMasterSection;
import com.cpdss.loadingplan.common.RuleType;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingRule;
import com.cpdss.loadingplan.entity.LoadingRuleInput;
import com.cpdss.loadingplan.repository.LoadingRuleInputRepository;
import com.cpdss.loadingplan.repository.LoadingRuleRepository;
import com.cpdss.loadingplan.service.LoadingInformationService;
import com.cpdss.loadingplan.service.LoadingPlanRuleService;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@Transactional
public class LoadingPlanRuleServiceImpl implements LoadingPlanRuleService {

  @Autowired LoadingInformationService loadingInformationService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Autowired LoadingRuleRepository loadingRuleRepository;

  @Autowired LoadingRuleInputRepository loadingRuleInputRepository;

  public void getOrSaveRulesForLoadingPlan(
      LoadingPlanModels.LoadingPlanRuleRequest request,
      LoadingPlanModels.LoadingPlanRuleReply.Builder builder)
      throws GenericServiceException {
    if (!RuleMasterSection.Loading.getId().equals(request.getSectionId())) {
      throw new GenericServiceException(
          "Invalid Rule Master Id Passed! Only Accept Loading Rule.",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    Optional<LoadingInformation> loadingInformation =
        this.loadingInformationService.getLoadingInformation(request.getLoadingInfoId());

    if (!loadingInformation.isPresent()) {
      log.error(
          "Failed to get loading information for get or save rule", request.getLoadingInfoId());
      throw new GenericServiceException(
          "Loading Information with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    VesselInfo.VesselRuleRequest.Builder vesselRuleBuilder =
        VesselInfo.VesselRuleRequest.newBuilder();
    vesselRuleBuilder.setSectionId(request.getSectionId());
    vesselRuleBuilder.setVesselId(request.getVesselId());
    vesselRuleBuilder.setIsNoDefaultRule(true);
    VesselInfo.VesselRuleReply vesselRuleReply =
        this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(vesselRuleBuilder.build());
    if (!SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get loadable study rule Details ",
          vesselRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.parseInt(vesselRuleReply.getResponseStatus().getCode())));
    }
    if (!CollectionUtils.isEmpty(request.getRulePlanList())) {
      log.info("Save Loading Info Rules");
      List<LoadingRule> loadingRuleList = new ArrayList<>();
      request
          .getRulePlanList()
          .forEach(
              rulePlans -> {
                rulePlans
                    .getRulesList()
                    .forEach(
                        rule -> {
                          LoadingRule loadingRules = new LoadingRule();
                          if (rule.getId() != null && rule.getId().trim() != "") {
                            Optional<LoadingRule> rVesselMapping =
                                loadingRuleRepository.findById(Long.valueOf(rule.getId()));
                            if (rVesselMapping.isPresent()) {
                              loadingRules = rVesselMapping.get();
                            }
                          }
                          loadingRules.setLoadingXid(loadingInformation.get().getId());
                          loadingRules.setIsActive(true);
                          Optional.ofNullable(rule.getDisplayInSettings())
                              .ifPresent(loadingRules::setDisplayInSettings);
                          Optional.ofNullable(rule.getEnable())
                              .ifPresent(loadingRules::setIsEnable);
                          Optional.ofNullable(rule.getIsHardRule())
                              .ifPresent(loadingRules::setIsHardRule);
                          Optional.ofNullable(rule.getNumericPrecision())
                              .ifPresent(loadingRules::setNumericPrecision);
                          Optional.ofNullable(rule.getNumericScale())
                              .ifPresent(loadingRules::setNumericScale);
                          LoadingRule finalLoadingRules = loadingRules;
                          Optional.ofNullable(rule.getRuleTemplateId())
                              .ifPresent(
                                  item -> finalLoadingRules.setParentRuleXid(Long.parseLong(item)));
                          loadingRules.setVesselXid(request.getVesselId());

                          if (rule.getRuleType() != null
                              && rule.getRuleType()
                                  .equalsIgnoreCase(RuleType.ABSOLUTE.getRuleType())) {
                            loadingRules.setRuleTypeXid(RuleType.ABSOLUTE.getId());
                          }
                          if (rule.getRuleType() != null
                              && rule.getRuleType()
                                  .equalsIgnoreCase(RuleType.PREFERABLE.getRuleType())) {
                            loadingRules.setRuleTypeXid(RuleType.PREFERABLE.getId());
                          }
                          Optional.ofNullable(rule.getVesselRuleXId())
                              .ifPresent(
                                  vesselRuleXId ->
                                      finalLoadingRules.setVesselRuleXid(
                                          Long.parseLong(vesselRuleXId)));
                          List<LoadingRuleInput> ruleVesselMappingInputList = new ArrayList<>();
                          for (Common.RulesInputs input : rule.getInputsList()) {
                            LoadingRuleInput ruleTemplateInput = new LoadingRuleInput();
                            if (input.getId() != null && input.getId().trim() != "") {
                              Optional<LoadingRuleInput> rTemplateInput =
                                  loadingRuleInputRepository.findById(Long.valueOf(input.getId()));
                              if (rTemplateInput.isPresent()) {
                                ruleTemplateInput = rTemplateInput.get();
                              }
                            }
                            Optional.ofNullable(input.getDefaultValue())
                                .ifPresent(ruleTemplateInput::setDefaultValue);
                            Optional.ofNullable(input.getMax())
                                .ifPresent(ruleTemplateInput::setMaxValue);
                            Optional.ofNullable(input.getMin())
                                .ifPresent(ruleTemplateInput::setMinValue);
                            Optional.ofNullable(input.getSuffix())
                                .ifPresent(ruleTemplateInput::setSuffix);
                            Optional.ofNullable(input.getPrefix())
                                .ifPresent(ruleTemplateInput::setPrefix);
                            Optional.ofNullable(input.getType())
                                .ifPresent(ruleTemplateInput::setTypeValue);
                            Optional.ofNullable(input.getIsMandatory())
                                .ifPresent(ruleTemplateInput::setIsMandatory);
                            ruleTemplateInput.setIsActive(true);
                            ruleTemplateInput.setLoadingRule(loadingRules);
                            try {
                              if (input.getType() != null
                                  && (input
                                          .getType()
                                          .trim()
                                          .equalsIgnoreCase(
                                              com.cpdss.loadingplan.common.TypeValue.DROPDOWN
                                                  .getType())
                                      || input
                                          .getType()
                                          .trim()
                                          .equalsIgnoreCase(
                                              com.cpdss.loadingplan.common.TypeValue.MULTISELECT
                                                  .getType()))
                                  && input.getDefaultValue() != null
                                  && input.getDefaultValue().trim() != "") {
                                if (input.getSuffix() != null
                                    && input.getPrefix() != null
                                    && input
                                        .getSuffix()
                                        .trim()
                                        .equalsIgnoreCase(
                                            com.cpdss.loadingplan.common.RuleMasterData.CargoTank
                                                .getSuffix())
                                    && input
                                        .getPrefix()
                                        .equalsIgnoreCase(
                                            com.cpdss.loadingplan.common.RuleMasterData.CargoTank
                                                .getPrefix())) {

                                  this.ruleMasterDropDownValidation(
                                      vesselRuleReply.getRuleDropDownValueMasterList(),
                                      vesselRuleReply.getCargoTankMasterList(),
                                      true,
                                      input,
                                      ruleTemplateInput,
                                      rule);
                                } else {
                                  this.ruleMasterDropDownValidation(
                                      vesselRuleReply.getRuleDropDownValueMasterList(),
                                      vesselRuleReply.getCargoTankMasterList(),
                                      false,
                                      input,
                                      ruleTemplateInput,
                                      rule);
                                }
                              }
                            } catch (GenericServiceException e) {
                              throw new RuntimeException(
                                  "Master rule drop down value does not exist");
                            }
                            ruleVesselMappingInputList.add(ruleTemplateInput);
                          }
                          loadingRules.setLoadingRuleInputs(ruleVesselMappingInputList);
                          loadingRuleList.add(loadingRules);
                        });
              });
      log.info("Saving Loading Rule List {}", loadingRuleList.size());
      loadingRuleRepository.saveAll(loadingRuleList);
    }
    updateDisplayInSettingsInLoadingInfoRules(vesselRuleReply);
    List<Long> ruleListId =
        vesselRuleReply.getRulePlanList().stream()
            .flatMap(rulesList -> rulesList.getRulesList().stream())
            .map(rules -> Long.parseLong(rules.getVesselRuleXId()))
            .collect(Collectors.toList());
    List<LoadingRule> loadingRulesList =
        loadingRuleRepository
            .findByLoadingXidAndVesselXidAndIsActiveTrueAndVesselRuleXidInOrderById(
                loadingInformation.get().getId(), request.getVesselId(), ruleListId);
    if (loadingRulesList.size() > 0) {
      log.info("Loading information rules fetched, Size {}", loadingRulesList.size());
      vesselRuleReply
          .getRulePlanList()
          .forEach(
              rulePlans -> {
                Common.RulePlans.Builder rulePlanBuider = Common.RulePlans.newBuilder();
                Optional.ofNullable(rulePlans.getHeader())
                    .ifPresent(item -> rulePlanBuider.setHeader(item));
                List<Long> ruleId =
                    rulePlans.getRulesList().stream()
                        .map(rules -> Long.parseLong(rules.getVesselRuleXId()))
                        .collect(Collectors.toList());
                List<LoadingRule> lStudyRulesList =
                    loadingRulesList.stream()
                        .filter(lRuleList -> ruleId.contains(lRuleList.getVesselRuleXid()))
                        .collect(Collectors.toList());
                buildResponseForRules(
                    lStudyRulesList, ruleId, rulePlanBuider, builder, vesselRuleReply);
              });
    } else {
      log.info(
          "Fetch default loading plan Rules, Size {}", vesselRuleReply.getRulePlanList().size());
      vesselRuleReply
          .getRulePlanList()
          .forEach(
              rulePlans -> {
                builder.addRulePlan(rulePlans);
              });
    }

    builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  private void ruleMasterDropDownValidation(
      List<VesselInfo.RuleDropDownValueMaster> listOfDropDownValue,
      List<VesselInfo.CargoTankMaster> cargoTankMaster,
      boolean isCargoTankMaster,
      Common.RulesInputs input,
      LoadingRuleInput ruleTemplateInput,
      Common.Rules rule)
      throws GenericServiceException {
    String value = "";
    List<VesselInfo.RuleDropDownValueMaster> filterMasterByRule =
        listOfDropDownValue.stream()
            .filter(
                rDropDown ->
                    rDropDown.getRuleTemplateId() != 0
                        && rule.getRuleTemplateId() != null
                        && rDropDown.getRuleTemplateId()
                            == Long.parseLong(rule.getRuleTemplateId().trim()))
            .collect(Collectors.toList());
    if (input.getDefaultValue().contains(",")) {
      String[] masterIds = input.getDefaultValue().split(",");
      for (int id = 0; id < masterIds.length; id++) {
        int finalId = id;
        if (isCargoTankMaster) {
          if (cargoTankMaster.stream()
              .map(VesselInfo.CargoTankMaster::getId)
              .filter(item -> item == Long.parseLong(masterIds[finalId].trim()))
              .findFirst()
              .isPresent()) {
            if (masterIds.length - 1 != id) {
              value += masterIds[id] + ",";
            } else {
              value += masterIds[id];
            }
          } else {
            throw new GenericServiceException(
                "Rulemaster master with given id does not exist",
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST);
          }
        } else {
          if (filterMasterByRule.stream()
              .map(VesselInfo.RuleDropDownValueMaster::getId)
              .filter(item -> item == Long.parseLong(masterIds[finalId].trim()))
              .findFirst()
              .isPresent()) {
            if (masterIds.length - 1 != id) {
              value += masterIds[id] + ",";
            } else {
              value += masterIds[id];
            }
          } else {
            throw new GenericServiceException(
                "Rulemaster master with given id does not exist",
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST);
          }
        }
      }
      ruleTemplateInput.setDefaultValue(value);
    } else {
      if (isCargoTankMaster) {
        if (!cargoTankMaster.stream()
            .map(VesselInfo.CargoTankMaster::getId)
            .filter(item -> item == Long.parseLong(input.getDefaultValue().trim()))
            .findFirst()
            .isPresent()) {
          throw new GenericServiceException(
              "Cargo master with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
      } else {
        if (!filterMasterByRule.stream()
            .map(VesselInfo.RuleDropDownValueMaster::getId)
            .filter(item -> item == Long.parseLong(input.getDefaultValue().trim()))
            .findFirst()
            .isPresent()) {
          throw new GenericServiceException(
              "Rulemaster master with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
      }
      ruleTemplateInput.setDefaultValue(input.getDefaultValue());
    }
  }

  private void updateDisplayInSettingsInLoadingInfoRules(
      VesselInfo.VesselRuleReply vesselRuleReply) {
    Set<Long> setOfSelectedDisplayInSetting = new LinkedHashSet<>();
    Set<Long> setOfDeselectedDisplayInSetting = new LinkedHashSet<>();
    vesselRuleReply.getRulePlanList().stream()
        .forEach(
            item -> {
              Set<Long> selectedDisplayInSetting =
                  item.getRulesList().stream()
                      .filter(
                          rules ->
                              rules.getDisplayInSettings()
                                  && rules.getVesselRuleXId() != null
                                  && rules.getVesselRuleXId() != "")
                      .map(id -> Long.parseLong(id.getVesselRuleXId()))
                      .collect(Collectors.toSet());
              Set<Long> deselectedDisplayInSetting =
                  item.getRulesList().stream()
                      .filter(
                          rules ->
                              !rules.getDisplayInSettings()
                                  && rules.getVesselRuleXId() != null
                                  && rules.getVesselRuleXId() != "")
                      .map(id -> Long.parseLong(id.getVesselRuleXId()))
                      .collect(Collectors.toSet());
              if (selectedDisplayInSetting != null && selectedDisplayInSetting.size() > 0) {
                setOfSelectedDisplayInSetting.addAll(selectedDisplayInSetting);
              }
              if (deselectedDisplayInSetting != null && deselectedDisplayInSetting.size() > 0) {
                setOfDeselectedDisplayInSetting.addAll(deselectedDisplayInSetting);
              }
            });
    loadingRuleRepository.updateDisplayInSettingsInLoadingRules(
        true, setOfSelectedDisplayInSetting);
    loadingRuleRepository.updateDisplayInSettingsInLoadingRules(
        false, setOfDeselectedDisplayInSetting);
  }

  private void buildResponseForRules(
      List<LoadingRule> lStudyRulesList,
      List<Long> ruleId,
      com.cpdss.common.generated.Common.RulePlans.Builder rulePlanBuider,
      LoadingPlanModels.LoadingPlanRuleReply.Builder builder,
      VesselInfo.VesselRuleReply vesselRuleReply) {
    for (int ruleIndex = 0; ruleIndex < lStudyRulesList.size(); ruleIndex++) {
      Common.Rules.Builder rulesBuilder = Common.Rules.newBuilder();
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getIsEnable())
          .ifPresent(item -> rulesBuilder.setEnable(item));
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getDisplayInSettings())
          .ifPresent(item -> rulesBuilder.setDisplayInSettings(item));
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getId())
          .ifPresent(item -> rulesBuilder.setId(String.valueOf(item)));
      if (lStudyRulesList.get(ruleIndex).getRuleTypeXid() != null
          && lStudyRulesList.get(ruleIndex).getRuleTypeXid().equals(RuleType.ABSOLUTE.getId())) {
        rulesBuilder.setRuleType(RuleType.ABSOLUTE.getRuleType());
      }
      if (lStudyRulesList.get(ruleIndex).getRuleTypeXid() != null
          && lStudyRulesList.get(ruleIndex).getRuleTypeXid().equals(RuleType.PREFERABLE.getId())) {
        rulesBuilder.setRuleType(RuleType.PREFERABLE.getRuleType());
      }
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getIsHardRule())
          .ifPresent(item -> rulesBuilder.setIsHardRule(item));
      if (lStudyRulesList.get(ruleIndex).getIsHardRule() == null) {
        rulesBuilder.setIsHardRule(false);
      }
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getVesselRuleXid())
          .ifPresent(item -> rulesBuilder.setVesselRuleXId(String.valueOf(item)));
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getRuleTypeXid())
          .ifPresent(item -> rulesBuilder.setRuleTemplateId(String.valueOf(item)));
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getNumericPrecision())
          .ifPresent(item -> rulesBuilder.setNumericPrecision(item));
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getNumericScale())
          .ifPresent(item -> rulesBuilder.setNumericScale(item));
      Common.RulesInputs.Builder ruleInput = Common.RulesInputs.newBuilder();
      for (int inputIndex = 0;
          inputIndex < lStudyRulesList.get(ruleIndex).getLoadingRuleInputs().size();
          inputIndex++) {
        Common.RulesInputs.Builder finalRuleInput = ruleInput;
        Optional.ofNullable(
                lStudyRulesList
                    .get(ruleIndex)
                    .getLoadingRuleInputs()
                    .get(inputIndex)
                    .getDefaultValue())
            .ifPresent(item -> finalRuleInput.setDefaultValue(item));
        Optional.ofNullable(
                lStudyRulesList.get(ruleIndex).getLoadingRuleInputs().get(inputIndex).getPrefix())
            .ifPresent(item -> finalRuleInput.setPrefix(item));
        Optional.ofNullable(
                lStudyRulesList.get(ruleIndex).getLoadingRuleInputs().get(inputIndex).getMinValue())
            .ifPresent(item -> finalRuleInput.setMin(item));
        Optional.ofNullable(
                lStudyRulesList.get(ruleIndex).getLoadingRuleInputs().get(inputIndex).getMaxValue())
            .ifPresent(item -> finalRuleInput.setMax(item));
        Optional.ofNullable(
                lStudyRulesList
                    .get(ruleIndex)
                    .getLoadingRuleInputs()
                    .get(inputIndex)
                    .getTypeValue())
            .ifPresent(item -> finalRuleInput.setType(item));
        Optional.ofNullable(
                lStudyRulesList.get(ruleIndex).getLoadingRuleInputs().get(inputIndex).getSuffix())
            .ifPresent(item -> finalRuleInput.setSuffix(item));
        Optional.ofNullable(
                lStudyRulesList.get(ruleIndex).getLoadingRuleInputs().get(inputIndex).getId())
            .ifPresent(item -> finalRuleInput.setId(String.valueOf(item)));
        Optional.ofNullable(
                lStudyRulesList
                    .get(ruleIndex)
                    .getLoadingRuleInputs()
                    .get(inputIndex)
                    .getIsMandatory())
            .ifPresent(finalRuleInput::setIsMandatory);
        if (lStudyRulesList.get(ruleIndex).getLoadingRuleInputs().get(inputIndex).getTypeValue()
                != null
            && lStudyRulesList
                .get(ruleIndex)
                .getLoadingRuleInputs()
                .get(inputIndex)
                .getTypeValue()
                .equalsIgnoreCase(com.cpdss.loadingplan.common.TypeValue.BOOLEAN.getType())
            && lStudyRulesList
                    .get(ruleIndex)
                    .getLoadingRuleInputs()
                    .get(inputIndex)
                    .getDefaultValue()
                == null) {
          finalRuleInput.setDefaultValue("false");
        }
        if (lStudyRulesList.get(ruleIndex).getLoadingRuleInputs().get(inputIndex).getTypeValue()
                != null
            && (lStudyRulesList
                    .get(ruleIndex)
                    .getLoadingRuleInputs()
                    .get(inputIndex)
                    .getTypeValue()
                    .trim()
                    .equalsIgnoreCase(com.cpdss.loadingplan.common.TypeValue.DROPDOWN.getType())
                || lStudyRulesList
                    .get(ruleIndex)
                    .getLoadingRuleInputs()
                    .get(inputIndex)
                    .getTypeValue()
                    .trim()
                    .equalsIgnoreCase(
                        com.cpdss.loadingplan.common.TypeValue.MULTISELECT.getType()))) {
          Common.RuleDropDownMaster.Builder ruleDropDownMaster =
              Common.RuleDropDownMaster.newBuilder();
          if (lStudyRulesList.get(ruleIndex).getLoadingRuleInputs().get(inputIndex).getSuffix()
                  != null
              && lStudyRulesList.get(ruleIndex).getLoadingRuleInputs().get(inputIndex).getPrefix()
                  != null
              && lStudyRulesList
                  .get(ruleIndex)
                  .getLoadingRuleInputs()
                  .get(inputIndex)
                  .getSuffix()
                  .trim()
                  .equalsIgnoreCase(
                      com.cpdss.loadingplan.common.RuleMasterData.CargoTank.getSuffix())
              && lStudyRulesList
                  .get(ruleIndex)
                  .getLoadingRuleInputs()
                  .get(inputIndex)
                  .getPrefix()
                  .trim()
                  .equalsIgnoreCase(
                      com.cpdss.loadingplan.common.RuleMasterData.CargoTank.getPrefix())) {
            vesselRuleReply
                .getCargoTankMasterList()
                .forEach(
                    cargoTank -> {
                      Optional.ofNullable(cargoTank.getId()).ifPresent(ruleDropDownMaster::setId);
                      Optional.ofNullable(cargoTank.getShortName())
                          .ifPresent(ruleDropDownMaster::setValue);
                      ruleInput.addRuleDropDownMaster(ruleDropDownMaster.build());
                    });
          } else {
            Optional<Long> ruleTempId =
                Optional.ofNullable(lStudyRulesList.get(ruleIndex).getParentRuleXid());
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
      rulePlanBuider.addRules(rulesBuilder.build());
    }
    builder.addRulePlan(rulePlanBuider);
  }

  @Override
  public LoadingPlanModels.LoadingPlanRuleReply getLoadingPlanRuleForAlgo(
      Long vesselId, Long loadingInfoId) {
    LoadingPlanModels.LoadingPlanRuleRequest.Builder request =
        LoadingPlanModels.LoadingPlanRuleRequest.newBuilder();
    request.setLoadingInfoId(loadingInfoId);
    request.setSectionId(LOADING_RULE_MASTER_ID);
    LoadingPlanModels.LoadingPlanRuleReply.Builder builder =
        LoadingPlanModels.LoadingPlanRuleReply.newBuilder();
    try {
      this.getOrSaveRulesForLoadingPlan(request.build(), builder);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
    return builder.build();
  }
}
