/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.loadingplan.CargoVesselTankDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingBerthDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingSequences;
import com.cpdss.gateway.domain.loadingplan.LoadingStages;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Discharge Information For Algo Receiving
 *
 * @author sreemanikandan.k
 * @since 03-12-2021
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlgoDischargingInformation {

  private CommonSuccessResponse responseStatus;
  private Long dischargeInfoId;
  private String dischargeStudyName;
  private Long dischargeStudyId;
  private Long synopticTableId;
  private Boolean isDischargeInfoComplete = false; // default unchecked

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
  private Boolean isDischargeInstructionsComplete;
  private Boolean isDischargeSequenceGenerated = false;
  private Boolean isDischargePlanGenerated = false;

  // For Discharge Information
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean dischargeSlopTanksFirst = false; // default unchecked;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean dischargeCommingledCargoSeparately = false; // default unchecked
}
