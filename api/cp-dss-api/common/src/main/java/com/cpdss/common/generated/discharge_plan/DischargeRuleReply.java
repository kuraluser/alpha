/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargeRuleReply} */
public final class DischargeRuleReply extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargeRuleReply)
    DischargeRuleReplyOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargeRuleReply.newBuilder() to construct.
  private DischargeRuleReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargeRuleReply() {
    rulePlan_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargeRuleReply();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargeRuleReply(
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
          case 18:
            {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                rulePlan_ = new java.util.ArrayList<com.cpdss.common.generated.Common.RulePlans>();
                mutable_bitField0_ |= 0x00000001;
              }
              rulePlan_.add(
                  input.readMessage(
                      com.cpdss.common.generated.Common.RulePlans.parser(), extensionRegistry));
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
        rulePlan_ = java.util.Collections.unmodifiableList(rulePlan_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeRuleReply_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeRuleReply_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargeRuleReply.class,
            com.cpdss.common.generated.discharge_plan.DischargeRuleReply.Builder.class);
  }

  public static final int RESPONSESTATUS_FIELD_NUMBER = 1;
  private com.cpdss.common.generated.Common.ResponseStatus responseStatus_;
  /**
   * <code>.ResponseStatus responseStatus = 1;</code>
   *
   * @return Whether the responseStatus field is set.
   */
  public boolean hasResponseStatus() {
    return responseStatus_ != null;
  }
  /**
   * <code>.ResponseStatus responseStatus = 1;</code>
   *
   * @return The responseStatus.
   */
  public com.cpdss.common.generated.Common.ResponseStatus getResponseStatus() {
    return responseStatus_ == null
        ? com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance()
        : responseStatus_;
  }
  /** <code>.ResponseStatus responseStatus = 1;</code> */
  public com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder() {
    return getResponseStatus();
  }

  public static final int RULEPLAN_FIELD_NUMBER = 2;
  private java.util.List<com.cpdss.common.generated.Common.RulePlans> rulePlan_;
  /** <code>repeated .RulePlans rulePlan = 2;</code> */
  public java.util.List<com.cpdss.common.generated.Common.RulePlans> getRulePlanList() {
    return rulePlan_;
  }
  /** <code>repeated .RulePlans rulePlan = 2;</code> */
  public java.util.List<? extends com.cpdss.common.generated.Common.RulePlansOrBuilder>
      getRulePlanOrBuilderList() {
    return rulePlan_;
  }
  /** <code>repeated .RulePlans rulePlan = 2;</code> */
  public int getRulePlanCount() {
    return rulePlan_.size();
  }
  /** <code>repeated .RulePlans rulePlan = 2;</code> */
  public com.cpdss.common.generated.Common.RulePlans getRulePlan(int index) {
    return rulePlan_.get(index);
  }
  /** <code>repeated .RulePlans rulePlan = 2;</code> */
  public com.cpdss.common.generated.Common.RulePlansOrBuilder getRulePlanOrBuilder(int index) {
    return rulePlan_.get(index);
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
    if (responseStatus_ != null) {
      output.writeMessage(1, getResponseStatus());
    }
    for (int i = 0; i < rulePlan_.size(); i++) {
      output.writeMessage(2, rulePlan_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (responseStatus_ != null) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getResponseStatus());
    }
    for (int i = 0; i < rulePlan_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, rulePlan_.get(i));
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargeRuleReply)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargeRuleReply other =
        (com.cpdss.common.generated.discharge_plan.DischargeRuleReply) obj;

    if (hasResponseStatus() != other.hasResponseStatus()) return false;
    if (hasResponseStatus()) {
      if (!getResponseStatus().equals(other.getResponseStatus())) return false;
    }
    if (!getRulePlanList().equals(other.getRulePlanList())) return false;
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
    if (hasResponseStatus()) {
      hash = (37 * hash) + RESPONSESTATUS_FIELD_NUMBER;
      hash = (53 * hash) + getResponseStatus().hashCode();
    }
    if (getRulePlanCount() > 0) {
      hash = (37 * hash) + RULEPLAN_FIELD_NUMBER;
      hash = (53 * hash) + getRulePlanList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargeRuleReply prototype) {
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
  /** Protobuf type {@code DischargeRuleReply} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargeRuleReply)
      com.cpdss.common.generated.discharge_plan.DischargeRuleReplyOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeRuleReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeRuleReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargeRuleReply.class,
              com.cpdss.common.generated.discharge_plan.DischargeRuleReply.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.DischargeRuleReply.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getRulePlanFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (responseStatusBuilder_ == null) {
        responseStatus_ = null;
      } else {
        responseStatus_ = null;
        responseStatusBuilder_ = null;
      }
      if (rulePlanBuilder_ == null) {
        rulePlan_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        rulePlanBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeRuleReply_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeRuleReply
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargeRuleReply.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeRuleReply build() {
      com.cpdss.common.generated.discharge_plan.DischargeRuleReply result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeRuleReply buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargeRuleReply result =
          new com.cpdss.common.generated.discharge_plan.DischargeRuleReply(this);
      int from_bitField0_ = bitField0_;
      if (responseStatusBuilder_ == null) {
        result.responseStatus_ = responseStatus_;
      } else {
        result.responseStatus_ = responseStatusBuilder_.build();
      }
      if (rulePlanBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          rulePlan_ = java.util.Collections.unmodifiableList(rulePlan_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.rulePlan_ = rulePlan_;
      } else {
        result.rulePlan_ = rulePlanBuilder_.build();
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargeRuleReply) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.DischargeRuleReply) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.DischargeRuleReply other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargeRuleReply.getDefaultInstance())
        return this;
      if (other.hasResponseStatus()) {
        mergeResponseStatus(other.getResponseStatus());
      }
      if (rulePlanBuilder_ == null) {
        if (!other.rulePlan_.isEmpty()) {
          if (rulePlan_.isEmpty()) {
            rulePlan_ = other.rulePlan_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureRulePlanIsMutable();
            rulePlan_.addAll(other.rulePlan_);
          }
          onChanged();
        }
      } else {
        if (!other.rulePlan_.isEmpty()) {
          if (rulePlanBuilder_.isEmpty()) {
            rulePlanBuilder_.dispose();
            rulePlanBuilder_ = null;
            rulePlan_ = other.rulePlan_;
            bitField0_ = (bitField0_ & ~0x00000001);
            rulePlanBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getRulePlanFieldBuilder()
                    : null;
          } else {
            rulePlanBuilder_.addAllMessages(other.rulePlan_);
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
      com.cpdss.common.generated.discharge_plan.DischargeRuleReply parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargeRuleReply) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int bitField0_;

    private com.cpdss.common.generated.Common.ResponseStatus responseStatus_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.Common.ResponseStatus,
            com.cpdss.common.generated.Common.ResponseStatus.Builder,
            com.cpdss.common.generated.Common.ResponseStatusOrBuilder>
        responseStatusBuilder_;
    /**
     * <code>.ResponseStatus responseStatus = 1;</code>
     *
     * @return Whether the responseStatus field is set.
     */
    public boolean hasResponseStatus() {
      return responseStatusBuilder_ != null || responseStatus_ != null;
    }
    /**
     * <code>.ResponseStatus responseStatus = 1;</code>
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
    /** <code>.ResponseStatus responseStatus = 1;</code> */
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
    /** <code>.ResponseStatus responseStatus = 1;</code> */
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
    /** <code>.ResponseStatus responseStatus = 1;</code> */
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
    /** <code>.ResponseStatus responseStatus = 1;</code> */
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
    /** <code>.ResponseStatus responseStatus = 1;</code> */
    public com.cpdss.common.generated.Common.ResponseStatus.Builder getResponseStatusBuilder() {

      onChanged();
      return getResponseStatusFieldBuilder().getBuilder();
    }
    /** <code>.ResponseStatus responseStatus = 1;</code> */
    public com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder() {
      if (responseStatusBuilder_ != null) {
        return responseStatusBuilder_.getMessageOrBuilder();
      } else {
        return responseStatus_ == null
            ? com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance()
            : responseStatus_;
      }
    }
    /** <code>.ResponseStatus responseStatus = 1;</code> */
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

    private java.util.List<com.cpdss.common.generated.Common.RulePlans> rulePlan_ =
        java.util.Collections.emptyList();

    private void ensureRulePlanIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        rulePlan_ = new java.util.ArrayList<com.cpdss.common.generated.Common.RulePlans>(rulePlan_);
        bitField0_ |= 0x00000001;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.Common.RulePlans,
            com.cpdss.common.generated.Common.RulePlans.Builder,
            com.cpdss.common.generated.Common.RulePlansOrBuilder>
        rulePlanBuilder_;

    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public java.util.List<com.cpdss.common.generated.Common.RulePlans> getRulePlanList() {
      if (rulePlanBuilder_ == null) {
        return java.util.Collections.unmodifiableList(rulePlan_);
      } else {
        return rulePlanBuilder_.getMessageList();
      }
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public int getRulePlanCount() {
      if (rulePlanBuilder_ == null) {
        return rulePlan_.size();
      } else {
        return rulePlanBuilder_.getCount();
      }
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public com.cpdss.common.generated.Common.RulePlans getRulePlan(int index) {
      if (rulePlanBuilder_ == null) {
        return rulePlan_.get(index);
      } else {
        return rulePlanBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public Builder setRulePlan(int index, com.cpdss.common.generated.Common.RulePlans value) {
      if (rulePlanBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRulePlanIsMutable();
        rulePlan_.set(index, value);
        onChanged();
      } else {
        rulePlanBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public Builder setRulePlan(
        int index, com.cpdss.common.generated.Common.RulePlans.Builder builderForValue) {
      if (rulePlanBuilder_ == null) {
        ensureRulePlanIsMutable();
        rulePlan_.set(index, builderForValue.build());
        onChanged();
      } else {
        rulePlanBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public Builder addRulePlan(com.cpdss.common.generated.Common.RulePlans value) {
      if (rulePlanBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRulePlanIsMutable();
        rulePlan_.add(value);
        onChanged();
      } else {
        rulePlanBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public Builder addRulePlan(int index, com.cpdss.common.generated.Common.RulePlans value) {
      if (rulePlanBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRulePlanIsMutable();
        rulePlan_.add(index, value);
        onChanged();
      } else {
        rulePlanBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public Builder addRulePlan(
        com.cpdss.common.generated.Common.RulePlans.Builder builderForValue) {
      if (rulePlanBuilder_ == null) {
        ensureRulePlanIsMutable();
        rulePlan_.add(builderForValue.build());
        onChanged();
      } else {
        rulePlanBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public Builder addRulePlan(
        int index, com.cpdss.common.generated.Common.RulePlans.Builder builderForValue) {
      if (rulePlanBuilder_ == null) {
        ensureRulePlanIsMutable();
        rulePlan_.add(index, builderForValue.build());
        onChanged();
      } else {
        rulePlanBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public Builder addAllRulePlan(
        java.lang.Iterable<? extends com.cpdss.common.generated.Common.RulePlans> values) {
      if (rulePlanBuilder_ == null) {
        ensureRulePlanIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, rulePlan_);
        onChanged();
      } else {
        rulePlanBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public Builder clearRulePlan() {
      if (rulePlanBuilder_ == null) {
        rulePlan_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        rulePlanBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public Builder removeRulePlan(int index) {
      if (rulePlanBuilder_ == null) {
        ensureRulePlanIsMutable();
        rulePlan_.remove(index);
        onChanged();
      } else {
        rulePlanBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public com.cpdss.common.generated.Common.RulePlans.Builder getRulePlanBuilder(int index) {
      return getRulePlanFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public com.cpdss.common.generated.Common.RulePlansOrBuilder getRulePlanOrBuilder(int index) {
      if (rulePlanBuilder_ == null) {
        return rulePlan_.get(index);
      } else {
        return rulePlanBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public java.util.List<? extends com.cpdss.common.generated.Common.RulePlansOrBuilder>
        getRulePlanOrBuilderList() {
      if (rulePlanBuilder_ != null) {
        return rulePlanBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(rulePlan_);
      }
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public com.cpdss.common.generated.Common.RulePlans.Builder addRulePlanBuilder() {
      return getRulePlanFieldBuilder()
          .addBuilder(com.cpdss.common.generated.Common.RulePlans.getDefaultInstance());
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public com.cpdss.common.generated.Common.RulePlans.Builder addRulePlanBuilder(int index) {
      return getRulePlanFieldBuilder()
          .addBuilder(index, com.cpdss.common.generated.Common.RulePlans.getDefaultInstance());
    }
    /** <code>repeated .RulePlans rulePlan = 2;</code> */
    public java.util.List<com.cpdss.common.generated.Common.RulePlans.Builder>
        getRulePlanBuilderList() {
      return getRulePlanFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.Common.RulePlans,
            com.cpdss.common.generated.Common.RulePlans.Builder,
            com.cpdss.common.generated.Common.RulePlansOrBuilder>
        getRulePlanFieldBuilder() {
      if (rulePlanBuilder_ == null) {
        rulePlanBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.Common.RulePlans,
                com.cpdss.common.generated.Common.RulePlans.Builder,
                com.cpdss.common.generated.Common.RulePlansOrBuilder>(
                rulePlan_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
        rulePlan_ = null;
      }
      return rulePlanBuilder_;
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

    // @@protoc_insertion_point(builder_scope:DischargeRuleReply)
  }

  // @@protoc_insertion_point(class_scope:DischargeRuleReply)
  private static final com.cpdss.common.generated.discharge_plan.DischargeRuleReply
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargeRuleReply();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeRuleReply getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargeRuleReply> PARSER =
      new com.google.protobuf.AbstractParser<DischargeRuleReply>() {
        @java.lang.Override
        public DischargeRuleReply parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargeRuleReply(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargeRuleReply> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargeRuleReply> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargeRuleReply getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
