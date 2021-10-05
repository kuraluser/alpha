/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class LoadablePlanQuantityDto {
  private Long id;

  private String grade;

  private Long cargoNominationId;

  private BigDecimal estimatedApi;

  private BigDecimal estimatedTemperature;

  private BigDecimal orderQuantity;

  private BigDecimal loadableQuantity;

  private String differencePercentage;

  private Boolean isActive;

  private String orderBblsDbs;

  private String orderBbls60f;

  private String minTolerence;

  private String maxTolerence;

  private String loadableBblsDbs;

  private String loadableBbls60f;

  private String loadableLt;

  private String loadableMt;

  private String loadableKl;

  private String differenceColor;

  private LoadablePatternDto loadablePattern;

  private Long cargoXId;

  private Integer priority;

  private String cargoAbbreviation;

  private String cargoColor;

  private Integer loadingOrder;

  private String slopQuantity;

  private String timeRequiredForLoading;

  private BigDecimal cargoNominationTemperature;
}
