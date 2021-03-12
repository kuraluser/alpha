/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

@Data
public class LoadableStudyAttachmentResponse {
  private CommonSuccessResponse responseStatus;
  private String filePath;
}
