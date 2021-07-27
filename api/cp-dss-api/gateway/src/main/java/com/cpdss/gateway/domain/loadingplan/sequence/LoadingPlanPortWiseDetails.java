/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class LoadingPlanPortWiseDetails {

  private String time;

  @JsonProperty("loadablePlanStowageDetails")
  private List<LoadingPlanStowageDetails> loadablePlanStowageDetails;

  private List<LoadingPlanRobDetails> loadablePlanRoBDetails;
  private List<LoadingPlanBallastDetails> loadablePlanBallastDetails;

  @JsonProperty("deballastingRateM3_Hr")
  private Map<String, String> deballastingRates;

  private String draft;

  @JsonProperty("BM")
  private String bm;

  @JsonProperty("SF")
  private String sf;
}
