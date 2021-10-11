/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Titles for CargoDetailsTable - Loadable Plan Report */
@Getter
@AllArgsConstructor
public enum CargoDetailsTableTitles {
  CARGO_CODE("CARGO CODE", "CARGO CODE", "", "", true),
  LOADING_PORT("LOADING PORT", "LOADING PORT", "", "", true),
  API("API", "API", "0.00", "", true),
  TEMP("TEMP (F)", "TEMP (F)", "0.00", "", true),
  CARGO_NOMINATION(
      "CARGO NOMINATION", "CARGO NOMINATION", "#,##0;[Red]-#,##0", "#,##0_ \"Bbls\"", true),
  TOLERANCE("TOLERANCE", "TOLERANCE", "", "", true),
  NBBLS("SHIP'S FIGURE", "N.BBLS", "#,##0", "#,##0_ \"Bbls\"", false),
  MT("", "M/T", "#,##0.00", "#,##0.00_ \"MT\"", false),
  KL15C("", "KL 15°C", "#,##0.000", "#,##0.000_ \"KL\"", false),
  LT("", "L/T", "#,##0.00", "#,##0.00_ \"LT\"", false),
  DIFF_BBLS("DIFF. from Nomination", "BBLS", "#,##0", "#,##0_ \"Bbls\"", false),
  DIFF_PERCENTAGE("", "%", "0.00%", "0.00%", false);

  private final String columnName;
  private final String subColumnName;
  private final String format;
  private final String totalFormat;
  private boolean xMerge;
}
