/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargePlanAlgoRequest} */
public final class DischargePlanAlgoRequest extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargePlanAlgoRequest)
    DischargePlanAlgoRequestOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargePlanAlgoRequest.newBuilder() to construct.
  private DischargePlanAlgoRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargePlanAlgoRequest() {
    requestAsJsonString_ = "";
    processId_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargePlanAlgoRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargePlanAlgoRequest(
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
              java.lang.String s = input.readStringRequireUtf8();

              requestAsJsonString_ = s;
              break;
            }
          case 26:
            {
              java.lang.String s = input.readStringRequireUtf8();

              processId_ = s;
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
        .internal_static_DischargePlanAlgoRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargePlanAlgoRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest.class,
            com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest.Builder.class);
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

  public static final int REQUESTASJSONSTRING_FIELD_NUMBER = 2;
  private volatile java.lang.Object requestAsJsonString_;
  /**
   * <code>string requestAsJsonString = 2;</code>
   *
   * @return The requestAsJsonString.
   */
  public java.lang.String getRequestAsJsonString() {
    java.lang.Object ref = requestAsJsonString_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      requestAsJsonString_ = s;
      return s;
    }
  }
  /**
   * <code>string requestAsJsonString = 2;</code>
   *
   * @return The bytes for requestAsJsonString.
   */
  public com.google.protobuf.ByteString getRequestAsJsonStringBytes() {
    java.lang.Object ref = requestAsJsonString_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      requestAsJsonString_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PROCESSID_FIELD_NUMBER = 3;
  private volatile java.lang.Object processId_;
  /**
   * <code>string processId = 3;</code>
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
   * <code>string processId = 3;</code>
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
    if (!getRequestAsJsonStringBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, requestAsJsonString_);
    }
    if (!getProcessIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, processId_);
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
    if (!getRequestAsJsonStringBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, requestAsJsonString_);
    }
    if (!getProcessIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, processId_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest other =
        (com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest) obj;

    if (hasResponseStatus() != other.hasResponseStatus()) return false;
    if (hasResponseStatus()) {
      if (!getResponseStatus().equals(other.getResponseStatus())) return false;
    }
    if (!getRequestAsJsonString().equals(other.getRequestAsJsonString())) return false;
    if (!getProcessId().equals(other.getProcessId())) return false;
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
    hash = (37 * hash) + REQUESTASJSONSTRING_FIELD_NUMBER;
    hash = (53 * hash) + getRequestAsJsonString().hashCode();
    hash = (37 * hash) + PROCESSID_FIELD_NUMBER;
    hash = (53 * hash) + getProcessId().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parseFrom(
      byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest
      parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest
      parseDelimitedFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest prototype) {
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
  /** Protobuf type {@code DischargePlanAlgoRequest} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargePlanAlgoRequest)
      com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargePlanAlgoRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargePlanAlgoRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest.class,
              com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest.Builder.class);
    }

    // Construct using
    // com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest.newBuilder()
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
      if (responseStatusBuilder_ == null) {
        responseStatus_ = null;
      } else {
        responseStatus_ = null;
        responseStatusBuilder_ = null;
      }
      requestAsJsonString_ = "";

      processId_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargePlanAlgoRequest_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest
          .getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest build() {
      com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest result =
          new com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest(this);
      if (responseStatusBuilder_ == null) {
        result.responseStatus_ = responseStatus_;
      } else {
        result.responseStatus_ = responseStatusBuilder_.build();
      }
      result.requestAsJsonString_ = requestAsJsonString_;
      result.processId_ = processId_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest) {
        return mergeFrom(
            (com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest
              .getDefaultInstance()) return this;
      if (other.hasResponseStatus()) {
        mergeResponseStatus(other.getResponseStatus());
      }
      if (!other.getRequestAsJsonString().isEmpty()) {
        requestAsJsonString_ = other.requestAsJsonString_;
        onChanged();
      }
      if (!other.getProcessId().isEmpty()) {
        processId_ = other.processId_;
        onChanged();
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
      com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest)
                e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

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

    private java.lang.Object requestAsJsonString_ = "";
    /**
     * <code>string requestAsJsonString = 2;</code>
     *
     * @return The requestAsJsonString.
     */
    public java.lang.String getRequestAsJsonString() {
      java.lang.Object ref = requestAsJsonString_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        requestAsJsonString_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string requestAsJsonString = 2;</code>
     *
     * @return The bytes for requestAsJsonString.
     */
    public com.google.protobuf.ByteString getRequestAsJsonStringBytes() {
      java.lang.Object ref = requestAsJsonString_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        requestAsJsonString_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string requestAsJsonString = 2;</code>
     *
     * @param value The requestAsJsonString to set.
     * @return This builder for chaining.
     */
    public Builder setRequestAsJsonString(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      requestAsJsonString_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string requestAsJsonString = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearRequestAsJsonString() {

      requestAsJsonString_ = getDefaultInstance().getRequestAsJsonString();
      onChanged();
      return this;
    }
    /**
     * <code>string requestAsJsonString = 2;</code>
     *
     * @param value The bytes for requestAsJsonString to set.
     * @return This builder for chaining.
     */
    public Builder setRequestAsJsonStringBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      requestAsJsonString_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object processId_ = "";
    /**
     * <code>string processId = 3;</code>
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
     * <code>string processId = 3;</code>
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
     * <code>string processId = 3;</code>
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
     * <code>string processId = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearProcessId() {

      processId_ = getDefaultInstance().getProcessId();
      onChanged();
      return this;
    }
    /**
     * <code>string processId = 3;</code>
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

    @java.lang.Override
    public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }

    // @@protoc_insertion_point(builder_scope:DischargePlanAlgoRequest)
  }

  // @@protoc_insertion_point(class_scope:DischargePlanAlgoRequest)
  private static final com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargePlanAlgoRequest> PARSER =
      new com.google.protobuf.AbstractParser<DischargePlanAlgoRequest>() {
        @java.lang.Override
        public DischargePlanAlgoRequest parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargePlanAlgoRequest(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargePlanAlgoRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargePlanAlgoRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargePlanAlgoRequest
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
