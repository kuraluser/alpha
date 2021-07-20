/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Titles for PortOperationsTable - Loadable Plan Report */
@Getter
@AllArgsConstructor
public enum PortOperationsTableTitles {
  OPERATION("Operation", ""),
  PORT_NAME("Name of Port", ""),
  COUNTRY("Country", ""),
  LAYCAN_RANGE("Laycan Range", ""),
  ETA("ETA", ""),
  ETD("ETD", ""),
  ARR_FWD_DRAFT("Arr. FWD Draft", "#,##0.00"),
  ARR_AFT_DRAFT("Arr.  AFT Draft", "#,##0.00"),
  ARR_DISPLACEMENT("Arr. Displacement", "#,##0.00"),
  DEP_FWD_DRAFT("Dep. FWD Draft", "#,##0.00"),
  DEP_AFT_DRAFT("Dep. AFT Draft", "#,##0.00"),
  DEP_DISP("Dep. Disp.", "#,##0.00");

  private final String columnName;
  private final String format;
}
