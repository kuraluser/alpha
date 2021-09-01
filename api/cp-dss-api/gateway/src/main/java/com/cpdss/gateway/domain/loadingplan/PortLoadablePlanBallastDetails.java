/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PortLoadablePlanBallastDetails {
  private Long cargoId;
  private String colorCode;
  private String correctedUllage;
  private String correctionFactor;
  private String fillingPercentage;
  private Long id;
  private boolean isActive;
  private Long loadablePatternId;
  private BigDecimal rdgUllage;
  private Long tankId;
  private String tankName;
  private String tankShortName;
  private BigDecimal temperature;
  private BigDecimal quantity;
  private String actualPlanned;
  private String arrivalDeparture;
  private BigDecimal sounding;
  private BigDecimal sg;
}
