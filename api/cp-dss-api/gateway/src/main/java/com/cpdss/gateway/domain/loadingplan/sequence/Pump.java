/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Pump {

  @JsonProperty("rateM3_Hr")
  private String rate;

  private String quantityM3;

  private String timeStart;

  private String timeEnd;
}
