/* Licensed at AlphaOri Technologies */
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
  private Boolean isSummerZone;

  private Integer caseNo;

  private String selectedZone;
}
