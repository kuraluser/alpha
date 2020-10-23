/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadableQuantityResponse {
  private CommonSuccessResponse responseStatus;
  private Long loadableQuantityId; 
  private String message;
  private LoadableQuantity loadableQuantity;
}
