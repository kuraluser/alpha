/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class DischargingDelays {

  private Long id;
  private Long dischargeInfoId;
  private List<Long> reasonForDelayIds;
  private BigDecimal duration;
  private Long cargoId;
  private Long cargoNominationId;
  private BigDecimal quantity;
  private Long sequenceNo;
}
