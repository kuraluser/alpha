/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class OnBoardQuantity {
  private Long id;
  private Long portId;
  private Long tankId;
  private Long cargoId;
  private String sounding;
  private String weight;
  private String volume;
}
