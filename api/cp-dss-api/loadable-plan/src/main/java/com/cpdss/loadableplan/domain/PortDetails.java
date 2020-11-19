/* Licensed under Apache-2.0 */
package com.cpdss.loadableplan.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class PortDetails {
  private Integer id;
  private String name;
  private String code;
  private Integer timeZoneXId;
  private String tideHeightTo;
  private String tideHeightFrom;
  private String densitySeaWaterTo;
  private String densitySeaWaterFrom;
  private String maximumLOA;
  private String maximumDWT;
  private String averageTideHeight;
  private String tideHeight;
  private String densitySeaWater;
  private String maxPermissibleShipDraftChannel;
  private String ambientTemperature;
}
