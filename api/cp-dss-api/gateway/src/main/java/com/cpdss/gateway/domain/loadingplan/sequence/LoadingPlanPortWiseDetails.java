/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;

import com.cpdss.gateway.domain.AlgoPlanPortWiseDetails;

import lombok.Data;

@Data
public class LoadingPlanPortWiseDetails extends AlgoPlanPortWiseDetails {

  private List<LoadingQuantityCommingleCargoDetails> loadableQuantityCommingleCargoDetails;
  private List<LoadingPlanStowageDetails> loadablePlanStowageDetails;
  private List<LoadingPlanRobDetails> loadablePlanRoBDetails;
  private List<LoadingPlanBallastDetails> loadablePlanBallastDetails;
 
}
