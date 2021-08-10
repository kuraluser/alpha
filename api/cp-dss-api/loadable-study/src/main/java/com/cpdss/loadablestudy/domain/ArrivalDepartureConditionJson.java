/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author gokul.p */
@Data
public class ArrivalDepartureConditionJson {
  private Boolean confirmPlanEligibility;
  private Object loadablePlanBallastDetails;
  private Object loadablePlanStowageDetails;
  private Object loadableQuantityCargoDetails;
  private Object loadableQuantityCommingleCargoDetails;
  private Object stabilityParameters;
}
