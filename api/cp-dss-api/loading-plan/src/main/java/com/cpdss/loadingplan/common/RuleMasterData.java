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
  CargoTank("only", "tank can be filled with commingled cargo");

  private String prefix;
  private String suffix;
}
