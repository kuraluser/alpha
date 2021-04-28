/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.Data;

@Data
public class UllageUpdateRequest {

  private Long id;

  private Long tankId;

  private String rdgUllage;
  private String trim;
  private String api;
  private String temp;
}
