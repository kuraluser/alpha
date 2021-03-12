/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

@Data
public class CommingleCargo {

  private Long purposeId;
  private boolean slopOnly;
  private List<Long> preferredTanks;
  private List<CargoGroup> cargoGroups;
}
