/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class VesselDraftCondition {
  private Long id;
  private Long draftConditionId;
  private String depth;
  private String freeboard;
  private String draftExtreme;
  private String displacement;
  private String deadWeight;
}
