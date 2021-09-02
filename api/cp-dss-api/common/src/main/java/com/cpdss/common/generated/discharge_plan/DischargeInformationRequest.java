/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/**
 *
 *
 * <pre>
 * A generic request with Optional params
 * </pre>
 *
 * Protobuf type {@code DischargeInformationRequest}
 */
public final class DischargeInformationRequest extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargeInformationRequest)
    DischargeInformationRequestOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargeInformationRequest.newBuilder() to construct.
  private DischargeInformationRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargeInformationRequest() {}

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargeInformationRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargeInformationRequest(
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
              companyId_ = input.readInt64();
              break;
            }
          case 16:
            {
              vesselId_ = input.readInt64();
              break;
            }
          case 24:
            {
              voyageId_ = input.readInt64();
              break;
            }
          case 32:
            {
              dischargeInfoId_ = input.readInt64();
              break;
            }
          case 40:
            {
              dischargePatternId_ = input.readInt64();
              break;
            }
          case 48:
            {
              portRotationId_ = input.readInt64();
              break;
            }
          case 56:
            {
              synopticTableId_ = input.readInt64();
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
        .internal_static_DischargeInformationRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeInformationRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargeInformationRequest.class,
            com.cpdss.common.generated.discharge_plan.DischargeInformationRequest.Builder.class);
  }

  public static final int COMPANYID_FIELD_NUMBER = 1;
  private long companyId_;
  /**
   * <code>int64 companyId = 1;</code>
   *
   * @return The companyId.
   */
  public long getCompanyId() {
    return companyId_;
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

  public static final int DISCHARGEINFOID_FIELD_NUMBER = 4;
  private long dischargeInfoId_;
  /**
   * <code>int64 dischargeInfoId = 4;</code>
   *
   * @return The dischargeInfoId.
   */
  public long getDischargeInfoId() {
    return dischargeInfoId_;
  }

  public static final int DISCHARGEPATTERNID_FIELD_NUMBER = 5;
  private long dischargePatternId_;
  /**
   * <code>int64 dischargePatternId = 5;</code>
   *
   * @return The dischargePatternId.
   */
  public long getDischargePatternId() {
    return dischargePatternId_;
  }

  public static final int PORTROTATIONID_FIELD_NUMBER = 6;
  private long portRotationId_;
  /**
   * <code>int64 portRotationId = 6;</code>
   *
   * @return The portRotationId.
   */
  public long getPortRotationId() {
    return portRotationId_;
  }

  public static final int SYNOPTICTABLEID_FIELD_NUMBER = 7;
  private long synopticTableId_;
  /**
   * <code>int64 synopticTableId = 7;</code>
   *
   * @return The synopticTableId.
   */
  public long getSynopticTableId() {
    return synopticTableId_;
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
    if (companyId_ != 0L) {
      output.writeInt64(1, companyId_);
    }
    if (vesselId_ != 0L) {
      output.writeInt64(2, vesselId_);
    }
    if (voyageId_ != 0L) {
      output.writeInt64(3, voyageId_);
    }
    if (dischargeInfoId_ != 0L) {
      output.writeInt64(4, dischargeInfoId_);
    }
    if (dischargePatternId_ != 0L) {
      output.writeInt64(5, dischargePatternId_);
    }
    if (portRotationId_ != 0L) {
      output.writeInt64(6, portRotationId_);
    }
    if (synopticTableId_ != 0L) {
      output.writeInt64(7, synopticTableId_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (companyId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, companyId_);
    }
    if (vesselId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, vesselId_);
    }
    if (voyageId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, voyageId_);
    }
    if (dischargeInfoId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, dischargeInfoId_);
    }
    if (dischargePatternId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(5, dischargePatternId_);
    }
    if (portRotationId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(6, portRotationId_);
    }
    if (synopticTableId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(7, synopticTableId_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargeInformationRequest)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargeInformationRequest other =
        (com.cpdss.common.generated.discharge_plan.DischargeInformationRequest) obj;

    if (getCompanyId() != other.getCompanyId()) return false;
    if (getVesselId() != other.getVesselId()) return false;
    if (getVoyageId() != other.getVoyageId()) return false;
    if (getDischargeInfoId() != other.getDischargeInfoId()) return false;
    if (getDischargePatternId() != other.getDischargePatternId()) return false;
    if (getPortRotationId() != other.getPortRotationId()) return false;
    if (getSynopticTableId() != other.getSynopticTableId()) return false;
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
    hash = (37 * hash) + COMPANYID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCompanyId());
    hash = (37 * hash) + VESSELID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVesselId());
    hash = (37 * hash) + VOYAGEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVoyageId());
    hash = (37 * hash) + DISCHARGEINFOID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargeInfoId());
    hash = (37 * hash) + DISCHARGEPATTERNID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargePatternId());
    hash = (37 * hash) + PORTROTATIONID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortRotationId());
    hash = (37 * hash) + SYNOPTICTABLEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getSynopticTableId());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parseFrom(
      byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest
      parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest
      parseDelimitedFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargeInformationRequest prototype) {
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
  /**
   *
   *
   * <pre>
   * A generic request with Optional params
   * </pre>
   *
   * Protobuf type {@code DischargeInformationRequest}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargeInformationRequest)
      com.cpdss.common.generated.discharge_plan.DischargeInformationRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInformationRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInformationRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargeInformationRequest.class,
              com.cpdss.common.generated.discharge_plan.DischargeInformationRequest.Builder.class);
    }

    // Construct using
    // com.cpdss.common.generated.discharge_plan.DischargeInformationRequest.newBuilder()
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
      companyId_ = 0L;

      vesselId_ = 0L;

      voyageId_ = 0L;

      dischargeInfoId_ = 0L;

      dischargePatternId_ = 0L;

      portRotationId_ = 0L;

      synopticTableId_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInformationRequest_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInformationRequest
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargeInformationRequest
          .getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInformationRequest build() {
      com.cpdss.common.generated.discharge_plan.DischargeInformationRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInformationRequest buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargeInformationRequest result =
          new com.cpdss.common.generated.discharge_plan.DischargeInformationRequest(this);
      result.companyId_ = companyId_;
      result.vesselId_ = vesselId_;
      result.voyageId_ = voyageId_;
      result.dischargeInfoId_ = dischargeInfoId_;
      result.dischargePatternId_ = dischargePatternId_;
      result.portRotationId_ = portRotationId_;
      result.synopticTableId_ = synopticTableId_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargeInformationRequest) {
        return mergeFrom(
            (com.cpdss.common.generated.discharge_plan.DischargeInformationRequest) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.DischargeInformationRequest other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargeInformationRequest
              .getDefaultInstance()) return this;
      if (other.getCompanyId() != 0L) {
        setCompanyId(other.getCompanyId());
      }
      if (other.getVesselId() != 0L) {
        setVesselId(other.getVesselId());
      }
      if (other.getVoyageId() != 0L) {
        setVoyageId(other.getVoyageId());
      }
      if (other.getDischargeInfoId() != 0L) {
        setDischargeInfoId(other.getDischargeInfoId());
      }
      if (other.getDischargePatternId() != 0L) {
        setDischargePatternId(other.getDischargePatternId());
      }
      if (other.getPortRotationId() != 0L) {
        setPortRotationId(other.getPortRotationId());
      }
      if (other.getSynopticTableId() != 0L) {
        setSynopticTableId(other.getSynopticTableId());
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
      com.cpdss.common.generated.discharge_plan.DischargeInformationRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargeInformationRequest)
                e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long companyId_;
    /**
     * <code>int64 companyId = 1;</code>
     *
     * @return The companyId.
     */
    public long getCompanyId() {
      return companyId_;
    }
    /**
     * <code>int64 companyId = 1;</code>
     *
     * @param value The companyId to set.
     * @return This builder for chaining.
     */
    public Builder setCompanyId(long value) {

      companyId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 companyId = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCompanyId() {

      companyId_ = 0L;
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

    private long dischargeInfoId_;
    /**
     * <code>int64 dischargeInfoId = 4;</code>
     *
     * @return The dischargeInfoId.
     */
    public long getDischargeInfoId() {
      return dischargeInfoId_;
    }
    /**
     * <code>int64 dischargeInfoId = 4;</code>
     *
     * @param value The dischargeInfoId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargeInfoId(long value) {

      dischargeInfoId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 dischargeInfoId = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargeInfoId() {

      dischargeInfoId_ = 0L;
      onChanged();
      return this;
    }

    private long dischargePatternId_;
    /**
     * <code>int64 dischargePatternId = 5;</code>
     *
     * @return The dischargePatternId.
     */
    public long getDischargePatternId() {
      return dischargePatternId_;
    }
    /**
     * <code>int64 dischargePatternId = 5;</code>
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
     * <code>int64 dischargePatternId = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargePatternId() {

      dischargePatternId_ = 0L;
      onChanged();
      return this;
    }

    private long portRotationId_;
    /**
     * <code>int64 portRotationId = 6;</code>
     *
     * @return The portRotationId.
     */
    public long getPortRotationId() {
      return portRotationId_;
    }
    /**
     * <code>int64 portRotationId = 6;</code>
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
     * <code>int64 portRotationId = 6;</code>
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
     * <code>int64 synopticTableId = 7;</code>
     *
     * @return The synopticTableId.
     */
    public long getSynopticTableId() {
      return synopticTableId_;
    }
    /**
     * <code>int64 synopticTableId = 7;</code>
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
     * <code>int64 synopticTableId = 7;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearSynopticTableId() {

      synopticTableId_ = 0L;
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

    // @@protoc_insertion_point(builder_scope:DischargeInformationRequest)
  }

  // @@protoc_insertion_point(class_scope:DischargeInformationRequest)
  private static final com.cpdss.common.generated.discharge_plan.DischargeInformationRequest
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargeInformationRequest();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformationRequest
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargeInformationRequest> PARSER =
      new com.google.protobuf.AbstractParser<DischargeInformationRequest>() {
        @java.lang.Override
        public DischargeInformationRequest parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargeInformationRequest(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargeInformationRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargeInformationRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargeInformationRequest
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
