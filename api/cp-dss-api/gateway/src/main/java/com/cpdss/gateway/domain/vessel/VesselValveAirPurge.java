/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import lombok.Data;

@Data
public class VesselValveAirPurge {
  private String id;
  private Long vesselId;
  private String vesselName;
  private String shortname;
  private Long tankId;
  private Long pumpId;
  private String pumpCode;
  private int sequenceNumber;
  private String valveNumber;
  private int valveId;
  private boolean isShut;
  private boolean isCopWarmup;
}
