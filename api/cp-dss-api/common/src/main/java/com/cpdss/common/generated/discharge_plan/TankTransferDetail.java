/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code TankTransferDetail} */
public final class TankTransferDetail extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:TankTransferDetail)
    TankTransferDetailOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use TankTransferDetail.newBuilder() to construct.
  private TankTransferDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private TankTransferDetail() {
    startQuantity_ = "";
    endQuantity_ = "";
    startUllage_ = "";
    endUllage_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new TankTransferDetail();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private TankTransferDetail(
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
              tankId_ = input.readInt64();
              break;
            }
          case 18:
            {
              java.lang.String s = input.readStringRequireUtf8();

              startQuantity_ = s;
              break;
            }
          case 26:
            {
              java.lang.String s = input.readStringRequireUtf8();

              endQuantity_ = s;
              break;
            }
          case 34:
            {
              java.lang.String s = input.readStringRequireUtf8();

              startUllage_ = s;
              break;
            }
          case 42:
            {
              java.lang.String s = input.readStringRequireUtf8();

              endUllage_ = s;
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
        .internal_static_TankTransferDetail_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_TankTransferDetail_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.TankTransferDetail.class,
            com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder.class);
  }

  public static final int TANKID_FIELD_NUMBER = 1;
  private long tankId_;
  /**
   * <code>int64 tankId = 1;</code>
   *
   * @return The tankId.
   */
  public long getTankId() {
    return tankId_;
  }

  public static final int STARTQUANTITY_FIELD_NUMBER = 2;
  private volatile java.lang.Object startQuantity_;
  /**
   * <code>string startQuantity = 2;</code>
   *
   * @return The startQuantity.
   */
  public java.lang.String getStartQuantity() {
    java.lang.Object ref = startQuantity_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      startQuantity_ = s;
      return s;
    }
  }
  /**
   * <code>string startQuantity = 2;</code>
   *
   * @return The bytes for startQuantity.
   */
  public com.google.protobuf.ByteString getStartQuantityBytes() {
    java.lang.Object ref = startQuantity_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      startQuantity_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ENDQUANTITY_FIELD_NUMBER = 3;
  private volatile java.lang.Object endQuantity_;
  /**
   * <code>string endQuantity = 3;</code>
   *
   * @return The endQuantity.
   */
  public java.lang.String getEndQuantity() {
    java.lang.Object ref = endQuantity_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      endQuantity_ = s;
      return s;
    }
  }
  /**
   * <code>string endQuantity = 3;</code>
   *
   * @return The bytes for endQuantity.
   */
  public com.google.protobuf.ByteString getEndQuantityBytes() {
    java.lang.Object ref = endQuantity_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      endQuantity_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int STARTULLAGE_FIELD_NUMBER = 4;
  private volatile java.lang.Object startUllage_;
  /**
   * <code>string startUllage = 4;</code>
   *
   * @return The startUllage.
   */
  public java.lang.String getStartUllage() {
    java.lang.Object ref = startUllage_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      startUllage_ = s;
      return s;
    }
  }
  /**
   * <code>string startUllage = 4;</code>
   *
   * @return The bytes for startUllage.
   */
  public com.google.protobuf.ByteString getStartUllageBytes() {
    java.lang.Object ref = startUllage_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      startUllage_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ENDULLAGE_FIELD_NUMBER = 5;
  private volatile java.lang.Object endUllage_;
  /**
   * <code>string endUllage = 5;</code>
   *
   * @return The endUllage.
   */
  public java.lang.String getEndUllage() {
    java.lang.Object ref = endUllage_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      endUllage_ = s;
      return s;
    }
  }
  /**
   * <code>string endUllage = 5;</code>
   *
   * @return The bytes for endUllage.
   */
  public com.google.protobuf.ByteString getEndUllageBytes() {
    java.lang.Object ref = endUllage_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      endUllage_ = b;
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
    if (tankId_ != 0L) {
      output.writeInt64(1, tankId_);
    }
    if (!getStartQuantityBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, startQuantity_);
    }
    if (!getEndQuantityBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, endQuantity_);
    }
    if (!getStartUllageBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, startUllage_);
    }
    if (!getEndUllageBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, endUllage_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (tankId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, tankId_);
    }
    if (!getStartQuantityBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, startQuantity_);
    }
    if (!getEndQuantityBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, endQuantity_);
    }
    if (!getStartUllageBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, startUllage_);
    }
    if (!getEndUllageBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, endUllage_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.TankTransferDetail)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.TankTransferDetail other =
        (com.cpdss.common.generated.discharge_plan.TankTransferDetail) obj;

    if (getTankId() != other.getTankId()) return false;
    if (!getStartQuantity().equals(other.getStartQuantity())) return false;
    if (!getEndQuantity().equals(other.getEndQuantity())) return false;
    if (!getStartUllage().equals(other.getStartUllage())) return false;
    if (!getEndUllage().equals(other.getEndUllage())) return false;
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
    hash = (37 * hash) + TANKID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getTankId());
    hash = (37 * hash) + STARTQUANTITY_FIELD_NUMBER;
    hash = (53 * hash) + getStartQuantity().hashCode();
    hash = (37 * hash) + ENDQUANTITY_FIELD_NUMBER;
    hash = (53 * hash) + getEndQuantity().hashCode();
    hash = (37 * hash) + STARTULLAGE_FIELD_NUMBER;
    hash = (53 * hash) + getStartUllage().hashCode();
    hash = (37 * hash) + ENDULLAGE_FIELD_NUMBER;
    hash = (53 * hash) + getEndUllage().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail parseFrom(
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
      com.cpdss.common.generated.discharge_plan.TankTransferDetail prototype) {
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
  /** Protobuf type {@code TankTransferDetail} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:TankTransferDetail)
      com.cpdss.common.generated.discharge_plan.TankTransferDetailOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_TankTransferDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_TankTransferDetail_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.TankTransferDetail.class,
              com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.TankTransferDetail.newBuilder()
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
      tankId_ = 0L;

      startQuantity_ = "";

      endQuantity_ = "";

      startUllage_ = "";

      endUllage_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_TankTransferDetail_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.TankTransferDetail
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.TankTransferDetail.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.TankTransferDetail build() {
      com.cpdss.common.generated.discharge_plan.TankTransferDetail result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.TankTransferDetail buildPartial() {
      com.cpdss.common.generated.discharge_plan.TankTransferDetail result =
          new com.cpdss.common.generated.discharge_plan.TankTransferDetail(this);
      result.tankId_ = tankId_;
      result.startQuantity_ = startQuantity_;
      result.endQuantity_ = endQuantity_;
      result.startUllage_ = startUllage_;
      result.endUllage_ = endUllage_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.TankTransferDetail) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.TankTransferDetail) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.TankTransferDetail other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.TankTransferDetail.getDefaultInstance())
        return this;
      if (other.getTankId() != 0L) {
        setTankId(other.getTankId());
      }
      if (!other.getStartQuantity().isEmpty()) {
        startQuantity_ = other.startQuantity_;
        onChanged();
      }
      if (!other.getEndQuantity().isEmpty()) {
        endQuantity_ = other.endQuantity_;
        onChanged();
      }
      if (!other.getStartUllage().isEmpty()) {
        startUllage_ = other.startUllage_;
        onChanged();
      }
      if (!other.getEndUllage().isEmpty()) {
        endUllage_ = other.endUllage_;
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
      com.cpdss.common.generated.discharge_plan.TankTransferDetail parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.TankTransferDetail) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long tankId_;
    /**
     * <code>int64 tankId = 1;</code>
     *
     * @return The tankId.
     */
    public long getTankId() {
      return tankId_;
    }
    /**
     * <code>int64 tankId = 1;</code>
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
     * <code>int64 tankId = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTankId() {

      tankId_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object startQuantity_ = "";
    /**
     * <code>string startQuantity = 2;</code>
     *
     * @return The startQuantity.
     */
    public java.lang.String getStartQuantity() {
      java.lang.Object ref = startQuantity_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        startQuantity_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string startQuantity = 2;</code>
     *
     * @return The bytes for startQuantity.
     */
    public com.google.protobuf.ByteString getStartQuantityBytes() {
      java.lang.Object ref = startQuantity_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        startQuantity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string startQuantity = 2;</code>
     *
     * @param value The startQuantity to set.
     * @return This builder for chaining.
     */
    public Builder setStartQuantity(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      startQuantity_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string startQuantity = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearStartQuantity() {

      startQuantity_ = getDefaultInstance().getStartQuantity();
      onChanged();
      return this;
    }
    /**
     * <code>string startQuantity = 2;</code>
     *
     * @param value The bytes for startQuantity to set.
     * @return This builder for chaining.
     */
    public Builder setStartQuantityBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      startQuantity_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object endQuantity_ = "";
    /**
     * <code>string endQuantity = 3;</code>
     *
     * @return The endQuantity.
     */
    public java.lang.String getEndQuantity() {
      java.lang.Object ref = endQuantity_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        endQuantity_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string endQuantity = 3;</code>
     *
     * @return The bytes for endQuantity.
     */
    public com.google.protobuf.ByteString getEndQuantityBytes() {
      java.lang.Object ref = endQuantity_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        endQuantity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string endQuantity = 3;</code>
     *
     * @param value The endQuantity to set.
     * @return This builder for chaining.
     */
    public Builder setEndQuantity(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      endQuantity_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string endQuantity = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearEndQuantity() {

      endQuantity_ = getDefaultInstance().getEndQuantity();
      onChanged();
      return this;
    }
    /**
     * <code>string endQuantity = 3;</code>
     *
     * @param value The bytes for endQuantity to set.
     * @return This builder for chaining.
     */
    public Builder setEndQuantityBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      endQuantity_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object startUllage_ = "";
    /**
     * <code>string startUllage = 4;</code>
     *
     * @return The startUllage.
     */
    public java.lang.String getStartUllage() {
      java.lang.Object ref = startUllage_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        startUllage_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string startUllage = 4;</code>
     *
     * @return The bytes for startUllage.
     */
    public com.google.protobuf.ByteString getStartUllageBytes() {
      java.lang.Object ref = startUllage_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        startUllage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string startUllage = 4;</code>
     *
     * @param value The startUllage to set.
     * @return This builder for chaining.
     */
    public Builder setStartUllage(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      startUllage_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string startUllage = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearStartUllage() {

      startUllage_ = getDefaultInstance().getStartUllage();
      onChanged();
      return this;
    }
    /**
     * <code>string startUllage = 4;</code>
     *
     * @param value The bytes for startUllage to set.
     * @return This builder for chaining.
     */
    public Builder setStartUllageBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      startUllage_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object endUllage_ = "";
    /**
     * <code>string endUllage = 5;</code>
     *
     * @return The endUllage.
     */
    public java.lang.String getEndUllage() {
      java.lang.Object ref = endUllage_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        endUllage_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string endUllage = 5;</code>
     *
     * @return The bytes for endUllage.
     */
    public com.google.protobuf.ByteString getEndUllageBytes() {
      java.lang.Object ref = endUllage_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        endUllage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string endUllage = 5;</code>
     *
     * @param value The endUllage to set.
     * @return This builder for chaining.
     */
    public Builder setEndUllage(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      endUllage_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string endUllage = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearEndUllage() {

      endUllage_ = getDefaultInstance().getEndUllage();
      onChanged();
      return this;
    }
    /**
     * <code>string endUllage = 5;</code>
     *
     * @param value The bytes for endUllage to set.
     * @return This builder for chaining.
     */
    public Builder setEndUllageBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      endUllage_ = value;
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

    // @@protoc_insertion_point(builder_scope:TankTransferDetail)
  }

  // @@protoc_insertion_point(class_scope:TankTransferDetail)
  private static final com.cpdss.common.generated.discharge_plan.TankTransferDetail
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.TankTransferDetail();
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransferDetail getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<TankTransferDetail> PARSER =
      new com.google.protobuf.AbstractParser<TankTransferDetail>() {
        @java.lang.Override
        public TankTransferDetail parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new TankTransferDetail(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<TankTransferDetail> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<TankTransferDetail> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.TankTransferDetail getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
