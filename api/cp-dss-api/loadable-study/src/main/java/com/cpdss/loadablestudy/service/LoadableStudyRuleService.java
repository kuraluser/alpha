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
    VesselInfo.VesselRuleRequest.Builder vesselRuleBuilder =
        VesselInfo.VesselRuleRequest.newBuilder();
    vesselRuleBuilder.setSectionId(RuleMasterSection.Plan.getId());
    vesselRuleBuilder.setVesselId(request.getVesselId());
    vesselRuleBuilder.setIsNoDefaultRule(true);
    VesselInfo.VesselRuleReply vesselRuleReply =
        this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(vesselRuleBuilder.build());
    if (!SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get loadable study rule Details against vessel ",
          vesselRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselRuleReply.getResponseStatus().getCode())));
    }
    updateDisplayInSettingsInLoadableStudyRules(vesselRuleReply);
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

  void saveDuplicateLoadableStudyRules(
      List<LoadableStudyRules> listOfExistingLSRules,
      LoadableStudy currentLoableStudy,
      com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request) {
    if (listOfExistingLSRules != null && listOfExistingLSRules.size() > 0) {
      List<LoadableStudyRules> listOfLSRules = new ArrayList<LoadableStudyRules>();
      log.info("Existing loadable rules has value ");
      listOfExistingLSRules.stream()
          .forEach(
              lsRules -> {
                LoadableStudyRules loadableStudyRules = new LoadableStudyRules();
                loadableStudyRules.setLoadableStudy(currentLoableStudy);
                Optional.ofNullable(lsRules.getVesselRuleXId())
                    .ifPresent(item -> loadableStudyRules.setVesselRuleXId(item));
                Optional.ofNullable(request.getVesselId())
                    .ifPresent(item -> loadableStudyRules.setVesselXId(item));
                Optional.ofNullable(lsRules.getRuleTypeXId())
                    .ifPresent(item -> loadableStudyRules.setRuleTypeXId(item));
                Optional.ofNullable(lsRules.getDisplayInSettings())
                    .ifPresent(loadableStudyRules::setDisplayInSettings);
                Optional.ofNullable(lsRules.getIsEnable())
                    .ifPresent(loadableStudyRules::setIsEnable);
                Optional.ofNullable(lsRules.getIsHardRule())
                    .ifPresent(loadableStudyRules::setIsHardRule);
                Optional.ofNullable(lsRules.getIsActive())
                    .ifPresent(loadableStudyRules::setIsActive);
                Optional.ofNullable(lsRules.getNumericPrecision())
                    .ifPresent(loadableStudyRules::setNumericPrecision);
                Optional.ofNullable(lsRules.getNumericScale())
                    .ifPresent(loadableStudyRules::setNumericScale);
                Optional.ofNullable(lsRules.getParentRuleXId())
                    .ifPresent(loadableStudyRules::setParentRuleXId);
                List<LoadableStudyRuleInput> lisOfLsRulesInput = new ArrayList<>();
                lsRules.getLoadableStudyRuleInputs().stream()
                    .forEach(
                        lsRulesInput -> {
                          LoadableStudyRuleInput loadableStudyRuleInput =
                              new LoadableStudyRuleInput();
                          loadableStudyRuleInput.setLoadableStudyRuleXId(loadableStudyRules);
                          Optional.ofNullable(lsRulesInput.getPrefix())
                              .ifPresent(loadableStudyRuleInput::setPrefix);
                          Optional.ofNullable(lsRulesInput.getDefaultValue())
                              .ifPresent(loadableStudyRuleInput::setDefaultValue);
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
                              .ifPresent(loadableStudyRuleInput::setIsMandatory);
                          lisOfLsRulesInput.add(loadableStudyRuleInput);
                        });
                loadableStudyRules.setLoadableStudyRuleInputs(lisOfLsRulesInput);
                listOfLSRules.add(loadableStudyRules);
              });
      loadableStudyRuleRepository.saveAll(listOfLSRules);
    }
  }

  public void getOrSaveRulesForLoadableStudy(
      com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request,
      com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.Builder builder)
      throws GenericServiceException {
    if (!RuleMasterSection.Plan.getId().equals(request.getSectionId())) {
      throw new GenericServiceException(
          "Planning can be only fetched against loadble study not for loading or discharging module",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Optional<LoadableStudy> loadableStudy =
        loadableStudyRepository.findByIdAndIsActiveAndVesselXId(
            request.getLoadableStudyId(), true, request.getVesselId());
    if (!loadableStudy.isPresent()) {
      log.error("Failed to get loadable study for get or save rule", request.getLoadableStudyId());
      throw new GenericServiceException(
          "Loadble study with given id does not exist",
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
          HttpStatusCode.valueOf(Integer.valueOf(vesselRuleReply.getResponseStatus().getCode())));
    }
    if (!CollectionUtils.isEmpty(request.getRulePlanList())) {
      log.info("save loadable study rules");
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
                          if (rule.getId() != null && rule.getId().trim() != "") {
                            Optional<LoadableStudyRules> rVesselMapping =
                                loadableStudyRuleRepository.findById(Long.valueOf(rule.getId()));
                            if (rVesselMapping.isPresent()) {
                              loadableStudyRules = rVesselMapping.get();
                            }
                          }
                          loadableStudyRules.setLoadableStudy(loadableStudy.get());
                          loadableStudyRules.setIsActive(true);
                          Optional.ofNullable(rule.getDisplayInSettings())
                              .ifPresent(loadableStudyRules::setDisplayInSettings);
                          Optional.ofNullable(rule.getEnable())
                              .ifPresent(loadableStudyRules::setIsEnable);
                          Optional.ofNullable(rule.getIsHardRule())
                              .ifPresent(loadableStudyRules::setIsHardRule);
                          Optional.ofNullable(rule.getNumericPrecision())
                              .ifPresent(loadableStudyRules::setNumericPrecision);
                          Optional.ofNullable(rule.getNumericScale())
                              .ifPresent(loadableStudyRules::setNumericScale);
                          LoadableStudyRules finalLoadableStudyRules = loadableStudyRules;
                          Optional.ofNullable(rule.getRuleTemplateId())
                              .ifPresent(
                                  item ->
                                      finalLoadableStudyRules.setParentRuleXId(
                                          Long.parseLong(item)));
                          loadableStudyRules.setVesselXId(request.getVesselId());

                          if (rule.getRuleType() != null
                              && rule.getRuleType()
                                  .equalsIgnoreCase(RuleType.ABSOLUTE.getRuleType())) {
                            loadableStudyRules.setRuleTypeXId(RuleType.ABSOLUTE.getId());
                          }
                          if (rule.getRuleType() != null
                              && rule.getRuleType()
                                  .equalsIgnoreCase(RuleType.PREFERABLE.getRuleType())) {
                            loadableStudyRules.setRuleTypeXId(RuleType.PREFERABLE.getId());
                          }
                          Optional.ofNullable(rule.getVesselRuleXId())
                              .ifPresent(
                                  vesselRuleXId ->
                                      finalLoadableStudyRules.setVesselRuleXId(
                                          Long.parseLong(vesselRuleXId)));
                          List<LoadableStudyRuleInput> ruleVesselMappingInputList =
                              new ArrayList<>();
                          for (Common.RulesInputs input : rule.getInputsList()) {
                            LoadableStudyRuleInput ruleTemplateInput = new LoadableStudyRuleInput();
                            if (input.getId() != null && input.getId().trim() != "") {
                              Optional<LoadableStudyRuleInput> rTemplateInput =
                                  loadableStudyRuleInputRepository.findById(
                                      Long.valueOf(input.getId()));
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
                            ruleTemplateInput.setLoadableStudyRuleXId(loadableStudyRules);
                            try {
                              if (input.getType() != null
                                  && (input
                                          .getType()
                                          .trim()
                                          .equalsIgnoreCase(
                                              com.cpdss.loadablestudy.domain.TypeValue.DROPDOWN
                                                  .getType())
                                      || input
                                          .getType()
                                          .trim()
                                          .equalsIgnoreCase(
                                              com.cpdss.loadablestudy.domain.TypeValue.MULTISELECT
                                                  .getType()))
                                  && input.getDefaultValue() != null
                                  && input.getDefaultValue().trim() != "") {
                                if (input.getSuffix() != null
                                    && input.getPrefix() != null
                                    && input
                                        .getSuffix()
                                        .trim()
                                        .equalsIgnoreCase(
                                            com.cpdss.loadablestudy.domain.RuleMasterData.CargoTank
                                                .getSuffix())
                                    && input
                                        .getPrefix()
                                        .equalsIgnoreCase(
                                            com.cpdss.loadablestudy.domain.RuleMasterData.CargoTank
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
                          loadableStudyRules.setLoadableStudyRuleInputs(ruleVesselMappingInputList);
                          loadableStudyRulesList.add(loadableStudyRules);
                        });
              });
      loadableStudyRuleRepository.saveAll(loadableStudyRulesList);
    }
    updateDisplayInSettingsInLoadableStudyRules(vesselRuleReply);
    List<Long> ruleListId =
        vesselRuleReply.getRulePlanList().stream()
            .flatMap(rulesList -> rulesList.getRulesList().stream())
            .map(rules -> Long.parseLong(rules.getVesselRuleXId()))
            .collect(Collectors.toList());
    List<LoadableStudyRules> loadableStudyRulesList =
        loadableStudyRuleRepository
            .findByLoadableStudyAndVesselXIdAndIsActiveAndVesselRuleXIdInOrderById(
                loadableStudy.get(), request.getVesselId(), true, ruleListId);
    if (loadableStudyRulesList.size() > 0) {
      log.info("Fetch  loadable study rules");
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
                buildResponseForRules(
                    lStudyRulesList, ruleId, rulePlanBuider, builder, vesselRuleReply);
              });
    } else {
      log.info("Fetch default loadable study rules : ");
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
      LoadableStudyRuleInput ruleTemplateInput,
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

  private void buildResponseForRules(
      List<LoadableStudyRules> lStudyRulesList,
      List<Long> ruleId,
      com.cpdss.common.generated.Common.RulePlans.Builder rulePlanBuider,
      com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.Builder builder,
      VesselInfo.VesselRuleReply vesselRuleReply) {
    for (int ruleIndex = 0; ruleIndex < lStudyRulesList.size(); ruleIndex++) {
      Common.Rules.Builder rulesBuilder = Common.Rules.newBuilder();
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getIsEnable())
          .ifPresent(item -> rulesBuilder.setEnable(item));
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getDisplayInSettings())
          .ifPresent(item -> rulesBuilder.setDisplayInSettings(item));
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getId())
          .ifPresent(item -> rulesBuilder.setId(String.valueOf(item)));
      if (lStudyRulesList.get(ruleIndex).getRuleTypeXId() != null
          && lStudyRulesList.get(ruleIndex).getRuleTypeXId().equals(RuleType.ABSOLUTE.getId())) {
        rulesBuilder.setRuleType(RuleType.ABSOLUTE.getRuleType());
      }
      if (lStudyRulesList.get(ruleIndex).getRuleTypeXId() != null
          && lStudyRulesList.get(ruleIndex).getRuleTypeXId().equals(RuleType.PREFERABLE.getId())) {
        rulesBuilder.setRuleType(RuleType.PREFERABLE.getRuleType());
      }
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getIsHardRule())
          .ifPresent(item -> rulesBuilder.setIsHardRule(item));
      if (lStudyRulesList.get(ruleIndex).getIsHardRule() == null) {
        rulesBuilder.setIsHardRule(false);
      }
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getVesselRuleXId())
          .ifPresent(item -> rulesBuilder.setVesselRuleXId(String.valueOf(item)));
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getParentRuleXId())
          .ifPresent(item -> rulesBuilder.setRuleTemplateId(String.valueOf(item)));
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getNumericPrecision())
          .ifPresent(item -> rulesBuilder.setNumericPrecision(item));
      Optional.ofNullable(lStudyRulesList.get(ruleIndex).getNumericScale())
          .ifPresent(item -> rulesBuilder.setNumericScale(item));
      Common.RulesInputs.Builder ruleInput = Common.RulesInputs.newBuilder();
      for (int inputIndex = 0;
          inputIndex < lStudyRulesList.get(ruleIndex).getLoadableStudyRuleInputs().size();
          inputIndex++) {
        Common.RulesInputs.Builder finalRuleInput = ruleInput;
        Optional.ofNullable(
                lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getDefaultValue())
            .ifPresent(item -> finalRuleInput.setDefaultValue(item));
        Optional.ofNullable(
                lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getPrefix())
            .ifPresent(item -> finalRuleInput.setPrefix(item));
        Optional.ofNullable(
                lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getMinValue())
            .ifPresent(item -> finalRuleInput.setMin(item));
        Optional.ofNullable(
                lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getMaxValue())
            .ifPresent(item -> finalRuleInput.setMax(item));
        Optional.ofNullable(
                lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getTypeValue())
            .ifPresent(item -> finalRuleInput.setType(item));
        Optional.ofNullable(
                lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getSuffix())
            .ifPresent(item -> finalRuleInput.setSuffix(item));
        Optional.ofNullable(
                lStudyRulesList.get(ruleIndex).getLoadableStudyRuleInputs().get(inputIndex).getId())
            .ifPresent(item -> finalRuleInput.setId(String.valueOf(item)));
        Optional.ofNullable(
                lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getIsMandatory())
            .ifPresent(finalRuleInput::setIsMandatory);
        if (lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getTypeValue()
                != null
            && lStudyRulesList
                .get(ruleIndex)
                .getLoadableStudyRuleInputs()
                .get(inputIndex)
                .getTypeValue()
                .equalsIgnoreCase(com.cpdss.loadablestudy.domain.TypeValue.BOOLEAN.getType())
            && lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getDefaultValue()
                == null) {
          finalRuleInput.setDefaultValue("false");
        }
        if (lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getTypeValue()
                != null
            && (lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getTypeValue()
                    .trim()
                    .equalsIgnoreCase(com.cpdss.loadablestudy.domain.TypeValue.DROPDOWN.getType())
                || lStudyRulesList
                    .get(ruleIndex)
                    .getLoadableStudyRuleInputs()
                    .get(inputIndex)
                    .getTypeValue()
                    .trim()
                    .equalsIgnoreCase(
                        com.cpdss.loadablestudy.domain.TypeValue.MULTISELECT.getType()))) {
          Common.RuleDropDownMaster.Builder ruleDropDownMaster =
              Common.RuleDropDownMaster.newBuilder();
          if (lStudyRulesList
                      .get(ruleIndex)
                      .getLoadableStudyRuleInputs()
                      .get(inputIndex)
                      .getSuffix()
                  != null
              && lStudyRulesList
                      .get(ruleIndex)
                      .getLoadableStudyRuleInputs()
                      .get(inputIndex)
                      .getPrefix()
                  != null
              && lStudyRulesList
                  .get(ruleIndex)
                  .getLoadableStudyRuleInputs()
                  .get(inputIndex)
                  .getSuffix()
                  .trim()
                  .equalsIgnoreCase(
                      com.cpdss.loadablestudy.domain.RuleMasterData.CargoTank.getSuffix())
              && lStudyRulesList
                  .get(ruleIndex)
                  .getLoadableStudyRuleInputs()
                  .get(inputIndex)
                  .getPrefix()
                  .trim()
                  .equalsIgnoreCase(
                      com.cpdss.loadablestudy.domain.RuleMasterData.CargoTank.getPrefix())) {
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
                Optional.ofNullable(lStudyRulesList.get(ruleIndex).getParentRuleXId());
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
}
