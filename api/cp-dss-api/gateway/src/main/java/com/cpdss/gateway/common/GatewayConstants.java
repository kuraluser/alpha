/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.common;

import com.cpdss.common.generated.VesselInfo;
import java.util.*;

public class GatewayConstants {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  public static final Long LOADING_RULE_MASTER_ID = 2l;

  public static final Long LOADING_INFORMATION_RESPONSE_JSON_ID = 10L;
  public static final List<Long> LOADING_VESSEL_PUMPS_VAL =
      Arrays.asList(
          (long) VesselInfo.VesselPumpTypes.BALLAST_PUMP_VALUE,
          (long) VesselInfo.VesselPumpTypes.GS_PUMP_VALUE,
          (long) VesselInfo.VesselPumpTypes.IG_PUMP_VALUE,
          (long) VesselInfo.VesselPumpTypes.BALLAST_EDUCTOR_VALUE);

  public static final List<Long> DISCHARGING_VESSEL_PUMPS_VAL =
      Arrays.asList(
          (long) VesselInfo.VesselPumpTypes.CARGO_PUMP_VALUE,
          (long) VesselInfo.VesselPumpTypes.TANK_CLEANING_PUMP_VALUE,
          (long) VesselInfo.VesselPumpTypes.STRIPPING_PUMP_VALUE,
          (long) VesselInfo.VesselPumpTypes.BALLAST_PUMP_VALUE,
          (long) VesselInfo.VesselPumpTypes.GS_PUMP_VALUE,
          (long) VesselInfo.VesselPumpTypes.IG_PUMP_VALUE);
}
