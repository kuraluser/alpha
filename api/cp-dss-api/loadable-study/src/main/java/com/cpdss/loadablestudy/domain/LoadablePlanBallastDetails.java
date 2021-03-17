/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanBallastDetails {
  private Long id;
  private Long tankId;
  private String quantityMT; // for saving result
}
