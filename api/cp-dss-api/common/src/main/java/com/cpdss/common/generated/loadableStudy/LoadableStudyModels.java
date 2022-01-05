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

  public interface DischargeStudyDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:DischargeStudyDetail)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    java.lang.String getName();
    /**
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    com.google.protobuf.ByteString getNameBytes();

    /**
     * <code>string enquiryDetails = 2;</code>
     *
     * @return The enquiryDetails.
     */
    java.lang.String getEnquiryDetails();
    /**
     * <code>string enquiryDetails = 2;</code>
     *
     * @return The bytes for enquiryDetails.
     */
    com.google.protobuf.ByteString getEnquiryDetailsBytes();

    /**
     * <code>int64 vesselId = 3;</code>
     *
     * @return The vesselId.
     */
    long getVesselId();

    /**
     * <code>int64 voyageId = 4;</code>
     *
     * @return The voyageId.
     */
    long getVoyageId();

    /**
     * <code>int64 dischargeStudyId = 5;</code>
     *
     * @return The dischargeStudyId.
     */
    long getDischargeStudyId();
  }
  /** Protobuf type {@code DischargeStudyDetail} */
  public static final class DischargeStudyDetail extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:DischargeStudyDetail)
      DischargeStudyDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use DischargeStudyDetail.newBuilder() to construct.
    private DischargeStudyDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private DischargeStudyDetail() {
      name_ = "";
      enquiryDetails_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new DischargeStudyDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private DischargeStudyDetail(
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

                name_ = s;
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                enquiryDetails_ = s;
                break;
              }
            case 24:
              {
                vesselId_ = input.readInt64();
                break;
              }
            case 32:
              {
                voyageId_ = input.readInt64();
                break;
              }
            case 40:
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
          .internal_static_DischargeStudyDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_DischargeStudyDetail_fieldAccessorTable.ensureFieldAccessorsInitialized(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail.class,
          com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DischargeStudyDetail
              .Builder
              .class);
    }

    public static final int NAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object name_;
    /**
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      }
    }
    /**
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    public com.google.protobuf.ByteString getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ENQUIRYDETAILS_FIELD_NUMBER = 2;
    private volatile java.lang.Object enquiryDetails_;
    /**
     * <code>string enquiryDetails = 2;</code>
     *
     * @return The enquiryDetails.
     */
    public java.lang.String getEnquiryDetails() {
      java.lang.Object ref = enquiryDetails_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        enquiryDetails_ = s;
        return s;
      }
    }
    /**
     * <code>string enquiryDetails = 2;</code>
     *
     * @return The bytes for enquiryDetails.
     */
    public com.google.protobuf.ByteString getEnquiryDetailsBytes() {
      java.lang.Object ref = enquiryDetails_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        enquiryDetails_ = b;
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

    public static final int VOYAGEID_FIELD_NUMBER = 4;
    private long voyageId_;
    /**
     * <code>int64 voyageId = 4;</code>
     *
     * @return The voyageId.
     */
    public long getVoyageId() {
      return voyageId_;
    }

    public static final int DISCHARGESTUDYID_FIELD_NUMBER = 5;
    private long dischargeStudyId_;
    /**
     * <code>int64 dischargeStudyId = 5;</code>
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
      if (!getNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
      }
      if (!getEnquiryDetailsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, enquiryDetails_);
      }
      if (vesselId_ != 0L) {
        output.writeInt64(3, vesselId_);
      }
      if (voyageId_ != 0L) {
        output.writeInt64(4, voyageId_);
      }
      if (dischargeStudyId_ != 0L) {
        output.writeInt64(5, dischargeStudyId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
      }
      if (!getEnquiryDetailsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, enquiryDetails_);
      }
      if (vesselId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, vesselId_);
      }
      if (voyageId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, voyageId_);
      }
      if (dischargeStudyId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(5, dischargeStudyId_);
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
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail) obj;

      if (!getName().equals(other.getName())) return false;
      if (!getEnquiryDetails().equals(other.getEnquiryDetails())) return false;
      if (getVesselId() != other.getVesselId()) return false;
      if (getVoyageId() != other.getVoyageId()) return false;
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
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      hash = (37 * hash) + ENQUIRYDETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getEnquiryDetails().hashCode();
      hash = (37 * hash) + VESSELID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVesselId());
      hash = (37 * hash) + VOYAGEID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVoyageId());
      hash = (37 * hash) + DISCHARGESTUDYID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargeStudyId());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
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
    /** Protobuf type {@code DischargeStudyDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:DischargeStudyDetail)
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DischargeStudyDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_DischargeStudyDetail_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DischargeStudyDetail
                    .class,
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DischargeStudyDetail
                    .Builder
                    .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail.newBuilder()
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
        name_ = "";

        enquiryDetails_ = "";

        vesselId_ = 0L;

        voyageId_ = 0L;

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
            .internal_static_DischargeStudyDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
          build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail result =
            new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail(
                this);
        result.name_ = name_;
        result.enquiryDetails_ = enquiryDetails_;
        result.vesselId_ = vesselId_;
        result.voyageId_ = voyageId_;
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
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail) {
          return mergeFrom(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
                .getDefaultInstance()) return this;
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
          onChanged();
        }
        if (!other.getEnquiryDetails().isEmpty()) {
          enquiryDetails_ = other.enquiryDetails_;
          onChanged();
        }
        if (other.getVesselId() != 0L) {
          setVesselId(other.getVesselId());
        }
        if (other.getVoyageId() != 0L) {
          setVoyageId(other.getVoyageId());
        }
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object name_ = "";
      /**
       * <code>string name = 1;</code>
       *
       * @return The name.
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          name_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string name = 1;</code>
       *
       * @return The bytes for name.
       */
      public com.google.protobuf.ByteString getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string name = 1;</code>
       *
       * @param value The name to set.
       * @return This builder for chaining.
       */
      public Builder setName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        name_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string name = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearName() {

        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>string name = 1;</code>
       *
       * @param value The bytes for name to set.
       * @return This builder for chaining.
       */
      public Builder setNameBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        name_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object enquiryDetails_ = "";
      /**
       * <code>string enquiryDetails = 2;</code>
       *
       * @return The enquiryDetails.
       */
      public java.lang.String getEnquiryDetails() {
        java.lang.Object ref = enquiryDetails_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          enquiryDetails_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string enquiryDetails = 2;</code>
       *
       * @return The bytes for enquiryDetails.
       */
      public com.google.protobuf.ByteString getEnquiryDetailsBytes() {
        java.lang.Object ref = enquiryDetails_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          enquiryDetails_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string enquiryDetails = 2;</code>
       *
       * @param value The enquiryDetails to set.
       * @return This builder for chaining.
       */
      public Builder setEnquiryDetails(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        enquiryDetails_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string enquiryDetails = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearEnquiryDetails() {

        enquiryDetails_ = getDefaultInstance().getEnquiryDetails();
        onChanged();
        return this;
      }
      /**
       * <code>string enquiryDetails = 2;</code>
       *
       * @param value The bytes for enquiryDetails to set.
       * @return This builder for chaining.
       */
      public Builder setEnquiryDetailsBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        enquiryDetails_ = value;
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

      private long voyageId_;
      /**
       * <code>int64 voyageId = 4;</code>
       *
       * @return The voyageId.
       */
      public long getVoyageId() {
        return voyageId_;
      }
      /**
       * <code>int64 voyageId = 4;</code>
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
       * <code>int64 voyageId = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearVoyageId() {

        voyageId_ = 0L;
        onChanged();
        return this;
      }

      private long dischargeStudyId_;
      /**
       * <code>int64 dischargeStudyId = 5;</code>
       *
       * @return The dischargeStudyId.
       */
      public long getDischargeStudyId() {
        return dischargeStudyId_;
      }
      /**
       * <code>int64 dischargeStudyId = 5;</code>
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
       * <code>int64 dischargeStudyId = 5;</code>
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

      // @@protoc_insertion_point(builder_scope:DischargeStudyDetail)
    }

    // @@protoc_insertion_point(class_scope:DischargeStudyDetail)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyDetail
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail();
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DischargeStudyDetail> PARSER =
        new com.google.protobuf.AbstractParser<DischargeStudyDetail>() {
          @java.lang.Override
          public DischargeStudyDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new DischargeStudyDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<DischargeStudyDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DischargeStudyDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface UpdateDischargeStudyReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:UpdateDischargeStudyReply)
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
     * <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code>
     *
     * @return Whether the dischargeStudy field is set.
     */
    boolean hasDischargeStudy();
    /**
     * <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code>
     *
     * @return The dischargeStudy.
     */
    com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
        getDischargeStudy();
    /** <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code> */
    com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetailOrBuilder
        getDischargeStudyOrBuilder();
  }
  /** Protobuf type {@code UpdateDischargeStudyReply} */
  public static final class UpdateDischargeStudyReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:UpdateDischargeStudyReply)
      UpdateDischargeStudyReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use UpdateDischargeStudyReply.newBuilder() to construct.
    private UpdateDischargeStudyReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private UpdateDischargeStudyReply() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new UpdateDischargeStudyReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private UpdateDischargeStudyReply(
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
                com.cpdss
                        .common
                        .generated
                        .loadableStudy
                        .LoadableStudyModels
                        .UpdateDischargeStudyDetail
                        .Builder
                    subBuilder = null;
                if (dischargeStudy_ != null) {
                  subBuilder = dischargeStudy_.toBuilder();
                }
                dischargeStudy_ =
                    input.readMessage(
                        com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                            .UpdateDischargeStudyDetail.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(dischargeStudy_);
                  dischargeStudy_ = subBuilder.buildPartial();
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
      return com.cpdss
          .common
          .generated
          .loadableStudy
          .LoadableStudyModels
          .internal_static_UpdateDischargeStudyReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_UpdateDischargeStudyReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyReply
                  .class,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyReply
                  .Builder
                  .class);
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

    public static final int DISCHARGESTUDY_FIELD_NUMBER = 2;
    private com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
        dischargeStudy_;
    /**
     * <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code>
     *
     * @return Whether the dischargeStudy field is set.
     */
    public boolean hasDischargeStudy() {
      return dischargeStudy_ != null;
    }
    /**
     * <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code>
     *
     * @return The dischargeStudy.
     */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
        getDischargeStudy() {
      return dischargeStudy_ == null
          ? com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
              .getDefaultInstance()
          : dischargeStudy_;
    }
    /** <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code> */
    public com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetailOrBuilder
        getDischargeStudyOrBuilder() {
      return getDischargeStudy();
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
      if (dischargeStudy_ != null) {
        output.writeMessage(2, getDischargeStudy());
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
      if (dischargeStudy_ != null) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getDischargeStudy());
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
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply)
              obj;

      if (hasResponseStatus() != other.hasResponseStatus()) return false;
      if (hasResponseStatus()) {
        if (!getResponseStatus().equals(other.getResponseStatus())) return false;
      }
      if (hasDischargeStudy() != other.hasDischargeStudy()) return false;
      if (hasDischargeStudy()) {
        if (!getDischargeStudy().equals(other.getDischargeStudy())) return false;
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
      if (hasDischargeStudy()) {
        hash = (37 * hash) + DISCHARGESTUDY_FIELD_NUMBER;
        hash = (53 * hash) + getDischargeStudy().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply
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
    /** Protobuf type {@code UpdateDischargeStudyReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:UpdateDischargeStudyReply)
        com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_UpdateDischargeStudyReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_UpdateDischargeStudyReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .UpdateDischargeStudyReply
                    .class,
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .UpdateDischargeStudyReply
                    .Builder
                    .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply.newBuilder()
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
        if (dischargeStudyBuilder_ == null) {
          dischargeStudy_ = null;
        } else {
          dischargeStudy_ = null;
          dischargeStudyBuilder_ = null;
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_UpdateDischargeStudyReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .UpdateDischargeStudyReply.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply
          build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply
            result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply
            result =
                new com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .UpdateDischargeStudyReply(this);
        if (responseStatusBuilder_ == null) {
          result.responseStatus_ = responseStatus_;
        } else {
          result.responseStatus_ = responseStatusBuilder_.build();
        }
        if (dischargeStudyBuilder_ == null) {
          result.dischargeStudy_ = dischargeStudy_;
        } else {
          result.dischargeStudy_ = dischargeStudyBuilder_.build();
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
        if (other
            instanceof
            com.cpdss
                .common
                .generated
                .loadableStudy
                .LoadableStudyModels
                .UpdateDischargeStudyReply) {
          return mergeFrom(
              (com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .UpdateDischargeStudyReply)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply
              other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                .UpdateDischargeStudyReply.getDefaultInstance()) return this;
        if (other.hasResponseStatus()) {
          mergeResponseStatus(other.getResponseStatus());
        }
        if (other.hasDischargeStudy()) {
          mergeDischargeStudy(other.getDischargeStudy());
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .UpdateDischargeStudyReply)
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

      private com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .UpdateDischargeStudyDetail
          dischargeStudy_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyDetail,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyDetail
                  .Builder,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyDetailOrBuilder>
          dischargeStudyBuilder_;
      /**
       * <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code>
       *
       * @return Whether the dischargeStudy field is set.
       */
      public boolean hasDischargeStudy() {
        return dischargeStudyBuilder_ != null || dischargeStudy_ != null;
      }
      /**
       * <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code>
       *
       * @return The dischargeStudy.
       */
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
          getDischargeStudy() {
        if (dischargeStudyBuilder_ == null) {
          return dischargeStudy_ == null
              ? com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                  .UpdateDischargeStudyDetail.getDefaultInstance()
              : dischargeStudy_;
        } else {
          return dischargeStudyBuilder_.getMessage();
        }
      }
      /** <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code> */
      public Builder setDischargeStudy(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
              value) {
        if (dischargeStudyBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          dischargeStudy_ = value;
          onChanged();
        } else {
          dischargeStudyBuilder_.setMessage(value);
        }

        return this;
      }
      /** <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code> */
      public Builder setDischargeStudy(
          com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyDetail
                  .Builder
              builderForValue) {
        if (dischargeStudyBuilder_ == null) {
          dischargeStudy_ = builderForValue.build();
          onChanged();
        } else {
          dischargeStudyBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /** <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code> */
      public Builder mergeDischargeStudy(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
              value) {
        if (dischargeStudyBuilder_ == null) {
          if (dischargeStudy_ != null) {
            dischargeStudy_ =
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .UpdateDischargeStudyDetail
                    .newBuilder(dischargeStudy_)
                    .mergeFrom(value)
                    .buildPartial();
          } else {
            dischargeStudy_ = value;
          }
          onChanged();
        } else {
          dischargeStudyBuilder_.mergeFrom(value);
        }

        return this;
      }
      /** <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code> */
      public Builder clearDischargeStudy() {
        if (dischargeStudyBuilder_ == null) {
          dischargeStudy_ = null;
          onChanged();
        } else {
          dischargeStudy_ = null;
          dischargeStudyBuilder_ = null;
        }

        return this;
      }
      /** <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .UpdateDischargeStudyDetail
              .Builder
          getDischargeStudyBuilder() {

        onChanged();
        return getDischargeStudyFieldBuilder().getBuilder();
      }
      /** <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .UpdateDischargeStudyDetailOrBuilder
          getDischargeStudyOrBuilder() {
        if (dischargeStudyBuilder_ != null) {
          return dischargeStudyBuilder_.getMessageOrBuilder();
        } else {
          return dischargeStudy_ == null
              ? com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                  .UpdateDischargeStudyDetail.getDefaultInstance()
              : dischargeStudy_;
        }
      }
      /** <code>.UpdateDischargeStudyDetail dischargeStudy = 2;</code> */
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyDetail,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyDetail
                  .Builder,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyDetailOrBuilder>
          getDischargeStudyFieldBuilder() {
        if (dischargeStudyBuilder_ == null) {
          dischargeStudyBuilder_ =
              new com.google.protobuf.SingleFieldBuilderV3<
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .UpdateDischargeStudyDetail,
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .UpdateDischargeStudyDetail
                      .Builder,
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .UpdateDischargeStudyDetailOrBuilder>(
                  getDischargeStudy(), getParentForChildren(), isClean());
          dischargeStudy_ = null;
        }
        return dischargeStudyBuilder_;
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

      // @@protoc_insertion_point(builder_scope:UpdateDischargeStudyReply)
    }

    // @@protoc_insertion_point(class_scope:UpdateDischargeStudyReply)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .UpdateDischargeStudyReply();
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyReply
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<UpdateDischargeStudyReply> PARSER =
        new com.google.protobuf.AbstractParser<UpdateDischargeStudyReply>() {
          @java.lang.Override
          public UpdateDischargeStudyReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new UpdateDischargeStudyReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<UpdateDischargeStudyReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<UpdateDischargeStudyReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface UpdateDischargeStudyDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:UpdateDischargeStudyDetail)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    java.lang.String getName();
    /**
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    com.google.protobuf.ByteString getNameBytes();

    /**
     * <code>string enquiryDetails = 2;</code>
     *
     * @return The enquiryDetails.
     */
    java.lang.String getEnquiryDetails();
    /**
     * <code>string enquiryDetails = 2;</code>
     *
     * @return The bytes for enquiryDetails.
     */
    com.google.protobuf.ByteString getEnquiryDetailsBytes();

    /**
     * <code>int64 id = 3;</code>
     *
     * @return The id.
     */
    long getId();
  }
  /** Protobuf type {@code UpdateDischargeStudyDetail} */
  public static final class UpdateDischargeStudyDetail
      extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:UpdateDischargeStudyDetail)
      UpdateDischargeStudyDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use UpdateDischargeStudyDetail.newBuilder() to construct.
    private UpdateDischargeStudyDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private UpdateDischargeStudyDetail() {
      name_ = "";
      enquiryDetails_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new UpdateDischargeStudyDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private UpdateDischargeStudyDetail(
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

                name_ = s;
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                enquiryDetails_ = s;
                break;
              }
            case 24:
              {
                id_ = input.readInt64();
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
          .internal_static_UpdateDischargeStudyDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_UpdateDischargeStudyDetail_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyDetail
                  .class,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .UpdateDischargeStudyDetail
                  .Builder
                  .class);
    }

    public static final int NAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object name_;
    /**
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      }
    }
    /**
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    public com.google.protobuf.ByteString getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ENQUIRYDETAILS_FIELD_NUMBER = 2;
    private volatile java.lang.Object enquiryDetails_;
    /**
     * <code>string enquiryDetails = 2;</code>
     *
     * @return The enquiryDetails.
     */
    public java.lang.String getEnquiryDetails() {
      java.lang.Object ref = enquiryDetails_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        enquiryDetails_ = s;
        return s;
      }
    }
    /**
     * <code>string enquiryDetails = 2;</code>
     *
     * @return The bytes for enquiryDetails.
     */
    public com.google.protobuf.ByteString getEnquiryDetailsBytes() {
      java.lang.Object ref = enquiryDetails_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        enquiryDetails_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ID_FIELD_NUMBER = 3;
    private long id_;
    /**
     * <code>int64 id = 3;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
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
      if (!getNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
      }
      if (!getEnquiryDetailsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, enquiryDetails_);
      }
      if (id_ != 0L) {
        output.writeInt64(3, id_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
      }
      if (!getEnquiryDetailsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, enquiryDetails_);
      }
      if (id_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, id_);
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
          com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .UpdateDischargeStudyDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
          other =
              (com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .UpdateDischargeStudyDetail)
                  obj;

      if (!getName().equals(other.getName())) return false;
      if (!getEnquiryDetails().equals(other.getEnquiryDetails())) return false;
      if (getId() != other.getId()) return false;
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
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      hash = (37 * hash) + ENQUIRYDETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getEnquiryDetails().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
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
    /** Protobuf type {@code UpdateDischargeStudyDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:UpdateDischargeStudyDetail)
        com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_UpdateDischargeStudyDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_UpdateDischargeStudyDetail_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .UpdateDischargeStudyDetail
                    .class,
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .UpdateDischargeStudyDetail
                    .Builder
                    .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail.newBuilder()
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
        name_ = "";

        enquiryDetails_ = "";

        id_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_UpdateDischargeStudyDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .UpdateDischargeStudyDetail.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
          build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
            result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
            result =
                new com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .UpdateDischargeStudyDetail(this);
        result.name_ = name_;
        result.enquiryDetails_ = enquiryDetails_;
        result.id_ = id_;
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
            com.cpdss
                .common
                .generated
                .loadableStudy
                .LoadableStudyModels
                .UpdateDischargeStudyDetail) {
          return mergeFrom(
              (com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .UpdateDischargeStudyDetail)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
              other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                .UpdateDischargeStudyDetail.getDefaultInstance()) return this;
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
          onChanged();
        }
        if (!other.getEnquiryDetails().isEmpty()) {
          enquiryDetails_ = other.enquiryDetails_;
          onChanged();
        }
        if (other.getId() != 0L) {
          setId(other.getId());
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .UpdateDischargeStudyDetail)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object name_ = "";
      /**
       * <code>string name = 1;</code>
       *
       * @return The name.
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          name_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string name = 1;</code>
       *
       * @return The bytes for name.
       */
      public com.google.protobuf.ByteString getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string name = 1;</code>
       *
       * @param value The name to set.
       * @return This builder for chaining.
       */
      public Builder setName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        name_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string name = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearName() {

        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>string name = 1;</code>
       *
       * @param value The bytes for name to set.
       * @return This builder for chaining.
       */
      public Builder setNameBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        name_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object enquiryDetails_ = "";
      /**
       * <code>string enquiryDetails = 2;</code>
       *
       * @return The enquiryDetails.
       */
      public java.lang.String getEnquiryDetails() {
        java.lang.Object ref = enquiryDetails_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          enquiryDetails_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string enquiryDetails = 2;</code>
       *
       * @return The bytes for enquiryDetails.
       */
      public com.google.protobuf.ByteString getEnquiryDetailsBytes() {
        java.lang.Object ref = enquiryDetails_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          enquiryDetails_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string enquiryDetails = 2;</code>
       *
       * @param value The enquiryDetails to set.
       * @return This builder for chaining.
       */
      public Builder setEnquiryDetails(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        enquiryDetails_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string enquiryDetails = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearEnquiryDetails() {

        enquiryDetails_ = getDefaultInstance().getEnquiryDetails();
        onChanged();
        return this;
      }
      /**
       * <code>string enquiryDetails = 2;</code>
       *
       * @param value The bytes for enquiryDetails to set.
       * @return This builder for chaining.
       */
      public Builder setEnquiryDetailsBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        enquiryDetails_ = value;
        onChanged();
        return this;
      }

      private long id_;
      /**
       * <code>int64 id = 3;</code>
       *
       * @return The id.
       */
      public long getId() {
        return id_;
      }
      /**
       * <code>int64 id = 3;</code>
       *
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(long value) {

        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 id = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {

        id_ = 0L;
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

      // @@protoc_insertion_point(builder_scope:UpdateDischargeStudyDetail)
    }

    // @@protoc_insertion_point(class_scope:UpdateDischargeStudyDetail)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .UpdateDischargeStudyDetail();
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .UpdateDischargeStudyDetail
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<UpdateDischargeStudyDetail> PARSER =
        new com.google.protobuf.AbstractParser<UpdateDischargeStudyDetail>() {
          @java.lang.Override
          public UpdateDischargeStudyDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new UpdateDischargeStudyDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<UpdateDischargeStudyDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<UpdateDischargeStudyDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface DischargeStudyReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:DischargeStudyReply)
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
     * <code>.DischargeStudyDetail dischargeStudy = 2;</code>
     *
     * @return Whether the dischargeStudy field is set.
     */
    boolean hasDischargeStudy();
    /**
     * <code>.DischargeStudyDetail dischargeStudy = 2;</code>
     *
     * @return The dischargeStudy.
     */
    com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        getDischargeStudy();
    /** <code>.DischargeStudyDetail dischargeStudy = 2;</code> */
    com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetailOrBuilder
        getDischargeStudyOrBuilder();

    /**
     * <code>int64 id = 3;</code>
     *
     * @return The id.
     */
    long getId();
  }
  /** Protobuf type {@code DischargeStudyReply} */
  public static final class DischargeStudyReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:DischargeStudyReply)
      DischargeStudyReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use DischargeStudyReply.newBuilder() to construct.
    private DischargeStudyReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private DischargeStudyReply() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new DischargeStudyReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private DischargeStudyReply(
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
                com.cpdss
                        .common
                        .generated
                        .loadableStudy
                        .LoadableStudyModels
                        .DischargeStudyDetail
                        .Builder
                    subBuilder = null;
                if (dischargeStudy_ != null) {
                  subBuilder = dischargeStudy_.toBuilder();
                }
                dischargeStudy_ =
                    input.readMessage(
                        com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                            .DischargeStudyDetail.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(dischargeStudy_);
                  dischargeStudy_ = subBuilder.buildPartial();
                }

                break;
              }
            case 24:
              {
                id_ = input.readInt64();
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
          .internal_static_DischargeStudyReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_DischargeStudyReply_fieldAccessorTable.ensureFieldAccessorsInitialized(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply.class,
          com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DischargeStudyReply
              .Builder
              .class);
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

    public static final int DISCHARGESTUDY_FIELD_NUMBER = 2;
    private com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        dischargeStudy_;
    /**
     * <code>.DischargeStudyDetail dischargeStudy = 2;</code>
     *
     * @return Whether the dischargeStudy field is set.
     */
    public boolean hasDischargeStudy() {
      return dischargeStudy_ != null;
    }
    /**
     * <code>.DischargeStudyDetail dischargeStudy = 2;</code>
     *
     * @return The dischargeStudy.
     */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
        getDischargeStudy() {
      return dischargeStudy_ == null
          ? com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
              .getDefaultInstance()
          : dischargeStudy_;
    }
    /** <code>.DischargeStudyDetail dischargeStudy = 2;</code> */
    public com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyDetailOrBuilder
        getDischargeStudyOrBuilder() {
      return getDischargeStudy();
    }

    public static final int ID_FIELD_NUMBER = 3;
    private long id_;
    /**
     * <code>int64 id = 3;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
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
      if (dischargeStudy_ != null) {
        output.writeMessage(2, getDischargeStudy());
      }
      if (id_ != 0L) {
        output.writeInt64(3, id_);
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
      if (dischargeStudy_ != null) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getDischargeStudy());
      }
      if (id_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, id_);
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
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply) obj;

      if (hasResponseStatus() != other.hasResponseStatus()) return false;
      if (hasResponseStatus()) {
        if (!getResponseStatus().equals(other.getResponseStatus())) return false;
      }
      if (hasDischargeStudy() != other.hasDischargeStudy()) return false;
      if (hasDischargeStudy()) {
        if (!getDischargeStudy().equals(other.getDischargeStudy())) return false;
      }
      if (getId() != other.getId()) return false;
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
      if (hasDischargeStudy()) {
        hash = (37 * hash) + DISCHARGESTUDY_FIELD_NUMBER;
        hash = (53 * hash) + getDischargeStudy().hashCode();
      }
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
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
    /** Protobuf type {@code DischargeStudyReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:DischargeStudyReply)
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DischargeStudyReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_DischargeStudyReply_fieldAccessorTable.ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply.class,
            com.cpdss
                .common
                .generated
                .loadableStudy
                .LoadableStudyModels
                .DischargeStudyReply
                .Builder
                .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply.newBuilder()
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
        if (dischargeStudyBuilder_ == null) {
          dischargeStudy_ = null;
        } else {
          dischargeStudy_ = null;
          dischargeStudyBuilder_ = null;
        }
        id_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DischargeStudyReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
          build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply result =
            new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply(
                this);
        if (responseStatusBuilder_ == null) {
          result.responseStatus_ = responseStatus_;
        } else {
          result.responseStatus_ = responseStatusBuilder_.build();
        }
        if (dischargeStudyBuilder_ == null) {
          result.dischargeStudy_ = dischargeStudy_;
        } else {
          result.dischargeStudy_ = dischargeStudyBuilder_.build();
        }
        result.id_ = id_;
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
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply) {
          return mergeFrom(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
                .getDefaultInstance()) return this;
        if (other.hasResponseStatus()) {
          mergeResponseStatus(other.getResponseStatus());
        }
        if (other.hasDischargeStudy()) {
          mergeDischargeStudy(other.getDischargeStudy());
        }
        if (other.getId() != 0L) {
          setId(other.getId());
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply)
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

      private com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
          dischargeStudy_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DischargeStudyDetail
                  .Builder,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DischargeStudyDetailOrBuilder>
          dischargeStudyBuilder_;
      /**
       * <code>.DischargeStudyDetail dischargeStudy = 2;</code>
       *
       * @return Whether the dischargeStudy field is set.
       */
      public boolean hasDischargeStudy() {
        return dischargeStudyBuilder_ != null || dischargeStudy_ != null;
      }
      /**
       * <code>.DischargeStudyDetail dischargeStudy = 2;</code>
       *
       * @return The dischargeStudy.
       */
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
          getDischargeStudy() {
        if (dischargeStudyBuilder_ == null) {
          return dischargeStudy_ == null
              ? com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
                  .getDefaultInstance()
              : dischargeStudy_;
        } else {
          return dischargeStudyBuilder_.getMessage();
        }
      }
      /** <code>.DischargeStudyDetail dischargeStudy = 2;</code> */
      public Builder setDischargeStudy(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail value) {
        if (dischargeStudyBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          dischargeStudy_ = value;
          onChanged();
        } else {
          dischargeStudyBuilder_.setMessage(value);
        }

        return this;
      }
      /** <code>.DischargeStudyDetail dischargeStudy = 2;</code> */
      public Builder setDischargeStudy(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail.Builder
              builderForValue) {
        if (dischargeStudyBuilder_ == null) {
          dischargeStudy_ = builderForValue.build();
          onChanged();
        } else {
          dischargeStudyBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /** <code>.DischargeStudyDetail dischargeStudy = 2;</code> */
      public Builder mergeDischargeStudy(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail value) {
        if (dischargeStudyBuilder_ == null) {
          if (dischargeStudy_ != null) {
            dischargeStudy_ =
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DischargeStudyDetail
                    .newBuilder(dischargeStudy_)
                    .mergeFrom(value)
                    .buildPartial();
          } else {
            dischargeStudy_ = value;
          }
          onChanged();
        } else {
          dischargeStudyBuilder_.mergeFrom(value);
        }

        return this;
      }
      /** <code>.DischargeStudyDetail dischargeStudy = 2;</code> */
      public Builder clearDischargeStudy() {
        if (dischargeStudyBuilder_ == null) {
          dischargeStudy_ = null;
          onChanged();
        } else {
          dischargeStudy_ = null;
          dischargeStudyBuilder_ = null;
        }

        return this;
      }
      /** <code>.DischargeStudyDetail dischargeStudy = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DischargeStudyDetail
              .Builder
          getDischargeStudyBuilder() {

        onChanged();
        return getDischargeStudyFieldBuilder().getBuilder();
      }
      /** <code>.DischargeStudyDetail dischargeStudy = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DischargeStudyDetailOrBuilder
          getDischargeStudyOrBuilder() {
        if (dischargeStudyBuilder_ != null) {
          return dischargeStudyBuilder_.getMessageOrBuilder();
        } else {
          return dischargeStudy_ == null
              ? com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
                  .getDefaultInstance()
              : dischargeStudy_;
        }
      }
      /** <code>.DischargeStudyDetail dischargeStudy = 2;</code> */
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DischargeStudyDetail
                  .Builder,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DischargeStudyDetailOrBuilder>
          getDischargeStudyFieldBuilder() {
        if (dischargeStudyBuilder_ == null) {
          dischargeStudyBuilder_ =
              new com.google.protobuf.SingleFieldBuilderV3<
                  com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail,
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DischargeStudyDetail
                      .Builder,
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DischargeStudyDetailOrBuilder>(
                  getDischargeStudy(), getParentForChildren(), isClean());
          dischargeStudy_ = null;
        }
        return dischargeStudyBuilder_;
      }

      private long id_;
      /**
       * <code>int64 id = 3;</code>
       *
       * @return The id.
       */
      public long getId() {
        return id_;
      }
      /**
       * <code>int64 id = 3;</code>
       *
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(long value) {

        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 id = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {

        id_ = 0L;
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

      // @@protoc_insertion_point(builder_scope:DischargeStudyReply)
    }

    // @@protoc_insertion_point(class_scope:DischargeStudyReply)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyReply
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply();
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DischargeStudyReply> PARSER =
        new com.google.protobuf.AbstractParser<DischargeStudyReply>() {
          @java.lang.Override
          public DischargeStudyReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new DischargeStudyReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<DischargeStudyReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DischargeStudyReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface BackLoadingOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:BackLoading)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>string colour = 2;</code>
     *
     * @return The colour.
     */
    java.lang.String getColour();
    /**
     * <code>string colour = 2;</code>
     *
     * @return The bytes for colour.
     */
    com.google.protobuf.ByteString getColourBytes();

    /**
     * <code>int64 cargoId = 3;</code>
     *
     * @return The cargoId.
     */
    long getCargoId();

    /**
     * <code>string api = 4;</code>
     *
     * @return The api.
     */
    java.lang.String getApi();
    /**
     * <code>string api = 4;</code>
     *
     * @return The bytes for api.
     */
    com.google.protobuf.ByteString getApiBytes();

    /**
     * <code>string temperature = 5;</code>
     *
     * @return The temperature.
     */
    java.lang.String getTemperature();
    /**
     * <code>string temperature = 5;</code>
     *
     * @return The bytes for temperature.
     */
    com.google.protobuf.ByteString getTemperatureBytes();

    /**
     * <code>string abbreviation = 6;</code>
     *
     * @return The abbreviation.
     */
    java.lang.String getAbbreviation();
    /**
     * <code>string abbreviation = 6;</code>
     *
     * @return The bytes for abbreviation.
     */
    com.google.protobuf.ByteString getAbbreviationBytes();

    /**
     * <code>string quantity = 7;</code>
     *
     * @return The quantity.
     */
    java.lang.String getQuantity();
    /**
     * <code>string quantity = 7;</code>
     *
     * @return The bytes for quantity.
     */
    com.google.protobuf.ByteString getQuantityBytes();
  }
  /** Protobuf type {@code BackLoading} */
  public static final class BackLoading extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:BackLoading)
      BackLoadingOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use BackLoading.newBuilder() to construct.
    private BackLoading(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private BackLoading() {
      colour_ = "";
      api_ = "";
      temperature_ = "";
      abbreviation_ = "";
      quantity_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new BackLoading();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private BackLoading(
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
                id_ = input.readInt64();
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                colour_ = s;
                break;
              }
            case 24:
              {
                cargoId_ = input.readInt64();
                break;
              }
            case 34:
              {
                java.lang.String s = input.readStringRequireUtf8();

                api_ = s;
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                temperature_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                abbreviation_ = s;
                break;
              }
            case 58:
              {
                java.lang.String s = input.readStringRequireUtf8();

                quantity_ = s;
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
          .internal_static_BackLoading_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_BackLoading_fieldAccessorTable.ensureFieldAccessorsInitialized(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading.class,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading.Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private long id_;
    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
    }

    public static final int COLOUR_FIELD_NUMBER = 2;
    private volatile java.lang.Object colour_;
    /**
     * <code>string colour = 2;</code>
     *
     * @return The colour.
     */
    public java.lang.String getColour() {
      java.lang.Object ref = colour_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        colour_ = s;
        return s;
      }
    }
    /**
     * <code>string colour = 2;</code>
     *
     * @return The bytes for colour.
     */
    public com.google.protobuf.ByteString getColourBytes() {
      java.lang.Object ref = colour_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        colour_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGOID_FIELD_NUMBER = 3;
    private long cargoId_;
    /**
     * <code>int64 cargoId = 3;</code>
     *
     * @return The cargoId.
     */
    public long getCargoId() {
      return cargoId_;
    }

    public static final int API_FIELD_NUMBER = 4;
    private volatile java.lang.Object api_;
    /**
     * <code>string api = 4;</code>
     *
     * @return The api.
     */
    public java.lang.String getApi() {
      java.lang.Object ref = api_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        api_ = s;
        return s;
      }
    }
    /**
     * <code>string api = 4;</code>
     *
     * @return The bytes for api.
     */
    public com.google.protobuf.ByteString getApiBytes() {
      java.lang.Object ref = api_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        api_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TEMPERATURE_FIELD_NUMBER = 5;
    private volatile java.lang.Object temperature_;
    /**
     * <code>string temperature = 5;</code>
     *
     * @return The temperature.
     */
    public java.lang.String getTemperature() {
      java.lang.Object ref = temperature_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        temperature_ = s;
        return s;
      }
    }
    /**
     * <code>string temperature = 5;</code>
     *
     * @return The bytes for temperature.
     */
    public com.google.protobuf.ByteString getTemperatureBytes() {
      java.lang.Object ref = temperature_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        temperature_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ABBREVIATION_FIELD_NUMBER = 6;
    private volatile java.lang.Object abbreviation_;
    /**
     * <code>string abbreviation = 6;</code>
     *
     * @return The abbreviation.
     */
    public java.lang.String getAbbreviation() {
      java.lang.Object ref = abbreviation_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        abbreviation_ = s;
        return s;
      }
    }
    /**
     * <code>string abbreviation = 6;</code>
     *
     * @return The bytes for abbreviation.
     */
    public com.google.protobuf.ByteString getAbbreviationBytes() {
      java.lang.Object ref = abbreviation_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        abbreviation_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int QUANTITY_FIELD_NUMBER = 7;
    private volatile java.lang.Object quantity_;
    /**
     * <code>string quantity = 7;</code>
     *
     * @return The quantity.
     */
    public java.lang.String getQuantity() {
      java.lang.Object ref = quantity_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        quantity_ = s;
        return s;
      }
    }
    /**
     * <code>string quantity = 7;</code>
     *
     * @return The bytes for quantity.
     */
    public com.google.protobuf.ByteString getQuantityBytes() {
      java.lang.Object ref = quantity_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        quantity_ = b;
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
      if (id_ != 0L) {
        output.writeInt64(1, id_);
      }
      if (!getColourBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, colour_);
      }
      if (cargoId_ != 0L) {
        output.writeInt64(3, cargoId_);
      }
      if (!getApiBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, api_);
      }
      if (!getTemperatureBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, temperature_);
      }
      if (!getAbbreviationBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, abbreviation_);
      }
      if (!getQuantityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, quantity_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (id_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, id_);
      }
      if (!getColourBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, colour_);
      }
      if (cargoId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, cargoId_);
      }
      if (!getApiBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, api_);
      }
      if (!getTemperatureBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, temperature_);
      }
      if (!getAbbreviationBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, abbreviation_);
      }
      if (!getQuantityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, quantity_);
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
          instanceof com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading) obj;

      if (getId() != other.getId()) return false;
      if (!getColour().equals(other.getColour())) return false;
      if (getCargoId() != other.getCargoId()) return false;
      if (!getApi().equals(other.getApi())) return false;
      if (!getTemperature().equals(other.getTemperature())) return false;
      if (!getAbbreviation().equals(other.getAbbreviation())) return false;
      if (!getQuantity().equals(other.getQuantity())) return false;
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
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
      hash = (37 * hash) + COLOUR_FIELD_NUMBER;
      hash = (53 * hash) + getColour().hashCode();
      hash = (37 * hash) + CARGOID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoId());
      hash = (37 * hash) + API_FIELD_NUMBER;
      hash = (53 * hash) + getApi().hashCode();
      hash = (37 * hash) + TEMPERATURE_FIELD_NUMBER;
      hash = (53 * hash) + getTemperature().hashCode();
      hash = (37 * hash) + ABBREVIATION_FIELD_NUMBER;
      hash = (53 * hash) + getAbbreviation().hashCode();
      hash = (37 * hash) + QUANTITY_FIELD_NUMBER;
      hash = (53 * hash) + getQuantity().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading prototype) {
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
    /** Protobuf type {@code BackLoading} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:BackLoading)
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoadingOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_BackLoading_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_BackLoading_fieldAccessorTable.ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading.class,
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading.Builder.class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading.newBuilder()
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
        id_ = 0L;

        colour_ = "";

        cargoId_ = 0L;

        api_ = "";

        temperature_ = "";

        abbreviation_ = "";

        quantity_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_BackLoading_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading result =
            new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading(this);
        result.id_ = id_;
        result.colour_ = colour_;
        result.cargoId_ = cargoId_;
        result.api_ = api_;
        result.temperature_ = temperature_;
        result.abbreviation_ = abbreviation_;
        result.quantity_ = quantity_;
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
            instanceof com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading) {
          return mergeFrom(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
                .getDefaultInstance()) return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (!other.getColour().isEmpty()) {
          colour_ = other.colour_;
          onChanged();
        }
        if (other.getCargoId() != 0L) {
          setCargoId(other.getCargoId());
        }
        if (!other.getApi().isEmpty()) {
          api_ = other.api_;
          onChanged();
        }
        if (!other.getTemperature().isEmpty()) {
          temperature_ = other.temperature_;
          onChanged();
        }
        if (!other.getAbbreviation().isEmpty()) {
          abbreviation_ = other.abbreviation_;
          onChanged();
        }
        if (!other.getQuantity().isEmpty()) {
          quantity_ = other.quantity_;
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading parsedMessage =
            null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long id_;
      /**
       * <code>int64 id = 1;</code>
       *
       * @return The id.
       */
      public long getId() {
        return id_;
      }
      /**
       * <code>int64 id = 1;</code>
       *
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(long value) {

        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 id = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {

        id_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object colour_ = "";
      /**
       * <code>string colour = 2;</code>
       *
       * @return The colour.
       */
      public java.lang.String getColour() {
        java.lang.Object ref = colour_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          colour_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string colour = 2;</code>
       *
       * @return The bytes for colour.
       */
      public com.google.protobuf.ByteString getColourBytes() {
        java.lang.Object ref = colour_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          colour_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string colour = 2;</code>
       *
       * @param value The colour to set.
       * @return This builder for chaining.
       */
      public Builder setColour(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        colour_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string colour = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearColour() {

        colour_ = getDefaultInstance().getColour();
        onChanged();
        return this;
      }
      /**
       * <code>string colour = 2;</code>
       *
       * @param value The bytes for colour to set.
       * @return This builder for chaining.
       */
      public Builder setColourBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        colour_ = value;
        onChanged();
        return this;
      }

      private long cargoId_;
      /**
       * <code>int64 cargoId = 3;</code>
       *
       * @return The cargoId.
       */
      public long getCargoId() {
        return cargoId_;
      }
      /**
       * <code>int64 cargoId = 3;</code>
       *
       * @param value The cargoId to set.
       * @return This builder for chaining.
       */
      public Builder setCargoId(long value) {

        cargoId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 cargoId = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoId() {

        cargoId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object api_ = "";
      /**
       * <code>string api = 4;</code>
       *
       * @return The api.
       */
      public java.lang.String getApi() {
        java.lang.Object ref = api_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          api_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string api = 4;</code>
       *
       * @return The bytes for api.
       */
      public com.google.protobuf.ByteString getApiBytes() {
        java.lang.Object ref = api_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          api_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string api = 4;</code>
       *
       * @param value The api to set.
       * @return This builder for chaining.
       */
      public Builder setApi(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        api_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string api = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearApi() {

        api_ = getDefaultInstance().getApi();
        onChanged();
        return this;
      }
      /**
       * <code>string api = 4;</code>
       *
       * @param value The bytes for api to set.
       * @return This builder for chaining.
       */
      public Builder setApiBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        api_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object temperature_ = "";
      /**
       * <code>string temperature = 5;</code>
       *
       * @return The temperature.
       */
      public java.lang.String getTemperature() {
        java.lang.Object ref = temperature_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          temperature_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string temperature = 5;</code>
       *
       * @return The bytes for temperature.
       */
      public com.google.protobuf.ByteString getTemperatureBytes() {
        java.lang.Object ref = temperature_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          temperature_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string temperature = 5;</code>
       *
       * @param value The temperature to set.
       * @return This builder for chaining.
       */
      public Builder setTemperature(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        temperature_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string temperature = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTemperature() {

        temperature_ = getDefaultInstance().getTemperature();
        onChanged();
        return this;
      }
      /**
       * <code>string temperature = 5;</code>
       *
       * @param value The bytes for temperature to set.
       * @return This builder for chaining.
       */
      public Builder setTemperatureBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        temperature_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object abbreviation_ = "";
      /**
       * <code>string abbreviation = 6;</code>
       *
       * @return The abbreviation.
       */
      public java.lang.String getAbbreviation() {
        java.lang.Object ref = abbreviation_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          abbreviation_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string abbreviation = 6;</code>
       *
       * @return The bytes for abbreviation.
       */
      public com.google.protobuf.ByteString getAbbreviationBytes() {
        java.lang.Object ref = abbreviation_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          abbreviation_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string abbreviation = 6;</code>
       *
       * @param value The abbreviation to set.
       * @return This builder for chaining.
       */
      public Builder setAbbreviation(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        abbreviation_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string abbreviation = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearAbbreviation() {

        abbreviation_ = getDefaultInstance().getAbbreviation();
        onChanged();
        return this;
      }
      /**
       * <code>string abbreviation = 6;</code>
       *
       * @param value The bytes for abbreviation to set.
       * @return This builder for chaining.
       */
      public Builder setAbbreviationBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        abbreviation_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object quantity_ = "";
      /**
       * <code>string quantity = 7;</code>
       *
       * @return The quantity.
       */
      public java.lang.String getQuantity() {
        java.lang.Object ref = quantity_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          quantity_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string quantity = 7;</code>
       *
       * @return The bytes for quantity.
       */
      public com.google.protobuf.ByteString getQuantityBytes() {
        java.lang.Object ref = quantity_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          quantity_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string quantity = 7;</code>
       *
       * @param value The quantity to set.
       * @return This builder for chaining.
       */
      public Builder setQuantity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        quantity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string quantity = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearQuantity() {

        quantity_ = getDefaultInstance().getQuantity();
        onChanged();
        return this;
      }
      /**
       * <code>string quantity = 7;</code>
       *
       * @param value The bytes for quantity to set.
       * @return This builder for chaining.
       */
      public Builder setQuantityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        quantity_ = value;
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

      // @@protoc_insertion_point(builder_scope:BackLoading)
    }

    // @@protoc_insertion_point(class_scope:BackLoading)
    private static final com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading();
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<BackLoading> PARSER =
        new com.google.protobuf.AbstractParser<BackLoading>() {
          @java.lang.Override
          public BackLoading parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new BackLoading(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<BackLoading> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<BackLoading> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.BackLoading
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface InstructionReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:InstructionReply)
      com.google.protobuf.MessageOrBuilder {

    /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
    java.util.List<com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail>
        getInstructionDetailsList();
    /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
    com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        getInstructionDetails(int index);
    /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
    int getInstructionDetailsCount();
    /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
    java.util.List<
            ? extends
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .InstructionDetailOrBuilder>
        getInstructionDetailsOrBuilderList();
    /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
    com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetailOrBuilder
        getInstructionDetailsOrBuilder(int index);

    /**
     * <code>.ResponseStatus responseStatus = 2;</code>
     *
     * @return Whether the responseStatus field is set.
     */
    boolean hasResponseStatus();
    /**
     * <code>.ResponseStatus responseStatus = 2;</code>
     *
     * @return The responseStatus.
     */
    com.cpdss.common.generated.Common.ResponseStatus getResponseStatus();
    /** <code>.ResponseStatus responseStatus = 2;</code> */
    com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder();
  }
  /** Protobuf type {@code InstructionReply} */
  public static final class InstructionReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:InstructionReply)
      InstructionReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use InstructionReply.newBuilder() to construct.
    private InstructionReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private InstructionReply() {
      instructionDetails_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new InstructionReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private InstructionReply(
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
                  instructionDetails_ =
                      new java.util.ArrayList<
                          com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .InstructionDetail>();
                  mutable_bitField0_ |= 0x00000001;
                }
                instructionDetails_.add(
                    input.readMessage(
                        com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                            .InstructionDetail.parser(),
                        extensionRegistry));
                break;
              }
            case 18:
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
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          instructionDetails_ = java.util.Collections.unmodifiableList(instructionDetails_);
        }
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
          .internal_static_InstructionReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_InstructionReply_fieldAccessorTable.ensureFieldAccessorsInitialized(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply.class,
          com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .InstructionReply
              .Builder
              .class);
    }

    public static final int INSTRUCTIONDETAILS_FIELD_NUMBER = 1;
    private java.util.List<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail>
        instructionDetails_;
    /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
    public java.util.List<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail>
        getInstructionDetailsList() {
      return instructionDetails_;
    }
    /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
    public java.util.List<
            ? extends
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .InstructionDetailOrBuilder>
        getInstructionDetailsOrBuilderList() {
      return instructionDetails_;
    }
    /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
    public int getInstructionDetailsCount() {
      return instructionDetails_.size();
    }
    /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        getInstructionDetails(int index) {
      return instructionDetails_.get(index);
    }
    /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetailOrBuilder
        getInstructionDetailsOrBuilder(int index) {
      return instructionDetails_.get(index);
    }

    public static final int RESPONSESTATUS_FIELD_NUMBER = 2;
    private com.cpdss.common.generated.Common.ResponseStatus responseStatus_;
    /**
     * <code>.ResponseStatus responseStatus = 2;</code>
     *
     * @return Whether the responseStatus field is set.
     */
    public boolean hasResponseStatus() {
      return responseStatus_ != null;
    }
    /**
     * <code>.ResponseStatus responseStatus = 2;</code>
     *
     * @return The responseStatus.
     */
    public com.cpdss.common.generated.Common.ResponseStatus getResponseStatus() {
      return responseStatus_ == null
          ? com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance()
          : responseStatus_;
    }
    /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      for (int i = 0; i < instructionDetails_.size(); i++) {
        output.writeMessage(1, instructionDetails_.get(i));
      }
      if (responseStatus_ != null) {
        output.writeMessage(2, getResponseStatus());
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      for (int i = 0; i < instructionDetails_.size(); i++) {
        size +=
            com.google.protobuf.CodedOutputStream.computeMessageSize(1, instructionDetails_.get(i));
      }
      if (responseStatus_ != null) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getResponseStatus());
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
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply) obj;

      if (!getInstructionDetailsList().equals(other.getInstructionDetailsList())) return false;
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
      if (getInstructionDetailsCount() > 0) {
        hash = (37 * hash) + INSTRUCTIONDETAILS_FIELD_NUMBER;
        hash = (53 * hash) + getInstructionDetailsList().hashCode();
      }
      if (hasResponseStatus()) {
        hash = (37 * hash) + RESPONSESTATUS_FIELD_NUMBER;
        hash = (53 * hash) + getResponseStatus().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply prototype) {
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
    /** Protobuf type {@code InstructionReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:InstructionReply)
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_InstructionReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_InstructionReply_fieldAccessorTable.ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply.class,
            com.cpdss
                .common
                .generated
                .loadableStudy
                .LoadableStudyModels
                .InstructionReply
                .Builder
                .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
          getInstructionDetailsFieldBuilder();
        }
      }

      @java.lang.Override
      public Builder clear() {
        super.clear();
        if (instructionDetailsBuilder_ == null) {
          instructionDetails_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          instructionDetailsBuilder_.clear();
        }
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
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_InstructionReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply result =
            new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply(this);
        int from_bitField0_ = bitField0_;
        if (instructionDetailsBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            instructionDetails_ = java.util.Collections.unmodifiableList(instructionDetails_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.instructionDetails_ = instructionDetails_;
        } else {
          result.instructionDetails_ = instructionDetailsBuilder_.build();
        }
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
        if (other
            instanceof
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply) {
          return mergeFrom(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
                .getDefaultInstance()) return this;
        if (instructionDetailsBuilder_ == null) {
          if (!other.instructionDetails_.isEmpty()) {
            if (instructionDetails_.isEmpty()) {
              instructionDetails_ = other.instructionDetails_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureInstructionDetailsIsMutable();
              instructionDetails_.addAll(other.instructionDetails_);
            }
            onChanged();
          }
        } else {
          if (!other.instructionDetails_.isEmpty()) {
            if (instructionDetailsBuilder_.isEmpty()) {
              instructionDetailsBuilder_.dispose();
              instructionDetailsBuilder_ = null;
              instructionDetails_ = other.instructionDetails_;
              bitField0_ = (bitField0_ & ~0x00000001);
              instructionDetailsBuilder_ =
                  com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                      ? getInstructionDetailsFieldBuilder()
                      : null;
            } else {
              instructionDetailsBuilder_.addAllMessages(other.instructionDetails_);
            }
          }
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply)
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

      private java.util.List<
              com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail>
          instructionDetails_ = java.util.Collections.emptyList();

      private void ensureInstructionDetailsIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          instructionDetails_ =
              new java.util.ArrayList<
                  com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail>(
                  instructionDetails_);
          bitField0_ |= 0x00000001;
        }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .InstructionDetail
                  .Builder,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .InstructionDetailOrBuilder>
          instructionDetailsBuilder_;

      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public java.util.List<
              com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail>
          getInstructionDetailsList() {
        if (instructionDetailsBuilder_ == null) {
          return java.util.Collections.unmodifiableList(instructionDetails_);
        } else {
          return instructionDetailsBuilder_.getMessageList();
        }
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public int getInstructionDetailsCount() {
        if (instructionDetailsBuilder_ == null) {
          return instructionDetails_.size();
        } else {
          return instructionDetailsBuilder_.getCount();
        }
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
          getInstructionDetails(int index) {
        if (instructionDetailsBuilder_ == null) {
          return instructionDetails_.get(index);
        } else {
          return instructionDetailsBuilder_.getMessage(index);
        }
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public Builder setInstructionDetails(
          int index,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail value) {
        if (instructionDetailsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureInstructionDetailsIsMutable();
          instructionDetails_.set(index, value);
          onChanged();
        } else {
          instructionDetailsBuilder_.setMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public Builder setInstructionDetails(
          int index,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail.Builder
              builderForValue) {
        if (instructionDetailsBuilder_ == null) {
          ensureInstructionDetailsIsMutable();
          instructionDetails_.set(index, builderForValue.build());
          onChanged();
        } else {
          instructionDetailsBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public Builder addInstructionDetails(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail value) {
        if (instructionDetailsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureInstructionDetailsIsMutable();
          instructionDetails_.add(value);
          onChanged();
        } else {
          instructionDetailsBuilder_.addMessage(value);
        }
        return this;
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public Builder addInstructionDetails(
          int index,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail value) {
        if (instructionDetailsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureInstructionDetailsIsMutable();
          instructionDetails_.add(index, value);
          onChanged();
        } else {
          instructionDetailsBuilder_.addMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public Builder addInstructionDetails(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail.Builder
              builderForValue) {
        if (instructionDetailsBuilder_ == null) {
          ensureInstructionDetailsIsMutable();
          instructionDetails_.add(builderForValue.build());
          onChanged();
        } else {
          instructionDetailsBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public Builder addInstructionDetails(
          int index,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail.Builder
              builderForValue) {
        if (instructionDetailsBuilder_ == null) {
          ensureInstructionDetailsIsMutable();
          instructionDetails_.add(index, builderForValue.build());
          onChanged();
        } else {
          instructionDetailsBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public Builder addAllInstructionDetails(
          java.lang.Iterable<
                  ? extends
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .InstructionDetail>
              values) {
        if (instructionDetailsBuilder_ == null) {
          ensureInstructionDetailsIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(values, instructionDetails_);
          onChanged();
        } else {
          instructionDetailsBuilder_.addAllMessages(values);
        }
        return this;
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public Builder clearInstructionDetails() {
        if (instructionDetailsBuilder_ == null) {
          instructionDetails_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          instructionDetailsBuilder_.clear();
        }
        return this;
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public Builder removeInstructionDetails(int index) {
        if (instructionDetailsBuilder_ == null) {
          ensureInstructionDetailsIsMutable();
          instructionDetails_.remove(index);
          onChanged();
        } else {
          instructionDetailsBuilder_.remove(index);
        }
        return this;
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail.Builder
          getInstructionDetailsBuilder(int index) {
        return getInstructionDetailsFieldBuilder().getBuilder(index);
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetailOrBuilder
          getInstructionDetailsOrBuilder(int index) {
        if (instructionDetailsBuilder_ == null) {
          return instructionDetails_.get(index);
        } else {
          return instructionDetailsBuilder_.getMessageOrBuilder(index);
        }
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public java.util.List<
              ? extends
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .InstructionDetailOrBuilder>
          getInstructionDetailsOrBuilderList() {
        if (instructionDetailsBuilder_ != null) {
          return instructionDetailsBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(instructionDetails_);
        }
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail.Builder
          addInstructionDetailsBuilder() {
        return getInstructionDetailsFieldBuilder()
            .addBuilder(
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
                    .getDefaultInstance());
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail.Builder
          addInstructionDetailsBuilder(int index) {
        return getInstructionDetailsFieldBuilder()
            .addBuilder(
                index,
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
                    .getDefaultInstance());
      }
      /** <code>repeated .InstructionDetail instructionDetails = 1;</code> */
      public java.util.List<
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .InstructionDetail
                  .Builder>
          getInstructionDetailsBuilderList() {
        return getInstructionDetailsFieldBuilder().getBuilderList();
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .InstructionDetail
                  .Builder,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .InstructionDetailOrBuilder>
          getInstructionDetailsFieldBuilder() {
        if (instructionDetailsBuilder_ == null) {
          instructionDetailsBuilder_ =
              new com.google.protobuf.RepeatedFieldBuilderV3<
                  com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail,
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .InstructionDetail
                      .Builder,
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .InstructionDetailOrBuilder>(
                  instructionDetails_,
                  ((bitField0_ & 0x00000001) != 0),
                  getParentForChildren(),
                  isClean());
          instructionDetails_ = null;
        }
        return instructionDetailsBuilder_;
      }

      private com.cpdss.common.generated.Common.ResponseStatus responseStatus_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.Common.ResponseStatus,
              com.cpdss.common.generated.Common.ResponseStatus.Builder,
              com.cpdss.common.generated.Common.ResponseStatusOrBuilder>
          responseStatusBuilder_;
      /**
       * <code>.ResponseStatus responseStatus = 2;</code>
       *
       * @return Whether the responseStatus field is set.
       */
      public boolean hasResponseStatus() {
        return responseStatusBuilder_ != null || responseStatus_ != null;
      }
      /**
       * <code>.ResponseStatus responseStatus = 2;</code>
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
      public com.cpdss.common.generated.Common.ResponseStatus.Builder getResponseStatusBuilder() {

        onChanged();
        return getResponseStatusFieldBuilder().getBuilder();
      }
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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

      // @@protoc_insertion_point(builder_scope:InstructionReply)
    }

    // @@protoc_insertion_point(class_scope:InstructionReply)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .InstructionReply
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply();
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<InstructionReply> PARSER =
        new com.google.protobuf.AbstractParser<InstructionReply>() {
          @java.lang.Override
          public InstructionReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new InstructionReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<InstructionReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<InstructionReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface InstructionDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:InstructionDetail)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>string instruction = 2;</code>
     *
     * @return The instruction.
     */
    java.lang.String getInstruction();
    /**
     * <code>string instruction = 2;</code>
     *
     * @return The bytes for instruction.
     */
    com.google.protobuf.ByteString getInstructionBytes();
  }
  /** Protobuf type {@code InstructionDetail} */
  public static final class InstructionDetail extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:InstructionDetail)
      InstructionDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use InstructionDetail.newBuilder() to construct.
    private InstructionDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private InstructionDetail() {
      instruction_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new InstructionDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private InstructionDetail(
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
                id_ = input.readInt64();
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                instruction_ = s;
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
          .internal_static_InstructionDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_InstructionDetail_fieldAccessorTable.ensureFieldAccessorsInitialized(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail.class,
          com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .InstructionDetail
              .Builder
              .class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private long id_;
    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
    }

    public static final int INSTRUCTION_FIELD_NUMBER = 2;
    private volatile java.lang.Object instruction_;
    /**
     * <code>string instruction = 2;</code>
     *
     * @return The instruction.
     */
    public java.lang.String getInstruction() {
      java.lang.Object ref = instruction_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        instruction_ = s;
        return s;
      }
    }
    /**
     * <code>string instruction = 2;</code>
     *
     * @return The bytes for instruction.
     */
    public com.google.protobuf.ByteString getInstructionBytes() {
      java.lang.Object ref = instruction_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        instruction_ = b;
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
      if (id_ != 0L) {
        output.writeInt64(1, id_);
      }
      if (!getInstructionBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, instruction_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (id_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, id_);
      }
      if (!getInstructionBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, instruction_);
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
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail) obj;

      if (getId() != other.getId()) return false;
      if (!getInstruction().equals(other.getInstruction())) return false;
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
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
      hash = (37 * hash) + INSTRUCTION_FIELD_NUMBER;
      hash = (53 * hash) + getInstruction().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail prototype) {
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
    /** Protobuf type {@code InstructionDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:InstructionDetail)
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_InstructionDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_InstructionDetail_fieldAccessorTable.ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail.class,
            com.cpdss
                .common
                .generated
                .loadableStudy
                .LoadableStudyModels
                .InstructionDetail
                .Builder
                .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail.newBuilder()
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
        id_ = 0L;

        instruction_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_InstructionDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
          build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail result =
            new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail(
                this);
        result.id_ = id_;
        result.instruction_ = instruction_;
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
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail) {
          return mergeFrom(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
                .getDefaultInstance()) return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (!other.getInstruction().isEmpty()) {
          instruction_ = other.instruction_;
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long id_;
      /**
       * <code>int64 id = 1;</code>
       *
       * @return The id.
       */
      public long getId() {
        return id_;
      }
      /**
       * <code>int64 id = 1;</code>
       *
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(long value) {

        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 id = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {

        id_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object instruction_ = "";
      /**
       * <code>string instruction = 2;</code>
       *
       * @return The instruction.
       */
      public java.lang.String getInstruction() {
        java.lang.Object ref = instruction_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          instruction_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string instruction = 2;</code>
       *
       * @return The bytes for instruction.
       */
      public com.google.protobuf.ByteString getInstructionBytes() {
        java.lang.Object ref = instruction_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          instruction_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string instruction = 2;</code>
       *
       * @param value The instruction to set.
       * @return This builder for chaining.
       */
      public Builder setInstruction(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        instruction_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string instruction = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearInstruction() {

        instruction_ = getDefaultInstance().getInstruction();
        onChanged();
        return this;
      }
      /**
       * <code>string instruction = 2;</code>
       *
       * @param value The bytes for instruction to set.
       * @return This builder for chaining.
       */
      public Builder setInstructionBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        instruction_ = value;
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

      // @@protoc_insertion_point(builder_scope:InstructionDetail)
    }

    // @@protoc_insertion_point(class_scope:InstructionDetail)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .InstructionDetail
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail();
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<InstructionDetail> PARSER =
        new com.google.protobuf.AbstractParser<InstructionDetail>() {
          @java.lang.Override
          public InstructionDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new InstructionDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<InstructionDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<InstructionDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionDetail
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface EmptyRequestOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:EmptyRequest)
      com.google.protobuf.MessageOrBuilder {}
  /** Protobuf type {@code EmptyRequest} */
  public static final class EmptyRequest extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:EmptyRequest)
      EmptyRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use EmptyRequest.newBuilder() to construct.
    private EmptyRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private EmptyRequest() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new EmptyRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private EmptyRequest(
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
          .internal_static_EmptyRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_EmptyRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest.class,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest.Builder.class);
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
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
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
          instanceof com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest) obj;

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
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest prototype) {
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
    /** Protobuf type {@code EmptyRequest} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:EmptyRequest)
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_EmptyRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_EmptyRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest.class,
            com.cpdss
                .common
                .generated
                .loadableStudy
                .LoadableStudyModels
                .EmptyRequest
                .Builder
                .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest.newBuilder()
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
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_EmptyRequest_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest result =
            new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest(this);
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
            instanceof com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest) {
          return mergeFrom(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
                .getDefaultInstance()) return this;
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest parsedMessage =
            null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
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

      // @@protoc_insertion_point(builder_scope:EmptyRequest)
    }

    // @@protoc_insertion_point(class_scope:EmptyRequest)
    private static final com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest();
    }

    public static com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<EmptyRequest> PARSER =
        new com.google.protobuf.AbstractParser<EmptyRequest>() {
          @java.lang.Override
          public EmptyRequest parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new EmptyRequest(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<EmptyRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<EmptyRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface DishargeStudyCargoDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:DishargeStudyCargoDetail)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>string crudeType = 2;</code>
     *
     * @return The crudeType.
     */
    java.lang.String getCrudeType();
    /**
     * <code>string crudeType = 2;</code>
     *
     * @return The bytes for crudeType.
     */
    com.google.protobuf.ByteString getCrudeTypeBytes();

    /**
     * <code>string abbreviation = 3;</code>
     *
     * @return The abbreviation.
     */
    java.lang.String getAbbreviation();
    /**
     * <code>string abbreviation = 3;</code>
     *
     * @return The bytes for abbreviation.
     */
    com.google.protobuf.ByteString getAbbreviationBytes();

    /**
     * <code>string api = 4;</code>
     *
     * @return The api.
     */
    java.lang.String getApi();
    /**
     * <code>string api = 4;</code>
     *
     * @return The bytes for api.
     */
    com.google.protobuf.ByteString getApiBytes();

    /**
     * <code>bool isCondensateCargo = 5;</code>
     *
     * @return The isCondensateCargo.
     */
    boolean getIsCondensateCargo();

    /**
     * <code>bool isHrvpCargo = 6;</code>
     *
     * @return The isHrvpCargo.
     */
    boolean getIsHrvpCargo();
  }
  /** Protobuf type {@code DishargeStudyCargoDetail} */
  public static final class DishargeStudyCargoDetail extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:DishargeStudyCargoDetail)
      DishargeStudyCargoDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use DishargeStudyCargoDetail.newBuilder() to construct.
    private DishargeStudyCargoDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private DishargeStudyCargoDetail() {
      crudeType_ = "";
      abbreviation_ = "";
      api_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new DishargeStudyCargoDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private DishargeStudyCargoDetail(
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
                id_ = input.readInt64();
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                crudeType_ = s;
                break;
              }
            case 26:
              {
                java.lang.String s = input.readStringRequireUtf8();

                abbreviation_ = s;
                break;
              }
            case 34:
              {
                java.lang.String s = input.readStringRequireUtf8();

                api_ = s;
                break;
              }
            case 40:
              {
                isCondensateCargo_ = input.readBool();
                break;
              }
            case 48:
              {
                isHrvpCargo_ = input.readBool();
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
          .internal_static_DishargeStudyCargoDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_DishargeStudyCargoDetail_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoDetail
                  .class,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoDetail
                  .Builder
                  .class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private long id_;
    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
    }

    public static final int CRUDETYPE_FIELD_NUMBER = 2;
    private volatile java.lang.Object crudeType_;
    /**
     * <code>string crudeType = 2;</code>
     *
     * @return The crudeType.
     */
    public java.lang.String getCrudeType() {
      java.lang.Object ref = crudeType_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        crudeType_ = s;
        return s;
      }
    }
    /**
     * <code>string crudeType = 2;</code>
     *
     * @return The bytes for crudeType.
     */
    public com.google.protobuf.ByteString getCrudeTypeBytes() {
      java.lang.Object ref = crudeType_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        crudeType_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ABBREVIATION_FIELD_NUMBER = 3;
    private volatile java.lang.Object abbreviation_;
    /**
     * <code>string abbreviation = 3;</code>
     *
     * @return The abbreviation.
     */
    public java.lang.String getAbbreviation() {
      java.lang.Object ref = abbreviation_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        abbreviation_ = s;
        return s;
      }
    }
    /**
     * <code>string abbreviation = 3;</code>
     *
     * @return The bytes for abbreviation.
     */
    public com.google.protobuf.ByteString getAbbreviationBytes() {
      java.lang.Object ref = abbreviation_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        abbreviation_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int API_FIELD_NUMBER = 4;
    private volatile java.lang.Object api_;
    /**
     * <code>string api = 4;</code>
     *
     * @return The api.
     */
    public java.lang.String getApi() {
      java.lang.Object ref = api_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        api_ = s;
        return s;
      }
    }
    /**
     * <code>string api = 4;</code>
     *
     * @return The bytes for api.
     */
    public com.google.protobuf.ByteString getApiBytes() {
      java.lang.Object ref = api_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        api_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ISCONDENSATECARGO_FIELD_NUMBER = 5;
    private boolean isCondensateCargo_;
    /**
     * <code>bool isCondensateCargo = 5;</code>
     *
     * @return The isCondensateCargo.
     */
    public boolean getIsCondensateCargo() {
      return isCondensateCargo_;
    }

    public static final int ISHRVPCARGO_FIELD_NUMBER = 6;
    private boolean isHrvpCargo_;
    /**
     * <code>bool isHrvpCargo = 6;</code>
     *
     * @return The isHrvpCargo.
     */
    public boolean getIsHrvpCargo() {
      return isHrvpCargo_;
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
      if (id_ != 0L) {
        output.writeInt64(1, id_);
      }
      if (!getCrudeTypeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, crudeType_);
      }
      if (!getAbbreviationBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, abbreviation_);
      }
      if (!getApiBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, api_);
      }
      if (isCondensateCargo_ != false) {
        output.writeBool(5, isCondensateCargo_);
      }
      if (isHrvpCargo_ != false) {
        output.writeBool(6, isHrvpCargo_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (id_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, id_);
      }
      if (!getCrudeTypeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, crudeType_);
      }
      if (!getAbbreviationBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, abbreviation_);
      }
      if (!getApiBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, api_);
      }
      if (isCondensateCargo_ != false) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(5, isCondensateCargo_);
      }
      if (isHrvpCargo_ != false) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(6, isHrvpCargo_);
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
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail)
              obj;

      if (getId() != other.getId()) return false;
      if (!getCrudeType().equals(other.getCrudeType())) return false;
      if (!getAbbreviation().equals(other.getAbbreviation())) return false;
      if (!getApi().equals(other.getApi())) return false;
      if (getIsCondensateCargo() != other.getIsCondensateCargo()) return false;
      if (getIsHrvpCargo() != other.getIsHrvpCargo()) return false;
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
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
      hash = (37 * hash) + CRUDETYPE_FIELD_NUMBER;
      hash = (53 * hash) + getCrudeType().hashCode();
      hash = (37 * hash) + ABBREVIATION_FIELD_NUMBER;
      hash = (53 * hash) + getAbbreviation().hashCode();
      hash = (37 * hash) + API_FIELD_NUMBER;
      hash = (53 * hash) + getApi().hashCode();
      hash = (37 * hash) + ISCONDENSATECARGO_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsCondensateCargo());
      hash = (37 * hash) + ISHRVPCARGO_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsHrvpCargo());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
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
    /** Protobuf type {@code DishargeStudyCargoDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:DishargeStudyCargoDetail)
        com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DishargeStudyCargoDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_DishargeStudyCargoDetail_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyCargoDetail
                    .class,
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyCargoDetail
                    .Builder
                    .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail.newBuilder()
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
        id_ = 0L;

        crudeType_ = "";

        abbreviation_ = "";

        api_ = "";

        isCondensateCargo_ = false;

        isHrvpCargo_ = false;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DishargeStudyCargoDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
          build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
            result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
            result =
                new com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyCargoDetail(this);
        result.id_ = id_;
        result.crudeType_ = crudeType_;
        result.abbreviation_ = abbreviation_;
        result.api_ = api_;
        result.isCondensateCargo_ = isCondensateCargo_;
        result.isHrvpCargo_ = isHrvpCargo_;
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
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail) {
          return mergeFrom(
              (com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyCargoDetail)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
              other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
                .getDefaultInstance()) return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (!other.getCrudeType().isEmpty()) {
          crudeType_ = other.crudeType_;
          onChanged();
        }
        if (!other.getAbbreviation().isEmpty()) {
          abbreviation_ = other.abbreviation_;
          onChanged();
        }
        if (!other.getApi().isEmpty()) {
          api_ = other.api_;
          onChanged();
        }
        if (other.getIsCondensateCargo() != false) {
          setIsCondensateCargo(other.getIsCondensateCargo());
        }
        if (other.getIsHrvpCargo() != false) {
          setIsHrvpCargo(other.getIsHrvpCargo());
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyCargoDetail)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long id_;
      /**
       * <code>int64 id = 1;</code>
       *
       * @return The id.
       */
      public long getId() {
        return id_;
      }
      /**
       * <code>int64 id = 1;</code>
       *
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(long value) {

        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 id = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {

        id_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object crudeType_ = "";
      /**
       * <code>string crudeType = 2;</code>
       *
       * @return The crudeType.
       */
      public java.lang.String getCrudeType() {
        java.lang.Object ref = crudeType_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          crudeType_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string crudeType = 2;</code>
       *
       * @return The bytes for crudeType.
       */
      public com.google.protobuf.ByteString getCrudeTypeBytes() {
        java.lang.Object ref = crudeType_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          crudeType_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string crudeType = 2;</code>
       *
       * @param value The crudeType to set.
       * @return This builder for chaining.
       */
      public Builder setCrudeType(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        crudeType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string crudeType = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCrudeType() {

        crudeType_ = getDefaultInstance().getCrudeType();
        onChanged();
        return this;
      }
      /**
       * <code>string crudeType = 2;</code>
       *
       * @param value The bytes for crudeType to set.
       * @return This builder for chaining.
       */
      public Builder setCrudeTypeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        crudeType_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object abbreviation_ = "";
      /**
       * <code>string abbreviation = 3;</code>
       *
       * @return The abbreviation.
       */
      public java.lang.String getAbbreviation() {
        java.lang.Object ref = abbreviation_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          abbreviation_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string abbreviation = 3;</code>
       *
       * @return The bytes for abbreviation.
       */
      public com.google.protobuf.ByteString getAbbreviationBytes() {
        java.lang.Object ref = abbreviation_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          abbreviation_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string abbreviation = 3;</code>
       *
       * @param value The abbreviation to set.
       * @return This builder for chaining.
       */
      public Builder setAbbreviation(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        abbreviation_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string abbreviation = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearAbbreviation() {

        abbreviation_ = getDefaultInstance().getAbbreviation();
        onChanged();
        return this;
      }
      /**
       * <code>string abbreviation = 3;</code>
       *
       * @param value The bytes for abbreviation to set.
       * @return This builder for chaining.
       */
      public Builder setAbbreviationBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        abbreviation_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object api_ = "";
      /**
       * <code>string api = 4;</code>
       *
       * @return The api.
       */
      public java.lang.String getApi() {
        java.lang.Object ref = api_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          api_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string api = 4;</code>
       *
       * @return The bytes for api.
       */
      public com.google.protobuf.ByteString getApiBytes() {
        java.lang.Object ref = api_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          api_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string api = 4;</code>
       *
       * @param value The api to set.
       * @return This builder for chaining.
       */
      public Builder setApi(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        api_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string api = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearApi() {

        api_ = getDefaultInstance().getApi();
        onChanged();
        return this;
      }
      /**
       * <code>string api = 4;</code>
       *
       * @param value The bytes for api to set.
       * @return This builder for chaining.
       */
      public Builder setApiBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        api_ = value;
        onChanged();
        return this;
      }

      private boolean isCondensateCargo_;
      /**
       * <code>bool isCondensateCargo = 5;</code>
       *
       * @return The isCondensateCargo.
       */
      public boolean getIsCondensateCargo() {
        return isCondensateCargo_;
      }
      /**
       * <code>bool isCondensateCargo = 5;</code>
       *
       * @param value The isCondensateCargo to set.
       * @return This builder for chaining.
       */
      public Builder setIsCondensateCargo(boolean value) {

        isCondensateCargo_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool isCondensateCargo = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearIsCondensateCargo() {

        isCondensateCargo_ = false;
        onChanged();
        return this;
      }

      private boolean isHrvpCargo_;
      /**
       * <code>bool isHrvpCargo = 6;</code>
       *
       * @return The isHrvpCargo.
       */
      public boolean getIsHrvpCargo() {
        return isHrvpCargo_;
      }
      /**
       * <code>bool isHrvpCargo = 6;</code>
       *
       * @param value The isHrvpCargo to set.
       * @return This builder for chaining.
       */
      public Builder setIsHrvpCargo(boolean value) {

        isHrvpCargo_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool isHrvpCargo = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearIsHrvpCargo() {

        isHrvpCargo_ = false;
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

      // @@protoc_insertion_point(builder_scope:DishargeStudyCargoDetail)
    }

    // @@protoc_insertion_point(class_scope:DishargeStudyCargoDetail)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyCargoDetail();
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetail
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DishargeStudyCargoDetail> PARSER =
        new com.google.protobuf.AbstractParser<DishargeStudyCargoDetail>() {
          @java.lang.Override
          public DishargeStudyCargoDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new DishargeStudyCargoDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<DishargeStudyCargoDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DishargeStudyCargoDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface DishargeStudyPortCargoMappingOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:DishargeStudyPortCargoMapping)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 portId = 1;</code>
     *
     * @return The portId.
     */
    long getPortId();

    /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
    java.util.List<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail>
        getCargosList();
    /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
    com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail getCargos(
        int index);
    /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
    int getCargosCount();
    /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
    java.util.List<
            ? extends
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyCargoDetailOrBuilder>
        getCargosOrBuilderList();
    /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
    com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetailOrBuilder
        getCargosOrBuilder(int index);
  }
  /** Protobuf type {@code DishargeStudyPortCargoMapping} */
  public static final class DishargeStudyPortCargoMapping
      extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:DishargeStudyPortCargoMapping)
      DishargeStudyPortCargoMappingOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use DishargeStudyPortCargoMapping.newBuilder() to construct.
    private DishargeStudyPortCargoMapping(
        com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private DishargeStudyPortCargoMapping() {
      cargos_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new DishargeStudyPortCargoMapping();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private DishargeStudyPortCargoMapping(
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
            case 8:
              {
                portId_ = input.readInt64();
                break;
              }
            case 18:
              {
                if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                  cargos_ =
                      new java.util.ArrayList<
                          com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .DishargeStudyCargoDetail>();
                  mutable_bitField0_ |= 0x00000001;
                }
                cargos_.add(
                    input.readMessage(
                        com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                            .DishargeStudyCargoDetail.parser(),
                        extensionRegistry));
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
          cargos_ = java.util.Collections.unmodifiableList(cargos_);
        }
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
          .internal_static_DishargeStudyPortCargoMapping_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_DishargeStudyPortCargoMapping_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping
                  .class,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping
                  .Builder
                  .class);
    }

    public static final int PORTID_FIELD_NUMBER = 1;
    private long portId_;
    /**
     * <code>int64 portId = 1;</code>
     *
     * @return The portId.
     */
    public long getPortId() {
      return portId_;
    }

    public static final int CARGOS_FIELD_NUMBER = 2;
    private java.util.List<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail>
        cargos_;
    /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
    public java.util.List<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail>
        getCargosList() {
      return cargos_;
    }
    /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
    public java.util.List<
            ? extends
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyCargoDetailOrBuilder>
        getCargosOrBuilderList() {
      return cargos_;
    }
    /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
    public int getCargosCount() {
      return cargos_.size();
    }
    /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
        getCargos(int index) {
      return cargos_.get(index);
    }
    /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
    public com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoDetailOrBuilder
        getCargosOrBuilder(int index) {
      return cargos_.get(index);
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
      if (portId_ != 0L) {
        output.writeInt64(1, portId_);
      }
      for (int i = 0; i < cargos_.size(); i++) {
        output.writeMessage(2, cargos_.get(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (portId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, portId_);
      }
      for (int i = 0; i < cargos_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, cargos_.get(i));
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
          com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyPortCargoMapping)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping
          other =
              (com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyPortCargoMapping)
                  obj;

      if (getPortId() != other.getPortId()) return false;
      if (!getCargosList().equals(other.getCargosList())) return false;
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
      if (getCargosCount() > 0) {
        hash = (37 * hash) + CARGOS_FIELD_NUMBER;
        hash = (53 * hash) + getCargosList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping
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
    /** Protobuf type {@code DishargeStudyPortCargoMapping} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:DishargeStudyPortCargoMapping)
        com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMappingOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DishargeStudyPortCargoMapping_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_DishargeStudyPortCargoMapping_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyPortCargoMapping
                    .class,
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyPortCargoMapping
                    .Builder
                    .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
          getCargosFieldBuilder();
        }
      }

      @java.lang.Override
      public Builder clear() {
        super.clear();
        portId_ = 0L;

        if (cargosBuilder_ == null) {
          cargos_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          cargosBuilder_.clear();
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DishargeStudyPortCargoMapping_descriptor;
      }

      @java.lang.Override
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyPortCargoMapping
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .DishargeStudyPortCargoMapping.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyPortCargoMapping
          build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping
            result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyPortCargoMapping
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping
            result =
                new com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyPortCargoMapping(this);
        int from_bitField0_ = bitField0_;
        result.portId_ = portId_;
        if (cargosBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            cargos_ = java.util.Collections.unmodifiableList(cargos_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.cargos_ = cargos_;
        } else {
          result.cargos_ = cargosBuilder_.build();
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
        if (other
            instanceof
            com.cpdss
                .common
                .generated
                .loadableStudy
                .LoadableStudyModels
                .DishargeStudyPortCargoMapping) {
          return mergeFrom(
              (com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyPortCargoMapping)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping
              other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                .DishargeStudyPortCargoMapping.getDefaultInstance()) return this;
        if (other.getPortId() != 0L) {
          setPortId(other.getPortId());
        }
        if (cargosBuilder_ == null) {
          if (!other.cargos_.isEmpty()) {
            if (cargos_.isEmpty()) {
              cargos_ = other.cargos_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureCargosIsMutable();
              cargos_.addAll(other.cargos_);
            }
            onChanged();
          }
        } else {
          if (!other.cargos_.isEmpty()) {
            if (cargosBuilder_.isEmpty()) {
              cargosBuilder_.dispose();
              cargosBuilder_ = null;
              cargos_ = other.cargos_;
              bitField0_ = (bitField0_ & ~0x00000001);
              cargosBuilder_ =
                  com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                      ? getCargosFieldBuilder()
                      : null;
            } else {
              cargosBuilder_.addAllMessages(other.cargos_);
            }
          }
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyPortCargoMapping)
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

      private long portId_;
      /**
       * <code>int64 portId = 1;</code>
       *
       * @return The portId.
       */
      public long getPortId() {
        return portId_;
      }
      /**
       * <code>int64 portId = 1;</code>
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
       * <code>int64 portId = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPortId() {

        portId_ = 0L;
        onChanged();
        return this;
      }

      private java.util.List<
              com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail>
          cargos_ = java.util.Collections.emptyList();

      private void ensureCargosIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          cargos_ =
              new java.util.ArrayList<
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyCargoDetail>(cargos_);
          bitField0_ |= 0x00000001;
        }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoDetail
                  .Builder,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoDetailOrBuilder>
          cargosBuilder_;

      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public java.util.List<
              com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail>
          getCargosList() {
        if (cargosBuilder_ == null) {
          return java.util.Collections.unmodifiableList(cargos_);
        } else {
          return cargosBuilder_.getMessageList();
        }
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public int getCargosCount() {
        if (cargosBuilder_ == null) {
          return cargos_.size();
        } else {
          return cargosBuilder_.getCount();
        }
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
          getCargos(int index) {
        if (cargosBuilder_ == null) {
          return cargos_.get(index);
        } else {
          return cargosBuilder_.getMessage(index);
        }
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public Builder setCargos(
          int index,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
              value) {
        if (cargosBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureCargosIsMutable();
          cargos_.set(index, value);
          onChanged();
        } else {
          cargosBuilder_.setMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public Builder setCargos(
          int index,
          com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoDetail
                  .Builder
              builderForValue) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          cargos_.set(index, builderForValue.build());
          onChanged();
        } else {
          cargosBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public Builder addCargos(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
              value) {
        if (cargosBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureCargosIsMutable();
          cargos_.add(value);
          onChanged();
        } else {
          cargosBuilder_.addMessage(value);
        }
        return this;
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public Builder addCargos(
          int index,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail
              value) {
        if (cargosBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureCargosIsMutable();
          cargos_.add(index, value);
          onChanged();
        } else {
          cargosBuilder_.addMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public Builder addCargos(
          com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoDetail
                  .Builder
              builderForValue) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          cargos_.add(builderForValue.build());
          onChanged();
        } else {
          cargosBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public Builder addCargos(
          int index,
          com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoDetail
                  .Builder
              builderForValue) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          cargos_.add(index, builderForValue.build());
          onChanged();
        } else {
          cargosBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public Builder addAllCargos(
          java.lang.Iterable<
                  ? extends
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DishargeStudyCargoDetail>
              values) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(values, cargos_);
          onChanged();
        } else {
          cargosBuilder_.addAllMessages(values);
        }
        return this;
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public Builder clearCargos() {
        if (cargosBuilder_ == null) {
          cargos_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          cargosBuilder_.clear();
        }
        return this;
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public Builder removeCargos(int index) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          cargos_.remove(index);
          onChanged();
        } else {
          cargosBuilder_.remove(index);
        }
        return this;
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyCargoDetail
              .Builder
          getCargosBuilder(int index) {
        return getCargosFieldBuilder().getBuilder(index);
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyCargoDetailOrBuilder
          getCargosOrBuilder(int index) {
        if (cargosBuilder_ == null) {
          return cargos_.get(index);
        } else {
          return cargosBuilder_.getMessageOrBuilder(index);
        }
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public java.util.List<
              ? extends
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyCargoDetailOrBuilder>
          getCargosOrBuilderList() {
        if (cargosBuilder_ != null) {
          return cargosBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(cargos_);
        }
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyCargoDetail
              .Builder
          addCargosBuilder() {
        return getCargosFieldBuilder()
            .addBuilder(
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                    .DishargeStudyCargoDetail.getDefaultInstance());
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyCargoDetail
              .Builder
          addCargosBuilder(int index) {
        return getCargosFieldBuilder()
            .addBuilder(
                index,
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                    .DishargeStudyCargoDetail.getDefaultInstance());
      }
      /** <code>repeated .DishargeStudyCargoDetail cargos = 2;</code> */
      public java.util.List<
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoDetail
                  .Builder>
          getCargosBuilderList() {
        return getCargosFieldBuilder().getBuilderList();
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoDetail,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoDetail
                  .Builder,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoDetailOrBuilder>
          getCargosFieldBuilder() {
        if (cargosBuilder_ == null) {
          cargosBuilder_ =
              new com.google.protobuf.RepeatedFieldBuilderV3<
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyCargoDetail,
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyCargoDetail
                      .Builder,
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyCargoDetailOrBuilder>(
                  cargos_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
          cargos_ = null;
        }
        return cargosBuilder_;
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

      // @@protoc_insertion_point(builder_scope:DishargeStudyPortCargoMapping)
    }

    // @@protoc_insertion_point(class_scope:DishargeStudyPortCargoMapping)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyPortCargoMapping();
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DishargeStudyPortCargoMapping> PARSER =
        new com.google.protobuf.AbstractParser<DishargeStudyPortCargoMapping>() {
          @java.lang.Override
          public DishargeStudyPortCargoMapping parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new DishargeStudyPortCargoMapping(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<DishargeStudyPortCargoMapping> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DishargeStudyPortCargoMapping> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface DishargeStudyCargoReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:DishargeStudyCargoReply)
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

    /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
    java.util.List<
            com.cpdss
                .common
                .generated
                .loadableStudy
                .LoadableStudyModels
                .DishargeStudyPortCargoMapping>
        getPortCargosList();
    /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
    com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping
        getPortCargos(int index);
    /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
    int getPortCargosCount();
    /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
    java.util.List<
            ? extends
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyPortCargoMappingOrBuilder>
        getPortCargosOrBuilderList();
    /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
    com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMappingOrBuilder
        getPortCargosOrBuilder(int index);
  }
  /** Protobuf type {@code DishargeStudyCargoReply} */
  public static final class DishargeStudyCargoReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:DishargeStudyCargoReply)
      DishargeStudyCargoReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use DishargeStudyCargoReply.newBuilder() to construct.
    private DishargeStudyCargoReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private DishargeStudyCargoReply() {
      portCargos_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new DishargeStudyCargoReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private DishargeStudyCargoReply(
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
                if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                  portCargos_ =
                      new java.util.ArrayList<
                          com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .DishargeStudyPortCargoMapping>();
                  mutable_bitField0_ |= 0x00000001;
                }
                portCargos_.add(
                    input.readMessage(
                        com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                            .DishargeStudyPortCargoMapping.parser(),
                        extensionRegistry));
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
          portCargos_ = java.util.Collections.unmodifiableList(portCargos_);
        }
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
          .internal_static_DishargeStudyCargoReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_DishargeStudyCargoReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoReply
                  .class,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyCargoReply
                  .Builder
                  .class);
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

    public static final int PORTCARGOS_FIELD_NUMBER = 2;
    private java.util.List<
            com.cpdss
                .common
                .generated
                .loadableStudy
                .LoadableStudyModels
                .DishargeStudyPortCargoMapping>
        portCargos_;
    /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
    public java.util.List<
            com.cpdss
                .common
                .generated
                .loadableStudy
                .LoadableStudyModels
                .DishargeStudyPortCargoMapping>
        getPortCargosList() {
      return portCargos_;
    }
    /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
    public java.util.List<
            ? extends
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyPortCargoMappingOrBuilder>
        getPortCargosOrBuilderList() {
      return portCargos_;
    }
    /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
    public int getPortCargosCount() {
      return portCargos_.size();
    }
    /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
    public com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMapping
        getPortCargos(int index) {
      return portCargos_.get(index);
    }
    /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
    public com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyPortCargoMappingOrBuilder
        getPortCargosOrBuilder(int index) {
      return portCargos_.get(index);
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
      for (int i = 0; i < portCargos_.size(); i++) {
        output.writeMessage(2, portCargos_.get(i));
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
      for (int i = 0; i < portCargos_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, portCargos_.get(i));
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
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply)
              obj;

      if (hasResponseStatus() != other.hasResponseStatus()) return false;
      if (hasResponseStatus()) {
        if (!getResponseStatus().equals(other.getResponseStatus())) return false;
      }
      if (!getPortCargosList().equals(other.getPortCargosList())) return false;
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
      if (getPortCargosCount() > 0) {
        hash = (37 * hash) + PORTCARGOS_FIELD_NUMBER;
        hash = (53 * hash) + getPortCargosList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
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
    /** Protobuf type {@code DishargeStudyCargoReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:DishargeStudyCargoReply)
        com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DishargeStudyCargoReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_DishargeStudyCargoReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyCargoReply
                    .class,
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyCargoReply
                    .Builder
                    .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
          getPortCargosFieldBuilder();
        }
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
        if (portCargosBuilder_ == null) {
          portCargos_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          portCargosBuilder_.clear();
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DishargeStudyCargoReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
          build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
            result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
            result =
                new com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyCargoReply(this);
        int from_bitField0_ = bitField0_;
        if (responseStatusBuilder_ == null) {
          result.responseStatus_ = responseStatus_;
        } else {
          result.responseStatus_ = responseStatusBuilder_.build();
        }
        if (portCargosBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            portCargos_ = java.util.Collections.unmodifiableList(portCargos_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.portCargos_ = portCargos_;
        } else {
          result.portCargos_ = portCargosBuilder_.build();
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
        if (other
            instanceof
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply) {
          return mergeFrom(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
              other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
                .getDefaultInstance()) return this;
        if (other.hasResponseStatus()) {
          mergeResponseStatus(other.getResponseStatus());
        }
        if (portCargosBuilder_ == null) {
          if (!other.portCargos_.isEmpty()) {
            if (portCargos_.isEmpty()) {
              portCargos_ = other.portCargos_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensurePortCargosIsMutable();
              portCargos_.addAll(other.portCargos_);
            }
            onChanged();
          }
        } else {
          if (!other.portCargos_.isEmpty()) {
            if (portCargosBuilder_.isEmpty()) {
              portCargosBuilder_.dispose();
              portCargosBuilder_ = null;
              portCargos_ = other.portCargos_;
              bitField0_ = (bitField0_ & ~0x00000001);
              portCargosBuilder_ =
                  com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                      ? getPortCargosFieldBuilder()
                      : null;
            } else {
              portCargosBuilder_.addAllMessages(other.portCargos_);
            }
          }
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply)
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

      private java.util.List<
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping>
          portCargos_ = java.util.Collections.emptyList();

      private void ensurePortCargosIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          portCargos_ =
              new java.util.ArrayList<
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyPortCargoMapping>(portCargos_);
          bitField0_ |= 0x00000001;
        }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping
                  .Builder,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMappingOrBuilder>
          portCargosBuilder_;

      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public java.util.List<
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping>
          getPortCargosList() {
        if (portCargosBuilder_ == null) {
          return java.util.Collections.unmodifiableList(portCargos_);
        } else {
          return portCargosBuilder_.getMessageList();
        }
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public int getPortCargosCount() {
        if (portCargosBuilder_ == null) {
          return portCargos_.size();
        } else {
          return portCargosBuilder_.getCount();
        }
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyPortCargoMapping
          getPortCargos(int index) {
        if (portCargosBuilder_ == null) {
          return portCargos_.get(index);
        } else {
          return portCargosBuilder_.getMessage(index);
        }
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public Builder setPortCargos(
          int index,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping
              value) {
        if (portCargosBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensurePortCargosIsMutable();
          portCargos_.set(index, value);
          onChanged();
        } else {
          portCargosBuilder_.setMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public Builder setPortCargos(
          int index,
          com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping
                  .Builder
              builderForValue) {
        if (portCargosBuilder_ == null) {
          ensurePortCargosIsMutable();
          portCargos_.set(index, builderForValue.build());
          onChanged();
        } else {
          portCargosBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public Builder addPortCargos(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping
              value) {
        if (portCargosBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensurePortCargosIsMutable();
          portCargos_.add(value);
          onChanged();
        } else {
          portCargosBuilder_.addMessage(value);
        }
        return this;
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public Builder addPortCargos(
          int index,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyPortCargoMapping
              value) {
        if (portCargosBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensurePortCargosIsMutable();
          portCargos_.add(index, value);
          onChanged();
        } else {
          portCargosBuilder_.addMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public Builder addPortCargos(
          com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping
                  .Builder
              builderForValue) {
        if (portCargosBuilder_ == null) {
          ensurePortCargosIsMutable();
          portCargos_.add(builderForValue.build());
          onChanged();
        } else {
          portCargosBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public Builder addPortCargos(
          int index,
          com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping
                  .Builder
              builderForValue) {
        if (portCargosBuilder_ == null) {
          ensurePortCargosIsMutable();
          portCargos_.add(index, builderForValue.build());
          onChanged();
        } else {
          portCargosBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public Builder addAllPortCargos(
          java.lang.Iterable<
                  ? extends
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DishargeStudyPortCargoMapping>
              values) {
        if (portCargosBuilder_ == null) {
          ensurePortCargosIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(values, portCargos_);
          onChanged();
        } else {
          portCargosBuilder_.addAllMessages(values);
        }
        return this;
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public Builder clearPortCargos() {
        if (portCargosBuilder_ == null) {
          portCargos_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          portCargosBuilder_.clear();
        }
        return this;
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public Builder removePortCargos(int index) {
        if (portCargosBuilder_ == null) {
          ensurePortCargosIsMutable();
          portCargos_.remove(index);
          onChanged();
        } else {
          portCargosBuilder_.remove(index);
        }
        return this;
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyPortCargoMapping
              .Builder
          getPortCargosBuilder(int index) {
        return getPortCargosFieldBuilder().getBuilder(index);
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyPortCargoMappingOrBuilder
          getPortCargosOrBuilder(int index) {
        if (portCargosBuilder_ == null) {
          return portCargos_.get(index);
        } else {
          return portCargosBuilder_.getMessageOrBuilder(index);
        }
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public java.util.List<
              ? extends
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyPortCargoMappingOrBuilder>
          getPortCargosOrBuilderList() {
        if (portCargosBuilder_ != null) {
          return portCargosBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(portCargos_);
        }
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyPortCargoMapping
              .Builder
          addPortCargosBuilder() {
        return getPortCargosFieldBuilder()
            .addBuilder(
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                    .DishargeStudyPortCargoMapping.getDefaultInstance());
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyPortCargoMapping
              .Builder
          addPortCargosBuilder(int index) {
        return getPortCargosFieldBuilder()
            .addBuilder(
                index,
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                    .DishargeStudyPortCargoMapping.getDefaultInstance());
      }
      /** <code>repeated .DishargeStudyPortCargoMapping portCargos = 2;</code> */
      public java.util.List<
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping
                  .Builder>
          getPortCargosBuilderList() {
        return getPortCargosFieldBuilder().getBuilderList();
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMapping
                  .Builder,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DishargeStudyPortCargoMappingOrBuilder>
          getPortCargosFieldBuilder() {
        if (portCargosBuilder_ == null) {
          portCargosBuilder_ =
              new com.google.protobuf.RepeatedFieldBuilderV3<
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyPortCargoMapping,
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyPortCargoMapping
                      .Builder,
                  com.cpdss
                      .common
                      .generated
                      .loadableStudy
                      .LoadableStudyModels
                      .DishargeStudyPortCargoMappingOrBuilder>(
                  portCargos_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
          portCargos_ = null;
        }
        return portCargosBuilder_;
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

      // @@protoc_insertion_point(builder_scope:DishargeStudyCargoReply)
    }

    // @@protoc_insertion_point(class_scope:DishargeStudyCargoReply)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyCargoReply();
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DishargeStudyCargoReply
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DishargeStudyCargoReply> PARSER =
        new com.google.protobuf.AbstractParser<DishargeStudyCargoReply>() {
          @java.lang.Override
          public DishargeStudyCargoReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new DishargeStudyCargoReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<DishargeStudyCargoReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DishargeStudyCargoReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface DischargeStudyJsonReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:DischargeStudyJsonReply)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string dischargeStudyJson = 1;</code>
     *
     * @return The dischargeStudyJson.
     */
    java.lang.String getDischargeStudyJson();
    /**
     * <code>string dischargeStudyJson = 1;</code>
     *
     * @return The bytes for dischargeStudyJson.
     */
    com.google.protobuf.ByteString getDischargeStudyJsonBytes();

    /**
     * <code>.ResponseStatus responseStatus = 2;</code>
     *
     * @return Whether the responseStatus field is set.
     */
    boolean hasResponseStatus();
    /**
     * <code>.ResponseStatus responseStatus = 2;</code>
     *
     * @return The responseStatus.
     */
    com.cpdss.common.generated.Common.ResponseStatus getResponseStatus();
    /** <code>.ResponseStatus responseStatus = 2;</code> */
    com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder();
  }
  /** Protobuf type {@code DischargeStudyJsonReply} */
  public static final class DischargeStudyJsonReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:DischargeStudyJsonReply)
      DischargeStudyJsonReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use DischargeStudyJsonReply.newBuilder() to construct.
    private DischargeStudyJsonReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private DischargeStudyJsonReply() {
      dischargeStudyJson_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new DischargeStudyJsonReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private DischargeStudyJsonReply(
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

                dischargeStudyJson_ = s;
                break;
              }
            case 18:
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
      return com.cpdss
          .common
          .generated
          .loadableStudy
          .LoadableStudyModels
          .internal_static_DischargeStudyJsonReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
          .internal_static_DischargeStudyJsonReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DischargeStudyJsonReply
                  .class,
              com.cpdss
                  .common
                  .generated
                  .loadableStudy
                  .LoadableStudyModels
                  .DischargeStudyJsonReply
                  .Builder
                  .class);
    }

    public static final int DISCHARGESTUDYJSON_FIELD_NUMBER = 1;
    private volatile java.lang.Object dischargeStudyJson_;
    /**
     * <code>string dischargeStudyJson = 1;</code>
     *
     * @return The dischargeStudyJson.
     */
    public java.lang.String getDischargeStudyJson() {
      java.lang.Object ref = dischargeStudyJson_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        dischargeStudyJson_ = s;
        return s;
      }
    }
    /**
     * <code>string dischargeStudyJson = 1;</code>
     *
     * @return The bytes for dischargeStudyJson.
     */
    public com.google.protobuf.ByteString getDischargeStudyJsonBytes() {
      java.lang.Object ref = dischargeStudyJson_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        dischargeStudyJson_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int RESPONSESTATUS_FIELD_NUMBER = 2;
    private com.cpdss.common.generated.Common.ResponseStatus responseStatus_;
    /**
     * <code>.ResponseStatus responseStatus = 2;</code>
     *
     * @return Whether the responseStatus field is set.
     */
    public boolean hasResponseStatus() {
      return responseStatus_ != null;
    }
    /**
     * <code>.ResponseStatus responseStatus = 2;</code>
     *
     * @return The responseStatus.
     */
    public com.cpdss.common.generated.Common.ResponseStatus getResponseStatus() {
      return responseStatus_ == null
          ? com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance()
          : responseStatus_;
    }
    /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      if (!getDischargeStudyJsonBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, dischargeStudyJson_);
      }
      if (responseStatus_ != null) {
        output.writeMessage(2, getResponseStatus());
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getDischargeStudyJsonBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, dischargeStudyJson_);
      }
      if (responseStatus_ != null) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getResponseStatus());
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
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply other =
          (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply)
              obj;

      if (!getDischargeStudyJson().equals(other.getDischargeStudyJson())) return false;
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
      hash = (37 * hash) + DISCHARGESTUDYJSON_FIELD_NUMBER;
      hash = (53 * hash) + getDischargeStudyJson().hashCode();
      if (hasResponseStatus()) {
        hash = (37 * hash) + RESPONSESTATUS_FIELD_NUMBER;
        hash = (53 * hash) + getResponseStatus().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
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
    /** Protobuf type {@code DischargeStudyJsonReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:DischargeStudyJsonReply)
        com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DischargeStudyJsonReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels
            .internal_static_DischargeStudyJsonReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DischargeStudyJsonReply
                    .class,
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DischargeStudyJsonReply
                    .Builder
                    .class);
      }

      // Construct using
      // com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply.newBuilder()
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
        dischargeStudyJson_ = "";

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
        return com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .internal_static_DischargeStudyJsonReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
          build() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
            result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
          buildPartial() {
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
            result =
                new com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DischargeStudyJsonReply(this);
        result.dischargeStudyJson_ = dischargeStudyJson_;
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
        if (other
            instanceof
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply) {
          return mergeFrom(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
              other) {
        if (other
            == com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
                .getDefaultInstance()) return this;
        if (!other.getDischargeStudyJson().isEmpty()) {
          dischargeStudyJson_ = other.dischargeStudyJson_;
          onChanged();
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
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object dischargeStudyJson_ = "";
      /**
       * <code>string dischargeStudyJson = 1;</code>
       *
       * @return The dischargeStudyJson.
       */
      public java.lang.String getDischargeStudyJson() {
        java.lang.Object ref = dischargeStudyJson_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          dischargeStudyJson_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string dischargeStudyJson = 1;</code>
       *
       * @return The bytes for dischargeStudyJson.
       */
      public com.google.protobuf.ByteString getDischargeStudyJsonBytes() {
        java.lang.Object ref = dischargeStudyJson_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          dischargeStudyJson_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string dischargeStudyJson = 1;</code>
       *
       * @param value The dischargeStudyJson to set.
       * @return This builder for chaining.
       */
      public Builder setDischargeStudyJson(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        dischargeStudyJson_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string dischargeStudyJson = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDischargeStudyJson() {

        dischargeStudyJson_ = getDefaultInstance().getDischargeStudyJson();
        onChanged();
        return this;
      }
      /**
       * <code>string dischargeStudyJson = 1;</code>
       *
       * @param value The bytes for dischargeStudyJson to set.
       * @return This builder for chaining.
       */
      public Builder setDischargeStudyJsonBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        dischargeStudyJson_ = value;
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
       * <code>.ResponseStatus responseStatus = 2;</code>
       *
       * @return Whether the responseStatus field is set.
       */
      public boolean hasResponseStatus() {
        return responseStatusBuilder_ != null || responseStatus_ != null;
      }
      /**
       * <code>.ResponseStatus responseStatus = 2;</code>
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
      public com.cpdss.common.generated.Common.ResponseStatus.Builder getResponseStatusBuilder() {

        onChanged();
        return getResponseStatusFieldBuilder().getBuilder();
      }
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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
      /** <code>.ResponseStatus responseStatus = 2;</code> */
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

      // @@protoc_insertion_point(builder_scope:DischargeStudyJsonReply)
    }

    // @@protoc_insertion_point(class_scope:DischargeStudyJsonReply)
    private static final com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DischargeStudyJsonReply();
    }

    public static com.cpdss
            .common
            .generated
            .loadableStudy
            .LoadableStudyModels
            .DischargeStudyJsonReply
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<DischargeStudyJsonReply> PARSER =
        new com.google.protobuf.AbstractParser<DischargeStudyJsonReply>() {
          @java.lang.Override
          public DischargeStudyJsonReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new DischargeStudyJsonReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<DischargeStudyJsonReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<DischargeStudyJsonReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyJsonReply
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeStudyRequest_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeStudyRequest_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeStudyDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeStudyDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_UpdateDischargeStudyReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_UpdateDischargeStudyReply_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_UpdateDischargeStudyDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_UpdateDischargeStudyDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeStudyReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeStudyReply_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_BackLoading_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_BackLoading_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_InstructionReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_InstructionReply_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_InstructionDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_InstructionDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_EmptyRequest_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_EmptyRequest_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DishargeStudyCargoDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DishargeStudyCargoDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DishargeStudyPortCargoMapping_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DishargeStudyPortCargoMapping_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DishargeStudyCargoReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DishargeStudyCargoReply_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeStudyJsonReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeStudyJsonReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n*loadable_study/loadable_study_models.p"
          + "roto\032\014common.proto\"1\n\025DischargeStudyRequ"
          + "est\022\030\n\020dischargeStudyId\030\001 \001(\003\"z\n\024Dischar"
          + "geStudyDetail\022\014\n\004name\030\001 \001(\t\022\026\n\016enquiryDe"
          + "tails\030\002 \001(\t\022\020\n\010vesselId\030\003 \001(\003\022\020\n\010voyageI"
          + "d\030\004 \001(\003\022\030\n\020dischargeStudyId\030\005 \001(\003\"y\n\031Upd"
          + "ateDischargeStudyReply\022\'\n\016responseStatus"
          + "\030\001 \001(\0132\017.ResponseStatus\0223\n\016dischargeStud"
          + "y\030\002 \001(\0132\033.UpdateDischargeStudyDetail\"N\n\032"
          + "UpdateDischargeStudyDetail\022\014\n\004name\030\001 \001(\t"
          + "\022\026\n\016enquiryDetails\030\002 \001(\t\022\n\n\002id\030\003 \001(\003\"y\n\023"
          + "DischargeStudyReply\022\'\n\016responseStatus\030\001 "
          + "\001(\0132\017.ResponseStatus\022-\n\016dischargeStudy\030\002"
          + " \001(\0132\025.DischargeStudyDetail\022\n\n\002id\030\003 \001(\003\""
          + "\204\001\n\013BackLoading\022\n\n\002id\030\001 \001(\003\022\016\n\006colour\030\002 "
          + "\001(\t\022\017\n\007cargoId\030\003 \001(\003\022\013\n\003api\030\004 \001(\t\022\023\n\013tem"
          + "perature\030\005 \001(\t\022\024\n\014abbreviation\030\006 \001(\t\022\020\n\010"
          + "quantity\030\007 \001(\t\"k\n\020InstructionReply\022.\n\022in"
          + "structionDetails\030\001 \003(\0132\022.InstructionDeta"
          + "il\022\'\n\016responseStatus\030\002 \001(\0132\017.ResponseSta"
          + "tus\"4\n\021InstructionDetail\022\n\n\002id\030\001 \001(\003\022\023\n\013"
          + "instruction\030\002 \001(\t\"\016\n\014EmptyRequest\"\214\001\n\030Di"
          + "shargeStudyCargoDetail\022\n\n\002id\030\001 \001(\003\022\021\n\tcr"
          + "udeType\030\002 \001(\t\022\024\n\014abbreviation\030\003 \001(\t\022\013\n\003a"
          + "pi\030\004 \001(\t\022\031\n\021isCondensateCargo\030\005 \001(\010\022\023\n\013i"
          + "sHrvpCargo\030\006 \001(\010\"Z\n\035DishargeStudyPortCar"
          + "goMapping\022\016\n\006portId\030\001 \001(\003\022)\n\006cargos\030\002 \003("
          + "\0132\031.DishargeStudyCargoDetail\"v\n\027Disharge"
          + "StudyCargoReply\022\'\n\016responseStatus\030\001 \001(\0132"
          + "\017.ResponseStatus\0222\n\nportCargos\030\002 \003(\0132\036.D"
          + "ishargeStudyPortCargoMapping\"^\n\027Discharg"
          + "eStudyJsonReply\022\032\n\022dischargeStudyJson\030\001 "
          + "\001(\t\022\'\n\016responseStatus\030\002 \001(\0132\017.ResponseSt"
          + "atusB,\n(com.cpdss.common.generated.loada"
          + "bleStudyP\000b\006proto3"
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
    internal_static_DischargeStudyDetail_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_DischargeStudyDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeStudyDetail_descriptor,
            new java.lang.String[] {
              "Name", "EnquiryDetails", "VesselId", "VoyageId", "DischargeStudyId",
            });
    internal_static_UpdateDischargeStudyReply_descriptor = getDescriptor().getMessageTypes().get(2);
    internal_static_UpdateDischargeStudyReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_UpdateDischargeStudyReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus", "DischargeStudy",
            });
    internal_static_UpdateDischargeStudyDetail_descriptor =
        getDescriptor().getMessageTypes().get(3);
    internal_static_UpdateDischargeStudyDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_UpdateDischargeStudyDetail_descriptor,
            new java.lang.String[] {
              "Name", "EnquiryDetails", "Id",
            });
    internal_static_DischargeStudyReply_descriptor = getDescriptor().getMessageTypes().get(4);
    internal_static_DischargeStudyReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeStudyReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus", "DischargeStudy", "Id",
            });
    internal_static_BackLoading_descriptor = getDescriptor().getMessageTypes().get(5);
    internal_static_BackLoading_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_BackLoading_descriptor,
            new java.lang.String[] {
              "Id", "Colour", "CargoId", "Api", "Temperature", "Abbreviation", "Quantity",
            });
    internal_static_InstructionReply_descriptor = getDescriptor().getMessageTypes().get(6);
    internal_static_InstructionReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_InstructionReply_descriptor,
            new java.lang.String[] {
              "InstructionDetails", "ResponseStatus",
            });
    internal_static_InstructionDetail_descriptor = getDescriptor().getMessageTypes().get(7);
    internal_static_InstructionDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_InstructionDetail_descriptor,
            new java.lang.String[] {
              "Id", "Instruction",
            });
    internal_static_EmptyRequest_descriptor = getDescriptor().getMessageTypes().get(8);
    internal_static_EmptyRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_EmptyRequest_descriptor, new java.lang.String[] {});
    internal_static_DishargeStudyCargoDetail_descriptor = getDescriptor().getMessageTypes().get(9);
    internal_static_DishargeStudyCargoDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DishargeStudyCargoDetail_descriptor,
            new java.lang.String[] {
              "Id", "CrudeType", "Abbreviation", "Api", "IsCondensateCargo", "IsHrvpCargo",
            });
    internal_static_DishargeStudyPortCargoMapping_descriptor =
        getDescriptor().getMessageTypes().get(10);
    internal_static_DishargeStudyPortCargoMapping_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DishargeStudyPortCargoMapping_descriptor,
            new java.lang.String[] {
              "PortId", "Cargos",
            });
    internal_static_DishargeStudyCargoReply_descriptor = getDescriptor().getMessageTypes().get(11);
    internal_static_DishargeStudyCargoReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DishargeStudyCargoReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus", "PortCargos",
            });
    internal_static_DischargeStudyJsonReply_descriptor = getDescriptor().getMessageTypes().get(12);
    internal_static_DischargeStudyJsonReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeStudyJsonReply_descriptor,
            new java.lang.String[] {
              "DischargeStudyJson", "ResponseStatus",
            });
    com.cpdss.common.generated.Common.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
