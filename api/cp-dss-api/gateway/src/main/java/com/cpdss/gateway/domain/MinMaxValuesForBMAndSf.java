/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class MinMaxValuesForBMAndSf {
  private Long id;
  private String frameNumber;
  private String minBm;
  private String maxBm;
  private String minSf;
  private String maxSf;
}
