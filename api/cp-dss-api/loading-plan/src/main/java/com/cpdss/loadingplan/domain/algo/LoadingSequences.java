/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadingSequences {

  private List<ReasonForDelay> reasonForDelays;
  private List<LoadingDelays> loadingDelays;
  private Boolean isSequenceAltered;
}
