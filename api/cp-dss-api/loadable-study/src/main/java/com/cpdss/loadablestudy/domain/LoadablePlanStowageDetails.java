/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanStowageDetails {
  private Long id;
  private Long tankId;
  private Long cargoNominationId;
  private String cargoId;
  private String quantityMT; // by ALGO
  private String api;
  private String temperature;
}
