/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
public class LoadicatorStage {

  @JsonInclude(value = Include.NON_NULL)
  private Integer time;

  private LDTrim ldTrim;
  private LDStrength ldStrength;
  private LDIntactStability ldIntactStability;
}
