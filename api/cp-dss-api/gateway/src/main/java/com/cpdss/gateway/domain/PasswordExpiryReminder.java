/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordExpiryReminder {
  private Timestamp expireOn;
}
