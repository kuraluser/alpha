/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import lombok.Data;

@Data
public class LoadingPlanBallastDetails {

  private Long tankId;
  private String tankName;
  private String quantityMT;
  private String quantityM3;
  private String sounding;
  private Integer conditionType;
  private Integer valueType;
}
