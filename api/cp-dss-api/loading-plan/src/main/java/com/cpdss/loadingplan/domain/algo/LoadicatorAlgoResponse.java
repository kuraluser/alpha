/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import java.util.List;
import lombok.Data;

@Data
public class LoadicatorAlgoResponse {

  private String processId;
  private Long loadingInformationId;
  private Long vesselId;
  private Long portId;
  private List<LoadicatorResult> loadicatorResults;
}
