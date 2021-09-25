/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

@Data
public class UllageResponse {

  private CommonSuccessResponse responseStatus;
  private List<CargoBillOfLadding> billOfLaddingList;
  private Boolean isPlannedValues;
  private List<UpdateUllageCargoQuantityDetail> cargoQuantityDetails;
  private List<List<VesselTank>> bunkerRearTanks;
  private List<List<VesselTank>> bunkerTanks;
  private List<List<VesselTank>> ballastFrontTanks;
  private List<List<VesselTank>> ballastCenterTanks;
  private List<List<VesselTank>> ballastRearTanks;
  private List<List<VesselTank>> cargoTanks;
}
