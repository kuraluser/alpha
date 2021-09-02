/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author gokul.p */
@Data
public class LoadablePlanPortWiseDetailsAlgoJson {
  private Long portId;
  private Long portRotationId;
  private String portCode;
  private ArrivalDepartureConditionJson arrivalCondition;
  private ArrivalDepartureConditionJson departureCondition;
}