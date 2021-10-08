/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.DATE_FORMAT;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.PORT;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.PORT_EXCEL_TEMPLATE_TITLES;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.PORT_TITLE_FONT_HEIGHT;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.PORT_TITLE_FONT_STYLE;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.SHEET;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.TIDE_DATE;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.TIDE_HEIGHT;
import static com.cpdss.dischargeplan.common.DischargePlanConstants.TIDE_TIME;

import com.cpdss.common.constants.RedisConfigConstants;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequestWithPaging;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.generated.discharge_plan.DischargeRuleReply;
import com.cpdss.common.generated.discharge_plan.DischargeRuleRequest;
import com.cpdss.common.generated.discharge_plan.DischargingDownloadTideDetailRequest;
import com.cpdss.common.generated.discharge_plan.DischargingDownloadTideDetailStatusReply.Builder;
import com.cpdss.common.generated.discharge_plan.DischargingUploadTideDetailRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.domain.rules.RuleMasterData;
import com.cpdss.dischargeplan.domain.rules.RuleMasterSection;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargePlanRuleInput;
import com.cpdss.dischargeplan.entity.DischargePlanRules;
import com.cpdss.dischargeplan.entity.DischargingBerthDetail;
import com.cpdss.dischargeplan.entity.DischargingInformationStatus;
import com.cpdss.dischargeplan.entity.PortTideDetail;
import com.cpdss.dischargeplan.repository.DischargeBerthDetailRepository;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargeRulesInputRepository;
import com.cpdss.dischargeplan.repository.DischargeRulesRepository;
import com.cpdss.dischargeplan.repository.PortTideDetailsRepository;
import com.google.protobuf.ByteString;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

  @Autowired DischargeBerthDetailRepository dischargeBerthDetailRepository;

  @Autowired DischargeRulesRepository dischargeStudyRulesRepository;

  @Autowired DischargeRulesInputRepository dischargeStudyRulesInputRepository;

  @Autowired PortTideDetailsRepository portTideDetailsRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

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
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder)
      throws GenericServiceException {
    DischargeInformation disEntity =
        this.getDischargeInformation(
            request.getVesselId(), request.getVoyageId(), request.getPortRotationId());
    if (disEntity == null) {
      log.error(
          "Discharge information not found for Vessel Id {}, Voyage Id {}, PortR Id {}",
          request.getVesselId(),
          request.getVoyageId(),
          request.getPortRotationId());
      throw new GenericServiceException(
          "No Discharge information found",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.NO_CONTENT);
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

    // Set Discharge berth
    List<DischargingBerthDetail> listVarB =
        this.dischargeBerthDetailRepository.findAllByDischargingInformationIdAndIsActiveTrue(
            disEntity.getId());
    this.informationBuilderService.buildDischargeBerthMessageFromEntity(
        disEntity, listVarB, builder);

    // Set Stages
    this.informationBuilderService.buildDischargeStageMessageFromEntity(disEntity, builder);

    // Set Delay
    this.informationBuilderService.buildDischargeDelaysMessageFromEntity(disEntity, builder);

    // Set Post Discharge stage
    this.informationBuilderService.buildPostDischargeStageMessageFromEntity(disEntity, builder);

    // set Pump and Machine Details
    this.informationBuilderService.buildMachineInUseMessageFromEntity(disEntity, builder);

    // Set Cow Details
    this.informationBuilderService.buildCowPlanMessageFromEntity(disEntity, builder);

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
    if (dischargeInformation.isEmpty()) {
      log.error(
          "Failed to get discharge study for get or save rule {}", request.getDischargeInfoId());
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
                                                        RuleMasterData.CargoTank.getPrefix()));
                            Optional<String> isSuffixExist =
                                Optional.ofNullable(input.getSuffix())
                                    .filter(
                                        item ->
                                            item.trim().length() != 0
                                                && item.trim()
                                                    .equalsIgnoreCase(
                                                        RuleMasterData.CargoTank.getSuffix()));
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

  /**
   * upload Port Tide Details to DB.
   *
   * @param request - DischargingUploadTideDetailRequest.
   * @throws GenericServiceException - throws GenericServiceException from the method.
   */
  public void uploadPortTideDetails(DischargingUploadTideDetailRequest request)
      throws GenericServiceException {

    try {
      ByteString tideDetaildata = request.getTideDetaildata();
      Map<Long, String> portDetails = getPortDetailsFromPortService();
      InputStream bin = new ByteArrayInputStream(tideDetaildata.toByteArray());
      Workbook workbook = WorkbookFactory.create(bin);
      Sheet sheetAt = workbook.getSheet(DischargePlanConstants.SHEET);
      Iterator<Row> rowIterator = sheetAt.iterator();
      if (rowIterator.hasNext()) {
        rowIterator.next();
      }
      List<PortTideDetail> tideDetails = new ArrayList<>();
      while (rowIterator.hasNext()) {
        PortTideDetail tideDetail = new PortTideDetail();
        tideDetail.setDischargingXid(request.getLoadingId());
        tideDetail.setIsActive(true);
        Row row = rowIterator.next();
        Iterator<Cell> cellIterator = row.cellIterator();
        for (int rowCell = 0; rowCell <= 3; rowCell++) {
          Cell cell = cellIterator.next();
          CellType cellType = cell.getCellType();
          // fetch String value from excel
          if (rowCell == 0) {
            if (!cellType.equals(CellType.STRING)) {
              throw new IllegalStateException(CommonErrorCodes.E_CPDSS_PORT_NAME_INVALID);
            }
            Optional<Long> findFirst =
                portDetails.entrySet().stream()
                    .filter(e -> cell.getStringCellValue().equalsIgnoreCase(e.getValue()))
                    .map(Map.Entry::getKey)
                    .findFirst();
            if (!findFirst.isPresent()) {
              throw new IllegalStateException(CommonErrorCodes.E_CPDSS_PORT_NAME_INVALID);
            }
            tideDetail.setPortXid(findFirst.get());
          }
          // fetch Date value from excel
          if (rowCell == 1) {
            if (cellType.equals(CellType.NUMERIC)) {
              double numberValue = cell.getNumericCellValue();
              if (DateUtil.isCellDateFormatted(cell)) {
                tideDetail.setTideDate(DateUtil.getJavaDate(numberValue));
              } else {
                throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_DATE_INVALID);
              }
            } else if (cellType.equals(CellType.STRING)) {
              if (!cell.getStringCellValue().matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
                throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_DATE_INVALID);
              }
              tideDetail.setTideDate(
                  new SimpleDateFormat(DATE_FORMAT).parse(cell.getStringCellValue()));
            } else {
              throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_DATE_INVALID);
            }
          }
          // fetch Time value from excel
          if (rowCell == 2) {
            if (cellType.equals(CellType.NUMERIC)) {
              if (DateUtil.isCellDateFormatted(cell)) {
                if (cell.getLocalDateTimeCellValue().toLocalTime().equals(LocalTime.of(0, 0))) {
                  throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_TIME_INVALID);
                }
                tideDetail.setTideTime(cell.getLocalDateTimeCellValue().toLocalTime());
              } else {
                throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_TIME_INVALID);
              }
            } else if (cellType.equals(CellType.STRING)) {
              if (!cell.getStringCellValue().matches("([0-9]{2}):([0-9]{2})")) {
                throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_TIME_INVALID);
              }
              tideDetail.setTideTime(LocalTime.parse(cell.getStringCellValue()));
            } else {
              throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_TIME_INVALID);
            }
          }
          // fetch Double value from excel
          if (rowCell == 3) {
            if (!cellType.equals(CellType.NUMERIC)) {
              throw new IllegalStateException(CommonErrorCodes.E_CPDSS_TIDE_HEIGHT_INVALID);
            }
            tideDetail.setTideHeight(
                new BigDecimal(cell.getNumericCellValue(), MathContext.DECIMAL64));
          }
        }
        tideDetails.add(tideDetail);
      }
      portTideDetailsRepository.updatePortDetailActiveState(request.getLoadingId());
      portTideDetailsRepository.saveAll(tideDetails);
    } catch (IllegalStateException e) {
      throw new GenericServiceException(e.getMessage(), e.getMessage(), HttpStatusCode.BAD_REQUEST);
    } catch (Exception e) {
      throw new GenericServiceException(
          e.getMessage(),
          CommonErrorCodes.E_HTTP_INTERNAL_SERVER_ERROR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * download Port Tide Details template
   *
   * @param workbook - XSSFWorkbook
   * @param request - DownloadTideDetailRequest
   * @param builder - Builder class
   * @throws GenericServiceException - throws GenericServiceException from the method.
   */
  public void downloadPortTideDetails(
      XSSFWorkbook workbook, DischargingDownloadTideDetailRequest request, Builder builder)
      throws GenericServiceException, IOException {
    try {
      XSSFSheet spreadsheet = workbook.createSheet(SHEET);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      int rowNo = 0;
      XSSFRow titleRow = spreadsheet.createRow(rowNo);
      XSSFCellStyle cellStyle = workbook.createCellStyle();
      XSSFFont font = workbook.createFont();
      font.setFontName(PORT_TITLE_FONT_STYLE);
      font.setFontHeight(PORT_TITLE_FONT_HEIGHT);
      font.setBold(true);
      cellStyle.setFont(font);
      for (int columnNo = 0; columnNo < PORT_EXCEL_TEMPLATE_TITLES.size(); columnNo++) {
        spreadsheet.setColumnWidth(columnNo, 17 * 256);
        XSSFCell titleCell = titleRow.createCell(columnNo);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue(PORT_EXCEL_TEMPLATE_TITLES.get(columnNo));
      }
      long loadingId = request.getLoadingId();
      if (loadingId != 0) {
        List<PortTideDetail> list =
            portTideDetailsRepository.findByDischargingXidAndIsActive(request.getLoadingId(), true);
        if (!list.isEmpty()) {
          Map<Long, String> portsMap = getPortDetailsFromPortService();
          for (rowNo = 0; rowNo < list.size(); rowNo++) {
            XSSFRow row = spreadsheet.createRow(rowNo + 1);
            for (int columnNo = 0; columnNo < PORT_EXCEL_TEMPLATE_TITLES.size(); columnNo++) {
              XSSFCell cell = row.createCell(columnNo);
              if (PORT_EXCEL_TEMPLATE_TITLES.get(columnNo).equals(PORT)) {
                cell.setCellType(CellType.STRING);
                cell.setCellValue(portsMap.get(list.get(rowNo).getPortXid()));
              }
              if (PORT_EXCEL_TEMPLATE_TITLES.get(columnNo).equals(TIDE_DATE)) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(
                    new SimpleDateFormat(DATE_FORMAT).format(list.get(rowNo).getTideDate()));
              }
              if (PORT_EXCEL_TEMPLATE_TITLES.get(columnNo).equals(TIDE_TIME)) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(list.get(rowNo).getTideTime().toString());
              }
              if (PORT_EXCEL_TEMPLATE_TITLES.get(columnNo).equals(TIDE_HEIGHT)) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(list.get(rowNo).getTideHeight().doubleValue());
              }
            }
          }
        }
      }
      workbook.write(byteArrayOutputStream);
      byte[] bytes = byteArrayOutputStream.toByteArray();
      builder
          .setData(ByteString.copyFrom(bytes))
          .setSize(bytes.length)
          .setResponseStatus(
              ResponseStatus.newBuilder()
                  .setStatus(SUCCESS)
                  .setCode(HttpStatusCode.OK.getReasonPhrase())
                  .build())
          .build();
      byteArrayOutputStream.close();
    } catch (Exception e) {
      throw new GenericServiceException(
          e.getMessage(),
          CommonErrorCodes.E_HTTP_INTERNAL_SERVER_ERROR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }
  /**
   * To fetch port details from port service.
   *
   * @return Map of Key value pair
   */
  private Map<Long, String> getPortDetailsFromPortService() {
    PortRequestWithPaging redisRequest =
        PortRequestWithPaging.newBuilder()
            .setOffset(RedisConfigConstants.OFFSET_VAL)
            .setLimit(RedisConfigConstants.PAGE_COUNT)
            .build();
    PortReply reply = portInfoServiceBlockingStub.getPortInfoByPaging(redisRequest);
    List<PortDetail> portsList = reply.getPortsList();
    Map<Long, String> portsMap = new HashMap<>();
    if (!portsList.isEmpty()) {
      portsMap =
          portsList.stream().collect(Collectors.toMap(map -> map.getId(), map -> map.getName()));
    }
    return portsMap;
  }

  public void updateDischargingPlanDetailsFromAlgo(Long id, String dischargingPlanDetailsFromAlgo) {
    dischargeInformationRepository.updateDischargingPlanDetailsFromAlgo(
        id, dischargingPlanDetailsFromAlgo);
  }

  public void updateDischargingInformationStatus(
      DischargingInformationStatus dischargingInformationStatus, Long id) {
    dischargeInformationRepository.updateDischargingInformationStatus(
        dischargingInformationStatus, id);
  }

  public void updateDischargingInformationStatuses(
      DischargingInformationStatus dischargingInformationStatus,
      DischargingInformationStatus arrivalStatus,
      DischargingInformationStatus departureStatus,
      Long id) {
    dischargeInformationRepository.updateDischargingInformationStatuses(
        dischargingInformationStatus, arrivalStatus.getId(), departureStatus.getId(), id);
  }

  public void updateIsDischargingSequenceGeneratedStatus(Long id, boolean sequence) {
    dischargeInformationRepository.updateIsDischargingSequenceGeneratedStatus(id, sequence);
  }

  public void updateIsDischargingPlanGeneratedStatus(Long id, boolean isPlanGenerated) {
    dischargeInformationRepository.updateIsDischargingPlanGeneratedStatus(id, isPlanGenerated);
  }
}
