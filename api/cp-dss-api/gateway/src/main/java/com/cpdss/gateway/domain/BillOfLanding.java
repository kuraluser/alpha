/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import lombok.*;

/** @author ravi.r */
@Data
public class BillOfLanding {

  private Long id;

  private Long dischargeStudyId;

  private Long portId;

  private Long cargoId;

  private String blRefNumber;

  private BigDecimal bblAt60f;

  private BigDecimal quantityLt;

  private BigDecimal quantityMt;

  private BigDecimal klAt15c;

  private BigDecimal api;

  private BigDecimal temperature;

  private BigDecimal isActive;

  private Long version;
}
