/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import lombok.Data;

@Data
public class LoadingPlanRobDetails {

  private Long tankId;
  private String tankName;
  private String quantityMT;
  private String quantityM3;
}