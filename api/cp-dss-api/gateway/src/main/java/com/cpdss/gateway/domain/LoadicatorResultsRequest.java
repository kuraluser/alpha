/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadicatorResultsRequest {
  private Long portId;
  private BigDecimal hog;

  private BigDecimal calculatedDraftFwdPlanned;

  private BigDecimal calculatedDraftFwdActual;

  private BigDecimal calculatedDraftMidPlanned;

  private BigDecimal calculatedDraftMidActual;

  private BigDecimal calculatedDraftAftPlanned;

  private BigDecimal calculatedDraftAftActual;

  private BigDecimal calculatedTrimPlanned;

  private BigDecimal calculatedTrimActual;

  private BigDecimal blindSector;

  private Long loadablePatternId;

  private BigDecimal ballastActual;

  private BigDecimal list;
}
