/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/** Class for CargoDetails - Loadable Plan Report */
@Builder
@Data
public class CargoDetailsTable {
  private List<CargosTable> cargosTableList;
  private double cargoNominationTotal;
  private double nBblsTotal;
  private double mtTotal;
  private double kl15CTotal;
  private double ltTotal;
  private double diffBblsTotal;
  private double diffPercentageTotal;
}
