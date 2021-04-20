/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadabalePatternValidateRequest {
  private LoadableStudy loadableStudy;
  private List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails;
  private Boolean ballastEdited;
  private Long loadablePatternId;
  private Integer caseNumber;
}
