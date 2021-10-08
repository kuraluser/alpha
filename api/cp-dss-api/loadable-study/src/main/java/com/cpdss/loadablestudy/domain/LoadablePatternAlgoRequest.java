/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.cpdss.loadablestudy.entity.AlgoErrors;
import java.util.List;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class LoadablePatternAlgoRequest {

  private String processId;
  private Boolean validated;
  private Boolean hasLoadicator;
  // private LoadablePlanDetails loadablePlanDetails;
  private Long loadableStudyId;
  private PatternDetails patternDetails;
  private List<AlgoErrors> algoError;
  private Long loadablePatternId;
  private Integer feedBackLoopCount;
}
