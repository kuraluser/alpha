/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(Include.NON_NULL)
public class LoadableQuantityCargoDetails {
	@JsonInclude(Include.NON_NULL)
	private Long id;

	@JsonInclude(Include.NON_NULL)
	private String grade;

	private String estimatedAPI;
	private String estimatedTemp;

	@JsonInclude(Include.NON_NULL)
	private String orderBblsdbs;

	@JsonInclude(Include.NON_NULL)
	private String orderBbls60f;

	private String minTolerence;
	private String maxTolerence;

	@JsonInclude(Include.NON_NULL)
	private String loadableBblsdbs;

	@JsonInclude(Include.NON_NULL)
	private String loadableBbls60f;

	@JsonInclude(Include.NON_NULL)
	private String loadableLT;

	private String loadableMT;

	@JsonInclude(Include.NON_NULL)
	private String loadableKL;

	private String differencePercentage;

	@JsonInclude(Include.NON_NULL)
	private String differenceColor;

	private Long cargoId;
	private String orderedQuantity;
	private String cargoAbbreviation;
	private String colorCode;
	private Integer priority;
	private Integer loadingOrder;
	private Long cargoNominationId;

	private String slopQuantity;
	private String timeRequiredForLoading;

	@JsonInclude(Include.NON_NULL)
	private List<String> loadingPorts;

	private List<CargoToppingOffSequence> toppingSequence;
	private String cargoNominationTemperature;

	private String cargoNominationQuantity;
	private String orderQuantity;
	private String maxLoadingRate = BigDecimal.ZERO.toString();

	// additional fields in DS
	private String dischargeMT;
	private String timeRequiredForDischarging;
	private String dischargingRate;
	private List<Tank> cowDetails;

}
