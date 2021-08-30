/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargeStudyDataTransferRequest} */
public final class DischargeStudyDataTransferRequest extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargeStudyDataTransferRequest)
    DischargeStudyDataTransferRequestOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargeStudyDataTransferRequest.newBuilder() to construct.
  private DischargeStudyDataTransferRequest(
      com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargeStudyDataTransferRequest() {
    portData_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargeStudyDataTransferRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargeStudyDataTransferRequest(
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
                portData_ =
                    new java.util.ArrayList<com.cpdss.common.generated.discharge_plan.PortData>();
                mutable_bitField0_ |= 0x00000001;
              }
              portData_.add(
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.PortData.parser(),
                      extensionRegistry));
              break;
            }
          case 16:
            {
              dischargePatternId_ = input.readInt64();
              break;
            }
          case 24:
            {
              voyageId_ = input.readInt64();
              break;
            }
          case 32:
            {
              vesselId_ = input.readInt64();
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
        portData_ = java.util.Collections.unmodifiableList(portData_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeStudyDataTransferRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeStudyDataTransferRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest.class,
            com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest.Builder
                .class);
  }

  public static final int PORTDATA_FIELD_NUMBER = 1;
  private java.util.List<com.cpdss.common.generated.discharge_plan.PortData> portData_;
  /** <code>repeated .PortData portData = 1;</code> */
  public java.util.List<com.cpdss.common.generated.discharge_plan.PortData> getPortDataList() {
    return portData_;
  }
  /** <code>repeated .PortData portData = 1;</code> */
  public java.util.List<? extends com.cpdss.common.generated.discharge_plan.PortDataOrBuilder>
      getPortDataOrBuilderList() {
    return portData_;
  }
  /** <code>repeated .PortData portData = 1;</code> */
  public int getPortDataCount() {
    return portData_.size();
  }
  /** <code>repeated .PortData portData = 1;</code> */
  public com.cpdss.common.generated.discharge_plan.PortData getPortData(int index) {
    return portData_.get(index);
  }
  /** <code>repeated .PortData portData = 1;</code> */
  public com.cpdss.common.generated.discharge_plan.PortDataOrBuilder getPortDataOrBuilder(
      int index) {
    return portData_.get(index);
  }

  public static final int DISCHARGEPATTERNID_FIELD_NUMBER = 2;
  private long dischargePatternId_;
  /**
   * <code>int64 dischargePatternId = 2;</code>
   *
   * @return The dischargePatternId.
   */
  public long getDischargePatternId() {
    return dischargePatternId_;
  }

  public static final int VOYAGEID_FIELD_NUMBER = 3;
  private long voyageId_;
  /**
   * <code>int64 voyageId = 3;</code>
   *
   * @return The voyageId.
   */
  public long getVoyageId() {
    return voyageId_;
  }

  public static final int VESSELID_FIELD_NUMBER = 4;
  private long vesselId_;
  /**
   * <code>int64 vesselId = 4;</code>
   *
   * @return The vesselId.
   */
  public long getVesselId() {
    return vesselId_;
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
    for (int i = 0; i < portData_.size(); i++) {
      output.writeMessage(1, portData_.get(i));
    }
    if (dischargePatternId_ != 0L) {
      output.writeInt64(2, dischargePatternId_);
    }
    if (voyageId_ != 0L) {
      output.writeInt64(3, voyageId_);
    }
    if (vesselId_ != 0L) {
      output.writeInt64(4, vesselId_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < portData_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, portData_.get(i));
    }
    if (dischargePatternId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, dischargePatternId_);
    }
    if (voyageId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, voyageId_);
    }
    if (vesselId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, vesselId_);
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
        instanceof com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest other =
        (com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest) obj;

    if (!getPortDataList().equals(other.getPortDataList())) return false;
    if (getDischargePatternId() != other.getDischargePatternId()) return false;
    if (getVoyageId() != other.getVoyageId()) return false;
    if (getVesselId() != other.getVesselId()) return false;
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
    if (getPortDataCount() > 0) {
      hash = (37 * hash) + PORTDATA_FIELD_NUMBER;
      hash = (53 * hash) + getPortDataList().hashCode();
    }
    hash = (37 * hash) + DISCHARGEPATTERNID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargePatternId());
    hash = (37 * hash) + VOYAGEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVoyageId());
    hash = (37 * hash) + VESSELID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVesselId());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseFrom(java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseFrom(
          java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseFrom(com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseDelimitedFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
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
      com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest prototype) {
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
  /** Protobuf type {@code DischargeStudyDataTransferRequest} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargeStudyDataTransferRequest)
      com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeStudyDataTransferRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeStudyDataTransferRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest.class,
              com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest.Builder
                  .class);
    }

    // Construct using
    // com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getPortDataFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (portDataBuilder_ == null) {
        portData_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        portDataBuilder_.clear();
      }
      dischargePatternId_ = 0L;

      voyageId_ = 0L;

      vesselId_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeStudyDataTransferRequest_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
          .getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest build() {
      com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest result =
          buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
        buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest result =
          new com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest(this);
      int from_bitField0_ = bitField0_;
      if (portDataBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          portData_ = java.util.Collections.unmodifiableList(portData_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.portData_ = portData_;
      } else {
        result.portData_ = portDataBuilder_.build();
      }
      result.dischargePatternId_ = dischargePatternId_;
      result.voyageId_ = voyageId_;
      result.vesselId_ = vesselId_;
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
          instanceof com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest) {
        return mergeFrom(
            (com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
              .getDefaultInstance()) return this;
      if (portDataBuilder_ == null) {
        if (!other.portData_.isEmpty()) {
          if (portData_.isEmpty()) {
            portData_ = other.portData_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensurePortDataIsMutable();
            portData_.addAll(other.portData_);
          }
          onChanged();
        }
      } else {
        if (!other.portData_.isEmpty()) {
          if (portDataBuilder_.isEmpty()) {
            portDataBuilder_.dispose();
            portDataBuilder_ = null;
            portData_ = other.portData_;
            bitField0_ = (bitField0_ & ~0x00000001);
            portDataBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getPortDataFieldBuilder()
                    : null;
          } else {
            portDataBuilder_.addAllMessages(other.portData_);
          }
        }
      }
      if (other.getDischargePatternId() != 0L) {
        setDischargePatternId(other.getDischargePatternId());
      }
      if (other.getVoyageId() != 0L) {
        setVoyageId(other.getVoyageId());
      }
      if (other.getVesselId() != 0L) {
        setVesselId(other.getVesselId());
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
      com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest parsedMessage =
          null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest)
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

    private java.util.List<com.cpdss.common.generated.discharge_plan.PortData> portData_ =
        java.util.Collections.emptyList();

    private void ensurePortDataIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        portData_ =
            new java.util.ArrayList<com.cpdss.common.generated.discharge_plan.PortData>(portData_);
        bitField0_ |= 0x00000001;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.PortData,
            com.cpdss.common.generated.discharge_plan.PortData.Builder,
            com.cpdss.common.generated.discharge_plan.PortDataOrBuilder>
        portDataBuilder_;

    /** <code>repeated .PortData portData = 1;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.PortData> getPortDataList() {
      if (portDataBuilder_ == null) {
        return java.util.Collections.unmodifiableList(portData_);
      } else {
        return portDataBuilder_.getMessageList();
      }
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public int getPortDataCount() {
      if (portDataBuilder_ == null) {
        return portData_.size();
      } else {
        return portDataBuilder_.getCount();
      }
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public com.cpdss.common.generated.discharge_plan.PortData getPortData(int index) {
      if (portDataBuilder_ == null) {
        return portData_.get(index);
      } else {
        return portDataBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public Builder setPortData(
        int index, com.cpdss.common.generated.discharge_plan.PortData value) {
      if (portDataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePortDataIsMutable();
        portData_.set(index, value);
        onChanged();
      } else {
        portDataBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public Builder setPortData(
        int index, com.cpdss.common.generated.discharge_plan.PortData.Builder builderForValue) {
      if (portDataBuilder_ == null) {
        ensurePortDataIsMutable();
        portData_.set(index, builderForValue.build());
        onChanged();
      } else {
        portDataBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public Builder addPortData(com.cpdss.common.generated.discharge_plan.PortData value) {
      if (portDataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePortDataIsMutable();
        portData_.add(value);
        onChanged();
      } else {
        portDataBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public Builder addPortData(
        int index, com.cpdss.common.generated.discharge_plan.PortData value) {
      if (portDataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePortDataIsMutable();
        portData_.add(index, value);
        onChanged();
      } else {
        portDataBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public Builder addPortData(
        com.cpdss.common.generated.discharge_plan.PortData.Builder builderForValue) {
      if (portDataBuilder_ == null) {
        ensurePortDataIsMutable();
        portData_.add(builderForValue.build());
        onChanged();
      } else {
        portDataBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public Builder addPortData(
        int index, com.cpdss.common.generated.discharge_plan.PortData.Builder builderForValue) {
      if (portDataBuilder_ == null) {
        ensurePortDataIsMutable();
        portData_.add(index, builderForValue.build());
        onChanged();
      } else {
        portDataBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public Builder addAllPortData(
        java.lang.Iterable<? extends com.cpdss.common.generated.discharge_plan.PortData> values) {
      if (portDataBuilder_ == null) {
        ensurePortDataIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, portData_);
        onChanged();
      } else {
        portDataBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public Builder clearPortData() {
      if (portDataBuilder_ == null) {
        portData_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        portDataBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public Builder removePortData(int index) {
      if (portDataBuilder_ == null) {
        ensurePortDataIsMutable();
        portData_.remove(index);
        onChanged();
      } else {
        portDataBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public com.cpdss.common.generated.discharge_plan.PortData.Builder getPortDataBuilder(
        int index) {
      return getPortDataFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public com.cpdss.common.generated.discharge_plan.PortDataOrBuilder getPortDataOrBuilder(
        int index) {
      if (portDataBuilder_ == null) {
        return portData_.get(index);
      } else {
        return portDataBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public java.util.List<? extends com.cpdss.common.generated.discharge_plan.PortDataOrBuilder>
        getPortDataOrBuilderList() {
      if (portDataBuilder_ != null) {
        return portDataBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(portData_);
      }
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public com.cpdss.common.generated.discharge_plan.PortData.Builder addPortDataBuilder() {
      return getPortDataFieldBuilder()
          .addBuilder(com.cpdss.common.generated.discharge_plan.PortData.getDefaultInstance());
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public com.cpdss.common.generated.discharge_plan.PortData.Builder addPortDataBuilder(
        int index) {
      return getPortDataFieldBuilder()
          .addBuilder(
              index, com.cpdss.common.generated.discharge_plan.PortData.getDefaultInstance());
    }
    /** <code>repeated .PortData portData = 1;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.PortData.Builder>
        getPortDataBuilderList() {
      return getPortDataFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.PortData,
            com.cpdss.common.generated.discharge_plan.PortData.Builder,
            com.cpdss.common.generated.discharge_plan.PortDataOrBuilder>
        getPortDataFieldBuilder() {
      if (portDataBuilder_ == null) {
        portDataBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.PortData,
                com.cpdss.common.generated.discharge_plan.PortData.Builder,
                com.cpdss.common.generated.discharge_plan.PortDataOrBuilder>(
                portData_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
        portData_ = null;
      }
      return portDataBuilder_;
    }

    private long dischargePatternId_;
    /**
     * <code>int64 dischargePatternId = 2;</code>
     *
     * @return The dischargePatternId.
     */
    public long getDischargePatternId() {
      return dischargePatternId_;
    }
    /**
     * <code>int64 dischargePatternId = 2;</code>
     *
     * @param value The dischargePatternId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargePatternId(long value) {

      dischargePatternId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 dischargePatternId = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargePatternId() {

      dischargePatternId_ = 0L;
      onChanged();
      return this;
    }

    private long voyageId_;
    /**
     * <code>int64 voyageId = 3;</code>
     *
     * @return The voyageId.
     */
    public long getVoyageId() {
      return voyageId_;
    }
    /**
     * <code>int64 voyageId = 3;</code>
     *
     * @param value The voyageId to set.
     * @return This builder for chaining.
     */
    public Builder setVoyageId(long value) {

      voyageId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 voyageId = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearVoyageId() {

      voyageId_ = 0L;
      onChanged();
      return this;
    }

    private long vesselId_;
    /**
     * <code>int64 vesselId = 4;</code>
     *
     * @return The vesselId.
     */
    public long getVesselId() {
      return vesselId_;
    }
    /**
     * <code>int64 vesselId = 4;</code>
     *
     * @param value The vesselId to set.
     * @return This builder for chaining.
     */
    public Builder setVesselId(long value) {

      vesselId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 vesselId = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearVesselId() {

      vesselId_ = 0L;
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

    // @@protoc_insertion_point(builder_scope:DischargeStudyDataTransferRequest)
  }

  // @@protoc_insertion_point(class_scope:DischargeStudyDataTransferRequest)
  private static final com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE =
        new com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargeStudyDataTransferRequest> PARSER =
      new com.google.protobuf.AbstractParser<DischargeStudyDataTransferRequest>() {
        @java.lang.Override
        public DischargeStudyDataTransferRequest parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargeStudyDataTransferRequest(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargeStudyDataTransferRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargeStudyDataTransferRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
