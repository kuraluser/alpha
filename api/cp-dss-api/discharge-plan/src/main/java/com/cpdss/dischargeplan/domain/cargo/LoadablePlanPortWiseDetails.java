/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.cargo;

import lombok.Data;

@Data
public class LoadablePlanPortWiseDetails {
  private Long portId;
  private String portCode;
  private Long portRotationId;
  private LoadablePatternDetails arrivalCondition;
  private LoadablePatternDetails departureCondition;
}
