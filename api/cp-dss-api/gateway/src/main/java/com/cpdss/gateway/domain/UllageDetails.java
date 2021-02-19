/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class UllageDetails {
  private Long id;
  private Long tankId;
  private String ullageDepth;
  private String evenKeelCapacityCubm;
}
