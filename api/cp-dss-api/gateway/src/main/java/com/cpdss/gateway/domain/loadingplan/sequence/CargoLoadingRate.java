package com.cpdss.gateway.domain.loadingplan.sequence;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class CargoLoadingRate {

	private Long startTime;
	private Long endTime;
	private List<BigDecimal> loadingRates;
}
