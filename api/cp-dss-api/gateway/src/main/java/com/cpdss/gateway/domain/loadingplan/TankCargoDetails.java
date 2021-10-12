/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TankCargoDetails {

  private String tankName;
  private Long id;
  private String cargoName;
  private String colorCode;
  private Double quantity;
  private String api;
  private String temperature;
  private String fillingRatio;
  private String ullage;
}
