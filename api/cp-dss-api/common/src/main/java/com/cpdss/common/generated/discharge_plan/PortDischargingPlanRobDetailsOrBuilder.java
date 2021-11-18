/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface PortDischargingPlanRobDetailsOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:PortDischargingPlanRobDetails)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 portXId = 1;</code>
   *
   * @return The portXId.
   */
  long getPortXId();

  /**
   * <code>int64 portRotationXId = 2;</code>
   *
   * @return The portRotationXId.
   */
  long getPortRotationXId();

  /**
   * <code>int64 tankXId = 3;</code>
   *
   * @return The tankXId.
   */
  long getTankXId();

  /**
   * <code>double quantity = 4;</code>
   *
   * @return The quantity.
   */
  double getQuantity();

  /**
   * <code>int32 conditionType = 5;</code>
   *
   * @return The conditionType.
   */
  int getConditionType();

  /**
   * <code>int32 valueType = 6;</code>
   *
   * @return The valueType.
   */
  int getValueType();

  /**
   * <code>double density = 7;</code>
   *
   * @return The density.
   */
  double getDensity();
}
