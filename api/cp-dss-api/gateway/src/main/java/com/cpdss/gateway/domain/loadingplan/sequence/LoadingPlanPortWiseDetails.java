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

  private String foreDraft;
  private String afterDraft;
  private String meanDraft;
  private String trim;

  @JsonProperty("heel")
  private String list;

  private String bendinMoment;
  private String shearForce;
}
