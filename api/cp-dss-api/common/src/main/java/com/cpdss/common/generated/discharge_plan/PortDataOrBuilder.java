/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface PortDataOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:PortData)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 portId = 4;</code>
   *
   * @return The portId.
   */
  long getPortId();

  /**
   * <code>int64 portRotationId = 1;</code>
   *
   * @return The portRotationId.
   */
  long getPortRotationId();

  /**
   * <code>int64 synopticTableId = 2;</code>
   *
   * @return The synopticTableId.
   */
  long getSynopticTableId();

  /**
   * <code>int32 portOrder = 6;</code>
   *
   * @return The portOrder.
   */
  int getPortOrder();

  /**
   * <code>.DSCowDetails cowDetails = 5;</code>
   *
   * @return Whether the cowDetails field is set.
   */
  boolean hasCowDetails();
  /**
   * <code>.DSCowDetails cowDetails = 5;</code>
   *
   * @return The cowDetails.
   */
  com.cpdss.common.generated.discharge_plan.DSCowDetails getCowDetails();
  /** <code>.DSCowDetails cowDetails = 5;</code> */
  com.cpdss.common.generated.discharge_plan.DSCowDetailsOrBuilder getCowDetailsOrBuilder();
}
