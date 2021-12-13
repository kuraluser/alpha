/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchedulerRequest {
  private String vesselName;
  private Long vesselId;
  private String shipId;
}
