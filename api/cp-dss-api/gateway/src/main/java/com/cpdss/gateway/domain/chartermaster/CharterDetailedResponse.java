/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.chartermaster;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CharterDetailedResponse {
  private CommonSuccessResponse responseStatus;
  private CharterDetailed charterDetailed;
}
