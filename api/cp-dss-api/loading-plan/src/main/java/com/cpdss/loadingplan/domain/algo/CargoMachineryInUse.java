/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CargoMachineryInUse {

  // private List<PumpType> pumpTypes;
  // private List<VesselPump> vesselPumps;
  private List<LoadingMachinesInUse> loadingMachinesInUses;
}
