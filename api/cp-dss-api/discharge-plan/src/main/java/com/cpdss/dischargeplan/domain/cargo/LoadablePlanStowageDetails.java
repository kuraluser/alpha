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

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long cargoNominationId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long cargoId;

  private String quantityMT; // by ALGO

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String api;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String temperature;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String cargo1QuantityMT;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String cargo2QuantityMT;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long cargo1NominationId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long cargo2NominationId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long dischargeCargoNominationId;
}
