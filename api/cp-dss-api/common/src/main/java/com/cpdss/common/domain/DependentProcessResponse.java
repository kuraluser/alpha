/* Licensed at AlphaOri Technologies */
package com.cpdss.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/** DTO for dependent process response in communication */
@Data
@AllArgsConstructor
public class DependentProcessResponse {
  private String dependantProcessId;
  private String dependantProcessModule;
}
