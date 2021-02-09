/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadicatorPatternDetails {
  private Long loadablePatternId;
  private List<LDTrim> ldTrim;
  private List<LDStrength> ldStrength;
  private List<LDIntactStability> ldIntactStability;
}
