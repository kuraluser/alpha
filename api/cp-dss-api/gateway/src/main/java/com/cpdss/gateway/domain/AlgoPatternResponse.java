/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

/** @Author jerin.g */
@Data
public class AlgoPatternResponse {
  private CommonSuccessResponse responseStatus;
  private String processId;
}
