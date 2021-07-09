/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

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
