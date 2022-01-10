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
import com.cpdss.loadingplan.common.RuleMasterData;
import com.cpdss.loadingplan.common.RuleMasterSection;
import com.cpdss.loadingplan.common.RuleType;
import com.cpdss.loadingplan.common.TypeValue;
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
    vesselRuleBuilder.setIsFetchEnabledRules(false);
    vesselRuleBuilder.setIsNoDefaultRule(true);
    VesselInfo.VesselRuleReply vesselRuleReply =
        this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(vesselRuleBuilder.build());
    if (!SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get vessel rule Details ",
          vesselRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.parseInt(vesselRuleReply.getResponseStatus().getCode())));
    }
    if (!CollectionUtils.isEmpty(request.getRulePlanList())) {
      log.info("Save Loading Info Rules");
      saveRulesAgainstLoading(request, loadingInformation, vesselRuleReply);
    }
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
                buildResponseForRules(lStudyRulesList, rulePlanBuider, builder, vesselRuleReply);
              });
    } else {
      log.info(
          "Fetch default loading plan Rules, Size {}", vesselRuleReply.getRulePlanList().size());
      vesselRuleReply
          .getRulePlanList()
          .forEach(
              rulePlans -> {
                Common.RulePlans.Builder rulePlanBuilder = Common.RulePlans.newBuilder();
                Optional.ofNullable(rulePlans.getHeader()).ifPresent(rulePlanBuilder::setHeader);
                List<Common.Rules> ruleList =
                    rulePlans.getRulesList().stream().collect(Collectors.toList());
                if (ruleList != null && ruleList.size() > 0) {
                  rulePlanBuilder.addAllRules(ruleList);
                  builder.addRulePlan(rulePlanBuilder);
                }
              });
    }
    builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  private void saveRulesAgainstLoading(
      LoadingPlanModels.LoadingPlanRuleRequest request,
      Optional<LoadingInformation> loadingInformation,
      VesselInfo.VesselRuleReply vesselRuleReply) {
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
                        Optional<String> isRuleTemplateIdExist =
                            Optional.ofNullable(rule.getRuleTemplateId())
                                .filter(item -> item.trim().length() != 0);
                        if (rule.getId() != null && rule.getId().trim().length() != 0) {
                          Optional<LoadingRule> rVesselMapping =
                              loadingRuleRepository.findById(Long.valueOf(rule.getId()));
                          if (rVesselMapping.isPresent()) {
                            loadingRules = rVesselMapping.get();
                          } else {
                            log.info("No record exist for this id in loading  rule table");
                            throw new RuntimeException(
                                "No record exist for this id in loading  rule table");
                          }
                        } else {
                          if (isRuleTemplateIdExist.isPresent()) {
                            Optional<LoadingRule> loadableStudyRulesRecord =
                                loadingRuleRepository.checkIsLoadingRuleExist(
                                    loadingInformation.get().getId(),
                                    true,
                                    Long.valueOf(rule.getRuleTemplateId()));
                            if (loadableStudyRulesRecord.isPresent()) {
                              log.info(
                                  "Duplicate row can't insert for given vessel id and rule template");
                              throw new RuntimeException(
                                  "Duplicate row can't insert for given vessel id and rule template");
                            }
                          }
                        }
                        loadingRules.setLoadingXid(loadingInformation.get().getId());
                        loadingRules.setIsActive(true);
                        LoadingRule finalLoadingRules = loadingRules;
                        Optional.ofNullable(rule.getDisplayInSettings())
                            .ifPresentOrElse(
                                loadingRules::setDisplayInSettings,
                                () -> finalLoadingRules.setDisplayInSettings(false));
                        Optional.ofNullable(rule.getEnable())
                            .ifPresentOrElse(
                                loadingRules::setIsEnable,
                                () -> finalLoadingRules.setIsEnable(false));
                        Optional.ofNullable(rule.getIsHardRule())
                            .ifPresentOrElse(
                                loadingRules::setIsHardRule,
                                () -> finalLoadingRules.setIsHardRule(false));
                        Optional.ofNullable(rule.getNumericPrecision())
                            .ifPresent(loadingRules::setNumericPrecision);
                        Optional.ofNullable(rule.getNumericScale())
                            .ifPresent(loadingRules::setNumericScale);
                        Optional.ofNullable(rule.getRuleTemplateId())
                            .ifPresent(
                                item -> finalLoadingRules.setParentRuleXid(Long.parseLong(item)));
                        loadingRules.setVesselXid(request.getVesselId());
                        if (!CollectionUtils.isEmpty(vesselRuleReply.getRuleTypeMasterList())
                            && rule.getRuleType() != null
                            && rule.getRuleType().trim() != "") {
                          Optional<VesselInfo.RuleTypeMaster> ruleType =
                              vesselRuleReply.getRuleTypeMasterList().stream()
                                  .filter(
                                      rType ->
                                          rType.getRuleType().equalsIgnoreCase(rule.getRuleType()))
                                  .findAny();
                          ruleType.orElseThrow(RuntimeException::new);
                          loadingRules.setRuleTypeXid(ruleType.get().getId());
                        } else {
                          log.info("Rule Type can't be null");
                          throw new RuntimeException("Rule type can't be null");
                        }
                        Optional.ofNullable(rule.getVesselRuleXId())
                            .ifPresent(
                                vesselRuleXId ->
                                    finalLoadingRules.setVesselRuleXid(
                                        Long.parseLong(vesselRuleXId)));
                        List<LoadingRuleInput> ruleVesselMappingInputList = new ArrayList<>();
                        for (Common.RulesInputs input : rule.getInputsList()) {
                          LoadingRuleInput ruleTemplateInput = new LoadingRuleInput();
                          if (input.getId() != null && input.getId().length() != 0) {
                            Optional<LoadingRuleInput> rTemplateInput =
                                loadingRuleInputRepository.findById(Long.valueOf(input.getId()));
                            if (rTemplateInput.isPresent()) {
                              ruleTemplateInput = rTemplateInput.get();
                            } else {
                              log.info(
                                  "No record exist for this id in rule loading rule input table");
                              throw new RuntimeException(
                                  "No record exist for this id in rule loading rule input input table");
                            }
                          }
                          LoadingRuleInput finalRuleTemplateInput = ruleTemplateInput;
                          Optional.ofNullable(input.getDefaultValue())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresentOrElse(
                                  ruleTemplateInput::setDefaultValue,
                                  () -> finalRuleTemplateInput.setDefaultValue(null));
                          Optional.ofNullable(input.getMax())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresent(ruleTemplateInput::setMaxValue);
                          Optional.ofNullable(input.getMin())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresent(ruleTemplateInput::setMinValue);
                          Optional.ofNullable(input.getSuffix())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresent(ruleTemplateInput::setSuffix);
                          Optional.ofNullable(input.getPrefix())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresent(ruleTemplateInput::setPrefix);
                          Optional.ofNullable(input.getType())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresent(ruleTemplateInput::setTypeValue);
                          Optional.ofNullable(input.getIsMandatory())
                              .ifPresentOrElse(
                                  ruleTemplateInput::setIsMandatory,
                                  () -> finalRuleTemplateInput.setIsMandatory(false));
                          ruleTemplateInput.setIsActive(true);
                          ruleTemplateInput.setLoadingRule(loadingRules);
                          Optional<String> isTypeDropDownOrMultiSelect =
                              Optional.ofNullable(input.getType())
                                  .filter(
                                      value ->
                                          value.trim().length() != 0
                                                  && value
                                                      .trim()
                                                      .equalsIgnoreCase(
                                                          com.cpdss.loadingplan.common.TypeValue
                                                              .DROPDOWN
                                                              .getType())
                                              || value
                                                  .trim()
                                                  .equalsIgnoreCase(
                                                      com.cpdss.loadingplan.common.TypeValue
                                                          .MULTISELECT
                                                          .getType()));

                          if (isTypeDropDownOrMultiSelect.isPresent()) {
                            Optional<String> isPrefixExistCTankOne =
                                Optional.ofNullable(input.getPrefix())
                                    .filter(
                                        item ->
                                            item.trim().length() != 0
                                                && item.trim()
                                                    .equalsIgnoreCase(
                                                        RuleMasterData.CargoTankOne.getPrefix()));
                            Optional<String> isSuffixExistCTankOne =
                                Optional.ofNullable(input.getSuffix())
                                    .filter(
                                        item ->
                                            item.trim().length() != 0
                                                && item.trim()
                                                    .equalsIgnoreCase(
                                                        RuleMasterData.CargoTankOne.getSuffix()));
                            Optional<String> isPrefixExistCTankTwo =
                                Optional.ofNullable(input.getPrefix())
                                    .filter(
                                        item ->
                                            item.trim().length() != 0
                                                && item.trim()
                                                    .equalsIgnoreCase(
                                                        RuleMasterData.CargoTankTwo.getPrefix()));
                            Optional<String> isSuffixExistCTankTwo =
                                Optional.ofNullable(input.getSuffix())
                                    .filter(
                                        item ->
                                            item.trim().length() != 0
                                                && item.trim()
                                                    .equalsIgnoreCase(
                                                        RuleMasterData.CargoTankTwo.getSuffix()));
                            try {
                              if ((isPrefixExistCTankOne.isPresent()
                                      && isSuffixExistCTankOne.isPresent())
                                  || (isPrefixExistCTankTwo.isPresent()
                                      && isSuffixExistCTankTwo.isPresent())) {
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
                            } catch (GenericServiceException e) {
                              throw new RuntimeException(
                                  "Master rule drop down value does not exist");
                            }
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

  /**
   * validate master dropdown values
   *
   * @param listOfDropDownValue
   * @param cargoTankMaster
   * @param isCargoTankMaster
   * @param input
   * @param ruleTemplateInput
   * @param rule
   * @throws GenericServiceException
   */
  private void ruleMasterDropDownValidation(
      List<VesselInfo.RuleDropDownValueMaster> listOfDropDownValue,
      List<VesselInfo.CargoTankMaster> cargoTankMaster,
      boolean isCargoTankMaster,
      Common.RulesInputs input,
      LoadingRuleInput ruleTemplateInput,
      Common.Rules rule)
      throws GenericServiceException {
    Optional<String> isDefaultValueExist =
        Optional.ofNullable(input.getDefaultValue()).filter(item -> item.trim().length() != 0);
    if (isDefaultValueExist.isPresent()) {
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
                  "Rule master master with given id does not exist",
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
                  "Rule master master with given id does not exist",
                  CommonErrorCodes.E_HTTP_BAD_REQUEST,
                  HttpStatusCode.BAD_REQUEST);
            }
          }
        }
        ruleTemplateInput.setDefaultValue(value);
      } else {
        if (isCargoTankMaster) {
          if (input.getDefaultValue() != null
              && input.getDefaultValue().length() != 0
              && !cargoTankMaster.stream()
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
          if (input.getDefaultValue() != null
              && input.getDefaultValue().length() != 0
              && !filterMasterByRule.stream()
                  .map(VesselInfo.RuleDropDownValueMaster::getId)
                  .filter(item -> item == Long.parseLong(input.getDefaultValue().trim()))
                  .findFirst()
                  .isPresent()) {
            throw new GenericServiceException(
                "Rule master master with given id does not exist",
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST);
          }
        }
        ruleTemplateInput.setDefaultValue(input.getDefaultValue());
      }
    } else {
      ruleTemplateInput.setDefaultValue(null);
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

  /**
   * @param lStudyRulesList
   * @param rulePlanBuilder
   * @param builder
   * @param vesselRuleReply
   */
  private void buildResponseForRules(
      List<LoadingRule> lStudyRulesList,
      com.cpdss.common.generated.Common.RulePlans.Builder rulePlanBuilder,
      LoadingPlanModels.LoadingPlanRuleReply.Builder builder,
      VesselInfo.VesselRuleReply vesselRuleReply) {
    lStudyRulesList.forEach(
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
          Optional.ofNullable(ruleList.getRuleTypeXid())
              .filter(item -> item.equals(RuleType.ABSOLUTE.getId()))
              .ifPresentOrElse(
                  item -> rulesBuilder.setRuleType(RuleType.ABSOLUTE.getRuleType()),
                  () -> rulesBuilder.setRuleType(RuleType.PREFERABLE.getRuleType()));
          Optional.ofNullable(ruleList.getIsHardRule())
              .ifPresentOrElse(
                  rulesBuilder::setIsHardRule, () -> rulesBuilder.setIsHardRule(false));
          Optional.ofNullable(ruleList.getVesselRuleXid())
              .ifPresentOrElse(
                  item -> rulesBuilder.setVesselRuleXId(String.valueOf(item)),
                  () -> rulesBuilder.setVesselRuleXId(""));
          Optional.ofNullable(ruleList.getParentRuleXid())
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
          List<LoadingRuleInput> loadingRuleInputList =
              loadingRuleInputRepository.findAllByLoadingRule(ruleList);
          List<LoadingRuleInput> loadableStudyRuleInputs;
          if (!CollectionUtils.isEmpty(loadingRuleInputList)) {
            loadableStudyRuleInputs =
                loadingRuleInputList.stream()
                    .sorted(Comparator.comparingLong(LoadingRuleInput::getId))
                    .collect(Collectors.toList());
          } else {
            loadableStudyRuleInputs = new ArrayList<>();
          }
          for (int inputIndex = 0; inputIndex < loadableStudyRuleInputs.size(); inputIndex++) {
            Common.RulesInputs.Builder finalRuleInput = ruleInput;
            LoadingRuleInput input = loadableStudyRuleInputs.get(inputIndex);
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
                                && item.trim().equalsIgnoreCase(TypeValue.BOOLEAN.getType()));
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
                                    && item.trim().equalsIgnoreCase(TypeValue.DROPDOWN.getType())
                                || item.trim().equalsIgnoreCase(TypeValue.MULTISELECT.getType()));
            if (isTypeDropDownOrMultiSelect.isPresent()) {
              Optional<String> isPrefixExistCTOne =
                  Optional.ofNullable(input.getPrefix())
                      .filter(
                          item ->
                              item.trim().length() != 0
                                  && item.trim()
                                      .equalsIgnoreCase(RuleMasterData.CargoTankOne.getPrefix()));
              Optional<String> isSuffixExistCTOne =
                  Optional.ofNullable(input.getSuffix())
                      .filter(
                          item ->
                              item.trim().length() != 0
                                  && item.trim()
                                      .equalsIgnoreCase(RuleMasterData.CargoTankOne.getSuffix()));
              Optional<String> isPrefixExistCTTwo =
                  Optional.ofNullable(input.getPrefix())
                      .filter(
                          item ->
                              item.trim().length() != 0
                                  && item.trim()
                                      .equalsIgnoreCase(RuleMasterData.CargoTankTwo.getPrefix()));
              Optional<String> isSuffixExistCTTwo =
                  Optional.ofNullable(input.getSuffix())
                      .filter(
                          item ->
                              item.trim().length() != 0
                                  && item.trim()
                                      .equalsIgnoreCase(RuleMasterData.CargoTankTwo.getSuffix()));
              Common.RuleDropDownMaster.Builder ruleDropDownMaster =
                  Common.RuleDropDownMaster.newBuilder();
              if ((isPrefixExistCTOne.isPresent() && isSuffixExistCTOne.isPresent())
                  || (isPrefixExistCTTwo.isPresent() && isSuffixExistCTTwo.isPresent())) {
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
                Optional<Long> ruleTempId = Optional.ofNullable(ruleList.getParentRuleXid());
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

  @Override
  public LoadingPlanModels.LoadingPlanRuleReply getLoadingPlanRuleForAlgo(
      Long vesselId, Long loadingInfoId) {
    LoadingPlanModels.LoadingPlanRuleRequest.Builder request =
        LoadingPlanModels.LoadingPlanRuleRequest.newBuilder();
    request.setLoadingInfoId(loadingInfoId);
    request.setVesselId(vesselId);
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

  /**
   * To fetch default loading rules and save rules against loading information
   *
   * @param loadingInformation
   * @throws GenericServiceException
   */
  public void saveRulesAgainstLoadingInformation(LoadingInformation loadingInformation)
      throws GenericServiceException {
    VesselInfo.VesselRuleRequest.Builder vesselRuleBuilder =
        VesselInfo.VesselRuleRequest.newBuilder();
    vesselRuleBuilder.setSectionId(RuleMasterSection.Loading.getId());
    vesselRuleBuilder.setVesselId(loadingInformation.getVesselXId());
    vesselRuleBuilder.setIsFetchEnabledRules(false);
    vesselRuleBuilder.setIsNoDefaultRule(true);
    VesselInfo.VesselRuleReply vesselRuleReply =
        this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(vesselRuleBuilder.build());
    if (!SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get loadable study rule Details ",
          vesselRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.parseInt(vesselRuleReply.getResponseStatus().getCode())));
    }
    List<LoadingRule> loadableRulesList = new ArrayList<>();
    vesselRuleReply.getRulePlanList().stream()
        .forEach(
            plan -> {
              plan.getRulesList()
                  .forEach(
                      loadingInfoRules -> {
                        LoadingRule loadingRule = new LoadingRule();
                        loadingRule.setLoadingXid(loadingInformation.getId());
                        Optional.ofNullable(loadingInfoRules.getVesselRuleXId())
                            .ifPresent(
                                vesselRuleId ->
                                    loadingRule.setVesselRuleXid(Long.parseLong(vesselRuleId)));
                        Optional.ofNullable(loadingInformation.getVesselXId())
                            .ifPresent(loadingRule::setVesselXid);
                        if (!CollectionUtils.isEmpty(vesselRuleReply.getRuleTypeMasterList())
                            && loadingInfoRules.getRuleType() != null
                            && loadingInfoRules.getRuleType().trim() != "") {
                          Optional<VesselInfo.RuleTypeMaster> ruleType =
                              vesselRuleReply.getRuleTypeMasterList().stream()
                                  .filter(
                                      rType ->
                                          rType
                                              .getRuleType()
                                              .equalsIgnoreCase(loadingInfoRules.getRuleType()))
                                  .findAny();
                          ruleType.orElseThrow(RuntimeException::new);
                          loadingRule.setRuleTypeXid(ruleType.get().getId());
                        }
                        Optional.ofNullable(loadingInfoRules.getDisplayInSettings())
                            .ifPresentOrElse(
                                loadingRule::setDisplayInSettings,
                                () -> loadingRule.setDisplayInSettings(false));
                        Optional.ofNullable(loadingInfoRules.getEnable())
                            .ifPresentOrElse(
                                loadingRule::setIsEnable, () -> loadingRule.setIsEnable(false));
                        Optional.ofNullable(loadingInfoRules.getIsHardRule())
                            .ifPresentOrElse(
                                loadingRule::setIsHardRule, () -> loadingRule.setIsHardRule(false));
                        loadingRule.setIsActive(true);
                        Optional.ofNullable(loadingInfoRules.getNumericPrecision())
                            .ifPresent(loadingRule::setNumericPrecision);
                        Optional.ofNullable(loadingInfoRules.getNumericScale())
                            .ifPresent(loadingRule::setNumericScale);
                        Optional.ofNullable(loadingInfoRules.getRuleTemplateId())
                            .ifPresent(item -> loadingRule.setParentRuleXid(Long.parseLong(item)));
                        List<LoadingRuleInput> lisOfLsRulesInput = new ArrayList<>();
                        loadingInfoRules
                            .getInputsList()
                            .forEach(
                                lodingInfoRulesInput -> {
                                  LoadingRuleInput loadingRuleInput = new LoadingRuleInput();
                                  loadingRuleInput.setLoadingRule(loadingRule);
                                  Optional.ofNullable(lodingInfoRulesInput.getPrefix())
                                      .ifPresent(loadingRuleInput::setPrefix);
                                  Optional.ofNullable(lodingInfoRulesInput.getDefaultValue())
                                      .ifPresentOrElse(
                                          loadingRuleInput::setDefaultValue,
                                          () -> loadingRuleInput.setDefaultValue(null));
                                  Optional.ofNullable(lodingInfoRulesInput.getType())
                                      .ifPresent(loadingRuleInput::setTypeValue);
                                  Optional.ofNullable(lodingInfoRulesInput.getMax())
                                      .ifPresent(loadingRuleInput::setMaxValue);
                                  Optional.ofNullable(lodingInfoRulesInput.getMin())
                                      .ifPresent(loadingRuleInput::setMinValue);
                                  Optional.ofNullable(lodingInfoRulesInput.getSuffix())
                                      .ifPresent(loadingRuleInput::setSuffix);
                                  loadingRuleInput.setIsActive(true);
                                  Optional.ofNullable(lodingInfoRulesInput.getIsMandatory())
                                      .ifPresentOrElse(
                                          loadingRuleInput::setIsMandatory,
                                          () -> loadingRuleInput.setIsMandatory(false));
                                  lisOfLsRulesInput.add(loadingRuleInput);
                                });
                        loadingRule.setLoadingRuleInputs(lisOfLsRulesInput);
                        loadableRulesList.add(loadingRule);
                      });
            });
    loadingRuleRepository.saveAll(loadableRulesList);
  }
}
