/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse;
import java.util.List;

public interface LoadingMachineryInUseService {

  public void saveLoadingMachineryList(List<LoadingMachinesInUse> loadingMachinesList)
      throws Exception;
}
