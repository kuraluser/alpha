/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.jsonbuilder.CPDSSJsonParser;
import com.cpdss.gateway.domain.AlgoError;
import com.cpdss.gateway.domain.loadingplan.sequence.Event;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceStabilityParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
@CPDSSJsonParser
public class DischargingPlanAlgoRequest {

  private String processId;
  private Map message;
  private List<Event> events;
  private Map<String, DischargingPlan> plans;
  // stability parameters per stage
  private List<LoadingSequenceStabilityParam> stages;
  private List<AlgoError> errors;
  private AlgoDischargingInformation dischargingInformation;
  private Boolean hasLoadicator;
}
