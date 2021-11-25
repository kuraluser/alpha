/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code CleaningTanks} */
public final class CleaningTanks extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:CleaningTanks)
    CleaningTanksOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use CleaningTanks.newBuilder() to construct.
  private CleaningTanks(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private CleaningTanks() {
    topTank_ = java.util.Collections.emptyList();
    bottomTank_ = java.util.Collections.emptyList();
    fullTank_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new CleaningTanks();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private CleaningTanks(
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
                topTank_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.discharge_plan.CleaningTankDetails>();
                mutable_bitField0_ |= 0x00000001;
              }
              topTank_.add(
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.CleaningTankDetails.parser(),
                      extensionRegistry));
              break;
            }
          case 18:
            {
              if (!((mutable_bitField0_ & 0x00000002) != 0)) {
                bottomTank_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.discharge_plan.CleaningTankDetails>();
                mutable_bitField0_ |= 0x00000002;
              }
              bottomTank_.add(
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.CleaningTankDetails.parser(),
                      extensionRegistry));
              break;
            }
          case 26:
            {
              if (!((mutable_bitField0_ & 0x00000004) != 0)) {
                fullTank_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.discharge_plan.CleaningTankDetails>();
                mutable_bitField0_ |= 0x00000004;
              }
              fullTank_.add(
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.CleaningTankDetails.parser(),
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
        topTank_ = java.util.Collections.unmodifiableList(topTank_);
      }
      if (((mutable_bitField0_ & 0x00000002) != 0)) {
        bottomTank_ = java.util.Collections.unmodifiableList(bottomTank_);
      }
      if (((mutable_bitField0_ & 0x00000004) != 0)) {
        fullTank_ = java.util.Collections.unmodifiableList(fullTank_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_CleaningTanks_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_CleaningTanks_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.CleaningTanks.class,
            com.cpdss.common.generated.discharge_plan.CleaningTanks.Builder.class);
  }

  public static final int TOPTANK_FIELD_NUMBER = 1;
  private java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails> topTank_;
  /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
  public java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
      getTopTankList() {
    return topTank_;
  }
  /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
  public java.util.List<
          ? extends com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
      getTopTankOrBuilderList() {
    return topTank_;
  }
  /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
  public int getTopTankCount() {
    return topTank_.size();
  }
  /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
  public com.cpdss.common.generated.discharge_plan.CleaningTankDetails getTopTank(int index) {
    return topTank_.get(index);
  }
  /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
  public com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder getTopTankOrBuilder(
      int index) {
    return topTank_.get(index);
  }

  public static final int BOTTOMTANK_FIELD_NUMBER = 2;
  private java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails> bottomTank_;
  /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
  public java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
      getBottomTankList() {
    return bottomTank_;
  }
  /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
  public java.util.List<
          ? extends com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
      getBottomTankOrBuilderList() {
    return bottomTank_;
  }
  /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
  public int getBottomTankCount() {
    return bottomTank_.size();
  }
  /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
  public com.cpdss.common.generated.discharge_plan.CleaningTankDetails getBottomTank(int index) {
    return bottomTank_.get(index);
  }
  /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
  public com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder
      getBottomTankOrBuilder(int index) {
    return bottomTank_.get(index);
  }

  public static final int FULLTANK_FIELD_NUMBER = 3;
  private java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails> fullTank_;
  /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
  public java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
      getFullTankList() {
    return fullTank_;
  }
  /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
  public java.util.List<
          ? extends com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
      getFullTankOrBuilderList() {
    return fullTank_;
  }
  /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
  public int getFullTankCount() {
    return fullTank_.size();
  }
  /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
  public com.cpdss.common.generated.discharge_plan.CleaningTankDetails getFullTank(int index) {
    return fullTank_.get(index);
  }
  /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
  public com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder
      getFullTankOrBuilder(int index) {
    return fullTank_.get(index);
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
    for (int i = 0; i < topTank_.size(); i++) {
      output.writeMessage(1, topTank_.get(i));
    }
    for (int i = 0; i < bottomTank_.size(); i++) {
      output.writeMessage(2, bottomTank_.get(i));
    }
    for (int i = 0; i < fullTank_.size(); i++) {
      output.writeMessage(3, fullTank_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < topTank_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, topTank_.get(i));
    }
    for (int i = 0; i < bottomTank_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, bottomTank_.get(i));
    }
    for (int i = 0; i < fullTank_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, fullTank_.get(i));
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.CleaningTanks)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.CleaningTanks other =
        (com.cpdss.common.generated.discharge_plan.CleaningTanks) obj;

    if (!getTopTankList().equals(other.getTopTankList())) return false;
    if (!getBottomTankList().equals(other.getBottomTankList())) return false;
    if (!getFullTankList().equals(other.getFullTankList())) return false;
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
    if (getTopTankCount() > 0) {
      hash = (37 * hash) + TOPTANK_FIELD_NUMBER;
      hash = (53 * hash) + getTopTankList().hashCode();
    }
    if (getBottomTankCount() > 0) {
      hash = (37 * hash) + BOTTOMTANK_FIELD_NUMBER;
      hash = (53 * hash) + getBottomTankList().hashCode();
    }
    if (getFullTankCount() > 0) {
      hash = (37 * hash) + FULLTANK_FIELD_NUMBER;
      hash = (53 * hash) + getFullTankList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks parseFrom(
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
      com.cpdss.common.generated.discharge_plan.CleaningTanks prototype) {
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
  /** Protobuf type {@code CleaningTanks} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:CleaningTanks)
      com.cpdss.common.generated.discharge_plan.CleaningTanksOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CleaningTanks_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CleaningTanks_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.CleaningTanks.class,
              com.cpdss.common.generated.discharge_plan.CleaningTanks.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.CleaningTanks.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getTopTankFieldBuilder();
        getBottomTankFieldBuilder();
        getFullTankFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (topTankBuilder_ == null) {
        topTank_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        topTankBuilder_.clear();
      }
      if (bottomTankBuilder_ == null) {
        bottomTank_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        bottomTankBuilder_.clear();
      }
      if (fullTankBuilder_ == null) {
        fullTank_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
      } else {
        fullTankBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CleaningTanks_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CleaningTanks getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.CleaningTanks.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CleaningTanks build() {
      com.cpdss.common.generated.discharge_plan.CleaningTanks result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CleaningTanks buildPartial() {
      com.cpdss.common.generated.discharge_plan.CleaningTanks result =
          new com.cpdss.common.generated.discharge_plan.CleaningTanks(this);
      int from_bitField0_ = bitField0_;
      if (topTankBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          topTank_ = java.util.Collections.unmodifiableList(topTank_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.topTank_ = topTank_;
      } else {
        result.topTank_ = topTankBuilder_.build();
      }
      if (bottomTankBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0)) {
          bottomTank_ = java.util.Collections.unmodifiableList(bottomTank_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.bottomTank_ = bottomTank_;
      } else {
        result.bottomTank_ = bottomTankBuilder_.build();
      }
      if (fullTankBuilder_ == null) {
        if (((bitField0_ & 0x00000004) != 0)) {
          fullTank_ = java.util.Collections.unmodifiableList(fullTank_);
          bitField0_ = (bitField0_ & ~0x00000004);
        }
        result.fullTank_ = fullTank_;
      } else {
        result.fullTank_ = fullTankBuilder_.build();
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.CleaningTanks) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.CleaningTanks) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.CleaningTanks other) {
      if (other == com.cpdss.common.generated.discharge_plan.CleaningTanks.getDefaultInstance())
        return this;
      if (topTankBuilder_ == null) {
        if (!other.topTank_.isEmpty()) {
          if (topTank_.isEmpty()) {
            topTank_ = other.topTank_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureTopTankIsMutable();
            topTank_.addAll(other.topTank_);
          }
          onChanged();
        }
      } else {
        if (!other.topTank_.isEmpty()) {
          if (topTankBuilder_.isEmpty()) {
            topTankBuilder_.dispose();
            topTankBuilder_ = null;
            topTank_ = other.topTank_;
            bitField0_ = (bitField0_ & ~0x00000001);
            topTankBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getTopTankFieldBuilder()
                    : null;
          } else {
            topTankBuilder_.addAllMessages(other.topTank_);
          }
        }
      }
      if (bottomTankBuilder_ == null) {
        if (!other.bottomTank_.isEmpty()) {
          if (bottomTank_.isEmpty()) {
            bottomTank_ = other.bottomTank_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureBottomTankIsMutable();
            bottomTank_.addAll(other.bottomTank_);
          }
          onChanged();
        }
      } else {
        if (!other.bottomTank_.isEmpty()) {
          if (bottomTankBuilder_.isEmpty()) {
            bottomTankBuilder_.dispose();
            bottomTankBuilder_ = null;
            bottomTank_ = other.bottomTank_;
            bitField0_ = (bitField0_ & ~0x00000002);
            bottomTankBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getBottomTankFieldBuilder()
                    : null;
          } else {
            bottomTankBuilder_.addAllMessages(other.bottomTank_);
          }
        }
      }
      if (fullTankBuilder_ == null) {
        if (!other.fullTank_.isEmpty()) {
          if (fullTank_.isEmpty()) {
            fullTank_ = other.fullTank_;
            bitField0_ = (bitField0_ & ~0x00000004);
          } else {
            ensureFullTankIsMutable();
            fullTank_.addAll(other.fullTank_);
          }
          onChanged();
        }
      } else {
        if (!other.fullTank_.isEmpty()) {
          if (fullTankBuilder_.isEmpty()) {
            fullTankBuilder_.dispose();
            fullTankBuilder_ = null;
            fullTank_ = other.fullTank_;
            bitField0_ = (bitField0_ & ~0x00000004);
            fullTankBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getFullTankFieldBuilder()
                    : null;
          } else {
            fullTankBuilder_.addAllMessages(other.fullTank_);
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
      com.cpdss.common.generated.discharge_plan.CleaningTanks parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.CleaningTanks) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int bitField0_;

    private java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails> topTank_ =
        java.util.Collections.emptyList();

    private void ensureTopTankIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        topTank_ =
            new java.util.ArrayList<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>(
                topTank_);
        bitField0_ |= 0x00000001;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
        topTankBuilder_;

    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
        getTopTankList() {
      if (topTankBuilder_ == null) {
        return java.util.Collections.unmodifiableList(topTank_);
      } else {
        return topTankBuilder_.getMessageList();
      }
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public int getTopTankCount() {
      if (topTankBuilder_ == null) {
        return topTank_.size();
      } else {
        return topTankBuilder_.getCount();
      }
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails getTopTank(int index) {
      if (topTankBuilder_ == null) {
        return topTank_.get(index);
      } else {
        return topTankBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public Builder setTopTank(
        int index, com.cpdss.common.generated.discharge_plan.CleaningTankDetails value) {
      if (topTankBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTopTankIsMutable();
        topTank_.set(index, value);
        onChanged();
      } else {
        topTankBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public Builder setTopTank(
        int index,
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder builderForValue) {
      if (topTankBuilder_ == null) {
        ensureTopTankIsMutable();
        topTank_.set(index, builderForValue.build());
        onChanged();
      } else {
        topTankBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public Builder addTopTank(com.cpdss.common.generated.discharge_plan.CleaningTankDetails value) {
      if (topTankBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTopTankIsMutable();
        topTank_.add(value);
        onChanged();
      } else {
        topTankBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public Builder addTopTank(
        int index, com.cpdss.common.generated.discharge_plan.CleaningTankDetails value) {
      if (topTankBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTopTankIsMutable();
        topTank_.add(index, value);
        onChanged();
      } else {
        topTankBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public Builder addTopTank(
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder builderForValue) {
      if (topTankBuilder_ == null) {
        ensureTopTankIsMutable();
        topTank_.add(builderForValue.build());
        onChanged();
      } else {
        topTankBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public Builder addTopTank(
        int index,
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder builderForValue) {
      if (topTankBuilder_ == null) {
        ensureTopTankIsMutable();
        topTank_.add(index, builderForValue.build());
        onChanged();
      } else {
        topTankBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public Builder addAllTopTank(
        java.lang.Iterable<? extends com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
            values) {
      if (topTankBuilder_ == null) {
        ensureTopTankIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, topTank_);
        onChanged();
      } else {
        topTankBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public Builder clearTopTank() {
      if (topTankBuilder_ == null) {
        topTank_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        topTankBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public Builder removeTopTank(int index) {
      if (topTankBuilder_ == null) {
        ensureTopTankIsMutable();
        topTank_.remove(index);
        onChanged();
      } else {
        topTankBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder getTopTankBuilder(
        int index) {
      return getTopTankFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder
        getTopTankOrBuilder(int index) {
      if (topTankBuilder_ == null) {
        return topTank_.get(index);
      } else {
        return topTankBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public java.util.List<
            ? extends com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
        getTopTankOrBuilderList() {
      if (topTankBuilder_ != null) {
        return topTankBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(topTank_);
      }
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder
        addTopTankBuilder() {
      return getTopTankFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.discharge_plan.CleaningTankDetails.getDefaultInstance());
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder addTopTankBuilder(
        int index) {
      return getTopTankFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.discharge_plan.CleaningTankDetails.getDefaultInstance());
    }
    /** <code>repeated .CleaningTankDetails topTank = 1;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder>
        getTopTankBuilderList() {
      return getTopTankFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
        getTopTankFieldBuilder() {
      if (topTankBuilder_ == null) {
        topTankBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.CleaningTankDetails,
                com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder,
                com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>(
                topTank_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
        topTank_ = null;
      }
      return topTankBuilder_;
    }

    private java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
        bottomTank_ = java.util.Collections.emptyList();

    private void ensureBottomTankIsMutable() {
      if (!((bitField0_ & 0x00000002) != 0)) {
        bottomTank_ =
            new java.util.ArrayList<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>(
                bottomTank_);
        bitField0_ |= 0x00000002;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
        bottomTankBuilder_;

    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
        getBottomTankList() {
      if (bottomTankBuilder_ == null) {
        return java.util.Collections.unmodifiableList(bottomTank_);
      } else {
        return bottomTankBuilder_.getMessageList();
      }
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public int getBottomTankCount() {
      if (bottomTankBuilder_ == null) {
        return bottomTank_.size();
      } else {
        return bottomTankBuilder_.getCount();
      }
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails getBottomTank(int index) {
      if (bottomTankBuilder_ == null) {
        return bottomTank_.get(index);
      } else {
        return bottomTankBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public Builder setBottomTank(
        int index, com.cpdss.common.generated.discharge_plan.CleaningTankDetails value) {
      if (bottomTankBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBottomTankIsMutable();
        bottomTank_.set(index, value);
        onChanged();
      } else {
        bottomTankBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public Builder setBottomTank(
        int index,
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder builderForValue) {
      if (bottomTankBuilder_ == null) {
        ensureBottomTankIsMutable();
        bottomTank_.set(index, builderForValue.build());
        onChanged();
      } else {
        bottomTankBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public Builder addBottomTank(
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails value) {
      if (bottomTankBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBottomTankIsMutable();
        bottomTank_.add(value);
        onChanged();
      } else {
        bottomTankBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public Builder addBottomTank(
        int index, com.cpdss.common.generated.discharge_plan.CleaningTankDetails value) {
      if (bottomTankBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBottomTankIsMutable();
        bottomTank_.add(index, value);
        onChanged();
      } else {
        bottomTankBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public Builder addBottomTank(
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder builderForValue) {
      if (bottomTankBuilder_ == null) {
        ensureBottomTankIsMutable();
        bottomTank_.add(builderForValue.build());
        onChanged();
      } else {
        bottomTankBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public Builder addBottomTank(
        int index,
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder builderForValue) {
      if (bottomTankBuilder_ == null) {
        ensureBottomTankIsMutable();
        bottomTank_.add(index, builderForValue.build());
        onChanged();
      } else {
        bottomTankBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public Builder addAllBottomTank(
        java.lang.Iterable<? extends com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
            values) {
      if (bottomTankBuilder_ == null) {
        ensureBottomTankIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, bottomTank_);
        onChanged();
      } else {
        bottomTankBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public Builder clearBottomTank() {
      if (bottomTankBuilder_ == null) {
        bottomTank_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        bottomTankBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public Builder removeBottomTank(int index) {
      if (bottomTankBuilder_ == null) {
        ensureBottomTankIsMutable();
        bottomTank_.remove(index);
        onChanged();
      } else {
        bottomTankBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder
        getBottomTankBuilder(int index) {
      return getBottomTankFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder
        getBottomTankOrBuilder(int index) {
      if (bottomTankBuilder_ == null) {
        return bottomTank_.get(index);
      } else {
        return bottomTankBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public java.util.List<
            ? extends com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
        getBottomTankOrBuilderList() {
      if (bottomTankBuilder_ != null) {
        return bottomTankBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(bottomTank_);
      }
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder
        addBottomTankBuilder() {
      return getBottomTankFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.discharge_plan.CleaningTankDetails.getDefaultInstance());
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder
        addBottomTankBuilder(int index) {
      return getBottomTankFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.discharge_plan.CleaningTankDetails.getDefaultInstance());
    }
    /** <code>repeated .CleaningTankDetails bottomTank = 2;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder>
        getBottomTankBuilderList() {
      return getBottomTankFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
        getBottomTankFieldBuilder() {
      if (bottomTankBuilder_ == null) {
        bottomTankBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.CleaningTankDetails,
                com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder,
                com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>(
                bottomTank_, ((bitField0_ & 0x00000002) != 0), getParentForChildren(), isClean());
        bottomTank_ = null;
      }
      return bottomTankBuilder_;
    }

    private java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
        fullTank_ = java.util.Collections.emptyList();

    private void ensureFullTankIsMutable() {
      if (!((bitField0_ & 0x00000004) != 0)) {
        fullTank_ =
            new java.util.ArrayList<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>(
                fullTank_);
        bitField0_ |= 0x00000004;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
        fullTankBuilder_;

    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
        getFullTankList() {
      if (fullTankBuilder_ == null) {
        return java.util.Collections.unmodifiableList(fullTank_);
      } else {
        return fullTankBuilder_.getMessageList();
      }
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public int getFullTankCount() {
      if (fullTankBuilder_ == null) {
        return fullTank_.size();
      } else {
        return fullTankBuilder_.getCount();
      }
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails getFullTank(int index) {
      if (fullTankBuilder_ == null) {
        return fullTank_.get(index);
      } else {
        return fullTankBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public Builder setFullTank(
        int index, com.cpdss.common.generated.discharge_plan.CleaningTankDetails value) {
      if (fullTankBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureFullTankIsMutable();
        fullTank_.set(index, value);
        onChanged();
      } else {
        fullTankBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public Builder setFullTank(
        int index,
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder builderForValue) {
      if (fullTankBuilder_ == null) {
        ensureFullTankIsMutable();
        fullTank_.set(index, builderForValue.build());
        onChanged();
      } else {
        fullTankBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public Builder addFullTank(
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails value) {
      if (fullTankBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureFullTankIsMutable();
        fullTank_.add(value);
        onChanged();
      } else {
        fullTankBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public Builder addFullTank(
        int index, com.cpdss.common.generated.discharge_plan.CleaningTankDetails value) {
      if (fullTankBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureFullTankIsMutable();
        fullTank_.add(index, value);
        onChanged();
      } else {
        fullTankBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public Builder addFullTank(
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder builderForValue) {
      if (fullTankBuilder_ == null) {
        ensureFullTankIsMutable();
        fullTank_.add(builderForValue.build());
        onChanged();
      } else {
        fullTankBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public Builder addFullTank(
        int index,
        com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder builderForValue) {
      if (fullTankBuilder_ == null) {
        ensureFullTankIsMutable();
        fullTank_.add(index, builderForValue.build());
        onChanged();
      } else {
        fullTankBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public Builder addAllFullTank(
        java.lang.Iterable<? extends com.cpdss.common.generated.discharge_plan.CleaningTankDetails>
            values) {
      if (fullTankBuilder_ == null) {
        ensureFullTankIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, fullTank_);
        onChanged();
      } else {
        fullTankBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public Builder clearFullTank() {
      if (fullTankBuilder_ == null) {
        fullTank_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
      } else {
        fullTankBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public Builder removeFullTank(int index) {
      if (fullTankBuilder_ == null) {
        ensureFullTankIsMutable();
        fullTank_.remove(index);
        onChanged();
      } else {
        fullTankBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder getFullTankBuilder(
        int index) {
      return getFullTankFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder
        getFullTankOrBuilder(int index) {
      if (fullTankBuilder_ == null) {
        return fullTank_.get(index);
      } else {
        return fullTankBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public java.util.List<
            ? extends com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
        getFullTankOrBuilderList() {
      if (fullTankBuilder_ != null) {
        return fullTankBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(fullTank_);
      }
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder
        addFullTankBuilder() {
      return getFullTankFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.discharge_plan.CleaningTankDetails.getDefaultInstance());
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder addFullTankBuilder(
        int index) {
      return getFullTankFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.discharge_plan.CleaningTankDetails.getDefaultInstance());
    }
    /** <code>repeated .CleaningTankDetails fullTank = 3;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder>
        getFullTankBuilderList() {
      return getFullTankFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder,
            com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>
        getFullTankFieldBuilder() {
      if (fullTankBuilder_ == null) {
        fullTankBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.CleaningTankDetails,
                com.cpdss.common.generated.discharge_plan.CleaningTankDetails.Builder,
                com.cpdss.common.generated.discharge_plan.CleaningTankDetailsOrBuilder>(
                fullTank_, ((bitField0_ & 0x00000004) != 0), getParentForChildren(), isClean());
        fullTank_ = null;
      }
      return fullTankBuilder_;
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

    // @@protoc_insertion_point(builder_scope:CleaningTanks)
  }

  // @@protoc_insertion_point(class_scope:CleaningTanks)
  private static final com.cpdss.common.generated.discharge_plan.CleaningTanks DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.CleaningTanks();
  }

  public static com.cpdss.common.generated.discharge_plan.CleaningTanks getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CleaningTanks> PARSER =
      new com.google.protobuf.AbstractParser<CleaningTanks>() {
        @java.lang.Override
        public CleaningTanks parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new CleaningTanks(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<CleaningTanks> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CleaningTanks> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.CleaningTanks getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
