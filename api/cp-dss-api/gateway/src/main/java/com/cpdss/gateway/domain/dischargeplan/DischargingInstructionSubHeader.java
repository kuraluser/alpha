/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author sajith.m
 *
 */
@Data
@AllArgsConstructor
public class DischargingInstructionSubHeader {

  private Long instructionTypeId;
  private Long instructionHeaderId;
  private Long subHeaderId;
  private String subHeaderName;
  private Boolean isChecked;
  private Boolean isEditable;
  private Boolean isSingleHeader;
  private List<DischargingInstructions> dischargingInstructionsList;
}
