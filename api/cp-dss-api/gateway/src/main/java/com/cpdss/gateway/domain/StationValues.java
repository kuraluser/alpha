/* Licensed under Apache-2.0 */
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
