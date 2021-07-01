/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.DischargeStudy;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

@Data
public class DischargeStudyUpdateResponse {
  private CommonSuccessResponse responseStatus;
  private DischargeStudyValue dischargeStudy;
}
