/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

public final class EnvoyWriter {
  private EnvoyWriter() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public interface LoadableStudyJsonOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadableStudyJson)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string loadableStudy = 1;</code>
     *
     * @return The loadableStudy.
     */
    java.lang.String getLoadableStudy();
    /**
     * <code>string loadableStudy = 1;</code>
     *
     * @return The bytes for loadableStudy.
     */
    com.google.protobuf.ByteString getLoadableStudyBytes();

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
  }
  /** Protobuf type {@code LoadableStudyJson} */
  public static final class LoadableStudyJson extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadableStudyJson)
      LoadableStudyJsonOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadableStudyJson.newBuilder() to construct.
    private LoadableStudyJson(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadableStudyJson() {
      loadableStudy_ = "";
      imoNumber_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadableStudyJson();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadableStudyJson(
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

                loadableStudy_ = s;
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
      return com.cpdss.common.generated.EnvoyWriter.internal_static_LoadableStudyJson_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.EnvoyWriter
          .internal_static_LoadableStudyJson_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson.class,
              com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson.Builder.class);
    }

    public static final int LOADABLESTUDY_FIELD_NUMBER = 1;
    private volatile java.lang.Object loadableStudy_;
    /**
     * <code>string loadableStudy = 1;</code>
     *
     * @return The loadableStudy.
     */
    public java.lang.String getLoadableStudy() {
      java.lang.Object ref = loadableStudy_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        loadableStudy_ = s;
        return s;
      }
    }
    /**
     * <code>string loadableStudy = 1;</code>
     *
     * @return The bytes for loadableStudy.
     */
    public com.google.protobuf.ByteString getLoadableStudyBytes() {
      java.lang.Object ref = loadableStudy_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        loadableStudy_ = b;
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
      if (!getLoadableStudyBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, loadableStudy_);
      }
      if (!getImoNumberBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, imoNumber_);
      }
      if (vesselId_ != 0L) {
        output.writeInt64(3, vesselId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getLoadableStudyBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, loadableStudy_);
      }
      if (!getImoNumberBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, imoNumber_);
      }
      if (vesselId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, vesselId_);
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
      if (!(obj instanceof com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson other =
          (com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson) obj;

      if (!getLoadableStudy().equals(other.getLoadableStudy())) return false;
      if (!getImoNumber().equals(other.getImoNumber())) return false;
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
      hash = (37 * hash) + LOADABLESTUDY_FIELD_NUMBER;
      hash = (53 * hash) + getLoadableStudy().hashCode();
      hash = (37 * hash) + IMONUMBER_FIELD_NUMBER;
      hash = (53 * hash) + getImoNumber().hashCode();
      hash = (37 * hash) + VESSELID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVesselId());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parseFrom(
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
        com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson prototype) {
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
    /** Protobuf type {@code LoadableStudyJson} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadableStudyJson)
        com.cpdss.common.generated.EnvoyWriter.LoadableStudyJsonOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.EnvoyWriter.internal_static_LoadableStudyJson_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.EnvoyWriter
            .internal_static_LoadableStudyJson_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson.class,
                com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson.Builder.class);
      }

      // Construct using com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson.newBuilder()
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
        loadableStudy_ = "";

        imoNumber_ = "";

        vesselId_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.EnvoyWriter.internal_static_LoadableStudyJson_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson getDefaultInstanceForType() {
        return com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson build() {
        com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson buildPartial() {
        com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson result =
            new com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson(this);
        result.loadableStudy_ = loadableStudy_;
        result.imoNumber_ = imoNumber_;
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
        if (other instanceof com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson) {
          return mergeFrom((com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson other) {
        if (other == com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson.getDefaultInstance())
          return this;
        if (!other.getLoadableStudy().isEmpty()) {
          loadableStudy_ = other.loadableStudy_;
          onChanged();
        }
        if (!other.getImoNumber().isEmpty()) {
          imoNumber_ = other.imoNumber_;
          onChanged();
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
        com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object loadableStudy_ = "";
      /**
       * <code>string loadableStudy = 1;</code>
       *
       * @return The loadableStudy.
       */
      public java.lang.String getLoadableStudy() {
        java.lang.Object ref = loadableStudy_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          loadableStudy_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string loadableStudy = 1;</code>
       *
       * @return The bytes for loadableStudy.
       */
      public com.google.protobuf.ByteString getLoadableStudyBytes() {
        java.lang.Object ref = loadableStudy_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          loadableStudy_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string loadableStudy = 1;</code>
       *
       * @param value The loadableStudy to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableStudy(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        loadableStudy_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string loadableStudy = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadableStudy() {

        loadableStudy_ = getDefaultInstance().getLoadableStudy();
        onChanged();
        return this;
      }
      /**
       * <code>string loadableStudy = 1;</code>
       *
       * @param value The bytes for loadableStudy to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableStudyBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        loadableStudy_ = value;
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

      // @@protoc_insertion_point(builder_scope:LoadableStudyJson)
    }

    // @@protoc_insertion_point(class_scope:LoadableStudyJson)
    private static final com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson();
    }

    public static com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadableStudyJson> PARSER =
        new com.google.protobuf.AbstractParser<LoadableStudyJson>() {
          @java.lang.Override
          public LoadableStudyJson parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadableStudyJson(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadableStudyJson> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadableStudyJson> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.EnvoyWriter.LoadableStudyJson getDefaultInstanceForType() {
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

    private WriterReply() {}

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
      internal_static_LoadableStudyJson_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadableStudyJson_fieldAccessorTable;
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
      "\n\022envoy-writer.proto\032\014common.proto\"O\n\021Lo"
          + "adableStudyJson\022\025\n\rloadableStudy\030\001 \001(\t\022\021"
          + "\n\timoNumber\030\002 \001(\t\022\020\n\010vesselId\030\003 \001(\003\"6\n\013W"
          + "riterReply\022\'\n\016responseStatus\030\001 \001(\0132\017.Res"
          + "ponseStatus2L\n\022EnvoyWriterService\0226\n\020Get"
          + "LoadableStudy\022\022.LoadableStudyJson\032\014.Writ"
          + "erReply\"\000B\036\n\032com.cpdss.common.generatedP"
          + "\000b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
            });
    internal_static_LoadableStudyJson_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_LoadableStudyJson_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadableStudyJson_descriptor,
            new java.lang.String[] {
              "LoadableStudy", "ImoNumber", "VesselId",
            });
    internal_static_WriterReply_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_WriterReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_WriterReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus",
            });
    com.cpdss.common.generated.Common.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
