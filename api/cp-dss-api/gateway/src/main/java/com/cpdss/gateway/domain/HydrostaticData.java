/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class HydrostaticData {
  private Long id;
  private String trim;
  private String draft;
  private String displacement;
  private String lcb;
  private String lcf;
  private String vcb;
  private String tkm;
  private String lkm;
  private String mtc;
}
