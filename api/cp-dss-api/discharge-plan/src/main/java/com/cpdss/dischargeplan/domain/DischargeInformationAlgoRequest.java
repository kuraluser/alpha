/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.domain.cargo.OnBoardQuantity;
import com.cpdss.dischargeplan.domain.cargo.OnHandQuantity;
import com.cpdss.dischargeplan.domain.rules.RuleResponse;
import com.cpdss.dischargeplan.repository.projections.PortTideAlgo;
import java.util.List;
import lombok.Data;

@Data
public class DischargeInformationAlgoRequest {

  private String module = DischargePlanConstants.DISCHARGE_INFORMATION_REQUEST_JSON_MODULE_NAME;
  private Long vesselId;
  private Long voyageId;
  private Long portId;
  private Long portRotationId;
  private DischargeInformation dischargeInformation;

  private List<DischargePlanPortWiseDetails> planPortWiseDetails;
  private List<OnHandQuantity> onHandQuantity;
  private List<OnBoardQuantity> onBoardQuantity;
  private List<PortTideAlgo> portTideDetails;
  private RuleResponse dischargingRules;
}
