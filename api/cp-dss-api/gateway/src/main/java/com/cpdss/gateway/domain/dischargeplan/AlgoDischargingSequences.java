/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.loadingplan.ReasonForDelay;
import java.util.List;
import lombok.Data;

/** @author pranav.k */
@Data
public class AlgoDischargingSequences {

  private List<ReasonForDelay> reasonForDelays;
  private List<DischargingDelays> loadingDelays;
}
