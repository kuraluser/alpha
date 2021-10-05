/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import lombok.Data;

/** @Author vinothkumar M */
@Data
public class SynopticalTableLoadicatorDataDto {

  private Long id;

  private BigDecimal deflection;

  private BigDecimal calculatedDraftFwdPlanned;

  private BigDecimal calculatedDraftFwdActual;

  private BigDecimal calculatedDraftMidPlanned;

  private BigDecimal calculatedDraftMidActual;

  private BigDecimal calculatedDraftAftPlanned;

  private BigDecimal calculatedDraftAftActual;

  private BigDecimal calculatedTrimPlanned;

  private BigDecimal calculatedTrimActual;

  private BigDecimal blindSector;

  private boolean isActive;

  private Long loadablePatternId;

  private BigDecimal ballastActual;

  private SynopticalTableDto synopticalTable;

  private BigDecimal list;

  private Long portId;

  private Long operationId;

  private BigDecimal bendingMoment;

  private BigDecimal shearingForce;
}
