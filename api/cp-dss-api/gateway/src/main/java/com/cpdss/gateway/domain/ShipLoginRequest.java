/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipLoginRequest {

  private String username;

  private String password;
}
