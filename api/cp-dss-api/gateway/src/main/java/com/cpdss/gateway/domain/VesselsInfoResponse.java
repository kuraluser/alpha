package com.cpdss.gateway.domain;

import java.util.List;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class VesselsInfoResponse {
	
	  private CommonSuccessResponse responseStatus;

	  private List<VesselInformation> vesselsInfo;
	  
	  private Long totalElements;

}
