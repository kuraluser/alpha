/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.simulator;

import lombok.Data;

@Data
public class LoadablePlanStowageDetailsJson {
  private Double api;
  private String cargoAbbreviation;
  private Long cargoNominationId;
  private Double cargoNominationTemperature;
  private String colorCode;
  private Double correctedUllage;
  private Double correctionFactor;
  private Double fillingRatio;
  private Double quantityMT;
  private Double rdgUllage;
  private Long tankId;
  private String tankName;
  private String tankShortName;
  private Double temperature;
}
