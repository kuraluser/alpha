/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.chartermaster;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartersCompanysResponse {
  private CommonSuccessResponse responseStatus;
  private List<CharterCompanyDetailed> charterCompanyDetails;
  private Long totalElements;
}
