/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadicatorAlgoRequest {
  private String processId;
  private List<LoadicatorPatternDetails> loadicatorPatternDetails;
  private LoadicatorPatternDetails loadicatorPatternDetail;
}
