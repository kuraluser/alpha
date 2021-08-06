/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadablePlanPortWiseDetails {
  private Long portId;
  private String portCode;
  private Long portRotationId;
  private LoadablePlanDetailsResponse arrivalCondition;
  private LoadablePlanDetailsResponse departureCondition;

  // DS fields
  private String seaWaterTemperature;
  private String ambientTemperature;
}
