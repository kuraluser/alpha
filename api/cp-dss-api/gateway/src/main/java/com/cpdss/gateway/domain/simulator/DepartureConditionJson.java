/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.simulator;

import java.util.List;
import lombok.Data;

/** @Author gokul.p */
@Data
public class DepartureConditionJson {
  private boolean confirmPlanEligibility;
  private List<LoadablePlanBallastDetailsJson> loadablePlanBallastDetails;
  private List<LoadablePlanStowageDetailsJson> loadablePlanStowageDetails;
  private List<LoadableQuantityCargoDetailsJson> loadableQuantityCargoDetails;
  private List<LoadableQuantityCommingleCargoDetailsJson> loadableQuantityCommingleCargoDetails;
  private List<StabilityParametersJson> stabilityParameters;
}
