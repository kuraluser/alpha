/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

/** @Author gokul.p */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadablePlanDetailsAlgoJson {
  private Long caseNumber;
  private List<LoadablePlanPortWiseDetailsAlgoJson> loadablePlanPortWiseDetails;
  private Object constraints;
  private Double slopQuantity;
  private Object stabilityParameters;
}
