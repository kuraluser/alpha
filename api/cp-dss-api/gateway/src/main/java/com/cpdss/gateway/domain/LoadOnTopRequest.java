/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoadOnTopRequest {

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  Boolean isLoadOnTop;
}
