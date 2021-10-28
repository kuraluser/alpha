package com.cpdss.gateway.domain;

import java.util.List;

import com.cpdss.common.rest.CommonSuccessResponse;

import lombok.Data;

@Data
public class CountrysResponse {
	
	private List<CountryInfo> countrys;
	
	private CommonSuccessResponse responseStatus;

}
