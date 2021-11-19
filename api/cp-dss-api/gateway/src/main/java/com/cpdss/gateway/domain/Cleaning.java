package com.cpdss.gateway.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Cleaning {
	
	@JsonProperty("TopClean")
	private List<CleaningTankDetails> topClean;
	
	@JsonProperty("BtmClean")
	private List<CleaningTankDetails> btmClean;
	
	@JsonProperty("FullClean")
	private List<CleaningTankDetails> fullClean;

}
