/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargeRates} */
public final class DischargeRates extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargeRates)
    DischargeRatesOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargeRates.newBuilder() to construct.
  private DischargeRates(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargeRates() {
    initialDischargeRate_ = "";
    maxDischargeRate_ = "";
    minBallastRate_ = "";
    maxBallastRate_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargeRates();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargeRates(
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

              initialDischargeRate_ = s;
              break;
            }
          case 18:
            {
              java.lang.String s = input.readStringRequireUtf8();

              maxDischargeRate_ = s;
              break;
            }
          case 26:
            {
              java.lang.String s = input.readStringRequireUtf8();

              minBallastRate_ = s;
              break;
            }
          case 34:
            {
              java.lang.String s = input.readStringRequireUtf8();

              maxBallastRate_ = s;
              break;
            }
          case 40:
            {
              id_ = input.readInt64();
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
        .internal_static_DischargeRates_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeRates_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargeRates.class,
            com.cpdss.common.generated.discharge_plan.DischargeRates.Builder.class);
  }

  public static final int INITIALDISCHARGERATE_FIELD_NUMBER = 1;
  private volatile java.lang.Object initialDischargeRate_;
  /**
   * <code>string initialDischargeRate = 1;</code>
   *
   * @return The initialDischargeRate.
   */
  public java.lang.String getInitialDischargeRate() {
    java.lang.Object ref = initialDischargeRate_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      initialDischargeRate_ = s;
      return s;
    }
  }
  /**
   * <code>string initialDischargeRate = 1;</code>
   *
   * @return The bytes for initialDischargeRate.
   */
  public com.google.protobuf.ByteString getInitialDischargeRateBytes() {
    java.lang.Object ref = initialDischargeRate_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      initialDischargeRate_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int MAXDISCHARGERATE_FIELD_NUMBER = 2;
  private volatile java.lang.Object maxDischargeRate_;
  /**
   * <code>string maxDischargeRate = 2;</code>
   *
   * @return The maxDischargeRate.
   */
  public java.lang.String getMaxDischargeRate() {
    java.lang.Object ref = maxDischargeRate_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      maxDischargeRate_ = s;
      return s;
    }
  }
  /**
   * <code>string maxDischargeRate = 2;</code>
   *
   * @return The bytes for maxDischargeRate.
   */
  public com.google.protobuf.ByteString getMaxDischargeRateBytes() {
    java.lang.Object ref = maxDischargeRate_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      maxDischargeRate_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int MINBALLASTRATE_FIELD_NUMBER = 3;
  private volatile java.lang.Object minBallastRate_;
  /**
   * <code>string minBallastRate = 3;</code>
   *
   * @return The minBallastRate.
   */
  public java.lang.String getMinBallastRate() {
    java.lang.Object ref = minBallastRate_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      minBallastRate_ = s;
      return s;
    }
  }
  /**
   * <code>string minBallastRate = 3;</code>
   *
   * @return The bytes for minBallastRate.
   */
  public com.google.protobuf.ByteString getMinBallastRateBytes() {
    java.lang.Object ref = minBallastRate_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      minBallastRate_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int MAXBALLASTRATE_FIELD_NUMBER = 4;
  private volatile java.lang.Object maxBallastRate_;
  /**
   * <code>string maxBallastRate = 4;</code>
   *
   * @return The maxBallastRate.
   */
  public java.lang.String getMaxBallastRate() {
    java.lang.Object ref = maxBallastRate_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      maxBallastRate_ = s;
      return s;
    }
  }
  /**
   * <code>string maxBallastRate = 4;</code>
   *
   * @return The bytes for maxBallastRate.
   */
  public com.google.protobuf.ByteString getMaxBallastRateBytes() {
    java.lang.Object ref = maxBallastRate_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      maxBallastRate_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ID_FIELD_NUMBER = 5;
  private long id_;
  /**
   * <code>int64 id = 5;</code>
   *
   * @return The id.
   */
  public long getId() {
    return id_;
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
    if (!getInitialDischargeRateBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, initialDischargeRate_);
    }
    if (!getMaxDischargeRateBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, maxDischargeRate_);
    }
    if (!getMinBallastRateBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, minBallastRate_);
    }
    if (!getMaxBallastRateBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, maxBallastRate_);
    }
    if (id_ != 0L) {
      output.writeInt64(5, id_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getInitialDischargeRateBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, initialDischargeRate_);
    }
    if (!getMaxDischargeRateBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, maxDischargeRate_);
    }
    if (!getMinBallastRateBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, minBallastRate_);
    }
    if (!getMaxBallastRateBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, maxBallastRate_);
    }
    if (id_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(5, id_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargeRates)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargeRates other =
        (com.cpdss.common.generated.discharge_plan.DischargeRates) obj;

    if (!getInitialDischargeRate().equals(other.getInitialDischargeRate())) return false;
    if (!getMaxDischargeRate().equals(other.getMaxDischargeRate())) return false;
    if (!getMinBallastRate().equals(other.getMinBallastRate())) return false;
    if (!getMaxBallastRate().equals(other.getMaxBallastRate())) return false;
    if (getId() != other.getId()) return false;
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
    hash = (37 * hash) + INITIALDISCHARGERATE_FIELD_NUMBER;
    hash = (53 * hash) + getInitialDischargeRate().hashCode();
    hash = (37 * hash) + MAXDISCHARGERATE_FIELD_NUMBER;
    hash = (53 * hash) + getMaxDischargeRate().hashCode();
    hash = (37 * hash) + MINBALLASTRATE_FIELD_NUMBER;
    hash = (53 * hash) + getMinBallastRate().hashCode();
    hash = (37 * hash) + MAXBALLASTRATE_FIELD_NUMBER;
    hash = (53 * hash) + getMaxBallastRate().hashCode();
    hash = (37 * hash) + ID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargeRates prototype) {
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
  /** Protobuf type {@code DischargeRates} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargeRates)
      com.cpdss.common.generated.discharge_plan.DischargeRatesOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeRates_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeRates_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargeRates.class,
              com.cpdss.common.generated.discharge_plan.DischargeRates.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.DischargeRates.newBuilder()
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
      initialDischargeRate_ = "";

      maxDischargeRate_ = "";

      minBallastRate_ = "";

      maxBallastRate_ = "";

      id_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeRates_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeRates getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargeRates.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeRates build() {
      com.cpdss.common.generated.discharge_plan.DischargeRates result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeRates buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargeRates result =
          new com.cpdss.common.generated.discharge_plan.DischargeRates(this);
      result.initialDischargeRate_ = initialDischargeRate_;
      result.maxDischargeRate_ = maxDischargeRate_;
      result.minBallastRate_ = minBallastRate_;
      result.maxBallastRate_ = maxBallastRate_;
      result.id_ = id_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargeRates) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.DischargeRates) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.DischargeRates other) {
      if (other == com.cpdss.common.generated.discharge_plan.DischargeRates.getDefaultInstance())
        return this;
      if (!other.getInitialDischargeRate().isEmpty()) {
        initialDischargeRate_ = other.initialDischargeRate_;
        onChanged();
      }
      if (!other.getMaxDischargeRate().isEmpty()) {
        maxDischargeRate_ = other.maxDischargeRate_;
        onChanged();
      }
      if (!other.getMinBallastRate().isEmpty()) {
        minBallastRate_ = other.minBallastRate_;
        onChanged();
      }
      if (!other.getMaxBallastRate().isEmpty()) {
        maxBallastRate_ = other.maxBallastRate_;
        onChanged();
      }
      if (other.getId() != 0L) {
        setId(other.getId());
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
      com.cpdss.common.generated.discharge_plan.DischargeRates parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargeRates) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object initialDischargeRate_ = "";
    /**
     * <code>string initialDischargeRate = 1;</code>
     *
     * @return The initialDischargeRate.
     */
    public java.lang.String getInitialDischargeRate() {
      java.lang.Object ref = initialDischargeRate_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        initialDischargeRate_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string initialDischargeRate = 1;</code>
     *
     * @return The bytes for initialDischargeRate.
     */
    public com.google.protobuf.ByteString getInitialDischargeRateBytes() {
      java.lang.Object ref = initialDischargeRate_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        initialDischargeRate_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string initialDischargeRate = 1;</code>
     *
     * @param value The initialDischargeRate to set.
     * @return This builder for chaining.
     */
    public Builder setInitialDischargeRate(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      initialDischargeRate_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string initialDischargeRate = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearInitialDischargeRate() {

      initialDischargeRate_ = getDefaultInstance().getInitialDischargeRate();
      onChanged();
      return this;
    }
    /**
     * <code>string initialDischargeRate = 1;</code>
     *
     * @param value The bytes for initialDischargeRate to set.
     * @return This builder for chaining.
     */
    public Builder setInitialDischargeRateBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      initialDischargeRate_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object maxDischargeRate_ = "";
    /**
     * <code>string maxDischargeRate = 2;</code>
     *
     * @return The maxDischargeRate.
     */
    public java.lang.String getMaxDischargeRate() {
      java.lang.Object ref = maxDischargeRate_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        maxDischargeRate_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string maxDischargeRate = 2;</code>
     *
     * @return The bytes for maxDischargeRate.
     */
    public com.google.protobuf.ByteString getMaxDischargeRateBytes() {
      java.lang.Object ref = maxDischargeRate_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        maxDischargeRate_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string maxDischargeRate = 2;</code>
     *
     * @param value The maxDischargeRate to set.
     * @return This builder for chaining.
     */
    public Builder setMaxDischargeRate(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      maxDischargeRate_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string maxDischargeRate = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearMaxDischargeRate() {

      maxDischargeRate_ = getDefaultInstance().getMaxDischargeRate();
      onChanged();
      return this;
    }
    /**
     * <code>string maxDischargeRate = 2;</code>
     *
     * @param value The bytes for maxDischargeRate to set.
     * @return This builder for chaining.
     */
    public Builder setMaxDischargeRateBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      maxDischargeRate_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object minBallastRate_ = "";
    /**
     * <code>string minBallastRate = 3;</code>
     *
     * @return The minBallastRate.
     */
    public java.lang.String getMinBallastRate() {
      java.lang.Object ref = minBallastRate_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        minBallastRate_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string minBallastRate = 3;</code>
     *
     * @return The bytes for minBallastRate.
     */
    public com.google.protobuf.ByteString getMinBallastRateBytes() {
      java.lang.Object ref = minBallastRate_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        minBallastRate_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string minBallastRate = 3;</code>
     *
     * @param value The minBallastRate to set.
     * @return This builder for chaining.
     */
    public Builder setMinBallastRate(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      minBallastRate_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string minBallastRate = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearMinBallastRate() {

      minBallastRate_ = getDefaultInstance().getMinBallastRate();
      onChanged();
      return this;
    }
    /**
     * <code>string minBallastRate = 3;</code>
     *
     * @param value The bytes for minBallastRate to set.
     * @return This builder for chaining.
     */
    public Builder setMinBallastRateBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      minBallastRate_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object maxBallastRate_ = "";
    /**
     * <code>string maxBallastRate = 4;</code>
     *
     * @return The maxBallastRate.
     */
    public java.lang.String getMaxBallastRate() {
      java.lang.Object ref = maxBallastRate_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        maxBallastRate_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string maxBallastRate = 4;</code>
     *
     * @return The bytes for maxBallastRate.
     */
    public com.google.protobuf.ByteString getMaxBallastRateBytes() {
      java.lang.Object ref = maxBallastRate_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        maxBallastRate_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string maxBallastRate = 4;</code>
     *
     * @param value The maxBallastRate to set.
     * @return This builder for chaining.
     */
    public Builder setMaxBallastRate(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      maxBallastRate_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string maxBallastRate = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearMaxBallastRate() {

      maxBallastRate_ = getDefaultInstance().getMaxBallastRate();
      onChanged();
      return this;
    }
    /**
     * <code>string maxBallastRate = 4;</code>
     *
     * @param value The bytes for maxBallastRate to set.
     * @return This builder for chaining.
     */
    public Builder setMaxBallastRateBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      maxBallastRate_ = value;
      onChanged();
      return this;
    }

    private long id_;
    /**
     * <code>int64 id = 5;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
    }
    /**
     * <code>int64 id = 5;</code>
     *
     * @param value The id to set.
     * @return This builder for chaining.
     */
    public Builder setId(long value) {

      id_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 id = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearId() {

      id_ = 0L;
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

    // @@protoc_insertion_point(builder_scope:DischargeRates)
  }

  // @@protoc_insertion_point(class_scope:DischargeRates)
  private static final com.cpdss.common.generated.discharge_plan.DischargeRates DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargeRates();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRates getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargeRates> PARSER =
      new com.google.protobuf.AbstractParser<DischargeRates>() {
        @java.lang.Override
        public DischargeRates parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargeRates(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargeRates> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargeRates> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargeRates getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
