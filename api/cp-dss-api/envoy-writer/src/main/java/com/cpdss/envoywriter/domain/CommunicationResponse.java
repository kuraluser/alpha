/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.domain;

import lombok.Data;

@Data
public class CommunicationResponse {
  private String message;
  private String messageId;
  private String shipId;
}
