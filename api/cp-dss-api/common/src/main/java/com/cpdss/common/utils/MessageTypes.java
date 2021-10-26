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
  LOADINGPLAN("LoadingPlan"),
  LOADINGPLAN_ALGORESULT("LoadingPlan_AlgoResult"),
  ULLAGE_UPDATE("Ullage_Update"),
  ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT("Ullage_Update_Loadicator_Off_AlgoResult"),
  ULLAGE_UPDATE_LOADICATOR_ON_LGORESULT("Ullage_Update_Loadicator_On_AlgoResult");

  public static EnumSet<MessageTypes> ship = EnumSet.of(ALGORESULT, PATTERNDETAIL);
  public static EnumSet<MessageTypes> shore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN);

  public static EnumSet<MessageTypes> loadingShore = EnumSet.of(LOADINGPLAN, ULLAGE_UPDATE);
  public static EnumSet<MessageTypes> loadingShip = EnumSet.of(LOADINGPLAN_ALGORESULT,ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT, ULLAGE_UPDATE_LOADICATOR_ON_LGORESULT);

  private final String messageType;
}
