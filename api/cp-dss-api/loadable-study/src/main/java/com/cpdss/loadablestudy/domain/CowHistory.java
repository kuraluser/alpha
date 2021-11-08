/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @author sanalkumar.k */
@Data
public class CowHistory {
  private Long id;
  private Long vesselId;
  private Long voyageId;
  private Long tankId;
  private String cowType;
  private Long cowTypeId;
  private Long portId;
  private String voyageEndDate;
}
