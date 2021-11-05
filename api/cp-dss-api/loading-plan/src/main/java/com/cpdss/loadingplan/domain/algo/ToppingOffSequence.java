/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToppingOffSequence {

  private Long id;
  private Long loadingInfoId;
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
}
