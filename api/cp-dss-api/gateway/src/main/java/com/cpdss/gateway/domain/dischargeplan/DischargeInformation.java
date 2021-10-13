/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.loadingplan.CargoMachineryInUse;
import com.cpdss.gateway.domain.loadingplan.CargoVesselTankDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingBerthDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingSequences;
import com.cpdss.gateway.domain.loadingplan.LoadingStages;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Discharge Plan Module, Parent Object
 *
 * @author Johnsooraj.x
 * @since 24-08-2021
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DischargeInformation {

  private CommonSuccessResponse responseStatus;
  private Long dischargeInfoId;
  private String dischargeStudyName;
  private Long dischargeStudyId;
  private Long synopticTableId;
  private Boolean isDischargingInfoComplete;

  // reusing common class from discharging
  private LoadingDetails dischargeDetails;

  private DischargeRates dischargeRates;

  private LoadingBerthDetails berthDetails;
  private CargoMachineryInUse machineryInUses;

  private LoadingStages dischargeStages;

  private LoadingSequences dischargeSequences;

  private CowPlan cowPlan;
  private PostDischargeStage postDischargeStageTime;
  // For layout and Grid
  private CargoVesselTankDetails cargoVesselTankDetails;
  private Long dischargeInfoStatusId;
  private Long dischargePlanArrStatusId;
  private Long dischargePlanDepStatusId;
  private Long dischargePatternId;
  private Boolean isDischargeInstructionsComplete = false;
  private Boolean isDischargeSequenceGenerated = false;
  private Boolean isDischargePlanGenerated = false;
}
