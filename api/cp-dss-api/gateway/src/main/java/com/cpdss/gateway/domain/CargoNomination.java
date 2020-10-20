/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cpdss.common.rest.CommonErrorCodes;

import lombok.Data;

@Data
public class CargoNomination {

  private Long id;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long loadableStudyId;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Integer priority;

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
}
