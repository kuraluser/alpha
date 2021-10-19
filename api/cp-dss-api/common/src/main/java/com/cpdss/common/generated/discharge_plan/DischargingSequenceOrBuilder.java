/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargingSequenceOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargingSequence)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 cargoNominationId = 1;</code>
   *
   * @return The cargoNominationId.
   */
  long getCargoNominationId();

  /**
   * <code>int32 startTime = 2;</code>
   *
   * @return The startTime.
   */
  int getStartTime();

  /**
   * <code>int32 endTime = 3;</code>
   *
   * @return The endTime.
   */
  int getEndTime();

  /**
   * <code>int64 portId = 4;</code>
   *
   * @return The portId.
   */
  long getPortId();

  /**
   * <code>int32 sequenceNumber = 5;</code>
   *
   * @return The sequenceNumber.
   */
  int getSequenceNumber();

  /**
   * <code>string stageName = 6;</code>
   *
   * @return The stageName.
   */
  java.lang.String getStageName();
  /**
   * <code>string stageName = 6;</code>
   *
   * @return The bytes for stageName.
   */
  com.google.protobuf.ByteString getStageNameBytes();

  /**
   * <code>bool toLoadicator = 7;</code>
   *
   * @return The toLoadicator.
   */
  boolean getToLoadicator();

  /** <code>repeated .Valve cargoValves = 8;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
      getCargoValvesList();
  /** <code>repeated .Valve cargoValves = 8;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve getCargoValves(int index);
  /** <code>repeated .Valve cargoValves = 8;</code> */
  int getCargoValvesCount();
  /** <code>repeated .Valve cargoValves = 8;</code> */
  java.util.List<? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>
      getCargoValvesOrBuilderList();
  /** <code>repeated .Valve cargoValves = 8;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder getCargoValvesOrBuilder(
      int index);

  /** <code>repeated .Valve ballastValves = 9;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
      getBallastValvesList();
  /** <code>repeated .Valve ballastValves = 9;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve getBallastValves(int index);
  /** <code>repeated .Valve ballastValves = 9;</code> */
  int getBallastValvesCount();
  /** <code>repeated .Valve ballastValves = 9;</code> */
  java.util.List<? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>
      getBallastValvesOrBuilderList();
  /** <code>repeated .Valve ballastValves = 9;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder
      getBallastValvesOrBuilder(int index);

  /** <code>repeated .DischargingRate dischargingRates = 10;</code> */
  java.util.List<com.cpdss.common.generated.discharge_plan.DischargingRate>
      getDischargingRatesList();
  /** <code>repeated .DischargingRate dischargingRates = 10;</code> */
  com.cpdss.common.generated.discharge_plan.DischargingRate getDischargingRates(int index);
  /** <code>repeated .DischargingRate dischargingRates = 10;</code> */
  int getDischargingRatesCount();
  /** <code>repeated .DischargingRate dischargingRates = 10;</code> */
  java.util.List<? extends com.cpdss.common.generated.discharge_plan.DischargingRateOrBuilder>
      getDischargingRatesOrBuilderList();
  /** <code>repeated .DischargingRate dischargingRates = 10;</code> */
  com.cpdss.common.generated.discharge_plan.DischargingRateOrBuilder getDischargingRatesOrBuilder(
      int index);

  /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate>
      getDeBallastingRatesList();
  /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate getDeBallastingRates(
      int index);
  /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
  int getDeBallastingRatesCount();
  /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRateOrBuilder>
      getDeBallastingRatesOrBuilderList();
  /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRateOrBuilder
      getDeBallastingRatesOrBuilder(int index);

  /** <code>repeated .LoadingPlanPortWiseDetails dischargingPlanPortWiseDetails = 12;</code> */
  java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails>
      getDischargingPlanPortWiseDetailsList();
  /** <code>repeated .LoadingPlanPortWiseDetails dischargingPlanPortWiseDetails = 12;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
      getDischargingPlanPortWiseDetails(int index);
  /** <code>repeated .LoadingPlanPortWiseDetails dischargingPlanPortWiseDetails = 12;</code> */
  int getDischargingPlanPortWiseDetailsCount();
  /** <code>repeated .LoadingPlanPortWiseDetails dischargingPlanPortWiseDetails = 12;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanPortWiseDetailsOrBuilder>
      getDischargingPlanPortWiseDetailsOrBuilderList();
  /** <code>repeated .LoadingPlanPortWiseDetails dischargingPlanPortWiseDetails = 12;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetailsOrBuilder
      getDischargingPlanPortWiseDetailsOrBuilder(int index);

  /**
   * <code>string cargoDischargingRate1 = 13;</code>
   *
   * @return The cargoDischargingRate1.
   */
  java.lang.String getCargoDischargingRate1();
  /**
   * <code>string cargoDischargingRate1 = 13;</code>
   *
   * @return The bytes for cargoDischargingRate1.
   */
  com.google.protobuf.ByteString getCargoDischargingRate1Bytes();

  /**
   * <code>string cargoDischargingRate2 = 14;</code>
   *
   * @return The cargoDischargingRate2.
   */
  java.lang.String getCargoDischargingRate2();
  /**
   * <code>string cargoDischargingRate2 = 14;</code>
   *
   * @return The bytes for cargoDischargingRate2.
   */
  com.google.protobuf.ByteString getCargoDischargingRate2Bytes();

  /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation>
      getBallastOperationsList();
  /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation getBallastOperations(
      int index);
  /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
  int getBallastOperationsCount();
  /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperationOrBuilder>
      getBallastOperationsOrBuilderList();
  /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperationOrBuilder
      getBallastOperationsOrBuilder(int index);
}
