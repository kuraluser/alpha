/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class CleaningTankDetails {

  private String tankShortName;
  private Long tankId;
  private String timeStart;
  private String timeEnd;
}
