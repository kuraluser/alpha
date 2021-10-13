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
  public static final Long LOADICATOR_TYPE_ID = 2L;
  public static final Long UPDATE_ULLAGE_VALIDATION_STARTED_ID = 12L;
  public static final Long UPDATE_ULLAGE_LOADICATOR_REQUEST_JSON_TYPE_ID = 15L;
  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
}
