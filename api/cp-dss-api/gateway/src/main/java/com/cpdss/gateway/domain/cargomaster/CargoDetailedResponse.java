/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.cargomaster;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/** Dto for displaying cargo detailed master info */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CargoDetailedResponse {

  private CommonSuccessResponse responseStatus;

  private CargoDetailed cargo;
}
