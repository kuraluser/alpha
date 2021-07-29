/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanStowageDetailsJson {

  private Long id;

  private Long tankId;

  private String api;
  private String temperature;
  private String quantity;
  private String quantityM3;
  private Long cargoNominationId;
  private String sounding;
  private String ullage;
}
