/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

@Data
public class SynopticalTable {
  private Long id;
  private Long loadableStudyPortRotationId;
  private Long portId;
  private String operationType;
}
