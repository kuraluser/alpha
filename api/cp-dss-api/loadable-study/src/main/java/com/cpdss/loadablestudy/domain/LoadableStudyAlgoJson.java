/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

/** @Author gokul.p */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadableStudyAlgoJson {
  private List<LoadablePlanDetailsAlgoJson> loadablePlanDetails;
}
