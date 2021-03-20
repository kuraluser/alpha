/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class BendingMoment {
  private Long id;
  private String frameNumber;
  private String baseDraft;
  private String baseValue;
  private String draftCorrection;
  private String trimCorrection;
}
