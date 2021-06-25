/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.util.List;
import lombok.Data;

@Data
public class CargoMachineryInUse {

  private List<PumpType> pumpTypes;
  private List<VesselPump> vesselPumps;
  private List<LoadingMachinesInUse> loadingMachinesInUses;
}
