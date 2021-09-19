/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.util.List;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.CargoBillOfLadding;
import com.cpdss.gateway.domain.UpdateUllageCargoQuantityDetail;
import com.cpdss.gateway.domain.VesselTank;
import com.cpdss.gateway.domain.loadingplan.LoadablePlanCommingleDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanRobDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanStowageDetails;

import lombok.Data;

@Data
public class DischargeUpdateUllageResponse  {
  private CommonSuccessResponse responseStatus;
  private List<CargoBillOfLadding> billOfLaddingList;
  private Boolean isPlannedValues;
  private List<PortLoadablePlanStowageDetails> portDischargePlanStowageDetails;
  private List<PortLoadablePlanBallastDetails> portDischargePlanBallastDetails;
  private List<PortLoadablePlanRobDetails> portDischargePlanRobDetails;
  private List<LoadablePlanCommingleDetails> dischargePlanCommingleDetails;
  private List<UpdateUllageCargoQuantityDetail> cargoQuantityDetails;
  private List<List<VesselTank>> bunkerRearTanks;
  private List<List<VesselTank>> bunkerTanks;
  private List<List<VesselTank>> ballastFrontTanks;
  private List<List<VesselTank>> ballastCenterTanks;
  private List<List<VesselTank>> ballastRearTanks;
  private List<List<VesselTank>> cargoTanks;
}
