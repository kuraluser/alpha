/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.util.List;
import lombok.Data;

@Data
public class LoadingStages {

  private Long id;
  private Boolean trackStartEndStage;
  private Boolean trackGradeSwitch;
  private Integer stageOffset = 4; // Default Value
  private Integer stageDuration = 4; // Default Value, in Hours

  private List<StageOffset> stageOffsetList;
  private List<StageDuration> stageDurationList;
}
