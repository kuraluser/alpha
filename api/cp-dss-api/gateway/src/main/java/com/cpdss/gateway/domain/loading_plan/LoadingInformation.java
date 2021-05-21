/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loading_plan;

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
public class LoadingInformation {

  private CommonSuccessResponse responseStatus;

  private LoadingDetails loadingDetails;
  private LoadingRates loadingRates;
  private List<BerthDetails> berthDetails;
  private List<CargoMachineryInUse> machineryInUses;
  // private CargoDetails cargoDetails;
  private LoadingStages loadingStages;
  private LoadingSequences loadingSequences;
  private ToppingOffSequence toppingOffSequence;
}
