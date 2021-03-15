/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class PortDetails {
  private Long id;
  private String name;
  private String code;
  private String averageTideHeight;
  private String tideHeight;
  private String densitySeaWater;
}
