/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

import java.math.BigDecimal;

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
  private BigDecimal maxDraft;
}
