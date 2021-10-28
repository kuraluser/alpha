/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

/** @author pranav.k */
@Data
public class DischargingInfoAlgoStatus {
  private Long dischargingInfoStatusId;
  private String dischargingInfoStatusLastModifiedTime;
  private CommonSuccessResponse responseStatus;
}
