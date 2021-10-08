/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadablePlanPortWiseDetails {
  private Long portId;
  private String portCode;
  private Long portRotationId;
  private LoadablePatternDetails arrivalCondition;
  private LoadablePatternDetails departureCondition;
}
