/* Licensed at AlphaOri Technologies */
package com.cpdss.common.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for file repo responses */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRepoReply {

  private long id;
  private CommonSuccessResponse responseStatus;
  private String filePath;
}
