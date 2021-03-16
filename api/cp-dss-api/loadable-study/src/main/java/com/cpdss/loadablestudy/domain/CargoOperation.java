/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.HashMap;
import java.util.Map;

public enum CargoOperation {
  LOADING(1L, "Loading"),
  DISCHARGING(2L, "Discharging"),
  BUNKERING(3L, "Bunkering"),
  TRANSIT(4L, "Transit"),
  STS_LOADING(5L, "STS Loading"),
  STS_DISCHARGING(5L, "STS Discharging");

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
