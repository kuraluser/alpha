/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class StabilityConditions {
  private BigDecimal list;
  private BigDecimal hogSag;
  private BigDecimal finalDraftFwd;
  private BigDecimal finalDraftAft;
  private BigDecimal finalDraftMid;
  private BigDecimal calculatedDraftFwdActual;
  private BigDecimal calculatedDraftAftActual;
  private BigDecimal calculatedDraftMidActual;
  private BigDecimal calculatedTrimActual;
}
