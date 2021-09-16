/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadabalePatternDetails {
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetails;
  private List<LoadablePlanBallastDetails> loadablePlanBallastDetails;
  private List<LoadablePlanStowageDetails> loadablePlanCommingleDetails;
  // private List<LoadableStudy.LoadableQuantityCargoDetails> loadableQuantityCargoDetails;
  StabilityParameter stabilityParameter;
}
