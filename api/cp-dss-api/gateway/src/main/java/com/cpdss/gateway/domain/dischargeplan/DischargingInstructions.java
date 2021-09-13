/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author sajith.m
 *
 */
@Data
@AllArgsConstructor
public class DischargingInstructions {

  private Long instructionTypeId;
  private Long instructionHeaderId;
  private Long instructionId;
  private String instruction;
  private Boolean isChecked;
  private Boolean isEditable;
}
