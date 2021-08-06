/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import lombok.Data;

@Data
public class PortLoadablePlanStowageDetails {
  private String abbreviation;
  private String api;
  private Long cargoNominationId;
  private Long cargoId;
  private String colorCode;
  private String correctedUllage;
  private String correctionFactor;
  private String fillingPercentage;
  private Long id;
  private boolean isActive;
  private Long loadablePatternId;
  private String observedBarrels;
  private String observedBarrelsAt60;
  private String observedM3;
  private String rdgUllage;
  private Long tankId;
  private String tankname;
  private String temperature;
  private String weight;
  private String quantity;
  private String actualPlanned;
  private String arrivalDeparture;
  private String ullage;
}
