/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface PortDischargingPlanRobDetailsReplyOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:PortDischargingPlanRobDetailsReply)
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

  /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
  java.util.List<com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails>
      getPortDischargingPlanRobDetailsList();
  /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
  com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails
      getPortDischargingPlanRobDetails(int index);
  /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
  int getPortDischargingPlanRobDetailsCount();
  /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsOrBuilder>
      getPortDischargingPlanRobDetailsOrBuilderList();
  /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
  com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsOrBuilder
      getPortDischargingPlanRobDetailsOrBuilder(int index);
}
