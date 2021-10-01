/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.algo;

import java.util.List;
import lombok.Data;

/** @author arun.j */
@Data
public class DischargingPlanLoadicatorDetails {

  List<LoadicatorStowageDetails> stowageDetails;
  List<LoadicatorBallastDetails> ballastDetails;
  List<LoadicatorRobDetails> robDetails;
}
