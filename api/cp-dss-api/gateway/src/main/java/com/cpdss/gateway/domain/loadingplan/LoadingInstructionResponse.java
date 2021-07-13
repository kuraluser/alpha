/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.util.List;

import lombok.Data;

@Data
public class LoadingInstructionResponse {

	private Long instructionTypeId;
	
	private String instructionType;

	List<LoadingInstructionHeader> loadingInstructionList;
}
