/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.util.List;
import lombok.Data;

@Data
public class DischargeSequences {

  private List<ReasonForDelay> reasonForDelays;
  private List<DischargeDelays> dischargeDelays;
  private Boolean isSequenceAltered;
}
