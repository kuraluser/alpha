/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.loadingplan.BerthDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingMachinesInUse;
import com.cpdss.gateway.domain.loadingplan.LoadingStagesRequest;
import com.cpdss.gateway.domain.loadingplan.ToppingOffSequence;
import java.util.List;
import lombok.Data;

@Data
public class DischargingInformationRequest {

  private Long dischargeInfoId;

  private Long synopticTableId;

  // re using loading object
  private LoadingDetails dischargeDetails;

  private DischargeRates dischargeRates;

  // re using berth details
  private List<BerthDetails> dischargingBerths;
  // re using loading module added ds info id as a field in this
  private List<LoadingMachinesInUse> dischargingMachineries;

  private List<DischargingDelays> dischargingDelays;

  private List<ToppingOffSequence> toppingOffSequence;

  // re using the loading module
  private LoadingStagesRequest dischargeStages;
  private Boolean isDischargeInfoComplete; // validation logic at Front-end
  private CowPlan cowPlan;
  private PostDischargeStage postDischargeStageTime;
  private PlannedCargo cargoToBeDischarged;
}
