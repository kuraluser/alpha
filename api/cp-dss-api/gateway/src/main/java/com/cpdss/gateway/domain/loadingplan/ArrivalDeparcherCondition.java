/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArrivalDeparcherCondition {

  private String draftF;
  private String draftA;
  private String trim;
  private Double ballastWaterSegregated;
  private List<CargoQuantity> cargoDetails;
  private TankRow ballastTopTanks;
  private TankRow ballastBottomTanks;
  private TankRow cargoTopTanks;
  private TankRow cargoBottomTanks;
  private TankRow cargoCenterTanks;
  private TankCargoDetails apt;
  private TankCargoDetails fpt;
  private TankCargoDetails lfpt;
  private TankCargoDetails ufpt;
  private boolean fptTankMerged;
}
