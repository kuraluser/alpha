/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.util.List;
import lombok.Data;

/** @author pranav.k */
@Data
public class LoadingPlanLoadicatorDetails {

  List<LoadicatorStowageDetails> stowageDetails;
  List<LoadicatorBallastDetails> ballastDetails;
  List<LoadicatorRobDetails> robDetails;
  List<LoadicatorCommingleDetails> commingleDetails;
  private Boolean isUllageAltered;
}
