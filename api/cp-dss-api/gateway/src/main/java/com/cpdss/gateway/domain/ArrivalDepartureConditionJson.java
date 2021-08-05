/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author gokul.p */
@Data
public class ArrivalDepartureConditionJson {
  private Object loadablePlanBallastDetails;
  private Object loadablePlanStowageDetails;
  private Object loadableQuantityCargoDetails;
  private Object loadableQuantityCommingleCargoDetails;
}
