/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class VesselInfoConstants {

  @Getter
  @AllArgsConstructor
  public enum VESSEL_INFO_TABLES {
    RULE_VESSEL_MAPPING("rule_vessel_mapping"),
    RULE_VESSEL_MAPPING_INPUT("rule_vessel_mapping_input");

    private final String tableName;
  }
}
