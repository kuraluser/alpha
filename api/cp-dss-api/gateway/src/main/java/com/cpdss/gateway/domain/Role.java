/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Role {
  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String name;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String description;

  private Long companyId;
}
