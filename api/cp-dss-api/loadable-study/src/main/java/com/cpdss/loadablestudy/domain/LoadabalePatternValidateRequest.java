/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADABLE_STUDY_JSON_MODULE_NAME;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadabalePatternValidateRequest {
  private String module = LOADABLE_STUDY_JSON_MODULE_NAME;
  private LoadableStudy loadableStudy;
  // private LoadablePlanDetailsAlgoJson loadablePlanDetails;
  // private List<LoadablePlanStowageTempDetails> loadablePlanStowageTempDetails;
  private List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails;
  private Boolean ballastEdited;
  private Long loadablePatternId;
  private Integer caseNumber;
}
