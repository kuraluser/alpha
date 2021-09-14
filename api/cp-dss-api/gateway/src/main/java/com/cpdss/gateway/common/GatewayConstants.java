/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.common;

import com.cpdss.common.generated.VesselInfo;
import java.util.*;

public class GatewayConstants {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  public static final Long LOADING_RULE_MASTER_ID = 2l;
  public static final Long DISCHARGING_RULE_MASTER_ID = 3l;

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

  public static final List<Long> OPERATIONS_TANK_TYPE =
      Arrays.asList(
          (long) VesselInfo.TankTypeForOperations.PORT_VALUE,
          (long) VesselInfo.TankTypeForOperations.STBD_VALUE);

  public static final String STATUS_ACTIVE = "ACTIVE";
  public static final String STATUS_CONFIRMED = "CONFIRMED";
  public static final String STATUS_CLOSE = "CLOSED";

  public static final String OPERATION_TYPE_ARR = "ARR";
  public static final String OPERATION_TYPE_DEP = "DEP";

  public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

  public static final String LOADABLE_STUDY_SAVE_REQUEST = "LS_SAVE";
  public static final String DISCHARGE_STUDY_SAVE_REQUEST = "DS_SAVE";
  public static final String LOADABLE_STUDY = "LS";
  public static final String DICHARGE_STUDY = "DS";
}
