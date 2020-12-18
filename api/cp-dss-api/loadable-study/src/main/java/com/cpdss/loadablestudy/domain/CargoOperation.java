/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.domain;

import java.util.HashMap;
import java.util.Map;

public enum CargoOperation {
  LOADING(1L, "Loading"),
  DISCHARGING(2L, "Discharging"),
  BUNKERING(3L, "Bunkering"),
  TRANSIT(4L, "Transit");

  private Long id;
  private String operation;
  private static final Map<Long, String> operationMapping = new HashMap<>();

  private CargoOperation(Long operationId, String operation) {
    this.id = operationId;
    this.operation = operation;
  }

  static {
    for (CargoOperation e : values()) {
      operationMapping.put(e.id, e.operation);
    }
  }

  public static String getOperation(Long operationId) {
    return operationMapping.get(operationId);
  }
}
