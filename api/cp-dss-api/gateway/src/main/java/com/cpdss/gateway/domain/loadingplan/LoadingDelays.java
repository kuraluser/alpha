/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

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
  private Long sequenceNo;
  private BigDecimal loadingRate;
  private BigDecimal dischargingRate;
}
