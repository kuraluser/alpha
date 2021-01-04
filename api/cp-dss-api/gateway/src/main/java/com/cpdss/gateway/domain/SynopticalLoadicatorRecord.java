/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SynopticalLoadicatorRecord {

  private String hogSag;
  private BigDecimal finalDraftFwd;
  private BigDecimal finalDraftAft;
  private BigDecimal finalDraftMid;
  private BigDecimal calculatedDraftFwdPlanned;
  private BigDecimal calculatedDraftFwdActual;
  private BigDecimal calculatedDraftAftPlanned;
  private BigDecimal calculatedDraftAftActual;
  private BigDecimal calculatedDraftMidPlanned;
  private BigDecimal calculatedDraftMidActual;
  private BigDecimal calculatedTrimPlanned;
  private BigDecimal calculatedTrimActual;
  private BigDecimal blindSector;
}
