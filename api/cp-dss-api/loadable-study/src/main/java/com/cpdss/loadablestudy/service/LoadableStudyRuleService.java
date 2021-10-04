/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.RuleMasterSection;
import com.cpdss.loadablestudy.domain.RulePlans;
import com.cpdss.loadablestudy.domain.RuleType;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyRuleInput;
import com.cpdss.loadablestudy.entity.LoadableStudyRules;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRuleInputRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRuleRepository;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Master Service for Loadable Pattern
 *
 * @author vinothkumar M
 * @since 08-07-2021
 */
@Slf4j
@Service
public class LoadableStudyRuleService {

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private LoadableStudyRuleRepository loadableStudyRuleRepository;

  @Autowired private LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  /**
   * @param request
   * @return
   * @throws GenericServiceException
   */
  List<LoadableStudyRules> getLoadableStudyRules(
      com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request)
      throws GenericServiceException {
    log.info("Duplicate Loadable Study Creating" + request.getDuplicatedFromId());
    List<LoadableStudyRules> listOfExistingLSRules = null;
    Optional<LoadableStudy> loadableStudy =
        this.loadableStudyRepository.findByIdAndIsActive(request.getDuplicatedFromId(), true);
    if (!loadableStudy.isPresent()) {
      throw new GenericServiceException(
          "Loadable study with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    // updateDisplayInSettingsInLoadableStudyRules(vesselRuleReply);
    listOfExistingLSRules =
        loadableStudyRuleRepository.findByLoadableStudyAndVesselXIdAndIsActive(
            loadableStudy.get(), request.getVesselId(), true);
    return listOfExistingLSRules;
  }

  private void updateDisplayInSettingsInLoadableStudyRules(
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
    loadableStudyRuleRepository.updateDisplayInSettingInLoadbleStudyRules(
        true, setOfSelectedDisplayInSetting);
    loadableStudyRuleRepository.updateDisplayInSettingInLoadbleStudyRules(
        false, setOfDeselectedDisplayInSetting);
  }

  /**
   * duplicate rules when ls duplicate
   *
   * @param listOfExistingLSRules
   * @param currentLoableStudy
   * @param request
   */
  void saveDuplicateLoadableStudyRules(
      List<LoadableStudyRules> listOfExistingLSRules,
      LoadableStudy currentLoableStudy,
      com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request) {
    if (listOfExistingLSRules != null && listOfExistingLSRules.size() > 0) {
      List<LoadableStudyRules> listOfLSRules = new ArrayList<LoadableStudyRules>();
      log.info("Existing loadable rules has value ");
      List<LoadableStudyRules> sortedLStudyRulesList =
          listOfExistingLSRules.stream()
              .sorted(Comparator.comparingLong(LoadableStudyRules::getId))
              .collect(Collectors.toList());
      sortedLStudyRulesList.stream()
          .forEach(
              lsRules -> {
                LoadableStudyRules loadableStudyRules = new LoadableStudyRules();
                loadableStudyRules.setLoadableStudy(currentLoableStudy);
                Optional.ofNullable(lsRules.getVesselRuleXId())
                    .ifPresent(loadableStudyRules::setVesselRuleXId);
                Optional.ofNullable(request.getVesselId())
                    .ifPresent(loadableStudyRules::setVesselXId);
                Optional.ofNullable(lsRules.getRuleTypeXId())
                    .ifPresent(loadableStudyRules::setRuleTypeXId);
                Optional.ofNullable(lsRules.getDisplayInSettings())
                    .ifPresentOrElse(
                        loadableStudyRules::setDisplayInSettings,
                        () -> loadableStudyRules.setDisplayInSettings(false));
                Optional.ofNullable(lsRules.getIsEnable())
                    .ifPresentOrElse(
                        loadableStudyRules::setIsEnable,
                        () -> loadableStudyRules.setIsEnable(false));
                Optional.ofNullable(lsRules.getIsHardRule())
                    .ifPresentOrElse(
                        loadableStudyRules::setIsHardRule,
                        () -> loadableStudyRules.setIsHardRule(false));
                Optional.ofNullable(lsRules.getIsActive())
                    .ifPresent(loadableStudyRules::setIsActive);
                Optional.ofNullable(lsRules.getNumericPrecision())
                    .ifPresent(loadableStudyRules::setNumericPrecision);
                Optional.ofNullable(lsRules.getNumericScale())
                    .ifPresent(loadableStudyRules::setNumericScale);
                Optional.ofNullable(lsRules.getParentRuleXId())
                    .ifPresent(loadableStudyRules::setParentRuleXId);
                List<LoadableStudyRuleInput> lisOfLsRulesInput = new ArrayList<>();
                List<LoadableStudyRuleInput> loadableStudyRuleInputs;
                if (lsRules != null && lsRules.getLoadableStudyRuleInputs().size() != 0) {
                  loadableStudyRuleInputs =
                      lsRules.getLoadableStudyRuleInputs().stream()
                          .sorted(Comparator.comparingLong(LoadableStudyRuleInput::getId))
                          .collect(Collectors.toList());
                } else {
                  loadableStudyRuleInputs = new ArrayList<>();
                }
                loadableStudyRuleInputs.forEach(
                    lsRulesInput -> {
                      LoadableStudyRuleInput loadableStudyRuleInput = new LoadableStudyRuleInput();
                      loadableStudyRuleInput.setLoadableStudyRuleXId(loadableStudyRules);
                      Optional.ofNullable(lsRulesInput.getPrefix())
                          .ifPresent(loadableStudyRuleInput::setPrefix);
                      Optional.ofNullable(lsRulesInput.getDefaultValue())
                          .ifPresentOrElse(
                              loadableStudyRuleInput::setDefaultValue,
                              () -> loadableStudyRuleInput.setDefaultValue(null));
                      Optional.ofNullable(lsRulesInput.getTypeValue())
                          .ifPresent(loadableStudyRuleInput::setTypeValue);
                      Optional.ofNullable(lsRulesInput.getMaxValue())
                          .ifPresent(loadableStudyRuleInput::setMaxValue);
                      Optional.ofNullable(lsRulesInput.getMinValue())
                          .ifPresent(loadableStudyRuleInput::setMinValue);
                      Optional.ofNullable(lsRulesInput.getSuffix())
                          .ifPresent(loadableStudyRuleInput::setSuffix);
                      Optional.ofNullable(lsRulesInput.getIsActive())
                          .ifPresent(loadableStudyRuleInput::setIsActive);
                      Optional.ofNullable(lsRulesInput.getIsMandatory())
                          .ifPresentOrElse(
                              loadableStudyRuleInput::setIsMandatory,
                              () -> loadableStudyRuleInput.setIsMandatory(false));
                      lisOfLsRulesInput.add(loadableStudyRuleInput);
                    });
                loadableStudyRules.setLoadableStudyRuleInputs(lisOfLsRulesInput);
                listOfLSRules.add(loadableStudyRules);
              });
      loadableStudyRuleRepository.saveAll(listOfLSRules);
    }
  }

  /**
   * fetch or update rules against ls
   *
   * @param request
   * @param builder
   * @throws GenericServiceException
   */
  public void getOrSaveRulesForLoadableStudy(
      com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request,
      com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.Builder builder)
      throws GenericServiceException {
    if (!RuleMasterSection.Plan.getId().equals(request.getSectionId())) {
      throw new GenericServiceException(
          "fetched rule against loadble study planning not for loading or discharging module",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Optional<LoadableStudy> loadableStudy =
        loadableStudyRepository.findByIdAndIsActiveAndVesselXId(
            request.getLoadableStudyId(), true, request.getVesselId());
    if (!loadableStudy.isPresent()) {
      log.error("Failed to get loadable study for get or save rule", request.getLoadableStudyId());
      throw new GenericServiceException(
          "Loadable study with given id does not exist",
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
          "failed to get loadable study rule Details ",
          vesselRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.parseInt(vesselRuleReply.getResponseStatus().getCode())));
    }
    if (!CollectionUtils.isEmpty(request.getRulePlanList())) {
      log.info("save loadable study rules");
      saveRulesAgainstLoadableStudy(request, loadableStudy, vesselRuleReply);
    }
    // updateDisplayInSettingsInLoadableStudyRules(vesselRuleReply);
    // Filter vessel rule primary key id
    List<Long> ruleListId =
        vesselRuleReply.getRulePlanList().stream()
            .flatMap(rulesList -> rulesList.getRulesList().stream())
            .map(rules -> Long.parseLong(rules.getVesselRuleXId()))
            .collect(Collectors.toList());
    // fetch loadable study rules based on vessel rule primary key id
    List<LoadableStudyRules> loadableStudyRulesList =
        loadableStudyRuleRepository
            .findByLoadableStudyAndVesselXIdAndIsActiveAndVesselRuleXIdInOrderById(
                loadableStudy.get(), request.getVesselId(), true, ruleListId);
    if (loadableStudyRulesList.size() > 0) {
      log.info("Fetch loadable study rules");
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
                List<LoadableStudyRules> lStudyRulesList =
                    loadableStudyRulesList.stream()
                        .filter(lRuleList -> ruleId.contains(lRuleList.getVesselRuleXId()))
                        .collect(Collectors.toList());
                List<LoadableStudyRules> sortedLStudyRulesList =
                    lStudyRulesList.stream()
                        .sorted(Comparator.comparingLong(LoadableStudyRules::getId))
                        .collect(Collectors.toList());
                buildResponseForRules(
                    sortedLStudyRulesList, rulePlanBuider, builder, vesselRuleReply);
              });
    } else {
      log.info("default vessel rules ");
      vesselRuleReply
          .getRulePlanList()
          .forEach(
              rulePlans -> {
                Common.RulePlans.Builder rulePlanBuilder = Common.RulePlans.newBuilder();
                Optional.ofNullable(rulePlans.getHeader()).ifPresent(rulePlanBuilder::setHeader);
                List<Common.Rules> ruleList =
                    rulePlans.getRulesList().stream().collect(Collectors.toList());
                System.out.println(ruleList.size());
                if (ruleList != null && ruleList.size() > 0) {
                  rulePlanBuilder.addAllRules(ruleList);
                  builder.addRulePlan(rulePlanBuilder);
                }
              });
    }
    builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  /**
   * save rules against loadable study
   *
   * @param request
   * @param loadableStudy
   * @param vesselRuleReply
   */
  private void saveRulesAgainstLoadableStudy(
      com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request,
      Optional<LoadableStudy> loadableStudy,
      VesselInfo.VesselRuleReply vesselRuleReply) {
    List<LoadableStudyRules> loadableStudyRulesList = new ArrayList<>();
    request
        .getRulePlanList()
        .forEach(
            rulePlans -> {
              rulePlans
                  .getRulesList()
                  .forEach(
                      rule -> {
                        LoadableStudyRules loadableStudyRules = new LoadableStudyRules();
                        Optional<String> isRuleTemplateIdExist =
                            Optional.ofNullable(rule.getRuleTemplateId())
                                .filter(item -> item.trim().length() != 0);
                        if (rule.getId() != null && rule.getId().trim().length() != 0) {
                          Optional<LoadableStudyRules> rVesselMapping =
                              loadableStudyRuleRepository.findById(Long.valueOf(rule.getId()));
                          if (rVesselMapping.isPresent()) {
                            loadableStudyRules = rVesselMapping.get();
                          } else {
                            log.info("No record exist for this id in loadable study rule table");
                            throw new RuntimeException(
                                "No record exist for this id in loadable study rule table");
                          }
                        } else {
                          if (isRuleTemplateIdExist.isPresent()) {
                            Optional<LoadableStudyRules> loadableStudyRulesRecord =
                                loadableStudyRuleRepository.checkIsRuleTemplateExist(
                                    loadableStudy.get().getId(),
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
                        loadableStudyRules.setLoadableStudy(loadableStudy.get());
                        loadableStudyRules.setIsActive(true);
                        LoadableStudyRules finalLoadableStudyRules1 = loadableStudyRules;
                        Optional.ofNullable(rule.getDisplayInSettings())
                            .ifPresentOrElse(
                                loadableStudyRules::setDisplayInSettings,
                                () -> finalLoadableStudyRules1.setDisplayInSettings(false));
                        Optional.ofNullable(rule.getEnable())
                            .ifPresentOrElse(
                                loadableStudyRules::setIsEnable,
                                () -> finalLoadableStudyRules1.setIsEnable(false));
                        Optional.ofNullable(rule.getIsHardRule())
                            .ifPresentOrElse(
                                loadableStudyRules::setIsHardRule,
                                () -> finalLoadableStudyRules1.setIsHardRule(false));
                        Optional.ofNullable(rule.getNumericPrecision())
                            .ifPresent(loadableStudyRules::setNumericPrecision);
                        Optional.ofNullable(rule.getNumericScale())
                            .ifPresent(loadableStudyRules::setNumericScale);
                        LoadableStudyRules finalLoadableStudyRules = loadableStudyRules;
                        Optional.ofNullable(rule.getRuleTemplateId())
                            .ifPresent(
                                item ->
                                    finalLoadableStudyRules.setParentRuleXId(Long.parseLong(item)));
                        loadableStudyRules.setVesselXId(request.getVesselId());
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
                          loadableStudyRules.setRuleTypeXId(ruleType.get().getId());
                        } else {
                          log.info("Rule Type can't be null");
                          throw new RuntimeException("Rule type can't be null");
                        }
                        Optional.ofNullable(rule.getVesselRuleXId())
                            .ifPresent(
                                vesselRuleXId ->
                                    finalLoadableStudyRules.setVesselRuleXId(
                                        Long.parseLong(vesselRuleXId)));
                        List<LoadableStudyRuleInput> ruleVesselMappingInputList = new ArrayList<>();
                        for (Common.RulesInputs input : rule.getInputsList()) {
                          LoadableStudyRuleInput ruleTemplateInput = new LoadableStudyRuleInput();
                          if (input.getId() != null && input.getId().length() != 0) {
                            Optional<LoadableStudyRuleInput> rTemplateInput =
                                loadableStudyRuleInputRepository.findById(
                                    Long.valueOf(input.getId()));
                            if (rTemplateInput.isPresent()) {
                              ruleTemplateInput = rTemplateInput.get();
                            } else {
                              log.info(
                                  "No record exist for this id in rule loadable study rule input table");
                              throw new RuntimeException(
                                  "No record exist for this id in rule loadable study rule input table");
                            }
                          }
                          LoadableStudyRuleInput finalRuleTemplateInput = ruleTemplateInput;
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
                          ruleTemplateInput.setLoadableStudyRuleXId(loadableStudyRules);
                          Optional<String> isTypeDropDownOrMultiSelect =
                              Optional.ofNullable(input.getType())
                                  .filter(
                                      value ->
                                          value.trim().length() != 0
                                                  && value
                                                      .trim()
                                                      .equalsIgnoreCase(
                                                          com.cpdss.loadablestudy.domain.TypeValue
                                                              .DROPDOWN
                                                              .getType())
                                              || value
                                                  .trim()
                                                  .equalsIgnoreCase(
                                                      com.cpdss.loadablestudy.domain.TypeValue
                                                          .MULTISELECT
                                                          .getType()));

                          if (isTypeDropDownOrMultiSelect.isPresent()) {
                            Optional<String> isPrefixExist =
                                Optional.ofNullable(input.getPrefix())
                                    .filter(
                                        item ->
                                            item.trim().length() != 0
                                                && item.trim()
                                                    .equalsIgnoreCase(
                                                        com.cpdss.loadablestudy.domain
                                                            .RuleMasterData.CargoTank.getPrefix()));
                            Optional<String> isSuffixExist =
                                Optional.ofNullable(input.getSuffix())
                                    .filter(
                                        item ->
                                            item.trim().length() != 0
                                                && item.trim()
                                                    .equalsIgnoreCase(
                                                        com.cpdss.loadablestudy.domain
                                                            .RuleMasterData.CargoTank.getSuffix()));
                            try {
                              if (isPrefixExist.isPresent() && isSuffixExist.isPresent()) {
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
                        loadableStudyRules.setLoadableStudyRuleInputs(ruleVesselMappingInputList);
                        loadableStudyRulesList.add(loadableStudyRules);
                      });
            });
    loadableStudyRuleRepository.saveAll(loadableStudyRulesList);
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
      LoadableStudyRuleInput ruleTemplateInput,
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

  /**
   * @param lStudyRulesList
   * @param rulePlanBuilder
   * @param builder
   * @param vesselRuleReply
   */
  private void buildResponseForRules(
      List<LoadableStudyRules> lStudyRulesList,
      Common.RulePlans.Builder rulePlanBuilder,
      com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.Builder builder,
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
          List<LoadableStudyRuleInput> loadableStudyRuleInputs;
          if (ruleList != null && ruleList.getLoadableStudyRuleInputs().size() != 0) {
            loadableStudyRuleInputs =
                ruleList.getLoadableStudyRuleInputs().stream()
                    .sorted(Comparator.comparingLong(LoadableStudyRuleInput::getId))
                    .collect(Collectors.toList());
          } else {
            loadableStudyRuleInputs = new ArrayList<>();
          }
          for (int inputIndex = 0; inputIndex < loadableStudyRuleInputs.size(); inputIndex++) {
            Common.RulesInputs.Builder finalRuleInput = ruleInput;
            LoadableStudyRuleInput input = loadableStudyRuleInputs.get(inputIndex);
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
                                        com.cpdss.loadablestudy.domain.TypeValue.BOOLEAN
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
                                            com.cpdss.loadablestudy.domain.TypeValue.DROPDOWN
                                                .getType())
                                || item.trim()
                                    .equalsIgnoreCase(
                                        com.cpdss.loadablestudy.domain.TypeValue.MULTISELECT
                                            .getType()));
            if (isTypeDropDownOrMultiSelect.isPresent()) {
              Optional<String> isPrefixExist =
                  Optional.ofNullable(input.getPrefix())
                      .filter(
                          item ->
                              item.trim().length() != 0
                                  && item.trim()
                                      .equalsIgnoreCase(
                                          com.cpdss.loadablestudy.domain.RuleMasterData.CargoTank
                                              .getPrefix()));
              Optional<String> isSuffixExist =
                  Optional.ofNullable(input.getSuffix())
                      .filter(
                          item ->
                              item.trim().length() != 0
                                  && item.trim()
                                      .equalsIgnoreCase(
                                          com.cpdss.loadablestudy.domain.RuleMasterData.CargoTank
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

  /**
   * @param loadableStudyOpt
   * @param loadableStudy
   * @throws GenericServiceException
   * @throws
   */
  public void buildLoadableStudyRuleDetails(
      LoadableStudy loadableStudyOpt, com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy)
      throws GenericServiceException {
    List<com.cpdss.loadablestudy.domain.RulePlans> listOfLSRulesPlan = new ArrayList<>();
    List<LoadableStudyRules> loadableStudyRulesList =
        loadableStudyRuleRepository.findByLoadableStudyAndVesselXIdAndIsActive(
            loadableStudyOpt, loadableStudyOpt.getVesselXId(), true);
    VesselInfo.VesselRuleRequest.Builder vesselRuleBuilder =
        VesselInfo.VesselRuleRequest.newBuilder();
    vesselRuleBuilder.setSectionId(RuleMasterSection.Plan.getId());
    vesselRuleBuilder.setVesselId(loadableStudyOpt.getVesselXId());
    vesselRuleBuilder.setIsNoDefaultRule(true);
    vesselRuleBuilder.setIsFetchEnabledRules(true);
    VesselInfo.VesselRuleReply vesselRuleReply =
        this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(vesselRuleBuilder.build());
    if (!SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get loadable study rule Details against vessel ",
          vesselRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselRuleReply.getResponseStatus().getCode())));
    } else {
      if (loadableStudyRulesList != null && loadableStudyRulesList.size() > 0) {
        log.info("Fetch particular loadable study rules");
        vesselRuleReply
            .getRulePlanList()
            .forEach(
                rulePlans -> {
                  com.cpdss.loadablestudy.domain.RulePlans rulePlan =
                      new com.cpdss.loadablestudy.domain.RulePlans();
                  Optional.ofNullable(rulePlans.getHeader())
                      .ifPresent(item -> rulePlan.setHeader(item));
                  List<Long> ruleId =
                      rulePlans.getRulesList().stream()
                          .map(rules -> Long.parseLong(rules.getVesselRuleXId()))
                          .collect(Collectors.toList());
                  List<LoadableStudyRules> lStudyRulesList =
                      loadableStudyRulesList.stream()
                          .filter(lRuleList -> ruleId.contains(lRuleList.getVesselRuleXId()))
                          .collect(Collectors.toList());
                  List<LoadableStudyRules> sortedLStudyRulesList =
                      lStudyRulesList.stream()
                          .sorted(Comparator.comparingLong(LoadableStudyRules::getId))
                          .collect(Collectors.toList());
                  buildLoadableStudyRuleFromLS(sortedLStudyRulesList, vesselRuleReply, rulePlan);
                  listOfLSRulesPlan.add(rulePlan);
                });
      } else {
        if (!CollectionUtils.isEmpty(vesselRuleReply.getRulePlanList())
            && vesselRuleReply.getRulePlanList().size() > 0) {
          log.info("Fetch default vessel rules");
          buildVesselRule(vesselRuleReply, listOfLSRulesPlan);
        }
      }
      loadableStudy.setLoadableStudyRuleList(listOfLSRulesPlan);
    }
  }

  private void buildVesselRule(
      VesselInfo.VesselRuleReply vesselRuleReply, List<RulePlans> listOfLSRulesPlan) {
    vesselRuleReply
        .getRulePlanList()
        .forEach(
            rulePlans -> {
              com.cpdss.loadablestudy.domain.RulePlans rulePlan =
                  new com.cpdss.loadablestudy.domain.RulePlans();
              Optional.ofNullable(rulePlans.getHeader())
                  .ifPresent(item -> rulePlan.setHeader(item));
              List<com.cpdss.loadablestudy.domain.Rules> rList = new ArrayList<>();
              rulePlans
                  .getRulesList()
                  .forEach(
                      rules -> {
                        Optional<Boolean> isEnable = Optional.ofNullable(rules.getEnable());
                        com.cpdss.loadablestudy.domain.Rules rule =
                            new com.cpdss.loadablestudy.domain.Rules();
                        Optional.ofNullable(rules.getId())
                            .filter(item -> item.trim().length() != 0)
                            .ifPresentOrElse(rule::setId, () -> rule.setId(null));
                        Optional.ofNullable(rules.getEnable()).ifPresent(rule::setEnable);
                        Optional.ofNullable(rules.getDisplayInSettings())
                            .ifPresentOrElse(
                                rule::setDisplayInSettings, () -> rule.setDisplayInSettings(false));
                        Optional.ofNullable(rules.getId())
                            .filter(item -> item.length() != 0)
                            .ifPresent(rule::setId);
                        Optional.ofNullable(rules.getRuleType())
                            .filter(item -> item.length() != 0)
                            .ifPresent(rule::setRuleType);
                        Optional.ofNullable(rules.getIsHardRule())
                            .ifPresentOrElse(rule::setIsHardRule, () -> rule.setIsHardRule(false));
                        Optional.ofNullable(rules.getRuleTemplateId())
                            .ifPresent(rule::setRuleTemplateId);
                        Optional.ofNullable(rules.getNumericPrecision())
                            .ifPresent(rule::setNumericPrecision);
                        Optional.ofNullable(rules.getNumericScale())
                            .ifPresent(rule::setNumericScale);
                        List<com.cpdss.loadablestudy.domain.RulesInputs> rInputList =
                            new ArrayList<>();
                        rules
                            .getInputsList()
                            .forEach(
                                inputs -> {
                                  com.cpdss.loadablestudy.domain.RulesInputs ruleInput =
                                      new com.cpdss.loadablestudy.domain.RulesInputs();
                                  Optional.ofNullable(inputs.getId())
                                      .filter(item -> item.trim().length() != 0)
                                      .ifPresentOrElse(
                                          ruleInput::setId, () -> ruleInput.setId(null));
                                  Optional.ofNullable(inputs.getDefaultValue())
                                      .filter(item -> item.trim().length() != 0)
                                      .ifPresent(ruleInput::setDefaultValue);
                                  Optional.ofNullable(inputs.getDefaultValue())
                                      .filter(item -> item.trim().length() != 0)
                                      .ifPresent(ruleInput::setValue);
                                  Optional.ofNullable(inputs.getPrefix())
                                      .filter(item -> item.trim().length() != 0)
                                      .ifPresent(ruleInput::setPrefix);
                                  Optional.ofNullable(inputs.getMin())
                                      .filter(item -> item.trim().length() != 0)
                                      .ifPresent(ruleInput::setMin);
                                  Optional.ofNullable(inputs.getMax())
                                      .filter(item -> item.trim().length() != 0)
                                      .ifPresent(ruleInput::setMax);
                                  Optional.ofNullable(inputs.getType())
                                      .filter(item -> item.trim().length() != 0)
                                      .ifPresent(ruleInput::setType);
                                  Optional.ofNullable(inputs.getSuffix())
                                      .filter(item -> item.trim().length() != 0)
                                      .ifPresent(ruleInput::setSuffix);
                                  Optional.ofNullable(inputs.getId())
                                      .filter(item -> item.trim().length() != 0)
                                      .ifPresent(ruleInput::setId);
                                  Optional.ofNullable(inputs.getIsMandatory())
                                      .ifPresentOrElse(
                                          ruleInput::setIsMandatory,
                                          () -> ruleInput.setIsMandatory(false));
                                  Optional<String> isTypeBoolean =
                                      Optional.ofNullable(inputs.getType())
                                          .filter(
                                              item ->
                                                  item.trim().length() != 0
                                                      && item.trim()
                                                          .equalsIgnoreCase(
                                                              com.cpdss.loadablestudy.domain
                                                                  .TypeValue.BOOLEAN
                                                                  .getType()));
                                  if (isTypeBoolean.isPresent()) {
                                    Optional.ofNullable(inputs.getDefaultValue())
                                        .filter(
                                            item ->
                                                item.trim().length() != 0
                                                    && item.trim().equalsIgnoreCase("true"))
                                        .ifPresentOrElse(
                                            ruleInput::setDefaultValue,
                                            () -> ruleInput.setDefaultValue("false"));
                                  }
                                  Optional<String> isTypeDropDownOrMultiSelect =
                                      Optional.ofNullable(inputs.getType())
                                          .filter(
                                              item ->
                                                  item.trim().length() != 0
                                                          && item.trim()
                                                              .equalsIgnoreCase(
                                                                  com.cpdss.loadablestudy.domain
                                                                      .TypeValue.DROPDOWN
                                                                      .getType())
                                                      || item.trim()
                                                          .equalsIgnoreCase(
                                                              com.cpdss.loadablestudy.domain
                                                                  .TypeValue.MULTISELECT
                                                                  .getType()));
                                  if (isTypeDropDownOrMultiSelect.isPresent()) {
                                    Optional<String> isPrefixExist =
                                        Optional.ofNullable(inputs.getPrefix())
                                            .filter(
                                                item ->
                                                    item.trim().length() != 0
                                                        && item.trim()
                                                            .equalsIgnoreCase(
                                                                com.cpdss.loadablestudy.domain
                                                                    .RuleMasterData.CargoTank
                                                                    .getPrefix()));
                                    Optional<String> isSuffixExist =
                                        Optional.ofNullable(inputs.getSuffix())
                                            .filter(
                                                item ->
                                                    item.trim().length() != 0
                                                        && item.trim()
                                                            .equalsIgnoreCase(
                                                                com.cpdss.loadablestudy.domain
                                                                    .RuleMasterData.CargoTank
                                                                    .getSuffix()));
                                    List<com.cpdss.loadablestudy.domain.RuleDropDownMaster>
                                        ruleDropDownMasterList = new ArrayList<>();
                                    if (isSuffixExist.isPresent() && isPrefixExist.isPresent()) {
                                      vesselRuleReply
                                          .getCargoTankMasterList()
                                          .forEach(
                                              cargoTank -> {
                                                com.cpdss.loadablestudy.domain.RuleDropDownMaster
                                                    ruleDropDownMaster =
                                                        new com.cpdss.loadablestudy.domain
                                                            .RuleDropDownMaster();
                                                Optional.ofNullable(cargoTank.getId())
                                                    .ifPresent(ruleDropDownMaster::setId);
                                                Optional.ofNullable(cargoTank.getShortName())
                                                    .ifPresent(ruleDropDownMaster::setValue);
                                                ruleDropDownMasterList.add(ruleDropDownMaster);
                                              });
                                      ruleInput.setRuleDropDownMaster(ruleDropDownMasterList);
                                    } else {
                                      Optional<Long> ruleTempId =
                                          Optional.ofNullable(
                                              Long.parseLong(rules.getRuleTemplateId()));
                                      if (ruleTempId.isPresent()) {
                                        List<VesselInfo.RuleDropDownValueMaster>
                                            filterMasterByRule =
                                                vesselRuleReply.getRuleDropDownValueMasterList()
                                                    .stream()
                                                    .filter(
                                                        rDropDown ->
                                                            rDropDown.getRuleTemplateId() != 0
                                                                && ruleTempId.get() != null
                                                                && rDropDown.getRuleTemplateId()
                                                                    == ruleTempId.get())
                                                    .collect(Collectors.toList());
                                        filterMasterByRule.forEach(
                                            masterDropDownRule -> {
                                              com.cpdss.loadablestudy.domain.RuleDropDownMaster
                                                  ruleDropDownMaster =
                                                      new com.cpdss.loadablestudy.domain
                                                          .RuleDropDownMaster();
                                              Optional.ofNullable(masterDropDownRule.getId())
                                                  .ifPresent(ruleDropDownMaster::setId);
                                              Optional.ofNullable(masterDropDownRule.getValue())
                                                  .ifPresent(ruleDropDownMaster::setValue);
                                              ruleDropDownMasterList.add(ruleDropDownMaster);
                                            });
                                      }
                                      ruleInput.setRuleDropDownMaster(ruleDropDownMasterList);
                                    }
                                  }
                                  rInputList.add(ruleInput);
                                });
                        rule.setInputs(rInputList);
                        rList.add(rule);
                      });
              rulePlan.setRules(rList);
              listOfLSRulesPlan.add(rulePlan);
            });
  }

  private void buildLoadableStudyRuleFromLS(
      List<LoadableStudyRules> lStudyRulesList,
      VesselInfo.VesselRuleReply vesselRuleReply,
      RulePlans rulePlan) {
    List<com.cpdss.loadablestudy.domain.Rules> ruleList = new ArrayList<>();
    lStudyRulesList.forEach(
        rulesList -> {
          Optional<Boolean> isEnable = Optional.ofNullable(rulesList.getIsEnable());
          if (isEnable.isPresent() && isEnable.get()) {
            com.cpdss.loadablestudy.domain.Rules rules = new com.cpdss.loadablestudy.domain.Rules();
            Optional.ofNullable(rulesList.getIsEnable()).ifPresent(rules::setEnable);
            Optional.ofNullable(rulesList.getDisplayInSettings())
                .ifPresentOrElse(
                    rules::setDisplayInSettings, () -> rules.setDisplayInSettings(false));
            Optional.ofNullable(rulesList.getId())
                .ifPresent(item -> rules.setId(String.valueOf(item)));
            Optional.ofNullable(rulesList.getRuleTypeXId())
                .filter(item -> item.equals(RuleType.ABSOLUTE.getId()))
                .ifPresent(item -> rules.setRuleType(RuleType.ABSOLUTE.getRuleType()));
            Optional.ofNullable(rulesList.getRuleTypeXId())
                .filter(item -> item.equals(RuleType.PREFERABLE.getId()))
                .ifPresent(item -> rules.setRuleType(RuleType.PREFERABLE.getRuleType()));
            Optional.ofNullable(rulesList.getIsHardRule())
                .ifPresentOrElse(rules::setIsHardRule, () -> rules.setIsHardRule(false));
            Optional.ofNullable(rulesList.getVesselRuleXId())
                .ifPresent(item -> rules.setVesselRuleXId(String.valueOf(item)));
            Optional.ofNullable(rulesList.getParentRuleXId())
                .ifPresent(item -> rules.setRuleTemplateId(String.valueOf(item)));
            Optional.ofNullable(rulesList.getNumericPrecision())
                .ifPresent(rules::setNumericPrecision);
            Optional.ofNullable(rulesList.getNumericScale()).ifPresent(rules::setNumericScale);
            List<com.cpdss.loadablestudy.domain.RulesInputs> ruleInputList = new ArrayList<>();
            List<LoadableStudyRuleInput> loadableStudyRuleInputs;
            if (rulesList != null && rulesList.getLoadableStudyRuleInputs().size() != 0) {
              loadableStudyRuleInputs =
                  rulesList.getLoadableStudyRuleInputs().stream()
                      .sorted(Comparator.comparingLong(LoadableStudyRuleInput::getId))
                      .collect(Collectors.toList());
            } else {
              loadableStudyRuleInputs = new ArrayList<>();
            }
            for (int inputIndex = 0; inputIndex < loadableStudyRuleInputs.size(); inputIndex++) {
              com.cpdss.loadablestudy.domain.RulesInputs ruleInput =
                  new com.cpdss.loadablestudy.domain.RulesInputs();
              LoadableStudyRuleInput loadableStudyRuleInput =
                  rulesList.getLoadableStudyRuleInputs().get(inputIndex);
              Optional.ofNullable(loadableStudyRuleInput.getId())
                  .ifPresentOrElse(
                      (id) -> ruleInput.setId(String.valueOf(id)), () -> ruleInput.setId(null));
              Optional.ofNullable(loadableStudyRuleInput.getDefaultValue())
                  .filter(item -> item.trim().length() != 0)
                  .ifPresentOrElse(
                      ruleInput::setDefaultValue, () -> ruleInput.setDefaultValue(null));
              Optional.ofNullable(loadableStudyRuleInput.getDefaultValue())
                  .filter(item -> item.trim().length() != 0)
                  .ifPresentOrElse(ruleInput::setValue, () -> ruleInput.setValue(null));
              Optional.ofNullable(loadableStudyRuleInput.getPrefix())
                  .filter(item -> item.trim().length() != 0)
                  .ifPresentOrElse(ruleInput::setPrefix, () -> ruleInput.setPrefix(null));
              Optional.ofNullable(loadableStudyRuleInput.getMinValue())
                  .filter(item -> item.trim().length() != 0)
                  .ifPresentOrElse(ruleInput::setMin, () -> ruleInput.setMin(null));
              Optional.ofNullable(loadableStudyRuleInput.getMaxValue())
                  .filter(item -> item.trim().length() != 0)
                  .ifPresentOrElse(ruleInput::setMax, () -> ruleInput.setMax(null));
              Optional.ofNullable(loadableStudyRuleInput.getTypeValue())
                  .filter(item -> item.trim().length() != 0)
                  .ifPresentOrElse(ruleInput::setType, () -> ruleInput.setType(null));
              Optional.ofNullable(loadableStudyRuleInput.getSuffix())
                  .filter(item -> item.trim().length() != 0)
                  .ifPresentOrElse(ruleInput::setSuffix, () -> ruleInput.setSuffix(null));
              Optional.ofNullable(loadableStudyRuleInput.getId())
                  .ifPresent(item -> ruleInput.setId(String.valueOf(item)));
              Optional.ofNullable(loadableStudyRuleInput.getIsMandatory())
                  .ifPresentOrElse(
                      ruleInput::setIsMandatory, () -> ruleInput.setIsMandatory(false));
              Optional<String> isTypeBoolean =
                  Optional.ofNullable(loadableStudyRuleInput.getTypeValue())
                      .filter(
                          item ->
                              item.trim().length() != 0
                                  && item.trim()
                                      .equalsIgnoreCase(
                                          com.cpdss.loadablestudy.domain.TypeValue.BOOLEAN
                                              .getType()));
              if (isTypeBoolean.isPresent()) {
                Optional.ofNullable(loadableStudyRuleInput.getDefaultValue())
                    .filter(
                        item -> item.trim().length() != 0 && item.trim().equalsIgnoreCase("true"))
                    .ifPresentOrElse(
                        ruleInput::setDefaultValue, () -> ruleInput.setDefaultValue("false"));
              }
              Optional<String> isTypeDropDownOrMultiSelect =
                  Optional.ofNullable(loadableStudyRuleInput.getTypeValue())
                      .filter(
                          item ->
                              item.trim().length() != 0
                                      && item.trim()
                                          .equalsIgnoreCase(
                                              com.cpdss.loadablestudy.domain.TypeValue.DROPDOWN
                                                  .getType())
                                  || item.trim()
                                      .equalsIgnoreCase(
                                          com.cpdss.loadablestudy.domain.TypeValue.MULTISELECT
                                              .getType()));
              if (isTypeDropDownOrMultiSelect.isPresent()) {
                Optional<String> isPrefixExist =
                    Optional.ofNullable(loadableStudyRuleInput.getPrefix())
                        .filter(
                            item ->
                                item.trim().length() != 0
                                    && item.trim()
                                        .equalsIgnoreCase(
                                            com.cpdss.loadablestudy.domain.RuleMasterData.CargoTank
                                                .getPrefix()));
                Optional<String> isSuffixExist =
                    Optional.ofNullable(loadableStudyRuleInput.getSuffix())
                        .filter(
                            item ->
                                item.trim().length() != 0
                                    && item.trim()
                                        .equalsIgnoreCase(
                                            com.cpdss.loadablestudy.domain.RuleMasterData.CargoTank
                                                .getSuffix()));
                List<com.cpdss.loadablestudy.domain.RuleDropDownMaster> ruleDropDownMasterList =
                    new ArrayList<>();
                if (isSuffixExist.isPresent() && isPrefixExist.isPresent()) {
                  vesselRuleReply
                      .getCargoTankMasterList()
                      .forEach(
                          cargoTank -> {
                            com.cpdss.loadablestudy.domain.RuleDropDownMaster ruleDropDownMaster =
                                new com.cpdss.loadablestudy.domain.RuleDropDownMaster();
                            Optional.ofNullable(cargoTank.getId())
                                .ifPresent(ruleDropDownMaster::setId);
                            Optional.ofNullable(cargoTank.getShortName())
                                .ifPresent(ruleDropDownMaster::setValue);
                            ruleDropDownMasterList.add(ruleDropDownMaster);
                          });
                  ruleInput.setRuleDropDownMaster(ruleDropDownMasterList);
                } else {
                  Optional<Long> ruleTempId = Optional.ofNullable(rulesList.getParentRuleXId());
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
                          com.cpdss.loadablestudy.domain.RuleDropDownMaster ruleDropDownMaster =
                              new com.cpdss.loadablestudy.domain.RuleDropDownMaster();
                          Optional.ofNullable(masterDropDownRule.getId())
                              .ifPresent(ruleDropDownMaster::setId);
                          Optional.ofNullable(masterDropDownRule.getValue())
                              .ifPresent(ruleDropDownMaster::setValue);
                          ruleDropDownMasterList.add(ruleDropDownMaster);
                        });
                  }
                  ruleInput.setRuleDropDownMaster(ruleDropDownMasterList);
                }
              }
              ruleInputList.add(ruleInput);
            }
            rules.setInputs(ruleInputList);
            ruleList.add(rules);
          }
        });
    rulePlan.setRules(ruleList);
  }
}
