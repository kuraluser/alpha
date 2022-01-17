/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static com.cpdss.dischargeplan.common.DischargePlanConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest;
import com.cpdss.common.generated.discharge_plan.PortData;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.AdminRuleTemplate;
import com.cpdss.dischargeplan.common.AdminRuleValueExtract;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.common.RuleUtility;
import com.cpdss.dischargeplan.domain.rules.RuleRequest;
import com.cpdss.dischargeplan.domain.rules.RuleResponse;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeRuleService {

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  public void setDischargeInfoDefaultValues(
      DischargeInformation dischargeInformation,
      PortData port,
      DischargeStudyDataTransferRequest request)
      throws GenericServiceException {
    // RPC call to vessel info, Get Rules (default value for Discharge Info)
    RuleResponse ruleResponse =
        this.getRulesByVesselIdAndSectionId(
            request.getVesselId(), DischargePlanConstants.DISCHARGING_RULE_MASTER_ID, null, null);
    AdminRuleValueExtract extract =
        AdminRuleValueExtract.builder().plan(ruleResponse.getPlan()).build();

    // Trim Values
    var initialTrim = extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_INITIAL_TRIM);
    log.info(
        "Discharge Info Default Admin Rule Id {}, Value {}",
        AdminRuleTemplate.DISCHARGE_INITIAL_TRIM,
        initialTrim);
    if (initialTrim != null && !initialTrim.isEmpty()) {
      dischargeInformation.setInitialTrim(new BigDecimal(initialTrim));
    }

    // Setting Initial Trim value with default as zero since the rules module is on hold.
    dischargeInformation.setInitialTrim(new BigDecimal(0));

    var maximumTrim = extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_MAXIMUM_TRIM, true);
    log.info(
        "Discharge Info Default Admin Rule Id {}, Value {}",
        AdminRuleTemplate.DISCHARGE_MAXIMUM_TRIM,
        maximumTrim);
    if (maximumTrim != null && !maximumTrim.isEmpty()) {
      dischargeInformation.setMaximumTrim(new BigDecimal(maximumTrim));
    }

    var finalTrim = "";
    if (port.getPortOrder() >= 0) {
      var lastPortId =
          request.getPortDataList().stream().map(PortData::getPortOrder).max(Integer::compareTo);
      if (lastPortId.isPresent() && (lastPortId.get().equals(port.getPortOrder()))) {
        finalTrim = extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_FINAL_TRIM_LAST_PORT);
        log.info(
            "Discharge Info Default Admin Rule Id {}, Value {}",
            AdminRuleTemplate.DISCHARGE_FINAL_TRIM_LAST_PORT,
            finalTrim);
      } else {
        finalTrim = extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_FINAL_TRIM);
        log.info(
            "Discharge Info Default Admin Rule Id {}, Value {}",
            AdminRuleTemplate.DISCHARGE_FINAL_TRIM,
            finalTrim);
      }
    }
    if (finalTrim != null && !finalTrim.isEmpty()) { // identify last port or not
      dischargeInformation.setFinalTrim(new BigDecimal(finalTrim));
    }

    // Discharge Rates
    var maxBallastRate =
        extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_MAX_DE_BALLAST_RATE);
    log.info(
        "Discharge Info Default Admin Rule Id {}, Value {}",
        AdminRuleTemplate.DISCHARGE_MAX_DE_BALLAST_RATE,
        maxBallastRate);
    if (maxBallastRate != null && !maxBallastRate.isEmpty()) {
      dischargeInformation.setMaxBallastRate(new BigDecimal(maxBallastRate));
    }
    var minBallastRate =
        extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_MIN_DE_BALLAST_RATE);
    log.info(
        "Discharge Info Default Admin Rule Id {}, Value {}",
        AdminRuleTemplate.DISCHARGE_MIN_DE_BALLAST_RATE,
        minBallastRate);
    if (minBallastRate != null && !minBallastRate.isEmpty()) {
      dischargeInformation.setMinBallastRate(new BigDecimal(minBallastRate));
    }
    var initialDischargingRate =
        extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_INITIAL_RATE);
    log.info(
        "Discharge Info Default Admin Rule Id {}, Value {}",
        AdminRuleTemplate.DISCHARGE_INITIAL_RATE,
        initialDischargingRate);
    if (initialDischargingRate != null && !initialDischargingRate.isEmpty()) {
      dischargeInformation.setInitialDischargingRate(new BigDecimal(initialDischargingRate));
    }
    var maxDischargingRate =
        extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_MAXIMUM_RATE);
    log.info(
        "Discharge Info Default Admin Rule Id {}, Value {}",
        AdminRuleTemplate.DISCHARGE_MAXIMUM_RATE,
        maxDischargingRate);
    if (maxDischargingRate != null && !maxDischargingRate.isEmpty()) {
      dischargeInformation.setMaxDischargingRate(new BigDecimal(maxDischargingRate));
    }

    // Post Discharge Rates
    var dischargeTimeForDryCheck =
        extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_TIME_FOR_DRY_CHECK);
    log.info(
        "Discharge Info Default Admin Rule Id {}, Value {}",
        AdminRuleTemplate.DISCHARGE_TIME_FOR_DRY_CHECK,
        dischargeTimeForDryCheck);
    if (dischargeTimeForDryCheck != null && !dischargeTimeForDryCheck.isEmpty()) {
      dischargeInformation.setTimeForDryCheck(new BigDecimal(dischargeTimeForDryCheck));
    }
    var dischargeSlopDischarge =
        extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_SLOP_DISCHARGE);
    log.info(
        "Discharge Info Default Admin Rule Id {}, Value {}",
        AdminRuleTemplate.DISCHARGE_SLOP_DISCHARGE,
        dischargeSlopDischarge);
    if (dischargeSlopDischarge != null && !dischargeSlopDischarge.isEmpty()) {
      dischargeInformation.setTimeForSlopDischarging(new BigDecimal(dischargeSlopDischarge));
    }
    var dischargeFinalStripping =
        extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_FINAL_STRIPPING);
    log.info(
        "Discharge Info Default Admin Rule Id {}, Value {}",
        AdminRuleTemplate.DISCHARGE_FINAL_STRIPPING,
        dischargeFinalStripping);
    if (dischargeFinalStripping != null && !dischargeFinalStripping.isEmpty()) {
      dischargeInformation.setTimeForFinalStripping(new BigDecimal(dischargeFinalStripping));
    }
    var dischargeFreshOilWashing =
        extract.getDefaultValueForKey(AdminRuleTemplate.DISCHARGE_FRESH_OIL_WASHING);
    log.info(
        "Discharge Info Default Admin Rule Id {}, Value {}",
        AdminRuleTemplate.DISCHARGE_FRESH_OIL_WASHING,
        dischargeFreshOilWashing);
    if (dischargeFreshOilWashing != null && !dischargeFreshOilWashing.isEmpty()) {
      dischargeInformation.setFreshOilWashing(new BigDecimal(dischargeFreshOilWashing));
    }
  }

  public RuleResponse getRulesByVesselIdAndSectionId(
      Long vesselId, Long sectionId, RuleRequest vesselRuleRequest, String correlationId)
      throws GenericServiceException {
    VesselInfo.VesselRuleRequest.Builder vesselRuleBuilder =
        VesselInfo.VesselRuleRequest.newBuilder();
    vesselRuleBuilder.setSectionId(sectionId);
    vesselRuleBuilder.setVesselId(vesselId);
    vesselRuleBuilder.setIsNoDefaultRule(false);
    vesselRuleBuilder.setIsFetchEnabledRules(false);
    VesselInfo.VesselRuleReply vesselRuleReply =
        this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(vesselRuleBuilder.build());
    RuleResponse ruleResponse = new RuleResponse();
    if (!SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get or save Vessel rules ",
          vesselRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselRuleReply.getResponseStatus().getCode())));
    }
    ruleResponse.setPlan(RuleUtility.buildAdminRulePlan(vesselRuleReply));
    ruleResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return ruleResponse;
  }
}
