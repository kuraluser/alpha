/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.*;

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
