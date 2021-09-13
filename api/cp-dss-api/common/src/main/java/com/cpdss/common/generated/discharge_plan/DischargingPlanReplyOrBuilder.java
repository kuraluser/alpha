/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargingPlanReplyOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargingPlanReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.ResponseStatus responseStatus = 7;</code>
   *
   * @return Whether the responseStatus field is set.
   */
  boolean hasResponseStatus();
  /**
   * <code>.ResponseStatus responseStatus = 7;</code>
   *
   * @return The responseStatus.
   */
  com.cpdss.common.generated.Common.ResponseStatus getResponseStatus();
  /** <code>.ResponseStatus responseStatus = 7;</code> */
  com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder();

  /**
   * <code>int64 loadingInfoId = 1;</code>
   *
   * @return The loadingInfoId.
   */
  long getLoadingInfoId();

  /**
   * <code>.DischargeInformation dischargingInformation = 8;</code>
   *
   * @return Whether the dischargingInformation field is set.
   */
  boolean hasDischargingInformation();
  /**
   * <code>.DischargeInformation dischargingInformation = 8;</code>
   *
   * @return The dischargingInformation.
   */
  com.cpdss.common.generated.discharge_plan.DischargeInformation getDischargingInformation();
  /** <code>.DischargeInformation dischargingInformation = 8;</code> */
  com.cpdss.common.generated.discharge_plan.DischargeInformationOrBuilder
      getDischargingInformationOrBuilder();

  /** <code>repeated .LoadingSequence dischargingSequences = 2;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence>
      getDischargingSequencesList();
  /** <code>repeated .LoadingSequence dischargingSequences = 2;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence getDischargingSequences(
      int index);
  /** <code>repeated .LoadingSequence dischargingSequences = 2;</code> */
  int getDischargingSequencesCount();
  /** <code>repeated .LoadingSequence dischargingSequences = 2;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceOrBuilder>
      getDischargingSequencesOrBuilderList();
  /** <code>repeated .LoadingSequence dischargingSequences = 2;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceOrBuilder
      getDischargingSequencesOrBuilder(int index);

  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanStowageDetails = 3;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails>
      getPortDischargingPlanStowageDetailsList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanStowageDetails = 3;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails
      getPortDischargingPlanStowageDetails(int index);
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanStowageDetails = 3;</code> */
  int getPortDischargingPlanStowageDetailsCount();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanStowageDetails = 3;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanTankDetailsOrBuilder>
      getPortDischargingPlanStowageDetailsOrBuilderList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanStowageDetails = 3;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetailsOrBuilder
      getPortDischargingPlanStowageDetailsOrBuilder(int index);

  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanBallastDetails = 4;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails>
      getPortDischargingPlanBallastDetailsList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanBallastDetails = 4;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails
      getPortDischargingPlanBallastDetails(int index);
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanBallastDetails = 4;</code> */
  int getPortDischargingPlanBallastDetailsCount();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanBallastDetails = 4;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanTankDetailsOrBuilder>
      getPortDischargingPlanBallastDetailsOrBuilderList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanBallastDetails = 4;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetailsOrBuilder
      getPortDischargingPlanBallastDetailsOrBuilder(int index);

  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanRobDetails = 5;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails>
      getPortDischargingPlanRobDetailsList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanRobDetails = 5;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails
      getPortDischargingPlanRobDetails(int index);
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanRobDetails = 5;</code> */
  int getPortDischargingPlanRobDetailsCount();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanRobDetails = 5;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanTankDetailsOrBuilder>
      getPortDischargingPlanRobDetailsOrBuilderList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanRobDetails = 5;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetailsOrBuilder
      getPortDischargingPlanRobDetailsOrBuilder(int index);

  /**
   * <code>repeated .LoadingPlanStabilityParameters portDischargingPlanStabilityParameters = 6;
   * </code>
   */
  java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters>
      getPortDischargingPlanStabilityParametersList();
  /**
   * <code>repeated .LoadingPlanStabilityParameters portDischargingPlanStabilityParameters = 6;
   * </code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
      getPortDischargingPlanStabilityParameters(int index);
  /**
   * <code>repeated .LoadingPlanStabilityParameters portDischargingPlanStabilityParameters = 6;
   * </code>
   */
  int getPortDischargingPlanStabilityParametersCount();
  /**
   * <code>repeated .LoadingPlanStabilityParameters portDischargingPlanStabilityParameters = 6;
   * </code>
   */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanStabilityParametersOrBuilder>
      getPortDischargingPlanStabilityParametersOrBuilderList();
  /**
   * <code>repeated .LoadingPlanStabilityParameters portDischargingPlanStabilityParameters = 6;
   * </code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParametersOrBuilder
      getPortDischargingPlanStabilityParametersOrBuilder(int index);
}
