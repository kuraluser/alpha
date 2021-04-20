/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Enum for notification status values */
@AllArgsConstructor
@Getter
public enum NotificationStatusValue {
  NEW(1, "New"),
  CLOSED(2, "Closed");

  private final long id;
  private final String notificationType;
}
