/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class CalculationSheet {
  private Long id;
  private Integer tankGroup;
  private Long tankId;
  private String weightRatio;
  private String lcg;
}
