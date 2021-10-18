/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.filerepo;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.Data;

@Data
public class FileRepoReply {
  private long id;
  private CommonSuccessResponse responseStatus;
}
