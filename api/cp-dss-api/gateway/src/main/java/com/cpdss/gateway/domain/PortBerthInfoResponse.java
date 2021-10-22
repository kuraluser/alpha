package com.cpdss.gateway.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PortBerthInfoResponse {
	
	  private Long berthId;
	  private Long portId;
	  private String berthName;
	  private BigDecimal maxShipDepth;
	  private BigDecimal depthInDatum;
	  private BigDecimal maxDwt;
	  private BigDecimal maxLoa;
	  private BigDecimal maxManifoldHeight;
	  private String minUKC;
	  private String regulationAndRestriction;

}
