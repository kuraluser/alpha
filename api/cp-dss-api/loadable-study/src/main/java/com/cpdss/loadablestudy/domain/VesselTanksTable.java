/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VesselTanksTable {
  private String tankNo;
  private String cargoCode;
  private float ullage;
  private float loadedPercentage;
  private float shipsNBbls;
  private float shipsMt;
  private float shipsKlAt15C;
  private float frameNoFrom;
  private float frameNoTo;
  private String colorCode;
}
