/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoadingSequenceStabilityParam {

  private String time;
  private String foreDraft;

  @JsonAlias("aftDraft")
  private String afterDraft;

  @JsonAlias("draft")
  private String meanDraft;

  private String trim;

  @JsonProperty("heel")
  private String list;

  @JsonAlias("BM")
  private String bendinMoment;

  @JsonAlias("SF")
  private String shearForce;

  private String gomValue;
}
