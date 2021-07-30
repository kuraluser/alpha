/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanBallastDetails {
  private Long id;
  private Long tankId;
  private String quantityMT; // for saving result
  private String rdgLevel;
  private String correctionFactor;
  private String correctedUllage;
  private String sg;
  private String tankName;
  private String fillingRatio;
}
