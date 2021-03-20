/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadicatorPatternDetailsResults {
  private Long loadablePatternId;
  private List<LoadicatorResultDetails> loadicatorResultDetails;
}
