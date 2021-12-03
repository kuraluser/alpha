/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.generated.Common;
import com.cpdss.gateway.domain.loadingplan.LoadingMachinesInUse;
import com.cpdss.gateway.domain.vessel.PumpType;
import com.cpdss.gateway.domain.vessel.VesselPump;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Cargo Machinery For Algo Receiving
 *
 * @author sreemanikandan.k
 * @since 03-12-2021
 */
@Slf4j
@Data
@AllArgsConstructor
public class CargoMachineryInUse {

  private List<PumpType> pumpTypes;
  private List<VesselPump> vesselPumps;

  private List<VesselComponent> vesselManifolds;
  private List<VesselComponent> vesselBottomLines;

  private List<LoadingMachinesInUse> machinesInUses;

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
