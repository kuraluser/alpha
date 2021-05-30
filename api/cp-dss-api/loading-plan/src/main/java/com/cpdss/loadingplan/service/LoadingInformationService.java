/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.Optional;

/** Master service for the Loading Information */
public interface LoadingInformationService {

  public LoadingInformation saveLoadingInformation(
      LoadingInformationDetail loadingInformationDetail, LoadingInformation loadingInformation);

  public void deleteLoadablePlanDetails(LoadingInformation loadingInformation);

  Optional<LoadingInformation> getLoadingInformation(Long id, Long vesselId, Long patternId);

  LoadingPlanModels.LoadingInformation getLoadingInformation(
      LoadingPlanModels.LoadingInformationRequest request,
      LoadingPlanModels.LoadingInformation.Builder response)
      throws GenericServiceException;
}
