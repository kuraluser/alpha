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

  @Getter
  @AllArgsConstructor
  public enum VESSEL_INFO_COLUMNS {
    RULE_TEMPLATE_XID("rule_template_xid"),
    VESSEL_XID("vessel_xid"),
    RULETYPE_XID("ruletype_xid"),
    RULE_VESSEL_MAPPING_XID("rule_vessel_mapping_xid");

    private final String columnName;
  }
}
