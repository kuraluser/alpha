/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadablePattern {
  private Long loadablePatternId;
  private List<String> constraints;
  private List<LoadablePatternCargoDetails> loadablePatternCargoDetails;
  private List<LoadablePlanStowageDetails> loadablePlanStowageDetails;
  private Long loadableStudyStatusId;
  private Integer caseNumber;
  private StabilityParameter stabilityParameters;

  private List<LoadableQuantityCargoDetails> loadableQuantityCargoDetails;
  private List<LoadableQuantityCommingleCargoDetails> loadableQuantityCommingleCargoDetails;
  private List<LoadablePlanSynopticalRecord> loadablePlanSynopticalRecords;
}
