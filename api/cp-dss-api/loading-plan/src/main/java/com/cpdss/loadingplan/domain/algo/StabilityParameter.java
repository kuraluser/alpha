/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import lombok.Data;

@Data
public class StabilityParameter {
  private String forwardDraft;
  private String meanDraft;
  private String afterDraft;
  private String trim;
  private String heel;
  private String bendinMoment;
  private String shearForce;
  private String bm;
  private String sf;
  private String draft;
  private String freeboard;
  private String manifoldHeight;
}
