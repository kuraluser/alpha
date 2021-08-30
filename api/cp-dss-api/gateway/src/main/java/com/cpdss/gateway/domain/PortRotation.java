/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;

/**
 * DTO for loadable study port rotation
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_NULL)
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

  private String etaActual;

  private String etd;

  private String etdActual;

  private String layCanFrom;

  private String layCanTo;

  private Long portOrder;

  private Boolean isPortsComplete;

  private Boolean isPortRotationOhqComplete;

  private Long portTimezoneId = 0l;

  private String type;

  private Long dischargeStudyId;

  private Boolean isBackLoadingEnabled;

  private List<BackLoading> backLoading = new ArrayList<>(Arrays.asList(new BackLoading()));

  private Long cowId;

  private Long percentage;

  private BigDecimal dischargeRate;

  private List<Long> tanks = new ArrayList<>();

  private List<Long> instructionId = new ArrayList<>();

  private List<CargoNomination> cargoNominationList;
}
