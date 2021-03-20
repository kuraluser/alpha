/* Licensed at AlphaOri Technologies */
package com.cpdss.loadableplan.domain;

import java.math.BigDecimal;
import lombok.Data;

/**
 * DTO for loadable study port rotation
 *
 * @author jerin.g
 */
@Data
public class LoadableStudyPortRotation {

  private Long id;

  private Long loadableStudyId;

  private Long portId;

  private Long berthId;

  private Long operationId;

  private BigDecimal seaWaterDensity;

  private BigDecimal distanceBetweenPorts;

  private BigDecimal timeOfStay;

  private BigDecimal maxDraft;

  private BigDecimal maxAirDraft;

  private String eta;

  private String etd;

  private String layCanFrom;

  private String layCanTo;

  private Long portOrder;
}
