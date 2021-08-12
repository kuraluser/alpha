/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

/** @author pranav.k */
@Data
public class LoadingInfoAlgoStatus {

  private Long loadingInfoStatusId;
  private String loadingInfoStatusLastModifiedTime;
  private CommonSuccessResponse responseStatus;
}
