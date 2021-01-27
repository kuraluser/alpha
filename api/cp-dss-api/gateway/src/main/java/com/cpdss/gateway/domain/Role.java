package com.cpdss.gateway.domain;

import javax.validation.constraints.NotNull;

/* Licensed under Apache-2.0 */

import com.cpdss.common.rest.CommonErrorCodes;

import lombok.Data;

@Data
public class Role {
 private Long id;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String name;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String description;

  private Long companyId;
}
