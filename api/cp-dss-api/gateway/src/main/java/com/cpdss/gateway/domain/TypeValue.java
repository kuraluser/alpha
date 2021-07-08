/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.*;

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
