/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargeInfoStatusReplyOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargeInfoStatusReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 dischargeInfoId = 1;</code>
   *
   * @return The dischargeInfoId.
   */
  long getDischargeInfoId();

  /**
   * <code>int64 dischargeInfoStatusId = 2;</code>
   *
   * @return The dischargeInfoStatusId.
   */
  long getDischargeInfoStatusId();

  /**
   * <code>string dischargeInfoStatusLastModifiedTime = 3;</code>
   *
   * @return The dischargeInfoStatusLastModifiedTime.
   */
  java.lang.String getDischargeInfoStatusLastModifiedTime();
  /**
   * <code>string dischargeInfoStatusLastModifiedTime = 3;</code>
   *
   * @return The bytes for dischargeInfoStatusLastModifiedTime.
   */
  com.google.protobuf.ByteString getDischargeInfoStatusLastModifiedTimeBytes();

  /**
   * <code>.ResponseStatus responseStatus = 4;</code>
   *
   * @return Whether the responseStatus field is set.
   */
  boolean hasResponseStatus();
  /**
   * <code>.ResponseStatus responseStatus = 4;</code>
   *
   * @return The responseStatus.
   */
  com.cpdss.common.generated.Common.ResponseStatus getResponseStatus();
  /** <code>.ResponseStatus responseStatus = 4;</code> */
  com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder();
}
