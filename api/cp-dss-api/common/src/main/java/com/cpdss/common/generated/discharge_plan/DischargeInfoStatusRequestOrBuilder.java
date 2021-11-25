/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargeInfoStatusRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargeInfoStatusRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 dischargeInfoId = 1;</code>
   *
   * @return The dischargeInfoId.
   */
  long getDischargeInfoId();

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

  /**
   * <code>int32 conditionType = 3;</code>
   *
   * @return The conditionType.
   */
  int getConditionType();
}
