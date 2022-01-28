/* Licensed at AlphaOri Technologies */
package com.cpdss.common.utils;

import java.util.Arrays;
import java.util.EnumSet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypes {
  LOADABLESTUDY("LoadableStudy", "LoadableStudy", null),
  LOADABLESTUDY_WITHOUT_ALGO("LoadableStudy_Without_Algo", LOADABLESTUDY.getMessageType(), null),
  ALGORESULT("AlgoResult", "AlgoResult", null),
  // ship to shore stowage
  VALIDATEPLAN("ValidatePlan", "ValidatePlan", LOADABLESTUDY),
  // shore to ship stowage
  PATTERNDETAIL("PatternDetail", "PatternDetail", null),
  LOADINGPLAN_SAVE("LoadingPlan_Save", "LoadingPlan_Save", null),
  LOADINGPLAN("LoadingPlan", "LoadingPlan", LOADABLESTUDY),
  LOADINGPLAN_WITHOUT_ALGO("LoadingPlan_Without_Algo", LOADINGPLAN.getMessageType(), LOADABLESTUDY),
  LOADINGPLAN_ALGORESULT("LoadingPlan_AlgoResult", "LoadingPlan_AlgoResult", null),
  ULLAGE_UPDATE("Ullage_Update", "Ullage_Update", null),
  ULLAGE_UPDATE_LOADICATOR_OFF_ALGORESULT(
      "Ullage_Update_Loadicator_Off_AlgoResult", "Ullage_Update_Loadicator_Off_AlgoResult", null),
  ULLAGE_UPDATE_LOADICATOR_ON_ALGORESULT(
      "Ullage_Update_Loadicator_On_AlgoResult", "Ullage_Update_Loadicator_On_AlgoResult", null),
  DISCHARGESTUDY("DischargeStudy", "DischargeStudy", LOADINGPLAN),
  DISCHARGESTUDY_WITHOUT_ALGO(
      "DischargeStudy_Without_Algo", DISCHARGESTUDY.getMessageType(), LOADINGPLAN),
  DISCHARGEPLAN("DischargePlan", "DischargePlan", null),
  DISCHARGEPLAN_WITHOUT_ALGO("DischargePlan_Without_Algo", DISCHARGEPLAN.getMessageType(), null),
  DISCHARGEPLAN_ALGORESULT("DischargePlan_AlgoResult", "DischargePlan_AlgoResult", null),
  DISCHARGEPLAN_ULLAGE_UPDATE("DischargePlan_Ullage_Update", "DischargePlan_Ullage_Update", null),
  DISCHARGEPLAN_ULLAGE_UPDATE_ALGORESULT(
      "DischargePlan_Ullage_Update_AlgoResult", "DischargePlan_Ullage_Update_AlgoResult", null),
  FILE_SHAREING("FileSharing", "FileSharing", null);

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
  private final MessageTypes dependentMessageType;

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
