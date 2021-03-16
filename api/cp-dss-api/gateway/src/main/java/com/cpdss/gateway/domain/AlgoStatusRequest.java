/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class AlgoStatusRequest {
  private String processId;
  private Long loadableStudyStatusId;
}
