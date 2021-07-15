/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

/** @Author jerin.g */
@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class LoadingInstructionsSaveResponse {

  private CommonSuccessResponse responseStatus;

}
