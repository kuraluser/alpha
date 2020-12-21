/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class SynopticalRecord {

  private Long id;
  private String operationType;
  private BigDecimal distance;
  private BigDecimal speed;
  private BigDecimal runningHours;
  private BigDecimal inPortHours;
  private String etaPlanned;
  private String etaActual;
  private String etdPlanned;
  private String etdActual;
  private String timeOfSunrise;
  private String timeOfSunset;
  private BigDecimal hwTideFrom;
  private BigDecimal hwTideTo;
  private String hwTideTimeFrom;
  private String hwTideTimeTo;
  private BigDecimal lwTideFrom;
  private BigDecimal lwTideTo;
  private String lwTideTimeFrom;
  private String lwTideTimeTo;
  private BigDecimal specificGravity;
  private Long portId;
  private String portName;
}
