/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class Tank {
  private Long tankId;
  private String shortName;
  private String frameNumberFrom;
  private String frameNumberTo;
}
