/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInformation;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationRequest;
import com.cpdss.gateway.domain.loadingplan.LoadingInformationResponse;
import com.cpdss.gateway.utility.LoadingPlanSection;

public interface LoadingPlanService {

  Object getLoadingPortRotationDetails(Long vesselId, Long portRId) throws GenericServiceException;

  LoadingInformation getLoadingInformationByPortRotation(Long vesselId, Long planId, Long portRId)
      throws GenericServiceException;

  public LoadingInformationResponse saveLoadingInformation(
      LoadingInformationRequest request, String correlationId) throws GenericServiceException;

  RuleResponse getLoadingPlanRules(
      Long vesselId, Long voyageId, Long loadingInfoId, LoadingPlanSection section)
      throws GenericServiceException;
}
