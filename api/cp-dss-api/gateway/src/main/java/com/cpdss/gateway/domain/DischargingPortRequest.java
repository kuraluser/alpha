/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

@Data
public class DischargingPortRequest {

  private Long loadableStudyId;

  private List<Long> portIds;

  // DSS-3156, change cargo id to cargo nomination id
  private Long cargoNominationId;

  private Boolean isDischargingPortComplete;
}
