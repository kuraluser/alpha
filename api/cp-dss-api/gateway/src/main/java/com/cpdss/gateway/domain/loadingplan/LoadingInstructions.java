/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.AllArgsConstructor;
import lombok.Data;

/** @Author jerin.g */
@Data
@AllArgsConstructor
public class LoadingInstructions {

  private Long instructionTypeId;
  private Long instructionHeaderId;
  private Long instructionId;
  private String instruction;
  private Boolean isChecked;
  private Boolean isEditable;
}
