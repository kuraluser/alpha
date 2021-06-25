/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.util.List;
import lombok.Data;

@Data
public class LoadingSequences {

  private List<ReasonForDelay> reasonForDelays;
  private List<LoadingDelays> loadingDelays;
}
