/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class AlgoStatusRequest {
  private String processId;
  private Long loadableStudyStatusId;
}
