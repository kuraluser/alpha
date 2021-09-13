/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class DischargeQuantityCargoDetails {
  @JsonInclude(Include.NON_NULL)
  private Long id;

  @JsonInclude(Include.NON_NULL)
  private String grade;

  private String estimatedAPI;
  private String estimatedTemp;

  @JsonInclude(Include.NON_NULL)
  private String orderBblsdbs;

  @JsonInclude(Include.NON_NULL)
  private String orderBbls60f;

  private String minTolerence;
  private String maxTolerence;

  @JsonInclude(Include.NON_NULL)
  private String dischargeBblsdbs;

  @JsonInclude(Include.NON_NULL)
  private String dischargeBbls60f;

  @JsonInclude(Include.NON_NULL)
  private String dischargeLT;

  @JsonInclude(Include.NON_NULL)
  private String dischargeKL;

  private String differencePercentage;

  @JsonInclude(Include.NON_NULL)
  private String differenceColor;

  private Long cargoId;
  private String orderedQuantity;
  private String cargoAbbreviation;
  private String colorCode;
  private Integer priority;
  private Integer dischargingOrder;
  private Long cargoNominationId;

  private String slopQuantity;
  private String timeRequiredForDischarging;

  @JsonInclude(Include.NON_NULL)
  private List<String> dischargingPorts;

  private List<CargoToppingOffSequence> toppingSequence;
  private String cargoNominationTemperature;

  private String cargoNominationQuantity;
  private String orderQuantity;
  private String maxDischargingRate = BigDecimal.ZERO.toString();

  // additional fields in DS
  private String dischargeMT;
  private String dischargingRate;
  private List<ContainerWashing> cowDetails;
}
