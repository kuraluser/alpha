/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(Include.NON_NULL)
public class LoadabalePatternValidateRequest {
  private LoadableStudy loadableStudy;
  private List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails;
  private Boolean ballastEdited;
  private Long loadablePatternId;
  private Integer caseNumber;
  private List<LoadablePlanStowageTempDetails> loadablePlanStowageTempDetails;
}
