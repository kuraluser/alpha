/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface TankTransferOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:TankTransfer)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated int64 fromTankIds = 1;</code>
   *
   * @return A list containing the fromTankIds.
   */
  java.util.List<java.lang.Long> getFromTankIdsList();
  /**
   * <code>repeated int64 fromTankIds = 1;</code>
   *
   * @return The count of fromTankIds.
   */
  int getFromTankIdsCount();
  /**
   * <code>repeated int64 fromTankIds = 1;</code>
   *
   * @param index The index of the element to return.
   * @return The fromTankIds at the given index.
   */
  long getFromTankIds(int index);

  /**
   * <code>int64 toTankId = 2;</code>
   *
   * @return The toTankId.
   */
  long getToTankId();

  /**
   * <code>int32 timeStart = 3;</code>
   *
   * @return The timeStart.
   */
  int getTimeStart();

  /**
   * <code>int32 timeEnd = 4;</code>
   *
   * @return The timeEnd.
   */
  int getTimeEnd();

  /**
   * <code>int64 cargoNominationId = 5;</code>
   *
   * @return The cargoNominationId.
   */
  long getCargoNominationId();

  /**
   * <code>string startQuantity = 6;</code>
   *
   * @return The startQuantity.
   */
  java.lang.String getStartQuantity();
  /**
   * <code>string startQuantity = 6;</code>
   *
   * @return The bytes for startQuantity.
   */
  com.google.protobuf.ByteString getStartQuantityBytes();

  /**
   * <code>string endQuantity = 7;</code>
   *
   * @return The endQuantity.
   */
  java.lang.String getEndQuantity();
  /**
   * <code>string endQuantity = 7;</code>
   *
   * @return The bytes for endQuantity.
   */
  com.google.protobuf.ByteString getEndQuantityBytes();

  /**
   * <code>string startUllage = 8;</code>
   *
   * @return The startUllage.
   */
  java.lang.String getStartUllage();
  /**
   * <code>string startUllage = 8;</code>
   *
   * @return The bytes for startUllage.
   */
  com.google.protobuf.ByteString getStartUllageBytes();

  /**
   * <code>string endUllage = 9;</code>
   *
   * @return The endUllage.
   */
  java.lang.String getEndUllage();
  /**
   * <code>string endUllage = 9;</code>
   *
   * @return The bytes for endUllage.
   */
  com.google.protobuf.ByteString getEndUllageBytes();

  /**
   * <code>string purpose = 10;</code>
   *
   * @return The purpose.
   */
  java.lang.String getPurpose();
  /**
   * <code>string purpose = 10;</code>
   *
   * @return The bytes for purpose.
   */
  com.google.protobuf.ByteString getPurposeBytes();
}
