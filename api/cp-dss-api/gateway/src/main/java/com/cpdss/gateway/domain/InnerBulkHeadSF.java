/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class InnerBulkHeadSF {
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
}
