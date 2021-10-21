/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargeInfoStatusRequest} */
public final class DischargeInfoStatusRequest extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargeInfoStatusRequest)
    DischargeInfoStatusRequestOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargeInfoStatusRequest.newBuilder() to construct.
  private DischargeInfoStatusRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargeInfoStatusRequest() {
    processId_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargeInfoStatusRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargeInfoStatusRequest(
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
              dischargeInfoId_ = input.readInt64();
              break;
            }
          case 18:
            {
              java.lang.String s = input.readStringRequireUtf8();

              processId_ = s;
              break;
            }
          case 24:
            {
              conditionType_ = input.readInt32();
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
        .internal_static_DischargeInfoStatusRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeInfoStatusRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest.class,
            com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest.Builder.class);
  }

  public static final int DISCHARGEINFOID_FIELD_NUMBER = 1;
  private long dischargeInfoId_;
  /**
   * <code>int64 dischargeInfoId = 1;</code>
   *
   * @return The dischargeInfoId.
   */
  public long getDischargeInfoId() {
    return dischargeInfoId_;
  }

  public static final int PROCESSID_FIELD_NUMBER = 2;
  private volatile java.lang.Object processId_;
  /**
   * <code>string processId = 2;</code>
   *
   * @return The processId.
   */
  public java.lang.String getProcessId() {
    java.lang.Object ref = processId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      processId_ = s;
      return s;
    }
  }
  /**
   * <code>string processId = 2;</code>
   *
   * @return The bytes for processId.
   */
  public com.google.protobuf.ByteString getProcessIdBytes() {
    java.lang.Object ref = processId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      processId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (dischargeInfoId_ != 0L) {
      output.writeInt64(1, dischargeInfoId_);
    }
    if (!getProcessIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, processId_);
    }
    if (conditionType_ != 0) {
      output.writeInt32(3, conditionType_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (dischargeInfoId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, dischargeInfoId_);
    }
    if (!getProcessIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, processId_);
    }
    if (conditionType_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(3, conditionType_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest other =
        (com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest) obj;

    if (getDischargeInfoId() != other.getDischargeInfoId()) return false;
    if (!getProcessId().equals(other.getProcessId())) return false;
    if (getConditionType() != other.getConditionType()) return false;
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
    hash = (37 * hash) + DISCHARGEINFOID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargeInfoId());
    hash = (37 * hash) + PROCESSID_FIELD_NUMBER;
    hash = (53 * hash) + getProcessId().hashCode();
    hash = (37 * hash) + CONDITIONTYPE_FIELD_NUMBER;
    hash = (53 * hash) + getConditionType();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parseFrom(
      byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest
      parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest
      parseDelimitedFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest prototype) {
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
  /** Protobuf type {@code DischargeInfoStatusRequest} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargeInfoStatusRequest)
      com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInfoStatusRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInfoStatusRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest.class,
              com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest.Builder.class);
    }

    // Construct using
    // com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest.newBuilder()
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
      dischargeInfoId_ = 0L;

      processId_ = "";

      conditionType_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInfoStatusRequest_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest
          .getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest build() {
      com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest result =
          new com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest(this);
      result.dischargeInfoId_ = dischargeInfoId_;
      result.processId_ = processId_;
      result.conditionType_ = conditionType_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest) {
        return mergeFrom(
            (com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest
              .getDefaultInstance()) return this;
      if (other.getDischargeInfoId() != 0L) {
        setDischargeInfoId(other.getDischargeInfoId());
      }
      if (!other.getProcessId().isEmpty()) {
        processId_ = other.processId_;
        onChanged();
      }
      if (other.getConditionType() != 0) {
        setConditionType(other.getConditionType());
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
      com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest)
                e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long dischargeInfoId_;
    /**
     * <code>int64 dischargeInfoId = 1;</code>
     *
     * @return The dischargeInfoId.
     */
    public long getDischargeInfoId() {
      return dischargeInfoId_;
    }
    /**
     * <code>int64 dischargeInfoId = 1;</code>
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
     * <code>int64 dischargeInfoId = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargeInfoId() {

      dischargeInfoId_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object processId_ = "";
    /**
     * <code>string processId = 2;</code>
     *
     * @return The processId.
     */
    public java.lang.String getProcessId() {
      java.lang.Object ref = processId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        processId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string processId = 2;</code>
     *
     * @return The bytes for processId.
     */
    public com.google.protobuf.ByteString getProcessIdBytes() {
      java.lang.Object ref = processId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        processId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string processId = 2;</code>
     *
     * @param value The processId to set.
     * @return This builder for chaining.
     */
    public Builder setProcessId(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      processId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string processId = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearProcessId() {

      processId_ = getDefaultInstance().getProcessId();
      onChanged();
      return this;
    }
    /**
     * <code>string processId = 2;</code>
     *
     * @param value The bytes for processId to set.
     * @return This builder for chaining.
     */
    public Builder setProcessIdBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      processId_ = value;
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

    @java.lang.Override
    public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }

    // @@protoc_insertion_point(builder_scope:DischargeInfoStatusRequest)
  }

  // @@protoc_insertion_point(class_scope:DischargeInfoStatusRequest)
  private static final com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargeInfoStatusRequest> PARSER =
      new com.google.protobuf.AbstractParser<DischargeInfoStatusRequest>() {
        @java.lang.Override
        public DischargeInfoStatusRequest parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargeInfoStatusRequest(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargeInfoStatusRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargeInfoStatusRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
