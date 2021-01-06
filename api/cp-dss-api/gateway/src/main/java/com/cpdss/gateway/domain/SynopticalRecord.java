/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.List;
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
  private String etaEtdPlanned;
  private String etaEtdActual;
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
  private Long portOrder;
  private List<SynopticalCargoRecord> cargos;
  private BigDecimal cargoPlannedTotal;
  private BigDecimal cargoActualTotal;
  private List<SynopticalOhqRecord> ohqList;
  private BigDecimal plannedFOTotal;
  private BigDecimal actualFOTotal;
  private BigDecimal plannedDOTotal;
  private BigDecimal actualDOTotal;
  private BigDecimal plannedLubeTotal;
  private BigDecimal actualLubeTotal;
  private BigDecimal othersPlanned;
  private BigDecimal othersActual;
  private BigDecimal constantPlanned;
  private BigDecimal constantActual;
  private BigDecimal totalDwtPlanned;
  private BigDecimal totalDwtActual;
  private BigDecimal displacementPlanned;
  private BigDecimal displacementActual;
  private BigDecimal ballastPlanned;
  private BigDecimal ballastActual;

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
