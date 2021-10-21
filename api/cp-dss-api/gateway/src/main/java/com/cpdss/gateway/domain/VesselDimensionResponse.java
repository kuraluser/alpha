/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class VesselDimensionResponse {

  private Long registerLength;
  private Long lengthOverall;
  private Double draftFullLoad;
  private Long breadthMoulded;
  private Long lengthBetweenPerpendiculars;
  private Double designedLoadDraft;
}
