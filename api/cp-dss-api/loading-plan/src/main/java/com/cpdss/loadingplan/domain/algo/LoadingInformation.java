/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.cpdss.common.jsonbuilder.CPDSSJsonParser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

/**
 * Loading Plan Module, Loading Information tab
 *
 * @author Johnsooraj.x
 * @since 20-05-2021
 */

// loading details
// loading rates
// berth details
// cargo machine in use
// cargo details
// no of stages
// manage sequence
// topping off sequence

@Data
@CPDSSJsonParser
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadingInformation {

  private Long loadingInfoId;
  private LoadingDetails loadingDetails;
  private LoadingRates loadingRates;
  private List<BerthDetails> berthDetails;
  private CargoMachineryInUse machineryInUses;
  private LoadingStages loadingStages;
  private LoadingSequences loadingSequences; // TO DO
  private List<ToppingOffSequence> toppingOffSequence;
  private List<LoadableQuantityCargoDetails> loadableQuantityCargoDetails;
}
