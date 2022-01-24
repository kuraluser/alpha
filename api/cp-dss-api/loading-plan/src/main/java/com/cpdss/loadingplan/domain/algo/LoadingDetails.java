/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadingDetails {

  private String timeOfSunrise;
  private String timeOfSunset;
  private String startTime;
  private TrimAllowed trimAllowed;
  private LocalDate commonDate;
  // tide details based on the Exel data.
}
