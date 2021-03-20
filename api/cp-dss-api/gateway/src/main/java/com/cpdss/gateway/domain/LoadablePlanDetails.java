/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanDetails {
  private Integer caseNumber;
  private List<String> constraints;
  private String slopQuantity;
  private List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails;
  private StabilityParameter stabilityParameters;
}
