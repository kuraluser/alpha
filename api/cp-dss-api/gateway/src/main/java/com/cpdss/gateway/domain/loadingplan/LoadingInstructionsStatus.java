/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadingInstructionsStatus {
  private Long instructionId;
  private String instruction;
  private Boolean isChecked;
}
