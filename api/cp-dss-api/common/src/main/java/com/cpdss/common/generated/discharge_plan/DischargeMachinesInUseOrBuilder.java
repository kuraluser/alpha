/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargeMachinesInUseOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargeMachinesInUse)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * primary key
   * </pre>
   *
   * <code>int64 id = 1;</code>
   *
   * @return The id.
   */
  long getId();

  /**
   * <code>int64 dischargeInfoId = 2;</code>
   *
   * @return The dischargeInfoId.
   */
  long getDischargeInfoId();

  /**
   * <code>int64 machineId = 3;</code>
   *
   * @return The machineId.
   */
  long getMachineId();

  /**
   * <code>string capacity = 4;</code>
   *
   * @return The capacity.
   */
  java.lang.String getCapacity();
  /**
   * <code>string capacity = 4;</code>
   *
   * @return The bytes for capacity.
   */
  com.google.protobuf.ByteString getCapacityBytes();

  /**
   * <code>bool isUsing = 5;</code>
   *
   * @return The isUsing.
   */
  boolean getIsUsing();

  /**
   * <code>.MachineType machineType = 6;</code>
   *
   * @return The enum numeric value on the wire for machineType.
   */
  int getMachineTypeValue();
  /**
   * <code>.MachineType machineType = 6;</code>
   *
   * @return The machineType.
   */
  com.cpdss.common.generated.Common.MachineType getMachineType();
}
