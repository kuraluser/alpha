/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadicatorResultDetails {
  private Long synopticalId;
  private Long operationId;
  private Long portId;
  private String deflection; // hog
  private String calculatedDraftFwdPlanned;
  private String calculatedDraftMidPlanned;
  private String calculatedDraftAftPlanned;
  private String calculatedTrimPlanned;
  private String blindSector;
  private String list;

  @JsonProperty(value = "BM")
  private String bm;

  @JsonProperty(value = "SF")
  private String sf;

  private String hog;
}
