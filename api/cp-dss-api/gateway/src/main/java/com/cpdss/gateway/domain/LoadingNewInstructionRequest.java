/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(Include.NON_NULL)
public class LoadingNewInstructionRequest {

  private Long subHeaderId;
  private String newInstruction;
  private Boolean isChecked;
}
