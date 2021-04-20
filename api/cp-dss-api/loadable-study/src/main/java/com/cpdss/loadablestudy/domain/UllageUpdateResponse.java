/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

@Data
public class UllageUpdateResponse {

  private Long id;

  private String correctionFactor;

  private String correctedUllage;

  private String quantityMt;

  private String fillingRatio;
}
