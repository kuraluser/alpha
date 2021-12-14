/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.Cargo;
import com.cpdss.gateway.domain.Tank;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class TransferDetail {

  private List<Tank> fromTanks;
  private Tank toTank;
  private Long start;
  private Long end;
  private BigDecimal startQuantity;
  private BigDecimal endQuantity;
  private BigDecimal startUllage;
  private BigDecimal endUllage;
  private Cargo cargo;
}
