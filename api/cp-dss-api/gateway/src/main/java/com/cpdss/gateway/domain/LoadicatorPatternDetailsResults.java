/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadicatorPatternDetailsResults {
  private Long loadablePatternId;
  private List<LodicatorResultDetails> lodicatorResultDetails;
}
