/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class AlgoErrorResponse {
  private List<AlgoError> algoErrors;
  private CommonSuccessResponse responseStatus;
}
