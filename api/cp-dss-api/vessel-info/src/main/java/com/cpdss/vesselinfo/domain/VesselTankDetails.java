/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VesselTankDetails {
  private Long tankId;
  private String shortName;
  private String tankName;
  private Integer tankDisplayOrder;
}
