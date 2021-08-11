/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import lombok.Data;

@Data
public class LoadicatorStage {

  private Integer time;
  private LDTrim ldTrim;
  private LDStrength ldStrength;
  private LDIntactStability ldIntactStability;
}
