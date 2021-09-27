/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import lombok.Data;

@Data
public class CowHistory {
  private Long id;
  private Long vesselId;
  private Long voyageId;
  private Long portId;
  private Long tankId;
  private String cowOptionType;
  private String voyageEndDate;
}
