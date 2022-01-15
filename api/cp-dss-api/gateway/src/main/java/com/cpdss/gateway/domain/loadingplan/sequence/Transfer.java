/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Transfer {

  private List<Long> fromTankId;
  private List<Long> toTankId;
  private String timeStart;
  private String timeEnd;
  private Long cargoNominationId;
  private Map<String, String> startQuantity;
  private Map<String, String> endQuantity;
  private Map<String, String> startUllage;
  private Map<String, String> endUllage;
  private String purpose;
}
