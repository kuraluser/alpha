/* Licensed at AlphaOri Technologies */
package com.cpdss.common.utils;

import java.util.EnumSet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypes {
  LOADABLESTUDY("LoadableStudy"),
  ALGORESULT("AlgoResult"),
  VALIDATEPLAN("ValidatePlan");

  public static EnumSet<MessageTypes> ship = EnumSet.of(ALGORESULT);
  public static EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN);
  private final String messageType;
}
