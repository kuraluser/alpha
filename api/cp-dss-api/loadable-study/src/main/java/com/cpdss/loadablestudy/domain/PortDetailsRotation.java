/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author ravi.g */
@Data
public class PortDetailsRotation {
  private Long id;
  private String name;
  private String code;
  private String averageTideHeight;
  private String tideHeight;
  private String densitySeaWater;
  private String countryName;
  private String latitude;
  private String longitude;
}
