/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.cargomaster;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/** Dto for displaying cargo detailed master info */
@Data
@JsonInclude(Include.NON_NULL)
public class CargosDetailedResponse {

  private CommonSuccessResponse responseStatus;

  private List<CargoDetailed> cargos;

  private Long totalElements;
}
