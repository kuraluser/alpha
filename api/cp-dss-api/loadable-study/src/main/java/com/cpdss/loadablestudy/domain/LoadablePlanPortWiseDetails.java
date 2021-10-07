/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanPortWiseDetails {
  private Long portId;
  private String portCode;
  private Long portRotationId;
  private LoadabalePatternDetails arrivalCondition;
  private LoadabalePatternDetails departureCondition;
  // DS fields
  private String seaWaterTemperature;
  private String ambientTemperature;
}
