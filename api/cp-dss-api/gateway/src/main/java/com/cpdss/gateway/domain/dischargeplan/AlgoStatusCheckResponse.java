/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

@Data
public class AlgoStatusCheckResponse {

  private Long dischargeInfoStatusId;
  private String dischargeInfoStatusLastModifiedTime;
  private CommonSuccessResponse responseStatus;
}
