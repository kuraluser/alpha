/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest.Builder;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanAlgoRequest;
import org.springframework.stereotype.Service;

@Service
public class LoadingPlanBuilderService {

  public void buildLoadingPlanSaveRequest(
      LoadingPlanAlgoRequest loadingPlanAlgoRequest, Builder builder) {}
}
