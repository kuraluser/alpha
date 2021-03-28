/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import javax.validation.constraints.Pattern;
import lombok.Data;

/** Voyage status request */
@Data
public class VoyageStatusRequest {

  private Long portOrder;

  private Long portRotationId;

  @Pattern(regexp = "ARR|DEP")
  private String operationType;
}
