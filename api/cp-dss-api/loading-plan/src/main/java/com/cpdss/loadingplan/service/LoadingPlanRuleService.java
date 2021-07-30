/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;

public interface LoadingPlanRuleService {

  LoadingPlanModels.LoadingPlanRuleReply getLoadingPlanRuleForAlgo(
      Long vesselXId, Long loadingInfoId);
}
