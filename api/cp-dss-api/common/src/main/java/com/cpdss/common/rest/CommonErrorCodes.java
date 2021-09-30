/* Licensed at AlphaOri Technologies */
package com.cpdss.common.rest;

public interface CommonErrorCodes {

  int ERROR_CODE_LENGTH = 5;
  String ERRORCODE_PATTERN = "^ERR-\\w+-\\d{" + ERROR_CODE_LENGTH + "}$";
  String E_HTTP_CONFLICT = "409";
  String E_HTTP_INTERNAL_SERVER_ERROR = "500";
  String E_HTTP_NOT_FOUND = "404";
  String E_HTTP_MISSING_SERVLET_REQ_PARAM = "203";
  String E_HTTP_BINDING_ERR = "207";
  String E_HTTP_TYPE_MISMATCH = "205";
  String E_HTTP_HTTP_MSG_UNREADABLE = "206";
  String E_GEN_INTERNAL_ERR = "500";
  String E_HTTP_METHOD_NOT_ALLOWED = "208";
  String E_HTTP_BAD_REQUEST = "400";
  String E_HTTP_UNKNOWN_URL = "404";
  String E_HTTP_ILLEGAL_URL_PARAM = "201";
  String E_HTTP_ILLEGAL_JSON_PARAM = "202";
  String E_HTTP_UNAUTHORIZED_RQST = "209";
  String E_HTTP_INVALID_TOKEN = "210";
  String E_CPDSS_VOYAGE_EXISTS = "100";
  String E_CPDSS_INVALID_USER = "101";
  String E_CPDSS_ALGO_ISSUE = "102";
  String E_CPDSS_USERNAME_IN_USE = "103";
  String E_CPDSS_MAX_SHIP_USER_COUNT_ERR = "104";
  String E_CPDSS_LS_NAME_EXISTS = "105";
  String E_CPDSS_ROLE_NAME_EXISTS = "106";
  String E_CPDSS_TRANSIT_PORT_EXISTS = "107";
  String E_CPDSS_ACTIVE_VOYAGE_EXISTS = "108";
  String E_CPDSS_CONFIRMED_LS_DOES_NOT_EXIST = "109";
  String E_CPDSS_SAVE_NOT_ALLOWED = "110";
  String E_CPDSS_RESEND_ACCESS_REQUSET = "111";
  String E_CPDSS_ALREADY_REQUESTED = "112";
  String E_CPDSS_REJECTION_COUNT_EXCEEDED = "113";
  String E_CPDSS_TASK_SCHEDULE_CREATE_ERROR = "125";
  String E_CPDSS_LS_INVALID_COMMINGLE_QUANTITY = "114";
  String E_CPDSS_FILE_WRITE_ERROR = "115";

  String E_CPDSS_PASSWORD_POLICIES_VIOLATION_1 =
      "120"; // Password cannot contain first name or last name
  String E_CPDSS_PASSWORD_POLICIES_VIOLATION_2 =
      "121"; // Passwords must use at least three of the four available character types: lowercase
  // letters, uppercase letters, numbers, and symbols
  String E_CPDSS_PASSWORD_EXPIRED = "124";

  String E_CPDSS_OPERATION_NOT_ALLOWED = "114";

  //  5XX – Server Error
  String E_CPDSS_LS_INVALID_LQ = "521";
  String E_CPDSS_NO_ACTIVE_VOYAGE_FOUND = "522";
  String E_CPDSS_NO_DISCHARGE_STUDY_FOUND = "150";
  String E_CPDSS_NO_ACUTALS_OR_BL_VALUES_FOUND = "151";
  String E_CPDSS_CONFIRM_PLAN_NOT_ALLOWED = "152";

  // Tide excel upload and download error codes
  String E_CPDSS_PORT_NAME_INVALID = "310";
  String E_CPDSS_TIDE_DATE_INVALID = "311";
  String E_CPDSS_TIDE_TIME_INVALID = "312";
  String E_CPDSS_TIDE_HEIGHT_INVALID = "313";
  String E_CPDSS_INVALID_EXCEL_FILE = "314";
  String E_CPDSS_EMPTY_EXCEL_FILE = "315";
  String E_CPDSS_INVALID_CONTENT = "316";

  String E_CPDSS_ULLAGE_INVALID_TANK = "320";
  String E_CPDSS_ULLAGE_INVALID_ULLAGE_OBSERVED = "321";
  String E_CPDSS_ULLAGE_INVALID_API = "322";
  String E_CPDSS_ULLAGE_INVALID_TEMPERATURE = "323";
  String E_CPDSS_ULLAGE_INVALID_WEIGHT = "324";
  
  String E_CPDSS_ULLAGE_UPDATE_INVALID_VALUE = "325";
}
