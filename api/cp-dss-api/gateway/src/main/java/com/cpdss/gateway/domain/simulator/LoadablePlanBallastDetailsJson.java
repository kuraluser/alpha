/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.simulator;

import lombok.Data;

@Data
public class LoadablePlanBallastDetailsJson {
  private Double correctedUllage;
  private Double correctionFactor;
  private Double fillingRatio;
  private Double quantityMT;
  private Double rdgLevel;
  private Double sg;
  private Long tankId;
  private String tankName;
  private String tankShortName;
}
