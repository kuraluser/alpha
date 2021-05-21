/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loading_plan;

import lombok.Data;

@Data
public class LoadingDetails {

  private String sunriseTime;
  private String sunsetTime;
  private String startTime;
  private TrimAllowed trimAllowed;
  // tide details based on the Exel data.
}

/**
 * Make sure always Even Keel (trim = 0.0) Departure Condition - Even Keel Ballast Stripping
 * Condition - 1.5 meter by stern Topping off - Even Keel, If possible. Else 1 meter by stern.
 * Slowly become even keel on completion
 */
class TrimAllowed {
  private double initialTrim;
  private double maximumTrim;
  private double finalTrim;
}
