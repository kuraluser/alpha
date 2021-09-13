/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class LoadicatorRobDetails {

  private Long portXId;
  private Long portRotationXId;
  private Long tankXId;
  private BigDecimal quantity;
  private BigDecimal quantityM3;
  private Boolean isActive;
  private Integer conditionType;
  private Integer valueType;
  private BigDecimal density;
  private String colorCode;
}
