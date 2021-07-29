/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADABLE_STUDY_JSON_MODULE_NAME;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadicatorAlgoRequest {
  private String module = LOADABLE_STUDY_JSON_MODULE_NAME;
  private String processId;
  private List<LoadicatorPatternDetails> loadicatorPatternDetails;
  private LoadicatorPatternDetails loadicatorPatternDetail;
  private LoadableStudy loadableStudy;
}
