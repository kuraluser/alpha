/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoTobeLoaded {

  private String cargoName;
  private String colorCode;
  private String api;
  private String temperature;
  private String loadingPort;
  private String nomination;
  private String shipLoadable;
  private String tolerance;
  private String difference;
  private String timeRequiredForLoading;
  private String slopQuantity;
}
