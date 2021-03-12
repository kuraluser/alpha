/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

/** @Author jerin.g */
@Data
public class AlgoPatternResponse {
  private CommonSuccessResponse responseStatus;
  private String processId;
}
