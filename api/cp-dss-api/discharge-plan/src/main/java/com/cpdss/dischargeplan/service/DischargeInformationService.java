/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.generated.discharge_plan.DischargeRuleReply;
import com.cpdss.common.generated.discharge_plan.DischargeRuleRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.domain.RuleMasterSection;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargePlanRuleInput;
import com.cpdss.dischargeplan.entity.DischargePlanRules;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargeRulesInputRepository;
import com.cpdss.dischargeplan.repository.DischargeRulesRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@Transactional
public class DischargeInformationService {

  @Autowired DischargeInformationRepository dischargeInformationRepository;

  @Autowired DischargeInformationBuilderService informationBuilderService;

  @Autowired DischargeRulesRepository dischargeStudyRulesRepository;

  @Autowired DischargeRulesInputRepository dischargeStudyRulesInputRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  public DischargeInformation getDischargeInformation(Long primaryKey) {
    return this.dischargeInformationRepository.findByIdAndIsActiveTrue(primaryKey).orElse(null);
  }

  public DischargeInformation getDischargeInformation(
      Long vesselId, Long voyage, Long portRotationId) {
    return this.dischargeInformationRepository
        .findByVesselXidAndVoyageXidAndPortRotationXidAndIsActiveTrue(
            vesselId, voyage, portRotationId)
        .orElse(null);
  }

  public void getDischargeInformation(
      DischargeInformationRequest request,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    DischargeInformation disEntity =
        this.getDischargeInformation(
            request.getVesselId(), request.getVoyageId(), request.getPortRotationId());
    if (disEntity == null) {
      log.error(
          "Discharge information not found for Vessel Id {}, Voyage Id {}, PortR Id {}",
          request.getVesselId(),
          request.getVoyageId(),
          request.getPortRotationId());
    }

    try {
      builder.setDischargeInfoId(disEntity.getId());
      builder.setSynopticTableId(disEntity.getSynopticTableXid());
      log.info("Setting Discharge PK and Synoptic Id");
    } catch (Exception e) {
      log.error("Failed to set PK, Synoptic Id in response - {}", e.getMessage());
    }

    // Set Discharge Details
    this.informationBuilderService.buildDischargeDetailsMessageFromEntity(disEntity, builder);

    // Set Discharge Rates
    this.informationBuilderService.buildDischargeRateMessageFromEntity(disEntity, builder);

    builder.setResponseStatus(
        Common.ResponseStatus.newBuilder()
            .setHttpStatusCode(HttpStatus.OK.value())
            .setStatus(DischargePlanConstants.SUCCESS)
            .build());
  }

  public void getOrSaveRulesForDischarge(
      DischargeRuleRequest request, DischargeRuleReply.Builder builder)
      throws GenericServiceException {
    if (!RuleMasterSection.Discharging.getId().equals(request.getSectionId())) {
      throw new GenericServiceException(
          "fetch rule for discharging study rule only not for loading or planning module",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Optional<DischargeInformation> dischargeInformation =
        dischargeInformationRepository.findByIdAndIsActiveAndVesselXid(
            request.getDischargeInfoId(), true, request.getVesselId());
    if (!dischargeInformation.isPresent()) {
      log.error("Failed to get discharge study for get or save rule", request.getDischargeInfoId());
      throw new GenericServiceException(
          "discharge study with given id does not exist",
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
    if (!DischargePlanConstants.SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get discharge study rule Details ",
          vesselRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.parseInt(vesselRuleReply.getResponseStatus().getCode())));
    }
    if (!CollectionUtils.isEmpty(request.getRulePlanList())) {
      log.info("save discharge study rules");
      saveRulesAgainstDischargeStudy(request, dischargeInformation, vesselRuleReply);
    }
    // Filter vessel rule primary key id
    List<Long> ruleListId =
        vesselRuleReply.getRulePlanList().stream()
            .flatMap(rulesList -> rulesList.getRulesList().stream())
            .map(rules -> Long.parseLong(rules.getVesselRuleXId()))
            .collect(Collectors.toList());
    // fetch discharge study rules based on vessel rule primary key id
    List<DischargePlanRules> dischargeStudyRulesList =
        dischargeStudyRulesRepository
            .findByDischargeInformationAndVesselXIdAndIsActiveAndVesselRuleXIdInOrderById(
                dischargeInformation.get(), request.getVesselId(), true, ruleListId);
    if (dischargeStudyRulesList.size() > 0) {
      log.info("Fetch discharge study rules");
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
                List<DischargePlanRules> dStudyRulesList =
                    dischargeStudyRulesList.stream()
                        .filter(lRuleList -> ruleId.contains(lRuleList.getVesselRuleXId()))
                        .collect(Collectors.toList());
                List<DischargePlanRules> sortedDStudyRulesList =
                    dStudyRulesList.stream()
                        .sorted(Comparator.comparingLong(DischargePlanRules::getId))
                        .collect(Collectors.toList());
                informationBuilderService.buildResponseForDSRules(
                    sortedDStudyRulesList, rulePlanBuider, builder, vesselRuleReply);
              });
    } else {
      log.info("default vessel rules ");
      informationBuilderService.buildResponseForDefaultDSRules(vesselRuleReply, builder);
    }
    builder.setResponseStatus(
        Common.ResponseStatus.newBuilder().setStatus(DischargePlanConstants.SUCCESS).build());
  }

  /**
   * save rules against discharge study
   *
   * @param request
   * @param dischargeStudy
   * @param vesselRuleReply
   */
  private void saveRulesAgainstDischargeStudy(
      DischargeRuleRequest request,
      Optional<DischargeInformation> dischargeStudy,
      VesselInfo.VesselRuleReply vesselRuleReply) {
    List<DischargePlanRules> dischargeStudyRulesList = new ArrayList<>();
    request
        .getRulePlanList()
        .forEach(
            rulePlans -> {
              rulePlans
                  .getRulesList()
                  .forEach(
                      rule -> {
                        DischargePlanRules dischargeStudyRules = new DischargePlanRules();
                        Optional<String> isRuleTemplateIdExist =
                            Optional.ofNullable(rule.getRuleTemplateId())
                                .filter(item -> item.trim().length() != 0);
                        if (rule.getId() != null && rule.getId().trim().length() != 0) {
                          Optional<DischargePlanRules> rVesselMapping =
                              dischargeStudyRulesRepository.findById(Long.valueOf(rule.getId()));
                          if (rVesselMapping.isPresent()) {
                            dischargeStudyRules = rVesselMapping.get();
                          } else {
                            log.info("No record exist for this id in discharge study rule table");
                            throw new RuntimeException(
                                "No record exist for this id in discharge study rule table");
                          }
                        } else {
                          if (isRuleTemplateIdExist.isPresent()) {
                            Optional<DischargePlanRules> loadableStudyRulesRecord =
                                dischargeStudyRulesRepository.checkIsRuleTemplateExist(
                                    dischargeStudy.get().getId(),
                                    true,
                                    Long.valueOf(rule.getRuleTemplateId()));
                            if (loadableStudyRulesRecord.isPresent()) {
                              log.info(
                                  "Duplicate row can't insert for given vessel id and parent rule id");
                              throw new RuntimeException(
                                  "Duplicate row can't insert for given vessel id and parent rule id");
                            }
                          }
                        }
                        dischargeStudyRules.setDischargeInformation(dischargeStudy.get());
                        dischargeStudyRules.setIsActive(true);
                        DischargePlanRules finalDischargeStudyRules = dischargeStudyRules;
                        Optional.ofNullable(rule.getDisplayInSettings())
                            .ifPresentOrElse(
                                dischargeStudyRules::setDisplayInSettings,
                                () -> finalDischargeStudyRules.setDisplayInSettings(false));
                        Optional.ofNullable(rule.getEnable())
                            .ifPresentOrElse(
                                dischargeStudyRules::setIsEnable,
                                () -> finalDischargeStudyRules.setIsEnable(false));
                        Optional.ofNullable(rule.getIsHardRule())
                            .ifPresentOrElse(
                                dischargeStudyRules::setIsHardRule,
                                () -> finalDischargeStudyRules.setIsHardRule(false));
                        Optional.ofNullable(rule.getNumericPrecision())
                            .ifPresent(dischargeStudyRules::setNumericPrecision);
                        Optional.ofNullable(rule.getNumericScale())
                            .ifPresent(dischargeStudyRules::setNumericScale);
                        Optional.ofNullable(rule.getRuleTemplateId())
                            .ifPresent(
                                item ->
                                    finalDischargeStudyRules.setParentRuleXId(
                                        Long.parseLong(item)));
                        dischargeStudyRules.setVesselXId(request.getVesselId());
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
                          dischargeStudyRules.setRuleTypeXId(ruleType.get().getId());
                        } else {
                          log.info("Rule Type can't be null");
                          throw new RuntimeException("Rule type can't be null");
                        }
                        Optional.ofNullable(rule.getVesselRuleXId())
                            .ifPresent(
                                vesselRuleXId ->
                                    finalDischargeStudyRules.setVesselRuleXId(
                                        Long.parseLong(vesselRuleXId)));
                        List<DischargePlanRuleInput> ruleVesselMappingInputList = new ArrayList<>();
                        for (Common.RulesInputs input : rule.getInputsList()) {
                          DischargePlanRuleInput ruleTemplateInput = new DischargePlanRuleInput();
                          if (input.getId() != null && input.getId().length() != 0) {
                            Optional<DischargePlanRuleInput> rTemplateInput =
                                dischargeStudyRulesInputRepository.findById(
                                    Long.valueOf(input.getId()));
                            if (rTemplateInput.isPresent()) {
                              ruleTemplateInput = rTemplateInput.get();
                            } else {
                              log.info(
                                  "No record exist for this id in rule discharging study rule input table");
                              throw new RuntimeException(
                                  "No record exist for this id in rule discharging study rule input table");
                            }
                          }
                          DischargePlanRuleInput finalRuleTemplateInput = ruleTemplateInput;
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
                          ruleTemplateInput.setDischargePlanRules(dischargeStudyRules);
                          Optional<String> isTypeDropDownOrMultiSelect =
                              Optional.ofNullable(input.getType())
                                  .filter(
                                      value ->
                                          value.trim().length() != 0
                                                  && value
                                                      .trim()
                                                      .equalsIgnoreCase(
                                                          com.cpdss.dischargeplan.domain.TypeValue
                                                              .DROPDOWN
                                                              .getType())
                                              || value
                                                  .trim()
                                                  .equalsIgnoreCase(
                                                      com.cpdss.dischargeplan.domain.TypeValue
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
                                                        com.cpdss.dischargeplan.domain
                                                            .RuleMasterData.CargoTank.getPrefix()));
                            Optional<String> isSuffixExist =
                                Optional.ofNullable(input.getSuffix())
                                    .filter(
                                        item ->
                                            item.trim().length() != 0
                                                && item.trim()
                                                    .equalsIgnoreCase(
                                                        com.cpdss.dischargeplan.domain
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
                        dischargeStudyRules.setDischargePlanRuleInputList(
                            ruleVesselMappingInputList);
                        dischargeStudyRulesList.add(dischargeStudyRules);
                      });
            });
    dischargeStudyRulesRepository.saveAll(dischargeStudyRulesList);
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
      DischargePlanRuleInput ruleTemplateInput,
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
}
