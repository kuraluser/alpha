/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.common.jsonbuilder.CPDSSJsonParser;
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
public class LoadingInformation {

  private LoadingDetails loadingDetails;
  private LoadingRates loadingRates;
  private List<LoadingBerthDetails> berthDetails;
  private List<CargoMachineryInUse> machineryInUses;
  // private CargoDetails cargoDetails; // call to LS and get by port rotation condition
  private LoadingStages loadingStages;
  private LoadingSequences loadingSequences;
  private ToppingOffSequence toppingOffSequence;
}
