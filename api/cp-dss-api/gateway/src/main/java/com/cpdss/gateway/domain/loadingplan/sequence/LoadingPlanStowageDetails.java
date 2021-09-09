/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import lombok.Data;

@Data
public class LoadingPlanStowageDetails {

  private Long tankId;
  private String tankName;
  private Long cargoNominationId;
  private String quantityMT;
  private String quantityM3;
  private String api;
  private String temperature;
  private String ullage;
  private Integer conditionType;
  private Integer valueType;
  private String colorCode;
  private String abbreviation;
  private Long cargoId;
}
