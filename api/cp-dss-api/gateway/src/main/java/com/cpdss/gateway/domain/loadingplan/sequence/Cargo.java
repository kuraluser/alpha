/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Cargo {

  private Long tankId;
  private String tankName;
  private BigDecimal quantity;
  private BigDecimal ullage;
  private Long start;
  private Long end;
  private String name;
  private String color;
  private String abbreviation;
  private Long cargoNominationId;
  private Long cargoId;
  private BigDecimal api;
  private Boolean isCommingle;
}
