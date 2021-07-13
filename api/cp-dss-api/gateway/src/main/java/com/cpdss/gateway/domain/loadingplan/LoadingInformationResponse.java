/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

@Data
public class LoadingInformationResponse {
  private Long loadingInfoId;
  private Long vesseld;
  private Long voyageId;
  private Long portRotationId;
  private Long synopticalTableId;
  private CommonSuccessResponse responseStatus;
  private LoadingInformation loadingInformation;
}
