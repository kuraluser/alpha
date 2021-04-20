/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class UllageDetails {
  private Long id;
  private Long tankId;
  private String ullageDepth;
  private String evenKeelCapacityCubm;
  private String soundDepth;
}
