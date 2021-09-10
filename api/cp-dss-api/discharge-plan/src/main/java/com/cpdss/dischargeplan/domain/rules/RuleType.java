/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.domain.rules;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum value for RuleType
 *
 * @author vinothkumar.m
 */
@AllArgsConstructor
@Getter
public enum RuleType {
  PREFERABLE(2L, "Preferable"),
  ABSOLUTE(1L, "Absolute");

  private Long id;
  private String ruleType;
}
