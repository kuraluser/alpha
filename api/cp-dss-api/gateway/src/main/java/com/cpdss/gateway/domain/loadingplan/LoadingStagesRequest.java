/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.Data;

@Data
public class LoadingStagesRequest {

  private Boolean trackStartEndStage;

  private Boolean trackGradeSwitch;

  private StageOffset stageOffset;

  private StageDuration stageDuration;

  private Boolean isStageOffsetUsed;

  private Boolean isStageDurationUsed;
}
