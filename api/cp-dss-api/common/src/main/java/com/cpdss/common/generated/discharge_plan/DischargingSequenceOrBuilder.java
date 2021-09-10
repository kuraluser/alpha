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

  /** <code>repeated .LoadingRate loadingRates = 10;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate>
      getLoadingRatesList();
  /** <code>repeated .LoadingRate loadingRates = 10;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate getLoadingRates(int index);
  /** <code>repeated .LoadingRate loadingRates = 10;</code> */
  int getLoadingRatesCount();
  /** <code>repeated .LoadingRate loadingRates = 10;</code> */
  java.util.List<
          ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRateOrBuilder>
      getLoadingRatesOrBuilderList();
  /** <code>repeated .LoadingRate loadingRates = 10;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRateOrBuilder
      getLoadingRatesOrBuilder(int index);

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

  /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
  java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails>
      getLoadingPlanPortWiseDetailsList();
  /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
      getLoadingPlanPortWiseDetails(int index);
  /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
  int getLoadingPlanPortWiseDetailsCount();
  /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanPortWiseDetailsOrBuilder>
      getLoadingPlanPortWiseDetailsOrBuilderList();
  /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetailsOrBuilder
      getLoadingPlanPortWiseDetailsOrBuilder(int index);

  /**
   * <code>string cargoLoadingRate1 = 13;</code>
   *
   * @return The cargoLoadingRate1.
   */
  java.lang.String getCargoLoadingRate1();
  /**
   * <code>string cargoLoadingRate1 = 13;</code>
   *
   * @return The bytes for cargoLoadingRate1.
   */
  com.google.protobuf.ByteString getCargoLoadingRate1Bytes();

  /**
   * <code>string cargoLoadingRate2 = 14;</code>
   *
   * @return The cargoLoadingRate2.
   */
  java.lang.String getCargoLoadingRate2();
  /**
   * <code>string cargoLoadingRate2 = 14;</code>
   *
   * @return The bytes for cargoLoadingRate2.
   */
  com.google.protobuf.ByteString getCargoLoadingRate2Bytes();

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