/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(Include.NON_NULL)
public class LoadingInstructionsSaveRequest {
    private Long instructionHeaderId;
    private Long instructionTypeId;
    private Boolean isChecked;
    private Boolean isSingleHeader;
    private Long subHeaderId;
    private String instruction;
	private Boolean isSubHeader;
}
