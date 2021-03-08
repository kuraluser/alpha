/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CargoHistory {
  private Long cargoId;
  private Long loadingPortId;
  private String loadedDate;
  private Integer loadedYear;
  private Integer loadedMonth;
  private BigDecimal api;
  private BigDecimal temperature;
}
