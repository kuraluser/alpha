/* Licensed at AlphaOri Technologies */
package com.cpdss.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypes {
  LOADABLESTUDY("LoadableStudy"),
  ALGORESULT("AlgoResult"),
  VALIDATEPLAN("ValidatePlan");
  private final String messageType;
}
