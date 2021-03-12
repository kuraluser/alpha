/* Licensed at AlphaOri Technologies */
package com.cpdss.loadableplan.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class CargoNominationOperationDetails {
  private Integer id;
  private Integer cargoNominationXId;
  private Integer portXId;
  private String portCode;
  private Integer operationXId;
  private String quantity;
}
