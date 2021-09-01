/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain.algo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/** @author pranav.k */
@Data
public class LoadicatorResult {
  private Integer time;
  private String calculatedDraftFwdPlanned;
  private String calculatedDraftMidPlanned;
  private String calculatedDraftAftPlanned;
  private String calculatedTrimPlanned;
  private String blindSector;
  private String list;
  private String deflection;
  private String airDraft;

  @JsonProperty("SF")
  private String shearingForce;

  @JsonProperty("BM")
  private String bendingMoment;

  private List<String> errorDetails;
  private List<String> judgement;
}
