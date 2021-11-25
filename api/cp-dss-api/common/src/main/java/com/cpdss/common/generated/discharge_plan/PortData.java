/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code PortData} */
public final class PortData extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:PortData)
    PortDataOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use PortData.newBuilder() to construct.
  private PortData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private PortData() {}

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new PortData();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private PortData(
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
              portRotationId_ = input.readInt64();
              break;
            }
          case 16:
            {
              synopticTableId_ = input.readInt64();
              break;
            }
          case 32:
            {
              portId_ = input.readInt64();
              break;
            }
          case 42:
            {
              com.cpdss.common.generated.discharge_plan.DSCowDetails.Builder subBuilder = null;
              if (cowDetails_ != null) {
                subBuilder = cowDetails_.toBuilder();
              }
              cowDetails_ =
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.DSCowDetails.parser(),
                      extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(cowDetails_);
                cowDetails_ = subBuilder.buildPartial();
              }

              break;
            }
          case 48:
            {
              portOrder_ = input.readInt32();
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
        .internal_static_PortData_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_PortData_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.PortData.class,
            com.cpdss.common.generated.discharge_plan.PortData.Builder.class);
  }

  public static final int PORTID_FIELD_NUMBER = 4;
  private long portId_;
  /**
   * <code>int64 portId = 4;</code>
   *
   * @return The portId.
   */
  public long getPortId() {
    return portId_;
  }

  public static final int PORTROTATIONID_FIELD_NUMBER = 1;
  private long portRotationId_;
  /**
   * <code>int64 portRotationId = 1;</code>
   *
   * @return The portRotationId.
   */
  public long getPortRotationId() {
    return portRotationId_;
  }

  public static final int SYNOPTICTABLEID_FIELD_NUMBER = 2;
  private long synopticTableId_;
  /**
   * <code>int64 synopticTableId = 2;</code>
   *
   * @return The synopticTableId.
   */
  public long getSynopticTableId() {
    return synopticTableId_;
  }

  public static final int PORTORDER_FIELD_NUMBER = 6;
  private int portOrder_;
  /**
   * <code>int32 portOrder = 6;</code>
   *
   * @return The portOrder.
   */
  public int getPortOrder() {
    return portOrder_;
  }

  public static final int COWDETAILS_FIELD_NUMBER = 5;
  private com.cpdss.common.generated.discharge_plan.DSCowDetails cowDetails_;
  /**
   * <code>.DSCowDetails cowDetails = 5;</code>
   *
   * @return Whether the cowDetails field is set.
   */
  public boolean hasCowDetails() {
    return cowDetails_ != null;
  }
  /**
   * <code>.DSCowDetails cowDetails = 5;</code>
   *
   * @return The cowDetails.
   */
  public com.cpdss.common.generated.discharge_plan.DSCowDetails getCowDetails() {
    return cowDetails_ == null
        ? com.cpdss.common.generated.discharge_plan.DSCowDetails.getDefaultInstance()
        : cowDetails_;
  }
  /** <code>.DSCowDetails cowDetails = 5;</code> */
  public com.cpdss.common.generated.discharge_plan.DSCowDetailsOrBuilder getCowDetailsOrBuilder() {
    return getCowDetails();
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
    if (portRotationId_ != 0L) {
      output.writeInt64(1, portRotationId_);
    }
    if (synopticTableId_ != 0L) {
      output.writeInt64(2, synopticTableId_);
    }
    if (portId_ != 0L) {
      output.writeInt64(4, portId_);
    }
    if (cowDetails_ != null) {
      output.writeMessage(5, getCowDetails());
    }
    if (portOrder_ != 0) {
      output.writeInt32(6, portOrder_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (portRotationId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, portRotationId_);
    }
    if (synopticTableId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, synopticTableId_);
    }
    if (portId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, portId_);
    }
    if (cowDetails_ != null) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(5, getCowDetails());
    }
    if (portOrder_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(6, portOrder_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.PortData)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.PortData other =
        (com.cpdss.common.generated.discharge_plan.PortData) obj;

    if (getPortId() != other.getPortId()) return false;
    if (getPortRotationId() != other.getPortRotationId()) return false;
    if (getSynopticTableId() != other.getSynopticTableId()) return false;
    if (getPortOrder() != other.getPortOrder()) return false;
    if (hasCowDetails() != other.hasCowDetails()) return false;
    if (hasCowDetails()) {
      if (!getCowDetails().equals(other.getCowDetails())) return false;
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
    hash = (37 * hash) + PORTID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortId());
    hash = (37 * hash) + PORTROTATIONID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortRotationId());
    hash = (37 * hash) + SYNOPTICTABLEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getSynopticTableId());
    hash = (37 * hash) + PORTORDER_FIELD_NUMBER;
    hash = (53 * hash) + getPortOrder();
    if (hasCowDetails()) {
      hash = (37 * hash) + COWDETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getCowDetails().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.PortData parseFrom(
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

  public static Builder newBuilder(com.cpdss.common.generated.discharge_plan.PortData prototype) {
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
  /** Protobuf type {@code PortData} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:PortData)
      com.cpdss.common.generated.discharge_plan.PortDataOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_PortData_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_PortData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.PortData.class,
              com.cpdss.common.generated.discharge_plan.PortData.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.PortData.newBuilder()
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
      portId_ = 0L;

      portRotationId_ = 0L;

      synopticTableId_ = 0L;

      portOrder_ = 0;

      if (cowDetailsBuilder_ == null) {
        cowDetails_ = null;
      } else {
        cowDetails_ = null;
        cowDetailsBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_PortData_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.PortData getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.PortData.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.PortData build() {
      com.cpdss.common.generated.discharge_plan.PortData result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.PortData buildPartial() {
      com.cpdss.common.generated.discharge_plan.PortData result =
          new com.cpdss.common.generated.discharge_plan.PortData(this);
      result.portId_ = portId_;
      result.portRotationId_ = portRotationId_;
      result.synopticTableId_ = synopticTableId_;
      result.portOrder_ = portOrder_;
      if (cowDetailsBuilder_ == null) {
        result.cowDetails_ = cowDetails_;
      } else {
        result.cowDetails_ = cowDetailsBuilder_.build();
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.PortData) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.PortData) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.PortData other) {
      if (other == com.cpdss.common.generated.discharge_plan.PortData.getDefaultInstance())
        return this;
      if (other.getPortId() != 0L) {
        setPortId(other.getPortId());
      }
      if (other.getPortRotationId() != 0L) {
        setPortRotationId(other.getPortRotationId());
      }
      if (other.getSynopticTableId() != 0L) {
        setSynopticTableId(other.getSynopticTableId());
      }
      if (other.getPortOrder() != 0) {
        setPortOrder(other.getPortOrder());
      }
      if (other.hasCowDetails()) {
        mergeCowDetails(other.getCowDetails());
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
      com.cpdss.common.generated.discharge_plan.PortData parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.PortData) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long portId_;
    /**
     * <code>int64 portId = 4;</code>
     *
     * @return The portId.
     */
    public long getPortId() {
      return portId_;
    }
    /**
     * <code>int64 portId = 4;</code>
     *
     * @param value The portId to set.
     * @return This builder for chaining.
     */
    public Builder setPortId(long value) {

      portId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 portId = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPortId() {

      portId_ = 0L;
      onChanged();
      return this;
    }

    private long portRotationId_;
    /**
     * <code>int64 portRotationId = 1;</code>
     *
     * @return The portRotationId.
     */
    public long getPortRotationId() {
      return portRotationId_;
    }
    /**
     * <code>int64 portRotationId = 1;</code>
     *
     * @param value The portRotationId to set.
     * @return This builder for chaining.
     */
    public Builder setPortRotationId(long value) {

      portRotationId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 portRotationId = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPortRotationId() {

      portRotationId_ = 0L;
      onChanged();
      return this;
    }

    private long synopticTableId_;
    /**
     * <code>int64 synopticTableId = 2;</code>
     *
     * @return The synopticTableId.
     */
    public long getSynopticTableId() {
      return synopticTableId_;
    }
    /**
     * <code>int64 synopticTableId = 2;</code>
     *
     * @param value The synopticTableId to set.
     * @return This builder for chaining.
     */
    public Builder setSynopticTableId(long value) {

      synopticTableId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 synopticTableId = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearSynopticTableId() {

      synopticTableId_ = 0L;
      onChanged();
      return this;
    }

    private int portOrder_;
    /**
     * <code>int32 portOrder = 6;</code>
     *
     * @return The portOrder.
     */
    public int getPortOrder() {
      return portOrder_;
    }
    /**
     * <code>int32 portOrder = 6;</code>
     *
     * @param value The portOrder to set.
     * @return This builder for chaining.
     */
    public Builder setPortOrder(int value) {

      portOrder_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 portOrder = 6;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPortOrder() {

      portOrder_ = 0;
      onChanged();
      return this;
    }

    private com.cpdss.common.generated.discharge_plan.DSCowDetails cowDetails_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DSCowDetails,
            com.cpdss.common.generated.discharge_plan.DSCowDetails.Builder,
            com.cpdss.common.generated.discharge_plan.DSCowDetailsOrBuilder>
        cowDetailsBuilder_;
    /**
     * <code>.DSCowDetails cowDetails = 5;</code>
     *
     * @return Whether the cowDetails field is set.
     */
    public boolean hasCowDetails() {
      return cowDetailsBuilder_ != null || cowDetails_ != null;
    }
    /**
     * <code>.DSCowDetails cowDetails = 5;</code>
     *
     * @return The cowDetails.
     */
    public com.cpdss.common.generated.discharge_plan.DSCowDetails getCowDetails() {
      if (cowDetailsBuilder_ == null) {
        return cowDetails_ == null
            ? com.cpdss.common.generated.discharge_plan.DSCowDetails.getDefaultInstance()
            : cowDetails_;
      } else {
        return cowDetailsBuilder_.getMessage();
      }
    }
    /** <code>.DSCowDetails cowDetails = 5;</code> */
    public Builder setCowDetails(com.cpdss.common.generated.discharge_plan.DSCowDetails value) {
      if (cowDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        cowDetails_ = value;
        onChanged();
      } else {
        cowDetailsBuilder_.setMessage(value);
      }

      return this;
    }
    /** <code>.DSCowDetails cowDetails = 5;</code> */
    public Builder setCowDetails(
        com.cpdss.common.generated.discharge_plan.DSCowDetails.Builder builderForValue) {
      if (cowDetailsBuilder_ == null) {
        cowDetails_ = builderForValue.build();
        onChanged();
      } else {
        cowDetailsBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /** <code>.DSCowDetails cowDetails = 5;</code> */
    public Builder mergeCowDetails(com.cpdss.common.generated.discharge_plan.DSCowDetails value) {
      if (cowDetailsBuilder_ == null) {
        if (cowDetails_ != null) {
          cowDetails_ =
              com.cpdss.common.generated.discharge_plan.DSCowDetails.newBuilder(cowDetails_)
                  .mergeFrom(value)
                  .buildPartial();
        } else {
          cowDetails_ = value;
        }
        onChanged();
      } else {
        cowDetailsBuilder_.mergeFrom(value);
      }

      return this;
    }
    /** <code>.DSCowDetails cowDetails = 5;</code> */
    public Builder clearCowDetails() {
      if (cowDetailsBuilder_ == null) {
        cowDetails_ = null;
        onChanged();
      } else {
        cowDetails_ = null;
        cowDetailsBuilder_ = null;
      }

      return this;
    }
    /** <code>.DSCowDetails cowDetails = 5;</code> */
    public com.cpdss.common.generated.discharge_plan.DSCowDetails.Builder getCowDetailsBuilder() {

      onChanged();
      return getCowDetailsFieldBuilder().getBuilder();
    }
    /** <code>.DSCowDetails cowDetails = 5;</code> */
    public com.cpdss.common.generated.discharge_plan.DSCowDetailsOrBuilder
        getCowDetailsOrBuilder() {
      if (cowDetailsBuilder_ != null) {
        return cowDetailsBuilder_.getMessageOrBuilder();
      } else {
        return cowDetails_ == null
            ? com.cpdss.common.generated.discharge_plan.DSCowDetails.getDefaultInstance()
            : cowDetails_;
      }
    }
    /** <code>.DSCowDetails cowDetails = 5;</code> */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DSCowDetails,
            com.cpdss.common.generated.discharge_plan.DSCowDetails.Builder,
            com.cpdss.common.generated.discharge_plan.DSCowDetailsOrBuilder>
        getCowDetailsFieldBuilder() {
      if (cowDetailsBuilder_ == null) {
        cowDetailsBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.DSCowDetails,
                com.cpdss.common.generated.discharge_plan.DSCowDetails.Builder,
                com.cpdss.common.generated.discharge_plan.DSCowDetailsOrBuilder>(
                getCowDetails(), getParentForChildren(), isClean());
        cowDetails_ = null;
      }
      return cowDetailsBuilder_;
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

    // @@protoc_insertion_point(builder_scope:PortData)
  }

  // @@protoc_insertion_point(class_scope:PortData)
  private static final com.cpdss.common.generated.discharge_plan.PortData DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.PortData();
  }

  public static com.cpdss.common.generated.discharge_plan.PortData getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PortData> PARSER =
      new com.google.protobuf.AbstractParser<PortData>() {
        @java.lang.Override
        public PortData parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new PortData(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<PortData> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PortData> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.PortData getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
