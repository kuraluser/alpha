/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanSynopticalRecord {
  private Long id;
  private String operationType;
  private Long portId;
  private String portName;
  private String etaEtdPlanned;
  private BigDecimal plannedFOTotal;
  private BigDecimal plannedDOTotal;
  private BigDecimal plannedFWTotal;
  private BigDecimal othersPlanned;
  private BigDecimal totalDwtPlanned;
  private BigDecimal displacementPlanned;
  private BigDecimal specificGravity; // density

  private BigDecimal finalDraftFwd;
  private BigDecimal finalDraftAft;
  private BigDecimal finalDraftMid;
  private BigDecimal calculatedTrimPlanned;
  private BigDecimal cargoPlannedTotal;
  private BigDecimal ballastPlanned;
  private Long portTimezoneId;
}
