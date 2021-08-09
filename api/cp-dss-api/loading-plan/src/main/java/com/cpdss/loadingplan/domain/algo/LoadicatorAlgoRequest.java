/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import lombok.Data;

@Data
public class LoadicatorAlgoRequest {

  private String processId;
  private Long loadingInformationId;
  private Long vesselId;
  private Long portId;
  private java.util.List<LoadicatorStage> stages;
}
