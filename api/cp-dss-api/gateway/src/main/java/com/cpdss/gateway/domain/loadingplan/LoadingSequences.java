/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;
import lombok.Data;

@Data
public class LoadingSequences {

  private List<ReasonForDelay> reasonForDelays;
  private List<LoadingDelays> loadingDelays;
}
