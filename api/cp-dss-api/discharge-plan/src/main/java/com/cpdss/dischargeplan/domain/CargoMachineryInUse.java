/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CargoMachineryInUse {
  private List<LoadingMachinesInUse> disMachinesInUses; // User selected data
}
