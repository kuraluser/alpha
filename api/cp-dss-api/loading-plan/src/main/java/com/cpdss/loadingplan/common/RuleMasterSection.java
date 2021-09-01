/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum class fro rule master section
 *
 * @author vinothkumar.m
 */
@AllArgsConstructor
@Getter
public enum RuleMasterSection {
  Plan(1L),
  Loading(2L),
  Discharging(3L);

  private Long id;
}
