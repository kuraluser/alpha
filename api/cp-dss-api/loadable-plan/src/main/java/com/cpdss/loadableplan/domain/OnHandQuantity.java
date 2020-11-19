/* Licensed under Apache-2.0 */
package com.cpdss.loadableplan.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class OnHandQuantity {
  private Integer id;
  private Integer loadablestudyXId;
  private Integer portXId;
  private String portCode;
  private Integer fueltypXId;
  private Integer tankXId;
  private String arrivalVolume;
  private String arrivalQuantity;
  private String departureVolume;
  private String departureQuatity;
}
