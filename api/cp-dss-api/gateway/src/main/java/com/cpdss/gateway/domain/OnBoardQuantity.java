/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * On hand quantity response
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class OnBoardQuantity {

  private Long id;

  private Long portId;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long tankId;

  private String tankName;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long cargoId;

  private BigDecimal sounding;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private BigDecimal weight;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private BigDecimal volume;

  private String colorCode;

  private Long loadableStudyId;
}
