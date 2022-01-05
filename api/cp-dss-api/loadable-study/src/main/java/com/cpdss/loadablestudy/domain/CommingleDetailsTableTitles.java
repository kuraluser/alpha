/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Titles for CommingleDetailsTable - Loadable Plan Report
 *
 * @author sreemanikandan.k
 * @since 04/01/2022
 */
@Getter
@AllArgsConstructor
public enum CommingleDetailsTableTitles {
  GRADE("GRADE", "GRADE", "", false, true),
  TANK("TANK", "TANK", "", false, true),
  QUANTITY("QUANTITY", "QUANTITY", "#,##0.00", false, true),
  API("API", "API", "0.00", false, true),
  TEMPERATURE("TEMP (F)", "TEMP (F)", "0.00", false, true),
  CARGO_CODE("COMPOSITION BREAKDOWN", "CARGO CODE", "", true, false),
  CARGO_PERCENTAGE("", "CARGO PERCENTAGE", "0.00%", false, false),
  CARGO_QUANTITY("", "CARGO QUANTITY", "#,##0.00", false, false);

  private final String columnName;
  private final String subColumnName;
  private final String format;
  private boolean isRowsMerge;
  private boolean isColumnsMerge;
}
