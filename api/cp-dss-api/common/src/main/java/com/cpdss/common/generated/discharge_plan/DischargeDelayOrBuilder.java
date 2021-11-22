/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargeDelayOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargeDelay)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * master data
   * </pre>
   *
   * <code>repeated .DelayReasons reasons = 1;</code>
   */
  java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons>
      getReasonsList();
  /**
   *
   *
   * <pre>
   * master data
   * </pre>
   *
   * <code>repeated .DelayReasons reasons = 1;</code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons getReasons(int index);
  /**
   *
   *
   * <pre>
   * master data
   * </pre>
   *
   * <code>repeated .DelayReasons reasons = 1;</code>
   */
  int getReasonsCount();
  /**
   *
   *
   * <pre>
   * master data
   * </pre>
   *
   * <code>repeated .DelayReasons reasons = 1;</code>
   */
  java.util.List<
          ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasonsOrBuilder>
      getReasonsOrBuilderList();
  /**
   *
   *
   * <pre>
   * master data
   * </pre>
   *
   * <code>repeated .DelayReasons reasons = 1;</code>
   */
  com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasonsOrBuilder
      getReasonsOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * user data
   * </pre>
   *
   * <code>repeated .DischargeDelays delays = 2;</code>
   */
  java.util.List<com.cpdss.common.generated.discharge_plan.DischargeDelays> getDelaysList();
  /**
   *
   *
   * <pre>
   * user data
   * </pre>
   *
   * <code>repeated .DischargeDelays delays = 2;</code>
   */
  com.cpdss.common.generated.discharge_plan.DischargeDelays getDelays(int index);
  /**
   *
   *
   * <pre>
   * user data
   * </pre>
   *
   * <code>repeated .DischargeDelays delays = 2;</code>
   */
  int getDelaysCount();
  /**
   *
   *
   * <pre>
   * user data
   * </pre>
   *
   * <code>repeated .DischargeDelays delays = 2;</code>
   */
  java.util.List<? extends com.cpdss.common.generated.discharge_plan.DischargeDelaysOrBuilder>
      getDelaysOrBuilderList();
  /**
   *
   *
   * <pre>
   * user data
   * </pre>
   *
   * <code>repeated .DischargeDelays delays = 2;</code>
   */
  com.cpdss.common.generated.discharge_plan.DischargeDelaysOrBuilder getDelaysOrBuilder(int index);
}
