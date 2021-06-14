/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Titles for PortOperationsTable - Loadable Plan Report */
@Getter
@AllArgsConstructor
public enum PortOperationsTableTitles {
  OPERATION("Operation"),
  PORT_NAME("Name of Port"),
  COUNTRY("Country"),
  LAYCAN_RANGE("Laycan Range"),
  ETA("ETA"),
  ETD("ETD"),
  ARR_FWD_DRAFT("Arr. FWD Draft"),
  ARR_AFT_DRAFT("Arr.  AFT Draft"),
  ARR_DISPLACEMENT("Arr. Displacement"),
  DEP_FWD_DRAFT("Dep. FWD Draft"),
  DEP_AFT_DRAFT("Dep. AFT Draft"),
  DEP_DISP("Dep. Disp.");

  private final String columnName;
}
