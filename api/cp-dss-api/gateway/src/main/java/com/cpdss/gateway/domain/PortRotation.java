/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

/**
 * DTO for loadable study port rotation
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class PortRotation {

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