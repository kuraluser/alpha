/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @author Sanal */
@Data
public class ArrivalConditionJson {

  private List<LoadableQuantityCargoDetails> loadableQuantityCargoDetails;
  private List<LoadableQuantityCommingleCargoDetails> loadableQuantityCommingleCargoDetails;
  private List<LoadablePlanStowageDetailsJson> loadablePlanStowageDetails;
  private List<LoadablePlanStowageDetailsJson> loadablePlanBallastDetails;
  private StabilityParameter stabilityParameters;
}
