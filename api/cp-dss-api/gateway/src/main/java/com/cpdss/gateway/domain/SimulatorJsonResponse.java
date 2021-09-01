/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.jsonbuilder.CPDSSJsonParser;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.simulator.DepartureConditionJson;
import lombok.Data;

@Data
@CPDSSJsonParser
public class SimulatorJsonResponse {
  private CommonSuccessResponse responseStatus;
  private DepartureConditionJson departureCondition;
}
