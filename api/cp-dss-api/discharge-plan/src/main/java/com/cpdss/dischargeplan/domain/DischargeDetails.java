/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class DischargeDetails {

  private String timeOfSunrise;
  private String timeOfSunset;
  private String startTime;
  private TrimAllowed trimAllowed;
  private String commonDate;
  private BigDecimal slopQuantity;
  // tide details based on the Exel data.
}
