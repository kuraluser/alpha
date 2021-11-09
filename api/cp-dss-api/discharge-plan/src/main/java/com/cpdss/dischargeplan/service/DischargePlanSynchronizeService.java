/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest;
import com.cpdss.common.generated.discharge_plan.PortData;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.Utils;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.domain.rules.RuleMasterSection;
import com.cpdss.dischargeplan.entity.CowPlanDetail;
import com.cpdss.dischargeplan.entity.CowTankDetail;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargePlanRuleInput;
import com.cpdss.dischargeplan.entity.DischargePlanRules;
import com.cpdss.dischargeplan.entity.DischargingInformationStatus;
import com.cpdss.dischargeplan.repository.CowPlanDetailRepository;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import com.cpdss.dischargeplan.repository.DischargeRulesRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
public class DischargePlanSynchronizeService {

  @Autowired DischargeInformationRepository dischargeInformationRepository;

  @Autowired DischargeInformationService dischargeInformationService;

  @Autowired CowPlanDetailRepository cowPlanDetailRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Autowired DischargeRulesRepository dischargeStudyRulesRepository;

  @Autowired DischargePlanAlgoService dischargePlanAlgoService;

  public void saveDischargeInformation(DischargeStudyDataTransferRequest request) {
    log.info("Discharge Study Synchronization Starts");
    List<PortData> portDataList = request.getPortDataList();
    List<DischargeInformation> infos = new ArrayList<>();
    portDataList.stream()
        .forEach(
            port -> {
              DischargeInformation dischargeInformation = new DischargeInformation();
              dischargeInformation.setVoyageXid(request.getVoyageId());
              dischargeInformation.setVesselXid(request.getVesselId());
              dischargeInformation.setDischargingPatternXid(request.getDischargePatternId());
              dischargeInformation.setPortRotationXid(port.getPortRotationId());
              dischargeInformation.setSynopticTableXid(port.getSynopticTableId());
              dischargeInformation.setIsActive(true);
              dischargeInformation.setPortXid(port.getPortId());
              try {
                Optional<DischargingInformationStatus> pendingStatusOpt =
                    dischargePlanAlgoService.getDischargingInformationStatus(
                        DischargePlanConstants.DISCHARGING_INFORMATION_PENDING_ID);
                pendingStatusOpt.ifPresent(
                    status -> {
                      dischargeInformation.setArrivalStatusId(status.getId());
                      dischargeInformation.setDischargingInformationStatus(status);
                      dischargeInformation.setDepartureStatusId(status.getId());
                    });
              } catch (GenericServiceException e) {
                log.error(
                    "Failed to fetch status with id {}",
                    DischargePlanConstants.DISCHARGING_INFORMATION_PENDING_ID);
              }
              infos.add(dischargeInformation);
              log.info("Discharge Study Synchronization Port Data - {}", Utils.toJson(port));
            });
    List<DischargeInformation> listOfDischargeInformationList =
        dischargeInformationRepository.saveAll(infos);
    listOfDischargeInformationList.forEach(
        dischargeInformationService -> {
          try {
            saveRulesAgainstDischargingInformation(dischargeInformationService);
            log.info("Discharge Info Id : ", dischargeInformationService.getId());
          } catch (GenericServiceException e) {
            e.printStackTrace();
          }
        });
    log.info("Discharge Study Synchronization Finished");
  }

  /**
   * To fetch default discharging rules and save rules against discharging information
   *
   * @param dischargeInformationService
   * @throws GenericServiceException
   */
  private void saveRulesAgainstDischargingInformation(
      DischargeInformation dischargeInformationService) throws GenericServiceException {
    VesselInfo.VesselRuleRequest.Builder vesselRuleBuilder =
        VesselInfo.VesselRuleRequest.newBuilder();
    vesselRuleBuilder.setSectionId(RuleMasterSection.Discharging.getId());
    vesselRuleBuilder.setVesselId(dischargeInformationService.getVesselXid());
    vesselRuleBuilder.setIsFetchEnabledRules(false);
    vesselRuleBuilder.setIsNoDefaultRule(true);
    VesselInfo.VesselRuleReply vesselRuleReply =
        this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(vesselRuleBuilder.build());
    if (!SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get discharging rule Details ",
          vesselRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.parseInt(vesselRuleReply.getResponseStatus().getCode())));
    }
    List<DischargePlanRules> dischargingRulesList = new ArrayList<>();
    vesselRuleReply.getRulePlanList().stream()
        .forEach(
            plan -> {
              plan.getRulesList()
                  .forEach(
                      dischargingInfoRules -> {
                        DischargePlanRules dischargingRule = new DischargePlanRules();
                        dischargingRule.setDischargeInformation(dischargeInformationService);
                        Optional.ofNullable(dischargingInfoRules.getVesselRuleXId())
                            .ifPresent(
                                vesselRuleId ->
                                    dischargingRule.setVesselRuleXId(Long.parseLong(vesselRuleId)));
                        Optional.ofNullable(dischargeInformationService.getVesselXid())
                            .ifPresent(dischargingRule::setVesselXId);
                        if (!CollectionUtils.isEmpty(vesselRuleReply.getRuleTypeMasterList())
                            && dischargingInfoRules.getRuleType() != null
                            && dischargingInfoRules.getRuleType().trim() != "") {
                          Optional<VesselInfo.RuleTypeMaster> ruleType =
                              vesselRuleReply.getRuleTypeMasterList().stream()
                                  .filter(
                                      rType ->
                                          rType
                                              .getRuleType()
                                              .equalsIgnoreCase(dischargingInfoRules.getRuleType()))
                                  .findAny();
                          ruleType.orElseThrow(RuntimeException::new);
                          dischargingRule.setRuleTypeXId(ruleType.get().getId());
                        }
                        Optional.ofNullable(dischargingInfoRules.getDisplayInSettings())
                            .ifPresentOrElse(
                                dischargingRule::setDisplayInSettings,
                                () -> dischargingRule.setDisplayInSettings(false));
                        Optional.ofNullable(dischargingInfoRules.getEnable())
                            .ifPresentOrElse(
                                dischargingRule::setIsEnable,
                                () -> dischargingRule.setIsEnable(false));
                        Optional.ofNullable(dischargingInfoRules.getIsHardRule())
                            .ifPresentOrElse(
                                dischargingRule::setIsHardRule,
                                () -> dischargingRule.setIsHardRule(false));
                        dischargingRule.setIsActive(true);
                        Optional.ofNullable(dischargingInfoRules.getNumericPrecision())
                            .ifPresent(dischargingRule::setNumericPrecision);
                        Optional.ofNullable(dischargingInfoRules.getNumericScale())
                            .ifPresent(dischargingRule::setNumericScale);
                        Optional.ofNullable(dischargingInfoRules.getRuleTemplateId())
                            .ifPresent(
                                item -> dischargingRule.setParentRuleXId(Long.parseLong(item)));
                        List<DischargePlanRuleInput> lisOfDischargingRulesInput = new ArrayList<>();
                        dischargingInfoRules
                            .getInputsList()
                            .forEach(
                                dischargingInfoRulesInput -> {
                                  DischargePlanRuleInput loadingRuleInput =
                                      new DischargePlanRuleInput();
                                  loadingRuleInput.setDischargePlanRules(dischargingRule);
                                  Optional.ofNullable(dischargingInfoRulesInput.getPrefix())
                                      .ifPresent(loadingRuleInput::setPrefix);
                                  Optional.ofNullable(dischargingInfoRulesInput.getDefaultValue())
                                      .ifPresentOrElse(
                                          loadingRuleInput::setDefaultValue,
                                          () -> loadingRuleInput.setDefaultValue(null));
                                  Optional.ofNullable(dischargingInfoRulesInput.getType())
                                      .ifPresent(loadingRuleInput::setTypeValue);
                                  Optional.ofNullable(dischargingInfoRulesInput.getMax())
                                      .ifPresent(loadingRuleInput::setMaxValue);
                                  Optional.ofNullable(dischargingInfoRulesInput.getMin())
                                      .ifPresent(loadingRuleInput::setMinValue);
                                  Optional.ofNullable(dischargingInfoRulesInput.getSuffix())
                                      .ifPresent(loadingRuleInput::setSuffix);
                                  loadingRuleInput.setIsActive(true);
                                  Optional.ofNullable(dischargingInfoRulesInput.getIsMandatory())
                                      .ifPresentOrElse(
                                          loadingRuleInput::setIsMandatory,
                                          () -> loadingRuleInput.setIsMandatory(false));
                                  lisOfDischargingRulesInput.add(loadingRuleInput);
                                });
                        dischargingRule.setDischargePlanRuleInputList(lisOfDischargingRulesInput);
                        dischargingRulesList.add(dischargingRule);
                      });
            });
    dischargeStudyRulesRepository.saveAll(dischargingRulesList);
  }

  public void saveCowDetailsForDischargeStudy(DischargeStudyDataTransferRequest request) {
    for (PortData portData : request.getPortDataList()) {
      try {
        DischargeInformation entity =
            this.dischargeInformationService.getDischargeInformation(
                request.getVesselId(), request.getVoyageId(), portData.getPortRotationId());
        if (entity != null) {
          CowPlanDetail cowPlanDetail = new CowPlanDetail();
          cowPlanDetail.setDischargeInformation(entity);
          cowPlanDetail.setCowOperationType(portData.getCowDetails().getCowOptionTypeValue());
          cowPlanDetail.setCowPercentage(new BigDecimal(portData.getCowDetails().getPercent()));
          if (portData.getCowDetails().getCowTankDetails() != null) {
            portData
                .getCowDetails()
                .getCowTankDetails()
                .getTankIdsList()
                .forEach(
                    v -> {
                      CowTankDetail tankDetails = new CowTankDetail();
                      tankDetails.setCowPlanDetail(cowPlanDetail);
                      tankDetails.setDischargingXid(entity.getId());
                      tankDetails.setCowTypeXid(
                          portData.getCowDetails().getCowTankDetails().getCowType().getNumber());
                      tankDetails.setTankXid(v);
                    });
          }
          cowPlanDetailRepository.save(cowPlanDetail);
          log.info("Cow Plan Details saved for Port R Id - {}", portData.getPortRotationId());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
