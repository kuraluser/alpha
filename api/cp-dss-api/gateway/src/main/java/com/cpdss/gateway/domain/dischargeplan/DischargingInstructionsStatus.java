/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import lombok.Data;

/** @author sajith.m */
@Data
public class DischargingInstructionsStatus {
  private Long instructionId;
  private String instruction;
  private Boolean isChecked;
}
