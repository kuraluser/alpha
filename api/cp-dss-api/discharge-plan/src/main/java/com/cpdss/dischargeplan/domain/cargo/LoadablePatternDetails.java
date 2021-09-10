/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.cargo;

import java.util.List;
import lombok.Data;

@Data
public class LoadablePatternDetails {
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetails;
  private List<LoadablePlanBallastDetails> loadablePlanBallastDetails;
  private List<LoadablePlanStowageDetails> loadablePlanCommingleDetails;
}
