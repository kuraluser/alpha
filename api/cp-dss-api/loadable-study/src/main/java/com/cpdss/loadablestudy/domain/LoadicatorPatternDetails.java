/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(value = Include.NON_EMPTY)
public class LoadicatorPatternDetails {
  private Long loadablePatternId;
  private List<LDTrim> ldTrim;
  private List<LDStrength> ldStrength;
  private List<LDIntactStability> ldIntactStability;
  private Boolean feedbackLoop;
  private Integer feedbackLoopCount;
}
