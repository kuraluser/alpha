/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanBallastDetails {
  private Long id;
  private Long tankId;
  private String rdgLevel;
  private String correctionFactor;
  private String correctedLevel; // show in tank layout
  private String metricTon;
  private String cubicMeter;
  private String percentage;
  private String sg;
  private String lcg;
  private String vcg;
  private String tcg;
  private String inertia;
  private String tankName;
  private String colorCode;
  private String quantityMT; // for saving result
  private String fillingRatio; // for saving result
}
