/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

@Data
public class PortDetailResponse {

  private CommonSuccessResponse responseStatus;

  private PortDetails portDetails;
}
