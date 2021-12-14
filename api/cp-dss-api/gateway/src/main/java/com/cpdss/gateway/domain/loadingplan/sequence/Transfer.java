/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;
import lombok.Data;

@Data
public class Transfer {

  private List<Long> fromTankId;
  private List<Long> toTankId;
  private String timeStart;
  private String timeEnd;
  private Long cargoNominationId;
  private String startQuantity;
  private String endQuantity;
  private String startUllage;
  private String endUllage;
  private String purpose;
}
