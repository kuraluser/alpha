/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class UpdateUllageCargoQuantityDetail {
  private Long cargoId;
  private String cargoColor;
  private String cargoName;
  private String cargoAbbrevation;
  private Long cargoNominationId;
  private Double nominationTotal;
  private Double maxTolerance;
  private Double minTolerance;
  private Double maxQuantity;
  private Double minQuantity;
  private Double plannedQuantityTotal;
  private Double actualQuantityTotal;
  private Double blQuantityMTTotal;
  private Double blQuantityBblsTotal;
  private Double blQuantityKLTotal;
  private Double blQuantityLTTotal;
  private Double difference;
  private String nominationApi;
  private String nominationTemp;
  private String actualAvgApi;
  private String actualAvgTemp;
  private String blAvgApi;
  private String blAvgTemp;
}
