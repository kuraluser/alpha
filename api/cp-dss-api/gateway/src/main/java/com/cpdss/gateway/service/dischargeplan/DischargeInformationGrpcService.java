/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import static com.cpdss.gateway.common.GatewayConstants.DISCHARGING_RULE_MASTER_ID;
import static com.cpdss.gateway.common.GatewayConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.discharge_plan.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.RuleRequest;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.utility.RuleUtility;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeInformationGrpcService {

  @GrpcClient("dischargeInformationService")
  DischargeInformationServiceGrpc.DischargeInformationServiceBlockingStub dischargeInfoServiceStub;

  private static final String SUCCESS = "SUCCESS";

  public DischargeInformation getDischargeInfoRpc(Long vesselId, Long voyageId, Long portRotationId)
      throws GenericServiceException {

    DischargeInformationRequest.Builder reqBuilder = DischargeInformationRequest.newBuilder();
    reqBuilder.setVoyageId(voyageId);
    reqBuilder.setVesselId(vesselId);
    reqBuilder.setPortRotationId(portRotationId);
    DischargeInformation rpcReplay =
        this.dischargeInfoServiceStub.getDischargeInformation(reqBuilder.build());
    if (!rpcReplay.getResponseStatus().getStatus().equals(SUCCESS)) {
      log.error(
          "Failed to get discharge plan from discharge-plan Service, vessel id {}, voyage id {}, port r Id {}",
          vesselId,
          voyageId,
          portRotationId);
      throw new GenericServiceException(
          "Failed to fetch discharge plan",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    return rpcReplay;
  }

  public RuleResponse getOrSaveRulesForDischarge(
      Long vesselId,
      Long dischargeStudyId,
      RuleRequest dischargeStudyRuleRequest,
      String correlationId)
      throws GenericServiceException {
    DischargeRuleRequest.Builder dischargeStudyRuleRequestBuilder =
        DischargeRuleRequest.newBuilder();
    dischargeStudyRuleRequestBuilder.setVesselId(vesselId);
    dischargeStudyRuleRequestBuilder.setSectionId(DISCHARGING_RULE_MASTER_ID);
    dischargeStudyRuleRequestBuilder.setDischargeInfoId(dischargeStudyId);
    RuleUtility.buildRuleListForSaveDischargeStudy(
        dischargeStudyRuleRequest, null, dischargeStudyRuleRequestBuilder, null, false, false);
    DischargeRuleReply dischargeStudyRuleReply =
        dischargeInfoServiceStub.getOrSaveRulesForDischarging(
            dischargeStudyRuleRequestBuilder.build());
    if (!SUCCESS.equals(dischargeStudyRuleReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get discharge rules ",
          dischargeStudyRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(dischargeStudyRuleReply.getResponseStatus().getCode())));
    }
    RuleResponse ruleResponse = new RuleResponse();
    ruleResponse.setPlan(RuleUtility.buildDischargeRulePlan(dischargeStudyRuleReply));
    ruleResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return ruleResponse;
  }
}
