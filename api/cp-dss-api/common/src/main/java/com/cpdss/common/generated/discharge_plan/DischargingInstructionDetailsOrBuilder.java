/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargingInstructionDetailsOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargingInstructionDetails)
    com.google.protobuf.MessageOrBuilder {

  /** <code>repeated .DischargingInstructionSubHeader dischargingInstructionSubHeader = 1;</code> */
  java.util.List<com.cpdss.common.generated.discharge_plan.DischargingInstructionSubHeader>
      getDischargingInstructionSubHeaderList();
  /** <code>repeated .DischargingInstructionSubHeader dischargingInstructionSubHeader = 1;</code> */
  com.cpdss.common.generated.discharge_plan.DischargingInstructionSubHeader
      getDischargingInstructionSubHeader(int index);
  /** <code>repeated .DischargingInstructionSubHeader dischargingInstructionSubHeader = 1;</code> */
  int getDischargingInstructionSubHeaderCount();
  /** <code>repeated .DischargingInstructionSubHeader dischargingInstructionSubHeader = 1;</code> */
  java.util.List<
          ? extends
              com.cpdss.common.generated.discharge_plan.DischargingInstructionSubHeaderOrBuilder>
      getDischargingInstructionSubHeaderOrBuilderList();
  /** <code>repeated .DischargingInstructionSubHeader dischargingInstructionSubHeader = 1;</code> */
  com.cpdss.common.generated.discharge_plan.DischargingInstructionSubHeaderOrBuilder
      getDischargingInstructionSubHeaderOrBuilder(int index);

  /** <code>repeated .DischargingInstructionGroup dischargingInstructionGroupList = 2;</code> */
  java.util.List<com.cpdss.common.generated.discharge_plan.DischargingInstructionGroup>
      getDischargingInstructionGroupListList();
  /** <code>repeated .DischargingInstructionGroup dischargingInstructionGroupList = 2;</code> */
  com.cpdss.common.generated.discharge_plan.DischargingInstructionGroup
      getDischargingInstructionGroupList(int index);
  /** <code>repeated .DischargingInstructionGroup dischargingInstructionGroupList = 2;</code> */
  int getDischargingInstructionGroupListCount();
  /** <code>repeated .DischargingInstructionGroup dischargingInstructionGroupList = 2;</code> */
  java.util.List<
          ? extends com.cpdss.common.generated.discharge_plan.DischargingInstructionGroupOrBuilder>
      getDischargingInstructionGroupListOrBuilderList();
  /** <code>repeated .DischargingInstructionGroup dischargingInstructionGroupList = 2;</code> */
  com.cpdss.common.generated.discharge_plan.DischargingInstructionGroupOrBuilder
      getDischargingInstructionGroupListOrBuilder(int index);

  /**
   * <code>.ResponseStatus responseStatus = 3;</code>
   *
   * @return Whether the responseStatus field is set.
   */
  boolean hasResponseStatus();
  /**
   * <code>.ResponseStatus responseStatus = 3;</code>
   *
   * @return The responseStatus.
   */
  com.cpdss.common.generated.Common.ResponseStatus getResponseStatus();
  /** <code>.ResponseStatus responseStatus = 3;</code> */
  com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder();
}
