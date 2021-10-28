/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargeInfoStatusReply} */
public final class DischargeInfoStatusReply extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargeInfoStatusReply)
    DischargeInfoStatusReplyOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargeInfoStatusReply.newBuilder() to construct.
  private DischargeInfoStatusReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargeInfoStatusReply() {
    dischargeInfoStatusLastModifiedTime_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargeInfoStatusReply();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargeInfoStatusReply(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8:
            {
              dischargeInfoId_ = input.readInt64();
              break;
            }
          case 16:
            {
              dischargeInfoStatusId_ = input.readInt64();
              break;
            }
          case 26:
            {
              java.lang.String s = input.readStringRequireUtf8();

              dischargeInfoStatusLastModifiedTime_ = s;
              break;
            }
          case 34:
            {
              com.cpdss.common.generated.Common.ResponseStatus.Builder subBuilder = null;
              if (responseStatus_ != null) {
                subBuilder = responseStatus_.toBuilder();
              }
              responseStatus_ =
                  input.readMessage(
                      com.cpdss.common.generated.Common.ResponseStatus.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(responseStatus_);
                responseStatus_ = subBuilder.buildPartial();
              }

              break;
            }
          default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeInfoStatusReply_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeInfoStatusReply_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply.class,
            com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply.Builder.class);
  }

  public static final int DISCHARGEINFOID_FIELD_NUMBER = 1;
  private long dischargeInfoId_;
  /**
   * <code>int64 dischargeInfoId = 1;</code>
   *
   * @return The dischargeInfoId.
   */
  public long getDischargeInfoId() {
    return dischargeInfoId_;
  }

  public static final int DISCHARGEINFOSTATUSID_FIELD_NUMBER = 2;
  private long dischargeInfoStatusId_;
  /**
   * <code>int64 dischargeInfoStatusId = 2;</code>
   *
   * @return The dischargeInfoStatusId.
   */
  public long getDischargeInfoStatusId() {
    return dischargeInfoStatusId_;
  }

  public static final int DISCHARGEINFOSTATUSLASTMODIFIEDTIME_FIELD_NUMBER = 3;
  private volatile java.lang.Object dischargeInfoStatusLastModifiedTime_;
  /**
   * <code>string dischargeInfoStatusLastModifiedTime = 3;</code>
   *
   * @return The dischargeInfoStatusLastModifiedTime.
   */
  public java.lang.String getDischargeInfoStatusLastModifiedTime() {
    java.lang.Object ref = dischargeInfoStatusLastModifiedTime_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      dischargeInfoStatusLastModifiedTime_ = s;
      return s;
    }
  }
  /**
   * <code>string dischargeInfoStatusLastModifiedTime = 3;</code>
   *
   * @return The bytes for dischargeInfoStatusLastModifiedTime.
   */
  public com.google.protobuf.ByteString getDischargeInfoStatusLastModifiedTimeBytes() {
    java.lang.Object ref = dischargeInfoStatusLastModifiedTime_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      dischargeInfoStatusLastModifiedTime_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int RESPONSESTATUS_FIELD_NUMBER = 4;
  private com.cpdss.common.generated.Common.ResponseStatus responseStatus_;
  /**
   * <code>.ResponseStatus responseStatus = 4;</code>
   *
   * @return Whether the responseStatus field is set.
   */
  public boolean hasResponseStatus() {
    return responseStatus_ != null;
  }
  /**
   * <code>.ResponseStatus responseStatus = 4;</code>
   *
   * @return The responseStatus.
   */
  public com.cpdss.common.generated.Common.ResponseStatus getResponseStatus() {
    return responseStatus_ == null
        ? com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance()
        : responseStatus_;
  }
  /** <code>.ResponseStatus responseStatus = 4;</code> */
  public com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder() {
    return getResponseStatus();
  }

  private byte memoizedIsInitialized = -1;

  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
    if (dischargeInfoId_ != 0L) {
      output.writeInt64(1, dischargeInfoId_);
    }
    if (dischargeInfoStatusId_ != 0L) {
      output.writeInt64(2, dischargeInfoStatusId_);
    }
    if (!getDischargeInfoStatusLastModifiedTimeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(
          output, 3, dischargeInfoStatusLastModifiedTime_);
    }
    if (responseStatus_ != null) {
      output.writeMessage(4, getResponseStatus());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (dischargeInfoId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, dischargeInfoId_);
    }
    if (dischargeInfoStatusId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, dischargeInfoStatusId_);
    }
    if (!getDischargeInfoStatusLastModifiedTimeBytes().isEmpty()) {
      size +=
          com.google.protobuf.GeneratedMessageV3.computeStringSize(
              3, dischargeInfoStatusLastModifiedTime_);
    }
    if (responseStatus_ != null) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(4, getResponseStatus());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply other =
        (com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply) obj;

    if (getDischargeInfoId() != other.getDischargeInfoId()) return false;
    if (getDischargeInfoStatusId() != other.getDischargeInfoStatusId()) return false;
    if (!getDischargeInfoStatusLastModifiedTime()
        .equals(other.getDischargeInfoStatusLastModifiedTime())) return false;
    if (hasResponseStatus() != other.hasResponseStatus()) return false;
    if (hasResponseStatus()) {
      if (!getResponseStatus().equals(other.getResponseStatus())) return false;
    }
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + DISCHARGEINFOID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargeInfoId());
    hash = (37 * hash) + DISCHARGEINFOSTATUSID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargeInfoStatusId());
    hash = (37 * hash) + DISCHARGEINFOSTATUSLASTMODIFIEDTIME_FIELD_NUMBER;
    hash = (53 * hash) + getDischargeInfoStatusLastModifiedTime().hashCode();
    if (hasResponseStatus()) {
      hash = (37 * hash) + RESPONSESTATUS_FIELD_NUMBER;
      hash = (53 * hash) + getResponseStatus().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parseFrom(
      byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply
      parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply
      parseDelimitedFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() {
    return newBuilder();
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(
      com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /** Protobuf type {@code DischargeInfoStatusReply} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargeInfoStatusReply)
      com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReplyOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInfoStatusReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInfoStatusReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply.class,
              com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply.Builder.class);
    }

    // Construct using
    // com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {}
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      dischargeInfoId_ = 0L;

      dischargeInfoStatusId_ = 0L;

      dischargeInfoStatusLastModifiedTime_ = "";

      if (responseStatusBuilder_ == null) {
        responseStatus_ = null;
      } else {
        responseStatus_ = null;
        responseStatusBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInfoStatusReply_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply
          .getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply build() {
      com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply result =
          new com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply(this);
      result.dischargeInfoId_ = dischargeInfoId_;
      result.dischargeInfoStatusId_ = dischargeInfoStatusId_;
      result.dischargeInfoStatusLastModifiedTime_ = dischargeInfoStatusLastModifiedTime_;
      if (responseStatusBuilder_ == null) {
        result.responseStatus_ = responseStatus_;
      } else {
        result.responseStatus_ = responseStatusBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }

    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
      return super.setField(field, value);
    }

    @java.lang.Override
    public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }

    @java.lang.Override
    public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }

    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field, int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }

    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply) {
        return mergeFrom(
            (com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply
              .getDefaultInstance()) return this;
      if (other.getDischargeInfoId() != 0L) {
        setDischargeInfoId(other.getDischargeInfoId());
      }
      if (other.getDischargeInfoStatusId() != 0L) {
        setDischargeInfoStatusId(other.getDischargeInfoStatusId());
      }
      if (!other.getDischargeInfoStatusLastModifiedTime().isEmpty()) {
        dischargeInfoStatusLastModifiedTime_ = other.dischargeInfoStatusLastModifiedTime_;
        onChanged();
      }
      if (other.hasResponseStatus()) {
        mergeResponseStatus(other.getResponseStatus());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply)
                e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long dischargeInfoId_;
    /**
     * <code>int64 dischargeInfoId = 1;</code>
     *
     * @return The dischargeInfoId.
     */
    public long getDischargeInfoId() {
      return dischargeInfoId_;
    }
    /**
     * <code>int64 dischargeInfoId = 1;</code>
     *
     * @param value The dischargeInfoId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargeInfoId(long value) {

      dischargeInfoId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 dischargeInfoId = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargeInfoId() {

      dischargeInfoId_ = 0L;
      onChanged();
      return this;
    }

    private long dischargeInfoStatusId_;
    /**
     * <code>int64 dischargeInfoStatusId = 2;</code>
     *
     * @return The dischargeInfoStatusId.
     */
    public long getDischargeInfoStatusId() {
      return dischargeInfoStatusId_;
    }
    /**
     * <code>int64 dischargeInfoStatusId = 2;</code>
     *
     * @param value The dischargeInfoStatusId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargeInfoStatusId(long value) {

      dischargeInfoStatusId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 dischargeInfoStatusId = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargeInfoStatusId() {

      dischargeInfoStatusId_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object dischargeInfoStatusLastModifiedTime_ = "";
    /**
     * <code>string dischargeInfoStatusLastModifiedTime = 3;</code>
     *
     * @return The dischargeInfoStatusLastModifiedTime.
     */
    public java.lang.String getDischargeInfoStatusLastModifiedTime() {
      java.lang.Object ref = dischargeInfoStatusLastModifiedTime_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        dischargeInfoStatusLastModifiedTime_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string dischargeInfoStatusLastModifiedTime = 3;</code>
     *
     * @return The bytes for dischargeInfoStatusLastModifiedTime.
     */
    public com.google.protobuf.ByteString getDischargeInfoStatusLastModifiedTimeBytes() {
      java.lang.Object ref = dischargeInfoStatusLastModifiedTime_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        dischargeInfoStatusLastModifiedTime_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string dischargeInfoStatusLastModifiedTime = 3;</code>
     *
     * @param value The dischargeInfoStatusLastModifiedTime to set.
     * @return This builder for chaining.
     */
    public Builder setDischargeInfoStatusLastModifiedTime(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      dischargeInfoStatusLastModifiedTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string dischargeInfoStatusLastModifiedTime = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargeInfoStatusLastModifiedTime() {

      dischargeInfoStatusLastModifiedTime_ =
          getDefaultInstance().getDischargeInfoStatusLastModifiedTime();
      onChanged();
      return this;
    }
    /**
     * <code>string dischargeInfoStatusLastModifiedTime = 3;</code>
     *
     * @param value The bytes for dischargeInfoStatusLastModifiedTime to set.
     * @return This builder for chaining.
     */
    public Builder setDischargeInfoStatusLastModifiedTimeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      dischargeInfoStatusLastModifiedTime_ = value;
      onChanged();
      return this;
    }

    private com.cpdss.common.generated.Common.ResponseStatus responseStatus_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.Common.ResponseStatus,
            com.cpdss.common.generated.Common.ResponseStatus.Builder,
            com.cpdss.common.generated.Common.ResponseStatusOrBuilder>
        responseStatusBuilder_;
    /**
     * <code>.ResponseStatus responseStatus = 4;</code>
     *
     * @return Whether the responseStatus field is set.
     */
    public boolean hasResponseStatus() {
      return responseStatusBuilder_ != null || responseStatus_ != null;
    }
    /**
     * <code>.ResponseStatus responseStatus = 4;</code>
     *
     * @return The responseStatus.
     */
    public com.cpdss.common.generated.Common.ResponseStatus getResponseStatus() {
      if (responseStatusBuilder_ == null) {
        return responseStatus_ == null
            ? com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance()
            : responseStatus_;
      } else {
        return responseStatusBuilder_.getMessage();
      }
    }
    /** <code>.ResponseStatus responseStatus = 4;</code> */
    public Builder setResponseStatus(com.cpdss.common.generated.Common.ResponseStatus value) {
      if (responseStatusBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        responseStatus_ = value;
        onChanged();
      } else {
        responseStatusBuilder_.setMessage(value);
      }

      return this;
    }
    /** <code>.ResponseStatus responseStatus = 4;</code> */
    public Builder setResponseStatus(
        com.cpdss.common.generated.Common.ResponseStatus.Builder builderForValue) {
      if (responseStatusBuilder_ == null) {
        responseStatus_ = builderForValue.build();
        onChanged();
      } else {
        responseStatusBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /** <code>.ResponseStatus responseStatus = 4;</code> */
    public Builder mergeResponseStatus(com.cpdss.common.generated.Common.ResponseStatus value) {
      if (responseStatusBuilder_ == null) {
        if (responseStatus_ != null) {
          responseStatus_ =
              com.cpdss.common.generated.Common.ResponseStatus.newBuilder(responseStatus_)
                  .mergeFrom(value)
                  .buildPartial();
        } else {
          responseStatus_ = value;
        }
        onChanged();
      } else {
        responseStatusBuilder_.mergeFrom(value);
      }

      return this;
    }
    /** <code>.ResponseStatus responseStatus = 4;</code> */
    public Builder clearResponseStatus() {
      if (responseStatusBuilder_ == null) {
        responseStatus_ = null;
        onChanged();
      } else {
        responseStatus_ = null;
        responseStatusBuilder_ = null;
      }

      return this;
    }
    /** <code>.ResponseStatus responseStatus = 4;</code> */
    public com.cpdss.common.generated.Common.ResponseStatus.Builder getResponseStatusBuilder() {

      onChanged();
      return getResponseStatusFieldBuilder().getBuilder();
    }
    /** <code>.ResponseStatus responseStatus = 4;</code> */
    public com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder() {
      if (responseStatusBuilder_ != null) {
        return responseStatusBuilder_.getMessageOrBuilder();
      } else {
        return responseStatus_ == null
            ? com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance()
            : responseStatus_;
      }
    }
    /** <code>.ResponseStatus responseStatus = 4;</code> */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.Common.ResponseStatus,
            com.cpdss.common.generated.Common.ResponseStatus.Builder,
            com.cpdss.common.generated.Common.ResponseStatusOrBuilder>
        getResponseStatusFieldBuilder() {
      if (responseStatusBuilder_ == null) {
        responseStatusBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.cpdss.common.generated.Common.ResponseStatus,
                com.cpdss.common.generated.Common.ResponseStatus.Builder,
                com.cpdss.common.generated.Common.ResponseStatusOrBuilder>(
                getResponseStatus(), getParentForChildren(), isClean());
        responseStatus_ = null;
      }
      return responseStatusBuilder_;
    }

    @java.lang.Override
    public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }

    // @@protoc_insertion_point(builder_scope:DischargeInfoStatusReply)
  }

  // @@protoc_insertion_point(class_scope:DischargeInfoStatusReply)
  private static final com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargeInfoStatusReply> PARSER =
      new com.google.protobuf.AbstractParser<DischargeInfoStatusReply>() {
        @java.lang.Override
        public DischargeInfoStatusReply parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargeInfoStatusReply(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargeInfoStatusReply> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargeInfoStatusReply> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
