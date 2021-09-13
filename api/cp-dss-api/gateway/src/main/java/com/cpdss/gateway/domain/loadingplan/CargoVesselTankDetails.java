/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.gateway.domain.Cargo;
import com.cpdss.gateway.domain.DischargeQuantityCargoDetails;
import com.cpdss.gateway.domain.LoadableQuantityCargoDetails;
import com.cpdss.gateway.domain.SynopticalCargoBallastRecord;
import com.cpdss.gateway.domain.VesselTank;
import java.util.List;
import lombok.Data;

@Data
public class CargoVesselTankDetails {
  private List<List<VesselTank>> cargoTanks;
  private List<SynopticalCargoBallastRecord> cargoQuantities;
  private List<LoadableQuantityCargoDetails> loadableQuantityCargoDetails;
  private List<DischargeQuantityCargoDetails> dischargeQuantityCargoDetails;
  private List<Cargo> cargoConditions;
}
