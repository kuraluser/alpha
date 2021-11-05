/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReasonForDelay {
  private Long id;
  private String reason;
}
