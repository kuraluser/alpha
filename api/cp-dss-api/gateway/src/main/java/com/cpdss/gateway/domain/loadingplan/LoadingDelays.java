/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LoadingDelays {

  private Long id;
  private Long loadingInfoId;
  private Long reasonForDelayId;
  private BigDecimal duration;
  private Long cargoId;
  private BigDecimal quantity;
}
