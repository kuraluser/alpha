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
  CargoTank("only", "tank can be filled with commingled cargo");

  private String prefix;
  private String suffix;
}
