/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CargoHistory {
  private Long vesselId;
  private Long cargoId;
  private Long loadingPortId;
  private String loadedDate;
  private Integer loadedYear;
  private Integer loadedMonth;
  private Integer loadedDay;
  private BigDecimal api;
  private BigDecimal temperature;

  private String vesselName;
  private String grade;
  private String loadingPortName;
}
