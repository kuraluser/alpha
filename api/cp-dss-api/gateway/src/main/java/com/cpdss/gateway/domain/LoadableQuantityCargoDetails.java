/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.util.List;

/** @Author jerin.g */
@Data
public class LoadableQuantityCargoDetails {
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
  private String loadableBblsdbs;

  @JsonInclude(Include.NON_NULL)
  private String loadableBbls60f;

  @JsonInclude(Include.NON_NULL)
  private String loadableLT;

  private String loadableMT;

  @JsonInclude(Include.NON_NULL)
  private String loadableKL;

  private String differencePercentage;

  @JsonInclude(Include.NON_NULL)
  private String differenceColor;

  private Long cargoId;
  private String orderedQuantity;
  private String cargoAbbreviation;
  private String colorCode;
  private Integer priority;
  private Integer loadingOrder;
  private Long cargoNominationId;

  private String slopQuantity;
  private String timeRequiredForLoading;
  private List<String> loadingPorts;
}
