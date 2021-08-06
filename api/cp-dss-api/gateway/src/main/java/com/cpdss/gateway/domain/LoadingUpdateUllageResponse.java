/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanRobDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanStowageDetails;
import java.util.List;
import lombok.Data;

@Data
public class LoadingUpdateUllageResponse {
  private CommonSuccessResponse responseStatus;
  private List<CargoBillOfLadding> billOfLaddingList;
  private List<PortLoadablePlanStowageDetails> portLoadablePlanStowageDetails;
  private List<PortLoadablePlanBallastDetails> portLoadablePlanBallastDetails;
  private List<PortLoadablePlanRobDetails> portLoadablePlanRobDetails;
  private List<UpdateUllageCargoQuantityDetail> cargoQuantityDetails;
  private List<List<VesselTank>> bunkerRearTanks;
  private List<List<VesselTank>> bunkerTanks;
  private List<List<VesselTank>> ballastFrontTanks;
  private List<List<VesselTank>> ballastCenterTanks;
  private List<List<VesselTank>> ballastRearTanks;
  private List<List<VesselTank>> cargoTanks;
}
