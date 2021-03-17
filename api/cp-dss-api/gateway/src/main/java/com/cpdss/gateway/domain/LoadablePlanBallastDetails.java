/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanBallastDetails {
  @JsonInclude(Include.NON_NULL)
  private Long id;

  private Long tankId;
  private String rdgLevel;
  private String correctionFactor;
  private String correctedUllage;

  @JsonInclude(Include.NON_NULL)
  private String correctedLevel; // show in tank layout

  @JsonInclude(Include.NON_NULL)
  private String metricTon;

  @JsonInclude(Include.NON_NULL)
  private String cubicMeter;

  @JsonInclude(Include.NON_NULL)
  private String percentage;

  private String sg;

  @JsonInclude(Include.NON_NULL)
  private String lcg;

  @JsonInclude(Include.NON_NULL)
  private String vcg;

  @JsonInclude(Include.NON_NULL)
  private String tcg;

  @JsonInclude(Include.NON_NULL)
  private String inertia;

  private String tankName;

  @JsonInclude(Include.NON_NULL)
  private String colorCode;

  private String quantityMT; // for saving result
  private String fillingRatio; // for saving result
}
