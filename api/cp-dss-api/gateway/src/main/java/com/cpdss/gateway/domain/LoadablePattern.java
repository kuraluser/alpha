/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePattern {
  private Long loadablePatternId;
  private String constraints;
  private String totalDifferenceColor;
  private List<LoadablePatternCargoDetails> loadablePatternCargoDetails;
  private Long loadableStudyStatusId;
}
