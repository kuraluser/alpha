/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadablePlanDetails {
  private Integer caseNumber;
  private List<String> constraints;
  private List<LoadablePlanPortWiseDetails> loadablePlanPortWiseDetails;
  private List<LoadablePlanPortWiseDetails> dischargePlanPortWiseDetails;
}
