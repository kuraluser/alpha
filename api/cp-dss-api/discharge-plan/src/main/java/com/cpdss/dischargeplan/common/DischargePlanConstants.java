/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.common;

import java.util.Arrays;
import java.util.List;

public class DischargePlanConstants {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

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
