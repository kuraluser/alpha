/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class CargoNominationOperationDetails {
  private Long id;
  private Long cargoNominationXId;
  private Long portId;
  private String quantity;
}
