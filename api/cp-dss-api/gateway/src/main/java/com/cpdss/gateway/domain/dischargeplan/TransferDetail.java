/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.gateway.domain.Cargo;
import java.util.List;
import lombok.Data;

@Data
public class TransferDetail {

  private List<TransferTank> fromTanks;
  private TransferTank toTank;
  private Long start;
  private Long end;
  private Cargo cargo;
}
