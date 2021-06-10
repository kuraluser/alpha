/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

/** @Author jerin.g */
@Data
public class ConfirmPlanStatusResponse {
  private Boolean confirmed;
  private CommonSuccessResponse responseStatus;
  private Long loadablePatternStatusId;
  private Boolean validated;
}
