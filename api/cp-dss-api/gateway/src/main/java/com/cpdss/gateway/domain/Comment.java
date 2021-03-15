/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Comment {
  @NotEmpty(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  String comment;

  Long user;
}
