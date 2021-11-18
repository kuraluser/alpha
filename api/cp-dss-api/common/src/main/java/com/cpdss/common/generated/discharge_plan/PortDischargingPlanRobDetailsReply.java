/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code PortDischargingPlanRobDetailsReply} */
public final class PortDischargingPlanRobDetailsReply extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:PortDischargingPlanRobDetailsReply)
    PortDischargingPlanRobDetailsReplyOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use PortDischargingPlanRobDetailsReply.newBuilder() to construct.
  private PortDischargingPlanRobDetailsReply(
      com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private PortDischargingPlanRobDetailsReply() {
    portDischargingPlanRobDetails_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new PortDischargingPlanRobDetailsReply();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private PortDischargingPlanRobDetailsReply(
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
                portDischargingPlanRobDetails_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails>();
                mutable_bitField0_ |= 0x00000001;
              }
              portDischargingPlanRobDetails_.add(
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails
                          .parser(),
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
        portDischargingPlanRobDetails_ =
            java.util.Collections.unmodifiableList(portDischargingPlanRobDetails_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_PortDischargingPlanRobDetailsReply_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_PortDischargingPlanRobDetailsReply_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply.class,
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply.Builder
                .class);
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

  public static final int PORTDISCHARGINGPLANROBDETAILS_FIELD_NUMBER = 2;
  private java.util.List<com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails>
      portDischargingPlanRobDetails_;
  /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
  public java.util.List<com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails>
      getPortDischargingPlanRobDetailsList() {
    return portDischargingPlanRobDetails_;
  }
  /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
  public java.util.List<
          ? extends
              com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsOrBuilder>
      getPortDischargingPlanRobDetailsOrBuilderList() {
    return portDischargingPlanRobDetails_;
  }
  /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
  public int getPortDischargingPlanRobDetailsCount() {
    return portDischargingPlanRobDetails_.size();
  }
  /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
  public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails
      getPortDischargingPlanRobDetails(int index) {
    return portDischargingPlanRobDetails_.get(index);
  }
  /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
  public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsOrBuilder
      getPortDischargingPlanRobDetailsOrBuilder(int index) {
    return portDischargingPlanRobDetails_.get(index);
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
    for (int i = 0; i < portDischargingPlanRobDetails_.size(); i++) {
      output.writeMessage(2, portDischargingPlanRobDetails_.get(i));
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
    for (int i = 0; i < portDischargingPlanRobDetails_.size(); i++) {
      size +=
          com.google.protobuf.CodedOutputStream.computeMessageSize(
              2, portDischargingPlanRobDetails_.get(i));
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
    if (!(obj
        instanceof com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply other =
        (com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply) obj;

    if (hasResponseStatus() != other.hasResponseStatus()) return false;
    if (hasResponseStatus()) {
      if (!getResponseStatus().equals(other.getResponseStatus())) return false;
    }
    if (!getPortDischargingPlanRobDetailsList()
        .equals(other.getPortDischargingPlanRobDetailsList())) return false;
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
    if (getPortDischargingPlanRobDetailsCount() > 0) {
      hash = (37 * hash) + PORTDISCHARGINGPLANROBDETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getPortDischargingPlanRobDetailsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseFrom(java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseFrom(
          java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseFrom(com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseDelimitedFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      parseFrom(
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
      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply prototype) {
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
  /** Protobuf type {@code PortDischargingPlanRobDetailsReply} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:PortDischargingPlanRobDetailsReply)
      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReplyOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_PortDischargingPlanRobDetailsReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_PortDischargingPlanRobDetailsReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply.class,
              com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply.Builder
                  .class);
    }

    // Construct using
    // com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getPortDischargingPlanRobDetailsFieldBuilder();
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
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        portDischargingPlanRobDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        portDischargingPlanRobDetailsBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_PortDischargingPlanRobDetailsReply_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
          .getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply build() {
      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply result =
          buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
        buildPartial() {
      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply result =
          new com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply(this);
      int from_bitField0_ = bitField0_;
      if (responseStatusBuilder_ == null) {
        result.responseStatus_ = responseStatus_;
      } else {
        result.responseStatus_ = responseStatusBuilder_.build();
      }
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          portDischargingPlanRobDetails_ =
              java.util.Collections.unmodifiableList(portDischargingPlanRobDetails_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.portDischargingPlanRobDetails_ = portDischargingPlanRobDetails_;
      } else {
        result.portDischargingPlanRobDetails_ = portDischargingPlanRobDetailsBuilder_.build();
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
      if (other
          instanceof com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply) {
        return mergeFrom(
            (com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
              .getDefaultInstance()) return this;
      if (other.hasResponseStatus()) {
        mergeResponseStatus(other.getResponseStatus());
      }
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        if (!other.portDischargingPlanRobDetails_.isEmpty()) {
          if (portDischargingPlanRobDetails_.isEmpty()) {
            portDischargingPlanRobDetails_ = other.portDischargingPlanRobDetails_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensurePortDischargingPlanRobDetailsIsMutable();
            portDischargingPlanRobDetails_.addAll(other.portDischargingPlanRobDetails_);
          }
          onChanged();
        }
      } else {
        if (!other.portDischargingPlanRobDetails_.isEmpty()) {
          if (portDischargingPlanRobDetailsBuilder_.isEmpty()) {
            portDischargingPlanRobDetailsBuilder_.dispose();
            portDischargingPlanRobDetailsBuilder_ = null;
            portDischargingPlanRobDetails_ = other.portDischargingPlanRobDetails_;
            bitField0_ = (bitField0_ & ~0x00000001);
            portDischargingPlanRobDetailsBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getPortDischargingPlanRobDetailsFieldBuilder()
                    : null;
          } else {
            portDischargingPlanRobDetailsBuilder_.addAllMessages(
                other.portDischargingPlanRobDetails_);
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
      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply parsedMessage =
          null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply)
                e.getUnfinishedMessage();
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

    private java.util.List<com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails>
        portDischargingPlanRobDetails_ = java.util.Collections.emptyList();

    private void ensurePortDischargingPlanRobDetailsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        portDischargingPlanRobDetails_ =
            new java.util.ArrayList<
                com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails>(
                portDischargingPlanRobDetails_);
        bitField0_ |= 0x00000001;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails,
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails.Builder,
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsOrBuilder>
        portDischargingPlanRobDetailsBuilder_;

    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails>
        getPortDischargingPlanRobDetailsList() {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(portDischargingPlanRobDetails_);
      } else {
        return portDischargingPlanRobDetailsBuilder_.getMessageList();
      }
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public int getPortDischargingPlanRobDetailsCount() {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        return portDischargingPlanRobDetails_.size();
      } else {
        return portDischargingPlanRobDetailsBuilder_.getCount();
      }
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails
        getPortDischargingPlanRobDetails(int index) {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        return portDischargingPlanRobDetails_.get(index);
      } else {
        return portDischargingPlanRobDetailsBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public Builder setPortDischargingPlanRobDetails(
        int index, com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails value) {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePortDischargingPlanRobDetailsIsMutable();
        portDischargingPlanRobDetails_.set(index, value);
        onChanged();
      } else {
        portDischargingPlanRobDetailsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public Builder setPortDischargingPlanRobDetails(
        int index,
        com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails.Builder
            builderForValue) {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        ensurePortDischargingPlanRobDetailsIsMutable();
        portDischargingPlanRobDetails_.set(index, builderForValue.build());
        onChanged();
      } else {
        portDischargingPlanRobDetailsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public Builder addPortDischargingPlanRobDetails(
        com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails value) {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePortDischargingPlanRobDetailsIsMutable();
        portDischargingPlanRobDetails_.add(value);
        onChanged();
      } else {
        portDischargingPlanRobDetailsBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public Builder addPortDischargingPlanRobDetails(
        int index, com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails value) {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePortDischargingPlanRobDetailsIsMutable();
        portDischargingPlanRobDetails_.add(index, value);
        onChanged();
      } else {
        portDischargingPlanRobDetailsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public Builder addPortDischargingPlanRobDetails(
        com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails.Builder
            builderForValue) {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        ensurePortDischargingPlanRobDetailsIsMutable();
        portDischargingPlanRobDetails_.add(builderForValue.build());
        onChanged();
      } else {
        portDischargingPlanRobDetailsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public Builder addPortDischargingPlanRobDetails(
        int index,
        com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails.Builder
            builderForValue) {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        ensurePortDischargingPlanRobDetailsIsMutable();
        portDischargingPlanRobDetails_.add(index, builderForValue.build());
        onChanged();
      } else {
        portDischargingPlanRobDetailsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public Builder addAllPortDischargingPlanRobDetails(
        java.lang.Iterable<
                ? extends com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails>
            values) {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        ensurePortDischargingPlanRobDetailsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, portDischargingPlanRobDetails_);
        onChanged();
      } else {
        portDischargingPlanRobDetailsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public Builder clearPortDischargingPlanRobDetails() {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        portDischargingPlanRobDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        portDischargingPlanRobDetailsBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public Builder removePortDischargingPlanRobDetails(int index) {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        ensurePortDischargingPlanRobDetailsIsMutable();
        portDischargingPlanRobDetails_.remove(index);
        onChanged();
      } else {
        portDischargingPlanRobDetailsBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails.Builder
        getPortDischargingPlanRobDetailsBuilder(int index) {
      return getPortDischargingPlanRobDetailsFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsOrBuilder
        getPortDischargingPlanRobDetailsOrBuilder(int index) {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        return portDischargingPlanRobDetails_.get(index);
      } else {
        return portDischargingPlanRobDetailsBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public java.util.List<
            ? extends
                com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsOrBuilder>
        getPortDischargingPlanRobDetailsOrBuilderList() {
      if (portDischargingPlanRobDetailsBuilder_ != null) {
        return portDischargingPlanRobDetailsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(portDischargingPlanRobDetails_);
      }
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails.Builder
        addPortDischargingPlanRobDetailsBuilder() {
      return getPortDischargingPlanRobDetailsFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails
                  .getDefaultInstance());
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails.Builder
        addPortDischargingPlanRobDetailsBuilder(int index) {
      return getPortDischargingPlanRobDetailsFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails
                  .getDefaultInstance());
    }
    /** <code>repeated .PortDischargingPlanRobDetails portDischargingPlanRobDetails = 2;</code> */
    public java.util.List<
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails.Builder>
        getPortDischargingPlanRobDetailsBuilderList() {
      return getPortDischargingPlanRobDetailsFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails,
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails.Builder,
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsOrBuilder>
        getPortDischargingPlanRobDetailsFieldBuilder() {
      if (portDischargingPlanRobDetailsBuilder_ == null) {
        portDischargingPlanRobDetailsBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails,
                com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetails.Builder,
                com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsOrBuilder>(
                portDischargingPlanRobDetails_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        portDischargingPlanRobDetails_ = null;
      }
      return portDischargingPlanRobDetailsBuilder_;
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

    // @@protoc_insertion_point(builder_scope:PortDischargingPlanRobDetailsReply)
  }

  // @@protoc_insertion_point(class_scope:PortDischargingPlanRobDetailsReply)
  private static final com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE =
        new com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply();
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PortDischargingPlanRobDetailsReply> PARSER =
      new com.google.protobuf.AbstractParser<PortDischargingPlanRobDetailsReply>() {
        @java.lang.Override
        public PortDischargingPlanRobDetailsReply parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new PortDischargingPlanRobDetailsReply(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<PortDischargingPlanRobDetailsReply> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PortDischargingPlanRobDetailsReply> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsReply
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
