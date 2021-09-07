/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface CowPlanOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:CowPlan)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.COW_OPTION_TYPE cowOptionType = 1;</code>
   *
   * @return The enum numeric value on the wire for cowOptionType.
   */
  int getCowOptionTypeValue();
  /**
   * <code>.COW_OPTION_TYPE cowOptionType = 1;</code>
   *
   * @return The cowOptionType.
   */
  com.cpdss.common.generated.Common.COW_OPTION_TYPE getCowOptionType();

  /**
   * <code>string cowTankPercent = 2;</code>
   *
   * @return The cowTankPercent.
   */
  java.lang.String getCowTankPercent();
  /**
   * <code>string cowTankPercent = 2;</code>
   *
   * @return The bytes for cowTankPercent.
   */
  com.google.protobuf.ByteString getCowTankPercentBytes();

  /**
   * <code>string cowStartTime = 3;</code>
   *
   * @return The cowStartTime.
   */
  java.lang.String getCowStartTime();
  /**
   * <code>string cowStartTime = 3;</code>
   *
   * @return The bytes for cowStartTime.
   */
  com.google.protobuf.ByteString getCowStartTimeBytes();

  /**
   * <code>string cowEndTime = 4;</code>
   *
   * @return The cowEndTime.
   */
  java.lang.String getCowEndTime();
  /**
   * <code>string cowEndTime = 4;</code>
   *
   * @return The bytes for cowEndTime.
   */
  com.google.protobuf.ByteString getCowEndTimeBytes();

  /**
   * <code>string estCowDuration = 5;</code>
   *
   * @return The estCowDuration.
   */
  java.lang.String getEstCowDuration();
  /**
   * <code>string estCowDuration = 5;</code>
   *
   * @return The bytes for estCowDuration.
   */
  com.google.protobuf.ByteString getEstCowDurationBytes();

  /**
   * <code>string trimCowMin = 6;</code>
   *
   * @return The trimCowMin.
   */
  java.lang.String getTrimCowMin();
  /**
   * <code>string trimCowMin = 6;</code>
   *
   * @return The bytes for trimCowMin.
   */
  com.google.protobuf.ByteString getTrimCowMinBytes();

  /**
   * <code>string trimCowMax = 7;</code>
   *
   * @return The trimCowMax.
   */
  java.lang.String getTrimCowMax();
  /**
   * <code>string trimCowMax = 7;</code>
   *
   * @return The bytes for trimCowMax.
   */
  com.google.protobuf.ByteString getTrimCowMaxBytes();

  /**
   * <code>bool needFreshCrudeStorage = 8;</code>
   *
   * @return The needFreshCrudeStorage.
   */
  boolean getNeedFreshCrudeStorage();

  /**
   * <code>bool needFlushingOil = 9;</code>
   *
   * @return The needFlushingOil.
   */
  boolean getNeedFlushingOil();

  /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
  java.util.List<com.cpdss.common.generated.discharge_plan.CowTankDetails> getCowTankDetailsList();
  /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
  com.cpdss.common.generated.discharge_plan.CowTankDetails getCowTankDetails(int index);
  /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
  int getCowTankDetailsCount();
  /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
  java.util.List<? extends com.cpdss.common.generated.discharge_plan.CowTankDetailsOrBuilder>
      getCowTankDetailsOrBuilderList();
  /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
  com.cpdss.common.generated.discharge_plan.CowTankDetailsOrBuilder getCowTankDetailsOrBuilder(
      int index);
}
