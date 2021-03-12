/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class StationValues {
  private Long id;
  private String stationFrom;
  private String stationTo;
  private String frameNumberFrom;
  private String frameNumberTo;
  private String distance;
}
