/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@ToString
public class ShipLoginResponse implements Serializable {

  /** */
  private static final long serialVersionUID = 7670073443393520469L;

  private CommonSuccessResponse responseStatus;

  private String token;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private PasswordExpiryReminder expiryReminder;

  public ShipLoginResponse(CommonSuccessResponse responseStatus, String token) {
    this.responseStatus = responseStatus;
    this.token = token;
  }

  public ShipLoginResponse(
      CommonSuccessResponse responseStatus, String token, PasswordExpiryReminder expiryReminder) {
    this.responseStatus = responseStatus;
    this.token = token;
    this.expiryReminder = expiryReminder;
  }
}
