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
  private float api;
  private float temp;
  private float cargoNomination;
  private String tolerance;
  private float nBbls;
  private float mt;
  private float kl15C;
  private float lt;
  private float diffBbls;
  private float diffPercentage;
  private String colorCode;
}
