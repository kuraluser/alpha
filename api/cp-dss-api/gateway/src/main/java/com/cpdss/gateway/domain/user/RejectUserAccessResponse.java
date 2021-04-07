/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.user;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Reject User Access Response object */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RejectUserAccessResponse {
  private CommonSuccessResponse commonSuccessResponse;
  private Long userId;
  private int rejectionCount;
  private Long status;
  private String statusMessage;
}
