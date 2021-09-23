/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.gateway.domain.LoadableQuantityCargoDetails;
import com.cpdss.gateway.domain.VesselTank;
import com.cpdss.gateway.domain.dischargeplan.DischargingInformation;
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
public class LoadingPlanReportRequest {

  private List<LoadingPlanStowageDetails> planStowageDetails;
  private List<LoadingPlanBallastDetails> planBallastDetails;
  private List<LoadingPlanRobDetails> planRobDetails;
  private List<LoadingPlanStabilityParam> planStabilityParams;
  private LoadingInformation loadingInformation;
  private List<List<VesselTank>> bunkerRearTanks;
  private List<List<VesselTank>> bunkerTanks;
  private List<List<VesselTank>> ballastFrontTanks;
  private List<List<VesselTank>> ballastCenterTanks;
  private List<List<VesselTank>> ballastRearTanks;
  private List<List<VesselTank>> cargoTanks;
  private DischargingInformation dischargingInformation;
  private List<LoadableQuantityCargoDetails> currentPortCargos;
}
