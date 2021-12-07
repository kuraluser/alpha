/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CargoNomination {

  private Long id;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long loadableStudyId;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long priority;

  private String color;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long cargoId;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String abbreviation;

  private List<LoadingPort> loadingPorts;

  private BigDecimal quantity;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private BigDecimal maxTolerance;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private BigDecimal minTolerance;

  private BigDecimal api;

  private BigDecimal temperature;

  private Long segregationId;

  private Boolean isCargoNominationComplete;

  private Long mode;

  private BigDecimal maxQuantity;

  private BigDecimal dischargeTime;

  private Long sequenceNo;

  private Boolean emptyMaxNoOfTanks;
}
