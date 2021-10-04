/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.algo;

import com.cpdss.dischargeplan.common.DischargePlanConstants;
import java.util.List;
import lombok.Data;

/** @author arun.j */
@Data
public class UllageEditLoadicatorAlgoRequest {

  private String module = DischargePlanConstants.DISCHARGING_INFORMATION_REQUEST_JSON_MODULE_NAME;
  private String processId;
  private String loadableStudyProcessId;
  private Long loadingInformationId;
  private Long vesselId;
  private Long portId;
  private List<LoadicatorStage> stages;
  private DischargingPlanLoadicatorDetails planDetails;
}
