/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.common.jsonbuilder.CPDSSJsonParser;
import com.cpdss.common.rest.CommonSuccessResponse;
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

  private CommonSuccessResponse responseStatus;
  private LoadingDetails loadingDetails;
  private LoadingRates loadingRates;
  private LoadingBerthDetails berthDetails;
  private CargoMachineryInUse machineryInUses;
  private LoadingStages loadingStages;
  private LoadingSequences loadingSequences; // TO DO
  private List<ToppingOffSequence> toppingOffSequence;
  private CargoVesselTankDetails cargoVesselTankDetails;
}
