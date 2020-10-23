/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class CargoNominationResponse {

  private CommonSuccessResponse responseStatus;

  private Long cargoNominationId;

  private List<CargoNomination> cargoNominations;

  private List<Cargo> cargos;

  private List<ValveSegregation> segregations;
}
