/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.LoadingUpdateUllageResponse;
import com.cpdss.gateway.domain.RuleRequest;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInformation;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationRequest;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoRequest;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;

public interface LoadingPlanService {

  Object getLoadingPortRotationDetails(Long vesselId, Long portRId) throws GenericServiceException;

  LoadingInformation getLoadingInformationByPortRotation(Long vesselId, Long planId, Long portRId)
      throws GenericServiceException;

  public LoadingInformationResponse saveLoadingInformation(
      LoadingInformationRequest request, String correlationId) throws GenericServiceException;

  RuleResponse getLoadingPlanRules(Long vesselId, Long voyageId, Long loadingInfoId)
      throws GenericServiceException;

  RuleResponse saveLoadingPlanRules(
      Long vesselId, Long voyageId, Long loadingInfoId, RuleRequest ruleRequest)
      throws GenericServiceException;

  LoadingSequenceResponse getLoadingSequence(Long vesselId, Long voyageId, Long infoId)
      throws GenericServiceException;

  LoadingPlanAlgoResponse saveLoadingPlan(
      Long vesselId, Long voyageId, Long infoId, LoadingPlanAlgoRequest loadingPlanAlgoRequest)
      throws GenericServiceException;

  LoadingPlanResponse getLoadingPlan(Long vesselId, Long voyageId, Long infoId, Long portRotationId)
      throws GenericServiceException;

  public LoadingUpdateUllageResponse getUpdateUllageDetails(
      Long vesselId, Long patternId, Long portId) throws GenericServiceException;
}
