/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.simulator;

import lombok.Data;

@Data
public class LoadableQuantityCommingleCargoDetailsJson {
  private Double actualQuantity;
  private Double api;
  private String cargo1Abbreviation;
  private Double cargo1MT;
  private Long cargo1NominationId;
  private Double cargo1Percentage;
  private String cargo2Abbreviation;
  private Double cargo2MT;
  private Long cargo2NominationId;
  private Double cargo2Percentage;
  private Double correctedUllage;
  private Double correctionFactor;
  private Double fillingRatio;
  private Double grade;
  private Double loadingOrder;
  private Double orderedQuantity;
  private Double priority;
  private Double quantity;
  private Double rdgUllage;
  private Double slopQuantity;
  private Long tankId;
  private String tankName;
  private Double temp;
  private Double timeRequiredForLoading;
}
