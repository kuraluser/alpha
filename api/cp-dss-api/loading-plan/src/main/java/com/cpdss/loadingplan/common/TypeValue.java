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
public enum TypeValue {
  NUMBER("Number"),
  BOOLEAN("Boolean"),
  DROPDOWN("Dropdown"),
  MULTISELECT("MultiSelect"),
  STRING("string");

  private String type;
}
