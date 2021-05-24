/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BerthDetails {

  private String berthName;
  private BigDecimal maxShipDepth;
  // hose connection -> not available in DB
  private BigDecimal seaDraftLimitation;
  private BigDecimal airDraftLimitation;
  private BigDecimal maxManifoldHeight;
  private String regulationAndRestriction;
}
