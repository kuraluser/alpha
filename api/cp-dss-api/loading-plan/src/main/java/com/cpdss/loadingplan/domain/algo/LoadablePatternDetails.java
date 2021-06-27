/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePatternDetails {
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetails;
  private List<LoadablePlanBallastDetails> loadablePlanBallastDetails;
  private List<LoadablePlanStowageDetails> loadablePlanCommingleDetails;
}
