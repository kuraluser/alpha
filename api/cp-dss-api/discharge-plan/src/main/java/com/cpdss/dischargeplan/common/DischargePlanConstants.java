/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.common;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class DischargePlanConstants {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  public static final String DISCHARGE_INFORMATION_REQUEST_JSON_MODULE_NAME = "DISCHARGING";

  public static final String DATE_FORMAT = "dd-MM-yyyy";
  public static final String SHEET = "Sheet1";
  public static final String PORT = "Port";
  public static final String TIDE_DATE = "Tide_Date";
  public static final String TIDE_TIME = "Tide_Time";
  public static final String TIDE_HEIGHT = "Tide_Height";
  public static final String PORT_TITLE_FONT_STYLE = "Ariel";
  public static final int PORT_TITLE_FONT_HEIGHT = 11;
  public static final int ACTUAL_TYPE_VALUE = 1;
  public static final List<String> PORT_EXCEL_TEMPLATE_TITLES =
      Arrays.asList(PORT, TIDE_DATE, TIDE_TIME, TIDE_HEIGHT);
  public static final String DISCHARGING_INFORMATION_REQUEST_JSON_MODULE_NAME = "DISCHARGING";
  public static final Long UPDATE_ULLAGE_VALIDATION_SUCCESS_ID = 13L;
  public static final int ARRIVAL_CONDITION_VALUE = 1;
  public static final int DEPARTURE_CONDITION_VALUE = 2;
  public static final String ARRIVAL_CONDITION_TYPE = "ARR";
  public static final String DEPARTURE_CONDITION_TYPE = "DEP";
  public static final Long LOADICATOR_TYPE_ID = 3L;
  public static final Long UPDATE_ULLAGE_VALIDATION_STARTED_ID = 12L;
  public static final Long UPDATE_ULLAGE_LOADICATOR_REQUEST_JSON_TYPE_ID = 22L;
  public static final Long DISCHARGING_INFORMATION_PENDING_ID = 1L;
  public static final Long DISCHARGING_INFORMATION_PROCESSING_STARTED_ID = 3L;
  public static final Long DISCHARGING_INFORMATION_NO_PLAN_AVAILABLE_ID = 6L;
  public static final Long PLAN_GENERATED_ID = 5L;
  public static final Long DISCHARGING_INFORMATION_VERIFICATION_WITH_LOADICATOR_ID = 8L;
  public static final Long DISCHARGING_INFORMATION_ERROR_OCCURRED_ID = 7L;
  public static final Integer DISCHARGING_PLAN_PLANNED_TYPE_VALUE = 2;
  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

  public static final Long DISCHARGE_INFORMATION_REQUEST_JSON_TYPE_ID = 18L;
  public static final Long DISCHARGE_INFORMATION_LOADICATOR_REQUEST_JSON_TYPE_ID = 20L;
  public static final Long DISCHARGE_INFORMATION_LOADICATOR_RESPONSE_JSON_TYPE_ID = 21L;

  // discharge Plan Conditions
  public static final Integer DISCHARGE_PLAN_ARRIVAL_CONDITION_VALUE = 1;
  public static final Integer DISCHARGE_PLAN_DEPARTURE_CONDITION_VALUE = 2;

  // discharge Plan Value Types
  public static final Integer DISCHARGE_PLAN_ACTUAL_TYPE_VALUE = 1;
  public static final Integer DISCHARGE_PLAN_PLANNED_TYPE_VALUE = 2;
}
