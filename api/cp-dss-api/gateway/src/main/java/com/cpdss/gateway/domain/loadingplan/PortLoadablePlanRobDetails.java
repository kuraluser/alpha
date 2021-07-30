/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.Data;

@Data
public class PortLoadablePlanRobDetails {
  private Long id;
  private boolean isActive;
  private Long loadablePatternId;
  private Long tankId;
  private String tankname;
  private String quantity;
  private String actualPlanned;
  private String arrivalDeparture;
}
