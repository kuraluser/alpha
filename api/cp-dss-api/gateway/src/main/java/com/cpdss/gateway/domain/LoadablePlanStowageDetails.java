/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanStowageDetails {
  @JsonInclude(Include.NON_NULL)
  private Long id;

  private Long tankId;
  private String cargoAbbreviation;

  @JsonInclude(Include.NON_NULL)
  private String weight; // MT to UI

  private String correctedUllage; // ullage(M)
  private String fillingRatio;
  private String tankName;
  private String rdgUllage;
  private String correctionFactor;

  @JsonInclude(Include.NON_NULL)
  private String observedM3;

  @JsonInclude(Include.NON_NULL)
  private String observedBarrels;

  @JsonInclude(Include.NON_NULL)
  private String observedBarrelsAt60; // volume(BBLS)

  private String api;
  private String temperature;
  private String colorCode;
  private String quantityMT; // by ALGO
  private Long cargoNominationId;

  @JsonInclude(Include.NON_NULL)
  private Boolean isCommingle;

  @JsonInclude(Include.NON_NULL)
  private String tankShortName;

  @JsonInclude(Include.NON_NULL)
  private Integer tankDisplayOrder;
}
