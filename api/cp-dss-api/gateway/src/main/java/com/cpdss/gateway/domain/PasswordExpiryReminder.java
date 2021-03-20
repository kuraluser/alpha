/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordExpiryReminder {
  private long daysRemain;
}
