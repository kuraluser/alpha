/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.util.List;

import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanRobDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanStowageDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingQuantityCommingleCargoDetails;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DischargingPlan {

  private String time;
  private List<LoadingPlanStowageDetails> dischargePlanStowageDetails;
  private List<LoadingPlanBallastDetails> dischargePlanBallastDetails;
  private List<LoadingPlanRobDetails> dischargePlanRoBDetails;
  private List<LoadingQuantityCommingleCargoDetails> dischargeQuantityCommingleCargoDetails;
  private String foreDraft;
  private String meanDraft;
  private String afterDraft;
  private String trim;

  @JsonProperty("heel")
  private String list;

  private String bendinMoment;

  private String shearForce;
}
