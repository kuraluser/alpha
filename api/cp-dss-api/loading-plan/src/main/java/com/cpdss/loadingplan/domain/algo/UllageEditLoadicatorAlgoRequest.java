/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.cpdss.loadingplan.common.LoadingPlanConstants;
import java.util.List;
import lombok.Data;

/** @author pranav.k */
@Data
public class UllageEditLoadicatorAlgoRequest {

  private String module = LoadingPlanConstants.LOADING_INFORMATION_REQUEST_JSON_MODULE_NAME;
  private String processId;
  private Long loadingInformationId;
  private Long vesselId;
  private Long portId;
  private List<LoadicatorStage> stages;
}
