/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Enum for user status values */
@AllArgsConstructor
@Getter
public enum UserStatusValue {
  APPROVED(1L, "Approved"),
  REJECTED(2L, "Rejected"),
  REQUESTED(3L, "Requested");

  private final long id;
  private final String statusName;
}
