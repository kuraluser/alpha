/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargeSequenceReplyOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargeSequenceReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 vesselId = 1;</code>
   *
   * @return The vesselId.
   */
  long getVesselId();

  /**
   * <code>int64 voyageId = 2;</code>
   *
   * @return The voyageId.
   */
  long getVoyageId();

  /**
   * <code>int64 dischargePatternId = 3;</code>
   *
   * @return The dischargePatternId.
   */
  long getDischargePatternId();

  /**
   * <code>int64 portId = 4;</code>
   *
   * @return The portId.
   */
  long getPortId();

  /**
   * <code>string startDate = 5;</code>
   *
   * @return The startDate.
   */
  java.lang.String getStartDate();
  /**
   * <code>string startDate = 5;</code>
   *
   * @return The bytes for startDate.
   */
  com.google.protobuf.ByteString getStartDateBytes();

  /**
   * <code>int32 interval = 6;</code>
   *
   * @return The interval.
   */
  int getInterval();

  /** <code>repeated .DischargingSequence dischargeSequences = 7;</code> */
  java.util.List<com.cpdss.common.generated.discharge_plan.DischargingSequence>
      getDischargeSequencesList();
  /** <code>repeated .DischargingSequence dischargeSequences = 7;</code> */
  com.cpdss.common.generated.discharge_plan.DischargingSequence getDischargeSequences(int index);
  /** <code>repeated .DischargingSequence dischargeSequences = 7;</code> */
  int getDischargeSequencesCount();
  /** <code>repeated .DischargingSequence dischargeSequences = 7;</code> */
  java.util.List<? extends com.cpdss.common.generated.discharge_plan.DischargingSequenceOrBuilder>
      getDischargeSequencesOrBuilderList();
  /** <code>repeated .DischargingSequence dischargeSequences = 7;</code> */
  com.cpdss.common.generated.discharge_plan.DischargingSequenceOrBuilder
      getDischargeSequencesOrBuilder(int index);

  /**
   * <code>repeated .LoadingPlanStabilityParameters dischargeSequenceStabilityParameters = 8;</code>
   */
  java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters>
      getDischargeSequenceStabilityParametersList();
  /**
   * <code>repeated .LoadingPlanStabilityParameters dischargeSequenceStabilityParameters = 8;</code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
      getDischargeSequenceStabilityParameters(int index);
  /**
   * <code>repeated .LoadingPlanStabilityParameters dischargeSequenceStabilityParameters = 8;</code>
   */
  int getDischargeSequenceStabilityParametersCount();
  /**
   * <code>repeated .LoadingPlanStabilityParameters dischargeSequenceStabilityParameters = 8;</code>
   */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanStabilityParametersOrBuilder>
      getDischargeSequenceStabilityParametersOrBuilderList();
  /**
   * <code>repeated .LoadingPlanStabilityParameters dischargeSequenceStabilityParameters = 8;</code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParametersOrBuilder
      getDischargeSequenceStabilityParametersOrBuilder(int index);

  /**
   * <code>.ResponseStatus responseStatus = 9;</code>
   *
   * @return Whether the responseStatus field is set.
   */
  boolean hasResponseStatus();
  /**
   * <code>.ResponseStatus responseStatus = 9;</code>
   *
   * @return The responseStatus.
   */
  com.cpdss.common.generated.Common.ResponseStatus getResponseStatus();
  /** <code>.ResponseStatus responseStatus = 9;</code> */
  com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder();

  /**
   * <code>int64 portRotationId = 10;</code>
   *
   * @return The portRotationId.
   */
  long getPortRotationId();

  /**
   * <code>.CleaningTanks cleaningTanks = 11;</code>
   *
   * @return Whether the cleaningTanks field is set.
   */
  boolean hasCleaningTanks();
  /**
   * <code>.CleaningTanks cleaningTanks = 11;</code>
   *
   * @return The cleaningTanks.
   */
  com.cpdss.common.generated.discharge_plan.CleaningTanks getCleaningTanks();
  /** <code>.CleaningTanks cleaningTanks = 11;</code> */
  com.cpdss.common.generated.discharge_plan.CleaningTanksOrBuilder getCleaningTanksOrBuilder();

  /** <code>repeated .DriveTankDetail driveTankDetails = 12;</code> */
  java.util.List<com.cpdss.common.generated.discharge_plan.DriveTankDetail>
      getDriveTankDetailsList();
  /** <code>repeated .DriveTankDetail driveTankDetails = 12;</code> */
  com.cpdss.common.generated.discharge_plan.DriveTankDetail getDriveTankDetails(int index);
  /** <code>repeated .DriveTankDetail driveTankDetails = 12;</code> */
  int getDriveTankDetailsCount();
  /** <code>repeated .DriveTankDetail driveTankDetails = 12;</code> */
  java.util.List<? extends com.cpdss.common.generated.discharge_plan.DriveTankDetailOrBuilder>
      getDriveTankDetailsOrBuilderList();
  /** <code>repeated .DriveTankDetail driveTankDetails = 12;</code> */
  com.cpdss.common.generated.discharge_plan.DriveTankDetailOrBuilder getDriveTankDetailsOrBuilder(
      int index);
}
