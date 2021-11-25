/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import lombok.Data;

@Data
public class TanksWashedWithCargo {

  private String tankName;
  private String loadedCargoName;
  private String washingCargoName;
}
