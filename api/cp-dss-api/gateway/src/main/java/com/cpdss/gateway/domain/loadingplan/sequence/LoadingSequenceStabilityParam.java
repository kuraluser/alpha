/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoadingSequenceStabilityParam {

  private String time;
  private String foreDraft;
  private String afterDraft;
  private String meanDraft;
  private String trim;

  @JsonProperty("heel")
  private String list;

  @JsonProperty("BM")
  private String bendingMoment;

  @JsonProperty("SF")
  private String shearingForce;
}
