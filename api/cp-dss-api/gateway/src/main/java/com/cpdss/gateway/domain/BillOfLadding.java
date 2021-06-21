/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author jerin.g */
@Data
public class BillOfLadding {
  private Long id;
  private Long portId;
  private Long cargoId;
  private BigDecimal quantityBbls;
  private BigDecimal quantityMt;
  private BigDecimal quantityKl;
  private BigDecimal api;
  private BigDecimal temperature;
}
