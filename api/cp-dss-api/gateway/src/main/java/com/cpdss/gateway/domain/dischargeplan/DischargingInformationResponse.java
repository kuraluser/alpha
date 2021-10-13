/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

@Data
public class DischargingInformationResponse {
  private Long dischargingInfoId;
  private Long vesseld;
  private Long voyageId;
  private Long portRotationId;
  private Long synopticalTableId;
  private CommonSuccessResponse responseStatus;
  private DischargeInformation dischargingInformation;
}
