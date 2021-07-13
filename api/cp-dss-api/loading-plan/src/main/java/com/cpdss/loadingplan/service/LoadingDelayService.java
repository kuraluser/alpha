/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelay;
import com.cpdss.loadingplan.entity.LoadingInformation;

public interface LoadingDelayService {

  public void saveLoadingDelayList(
      LoadingDelay loadingDelays, LoadingInformation loadingInformation) throws Exception;
}
