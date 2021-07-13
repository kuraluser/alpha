/* Licensed at AlphaOri Technologies */
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
  private Long portRotationId;
  private String portName;
  private Long portOrder;
  private List<SynopticalCargoBallastRecord> cargos;
  private BigDecimal cargoPlannedTotal;
  private BigDecimal cargoActualTotal;
  private List<SynopticalOhqRecord> foList;
  private List<SynopticalOhqRecord> doList;
  private List<SynopticalOhqRecord> fwList;
  private List<SynopticalOhqRecord> lubeList;
  private BigDecimal plannedFOTotal;
  private BigDecimal actualFOTotal;
  private BigDecimal plannedDOTotal;
  private BigDecimal actualDOTotal;
  private BigDecimal plannedLubeTotal;
  private BigDecimal actualLubeTotal;
  private BigDecimal plannedFWTotal;
  private BigDecimal actualFWTotal;
  private BigDecimal othersPlanned;
  private BigDecimal othersActual;
  private BigDecimal constantPlanned;
  private BigDecimal constantActual;
  private BigDecimal totalDwtPlanned;
  private BigDecimal totalDwtActual;
  private BigDecimal displacementPlanned;
  private BigDecimal displacementActual;
  private BigDecimal ballastPlannedTotal;
  private BigDecimal ballastActualTotal;

  private BigDecimal deflection;
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
  private BigDecimal list;
  private List<SynopticalCargoBallastRecord> ballast;
  private Long portTimezoneId;
  private BigDecimal bm;
  private BigDecimal sf;
  private Boolean hasLoadicator;
}
