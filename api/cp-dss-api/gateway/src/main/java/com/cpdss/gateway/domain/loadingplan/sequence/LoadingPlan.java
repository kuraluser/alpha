/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class LoadingPlan {

  private String time;
  private List<LoadingPlanStowageDetails> loadablePlanStowageDetails;
  private List<LoadingPlanBallastDetails> loadablePlanBallastDetails;
  private List<LoadingPlanRobDetails> loadablePlanRoBDetails;
  private String foreDraft;
  private String meanDraft;
  private String afterDraft;
  private String trim;

  @JsonProperty("heel")
  private String list;

  @JsonProperty("BM")
  private String bm;

  @JsonProperty("SF")
  private String sf;
}
