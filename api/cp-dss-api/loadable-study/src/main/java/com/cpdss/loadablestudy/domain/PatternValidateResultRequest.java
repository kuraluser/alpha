/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class PatternValidateResultRequest {
  private String processId;
  private Boolean validated;
  private LoadablePlanDetails loadablePlanDetails;
  private List<AlgoError> errors;
  private Boolean hasLoadicator;
}
