/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum value for type value
 *
 * @author vinothkumar.m
 */
@AllArgsConstructor
@Getter
public enum RuleMasterData {
  CargoTankOne("only", "tank can be filled with commingled cargo"),
  CargoTankTwo("Commence loading only in", "tanks");

  private String prefix;
  private String suffix;
}
