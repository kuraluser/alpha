/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author suhail.k */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoHistory {

  private Long tankId;

  private Long cargoNominationId;

  private Long cargoId;

  private String cargoColor;
}
