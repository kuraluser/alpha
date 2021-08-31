/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.common;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/** Constants using in the Loading Plan Services. */
public final class LoadingPlanConstants {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

  // JSON types
  public static final Long LOADING_INFORMATION_REQUEST_JSON_TYPE_ID = 9L;
  public static final Long LOADING_INFORMATION_LOADICATOR_REQUEST_JSON_TYPE_ID = 13L;
  public static final Long LOADING_INFORMATION_LOADICATOR_RESPONSE_JSON_TYPE_ID = 14L;
  public static final Long LOADING_PLAN_EDIT_LOADICATOR_REQUEST_JSON_TYPE_ID = 15L;
  public static final Long LOADING_PLAN_EDIT_LOADICATOR_RESPONSE_JSON_TYPE_ID = 16L;

  public static final Integer DEFAULT_STAGE_OFFSET_VALUE = 4;
  public static final Integer DEFAULT_STAGE_DURATION_VALUE = 4;

  // Loading Information Status IDs
  public static final Long LOADING_INFORMATION_PROCESSING_STARTED_ID = 3L;
  public static final Long LOADING_INFORMATION_PLAN_GENERATED_ID = 5L;
  public static final Long LOADING_INFORMATION_NO_PLAN_AVAILABLE_ID = 6L;
  public static final Long LOADING_INFORMATION_ERROR_OCCURRED_ID = 7L;
  public static final Long LOADING_INFORMATION_VERIFICATION_WITH_LOADICATOR_ID = 8L;

  // Loading Plan Conditions
  public static final Integer LOADING_PLAN_ARRIVAL_CONDITION_VALUE = 1;
  public static final Integer LOADING_PLAN_DEPARTURE_CONDITION_VALUE = 2;

  // Loading Plan Value Types
  public static final Integer LOADING_PLAN_ACTUAL_TYPE_VALUE = 1;
  public static final Integer LOADING_PLAN_PLANNED_TYPE_VALUE = 2;

  // Loading Information Request Module Name
  public static final String LOADING_INFORMATION_REQUEST_JSON_MODULE_NAME = "LOADING";

  public static final Long LOADING_RULE_MASTER_ID = 2l;

  public static final Long LOADING_INFORMATION_LOADICATOR_TYPE_ID = 2L;

  public static final String DATE_FORMAT = "dd-MM-yyyy";
  public static final String SHEET = "Sheet1";
  public static final String PORT = "Port";
  public static final String TIDE_DATE = "Tide_Date";
  public static final String TIDE_TIME = "Tide_Time";
  public static final String TIDE_HEIGHT = "Tide_Height";
  public static final String PORT_TITLE_FONT_STYLE = "Ariel";
  public static final int PORT_TITLE_FONT_HEIGHT = 11;
  public static final List<String> PORT_EXCEL_TEMPLATE_TITLES =
      Arrays.asList(PORT, TIDE_DATE, TIDE_TIME, TIDE_HEIGHT);
}
