/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VesselPlanTableTitles {
  STOWAGE_PLAN("STOWAGE PLAN"),
  VESSEL_NAME("VESSEL NAME: "),
  VOYAGE_NO("Voy.No. : "),
  DATE("DATE: ");

  private final String columnName;
}
