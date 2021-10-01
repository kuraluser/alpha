/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;
import lombok.Data;

/** @author pranav.k */
@Data
public class AlgoLoadingInformation {

  private AlgoLoadingRates loadingRates;
  private List<AlgoBerthDetails> berthDetails;
  private AlgoLoadingSequences loadingSequences;
}
