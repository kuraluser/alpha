/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author sajith.m
 *
 */
@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class DischargingInstructionsSaveResponse {

  private CommonSuccessResponse responseStatus;
}
