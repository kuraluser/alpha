/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;

import com.cpdss.common.generated.LoadableStudy;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadabalePatternDetails {
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetails;
  private List<LoadablePlanBallastDetails> loadablePlanBallastDetails;
  private List<LoadablePlanStowageDetails> loadablePlanCommingleDetails;
  private List<LoadableStudy.LoadableQuantityCargoDetails> loadableQuantityCargoDetails;
  StabilityParameter stabilityParameter;
}
