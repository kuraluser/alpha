/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.util.List;
import lombok.Data;

/** @author sajith.m */
@Data
public class DischargingInstructionResponse {
  private List<DischargingInstructionSubHeader> dischargingInstructionSubHeader;
  private List<DischargingInstructionGroup> dischargingInstructionGroupList;
}
