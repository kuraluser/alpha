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
import com.fasterxml.jackson.annotation.JsonProperty;
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
  private Boolean isDischargeInfoComplete;

  // reusing common class from discharging
  @JsonProperty("dischargingDetails")
  private LoadingDetails dischargeDetails;

  @JsonProperty("dischargingRates")
  private DischargeRates dischargeRates;

  private LoadingBerthDetails berthDetails;
  private CargoMachineryInUse machineryInUses;

  @JsonProperty("dischargingStages")
  private LoadingStages dischargeStages;

  @JsonProperty("dischargingSequences")
  private LoadingSequences dischargeSequences;

  private CowPlan cowPlan;
  private PostDischargeStage postDischargeStageTime;
  // For layout and Grid
  private CargoVesselTankDetails cargoVesselTankDetails;
  private Long dischargingInfoStatusId;
  private Long dischargingPlanArrStatusId;
  private Long dischargingPlanDepStatusId;
  private Long dischargePatternId;
  private Boolean isDischargingInstructionsComplete;
  private Boolean isDischargingSequenceGenerated;
  private Boolean isDischargingPlanGenerated;
}
