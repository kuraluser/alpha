package com.cpdss.gateway.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@Builder
public class LatestApiTempCargoResponse {
	
	private Long vesselId;
	private Long cargoId;
	private Long loadingPortId;
    private String api;
    private String temperature;
	  

}
