/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.crewmaster;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrewDetailedResponse {

  private CommonSuccessResponse responseStatus;

  private List<CrewDetailed> crewDetails;

  private Long totalElements;
}
