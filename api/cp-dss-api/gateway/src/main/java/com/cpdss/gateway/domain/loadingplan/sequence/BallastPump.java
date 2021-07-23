/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BallastPump {
  private Long pumpId;
  private Long start;
  private Long end;
  private BigDecimal quantityM3;
  private BigDecimal rate;
}
