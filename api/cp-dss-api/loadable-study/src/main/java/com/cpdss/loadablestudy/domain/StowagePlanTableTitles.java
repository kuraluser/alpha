/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Titles for StowagePlanTable - Loadable Plan Report */
@Getter
@AllArgsConstructor
public enum StowagePlanTableTitles {
  TANK_NO("Tank No.", "\"0 P\"   0.0 \"%\""),
  CARGO_CODE("Cargo Code", ""),
  ULLAGE("Ullage (m)", "\"(U)\"_0.00_ \"m\""),
  LOADED_PERCENTAGE("Loaded  %", "0.00%"),
  SHIPS_NBBLS("Ship's N.BBLS", "#,##0.00_ \"Bbls\""),
  SHIPS_MT("Ship's MT", "#,##0.00_ \"MT\""),
  SHIPS_KL_15C("Ship's KL @ 15°C", "#,##0_ \"KL\"");

  private final String columnName;
  private final String format;
}
