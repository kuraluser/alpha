/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author gokul.p */
@Data
public class LoadableStudyAlgoJson {
  private String processId;
  private List<LoadablePlanDetailsAlgoJson> loadablePlanDetails;
  private String errors;
}
