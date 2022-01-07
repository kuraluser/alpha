/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code PortDischargingPlanRobDetailsRequest} */
public final class PortDischargingPlanRobDetailsRequest
    extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:PortDischargingPlanRobDetailsRequest)
    PortDischargingPlanRobDetailsRequestOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use PortDischargingPlanRobDetailsRequest.newBuilder() to construct.
  private PortDischargingPlanRobDetailsRequest(
      com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private PortDischargingPlanRobDetailsRequest() {}

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new PortDischargingPlanRobDetailsRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private PortDischargingPlanRobDetailsRequest(
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
              portXId_ = input.readInt64();
              break;
            }
          case 16:
            {
              portRotationXId_ = input.readInt64();
              break;
            }
          case 24:
            {
              conditionType_ = input.readInt32();
              break;
            }
          case 32:
            {
              valueType_ = input.readInt32();
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
        .internal_static_PortDischargingPlanRobDetailsRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_PortDischargingPlanRobDetailsRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest.class,
            com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest.Builder
                .class);
  }

  public static final int PORTXID_FIELD_NUMBER = 1;
  private long portXId_;
  /**
   * <code>int64 portXId = 1;</code>
   *
   * @return The portXId.
   */
  public long getPortXId() {
    return portXId_;
  }

  public static final int PORTROTATIONXID_FIELD_NUMBER = 2;
  private long portRotationXId_;
  /**
   * <code>int64 portRotationXId = 2;</code>
   *
   * @return The portRotationXId.
   */
  public long getPortRotationXId() {
    return portRotationXId_;
  }

  public static final int CONDITIONTYPE_FIELD_NUMBER = 3;
  private int conditionType_;
  /**
   * <code>int32 conditionType = 3;</code>
   *
   * @return The conditionType.
   */
  public int getConditionType() {
    return conditionType_;
  }

  public static final int VALUETYPE_FIELD_NUMBER = 4;
  private int valueType_;
  /**
   * <code>int32 valueType = 4;</code>
   *
   * @return The valueType.
   */
  public int getValueType() {
    return valueType_;
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
    if (portXId_ != 0L) {
      output.writeInt64(1, portXId_);
    }
    if (portRotationXId_ != 0L) {
      output.writeInt64(2, portRotationXId_);
    }
    if (conditionType_ != 0) {
      output.writeInt32(3, conditionType_);
    }
    if (valueType_ != 0) {
      output.writeInt32(4, valueType_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (portXId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, portXId_);
    }
    if (portRotationXId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, portRotationXId_);
    }
    if (conditionType_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(3, conditionType_);
    }
    if (valueType_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(4, valueType_);
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
        instanceof
        com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest other =
        (com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest) obj;

    if (getPortXId() != other.getPortXId()) return false;
    if (getPortRotationXId() != other.getPortRotationXId()) return false;
    if (getConditionType() != other.getConditionType()) return false;
    if (getValueType() != other.getValueType()) return false;
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
    hash = (37 * hash) + PORTXID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortXId());
    hash = (37 * hash) + PORTROTATIONXID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortRotationXId());
    hash = (37 * hash) + CONDITIONTYPE_FIELD_NUMBER;
    hash = (53 * hash) + getConditionType();
    hash = (37 * hash) + VALUETYPE_FIELD_NUMBER;
    hash = (53 * hash) + getValueType();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseFrom(java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseFrom(
          java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseFrom(com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseDelimitedFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
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
      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest prototype) {
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
  /** Protobuf type {@code PortDischargingPlanRobDetailsRequest} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:PortDischargingPlanRobDetailsRequest)
      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_PortDischargingPlanRobDetailsRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_PortDischargingPlanRobDetailsRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest.class,
              com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest.Builder
                  .class);
    }

    // Construct using
    // com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest.newBuilder()
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
      portXId_ = 0L;

      portRotationXId_ = 0L;

      conditionType_ = 0;

      valueType_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_PortDischargingPlanRobDetailsRequest_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
          .getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest build() {
      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest result =
          buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
        buildPartial() {
      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest result =
          new com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest(this);
      result.portXId_ = portXId_;
      result.portRotationXId_ = portRotationXId_;
      result.conditionType_ = conditionType_;
      result.valueType_ = valueType_;
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
          instanceof
          com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest) {
        return mergeFrom(
            (com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
              .getDefaultInstance()) return this;
      if (other.getPortXId() != 0L) {
        setPortXId(other.getPortXId());
      }
      if (other.getPortRotationXId() != 0L) {
        setPortRotationXId(other.getPortRotationXId());
      }
      if (other.getConditionType() != 0) {
        setConditionType(other.getConditionType());
      }
      if (other.getValueType() != 0) {
        setValueType(other.getValueType());
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
      com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest parsedMessage =
          null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest)
                e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long portXId_;
    /**
     * <code>int64 portXId = 1;</code>
     *
     * @return The portXId.
     */
    public long getPortXId() {
      return portXId_;
    }
    /**
     * <code>int64 portXId = 1;</code>
     *
     * @param value The portXId to set.
     * @return This builder for chaining.
     */
    public Builder setPortXId(long value) {

      portXId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 portXId = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPortXId() {

      portXId_ = 0L;
      onChanged();
      return this;
    }

    private long portRotationXId_;
    /**
     * <code>int64 portRotationXId = 2;</code>
     *
     * @return The portRotationXId.
     */
    public long getPortRotationXId() {
      return portRotationXId_;
    }
    /**
     * <code>int64 portRotationXId = 2;</code>
     *
     * @param value The portRotationXId to set.
     * @return This builder for chaining.
     */
    public Builder setPortRotationXId(long value) {

      portRotationXId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 portRotationXId = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPortRotationXId() {

      portRotationXId_ = 0L;
      onChanged();
      return this;
    }

    private int conditionType_;
    /**
     * <code>int32 conditionType = 3;</code>
     *
     * @return The conditionType.
     */
    public int getConditionType() {
      return conditionType_;
    }
    /**
     * <code>int32 conditionType = 3;</code>
     *
     * @param value The conditionType to set.
     * @return This builder for chaining.
     */
    public Builder setConditionType(int value) {

      conditionType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 conditionType = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearConditionType() {

      conditionType_ = 0;
      onChanged();
      return this;
    }

    private int valueType_;
    /**
     * <code>int32 valueType = 4;</code>
     *
     * @return The valueType.
     */
    public int getValueType() {
      return valueType_;
    }
    /**
     * <code>int32 valueType = 4;</code>
     *
     * @param value The valueType to set.
     * @return This builder for chaining.
     */
    public Builder setValueType(int value) {

      valueType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 valueType = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearValueType() {

      valueType_ = 0;
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

    // @@protoc_insertion_point(builder_scope:PortDischargingPlanRobDetailsRequest)
  }

  // @@protoc_insertion_point(class_scope:PortDischargingPlanRobDetailsRequest)
  private static final com.cpdss.common.generated.discharge_plan
          .PortDischargingPlanRobDetailsRequest
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE =
        new com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest();
  }

  public static com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PortDischargingPlanRobDetailsRequest> PARSER =
      new com.google.protobuf.AbstractParser<PortDischargingPlanRobDetailsRequest>() {
        @java.lang.Override
        public PortDischargingPlanRobDetailsRequest parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new PortDischargingPlanRobDetailsRequest(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<PortDischargingPlanRobDetailsRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PortDischargingPlanRobDetailsRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.PortDischargingPlanRobDetailsRequest
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
