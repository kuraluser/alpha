/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BerthInformation {

	private String berthName;
	private String hoseConnection;
	private String maxManifoldPressure;
	private BigDecimal airDraftLimitation;
	private String airDraft;
	private Boolean airPurge;
	private String specialRegulation;
	private String itemsAgreedWithTerminal;
}
