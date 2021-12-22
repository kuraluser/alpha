/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

/** @author pranav.k */
@Data
public class AlgoBerthDetails {

  private Long id; // Primary key for Berth Info Master table
  private Long portId;
  private Long loadingInfoId;
  private Long dischargingInfoId;
  private BigDecimal maxShpChannel;
  private String berthName;
  private Long loadingBerthId; // Primary key for loading berth, in Loading Plan DB
  private Long dischargingBerthId;
  private BigDecimal maxShipDepth;
  private String hoseConnections;
  private BigDecimal seaDraftLimitation;
  private BigDecimal airDraftLimitation;
  private BigDecimal maxManifoldHeight;
  private String regulationAndRestriction;
  private BigDecimal maxLoa;
  private BigDecimal maxDraft;
  private String itemsToBeAgreedWith;
  private String lineDisplacement;

  private String controllingDepth; // Get from port
  private String underKeelClearance; // Get from port
  private String seawaterDensity; // Get from port
  private BigDecimal displacement;
}
