/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelay;

public interface LoadingDelayService {

  public void saveLoadingDelayList(LoadingDelay loadingDelays) throws Exception;
}
