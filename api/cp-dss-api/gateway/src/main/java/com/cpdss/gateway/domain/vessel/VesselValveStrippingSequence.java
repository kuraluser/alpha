/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import lombok.Data;

@Data
public class VesselValveStrippingSequence {
  private String id;
  private Long vesselId;
  private String vesselName;
  private int pipeLineId;
  private String pipeLineName;
  private String colour;
  private int valveId;
  private String valve;
  private int sequenceNumber;

  private String manifoldName;
  private String manifoldSide;
}
