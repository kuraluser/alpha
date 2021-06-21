/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

@Data
public class LoadingInformationResponse {

  private CommonSuccessResponse responseStatus;
  private LoadingInformation loadingInformation;
}
