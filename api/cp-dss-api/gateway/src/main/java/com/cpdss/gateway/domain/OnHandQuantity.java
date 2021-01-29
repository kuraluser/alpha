/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for On hand quantity
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class OnHandQuantity {

  private Long id;

  private Long portId;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long fuelTypeId;

  private String fuelTypeName;

  private String fuelTypeShortName;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long tankId;

  private String tankName;

  private Long loadableStudyId;

  private BigDecimal arrivalVolume;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private BigDecimal arrivalQuantity;

  private BigDecimal actualArrivalQuantity;

  private BigDecimal departureVolume;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private BigDecimal departureQuantity;

  private BigDecimal actualDepartureQuantity;

  private String colorCode;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private BigDecimal density;
}
