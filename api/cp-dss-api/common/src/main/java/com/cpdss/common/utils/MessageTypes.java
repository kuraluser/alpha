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
  VALIDATEPLAN("ValidatePlan"),
  PATTERNDETAIL("PatternDetail"),
  LOADINGPLAN("LoadingPlan");

  public static EnumSet<MessageTypes> ship = EnumSet.of(ALGORESULT, PATTERNDETAIL);
  public static EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, LOADINGPLAN);
  private final String messageType;
}
