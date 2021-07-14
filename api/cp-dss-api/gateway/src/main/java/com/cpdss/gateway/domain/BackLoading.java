/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class BackLoading {
  private Long id;
  private String colour;
  private Long cargoId;
  private BigDecimal api;
  private BigDecimal temperature;
  private String abbreviation;
  private BigDecimal quantity;
}
