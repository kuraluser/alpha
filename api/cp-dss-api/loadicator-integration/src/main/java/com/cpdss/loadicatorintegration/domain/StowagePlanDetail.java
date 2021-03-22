/* Licensed at AlphaOri Technologies */
package com.cpdss.loadicatorintegration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StowagePlanDetail {
  private Long id;
  private Long portId;
  private Long synopticalId;
}
