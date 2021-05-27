/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageTypes {
  LOADABLESTUDY("LoadableStudy"),
  ALGORESULT("AlgoResult");
  private final String messageType;
}
