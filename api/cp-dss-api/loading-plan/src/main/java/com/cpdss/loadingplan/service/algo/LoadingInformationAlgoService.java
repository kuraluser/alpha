/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.algo;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus.Builder;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadingInformationAlgoService {

  @Autowired LoadingInformationAlgoRequestBuilderService loadingInfoAlgoRequestBuilderService;

  public void createAlgoRequest(LoadingInformationRequest request, Builder builder)
      throws GenericServiceException {

    loadingInfoAlgoRequestBuilderService.createAlgoRequest(request, builder);
  }
}
