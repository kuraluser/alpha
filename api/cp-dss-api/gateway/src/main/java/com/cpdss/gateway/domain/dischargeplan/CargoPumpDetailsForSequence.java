/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.loadingplan.sequence.TankWithSequenceDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CargoPumpDetailsForSequence {

  private Long id;
  private BigDecimal quantity;
  private String colorCode;
  private TankWithSequenceDetails cargoTankUllage;
}
