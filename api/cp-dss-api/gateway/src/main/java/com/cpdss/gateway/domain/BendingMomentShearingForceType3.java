/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author ravi.r */
@Data
public class BendingMomentShearingForceType3 {
  private Long id;
  private String frameNumber;
  private String loadCondition;
  private String draftAp;
  private String draftFp;
  private String bendingMoment;
  private String shearingForce;
  private String isActive;
}
