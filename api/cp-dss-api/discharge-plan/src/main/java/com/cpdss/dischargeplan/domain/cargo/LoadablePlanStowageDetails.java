/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.cargo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadablePlanStowageDetails {
  private Long id;
  private Long tankId;

  private String colorCode;

  private String abbreviation;

  private Long cargoNominationId;

  private Long cargoId;

  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String quantityMT; // by ALGO

  private String api;

  private String temperature;

  private String cargo1QuantityMT;

  private String cargo2QuantityMT;

  private Long cargo1NominationId;

  private Long cargo2NominationId;

  private Long dischargeCargoNominationId;
}
