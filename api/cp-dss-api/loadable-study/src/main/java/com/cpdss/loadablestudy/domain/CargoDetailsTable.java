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
  private float cargoNominationTotal;
  private float nBblsTotal;
  private float mtTotal;
  private float kl15CTotal;
  private float ltTotal;
  private float diffBblsTotal;
  private float diffPercentageTotal;
}
