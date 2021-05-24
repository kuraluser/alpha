/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.Data;

@Data
public class LoadingStages {

  private Boolean trackStartAndEndStage;
  private Boolean trackGradeSwitching;
  private Integer minNumberOfStage = 4; // Default Value
  private Integer durationOfStage = 4; // Default Value, in Hours
}
