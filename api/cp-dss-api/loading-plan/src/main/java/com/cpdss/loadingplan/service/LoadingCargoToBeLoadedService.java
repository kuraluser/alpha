/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;

public interface LoadingCargoToBeLoadedService {

  void saveCargoToBeLoaded(
      List<LoadableStudy.LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList,
      LoadingInformation loadingInformation);

  void getCargoToBeLoaded(
      LoadingInformation loadingInformation, LoadingPlanModels.LoadingInformation.Builder builder);
}
