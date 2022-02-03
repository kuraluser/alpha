/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.cargo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadablePlanBallastDetails {
  private Long id;
  private Long tankId;

  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String quantityMT; // for saving result

  private String rdgLevel;
  private String correctionFactor;
  private String correctedUllage;
  private String sg;
  private String tankName;
  private String fillingRatio;

  private String colorCode;
  private String abbreviation;
}
