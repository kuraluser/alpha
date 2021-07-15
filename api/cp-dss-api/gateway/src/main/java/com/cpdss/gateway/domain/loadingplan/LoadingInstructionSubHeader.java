/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/** @Author jerin.g */
@Data
@AllArgsConstructor
public class LoadingInstructionSubHeader {

  private Long instructionTypeId;
  private Long instructionHeaderId;
  private Long subHeaderId;
  private String subHeaderName;
  private Boolean isChecked;
  private List<LoadingInstructions> loadingInstructionsList;
  
}
