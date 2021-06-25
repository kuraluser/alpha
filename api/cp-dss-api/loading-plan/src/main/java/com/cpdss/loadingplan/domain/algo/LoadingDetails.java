/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import lombok.Data;

@Data
public class LoadingDetails {

  private String timeOfSunrise;
  private String timeOfSunset;
  private String startTime;
  private TrimAllowed trimAllowed;
  // tide details based on the Exel data.
}
