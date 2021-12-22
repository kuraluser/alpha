/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class DischargeDelays {

  private Long id;
  private Long sequenceNumber;
  private Long dischargeInfoId;
  private List<Long> reasonForDelayIds;
  private BigDecimal duration;
  private Long cargoId;
  private Long dsCargoNominationId;
  private BigDecimal quantity;
}
