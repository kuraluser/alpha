/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.gateway.domain.dischargeplan.DischargingDelays;
import java.util.List;
import lombok.Data;

@Data
public class LoadingSequences {

  private List<ReasonForDelay> reasonForDelays;
  private List<LoadingDelays> loadingDelays;
  private List<DischargingDelays> dischargingDelays;
}
