/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.simulator;

import lombok.Data;

@Data
public class StabilityParametersJson {
  private Double afterDraft;
  private Double bendinMoment;
  private Double forwardDraft;
  private Double heel;
  private Double meanDraft;
  private Double shearForce;
  private Double trim;
}
