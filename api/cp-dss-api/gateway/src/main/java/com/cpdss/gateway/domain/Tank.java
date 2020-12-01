/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class Tank {
  private Long tankId;
  private String shortName;
  private String frameNumberFrom;
  private String frameNumberTo;
}
