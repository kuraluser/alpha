/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class CargoHistoryRequest {

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long cargoId;

  private List<Long> loadingPortIds;
}
