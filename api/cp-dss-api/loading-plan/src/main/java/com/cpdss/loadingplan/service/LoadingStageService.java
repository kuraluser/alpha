/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages;

public interface LoadingStageService {

  public void saveLoadingStages(LoadingStages loadingStages) throws Exception;
}
