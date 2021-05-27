/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail;
import com.cpdss.loadingplan.entity.LoadingInformation;

/** Master service for the Loading Information */
public interface LoadingInformationService {

  public LoadingInformation saveLoadingInformation(
      LoadingInformationDetail loadingInformationDetail, LoadingInformation loadingInformation);

  public void deleteLoadablePlanDetails(LoadingInformation loadingInformation);
}
