/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

public final class EnvoyWriter {
  private EnvoyWriter() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public interface EnvoyWriterRequestOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:EnvoyWriterRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string jsonPayload = 1;</code>
     *
     * @return The jsonPayload.
     */
    java.lang.String getJsonPayload();
    /**
     * <code>string jsonPayload = 1;</code>
     *
     * @return The bytes for jsonPayload.
     */
    com.google.protobuf.ByteString getJsonPayloadBytes();

    /**
     * <code>string imoNumber = 2;</code>
     *
     * @return The imoNumber.
     */
    java.lang.String getImoNumber();
    /**
     * <code>string imoNumber = 2;</code>
     *
     * @return The bytes for imoNumber.
     */
    com.google.protobuf.ByteString getImoNumberBytes();

    /**
     * <code>int64 vesselId = 3;</code>
     *
     * @return The vesselId.
     */
    long getVesselId();

    /**
     * <code>string requestType = 4;</code>
     *
     * @return The requestType.
     */
    java.lang.String getRequestType();
    /**
     * <code>string requestType = 4;</code>
     *
     * @return The bytes for requestType.
     */
    com.google.protobuf.ByteString getRequestTypeBytes();

    /**
     * <code>string vesselName = 5;</code>
     *
     * @return The vesselName.
     */
    java.lang.String getVesselName();
    /**
     * <code>string vesselName = 5;</code>
     *
     * @return The bytes for vesselName.
     */
    com.google.protobuf.ByteString getVesselNameBytes();
  }
  /** Protobuf type {@code EnvoyWriterRequest} */
  public static final class EnvoyWriterRequest extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:EnvoyWriterRequest)
      EnvoyWriterRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use EnvoyWriterRequest.newBuilder() to construct.
    private EnvoyWriterRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private EnvoyWriterRequest() {
      jsonPayload_ = "";
      imoNumber_ = "";
      requestType_ = "";
      vesselName_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new EnvoyWriterRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private EnvoyWriterRequest(
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
                java.lang.String s = input.readStringRequireUtf8();

                jsonPayload_ = s;
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                imoNumber_ = s;
                break;
              }
            case 24:
              {
                vesselId_ = input.readInt64();
                break;
              }
            case 34:
              {
                java.lang.String s = input.readStringRequireUtf8();

                requestType_ = s;
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                vesselName_ = s;
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
      return com.cpdss.common.generated.EnvoyWriter.internal_static_EnvoyWriterRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.EnvoyWriter
          .internal_static_EnvoyWriterRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest.class,
              com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest.Builder.class);
    }

    public static final int JSONPAYLOAD_FIELD_NUMBER = 1;
    private volatile java.lang.Object jsonPayload_;
    /**
     * <code>string jsonPayload = 1;</code>
     *
     * @return The jsonPayload.
     */
    public java.lang.String getJsonPayload() {
      java.lang.Object ref = jsonPayload_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        jsonPayload_ = s;
        return s;
      }
    }
    /**
     * <code>string jsonPayload = 1;</code>
     *
     * @return The bytes for jsonPayload.
     */
    public com.google.protobuf.ByteString getJsonPayloadBytes() {
      java.lang.Object ref = jsonPayload_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        jsonPayload_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int IMONUMBER_FIELD_NUMBER = 2;
    private volatile java.lang.Object imoNumber_;
    /**
     * <code>string imoNumber = 2;</code>
     *
     * @return The imoNumber.
     */
    public java.lang.String getImoNumber() {
      java.lang.Object ref = imoNumber_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        imoNumber_ = s;
        return s;
      }
    }
    /**
     * <code>string imoNumber = 2;</code>
     *
     * @return The bytes for imoNumber.
     */
    public com.google.protobuf.ByteString getImoNumberBytes() {
      java.lang.Object ref = imoNumber_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        imoNumber_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int VESSELID_FIELD_NUMBER = 3;
    private long vesselId_;
    /**
     * <code>int64 vesselId = 3;</code>
     *
     * @return The vesselId.
     */
    public long getVesselId() {
      return vesselId_;
    }

    public static final int REQUESTTYPE_FIELD_NUMBER = 4;
    private volatile java.lang.Object requestType_;
    /**
     * <code>string requestType = 4;</code>
     *
     * @return The requestType.
     */
    public java.lang.String getRequestType() {
      java.lang.Object ref = requestType_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        requestType_ = s;
        return s;
      }
    }
    /**
     * <code>string requestType = 4;</code>
     *
     * @return The bytes for requestType.
     */
    public com.google.protobuf.ByteString getRequestTypeBytes() {
      java.lang.Object ref = requestType_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        requestType_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int VESSELNAME_FIELD_NUMBER = 5;
    private volatile java.lang.Object vesselName_;
    /**
     * <code>string vesselName = 5;</code>
     *
     * @return The vesselName.
     */
    public java.lang.String getVesselName() {
      java.lang.Object ref = vesselName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        vesselName_ = s;
        return s;
      }
    }
    /**
     * <code>string vesselName = 5;</code>
     *
     * @return The bytes for vesselName.
     */
    public com.google.protobuf.ByteString getVesselNameBytes() {
      java.lang.Object ref = vesselName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        vesselName_ = b;
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
      if (!getJsonPayloadBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, jsonPayload_);
      }
      if (!getImoNumberBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, imoNumber_);
      }
      if (vesselId_ != 0L) {
        output.writeInt64(3, vesselId_);
      }
      if (!getRequestTypeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, requestType_);
      }
      if (!getVesselNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, vesselName_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getJsonPayloadBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, jsonPayload_);
      }
      if (!getImoNumberBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, imoNumber_);
      }
      if (vesselId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, vesselId_);
      }
      if (!getRequestTypeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, requestType_);
      }
      if (!getVesselNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, vesselName_);
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
      if (!(obj instanceof com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest other =
          (com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest) obj;

      if (!getJsonPayload().equals(other.getJsonPayload())) return false;
      if (!getImoNumber().equals(other.getImoNumber())) return false;
      if (getVesselId() != other.getVesselId()) return false;
      if (!getRequestType().equals(other.getRequestType())) return false;
      if (!getVesselName().equals(other.getVesselName())) return false;
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
      hash = (37 * hash) + JSONPAYLOAD_FIELD_NUMBER;
      hash = (53 * hash) + getJsonPayload().hashCode();
      hash = (37 * hash) + IMONUMBER_FIELD_NUMBER;
      hash = (53 * hash) + getImoNumber().hashCode();
      hash = (37 * hash) + VESSELID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVesselId());
      hash = (37 * hash) + REQUESTTYPE_FIELD_NUMBER;
      hash = (53 * hash) + getRequestType().hashCode();
      hash = (37 * hash) + VESSELNAME_FIELD_NUMBER;
      hash = (53 * hash) + getVesselName().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parseFrom(
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
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /** Protobuf type {@code EnvoyWriterRequest} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:EnvoyWriterRequest)
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.EnvoyWriter.internal_static_EnvoyWriterRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.EnvoyWriter
            .internal_static_EnvoyWriterRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest.class,
                com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest.Builder.class);
      }

      // Construct using com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest.newBuilder()
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
        jsonPayload_ = "";

        imoNumber_ = "";

        vesselId_ = 0L;

        requestType_ = "";

        vesselName_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.EnvoyWriter.internal_static_EnvoyWriterRequest_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest getDefaultInstanceForType() {
        return com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest build() {
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest buildPartial() {
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest result =
            new com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest(this);
        result.jsonPayload_ = jsonPayload_;
        result.imoNumber_ = imoNumber_;
        result.vesselId_ = vesselId_;
        result.requestType_ = requestType_;
        result.vesselName_ = vesselName_;
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
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index,
          java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }

      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest) {
          return mergeFrom((com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest other) {
        if (other == com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest.getDefaultInstance())
          return this;
        if (!other.getJsonPayload().isEmpty()) {
          jsonPayload_ = other.jsonPayload_;
          onChanged();
        }
        if (!other.getImoNumber().isEmpty()) {
          imoNumber_ = other.imoNumber_;
          onChanged();
        }
        if (other.getVesselId() != 0L) {
          setVesselId(other.getVesselId());
        }
        if (!other.getRequestType().isEmpty()) {
          requestType_ = other.requestType_;
          onChanged();
        }
        if (!other.getVesselName().isEmpty()) {
          vesselName_ = other.vesselName_;
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
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object jsonPayload_ = "";
      /**
       * <code>string jsonPayload = 1;</code>
       *
       * @return The jsonPayload.
       */
      public java.lang.String getJsonPayload() {
        java.lang.Object ref = jsonPayload_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          jsonPayload_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string jsonPayload = 1;</code>
       *
       * @return The bytes for jsonPayload.
       */
      public com.google.protobuf.ByteString getJsonPayloadBytes() {
        java.lang.Object ref = jsonPayload_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          jsonPayload_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string jsonPayload = 1;</code>
       *
       * @param value The jsonPayload to set.
       * @return This builder for chaining.
       */
      public Builder setJsonPayload(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        jsonPayload_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string jsonPayload = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearJsonPayload() {

        jsonPayload_ = getDefaultInstance().getJsonPayload();
        onChanged();
        return this;
      }
      /**
       * <code>string jsonPayload = 1;</code>
       *
       * @param value The bytes for jsonPayload to set.
       * @return This builder for chaining.
       */
      public Builder setJsonPayloadBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        jsonPayload_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object imoNumber_ = "";
      /**
       * <code>string imoNumber = 2;</code>
       *
       * @return The imoNumber.
       */
      public java.lang.String getImoNumber() {
        java.lang.Object ref = imoNumber_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          imoNumber_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string imoNumber = 2;</code>
       *
       * @return The bytes for imoNumber.
       */
      public com.google.protobuf.ByteString getImoNumberBytes() {
        java.lang.Object ref = imoNumber_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          imoNumber_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string imoNumber = 2;</code>
       *
       * @param value The imoNumber to set.
       * @return This builder for chaining.
       */
      public Builder setImoNumber(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        imoNumber_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string imoNumber = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearImoNumber() {

        imoNumber_ = getDefaultInstance().getImoNumber();
        onChanged();
        return this;
      }
      /**
       * <code>string imoNumber = 2;</code>
       *
       * @param value The bytes for imoNumber to set.
       * @return This builder for chaining.
       */
      public Builder setImoNumberBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        imoNumber_ = value;
        onChanged();
        return this;
      }

      private long vesselId_;
      /**
       * <code>int64 vesselId = 3;</code>
       *
       * @return The vesselId.
       */
      public long getVesselId() {
        return vesselId_;
      }
      /**
       * <code>int64 vesselId = 3;</code>
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
       * <code>int64 vesselId = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearVesselId() {

        vesselId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object requestType_ = "";
      /**
       * <code>string requestType = 4;</code>
       *
       * @return The requestType.
       */
      public java.lang.String getRequestType() {
        java.lang.Object ref = requestType_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          requestType_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string requestType = 4;</code>
       *
       * @return The bytes for requestType.
       */
      public com.google.protobuf.ByteString getRequestTypeBytes() {
        java.lang.Object ref = requestType_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          requestType_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string requestType = 4;</code>
       *
       * @param value The requestType to set.
       * @return This builder for chaining.
       */
      public Builder setRequestType(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        requestType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string requestType = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearRequestType() {

        requestType_ = getDefaultInstance().getRequestType();
        onChanged();
        return this;
      }
      /**
       * <code>string requestType = 4;</code>
       *
       * @param value The bytes for requestType to set.
       * @return This builder for chaining.
       */
      public Builder setRequestTypeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        requestType_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object vesselName_ = "";
      /**
       * <code>string vesselName = 5;</code>
       *
       * @return The vesselName.
       */
      public java.lang.String getVesselName() {
        java.lang.Object ref = vesselName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          vesselName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string vesselName = 5;</code>
       *
       * @return The bytes for vesselName.
       */
      public com.google.protobuf.ByteString getVesselNameBytes() {
        java.lang.Object ref = vesselName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          vesselName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string vesselName = 5;</code>
       *
       * @param value The vesselName to set.
       * @return This builder for chaining.
       */
      public Builder setVesselName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        vesselName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string vesselName = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearVesselName() {

        vesselName_ = getDefaultInstance().getVesselName();
        onChanged();
        return this;
      }
      /**
       * <code>string vesselName = 5;</code>
       *
       * @param value The bytes for vesselName to set.
       * @return This builder for chaining.
       */
      public Builder setVesselNameBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        vesselName_ = value;
        onChanged();
        return this;
      }

      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }

      // @@protoc_insertion_point(builder_scope:EnvoyWriterRequest)
    }

    // @@protoc_insertion_point(class_scope:EnvoyWriterRequest)
    private static final com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest();
    }

    public static com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<EnvoyWriterRequest> PARSER =
        new com.google.protobuf.AbstractParser<EnvoyWriterRequest>() {
          @java.lang.Override
          public EnvoyWriterRequest parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new EnvoyWriterRequest(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<EnvoyWriterRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<EnvoyWriterRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface WriterReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:WriterReply)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.ResponseStatus responseStatus = 1;</code>
     *
     * @return Whether the responseStatus field is set.
     */
    boolean hasResponseStatus();
    /**
     * <code>.ResponseStatus responseStatus = 1;</code>
     *
     * @return The responseStatus.
     */
    com.cpdss.common.generated.Common.ResponseStatus getResponseStatus();
    /** <code>.ResponseStatus responseStatus = 1;</code> */
    com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder();

    /**
     * <code>string messageId = 2;</code>
     *
     * @return The messageId.
     */
    java.lang.String getMessageId();
    /**
     * <code>string messageId = 2;</code>
     *
     * @return The bytes for messageId.
     */
    com.google.protobuf.ByteString getMessageIdBytes();

    /**
     * <code>string shipId = 3;</code>
     *
     * @return The shipId.
     */
    java.lang.String getShipId();
    /**
     * <code>string shipId = 3;</code>
     *
     * @return The bytes for shipId.
     */
    com.google.protobuf.ByteString getShipIdBytes();

    /**
     * <code>string message = 4;</code>
     *
     * @return The message.
     */
    java.lang.String getMessage();
    /**
     * <code>string message = 4;</code>
     *
     * @return The bytes for message.
     */
    com.google.protobuf.ByteString getMessageBytes();
  }
  /** Protobuf type {@code WriterReply} */
  public static final class WriterReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:WriterReply)
      WriterReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use WriterReply.newBuilder() to construct.
    private WriterReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private WriterReply() {
      messageId_ = "";
      shipId_ = "";
      message_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new WriterReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private WriterReply(
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
                        com.cpdss.common.generated.Common.ResponseStatus.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(responseStatus_);
                  responseStatus_ = subBuilder.buildPartial();
                }

                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                messageId_ = s;
                break;
              }
            case 26:
              {
                java.lang.String s = input.readStringRequireUtf8();

                shipId_ = s;
                break;
              }
            case 34:
              {
                java.lang.String s = input.readStringRequireUtf8();

                message_ = s;
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
      return com.cpdss.common.generated.EnvoyWriter.internal_static_WriterReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.EnvoyWriter.internal_static_WriterReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.EnvoyWriter.WriterReply.class,
              com.cpdss.common.generated.EnvoyWriter.WriterReply.Builder.class);
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

    public static final int MESSAGEID_FIELD_NUMBER = 2;
    private volatile java.lang.Object messageId_;
    /**
     * <code>string messageId = 2;</code>
     *
     * @return The messageId.
     */
    public java.lang.String getMessageId() {
      java.lang.Object ref = messageId_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        messageId_ = s;
        return s;
      }
    }
    /**
     * <code>string messageId = 2;</code>
     *
     * @return The bytes for messageId.
     */
    public com.google.protobuf.ByteString getMessageIdBytes() {
      java.lang.Object ref = messageId_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        messageId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SHIPID_FIELD_NUMBER = 3;
    private volatile java.lang.Object shipId_;
    /**
     * <code>string shipId = 3;</code>
     *
     * @return The shipId.
     */
    public java.lang.String getShipId() {
      java.lang.Object ref = shipId_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        shipId_ = s;
        return s;
      }
    }
    /**
     * <code>string shipId = 3;</code>
     *
     * @return The bytes for shipId.
     */
    public com.google.protobuf.ByteString getShipIdBytes() {
      java.lang.Object ref = shipId_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        shipId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int MESSAGE_FIELD_NUMBER = 4;
    private volatile java.lang.Object message_;
    /**
     * <code>string message = 4;</code>
     *
     * @return The message.
     */
    public java.lang.String getMessage() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        message_ = s;
        return s;
      }
    }
    /**
     * <code>string message = 4;</code>
     *
     * @return The bytes for message.
     */
    public com.google.protobuf.ByteString getMessageBytes() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        message_ = b;
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
      if (!getMessageIdBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, messageId_);
      }
      if (!getShipIdBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, shipId_);
      }
      if (!getMessageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, message_);
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
      if (!getMessageIdBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, messageId_);
      }
      if (!getShipIdBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, shipId_);
      }
      if (!getMessageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, message_);
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
      if (!(obj instanceof com.cpdss.common.generated.EnvoyWriter.WriterReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.EnvoyWriter.WriterReply other =
          (com.cpdss.common.generated.EnvoyWriter.WriterReply) obj;

      if (hasResponseStatus() != other.hasResponseStatus()) return false;
      if (hasResponseStatus()) {
        if (!getResponseStatus().equals(other.getResponseStatus())) return false;
      }
      if (!getMessageId().equals(other.getMessageId())) return false;
      if (!getShipId().equals(other.getShipId())) return false;
      if (!getMessage().equals(other.getMessage())) return false;
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
      hash = (37 * hash) + MESSAGEID_FIELD_NUMBER;
      hash = (53 * hash) + getMessageId().hashCode();
      hash = (37 * hash) + SHIPID_FIELD_NUMBER;
      hash = (53 * hash) + getShipId().hashCode();
      hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
      hash = (53 * hash) + getMessage().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.EnvoyWriter.WriterReply prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /** Protobuf type {@code WriterReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:WriterReply)
        com.cpdss.common.generated.EnvoyWriter.WriterReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.EnvoyWriter.internal_static_WriterReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.EnvoyWriter.internal_static_WriterReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.EnvoyWriter.WriterReply.class,
                com.cpdss.common.generated.EnvoyWriter.WriterReply.Builder.class);
      }

      // Construct using com.cpdss.common.generated.EnvoyWriter.WriterReply.newBuilder()
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
        messageId_ = "";

        shipId_ = "";

        message_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.EnvoyWriter.internal_static_WriterReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.EnvoyWriter.WriterReply getDefaultInstanceForType() {
        return com.cpdss.common.generated.EnvoyWriter.WriterReply.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.EnvoyWriter.WriterReply build() {
        com.cpdss.common.generated.EnvoyWriter.WriterReply result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.EnvoyWriter.WriterReply buildPartial() {
        com.cpdss.common.generated.EnvoyWriter.WriterReply result =
            new com.cpdss.common.generated.EnvoyWriter.WriterReply(this);
        if (responseStatusBuilder_ == null) {
          result.responseStatus_ = responseStatus_;
        } else {
          result.responseStatus_ = responseStatusBuilder_.build();
        }
        result.messageId_ = messageId_;
        result.shipId_ = shipId_;
        result.message_ = message_;
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
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index,
          java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }

      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.cpdss.common.generated.EnvoyWriter.WriterReply) {
          return mergeFrom((com.cpdss.common.generated.EnvoyWriter.WriterReply) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.EnvoyWriter.WriterReply other) {
        if (other == com.cpdss.common.generated.EnvoyWriter.WriterReply.getDefaultInstance())
          return this;
        if (other.hasResponseStatus()) {
          mergeResponseStatus(other.getResponseStatus());
        }
        if (!other.getMessageId().isEmpty()) {
          messageId_ = other.messageId_;
          onChanged();
        }
        if (!other.getShipId().isEmpty()) {
          shipId_ = other.shipId_;
          onChanged();
        }
        if (!other.getMessage().isEmpty()) {
          message_ = other.message_;
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
        com.cpdss.common.generated.EnvoyWriter.WriterReply parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.EnvoyWriter.WriterReply) e.getUnfinishedMessage();
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
      public com.cpdss.common.generated.Common.ResponseStatusOrBuilder
          getResponseStatusOrBuilder() {
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

      private java.lang.Object messageId_ = "";
      /**
       * <code>string messageId = 2;</code>
       *
       * @return The messageId.
       */
      public java.lang.String getMessageId() {
        java.lang.Object ref = messageId_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          messageId_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string messageId = 2;</code>
       *
       * @return The bytes for messageId.
       */
      public com.google.protobuf.ByteString getMessageIdBytes() {
        java.lang.Object ref = messageId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          messageId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string messageId = 2;</code>
       *
       * @param value The messageId to set.
       * @return This builder for chaining.
       */
      public Builder setMessageId(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        messageId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string messageId = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearMessageId() {

        messageId_ = getDefaultInstance().getMessageId();
        onChanged();
        return this;
      }
      /**
       * <code>string messageId = 2;</code>
       *
       * @param value The bytes for messageId to set.
       * @return This builder for chaining.
       */
      public Builder setMessageIdBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        messageId_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object shipId_ = "";
      /**
       * <code>string shipId = 3;</code>
       *
       * @return The shipId.
       */
      public java.lang.String getShipId() {
        java.lang.Object ref = shipId_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          shipId_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string shipId = 3;</code>
       *
       * @return The bytes for shipId.
       */
      public com.google.protobuf.ByteString getShipIdBytes() {
        java.lang.Object ref = shipId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          shipId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string shipId = 3;</code>
       *
       * @param value The shipId to set.
       * @return This builder for chaining.
       */
      public Builder setShipId(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        shipId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string shipId = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearShipId() {

        shipId_ = getDefaultInstance().getShipId();
        onChanged();
        return this;
      }
      /**
       * <code>string shipId = 3;</code>
       *
       * @param value The bytes for shipId to set.
       * @return This builder for chaining.
       */
      public Builder setShipIdBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        shipId_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object message_ = "";
      /**
       * <code>string message = 4;</code>
       *
       * @return The message.
       */
      public java.lang.String getMessage() {
        java.lang.Object ref = message_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          message_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string message = 4;</code>
       *
       * @return The bytes for message.
       */
      public com.google.protobuf.ByteString getMessageBytes() {
        java.lang.Object ref = message_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          message_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string message = 4;</code>
       *
       * @param value The message to set.
       * @return This builder for chaining.
       */
      public Builder setMessage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        message_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string message = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearMessage() {

        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }
      /**
       * <code>string message = 4;</code>
       *
       * @param value The bytes for message to set.
       * @return This builder for chaining.
       */
      public Builder setMessageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        message_ = value;
        onChanged();
        return this;
      }

      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }

      // @@protoc_insertion_point(builder_scope:WriterReply)
    }

    // @@protoc_insertion_point(class_scope:WriterReply)
    private static final com.cpdss.common.generated.EnvoyWriter.WriterReply DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.EnvoyWriter.WriterReply();
    }

    public static com.cpdss.common.generated.EnvoyWriter.WriterReply getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<WriterReply> PARSER =
        new com.google.protobuf.AbstractParser<WriterReply>() {
          @java.lang.Override
          public WriterReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new WriterReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<WriterReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<WriterReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.EnvoyWriter.WriterReply getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_EnvoyWriterRequest_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_EnvoyWriterRequest_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_WriterReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_WriterReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\022envoy_writer.proto\032\014common.proto\"w\n\022En"
          + "voyWriterRequest\022\023\n\013jsonPayload\030\001 \001(\t\022\021\n"
          + "\timoNumber\030\002 \001(\t\022\020\n\010vesselId\030\003 \001(\003\022\023\n\013re"
          + "questType\030\004 \001(\t\022\022\n\nvesselName\030\005 \001(\t\"j\n\013W"
          + "riterReply\022\'\n\016responseStatus\030\001 \001(\0132\017.Res"
          + "ponseStatus\022\021\n\tmessageId\030\002 \001(\t\022\016\n\006shipId"
          + "\030\003 \001(\t\022\017\n\007message\030\004 \001(\t2S\n\022EnvoyWriterSe"
          + "rvice\022=\n\026GetCommunicationServer\022\023.EnvoyW"
          + "riterRequest\032\014.WriterReply\"\000B\036\n\032com.cpds"
          + "s.common.generatedP\000b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
            });
    internal_static_EnvoyWriterRequest_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_EnvoyWriterRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_EnvoyWriterRequest_descriptor,
            new java.lang.String[] {
              "JsonPayload", "ImoNumber", "VesselId", "RequestType", "VesselName",
            });
    internal_static_WriterReply_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_WriterReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_WriterReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus", "MessageId", "ShipId", "Message",
            });
    com.cpdss.common.generated.Common.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
