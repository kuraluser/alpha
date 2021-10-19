/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.DischargeQuantityCargoDetails;
import java.util.List;
import lombok.Data;

@Data
public class PlannedCargo {

  private Boolean dischargeSlopTanksFirst;
  private Boolean dischargeCommingledCargoSeparately;
  private List<DischargeQuantityCargoDetails> dischargeQuantityCargoDetails;
}
