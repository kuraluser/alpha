/* Licensed at AlphaOri Technologies */
package com.cpdss.common.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageTypes {
  LOADABLESTUDY("LoadableStudy"),
  ALGORESULT("AlgoResult");
  private final String messageType;
}
