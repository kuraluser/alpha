/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class OnHandQuantity {
  private Long id;
  private Long portXId;
  private Long fuelTypeXId;
  private Long tankXId;
  private String arrivalVolume;
  private String arrivalQuantity;
  private String departureVolume;
  private String departureQuantity;
}
