/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargingInfoLoadicatorDataRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargingInfoLoadicatorDataRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string processId = 1;</code>
   *
   * @return The processId.
   */
  java.lang.String getProcessId();
  /**
   * <code>string processId = 1;</code>
   *
   * @return The bytes for processId.
   */
  com.google.protobuf.ByteString getProcessIdBytes();

  /**
   * <code>int64 dischargingInformationId = 2;</code>
   *
   * @return The dischargingInformationId.
   */
  long getDischargingInformationId();

  /** <code>repeated .LoadingInfoLoadicatorDetail loadingInfoLoadicatorDetails = 3;</code> */
  java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDetail>
      getLoadingInfoLoadicatorDetailsList();
  /** <code>repeated .LoadingInfoLoadicatorDetail loadingInfoLoadicatorDetails = 3;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDetail
      getLoadingInfoLoadicatorDetails(int index);
  /** <code>repeated .LoadingInfoLoadicatorDetail loadingInfoLoadicatorDetails = 3;</code> */
  int getLoadingInfoLoadicatorDetailsCount();
  /** <code>repeated .LoadingInfoLoadicatorDetail loadingInfoLoadicatorDetails = 3;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingInfoLoadicatorDetailOrBuilder>
      getLoadingInfoLoadicatorDetailsOrBuilderList();
  /** <code>repeated .LoadingInfoLoadicatorDetail loadingInfoLoadicatorDetails = 3;</code> */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDetailOrBuilder
      getLoadingInfoLoadicatorDetailsOrBuilder(int index);

  /**
   * <code>bool isUllageUpdate = 4;</code>
   *
   * @return The isUllageUpdate.
   */
  boolean getIsUllageUpdate();

  /**
   * <code>int32 conditionType = 5;</code>
   *
   * @return The conditionType.
   */
  int getConditionType();
}
