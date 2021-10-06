/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Authot vinothkumar M */
@Data
public class LoadablePlanBallastDetailsDto {

  private Long id;

  private Long tankId;

  private String rdgLevel;

  private String correctionFactor;

  private String correctedLevel;

  private String metricTon;

  private String cubicMeter;

  private String percentage;

  private String sg;

  private String lcg;

  private String tcg;

  private String vcg;

  private String inertia;

  private Boolean isActive;

  private String tankName;

  private String colorCode;

  private LoadablePatternDto loadablePattern;
}
