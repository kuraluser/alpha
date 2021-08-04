/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class LoadingPlanAlgoRequest {

  private String processId;
  private List<Event> events;
  private Map<String, LoadingPlan> plans;

  // stability parameters per stage
  private List<LoadingSequenceStabilityParam> stages;
}
