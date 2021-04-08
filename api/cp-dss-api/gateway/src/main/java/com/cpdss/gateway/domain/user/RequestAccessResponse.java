/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.user;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Request Access Response Object */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestAccessResponse {
  private CommonSuccessResponse responseStatus;
  private String userId;
  private int rejectionCount;
  private Long status;
  private String statusMessage;
}
