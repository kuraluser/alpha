/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.utility;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class LoadingPlanConstants {
  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";
  public static final List<String> LOADING_SYNCHRONIZATION_IDENTIFIERS =
      Arrays.asList("loading_information", "cargo_topping_off_sequence");
  public static final String FAILED_WITH_RESOURCE_EXC = "FAILED_WITH_RESOURCE_EXC";
  public static final String FAILED_WITH_EXC = "FAILED_WITH_EXC";

  @Getter
  @AllArgsConstructor
  public enum LoadingPlanTables {
    PORT_LOADING_PLAN_STABILITY_PARAMETERS("port_loading_plan_stability_parameters"),
    PORT_LOADING_PLAN_ROB_DETAILS("port_loading_plan_rob_details"),
    LOADING_PLAN_BALLAST_DETAILS("loading_plan_ballast_details"),
    LOADING_PLAN_ROB_DETAILS("loading_plan_rob_details"),
    LOADING_PLAN_PORTWISE_DETAILS("loading_plan_portwise_details"),
    PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS("port_loading_plan_stowage_ballast_details"),
    PORT_LOADING_PLAN_STOWAGE_BALLAST_DETAILS_TEMP(
        "port_loading_plan_stowage_ballast_details_temp"),
    PORT_LOADING_PLAN_STOWAGE_DETAILS("port_loading_plan_stowage_details"),
    PORT_LOADING_PLAN_STOWAGE_DETAILS_TEMP("port_loading_plan_stowage_details_temp"),
    LOADING_SEQUENCE("loading_sequence"),
    LOADING_PLAN_STOWAGE_DETAILS("loading_plan_stowage_details"),
    LOADING_SEQUENCE_STABILITY_PARAMETERS("loading_sequence_stability_parameters"),
    LOADING_PLAN_STABILITY_PARAMETERS("loading_plan_stability_parameters"),
    LOADING_INFORMATION("loading_information"),
    CARGO_TOPPING_OFF_SEQUENCE("cargo_topping_off_sequence"),
    LOADING_BERTH_DETAILS("loading_berth_details"),
    LOADING_DELAY("loading_delay"),
    LOADING_DELAY_REASON("loading_delay_reason"),
    LOADING_MACHINARY_IN_USE("loading_machinary_in_use"),
    STAGES_MIN_AMOUNT("stages_min_amount"),
    STAGES_DURATION("stages_duration"),
    LOADING_INFORMATION_STATUS("loading_information_status"),
    LOADING_INFORMATION_ARRIVAL_STATUS("loading_information_arrival_status"),
    LOADING_INFORMATION_DEPARTURE_STATUS("loading_information_departure_status"),
    PORT_LOADABLE_PLAN_COMMINGLE_DETAILS_TEMP("port_loadable_plan_commingle_details_temp"),
    PORT_LOADABLE_PLAN_COMMINGLE_DETAILS("port_loadable_plan_commingle_details"),
    BILL_OF_LADDING("bill_of_ladding"),
    PYUSER("pyuser"),
    VOYAGE("voyage"),
    LOADABLE_PATTERN("loadable_pattern"),
    LOADICATOR_DATA_FOR_SYNOPTICAL_TABLE("loadicator_data_for_synoptical_table"),
    BALLAST_OPERATION("ballast_operation"),
    EDUCTION_OPERATION("eduction_operation"),
    CARGO_LOADING_RATE("cargo_loading_rate"),
    JSON_DATA("json_data"),
    LOADING_PORT_TIDE_DETAILS("loading_port_tide_details"),
    ALGO_ERROR_HEADING("algo_error_heading"),
    ALGO_ERRORS("algo_errors"),
    LOADING_INSTRUCTIONS("loading_instructions"),
    SYNOPTICAL_TABLE("synoptical_table"),
    LOADABLE_STUDY_PORT_ROTATION("loadable_study_port_rotation"),
    LOADING_INFORMATION_ALGO_STATUS("loading_information_algo_status");

    public final String table;
  }

  public static final List<String> loadingPlanCommunicationList =
      Arrays.asList(
          "loading_information",
          "loading_sequence",
          "loading_plan_portwise_details",
          "port_loading_plan_stability_parameters",
          "port_loading_plan_rob_details",
          "loading_plan_ballast_details",
          "loading_plan_rob_details",
          "port_loading_plan_stowage_ballast_details",
          "port_loading_plan_stowage_ballast_details_temp",
          "port_loading_plan_stowage_details",
          "port_loading_plan_stowage_details_temp",
          "loading_plan_stowage_details",
          "loading_sequence_stability_parameters",
          "loading_plan_stability_parameters",
          "ballast_operation",
          "eduction_operation",
          "cargo_loading_rate",
          "json_data",
          "algo_error_heading",
          "algo_errors");

  public static final List<String> loadingPlanCommWithAlgoErrorsList =
      Arrays.asList(
          "loading_information",
          "loading_information_algo_status",
          "algo_error_heading",
          "algo_errors");

  public static final List<String> loadingPlanGenerationCommList =
      Arrays.asList(
          "loading_information",
          "cargo_topping_off_sequence",
          "loading_berth_details",
          "loading_delay",
          "loading_delay_reason",
          "loading_machinary_in_use",
          "voyage",
          "loadable_pattern",
          "loading_port_tide_details",
          "loading_instructions",
          "synoptical_table",
          "loadable_study_port_rotation");

  public static final List<String> ULLAGE_UPDATE_SHIP_TO_SHORE =
      Arrays.asList(
          "loading_information",
          "port_loading_plan_stowage_ballast_details_temp",
          "port_loading_plan_stowage_details_temp",
          "port_loadable_plan_commingle_details_temp",
          "port_loading_plan_rob_details",
          "bill_of_ladding");

  public static final List<String> ULLAGE_UPDATE_SHORE_TO_SHIP_LOADICATOR_ON =
      Arrays.asList(
          "loading_information",
          "port_loading_plan_stowage_details",
          "port_loading_plan_stowage_ballast_details",
          "port_loadable_plan_commingle_details",
          "port_loading_plan_stability_parameters",
          "pyuser");

  public static final List<String> ULLAGE_UPDATE_SHORE_TO_SHIP_LOADICATOR_OFF =
      Arrays.asList(
          "loading_information",
          "port_loading_plan_stowage_details",
          "port_loading_plan_stowage_ballast_details",
          "port_loadable_plan_commingle_details",
          "port_loading_plan_stability_parameters",
          "pyuser");

  public static final List<String> ULLAGE_UPDATE_ALGO_ERRORS =
      Arrays.asList(
          "loading_information",
          "loading_information_algo_status",
          "algo_error_heading",
          "algo_errors");
}
