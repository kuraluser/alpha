/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;
import lombok.Data;

@Data
public class CleaningTank {

  private List<CowTankDetail> fullTanks;
  private List<CowTankDetail> topTanks;
  private List<CowTankDetail> bottomTanks;
}
