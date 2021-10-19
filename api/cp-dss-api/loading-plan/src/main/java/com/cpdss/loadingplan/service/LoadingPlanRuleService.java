/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.LoadingInformation;

public interface LoadingPlanRuleService {

  LoadingPlanModels.LoadingPlanRuleReply getLoadingPlanRuleForAlgo(
      Long vesselXId, Long loadingInfoId);

  void saveRulesAgainstLoadingInformation(LoadingInformation savedLoadingInformation)
      throws GenericServiceException;
}
