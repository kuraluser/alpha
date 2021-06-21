/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author jerin.g */
@Data
public class BillOfLaddingDto {
  /**
   * @param sumQuantityMt
   * @param sumQuantityKL
   * @param sumQuantityBbls
   */
  public BillOfLaddingDto(
      BigDecimal sumQuantityMt, BigDecimal sumQuantityKL, BigDecimal sumQuantityBbls) {
    // TODO Auto-generated constructor stub
  }

  private Long Id;
  private Long cargoId;

  private Long portId;

  private BigDecimal quantityBbls;

  private BigDecimal quantityKl;

  private BigDecimal api;

  private BigDecimal temperature;
}
