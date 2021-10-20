/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.AlgoPlanPortWiseDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanBallastDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanRobDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingPlanStowageDetails;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingQuantityCommingleCargoDetails;
import java.util.List;
import lombok.Data;

@Data
public class DischargingPlanPortWiseDetails extends AlgoPlanPortWiseDetails {

  private List<LoadingQuantityCommingleCargoDetails> dischargeQuantityCommingleCargoDetails;
  private List<LoadingPlanStowageDetails> dischargePlanStowageDetails;
  private List<LoadingPlanRobDetails> dischargePlanRoBDetails;
  private List<LoadingPlanBallastDetails> dischargePlanBallastDetails;
}
