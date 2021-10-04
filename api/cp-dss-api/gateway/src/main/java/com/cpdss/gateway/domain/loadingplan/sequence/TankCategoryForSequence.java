/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class TankCategoryForSequence {

  private Long id;
  private String tankName;
  private BigDecimal quantity;
  private BigDecimal ullage;
  private String cargoName;
  private Integer displayOrder; 
  private String colorCode; 
  private TankWithSequenceDetails cargoTankUllage;
}
