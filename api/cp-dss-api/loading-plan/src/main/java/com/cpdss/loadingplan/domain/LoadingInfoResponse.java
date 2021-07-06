/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain;

import lombok.Data;

@Data
public class LoadingInfoResponse {

  private Long loadingInfoId;
  private Long vesselId;
  private Long voyageId;
  private Long portRotationId;
  private Long synopticalTableId;
}
