/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargingPlanSaveRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargingPlanSaveRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 dischargingInfoId = 1;</code>
   *
   * @return The dischargingInfoId.
   */
  long getDischargingInfoId();

  /**
   * <code>string processId = 2;</code>
   *
   * @return The processId.
   */
  java.lang.String getProcessId();
  /**
   * <code>string processId = 2;</code>
   *
   * @return The bytes for processId.
   */
  com.google.protobuf.ByteString getProcessIdBytes();

  /** <code>repeated .DischargingSequence dischargingSequences = 3;</code> */
  java.util.List<com.cpdss.common.generated.discharge_plan.DischargingSequence>
      getDischargingSequencesList();
  /** <code>repeated .DischargingSequence dischargingSequences = 3;</code> */
  com.cpdss.common.generated.discharge_plan.DischargingSequence getDischargingSequences(int index);
  /** <code>repeated .DischargingSequence dischargingSequences = 3;</code> */
  int getDischargingSequencesCount();
  /** <code>repeated .DischargingSequence dischargingSequences = 3;</code> */
  java.util.List<? extends com.cpdss.common.generated.discharge_plan.DischargingSequenceOrBuilder>
      getDischargingSequencesOrBuilderList();
  /** <code>repeated .DischargingSequence dischargingSequences = 3;</code> */
  com.cpdss.common.generated.discharge_plan.DischargingSequenceOrBuilder
      getDischargingSequencesOrBuilder(int index);

  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanStowageDetails = 4;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails>
      getPortDischargingPlanStowageDetailsList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanStowageDetails = 4;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails
      getPortDischargingPlanStowageDetails(int index);
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanStowageDetails = 4;</code> */
  int getPortDischargingPlanStowageDetailsCount();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanStowageDetails = 4;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanTankDetailsOrBuilder>
      getPortDischargingPlanStowageDetailsOrBuilderList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanStowageDetails = 4;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetailsOrBuilder
      getPortDischargingPlanStowageDetailsOrBuilder(int index);

  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanBallastDetails = 5;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails>
      getPortDischargingPlanBallastDetailsList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanBallastDetails = 5;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails
      getPortDischargingPlanBallastDetails(int index);
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanBallastDetails = 5;</code> */
  int getPortDischargingPlanBallastDetailsCount();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanBallastDetails = 5;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanTankDetailsOrBuilder>
      getPortDischargingPlanBallastDetailsOrBuilderList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanBallastDetails = 5;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetailsOrBuilder
      getPortDischargingPlanBallastDetailsOrBuilder(int index);

  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanRobDetails = 6;</code> */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails>
      getPortDischargingPlanRobDetailsList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanRobDetails = 6;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails
      getPortDischargingPlanRobDetails(int index);
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanRobDetails = 6;</code> */
  int getPortDischargingPlanRobDetailsCount();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanRobDetails = 6;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanTankDetailsOrBuilder>
      getPortDischargingPlanRobDetailsOrBuilderList();
  /** <code>repeated .LoadingPlanTankDetails portDischargingPlanRobDetails = 6;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetailsOrBuilder
      getPortDischargingPlanRobDetailsOrBuilder(int index);

  /**
   * <code>repeated .LoadingPlanStabilityParameters portDischargingPlanStabilityParameters = 7;
   * </code>
   */
  java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters>
      getPortDischargingPlanStabilityParametersList();
  /**
   * <code>repeated .LoadingPlanStabilityParameters portDischargingPlanStabilityParameters = 7;
   * </code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
      getPortDischargingPlanStabilityParameters(int index);
  /**
   * <code>repeated .LoadingPlanStabilityParameters portDischargingPlanStabilityParameters = 7;
   * </code>
   */
  int getPortDischargingPlanStabilityParametersCount();
  /**
   * <code>repeated .LoadingPlanStabilityParameters portDischargingPlanStabilityParameters = 7;
   * </code>
   */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanStabilityParametersOrBuilder>
      getPortDischargingPlanStabilityParametersOrBuilderList();
  /**
   * <code>repeated .LoadingPlanStabilityParameters portDischargingPlanStabilityParameters = 7;
   * </code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParametersOrBuilder
      getPortDischargingPlanStabilityParametersOrBuilder(int index);

  /**
   * <code>repeated .LoadingPlanStabilityParameters dischargingSequenceStabilityParameters = 8;
   * </code>
   */
  java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters>
      getDischargingSequenceStabilityParametersList();
  /**
   * <code>repeated .LoadingPlanStabilityParameters dischargingSequenceStabilityParameters = 8;
   * </code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
      getDischargingSequenceStabilityParameters(int index);
  /**
   * <code>repeated .LoadingPlanStabilityParameters dischargingSequenceStabilityParameters = 8;
   * </code>
   */
  int getDischargingSequenceStabilityParametersCount();
  /**
   * <code>repeated .LoadingPlanStabilityParameters dischargingSequenceStabilityParameters = 8;
   * </code>
   */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanStabilityParametersOrBuilder>
      getDischargingSequenceStabilityParametersOrBuilderList();
  /**
   * <code>repeated .LoadingPlanStabilityParameters dischargingSequenceStabilityParameters = 8;
   * </code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParametersOrBuilder
      getDischargingSequenceStabilityParametersOrBuilder(int index);

  /** <code>repeated .AlgoErrors algoErrors = 9;</code> */
  java.util.List<com.cpdss.common.generated.LoadableStudy.AlgoErrors> getAlgoErrorsList();
  /** <code>repeated .AlgoErrors algoErrors = 9;</code> */
  com.cpdss.common.generated.LoadableStudy.AlgoErrors getAlgoErrors(int index);
  /** <code>repeated .AlgoErrors algoErrors = 9;</code> */
  int getAlgoErrorsCount();
  /** <code>repeated .AlgoErrors algoErrors = 9;</code> */
  java.util.List<? extends com.cpdss.common.generated.LoadableStudy.AlgoErrorsOrBuilder>
      getAlgoErrorsOrBuilderList();
  /** <code>repeated .AlgoErrors algoErrors = 9;</code> */
  com.cpdss.common.generated.LoadableStudy.AlgoErrorsOrBuilder getAlgoErrorsOrBuilder(int index);

  /**
   * <code>bool hasLoadicator = 10;</code>
   *
   * @return The hasLoadicator.
   */
  boolean getHasLoadicator();

  /**
   * <code>string dischargingPlanDetailsFromAlgo = 11;</code>
   *
   * @return The dischargingPlanDetailsFromAlgo.
   */
  java.lang.String getDischargingPlanDetailsFromAlgo();
  /**
   * <code>string dischargingPlanDetailsFromAlgo = 11;</code>
   *
   * @return The bytes for dischargingPlanDetailsFromAlgo.
   */
  com.google.protobuf.ByteString getDischargingPlanDetailsFromAlgoBytes();

  /**
   * <code>repeated .LoadingPlanCommingleDetails portDischargingPlanCommingleDetails = 12;</code>
   */
  java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails>
      getPortDischargingPlanCommingleDetailsList();
  /**
   * <code>repeated .LoadingPlanCommingleDetails portDischargingPlanCommingleDetails = 12;</code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails
      getPortDischargingPlanCommingleDetails(int index);
  /**
   * <code>repeated .LoadingPlanCommingleDetails portDischargingPlanCommingleDetails = 12;</code>
   */
  int getPortDischargingPlanCommingleDetailsCount();
  /**
   * <code>repeated .LoadingPlanCommingleDetails portDischargingPlanCommingleDetails = 12;</code>
   */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanCommingleDetailsOrBuilder>
      getPortDischargingPlanCommingleDetailsOrBuilderList();
  /**
   * <code>repeated .LoadingPlanCommingleDetails portDischargingPlanCommingleDetails = 12;</code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetailsOrBuilder
      getPortDischargingPlanCommingleDetailsOrBuilder(int index);
}
