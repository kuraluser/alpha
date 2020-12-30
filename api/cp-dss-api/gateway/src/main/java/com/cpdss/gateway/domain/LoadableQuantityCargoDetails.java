/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadableQuantityCargoDetails {
  private Long id;
  private String grade;
  private String estimatedAPI;
  private String estimatedTemp;
  private String orderBblsdbs;
  private String orderBbls60f;
  private String minTolerence;
  private String maxTolerence;
  private String loadableBblsdbs;
  private String loadableBbls60f;
  private String loadableLT;
  private String loadableMT;
  private String loadableKL;
  private String differencePercentage;
  private String differenceColor;
}
