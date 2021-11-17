/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.DischargeQuantityCargoDetails;
import com.cpdss.gateway.domain.VesselTank;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanStabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanRobDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanStowageDetails;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DischargePlanResponse {

  private CommonSuccessResponse responseStatus;

  private List<LoadingPlanStowageDetails> planStowageDetails;
  private List<LoadingPlanBallastDetails> planBallastDetails;
  private List<LoadingPlanRobDetails> planRobDetails;
  private List<LoadingPlanStabilityParam> planStabilityParams;
  private DischargeInformation dischargingInformation;
  private List<List<VesselTank>> bunkerRearTanks;
  private List<List<VesselTank>> bunkerTanks;
  private List<List<VesselTank>> ballastFrontTanks;
  private List<List<VesselTank>> ballastCenterTanks;
  private List<List<VesselTank>> ballastRearTanks;
  private List<List<VesselTank>> cargoTanks;
  private List<DischargeQuantityCargoDetails> currentPortCargos;
  private List<LoadingPlanCommingleDetails> planCommingleDetails;

  public DischargePlanResponse(CommonSuccessResponse responseStatus) {
    this.responseStatus = responseStatus;
  }
}
