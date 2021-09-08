/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** @Author gokul.p */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadableStudyAlgoJson {
  private List<LoadablePlanDetailsAlgoJson> loadablePlanDetails;
}
