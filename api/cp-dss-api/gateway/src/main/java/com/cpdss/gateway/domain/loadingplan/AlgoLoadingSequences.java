/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;
import lombok.Data;

/** @author pranav.k */
@Data
public class AlgoLoadingSequences {

  private List<ReasonForDelay> reasonForDelays;
  private List<LoadingDelays> loadingDelays;
}
