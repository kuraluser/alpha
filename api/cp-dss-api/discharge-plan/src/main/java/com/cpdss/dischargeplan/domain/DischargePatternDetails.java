/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import com.cpdss.dischargeplan.domain.cargo.LoadablePlanBallastDetails;
import com.cpdss.dischargeplan.domain.cargo.LoadablePlanStowageDetails;
import java.util.List;
import lombok.Data;

@Data
public class DischargePatternDetails {
  private List<LoadablePlanStowageDetails> dischargePlanStowageDetails;
  private List<LoadablePlanBallastDetails> dischargePlanBallastDetails;
  private List<LoadablePlanStowageDetails> dischargePlanCommingleDetails;
}
