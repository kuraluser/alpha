/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadablePlanRequest {
  private String processId;
  private boolean hasLodicator;
  private List<LoadablePlanDetails> loadablePlanDetails;
  private List<AlgoError> errors;
  private Long loadablePatternId;
  private Boolean validated;
  private Boolean hasLoadicator;
 
  //DS fields
  private List<LoadablePlanDetails> dischargePlanDetails;
  private Boolean feedbackLoop;
  private Long feedbackLoopCount;
  private String user;
  private String role;
}
