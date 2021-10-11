/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.*;

/**
 * Enum value for type value
 *
 * @author vinothkumar.m
 */
@AllArgsConstructor
@Getter
public enum RuleMasterData {
  CargoTank("**", "**");

  private String prefix;
  private String suffix;
}
