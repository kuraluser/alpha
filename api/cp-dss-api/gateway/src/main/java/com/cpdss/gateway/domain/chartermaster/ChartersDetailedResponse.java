/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.chartermaster;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChartersDetailedResponse {
  private CommonSuccessResponse responseStatus;
  private List<CharterDetailed> charterDetails;
  private Long totalElements;
}
