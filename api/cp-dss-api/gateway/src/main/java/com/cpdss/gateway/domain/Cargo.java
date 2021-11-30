/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Data;

@Data
// @JsonInclude(Include.NON_NULL)
public class Cargo {
  private Long id;
  private String name;
  private String abbreviation;
  private String api;
  private String temp;
  private String companyId;
  private BigDecimal plannedWeight;
  private BigDecimal actualWeight;
  private Long dischargeTime;
  private Long lpCargoDetailsId;
  private Long planQtyId;
  private Integer planQtyCargoOrder;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String colorCode;
}
