/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadingStages {

  private Long id;
  private Boolean trackStartEndStage;
  private Boolean trackGradeSwitch;
  private Integer stageOffset = 4; // Default Value
  private Integer stageDuration = 4; // Default Value, in Hours
  private Boolean isStageOffsetUsed;
  private Boolean isStageDurationUsed;

  // private List<StageOffset> stageOffsetList;
  // private List<StageDuration> stageDurationList;
}
