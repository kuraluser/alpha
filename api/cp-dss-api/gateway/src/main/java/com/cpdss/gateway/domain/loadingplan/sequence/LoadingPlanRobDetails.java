/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LoadingPlanRobDetails {

  private Long tankId;
  private String tankName;
  private String quantityMT;
  private String quantityM3;
  private Integer conditionType;
  private Integer valueType;
  private String colorCode;
  private BigDecimal density;
}
