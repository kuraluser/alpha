/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanRequest {
  private String processId;
  private List<LoadablePlanDetails> loadablePlanDetails;
  private List<AlgoError> errors;
  private Long loadablePatternId;
}
