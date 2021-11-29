/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.utility;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class LoadableStudiesConstants {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  public static final String STATUS_ACTIVE = "ACTIVE";
  public static final String STATUS_CONFIRMED = "CONFIRMED";
  public static final String STATUS_CLOSE = "CLOSED";

  public static final String OPERATION_TYPE_ARR = "ARR";
  public static final String OPERATION_TYPE_DEP = "DEP";

  public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

  public static final String VOYAGE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";

  public static final String INVALID_LOADABLE_STUDY_ID = "INVALID_LOADABLE_STUDY_ID";
  public static final String INVALID_LOADABLE_QUANTITY = "INVALID_LOADABLE_QUANTITY";

  public static final Long FRESH_WATER_TANK_CATEGORY_ID = 3L;
  public static final Long FUEL_OIL_TANK_CATEGORY_ID = 5L;
  public static final Long DIESEL_OIL_TANK_CATEGORY_ID = 6L;
  public static final Long LUBRICATING_OIL_TANK_CATEGORY_ID = 14L;
  public static final Long LUBRICANT_OIL_TANK_CATEGORY_ID = 19L;
  public static final Long FUEL_VOID_TANK_CATEGORY_ID = 22L;
  public static final Long FRESH_WATER_VOID_TANK_CATEGORY_ID = 23L;
  public static final Long BALLAST_VOID_TANK_CATEGORY_ID = 16L;
  public static final Long BALLAST_TANK_CATEGORY_ID = 2L;

  public static final Long LOADING_OPERATION_ID = 1L;
  public static final Long DISCHARGING_OPERATION_ID = 2L;
  public static final Long BUNKERING_OPERATION_ID = 3L;
  public static final Long TRANSIT_OPERATION_ID = 4L;
  public static final Long STS_LOADING_OPERATION_ID = 5L;
  public static final Long STS_DISCHARGING_OPERATION_ID = 6L;

  public static final Long LS_STATUS_PENDING = 1l;
  public static final Long LS_STATUS_CONFIRMED = 2l;
  public static final Long LS_STATUS_PLAN_GENERATED = 3l;

  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
  public static final Long CLOSE_VOYAGE_STATUS = 2L;
  public static final Long OPEN_VOYAGE_STATUS = 1L;
  public static final Long CONFIRMED_STATUS_ID = 2L;
  public static final Long LOADABLE_STUDY_INITIAL_STATUS_ID = 1L;
  public static final Long LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID = 3L;
  public static final String VOYAGE_EXISTS = "VOYAGE_EXISTS";
  public static final String SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL = "ARR";
  public static final String SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE = "DEP";
  public static final String ETA_ETD_FORMAT = "dd-MM-yyyy HH:mm";
  public static final String LAY_CAN_FORMAT = "dd-MM-yyyy";
  public static final Long CARGO_TANK_CATEGORY_ID = 1L;
  public static final Long CARGO_SLOP_TANK_CATEGORY_ID = 9L;
  public static final Long CARGO_VOID_TANK_CATEGORY_ID = 15L;

  public static final List<Long> CARGO_OPERATION_ARR_DEP_SYNOPTICAL =
      Arrays.asList(
          LOADING_OPERATION_ID,
          DISCHARGING_OPERATION_ID,
          BUNKERING_OPERATION_ID,
          TRANSIT_OPERATION_ID,
          STS_LOADING_OPERATION_ID,
          STS_DISCHARGING_OPERATION_ID);

  public static final List<Long> CARGO_TANK_CATEGORIES =
      Arrays.asList(
          CARGO_TANK_CATEGORY_ID, CARGO_SLOP_TANK_CATEGORY_ID, CARGO_VOID_TANK_CATEGORY_ID);

  public static final List<Long> OHQ_TANK_CATEGORIES =
      Arrays.asList(
          FRESH_WATER_TANK_CATEGORY_ID,
          FUEL_OIL_TANK_CATEGORY_ID,
          DIESEL_OIL_TANK_CATEGORY_ID,
          FUEL_VOID_TANK_CATEGORY_ID,
          FRESH_WATER_VOID_TANK_CATEGORY_ID);

  public static final List<Long> SYNOPTICAL_TABLE_TANK_CATEGORIES =
      Arrays.asList(
          CARGO_TANK_CATEGORY_ID,
          CARGO_SLOP_TANK_CATEGORY_ID,
          FRESH_WATER_TANK_CATEGORY_ID,
          FUEL_OIL_TANK_CATEGORY_ID,
          DIESEL_OIL_TANK_CATEGORY_ID,
          BALLAST_TANK_CATEGORY_ID,
          BALLAST_VOID_TANK_CATEGORY_ID);

  public static final Long ACTIVE_VOYAGE_STATUS = 3L;
  public static final String START_VOYAGE = "START";
  public static final String LOADABLE_PLAN_REPORT_BEFORE_LOADING_SHEET_NAME =
      "STOWAGE PLAN Before Loading";
  public static final int LOADABLE_PLAN_REPORT_DEFAULT_COLUMN_WIDTH = 17;
  public static final int LOADABLE_PLAN_REPORT_START_ROW = 0;
  public static final int LOADABLE_PLAN_REPORT_START_COLUMN = 1;
  public static final int LOADABLE_PLAN_REPORT_TABLE_SPACER = 2;
  public static final String LOADABLE_PLAN_REPORT_DEFAULT_FONT = "Arial";
  public static final int LOADABLE_PLAN_REPORT_DEFAULT_FONT_HEIGHT = 11;
  public static final String COMMINGLE_DEFAULT_COLOR_CODE = "#7114d9";
  public static final String WHITE_COLOR_CODE = "#FFFFFF";
  public static final int LOADABLE_PLAN_REPORT_CARGO_TITLE_WIDTH = 2;
  public static final String LOADABLE_PLAN_REPORT_TOTAL_VALUE = "TOTAL";
  public static final int LOADABLE_PLAN_REPORT_TITLE_WIDTH = 4;
  public static final String LOADABLE_PLAN_REPORT_FPT_VALUE = "FPT";
  public static final String ET_FORMAT = "HH:mm 'LT' dd/MM/yy";

  public static final List<Long> CARGO_BALLAST_TANK_CATEGORIES =
      Arrays.asList(
          CARGO_TANK_CATEGORY_ID,
          CARGO_SLOP_TANK_CATEGORY_ID,
          CARGO_VOID_TANK_CATEGORY_ID,
          BALLAST_TANK_CATEGORY_ID,
          BALLAST_VOID_TANK_CATEGORY_ID);

  public static final List<Long> BALLAST_TANK_CATEGORIES =
      Arrays.asList(BALLAST_TANK_CATEGORY_ID, BALLAST_VOID_TANK_CATEGORY_ID);

  public static final Long LOADABLE_PATTERN_VALIDATION_STARTED_ID = 14L;
  public static final Long LOADABLE_PATTERN_VALIDATION_SUCCESS_ID = 12L;
  public static final Long LOADABLE_PATTERN_VALIDATION_FAILED_ID = 13L;
  public static final Long LOADABLE_PATTERN_VALIDATE_RESULT_JSON_ID = 6L;
  public static final List<Long> VALIDATED_CONDITIONS =
      Arrays.asList(
          LOADABLE_PATTERN_VALIDATION_STARTED_ID,
          LOADABLE_PATTERN_VALIDATION_SUCCESS_ID,
          LOADABLE_PATTERN_VALIDATION_FAILED_ID);

  public static final String DEFAULT_USER_NAME = "UNKNOWN";
  public static final String CREATED_DATE_FORMAT = "dd-MM-yyyy";
  public static final String BALLAST_FRONT_TANK = "FRONT";
  public static final String BALLAST_CENTER_TANK = "CENTER";
  public static final String BALLAST_REAR_TANK = "REAR";

  public static final String COMMINGLE = "COM";
  public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
  public static final String DATE_TIME_FORMAT_LAST_MODIFIED = "dd-MM-yyyy HH:mm";
  public static final Long LOADABLE_STUDY_PROCESSING_STARTED_ID = 4L;
  public static final Long LOADABLE_STUDY_STATUS_VERIFICATION_WITH_LOADICATOR_ID = 7L;
  public static final Long LOADABLE_STUDY_STATUS_ERROR_OCCURRED_ID = 11L;
  public static final Long LOADABLE_STUDY_STATUS_FEEDBACK_LOOP_STARTED = 16L;
  public static final Long LOADABLE_PATTERN_VALIDATION_FEEDBACK_LOOP_STARTED = 18L;
  public static final Long LOADABLE_STUDY_NO_PLAN_AVAILABLE_ID = 6L;
  public static final Long LOADABLE_STUDY_COMMUNICATED_TO_SHORE = 21L;
  public static final Long PROCESSING_WITH_SHIP_SIDE_ALGO = 22L;
  public static final Long PATTERN_COMMUNICATED_TO_SHORE = 23L;
  public static final String ERRO_CALLING_ALGO = "ERROR_CALLING_ALGO";
  public static final int CASE_1 = 1;
  public static final int CASE_2 = 2;
  public static final int CASE_3 = 3;
  public static final String INVALID_LOADABLE_PATTERN_COMMINGLE_DETAIL_ID =
      "INVALID_LOADABLE_PATTERN_COMMINGLE_DETAIL_ID";
  public static final String INVALID_LOADABLE_PATTERN_ID = "INVALID_LOADABLE_PATTERN_ID";
  public static final Long LOAD_LINE_TROPICAL_TO_SUMMER_ID = 7L;
  public static final Long LOAD_LINE_TROPICAL_TO_WINTER_ID = 8L;
  public static final Long LOAD_LINE_SUMMER_TO_WINTER_ID = 9L;

  public static final List<Long> CASE_1_LOAD_LINES =
      Arrays.asList(
          LOAD_LINE_TROPICAL_TO_SUMMER_ID,
          LOAD_LINE_TROPICAL_TO_WINTER_ID,
          LOAD_LINE_SUMMER_TO_WINTER_ID);
  public static final List<Long> OHQ_CENTER_TANK_CATEGORIES =
      Arrays.asList(
          FUEL_OIL_TANK_CATEGORY_ID, DIESEL_OIL_TANK_CATEGORY_ID, FUEL_VOID_TANK_CATEGORY_ID);

  public static final List<Long> OHQ_REAR_TANK_CATEGORIES =
      Arrays.asList(FRESH_WATER_TANK_CATEGORY_ID, FRESH_WATER_VOID_TANK_CATEGORY_ID);

  public static final List<Long> OHQ_VOID_TANK_CATEGORIES =
      Arrays.asList(FUEL_VOID_TANK_CATEGORY_ID, FRESH_WATER_VOID_TANK_CATEGORY_ID);

  public static final String BALLAST_TANK_COLOR_CODE = "#01717D";
  public static final long STOWAGE_STATUS = 1L;

  public static final String LOADABLE_STUDY = "LS";
  public static final String DICHARGE_STUDY = "DS";
  public static final Long LOADABLE_STUDY_REQUEST = 1L;
  public static final Long LOADABLE_STUDY_LOADICATOR_REQUEST = 3L;
  public static final Long LOADABLE_STUDY_LOADICATOR_RESPONSE = 4L;
  public static final Long LOADABLE_PATTERN_EDIT_REQUEST = 5L;
  public static final Long LOADABLE_PATTERN_EDIT_LOADICATOR_REQUEST = 7L;
  public static final Long LOADABLE_PATTERN_EDIT_LOADICATOR_RESPONSE = 8L;

  public static final String TYPE_DEPARTURE = "Departure";

  public static final String LOADABLE_STUDY_JSON_MODULE_NAME = "LOADABLE";
  public static final String DISCHARGE_STUDY_JSON_MODULE_NAME = "DISCHARGE";
  public static final Long DISCHARGE_STUDY_REQUEST = 11L;
  public static final Long UPDATED_LOADABLE_STUDY_RESULT_JSON_ID = 2L;
  public static final Long LOADABLE_STUDY_ALGO_RESULT_JSON_ID = 17L;
  public static final Long DISCHARGE_STUDY_RESULT_JSON_ID = 12L;

  public static final Long LOADABLE_STUDY_LOADICATOR_TYPE_ID = 1L;
  public static final Integer PLANNING_TYPE_LOADING = 1;
  public static final Integer PLANNING_TYPE_DISCHARGE = 2;
  public static final String LOADING_PORT = "LOADING";
  public static final String DISCHARGE_PORT = "DISCHARGE";
  public static final String INVALID_ULLAGE_OR_SOUNDING_VALUE = "INVALID_ULLAGE_OR_SOUNDING_VALUE";

  public static final String CPDSS_BUILD_ENV_SHIP = "ship";
  public static final String CPDSS_BUILD_ENV_SHORE = "cloud";
  public static final String FAILED_WITH_RESOURCE_EXC = "FAILED_WITH_RESOURCE_EXC";
  public static final String FAILED_WITH_EXC = "FAILED_WITH_EXC";

  public static final String ENV = "env";

  public static List<String> LOADABLE_STUDY_COMM_TABLES_SHORE_TO_SHIP =
      Arrays.asList(
          "loadable_study",
          "json_data",
          "loadable_pattern",
          "algo_error_heading",
          "algo_errors",
          "loadable_plan_constraints",
          "loadable_plan_quantity",
          "loadable_plan_commingle_details",
          "loadable_pattern_cargo_topping_off_sequence",
          "loadable_plan_stowage_details",
          "loadable_plan_ballast_details",
          "loadable_plan_commingle_details_portwise",
          "stability_parameters",
          "loadable_pattern_cargo_details",
          "loadable_plan_stowage_ballast_details",
          "synoptical_table",
          "loadicator_data_for_synoptical_table");
  public static List<String> LOADABLE_STUDY_COMM_TABLES_SHIP_TO_SHORE =
      Arrays.asList(
          "loadable_study",
          "comingle_cargo",
          "cargo_nomination",
          "loadable_study_port_rotation",
          "on_hand_quantity",
          "on_board_quantity",
          "loadable_quantity",
          "synoptical_table",
          "cargo_nomination_operation_details");

  @Getter
  @AllArgsConstructor
  public enum LoadableStudyTables {
    LOADABLE_STUDY("loadable_study"),
    VOYAGE("voyage"),
    // DUPLICATED_FROM_LOADABLE_STUDY("duplicated_from_loadable_study"),
    // LOADABLE_STUDY_STATUS("loadable_study_status"),
    COMINGLE_CARGO("comingle_cargo"),
    CARGO_NOMINATION("cargo_nomination"),
    LOADABLE_STUDY_PORT_ROTATION("loadable_study_port_rotation"),
    ON_HAND_QUANTITY("on_hand_quantity"),
    ON_BOARD_QUANTITY("on_board_quantity"),
    LOADABLE_QUANTITY("loadable_quantity"),
    SYNOPTICAL_TABLE("synoptical_table"),
    JSON_DATA("json_data"),
    LOADABLE_STUDY_ALGO_STATUS("loadable_study_algo_status"),
    LOADABLE_PATTERN("loadable_pattern"),
    ALGO_ERROR_HEADING("algo_error_heading"),
    ALGO_ERRORS("algo_errors"),
    LOADABLE_PLAN_CONSTRAINTS("loadable_plan_constraints"),
    LOADABLE_PLAN_QUANTITY("loadable_plan_quantity"),
    LOADABLE_PLAN_COMMINGLE_DETAILS("loadable_plan_commingle_details"),
    LOADABLE_PATTERN_CARGO_TOPPING_OFF_SEQUENCE("loadable_pattern_cargo_topping_off_sequence"),
    LOADABLE_PLAN_STOWAGE_DETAILS("loadable_plan_stowage_details"),
    LOADABLE_PLAN_BALLAST_DETAILS("loadable_plan_ballast_details"),
    LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE("loadable_plan_commingle_details_portwise"),
    STABILITY_PARAMETERS("stability_parameters"),
    LOADABLE_PATTERN_CARGO_DETAILS("loadable_pattern_cargo_details"),
    LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS("loadable_plan_stowage_ballast_details"),
    LOADICATOR_DATA_FOR_SYNOPTICAL_TABLE("loadicator_data_for_synoptical_table"),
    CARGO_NOMINATION_OPERATION_DETAILS("cargo_nomination_operation_details"),
    COMMUNICATION_STATUS_UPDATE("communication_status_update");

    public final String table;
  }
}
