/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.common;

import java.time.format.DateTimeFormatter;

/** Constants using in the Loading Plan Services. */
public final class LoadingPlanConstants {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

  public static final Long LOADING_INFORMATION_REQUEST_ID = 9L;
}
