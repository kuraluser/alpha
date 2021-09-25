/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

@Data
public class ListOfUllageReportResponse {

  private CommonSuccessResponse responseStatus;
  List<UllageReportImportResponse> ullageReportResponse;
}
