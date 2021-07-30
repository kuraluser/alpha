/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.common;

import java.time.format.DateTimeFormatter;

/** Constants using in the Loading Plan Services. */
public final class LoadingPlanConstants {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

  // JSON types
  public static final Long LOADING_INFORMATION_REQUEST_JSON_TYPE_ID = 9L;

  public static final Integer DEFAULT_STAGE_OFFSET_VALUE = 4;
  public static final Integer DEFAULT_STAGE_DURATION_VALUE = 4;

  // Loading Information Status IDs
  public static final Long LOADING_INFORMATION_PROCESSING_STARTED_ID = 3L;
  public static final Long LOADING_INFORMATION_PLAN_GENERATED_ID = 5L;

  // Loading Plan Conditions
  public static final Integer LOADING_PLAN_ARRIVAL_CONDITION_VALUE = 1;
  public static final Integer LOADING_PLAN_DEPARTURE_CONDITION_VALUE = 2;

  // Loading Plan Value Types
  public static final Integer LOADING_PLAN_ACTUAL_TYPE_VALUE = 1;
  public static final Integer LOADING_PLAN_PLANNED_TYPE_VALUE = 2;

  // Loading Information Request Module Name
  public static final String LOADING_INFORMATION_REQUEST_JSON_MODULE_NAME = "LOADING";

  public static final Long LOADING_RULE_MASTER_ID = 2l;
}
