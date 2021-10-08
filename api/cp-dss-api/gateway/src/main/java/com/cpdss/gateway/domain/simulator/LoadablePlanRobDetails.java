/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.simulator;

import lombok.Data;

/** @Author gokul.p */
@Data
public class LoadablePlanRobDetails {
  private Double quantity;
  private Double quantityM3;
  private Double sg;
  private Long tankId;
  private String tankName;
  private String tankShortName;
}
