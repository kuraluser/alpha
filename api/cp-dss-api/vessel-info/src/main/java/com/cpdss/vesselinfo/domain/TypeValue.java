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
public enum TypeValue {
  NUMBER("Number"),
  BOOLEAN("Boolean"),
  DROPDOWN("Dropdown"),
  STRING("string");

  private String type;
}
