/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargeRuleRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargeRuleRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 vesselId = 1;</code>
   *
   * @return The vesselId.
   */
  long getVesselId();

  /**
   * <code>int64 sectionId = 2;</code>
   *
   * @return The sectionId.
   */
  long getSectionId();

  /** <code>repeated .RulePlans rulePlan = 3;</code> */
  java.util.List<com.cpdss.common.generated.Common.RulePlans> getRulePlanList();
  /** <code>repeated .RulePlans rulePlan = 3;</code> */
  com.cpdss.common.generated.Common.RulePlans getRulePlan(int index);
  /** <code>repeated .RulePlans rulePlan = 3;</code> */
  int getRulePlanCount();
  /** <code>repeated .RulePlans rulePlan = 3;</code> */
  java.util.List<? extends com.cpdss.common.generated.Common.RulePlansOrBuilder>
      getRulePlanOrBuilderList();
  /** <code>repeated .RulePlans rulePlan = 3;</code> */
  com.cpdss.common.generated.Common.RulePlansOrBuilder getRulePlanOrBuilder(int index);

  /**
   * <code>int64 dischargeInfoId = 4;</code>
   *
   * @return The dischargeInfoId.
   */
  long getDischargeInfoId();

  /**
   * <code>bool isNoDefaultRule = 5;</code>
   *
   * @return The isNoDefaultRule.
   */
  boolean getIsNoDefaultRule();
}
