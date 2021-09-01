/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class LoadingDelays {

  private Long id;
  private Long loadingInfoId;
  private List<Long> reasonForDelayIds;
  private BigDecimal duration;
  private Long cargoId;
  private Long cargoNominationId;
  private BigDecimal quantity;
}
