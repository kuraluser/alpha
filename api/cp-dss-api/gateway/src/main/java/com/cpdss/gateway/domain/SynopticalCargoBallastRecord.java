/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynopticalCargoBallastRecord {

  private Long lpCargoDetailId;

  private Long cargoNominationId;

  private Long tankId;

  private String tankName;

  private BigDecimal actualWeight;

  private BigDecimal plannedWeight;

  private BigDecimal capacity;

  private String abbreviation;

  private Long cargoId;

  private String colorCode;

  private BigDecimal correctedUllage;

  private BigDecimal api;

  private String sg;

  private Boolean isCommingleCargo;

  private String grade;

  private BigDecimal temperature = BigDecimal.ZERO;

  private String fillingRatio;

  private Long planQtyId;
  private Integer planQtyCargoOrder;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long dischargeCargoNominationId;
}
