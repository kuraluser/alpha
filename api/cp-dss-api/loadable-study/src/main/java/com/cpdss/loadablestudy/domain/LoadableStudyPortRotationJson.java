/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * DTO for loadable study port rotation
 *
 * @author jerin.g
 */
@Data
public class LoadableStudyPortRotationJson {

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

  private Long portOrder;
  
  private List<?> cowDetails;
  
  private List<?> instructions;
  
}
