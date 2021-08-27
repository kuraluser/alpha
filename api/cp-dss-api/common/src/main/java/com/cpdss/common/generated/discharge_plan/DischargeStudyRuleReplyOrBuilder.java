/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargeStudyRuleReplyOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargeStudyRuleReply)
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

  /** <code>repeated .RulePlans rulePlan = 2;</code> */
  java.util.List<com.cpdss.common.generated.Common.RulePlans> getRulePlanList();
  /** <code>repeated .RulePlans rulePlan = 2;</code> */
  com.cpdss.common.generated.Common.RulePlans getRulePlan(int index);
  /** <code>repeated .RulePlans rulePlan = 2;</code> */
  int getRulePlanCount();
  /** <code>repeated .RulePlans rulePlan = 2;</code> */
  java.util.List<? extends com.cpdss.common.generated.Common.RulePlansOrBuilder>
      getRulePlanOrBuilderList();
  /** <code>repeated .RulePlans rulePlan = 2;</code> */
  com.cpdss.common.generated.Common.RulePlansOrBuilder getRulePlanOrBuilder(int index);
}
