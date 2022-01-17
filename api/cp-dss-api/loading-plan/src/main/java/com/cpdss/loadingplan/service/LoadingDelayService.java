/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelay;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;

public interface LoadingDelayService {

  public void saveLoadingDelayList(
      LoadingDelay loadingDelays, LoadingInformation loadingInformation) throws Exception;

  void saveDefaultManagingSequence(
      List<LoadableStudy.LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList,
      LoadingInformation savedLoadingInformation);
}
