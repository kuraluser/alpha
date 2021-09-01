/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadabalePatternDetails {
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetails;
  private List<LoadablePlanBallastDetails> loadablePlanBallastDetails;
  private List<LoadablePlanStowageDetails> loadablePlanCommingleDetails;
}
