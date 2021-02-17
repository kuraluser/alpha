/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShipLoginResponse implements Serializable {

  /** */
  private static final long serialVersionUID = 7670073443393520469L;

  private CommonSuccessResponse responseStatus;

  private String token;
}
