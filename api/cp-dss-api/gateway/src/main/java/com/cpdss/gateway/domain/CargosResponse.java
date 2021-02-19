/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/** Dto for displaying port master info */
@Data
@JsonInclude(Include.NON_EMPTY)
public class CargosResponse {

  private CommonSuccessResponse responseStatus;

  private List<Cargo> cargos;
}
