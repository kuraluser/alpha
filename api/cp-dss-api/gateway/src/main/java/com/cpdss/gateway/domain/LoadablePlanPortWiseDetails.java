/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePlanPortWiseDetails {
  private Long portId;
  private String portCode;
  private Long portRotationId;
  private LoadablePlanDetailsResponse arrivalCondition;
  private LoadablePlanDetailsResponse departureCondition;
}
