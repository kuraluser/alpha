/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import lombok.Data;

@Data
public class DischargePlanPortWiseDetails {
  private Long portId;
  private String portCode;
  private Long portRotationId;
  private DischargePatternDetails arrivalCondition;
  private DischargePatternDetails departureCondition;
}
