/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class OnHandQuantity {
  private Long id;
  private Long portId;
  private Long fueltypeId;
  private Long tankId;
  private String arrivalVolume;
  private String arrivalQuantity;
  private String departureVolume;
  private String departureQuantity;
}
