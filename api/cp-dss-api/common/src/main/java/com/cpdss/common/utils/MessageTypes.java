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
  // ship to shore stowage
  VALIDATEPLAN("ValidatePlan"),
  // shore to ship stowage
  PATTERNDETAIL("PatternDetail"),
  LOADINGPLAN_SAVE("LoadingPlan_Save"),
  LOADINGPLAN("LoadingPlan"),
  LOADINGPLAN_ALGORESULT("LoadingPlan_AlgoResult"),
  ULLAGE_UPDATE("Ullage_Update"),
  ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT("Ullage_Update_Loadicator_Off_AlgoResult"),
  ULLAGE_UPDATE_LOADICATOR_ON_ALGORESULT("Ullage_Update_Loadicator_On_AlgoResult"),
  DISCHARGESTUDY("DischargeStudy"),
  DISCHARGEPLAN("DischargePlan"),
  DISCHARGEPLAN_ALGORESULT("DischargePlan_AlgoResult"),
  DISCHARGEPLAN_ULLAGE_UPDATE("DischargePlan_Ullage_Update"),
  DISCHARGEPLAN_ULLAGE_UPDATE_ALGORESULT("DischargePlan_Ullage_Update_AlgoResult");

  public static EnumSet<MessageTypes> loadableShip = EnumSet.of(ALGORESULT, PATTERNDETAIL);
  public static EnumSet<MessageTypes> loadableShore = EnumSet.of(LOADABLESTUDY, VALIDATEPLAN);

  public static EnumSet<MessageTypes> loadingShore =
      EnumSet.of(LOADINGPLAN_SAVE, LOADINGPLAN, ULLAGE_UPDATE);
  public static EnumSet<MessageTypes> loadingShip =
      EnumSet.of(
          LOADINGPLAN_ALGORESULT,
          ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT,
          ULLAGE_UPDATE_LOADICATOR_ON_ALGORESULT);
  public static EnumSet<MessageTypes> dischargeShore =
      EnumSet.of(DISCHARGEPLAN, DISCHARGEPLAN_ULLAGE_UPDATE);
  public static EnumSet<MessageTypes> dischargeShip =
      EnumSet.of(DISCHARGEPLAN_ALGORESULT, DISCHARGEPLAN_ULLAGE_UPDATE_ALGORESULT);
  public static EnumSet<MessageTypes> dischargeStudyShore = EnumSet.of(DISCHARGESTUDY);

  private final String messageType;
}
