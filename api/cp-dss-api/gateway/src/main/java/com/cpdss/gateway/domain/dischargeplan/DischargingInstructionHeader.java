/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import java.util.List;
import lombok.Data;

/** @author sajith.m */
@Data
public class DischargingInstructionHeader {

  private String headerName;

  private Boolean isHeaderChecked;

  List<DischargingInstructions> dischargingInstructionlist;
}
