/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.vessel;

import lombok.Data;

@Data
public class VesselValveStrippingSequenceCargoValve {
  private Long id;
  private Long vesselId;
  private String vesselName;
  private Integer pipeLineId;
  private String pipeLineName;
  private String colour;
  private Integer valveId;
  private String valve;
  private Integer sequenceNumber;
}
