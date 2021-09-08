/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.DischargeStudy;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

/** @Author arun.j */
@Data
public class DischargeStudyStatusResponse {
  private Long dischargeStudyStatusId;
  private String dischargeStudyStatusLastModifiedTime;
  private CommonSuccessResponse responseStatus;
}
