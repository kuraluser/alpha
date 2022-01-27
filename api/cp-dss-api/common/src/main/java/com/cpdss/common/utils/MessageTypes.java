/* Licensed at AlphaOri Technologies */
package com.cpdss.common.utils;

import java.util.Arrays;
import java.util.EnumSet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypes {
  LOADABLESTUDY("LoadableStudy", "LoadableStudy"),
  LOADABLESTUDY_WITHOUT_ALGO("LoadableStudy_Without_Algo", LOADABLESTUDY.getMessageType()),
  ALGORESULT("AlgoResult", "AlgoResult"),
  // ship to shore stowage
  VALIDATEPLAN("ValidatePlan", "ValidatePlan"),
  // shore to ship stowage
  PATTERNDETAIL("PatternDetail", "PatternDetail"),
  LOADINGPLAN_SAVE("LoadingPlan_Save", "LoadingPlan_Save"),
  LOADINGPLAN("LoadingPlan", "LoadingPlan"),
  LOADINGPLAN_WITHOUT_ALGO("LoadingPlan_Without_Algo", LOADINGPLAN.getMessageType()),
  LOADINGPLAN_ALGORESULT("LoadingPlan_AlgoResult", "LoadingPlan_AlgoResult"),
  ULLAGE_UPDATE("Ullage_Update", "Ullage_Update"),
  ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT(
      "Ullage_Update_Loadicator_Off_AlgoResult", "Ullage_Update_Loadicator_Off_AlgoResult"),
  ULLAGE_UPDATE_LOADICATOR_ON_ALGORESULT(
      "Ullage_Update_Loadicator_On_AlgoResult", "Ullage_Update_Loadicator_On_AlgoResult"),
  DISCHARGESTUDY("DischargeStudy", "DischargeStudy"),
  DISCHARGESTUDY_WITHOUT_ALGO("DischargeStudy_Without_Algo", DISCHARGESTUDY.getMessageType()),
  DISCHARGEPLAN("DischargePlan", "DischargePlan"),
  DISCHARGEPLAN_WITHOUT_ALGO("DischargePlan_Without_Algo", DISCHARGEPLAN.getMessageType()),
  DISCHARGEPLAN_ALGORESULT("DischargePlan_AlgoResult", "DischargePlan_AlgoResult"),
  DISCHARGEPLAN_ULLAGE_UPDATE("DischargePlan_Ullage_Update", "DischargePlan_Ullage_Update"),
  DISCHARGEPLAN_ULLAGE_UPDATE_ALGORESULT(
      "DischargePlan_Ullage_Update_AlgoResult", "DischargePlan_Ullage_Update_AlgoResult"),
  FILE_SHAREING("FileSharing", "FileSharing");

  public static EnumSet<MessageTypes> loadableShip = EnumSet.of(ALGORESULT, PATTERNDETAIL);
  public static EnumSet<MessageTypes> loadableShore =
      EnumSet.of(LOADABLESTUDY, VALIDATEPLAN, LOADABLESTUDY_WITHOUT_ALGO);

  public static EnumSet<MessageTypes> loadingShore =
      EnumSet.of(LOADINGPLAN_SAVE, LOADINGPLAN, ULLAGE_UPDATE, LOADINGPLAN_WITHOUT_ALGO);
  public static EnumSet<MessageTypes> loadingShip =
      EnumSet.of(
          LOADINGPLAN_ALGORESULT,
          ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT,
          ULLAGE_UPDATE_LOADICATOR_ON_ALGORESULT);
  public static EnumSet<MessageTypes> dischargeShore =
      EnumSet.of(DISCHARGEPLAN, DISCHARGEPLAN_ULLAGE_UPDATE);
  public static EnumSet<MessageTypes> dischargeShip =
      EnumSet.of(DISCHARGEPLAN_ALGORESULT, DISCHARGEPLAN_ULLAGE_UPDATE_ALGORESULT);
  public static EnumSet<MessageTypes> dischargeStudyShore =
      EnumSet.of(DISCHARGESTUDY, DISCHARGESTUDY_WITHOUT_ALGO);

  private final String messageType;
  private final String sequenceMessageType;

  /**
   * Method to get CommunicationModule enumfrom moduleName
   *
   * @param moduleName module name value
   * @return CommunicationModule enum
   */
  public static MessageTypes getMessageType(final String messageType) {

    return Arrays.stream(MessageTypes.values())
        .filter(messageTypeEnum -> messageTypeEnum.getMessageType().equals(messageType))
        .findAny()
        .orElseThrow(
            () -> {
              return new IllegalArgumentException("Invalid messageType: " + messageType);
            });
  }
}
