/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargingPlanSaveResponseOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargingPlanSaveResponse)
    com.google.protobuf.MessageOrBuilder {

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
   * <code>int64 portRotationId = 2;</code>
   *
   * @return The portRotationId.
   */
  long getPortRotationId();
}
