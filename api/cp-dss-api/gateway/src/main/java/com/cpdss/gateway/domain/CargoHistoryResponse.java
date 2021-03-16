/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class CargoHistoryResponse {

  private CommonSuccessResponse responseStatus;

  private List<CargoHistory> portHistory;

  private List<CargoHistory> monthlyHistory;

  private List<CargoHistory> cargoHistory;
}
