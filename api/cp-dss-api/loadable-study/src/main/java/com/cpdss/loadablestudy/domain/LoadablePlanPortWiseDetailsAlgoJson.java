/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** @Author gokul.p */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadablePlanPortWiseDetailsAlgoJson {
  private Long portRotationId;
  private Object departureCondition;
}
