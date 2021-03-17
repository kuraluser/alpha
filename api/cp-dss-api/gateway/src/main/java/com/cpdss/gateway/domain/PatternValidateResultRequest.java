/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class PatternValidateResultRequest {
  private Boolean validated;
  private List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails;
  private List<AlgoError> errors;
}
