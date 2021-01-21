/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class InnerBulkHeadValues {
  private Long id;
  private String frameNumber;
  private String foreAlpha;
  private Long foreCenterCargoTankId;
  private String foreC1;
  private String foreWingTankIds;
  private String foreC2;
  private String foreBallstTanks;
  private String foreC3;
  private String foreBWCorrection;
  private String foreC4;
  private String foreMaxAllowence;
  private String foreMinAllowence;
  private String aftAlpha;
  private Long aftCenterCargoTankId;
  private String aftC1;
  private String aftWingTankIds;
  private String aftC2;
  private String aftBallstTanks;
  private String aftC3;
  private String aftBWCorrection;
  private String aftC4;
  private String aftMaxFlAllowence;
  private String aftMinFlAllowence;
}
