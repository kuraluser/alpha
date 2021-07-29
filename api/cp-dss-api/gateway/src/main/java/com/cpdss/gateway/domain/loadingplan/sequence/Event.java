/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import java.util.List;
import lombok.Data;

@Data
public class Event {

  private Long cargoNominationId;
  private List<Sequence> sequence;
}
