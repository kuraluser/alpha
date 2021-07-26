/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * DTO for loadable study port rotation
 *
 * @author Sanal
 */
@Data
public class DischargeStudyPortRotationJson {

  private Long id;

  private Long dischargeStudyId;

  private Long portId;

  private Long berthId;

  private Long operationId;

  private String seaWaterDensity;

  private String distanceBetweenPorts;

  private String timeOfStay;

  private String maxDraft;

  private String maxAirDraft;

  private String eta;

  private String etd;

  private Long portOrder;
  
  private CowDetail cowDetails;
  
  private List<DischargeStudyPortInstructionDetailsJson> instructions;
  
}
