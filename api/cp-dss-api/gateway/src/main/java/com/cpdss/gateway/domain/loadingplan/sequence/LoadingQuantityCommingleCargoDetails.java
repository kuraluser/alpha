/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import lombok.Data;

/** @author pranav.k */
@Data
public class LoadingQuantityCommingleCargoDetails {

  private String tankName;
  private Long tankId;
  private String quantityMT;
  private String quantityM3;
  private String api;
  private String temperature;
  private String ullage;
  private Long cargoNomination1Id;
  private Long cargoNomination2Id;
  private Long cargo1Id;
  private Long cargo2Id;
  private String colorCode;
  private String abbreviation;
}
