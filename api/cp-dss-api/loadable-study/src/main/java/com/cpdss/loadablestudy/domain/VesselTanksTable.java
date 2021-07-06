/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VesselTanksTable {
  private String tankNo;
  private String cargoCode;
  private double ullage;
  private double loadedPercentage;
  private double shipsNBbls;
  private double shipsMt;
  private double shipsKlAt15C;
  private float frameNoFrom;
  private float frameNoTo;
  private String colorCode;
}
