/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanStowageDetails {
  private Long id;
  private Long tankId;
  private String cargoAbbreviation;
  private String weight; // MT to UI
  private String correctedUllage; // ullage(M)
  private String fillingRatio;
  private String tankName;
  private String rdgUllage;
  private String correctionFactor;
  private String observedM3;
  private String observedBarrels;
  private String observedBarrelsAt60; // volume(BBLS)
  private String api;
  private String temperature;
  private String colorCode;
  private String quantityMT; // by ALGO
}
