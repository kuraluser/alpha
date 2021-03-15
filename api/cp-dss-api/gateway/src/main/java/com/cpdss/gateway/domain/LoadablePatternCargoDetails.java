/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePatternCargoDetails {
  private Long priority;
  private String cargoAbbreviation;
  private String cargoColor;
  private String quantity;
  private Boolean isCommingle;
  private Long loadablePatternCommingleDetailsId;
  private String orderedQuantity;
  private Integer loadingOrder;
  private String api;
  private String tankName;
}
