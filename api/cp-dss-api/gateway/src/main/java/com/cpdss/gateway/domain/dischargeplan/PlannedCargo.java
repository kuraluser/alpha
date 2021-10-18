package com.cpdss.gateway.domain.dischargeplan;

import java.util.List;

import com.cpdss.gateway.domain.DischargeQuantityCargoDetails;

import lombok.Data;

@Data
public class PlannedCargo {
	
	private Boolean dischargeSlopTanksFirst ; 
	private Boolean dischargeCommingledCargoSeparately ;
	private List<DischargeQuantityCargoDetails> dischargeQuantityCargoDetails;

}
