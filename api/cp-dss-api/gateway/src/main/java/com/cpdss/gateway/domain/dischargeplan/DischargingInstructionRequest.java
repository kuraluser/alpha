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
public class DischargingInstructionRequest {

  private Long headerId;
  private Long instructionTypeId;
  private String newSubheading;
  private Boolean isChecked;
}
