/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargingInfoSaveResponse} */
public final class DischargingInfoSaveResponse extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargingInfoSaveResponse)
    DischargingInfoSaveResponseOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargingInfoSaveResponse.newBuilder() to construct.
  private DischargingInfoSaveResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargingInfoSaveResponse() {}

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargingInfoSaveResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargingInfoSaveResponse(
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
              dischargingInfoId_ = input.readInt64();
              break;
            }
          case 16:
            {
              vesselId_ = input.readInt64();
              break;
            }
          case 24:
            {
              portRotationId_ = input.readInt64();
              break;
            }
          case 32:
            {
              synopticalTableId_ = input.readInt64();
              break;
            }
          case 40:
            {
              voyageId_ = input.readInt64();
              break;
            }
          case 50:
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
        .internal_static_DischargingInfoSaveResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargingInfoSaveResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse.class,
            com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse.Builder.class);
  }

  public static final int DISCHARGINGINFOID_FIELD_NUMBER = 1;
  private long dischargingInfoId_;
  /**
   * <code>int64 dischargingInfoId = 1;</code>
   *
   * @return The dischargingInfoId.
   */
  public long getDischargingInfoId() {
    return dischargingInfoId_;
  }

  public static final int VESSELID_FIELD_NUMBER = 2;
  private long vesselId_;
  /**
   * <code>int64 vesselId = 2;</code>
   *
   * @return The vesselId.
   */
  public long getVesselId() {
    return vesselId_;
  }

  public static final int PORTROTATIONID_FIELD_NUMBER = 3;
  private long portRotationId_;
  /**
   * <code>int64 portRotationId = 3;</code>
   *
   * @return The portRotationId.
   */
  public long getPortRotationId() {
    return portRotationId_;
  }

  public static final int SYNOPTICALTABLEID_FIELD_NUMBER = 4;
  private long synopticalTableId_;
  /**
   * <code>int64 synopticalTableId = 4;</code>
   *
   * @return The synopticalTableId.
   */
  public long getSynopticalTableId() {
    return synopticalTableId_;
  }

  public static final int VOYAGEID_FIELD_NUMBER = 5;
  private long voyageId_;
  /**
   * <code>int64 voyageId = 5;</code>
   *
   * @return The voyageId.
   */
  public long getVoyageId() {
    return voyageId_;
  }

  public static final int RESPONSESTATUS_FIELD_NUMBER = 6;
  private com.cpdss.common.generated.Common.ResponseStatus responseStatus_;
  /**
   * <code>.ResponseStatus responseStatus = 6;</code>
   *
   * @return Whether the responseStatus field is set.
   */
  public boolean hasResponseStatus() {
    return responseStatus_ != null;
  }
  /**
   * <code>.ResponseStatus responseStatus = 6;</code>
   *
   * @return The responseStatus.
   */
  public com.cpdss.common.generated.Common.ResponseStatus getResponseStatus() {
    return responseStatus_ == null
        ? com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance()
        : responseStatus_;
  }
  /** <code>.ResponseStatus responseStatus = 6;</code> */
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
    if (dischargingInfoId_ != 0L) {
      output.writeInt64(1, dischargingInfoId_);
    }
    if (vesselId_ != 0L) {
      output.writeInt64(2, vesselId_);
    }
    if (portRotationId_ != 0L) {
      output.writeInt64(3, portRotationId_);
    }
    if (synopticalTableId_ != 0L) {
      output.writeInt64(4, synopticalTableId_);
    }
    if (voyageId_ != 0L) {
      output.writeInt64(5, voyageId_);
    }
    if (responseStatus_ != null) {
      output.writeMessage(6, getResponseStatus());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (dischargingInfoId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, dischargingInfoId_);
    }
    if (vesselId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, vesselId_);
    }
    if (portRotationId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, portRotationId_);
    }
    if (synopticalTableId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, synopticalTableId_);
    }
    if (voyageId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(5, voyageId_);
    }
    if (responseStatus_ != null) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(6, getResponseStatus());
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse other =
        (com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse) obj;

    if (getDischargingInfoId() != other.getDischargingInfoId()) return false;
    if (getVesselId() != other.getVesselId()) return false;
    if (getPortRotationId() != other.getPortRotationId()) return false;
    if (getSynopticalTableId() != other.getSynopticalTableId()) return false;
    if (getVoyageId() != other.getVoyageId()) return false;
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
    hash = (37 * hash) + DISCHARGINGINFOID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargingInfoId());
    hash = (37 * hash) + VESSELID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVesselId());
    hash = (37 * hash) + PORTROTATIONID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortRotationId());
    hash = (37 * hash) + SYNOPTICALTABLEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getSynopticalTableId());
    hash = (37 * hash) + VOYAGEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVoyageId());
    if (hasResponseStatus()) {
      hash = (37 * hash) + RESPONSESTATUS_FIELD_NUMBER;
      hash = (53 * hash) + getResponseStatus().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parseFrom(
      byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse
      parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse
      parseDelimitedFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse prototype) {
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
  /** Protobuf type {@code DischargingInfoSaveResponse} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargingInfoSaveResponse)
      com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingInfoSaveResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingInfoSaveResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse.class,
              com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse.Builder.class);
    }

    // Construct using
    // com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse.newBuilder()
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
      dischargingInfoId_ = 0L;

      vesselId_ = 0L;

      portRotationId_ = 0L;

      synopticalTableId_ = 0L;

      voyageId_ = 0L;

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
          .internal_static_DischargingInfoSaveResponse_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse
          .getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse build() {
      com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse result =
          new com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse(this);
      result.dischargingInfoId_ = dischargingInfoId_;
      result.vesselId_ = vesselId_;
      result.portRotationId_ = portRotationId_;
      result.synopticalTableId_ = synopticalTableId_;
      result.voyageId_ = voyageId_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse) {
        return mergeFrom(
            (com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse
              .getDefaultInstance()) return this;
      if (other.getDischargingInfoId() != 0L) {
        setDischargingInfoId(other.getDischargingInfoId());
      }
      if (other.getVesselId() != 0L) {
        setVesselId(other.getVesselId());
      }
      if (other.getPortRotationId() != 0L) {
        setPortRotationId(other.getPortRotationId());
      }
      if (other.getSynopticalTableId() != 0L) {
        setSynopticalTableId(other.getSynopticalTableId());
      }
      if (other.getVoyageId() != 0L) {
        setVoyageId(other.getVoyageId());
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
      com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse)
                e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long dischargingInfoId_;
    /**
     * <code>int64 dischargingInfoId = 1;</code>
     *
     * @return The dischargingInfoId.
     */
    public long getDischargingInfoId() {
      return dischargingInfoId_;
    }
    /**
     * <code>int64 dischargingInfoId = 1;</code>
     *
     * @param value The dischargingInfoId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargingInfoId(long value) {

      dischargingInfoId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 dischargingInfoId = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargingInfoId() {

      dischargingInfoId_ = 0L;
      onChanged();
      return this;
    }

    private long vesselId_;
    /**
     * <code>int64 vesselId = 2;</code>
     *
     * @return The vesselId.
     */
    public long getVesselId() {
      return vesselId_;
    }
    /**
     * <code>int64 vesselId = 2;</code>
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
     * <code>int64 vesselId = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearVesselId() {

      vesselId_ = 0L;
      onChanged();
      return this;
    }

    private long portRotationId_;
    /**
     * <code>int64 portRotationId = 3;</code>
     *
     * @return The portRotationId.
     */
    public long getPortRotationId() {
      return portRotationId_;
    }
    /**
     * <code>int64 portRotationId = 3;</code>
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
     * <code>int64 portRotationId = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPortRotationId() {

      portRotationId_ = 0L;
      onChanged();
      return this;
    }

    private long synopticalTableId_;
    /**
     * <code>int64 synopticalTableId = 4;</code>
     *
     * @return The synopticalTableId.
     */
    public long getSynopticalTableId() {
      return synopticalTableId_;
    }
    /**
     * <code>int64 synopticalTableId = 4;</code>
     *
     * @param value The synopticalTableId to set.
     * @return This builder for chaining.
     */
    public Builder setSynopticalTableId(long value) {

      synopticalTableId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 synopticalTableId = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearSynopticalTableId() {

      synopticalTableId_ = 0L;
      onChanged();
      return this;
    }

    private long voyageId_;
    /**
     * <code>int64 voyageId = 5;</code>
     *
     * @return The voyageId.
     */
    public long getVoyageId() {
      return voyageId_;
    }
    /**
     * <code>int64 voyageId = 5;</code>
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
     * <code>int64 voyageId = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearVoyageId() {

      voyageId_ = 0L;
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
     * <code>.ResponseStatus responseStatus = 6;</code>
     *
     * @return Whether the responseStatus field is set.
     */
    public boolean hasResponseStatus() {
      return responseStatusBuilder_ != null || responseStatus_ != null;
    }
    /**
     * <code>.ResponseStatus responseStatus = 6;</code>
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
    /** <code>.ResponseStatus responseStatus = 6;</code> */
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
    /** <code>.ResponseStatus responseStatus = 6;</code> */
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
    /** <code>.ResponseStatus responseStatus = 6;</code> */
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
    /** <code>.ResponseStatus responseStatus = 6;</code> */
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
    /** <code>.ResponseStatus responseStatus = 6;</code> */
    public com.cpdss.common.generated.Common.ResponseStatus.Builder getResponseStatusBuilder() {

      onChanged();
      return getResponseStatusFieldBuilder().getBuilder();
    }
    /** <code>.ResponseStatus responseStatus = 6;</code> */
    public com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder() {
      if (responseStatusBuilder_ != null) {
        return responseStatusBuilder_.getMessageOrBuilder();
      } else {
        return responseStatus_ == null
            ? com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance()
            : responseStatus_;
      }
    }
    /** <code>.ResponseStatus responseStatus = 6;</code> */
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

    // @@protoc_insertion_point(builder_scope:DischargingInfoSaveResponse)
  }

  // @@protoc_insertion_point(class_scope:DischargingInfoSaveResponse)
  private static final com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargingInfoSaveResponse> PARSER =
      new com.google.protobuf.AbstractParser<DischargingInfoSaveResponse>() {
        @java.lang.Override
        public DischargingInfoSaveResponse parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargingInfoSaveResponse(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargingInfoSaveResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargingInfoSaveResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargingInfoSaveResponse
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
