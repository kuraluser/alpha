/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

@Data
public class LoadingPlanAlgoResponse {
  private String processId;
  private CommonSuccessResponse responseStatus;
}
