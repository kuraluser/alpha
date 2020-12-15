/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePatternCargoDetails {
  private Long priority;
  private String cargoAbbreviation;
  private String cargoColor;
  private Long tankId;
  private String quantity;
  private String difference;
  private String differenceColor;
  private Long loadablePatternDetailsId;
  private Boolean isCommingle;
  private Long loadablePatternCommingleDetailsId;
}
