/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VoyageActionRequest {
  @Pattern(regexp = "START|STOP")
  @NotNull
  private String status;

  private Long voyageId;

  private String actualStartDate;

  private String actualEndDate;

  private Long vesselId;
}
