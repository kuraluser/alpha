/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadicatorResultsRequest {
  private String processId;
  private List<LoadicatorPatternDetailsResults> loadicatorResultsPatternWise;
}
