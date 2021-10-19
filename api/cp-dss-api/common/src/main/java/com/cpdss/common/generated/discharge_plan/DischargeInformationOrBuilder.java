/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargeInformationOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargeInformation)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 dischargeInfoId = 8;</code>
   *
   * @return The dischargeInfoId.
   */
  long getDischargeInfoId();

  /**
   * <code>int64 synopticTableId = 9;</code>
   *
   * @return The synopticTableId.
   */
  long getSynopticTableId();

  /**
   * <code>.ResponseStatus responseStatus = 1;</code>
   *
   * @return Whether the responseStatus field is set.
   */
  boolean hasResponseStatus();
  /**
   * <code>.ResponseStatus responseStatus = 1;</code>
   *
   * @return The responseStatus.
   */
  com.cpdss.common.generated.Common.ResponseStatus getResponseStatus();
  /** <code>.ResponseStatus responseStatus = 1;</code> */
  com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder();

  /**
   * <code>.DischargeDetails dischargeDetails = 2;</code>
   *
   * @return Whether the dischargeDetails field is set.
   */
  boolean hasDischargeDetails();
  /**
   * <code>.DischargeDetails dischargeDetails = 2;</code>
   *
   * @return The dischargeDetails.
   */
  com.cpdss.common.generated.discharge_plan.DischargeDetails getDischargeDetails();
  /** <code>.DischargeDetails dischargeDetails = 2;</code> */
  com.cpdss.common.generated.discharge_plan.DischargeDetailsOrBuilder
      getDischargeDetailsOrBuilder();

  /**
   * <code>.DischargeRates dischargeRate = 3;</code>
   *
   * @return Whether the dischargeRate field is set.
   */
  boolean hasDischargeRate();
  /**
   * <code>.DischargeRates dischargeRate = 3;</code>
   *
   * @return The dischargeRate.
   */
  com.cpdss.common.generated.discharge_plan.DischargeRates getDischargeRate();
  /** <code>.DischargeRates dischargeRate = 3;</code> */
  com.cpdss.common.generated.discharge_plan.DischargeRatesOrBuilder getDischargeRateOrBuilder();

  /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
  java.util.List<com.cpdss.common.generated.discharge_plan.DischargeBerths> getBerthDetailsList();
  /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
  com.cpdss.common.generated.discharge_plan.DischargeBerths getBerthDetails(int index);
  /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
  int getBerthDetailsCount();
  /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
  java.util.List<? extends com.cpdss.common.generated.discharge_plan.DischargeBerthsOrBuilder>
      getBerthDetailsOrBuilderList();
  /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
  com.cpdss.common.generated.discharge_plan.DischargeBerthsOrBuilder getBerthDetailsOrBuilder(
      int index);

  /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse>
      getMachineInUseList();
  /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse getMachineInUse(
      int index);
  /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
  int getMachineInUseCount();
  /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingMachinesInUseOrBuilder>
      getMachineInUseOrBuilderList();
  /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUseOrBuilder
      getMachineInUseOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * reusing
   * </pre>
   *
   * <code>.LoadingStages dischargeStage = 5;</code>
   *
   * @return Whether the dischargeStage field is set.
   */
  boolean hasDischargeStage();
  /**
   *
   *
   * <pre>
   * reusing
   * </pre>
   *
   * <code>.LoadingStages dischargeStage = 5;</code>
   *
   * @return The dischargeStage.
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages getDischargeStage();
  /**
   *
   *
   * <pre>
   * reusing
   * </pre>
   *
   * <code>.LoadingStages dischargeStage = 5;</code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStagesOrBuilder
      getDischargeStageOrBuilder();

  /**
   * <code>.DischargeDelay dischargeDelay = 6;</code>
   *
   * @return Whether the dischargeDelay field is set.
   */
  boolean hasDischargeDelay();
  /**
   * <code>.DischargeDelay dischargeDelay = 6;</code>
   *
   * @return The dischargeDelay.
   */
  com.cpdss.common.generated.discharge_plan.DischargeDelay getDischargeDelay();
  /** <code>.DischargeDelay dischargeDelay = 6;</code> */
  com.cpdss.common.generated.discharge_plan.DischargeDelayOrBuilder getDischargeDelayOrBuilder();

  /**
   * <code>.CowPlan cowPlan = 11;</code>
   *
   * @return Whether the cowPlan field is set.
   */
  boolean hasCowPlan();
  /**
   * <code>.CowPlan cowPlan = 11;</code>
   *
   * @return The cowPlan.
   */
  com.cpdss.common.generated.discharge_plan.CowPlan getCowPlan();
  /** <code>.CowPlan cowPlan = 11;</code> */
  com.cpdss.common.generated.discharge_plan.CowPlanOrBuilder getCowPlanOrBuilder();

  /**
   * <code>.PostDischargeStageTime postDischargeStageTime = 7;</code>
   *
   * @return Whether the postDischargeStageTime field is set.
   */
  boolean hasPostDischargeStageTime();
  /**
   * <code>.PostDischargeStageTime postDischargeStageTime = 7;</code>
   *
   * @return The postDischargeStageTime.
   */
  com.cpdss.common.generated.discharge_plan.PostDischargeStageTime getPostDischargeStageTime();
  /** <code>.PostDischargeStageTime postDischargeStageTime = 7;</code> */
  com.cpdss.common.generated.discharge_plan.PostDischargeStageTimeOrBuilder
      getPostDischargeStageTimeOrBuilder();

  /**
   * <code>int64 dischargingInfoStatusId = 12;</code>
   *
   * @return The dischargingInfoStatusId.
   */
  long getDischargingInfoStatusId();

  /**
   * <code>int64 dischargingPlanArrStatusId = 13;</code>
   *
   * @return The dischargingPlanArrStatusId.
   */
  long getDischargingPlanArrStatusId();

  /**
   * <code>int64 dischargingPlanDepStatusId = 14;</code>
   *
   * @return The dischargingPlanDepStatusId.
   */
  long getDischargingPlanDepStatusId();

  /**
   * <code>int64 dischargePatternId = 15;</code>
   *
   * @return The dischargePatternId.
   */
  long getDischargePatternId();

  /**
   * <code>bool isDischargingInstructionsComplete = 16;</code>
   *
   * @return The isDischargingInstructionsComplete.
   */
  boolean getIsDischargingInstructionsComplete();

  /**
   * <code>bool isDischargingSequenceGenerated = 17;</code>
   *
   * @return The isDischargingSequenceGenerated.
   */
  boolean getIsDischargingSequenceGenerated();

  /**
   * <code>bool isDischargingPlanGenerated = 18;</code>
   *
   * @return The isDischargingPlanGenerated.
   */
  boolean getIsDischargingPlanGenerated();

  /**
   * <code>int64 portId = 19;</code>
   *
   * @return The portId.
   */
  long getPortId();

  /**
   * <code>int64 vesselId = 20;</code>
   *
   * @return The vesselId.
   */
  long getVesselId();

  /**
   * <code>int64 voyageId = 21;</code>
   *
   * @return The voyageId.
   */
  long getVoyageId();

  /**
   * <code>int64 portRotationId = 22;</code>
   *
   * @return The portRotationId.
   */
  long getPortRotationId();

  /**
   * <code>string dischargeStudyProcessId = 23;</code>
   *
   * @return The dischargeStudyProcessId.
   */
  java.lang.String getDischargeStudyProcessId();
  /**
   * <code>string dischargeStudyProcessId = 23;</code>
   *
   * @return The bytes for dischargeStudyProcessId.
   */
  com.google.protobuf.ByteString getDischargeStudyProcessIdBytes();

  /**
   * <code>bool isDischargingInfoComplete = 24;</code>
   *
   * @return The isDischargingInfoComplete.
   */
  boolean getIsDischargingInfoComplete();

  /**
   *
   *
   * <pre>
   * Cargo Section
   * </pre>
   *
   * <code>bool dischargeSlopTanksFirst = 25;</code>
   *
   * @return The dischargeSlopTanksFirst.
   */
  boolean getDischargeSlopTanksFirst();

  /**
   * <code>bool dischargeCommingledCargoSeparately = 26;</code>
   *
   * @return The dischargeCommingledCargoSeparately.
   */
  boolean getDischargeCommingledCargoSeparately();

  /**
   * <code>bool isDischargeInfoComplete = 27;</code>
   *
   * @return The isDischargeInfoComplete.
   */
  boolean getIsDischargeInfoComplete();
}
