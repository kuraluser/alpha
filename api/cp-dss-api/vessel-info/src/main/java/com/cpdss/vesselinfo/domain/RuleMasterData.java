/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.domain;

import lombok.*;

/**
 * Enum value for type value
 *
 * @author vinothkumar.m
 */
@AllArgsConstructor
@Getter
public enum RuleMasterData {
  CargoTankOne("**", "**"),
  CargoTankTwo("Commence loading only in", "tanks");

  private String prefix;
  private String suffix;
}
