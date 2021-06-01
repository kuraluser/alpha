/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingBerths;
import java.util.List;

public interface LoadingBerthService {

  public void saveLoadingBerthList(List<LoadingBerths> loadingBerthsList) throws Exception;
}
