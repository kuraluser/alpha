/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.cargomaster;

import lombok.Data;

@Data
public class CargoPort {
  private long id;
  private String name;
  private String code;
  private String waterDensity;
  private String maxAirDraft;
  private String maxDraft;
}
