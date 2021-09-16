/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.common.generated.Common;
import com.cpdss.gateway.domain.vessel.PumpType;
import com.cpdss.gateway.domain.vessel.VesselPump;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class CargoMachineryInUse {

  private List<PumpType> pumpTypes; // Master data, Type
  private List<VesselPump> vesselPumps; // Master data

  private List<VesselComponent> vesselManifold; // Master data
  private List<VesselComponent> vesselBottomLine; // Master data

  private List<LoadingMachinesInUse> loadingMachinesInUses; // User selected data
  private List<LoadingMachinesInUse> dischargeMachinesInUses; // User selected data

  private Map<String, Integer> machineTypes = new HashMap<>();

  private List<PumpType> tankTypes;

  public CargoMachineryInUse() {
    for (Common.MachineType mt : EnumSet.allOf(Common.MachineType.class)) {
      try {
        machineTypes.put(mt.getValueDescriptor().getName(), mt.getValueDescriptor().getNumber());
      } catch (ArrayIndexOutOfBoundsException e) {
        log.error("Index out of bound error for enum");
      }
    }
  }
}
