/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.utility;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class DischargePlanConstants {
  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";
  public static final String FAILED_WITH_RESOURCE_EXC = "FAILED_WITH_RESOURCE_EXC";
  public static final String FAILED_WITH_EXC = "FAILED_WITH_EXC";
  public static final String CPDSS_BUILD_ENV_SHIP = "ship";
  public static final String CPDSS_BUILD_ENV_SHORE = "cloud";

  @Getter
  @AllArgsConstructor
  public enum DischargingPlanTables {
    DISCHARGING_INFORMATION("discharging_information"),
    DISCHARGING_INFORMATION_STATUS("discharging_information_status"),
    DISCHARGING_STAGES_MIN_AMOUNT("discharging_stages_min_amount"),
    DISCHARGING_STAGES_DURATION("discharging_stages_duration"),
    COW_PLAN_DETAILS("cow_plan_details"),
    COW_WITH_DIFFERENT_CARGO("cow_with_different_cargo"),
    COW_TANK_DETAILS("cow_tank_details"),
    DISCHARGING_DELAY("discharging_delay"),
    DISCHARGING_DELAY_REASON("discharging_delay_reason"),
    DISCHARGING_MACHINARY_IN_USE("discharging_machinary_in_use"),
    DISCHARGING_PORT_TIDE_DETAILS("discharging_port_tide_details"),
    DISCHARGING_BERTH_DETAILS("discharging_berth_details"),
    DISCHARGING_INFORMATION_ALGO_STATUS("discharging_information_algo_status"),
    ALGO_ERROR_HEADING("algo_error_heading"),
    ALGO_ERRORS("algo_errors"),
    DISCHARGING_SEQUENCE("discharging_sequence"),
    BALLAST_VALVES("ballast_valves"),
    CARGO_VALVES("cargo_valves"),
    CARGO_DISCHARGING_RATE("cargo_discharging_rate"),
    BALLAST_OPERATION("ballast_operation"),
    DISCHARGING_PLAN_PORTWISE_DETAILS("discharging_plan_portwise_details"),
    BALLASTING_RATE("ballasting_rate"),
    DEBALLASTING_RATE("deballasting_rate"),
    DISCHARGING_PLAN_BALLAST_DETAILS("discharging_plan_ballast_details"),
    DISCHARGING_PLAN_ROB_DETAILS("discharging_plan_rob_details"),
    DISCHARGING_PLAN_STOWAGE_DETAILS("discharging_plan_stowage_details"),
    DISCHARGING_PLAN_STABILITY_PARAMETERS("discharging_plan_stability_parameters"),
    DISCHARGING_PLAN_COMMINGLE_DETAILS("discharging_plan_commingle_details"),
    DISCHARGING_SEQUENCE_STABILITY_PARAMETERS("discharging_sequence_stability_parameters"),
    PORT_DISCHARGING_PLAN_STOWAGE_BALLAST_DETAILS("port_discharging_plan_stowage_ballast_details"),
    PORT_DISCHARGING_PLAN_ROB_DETAILS("port_discharging_plan_rob_details"),
    PORT_DISCHARGING_PLAN_STABILITY_PARAMETERS("port_discharging_plan_stability_parameters"),
    PORT_DISCHARGING_PLAN_STOWAGE_DETAILS("port_discharging_plan_stowage_details"),
    PORT_DISCHARGE_PLAN_COMMINGLE_DETAILS("port_discharge_plan_commingle_details"),
    JSON_DATA("json_data"),
    LOADABLE_PATTERN("loadable_pattern"),
    PORT_DISCHARGING_PLAN_STOWAGE_DETAILS_TEMP("port_discharging_plan_stowage_details_temp"),
    PORT_DISCHARGING_PLAN_STOWAGE_BALLAST_DETAILS_TEMP(
        "port_discharging_plan_stowage_ballast_details_temp"),
    PORT_DISCHARGE_PLAN_COMMINGLE_DETAILS_TEMP("port_discharge_plan_commingle_details_temp"),
    BILL_OF_LADDING("bill_of_ladding"),
    SYNOPTICAL_TABLE("synoptical_table"),
    PYUSER("pyuser");

    public final String table;
  }

  public static List<String> DISCHARGE_PLAN_SHIP_TO_SHORE =
      Arrays.asList(
          "discharging_information",
          "cow_plan_details",
          "cow_with_different_cargo",
          "cow_tank_details",
          "discharging_delay",
          "discharging_delay_reason",
          "discharging_machinary_in_use",
          "discharging_port_tide_details",
          "discharging_sequence",
          "discharging_berth_details",
          "discharging_delay_reason",
          "loadable_pattern",
          "synoptical_table");

  public static List<String> DISCHARGE_PLAN_SHORE_TO_SHIP =
      Arrays.asList(
          "discharging_information",
          "discharging_information_algo_status",
          "algo_error_heading",
          "algo_errors",
          "discharging_sequence",
          "ballast_valves",
          "cargo_valves",
          "ballasting_rate",
          "deballasting_rate",
          "discharging_plan_portwise_details",
          "discharging_plan_ballast_details",
          "discharging_plan_rob_details",
          "discharging_plan_stowage_details",
          "discharging_plan_stability_parameters",
          "discharging_plan_commingle_details",
          "cargo_discharging_rate",
          "ballast_operation",
          "discharging_sequence_stability_parameters",
          "port_discharging_plan_stowage_ballast_details",
          "port_discharging_plan_rob_details",
          "port_discharging_plan_stability_parameters",
          "port_discharging_plan_stowage_details",
          "port_discharge_plan_commingle_details",
          "json_data",
          "pyuser");

  public static List<String> DISCHARGE_PLAN_ALGO_ERRORS_SHORE_TO_SHIP =
      Arrays.asList(
          "discharging_information",
          "discharging_information_algo_status",
          "algo_error_heading",
          "algo_errors");

  public static final List<String> ULLAGE_UPDATE_SHIP_TO_SHORE =
      Arrays.asList(
          "discharging_information",
          "port_discharging_plan_stowage_details_temp",
          "port_discharging_plan_stowage_ballast_details_temp",
          "port_discharge_plan_commingle_details_temp",
          "port_discharging_plan_rob_details",
          "bill_of_ladding",
          "port_discharging_plan_stowage_details",
          "port_discharging_plan_stowage_ballast_details",
          "port_discharge_plan_commingle_details",
          "port_discharging_plan_stability_parameters");

  public static final List<String> ULLAGE_UPDATE_SHORE_TO_SHIP =
      Arrays.asList(
          "discharging_information",
          "port_discharging_plan_stowage_details_temp",
          "port_discharging_plan_stowage_ballast_details_temp",
          "port_discharge_plan_commingle_details_temp",
          "port_discharging_plan_stowage_details",
          "port_discharging_plan_stowage_ballast_details",
          "port_discharge_plan_commingle_details",
          "port_discharging_plan_stability_parameters",
          "port_discharging_plan_rob_details",
          "discharging_information_algo_status",
          "json_data",
          "synoptical_table",
          "pyuser");

  public static final List<String> ULLAGE_UPDATE_ALGO_ERRORS =
      Arrays.asList(
          "discharging_information",
          "discharging_information_algo_status",
          "algo_error_heading",
          "algo_errors");
}
