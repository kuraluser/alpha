/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ToppingOffSequence {

  private Long id;
  private Long loadingInfoId;
  private Long dischargeInfoId;
  private Integer orderNumber;
  private Long tankId;
  private Long cargoId;
  private String shortName;
  private String cargoName;
  private String cargoAbbreviation;
  private String colourCode;
  private String remark;
  private BigDecimal ullage;
  private BigDecimal quantity;
  private BigDecimal fillingRatio;
  private BigDecimal api;
  private BigDecimal temperature;
  private Integer displayOrder;
  private Long cargoNominationId;
}
