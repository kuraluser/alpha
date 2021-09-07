/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import lombok.Data;

@Data
public class DischargeDetails {

  private String timeOfSunrise;
  private String timeOfSunset;
  private String startTime;
  private TrimAllowed trimAllowed;
  // tide details based on the Exel data.
}
