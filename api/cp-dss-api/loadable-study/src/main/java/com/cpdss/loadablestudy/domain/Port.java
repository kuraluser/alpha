/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

/** @author pranav.k */
@Data
public class Port {
  private Long portId;
  private Long portOrder;
  private Integer sequenceNumber;
  private Long loadableStudyId;
  private Long portRotationId;
}
