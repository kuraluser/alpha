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
@Data
@CPDSSJsonParser
public class LoadingInformation {

  private CommonSuccessResponse responseStatus;
  private Long loadingInfoId;
  private String loadableStudyName;
  private Long loadableStudyId;
  private Long synopticTableId;
  private Boolean isLoadingInfoComplete;
  private LoadingDetails loadingDetails;
  private LoadingRates loadingRates;
  private LoadingBerthDetails berthDetails;
  private CargoMachineryInUse machineryInUses;
  private LoadingStages loadingStages;
  private LoadingSequences loadingSequences;
  private List<ToppingOffSequence> toppingOffSequence;
  private CargoVesselTankDetails cargoVesselTankDetails;
  private Long loadingInfoStatusId;
  private Long loadingPlanArrStatusId;
  private Long loadingPlanDepStatusId;
  private Long loadablePatternId;
  private Boolean isLoadingInstructionsComplete;
  private Boolean isLoadingSequenceGenerated;
  private Boolean isLoadingPlanGenerated;
}
