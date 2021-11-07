/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadableQuantityCargoDetails {
  @JsonInclude(Include.NON_NULL)
  private Long id;

  @JsonInclude(Include.NON_NULL)
  private String grade;

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

  private String orderedQuantity;
  private String cargoAbbreviation;
  private String colorCode;
  private Integer priority;
  private Integer loadingOrder;

  private String slopQuantity;
  private String timeRequiredForLoading;

  @JsonInclude(Include.NON_NULL)
  private List<String> loadingPorts;

  private String estimatedAPI;
  private String estimatedTemp;
  private Long cargoId;
  private Long cargoNominationId;
}
