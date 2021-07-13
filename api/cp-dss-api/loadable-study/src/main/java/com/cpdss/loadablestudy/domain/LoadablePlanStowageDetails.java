/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanStowageDetails {
  private Long id;
  private Long tankId;

  @JsonInclude(Include.NON_NULL)
  private Long cargoNominationId;

  @JsonInclude(Include.NON_NULL)
  private Long cargoId;

  private String quantityMT; // by ALGO

  @JsonInclude(Include.NON_NULL)
  private String api;

  @JsonInclude(Include.NON_NULL)
  private String temperature;

  @JsonInclude(Include.NON_NULL)
  private String cargo1QuantityMT;

  @JsonInclude(Include.NON_NULL)
  private String cargo2QuantityMT;

  @JsonInclude(Include.NON_NULL)
  private Long cargo1NominationId;

  @JsonInclude(Include.NON_NULL)
  private Long cargo2NominationId;
}
