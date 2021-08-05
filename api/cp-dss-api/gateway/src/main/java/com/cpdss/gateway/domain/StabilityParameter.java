/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/** @Author jerin.g */
@Data

public class StabilityParameter {
  private String forwardDraft;
  private String meanDraft;
  private String afterDraft;
  private String trim;
  private String heel;
  private String bendinMoment;
  private String shearForce;
  
  //DS fields
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String airDraft;
  
}
