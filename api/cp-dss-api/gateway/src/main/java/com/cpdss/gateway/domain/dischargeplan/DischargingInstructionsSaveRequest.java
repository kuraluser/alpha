/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * 
 * @author sajith.m
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class DischargingInstructionsSaveRequest {
  private Long instructionHeaderId;
  private Long instructionTypeId;
  private Boolean isChecked;
  private Boolean isSingleHeader;
  private Long subHeaderId;
  private String instruction;
  private Boolean isSubHeader;
}
