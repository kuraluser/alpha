/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(Include.NON_NULL)
public class UpdateUllage {

  private CommonSuccessResponse responseStatus;

  private Long id;

  private Long tankId;

  private BigDecimal correctedUllage;

  private BigDecimal correctionFactor;

  private BigDecimal quantityMt;

  private Boolean isBallast;

  private String fillingRatio;

  private String api;

  private String temperature;

  private String observedM3;

  private String sg;
}
