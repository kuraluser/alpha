/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargeDelay} */
public final class DischargeDelay extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargeDelay)
    DischargeDelayOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargeDelay.newBuilder() to construct.
  private DischargeDelay(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargeDelay() {
    reasons_ = java.util.Collections.emptyList();
    delays_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargeDelay();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargeDelay(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                reasons_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons>();
                mutable_bitField0_ |= 0x00000001;
              }
              reasons_.add(
                  input.readMessage(
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons
                          .parser(),
                      extensionRegistry));
              break;
            }
          case 18:
            {
              if (!((mutable_bitField0_ & 0x00000002) != 0)) {
                delays_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.discharge_plan.DischargeDelays>();
                mutable_bitField0_ |= 0x00000002;
              }
              delays_.add(
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.DischargeDelays.parser(),
                      extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        reasons_ = java.util.Collections.unmodifiableList(reasons_);
      }
      if (((mutable_bitField0_ & 0x00000002) != 0)) {
        delays_ = java.util.Collections.unmodifiableList(delays_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeDelay_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeDelay_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargeDelay.class,
            com.cpdss.common.generated.discharge_plan.DischargeDelay.Builder.class);
  }

  public static final int REASONS_FIELD_NUMBER = 1;
  private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons>
      reasons_;
  /**
   *
   *
   * <pre>
   * master data
   * </pre>
   *
   * <code>repeated .DelayReasons reasons = 1;</code>
   */
  public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons>
      getReasonsList() {
    return reasons_;
  }
  /**
   *
   *
   * <pre>
   * master data
   * </pre>
   *
   * <code>repeated .DelayReasons reasons = 1;</code>
   */
  public java.util.List<
          ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasonsOrBuilder>
      getReasonsOrBuilderList() {
    return reasons_;
  }
  /**
   *
   *
   * <pre>
   * master data
   * </pre>
   *
   * <code>repeated .DelayReasons reasons = 1;</code>
   */
  public int getReasonsCount() {
    return reasons_.size();
  }
  /**
   *
   *
   * <pre>
   * master data
   * </pre>
   *
   * <code>repeated .DelayReasons reasons = 1;</code>
   */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons getReasons(
      int index) {
    return reasons_.get(index);
  }
  /**
   *
   *
   * <pre>
   * master data
   * </pre>
   *
   * <code>repeated .DelayReasons reasons = 1;</code>
   */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasonsOrBuilder
      getReasonsOrBuilder(int index) {
    return reasons_.get(index);
  }

  public static final int DELAYS_FIELD_NUMBER = 2;
  private java.util.List<com.cpdss.common.generated.discharge_plan.DischargeDelays> delays_;
  /**
   *
   *
   * <pre>
   * user data
   * </pre>
   *
   * <code>repeated .DischargeDelays delays = 2;</code>
   */
  public java.util.List<com.cpdss.common.generated.discharge_plan.DischargeDelays> getDelaysList() {
    return delays_;
  }
  /**
   *
   *
   * <pre>
   * user data
   * </pre>
   *
   * <code>repeated .DischargeDelays delays = 2;</code>
   */
  public java.util.List<
          ? extends com.cpdss.common.generated.discharge_plan.DischargeDelaysOrBuilder>
      getDelaysOrBuilderList() {
    return delays_;
  }
  /**
   *
   *
   * <pre>
   * user data
   * </pre>
   *
   * <code>repeated .DischargeDelays delays = 2;</code>
   */
  public int getDelaysCount() {
    return delays_.size();
  }
  /**
   *
   *
   * <pre>
   * user data
   * </pre>
   *
   * <code>repeated .DischargeDelays delays = 2;</code>
   */
  public com.cpdss.common.generated.discharge_plan.DischargeDelays getDelays(int index) {
    return delays_.get(index);
  }
  /**
   *
   *
   * <pre>
   * user data
   * </pre>
   *
   * <code>repeated .DischargeDelays delays = 2;</code>
   */
  public com.cpdss.common.generated.discharge_plan.DischargeDelaysOrBuilder getDelaysOrBuilder(
      int index) {
    return delays_.get(index);
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
    for (int i = 0; i < reasons_.size(); i++) {
      output.writeMessage(1, reasons_.get(i));
    }
    for (int i = 0; i < delays_.size(); i++) {
      output.writeMessage(2, delays_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < reasons_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, reasons_.get(i));
    }
    for (int i = 0; i < delays_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, delays_.get(i));
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargeDelay)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargeDelay other =
        (com.cpdss.common.generated.discharge_plan.DischargeDelay) obj;

    if (!getReasonsList().equals(other.getReasonsList())) return false;
    if (!getDelaysList().equals(other.getDelaysList())) return false;
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
    if (getReasonsCount() > 0) {
      hash = (37 * hash) + REASONS_FIELD_NUMBER;
      hash = (53 * hash) + getReasonsList().hashCode();
    }
    if (getDelaysCount() > 0) {
      hash = (37 * hash) + DELAYS_FIELD_NUMBER;
      hash = (53 * hash) + getDelaysList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargeDelay prototype) {
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
  /** Protobuf type {@code DischargeDelay} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargeDelay)
      com.cpdss.common.generated.discharge_plan.DischargeDelayOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeDelay_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeDelay_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargeDelay.class,
              com.cpdss.common.generated.discharge_plan.DischargeDelay.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.DischargeDelay.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getReasonsFieldBuilder();
        getDelaysFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (reasonsBuilder_ == null) {
        reasons_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        reasonsBuilder_.clear();
      }
      if (delaysBuilder_ == null) {
        delays_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        delaysBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeDelay_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeDelay getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargeDelay.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeDelay build() {
      com.cpdss.common.generated.discharge_plan.DischargeDelay result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeDelay buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargeDelay result =
          new com.cpdss.common.generated.discharge_plan.DischargeDelay(this);
      int from_bitField0_ = bitField0_;
      if (reasonsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          reasons_ = java.util.Collections.unmodifiableList(reasons_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.reasons_ = reasons_;
      } else {
        result.reasons_ = reasonsBuilder_.build();
      }
      if (delaysBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0)) {
          delays_ = java.util.Collections.unmodifiableList(delays_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.delays_ = delays_;
      } else {
        result.delays_ = delaysBuilder_.build();
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargeDelay) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.DischargeDelay) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.DischargeDelay other) {
      if (other == com.cpdss.common.generated.discharge_plan.DischargeDelay.getDefaultInstance())
        return this;
      if (reasonsBuilder_ == null) {
        if (!other.reasons_.isEmpty()) {
          if (reasons_.isEmpty()) {
            reasons_ = other.reasons_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureReasonsIsMutable();
            reasons_.addAll(other.reasons_);
          }
          onChanged();
        }
      } else {
        if (!other.reasons_.isEmpty()) {
          if (reasonsBuilder_.isEmpty()) {
            reasonsBuilder_.dispose();
            reasonsBuilder_ = null;
            reasons_ = other.reasons_;
            bitField0_ = (bitField0_ & ~0x00000001);
            reasonsBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getReasonsFieldBuilder()
                    : null;
          } else {
            reasonsBuilder_.addAllMessages(other.reasons_);
          }
        }
      }
      if (delaysBuilder_ == null) {
        if (!other.delays_.isEmpty()) {
          if (delays_.isEmpty()) {
            delays_ = other.delays_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureDelaysIsMutable();
            delays_.addAll(other.delays_);
          }
          onChanged();
        }
      } else {
        if (!other.delays_.isEmpty()) {
          if (delaysBuilder_.isEmpty()) {
            delaysBuilder_.dispose();
            delaysBuilder_ = null;
            delays_ = other.delays_;
            bitField0_ = (bitField0_ & ~0x00000002);
            delaysBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getDelaysFieldBuilder()
                    : null;
          } else {
            delaysBuilder_.addAllMessages(other.delays_);
          }
        }
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
      com.cpdss.common.generated.discharge_plan.DischargeDelay parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargeDelay) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int bitField0_;

    private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons>
        reasons_ = java.util.Collections.emptyList();

    private void ensureReasonsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        reasons_ =
            new java.util.ArrayList<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons>(reasons_);
        bitField0_ |= 0x00000001;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasonsOrBuilder>
        reasonsBuilder_;

    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons>
        getReasonsList() {
      if (reasonsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(reasons_);
      } else {
        return reasonsBuilder_.getMessageList();
      }
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public int getReasonsCount() {
      if (reasonsBuilder_ == null) {
        return reasons_.size();
      } else {
        return reasonsBuilder_.getCount();
      }
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons getReasons(
        int index) {
      if (reasonsBuilder_ == null) {
        return reasons_.get(index);
      } else {
        return reasonsBuilder_.getMessage(index);
      }
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public Builder setReasons(
        int index, com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons value) {
      if (reasonsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureReasonsIsMutable();
        reasons_.set(index, value);
        onChanged();
      } else {
        reasonsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public Builder setReasons(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons.Builder
            builderForValue) {
      if (reasonsBuilder_ == null) {
        ensureReasonsIsMutable();
        reasons_.set(index, builderForValue.build());
        onChanged();
      } else {
        reasonsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public Builder addReasons(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons value) {
      if (reasonsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureReasonsIsMutable();
        reasons_.add(value);
        onChanged();
      } else {
        reasonsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public Builder addReasons(
        int index, com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons value) {
      if (reasonsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureReasonsIsMutable();
        reasons_.add(index, value);
        onChanged();
      } else {
        reasonsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public Builder addReasons(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons.Builder
            builderForValue) {
      if (reasonsBuilder_ == null) {
        ensureReasonsIsMutable();
        reasons_.add(builderForValue.build());
        onChanged();
      } else {
        reasonsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public Builder addReasons(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons.Builder
            builderForValue) {
      if (reasonsBuilder_ == null) {
        ensureReasonsIsMutable();
        reasons_.add(index, builderForValue.build());
        onChanged();
      } else {
        reasonsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public Builder addAllReasons(
        java.lang.Iterable<
                ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons>
            values) {
      if (reasonsBuilder_ == null) {
        ensureReasonsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, reasons_);
        onChanged();
      } else {
        reasonsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public Builder clearReasons() {
      if (reasonsBuilder_ == null) {
        reasons_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        reasonsBuilder_.clear();
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public Builder removeReasons(int index) {
      if (reasonsBuilder_ == null) {
        ensureReasonsIsMutable();
        reasons_.remove(index);
        onChanged();
      } else {
        reasonsBuilder_.remove(index);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons.Builder
        getReasonsBuilder(int index) {
      return getReasonsFieldBuilder().getBuilder(index);
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasonsOrBuilder
        getReasonsOrBuilder(int index) {
      if (reasonsBuilder_ == null) {
        return reasons_.get(index);
      } else {
        return reasonsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public java.util.List<
            ? extends
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasonsOrBuilder>
        getReasonsOrBuilderList() {
      if (reasonsBuilder_ != null) {
        return reasonsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(reasons_);
      }
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons.Builder
        addReasonsBuilder() {
      return getReasonsFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons
                  .getDefaultInstance());
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons.Builder
        addReasonsBuilder(int index) {
      return getReasonsFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons
                  .getDefaultInstance());
    }
    /**
     *
     *
     * <pre>
     * master data
     * </pre>
     *
     * <code>repeated .DelayReasons reasons = 1;</code>
     */
    public java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons.Builder>
        getReasonsBuilderList() {
      return getReasonsFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasonsOrBuilder>
        getReasonsFieldBuilder() {
      if (reasonsBuilder_ == null) {
        reasonsBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasons.Builder,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.DelayReasonsOrBuilder>(
                reasons_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
        reasons_ = null;
      }
      return reasonsBuilder_;
    }

    private java.util.List<com.cpdss.common.generated.discharge_plan.DischargeDelays> delays_ =
        java.util.Collections.emptyList();

    private void ensureDelaysIsMutable() {
      if (!((bitField0_ & 0x00000002) != 0)) {
        delays_ =
            new java.util.ArrayList<com.cpdss.common.generated.discharge_plan.DischargeDelays>(
                delays_);
        bitField0_ |= 0x00000002;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DischargeDelays,
            com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder,
            com.cpdss.common.generated.discharge_plan.DischargeDelaysOrBuilder>
        delaysBuilder_;

    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public java.util.List<com.cpdss.common.generated.discharge_plan.DischargeDelays>
        getDelaysList() {
      if (delaysBuilder_ == null) {
        return java.util.Collections.unmodifiableList(delays_);
      } else {
        return delaysBuilder_.getMessageList();
      }
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public int getDelaysCount() {
      if (delaysBuilder_ == null) {
        return delays_.size();
      } else {
        return delaysBuilder_.getCount();
      }
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public com.cpdss.common.generated.discharge_plan.DischargeDelays getDelays(int index) {
      if (delaysBuilder_ == null) {
        return delays_.get(index);
      } else {
        return delaysBuilder_.getMessage(index);
      }
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public Builder setDelays(
        int index, com.cpdss.common.generated.discharge_plan.DischargeDelays value) {
      if (delaysBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDelaysIsMutable();
        delays_.set(index, value);
        onChanged();
      } else {
        delaysBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public Builder setDelays(
        int index,
        com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder builderForValue) {
      if (delaysBuilder_ == null) {
        ensureDelaysIsMutable();
        delays_.set(index, builderForValue.build());
        onChanged();
      } else {
        delaysBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public Builder addDelays(com.cpdss.common.generated.discharge_plan.DischargeDelays value) {
      if (delaysBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDelaysIsMutable();
        delays_.add(value);
        onChanged();
      } else {
        delaysBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public Builder addDelays(
        int index, com.cpdss.common.generated.discharge_plan.DischargeDelays value) {
      if (delaysBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDelaysIsMutable();
        delays_.add(index, value);
        onChanged();
      } else {
        delaysBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public Builder addDelays(
        com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder builderForValue) {
      if (delaysBuilder_ == null) {
        ensureDelaysIsMutable();
        delays_.add(builderForValue.build());
        onChanged();
      } else {
        delaysBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public Builder addDelays(
        int index,
        com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder builderForValue) {
      if (delaysBuilder_ == null) {
        ensureDelaysIsMutable();
        delays_.add(index, builderForValue.build());
        onChanged();
      } else {
        delaysBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public Builder addAllDelays(
        java.lang.Iterable<? extends com.cpdss.common.generated.discharge_plan.DischargeDelays>
            values) {
      if (delaysBuilder_ == null) {
        ensureDelaysIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, delays_);
        onChanged();
      } else {
        delaysBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public Builder clearDelays() {
      if (delaysBuilder_ == null) {
        delays_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        delaysBuilder_.clear();
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public Builder removeDelays(int index) {
      if (delaysBuilder_ == null) {
        ensureDelaysIsMutable();
        delays_.remove(index);
        onChanged();
      } else {
        delaysBuilder_.remove(index);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder getDelaysBuilder(
        int index) {
      return getDelaysFieldBuilder().getBuilder(index);
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public com.cpdss.common.generated.discharge_plan.DischargeDelaysOrBuilder getDelaysOrBuilder(
        int index) {
      if (delaysBuilder_ == null) {
        return delays_.get(index);
      } else {
        return delaysBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public java.util.List<
            ? extends com.cpdss.common.generated.discharge_plan.DischargeDelaysOrBuilder>
        getDelaysOrBuilderList() {
      if (delaysBuilder_ != null) {
        return delaysBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(delays_);
      }
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder addDelaysBuilder() {
      return getDelaysFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.discharge_plan.DischargeDelays.getDefaultInstance());
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder addDelaysBuilder(
        int index) {
      return getDelaysFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.discharge_plan.DischargeDelays.getDefaultInstance());
    }
    /**
     *
     *
     * <pre>
     * user data
     * </pre>
     *
     * <code>repeated .DischargeDelays delays = 2;</code>
     */
    public java.util.List<com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder>
        getDelaysBuilderList() {
      return getDelaysFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DischargeDelays,
            com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder,
            com.cpdss.common.generated.discharge_plan.DischargeDelaysOrBuilder>
        getDelaysFieldBuilder() {
      if (delaysBuilder_ == null) {
        delaysBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.DischargeDelays,
                com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder,
                com.cpdss.common.generated.discharge_plan.DischargeDelaysOrBuilder>(
                delays_, ((bitField0_ & 0x00000002) != 0), getParentForChildren(), isClean());
        delays_ = null;
      }
      return delaysBuilder_;
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

    // @@protoc_insertion_point(builder_scope:DischargeDelay)
  }

  // @@protoc_insertion_point(class_scope:DischargeDelay)
  private static final com.cpdss.common.generated.discharge_plan.DischargeDelay DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargeDelay();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelay getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargeDelay> PARSER =
      new com.google.protobuf.AbstractParser<DischargeDelay>() {
        @java.lang.Override
        public DischargeDelay parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargeDelay(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargeDelay> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargeDelay> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargeDelay getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
