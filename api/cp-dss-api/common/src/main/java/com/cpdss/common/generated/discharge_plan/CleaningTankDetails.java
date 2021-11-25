/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code CleaningTankDetails} */
public final class CleaningTankDetails extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:CleaningTankDetails)
    CleaningTankDetailsOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use CleaningTankDetails.newBuilder() to construct.
  private CleaningTankDetails(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private CleaningTankDetails() {
    tankShortName_ = "";
    timeStart_ = "";
    timeEnd_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new CleaningTankDetails();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private CleaningTankDetails(
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
          case 10:
            {
              java.lang.String s = input.readStringRequireUtf8();

              tankShortName_ = s;
              break;
            }
          case 16:
            {
              tankId_ = input.readInt64();
              break;
            }
          case 26:
            {
              java.lang.String s = input.readStringRequireUtf8();

              timeStart_ = s;
              break;
            }
          case 34:
            {
              java.lang.String s = input.readStringRequireUtf8();

              timeEnd_ = s;
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
        .internal_static_CleaningTankDetails_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_CleaningTankDetails_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails.class,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder.class);
  }

  public static final int TANKSHORTNAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object tankShortName_;
  /**
   * <code>string tankShortName = 1;</code>
   *
   * @return The tankShortName.
   */
  public java.lang.String getTankShortName() {
    java.lang.Object ref = tankShortName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      tankShortName_ = s;
      return s;
    }
  }
  /**
   * <code>string tankShortName = 1;</code>
   *
   * @return The bytes for tankShortName.
   */
  public com.google.protobuf.ByteString getTankShortNameBytes() {
    java.lang.Object ref = tankShortName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      tankShortName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TANKID_FIELD_NUMBER = 2;
  private long tankId_;
  /**
   * <code>int64 tankId = 2;</code>
   *
   * @return The tankId.
   */
  public long getTankId() {
    return tankId_;
  }

  public static final int TIMESTART_FIELD_NUMBER = 3;
  private volatile java.lang.Object timeStart_;
  /**
   * <code>string timeStart = 3;</code>
   *
   * @return The timeStart.
   */
  public java.lang.String getTimeStart() {
    java.lang.Object ref = timeStart_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      timeStart_ = s;
      return s;
    }
  }
  /**
   * <code>string timeStart = 3;</code>
   *
   * @return The bytes for timeStart.
   */
  public com.google.protobuf.ByteString getTimeStartBytes() {
    java.lang.Object ref = timeStart_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      timeStart_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TIMEEND_FIELD_NUMBER = 4;
  private volatile java.lang.Object timeEnd_;
  /**
   * <code>string timeEnd = 4;</code>
   *
   * @return The timeEnd.
   */
  public java.lang.String getTimeEnd() {
    java.lang.Object ref = timeEnd_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      timeEnd_ = s;
      return s;
    }
  }
  /**
   * <code>string timeEnd = 4;</code>
   *
   * @return The bytes for timeEnd.
   */
  public com.google.protobuf.ByteString getTimeEndBytes() {
    java.lang.Object ref = timeEnd_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      timeEnd_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (!getTankShortNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, tankShortName_);
    }
    if (tankId_ != 0L) {
      output.writeInt64(2, tankId_);
    }
    if (!getTimeStartBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, timeStart_);
    }
    if (!getTimeEndBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, timeEnd_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getTankShortNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, tankShortName_);
    }
    if (tankId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, tankId_);
    }
    if (!getTimeStartBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, timeStart_);
    }
    if (!getTimeEndBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, timeEnd_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.CleaningTankDetails)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.CleaningTankDetails other =
        (com.cpdss.common.generated.discharge_plan.CleaningTankDetails) obj;

    if (!getTankShortName().equals(other.getTankShortName())) return false;
    if (getTankId() != other.getTankId()) return false;
    if (!getTimeStart().equals(other.getTimeStart())) return false;
    if (!getTimeEnd().equals(other.getTimeEnd())) return false;
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
    hash = (37 * hash) + TANKSHORTNAME_FIELD_NUMBER;
    hash = (53 * hash) + getTankShortName().hashCode();
    hash = (37 * hash) + TANKID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getTankId());
    hash = (37 * hash) + TIMESTART_FIELD_NUMBER;
    hash = (53 * hash) + getTimeStart().hashCode();
    hash = (37 * hash) + TIMEEND_FIELD_NUMBER;
    hash = (53 * hash) + getTimeEnd().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails parseFrom(
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
      com.cpdss.common.generated.discharge_plan.CleaningTankDetails prototype) {
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
  /** Protobuf type {@code CleaningTankDetails} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:CleaningTankDetails)
      com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CleaningTankDetails_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CleaningTankDetails_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.CleaningTankDetails.class,
              com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.CleaningTankDetails.newBuilder()
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
      tankShortName_ = "";

      tankId_ = 0L;

      timeStart_ = "";

      timeEnd_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CleaningTankDetails_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.CleaningTankDetails.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails build() {
      com.cpdss.common.generated.discharge_plan.CleaningTankDetails result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails buildPartial() {
      com.cpdss.common.generated.discharge_plan.CleaningTankDetails result =
          new com.cpdss.common.generated.discharge_plan.CleaningTankDetails(this);
      result.tankShortName_ = tankShortName_;
      result.tankId_ = tankId_;
      result.timeStart_ = timeStart_;
      result.timeEnd_ = timeEnd_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.CleaningTankDetails) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.CleaningTankDetails) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.CleaningTankDetails other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.CleaningTankDetails.getDefaultInstance())
        return this;
      if (!other.getTankShortName().isEmpty()) {
        tankShortName_ = other.tankShortName_;
        onChanged();
      }
      if (other.getTankId() != 0L) {
        setTankId(other.getTankId());
      }
      if (!other.getTimeStart().isEmpty()) {
        timeStart_ = other.timeStart_;
        onChanged();
      }
      if (!other.getTimeEnd().isEmpty()) {
        timeEnd_ = other.timeEnd_;
        onChanged();
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
      com.cpdss.common.generated.discharge_plan.CleaningTankDetails parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.CleaningTankDetails)
                e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object tankShortName_ = "";
    /**
     * <code>string tankShortName = 1;</code>
     *
     * @return The tankShortName.
     */
    public java.lang.String getTankShortName() {
      java.lang.Object ref = tankShortName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tankShortName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string tankShortName = 1;</code>
     *
     * @return The bytes for tankShortName.
     */
    public com.google.protobuf.ByteString getTankShortNameBytes() {
      java.lang.Object ref = tankShortName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        tankShortName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string tankShortName = 1;</code>
     *
     * @param value The tankShortName to set.
     * @return This builder for chaining.
     */
    public Builder setTankShortName(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      tankShortName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string tankShortName = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTankShortName() {

      tankShortName_ = getDefaultInstance().getTankShortName();
      onChanged();
      return this;
    }
    /**
     * <code>string tankShortName = 1;</code>
     *
     * @param value The bytes for tankShortName to set.
     * @return This builder for chaining.
     */
    public Builder setTankShortNameBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      tankShortName_ = value;
      onChanged();
      return this;
    }

    private long tankId_;
    /**
     * <code>int64 tankId = 2;</code>
     *
     * @return The tankId.
     */
    public long getTankId() {
      return tankId_;
    }
    /**
     * <code>int64 tankId = 2;</code>
     *
     * @param value The tankId to set.
     * @return This builder for chaining.
     */
    public Builder setTankId(long value) {

      tankId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 tankId = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTankId() {

      tankId_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object timeStart_ = "";
    /**
     * <code>string timeStart = 3;</code>
     *
     * @return The timeStart.
     */
    public java.lang.String getTimeStart() {
      java.lang.Object ref = timeStart_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        timeStart_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string timeStart = 3;</code>
     *
     * @return The bytes for timeStart.
     */
    public com.google.protobuf.ByteString getTimeStartBytes() {
      java.lang.Object ref = timeStart_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        timeStart_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string timeStart = 3;</code>
     *
     * @param value The timeStart to set.
     * @return This builder for chaining.
     */
    public Builder setTimeStart(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      timeStart_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string timeStart = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTimeStart() {

      timeStart_ = getDefaultInstance().getTimeStart();
      onChanged();
      return this;
    }
    /**
     * <code>string timeStart = 3;</code>
     *
     * @param value The bytes for timeStart to set.
     * @return This builder for chaining.
     */
    public Builder setTimeStartBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      timeStart_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object timeEnd_ = "";
    /**
     * <code>string timeEnd = 4;</code>
     *
     * @return The timeEnd.
     */
    public java.lang.String getTimeEnd() {
      java.lang.Object ref = timeEnd_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        timeEnd_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string timeEnd = 4;</code>
     *
     * @return The bytes for timeEnd.
     */
    public com.google.protobuf.ByteString getTimeEndBytes() {
      java.lang.Object ref = timeEnd_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        timeEnd_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string timeEnd = 4;</code>
     *
     * @param value The timeEnd to set.
     * @return This builder for chaining.
     */
    public Builder setTimeEnd(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      timeEnd_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string timeEnd = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTimeEnd() {

      timeEnd_ = getDefaultInstance().getTimeEnd();
      onChanged();
      return this;
    }
    /**
     * <code>string timeEnd = 4;</code>
     *
     * @param value The bytes for timeEnd to set.
     * @return This builder for chaining.
     */
    public Builder setTimeEndBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      timeEnd_ = value;
      onChanged();
      return this;
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

    // @@protoc_insertion_point(builder_scope:CleaningTankDetails)
  }

  // @@protoc_insertion_point(class_scope:CleaningTankDetails)
  private static final com.cpdss.common.generated.discharge_plan.CleaningTankDetails
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.CleaningTankDetails();
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTankDetails getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CleaningTankDetails> PARSER =
      new com.google.protobuf.AbstractParser<CleaningTankDetails>() {
        @java.lang.Override
        public CleaningTankDetails parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new CleaningTankDetails(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<CleaningTankDetails> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CleaningTankDetails> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.CleaningTankDetails getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
