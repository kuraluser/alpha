/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.cargo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnBoardQuantity {

  private Long id;

  private Long portId;

  private Long tankId;

  private String tankName;

  private Long cargoId;

  private BigDecimal sounding;

  private BigDecimal quantity;

  private BigDecimal actualWeight;

  private BigDecimal volume;

  private String colorCode;

  private String abbreviation;

  private Long loadableStudyId;

  private BigDecimal api;

  private Boolean loadOnTop;

  private Boolean isObqComplete;

  private BigDecimal temperature = BigDecimal.ZERO;
}
