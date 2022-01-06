/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class CargoNominationOperationDetails {
  private Long id;
  private Long cargoNominationId;
  private Long dscargoNominationId;
  private Long portId;
  private String quantity;
  private Long sequenceNo;
  private Boolean emptyMaxNoOfTanks;
  private Long dischargingMode;
}
