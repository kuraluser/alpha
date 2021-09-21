/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class UllageReportImportResponse {

  private Long tankId;
  private String tank;
  private Double ullageObserved;
  private Double api;
  private Double temperature;
  private Double weight;
  private Long cargoNominationId;
}
