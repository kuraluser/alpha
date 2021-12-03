/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DriveTank {

  private Long tankId;
  private String tankName;
  private String tankShortName;
  private Long start;
  private Long end;
}
