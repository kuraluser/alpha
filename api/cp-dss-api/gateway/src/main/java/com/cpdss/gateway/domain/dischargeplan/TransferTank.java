/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Data;

/** @author pranav.k */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferTank {
  private Long tankId;
  private String shortName;
  private String frameNumberFrom;
  private String frameNumberTo;
  private String tankName;
  private BigDecimal startQuantity;
  private BigDecimal endQuantity;
  private BigDecimal startUllage;
  private BigDecimal endUllage;
}
