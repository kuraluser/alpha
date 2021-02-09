/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadicatorAlgoRequest {
  private String processId;
  private List<LoadicatorPatternDetails> loadicatorPatternDetails;
}
