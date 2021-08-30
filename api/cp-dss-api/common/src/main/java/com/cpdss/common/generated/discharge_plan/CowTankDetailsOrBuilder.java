/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface CowTankDetailsOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:CowTankDetails)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.COW_TYPE cowType = 1;</code>
   *
   * @return The enum numeric value on the wire for cowType.
   */
  int getCowTypeValue();
  /**
   * <code>.COW_TYPE cowType = 1;</code>
   *
   * @return The cowType.
   */
  com.cpdss.common.generated.Common.COW_TYPE getCowType();

  /**
   * <code>.CargoForCow cargoForCow = 2;</code>
   *
   * @return Whether the cargoForCow field is set.
   */
  boolean hasCargoForCow();
  /**
   * <code>.CargoForCow cargoForCow = 2;</code>
   *
   * @return The cargoForCow.
   */
  com.cpdss.common.generated.discharge_plan.CargoForCow getCargoForCow();
  /** <code>.CargoForCow cargoForCow = 2;</code> */
  com.cpdss.common.generated.discharge_plan.CargoForCowOrBuilder getCargoForCowOrBuilder();

  /**
   * <code>repeated int64 tankIds = 3;</code>
   *
   * @return A list containing the tankIds.
   */
  java.util.List<java.lang.Long> getTankIdsList();
  /**
   * <code>repeated int64 tankIds = 3;</code>
   *
   * @return The count of tankIds.
   */
  int getTankIdsCount();
  /**
   * <code>repeated int64 tankIds = 3;</code>
   *
   * @param index The index of the element to return.
   * @return The tankIds at the given index.
   */
  long getTankIds(int index);
}
