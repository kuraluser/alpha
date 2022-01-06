/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.*;

/** @author sanalkumar.k */
@AllArgsConstructor
@Getter
public enum DischargingModesEnum {
  MANUAL(2L, "Manual"),
  BALANCE(1L, "Balance"),
  ENTIRE(3L, "Entire");

  private Long id;
  private String mode;
}
