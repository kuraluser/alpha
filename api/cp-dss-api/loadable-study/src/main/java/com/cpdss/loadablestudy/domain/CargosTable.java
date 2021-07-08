/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Builder;
import lombok.Data;

/** Details of CargoDetailsTable - Loadable Plan Report */
@Builder
@Data
public class CargosTable {
  private String cargoCode;
  private String loadingPort;
  private double api;
  private double temp;
  private double cargoNomination;
  private String tolerance;
  private double nBbls;
  private double mt;
  private double kl15C;
  private double lt;
  private double diffBbls;
  private double diffPercentage;
  private String colorCode;
}
