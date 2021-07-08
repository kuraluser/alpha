/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author ravi.r */
@Data
public class ShearingForceType2 {
  private Long id;
  private String frameNumber;
  private String displacement;
  private String buay;
  private String difft;
  private String corrt;
  private String isActive;
}
