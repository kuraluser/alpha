/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import lombok.Data;

@Data
public class DischargeStages {

  // private Long id; not needed for algo
  private Boolean trackStartEndStage;
  private Boolean trackGradeSwitch;
  private Integer stageOffset = 4; // Default Value
  private Integer stageDuration = 4; // Default Value, in Hours
  private Boolean isStageOffsetUsed;
  private Boolean isStageDurationUsed;
}
