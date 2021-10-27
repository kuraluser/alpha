/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusCheckResponse {
  private String statusCode;
  private String eventUploadStatus;
  private String eventDownloadStatus;
  private String message;
  private String messageId;
}
