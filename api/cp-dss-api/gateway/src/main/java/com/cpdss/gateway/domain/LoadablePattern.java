/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePattern {
  private Long loadablePatternId;
  private List<String> constraints;
  private List<LoadablePatternCargoDetails> loadablePatternCargoDetails;
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetails;
  private Long loadableStudyStatusId;
  private Integer caseNumber;
}
