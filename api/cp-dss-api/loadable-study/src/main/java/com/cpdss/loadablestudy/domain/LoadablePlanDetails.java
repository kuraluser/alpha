/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class LoadablePlanDetails {
  private Integer caseNumber;
  private List<String> constraints;
  private List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails;
  private List<LoadablePlanPortWiseDetails> dischargePlanPortWiseDetails;
}
