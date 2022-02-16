/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class OnBoardQuantity {
  private Long id;
  private Long portId;
  private Long tankId;
  private Long cargoId;

  // @JsonInclude(JsonInclude.Include.NON_NULL)
  private String volume; // volumeInM3

  private String plannedArrivalWeight;
  private String api;
  private String colorCode;
  private String temperature;
  private String abbreviation;
  private Boolean isSlopTank;
}
