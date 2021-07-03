/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.loadableStudy;

public final class LoadableStudyModels {
  private LoadableStudyModels() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public interface DischargeStudyRequestOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:DischargeStudyRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 dischargeStudyId = 1;</code>
     *
     * @return The dischargeStudyId.
     */
    long getDischargeStudyId();
  }
  /** Protobuf type {@code DischargeStudyRequest} */
  public static final class DischargeStudyRequest extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:DischargeStudyRequest)
      DischargeStudyRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use DischargeStudyRequest.newBuilder() to construct.
    private DischargeStudyRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private DischargeStudyRequest() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new DischargeStudyRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private DischargeStudyRequest(
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
                dischargeStudyId_ = input.readInt64();
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
      return com.cpdss
          .common
          .generated
          .loadableStudy
          .LoadableStudyModels
          .internal_static_DischargeStudyRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_DischargeStudyRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest.class,
          com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DischargeStudyRequest
              .Builder
              .class);
    }

    public static final int DISCHARGESTUDYID_FIELD_NUMBER = 1;
    private long dischargeStudyId_;
    /**
     * <code>int64 dischargeStudyId = 1;</code>
     *
     * @return The dischargeStudyId.
     */
    public long getDischargeStudyId() {
      return dischargeStudyId_;
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
      if (dischargeStudyId_ != 0L) {
        output.writeInt64(1, dischargeStudyId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (dischargeStudyId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, dischargeStudyId_);
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
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest) obj;

      if (getDischargeStudyId() != other.getDischargeStudyId()) return false;
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
      hash = (37 * hash) + DISCHARGESTUDYID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargeStudyId());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
            prototype) {
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
    /** Protobuf type {@code DischargeStudyRequest} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:DischargeStudyRequest)
        com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DischargeStudyRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_DischargeStudyRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DischargeStudyRequest
                    .class,
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DischargeStudyRequest
                    .Builder
                    .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest.newBuilder()
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
        dischargeStudyId_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DischargeStudyRequest_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
          build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest result =
            new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest(
                this);
        result.dischargeStudyId_ = dischargeStudyId_;
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
        if (other
            instanceof
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest) {
          return mergeFrom(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
              other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
                .getDefaultInstance()) return this;
        if (other.getDischargeStudyId() != 0L) {
          setDischargeStudyId(other.getDischargeStudyId());
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long dischargeStudyId_;
      /**
       * <code>int64 dischargeStudyId = 1;</code>
       *
       * @return The dischargeStudyId.
       */
      public long getDischargeStudyId() {
        return dischargeStudyId_;
      }
      /**
       * <code>int64 dischargeStudyId = 1;</code>
       *
       * @param value The dischargeStudyId to set.
       * @return This builder for chaining.
       */
      public Builder setDischargeStudyId(long value) {

        dischargeStudyId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 dischargeStudyId = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDischargeStudyId() {

        dischargeStudyId_ = 0L;
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

      // @@protoc_insertion_point(builder_scope:DischargeStudyRequest)
    }

    // @@protoc_insertion_point(class_scope:DischargeStudyRequest)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyRequest
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest();
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DischargeStudyRequest> PARSER =
        new com.google.protobuf.AbstractParser<DischargeStudyRequest>() {
          @java.lang.Override
          public DischargeStudyRequest parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new DischargeStudyRequest(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<DischargeStudyRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DischargeStudyRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeStudyRequest_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeStudyRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n*loadable_study/loadable_study_models.p"
          + "roto\032\014common.proto\"1\n\025DischargeStudyRequ"
          + "est\022\030\n\020dischargeStudyId\030\001 \001(\003B,\n(com.cpd"
          + "ss.common.generated.loadableStudyP\000b\006pro"
          + "to3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
            });
    internal_static_DischargeStudyRequest_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_DischargeStudyRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeStudyRequest_descriptor,
            new java.lang.String[] {
              "DischargeStudyId",
            });
    com.cpdss.common.generated.Common.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
