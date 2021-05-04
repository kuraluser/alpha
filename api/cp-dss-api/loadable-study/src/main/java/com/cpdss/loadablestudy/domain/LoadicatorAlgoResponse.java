/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadicatorAlgoResponse {
  private String processId;
  private List<LoadicatorPatternDetailsResults> loadicatorResultsPatternWise;
  private LoadicatorPatternDetailsResults loadicatorResults;
  private Boolean feedbackLoop;
  private Integer feedbackLoopCount;
}
