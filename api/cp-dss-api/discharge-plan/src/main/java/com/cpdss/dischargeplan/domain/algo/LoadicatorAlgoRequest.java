/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.algo;

import com.cpdss.dischargeplan.common.DischargePlanConstants;
import java.util.List;
import lombok.Data;

@Data
public class LoadicatorAlgoRequest {

  private String module = DischargePlanConstants.DISCHARGING_INFORMATION_REQUEST_JSON_MODULE_NAME;
  private String processId;
  private Long dischargingInformationId;
  private String dischargeStudyProcessId;
  private Long vesselId;
  private Long portId;
  private Long portRotationId;
  private List<LoadicatorStage> stages;
}
