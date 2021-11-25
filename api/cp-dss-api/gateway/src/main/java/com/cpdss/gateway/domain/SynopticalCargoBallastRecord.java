/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
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

  private Long cargo1NominationId;

  private Long cargo2NominationId;

  private String cargo1Lt;

  private String cargo2Lt;

  private String cargo1Mt;

  private String cargo2Mt;

  private String cargo1Abbreviation;

  private String cargo2Abbreviation;
}
