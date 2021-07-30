/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;

import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.loadingplan.CargoVesselTankDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanRobDetails;
import com.cpdss.gateway.domain.loadingplan.PortLoadablePlanStowageDetails;
import lombok.Data;

@Data
public class LoadingUpdateUllageResponse {
  private CommonSuccessResponse responseStatus;
  private List<CargoBillOfLadding> billOfLaddingList;
  private CargoVesselTankDetails vesselTankDetails;
  private List<PortLoadablePlanStowageDetails> portLoadablePlanStowageDetails;
  private List<PortLoadablePlanBallastDetails> portLoadablePlanBallastDetails;
  private List<PortLoadablePlanRobDetails> portLoadablePlanRobDetails;
  private List<UpdateUllageCargoQuantityDetail> cargoQuantityDetails;
}
