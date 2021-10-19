/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.Data;

@Data
public class VesselParticularsForExcel {

  private Long vesselId;
  private Long vesselTypeId;
  private String shipMaxLoadingRate;
  private String shipMaxFlowRate;
  private String shipMaxFlowRatePerTank;
  private String maxLoadingRateSlopP;
  private String maxLoadingRateSlopS;
  private Long ballastPumpCount;
  private Long capacityPerPump;
  private Long shipManifold;
  private String summerDraft;
  private String averageLoadingRate;
  private String tropicalDraft;
  private String freshWaterDraft;
  private String summerDeadweight;
  private String summerDisplacement;
  private String cargoTankCapacity;
  private String highVelocityVentingPressure;
  private String highVelocityVentingVaccum;
  private String pvBreakerVentingPressure;
  private String pvBreakerVentingVaccum;
}
