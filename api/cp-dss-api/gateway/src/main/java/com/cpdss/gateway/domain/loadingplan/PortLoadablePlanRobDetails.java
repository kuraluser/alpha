/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortLoadablePlanRobDetails {
  private Long id;
  private boolean isActive;
  private Long loadablePatternId;
  private Long tankId;
  private String tankName;
  private String tankShortName;
  private String quantity;
  private String actualPlanned;
  private String arrivalDeparture;
  private BigDecimal density;
  private String colorCode;
}
