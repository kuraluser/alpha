/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanPortWiseDetails {
  private Long portId;
  private String portCode;
  private Long portRotationId;
  private LoadablePatternDetails arrivalCondition;
  private LoadablePatternDetails departureCondition;
}
