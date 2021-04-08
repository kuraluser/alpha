/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

  private Long id;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String name;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String description;

  private Long companyId;
  
  private Boolean isUserMapped;
}
