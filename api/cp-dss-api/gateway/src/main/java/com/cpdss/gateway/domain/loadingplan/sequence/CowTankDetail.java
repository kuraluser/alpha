/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan.sequence;

import lombok.Data;

@Data
public class CowTankDetail {

  private Long tankId;
  private String tankName;
  private Long start;
  private Long end;
}
