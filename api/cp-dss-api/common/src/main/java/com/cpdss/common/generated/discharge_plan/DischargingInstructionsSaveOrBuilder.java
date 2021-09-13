/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargingInstructionsSaveOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargingInstructionsSave)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 headerId = 1;</code>
   *
   * @return The headerId.
   */
  long getHeaderId();

  /**
   * <code>int64 instructionTypeId = 2;</code>
   *
   * @return The instructionTypeId.
   */
  long getInstructionTypeId();

  /**
   * <code>bool isChecked = 3;</code>
   *
   * @return The isChecked.
   */
  boolean getIsChecked();

  /**
   * <code>bool isSingleHeader = 4;</code>
   *
   * @return The isSingleHeader.
   */
  boolean getIsSingleHeader();

  /**
   * <code>int64 subHeaderId = 5;</code>
   *
   * @return The subHeaderId.
   */
  long getSubHeaderId();

  /**
   * <code>string instruction = 6;</code>
   *
   * @return The instruction.
   */
  java.lang.String getInstruction();
  /**
   * <code>string instruction = 6;</code>
   *
   * @return The bytes for instruction.
   */
  com.google.protobuf.ByteString getInstructionBytes();

  /**
   * <code>bool isSubHeader = 7;</code>
   *
   * @return The isSubHeader.
   */
  boolean getIsSubHeader();

  /**
   * <code>int64 dischargingInfoId = 8;</code>
   *
   * @return The dischargingInfoId.
   */
  long getDischargingInfoId();

  /**
   * <code>int64 vesselId = 9;</code>
   *
   * @return The vesselId.
   */
  long getVesselId();

  /**
   * <code>int64 portRotationId = 10;</code>
   *
   * @return The portRotationId.
   */
  long getPortRotationId();
}
