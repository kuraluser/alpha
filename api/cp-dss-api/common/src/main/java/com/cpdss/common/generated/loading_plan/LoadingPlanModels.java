/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.loading_plan;

public final class LoadingPlanModels {
  private LoadingPlanModels() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public interface LoadingPlanRequestOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadingPlanRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 companyId = 1;</code>
     *
     * @return The companyId.
     */
    long getCompanyId();

    /**
     * <code>int64 vesselId = 2;</code>
     *
     * @return The vesselId.
     */
    long getVesselId();

    /**
     * <code>int64 voyageId = 3;</code>
     *
     * @return The voyageId.
     */
    long getVoyageId();
  }
  /** Protobuf type {@code LoadingPlanRequest} */
  public static final class LoadingPlanRequest extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadingPlanRequest)
      LoadingPlanRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadingPlanRequest.newBuilder() to construct.
    private LoadingPlanRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadingPlanRequest() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadingPlanRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadingPlanRequest(
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
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadingPlanRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadingPlanRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest.class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest.Builder
                  .class);
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
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest) obj;

      if (getCompanyId() != other.getCompanyId()) return false;
      if (getVesselId() != other.getVesselId()) return false;
      if (getVoyageId() != other.getVoyageId()) return false;
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
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest prototype) {
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
    /** Protobuf type {@code LoadingPlanRequest} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadingPlanRequest)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest.class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest.Builder
                    .class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest.newBuilder()
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

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanRequest_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest result =
            new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest(this);
        result.companyId_ = companyId_;
        result.vesselId_ = vesselId_;
        result.voyageId_ = voyageId_;
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
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest parsedMessage =
            null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest)
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

      // @@protoc_insertion_point(builder_scope:LoadingPlanRequest)
    }

    // @@protoc_insertion_point(class_scope:LoadingPlanRequest)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingPlanRequest
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadingPlanRequest> PARSER =
        new com.google.protobuf.AbstractParser<LoadingPlanRequest>() {
          @java.lang.Override
          public LoadingPlanRequest parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadingPlanRequest(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadingPlanRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadingPlanRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRequest
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadingPlanResponseOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadingPlanResponse)
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
  /**
   *
   *
   * <pre>
   * Plane response, can edit
   * </pre>
   *
   * Protobuf type {@code LoadingPlanResponse}
   */
  public static final class LoadingPlanResponse extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadingPlanResponse)
      LoadingPlanResponseOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadingPlanResponse.newBuilder() to construct.
    private LoadingPlanResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadingPlanResponse() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadingPlanResponse();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadingPlanResponse(
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
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadingPlanResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadingPlanResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse.class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse.Builder
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
      if (!(obj
          instanceof
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse) obj;

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

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse prototype) {
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
    /**
     *
     *
     * <pre>
     * Plane response, can edit
     * </pre>
     *
     * Protobuf type {@code LoadingPlanResponse}
     */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadingPlanResponse)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponseOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanResponse_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse.class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
                    .Builder.class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse.newBuilder()
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
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanResponse_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse result =
            new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse(this);
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
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
                .getDefaultInstance()) return this;
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse)
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

      // @@protoc_insertion_point(builder_scope:LoadingPlanResponse)
    }

    // @@protoc_insertion_point(class_scope:LoadingPlanResponse)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingPlanResponse
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadingPlanResponse> PARSER =
        new com.google.protobuf.AbstractParser<LoadingPlanResponse>() {
          @java.lang.Override
          public LoadingPlanResponse parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadingPlanResponse(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadingPlanResponse> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadingPlanResponse> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanResponse
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadingInformationDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadingInformationDetail)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 loadablePatternId = 1;</code>
     *
     * @return The loadablePatternId.
     */
    long getLoadablePatternId();

    /**
     * <code>int64 portId = 2;</code>
     *
     * @return The portId.
     */
    long getPortId();

    /**
     * <code>int64 synopticalTableId = 3;</code>
     *
     * @return The synopticalTableId.
     */
    long getSynopticalTableId();

    /**
     * <code>int64 vesselId = 4;</code>
     *
     * @return The vesselId.
     */
    long getVesselId();
  }
  /** Protobuf type {@code LoadingInformationDetail} */
  public static final class LoadingInformationDetail extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadingInformationDetail)
      LoadingInformationDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadingInformationDetail.newBuilder() to construct.
    private LoadingInformationDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadingInformationDetail() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadingInformationDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadingInformationDetail(
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
                loadablePatternId_ = input.readInt64();
                break;
              }
            case 16:
              {
                portId_ = input.readInt64();
                break;
              }
            case 24:
              {
                synopticalTableId_ = input.readInt64();
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
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadingInformationDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadingInformationDetail_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                  .class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                  .Builder.class);
    }

    public static final int LOADABLEPATTERNID_FIELD_NUMBER = 1;
    private long loadablePatternId_;
    /**
     * <code>int64 loadablePatternId = 1;</code>
     *
     * @return The loadablePatternId.
     */
    public long getLoadablePatternId() {
      return loadablePatternId_;
    }

    public static final int PORTID_FIELD_NUMBER = 2;
    private long portId_;
    /**
     * <code>int64 portId = 2;</code>
     *
     * @return The portId.
     */
    public long getPortId() {
      return portId_;
    }

    public static final int SYNOPTICALTABLEID_FIELD_NUMBER = 3;
    private long synopticalTableId_;
    /**
     * <code>int64 synopticalTableId = 3;</code>
     *
     * @return The synopticalTableId.
     */
    public long getSynopticalTableId() {
      return synopticalTableId_;
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
      if (loadablePatternId_ != 0L) {
        output.writeInt64(1, loadablePatternId_);
      }
      if (portId_ != 0L) {
        output.writeInt64(2, portId_);
      }
      if (synopticalTableId_ != 0L) {
        output.writeInt64(3, synopticalTableId_);
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
      if (loadablePatternId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, loadablePatternId_);
      }
      if (portId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, portId_);
      }
      if (synopticalTableId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, synopticalTableId_);
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
          instanceof
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail) obj;

      if (getLoadablePatternId() != other.getLoadablePatternId()) return false;
      if (getPortId() != other.getPortId()) return false;
      if (getSynopticalTableId() != other.getSynopticalTableId()) return false;
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
      hash = (37 * hash) + LOADABLEPATTERNID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadablePatternId());
      hash = (37 * hash) + PORTID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortId());
      hash = (37 * hash) + SYNOPTICALTABLEID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getSynopticalTableId());
      hash = (37 * hash) + VESSELID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVesselId());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
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
    /** Protobuf type {@code LoadingInformationDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadingInformationDetail)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingInformationDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingInformationDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingInformationDetail_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                    .class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                    .Builder.class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail.newBuilder()
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
        loadablePatternId_ = 0L;

        portId_ = 0L;

        synopticalTableId_ = 0L;

        vesselId_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingInformationDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
          build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail result =
            new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail(
                this);
        result.loadablePatternId_ = loadablePatternId_;
        result.portId_ = portId_;
        result.synopticalTableId_ = synopticalTableId_;
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
        if (other
            instanceof
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
              other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                .getDefaultInstance()) return this;
        if (other.getLoadablePatternId() != 0L) {
          setLoadablePatternId(other.getLoadablePatternId());
        }
        if (other.getPortId() != 0L) {
          setPortId(other.getPortId());
        }
        if (other.getSynopticalTableId() != 0L) {
          setSynopticalTableId(other.getSynopticalTableId());
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long loadablePatternId_;
      /**
       * <code>int64 loadablePatternId = 1;</code>
       *
       * @return The loadablePatternId.
       */
      public long getLoadablePatternId() {
        return loadablePatternId_;
      }
      /**
       * <code>int64 loadablePatternId = 1;</code>
       *
       * @param value The loadablePatternId to set.
       * @return This builder for chaining.
       */
      public Builder setLoadablePatternId(long value) {

        loadablePatternId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 loadablePatternId = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadablePatternId() {

        loadablePatternId_ = 0L;
        onChanged();
        return this;
      }

      private long portId_;
      /**
       * <code>int64 portId = 2;</code>
       *
       * @return The portId.
       */
      public long getPortId() {
        return portId_;
      }
      /**
       * <code>int64 portId = 2;</code>
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
       * <code>int64 portId = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPortId() {

        portId_ = 0L;
        onChanged();
        return this;
      }

      private long synopticalTableId_;
      /**
       * <code>int64 synopticalTableId = 3;</code>
       *
       * @return The synopticalTableId.
       */
      public long getSynopticalTableId() {
        return synopticalTableId_;
      }
      /**
       * <code>int64 synopticalTableId = 3;</code>
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
       * <code>int64 synopticalTableId = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSynopticalTableId() {

        synopticalTableId_ = 0L;
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
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }

      // @@protoc_insertion_point(builder_scope:LoadingInformationDetail)
    }

    // @@protoc_insertion_point(class_scope:LoadingInformationDetail)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingInformationDetail
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadingInformationDetail> PARSER =
        new com.google.protobuf.AbstractParser<LoadingInformationDetail>() {
          @java.lang.Override
          public LoadingInformationDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadingInformationDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadingInformationDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadingInformationDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadablePlanBallastDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadablePlanBallastDetail)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>string colorCode = 2;</code>
     *
     * @return The colorCode.
     */
    java.lang.String getColorCode();
    /**
     * <code>string colorCode = 2;</code>
     *
     * @return The bytes for colorCode.
     */
    com.google.protobuf.ByteString getColorCodeBytes();

    /**
     * <code>string correctedLevel = 3;</code>
     *
     * @return The correctedLevel.
     */
    java.lang.String getCorrectedLevel();
    /**
     * <code>string correctedLevel = 3;</code>
     *
     * @return The bytes for correctedLevel.
     */
    com.google.protobuf.ByteString getCorrectedLevelBytes();

    /**
     * <code>string correctionFactor = 4;</code>
     *
     * @return The correctionFactor.
     */
    java.lang.String getCorrectionFactor();
    /**
     * <code>string correctionFactor = 4;</code>
     *
     * @return The bytes for correctionFactor.
     */
    com.google.protobuf.ByteString getCorrectionFactorBytes();

    /**
     * <code>string cubicMeter = 5;</code>
     *
     * @return The cubicMeter.
     */
    java.lang.String getCubicMeter();
    /**
     * <code>string cubicMeter = 5;</code>
     *
     * @return The bytes for cubicMeter.
     */
    com.google.protobuf.ByteString getCubicMeterBytes();

    /**
     * <code>string inertia = 6;</code>
     *
     * @return The inertia.
     */
    java.lang.String getInertia();
    /**
     * <code>string inertia = 6;</code>
     *
     * @return The bytes for inertia.
     */
    com.google.protobuf.ByteString getInertiaBytes();

    /**
     * <code>bool isActive = 7;</code>
     *
     * @return The isActive.
     */
    boolean getIsActive();

    /**
     * <code>string lcg = 8;</code>
     *
     * @return The lcg.
     */
    java.lang.String getLcg();
    /**
     * <code>string lcg = 8;</code>
     *
     * @return The bytes for lcg.
     */
    com.google.protobuf.ByteString getLcgBytes();

    /**
     * <code>int64 loadablePatternId = 9;</code>
     *
     * @return The loadablePatternId.
     */
    long getLoadablePatternId();

    /**
     * <code>string metricTon = 10;</code>
     *
     * @return The metricTon.
     */
    java.lang.String getMetricTon();
    /**
     * <code>string metricTon = 10;</code>
     *
     * @return The bytes for metricTon.
     */
    com.google.protobuf.ByteString getMetricTonBytes();

    /**
     * <code>string percentage = 11;</code>
     *
     * @return The percentage.
     */
    java.lang.String getPercentage();
    /**
     * <code>string percentage = 11;</code>
     *
     * @return The bytes for percentage.
     */
    com.google.protobuf.ByteString getPercentageBytes();

    /**
     * <code>string rdgLevel = 12;</code>
     *
     * @return The rdgLevel.
     */
    java.lang.String getRdgLevel();
    /**
     * <code>string rdgLevel = 12;</code>
     *
     * @return The bytes for rdgLevel.
     */
    com.google.protobuf.ByteString getRdgLevelBytes();

    /**
     * <code>string sg = 13;</code>
     *
     * @return The sg.
     */
    java.lang.String getSg();
    /**
     * <code>string sg = 13;</code>
     *
     * @return The bytes for sg.
     */
    com.google.protobuf.ByteString getSgBytes();

    /**
     * <code>int64 tankId = 14;</code>
     *
     * @return The tankId.
     */
    long getTankId();

    /**
     * <code>string tankName = 15;</code>
     *
     * @return The tankName.
     */
    java.lang.String getTankName();
    /**
     * <code>string tankName = 15;</code>
     *
     * @return The bytes for tankName.
     */
    com.google.protobuf.ByteString getTankNameBytes();

    /**
     * <code>string tcg = 16;</code>
     *
     * @return The tcg.
     */
    java.lang.String getTcg();
    /**
     * <code>string tcg = 16;</code>
     *
     * @return The bytes for tcg.
     */
    com.google.protobuf.ByteString getTcgBytes();

    /**
     * <code>string vcg = 17;</code>
     *
     * @return The vcg.
     */
    java.lang.String getVcg();
    /**
     * <code>string vcg = 17;</code>
     *
     * @return The bytes for vcg.
     */
    com.google.protobuf.ByteString getVcgBytes();
  }
  /** Protobuf type {@code LoadablePlanBallastDetail} */
  public static final class LoadablePlanBallastDetail extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadablePlanBallastDetail)
      LoadablePlanBallastDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadablePlanBallastDetail.newBuilder() to construct.
    private LoadablePlanBallastDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadablePlanBallastDetail() {
      colorCode_ = "";
      correctedLevel_ = "";
      correctionFactor_ = "";
      cubicMeter_ = "";
      inertia_ = "";
      lcg_ = "";
      metricTon_ = "";
      percentage_ = "";
      rdgLevel_ = "";
      sg_ = "";
      tankName_ = "";
      tcg_ = "";
      vcg_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadablePlanBallastDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadablePlanBallastDetail(
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

                colorCode_ = s;
                break;
              }
            case 26:
              {
                java.lang.String s = input.readStringRequireUtf8();

                correctedLevel_ = s;
                break;
              }
            case 34:
              {
                java.lang.String s = input.readStringRequireUtf8();

                correctionFactor_ = s;
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cubicMeter_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                inertia_ = s;
                break;
              }
            case 56:
              {
                isActive_ = input.readBool();
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                lcg_ = s;
                break;
              }
            case 72:
              {
                loadablePatternId_ = input.readInt64();
                break;
              }
            case 82:
              {
                java.lang.String s = input.readStringRequireUtf8();

                metricTon_ = s;
                break;
              }
            case 90:
              {
                java.lang.String s = input.readStringRequireUtf8();

                percentage_ = s;
                break;
              }
            case 98:
              {
                java.lang.String s = input.readStringRequireUtf8();

                rdgLevel_ = s;
                break;
              }
            case 106:
              {
                java.lang.String s = input.readStringRequireUtf8();

                sg_ = s;
                break;
              }
            case 112:
              {
                tankId_ = input.readInt64();
                break;
              }
            case 122:
              {
                java.lang.String s = input.readStringRequireUtf8();

                tankName_ = s;
                break;
              }
            case 130:
              {
                java.lang.String s = input.readStringRequireUtf8();

                tcg_ = s;
                break;
              }
            case 138:
              {
                java.lang.String s = input.readStringRequireUtf8();

                vcg_ = s;
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
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadablePlanBallastDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadablePlanBallastDetail_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
                  .class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
                  .Builder.class);
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

    public static final int COLORCODE_FIELD_NUMBER = 2;
    private volatile java.lang.Object colorCode_;
    /**
     * <code>string colorCode = 2;</code>
     *
     * @return The colorCode.
     */
    public java.lang.String getColorCode() {
      java.lang.Object ref = colorCode_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        colorCode_ = s;
        return s;
      }
    }
    /**
     * <code>string colorCode = 2;</code>
     *
     * @return The bytes for colorCode.
     */
    public com.google.protobuf.ByteString getColorCodeBytes() {
      java.lang.Object ref = colorCode_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        colorCode_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CORRECTEDLEVEL_FIELD_NUMBER = 3;
    private volatile java.lang.Object correctedLevel_;
    /**
     * <code>string correctedLevel = 3;</code>
     *
     * @return The correctedLevel.
     */
    public java.lang.String getCorrectedLevel() {
      java.lang.Object ref = correctedLevel_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        correctedLevel_ = s;
        return s;
      }
    }
    /**
     * <code>string correctedLevel = 3;</code>
     *
     * @return The bytes for correctedLevel.
     */
    public com.google.protobuf.ByteString getCorrectedLevelBytes() {
      java.lang.Object ref = correctedLevel_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        correctedLevel_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CORRECTIONFACTOR_FIELD_NUMBER = 4;
    private volatile java.lang.Object correctionFactor_;
    /**
     * <code>string correctionFactor = 4;</code>
     *
     * @return The correctionFactor.
     */
    public java.lang.String getCorrectionFactor() {
      java.lang.Object ref = correctionFactor_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        correctionFactor_ = s;
        return s;
      }
    }
    /**
     * <code>string correctionFactor = 4;</code>
     *
     * @return The bytes for correctionFactor.
     */
    public com.google.protobuf.ByteString getCorrectionFactorBytes() {
      java.lang.Object ref = correctionFactor_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        correctionFactor_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CUBICMETER_FIELD_NUMBER = 5;
    private volatile java.lang.Object cubicMeter_;
    /**
     * <code>string cubicMeter = 5;</code>
     *
     * @return The cubicMeter.
     */
    public java.lang.String getCubicMeter() {
      java.lang.Object ref = cubicMeter_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cubicMeter_ = s;
        return s;
      }
    }
    /**
     * <code>string cubicMeter = 5;</code>
     *
     * @return The bytes for cubicMeter.
     */
    public com.google.protobuf.ByteString getCubicMeterBytes() {
      java.lang.Object ref = cubicMeter_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cubicMeter_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int INERTIA_FIELD_NUMBER = 6;
    private volatile java.lang.Object inertia_;
    /**
     * <code>string inertia = 6;</code>
     *
     * @return The inertia.
     */
    public java.lang.String getInertia() {
      java.lang.Object ref = inertia_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        inertia_ = s;
        return s;
      }
    }
    /**
     * <code>string inertia = 6;</code>
     *
     * @return The bytes for inertia.
     */
    public com.google.protobuf.ByteString getInertiaBytes() {
      java.lang.Object ref = inertia_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        inertia_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ISACTIVE_FIELD_NUMBER = 7;
    private boolean isActive_;
    /**
     * <code>bool isActive = 7;</code>
     *
     * @return The isActive.
     */
    public boolean getIsActive() {
      return isActive_;
    }

    public static final int LCG_FIELD_NUMBER = 8;
    private volatile java.lang.Object lcg_;
    /**
     * <code>string lcg = 8;</code>
     *
     * @return The lcg.
     */
    public java.lang.String getLcg() {
      java.lang.Object ref = lcg_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        lcg_ = s;
        return s;
      }
    }
    /**
     * <code>string lcg = 8;</code>
     *
     * @return The bytes for lcg.
     */
    public com.google.protobuf.ByteString getLcgBytes() {
      java.lang.Object ref = lcg_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        lcg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOADABLEPATTERNID_FIELD_NUMBER = 9;
    private long loadablePatternId_;
    /**
     * <code>int64 loadablePatternId = 9;</code>
     *
     * @return The loadablePatternId.
     */
    public long getLoadablePatternId() {
      return loadablePatternId_;
    }

    public static final int METRICTON_FIELD_NUMBER = 10;
    private volatile java.lang.Object metricTon_;
    /**
     * <code>string metricTon = 10;</code>
     *
     * @return The metricTon.
     */
    public java.lang.String getMetricTon() {
      java.lang.Object ref = metricTon_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        metricTon_ = s;
        return s;
      }
    }
    /**
     * <code>string metricTon = 10;</code>
     *
     * @return The bytes for metricTon.
     */
    public com.google.protobuf.ByteString getMetricTonBytes() {
      java.lang.Object ref = metricTon_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        metricTon_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PERCENTAGE_FIELD_NUMBER = 11;
    private volatile java.lang.Object percentage_;
    /**
     * <code>string percentage = 11;</code>
     *
     * @return The percentage.
     */
    public java.lang.String getPercentage() {
      java.lang.Object ref = percentage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        percentage_ = s;
        return s;
      }
    }
    /**
     * <code>string percentage = 11;</code>
     *
     * @return The bytes for percentage.
     */
    public com.google.protobuf.ByteString getPercentageBytes() {
      java.lang.Object ref = percentage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        percentage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int RDGLEVEL_FIELD_NUMBER = 12;
    private volatile java.lang.Object rdgLevel_;
    /**
     * <code>string rdgLevel = 12;</code>
     *
     * @return The rdgLevel.
     */
    public java.lang.String getRdgLevel() {
      java.lang.Object ref = rdgLevel_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        rdgLevel_ = s;
        return s;
      }
    }
    /**
     * <code>string rdgLevel = 12;</code>
     *
     * @return The bytes for rdgLevel.
     */
    public com.google.protobuf.ByteString getRdgLevelBytes() {
      java.lang.Object ref = rdgLevel_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        rdgLevel_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SG_FIELD_NUMBER = 13;
    private volatile java.lang.Object sg_;
    /**
     * <code>string sg = 13;</code>
     *
     * @return The sg.
     */
    public java.lang.String getSg() {
      java.lang.Object ref = sg_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        sg_ = s;
        return s;
      }
    }
    /**
     * <code>string sg = 13;</code>
     *
     * @return The bytes for sg.
     */
    public com.google.protobuf.ByteString getSgBytes() {
      java.lang.Object ref = sg_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        sg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TANKID_FIELD_NUMBER = 14;
    private long tankId_;
    /**
     * <code>int64 tankId = 14;</code>
     *
     * @return The tankId.
     */
    public long getTankId() {
      return tankId_;
    }

    public static final int TANKNAME_FIELD_NUMBER = 15;
    private volatile java.lang.Object tankName_;
    /**
     * <code>string tankName = 15;</code>
     *
     * @return The tankName.
     */
    public java.lang.String getTankName() {
      java.lang.Object ref = tankName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tankName_ = s;
        return s;
      }
    }
    /**
     * <code>string tankName = 15;</code>
     *
     * @return The bytes for tankName.
     */
    public com.google.protobuf.ByteString getTankNameBytes() {
      java.lang.Object ref = tankName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        tankName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TCG_FIELD_NUMBER = 16;
    private volatile java.lang.Object tcg_;
    /**
     * <code>string tcg = 16;</code>
     *
     * @return The tcg.
     */
    public java.lang.String getTcg() {
      java.lang.Object ref = tcg_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tcg_ = s;
        return s;
      }
    }
    /**
     * <code>string tcg = 16;</code>
     *
     * @return The bytes for tcg.
     */
    public com.google.protobuf.ByteString getTcgBytes() {
      java.lang.Object ref = tcg_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        tcg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int VCG_FIELD_NUMBER = 17;
    private volatile java.lang.Object vcg_;
    /**
     * <code>string vcg = 17;</code>
     *
     * @return The vcg.
     */
    public java.lang.String getVcg() {
      java.lang.Object ref = vcg_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        vcg_ = s;
        return s;
      }
    }
    /**
     * <code>string vcg = 17;</code>
     *
     * @return The bytes for vcg.
     */
    public com.google.protobuf.ByteString getVcgBytes() {
      java.lang.Object ref = vcg_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        vcg_ = b;
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
      if (!getColorCodeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, colorCode_);
      }
      if (!getCorrectedLevelBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, correctedLevel_);
      }
      if (!getCorrectionFactorBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, correctionFactor_);
      }
      if (!getCubicMeterBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, cubicMeter_);
      }
      if (!getInertiaBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, inertia_);
      }
      if (isActive_ != false) {
        output.writeBool(7, isActive_);
      }
      if (!getLcgBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, lcg_);
      }
      if (loadablePatternId_ != 0L) {
        output.writeInt64(9, loadablePatternId_);
      }
      if (!getMetricTonBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 10, metricTon_);
      }
      if (!getPercentageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 11, percentage_);
      }
      if (!getRdgLevelBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 12, rdgLevel_);
      }
      if (!getSgBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 13, sg_);
      }
      if (tankId_ != 0L) {
        output.writeInt64(14, tankId_);
      }
      if (!getTankNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 15, tankName_);
      }
      if (!getTcgBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 16, tcg_);
      }
      if (!getVcgBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 17, vcg_);
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
      if (!getColorCodeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, colorCode_);
      }
      if (!getCorrectedLevelBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, correctedLevel_);
      }
      if (!getCorrectionFactorBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, correctionFactor_);
      }
      if (!getCubicMeterBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, cubicMeter_);
      }
      if (!getInertiaBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, inertia_);
      }
      if (isActive_ != false) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(7, isActive_);
      }
      if (!getLcgBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, lcg_);
      }
      if (loadablePatternId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(9, loadablePatternId_);
      }
      if (!getMetricTonBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(10, metricTon_);
      }
      if (!getPercentageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(11, percentage_);
      }
      if (!getRdgLevelBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(12, rdgLevel_);
      }
      if (!getSgBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(13, sg_);
      }
      if (tankId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(14, tankId_);
      }
      if (!getTankNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(15, tankName_);
      }
      if (!getTcgBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(16, tcg_);
      }
      if (!getVcgBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(17, vcg_);
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
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail) obj;

      if (getId() != other.getId()) return false;
      if (!getColorCode().equals(other.getColorCode())) return false;
      if (!getCorrectedLevel().equals(other.getCorrectedLevel())) return false;
      if (!getCorrectionFactor().equals(other.getCorrectionFactor())) return false;
      if (!getCubicMeter().equals(other.getCubicMeter())) return false;
      if (!getInertia().equals(other.getInertia())) return false;
      if (getIsActive() != other.getIsActive()) return false;
      if (!getLcg().equals(other.getLcg())) return false;
      if (getLoadablePatternId() != other.getLoadablePatternId()) return false;
      if (!getMetricTon().equals(other.getMetricTon())) return false;
      if (!getPercentage().equals(other.getPercentage())) return false;
      if (!getRdgLevel().equals(other.getRdgLevel())) return false;
      if (!getSg().equals(other.getSg())) return false;
      if (getTankId() != other.getTankId()) return false;
      if (!getTankName().equals(other.getTankName())) return false;
      if (!getTcg().equals(other.getTcg())) return false;
      if (!getVcg().equals(other.getVcg())) return false;
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
      hash = (37 * hash) + COLORCODE_FIELD_NUMBER;
      hash = (53 * hash) + getColorCode().hashCode();
      hash = (37 * hash) + CORRECTEDLEVEL_FIELD_NUMBER;
      hash = (53 * hash) + getCorrectedLevel().hashCode();
      hash = (37 * hash) + CORRECTIONFACTOR_FIELD_NUMBER;
      hash = (53 * hash) + getCorrectionFactor().hashCode();
      hash = (37 * hash) + CUBICMETER_FIELD_NUMBER;
      hash = (53 * hash) + getCubicMeter().hashCode();
      hash = (37 * hash) + INERTIA_FIELD_NUMBER;
      hash = (53 * hash) + getInertia().hashCode();
      hash = (37 * hash) + ISACTIVE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsActive());
      hash = (37 * hash) + LCG_FIELD_NUMBER;
      hash = (53 * hash) + getLcg().hashCode();
      hash = (37 * hash) + LOADABLEPATTERNID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadablePatternId());
      hash = (37 * hash) + METRICTON_FIELD_NUMBER;
      hash = (53 * hash) + getMetricTon().hashCode();
      hash = (37 * hash) + PERCENTAGE_FIELD_NUMBER;
      hash = (53 * hash) + getPercentage().hashCode();
      hash = (37 * hash) + RDGLEVEL_FIELD_NUMBER;
      hash = (53 * hash) + getRdgLevel().hashCode();
      hash = (37 * hash) + SG_FIELD_NUMBER;
      hash = (53 * hash) + getSg().hashCode();
      hash = (37 * hash) + TANKID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getTankId());
      hash = (37 * hash) + TANKNAME_FIELD_NUMBER;
      hash = (53 * hash) + getTankName().hashCode();
      hash = (37 * hash) + TCG_FIELD_NUMBER;
      hash = (53 * hash) + getTcg().hashCode();
      hash = (37 * hash) + VCG_FIELD_NUMBER;
      hash = (53 * hash) + getVcg().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
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
    /** Protobuf type {@code LoadablePlanBallastDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadablePlanBallastDetail)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanBallastDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanBallastDetail_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
                    .class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
                    .Builder.class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail.newBuilder()
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

        colorCode_ = "";

        correctedLevel_ = "";

        correctionFactor_ = "";

        cubicMeter_ = "";

        inertia_ = "";

        isActive_ = false;

        lcg_ = "";

        loadablePatternId_ = 0L;

        metricTon_ = "";

        percentage_ = "";

        rdgLevel_ = "";

        sg_ = "";

        tankId_ = 0L;

        tankName_ = "";

        tcg_ = "";

        vcg_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanBallastDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
          build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail result =
            new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail(
                this);
        result.id_ = id_;
        result.colorCode_ = colorCode_;
        result.correctedLevel_ = correctedLevel_;
        result.correctionFactor_ = correctionFactor_;
        result.cubicMeter_ = cubicMeter_;
        result.inertia_ = inertia_;
        result.isActive_ = isActive_;
        result.lcg_ = lcg_;
        result.loadablePatternId_ = loadablePatternId_;
        result.metricTon_ = metricTon_;
        result.percentage_ = percentage_;
        result.rdgLevel_ = rdgLevel_;
        result.sg_ = sg_;
        result.tankId_ = tankId_;
        result.tankName_ = tankName_;
        result.tcg_ = tcg_;
        result.vcg_ = vcg_;
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
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
              other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
                .getDefaultInstance()) return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (!other.getColorCode().isEmpty()) {
          colorCode_ = other.colorCode_;
          onChanged();
        }
        if (!other.getCorrectedLevel().isEmpty()) {
          correctedLevel_ = other.correctedLevel_;
          onChanged();
        }
        if (!other.getCorrectionFactor().isEmpty()) {
          correctionFactor_ = other.correctionFactor_;
          onChanged();
        }
        if (!other.getCubicMeter().isEmpty()) {
          cubicMeter_ = other.cubicMeter_;
          onChanged();
        }
        if (!other.getInertia().isEmpty()) {
          inertia_ = other.inertia_;
          onChanged();
        }
        if (other.getIsActive() != false) {
          setIsActive(other.getIsActive());
        }
        if (!other.getLcg().isEmpty()) {
          lcg_ = other.lcg_;
          onChanged();
        }
        if (other.getLoadablePatternId() != 0L) {
          setLoadablePatternId(other.getLoadablePatternId());
        }
        if (!other.getMetricTon().isEmpty()) {
          metricTon_ = other.metricTon_;
          onChanged();
        }
        if (!other.getPercentage().isEmpty()) {
          percentage_ = other.percentage_;
          onChanged();
        }
        if (!other.getRdgLevel().isEmpty()) {
          rdgLevel_ = other.rdgLevel_;
          onChanged();
        }
        if (!other.getSg().isEmpty()) {
          sg_ = other.sg_;
          onChanged();
        }
        if (other.getTankId() != 0L) {
          setTankId(other.getTankId());
        }
        if (!other.getTankName().isEmpty()) {
          tankName_ = other.tankName_;
          onChanged();
        }
        if (!other.getTcg().isEmpty()) {
          tcg_ = other.tcg_;
          onChanged();
        }
        if (!other.getVcg().isEmpty()) {
          vcg_ = other.vcg_;
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail)
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

      private java.lang.Object colorCode_ = "";
      /**
       * <code>string colorCode = 2;</code>
       *
       * @return The colorCode.
       */
      public java.lang.String getColorCode() {
        java.lang.Object ref = colorCode_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          colorCode_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string colorCode = 2;</code>
       *
       * @return The bytes for colorCode.
       */
      public com.google.protobuf.ByteString getColorCodeBytes() {
        java.lang.Object ref = colorCode_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          colorCode_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string colorCode = 2;</code>
       *
       * @param value The colorCode to set.
       * @return This builder for chaining.
       */
      public Builder setColorCode(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        colorCode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string colorCode = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearColorCode() {

        colorCode_ = getDefaultInstance().getColorCode();
        onChanged();
        return this;
      }
      /**
       * <code>string colorCode = 2;</code>
       *
       * @param value The bytes for colorCode to set.
       * @return This builder for chaining.
       */
      public Builder setColorCodeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        colorCode_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object correctedLevel_ = "";
      /**
       * <code>string correctedLevel = 3;</code>
       *
       * @return The correctedLevel.
       */
      public java.lang.String getCorrectedLevel() {
        java.lang.Object ref = correctedLevel_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          correctedLevel_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string correctedLevel = 3;</code>
       *
       * @return The bytes for correctedLevel.
       */
      public com.google.protobuf.ByteString getCorrectedLevelBytes() {
        java.lang.Object ref = correctedLevel_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          correctedLevel_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string correctedLevel = 3;</code>
       *
       * @param value The correctedLevel to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectedLevel(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        correctedLevel_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string correctedLevel = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCorrectedLevel() {

        correctedLevel_ = getDefaultInstance().getCorrectedLevel();
        onChanged();
        return this;
      }
      /**
       * <code>string correctedLevel = 3;</code>
       *
       * @param value The bytes for correctedLevel to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectedLevelBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        correctedLevel_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object correctionFactor_ = "";
      /**
       * <code>string correctionFactor = 4;</code>
       *
       * @return The correctionFactor.
       */
      public java.lang.String getCorrectionFactor() {
        java.lang.Object ref = correctionFactor_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          correctionFactor_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string correctionFactor = 4;</code>
       *
       * @return The bytes for correctionFactor.
       */
      public com.google.protobuf.ByteString getCorrectionFactorBytes() {
        java.lang.Object ref = correctionFactor_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          correctionFactor_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string correctionFactor = 4;</code>
       *
       * @param value The correctionFactor to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectionFactor(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        correctionFactor_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string correctionFactor = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCorrectionFactor() {

        correctionFactor_ = getDefaultInstance().getCorrectionFactor();
        onChanged();
        return this;
      }
      /**
       * <code>string correctionFactor = 4;</code>
       *
       * @param value The bytes for correctionFactor to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectionFactorBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        correctionFactor_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cubicMeter_ = "";
      /**
       * <code>string cubicMeter = 5;</code>
       *
       * @return The cubicMeter.
       */
      public java.lang.String getCubicMeter() {
        java.lang.Object ref = cubicMeter_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cubicMeter_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cubicMeter = 5;</code>
       *
       * @return The bytes for cubicMeter.
       */
      public com.google.protobuf.ByteString getCubicMeterBytes() {
        java.lang.Object ref = cubicMeter_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cubicMeter_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cubicMeter = 5;</code>
       *
       * @param value The cubicMeter to set.
       * @return This builder for chaining.
       */
      public Builder setCubicMeter(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cubicMeter_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cubicMeter = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCubicMeter() {

        cubicMeter_ = getDefaultInstance().getCubicMeter();
        onChanged();
        return this;
      }
      /**
       * <code>string cubicMeter = 5;</code>
       *
       * @param value The bytes for cubicMeter to set.
       * @return This builder for chaining.
       */
      public Builder setCubicMeterBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cubicMeter_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object inertia_ = "";
      /**
       * <code>string inertia = 6;</code>
       *
       * @return The inertia.
       */
      public java.lang.String getInertia() {
        java.lang.Object ref = inertia_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          inertia_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string inertia = 6;</code>
       *
       * @return The bytes for inertia.
       */
      public com.google.protobuf.ByteString getInertiaBytes() {
        java.lang.Object ref = inertia_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          inertia_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string inertia = 6;</code>
       *
       * @param value The inertia to set.
       * @return This builder for chaining.
       */
      public Builder setInertia(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        inertia_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string inertia = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearInertia() {

        inertia_ = getDefaultInstance().getInertia();
        onChanged();
        return this;
      }
      /**
       * <code>string inertia = 6;</code>
       *
       * @param value The bytes for inertia to set.
       * @return This builder for chaining.
       */
      public Builder setInertiaBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        inertia_ = value;
        onChanged();
        return this;
      }

      private boolean isActive_;
      /**
       * <code>bool isActive = 7;</code>
       *
       * @return The isActive.
       */
      public boolean getIsActive() {
        return isActive_;
      }
      /**
       * <code>bool isActive = 7;</code>
       *
       * @param value The isActive to set.
       * @return This builder for chaining.
       */
      public Builder setIsActive(boolean value) {

        isActive_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool isActive = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearIsActive() {

        isActive_ = false;
        onChanged();
        return this;
      }

      private java.lang.Object lcg_ = "";
      /**
       * <code>string lcg = 8;</code>
       *
       * @return The lcg.
       */
      public java.lang.String getLcg() {
        java.lang.Object ref = lcg_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          lcg_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string lcg = 8;</code>
       *
       * @return The bytes for lcg.
       */
      public com.google.protobuf.ByteString getLcgBytes() {
        java.lang.Object ref = lcg_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          lcg_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string lcg = 8;</code>
       *
       * @param value The lcg to set.
       * @return This builder for chaining.
       */
      public Builder setLcg(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        lcg_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string lcg = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLcg() {

        lcg_ = getDefaultInstance().getLcg();
        onChanged();
        return this;
      }
      /**
       * <code>string lcg = 8;</code>
       *
       * @param value The bytes for lcg to set.
       * @return This builder for chaining.
       */
      public Builder setLcgBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        lcg_ = value;
        onChanged();
        return this;
      }

      private long loadablePatternId_;
      /**
       * <code>int64 loadablePatternId = 9;</code>
       *
       * @return The loadablePatternId.
       */
      public long getLoadablePatternId() {
        return loadablePatternId_;
      }
      /**
       * <code>int64 loadablePatternId = 9;</code>
       *
       * @param value The loadablePatternId to set.
       * @return This builder for chaining.
       */
      public Builder setLoadablePatternId(long value) {

        loadablePatternId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 loadablePatternId = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadablePatternId() {

        loadablePatternId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object metricTon_ = "";
      /**
       * <code>string metricTon = 10;</code>
       *
       * @return The metricTon.
       */
      public java.lang.String getMetricTon() {
        java.lang.Object ref = metricTon_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          metricTon_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string metricTon = 10;</code>
       *
       * @return The bytes for metricTon.
       */
      public com.google.protobuf.ByteString getMetricTonBytes() {
        java.lang.Object ref = metricTon_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          metricTon_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string metricTon = 10;</code>
       *
       * @param value The metricTon to set.
       * @return This builder for chaining.
       */
      public Builder setMetricTon(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        metricTon_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string metricTon = 10;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearMetricTon() {

        metricTon_ = getDefaultInstance().getMetricTon();
        onChanged();
        return this;
      }
      /**
       * <code>string metricTon = 10;</code>
       *
       * @param value The bytes for metricTon to set.
       * @return This builder for chaining.
       */
      public Builder setMetricTonBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        metricTon_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object percentage_ = "";
      /**
       * <code>string percentage = 11;</code>
       *
       * @return The percentage.
       */
      public java.lang.String getPercentage() {
        java.lang.Object ref = percentage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          percentage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string percentage = 11;</code>
       *
       * @return The bytes for percentage.
       */
      public com.google.protobuf.ByteString getPercentageBytes() {
        java.lang.Object ref = percentage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          percentage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string percentage = 11;</code>
       *
       * @param value The percentage to set.
       * @return This builder for chaining.
       */
      public Builder setPercentage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        percentage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string percentage = 11;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPercentage() {

        percentage_ = getDefaultInstance().getPercentage();
        onChanged();
        return this;
      }
      /**
       * <code>string percentage = 11;</code>
       *
       * @param value The bytes for percentage to set.
       * @return This builder for chaining.
       */
      public Builder setPercentageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        percentage_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object rdgLevel_ = "";
      /**
       * <code>string rdgLevel = 12;</code>
       *
       * @return The rdgLevel.
       */
      public java.lang.String getRdgLevel() {
        java.lang.Object ref = rdgLevel_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          rdgLevel_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string rdgLevel = 12;</code>
       *
       * @return The bytes for rdgLevel.
       */
      public com.google.protobuf.ByteString getRdgLevelBytes() {
        java.lang.Object ref = rdgLevel_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          rdgLevel_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string rdgLevel = 12;</code>
       *
       * @param value The rdgLevel to set.
       * @return This builder for chaining.
       */
      public Builder setRdgLevel(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        rdgLevel_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string rdgLevel = 12;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearRdgLevel() {

        rdgLevel_ = getDefaultInstance().getRdgLevel();
        onChanged();
        return this;
      }
      /**
       * <code>string rdgLevel = 12;</code>
       *
       * @param value The bytes for rdgLevel to set.
       * @return This builder for chaining.
       */
      public Builder setRdgLevelBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        rdgLevel_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object sg_ = "";
      /**
       * <code>string sg = 13;</code>
       *
       * @return The sg.
       */
      public java.lang.String getSg() {
        java.lang.Object ref = sg_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          sg_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string sg = 13;</code>
       *
       * @return The bytes for sg.
       */
      public com.google.protobuf.ByteString getSgBytes() {
        java.lang.Object ref = sg_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          sg_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string sg = 13;</code>
       *
       * @param value The sg to set.
       * @return This builder for chaining.
       */
      public Builder setSg(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        sg_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string sg = 13;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSg() {

        sg_ = getDefaultInstance().getSg();
        onChanged();
        return this;
      }
      /**
       * <code>string sg = 13;</code>
       *
       * @param value The bytes for sg to set.
       * @return This builder for chaining.
       */
      public Builder setSgBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        sg_ = value;
        onChanged();
        return this;
      }

      private long tankId_;
      /**
       * <code>int64 tankId = 14;</code>
       *
       * @return The tankId.
       */
      public long getTankId() {
        return tankId_;
      }
      /**
       * <code>int64 tankId = 14;</code>
       *
       * @param value The tankId to set.
       * @return This builder for chaining.
       */
      public Builder setTankId(long value) {

        tankId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 tankId = 14;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTankId() {

        tankId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object tankName_ = "";
      /**
       * <code>string tankName = 15;</code>
       *
       * @return The tankName.
       */
      public java.lang.String getTankName() {
        java.lang.Object ref = tankName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          tankName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string tankName = 15;</code>
       *
       * @return The bytes for tankName.
       */
      public com.google.protobuf.ByteString getTankNameBytes() {
        java.lang.Object ref = tankName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          tankName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string tankName = 15;</code>
       *
       * @param value The tankName to set.
       * @return This builder for chaining.
       */
      public Builder setTankName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        tankName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string tankName = 15;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTankName() {

        tankName_ = getDefaultInstance().getTankName();
        onChanged();
        return this;
      }
      /**
       * <code>string tankName = 15;</code>
       *
       * @param value The bytes for tankName to set.
       * @return This builder for chaining.
       */
      public Builder setTankNameBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        tankName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object tcg_ = "";
      /**
       * <code>string tcg = 16;</code>
       *
       * @return The tcg.
       */
      public java.lang.String getTcg() {
        java.lang.Object ref = tcg_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          tcg_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string tcg = 16;</code>
       *
       * @return The bytes for tcg.
       */
      public com.google.protobuf.ByteString getTcgBytes() {
        java.lang.Object ref = tcg_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          tcg_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string tcg = 16;</code>
       *
       * @param value The tcg to set.
       * @return This builder for chaining.
       */
      public Builder setTcg(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        tcg_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string tcg = 16;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTcg() {

        tcg_ = getDefaultInstance().getTcg();
        onChanged();
        return this;
      }
      /**
       * <code>string tcg = 16;</code>
       *
       * @param value The bytes for tcg to set.
       * @return This builder for chaining.
       */
      public Builder setTcgBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        tcg_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object vcg_ = "";
      /**
       * <code>string vcg = 17;</code>
       *
       * @return The vcg.
       */
      public java.lang.String getVcg() {
        java.lang.Object ref = vcg_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          vcg_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string vcg = 17;</code>
       *
       * @return The bytes for vcg.
       */
      public com.google.protobuf.ByteString getVcgBytes() {
        java.lang.Object ref = vcg_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          vcg_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string vcg = 17;</code>
       *
       * @param value The vcg to set.
       * @return This builder for chaining.
       */
      public Builder setVcg(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        vcg_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string vcg = 17;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearVcg() {

        vcg_ = getDefaultInstance().getVcg();
        onChanged();
        return this;
      }
      /**
       * <code>string vcg = 17;</code>
       *
       * @param value The bytes for vcg to set.
       * @return This builder for chaining.
       */
      public Builder setVcgBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        vcg_ = value;
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

      // @@protoc_insertion_point(builder_scope:LoadablePlanBallastDetail)
    }

    // @@protoc_insertion_point(class_scope:LoadablePlanBallastDetail)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanBallastDetail
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadablePlanBallastDetail> PARSER =
        new com.google.protobuf.AbstractParser<LoadablePlanBallastDetail>() {
          @java.lang.Override
          public LoadablePlanBallastDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadablePlanBallastDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadablePlanBallastDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadablePlanBallastDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanBallastDetail
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadablePlanCommingleDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadablePlanCommingleDetail)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>string api = 2;</code>
     *
     * @return The api.
     */
    java.lang.String getApi();
    /**
     * <code>string api = 2;</code>
     *
     * @return The bytes for api.
     */
    com.google.protobuf.ByteString getApiBytes();

    /**
     * <code>string cargo1Abbreviation = 3;</code>
     *
     * @return The cargo1Abbreviation.
     */
    java.lang.String getCargo1Abbreviation();
    /**
     * <code>string cargo1Abbreviation = 3;</code>
     *
     * @return The bytes for cargo1Abbreviation.
     */
    com.google.protobuf.ByteString getCargo1AbbreviationBytes();

    /**
     * <code>string cargo1Bbls60f = 4;</code>
     *
     * @return The cargo1Bbls60f.
     */
    java.lang.String getCargo1Bbls60F();
    /**
     * <code>string cargo1Bbls60f = 4;</code>
     *
     * @return The bytes for cargo1Bbls60f.
     */
    com.google.protobuf.ByteString getCargo1Bbls60FBytes();

    /**
     * <code>string cargo1BblsDbs = 5;</code>
     *
     * @return The cargo1BblsDbs.
     */
    java.lang.String getCargo1BblsDbs();
    /**
     * <code>string cargo1BblsDbs = 5;</code>
     *
     * @return The bytes for cargo1BblsDbs.
     */
    com.google.protobuf.ByteString getCargo1BblsDbsBytes();

    /**
     * <code>string cargo1Kl = 6;</code>
     *
     * @return The cargo1Kl.
     */
    java.lang.String getCargo1Kl();
    /**
     * <code>string cargo1Kl = 6;</code>
     *
     * @return The bytes for cargo1Kl.
     */
    com.google.protobuf.ByteString getCargo1KlBytes();

    /**
     * <code>string cargo1Lt = 7;</code>
     *
     * @return The cargo1Lt.
     */
    java.lang.String getCargo1Lt();
    /**
     * <code>string cargo1Lt = 7;</code>
     *
     * @return The bytes for cargo1Lt.
     */
    com.google.protobuf.ByteString getCargo1LtBytes();

    /**
     * <code>string cargo1Mt = 8;</code>
     *
     * @return The cargo1Mt.
     */
    java.lang.String getCargo1Mt();
    /**
     * <code>string cargo1Mt = 8;</code>
     *
     * @return The bytes for cargo1Mt.
     */
    com.google.protobuf.ByteString getCargo1MtBytes();

    /**
     * <code>string cargo1Percentage = 9;</code>
     *
     * @return The cargo1Percentage.
     */
    java.lang.String getCargo1Percentage();
    /**
     * <code>string cargo1Percentage = 9;</code>
     *
     * @return The bytes for cargo1Percentage.
     */
    com.google.protobuf.ByteString getCargo1PercentageBytes();

    /**
     * <code>string cargo2Abbreviation = 10;</code>
     *
     * @return The cargo2Abbreviation.
     */
    java.lang.String getCargo2Abbreviation();
    /**
     * <code>string cargo2Abbreviation = 10;</code>
     *
     * @return The bytes for cargo2Abbreviation.
     */
    com.google.protobuf.ByteString getCargo2AbbreviationBytes();

    /**
     * <code>string cargo2Bbls60f = 11;</code>
     *
     * @return The cargo2Bbls60f.
     */
    java.lang.String getCargo2Bbls60F();
    /**
     * <code>string cargo2Bbls60f = 11;</code>
     *
     * @return The bytes for cargo2Bbls60f.
     */
    com.google.protobuf.ByteString getCargo2Bbls60FBytes();

    /**
     * <code>string cargo2BblsDbs = 12;</code>
     *
     * @return The cargo2BblsDbs.
     */
    java.lang.String getCargo2BblsDbs();
    /**
     * <code>string cargo2BblsDbs = 12;</code>
     *
     * @return The bytes for cargo2BblsDbs.
     */
    com.google.protobuf.ByteString getCargo2BblsDbsBytes();

    /**
     * <code>string cargo2Kl = 13;</code>
     *
     * @return The cargo2Kl.
     */
    java.lang.String getCargo2Kl();
    /**
     * <code>string cargo2Kl = 13;</code>
     *
     * @return The bytes for cargo2Kl.
     */
    com.google.protobuf.ByteString getCargo2KlBytes();

    /**
     * <code>string cargo2Lt = 14;</code>
     *
     * @return The cargo2Lt.
     */
    java.lang.String getCargo2Lt();
    /**
     * <code>string cargo2Lt = 14;</code>
     *
     * @return The bytes for cargo2Lt.
     */
    com.google.protobuf.ByteString getCargo2LtBytes();

    /**
     * <code>string cargo2Mt = 15;</code>
     *
     * @return The cargo2Mt.
     */
    java.lang.String getCargo2Mt();
    /**
     * <code>string cargo2Mt = 15;</code>
     *
     * @return The bytes for cargo2Mt.
     */
    com.google.protobuf.ByteString getCargo2MtBytes();

    /**
     * <code>string cargo2Percentage = 16;</code>
     *
     * @return The cargo2Percentage.
     */
    java.lang.String getCargo2Percentage();
    /**
     * <code>string cargo2Percentage = 16;</code>
     *
     * @return The bytes for cargo2Percentage.
     */
    com.google.protobuf.ByteString getCargo2PercentageBytes();

    /**
     * <code>string correctedUllage = 17;</code>
     *
     * @return The correctedUllage.
     */
    java.lang.String getCorrectedUllage();
    /**
     * <code>string correctedUllage = 17;</code>
     *
     * @return The bytes for correctedUllage.
     */
    com.google.protobuf.ByteString getCorrectedUllageBytes();

    /**
     * <code>string correctionFactor = 18;</code>
     *
     * @return The correctionFactor.
     */
    java.lang.String getCorrectionFactor();
    /**
     * <code>string correctionFactor = 18;</code>
     *
     * @return The bytes for correctionFactor.
     */
    com.google.protobuf.ByteString getCorrectionFactorBytes();

    /**
     * <code>string fillingRatio = 19;</code>
     *
     * @return The fillingRatio.
     */
    java.lang.String getFillingRatio();
    /**
     * <code>string fillingRatio = 19;</code>
     *
     * @return The bytes for fillingRatio.
     */
    com.google.protobuf.ByteString getFillingRatioBytes();

    /**
     * <code>string grade = 20;</code>
     *
     * @return The grade.
     */
    java.lang.String getGrade();
    /**
     * <code>string grade = 20;</code>
     *
     * @return The bytes for grade.
     */
    com.google.protobuf.ByteString getGradeBytes();

    /**
     * <code>bool isActive = 21;</code>
     *
     * @return The isActive.
     */
    boolean getIsActive();

    /**
     * <code>int64 loadablePatternId = 22;</code>
     *
     * @return The loadablePatternId.
     */
    long getLoadablePatternId();

    /**
     * <code>int64 loadablePlanId = 23;</code>
     *
     * @return The loadablePlanId.
     */
    long getLoadablePlanId();

    /**
     * <code>int32 loadingOrder = 24;</code>
     *
     * @return The loadingOrder.
     */
    int getLoadingOrder();

    /**
     * <code>string orderQuantity = 25;</code>
     *
     * @return The orderQuantity.
     */
    java.lang.String getOrderQuantity();
    /**
     * <code>string orderQuantity = 25;</code>
     *
     * @return The bytes for orderQuantity.
     */
    com.google.protobuf.ByteString getOrderQuantityBytes();

    /**
     * <code>int32 priority = 26;</code>
     *
     * @return The priority.
     */
    int getPriority();

    /**
     * <code>string quantity = 27;</code>
     *
     * @return The quantity.
     */
    java.lang.String getQuantity();
    /**
     * <code>string quantity = 27;</code>
     *
     * @return The bytes for quantity.
     */
    com.google.protobuf.ByteString getQuantityBytes();

    /**
     * <code>string rdgUllage = 28;</code>
     *
     * @return The rdgUllage.
     */
    java.lang.String getRdgUllage();
    /**
     * <code>string rdgUllage = 28;</code>
     *
     * @return The bytes for rdgUllage.
     */
    com.google.protobuf.ByteString getRdgUllageBytes();

    /**
     * <code>string slopQuantity = 29;</code>
     *
     * @return The slopQuantity.
     */
    java.lang.String getSlopQuantity();
    /**
     * <code>string slopQuantity = 29;</code>
     *
     * @return The bytes for slopQuantity.
     */
    com.google.protobuf.ByteString getSlopQuantityBytes();

    /**
     * <code>int64 tankId = 30;</code>
     *
     * @return The tankId.
     */
    long getTankId();

    /**
     * <code>string tankName = 31;</code>
     *
     * @return The tankName.
     */
    java.lang.String getTankName();
    /**
     * <code>string tankName = 31;</code>
     *
     * @return The bytes for tankName.
     */
    com.google.protobuf.ByteString getTankNameBytes();

    /**
     * <code>string temperature = 32;</code>
     *
     * @return The temperature.
     */
    java.lang.String getTemperature();
    /**
     * <code>string temperature = 32;</code>
     *
     * @return The bytes for temperature.
     */
    com.google.protobuf.ByteString getTemperatureBytes();

    /**
     * <code>string timeRequiredForLoading = 33;</code>
     *
     * @return The timeRequiredForLoading.
     */
    java.lang.String getTimeRequiredForLoading();
    /**
     * <code>string timeRequiredForLoading = 33;</code>
     *
     * @return The bytes for timeRequiredForLoading.
     */
    com.google.protobuf.ByteString getTimeRequiredForLoadingBytes();
  }
  /** Protobuf type {@code LoadablePlanCommingleDetail} */
  public static final class LoadablePlanCommingleDetail
      extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadablePlanCommingleDetail)
      LoadablePlanCommingleDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadablePlanCommingleDetail.newBuilder() to construct.
    private LoadablePlanCommingleDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadablePlanCommingleDetail() {
      api_ = "";
      cargo1Abbreviation_ = "";
      cargo1Bbls60F_ = "";
      cargo1BblsDbs_ = "";
      cargo1Kl_ = "";
      cargo1Lt_ = "";
      cargo1Mt_ = "";
      cargo1Percentage_ = "";
      cargo2Abbreviation_ = "";
      cargo2Bbls60F_ = "";
      cargo2BblsDbs_ = "";
      cargo2Kl_ = "";
      cargo2Lt_ = "";
      cargo2Mt_ = "";
      cargo2Percentage_ = "";
      correctedUllage_ = "";
      correctionFactor_ = "";
      fillingRatio_ = "";
      grade_ = "";
      orderQuantity_ = "";
      quantity_ = "";
      rdgUllage_ = "";
      slopQuantity_ = "";
      tankName_ = "";
      temperature_ = "";
      timeRequiredForLoading_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadablePlanCommingleDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadablePlanCommingleDetail(
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

                api_ = s;
                break;
              }
            case 26:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo1Abbreviation_ = s;
                break;
              }
            case 34:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo1Bbls60F_ = s;
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo1BblsDbs_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo1Kl_ = s;
                break;
              }
            case 58:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo1Lt_ = s;
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo1Mt_ = s;
                break;
              }
            case 74:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo1Percentage_ = s;
                break;
              }
            case 82:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo2Abbreviation_ = s;
                break;
              }
            case 90:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo2Bbls60F_ = s;
                break;
              }
            case 98:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo2BblsDbs_ = s;
                break;
              }
            case 106:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo2Kl_ = s;
                break;
              }
            case 114:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo2Lt_ = s;
                break;
              }
            case 122:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo2Mt_ = s;
                break;
              }
            case 130:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargo2Percentage_ = s;
                break;
              }
            case 138:
              {
                java.lang.String s = input.readStringRequireUtf8();

                correctedUllage_ = s;
                break;
              }
            case 146:
              {
                java.lang.String s = input.readStringRequireUtf8();

                correctionFactor_ = s;
                break;
              }
            case 154:
              {
                java.lang.String s = input.readStringRequireUtf8();

                fillingRatio_ = s;
                break;
              }
            case 162:
              {
                java.lang.String s = input.readStringRequireUtf8();

                grade_ = s;
                break;
              }
            case 168:
              {
                isActive_ = input.readBool();
                break;
              }
            case 176:
              {
                loadablePatternId_ = input.readInt64();
                break;
              }
            case 184:
              {
                loadablePlanId_ = input.readInt64();
                break;
              }
            case 192:
              {
                loadingOrder_ = input.readInt32();
                break;
              }
            case 202:
              {
                java.lang.String s = input.readStringRequireUtf8();

                orderQuantity_ = s;
                break;
              }
            case 208:
              {
                priority_ = input.readInt32();
                break;
              }
            case 218:
              {
                java.lang.String s = input.readStringRequireUtf8();

                quantity_ = s;
                break;
              }
            case 226:
              {
                java.lang.String s = input.readStringRequireUtf8();

                rdgUllage_ = s;
                break;
              }
            case 234:
              {
                java.lang.String s = input.readStringRequireUtf8();

                slopQuantity_ = s;
                break;
              }
            case 240:
              {
                tankId_ = input.readInt64();
                break;
              }
            case 250:
              {
                java.lang.String s = input.readStringRequireUtf8();

                tankName_ = s;
                break;
              }
            case 258:
              {
                java.lang.String s = input.readStringRequireUtf8();

                temperature_ = s;
                break;
              }
            case 266:
              {
                java.lang.String s = input.readStringRequireUtf8();

                timeRequiredForLoading_ = s;
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
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadablePlanCommingleDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadablePlanCommingleDetail_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
                  .class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
                  .Builder.class);
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

    public static final int API_FIELD_NUMBER = 2;
    private volatile java.lang.Object api_;
    /**
     * <code>string api = 2;</code>
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
     * <code>string api = 2;</code>
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

    public static final int CARGO1ABBREVIATION_FIELD_NUMBER = 3;
    private volatile java.lang.Object cargo1Abbreviation_;
    /**
     * <code>string cargo1Abbreviation = 3;</code>
     *
     * @return The cargo1Abbreviation.
     */
    public java.lang.String getCargo1Abbreviation() {
      java.lang.Object ref = cargo1Abbreviation_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo1Abbreviation_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo1Abbreviation = 3;</code>
     *
     * @return The bytes for cargo1Abbreviation.
     */
    public com.google.protobuf.ByteString getCargo1AbbreviationBytes() {
      java.lang.Object ref = cargo1Abbreviation_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo1Abbreviation_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO1BBLS60F_FIELD_NUMBER = 4;
    private volatile java.lang.Object cargo1Bbls60F_;
    /**
     * <code>string cargo1Bbls60f = 4;</code>
     *
     * @return The cargo1Bbls60f.
     */
    public java.lang.String getCargo1Bbls60F() {
      java.lang.Object ref = cargo1Bbls60F_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo1Bbls60F_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo1Bbls60f = 4;</code>
     *
     * @return The bytes for cargo1Bbls60f.
     */
    public com.google.protobuf.ByteString getCargo1Bbls60FBytes() {
      java.lang.Object ref = cargo1Bbls60F_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo1Bbls60F_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO1BBLSDBS_FIELD_NUMBER = 5;
    private volatile java.lang.Object cargo1BblsDbs_;
    /**
     * <code>string cargo1BblsDbs = 5;</code>
     *
     * @return The cargo1BblsDbs.
     */
    public java.lang.String getCargo1BblsDbs() {
      java.lang.Object ref = cargo1BblsDbs_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo1BblsDbs_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo1BblsDbs = 5;</code>
     *
     * @return The bytes for cargo1BblsDbs.
     */
    public com.google.protobuf.ByteString getCargo1BblsDbsBytes() {
      java.lang.Object ref = cargo1BblsDbs_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo1BblsDbs_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO1KL_FIELD_NUMBER = 6;
    private volatile java.lang.Object cargo1Kl_;
    /**
     * <code>string cargo1Kl = 6;</code>
     *
     * @return The cargo1Kl.
     */
    public java.lang.String getCargo1Kl() {
      java.lang.Object ref = cargo1Kl_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo1Kl_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo1Kl = 6;</code>
     *
     * @return The bytes for cargo1Kl.
     */
    public com.google.protobuf.ByteString getCargo1KlBytes() {
      java.lang.Object ref = cargo1Kl_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo1Kl_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO1LT_FIELD_NUMBER = 7;
    private volatile java.lang.Object cargo1Lt_;
    /**
     * <code>string cargo1Lt = 7;</code>
     *
     * @return The cargo1Lt.
     */
    public java.lang.String getCargo1Lt() {
      java.lang.Object ref = cargo1Lt_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo1Lt_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo1Lt = 7;</code>
     *
     * @return The bytes for cargo1Lt.
     */
    public com.google.protobuf.ByteString getCargo1LtBytes() {
      java.lang.Object ref = cargo1Lt_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo1Lt_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO1MT_FIELD_NUMBER = 8;
    private volatile java.lang.Object cargo1Mt_;
    /**
     * <code>string cargo1Mt = 8;</code>
     *
     * @return The cargo1Mt.
     */
    public java.lang.String getCargo1Mt() {
      java.lang.Object ref = cargo1Mt_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo1Mt_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo1Mt = 8;</code>
     *
     * @return The bytes for cargo1Mt.
     */
    public com.google.protobuf.ByteString getCargo1MtBytes() {
      java.lang.Object ref = cargo1Mt_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo1Mt_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO1PERCENTAGE_FIELD_NUMBER = 9;
    private volatile java.lang.Object cargo1Percentage_;
    /**
     * <code>string cargo1Percentage = 9;</code>
     *
     * @return The cargo1Percentage.
     */
    public java.lang.String getCargo1Percentage() {
      java.lang.Object ref = cargo1Percentage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo1Percentage_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo1Percentage = 9;</code>
     *
     * @return The bytes for cargo1Percentage.
     */
    public com.google.protobuf.ByteString getCargo1PercentageBytes() {
      java.lang.Object ref = cargo1Percentage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo1Percentage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO2ABBREVIATION_FIELD_NUMBER = 10;
    private volatile java.lang.Object cargo2Abbreviation_;
    /**
     * <code>string cargo2Abbreviation = 10;</code>
     *
     * @return The cargo2Abbreviation.
     */
    public java.lang.String getCargo2Abbreviation() {
      java.lang.Object ref = cargo2Abbreviation_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo2Abbreviation_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo2Abbreviation = 10;</code>
     *
     * @return The bytes for cargo2Abbreviation.
     */
    public com.google.protobuf.ByteString getCargo2AbbreviationBytes() {
      java.lang.Object ref = cargo2Abbreviation_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo2Abbreviation_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO2BBLS60F_FIELD_NUMBER = 11;
    private volatile java.lang.Object cargo2Bbls60F_;
    /**
     * <code>string cargo2Bbls60f = 11;</code>
     *
     * @return The cargo2Bbls60f.
     */
    public java.lang.String getCargo2Bbls60F() {
      java.lang.Object ref = cargo2Bbls60F_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo2Bbls60F_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo2Bbls60f = 11;</code>
     *
     * @return The bytes for cargo2Bbls60f.
     */
    public com.google.protobuf.ByteString getCargo2Bbls60FBytes() {
      java.lang.Object ref = cargo2Bbls60F_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo2Bbls60F_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO2BBLSDBS_FIELD_NUMBER = 12;
    private volatile java.lang.Object cargo2BblsDbs_;
    /**
     * <code>string cargo2BblsDbs = 12;</code>
     *
     * @return The cargo2BblsDbs.
     */
    public java.lang.String getCargo2BblsDbs() {
      java.lang.Object ref = cargo2BblsDbs_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo2BblsDbs_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo2BblsDbs = 12;</code>
     *
     * @return The bytes for cargo2BblsDbs.
     */
    public com.google.protobuf.ByteString getCargo2BblsDbsBytes() {
      java.lang.Object ref = cargo2BblsDbs_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo2BblsDbs_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO2KL_FIELD_NUMBER = 13;
    private volatile java.lang.Object cargo2Kl_;
    /**
     * <code>string cargo2Kl = 13;</code>
     *
     * @return The cargo2Kl.
     */
    public java.lang.String getCargo2Kl() {
      java.lang.Object ref = cargo2Kl_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo2Kl_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo2Kl = 13;</code>
     *
     * @return The bytes for cargo2Kl.
     */
    public com.google.protobuf.ByteString getCargo2KlBytes() {
      java.lang.Object ref = cargo2Kl_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo2Kl_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO2LT_FIELD_NUMBER = 14;
    private volatile java.lang.Object cargo2Lt_;
    /**
     * <code>string cargo2Lt = 14;</code>
     *
     * @return The cargo2Lt.
     */
    public java.lang.String getCargo2Lt() {
      java.lang.Object ref = cargo2Lt_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo2Lt_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo2Lt = 14;</code>
     *
     * @return The bytes for cargo2Lt.
     */
    public com.google.protobuf.ByteString getCargo2LtBytes() {
      java.lang.Object ref = cargo2Lt_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo2Lt_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO2MT_FIELD_NUMBER = 15;
    private volatile java.lang.Object cargo2Mt_;
    /**
     * <code>string cargo2Mt = 15;</code>
     *
     * @return The cargo2Mt.
     */
    public java.lang.String getCargo2Mt() {
      java.lang.Object ref = cargo2Mt_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo2Mt_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo2Mt = 15;</code>
     *
     * @return The bytes for cargo2Mt.
     */
    public com.google.protobuf.ByteString getCargo2MtBytes() {
      java.lang.Object ref = cargo2Mt_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo2Mt_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGO2PERCENTAGE_FIELD_NUMBER = 16;
    private volatile java.lang.Object cargo2Percentage_;
    /**
     * <code>string cargo2Percentage = 16;</code>
     *
     * @return The cargo2Percentage.
     */
    public java.lang.String getCargo2Percentage() {
      java.lang.Object ref = cargo2Percentage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargo2Percentage_ = s;
        return s;
      }
    }
    /**
     * <code>string cargo2Percentage = 16;</code>
     *
     * @return The bytes for cargo2Percentage.
     */
    public com.google.protobuf.ByteString getCargo2PercentageBytes() {
      java.lang.Object ref = cargo2Percentage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargo2Percentage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CORRECTEDULLAGE_FIELD_NUMBER = 17;
    private volatile java.lang.Object correctedUllage_;
    /**
     * <code>string correctedUllage = 17;</code>
     *
     * @return The correctedUllage.
     */
    public java.lang.String getCorrectedUllage() {
      java.lang.Object ref = correctedUllage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        correctedUllage_ = s;
        return s;
      }
    }
    /**
     * <code>string correctedUllage = 17;</code>
     *
     * @return The bytes for correctedUllage.
     */
    public com.google.protobuf.ByteString getCorrectedUllageBytes() {
      java.lang.Object ref = correctedUllage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        correctedUllage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CORRECTIONFACTOR_FIELD_NUMBER = 18;
    private volatile java.lang.Object correctionFactor_;
    /**
     * <code>string correctionFactor = 18;</code>
     *
     * @return The correctionFactor.
     */
    public java.lang.String getCorrectionFactor() {
      java.lang.Object ref = correctionFactor_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        correctionFactor_ = s;
        return s;
      }
    }
    /**
     * <code>string correctionFactor = 18;</code>
     *
     * @return The bytes for correctionFactor.
     */
    public com.google.protobuf.ByteString getCorrectionFactorBytes() {
      java.lang.Object ref = correctionFactor_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        correctionFactor_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int FILLINGRATIO_FIELD_NUMBER = 19;
    private volatile java.lang.Object fillingRatio_;
    /**
     * <code>string fillingRatio = 19;</code>
     *
     * @return The fillingRatio.
     */
    public java.lang.String getFillingRatio() {
      java.lang.Object ref = fillingRatio_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        fillingRatio_ = s;
        return s;
      }
    }
    /**
     * <code>string fillingRatio = 19;</code>
     *
     * @return The bytes for fillingRatio.
     */
    public com.google.protobuf.ByteString getFillingRatioBytes() {
      java.lang.Object ref = fillingRatio_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        fillingRatio_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int GRADE_FIELD_NUMBER = 20;
    private volatile java.lang.Object grade_;
    /**
     * <code>string grade = 20;</code>
     *
     * @return The grade.
     */
    public java.lang.String getGrade() {
      java.lang.Object ref = grade_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        grade_ = s;
        return s;
      }
    }
    /**
     * <code>string grade = 20;</code>
     *
     * @return The bytes for grade.
     */
    public com.google.protobuf.ByteString getGradeBytes() {
      java.lang.Object ref = grade_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        grade_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ISACTIVE_FIELD_NUMBER = 21;
    private boolean isActive_;
    /**
     * <code>bool isActive = 21;</code>
     *
     * @return The isActive.
     */
    public boolean getIsActive() {
      return isActive_;
    }

    public static final int LOADABLEPATTERNID_FIELD_NUMBER = 22;
    private long loadablePatternId_;
    /**
     * <code>int64 loadablePatternId = 22;</code>
     *
     * @return The loadablePatternId.
     */
    public long getLoadablePatternId() {
      return loadablePatternId_;
    }

    public static final int LOADABLEPLANID_FIELD_NUMBER = 23;
    private long loadablePlanId_;
    /**
     * <code>int64 loadablePlanId = 23;</code>
     *
     * @return The loadablePlanId.
     */
    public long getLoadablePlanId() {
      return loadablePlanId_;
    }

    public static final int LOADINGORDER_FIELD_NUMBER = 24;
    private int loadingOrder_;
    /**
     * <code>int32 loadingOrder = 24;</code>
     *
     * @return The loadingOrder.
     */
    public int getLoadingOrder() {
      return loadingOrder_;
    }

    public static final int ORDERQUANTITY_FIELD_NUMBER = 25;
    private volatile java.lang.Object orderQuantity_;
    /**
     * <code>string orderQuantity = 25;</code>
     *
     * @return The orderQuantity.
     */
    public java.lang.String getOrderQuantity() {
      java.lang.Object ref = orderQuantity_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        orderQuantity_ = s;
        return s;
      }
    }
    /**
     * <code>string orderQuantity = 25;</code>
     *
     * @return The bytes for orderQuantity.
     */
    public com.google.protobuf.ByteString getOrderQuantityBytes() {
      java.lang.Object ref = orderQuantity_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        orderQuantity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PRIORITY_FIELD_NUMBER = 26;
    private int priority_;
    /**
     * <code>int32 priority = 26;</code>
     *
     * @return The priority.
     */
    public int getPriority() {
      return priority_;
    }

    public static final int QUANTITY_FIELD_NUMBER = 27;
    private volatile java.lang.Object quantity_;
    /**
     * <code>string quantity = 27;</code>
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
     * <code>string quantity = 27;</code>
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

    public static final int RDGULLAGE_FIELD_NUMBER = 28;
    private volatile java.lang.Object rdgUllage_;
    /**
     * <code>string rdgUllage = 28;</code>
     *
     * @return The rdgUllage.
     */
    public java.lang.String getRdgUllage() {
      java.lang.Object ref = rdgUllage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        rdgUllage_ = s;
        return s;
      }
    }
    /**
     * <code>string rdgUllage = 28;</code>
     *
     * @return The bytes for rdgUllage.
     */
    public com.google.protobuf.ByteString getRdgUllageBytes() {
      java.lang.Object ref = rdgUllage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        rdgUllage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SLOPQUANTITY_FIELD_NUMBER = 29;
    private volatile java.lang.Object slopQuantity_;
    /**
     * <code>string slopQuantity = 29;</code>
     *
     * @return The slopQuantity.
     */
    public java.lang.String getSlopQuantity() {
      java.lang.Object ref = slopQuantity_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        slopQuantity_ = s;
        return s;
      }
    }
    /**
     * <code>string slopQuantity = 29;</code>
     *
     * @return The bytes for slopQuantity.
     */
    public com.google.protobuf.ByteString getSlopQuantityBytes() {
      java.lang.Object ref = slopQuantity_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        slopQuantity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TANKID_FIELD_NUMBER = 30;
    private long tankId_;
    /**
     * <code>int64 tankId = 30;</code>
     *
     * @return The tankId.
     */
    public long getTankId() {
      return tankId_;
    }

    public static final int TANKNAME_FIELD_NUMBER = 31;
    private volatile java.lang.Object tankName_;
    /**
     * <code>string tankName = 31;</code>
     *
     * @return The tankName.
     */
    public java.lang.String getTankName() {
      java.lang.Object ref = tankName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tankName_ = s;
        return s;
      }
    }
    /**
     * <code>string tankName = 31;</code>
     *
     * @return The bytes for tankName.
     */
    public com.google.protobuf.ByteString getTankNameBytes() {
      java.lang.Object ref = tankName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        tankName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TEMPERATURE_FIELD_NUMBER = 32;
    private volatile java.lang.Object temperature_;
    /**
     * <code>string temperature = 32;</code>
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
     * <code>string temperature = 32;</code>
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

    public static final int TIMEREQUIREDFORLOADING_FIELD_NUMBER = 33;
    private volatile java.lang.Object timeRequiredForLoading_;
    /**
     * <code>string timeRequiredForLoading = 33;</code>
     *
     * @return The timeRequiredForLoading.
     */
    public java.lang.String getTimeRequiredForLoading() {
      java.lang.Object ref = timeRequiredForLoading_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        timeRequiredForLoading_ = s;
        return s;
      }
    }
    /**
     * <code>string timeRequiredForLoading = 33;</code>
     *
     * @return The bytes for timeRequiredForLoading.
     */
    public com.google.protobuf.ByteString getTimeRequiredForLoadingBytes() {
      java.lang.Object ref = timeRequiredForLoading_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        timeRequiredForLoading_ = b;
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
      if (!getApiBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, api_);
      }
      if (!getCargo1AbbreviationBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, cargo1Abbreviation_);
      }
      if (!getCargo1Bbls60FBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, cargo1Bbls60F_);
      }
      if (!getCargo1BblsDbsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, cargo1BblsDbs_);
      }
      if (!getCargo1KlBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, cargo1Kl_);
      }
      if (!getCargo1LtBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, cargo1Lt_);
      }
      if (!getCargo1MtBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, cargo1Mt_);
      }
      if (!getCargo1PercentageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 9, cargo1Percentage_);
      }
      if (!getCargo2AbbreviationBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 10, cargo2Abbreviation_);
      }
      if (!getCargo2Bbls60FBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 11, cargo2Bbls60F_);
      }
      if (!getCargo2BblsDbsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 12, cargo2BblsDbs_);
      }
      if (!getCargo2KlBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 13, cargo2Kl_);
      }
      if (!getCargo2LtBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 14, cargo2Lt_);
      }
      if (!getCargo2MtBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 15, cargo2Mt_);
      }
      if (!getCargo2PercentageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 16, cargo2Percentage_);
      }
      if (!getCorrectedUllageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 17, correctedUllage_);
      }
      if (!getCorrectionFactorBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 18, correctionFactor_);
      }
      if (!getFillingRatioBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 19, fillingRatio_);
      }
      if (!getGradeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 20, grade_);
      }
      if (isActive_ != false) {
        output.writeBool(21, isActive_);
      }
      if (loadablePatternId_ != 0L) {
        output.writeInt64(22, loadablePatternId_);
      }
      if (loadablePlanId_ != 0L) {
        output.writeInt64(23, loadablePlanId_);
      }
      if (loadingOrder_ != 0) {
        output.writeInt32(24, loadingOrder_);
      }
      if (!getOrderQuantityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 25, orderQuantity_);
      }
      if (priority_ != 0) {
        output.writeInt32(26, priority_);
      }
      if (!getQuantityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 27, quantity_);
      }
      if (!getRdgUllageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 28, rdgUllage_);
      }
      if (!getSlopQuantityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 29, slopQuantity_);
      }
      if (tankId_ != 0L) {
        output.writeInt64(30, tankId_);
      }
      if (!getTankNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 31, tankName_);
      }
      if (!getTemperatureBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 32, temperature_);
      }
      if (!getTimeRequiredForLoadingBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 33, timeRequiredForLoading_);
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
      if (!getApiBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, api_);
      }
      if (!getCargo1AbbreviationBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, cargo1Abbreviation_);
      }
      if (!getCargo1Bbls60FBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, cargo1Bbls60F_);
      }
      if (!getCargo1BblsDbsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, cargo1BblsDbs_);
      }
      if (!getCargo1KlBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, cargo1Kl_);
      }
      if (!getCargo1LtBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, cargo1Lt_);
      }
      if (!getCargo1MtBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, cargo1Mt_);
      }
      if (!getCargo1PercentageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, cargo1Percentage_);
      }
      if (!getCargo2AbbreviationBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(10, cargo2Abbreviation_);
      }
      if (!getCargo2Bbls60FBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(11, cargo2Bbls60F_);
      }
      if (!getCargo2BblsDbsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(12, cargo2BblsDbs_);
      }
      if (!getCargo2KlBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(13, cargo2Kl_);
      }
      if (!getCargo2LtBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(14, cargo2Lt_);
      }
      if (!getCargo2MtBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(15, cargo2Mt_);
      }
      if (!getCargo2PercentageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(16, cargo2Percentage_);
      }
      if (!getCorrectedUllageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(17, correctedUllage_);
      }
      if (!getCorrectionFactorBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(18, correctionFactor_);
      }
      if (!getFillingRatioBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(19, fillingRatio_);
      }
      if (!getGradeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(20, grade_);
      }
      if (isActive_ != false) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(21, isActive_);
      }
      if (loadablePatternId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(22, loadablePatternId_);
      }
      if (loadablePlanId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(23, loadablePlanId_);
      }
      if (loadingOrder_ != 0) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(24, loadingOrder_);
      }
      if (!getOrderQuantityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(25, orderQuantity_);
      }
      if (priority_ != 0) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(26, priority_);
      }
      if (!getQuantityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(27, quantity_);
      }
      if (!getRdgUllageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(28, rdgUllage_);
      }
      if (!getSlopQuantityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(29, slopQuantity_);
      }
      if (tankId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(30, tankId_);
      }
      if (!getTankNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(31, tankName_);
      }
      if (!getTemperatureBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(32, temperature_);
      }
      if (!getTimeRequiredForLoadingBytes().isEmpty()) {
        size +=
            com.google.protobuf.GeneratedMessageV3.computeStringSize(33, timeRequiredForLoading_);
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
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail)
              obj;

      if (getId() != other.getId()) return false;
      if (!getApi().equals(other.getApi())) return false;
      if (!getCargo1Abbreviation().equals(other.getCargo1Abbreviation())) return false;
      if (!getCargo1Bbls60F().equals(other.getCargo1Bbls60F())) return false;
      if (!getCargo1BblsDbs().equals(other.getCargo1BblsDbs())) return false;
      if (!getCargo1Kl().equals(other.getCargo1Kl())) return false;
      if (!getCargo1Lt().equals(other.getCargo1Lt())) return false;
      if (!getCargo1Mt().equals(other.getCargo1Mt())) return false;
      if (!getCargo1Percentage().equals(other.getCargo1Percentage())) return false;
      if (!getCargo2Abbreviation().equals(other.getCargo2Abbreviation())) return false;
      if (!getCargo2Bbls60F().equals(other.getCargo2Bbls60F())) return false;
      if (!getCargo2BblsDbs().equals(other.getCargo2BblsDbs())) return false;
      if (!getCargo2Kl().equals(other.getCargo2Kl())) return false;
      if (!getCargo2Lt().equals(other.getCargo2Lt())) return false;
      if (!getCargo2Mt().equals(other.getCargo2Mt())) return false;
      if (!getCargo2Percentage().equals(other.getCargo2Percentage())) return false;
      if (!getCorrectedUllage().equals(other.getCorrectedUllage())) return false;
      if (!getCorrectionFactor().equals(other.getCorrectionFactor())) return false;
      if (!getFillingRatio().equals(other.getFillingRatio())) return false;
      if (!getGrade().equals(other.getGrade())) return false;
      if (getIsActive() != other.getIsActive()) return false;
      if (getLoadablePatternId() != other.getLoadablePatternId()) return false;
      if (getLoadablePlanId() != other.getLoadablePlanId()) return false;
      if (getLoadingOrder() != other.getLoadingOrder()) return false;
      if (!getOrderQuantity().equals(other.getOrderQuantity())) return false;
      if (getPriority() != other.getPriority()) return false;
      if (!getQuantity().equals(other.getQuantity())) return false;
      if (!getRdgUllage().equals(other.getRdgUllage())) return false;
      if (!getSlopQuantity().equals(other.getSlopQuantity())) return false;
      if (getTankId() != other.getTankId()) return false;
      if (!getTankName().equals(other.getTankName())) return false;
      if (!getTemperature().equals(other.getTemperature())) return false;
      if (!getTimeRequiredForLoading().equals(other.getTimeRequiredForLoading())) return false;
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
      hash = (37 * hash) + API_FIELD_NUMBER;
      hash = (53 * hash) + getApi().hashCode();
      hash = (37 * hash) + CARGO1ABBREVIATION_FIELD_NUMBER;
      hash = (53 * hash) + getCargo1Abbreviation().hashCode();
      hash = (37 * hash) + CARGO1BBLS60F_FIELD_NUMBER;
      hash = (53 * hash) + getCargo1Bbls60F().hashCode();
      hash = (37 * hash) + CARGO1BBLSDBS_FIELD_NUMBER;
      hash = (53 * hash) + getCargo1BblsDbs().hashCode();
      hash = (37 * hash) + CARGO1KL_FIELD_NUMBER;
      hash = (53 * hash) + getCargo1Kl().hashCode();
      hash = (37 * hash) + CARGO1LT_FIELD_NUMBER;
      hash = (53 * hash) + getCargo1Lt().hashCode();
      hash = (37 * hash) + CARGO1MT_FIELD_NUMBER;
      hash = (53 * hash) + getCargo1Mt().hashCode();
      hash = (37 * hash) + CARGO1PERCENTAGE_FIELD_NUMBER;
      hash = (53 * hash) + getCargo1Percentage().hashCode();
      hash = (37 * hash) + CARGO2ABBREVIATION_FIELD_NUMBER;
      hash = (53 * hash) + getCargo2Abbreviation().hashCode();
      hash = (37 * hash) + CARGO2BBLS60F_FIELD_NUMBER;
      hash = (53 * hash) + getCargo2Bbls60F().hashCode();
      hash = (37 * hash) + CARGO2BBLSDBS_FIELD_NUMBER;
      hash = (53 * hash) + getCargo2BblsDbs().hashCode();
      hash = (37 * hash) + CARGO2KL_FIELD_NUMBER;
      hash = (53 * hash) + getCargo2Kl().hashCode();
      hash = (37 * hash) + CARGO2LT_FIELD_NUMBER;
      hash = (53 * hash) + getCargo2Lt().hashCode();
      hash = (37 * hash) + CARGO2MT_FIELD_NUMBER;
      hash = (53 * hash) + getCargo2Mt().hashCode();
      hash = (37 * hash) + CARGO2PERCENTAGE_FIELD_NUMBER;
      hash = (53 * hash) + getCargo2Percentage().hashCode();
      hash = (37 * hash) + CORRECTEDULLAGE_FIELD_NUMBER;
      hash = (53 * hash) + getCorrectedUllage().hashCode();
      hash = (37 * hash) + CORRECTIONFACTOR_FIELD_NUMBER;
      hash = (53 * hash) + getCorrectionFactor().hashCode();
      hash = (37 * hash) + FILLINGRATIO_FIELD_NUMBER;
      hash = (53 * hash) + getFillingRatio().hashCode();
      hash = (37 * hash) + GRADE_FIELD_NUMBER;
      hash = (53 * hash) + getGrade().hashCode();
      hash = (37 * hash) + ISACTIVE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsActive());
      hash = (37 * hash) + LOADABLEPATTERNID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadablePatternId());
      hash = (37 * hash) + LOADABLEPLANID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadablePlanId());
      hash = (37 * hash) + LOADINGORDER_FIELD_NUMBER;
      hash = (53 * hash) + getLoadingOrder();
      hash = (37 * hash) + ORDERQUANTITY_FIELD_NUMBER;
      hash = (53 * hash) + getOrderQuantity().hashCode();
      hash = (37 * hash) + PRIORITY_FIELD_NUMBER;
      hash = (53 * hash) + getPriority();
      hash = (37 * hash) + QUANTITY_FIELD_NUMBER;
      hash = (53 * hash) + getQuantity().hashCode();
      hash = (37 * hash) + RDGULLAGE_FIELD_NUMBER;
      hash = (53 * hash) + getRdgUllage().hashCode();
      hash = (37 * hash) + SLOPQUANTITY_FIELD_NUMBER;
      hash = (53 * hash) + getSlopQuantity().hashCode();
      hash = (37 * hash) + TANKID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getTankId());
      hash = (37 * hash) + TANKNAME_FIELD_NUMBER;
      hash = (53 * hash) + getTankName().hashCode();
      hash = (37 * hash) + TEMPERATURE_FIELD_NUMBER;
      hash = (53 * hash) + getTemperature().hashCode();
      hash = (37 * hash) + TIMEREQUIREDFORLOADING_FIELD_NUMBER;
      hash = (53 * hash) + getTimeRequiredForLoading().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
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
    /** Protobuf type {@code LoadablePlanCommingleDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadablePlanCommingleDetail)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanCommingleDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanCommingleDetail_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadablePlanCommingleDetail.class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadablePlanCommingleDetail.Builder.class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail.newBuilder()
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

        api_ = "";

        cargo1Abbreviation_ = "";

        cargo1Bbls60F_ = "";

        cargo1BblsDbs_ = "";

        cargo1Kl_ = "";

        cargo1Lt_ = "";

        cargo1Mt_ = "";

        cargo1Percentage_ = "";

        cargo2Abbreviation_ = "";

        cargo2Bbls60F_ = "";

        cargo2BblsDbs_ = "";

        cargo2Kl_ = "";

        cargo2Lt_ = "";

        cargo2Mt_ = "";

        cargo2Percentage_ = "";

        correctedUllage_ = "";

        correctionFactor_ = "";

        fillingRatio_ = "";

        grade_ = "";

        isActive_ = false;

        loadablePatternId_ = 0L;

        loadablePlanId_ = 0L;

        loadingOrder_ = 0;

        orderQuantity_ = "";

        priority_ = 0;

        quantity_ = "";

        rdgUllage_ = "";

        slopQuantity_ = "";

        tankId_ = 0L;

        tankName_ = "";

        temperature_ = "";

        timeRequiredForLoading_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanCommingleDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
          build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
            result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
            result =
                new com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadablePlanCommingleDetail(this);
        result.id_ = id_;
        result.api_ = api_;
        result.cargo1Abbreviation_ = cargo1Abbreviation_;
        result.cargo1Bbls60F_ = cargo1Bbls60F_;
        result.cargo1BblsDbs_ = cargo1BblsDbs_;
        result.cargo1Kl_ = cargo1Kl_;
        result.cargo1Lt_ = cargo1Lt_;
        result.cargo1Mt_ = cargo1Mt_;
        result.cargo1Percentage_ = cargo1Percentage_;
        result.cargo2Abbreviation_ = cargo2Abbreviation_;
        result.cargo2Bbls60F_ = cargo2Bbls60F_;
        result.cargo2BblsDbs_ = cargo2BblsDbs_;
        result.cargo2Kl_ = cargo2Kl_;
        result.cargo2Lt_ = cargo2Lt_;
        result.cargo2Mt_ = cargo2Mt_;
        result.cargo2Percentage_ = cargo2Percentage_;
        result.correctedUllage_ = correctedUllage_;
        result.correctionFactor_ = correctionFactor_;
        result.fillingRatio_ = fillingRatio_;
        result.grade_ = grade_;
        result.isActive_ = isActive_;
        result.loadablePatternId_ = loadablePatternId_;
        result.loadablePlanId_ = loadablePlanId_;
        result.loadingOrder_ = loadingOrder_;
        result.orderQuantity_ = orderQuantity_;
        result.priority_ = priority_;
        result.quantity_ = quantity_;
        result.rdgUllage_ = rdgUllage_;
        result.slopQuantity_ = slopQuantity_;
        result.tankId_ = tankId_;
        result.tankName_ = tankName_;
        result.temperature_ = temperature_;
        result.timeRequiredForLoading_ = timeRequiredForLoading_;
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
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .LoadablePlanCommingleDetail)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
              other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
                .getDefaultInstance()) return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (!other.getApi().isEmpty()) {
          api_ = other.api_;
          onChanged();
        }
        if (!other.getCargo1Abbreviation().isEmpty()) {
          cargo1Abbreviation_ = other.cargo1Abbreviation_;
          onChanged();
        }
        if (!other.getCargo1Bbls60F().isEmpty()) {
          cargo1Bbls60F_ = other.cargo1Bbls60F_;
          onChanged();
        }
        if (!other.getCargo1BblsDbs().isEmpty()) {
          cargo1BblsDbs_ = other.cargo1BblsDbs_;
          onChanged();
        }
        if (!other.getCargo1Kl().isEmpty()) {
          cargo1Kl_ = other.cargo1Kl_;
          onChanged();
        }
        if (!other.getCargo1Lt().isEmpty()) {
          cargo1Lt_ = other.cargo1Lt_;
          onChanged();
        }
        if (!other.getCargo1Mt().isEmpty()) {
          cargo1Mt_ = other.cargo1Mt_;
          onChanged();
        }
        if (!other.getCargo1Percentage().isEmpty()) {
          cargo1Percentage_ = other.cargo1Percentage_;
          onChanged();
        }
        if (!other.getCargo2Abbreviation().isEmpty()) {
          cargo2Abbreviation_ = other.cargo2Abbreviation_;
          onChanged();
        }
        if (!other.getCargo2Bbls60F().isEmpty()) {
          cargo2Bbls60F_ = other.cargo2Bbls60F_;
          onChanged();
        }
        if (!other.getCargo2BblsDbs().isEmpty()) {
          cargo2BblsDbs_ = other.cargo2BblsDbs_;
          onChanged();
        }
        if (!other.getCargo2Kl().isEmpty()) {
          cargo2Kl_ = other.cargo2Kl_;
          onChanged();
        }
        if (!other.getCargo2Lt().isEmpty()) {
          cargo2Lt_ = other.cargo2Lt_;
          onChanged();
        }
        if (!other.getCargo2Mt().isEmpty()) {
          cargo2Mt_ = other.cargo2Mt_;
          onChanged();
        }
        if (!other.getCargo2Percentage().isEmpty()) {
          cargo2Percentage_ = other.cargo2Percentage_;
          onChanged();
        }
        if (!other.getCorrectedUllage().isEmpty()) {
          correctedUllage_ = other.correctedUllage_;
          onChanged();
        }
        if (!other.getCorrectionFactor().isEmpty()) {
          correctionFactor_ = other.correctionFactor_;
          onChanged();
        }
        if (!other.getFillingRatio().isEmpty()) {
          fillingRatio_ = other.fillingRatio_;
          onChanged();
        }
        if (!other.getGrade().isEmpty()) {
          grade_ = other.grade_;
          onChanged();
        }
        if (other.getIsActive() != false) {
          setIsActive(other.getIsActive());
        }
        if (other.getLoadablePatternId() != 0L) {
          setLoadablePatternId(other.getLoadablePatternId());
        }
        if (other.getLoadablePlanId() != 0L) {
          setLoadablePlanId(other.getLoadablePlanId());
        }
        if (other.getLoadingOrder() != 0) {
          setLoadingOrder(other.getLoadingOrder());
        }
        if (!other.getOrderQuantity().isEmpty()) {
          orderQuantity_ = other.orderQuantity_;
          onChanged();
        }
        if (other.getPriority() != 0) {
          setPriority(other.getPriority());
        }
        if (!other.getQuantity().isEmpty()) {
          quantity_ = other.quantity_;
          onChanged();
        }
        if (!other.getRdgUllage().isEmpty()) {
          rdgUllage_ = other.rdgUllage_;
          onChanged();
        }
        if (!other.getSlopQuantity().isEmpty()) {
          slopQuantity_ = other.slopQuantity_;
          onChanged();
        }
        if (other.getTankId() != 0L) {
          setTankId(other.getTankId());
        }
        if (!other.getTankName().isEmpty()) {
          tankName_ = other.tankName_;
          onChanged();
        }
        if (!other.getTemperature().isEmpty()) {
          temperature_ = other.temperature_;
          onChanged();
        }
        if (!other.getTimeRequiredForLoading().isEmpty()) {
          timeRequiredForLoading_ = other.timeRequiredForLoading_;
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .LoadablePlanCommingleDetail)
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

      private java.lang.Object api_ = "";
      /**
       * <code>string api = 2;</code>
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
       * <code>string api = 2;</code>
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
       * <code>string api = 2;</code>
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
       * <code>string api = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearApi() {

        api_ = getDefaultInstance().getApi();
        onChanged();
        return this;
      }
      /**
       * <code>string api = 2;</code>
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

      private java.lang.Object cargo1Abbreviation_ = "";
      /**
       * <code>string cargo1Abbreviation = 3;</code>
       *
       * @return The cargo1Abbreviation.
       */
      public java.lang.String getCargo1Abbreviation() {
        java.lang.Object ref = cargo1Abbreviation_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo1Abbreviation_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo1Abbreviation = 3;</code>
       *
       * @return The bytes for cargo1Abbreviation.
       */
      public com.google.protobuf.ByteString getCargo1AbbreviationBytes() {
        java.lang.Object ref = cargo1Abbreviation_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo1Abbreviation_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo1Abbreviation = 3;</code>
       *
       * @param value The cargo1Abbreviation to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1Abbreviation(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo1Abbreviation_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Abbreviation = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo1Abbreviation() {

        cargo1Abbreviation_ = getDefaultInstance().getCargo1Abbreviation();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Abbreviation = 3;</code>
       *
       * @param value The bytes for cargo1Abbreviation to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1AbbreviationBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo1Abbreviation_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo1Bbls60F_ = "";
      /**
       * <code>string cargo1Bbls60f = 4;</code>
       *
       * @return The cargo1Bbls60f.
       */
      public java.lang.String getCargo1Bbls60F() {
        java.lang.Object ref = cargo1Bbls60F_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo1Bbls60F_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo1Bbls60f = 4;</code>
       *
       * @return The bytes for cargo1Bbls60f.
       */
      public com.google.protobuf.ByteString getCargo1Bbls60FBytes() {
        java.lang.Object ref = cargo1Bbls60F_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo1Bbls60F_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo1Bbls60f = 4;</code>
       *
       * @param value The cargo1Bbls60f to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1Bbls60F(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo1Bbls60F_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Bbls60f = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo1Bbls60F() {

        cargo1Bbls60F_ = getDefaultInstance().getCargo1Bbls60F();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Bbls60f = 4;</code>
       *
       * @param value The bytes for cargo1Bbls60f to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1Bbls60FBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo1Bbls60F_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo1BblsDbs_ = "";
      /**
       * <code>string cargo1BblsDbs = 5;</code>
       *
       * @return The cargo1BblsDbs.
       */
      public java.lang.String getCargo1BblsDbs() {
        java.lang.Object ref = cargo1BblsDbs_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo1BblsDbs_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo1BblsDbs = 5;</code>
       *
       * @return The bytes for cargo1BblsDbs.
       */
      public com.google.protobuf.ByteString getCargo1BblsDbsBytes() {
        java.lang.Object ref = cargo1BblsDbs_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo1BblsDbs_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo1BblsDbs = 5;</code>
       *
       * @param value The cargo1BblsDbs to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1BblsDbs(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo1BblsDbs_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1BblsDbs = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo1BblsDbs() {

        cargo1BblsDbs_ = getDefaultInstance().getCargo1BblsDbs();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1BblsDbs = 5;</code>
       *
       * @param value The bytes for cargo1BblsDbs to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1BblsDbsBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo1BblsDbs_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo1Kl_ = "";
      /**
       * <code>string cargo1Kl = 6;</code>
       *
       * @return The cargo1Kl.
       */
      public java.lang.String getCargo1Kl() {
        java.lang.Object ref = cargo1Kl_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo1Kl_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo1Kl = 6;</code>
       *
       * @return The bytes for cargo1Kl.
       */
      public com.google.protobuf.ByteString getCargo1KlBytes() {
        java.lang.Object ref = cargo1Kl_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo1Kl_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo1Kl = 6;</code>
       *
       * @param value The cargo1Kl to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1Kl(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo1Kl_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Kl = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo1Kl() {

        cargo1Kl_ = getDefaultInstance().getCargo1Kl();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Kl = 6;</code>
       *
       * @param value The bytes for cargo1Kl to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1KlBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo1Kl_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo1Lt_ = "";
      /**
       * <code>string cargo1Lt = 7;</code>
       *
       * @return The cargo1Lt.
       */
      public java.lang.String getCargo1Lt() {
        java.lang.Object ref = cargo1Lt_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo1Lt_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo1Lt = 7;</code>
       *
       * @return The bytes for cargo1Lt.
       */
      public com.google.protobuf.ByteString getCargo1LtBytes() {
        java.lang.Object ref = cargo1Lt_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo1Lt_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo1Lt = 7;</code>
       *
       * @param value The cargo1Lt to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1Lt(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo1Lt_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Lt = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo1Lt() {

        cargo1Lt_ = getDefaultInstance().getCargo1Lt();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Lt = 7;</code>
       *
       * @param value The bytes for cargo1Lt to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1LtBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo1Lt_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo1Mt_ = "";
      /**
       * <code>string cargo1Mt = 8;</code>
       *
       * @return The cargo1Mt.
       */
      public java.lang.String getCargo1Mt() {
        java.lang.Object ref = cargo1Mt_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo1Mt_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo1Mt = 8;</code>
       *
       * @return The bytes for cargo1Mt.
       */
      public com.google.protobuf.ByteString getCargo1MtBytes() {
        java.lang.Object ref = cargo1Mt_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo1Mt_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo1Mt = 8;</code>
       *
       * @param value The cargo1Mt to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1Mt(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo1Mt_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Mt = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo1Mt() {

        cargo1Mt_ = getDefaultInstance().getCargo1Mt();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Mt = 8;</code>
       *
       * @param value The bytes for cargo1Mt to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1MtBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo1Mt_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo1Percentage_ = "";
      /**
       * <code>string cargo1Percentage = 9;</code>
       *
       * @return The cargo1Percentage.
       */
      public java.lang.String getCargo1Percentage() {
        java.lang.Object ref = cargo1Percentage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo1Percentage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo1Percentage = 9;</code>
       *
       * @return The bytes for cargo1Percentage.
       */
      public com.google.protobuf.ByteString getCargo1PercentageBytes() {
        java.lang.Object ref = cargo1Percentage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo1Percentage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo1Percentage = 9;</code>
       *
       * @param value The cargo1Percentage to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1Percentage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo1Percentage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Percentage = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo1Percentage() {

        cargo1Percentage_ = getDefaultInstance().getCargo1Percentage();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo1Percentage = 9;</code>
       *
       * @param value The bytes for cargo1Percentage to set.
       * @return This builder for chaining.
       */
      public Builder setCargo1PercentageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo1Percentage_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo2Abbreviation_ = "";
      /**
       * <code>string cargo2Abbreviation = 10;</code>
       *
       * @return The cargo2Abbreviation.
       */
      public java.lang.String getCargo2Abbreviation() {
        java.lang.Object ref = cargo2Abbreviation_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo2Abbreviation_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo2Abbreviation = 10;</code>
       *
       * @return The bytes for cargo2Abbreviation.
       */
      public com.google.protobuf.ByteString getCargo2AbbreviationBytes() {
        java.lang.Object ref = cargo2Abbreviation_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo2Abbreviation_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo2Abbreviation = 10;</code>
       *
       * @param value The cargo2Abbreviation to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2Abbreviation(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo2Abbreviation_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Abbreviation = 10;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo2Abbreviation() {

        cargo2Abbreviation_ = getDefaultInstance().getCargo2Abbreviation();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Abbreviation = 10;</code>
       *
       * @param value The bytes for cargo2Abbreviation to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2AbbreviationBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo2Abbreviation_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo2Bbls60F_ = "";
      /**
       * <code>string cargo2Bbls60f = 11;</code>
       *
       * @return The cargo2Bbls60f.
       */
      public java.lang.String getCargo2Bbls60F() {
        java.lang.Object ref = cargo2Bbls60F_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo2Bbls60F_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo2Bbls60f = 11;</code>
       *
       * @return The bytes for cargo2Bbls60f.
       */
      public com.google.protobuf.ByteString getCargo2Bbls60FBytes() {
        java.lang.Object ref = cargo2Bbls60F_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo2Bbls60F_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo2Bbls60f = 11;</code>
       *
       * @param value The cargo2Bbls60f to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2Bbls60F(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo2Bbls60F_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Bbls60f = 11;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo2Bbls60F() {

        cargo2Bbls60F_ = getDefaultInstance().getCargo2Bbls60F();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Bbls60f = 11;</code>
       *
       * @param value The bytes for cargo2Bbls60f to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2Bbls60FBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo2Bbls60F_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo2BblsDbs_ = "";
      /**
       * <code>string cargo2BblsDbs = 12;</code>
       *
       * @return The cargo2BblsDbs.
       */
      public java.lang.String getCargo2BblsDbs() {
        java.lang.Object ref = cargo2BblsDbs_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo2BblsDbs_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo2BblsDbs = 12;</code>
       *
       * @return The bytes for cargo2BblsDbs.
       */
      public com.google.protobuf.ByteString getCargo2BblsDbsBytes() {
        java.lang.Object ref = cargo2BblsDbs_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo2BblsDbs_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo2BblsDbs = 12;</code>
       *
       * @param value The cargo2BblsDbs to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2BblsDbs(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo2BblsDbs_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2BblsDbs = 12;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo2BblsDbs() {

        cargo2BblsDbs_ = getDefaultInstance().getCargo2BblsDbs();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2BblsDbs = 12;</code>
       *
       * @param value The bytes for cargo2BblsDbs to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2BblsDbsBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo2BblsDbs_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo2Kl_ = "";
      /**
       * <code>string cargo2Kl = 13;</code>
       *
       * @return The cargo2Kl.
       */
      public java.lang.String getCargo2Kl() {
        java.lang.Object ref = cargo2Kl_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo2Kl_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo2Kl = 13;</code>
       *
       * @return The bytes for cargo2Kl.
       */
      public com.google.protobuf.ByteString getCargo2KlBytes() {
        java.lang.Object ref = cargo2Kl_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo2Kl_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo2Kl = 13;</code>
       *
       * @param value The cargo2Kl to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2Kl(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo2Kl_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Kl = 13;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo2Kl() {

        cargo2Kl_ = getDefaultInstance().getCargo2Kl();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Kl = 13;</code>
       *
       * @param value The bytes for cargo2Kl to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2KlBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo2Kl_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo2Lt_ = "";
      /**
       * <code>string cargo2Lt = 14;</code>
       *
       * @return The cargo2Lt.
       */
      public java.lang.String getCargo2Lt() {
        java.lang.Object ref = cargo2Lt_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo2Lt_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo2Lt = 14;</code>
       *
       * @return The bytes for cargo2Lt.
       */
      public com.google.protobuf.ByteString getCargo2LtBytes() {
        java.lang.Object ref = cargo2Lt_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo2Lt_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo2Lt = 14;</code>
       *
       * @param value The cargo2Lt to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2Lt(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo2Lt_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Lt = 14;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo2Lt() {

        cargo2Lt_ = getDefaultInstance().getCargo2Lt();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Lt = 14;</code>
       *
       * @param value The bytes for cargo2Lt to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2LtBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo2Lt_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo2Mt_ = "";
      /**
       * <code>string cargo2Mt = 15;</code>
       *
       * @return The cargo2Mt.
       */
      public java.lang.String getCargo2Mt() {
        java.lang.Object ref = cargo2Mt_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo2Mt_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo2Mt = 15;</code>
       *
       * @return The bytes for cargo2Mt.
       */
      public com.google.protobuf.ByteString getCargo2MtBytes() {
        java.lang.Object ref = cargo2Mt_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo2Mt_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo2Mt = 15;</code>
       *
       * @param value The cargo2Mt to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2Mt(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo2Mt_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Mt = 15;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo2Mt() {

        cargo2Mt_ = getDefaultInstance().getCargo2Mt();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Mt = 15;</code>
       *
       * @param value The bytes for cargo2Mt to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2MtBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo2Mt_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargo2Percentage_ = "";
      /**
       * <code>string cargo2Percentage = 16;</code>
       *
       * @return The cargo2Percentage.
       */
      public java.lang.String getCargo2Percentage() {
        java.lang.Object ref = cargo2Percentage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargo2Percentage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargo2Percentage = 16;</code>
       *
       * @return The bytes for cargo2Percentage.
       */
      public com.google.protobuf.ByteString getCargo2PercentageBytes() {
        java.lang.Object ref = cargo2Percentage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargo2Percentage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargo2Percentage = 16;</code>
       *
       * @param value The cargo2Percentage to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2Percentage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargo2Percentage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Percentage = 16;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargo2Percentage() {

        cargo2Percentage_ = getDefaultInstance().getCargo2Percentage();
        onChanged();
        return this;
      }
      /**
       * <code>string cargo2Percentage = 16;</code>
       *
       * @param value The bytes for cargo2Percentage to set.
       * @return This builder for chaining.
       */
      public Builder setCargo2PercentageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargo2Percentage_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object correctedUllage_ = "";
      /**
       * <code>string correctedUllage = 17;</code>
       *
       * @return The correctedUllage.
       */
      public java.lang.String getCorrectedUllage() {
        java.lang.Object ref = correctedUllage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          correctedUllage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string correctedUllage = 17;</code>
       *
       * @return The bytes for correctedUllage.
       */
      public com.google.protobuf.ByteString getCorrectedUllageBytes() {
        java.lang.Object ref = correctedUllage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          correctedUllage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string correctedUllage = 17;</code>
       *
       * @param value The correctedUllage to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectedUllage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        correctedUllage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string correctedUllage = 17;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCorrectedUllage() {

        correctedUllage_ = getDefaultInstance().getCorrectedUllage();
        onChanged();
        return this;
      }
      /**
       * <code>string correctedUllage = 17;</code>
       *
       * @param value The bytes for correctedUllage to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectedUllageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        correctedUllage_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object correctionFactor_ = "";
      /**
       * <code>string correctionFactor = 18;</code>
       *
       * @return The correctionFactor.
       */
      public java.lang.String getCorrectionFactor() {
        java.lang.Object ref = correctionFactor_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          correctionFactor_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string correctionFactor = 18;</code>
       *
       * @return The bytes for correctionFactor.
       */
      public com.google.protobuf.ByteString getCorrectionFactorBytes() {
        java.lang.Object ref = correctionFactor_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          correctionFactor_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string correctionFactor = 18;</code>
       *
       * @param value The correctionFactor to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectionFactor(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        correctionFactor_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string correctionFactor = 18;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCorrectionFactor() {

        correctionFactor_ = getDefaultInstance().getCorrectionFactor();
        onChanged();
        return this;
      }
      /**
       * <code>string correctionFactor = 18;</code>
       *
       * @param value The bytes for correctionFactor to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectionFactorBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        correctionFactor_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object fillingRatio_ = "";
      /**
       * <code>string fillingRatio = 19;</code>
       *
       * @return The fillingRatio.
       */
      public java.lang.String getFillingRatio() {
        java.lang.Object ref = fillingRatio_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          fillingRatio_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string fillingRatio = 19;</code>
       *
       * @return The bytes for fillingRatio.
       */
      public com.google.protobuf.ByteString getFillingRatioBytes() {
        java.lang.Object ref = fillingRatio_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          fillingRatio_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string fillingRatio = 19;</code>
       *
       * @param value The fillingRatio to set.
       * @return This builder for chaining.
       */
      public Builder setFillingRatio(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        fillingRatio_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string fillingRatio = 19;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearFillingRatio() {

        fillingRatio_ = getDefaultInstance().getFillingRatio();
        onChanged();
        return this;
      }
      /**
       * <code>string fillingRatio = 19;</code>
       *
       * @param value The bytes for fillingRatio to set.
       * @return This builder for chaining.
       */
      public Builder setFillingRatioBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        fillingRatio_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object grade_ = "";
      /**
       * <code>string grade = 20;</code>
       *
       * @return The grade.
       */
      public java.lang.String getGrade() {
        java.lang.Object ref = grade_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          grade_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string grade = 20;</code>
       *
       * @return The bytes for grade.
       */
      public com.google.protobuf.ByteString getGradeBytes() {
        java.lang.Object ref = grade_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          grade_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string grade = 20;</code>
       *
       * @param value The grade to set.
       * @return This builder for chaining.
       */
      public Builder setGrade(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        grade_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string grade = 20;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearGrade() {

        grade_ = getDefaultInstance().getGrade();
        onChanged();
        return this;
      }
      /**
       * <code>string grade = 20;</code>
       *
       * @param value The bytes for grade to set.
       * @return This builder for chaining.
       */
      public Builder setGradeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        grade_ = value;
        onChanged();
        return this;
      }

      private boolean isActive_;
      /**
       * <code>bool isActive = 21;</code>
       *
       * @return The isActive.
       */
      public boolean getIsActive() {
        return isActive_;
      }
      /**
       * <code>bool isActive = 21;</code>
       *
       * @param value The isActive to set.
       * @return This builder for chaining.
       */
      public Builder setIsActive(boolean value) {

        isActive_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool isActive = 21;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearIsActive() {

        isActive_ = false;
        onChanged();
        return this;
      }

      private long loadablePatternId_;
      /**
       * <code>int64 loadablePatternId = 22;</code>
       *
       * @return The loadablePatternId.
       */
      public long getLoadablePatternId() {
        return loadablePatternId_;
      }
      /**
       * <code>int64 loadablePatternId = 22;</code>
       *
       * @param value The loadablePatternId to set.
       * @return This builder for chaining.
       */
      public Builder setLoadablePatternId(long value) {

        loadablePatternId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 loadablePatternId = 22;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadablePatternId() {

        loadablePatternId_ = 0L;
        onChanged();
        return this;
      }

      private long loadablePlanId_;
      /**
       * <code>int64 loadablePlanId = 23;</code>
       *
       * @return The loadablePlanId.
       */
      public long getLoadablePlanId() {
        return loadablePlanId_;
      }
      /**
       * <code>int64 loadablePlanId = 23;</code>
       *
       * @param value The loadablePlanId to set.
       * @return This builder for chaining.
       */
      public Builder setLoadablePlanId(long value) {

        loadablePlanId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 loadablePlanId = 23;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadablePlanId() {

        loadablePlanId_ = 0L;
        onChanged();
        return this;
      }

      private int loadingOrder_;
      /**
       * <code>int32 loadingOrder = 24;</code>
       *
       * @return The loadingOrder.
       */
      public int getLoadingOrder() {
        return loadingOrder_;
      }
      /**
       * <code>int32 loadingOrder = 24;</code>
       *
       * @param value The loadingOrder to set.
       * @return This builder for chaining.
       */
      public Builder setLoadingOrder(int value) {

        loadingOrder_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 loadingOrder = 24;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadingOrder() {

        loadingOrder_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object orderQuantity_ = "";
      /**
       * <code>string orderQuantity = 25;</code>
       *
       * @return The orderQuantity.
       */
      public java.lang.String getOrderQuantity() {
        java.lang.Object ref = orderQuantity_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          orderQuantity_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string orderQuantity = 25;</code>
       *
       * @return The bytes for orderQuantity.
       */
      public com.google.protobuf.ByteString getOrderQuantityBytes() {
        java.lang.Object ref = orderQuantity_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          orderQuantity_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string orderQuantity = 25;</code>
       *
       * @param value The orderQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setOrderQuantity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        orderQuantity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string orderQuantity = 25;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearOrderQuantity() {

        orderQuantity_ = getDefaultInstance().getOrderQuantity();
        onChanged();
        return this;
      }
      /**
       * <code>string orderQuantity = 25;</code>
       *
       * @param value The bytes for orderQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setOrderQuantityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        orderQuantity_ = value;
        onChanged();
        return this;
      }

      private int priority_;
      /**
       * <code>int32 priority = 26;</code>
       *
       * @return The priority.
       */
      public int getPriority() {
        return priority_;
      }
      /**
       * <code>int32 priority = 26;</code>
       *
       * @param value The priority to set.
       * @return This builder for chaining.
       */
      public Builder setPriority(int value) {

        priority_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 priority = 26;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPriority() {

        priority_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object quantity_ = "";
      /**
       * <code>string quantity = 27;</code>
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
       * <code>string quantity = 27;</code>
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
       * <code>string quantity = 27;</code>
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
       * <code>string quantity = 27;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearQuantity() {

        quantity_ = getDefaultInstance().getQuantity();
        onChanged();
        return this;
      }
      /**
       * <code>string quantity = 27;</code>
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

      private java.lang.Object rdgUllage_ = "";
      /**
       * <code>string rdgUllage = 28;</code>
       *
       * @return The rdgUllage.
       */
      public java.lang.String getRdgUllage() {
        java.lang.Object ref = rdgUllage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          rdgUllage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string rdgUllage = 28;</code>
       *
       * @return The bytes for rdgUllage.
       */
      public com.google.protobuf.ByteString getRdgUllageBytes() {
        java.lang.Object ref = rdgUllage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          rdgUllage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string rdgUllage = 28;</code>
       *
       * @param value The rdgUllage to set.
       * @return This builder for chaining.
       */
      public Builder setRdgUllage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        rdgUllage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string rdgUllage = 28;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearRdgUllage() {

        rdgUllage_ = getDefaultInstance().getRdgUllage();
        onChanged();
        return this;
      }
      /**
       * <code>string rdgUllage = 28;</code>
       *
       * @param value The bytes for rdgUllage to set.
       * @return This builder for chaining.
       */
      public Builder setRdgUllageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        rdgUllage_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object slopQuantity_ = "";
      /**
       * <code>string slopQuantity = 29;</code>
       *
       * @return The slopQuantity.
       */
      public java.lang.String getSlopQuantity() {
        java.lang.Object ref = slopQuantity_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          slopQuantity_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string slopQuantity = 29;</code>
       *
       * @return The bytes for slopQuantity.
       */
      public com.google.protobuf.ByteString getSlopQuantityBytes() {
        java.lang.Object ref = slopQuantity_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          slopQuantity_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string slopQuantity = 29;</code>
       *
       * @param value The slopQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setSlopQuantity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        slopQuantity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string slopQuantity = 29;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSlopQuantity() {

        slopQuantity_ = getDefaultInstance().getSlopQuantity();
        onChanged();
        return this;
      }
      /**
       * <code>string slopQuantity = 29;</code>
       *
       * @param value The bytes for slopQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setSlopQuantityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        slopQuantity_ = value;
        onChanged();
        return this;
      }

      private long tankId_;
      /**
       * <code>int64 tankId = 30;</code>
       *
       * @return The tankId.
       */
      public long getTankId() {
        return tankId_;
      }
      /**
       * <code>int64 tankId = 30;</code>
       *
       * @param value The tankId to set.
       * @return This builder for chaining.
       */
      public Builder setTankId(long value) {

        tankId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 tankId = 30;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTankId() {

        tankId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object tankName_ = "";
      /**
       * <code>string tankName = 31;</code>
       *
       * @return The tankName.
       */
      public java.lang.String getTankName() {
        java.lang.Object ref = tankName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          tankName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string tankName = 31;</code>
       *
       * @return The bytes for tankName.
       */
      public com.google.protobuf.ByteString getTankNameBytes() {
        java.lang.Object ref = tankName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          tankName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string tankName = 31;</code>
       *
       * @param value The tankName to set.
       * @return This builder for chaining.
       */
      public Builder setTankName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        tankName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string tankName = 31;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTankName() {

        tankName_ = getDefaultInstance().getTankName();
        onChanged();
        return this;
      }
      /**
       * <code>string tankName = 31;</code>
       *
       * @param value The bytes for tankName to set.
       * @return This builder for chaining.
       */
      public Builder setTankNameBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        tankName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object temperature_ = "";
      /**
       * <code>string temperature = 32;</code>
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
       * <code>string temperature = 32;</code>
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
       * <code>string temperature = 32;</code>
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
       * <code>string temperature = 32;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTemperature() {

        temperature_ = getDefaultInstance().getTemperature();
        onChanged();
        return this;
      }
      /**
       * <code>string temperature = 32;</code>
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

      private java.lang.Object timeRequiredForLoading_ = "";
      /**
       * <code>string timeRequiredForLoading = 33;</code>
       *
       * @return The timeRequiredForLoading.
       */
      public java.lang.String getTimeRequiredForLoading() {
        java.lang.Object ref = timeRequiredForLoading_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          timeRequiredForLoading_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string timeRequiredForLoading = 33;</code>
       *
       * @return The bytes for timeRequiredForLoading.
       */
      public com.google.protobuf.ByteString getTimeRequiredForLoadingBytes() {
        java.lang.Object ref = timeRequiredForLoading_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          timeRequiredForLoading_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string timeRequiredForLoading = 33;</code>
       *
       * @param value The timeRequiredForLoading to set.
       * @return This builder for chaining.
       */
      public Builder setTimeRequiredForLoading(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        timeRequiredForLoading_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string timeRequiredForLoading = 33;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTimeRequiredForLoading() {

        timeRequiredForLoading_ = getDefaultInstance().getTimeRequiredForLoading();
        onChanged();
        return this;
      }
      /**
       * <code>string timeRequiredForLoading = 33;</code>
       *
       * @param value The bytes for timeRequiredForLoading to set.
       * @return This builder for chaining.
       */
      public Builder setTimeRequiredForLoadingBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        timeRequiredForLoading_ = value;
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

      // @@protoc_insertion_point(builder_scope:LoadablePlanCommingleDetail)
    }

    // @@protoc_insertion_point(class_scope:LoadablePlanCommingleDetail)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadablePlanCommingleDetail();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanCommingleDetail
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadablePlanCommingleDetail> PARSER =
        new com.google.protobuf.AbstractParser<LoadablePlanCommingleDetail>() {
          @java.lang.Override
          public LoadablePlanCommingleDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadablePlanCommingleDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadablePlanCommingleDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadablePlanCommingleDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleDetail
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadablePlanQuantityOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadablePlanQuantity)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string cargoAbbreviation = 1;</code>
     *
     * @return The cargoAbbreviation.
     */
    java.lang.String getCargoAbbreviation();
    /**
     * <code>string cargoAbbreviation = 1;</code>
     *
     * @return The bytes for cargoAbbreviation.
     */
    com.google.protobuf.ByteString getCargoAbbreviationBytes();

    /**
     * <code>string cargoColor = 2;</code>
     *
     * @return The cargoColor.
     */
    java.lang.String getCargoColor();
    /**
     * <code>string cargoColor = 2;</code>
     *
     * @return The bytes for cargoColor.
     */
    com.google.protobuf.ByteString getCargoColorBytes();

    /**
     * <code>int64 cargoNominationId = 3;</code>
     *
     * @return The cargoNominationId.
     */
    long getCargoNominationId();

    /**
     * <code>int64 cargoId = 4;</code>
     *
     * @return The cargoId.
     */
    long getCargoId();

    /**
     * <code>string differenceColor = 5;</code>
     *
     * @return The differenceColor.
     */
    java.lang.String getDifferenceColor();
    /**
     * <code>string differenceColor = 5;</code>
     *
     * @return The bytes for differenceColor.
     */
    com.google.protobuf.ByteString getDifferenceColorBytes();

    /**
     * <code>string differencePercentage = 6;</code>
     *
     * @return The differencePercentage.
     */
    java.lang.String getDifferencePercentage();
    /**
     * <code>string differencePercentage = 6;</code>
     *
     * @return The bytes for differencePercentage.
     */
    com.google.protobuf.ByteString getDifferencePercentageBytes();

    /**
     * <code>string estimatedApi = 7;</code>
     *
     * @return The estimatedApi.
     */
    java.lang.String getEstimatedApi();
    /**
     * <code>string estimatedApi = 7;</code>
     *
     * @return The bytes for estimatedApi.
     */
    com.google.protobuf.ByteString getEstimatedApiBytes();

    /**
     * <code>string estimatedTemperature = 8;</code>
     *
     * @return The estimatedTemperature.
     */
    java.lang.String getEstimatedTemperature();
    /**
     * <code>string estimatedTemperature = 8;</code>
     *
     * @return The bytes for estimatedTemperature.
     */
    com.google.protobuf.ByteString getEstimatedTemperatureBytes();

    /**
     * <code>string grade = 9;</code>
     *
     * @return The grade.
     */
    java.lang.String getGrade();
    /**
     * <code>string grade = 9;</code>
     *
     * @return The bytes for grade.
     */
    com.google.protobuf.ByteString getGradeBytes();

    /**
     * <code>int64 id = 10;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>bool isActive = 11;</code>
     *
     * @return The isActive.
     */
    boolean getIsActive();

    /**
     * <code>string loadableBbls60f = 12;</code>
     *
     * @return The loadableBbls60f.
     */
    java.lang.String getLoadableBbls60F();
    /**
     * <code>string loadableBbls60f = 12;</code>
     *
     * @return The bytes for loadableBbls60f.
     */
    com.google.protobuf.ByteString getLoadableBbls60FBytes();

    /**
     * <code>string loadableBblsDbs = 13;</code>
     *
     * @return The loadableBblsDbs.
     */
    java.lang.String getLoadableBblsDbs();
    /**
     * <code>string loadableBblsDbs = 13;</code>
     *
     * @return The bytes for loadableBblsDbs.
     */
    com.google.protobuf.ByteString getLoadableBblsDbsBytes();

    /**
     * <code>string loadableKl = 14;</code>
     *
     * @return The loadableKl.
     */
    java.lang.String getLoadableKl();
    /**
     * <code>string loadableKl = 14;</code>
     *
     * @return The bytes for loadableKl.
     */
    com.google.protobuf.ByteString getLoadableKlBytes();

    /**
     * <code>string loadableLt = 15;</code>
     *
     * @return The loadableLt.
     */
    java.lang.String getLoadableLt();
    /**
     * <code>string loadableLt = 15;</code>
     *
     * @return The bytes for loadableLt.
     */
    com.google.protobuf.ByteString getLoadableLtBytes();

    /**
     * <code>string loadableMt = 16;</code>
     *
     * @return The loadableMt.
     */
    java.lang.String getLoadableMt();
    /**
     * <code>string loadableMt = 16;</code>
     *
     * @return The bytes for loadableMt.
     */
    com.google.protobuf.ByteString getLoadableMtBytes();

    /**
     * <code>int64 loadablePatternId = 17;</code>
     *
     * @return The loadablePatternId.
     */
    long getLoadablePatternId();

    /**
     * <code>int64 loadablePlanId = 18;</code>
     *
     * @return The loadablePlanId.
     */
    long getLoadablePlanId();

    /**
     * <code>string loadableQuantity = 19;</code>
     *
     * @return The loadableQuantity.
     */
    java.lang.String getLoadableQuantity();
    /**
     * <code>string loadableQuantity = 19;</code>
     *
     * @return The bytes for loadableQuantity.
     */
    com.google.protobuf.ByteString getLoadableQuantityBytes();

    /**
     * <code>int32 loadingOrder = 20;</code>
     *
     * @return The loadingOrder.
     */
    int getLoadingOrder();

    /**
     * <code>string maxTolerence = 21;</code>
     *
     * @return The maxTolerence.
     */
    java.lang.String getMaxTolerence();
    /**
     * <code>string maxTolerence = 21;</code>
     *
     * @return The bytes for maxTolerence.
     */
    com.google.protobuf.ByteString getMaxTolerenceBytes();

    /**
     * <code>string minTolerence = 22;</code>
     *
     * @return The minTolerence.
     */
    java.lang.String getMinTolerence();
    /**
     * <code>string minTolerence = 22;</code>
     *
     * @return The bytes for minTolerence.
     */
    com.google.protobuf.ByteString getMinTolerenceBytes();

    /**
     * <code>string orderBbls60f = 23;</code>
     *
     * @return The orderBbls60f.
     */
    java.lang.String getOrderBbls60F();
    /**
     * <code>string orderBbls60f = 23;</code>
     *
     * @return The bytes for orderBbls60f.
     */
    com.google.protobuf.ByteString getOrderBbls60FBytes();

    /**
     * <code>string orderBblsDbs = 24;</code>
     *
     * @return The orderBblsDbs.
     */
    java.lang.String getOrderBblsDbs();
    /**
     * <code>string orderBblsDbs = 24;</code>
     *
     * @return The bytes for orderBblsDbs.
     */
    com.google.protobuf.ByteString getOrderBblsDbsBytes();

    /**
     * <code>string orderQuantity = 25;</code>
     *
     * @return The orderQuantity.
     */
    java.lang.String getOrderQuantity();
    /**
     * <code>string orderQuantity = 25;</code>
     *
     * @return The bytes for orderQuantity.
     */
    com.google.protobuf.ByteString getOrderQuantityBytes();

    /**
     * <code>int32 priority = 26;</code>
     *
     * @return The priority.
     */
    int getPriority();

    /**
     * <code>string slopQuantity = 27;</code>
     *
     * @return The slopQuantity.
     */
    java.lang.String getSlopQuantity();
    /**
     * <code>string slopQuantity = 27;</code>
     *
     * @return The bytes for slopQuantity.
     */
    com.google.protobuf.ByteString getSlopQuantityBytes();

    /**
     * <code>string timeRequiredForLoading = 28;</code>
     *
     * @return The timeRequiredForLoading.
     */
    java.lang.String getTimeRequiredForLoading();
    /**
     * <code>string timeRequiredForLoading = 28;</code>
     *
     * @return The bytes for timeRequiredForLoading.
     */
    com.google.protobuf.ByteString getTimeRequiredForLoadingBytes();
  }
  /** Protobuf type {@code LoadablePlanQuantity} */
  public static final class LoadablePlanQuantity extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadablePlanQuantity)
      LoadablePlanQuantityOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadablePlanQuantity.newBuilder() to construct.
    private LoadablePlanQuantity(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadablePlanQuantity() {
      cargoAbbreviation_ = "";
      cargoColor_ = "";
      differenceColor_ = "";
      differencePercentage_ = "";
      estimatedApi_ = "";
      estimatedTemperature_ = "";
      grade_ = "";
      loadableBbls60F_ = "";
      loadableBblsDbs_ = "";
      loadableKl_ = "";
      loadableLt_ = "";
      loadableMt_ = "";
      loadableQuantity_ = "";
      maxTolerence_ = "";
      minTolerence_ = "";
      orderBbls60F_ = "";
      orderBblsDbs_ = "";
      orderQuantity_ = "";
      slopQuantity_ = "";
      timeRequiredForLoading_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadablePlanQuantity();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadablePlanQuantity(
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

                cargoAbbreviation_ = s;
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargoColor_ = s;
                break;
              }
            case 24:
              {
                cargoNominationId_ = input.readInt64();
                break;
              }
            case 32:
              {
                cargoId_ = input.readInt64();
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                differenceColor_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                differencePercentage_ = s;
                break;
              }
            case 58:
              {
                java.lang.String s = input.readStringRequireUtf8();

                estimatedApi_ = s;
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                estimatedTemperature_ = s;
                break;
              }
            case 74:
              {
                java.lang.String s = input.readStringRequireUtf8();

                grade_ = s;
                break;
              }
            case 80:
              {
                id_ = input.readInt64();
                break;
              }
            case 88:
              {
                isActive_ = input.readBool();
                break;
              }
            case 98:
              {
                java.lang.String s = input.readStringRequireUtf8();

                loadableBbls60F_ = s;
                break;
              }
            case 106:
              {
                java.lang.String s = input.readStringRequireUtf8();

                loadableBblsDbs_ = s;
                break;
              }
            case 114:
              {
                java.lang.String s = input.readStringRequireUtf8();

                loadableKl_ = s;
                break;
              }
            case 122:
              {
                java.lang.String s = input.readStringRequireUtf8();

                loadableLt_ = s;
                break;
              }
            case 130:
              {
                java.lang.String s = input.readStringRequireUtf8();

                loadableMt_ = s;
                break;
              }
            case 136:
              {
                loadablePatternId_ = input.readInt64();
                break;
              }
            case 144:
              {
                loadablePlanId_ = input.readInt64();
                break;
              }
            case 154:
              {
                java.lang.String s = input.readStringRequireUtf8();

                loadableQuantity_ = s;
                break;
              }
            case 160:
              {
                loadingOrder_ = input.readInt32();
                break;
              }
            case 170:
              {
                java.lang.String s = input.readStringRequireUtf8();

                maxTolerence_ = s;
                break;
              }
            case 178:
              {
                java.lang.String s = input.readStringRequireUtf8();

                minTolerence_ = s;
                break;
              }
            case 186:
              {
                java.lang.String s = input.readStringRequireUtf8();

                orderBbls60F_ = s;
                break;
              }
            case 194:
              {
                java.lang.String s = input.readStringRequireUtf8();

                orderBblsDbs_ = s;
                break;
              }
            case 202:
              {
                java.lang.String s = input.readStringRequireUtf8();

                orderQuantity_ = s;
                break;
              }
            case 208:
              {
                priority_ = input.readInt32();
                break;
              }
            case 218:
              {
                java.lang.String s = input.readStringRequireUtf8();

                slopQuantity_ = s;
                break;
              }
            case 226:
              {
                java.lang.String s = input.readStringRequireUtf8();

                timeRequiredForLoading_ = s;
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
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadablePlanQuantity_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadablePlanQuantity_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity.class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity.Builder
                  .class);
    }

    public static final int CARGOABBREVIATION_FIELD_NUMBER = 1;
    private volatile java.lang.Object cargoAbbreviation_;
    /**
     * <code>string cargoAbbreviation = 1;</code>
     *
     * @return The cargoAbbreviation.
     */
    public java.lang.String getCargoAbbreviation() {
      java.lang.Object ref = cargoAbbreviation_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargoAbbreviation_ = s;
        return s;
      }
    }
    /**
     * <code>string cargoAbbreviation = 1;</code>
     *
     * @return The bytes for cargoAbbreviation.
     */
    public com.google.protobuf.ByteString getCargoAbbreviationBytes() {
      java.lang.Object ref = cargoAbbreviation_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargoAbbreviation_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGOCOLOR_FIELD_NUMBER = 2;
    private volatile java.lang.Object cargoColor_;
    /**
     * <code>string cargoColor = 2;</code>
     *
     * @return The cargoColor.
     */
    public java.lang.String getCargoColor() {
      java.lang.Object ref = cargoColor_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargoColor_ = s;
        return s;
      }
    }
    /**
     * <code>string cargoColor = 2;</code>
     *
     * @return The bytes for cargoColor.
     */
    public com.google.protobuf.ByteString getCargoColorBytes() {
      java.lang.Object ref = cargoColor_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargoColor_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGONOMINATIONID_FIELD_NUMBER = 3;
    private long cargoNominationId_;
    /**
     * <code>int64 cargoNominationId = 3;</code>
     *
     * @return The cargoNominationId.
     */
    public long getCargoNominationId() {
      return cargoNominationId_;
    }

    public static final int CARGOID_FIELD_NUMBER = 4;
    private long cargoId_;
    /**
     * <code>int64 cargoId = 4;</code>
     *
     * @return The cargoId.
     */
    public long getCargoId() {
      return cargoId_;
    }

    public static final int DIFFERENCECOLOR_FIELD_NUMBER = 5;
    private volatile java.lang.Object differenceColor_;
    /**
     * <code>string differenceColor = 5;</code>
     *
     * @return The differenceColor.
     */
    public java.lang.String getDifferenceColor() {
      java.lang.Object ref = differenceColor_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        differenceColor_ = s;
        return s;
      }
    }
    /**
     * <code>string differenceColor = 5;</code>
     *
     * @return The bytes for differenceColor.
     */
    public com.google.protobuf.ByteString getDifferenceColorBytes() {
      java.lang.Object ref = differenceColor_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        differenceColor_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DIFFERENCEPERCENTAGE_FIELD_NUMBER = 6;
    private volatile java.lang.Object differencePercentage_;
    /**
     * <code>string differencePercentage = 6;</code>
     *
     * @return The differencePercentage.
     */
    public java.lang.String getDifferencePercentage() {
      java.lang.Object ref = differencePercentage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        differencePercentage_ = s;
        return s;
      }
    }
    /**
     * <code>string differencePercentage = 6;</code>
     *
     * @return The bytes for differencePercentage.
     */
    public com.google.protobuf.ByteString getDifferencePercentageBytes() {
      java.lang.Object ref = differencePercentage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        differencePercentage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ESTIMATEDAPI_FIELD_NUMBER = 7;
    private volatile java.lang.Object estimatedApi_;
    /**
     * <code>string estimatedApi = 7;</code>
     *
     * @return The estimatedApi.
     */
    public java.lang.String getEstimatedApi() {
      java.lang.Object ref = estimatedApi_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        estimatedApi_ = s;
        return s;
      }
    }
    /**
     * <code>string estimatedApi = 7;</code>
     *
     * @return The bytes for estimatedApi.
     */
    public com.google.protobuf.ByteString getEstimatedApiBytes() {
      java.lang.Object ref = estimatedApi_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        estimatedApi_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ESTIMATEDTEMPERATURE_FIELD_NUMBER = 8;
    private volatile java.lang.Object estimatedTemperature_;
    /**
     * <code>string estimatedTemperature = 8;</code>
     *
     * @return The estimatedTemperature.
     */
    public java.lang.String getEstimatedTemperature() {
      java.lang.Object ref = estimatedTemperature_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        estimatedTemperature_ = s;
        return s;
      }
    }
    /**
     * <code>string estimatedTemperature = 8;</code>
     *
     * @return The bytes for estimatedTemperature.
     */
    public com.google.protobuf.ByteString getEstimatedTemperatureBytes() {
      java.lang.Object ref = estimatedTemperature_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        estimatedTemperature_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int GRADE_FIELD_NUMBER = 9;
    private volatile java.lang.Object grade_;
    /**
     * <code>string grade = 9;</code>
     *
     * @return The grade.
     */
    public java.lang.String getGrade() {
      java.lang.Object ref = grade_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        grade_ = s;
        return s;
      }
    }
    /**
     * <code>string grade = 9;</code>
     *
     * @return The bytes for grade.
     */
    public com.google.protobuf.ByteString getGradeBytes() {
      java.lang.Object ref = grade_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        grade_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ID_FIELD_NUMBER = 10;
    private long id_;
    /**
     * <code>int64 id = 10;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
    }

    public static final int ISACTIVE_FIELD_NUMBER = 11;
    private boolean isActive_;
    /**
     * <code>bool isActive = 11;</code>
     *
     * @return The isActive.
     */
    public boolean getIsActive() {
      return isActive_;
    }

    public static final int LOADABLEBBLS60F_FIELD_NUMBER = 12;
    private volatile java.lang.Object loadableBbls60F_;
    /**
     * <code>string loadableBbls60f = 12;</code>
     *
     * @return The loadableBbls60f.
     */
    public java.lang.String getLoadableBbls60F() {
      java.lang.Object ref = loadableBbls60F_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        loadableBbls60F_ = s;
        return s;
      }
    }
    /**
     * <code>string loadableBbls60f = 12;</code>
     *
     * @return The bytes for loadableBbls60f.
     */
    public com.google.protobuf.ByteString getLoadableBbls60FBytes() {
      java.lang.Object ref = loadableBbls60F_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        loadableBbls60F_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOADABLEBBLSDBS_FIELD_NUMBER = 13;
    private volatile java.lang.Object loadableBblsDbs_;
    /**
     * <code>string loadableBblsDbs = 13;</code>
     *
     * @return The loadableBblsDbs.
     */
    public java.lang.String getLoadableBblsDbs() {
      java.lang.Object ref = loadableBblsDbs_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        loadableBblsDbs_ = s;
        return s;
      }
    }
    /**
     * <code>string loadableBblsDbs = 13;</code>
     *
     * @return The bytes for loadableBblsDbs.
     */
    public com.google.protobuf.ByteString getLoadableBblsDbsBytes() {
      java.lang.Object ref = loadableBblsDbs_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        loadableBblsDbs_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOADABLEKL_FIELD_NUMBER = 14;
    private volatile java.lang.Object loadableKl_;
    /**
     * <code>string loadableKl = 14;</code>
     *
     * @return The loadableKl.
     */
    public java.lang.String getLoadableKl() {
      java.lang.Object ref = loadableKl_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        loadableKl_ = s;
        return s;
      }
    }
    /**
     * <code>string loadableKl = 14;</code>
     *
     * @return The bytes for loadableKl.
     */
    public com.google.protobuf.ByteString getLoadableKlBytes() {
      java.lang.Object ref = loadableKl_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        loadableKl_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOADABLELT_FIELD_NUMBER = 15;
    private volatile java.lang.Object loadableLt_;
    /**
     * <code>string loadableLt = 15;</code>
     *
     * @return The loadableLt.
     */
    public java.lang.String getLoadableLt() {
      java.lang.Object ref = loadableLt_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        loadableLt_ = s;
        return s;
      }
    }
    /**
     * <code>string loadableLt = 15;</code>
     *
     * @return The bytes for loadableLt.
     */
    public com.google.protobuf.ByteString getLoadableLtBytes() {
      java.lang.Object ref = loadableLt_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        loadableLt_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOADABLEMT_FIELD_NUMBER = 16;
    private volatile java.lang.Object loadableMt_;
    /**
     * <code>string loadableMt = 16;</code>
     *
     * @return The loadableMt.
     */
    public java.lang.String getLoadableMt() {
      java.lang.Object ref = loadableMt_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        loadableMt_ = s;
        return s;
      }
    }
    /**
     * <code>string loadableMt = 16;</code>
     *
     * @return The bytes for loadableMt.
     */
    public com.google.protobuf.ByteString getLoadableMtBytes() {
      java.lang.Object ref = loadableMt_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        loadableMt_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOADABLEPATTERNID_FIELD_NUMBER = 17;
    private long loadablePatternId_;
    /**
     * <code>int64 loadablePatternId = 17;</code>
     *
     * @return The loadablePatternId.
     */
    public long getLoadablePatternId() {
      return loadablePatternId_;
    }

    public static final int LOADABLEPLANID_FIELD_NUMBER = 18;
    private long loadablePlanId_;
    /**
     * <code>int64 loadablePlanId = 18;</code>
     *
     * @return The loadablePlanId.
     */
    public long getLoadablePlanId() {
      return loadablePlanId_;
    }

    public static final int LOADABLEQUANTITY_FIELD_NUMBER = 19;
    private volatile java.lang.Object loadableQuantity_;
    /**
     * <code>string loadableQuantity = 19;</code>
     *
     * @return The loadableQuantity.
     */
    public java.lang.String getLoadableQuantity() {
      java.lang.Object ref = loadableQuantity_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        loadableQuantity_ = s;
        return s;
      }
    }
    /**
     * <code>string loadableQuantity = 19;</code>
     *
     * @return The bytes for loadableQuantity.
     */
    public com.google.protobuf.ByteString getLoadableQuantityBytes() {
      java.lang.Object ref = loadableQuantity_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        loadableQuantity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOADINGORDER_FIELD_NUMBER = 20;
    private int loadingOrder_;
    /**
     * <code>int32 loadingOrder = 20;</code>
     *
     * @return The loadingOrder.
     */
    public int getLoadingOrder() {
      return loadingOrder_;
    }

    public static final int MAXTOLERENCE_FIELD_NUMBER = 21;
    private volatile java.lang.Object maxTolerence_;
    /**
     * <code>string maxTolerence = 21;</code>
     *
     * @return The maxTolerence.
     */
    public java.lang.String getMaxTolerence() {
      java.lang.Object ref = maxTolerence_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        maxTolerence_ = s;
        return s;
      }
    }
    /**
     * <code>string maxTolerence = 21;</code>
     *
     * @return The bytes for maxTolerence.
     */
    public com.google.protobuf.ByteString getMaxTolerenceBytes() {
      java.lang.Object ref = maxTolerence_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        maxTolerence_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int MINTOLERENCE_FIELD_NUMBER = 22;
    private volatile java.lang.Object minTolerence_;
    /**
     * <code>string minTolerence = 22;</code>
     *
     * @return The minTolerence.
     */
    public java.lang.String getMinTolerence() {
      java.lang.Object ref = minTolerence_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        minTolerence_ = s;
        return s;
      }
    }
    /**
     * <code>string minTolerence = 22;</code>
     *
     * @return The bytes for minTolerence.
     */
    public com.google.protobuf.ByteString getMinTolerenceBytes() {
      java.lang.Object ref = minTolerence_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        minTolerence_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ORDERBBLS60F_FIELD_NUMBER = 23;
    private volatile java.lang.Object orderBbls60F_;
    /**
     * <code>string orderBbls60f = 23;</code>
     *
     * @return The orderBbls60f.
     */
    public java.lang.String getOrderBbls60F() {
      java.lang.Object ref = orderBbls60F_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        orderBbls60F_ = s;
        return s;
      }
    }
    /**
     * <code>string orderBbls60f = 23;</code>
     *
     * @return The bytes for orderBbls60f.
     */
    public com.google.protobuf.ByteString getOrderBbls60FBytes() {
      java.lang.Object ref = orderBbls60F_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        orderBbls60F_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ORDERBBLSDBS_FIELD_NUMBER = 24;
    private volatile java.lang.Object orderBblsDbs_;
    /**
     * <code>string orderBblsDbs = 24;</code>
     *
     * @return The orderBblsDbs.
     */
    public java.lang.String getOrderBblsDbs() {
      java.lang.Object ref = orderBblsDbs_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        orderBblsDbs_ = s;
        return s;
      }
    }
    /**
     * <code>string orderBblsDbs = 24;</code>
     *
     * @return The bytes for orderBblsDbs.
     */
    public com.google.protobuf.ByteString getOrderBblsDbsBytes() {
      java.lang.Object ref = orderBblsDbs_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        orderBblsDbs_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ORDERQUANTITY_FIELD_NUMBER = 25;
    private volatile java.lang.Object orderQuantity_;
    /**
     * <code>string orderQuantity = 25;</code>
     *
     * @return The orderQuantity.
     */
    public java.lang.String getOrderQuantity() {
      java.lang.Object ref = orderQuantity_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        orderQuantity_ = s;
        return s;
      }
    }
    /**
     * <code>string orderQuantity = 25;</code>
     *
     * @return The bytes for orderQuantity.
     */
    public com.google.protobuf.ByteString getOrderQuantityBytes() {
      java.lang.Object ref = orderQuantity_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        orderQuantity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PRIORITY_FIELD_NUMBER = 26;
    private int priority_;
    /**
     * <code>int32 priority = 26;</code>
     *
     * @return The priority.
     */
    public int getPriority() {
      return priority_;
    }

    public static final int SLOPQUANTITY_FIELD_NUMBER = 27;
    private volatile java.lang.Object slopQuantity_;
    /**
     * <code>string slopQuantity = 27;</code>
     *
     * @return The slopQuantity.
     */
    public java.lang.String getSlopQuantity() {
      java.lang.Object ref = slopQuantity_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        slopQuantity_ = s;
        return s;
      }
    }
    /**
     * <code>string slopQuantity = 27;</code>
     *
     * @return The bytes for slopQuantity.
     */
    public com.google.protobuf.ByteString getSlopQuantityBytes() {
      java.lang.Object ref = slopQuantity_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        slopQuantity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TIMEREQUIREDFORLOADING_FIELD_NUMBER = 28;
    private volatile java.lang.Object timeRequiredForLoading_;
    /**
     * <code>string timeRequiredForLoading = 28;</code>
     *
     * @return The timeRequiredForLoading.
     */
    public java.lang.String getTimeRequiredForLoading() {
      java.lang.Object ref = timeRequiredForLoading_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        timeRequiredForLoading_ = s;
        return s;
      }
    }
    /**
     * <code>string timeRequiredForLoading = 28;</code>
     *
     * @return The bytes for timeRequiredForLoading.
     */
    public com.google.protobuf.ByteString getTimeRequiredForLoadingBytes() {
      java.lang.Object ref = timeRequiredForLoading_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        timeRequiredForLoading_ = b;
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
      if (!getCargoAbbreviationBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, cargoAbbreviation_);
      }
      if (!getCargoColorBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, cargoColor_);
      }
      if (cargoNominationId_ != 0L) {
        output.writeInt64(3, cargoNominationId_);
      }
      if (cargoId_ != 0L) {
        output.writeInt64(4, cargoId_);
      }
      if (!getDifferenceColorBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, differenceColor_);
      }
      if (!getDifferencePercentageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, differencePercentage_);
      }
      if (!getEstimatedApiBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, estimatedApi_);
      }
      if (!getEstimatedTemperatureBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, estimatedTemperature_);
      }
      if (!getGradeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 9, grade_);
      }
      if (id_ != 0L) {
        output.writeInt64(10, id_);
      }
      if (isActive_ != false) {
        output.writeBool(11, isActive_);
      }
      if (!getLoadableBbls60FBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 12, loadableBbls60F_);
      }
      if (!getLoadableBblsDbsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 13, loadableBblsDbs_);
      }
      if (!getLoadableKlBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 14, loadableKl_);
      }
      if (!getLoadableLtBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 15, loadableLt_);
      }
      if (!getLoadableMtBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 16, loadableMt_);
      }
      if (loadablePatternId_ != 0L) {
        output.writeInt64(17, loadablePatternId_);
      }
      if (loadablePlanId_ != 0L) {
        output.writeInt64(18, loadablePlanId_);
      }
      if (!getLoadableQuantityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 19, loadableQuantity_);
      }
      if (loadingOrder_ != 0) {
        output.writeInt32(20, loadingOrder_);
      }
      if (!getMaxTolerenceBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 21, maxTolerence_);
      }
      if (!getMinTolerenceBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 22, minTolerence_);
      }
      if (!getOrderBbls60FBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 23, orderBbls60F_);
      }
      if (!getOrderBblsDbsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 24, orderBblsDbs_);
      }
      if (!getOrderQuantityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 25, orderQuantity_);
      }
      if (priority_ != 0) {
        output.writeInt32(26, priority_);
      }
      if (!getSlopQuantityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 27, slopQuantity_);
      }
      if (!getTimeRequiredForLoadingBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 28, timeRequiredForLoading_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getCargoAbbreviationBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, cargoAbbreviation_);
      }
      if (!getCargoColorBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, cargoColor_);
      }
      if (cargoNominationId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, cargoNominationId_);
      }
      if (cargoId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, cargoId_);
      }
      if (!getDifferenceColorBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, differenceColor_);
      }
      if (!getDifferencePercentageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, differencePercentage_);
      }
      if (!getEstimatedApiBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, estimatedApi_);
      }
      if (!getEstimatedTemperatureBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, estimatedTemperature_);
      }
      if (!getGradeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, grade_);
      }
      if (id_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(10, id_);
      }
      if (isActive_ != false) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(11, isActive_);
      }
      if (!getLoadableBbls60FBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(12, loadableBbls60F_);
      }
      if (!getLoadableBblsDbsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(13, loadableBblsDbs_);
      }
      if (!getLoadableKlBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(14, loadableKl_);
      }
      if (!getLoadableLtBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(15, loadableLt_);
      }
      if (!getLoadableMtBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(16, loadableMt_);
      }
      if (loadablePatternId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(17, loadablePatternId_);
      }
      if (loadablePlanId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(18, loadablePlanId_);
      }
      if (!getLoadableQuantityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(19, loadableQuantity_);
      }
      if (loadingOrder_ != 0) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(20, loadingOrder_);
      }
      if (!getMaxTolerenceBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(21, maxTolerence_);
      }
      if (!getMinTolerenceBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(22, minTolerence_);
      }
      if (!getOrderBbls60FBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(23, orderBbls60F_);
      }
      if (!getOrderBblsDbsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(24, orderBblsDbs_);
      }
      if (!getOrderQuantityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(25, orderQuantity_);
      }
      if (priority_ != 0) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(26, priority_);
      }
      if (!getSlopQuantityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(27, slopQuantity_);
      }
      if (!getTimeRequiredForLoadingBytes().isEmpty()) {
        size +=
            com.google.protobuf.GeneratedMessageV3.computeStringSize(28, timeRequiredForLoading_);
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
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity) obj;

      if (!getCargoAbbreviation().equals(other.getCargoAbbreviation())) return false;
      if (!getCargoColor().equals(other.getCargoColor())) return false;
      if (getCargoNominationId() != other.getCargoNominationId()) return false;
      if (getCargoId() != other.getCargoId()) return false;
      if (!getDifferenceColor().equals(other.getDifferenceColor())) return false;
      if (!getDifferencePercentage().equals(other.getDifferencePercentage())) return false;
      if (!getEstimatedApi().equals(other.getEstimatedApi())) return false;
      if (!getEstimatedTemperature().equals(other.getEstimatedTemperature())) return false;
      if (!getGrade().equals(other.getGrade())) return false;
      if (getId() != other.getId()) return false;
      if (getIsActive() != other.getIsActive()) return false;
      if (!getLoadableBbls60F().equals(other.getLoadableBbls60F())) return false;
      if (!getLoadableBblsDbs().equals(other.getLoadableBblsDbs())) return false;
      if (!getLoadableKl().equals(other.getLoadableKl())) return false;
      if (!getLoadableLt().equals(other.getLoadableLt())) return false;
      if (!getLoadableMt().equals(other.getLoadableMt())) return false;
      if (getLoadablePatternId() != other.getLoadablePatternId()) return false;
      if (getLoadablePlanId() != other.getLoadablePlanId()) return false;
      if (!getLoadableQuantity().equals(other.getLoadableQuantity())) return false;
      if (getLoadingOrder() != other.getLoadingOrder()) return false;
      if (!getMaxTolerence().equals(other.getMaxTolerence())) return false;
      if (!getMinTolerence().equals(other.getMinTolerence())) return false;
      if (!getOrderBbls60F().equals(other.getOrderBbls60F())) return false;
      if (!getOrderBblsDbs().equals(other.getOrderBblsDbs())) return false;
      if (!getOrderQuantity().equals(other.getOrderQuantity())) return false;
      if (getPriority() != other.getPriority()) return false;
      if (!getSlopQuantity().equals(other.getSlopQuantity())) return false;
      if (!getTimeRequiredForLoading().equals(other.getTimeRequiredForLoading())) return false;
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
      hash = (37 * hash) + CARGOABBREVIATION_FIELD_NUMBER;
      hash = (53 * hash) + getCargoAbbreviation().hashCode();
      hash = (37 * hash) + CARGOCOLOR_FIELD_NUMBER;
      hash = (53 * hash) + getCargoColor().hashCode();
      hash = (37 * hash) + CARGONOMINATIONID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoNominationId());
      hash = (37 * hash) + CARGOID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoId());
      hash = (37 * hash) + DIFFERENCECOLOR_FIELD_NUMBER;
      hash = (53 * hash) + getDifferenceColor().hashCode();
      hash = (37 * hash) + DIFFERENCEPERCENTAGE_FIELD_NUMBER;
      hash = (53 * hash) + getDifferencePercentage().hashCode();
      hash = (37 * hash) + ESTIMATEDAPI_FIELD_NUMBER;
      hash = (53 * hash) + getEstimatedApi().hashCode();
      hash = (37 * hash) + ESTIMATEDTEMPERATURE_FIELD_NUMBER;
      hash = (53 * hash) + getEstimatedTemperature().hashCode();
      hash = (37 * hash) + GRADE_FIELD_NUMBER;
      hash = (53 * hash) + getGrade().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
      hash = (37 * hash) + ISACTIVE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsActive());
      hash = (37 * hash) + LOADABLEBBLS60F_FIELD_NUMBER;
      hash = (53 * hash) + getLoadableBbls60F().hashCode();
      hash = (37 * hash) + LOADABLEBBLSDBS_FIELD_NUMBER;
      hash = (53 * hash) + getLoadableBblsDbs().hashCode();
      hash = (37 * hash) + LOADABLEKL_FIELD_NUMBER;
      hash = (53 * hash) + getLoadableKl().hashCode();
      hash = (37 * hash) + LOADABLELT_FIELD_NUMBER;
      hash = (53 * hash) + getLoadableLt().hashCode();
      hash = (37 * hash) + LOADABLEMT_FIELD_NUMBER;
      hash = (53 * hash) + getLoadableMt().hashCode();
      hash = (37 * hash) + LOADABLEPATTERNID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadablePatternId());
      hash = (37 * hash) + LOADABLEPLANID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadablePlanId());
      hash = (37 * hash) + LOADABLEQUANTITY_FIELD_NUMBER;
      hash = (53 * hash) + getLoadableQuantity().hashCode();
      hash = (37 * hash) + LOADINGORDER_FIELD_NUMBER;
      hash = (53 * hash) + getLoadingOrder();
      hash = (37 * hash) + MAXTOLERENCE_FIELD_NUMBER;
      hash = (53 * hash) + getMaxTolerence().hashCode();
      hash = (37 * hash) + MINTOLERENCE_FIELD_NUMBER;
      hash = (53 * hash) + getMinTolerence().hashCode();
      hash = (37 * hash) + ORDERBBLS60F_FIELD_NUMBER;
      hash = (53 * hash) + getOrderBbls60F().hashCode();
      hash = (37 * hash) + ORDERBBLSDBS_FIELD_NUMBER;
      hash = (53 * hash) + getOrderBblsDbs().hashCode();
      hash = (37 * hash) + ORDERQUANTITY_FIELD_NUMBER;
      hash = (53 * hash) + getOrderQuantity().hashCode();
      hash = (37 * hash) + PRIORITY_FIELD_NUMBER;
      hash = (53 * hash) + getPriority();
      hash = (37 * hash) + SLOPQUANTITY_FIELD_NUMBER;
      hash = (53 * hash) + getSlopQuantity().hashCode();
      hash = (37 * hash) + TIMEREQUIREDFORLOADING_FIELD_NUMBER;
      hash = (53 * hash) + getTimeRequiredForLoading().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity prototype) {
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
    /** Protobuf type {@code LoadablePlanQuantity} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadablePlanQuantity)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantityOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanQuantity_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanQuantity_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
                    .class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
                    .Builder.class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity.newBuilder()
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
        cargoAbbreviation_ = "";

        cargoColor_ = "";

        cargoNominationId_ = 0L;

        cargoId_ = 0L;

        differenceColor_ = "";

        differencePercentage_ = "";

        estimatedApi_ = "";

        estimatedTemperature_ = "";

        grade_ = "";

        id_ = 0L;

        isActive_ = false;

        loadableBbls60F_ = "";

        loadableBblsDbs_ = "";

        loadableKl_ = "";

        loadableLt_ = "";

        loadableMt_ = "";

        loadablePatternId_ = 0L;

        loadablePlanId_ = 0L;

        loadableQuantity_ = "";

        loadingOrder_ = 0;

        maxTolerence_ = "";

        minTolerence_ = "";

        orderBbls60F_ = "";

        orderBblsDbs_ = "";

        orderQuantity_ = "";

        priority_ = 0;

        slopQuantity_ = "";

        timeRequiredForLoading_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanQuantity_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
          build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity result =
            new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity(
                this);
        result.cargoAbbreviation_ = cargoAbbreviation_;
        result.cargoColor_ = cargoColor_;
        result.cargoNominationId_ = cargoNominationId_;
        result.cargoId_ = cargoId_;
        result.differenceColor_ = differenceColor_;
        result.differencePercentage_ = differencePercentage_;
        result.estimatedApi_ = estimatedApi_;
        result.estimatedTemperature_ = estimatedTemperature_;
        result.grade_ = grade_;
        result.id_ = id_;
        result.isActive_ = isActive_;
        result.loadableBbls60F_ = loadableBbls60F_;
        result.loadableBblsDbs_ = loadableBblsDbs_;
        result.loadableKl_ = loadableKl_;
        result.loadableLt_ = loadableLt_;
        result.loadableMt_ = loadableMt_;
        result.loadablePatternId_ = loadablePatternId_;
        result.loadablePlanId_ = loadablePlanId_;
        result.loadableQuantity_ = loadableQuantity_;
        result.loadingOrder_ = loadingOrder_;
        result.maxTolerence_ = maxTolerence_;
        result.minTolerence_ = minTolerence_;
        result.orderBbls60F_ = orderBbls60F_;
        result.orderBblsDbs_ = orderBblsDbs_;
        result.orderQuantity_ = orderQuantity_;
        result.priority_ = priority_;
        result.slopQuantity_ = slopQuantity_;
        result.timeRequiredForLoading_ = timeRequiredForLoading_;
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
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
                .getDefaultInstance()) return this;
        if (!other.getCargoAbbreviation().isEmpty()) {
          cargoAbbreviation_ = other.cargoAbbreviation_;
          onChanged();
        }
        if (!other.getCargoColor().isEmpty()) {
          cargoColor_ = other.cargoColor_;
          onChanged();
        }
        if (other.getCargoNominationId() != 0L) {
          setCargoNominationId(other.getCargoNominationId());
        }
        if (other.getCargoId() != 0L) {
          setCargoId(other.getCargoId());
        }
        if (!other.getDifferenceColor().isEmpty()) {
          differenceColor_ = other.differenceColor_;
          onChanged();
        }
        if (!other.getDifferencePercentage().isEmpty()) {
          differencePercentage_ = other.differencePercentage_;
          onChanged();
        }
        if (!other.getEstimatedApi().isEmpty()) {
          estimatedApi_ = other.estimatedApi_;
          onChanged();
        }
        if (!other.getEstimatedTemperature().isEmpty()) {
          estimatedTemperature_ = other.estimatedTemperature_;
          onChanged();
        }
        if (!other.getGrade().isEmpty()) {
          grade_ = other.grade_;
          onChanged();
        }
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (other.getIsActive() != false) {
          setIsActive(other.getIsActive());
        }
        if (!other.getLoadableBbls60F().isEmpty()) {
          loadableBbls60F_ = other.loadableBbls60F_;
          onChanged();
        }
        if (!other.getLoadableBblsDbs().isEmpty()) {
          loadableBblsDbs_ = other.loadableBblsDbs_;
          onChanged();
        }
        if (!other.getLoadableKl().isEmpty()) {
          loadableKl_ = other.loadableKl_;
          onChanged();
        }
        if (!other.getLoadableLt().isEmpty()) {
          loadableLt_ = other.loadableLt_;
          onChanged();
        }
        if (!other.getLoadableMt().isEmpty()) {
          loadableMt_ = other.loadableMt_;
          onChanged();
        }
        if (other.getLoadablePatternId() != 0L) {
          setLoadablePatternId(other.getLoadablePatternId());
        }
        if (other.getLoadablePlanId() != 0L) {
          setLoadablePlanId(other.getLoadablePlanId());
        }
        if (!other.getLoadableQuantity().isEmpty()) {
          loadableQuantity_ = other.loadableQuantity_;
          onChanged();
        }
        if (other.getLoadingOrder() != 0) {
          setLoadingOrder(other.getLoadingOrder());
        }
        if (!other.getMaxTolerence().isEmpty()) {
          maxTolerence_ = other.maxTolerence_;
          onChanged();
        }
        if (!other.getMinTolerence().isEmpty()) {
          minTolerence_ = other.minTolerence_;
          onChanged();
        }
        if (!other.getOrderBbls60F().isEmpty()) {
          orderBbls60F_ = other.orderBbls60F_;
          onChanged();
        }
        if (!other.getOrderBblsDbs().isEmpty()) {
          orderBblsDbs_ = other.orderBblsDbs_;
          onChanged();
        }
        if (!other.getOrderQuantity().isEmpty()) {
          orderQuantity_ = other.orderQuantity_;
          onChanged();
        }
        if (other.getPriority() != 0) {
          setPriority(other.getPriority());
        }
        if (!other.getSlopQuantity().isEmpty()) {
          slopQuantity_ = other.slopQuantity_;
          onChanged();
        }
        if (!other.getTimeRequiredForLoading().isEmpty()) {
          timeRequiredForLoading_ = other.timeRequiredForLoading_;
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object cargoAbbreviation_ = "";
      /**
       * <code>string cargoAbbreviation = 1;</code>
       *
       * @return The cargoAbbreviation.
       */
      public java.lang.String getCargoAbbreviation() {
        java.lang.Object ref = cargoAbbreviation_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargoAbbreviation_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargoAbbreviation = 1;</code>
       *
       * @return The bytes for cargoAbbreviation.
       */
      public com.google.protobuf.ByteString getCargoAbbreviationBytes() {
        java.lang.Object ref = cargoAbbreviation_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargoAbbreviation_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargoAbbreviation = 1;</code>
       *
       * @param value The cargoAbbreviation to set.
       * @return This builder for chaining.
       */
      public Builder setCargoAbbreviation(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargoAbbreviation_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargoAbbreviation = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoAbbreviation() {

        cargoAbbreviation_ = getDefaultInstance().getCargoAbbreviation();
        onChanged();
        return this;
      }
      /**
       * <code>string cargoAbbreviation = 1;</code>
       *
       * @param value The bytes for cargoAbbreviation to set.
       * @return This builder for chaining.
       */
      public Builder setCargoAbbreviationBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargoAbbreviation_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargoColor_ = "";
      /**
       * <code>string cargoColor = 2;</code>
       *
       * @return The cargoColor.
       */
      public java.lang.String getCargoColor() {
        java.lang.Object ref = cargoColor_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargoColor_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargoColor = 2;</code>
       *
       * @return The bytes for cargoColor.
       */
      public com.google.protobuf.ByteString getCargoColorBytes() {
        java.lang.Object ref = cargoColor_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargoColor_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargoColor = 2;</code>
       *
       * @param value The cargoColor to set.
       * @return This builder for chaining.
       */
      public Builder setCargoColor(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargoColor_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargoColor = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoColor() {

        cargoColor_ = getDefaultInstance().getCargoColor();
        onChanged();
        return this;
      }
      /**
       * <code>string cargoColor = 2;</code>
       *
       * @param value The bytes for cargoColor to set.
       * @return This builder for chaining.
       */
      public Builder setCargoColorBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargoColor_ = value;
        onChanged();
        return this;
      }

      private long cargoNominationId_;
      /**
       * <code>int64 cargoNominationId = 3;</code>
       *
       * @return The cargoNominationId.
       */
      public long getCargoNominationId() {
        return cargoNominationId_;
      }
      /**
       * <code>int64 cargoNominationId = 3;</code>
       *
       * @param value The cargoNominationId to set.
       * @return This builder for chaining.
       */
      public Builder setCargoNominationId(long value) {

        cargoNominationId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 cargoNominationId = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoNominationId() {

        cargoNominationId_ = 0L;
        onChanged();
        return this;
      }

      private long cargoId_;
      /**
       * <code>int64 cargoId = 4;</code>
       *
       * @return The cargoId.
       */
      public long getCargoId() {
        return cargoId_;
      }
      /**
       * <code>int64 cargoId = 4;</code>
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
       * <code>int64 cargoId = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoId() {

        cargoId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object differenceColor_ = "";
      /**
       * <code>string differenceColor = 5;</code>
       *
       * @return The differenceColor.
       */
      public java.lang.String getDifferenceColor() {
        java.lang.Object ref = differenceColor_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          differenceColor_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string differenceColor = 5;</code>
       *
       * @return The bytes for differenceColor.
       */
      public com.google.protobuf.ByteString getDifferenceColorBytes() {
        java.lang.Object ref = differenceColor_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          differenceColor_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string differenceColor = 5;</code>
       *
       * @param value The differenceColor to set.
       * @return This builder for chaining.
       */
      public Builder setDifferenceColor(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        differenceColor_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string differenceColor = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDifferenceColor() {

        differenceColor_ = getDefaultInstance().getDifferenceColor();
        onChanged();
        return this;
      }
      /**
       * <code>string differenceColor = 5;</code>
       *
       * @param value The bytes for differenceColor to set.
       * @return This builder for chaining.
       */
      public Builder setDifferenceColorBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        differenceColor_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object differencePercentage_ = "";
      /**
       * <code>string differencePercentage = 6;</code>
       *
       * @return The differencePercentage.
       */
      public java.lang.String getDifferencePercentage() {
        java.lang.Object ref = differencePercentage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          differencePercentage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string differencePercentage = 6;</code>
       *
       * @return The bytes for differencePercentage.
       */
      public com.google.protobuf.ByteString getDifferencePercentageBytes() {
        java.lang.Object ref = differencePercentage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          differencePercentage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string differencePercentage = 6;</code>
       *
       * @param value The differencePercentage to set.
       * @return This builder for chaining.
       */
      public Builder setDifferencePercentage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        differencePercentage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string differencePercentage = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDifferencePercentage() {

        differencePercentage_ = getDefaultInstance().getDifferencePercentage();
        onChanged();
        return this;
      }
      /**
       * <code>string differencePercentage = 6;</code>
       *
       * @param value The bytes for differencePercentage to set.
       * @return This builder for chaining.
       */
      public Builder setDifferencePercentageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        differencePercentage_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object estimatedApi_ = "";
      /**
       * <code>string estimatedApi = 7;</code>
       *
       * @return The estimatedApi.
       */
      public java.lang.String getEstimatedApi() {
        java.lang.Object ref = estimatedApi_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          estimatedApi_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string estimatedApi = 7;</code>
       *
       * @return The bytes for estimatedApi.
       */
      public com.google.protobuf.ByteString getEstimatedApiBytes() {
        java.lang.Object ref = estimatedApi_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          estimatedApi_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string estimatedApi = 7;</code>
       *
       * @param value The estimatedApi to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedApi(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        estimatedApi_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedApi = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearEstimatedApi() {

        estimatedApi_ = getDefaultInstance().getEstimatedApi();
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedApi = 7;</code>
       *
       * @param value The bytes for estimatedApi to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedApiBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        estimatedApi_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object estimatedTemperature_ = "";
      /**
       * <code>string estimatedTemperature = 8;</code>
       *
       * @return The estimatedTemperature.
       */
      public java.lang.String getEstimatedTemperature() {
        java.lang.Object ref = estimatedTemperature_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          estimatedTemperature_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string estimatedTemperature = 8;</code>
       *
       * @return The bytes for estimatedTemperature.
       */
      public com.google.protobuf.ByteString getEstimatedTemperatureBytes() {
        java.lang.Object ref = estimatedTemperature_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          estimatedTemperature_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string estimatedTemperature = 8;</code>
       *
       * @param value The estimatedTemperature to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedTemperature(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        estimatedTemperature_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedTemperature = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearEstimatedTemperature() {

        estimatedTemperature_ = getDefaultInstance().getEstimatedTemperature();
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedTemperature = 8;</code>
       *
       * @param value The bytes for estimatedTemperature to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedTemperatureBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        estimatedTemperature_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object grade_ = "";
      /**
       * <code>string grade = 9;</code>
       *
       * @return The grade.
       */
      public java.lang.String getGrade() {
        java.lang.Object ref = grade_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          grade_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string grade = 9;</code>
       *
       * @return The bytes for grade.
       */
      public com.google.protobuf.ByteString getGradeBytes() {
        java.lang.Object ref = grade_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          grade_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string grade = 9;</code>
       *
       * @param value The grade to set.
       * @return This builder for chaining.
       */
      public Builder setGrade(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        grade_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string grade = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearGrade() {

        grade_ = getDefaultInstance().getGrade();
        onChanged();
        return this;
      }
      /**
       * <code>string grade = 9;</code>
       *
       * @param value The bytes for grade to set.
       * @return This builder for chaining.
       */
      public Builder setGradeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        grade_ = value;
        onChanged();
        return this;
      }

      private long id_;
      /**
       * <code>int64 id = 10;</code>
       *
       * @return The id.
       */
      public long getId() {
        return id_;
      }
      /**
       * <code>int64 id = 10;</code>
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
       * <code>int64 id = 10;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {

        id_ = 0L;
        onChanged();
        return this;
      }

      private boolean isActive_;
      /**
       * <code>bool isActive = 11;</code>
       *
       * @return The isActive.
       */
      public boolean getIsActive() {
        return isActive_;
      }
      /**
       * <code>bool isActive = 11;</code>
       *
       * @param value The isActive to set.
       * @return This builder for chaining.
       */
      public Builder setIsActive(boolean value) {

        isActive_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool isActive = 11;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearIsActive() {

        isActive_ = false;
        onChanged();
        return this;
      }

      private java.lang.Object loadableBbls60F_ = "";
      /**
       * <code>string loadableBbls60f = 12;</code>
       *
       * @return The loadableBbls60f.
       */
      public java.lang.String getLoadableBbls60F() {
        java.lang.Object ref = loadableBbls60F_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          loadableBbls60F_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string loadableBbls60f = 12;</code>
       *
       * @return The bytes for loadableBbls60f.
       */
      public com.google.protobuf.ByteString getLoadableBbls60FBytes() {
        java.lang.Object ref = loadableBbls60F_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          loadableBbls60F_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string loadableBbls60f = 12;</code>
       *
       * @param value The loadableBbls60f to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableBbls60F(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        loadableBbls60F_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string loadableBbls60f = 12;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadableBbls60F() {

        loadableBbls60F_ = getDefaultInstance().getLoadableBbls60F();
        onChanged();
        return this;
      }
      /**
       * <code>string loadableBbls60f = 12;</code>
       *
       * @param value The bytes for loadableBbls60f to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableBbls60FBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        loadableBbls60F_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object loadableBblsDbs_ = "";
      /**
       * <code>string loadableBblsDbs = 13;</code>
       *
       * @return The loadableBblsDbs.
       */
      public java.lang.String getLoadableBblsDbs() {
        java.lang.Object ref = loadableBblsDbs_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          loadableBblsDbs_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string loadableBblsDbs = 13;</code>
       *
       * @return The bytes for loadableBblsDbs.
       */
      public com.google.protobuf.ByteString getLoadableBblsDbsBytes() {
        java.lang.Object ref = loadableBblsDbs_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          loadableBblsDbs_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string loadableBblsDbs = 13;</code>
       *
       * @param value The loadableBblsDbs to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableBblsDbs(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        loadableBblsDbs_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string loadableBblsDbs = 13;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadableBblsDbs() {

        loadableBblsDbs_ = getDefaultInstance().getLoadableBblsDbs();
        onChanged();
        return this;
      }
      /**
       * <code>string loadableBblsDbs = 13;</code>
       *
       * @param value The bytes for loadableBblsDbs to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableBblsDbsBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        loadableBblsDbs_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object loadableKl_ = "";
      /**
       * <code>string loadableKl = 14;</code>
       *
       * @return The loadableKl.
       */
      public java.lang.String getLoadableKl() {
        java.lang.Object ref = loadableKl_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          loadableKl_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string loadableKl = 14;</code>
       *
       * @return The bytes for loadableKl.
       */
      public com.google.protobuf.ByteString getLoadableKlBytes() {
        java.lang.Object ref = loadableKl_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          loadableKl_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string loadableKl = 14;</code>
       *
       * @param value The loadableKl to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableKl(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        loadableKl_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string loadableKl = 14;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadableKl() {

        loadableKl_ = getDefaultInstance().getLoadableKl();
        onChanged();
        return this;
      }
      /**
       * <code>string loadableKl = 14;</code>
       *
       * @param value The bytes for loadableKl to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableKlBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        loadableKl_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object loadableLt_ = "";
      /**
       * <code>string loadableLt = 15;</code>
       *
       * @return The loadableLt.
       */
      public java.lang.String getLoadableLt() {
        java.lang.Object ref = loadableLt_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          loadableLt_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string loadableLt = 15;</code>
       *
       * @return The bytes for loadableLt.
       */
      public com.google.protobuf.ByteString getLoadableLtBytes() {
        java.lang.Object ref = loadableLt_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          loadableLt_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string loadableLt = 15;</code>
       *
       * @param value The loadableLt to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableLt(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        loadableLt_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string loadableLt = 15;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadableLt() {

        loadableLt_ = getDefaultInstance().getLoadableLt();
        onChanged();
        return this;
      }
      /**
       * <code>string loadableLt = 15;</code>
       *
       * @param value The bytes for loadableLt to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableLtBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        loadableLt_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object loadableMt_ = "";
      /**
       * <code>string loadableMt = 16;</code>
       *
       * @return The loadableMt.
       */
      public java.lang.String getLoadableMt() {
        java.lang.Object ref = loadableMt_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          loadableMt_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string loadableMt = 16;</code>
       *
       * @return The bytes for loadableMt.
       */
      public com.google.protobuf.ByteString getLoadableMtBytes() {
        java.lang.Object ref = loadableMt_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          loadableMt_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string loadableMt = 16;</code>
       *
       * @param value The loadableMt to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableMt(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        loadableMt_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string loadableMt = 16;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadableMt() {

        loadableMt_ = getDefaultInstance().getLoadableMt();
        onChanged();
        return this;
      }
      /**
       * <code>string loadableMt = 16;</code>
       *
       * @param value The bytes for loadableMt to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableMtBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        loadableMt_ = value;
        onChanged();
        return this;
      }

      private long loadablePatternId_;
      /**
       * <code>int64 loadablePatternId = 17;</code>
       *
       * @return The loadablePatternId.
       */
      public long getLoadablePatternId() {
        return loadablePatternId_;
      }
      /**
       * <code>int64 loadablePatternId = 17;</code>
       *
       * @param value The loadablePatternId to set.
       * @return This builder for chaining.
       */
      public Builder setLoadablePatternId(long value) {

        loadablePatternId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 loadablePatternId = 17;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadablePatternId() {

        loadablePatternId_ = 0L;
        onChanged();
        return this;
      }

      private long loadablePlanId_;
      /**
       * <code>int64 loadablePlanId = 18;</code>
       *
       * @return The loadablePlanId.
       */
      public long getLoadablePlanId() {
        return loadablePlanId_;
      }
      /**
       * <code>int64 loadablePlanId = 18;</code>
       *
       * @param value The loadablePlanId to set.
       * @return This builder for chaining.
       */
      public Builder setLoadablePlanId(long value) {

        loadablePlanId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 loadablePlanId = 18;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadablePlanId() {

        loadablePlanId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object loadableQuantity_ = "";
      /**
       * <code>string loadableQuantity = 19;</code>
       *
       * @return The loadableQuantity.
       */
      public java.lang.String getLoadableQuantity() {
        java.lang.Object ref = loadableQuantity_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          loadableQuantity_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string loadableQuantity = 19;</code>
       *
       * @return The bytes for loadableQuantity.
       */
      public com.google.protobuf.ByteString getLoadableQuantityBytes() {
        java.lang.Object ref = loadableQuantity_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          loadableQuantity_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string loadableQuantity = 19;</code>
       *
       * @param value The loadableQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableQuantity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        loadableQuantity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string loadableQuantity = 19;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadableQuantity() {

        loadableQuantity_ = getDefaultInstance().getLoadableQuantity();
        onChanged();
        return this;
      }
      /**
       * <code>string loadableQuantity = 19;</code>
       *
       * @param value The bytes for loadableQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableQuantityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        loadableQuantity_ = value;
        onChanged();
        return this;
      }

      private int loadingOrder_;
      /**
       * <code>int32 loadingOrder = 20;</code>
       *
       * @return The loadingOrder.
       */
      public int getLoadingOrder() {
        return loadingOrder_;
      }
      /**
       * <code>int32 loadingOrder = 20;</code>
       *
       * @param value The loadingOrder to set.
       * @return This builder for chaining.
       */
      public Builder setLoadingOrder(int value) {

        loadingOrder_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 loadingOrder = 20;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadingOrder() {

        loadingOrder_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object maxTolerence_ = "";
      /**
       * <code>string maxTolerence = 21;</code>
       *
       * @return The maxTolerence.
       */
      public java.lang.String getMaxTolerence() {
        java.lang.Object ref = maxTolerence_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          maxTolerence_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string maxTolerence = 21;</code>
       *
       * @return The bytes for maxTolerence.
       */
      public com.google.protobuf.ByteString getMaxTolerenceBytes() {
        java.lang.Object ref = maxTolerence_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          maxTolerence_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string maxTolerence = 21;</code>
       *
       * @param value The maxTolerence to set.
       * @return This builder for chaining.
       */
      public Builder setMaxTolerence(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        maxTolerence_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string maxTolerence = 21;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearMaxTolerence() {

        maxTolerence_ = getDefaultInstance().getMaxTolerence();
        onChanged();
        return this;
      }
      /**
       * <code>string maxTolerence = 21;</code>
       *
       * @param value The bytes for maxTolerence to set.
       * @return This builder for chaining.
       */
      public Builder setMaxTolerenceBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        maxTolerence_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object minTolerence_ = "";
      /**
       * <code>string minTolerence = 22;</code>
       *
       * @return The minTolerence.
       */
      public java.lang.String getMinTolerence() {
        java.lang.Object ref = minTolerence_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          minTolerence_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string minTolerence = 22;</code>
       *
       * @return The bytes for minTolerence.
       */
      public com.google.protobuf.ByteString getMinTolerenceBytes() {
        java.lang.Object ref = minTolerence_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          minTolerence_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string minTolerence = 22;</code>
       *
       * @param value The minTolerence to set.
       * @return This builder for chaining.
       */
      public Builder setMinTolerence(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        minTolerence_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string minTolerence = 22;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearMinTolerence() {

        minTolerence_ = getDefaultInstance().getMinTolerence();
        onChanged();
        return this;
      }
      /**
       * <code>string minTolerence = 22;</code>
       *
       * @param value The bytes for minTolerence to set.
       * @return This builder for chaining.
       */
      public Builder setMinTolerenceBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        minTolerence_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object orderBbls60F_ = "";
      /**
       * <code>string orderBbls60f = 23;</code>
       *
       * @return The orderBbls60f.
       */
      public java.lang.String getOrderBbls60F() {
        java.lang.Object ref = orderBbls60F_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          orderBbls60F_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string orderBbls60f = 23;</code>
       *
       * @return The bytes for orderBbls60f.
       */
      public com.google.protobuf.ByteString getOrderBbls60FBytes() {
        java.lang.Object ref = orderBbls60F_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          orderBbls60F_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string orderBbls60f = 23;</code>
       *
       * @param value The orderBbls60f to set.
       * @return This builder for chaining.
       */
      public Builder setOrderBbls60F(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        orderBbls60F_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string orderBbls60f = 23;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearOrderBbls60F() {

        orderBbls60F_ = getDefaultInstance().getOrderBbls60F();
        onChanged();
        return this;
      }
      /**
       * <code>string orderBbls60f = 23;</code>
       *
       * @param value The bytes for orderBbls60f to set.
       * @return This builder for chaining.
       */
      public Builder setOrderBbls60FBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        orderBbls60F_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object orderBblsDbs_ = "";
      /**
       * <code>string orderBblsDbs = 24;</code>
       *
       * @return The orderBblsDbs.
       */
      public java.lang.String getOrderBblsDbs() {
        java.lang.Object ref = orderBblsDbs_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          orderBblsDbs_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string orderBblsDbs = 24;</code>
       *
       * @return The bytes for orderBblsDbs.
       */
      public com.google.protobuf.ByteString getOrderBblsDbsBytes() {
        java.lang.Object ref = orderBblsDbs_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          orderBblsDbs_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string orderBblsDbs = 24;</code>
       *
       * @param value The orderBblsDbs to set.
       * @return This builder for chaining.
       */
      public Builder setOrderBblsDbs(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        orderBblsDbs_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string orderBblsDbs = 24;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearOrderBblsDbs() {

        orderBblsDbs_ = getDefaultInstance().getOrderBblsDbs();
        onChanged();
        return this;
      }
      /**
       * <code>string orderBblsDbs = 24;</code>
       *
       * @param value The bytes for orderBblsDbs to set.
       * @return This builder for chaining.
       */
      public Builder setOrderBblsDbsBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        orderBblsDbs_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object orderQuantity_ = "";
      /**
       * <code>string orderQuantity = 25;</code>
       *
       * @return The orderQuantity.
       */
      public java.lang.String getOrderQuantity() {
        java.lang.Object ref = orderQuantity_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          orderQuantity_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string orderQuantity = 25;</code>
       *
       * @return The bytes for orderQuantity.
       */
      public com.google.protobuf.ByteString getOrderQuantityBytes() {
        java.lang.Object ref = orderQuantity_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          orderQuantity_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string orderQuantity = 25;</code>
       *
       * @param value The orderQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setOrderQuantity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        orderQuantity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string orderQuantity = 25;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearOrderQuantity() {

        orderQuantity_ = getDefaultInstance().getOrderQuantity();
        onChanged();
        return this;
      }
      /**
       * <code>string orderQuantity = 25;</code>
       *
       * @param value The bytes for orderQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setOrderQuantityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        orderQuantity_ = value;
        onChanged();
        return this;
      }

      private int priority_;
      /**
       * <code>int32 priority = 26;</code>
       *
       * @return The priority.
       */
      public int getPriority() {
        return priority_;
      }
      /**
       * <code>int32 priority = 26;</code>
       *
       * @param value The priority to set.
       * @return This builder for chaining.
       */
      public Builder setPriority(int value) {

        priority_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 priority = 26;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPriority() {

        priority_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object slopQuantity_ = "";
      /**
       * <code>string slopQuantity = 27;</code>
       *
       * @return The slopQuantity.
       */
      public java.lang.String getSlopQuantity() {
        java.lang.Object ref = slopQuantity_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          slopQuantity_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string slopQuantity = 27;</code>
       *
       * @return The bytes for slopQuantity.
       */
      public com.google.protobuf.ByteString getSlopQuantityBytes() {
        java.lang.Object ref = slopQuantity_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          slopQuantity_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string slopQuantity = 27;</code>
       *
       * @param value The slopQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setSlopQuantity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        slopQuantity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string slopQuantity = 27;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSlopQuantity() {

        slopQuantity_ = getDefaultInstance().getSlopQuantity();
        onChanged();
        return this;
      }
      /**
       * <code>string slopQuantity = 27;</code>
       *
       * @param value The bytes for slopQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setSlopQuantityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        slopQuantity_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object timeRequiredForLoading_ = "";
      /**
       * <code>string timeRequiredForLoading = 28;</code>
       *
       * @return The timeRequiredForLoading.
       */
      public java.lang.String getTimeRequiredForLoading() {
        java.lang.Object ref = timeRequiredForLoading_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          timeRequiredForLoading_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string timeRequiredForLoading = 28;</code>
       *
       * @return The bytes for timeRequiredForLoading.
       */
      public com.google.protobuf.ByteString getTimeRequiredForLoadingBytes() {
        java.lang.Object ref = timeRequiredForLoading_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          timeRequiredForLoading_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string timeRequiredForLoading = 28;</code>
       *
       * @param value The timeRequiredForLoading to set.
       * @return This builder for chaining.
       */
      public Builder setTimeRequiredForLoading(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        timeRequiredForLoading_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string timeRequiredForLoading = 28;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTimeRequiredForLoading() {

        timeRequiredForLoading_ = getDefaultInstance().getTimeRequiredForLoading();
        onChanged();
        return this;
      }
      /**
       * <code>string timeRequiredForLoading = 28;</code>
       *
       * @param value The bytes for timeRequiredForLoading to set.
       * @return This builder for chaining.
       */
      public Builder setTimeRequiredForLoadingBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        timeRequiredForLoading_ = value;
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

      // @@protoc_insertion_point(builder_scope:LoadablePlanQuantity)
    }

    // @@protoc_insertion_point(class_scope:LoadablePlanQuantity)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanQuantity
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadablePlanQuantity> PARSER =
        new com.google.protobuf.AbstractParser<LoadablePlanQuantity>() {
          @java.lang.Override
          public LoadablePlanQuantity parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadablePlanQuantity(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadablePlanQuantity> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadablePlanQuantity> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanQuantity
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadablePlanStowageDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadablePlanStowageDetail)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string abbreviation = 1;</code>
     *
     * @return The abbreviation.
     */
    java.lang.String getAbbreviation();
    /**
     * <code>string abbreviation = 1;</code>
     *
     * @return The bytes for abbreviation.
     */
    com.google.protobuf.ByteString getAbbreviationBytes();

    /**
     * <code>string api = 2;</code>
     *
     * @return The api.
     */
    java.lang.String getApi();
    /**
     * <code>string api = 2;</code>
     *
     * @return The bytes for api.
     */
    com.google.protobuf.ByteString getApiBytes();

    /**
     * <code>int64 cargoNominationId = 3;</code>
     *
     * @return The cargoNominationId.
     */
    long getCargoNominationId();

    /**
     * <code>int64 cargoId = 4;</code>
     *
     * @return The cargoId.
     */
    long getCargoId();

    /**
     * <code>string colorCode = 5;</code>
     *
     * @return The colorCode.
     */
    java.lang.String getColorCode();
    /**
     * <code>string colorCode = 5;</code>
     *
     * @return The bytes for colorCode.
     */
    com.google.protobuf.ByteString getColorCodeBytes();

    /**
     * <code>string correctedUllage = 6;</code>
     *
     * @return The correctedUllage.
     */
    java.lang.String getCorrectedUllage();
    /**
     * <code>string correctedUllage = 6;</code>
     *
     * @return The bytes for correctedUllage.
     */
    com.google.protobuf.ByteString getCorrectedUllageBytes();

    /**
     * <code>string correctionFactor = 7;</code>
     *
     * @return The correctionFactor.
     */
    java.lang.String getCorrectionFactor();
    /**
     * <code>string correctionFactor = 7;</code>
     *
     * @return The bytes for correctionFactor.
     */
    com.google.protobuf.ByteString getCorrectionFactorBytes();

    /**
     * <code>string fillingPercentage = 8;</code>
     *
     * @return The fillingPercentage.
     */
    java.lang.String getFillingPercentage();
    /**
     * <code>string fillingPercentage = 8;</code>
     *
     * @return The bytes for fillingPercentage.
     */
    com.google.protobuf.ByteString getFillingPercentageBytes();

    /**
     * <code>int64 id = 9;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>bool isActive = 10;</code>
     *
     * @return The isActive.
     */
    boolean getIsActive();

    /**
     * <code>int64 loadablePatternId = 11;</code>
     *
     * @return The loadablePatternId.
     */
    long getLoadablePatternId();

    /**
     * <code>string observedBarrels = 12;</code>
     *
     * @return The observedBarrels.
     */
    java.lang.String getObservedBarrels();
    /**
     * <code>string observedBarrels = 12;</code>
     *
     * @return The bytes for observedBarrels.
     */
    com.google.protobuf.ByteString getObservedBarrelsBytes();

    /**
     * <code>string observedBarrelsAt60 = 13;</code>
     *
     * @return The observedBarrelsAt60.
     */
    java.lang.String getObservedBarrelsAt60();
    /**
     * <code>string observedBarrelsAt60 = 13;</code>
     *
     * @return The bytes for observedBarrelsAt60.
     */
    com.google.protobuf.ByteString getObservedBarrelsAt60Bytes();

    /**
     * <code>string observedM3 = 14;</code>
     *
     * @return The observedM3.
     */
    java.lang.String getObservedM3();
    /**
     * <code>string observedM3 = 14;</code>
     *
     * @return The bytes for observedM3.
     */
    com.google.protobuf.ByteString getObservedM3Bytes();

    /**
     * <code>string rdgUllage = 15;</code>
     *
     * @return The rdgUllage.
     */
    java.lang.String getRdgUllage();
    /**
     * <code>string rdgUllage = 15;</code>
     *
     * @return The bytes for rdgUllage.
     */
    com.google.protobuf.ByteString getRdgUllageBytes();

    /**
     * <code>int64 tankId = 16;</code>
     *
     * @return The tankId.
     */
    long getTankId();

    /**
     * <code>string tankname = 17;</code>
     *
     * @return The tankname.
     */
    java.lang.String getTankname();
    /**
     * <code>string tankname = 17;</code>
     *
     * @return The bytes for tankname.
     */
    com.google.protobuf.ByteString getTanknameBytes();

    /**
     * <code>string temperature = 18;</code>
     *
     * @return The temperature.
     */
    java.lang.String getTemperature();
    /**
     * <code>string temperature = 18;</code>
     *
     * @return The bytes for temperature.
     */
    com.google.protobuf.ByteString getTemperatureBytes();

    /**
     * <code>string weight = 19;</code>
     *
     * @return The weight.
     */
    java.lang.String getWeight();
    /**
     * <code>string weight = 19;</code>
     *
     * @return The bytes for weight.
     */
    com.google.protobuf.ByteString getWeightBytes();
  }
  /** Protobuf type {@code LoadablePlanStowageDetail} */
  public static final class LoadablePlanStowageDetail extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadablePlanStowageDetail)
      LoadablePlanStowageDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadablePlanStowageDetail.newBuilder() to construct.
    private LoadablePlanStowageDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadablePlanStowageDetail() {
      abbreviation_ = "";
      api_ = "";
      colorCode_ = "";
      correctedUllage_ = "";
      correctionFactor_ = "";
      fillingPercentage_ = "";
      observedBarrels_ = "";
      observedBarrelsAt60_ = "";
      observedM3_ = "";
      rdgUllage_ = "";
      tankname_ = "";
      temperature_ = "";
      weight_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadablePlanStowageDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadablePlanStowageDetail(
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

                abbreviation_ = s;
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                api_ = s;
                break;
              }
            case 24:
              {
                cargoNominationId_ = input.readInt64();
                break;
              }
            case 32:
              {
                cargoId_ = input.readInt64();
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                colorCode_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                correctedUllage_ = s;
                break;
              }
            case 58:
              {
                java.lang.String s = input.readStringRequireUtf8();

                correctionFactor_ = s;
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                fillingPercentage_ = s;
                break;
              }
            case 72:
              {
                id_ = input.readInt64();
                break;
              }
            case 80:
              {
                isActive_ = input.readBool();
                break;
              }
            case 88:
              {
                loadablePatternId_ = input.readInt64();
                break;
              }
            case 98:
              {
                java.lang.String s = input.readStringRequireUtf8();

                observedBarrels_ = s;
                break;
              }
            case 106:
              {
                java.lang.String s = input.readStringRequireUtf8();

                observedBarrelsAt60_ = s;
                break;
              }
            case 114:
              {
                java.lang.String s = input.readStringRequireUtf8();

                observedM3_ = s;
                break;
              }
            case 122:
              {
                java.lang.String s = input.readStringRequireUtf8();

                rdgUllage_ = s;
                break;
              }
            case 128:
              {
                tankId_ = input.readInt64();
                break;
              }
            case 138:
              {
                java.lang.String s = input.readStringRequireUtf8();

                tankname_ = s;
                break;
              }
            case 146:
              {
                java.lang.String s = input.readStringRequireUtf8();

                temperature_ = s;
                break;
              }
            case 154:
              {
                java.lang.String s = input.readStringRequireUtf8();

                weight_ = s;
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
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadablePlanStowageDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadablePlanStowageDetail_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
                  .class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
                  .Builder.class);
    }

    public static final int ABBREVIATION_FIELD_NUMBER = 1;
    private volatile java.lang.Object abbreviation_;
    /**
     * <code>string abbreviation = 1;</code>
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
     * <code>string abbreviation = 1;</code>
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

    public static final int API_FIELD_NUMBER = 2;
    private volatile java.lang.Object api_;
    /**
     * <code>string api = 2;</code>
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
     * <code>string api = 2;</code>
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

    public static final int CARGONOMINATIONID_FIELD_NUMBER = 3;
    private long cargoNominationId_;
    /**
     * <code>int64 cargoNominationId = 3;</code>
     *
     * @return The cargoNominationId.
     */
    public long getCargoNominationId() {
      return cargoNominationId_;
    }

    public static final int CARGOID_FIELD_NUMBER = 4;
    private long cargoId_;
    /**
     * <code>int64 cargoId = 4;</code>
     *
     * @return The cargoId.
     */
    public long getCargoId() {
      return cargoId_;
    }

    public static final int COLORCODE_FIELD_NUMBER = 5;
    private volatile java.lang.Object colorCode_;
    /**
     * <code>string colorCode = 5;</code>
     *
     * @return The colorCode.
     */
    public java.lang.String getColorCode() {
      java.lang.Object ref = colorCode_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        colorCode_ = s;
        return s;
      }
    }
    /**
     * <code>string colorCode = 5;</code>
     *
     * @return The bytes for colorCode.
     */
    public com.google.protobuf.ByteString getColorCodeBytes() {
      java.lang.Object ref = colorCode_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        colorCode_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CORRECTEDULLAGE_FIELD_NUMBER = 6;
    private volatile java.lang.Object correctedUllage_;
    /**
     * <code>string correctedUllage = 6;</code>
     *
     * @return The correctedUllage.
     */
    public java.lang.String getCorrectedUllage() {
      java.lang.Object ref = correctedUllage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        correctedUllage_ = s;
        return s;
      }
    }
    /**
     * <code>string correctedUllage = 6;</code>
     *
     * @return The bytes for correctedUllage.
     */
    public com.google.protobuf.ByteString getCorrectedUllageBytes() {
      java.lang.Object ref = correctedUllage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        correctedUllage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CORRECTIONFACTOR_FIELD_NUMBER = 7;
    private volatile java.lang.Object correctionFactor_;
    /**
     * <code>string correctionFactor = 7;</code>
     *
     * @return The correctionFactor.
     */
    public java.lang.String getCorrectionFactor() {
      java.lang.Object ref = correctionFactor_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        correctionFactor_ = s;
        return s;
      }
    }
    /**
     * <code>string correctionFactor = 7;</code>
     *
     * @return The bytes for correctionFactor.
     */
    public com.google.protobuf.ByteString getCorrectionFactorBytes() {
      java.lang.Object ref = correctionFactor_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        correctionFactor_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int FILLINGPERCENTAGE_FIELD_NUMBER = 8;
    private volatile java.lang.Object fillingPercentage_;
    /**
     * <code>string fillingPercentage = 8;</code>
     *
     * @return The fillingPercentage.
     */
    public java.lang.String getFillingPercentage() {
      java.lang.Object ref = fillingPercentage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        fillingPercentage_ = s;
        return s;
      }
    }
    /**
     * <code>string fillingPercentage = 8;</code>
     *
     * @return The bytes for fillingPercentage.
     */
    public com.google.protobuf.ByteString getFillingPercentageBytes() {
      java.lang.Object ref = fillingPercentage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        fillingPercentage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ID_FIELD_NUMBER = 9;
    private long id_;
    /**
     * <code>int64 id = 9;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
    }

    public static final int ISACTIVE_FIELD_NUMBER = 10;
    private boolean isActive_;
    /**
     * <code>bool isActive = 10;</code>
     *
     * @return The isActive.
     */
    public boolean getIsActive() {
      return isActive_;
    }

    public static final int LOADABLEPATTERNID_FIELD_NUMBER = 11;
    private long loadablePatternId_;
    /**
     * <code>int64 loadablePatternId = 11;</code>
     *
     * @return The loadablePatternId.
     */
    public long getLoadablePatternId() {
      return loadablePatternId_;
    }

    public static final int OBSERVEDBARRELS_FIELD_NUMBER = 12;
    private volatile java.lang.Object observedBarrels_;
    /**
     * <code>string observedBarrels = 12;</code>
     *
     * @return The observedBarrels.
     */
    public java.lang.String getObservedBarrels() {
      java.lang.Object ref = observedBarrels_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        observedBarrels_ = s;
        return s;
      }
    }
    /**
     * <code>string observedBarrels = 12;</code>
     *
     * @return The bytes for observedBarrels.
     */
    public com.google.protobuf.ByteString getObservedBarrelsBytes() {
      java.lang.Object ref = observedBarrels_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        observedBarrels_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int OBSERVEDBARRELSAT60_FIELD_NUMBER = 13;
    private volatile java.lang.Object observedBarrelsAt60_;
    /**
     * <code>string observedBarrelsAt60 = 13;</code>
     *
     * @return The observedBarrelsAt60.
     */
    public java.lang.String getObservedBarrelsAt60() {
      java.lang.Object ref = observedBarrelsAt60_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        observedBarrelsAt60_ = s;
        return s;
      }
    }
    /**
     * <code>string observedBarrelsAt60 = 13;</code>
     *
     * @return The bytes for observedBarrelsAt60.
     */
    public com.google.protobuf.ByteString getObservedBarrelsAt60Bytes() {
      java.lang.Object ref = observedBarrelsAt60_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        observedBarrelsAt60_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int OBSERVEDM3_FIELD_NUMBER = 14;
    private volatile java.lang.Object observedM3_;
    /**
     * <code>string observedM3 = 14;</code>
     *
     * @return The observedM3.
     */
    public java.lang.String getObservedM3() {
      java.lang.Object ref = observedM3_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        observedM3_ = s;
        return s;
      }
    }
    /**
     * <code>string observedM3 = 14;</code>
     *
     * @return The bytes for observedM3.
     */
    public com.google.protobuf.ByteString getObservedM3Bytes() {
      java.lang.Object ref = observedM3_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        observedM3_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int RDGULLAGE_FIELD_NUMBER = 15;
    private volatile java.lang.Object rdgUllage_;
    /**
     * <code>string rdgUllage = 15;</code>
     *
     * @return The rdgUllage.
     */
    public java.lang.String getRdgUllage() {
      java.lang.Object ref = rdgUllage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        rdgUllage_ = s;
        return s;
      }
    }
    /**
     * <code>string rdgUllage = 15;</code>
     *
     * @return The bytes for rdgUllage.
     */
    public com.google.protobuf.ByteString getRdgUllageBytes() {
      java.lang.Object ref = rdgUllage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        rdgUllage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TANKID_FIELD_NUMBER = 16;
    private long tankId_;
    /**
     * <code>int64 tankId = 16;</code>
     *
     * @return The tankId.
     */
    public long getTankId() {
      return tankId_;
    }

    public static final int TANKNAME_FIELD_NUMBER = 17;
    private volatile java.lang.Object tankname_;
    /**
     * <code>string tankname = 17;</code>
     *
     * @return The tankname.
     */
    public java.lang.String getTankname() {
      java.lang.Object ref = tankname_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tankname_ = s;
        return s;
      }
    }
    /**
     * <code>string tankname = 17;</code>
     *
     * @return The bytes for tankname.
     */
    public com.google.protobuf.ByteString getTanknameBytes() {
      java.lang.Object ref = tankname_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        tankname_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TEMPERATURE_FIELD_NUMBER = 18;
    private volatile java.lang.Object temperature_;
    /**
     * <code>string temperature = 18;</code>
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
     * <code>string temperature = 18;</code>
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

    public static final int WEIGHT_FIELD_NUMBER = 19;
    private volatile java.lang.Object weight_;
    /**
     * <code>string weight = 19;</code>
     *
     * @return The weight.
     */
    public java.lang.String getWeight() {
      java.lang.Object ref = weight_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        weight_ = s;
        return s;
      }
    }
    /**
     * <code>string weight = 19;</code>
     *
     * @return The bytes for weight.
     */
    public com.google.protobuf.ByteString getWeightBytes() {
      java.lang.Object ref = weight_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        weight_ = b;
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
      if (!getAbbreviationBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, abbreviation_);
      }
      if (!getApiBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, api_);
      }
      if (cargoNominationId_ != 0L) {
        output.writeInt64(3, cargoNominationId_);
      }
      if (cargoId_ != 0L) {
        output.writeInt64(4, cargoId_);
      }
      if (!getColorCodeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, colorCode_);
      }
      if (!getCorrectedUllageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, correctedUllage_);
      }
      if (!getCorrectionFactorBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, correctionFactor_);
      }
      if (!getFillingPercentageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, fillingPercentage_);
      }
      if (id_ != 0L) {
        output.writeInt64(9, id_);
      }
      if (isActive_ != false) {
        output.writeBool(10, isActive_);
      }
      if (loadablePatternId_ != 0L) {
        output.writeInt64(11, loadablePatternId_);
      }
      if (!getObservedBarrelsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 12, observedBarrels_);
      }
      if (!getObservedBarrelsAt60Bytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 13, observedBarrelsAt60_);
      }
      if (!getObservedM3Bytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 14, observedM3_);
      }
      if (!getRdgUllageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 15, rdgUllage_);
      }
      if (tankId_ != 0L) {
        output.writeInt64(16, tankId_);
      }
      if (!getTanknameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 17, tankname_);
      }
      if (!getTemperatureBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 18, temperature_);
      }
      if (!getWeightBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 19, weight_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getAbbreviationBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, abbreviation_);
      }
      if (!getApiBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, api_);
      }
      if (cargoNominationId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, cargoNominationId_);
      }
      if (cargoId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, cargoId_);
      }
      if (!getColorCodeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, colorCode_);
      }
      if (!getCorrectedUllageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, correctedUllage_);
      }
      if (!getCorrectionFactorBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, correctionFactor_);
      }
      if (!getFillingPercentageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, fillingPercentage_);
      }
      if (id_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(9, id_);
      }
      if (isActive_ != false) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(10, isActive_);
      }
      if (loadablePatternId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(11, loadablePatternId_);
      }
      if (!getObservedBarrelsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(12, observedBarrels_);
      }
      if (!getObservedBarrelsAt60Bytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(13, observedBarrelsAt60_);
      }
      if (!getObservedM3Bytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(14, observedM3_);
      }
      if (!getRdgUllageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(15, rdgUllage_);
      }
      if (tankId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(16, tankId_);
      }
      if (!getTanknameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(17, tankname_);
      }
      if (!getTemperatureBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(18, temperature_);
      }
      if (!getWeightBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(19, weight_);
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
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail) obj;

      if (!getAbbreviation().equals(other.getAbbreviation())) return false;
      if (!getApi().equals(other.getApi())) return false;
      if (getCargoNominationId() != other.getCargoNominationId()) return false;
      if (getCargoId() != other.getCargoId()) return false;
      if (!getColorCode().equals(other.getColorCode())) return false;
      if (!getCorrectedUllage().equals(other.getCorrectedUllage())) return false;
      if (!getCorrectionFactor().equals(other.getCorrectionFactor())) return false;
      if (!getFillingPercentage().equals(other.getFillingPercentage())) return false;
      if (getId() != other.getId()) return false;
      if (getIsActive() != other.getIsActive()) return false;
      if (getLoadablePatternId() != other.getLoadablePatternId()) return false;
      if (!getObservedBarrels().equals(other.getObservedBarrels())) return false;
      if (!getObservedBarrelsAt60().equals(other.getObservedBarrelsAt60())) return false;
      if (!getObservedM3().equals(other.getObservedM3())) return false;
      if (!getRdgUllage().equals(other.getRdgUllage())) return false;
      if (getTankId() != other.getTankId()) return false;
      if (!getTankname().equals(other.getTankname())) return false;
      if (!getTemperature().equals(other.getTemperature())) return false;
      if (!getWeight().equals(other.getWeight())) return false;
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
      hash = (37 * hash) + ABBREVIATION_FIELD_NUMBER;
      hash = (53 * hash) + getAbbreviation().hashCode();
      hash = (37 * hash) + API_FIELD_NUMBER;
      hash = (53 * hash) + getApi().hashCode();
      hash = (37 * hash) + CARGONOMINATIONID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoNominationId());
      hash = (37 * hash) + CARGOID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoId());
      hash = (37 * hash) + COLORCODE_FIELD_NUMBER;
      hash = (53 * hash) + getColorCode().hashCode();
      hash = (37 * hash) + CORRECTEDULLAGE_FIELD_NUMBER;
      hash = (53 * hash) + getCorrectedUllage().hashCode();
      hash = (37 * hash) + CORRECTIONFACTOR_FIELD_NUMBER;
      hash = (53 * hash) + getCorrectionFactor().hashCode();
      hash = (37 * hash) + FILLINGPERCENTAGE_FIELD_NUMBER;
      hash = (53 * hash) + getFillingPercentage().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
      hash = (37 * hash) + ISACTIVE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsActive());
      hash = (37 * hash) + LOADABLEPATTERNID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadablePatternId());
      hash = (37 * hash) + OBSERVEDBARRELS_FIELD_NUMBER;
      hash = (53 * hash) + getObservedBarrels().hashCode();
      hash = (37 * hash) + OBSERVEDBARRELSAT60_FIELD_NUMBER;
      hash = (53 * hash) + getObservedBarrelsAt60().hashCode();
      hash = (37 * hash) + OBSERVEDM3_FIELD_NUMBER;
      hash = (53 * hash) + getObservedM3().hashCode();
      hash = (37 * hash) + RDGULLAGE_FIELD_NUMBER;
      hash = (53 * hash) + getRdgUllage().hashCode();
      hash = (37 * hash) + TANKID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getTankId());
      hash = (37 * hash) + TANKNAME_FIELD_NUMBER;
      hash = (53 * hash) + getTankname().hashCode();
      hash = (37 * hash) + TEMPERATURE_FIELD_NUMBER;
      hash = (53 * hash) + getTemperature().hashCode();
      hash = (37 * hash) + WEIGHT_FIELD_NUMBER;
      hash = (53 * hash) + getWeight().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
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
    /** Protobuf type {@code LoadablePlanStowageDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadablePlanStowageDetail)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanStowageDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanStowageDetail_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
                    .class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
                    .Builder.class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail.newBuilder()
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
        abbreviation_ = "";

        api_ = "";

        cargoNominationId_ = 0L;

        cargoId_ = 0L;

        colorCode_ = "";

        correctedUllage_ = "";

        correctionFactor_ = "";

        fillingPercentage_ = "";

        id_ = 0L;

        isActive_ = false;

        loadablePatternId_ = 0L;

        observedBarrels_ = "";

        observedBarrelsAt60_ = "";

        observedM3_ = "";

        rdgUllage_ = "";

        tankId_ = 0L;

        tankname_ = "";

        temperature_ = "";

        weight_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadablePlanStowageDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
          build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail result =
            new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail(
                this);
        result.abbreviation_ = abbreviation_;
        result.api_ = api_;
        result.cargoNominationId_ = cargoNominationId_;
        result.cargoId_ = cargoId_;
        result.colorCode_ = colorCode_;
        result.correctedUllage_ = correctedUllage_;
        result.correctionFactor_ = correctionFactor_;
        result.fillingPercentage_ = fillingPercentage_;
        result.id_ = id_;
        result.isActive_ = isActive_;
        result.loadablePatternId_ = loadablePatternId_;
        result.observedBarrels_ = observedBarrels_;
        result.observedBarrelsAt60_ = observedBarrelsAt60_;
        result.observedM3_ = observedM3_;
        result.rdgUllage_ = rdgUllage_;
        result.tankId_ = tankId_;
        result.tankname_ = tankname_;
        result.temperature_ = temperature_;
        result.weight_ = weight_;
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
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
              other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
                .getDefaultInstance()) return this;
        if (!other.getAbbreviation().isEmpty()) {
          abbreviation_ = other.abbreviation_;
          onChanged();
        }
        if (!other.getApi().isEmpty()) {
          api_ = other.api_;
          onChanged();
        }
        if (other.getCargoNominationId() != 0L) {
          setCargoNominationId(other.getCargoNominationId());
        }
        if (other.getCargoId() != 0L) {
          setCargoId(other.getCargoId());
        }
        if (!other.getColorCode().isEmpty()) {
          colorCode_ = other.colorCode_;
          onChanged();
        }
        if (!other.getCorrectedUllage().isEmpty()) {
          correctedUllage_ = other.correctedUllage_;
          onChanged();
        }
        if (!other.getCorrectionFactor().isEmpty()) {
          correctionFactor_ = other.correctionFactor_;
          onChanged();
        }
        if (!other.getFillingPercentage().isEmpty()) {
          fillingPercentage_ = other.fillingPercentage_;
          onChanged();
        }
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (other.getIsActive() != false) {
          setIsActive(other.getIsActive());
        }
        if (other.getLoadablePatternId() != 0L) {
          setLoadablePatternId(other.getLoadablePatternId());
        }
        if (!other.getObservedBarrels().isEmpty()) {
          observedBarrels_ = other.observedBarrels_;
          onChanged();
        }
        if (!other.getObservedBarrelsAt60().isEmpty()) {
          observedBarrelsAt60_ = other.observedBarrelsAt60_;
          onChanged();
        }
        if (!other.getObservedM3().isEmpty()) {
          observedM3_ = other.observedM3_;
          onChanged();
        }
        if (!other.getRdgUllage().isEmpty()) {
          rdgUllage_ = other.rdgUllage_;
          onChanged();
        }
        if (other.getTankId() != 0L) {
          setTankId(other.getTankId());
        }
        if (!other.getTankname().isEmpty()) {
          tankname_ = other.tankname_;
          onChanged();
        }
        if (!other.getTemperature().isEmpty()) {
          temperature_ = other.temperature_;
          onChanged();
        }
        if (!other.getWeight().isEmpty()) {
          weight_ = other.weight_;
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object abbreviation_ = "";
      /**
       * <code>string abbreviation = 1;</code>
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
       * <code>string abbreviation = 1;</code>
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
       * <code>string abbreviation = 1;</code>
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
       * <code>string abbreviation = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearAbbreviation() {

        abbreviation_ = getDefaultInstance().getAbbreviation();
        onChanged();
        return this;
      }
      /**
       * <code>string abbreviation = 1;</code>
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
       * <code>string api = 2;</code>
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
       * <code>string api = 2;</code>
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
       * <code>string api = 2;</code>
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
       * <code>string api = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearApi() {

        api_ = getDefaultInstance().getApi();
        onChanged();
        return this;
      }
      /**
       * <code>string api = 2;</code>
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

      private long cargoNominationId_;
      /**
       * <code>int64 cargoNominationId = 3;</code>
       *
       * @return The cargoNominationId.
       */
      public long getCargoNominationId() {
        return cargoNominationId_;
      }
      /**
       * <code>int64 cargoNominationId = 3;</code>
       *
       * @param value The cargoNominationId to set.
       * @return This builder for chaining.
       */
      public Builder setCargoNominationId(long value) {

        cargoNominationId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 cargoNominationId = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoNominationId() {

        cargoNominationId_ = 0L;
        onChanged();
        return this;
      }

      private long cargoId_;
      /**
       * <code>int64 cargoId = 4;</code>
       *
       * @return The cargoId.
       */
      public long getCargoId() {
        return cargoId_;
      }
      /**
       * <code>int64 cargoId = 4;</code>
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
       * <code>int64 cargoId = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoId() {

        cargoId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object colorCode_ = "";
      /**
       * <code>string colorCode = 5;</code>
       *
       * @return The colorCode.
       */
      public java.lang.String getColorCode() {
        java.lang.Object ref = colorCode_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          colorCode_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string colorCode = 5;</code>
       *
       * @return The bytes for colorCode.
       */
      public com.google.protobuf.ByteString getColorCodeBytes() {
        java.lang.Object ref = colorCode_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          colorCode_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string colorCode = 5;</code>
       *
       * @param value The colorCode to set.
       * @return This builder for chaining.
       */
      public Builder setColorCode(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        colorCode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string colorCode = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearColorCode() {

        colorCode_ = getDefaultInstance().getColorCode();
        onChanged();
        return this;
      }
      /**
       * <code>string colorCode = 5;</code>
       *
       * @param value The bytes for colorCode to set.
       * @return This builder for chaining.
       */
      public Builder setColorCodeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        colorCode_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object correctedUllage_ = "";
      /**
       * <code>string correctedUllage = 6;</code>
       *
       * @return The correctedUllage.
       */
      public java.lang.String getCorrectedUllage() {
        java.lang.Object ref = correctedUllage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          correctedUllage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string correctedUllage = 6;</code>
       *
       * @return The bytes for correctedUllage.
       */
      public com.google.protobuf.ByteString getCorrectedUllageBytes() {
        java.lang.Object ref = correctedUllage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          correctedUllage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string correctedUllage = 6;</code>
       *
       * @param value The correctedUllage to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectedUllage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        correctedUllage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string correctedUllage = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCorrectedUllage() {

        correctedUllage_ = getDefaultInstance().getCorrectedUllage();
        onChanged();
        return this;
      }
      /**
       * <code>string correctedUllage = 6;</code>
       *
       * @param value The bytes for correctedUllage to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectedUllageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        correctedUllage_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object correctionFactor_ = "";
      /**
       * <code>string correctionFactor = 7;</code>
       *
       * @return The correctionFactor.
       */
      public java.lang.String getCorrectionFactor() {
        java.lang.Object ref = correctionFactor_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          correctionFactor_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string correctionFactor = 7;</code>
       *
       * @return The bytes for correctionFactor.
       */
      public com.google.protobuf.ByteString getCorrectionFactorBytes() {
        java.lang.Object ref = correctionFactor_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          correctionFactor_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string correctionFactor = 7;</code>
       *
       * @param value The correctionFactor to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectionFactor(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        correctionFactor_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string correctionFactor = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCorrectionFactor() {

        correctionFactor_ = getDefaultInstance().getCorrectionFactor();
        onChanged();
        return this;
      }
      /**
       * <code>string correctionFactor = 7;</code>
       *
       * @param value The bytes for correctionFactor to set.
       * @return This builder for chaining.
       */
      public Builder setCorrectionFactorBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        correctionFactor_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object fillingPercentage_ = "";
      /**
       * <code>string fillingPercentage = 8;</code>
       *
       * @return The fillingPercentage.
       */
      public java.lang.String getFillingPercentage() {
        java.lang.Object ref = fillingPercentage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          fillingPercentage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string fillingPercentage = 8;</code>
       *
       * @return The bytes for fillingPercentage.
       */
      public com.google.protobuf.ByteString getFillingPercentageBytes() {
        java.lang.Object ref = fillingPercentage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          fillingPercentage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string fillingPercentage = 8;</code>
       *
       * @param value The fillingPercentage to set.
       * @return This builder for chaining.
       */
      public Builder setFillingPercentage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        fillingPercentage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string fillingPercentage = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearFillingPercentage() {

        fillingPercentage_ = getDefaultInstance().getFillingPercentage();
        onChanged();
        return this;
      }
      /**
       * <code>string fillingPercentage = 8;</code>
       *
       * @param value The bytes for fillingPercentage to set.
       * @return This builder for chaining.
       */
      public Builder setFillingPercentageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        fillingPercentage_ = value;
        onChanged();
        return this;
      }

      private long id_;
      /**
       * <code>int64 id = 9;</code>
       *
       * @return The id.
       */
      public long getId() {
        return id_;
      }
      /**
       * <code>int64 id = 9;</code>
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
       * <code>int64 id = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {

        id_ = 0L;
        onChanged();
        return this;
      }

      private boolean isActive_;
      /**
       * <code>bool isActive = 10;</code>
       *
       * @return The isActive.
       */
      public boolean getIsActive() {
        return isActive_;
      }
      /**
       * <code>bool isActive = 10;</code>
       *
       * @param value The isActive to set.
       * @return This builder for chaining.
       */
      public Builder setIsActive(boolean value) {

        isActive_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool isActive = 10;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearIsActive() {

        isActive_ = false;
        onChanged();
        return this;
      }

      private long loadablePatternId_;
      /**
       * <code>int64 loadablePatternId = 11;</code>
       *
       * @return The loadablePatternId.
       */
      public long getLoadablePatternId() {
        return loadablePatternId_;
      }
      /**
       * <code>int64 loadablePatternId = 11;</code>
       *
       * @param value The loadablePatternId to set.
       * @return This builder for chaining.
       */
      public Builder setLoadablePatternId(long value) {

        loadablePatternId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 loadablePatternId = 11;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadablePatternId() {

        loadablePatternId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object observedBarrels_ = "";
      /**
       * <code>string observedBarrels = 12;</code>
       *
       * @return The observedBarrels.
       */
      public java.lang.String getObservedBarrels() {
        java.lang.Object ref = observedBarrels_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          observedBarrels_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string observedBarrels = 12;</code>
       *
       * @return The bytes for observedBarrels.
       */
      public com.google.protobuf.ByteString getObservedBarrelsBytes() {
        java.lang.Object ref = observedBarrels_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          observedBarrels_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string observedBarrels = 12;</code>
       *
       * @param value The observedBarrels to set.
       * @return This builder for chaining.
       */
      public Builder setObservedBarrels(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        observedBarrels_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string observedBarrels = 12;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearObservedBarrels() {

        observedBarrels_ = getDefaultInstance().getObservedBarrels();
        onChanged();
        return this;
      }
      /**
       * <code>string observedBarrels = 12;</code>
       *
       * @param value The bytes for observedBarrels to set.
       * @return This builder for chaining.
       */
      public Builder setObservedBarrelsBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        observedBarrels_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object observedBarrelsAt60_ = "";
      /**
       * <code>string observedBarrelsAt60 = 13;</code>
       *
       * @return The observedBarrelsAt60.
       */
      public java.lang.String getObservedBarrelsAt60() {
        java.lang.Object ref = observedBarrelsAt60_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          observedBarrelsAt60_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string observedBarrelsAt60 = 13;</code>
       *
       * @return The bytes for observedBarrelsAt60.
       */
      public com.google.protobuf.ByteString getObservedBarrelsAt60Bytes() {
        java.lang.Object ref = observedBarrelsAt60_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          observedBarrelsAt60_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string observedBarrelsAt60 = 13;</code>
       *
       * @param value The observedBarrelsAt60 to set.
       * @return This builder for chaining.
       */
      public Builder setObservedBarrelsAt60(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        observedBarrelsAt60_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string observedBarrelsAt60 = 13;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearObservedBarrelsAt60() {

        observedBarrelsAt60_ = getDefaultInstance().getObservedBarrelsAt60();
        onChanged();
        return this;
      }
      /**
       * <code>string observedBarrelsAt60 = 13;</code>
       *
       * @param value The bytes for observedBarrelsAt60 to set.
       * @return This builder for chaining.
       */
      public Builder setObservedBarrelsAt60Bytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        observedBarrelsAt60_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object observedM3_ = "";
      /**
       * <code>string observedM3 = 14;</code>
       *
       * @return The observedM3.
       */
      public java.lang.String getObservedM3() {
        java.lang.Object ref = observedM3_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          observedM3_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string observedM3 = 14;</code>
       *
       * @return The bytes for observedM3.
       */
      public com.google.protobuf.ByteString getObservedM3Bytes() {
        java.lang.Object ref = observedM3_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          observedM3_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string observedM3 = 14;</code>
       *
       * @param value The observedM3 to set.
       * @return This builder for chaining.
       */
      public Builder setObservedM3(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        observedM3_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string observedM3 = 14;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearObservedM3() {

        observedM3_ = getDefaultInstance().getObservedM3();
        onChanged();
        return this;
      }
      /**
       * <code>string observedM3 = 14;</code>
       *
       * @param value The bytes for observedM3 to set.
       * @return This builder for chaining.
       */
      public Builder setObservedM3Bytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        observedM3_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object rdgUllage_ = "";
      /**
       * <code>string rdgUllage = 15;</code>
       *
       * @return The rdgUllage.
       */
      public java.lang.String getRdgUllage() {
        java.lang.Object ref = rdgUllage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          rdgUllage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string rdgUllage = 15;</code>
       *
       * @return The bytes for rdgUllage.
       */
      public com.google.protobuf.ByteString getRdgUllageBytes() {
        java.lang.Object ref = rdgUllage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          rdgUllage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string rdgUllage = 15;</code>
       *
       * @param value The rdgUllage to set.
       * @return This builder for chaining.
       */
      public Builder setRdgUllage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        rdgUllage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string rdgUllage = 15;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearRdgUllage() {

        rdgUllage_ = getDefaultInstance().getRdgUllage();
        onChanged();
        return this;
      }
      /**
       * <code>string rdgUllage = 15;</code>
       *
       * @param value The bytes for rdgUllage to set.
       * @return This builder for chaining.
       */
      public Builder setRdgUllageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        rdgUllage_ = value;
        onChanged();
        return this;
      }

      private long tankId_;
      /**
       * <code>int64 tankId = 16;</code>
       *
       * @return The tankId.
       */
      public long getTankId() {
        return tankId_;
      }
      /**
       * <code>int64 tankId = 16;</code>
       *
       * @param value The tankId to set.
       * @return This builder for chaining.
       */
      public Builder setTankId(long value) {

        tankId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 tankId = 16;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTankId() {

        tankId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object tankname_ = "";
      /**
       * <code>string tankname = 17;</code>
       *
       * @return The tankname.
       */
      public java.lang.String getTankname() {
        java.lang.Object ref = tankname_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          tankname_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string tankname = 17;</code>
       *
       * @return The bytes for tankname.
       */
      public com.google.protobuf.ByteString getTanknameBytes() {
        java.lang.Object ref = tankname_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          tankname_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string tankname = 17;</code>
       *
       * @param value The tankname to set.
       * @return This builder for chaining.
       */
      public Builder setTankname(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        tankname_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string tankname = 17;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTankname() {

        tankname_ = getDefaultInstance().getTankname();
        onChanged();
        return this;
      }
      /**
       * <code>string tankname = 17;</code>
       *
       * @param value The bytes for tankname to set.
       * @return This builder for chaining.
       */
      public Builder setTanknameBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        tankname_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object temperature_ = "";
      /**
       * <code>string temperature = 18;</code>
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
       * <code>string temperature = 18;</code>
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
       * <code>string temperature = 18;</code>
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
       * <code>string temperature = 18;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTemperature() {

        temperature_ = getDefaultInstance().getTemperature();
        onChanged();
        return this;
      }
      /**
       * <code>string temperature = 18;</code>
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

      private java.lang.Object weight_ = "";
      /**
       * <code>string weight = 19;</code>
       *
       * @return The weight.
       */
      public java.lang.String getWeight() {
        java.lang.Object ref = weight_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          weight_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string weight = 19;</code>
       *
       * @return The bytes for weight.
       */
      public com.google.protobuf.ByteString getWeightBytes() {
        java.lang.Object ref = weight_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          weight_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string weight = 19;</code>
       *
       * @param value The weight to set.
       * @return This builder for chaining.
       */
      public Builder setWeight(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        weight_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string weight = 19;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearWeight() {

        weight_ = getDefaultInstance().getWeight();
        onChanged();
        return this;
      }
      /**
       * <code>string weight = 19;</code>
       *
       * @param value The bytes for weight to set.
       * @return This builder for chaining.
       */
      public Builder setWeightBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        weight_ = value;
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

      // @@protoc_insertion_point(builder_scope:LoadablePlanStowageDetail)
    }

    // @@protoc_insertion_point(class_scope:LoadablePlanStowageDetail)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadablePlanStowageDetail
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadablePlanStowageDetail> PARSER =
        new com.google.protobuf.AbstractParser<LoadablePlanStowageDetail>() {
          @java.lang.Override
          public LoadablePlanStowageDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadablePlanStowageDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadablePlanStowageDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadablePlanStowageDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanStowageDetail
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadableQuantityOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadableQuantity)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string ballast = 1;</code>
     *
     * @return The ballast.
     */
    java.lang.String getBallast();
    /**
     * <code>string ballast = 1;</code>
     *
     * @return The bytes for ballast.
     */
    com.google.protobuf.ByteString getBallastBytes();

    /**
     * <code>string boilerWaterOnBoard = 2;</code>
     *
     * @return The boilerWaterOnBoard.
     */
    java.lang.String getBoilerWaterOnBoard();
    /**
     * <code>string boilerWaterOnBoard = 2;</code>
     *
     * @return The bytes for boilerWaterOnBoard.
     */
    com.google.protobuf.ByteString getBoilerWaterOnBoardBytes();

    /**
     * <code>string constant = 3;</code>
     *
     * @return The constant.
     */
    java.lang.String getConstant();
    /**
     * <code>string constant = 3;</code>
     *
     * @return The bytes for constant.
     */
    com.google.protobuf.ByteString getConstantBytes();

    /**
     * <code>string deadWeight = 4;</code>
     *
     * @return The deadWeight.
     */
    java.lang.String getDeadWeight();
    /**
     * <code>string deadWeight = 4;</code>
     *
     * @return The bytes for deadWeight.
     */
    com.google.protobuf.ByteString getDeadWeightBytes();

    /**
     * <code>string displacementAtDraftRestriction = 5;</code>
     *
     * @return The displacementAtDraftRestriction.
     */
    java.lang.String getDisplacementAtDraftRestriction();
    /**
     * <code>string displacementAtDraftRestriction = 5;</code>
     *
     * @return The bytes for displacementAtDraftRestriction.
     */
    com.google.protobuf.ByteString getDisplacementAtDraftRestrictionBytes();

    /**
     * <code>string distanceFromLastPort = 6;</code>
     *
     * @return The distanceFromLastPort.
     */
    java.lang.String getDistanceFromLastPort();
    /**
     * <code>string distanceFromLastPort = 6;</code>
     *
     * @return The bytes for distanceFromLastPort.
     */
    com.google.protobuf.ByteString getDistanceFromLastPortBytes();

    /**
     * <code>string draftRestriction = 7;</code>
     *
     * @return The draftRestriction.
     */
    java.lang.String getDraftRestriction();
    /**
     * <code>string draftRestriction = 7;</code>
     *
     * @return The bytes for draftRestriction.
     */
    com.google.protobuf.ByteString getDraftRestrictionBytes();

    /**
     * <code>string estimatedDOOnBoard = 8;</code>
     *
     * @return The estimatedDOOnBoard.
     */
    java.lang.String getEstimatedDOOnBoard();
    /**
     * <code>string estimatedDOOnBoard = 8;</code>
     *
     * @return The bytes for estimatedDOOnBoard.
     */
    com.google.protobuf.ByteString getEstimatedDOOnBoardBytes();

    /**
     * <code>string estimatedFOOnBoard = 9;</code>
     *
     * @return The estimatedFOOnBoard.
     */
    java.lang.String getEstimatedFOOnBoard();
    /**
     * <code>string estimatedFOOnBoard = 9;</code>
     *
     * @return The bytes for estimatedFOOnBoard.
     */
    com.google.protobuf.ByteString getEstimatedFOOnBoardBytes();

    /**
     * <code>string estimatedFWOnBoard = 10;</code>
     *
     * @return The estimatedFWOnBoard.
     */
    java.lang.String getEstimatedFWOnBoard();
    /**
     * <code>string estimatedFWOnBoard = 10;</code>
     *
     * @return The bytes for estimatedFWOnBoard.
     */
    com.google.protobuf.ByteString getEstimatedFWOnBoardBytes();

    /**
     * <code>string estimatedSagging = 11;</code>
     *
     * @return The estimatedSagging.
     */
    java.lang.String getEstimatedSagging();
    /**
     * <code>string estimatedSagging = 11;</code>
     *
     * @return The bytes for estimatedSagging.
     */
    com.google.protobuf.ByteString getEstimatedSaggingBytes();

    /**
     * <code>string estimatedSeaDensity = 12;</code>
     *
     * @return The estimatedSeaDensity.
     */
    java.lang.String getEstimatedSeaDensity();
    /**
     * <code>string estimatedSeaDensity = 12;</code>
     *
     * @return The bytes for estimatedSeaDensity.
     */
    com.google.protobuf.ByteString getEstimatedSeaDensityBytes();

    /**
     * <code>string foConsumptionInSZ = 13;</code>
     *
     * @return The foConsumptionInSZ.
     */
    java.lang.String getFoConsumptionInSZ();
    /**
     * <code>string foConsumptionInSZ = 13;</code>
     *
     * @return The bytes for foConsumptionInSZ.
     */
    com.google.protobuf.ByteString getFoConsumptionInSZBytes();

    /**
     * <code>string foConsumptionPerDay = 14;</code>
     *
     * @return The foConsumptionPerDay.
     */
    java.lang.String getFoConsumptionPerDay();
    /**
     * <code>string foConsumptionPerDay = 14;</code>
     *
     * @return The bytes for foConsumptionPerDay.
     */
    com.google.protobuf.ByteString getFoConsumptionPerDayBytes();

    /**
     * <code>int64 id = 15;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>bool isActive = 16;</code>
     *
     * @return The isActive.
     */
    boolean getIsActive();

    /**
     * <code>string lightWeight = 17;</code>
     *
     * @return The lightWeight.
     */
    java.lang.String getLightWeight();
    /**
     * <code>string lightWeight = 17;</code>
     *
     * @return The bytes for lightWeight.
     */
    com.google.protobuf.ByteString getLightWeightBytes();

    /**
     * <code>int64 loadableStudyPortRotationId = 18;</code>
     *
     * @return The loadableStudyPortRotationId.
     */
    long getLoadableStudyPortRotationId();

    /**
     * <code>int64 loadableStudyId = 19;</code>
     *
     * @return The loadableStudyId.
     */
    long getLoadableStudyId();

    /**
     * <code>string otherIfAny = 20;</code>
     *
     * @return The otherIfAny.
     */
    java.lang.String getOtherIfAny();
    /**
     * <code>string otherIfAny = 20;</code>
     *
     * @return The bytes for otherIfAny.
     */
    com.google.protobuf.ByteString getOtherIfAnyBytes();

    /**
     * <code>string portId = 21;</code>
     *
     * @return The portId.
     */
    java.lang.String getPortId();
    /**
     * <code>string portId = 21;</code>
     *
     * @return The bytes for portId.
     */
    com.google.protobuf.ByteString getPortIdBytes();

    /**
     * <code>string runningDays = 22;</code>
     *
     * @return The runningDays.
     */
    java.lang.String getRunningDays();
    /**
     * <code>string runningDays = 22;</code>
     *
     * @return The bytes for runningDays.
     */
    com.google.protobuf.ByteString getRunningDaysBytes();

    /**
     * <code>string runningHours = 23;</code>
     *
     * @return The runningHours.
     */
    java.lang.String getRunningHours();
    /**
     * <code>string runningHours = 23;</code>
     *
     * @return The bytes for runningHours.
     */
    com.google.protobuf.ByteString getRunningHoursBytes();

    /**
     * <code>string saggingDeduction = 24;</code>
     *
     * @return The saggingDeduction.
     */
    java.lang.String getSaggingDeduction();
    /**
     * <code>string saggingDeduction = 24;</code>
     *
     * @return The bytes for saggingDeduction.
     */
    com.google.protobuf.ByteString getSaggingDeductionBytes();

    /**
     * <code>string sgCorrection = 25;</code>
     *
     * @return The sgCorrection.
     */
    java.lang.String getSgCorrection();
    /**
     * <code>string sgCorrection = 25;</code>
     *
     * @return The bytes for sgCorrection.
     */
    com.google.protobuf.ByteString getSgCorrectionBytes();

    /**
     * <code>string subTotal = 26;</code>
     *
     * @return The subTotal.
     */
    java.lang.String getSubTotal();
    /**
     * <code>string subTotal = 26;</code>
     *
     * @return The bytes for subTotal.
     */
    com.google.protobuf.ByteString getSubTotalBytes();

    /**
     * <code>string totalFoConsumption = 27;</code>
     *
     * @return The totalFoConsumption.
     */
    java.lang.String getTotalFoConsumption();
    /**
     * <code>string totalFoConsumption = 27;</code>
     *
     * @return The bytes for totalFoConsumption.
     */
    com.google.protobuf.ByteString getTotalFoConsumptionBytes();

    /**
     * <code>string totalQuantity = 28;</code>
     *
     * @return The totalQuantity.
     */
    java.lang.String getTotalQuantity();
    /**
     * <code>string totalQuantity = 28;</code>
     *
     * @return The bytes for totalQuantity.
     */
    com.google.protobuf.ByteString getTotalQuantityBytes();

    /**
     * <code>string tpcatDraft = 29;</code>
     *
     * @return The tpcatDraft.
     */
    java.lang.String getTpcatDraft();
    /**
     * <code>string tpcatDraft = 29;</code>
     *
     * @return The bytes for tpcatDraft.
     */
    com.google.protobuf.ByteString getTpcatDraftBytes();

    /**
     * <code>string vesselAverageSpeed = 30;</code>
     *
     * @return The vesselAverageSpeed.
     */
    java.lang.String getVesselAverageSpeed();
    /**
     * <code>string vesselAverageSpeed = 30;</code>
     *
     * @return The bytes for vesselAverageSpeed.
     */
    com.google.protobuf.ByteString getVesselAverageSpeedBytes();
  }
  /** Protobuf type {@code LoadableQuantity} */
  public static final class LoadableQuantity extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadableQuantity)
      LoadableQuantityOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadableQuantity.newBuilder() to construct.
    private LoadableQuantity(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadableQuantity() {
      ballast_ = "";
      boilerWaterOnBoard_ = "";
      constant_ = "";
      deadWeight_ = "";
      displacementAtDraftRestriction_ = "";
      distanceFromLastPort_ = "";
      draftRestriction_ = "";
      estimatedDOOnBoard_ = "";
      estimatedFOOnBoard_ = "";
      estimatedFWOnBoard_ = "";
      estimatedSagging_ = "";
      estimatedSeaDensity_ = "";
      foConsumptionInSZ_ = "";
      foConsumptionPerDay_ = "";
      lightWeight_ = "";
      otherIfAny_ = "";
      portId_ = "";
      runningDays_ = "";
      runningHours_ = "";
      saggingDeduction_ = "";
      sgCorrection_ = "";
      subTotal_ = "";
      totalFoConsumption_ = "";
      totalQuantity_ = "";
      tpcatDraft_ = "";
      vesselAverageSpeed_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadableQuantity();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadableQuantity(
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

                ballast_ = s;
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                boilerWaterOnBoard_ = s;
                break;
              }
            case 26:
              {
                java.lang.String s = input.readStringRequireUtf8();

                constant_ = s;
                break;
              }
            case 34:
              {
                java.lang.String s = input.readStringRequireUtf8();

                deadWeight_ = s;
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                displacementAtDraftRestriction_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                distanceFromLastPort_ = s;
                break;
              }
            case 58:
              {
                java.lang.String s = input.readStringRequireUtf8();

                draftRestriction_ = s;
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                estimatedDOOnBoard_ = s;
                break;
              }
            case 74:
              {
                java.lang.String s = input.readStringRequireUtf8();

                estimatedFOOnBoard_ = s;
                break;
              }
            case 82:
              {
                java.lang.String s = input.readStringRequireUtf8();

                estimatedFWOnBoard_ = s;
                break;
              }
            case 90:
              {
                java.lang.String s = input.readStringRequireUtf8();

                estimatedSagging_ = s;
                break;
              }
            case 98:
              {
                java.lang.String s = input.readStringRequireUtf8();

                estimatedSeaDensity_ = s;
                break;
              }
            case 106:
              {
                java.lang.String s = input.readStringRequireUtf8();

                foConsumptionInSZ_ = s;
                break;
              }
            case 114:
              {
                java.lang.String s = input.readStringRequireUtf8();

                foConsumptionPerDay_ = s;
                break;
              }
            case 120:
              {
                id_ = input.readInt64();
                break;
              }
            case 128:
              {
                isActive_ = input.readBool();
                break;
              }
            case 138:
              {
                java.lang.String s = input.readStringRequireUtf8();

                lightWeight_ = s;
                break;
              }
            case 144:
              {
                loadableStudyPortRotationId_ = input.readInt64();
                break;
              }
            case 152:
              {
                loadableStudyId_ = input.readInt64();
                break;
              }
            case 162:
              {
                java.lang.String s = input.readStringRequireUtf8();

                otherIfAny_ = s;
                break;
              }
            case 170:
              {
                java.lang.String s = input.readStringRequireUtf8();

                portId_ = s;
                break;
              }
            case 178:
              {
                java.lang.String s = input.readStringRequireUtf8();

                runningDays_ = s;
                break;
              }
            case 186:
              {
                java.lang.String s = input.readStringRequireUtf8();

                runningHours_ = s;
                break;
              }
            case 194:
              {
                java.lang.String s = input.readStringRequireUtf8();

                saggingDeduction_ = s;
                break;
              }
            case 202:
              {
                java.lang.String s = input.readStringRequireUtf8();

                sgCorrection_ = s;
                break;
              }
            case 210:
              {
                java.lang.String s = input.readStringRequireUtf8();

                subTotal_ = s;
                break;
              }
            case 218:
              {
                java.lang.String s = input.readStringRequireUtf8();

                totalFoConsumption_ = s;
                break;
              }
            case 226:
              {
                java.lang.String s = input.readStringRequireUtf8();

                totalQuantity_ = s;
                break;
              }
            case 234:
              {
                java.lang.String s = input.readStringRequireUtf8();

                tpcatDraft_ = s;
                break;
              }
            case 242:
              {
                java.lang.String s = input.readStringRequireUtf8();

                vesselAverageSpeed_ = s;
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
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadableQuantity_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadableQuantity_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity.class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity.Builder
                  .class);
    }

    public static final int BALLAST_FIELD_NUMBER = 1;
    private volatile java.lang.Object ballast_;
    /**
     * <code>string ballast = 1;</code>
     *
     * @return The ballast.
     */
    public java.lang.String getBallast() {
      java.lang.Object ref = ballast_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        ballast_ = s;
        return s;
      }
    }
    /**
     * <code>string ballast = 1;</code>
     *
     * @return The bytes for ballast.
     */
    public com.google.protobuf.ByteString getBallastBytes() {
      java.lang.Object ref = ballast_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        ballast_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int BOILERWATERONBOARD_FIELD_NUMBER = 2;
    private volatile java.lang.Object boilerWaterOnBoard_;
    /**
     * <code>string boilerWaterOnBoard = 2;</code>
     *
     * @return The boilerWaterOnBoard.
     */
    public java.lang.String getBoilerWaterOnBoard() {
      java.lang.Object ref = boilerWaterOnBoard_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        boilerWaterOnBoard_ = s;
        return s;
      }
    }
    /**
     * <code>string boilerWaterOnBoard = 2;</code>
     *
     * @return The bytes for boilerWaterOnBoard.
     */
    public com.google.protobuf.ByteString getBoilerWaterOnBoardBytes() {
      java.lang.Object ref = boilerWaterOnBoard_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        boilerWaterOnBoard_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CONSTANT_FIELD_NUMBER = 3;
    private volatile java.lang.Object constant_;
    /**
     * <code>string constant = 3;</code>
     *
     * @return The constant.
     */
    public java.lang.String getConstant() {
      java.lang.Object ref = constant_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        constant_ = s;
        return s;
      }
    }
    /**
     * <code>string constant = 3;</code>
     *
     * @return The bytes for constant.
     */
    public com.google.protobuf.ByteString getConstantBytes() {
      java.lang.Object ref = constant_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        constant_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DEADWEIGHT_FIELD_NUMBER = 4;
    private volatile java.lang.Object deadWeight_;
    /**
     * <code>string deadWeight = 4;</code>
     *
     * @return The deadWeight.
     */
    public java.lang.String getDeadWeight() {
      java.lang.Object ref = deadWeight_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        deadWeight_ = s;
        return s;
      }
    }
    /**
     * <code>string deadWeight = 4;</code>
     *
     * @return The bytes for deadWeight.
     */
    public com.google.protobuf.ByteString getDeadWeightBytes() {
      java.lang.Object ref = deadWeight_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        deadWeight_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DISPLACEMENTATDRAFTRESTRICTION_FIELD_NUMBER = 5;
    private volatile java.lang.Object displacementAtDraftRestriction_;
    /**
     * <code>string displacementAtDraftRestriction = 5;</code>
     *
     * @return The displacementAtDraftRestriction.
     */
    public java.lang.String getDisplacementAtDraftRestriction() {
      java.lang.Object ref = displacementAtDraftRestriction_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        displacementAtDraftRestriction_ = s;
        return s;
      }
    }
    /**
     * <code>string displacementAtDraftRestriction = 5;</code>
     *
     * @return The bytes for displacementAtDraftRestriction.
     */
    public com.google.protobuf.ByteString getDisplacementAtDraftRestrictionBytes() {
      java.lang.Object ref = displacementAtDraftRestriction_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        displacementAtDraftRestriction_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DISTANCEFROMLASTPORT_FIELD_NUMBER = 6;
    private volatile java.lang.Object distanceFromLastPort_;
    /**
     * <code>string distanceFromLastPort = 6;</code>
     *
     * @return The distanceFromLastPort.
     */
    public java.lang.String getDistanceFromLastPort() {
      java.lang.Object ref = distanceFromLastPort_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        distanceFromLastPort_ = s;
        return s;
      }
    }
    /**
     * <code>string distanceFromLastPort = 6;</code>
     *
     * @return The bytes for distanceFromLastPort.
     */
    public com.google.protobuf.ByteString getDistanceFromLastPortBytes() {
      java.lang.Object ref = distanceFromLastPort_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        distanceFromLastPort_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DRAFTRESTRICTION_FIELD_NUMBER = 7;
    private volatile java.lang.Object draftRestriction_;
    /**
     * <code>string draftRestriction = 7;</code>
     *
     * @return The draftRestriction.
     */
    public java.lang.String getDraftRestriction() {
      java.lang.Object ref = draftRestriction_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        draftRestriction_ = s;
        return s;
      }
    }
    /**
     * <code>string draftRestriction = 7;</code>
     *
     * @return The bytes for draftRestriction.
     */
    public com.google.protobuf.ByteString getDraftRestrictionBytes() {
      java.lang.Object ref = draftRestriction_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        draftRestriction_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ESTIMATEDDOONBOARD_FIELD_NUMBER = 8;
    private volatile java.lang.Object estimatedDOOnBoard_;
    /**
     * <code>string estimatedDOOnBoard = 8;</code>
     *
     * @return The estimatedDOOnBoard.
     */
    public java.lang.String getEstimatedDOOnBoard() {
      java.lang.Object ref = estimatedDOOnBoard_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        estimatedDOOnBoard_ = s;
        return s;
      }
    }
    /**
     * <code>string estimatedDOOnBoard = 8;</code>
     *
     * @return The bytes for estimatedDOOnBoard.
     */
    public com.google.protobuf.ByteString getEstimatedDOOnBoardBytes() {
      java.lang.Object ref = estimatedDOOnBoard_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        estimatedDOOnBoard_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ESTIMATEDFOONBOARD_FIELD_NUMBER = 9;
    private volatile java.lang.Object estimatedFOOnBoard_;
    /**
     * <code>string estimatedFOOnBoard = 9;</code>
     *
     * @return The estimatedFOOnBoard.
     */
    public java.lang.String getEstimatedFOOnBoard() {
      java.lang.Object ref = estimatedFOOnBoard_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        estimatedFOOnBoard_ = s;
        return s;
      }
    }
    /**
     * <code>string estimatedFOOnBoard = 9;</code>
     *
     * @return The bytes for estimatedFOOnBoard.
     */
    public com.google.protobuf.ByteString getEstimatedFOOnBoardBytes() {
      java.lang.Object ref = estimatedFOOnBoard_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        estimatedFOOnBoard_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ESTIMATEDFWONBOARD_FIELD_NUMBER = 10;
    private volatile java.lang.Object estimatedFWOnBoard_;
    /**
     * <code>string estimatedFWOnBoard = 10;</code>
     *
     * @return The estimatedFWOnBoard.
     */
    public java.lang.String getEstimatedFWOnBoard() {
      java.lang.Object ref = estimatedFWOnBoard_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        estimatedFWOnBoard_ = s;
        return s;
      }
    }
    /**
     * <code>string estimatedFWOnBoard = 10;</code>
     *
     * @return The bytes for estimatedFWOnBoard.
     */
    public com.google.protobuf.ByteString getEstimatedFWOnBoardBytes() {
      java.lang.Object ref = estimatedFWOnBoard_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        estimatedFWOnBoard_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ESTIMATEDSAGGING_FIELD_NUMBER = 11;
    private volatile java.lang.Object estimatedSagging_;
    /**
     * <code>string estimatedSagging = 11;</code>
     *
     * @return The estimatedSagging.
     */
    public java.lang.String getEstimatedSagging() {
      java.lang.Object ref = estimatedSagging_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        estimatedSagging_ = s;
        return s;
      }
    }
    /**
     * <code>string estimatedSagging = 11;</code>
     *
     * @return The bytes for estimatedSagging.
     */
    public com.google.protobuf.ByteString getEstimatedSaggingBytes() {
      java.lang.Object ref = estimatedSagging_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        estimatedSagging_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ESTIMATEDSEADENSITY_FIELD_NUMBER = 12;
    private volatile java.lang.Object estimatedSeaDensity_;
    /**
     * <code>string estimatedSeaDensity = 12;</code>
     *
     * @return The estimatedSeaDensity.
     */
    public java.lang.String getEstimatedSeaDensity() {
      java.lang.Object ref = estimatedSeaDensity_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        estimatedSeaDensity_ = s;
        return s;
      }
    }
    /**
     * <code>string estimatedSeaDensity = 12;</code>
     *
     * @return The bytes for estimatedSeaDensity.
     */
    public com.google.protobuf.ByteString getEstimatedSeaDensityBytes() {
      java.lang.Object ref = estimatedSeaDensity_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        estimatedSeaDensity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int FOCONSUMPTIONINSZ_FIELD_NUMBER = 13;
    private volatile java.lang.Object foConsumptionInSZ_;
    /**
     * <code>string foConsumptionInSZ = 13;</code>
     *
     * @return The foConsumptionInSZ.
     */
    public java.lang.String getFoConsumptionInSZ() {
      java.lang.Object ref = foConsumptionInSZ_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        foConsumptionInSZ_ = s;
        return s;
      }
    }
    /**
     * <code>string foConsumptionInSZ = 13;</code>
     *
     * @return The bytes for foConsumptionInSZ.
     */
    public com.google.protobuf.ByteString getFoConsumptionInSZBytes() {
      java.lang.Object ref = foConsumptionInSZ_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        foConsumptionInSZ_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int FOCONSUMPTIONPERDAY_FIELD_NUMBER = 14;
    private volatile java.lang.Object foConsumptionPerDay_;
    /**
     * <code>string foConsumptionPerDay = 14;</code>
     *
     * @return The foConsumptionPerDay.
     */
    public java.lang.String getFoConsumptionPerDay() {
      java.lang.Object ref = foConsumptionPerDay_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        foConsumptionPerDay_ = s;
        return s;
      }
    }
    /**
     * <code>string foConsumptionPerDay = 14;</code>
     *
     * @return The bytes for foConsumptionPerDay.
     */
    public com.google.protobuf.ByteString getFoConsumptionPerDayBytes() {
      java.lang.Object ref = foConsumptionPerDay_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        foConsumptionPerDay_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ID_FIELD_NUMBER = 15;
    private long id_;
    /**
     * <code>int64 id = 15;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
    }

    public static final int ISACTIVE_FIELD_NUMBER = 16;
    private boolean isActive_;
    /**
     * <code>bool isActive = 16;</code>
     *
     * @return The isActive.
     */
    public boolean getIsActive() {
      return isActive_;
    }

    public static final int LIGHTWEIGHT_FIELD_NUMBER = 17;
    private volatile java.lang.Object lightWeight_;
    /**
     * <code>string lightWeight = 17;</code>
     *
     * @return The lightWeight.
     */
    public java.lang.String getLightWeight() {
      java.lang.Object ref = lightWeight_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        lightWeight_ = s;
        return s;
      }
    }
    /**
     * <code>string lightWeight = 17;</code>
     *
     * @return The bytes for lightWeight.
     */
    public com.google.protobuf.ByteString getLightWeightBytes() {
      java.lang.Object ref = lightWeight_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        lightWeight_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOADABLESTUDYPORTROTATIONID_FIELD_NUMBER = 18;
    private long loadableStudyPortRotationId_;
    /**
     * <code>int64 loadableStudyPortRotationId = 18;</code>
     *
     * @return The loadableStudyPortRotationId.
     */
    public long getLoadableStudyPortRotationId() {
      return loadableStudyPortRotationId_;
    }

    public static final int LOADABLESTUDYID_FIELD_NUMBER = 19;
    private long loadableStudyId_;
    /**
     * <code>int64 loadableStudyId = 19;</code>
     *
     * @return The loadableStudyId.
     */
    public long getLoadableStudyId() {
      return loadableStudyId_;
    }

    public static final int OTHERIFANY_FIELD_NUMBER = 20;
    private volatile java.lang.Object otherIfAny_;
    /**
     * <code>string otherIfAny = 20;</code>
     *
     * @return The otherIfAny.
     */
    public java.lang.String getOtherIfAny() {
      java.lang.Object ref = otherIfAny_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        otherIfAny_ = s;
        return s;
      }
    }
    /**
     * <code>string otherIfAny = 20;</code>
     *
     * @return The bytes for otherIfAny.
     */
    public com.google.protobuf.ByteString getOtherIfAnyBytes() {
      java.lang.Object ref = otherIfAny_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        otherIfAny_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PORTID_FIELD_NUMBER = 21;
    private volatile java.lang.Object portId_;
    /**
     * <code>string portId = 21;</code>
     *
     * @return The portId.
     */
    public java.lang.String getPortId() {
      java.lang.Object ref = portId_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        portId_ = s;
        return s;
      }
    }
    /**
     * <code>string portId = 21;</code>
     *
     * @return The bytes for portId.
     */
    public com.google.protobuf.ByteString getPortIdBytes() {
      java.lang.Object ref = portId_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        portId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int RUNNINGDAYS_FIELD_NUMBER = 22;
    private volatile java.lang.Object runningDays_;
    /**
     * <code>string runningDays = 22;</code>
     *
     * @return The runningDays.
     */
    public java.lang.String getRunningDays() {
      java.lang.Object ref = runningDays_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        runningDays_ = s;
        return s;
      }
    }
    /**
     * <code>string runningDays = 22;</code>
     *
     * @return The bytes for runningDays.
     */
    public com.google.protobuf.ByteString getRunningDaysBytes() {
      java.lang.Object ref = runningDays_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        runningDays_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int RUNNINGHOURS_FIELD_NUMBER = 23;
    private volatile java.lang.Object runningHours_;
    /**
     * <code>string runningHours = 23;</code>
     *
     * @return The runningHours.
     */
    public java.lang.String getRunningHours() {
      java.lang.Object ref = runningHours_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        runningHours_ = s;
        return s;
      }
    }
    /**
     * <code>string runningHours = 23;</code>
     *
     * @return The bytes for runningHours.
     */
    public com.google.protobuf.ByteString getRunningHoursBytes() {
      java.lang.Object ref = runningHours_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        runningHours_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SAGGINGDEDUCTION_FIELD_NUMBER = 24;
    private volatile java.lang.Object saggingDeduction_;
    /**
     * <code>string saggingDeduction = 24;</code>
     *
     * @return The saggingDeduction.
     */
    public java.lang.String getSaggingDeduction() {
      java.lang.Object ref = saggingDeduction_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        saggingDeduction_ = s;
        return s;
      }
    }
    /**
     * <code>string saggingDeduction = 24;</code>
     *
     * @return The bytes for saggingDeduction.
     */
    public com.google.protobuf.ByteString getSaggingDeductionBytes() {
      java.lang.Object ref = saggingDeduction_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        saggingDeduction_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SGCORRECTION_FIELD_NUMBER = 25;
    private volatile java.lang.Object sgCorrection_;
    /**
     * <code>string sgCorrection = 25;</code>
     *
     * @return The sgCorrection.
     */
    public java.lang.String getSgCorrection() {
      java.lang.Object ref = sgCorrection_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        sgCorrection_ = s;
        return s;
      }
    }
    /**
     * <code>string sgCorrection = 25;</code>
     *
     * @return The bytes for sgCorrection.
     */
    public com.google.protobuf.ByteString getSgCorrectionBytes() {
      java.lang.Object ref = sgCorrection_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        sgCorrection_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SUBTOTAL_FIELD_NUMBER = 26;
    private volatile java.lang.Object subTotal_;
    /**
     * <code>string subTotal = 26;</code>
     *
     * @return The subTotal.
     */
    public java.lang.String getSubTotal() {
      java.lang.Object ref = subTotal_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        subTotal_ = s;
        return s;
      }
    }
    /**
     * <code>string subTotal = 26;</code>
     *
     * @return The bytes for subTotal.
     */
    public com.google.protobuf.ByteString getSubTotalBytes() {
      java.lang.Object ref = subTotal_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        subTotal_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TOTALFOCONSUMPTION_FIELD_NUMBER = 27;
    private volatile java.lang.Object totalFoConsumption_;
    /**
     * <code>string totalFoConsumption = 27;</code>
     *
     * @return The totalFoConsumption.
     */
    public java.lang.String getTotalFoConsumption() {
      java.lang.Object ref = totalFoConsumption_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        totalFoConsumption_ = s;
        return s;
      }
    }
    /**
     * <code>string totalFoConsumption = 27;</code>
     *
     * @return The bytes for totalFoConsumption.
     */
    public com.google.protobuf.ByteString getTotalFoConsumptionBytes() {
      java.lang.Object ref = totalFoConsumption_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        totalFoConsumption_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TOTALQUANTITY_FIELD_NUMBER = 28;
    private volatile java.lang.Object totalQuantity_;
    /**
     * <code>string totalQuantity = 28;</code>
     *
     * @return The totalQuantity.
     */
    public java.lang.String getTotalQuantity() {
      java.lang.Object ref = totalQuantity_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        totalQuantity_ = s;
        return s;
      }
    }
    /**
     * <code>string totalQuantity = 28;</code>
     *
     * @return The bytes for totalQuantity.
     */
    public com.google.protobuf.ByteString getTotalQuantityBytes() {
      java.lang.Object ref = totalQuantity_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        totalQuantity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TPCATDRAFT_FIELD_NUMBER = 29;
    private volatile java.lang.Object tpcatDraft_;
    /**
     * <code>string tpcatDraft = 29;</code>
     *
     * @return The tpcatDraft.
     */
    public java.lang.String getTpcatDraft() {
      java.lang.Object ref = tpcatDraft_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tpcatDraft_ = s;
        return s;
      }
    }
    /**
     * <code>string tpcatDraft = 29;</code>
     *
     * @return The bytes for tpcatDraft.
     */
    public com.google.protobuf.ByteString getTpcatDraftBytes() {
      java.lang.Object ref = tpcatDraft_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        tpcatDraft_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int VESSELAVERAGESPEED_FIELD_NUMBER = 30;
    private volatile java.lang.Object vesselAverageSpeed_;
    /**
     * <code>string vesselAverageSpeed = 30;</code>
     *
     * @return The vesselAverageSpeed.
     */
    public java.lang.String getVesselAverageSpeed() {
      java.lang.Object ref = vesselAverageSpeed_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        vesselAverageSpeed_ = s;
        return s;
      }
    }
    /**
     * <code>string vesselAverageSpeed = 30;</code>
     *
     * @return The bytes for vesselAverageSpeed.
     */
    public com.google.protobuf.ByteString getVesselAverageSpeedBytes() {
      java.lang.Object ref = vesselAverageSpeed_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        vesselAverageSpeed_ = b;
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
      if (!getBallastBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, ballast_);
      }
      if (!getBoilerWaterOnBoardBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, boilerWaterOnBoard_);
      }
      if (!getConstantBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, constant_);
      }
      if (!getDeadWeightBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, deadWeight_);
      }
      if (!getDisplacementAtDraftRestrictionBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(
            output, 5, displacementAtDraftRestriction_);
      }
      if (!getDistanceFromLastPortBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, distanceFromLastPort_);
      }
      if (!getDraftRestrictionBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, draftRestriction_);
      }
      if (!getEstimatedDOOnBoardBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, estimatedDOOnBoard_);
      }
      if (!getEstimatedFOOnBoardBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 9, estimatedFOOnBoard_);
      }
      if (!getEstimatedFWOnBoardBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 10, estimatedFWOnBoard_);
      }
      if (!getEstimatedSaggingBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 11, estimatedSagging_);
      }
      if (!getEstimatedSeaDensityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 12, estimatedSeaDensity_);
      }
      if (!getFoConsumptionInSZBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 13, foConsumptionInSZ_);
      }
      if (!getFoConsumptionPerDayBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 14, foConsumptionPerDay_);
      }
      if (id_ != 0L) {
        output.writeInt64(15, id_);
      }
      if (isActive_ != false) {
        output.writeBool(16, isActive_);
      }
      if (!getLightWeightBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 17, lightWeight_);
      }
      if (loadableStudyPortRotationId_ != 0L) {
        output.writeInt64(18, loadableStudyPortRotationId_);
      }
      if (loadableStudyId_ != 0L) {
        output.writeInt64(19, loadableStudyId_);
      }
      if (!getOtherIfAnyBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 20, otherIfAny_);
      }
      if (!getPortIdBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 21, portId_);
      }
      if (!getRunningDaysBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 22, runningDays_);
      }
      if (!getRunningHoursBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 23, runningHours_);
      }
      if (!getSaggingDeductionBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 24, saggingDeduction_);
      }
      if (!getSgCorrectionBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 25, sgCorrection_);
      }
      if (!getSubTotalBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 26, subTotal_);
      }
      if (!getTotalFoConsumptionBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 27, totalFoConsumption_);
      }
      if (!getTotalQuantityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 28, totalQuantity_);
      }
      if (!getTpcatDraftBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 29, tpcatDraft_);
      }
      if (!getVesselAverageSpeedBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 30, vesselAverageSpeed_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getBallastBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, ballast_);
      }
      if (!getBoilerWaterOnBoardBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, boilerWaterOnBoard_);
      }
      if (!getConstantBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, constant_);
      }
      if (!getDeadWeightBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, deadWeight_);
      }
      if (!getDisplacementAtDraftRestrictionBytes().isEmpty()) {
        size +=
            com.google.protobuf.GeneratedMessageV3.computeStringSize(
                5, displacementAtDraftRestriction_);
      }
      if (!getDistanceFromLastPortBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, distanceFromLastPort_);
      }
      if (!getDraftRestrictionBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, draftRestriction_);
      }
      if (!getEstimatedDOOnBoardBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, estimatedDOOnBoard_);
      }
      if (!getEstimatedFOOnBoardBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, estimatedFOOnBoard_);
      }
      if (!getEstimatedFWOnBoardBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(10, estimatedFWOnBoard_);
      }
      if (!getEstimatedSaggingBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(11, estimatedSagging_);
      }
      if (!getEstimatedSeaDensityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(12, estimatedSeaDensity_);
      }
      if (!getFoConsumptionInSZBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(13, foConsumptionInSZ_);
      }
      if (!getFoConsumptionPerDayBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(14, foConsumptionPerDay_);
      }
      if (id_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(15, id_);
      }
      if (isActive_ != false) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(16, isActive_);
      }
      if (!getLightWeightBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(17, lightWeight_);
      }
      if (loadableStudyPortRotationId_ != 0L) {
        size +=
            com.google.protobuf.CodedOutputStream.computeInt64Size(
                18, loadableStudyPortRotationId_);
      }
      if (loadableStudyId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(19, loadableStudyId_);
      }
      if (!getOtherIfAnyBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(20, otherIfAny_);
      }
      if (!getPortIdBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(21, portId_);
      }
      if (!getRunningDaysBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(22, runningDays_);
      }
      if (!getRunningHoursBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(23, runningHours_);
      }
      if (!getSaggingDeductionBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(24, saggingDeduction_);
      }
      if (!getSgCorrectionBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(25, sgCorrection_);
      }
      if (!getSubTotalBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(26, subTotal_);
      }
      if (!getTotalFoConsumptionBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(27, totalFoConsumption_);
      }
      if (!getTotalQuantityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(28, totalQuantity_);
      }
      if (!getTpcatDraftBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(29, tpcatDraft_);
      }
      if (!getVesselAverageSpeedBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(30, vesselAverageSpeed_);
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
          instanceof com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity) obj;

      if (!getBallast().equals(other.getBallast())) return false;
      if (!getBoilerWaterOnBoard().equals(other.getBoilerWaterOnBoard())) return false;
      if (!getConstant().equals(other.getConstant())) return false;
      if (!getDeadWeight().equals(other.getDeadWeight())) return false;
      if (!getDisplacementAtDraftRestriction().equals(other.getDisplacementAtDraftRestriction()))
        return false;
      if (!getDistanceFromLastPort().equals(other.getDistanceFromLastPort())) return false;
      if (!getDraftRestriction().equals(other.getDraftRestriction())) return false;
      if (!getEstimatedDOOnBoard().equals(other.getEstimatedDOOnBoard())) return false;
      if (!getEstimatedFOOnBoard().equals(other.getEstimatedFOOnBoard())) return false;
      if (!getEstimatedFWOnBoard().equals(other.getEstimatedFWOnBoard())) return false;
      if (!getEstimatedSagging().equals(other.getEstimatedSagging())) return false;
      if (!getEstimatedSeaDensity().equals(other.getEstimatedSeaDensity())) return false;
      if (!getFoConsumptionInSZ().equals(other.getFoConsumptionInSZ())) return false;
      if (!getFoConsumptionPerDay().equals(other.getFoConsumptionPerDay())) return false;
      if (getId() != other.getId()) return false;
      if (getIsActive() != other.getIsActive()) return false;
      if (!getLightWeight().equals(other.getLightWeight())) return false;
      if (getLoadableStudyPortRotationId() != other.getLoadableStudyPortRotationId()) return false;
      if (getLoadableStudyId() != other.getLoadableStudyId()) return false;
      if (!getOtherIfAny().equals(other.getOtherIfAny())) return false;
      if (!getPortId().equals(other.getPortId())) return false;
      if (!getRunningDays().equals(other.getRunningDays())) return false;
      if (!getRunningHours().equals(other.getRunningHours())) return false;
      if (!getSaggingDeduction().equals(other.getSaggingDeduction())) return false;
      if (!getSgCorrection().equals(other.getSgCorrection())) return false;
      if (!getSubTotal().equals(other.getSubTotal())) return false;
      if (!getTotalFoConsumption().equals(other.getTotalFoConsumption())) return false;
      if (!getTotalQuantity().equals(other.getTotalQuantity())) return false;
      if (!getTpcatDraft().equals(other.getTpcatDraft())) return false;
      if (!getVesselAverageSpeed().equals(other.getVesselAverageSpeed())) return false;
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
      hash = (37 * hash) + BALLAST_FIELD_NUMBER;
      hash = (53 * hash) + getBallast().hashCode();
      hash = (37 * hash) + BOILERWATERONBOARD_FIELD_NUMBER;
      hash = (53 * hash) + getBoilerWaterOnBoard().hashCode();
      hash = (37 * hash) + CONSTANT_FIELD_NUMBER;
      hash = (53 * hash) + getConstant().hashCode();
      hash = (37 * hash) + DEADWEIGHT_FIELD_NUMBER;
      hash = (53 * hash) + getDeadWeight().hashCode();
      hash = (37 * hash) + DISPLACEMENTATDRAFTRESTRICTION_FIELD_NUMBER;
      hash = (53 * hash) + getDisplacementAtDraftRestriction().hashCode();
      hash = (37 * hash) + DISTANCEFROMLASTPORT_FIELD_NUMBER;
      hash = (53 * hash) + getDistanceFromLastPort().hashCode();
      hash = (37 * hash) + DRAFTRESTRICTION_FIELD_NUMBER;
      hash = (53 * hash) + getDraftRestriction().hashCode();
      hash = (37 * hash) + ESTIMATEDDOONBOARD_FIELD_NUMBER;
      hash = (53 * hash) + getEstimatedDOOnBoard().hashCode();
      hash = (37 * hash) + ESTIMATEDFOONBOARD_FIELD_NUMBER;
      hash = (53 * hash) + getEstimatedFOOnBoard().hashCode();
      hash = (37 * hash) + ESTIMATEDFWONBOARD_FIELD_NUMBER;
      hash = (53 * hash) + getEstimatedFWOnBoard().hashCode();
      hash = (37 * hash) + ESTIMATEDSAGGING_FIELD_NUMBER;
      hash = (53 * hash) + getEstimatedSagging().hashCode();
      hash = (37 * hash) + ESTIMATEDSEADENSITY_FIELD_NUMBER;
      hash = (53 * hash) + getEstimatedSeaDensity().hashCode();
      hash = (37 * hash) + FOCONSUMPTIONINSZ_FIELD_NUMBER;
      hash = (53 * hash) + getFoConsumptionInSZ().hashCode();
      hash = (37 * hash) + FOCONSUMPTIONPERDAY_FIELD_NUMBER;
      hash = (53 * hash) + getFoConsumptionPerDay().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
      hash = (37 * hash) + ISACTIVE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsActive());
      hash = (37 * hash) + LIGHTWEIGHT_FIELD_NUMBER;
      hash = (53 * hash) + getLightWeight().hashCode();
      hash = (37 * hash) + LOADABLESTUDYPORTROTATIONID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadableStudyPortRotationId());
      hash = (37 * hash) + LOADABLESTUDYID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadableStudyId());
      hash = (37 * hash) + OTHERIFANY_FIELD_NUMBER;
      hash = (53 * hash) + getOtherIfAny().hashCode();
      hash = (37 * hash) + PORTID_FIELD_NUMBER;
      hash = (53 * hash) + getPortId().hashCode();
      hash = (37 * hash) + RUNNINGDAYS_FIELD_NUMBER;
      hash = (53 * hash) + getRunningDays().hashCode();
      hash = (37 * hash) + RUNNINGHOURS_FIELD_NUMBER;
      hash = (53 * hash) + getRunningHours().hashCode();
      hash = (37 * hash) + SAGGINGDEDUCTION_FIELD_NUMBER;
      hash = (53 * hash) + getSaggingDeduction().hashCode();
      hash = (37 * hash) + SGCORRECTION_FIELD_NUMBER;
      hash = (53 * hash) + getSgCorrection().hashCode();
      hash = (37 * hash) + SUBTOTAL_FIELD_NUMBER;
      hash = (53 * hash) + getSubTotal().hashCode();
      hash = (37 * hash) + TOTALFOCONSUMPTION_FIELD_NUMBER;
      hash = (53 * hash) + getTotalFoConsumption().hashCode();
      hash = (37 * hash) + TOTALQUANTITY_FIELD_NUMBER;
      hash = (53 * hash) + getTotalQuantity().hashCode();
      hash = (37 * hash) + TPCATDRAFT_FIELD_NUMBER;
      hash = (53 * hash) + getTpcatDraft().hashCode();
      hash = (37 * hash) + VESSELAVERAGESPEED_FIELD_NUMBER;
      hash = (53 * hash) + getVesselAverageSpeed().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity prototype) {
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
    /** Protobuf type {@code LoadableQuantity} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadableQuantity)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantityOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadableQuantity_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadableQuantity_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity.class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity.Builder
                    .class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity.newBuilder()
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
        ballast_ = "";

        boilerWaterOnBoard_ = "";

        constant_ = "";

        deadWeight_ = "";

        displacementAtDraftRestriction_ = "";

        distanceFromLastPort_ = "";

        draftRestriction_ = "";

        estimatedDOOnBoard_ = "";

        estimatedFOOnBoard_ = "";

        estimatedFWOnBoard_ = "";

        estimatedSagging_ = "";

        estimatedSeaDensity_ = "";

        foConsumptionInSZ_ = "";

        foConsumptionPerDay_ = "";

        id_ = 0L;

        isActive_ = false;

        lightWeight_ = "";

        loadableStudyPortRotationId_ = 0L;

        loadableStudyId_ = 0L;

        otherIfAny_ = "";

        portId_ = "";

        runningDays_ = "";

        runningHours_ = "";

        saggingDeduction_ = "";

        sgCorrection_ = "";

        subTotal_ = "";

        totalFoConsumption_ = "";

        totalQuantity_ = "";

        tpcatDraft_ = "";

        vesselAverageSpeed_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadableQuantity_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity result =
            new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity(this);
        result.ballast_ = ballast_;
        result.boilerWaterOnBoard_ = boilerWaterOnBoard_;
        result.constant_ = constant_;
        result.deadWeight_ = deadWeight_;
        result.displacementAtDraftRestriction_ = displacementAtDraftRestriction_;
        result.distanceFromLastPort_ = distanceFromLastPort_;
        result.draftRestriction_ = draftRestriction_;
        result.estimatedDOOnBoard_ = estimatedDOOnBoard_;
        result.estimatedFOOnBoard_ = estimatedFOOnBoard_;
        result.estimatedFWOnBoard_ = estimatedFWOnBoard_;
        result.estimatedSagging_ = estimatedSagging_;
        result.estimatedSeaDensity_ = estimatedSeaDensity_;
        result.foConsumptionInSZ_ = foConsumptionInSZ_;
        result.foConsumptionPerDay_ = foConsumptionPerDay_;
        result.id_ = id_;
        result.isActive_ = isActive_;
        result.lightWeight_ = lightWeight_;
        result.loadableStudyPortRotationId_ = loadableStudyPortRotationId_;
        result.loadableStudyId_ = loadableStudyId_;
        result.otherIfAny_ = otherIfAny_;
        result.portId_ = portId_;
        result.runningDays_ = runningDays_;
        result.runningHours_ = runningHours_;
        result.saggingDeduction_ = saggingDeduction_;
        result.sgCorrection_ = sgCorrection_;
        result.subTotal_ = subTotal_;
        result.totalFoConsumption_ = totalFoConsumption_;
        result.totalQuantity_ = totalQuantity_;
        result.tpcatDraft_ = tpcatDraft_;
        result.vesselAverageSpeed_ = vesselAverageSpeed_;
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
            instanceof com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
                .getDefaultInstance()) return this;
        if (!other.getBallast().isEmpty()) {
          ballast_ = other.ballast_;
          onChanged();
        }
        if (!other.getBoilerWaterOnBoard().isEmpty()) {
          boilerWaterOnBoard_ = other.boilerWaterOnBoard_;
          onChanged();
        }
        if (!other.getConstant().isEmpty()) {
          constant_ = other.constant_;
          onChanged();
        }
        if (!other.getDeadWeight().isEmpty()) {
          deadWeight_ = other.deadWeight_;
          onChanged();
        }
        if (!other.getDisplacementAtDraftRestriction().isEmpty()) {
          displacementAtDraftRestriction_ = other.displacementAtDraftRestriction_;
          onChanged();
        }
        if (!other.getDistanceFromLastPort().isEmpty()) {
          distanceFromLastPort_ = other.distanceFromLastPort_;
          onChanged();
        }
        if (!other.getDraftRestriction().isEmpty()) {
          draftRestriction_ = other.draftRestriction_;
          onChanged();
        }
        if (!other.getEstimatedDOOnBoard().isEmpty()) {
          estimatedDOOnBoard_ = other.estimatedDOOnBoard_;
          onChanged();
        }
        if (!other.getEstimatedFOOnBoard().isEmpty()) {
          estimatedFOOnBoard_ = other.estimatedFOOnBoard_;
          onChanged();
        }
        if (!other.getEstimatedFWOnBoard().isEmpty()) {
          estimatedFWOnBoard_ = other.estimatedFWOnBoard_;
          onChanged();
        }
        if (!other.getEstimatedSagging().isEmpty()) {
          estimatedSagging_ = other.estimatedSagging_;
          onChanged();
        }
        if (!other.getEstimatedSeaDensity().isEmpty()) {
          estimatedSeaDensity_ = other.estimatedSeaDensity_;
          onChanged();
        }
        if (!other.getFoConsumptionInSZ().isEmpty()) {
          foConsumptionInSZ_ = other.foConsumptionInSZ_;
          onChanged();
        }
        if (!other.getFoConsumptionPerDay().isEmpty()) {
          foConsumptionPerDay_ = other.foConsumptionPerDay_;
          onChanged();
        }
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (other.getIsActive() != false) {
          setIsActive(other.getIsActive());
        }
        if (!other.getLightWeight().isEmpty()) {
          lightWeight_ = other.lightWeight_;
          onChanged();
        }
        if (other.getLoadableStudyPortRotationId() != 0L) {
          setLoadableStudyPortRotationId(other.getLoadableStudyPortRotationId());
        }
        if (other.getLoadableStudyId() != 0L) {
          setLoadableStudyId(other.getLoadableStudyId());
        }
        if (!other.getOtherIfAny().isEmpty()) {
          otherIfAny_ = other.otherIfAny_;
          onChanged();
        }
        if (!other.getPortId().isEmpty()) {
          portId_ = other.portId_;
          onChanged();
        }
        if (!other.getRunningDays().isEmpty()) {
          runningDays_ = other.runningDays_;
          onChanged();
        }
        if (!other.getRunningHours().isEmpty()) {
          runningHours_ = other.runningHours_;
          onChanged();
        }
        if (!other.getSaggingDeduction().isEmpty()) {
          saggingDeduction_ = other.saggingDeduction_;
          onChanged();
        }
        if (!other.getSgCorrection().isEmpty()) {
          sgCorrection_ = other.sgCorrection_;
          onChanged();
        }
        if (!other.getSubTotal().isEmpty()) {
          subTotal_ = other.subTotal_;
          onChanged();
        }
        if (!other.getTotalFoConsumption().isEmpty()) {
          totalFoConsumption_ = other.totalFoConsumption_;
          onChanged();
        }
        if (!other.getTotalQuantity().isEmpty()) {
          totalQuantity_ = other.totalQuantity_;
          onChanged();
        }
        if (!other.getTpcatDraft().isEmpty()) {
          tpcatDraft_ = other.tpcatDraft_;
          onChanged();
        }
        if (!other.getVesselAverageSpeed().isEmpty()) {
          vesselAverageSpeed_ = other.vesselAverageSpeed_;
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity parsedMessage =
            null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object ballast_ = "";
      /**
       * <code>string ballast = 1;</code>
       *
       * @return The ballast.
       */
      public java.lang.String getBallast() {
        java.lang.Object ref = ballast_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          ballast_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string ballast = 1;</code>
       *
       * @return The bytes for ballast.
       */
      public com.google.protobuf.ByteString getBallastBytes() {
        java.lang.Object ref = ballast_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          ballast_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string ballast = 1;</code>
       *
       * @param value The ballast to set.
       * @return This builder for chaining.
       */
      public Builder setBallast(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        ballast_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string ballast = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearBallast() {

        ballast_ = getDefaultInstance().getBallast();
        onChanged();
        return this;
      }
      /**
       * <code>string ballast = 1;</code>
       *
       * @param value The bytes for ballast to set.
       * @return This builder for chaining.
       */
      public Builder setBallastBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        ballast_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object boilerWaterOnBoard_ = "";
      /**
       * <code>string boilerWaterOnBoard = 2;</code>
       *
       * @return The boilerWaterOnBoard.
       */
      public java.lang.String getBoilerWaterOnBoard() {
        java.lang.Object ref = boilerWaterOnBoard_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          boilerWaterOnBoard_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string boilerWaterOnBoard = 2;</code>
       *
       * @return The bytes for boilerWaterOnBoard.
       */
      public com.google.protobuf.ByteString getBoilerWaterOnBoardBytes() {
        java.lang.Object ref = boilerWaterOnBoard_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          boilerWaterOnBoard_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string boilerWaterOnBoard = 2;</code>
       *
       * @param value The boilerWaterOnBoard to set.
       * @return This builder for chaining.
       */
      public Builder setBoilerWaterOnBoard(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        boilerWaterOnBoard_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string boilerWaterOnBoard = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearBoilerWaterOnBoard() {

        boilerWaterOnBoard_ = getDefaultInstance().getBoilerWaterOnBoard();
        onChanged();
        return this;
      }
      /**
       * <code>string boilerWaterOnBoard = 2;</code>
       *
       * @param value The bytes for boilerWaterOnBoard to set.
       * @return This builder for chaining.
       */
      public Builder setBoilerWaterOnBoardBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        boilerWaterOnBoard_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object constant_ = "";
      /**
       * <code>string constant = 3;</code>
       *
       * @return The constant.
       */
      public java.lang.String getConstant() {
        java.lang.Object ref = constant_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          constant_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string constant = 3;</code>
       *
       * @return The bytes for constant.
       */
      public com.google.protobuf.ByteString getConstantBytes() {
        java.lang.Object ref = constant_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          constant_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string constant = 3;</code>
       *
       * @param value The constant to set.
       * @return This builder for chaining.
       */
      public Builder setConstant(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        constant_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string constant = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearConstant() {

        constant_ = getDefaultInstance().getConstant();
        onChanged();
        return this;
      }
      /**
       * <code>string constant = 3;</code>
       *
       * @param value The bytes for constant to set.
       * @return This builder for chaining.
       */
      public Builder setConstantBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        constant_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object deadWeight_ = "";
      /**
       * <code>string deadWeight = 4;</code>
       *
       * @return The deadWeight.
       */
      public java.lang.String getDeadWeight() {
        java.lang.Object ref = deadWeight_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          deadWeight_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string deadWeight = 4;</code>
       *
       * @return The bytes for deadWeight.
       */
      public com.google.protobuf.ByteString getDeadWeightBytes() {
        java.lang.Object ref = deadWeight_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          deadWeight_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string deadWeight = 4;</code>
       *
       * @param value The deadWeight to set.
       * @return This builder for chaining.
       */
      public Builder setDeadWeight(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        deadWeight_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string deadWeight = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDeadWeight() {

        deadWeight_ = getDefaultInstance().getDeadWeight();
        onChanged();
        return this;
      }
      /**
       * <code>string deadWeight = 4;</code>
       *
       * @param value The bytes for deadWeight to set.
       * @return This builder for chaining.
       */
      public Builder setDeadWeightBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        deadWeight_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object displacementAtDraftRestriction_ = "";
      /**
       * <code>string displacementAtDraftRestriction = 5;</code>
       *
       * @return The displacementAtDraftRestriction.
       */
      public java.lang.String getDisplacementAtDraftRestriction() {
        java.lang.Object ref = displacementAtDraftRestriction_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          displacementAtDraftRestriction_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string displacementAtDraftRestriction = 5;</code>
       *
       * @return The bytes for displacementAtDraftRestriction.
       */
      public com.google.protobuf.ByteString getDisplacementAtDraftRestrictionBytes() {
        java.lang.Object ref = displacementAtDraftRestriction_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          displacementAtDraftRestriction_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string displacementAtDraftRestriction = 5;</code>
       *
       * @param value The displacementAtDraftRestriction to set.
       * @return This builder for chaining.
       */
      public Builder setDisplacementAtDraftRestriction(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        displacementAtDraftRestriction_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string displacementAtDraftRestriction = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDisplacementAtDraftRestriction() {

        displacementAtDraftRestriction_ = getDefaultInstance().getDisplacementAtDraftRestriction();
        onChanged();
        return this;
      }
      /**
       * <code>string displacementAtDraftRestriction = 5;</code>
       *
       * @param value The bytes for displacementAtDraftRestriction to set.
       * @return This builder for chaining.
       */
      public Builder setDisplacementAtDraftRestrictionBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        displacementAtDraftRestriction_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object distanceFromLastPort_ = "";
      /**
       * <code>string distanceFromLastPort = 6;</code>
       *
       * @return The distanceFromLastPort.
       */
      public java.lang.String getDistanceFromLastPort() {
        java.lang.Object ref = distanceFromLastPort_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          distanceFromLastPort_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string distanceFromLastPort = 6;</code>
       *
       * @return The bytes for distanceFromLastPort.
       */
      public com.google.protobuf.ByteString getDistanceFromLastPortBytes() {
        java.lang.Object ref = distanceFromLastPort_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          distanceFromLastPort_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string distanceFromLastPort = 6;</code>
       *
       * @param value The distanceFromLastPort to set.
       * @return This builder for chaining.
       */
      public Builder setDistanceFromLastPort(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        distanceFromLastPort_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string distanceFromLastPort = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDistanceFromLastPort() {

        distanceFromLastPort_ = getDefaultInstance().getDistanceFromLastPort();
        onChanged();
        return this;
      }
      /**
       * <code>string distanceFromLastPort = 6;</code>
       *
       * @param value The bytes for distanceFromLastPort to set.
       * @return This builder for chaining.
       */
      public Builder setDistanceFromLastPortBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        distanceFromLastPort_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object draftRestriction_ = "";
      /**
       * <code>string draftRestriction = 7;</code>
       *
       * @return The draftRestriction.
       */
      public java.lang.String getDraftRestriction() {
        java.lang.Object ref = draftRestriction_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          draftRestriction_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string draftRestriction = 7;</code>
       *
       * @return The bytes for draftRestriction.
       */
      public com.google.protobuf.ByteString getDraftRestrictionBytes() {
        java.lang.Object ref = draftRestriction_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          draftRestriction_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string draftRestriction = 7;</code>
       *
       * @param value The draftRestriction to set.
       * @return This builder for chaining.
       */
      public Builder setDraftRestriction(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        draftRestriction_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string draftRestriction = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDraftRestriction() {

        draftRestriction_ = getDefaultInstance().getDraftRestriction();
        onChanged();
        return this;
      }
      /**
       * <code>string draftRestriction = 7;</code>
       *
       * @param value The bytes for draftRestriction to set.
       * @return This builder for chaining.
       */
      public Builder setDraftRestrictionBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        draftRestriction_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object estimatedDOOnBoard_ = "";
      /**
       * <code>string estimatedDOOnBoard = 8;</code>
       *
       * @return The estimatedDOOnBoard.
       */
      public java.lang.String getEstimatedDOOnBoard() {
        java.lang.Object ref = estimatedDOOnBoard_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          estimatedDOOnBoard_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string estimatedDOOnBoard = 8;</code>
       *
       * @return The bytes for estimatedDOOnBoard.
       */
      public com.google.protobuf.ByteString getEstimatedDOOnBoardBytes() {
        java.lang.Object ref = estimatedDOOnBoard_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          estimatedDOOnBoard_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string estimatedDOOnBoard = 8;</code>
       *
       * @param value The estimatedDOOnBoard to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedDOOnBoard(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        estimatedDOOnBoard_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedDOOnBoard = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearEstimatedDOOnBoard() {

        estimatedDOOnBoard_ = getDefaultInstance().getEstimatedDOOnBoard();
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedDOOnBoard = 8;</code>
       *
       * @param value The bytes for estimatedDOOnBoard to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedDOOnBoardBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        estimatedDOOnBoard_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object estimatedFOOnBoard_ = "";
      /**
       * <code>string estimatedFOOnBoard = 9;</code>
       *
       * @return The estimatedFOOnBoard.
       */
      public java.lang.String getEstimatedFOOnBoard() {
        java.lang.Object ref = estimatedFOOnBoard_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          estimatedFOOnBoard_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string estimatedFOOnBoard = 9;</code>
       *
       * @return The bytes for estimatedFOOnBoard.
       */
      public com.google.protobuf.ByteString getEstimatedFOOnBoardBytes() {
        java.lang.Object ref = estimatedFOOnBoard_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          estimatedFOOnBoard_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string estimatedFOOnBoard = 9;</code>
       *
       * @param value The estimatedFOOnBoard to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedFOOnBoard(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        estimatedFOOnBoard_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedFOOnBoard = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearEstimatedFOOnBoard() {

        estimatedFOOnBoard_ = getDefaultInstance().getEstimatedFOOnBoard();
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedFOOnBoard = 9;</code>
       *
       * @param value The bytes for estimatedFOOnBoard to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedFOOnBoardBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        estimatedFOOnBoard_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object estimatedFWOnBoard_ = "";
      /**
       * <code>string estimatedFWOnBoard = 10;</code>
       *
       * @return The estimatedFWOnBoard.
       */
      public java.lang.String getEstimatedFWOnBoard() {
        java.lang.Object ref = estimatedFWOnBoard_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          estimatedFWOnBoard_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string estimatedFWOnBoard = 10;</code>
       *
       * @return The bytes for estimatedFWOnBoard.
       */
      public com.google.protobuf.ByteString getEstimatedFWOnBoardBytes() {
        java.lang.Object ref = estimatedFWOnBoard_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          estimatedFWOnBoard_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string estimatedFWOnBoard = 10;</code>
       *
       * @param value The estimatedFWOnBoard to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedFWOnBoard(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        estimatedFWOnBoard_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedFWOnBoard = 10;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearEstimatedFWOnBoard() {

        estimatedFWOnBoard_ = getDefaultInstance().getEstimatedFWOnBoard();
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedFWOnBoard = 10;</code>
       *
       * @param value The bytes for estimatedFWOnBoard to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedFWOnBoardBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        estimatedFWOnBoard_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object estimatedSagging_ = "";
      /**
       * <code>string estimatedSagging = 11;</code>
       *
       * @return The estimatedSagging.
       */
      public java.lang.String getEstimatedSagging() {
        java.lang.Object ref = estimatedSagging_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          estimatedSagging_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string estimatedSagging = 11;</code>
       *
       * @return The bytes for estimatedSagging.
       */
      public com.google.protobuf.ByteString getEstimatedSaggingBytes() {
        java.lang.Object ref = estimatedSagging_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          estimatedSagging_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string estimatedSagging = 11;</code>
       *
       * @param value The estimatedSagging to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedSagging(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        estimatedSagging_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedSagging = 11;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearEstimatedSagging() {

        estimatedSagging_ = getDefaultInstance().getEstimatedSagging();
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedSagging = 11;</code>
       *
       * @param value The bytes for estimatedSagging to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedSaggingBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        estimatedSagging_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object estimatedSeaDensity_ = "";
      /**
       * <code>string estimatedSeaDensity = 12;</code>
       *
       * @return The estimatedSeaDensity.
       */
      public java.lang.String getEstimatedSeaDensity() {
        java.lang.Object ref = estimatedSeaDensity_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          estimatedSeaDensity_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string estimatedSeaDensity = 12;</code>
       *
       * @return The bytes for estimatedSeaDensity.
       */
      public com.google.protobuf.ByteString getEstimatedSeaDensityBytes() {
        java.lang.Object ref = estimatedSeaDensity_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          estimatedSeaDensity_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string estimatedSeaDensity = 12;</code>
       *
       * @param value The estimatedSeaDensity to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedSeaDensity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        estimatedSeaDensity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedSeaDensity = 12;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearEstimatedSeaDensity() {

        estimatedSeaDensity_ = getDefaultInstance().getEstimatedSeaDensity();
        onChanged();
        return this;
      }
      /**
       * <code>string estimatedSeaDensity = 12;</code>
       *
       * @param value The bytes for estimatedSeaDensity to set.
       * @return This builder for chaining.
       */
      public Builder setEstimatedSeaDensityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        estimatedSeaDensity_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object foConsumptionInSZ_ = "";
      /**
       * <code>string foConsumptionInSZ = 13;</code>
       *
       * @return The foConsumptionInSZ.
       */
      public java.lang.String getFoConsumptionInSZ() {
        java.lang.Object ref = foConsumptionInSZ_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          foConsumptionInSZ_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string foConsumptionInSZ = 13;</code>
       *
       * @return The bytes for foConsumptionInSZ.
       */
      public com.google.protobuf.ByteString getFoConsumptionInSZBytes() {
        java.lang.Object ref = foConsumptionInSZ_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          foConsumptionInSZ_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string foConsumptionInSZ = 13;</code>
       *
       * @param value The foConsumptionInSZ to set.
       * @return This builder for chaining.
       */
      public Builder setFoConsumptionInSZ(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        foConsumptionInSZ_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string foConsumptionInSZ = 13;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearFoConsumptionInSZ() {

        foConsumptionInSZ_ = getDefaultInstance().getFoConsumptionInSZ();
        onChanged();
        return this;
      }
      /**
       * <code>string foConsumptionInSZ = 13;</code>
       *
       * @param value The bytes for foConsumptionInSZ to set.
       * @return This builder for chaining.
       */
      public Builder setFoConsumptionInSZBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        foConsumptionInSZ_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object foConsumptionPerDay_ = "";
      /**
       * <code>string foConsumptionPerDay = 14;</code>
       *
       * @return The foConsumptionPerDay.
       */
      public java.lang.String getFoConsumptionPerDay() {
        java.lang.Object ref = foConsumptionPerDay_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          foConsumptionPerDay_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string foConsumptionPerDay = 14;</code>
       *
       * @return The bytes for foConsumptionPerDay.
       */
      public com.google.protobuf.ByteString getFoConsumptionPerDayBytes() {
        java.lang.Object ref = foConsumptionPerDay_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          foConsumptionPerDay_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string foConsumptionPerDay = 14;</code>
       *
       * @param value The foConsumptionPerDay to set.
       * @return This builder for chaining.
       */
      public Builder setFoConsumptionPerDay(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        foConsumptionPerDay_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string foConsumptionPerDay = 14;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearFoConsumptionPerDay() {

        foConsumptionPerDay_ = getDefaultInstance().getFoConsumptionPerDay();
        onChanged();
        return this;
      }
      /**
       * <code>string foConsumptionPerDay = 14;</code>
       *
       * @param value The bytes for foConsumptionPerDay to set.
       * @return This builder for chaining.
       */
      public Builder setFoConsumptionPerDayBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        foConsumptionPerDay_ = value;
        onChanged();
        return this;
      }

      private long id_;
      /**
       * <code>int64 id = 15;</code>
       *
       * @return The id.
       */
      public long getId() {
        return id_;
      }
      /**
       * <code>int64 id = 15;</code>
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
       * <code>int64 id = 15;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {

        id_ = 0L;
        onChanged();
        return this;
      }

      private boolean isActive_;
      /**
       * <code>bool isActive = 16;</code>
       *
       * @return The isActive.
       */
      public boolean getIsActive() {
        return isActive_;
      }
      /**
       * <code>bool isActive = 16;</code>
       *
       * @param value The isActive to set.
       * @return This builder for chaining.
       */
      public Builder setIsActive(boolean value) {

        isActive_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool isActive = 16;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearIsActive() {

        isActive_ = false;
        onChanged();
        return this;
      }

      private java.lang.Object lightWeight_ = "";
      /**
       * <code>string lightWeight = 17;</code>
       *
       * @return The lightWeight.
       */
      public java.lang.String getLightWeight() {
        java.lang.Object ref = lightWeight_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          lightWeight_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string lightWeight = 17;</code>
       *
       * @return The bytes for lightWeight.
       */
      public com.google.protobuf.ByteString getLightWeightBytes() {
        java.lang.Object ref = lightWeight_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          lightWeight_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string lightWeight = 17;</code>
       *
       * @param value The lightWeight to set.
       * @return This builder for chaining.
       */
      public Builder setLightWeight(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        lightWeight_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string lightWeight = 17;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLightWeight() {

        lightWeight_ = getDefaultInstance().getLightWeight();
        onChanged();
        return this;
      }
      /**
       * <code>string lightWeight = 17;</code>
       *
       * @param value The bytes for lightWeight to set.
       * @return This builder for chaining.
       */
      public Builder setLightWeightBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        lightWeight_ = value;
        onChanged();
        return this;
      }

      private long loadableStudyPortRotationId_;
      /**
       * <code>int64 loadableStudyPortRotationId = 18;</code>
       *
       * @return The loadableStudyPortRotationId.
       */
      public long getLoadableStudyPortRotationId() {
        return loadableStudyPortRotationId_;
      }
      /**
       * <code>int64 loadableStudyPortRotationId = 18;</code>
       *
       * @param value The loadableStudyPortRotationId to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableStudyPortRotationId(long value) {

        loadableStudyPortRotationId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 loadableStudyPortRotationId = 18;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadableStudyPortRotationId() {

        loadableStudyPortRotationId_ = 0L;
        onChanged();
        return this;
      }

      private long loadableStudyId_;
      /**
       * <code>int64 loadableStudyId = 19;</code>
       *
       * @return The loadableStudyId.
       */
      public long getLoadableStudyId() {
        return loadableStudyId_;
      }
      /**
       * <code>int64 loadableStudyId = 19;</code>
       *
       * @param value The loadableStudyId to set.
       * @return This builder for chaining.
       */
      public Builder setLoadableStudyId(long value) {

        loadableStudyId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 loadableStudyId = 19;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadableStudyId() {

        loadableStudyId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object otherIfAny_ = "";
      /**
       * <code>string otherIfAny = 20;</code>
       *
       * @return The otherIfAny.
       */
      public java.lang.String getOtherIfAny() {
        java.lang.Object ref = otherIfAny_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          otherIfAny_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string otherIfAny = 20;</code>
       *
       * @return The bytes for otherIfAny.
       */
      public com.google.protobuf.ByteString getOtherIfAnyBytes() {
        java.lang.Object ref = otherIfAny_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          otherIfAny_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string otherIfAny = 20;</code>
       *
       * @param value The otherIfAny to set.
       * @return This builder for chaining.
       */
      public Builder setOtherIfAny(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        otherIfAny_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string otherIfAny = 20;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearOtherIfAny() {

        otherIfAny_ = getDefaultInstance().getOtherIfAny();
        onChanged();
        return this;
      }
      /**
       * <code>string otherIfAny = 20;</code>
       *
       * @param value The bytes for otherIfAny to set.
       * @return This builder for chaining.
       */
      public Builder setOtherIfAnyBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        otherIfAny_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object portId_ = "";
      /**
       * <code>string portId = 21;</code>
       *
       * @return The portId.
       */
      public java.lang.String getPortId() {
        java.lang.Object ref = portId_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          portId_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string portId = 21;</code>
       *
       * @return The bytes for portId.
       */
      public com.google.protobuf.ByteString getPortIdBytes() {
        java.lang.Object ref = portId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          portId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string portId = 21;</code>
       *
       * @param value The portId to set.
       * @return This builder for chaining.
       */
      public Builder setPortId(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        portId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string portId = 21;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPortId() {

        portId_ = getDefaultInstance().getPortId();
        onChanged();
        return this;
      }
      /**
       * <code>string portId = 21;</code>
       *
       * @param value The bytes for portId to set.
       * @return This builder for chaining.
       */
      public Builder setPortIdBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        portId_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object runningDays_ = "";
      /**
       * <code>string runningDays = 22;</code>
       *
       * @return The runningDays.
       */
      public java.lang.String getRunningDays() {
        java.lang.Object ref = runningDays_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          runningDays_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string runningDays = 22;</code>
       *
       * @return The bytes for runningDays.
       */
      public com.google.protobuf.ByteString getRunningDaysBytes() {
        java.lang.Object ref = runningDays_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          runningDays_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string runningDays = 22;</code>
       *
       * @param value The runningDays to set.
       * @return This builder for chaining.
       */
      public Builder setRunningDays(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        runningDays_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string runningDays = 22;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearRunningDays() {

        runningDays_ = getDefaultInstance().getRunningDays();
        onChanged();
        return this;
      }
      /**
       * <code>string runningDays = 22;</code>
       *
       * @param value The bytes for runningDays to set.
       * @return This builder for chaining.
       */
      public Builder setRunningDaysBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        runningDays_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object runningHours_ = "";
      /**
       * <code>string runningHours = 23;</code>
       *
       * @return The runningHours.
       */
      public java.lang.String getRunningHours() {
        java.lang.Object ref = runningHours_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          runningHours_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string runningHours = 23;</code>
       *
       * @return The bytes for runningHours.
       */
      public com.google.protobuf.ByteString getRunningHoursBytes() {
        java.lang.Object ref = runningHours_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          runningHours_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string runningHours = 23;</code>
       *
       * @param value The runningHours to set.
       * @return This builder for chaining.
       */
      public Builder setRunningHours(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        runningHours_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string runningHours = 23;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearRunningHours() {

        runningHours_ = getDefaultInstance().getRunningHours();
        onChanged();
        return this;
      }
      /**
       * <code>string runningHours = 23;</code>
       *
       * @param value The bytes for runningHours to set.
       * @return This builder for chaining.
       */
      public Builder setRunningHoursBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        runningHours_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object saggingDeduction_ = "";
      /**
       * <code>string saggingDeduction = 24;</code>
       *
       * @return The saggingDeduction.
       */
      public java.lang.String getSaggingDeduction() {
        java.lang.Object ref = saggingDeduction_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          saggingDeduction_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string saggingDeduction = 24;</code>
       *
       * @return The bytes for saggingDeduction.
       */
      public com.google.protobuf.ByteString getSaggingDeductionBytes() {
        java.lang.Object ref = saggingDeduction_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          saggingDeduction_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string saggingDeduction = 24;</code>
       *
       * @param value The saggingDeduction to set.
       * @return This builder for chaining.
       */
      public Builder setSaggingDeduction(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        saggingDeduction_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string saggingDeduction = 24;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSaggingDeduction() {

        saggingDeduction_ = getDefaultInstance().getSaggingDeduction();
        onChanged();
        return this;
      }
      /**
       * <code>string saggingDeduction = 24;</code>
       *
       * @param value The bytes for saggingDeduction to set.
       * @return This builder for chaining.
       */
      public Builder setSaggingDeductionBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        saggingDeduction_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object sgCorrection_ = "";
      /**
       * <code>string sgCorrection = 25;</code>
       *
       * @return The sgCorrection.
       */
      public java.lang.String getSgCorrection() {
        java.lang.Object ref = sgCorrection_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          sgCorrection_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string sgCorrection = 25;</code>
       *
       * @return The bytes for sgCorrection.
       */
      public com.google.protobuf.ByteString getSgCorrectionBytes() {
        java.lang.Object ref = sgCorrection_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          sgCorrection_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string sgCorrection = 25;</code>
       *
       * @param value The sgCorrection to set.
       * @return This builder for chaining.
       */
      public Builder setSgCorrection(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        sgCorrection_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string sgCorrection = 25;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSgCorrection() {

        sgCorrection_ = getDefaultInstance().getSgCorrection();
        onChanged();
        return this;
      }
      /**
       * <code>string sgCorrection = 25;</code>
       *
       * @param value The bytes for sgCorrection to set.
       * @return This builder for chaining.
       */
      public Builder setSgCorrectionBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        sgCorrection_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object subTotal_ = "";
      /**
       * <code>string subTotal = 26;</code>
       *
       * @return The subTotal.
       */
      public java.lang.String getSubTotal() {
        java.lang.Object ref = subTotal_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          subTotal_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string subTotal = 26;</code>
       *
       * @return The bytes for subTotal.
       */
      public com.google.protobuf.ByteString getSubTotalBytes() {
        java.lang.Object ref = subTotal_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          subTotal_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string subTotal = 26;</code>
       *
       * @param value The subTotal to set.
       * @return This builder for chaining.
       */
      public Builder setSubTotal(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        subTotal_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string subTotal = 26;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSubTotal() {

        subTotal_ = getDefaultInstance().getSubTotal();
        onChanged();
        return this;
      }
      /**
       * <code>string subTotal = 26;</code>
       *
       * @param value The bytes for subTotal to set.
       * @return This builder for chaining.
       */
      public Builder setSubTotalBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        subTotal_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object totalFoConsumption_ = "";
      /**
       * <code>string totalFoConsumption = 27;</code>
       *
       * @return The totalFoConsumption.
       */
      public java.lang.String getTotalFoConsumption() {
        java.lang.Object ref = totalFoConsumption_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          totalFoConsumption_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string totalFoConsumption = 27;</code>
       *
       * @return The bytes for totalFoConsumption.
       */
      public com.google.protobuf.ByteString getTotalFoConsumptionBytes() {
        java.lang.Object ref = totalFoConsumption_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          totalFoConsumption_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string totalFoConsumption = 27;</code>
       *
       * @param value The totalFoConsumption to set.
       * @return This builder for chaining.
       */
      public Builder setTotalFoConsumption(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        totalFoConsumption_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string totalFoConsumption = 27;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTotalFoConsumption() {

        totalFoConsumption_ = getDefaultInstance().getTotalFoConsumption();
        onChanged();
        return this;
      }
      /**
       * <code>string totalFoConsumption = 27;</code>
       *
       * @param value The bytes for totalFoConsumption to set.
       * @return This builder for chaining.
       */
      public Builder setTotalFoConsumptionBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        totalFoConsumption_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object totalQuantity_ = "";
      /**
       * <code>string totalQuantity = 28;</code>
       *
       * @return The totalQuantity.
       */
      public java.lang.String getTotalQuantity() {
        java.lang.Object ref = totalQuantity_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          totalQuantity_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string totalQuantity = 28;</code>
       *
       * @return The bytes for totalQuantity.
       */
      public com.google.protobuf.ByteString getTotalQuantityBytes() {
        java.lang.Object ref = totalQuantity_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          totalQuantity_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string totalQuantity = 28;</code>
       *
       * @param value The totalQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setTotalQuantity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        totalQuantity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string totalQuantity = 28;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTotalQuantity() {

        totalQuantity_ = getDefaultInstance().getTotalQuantity();
        onChanged();
        return this;
      }
      /**
       * <code>string totalQuantity = 28;</code>
       *
       * @param value The bytes for totalQuantity to set.
       * @return This builder for chaining.
       */
      public Builder setTotalQuantityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        totalQuantity_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object tpcatDraft_ = "";
      /**
       * <code>string tpcatDraft = 29;</code>
       *
       * @return The tpcatDraft.
       */
      public java.lang.String getTpcatDraft() {
        java.lang.Object ref = tpcatDraft_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          tpcatDraft_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string tpcatDraft = 29;</code>
       *
       * @return The bytes for tpcatDraft.
       */
      public com.google.protobuf.ByteString getTpcatDraftBytes() {
        java.lang.Object ref = tpcatDraft_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          tpcatDraft_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string tpcatDraft = 29;</code>
       *
       * @param value The tpcatDraft to set.
       * @return This builder for chaining.
       */
      public Builder setTpcatDraft(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        tpcatDraft_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string tpcatDraft = 29;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTpcatDraft() {

        tpcatDraft_ = getDefaultInstance().getTpcatDraft();
        onChanged();
        return this;
      }
      /**
       * <code>string tpcatDraft = 29;</code>
       *
       * @param value The bytes for tpcatDraft to set.
       * @return This builder for chaining.
       */
      public Builder setTpcatDraftBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        tpcatDraft_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object vesselAverageSpeed_ = "";
      /**
       * <code>string vesselAverageSpeed = 30;</code>
       *
       * @return The vesselAverageSpeed.
       */
      public java.lang.String getVesselAverageSpeed() {
        java.lang.Object ref = vesselAverageSpeed_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          vesselAverageSpeed_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string vesselAverageSpeed = 30;</code>
       *
       * @return The bytes for vesselAverageSpeed.
       */
      public com.google.protobuf.ByteString getVesselAverageSpeedBytes() {
        java.lang.Object ref = vesselAverageSpeed_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          vesselAverageSpeed_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string vesselAverageSpeed = 30;</code>
       *
       * @param value The vesselAverageSpeed to set.
       * @return This builder for chaining.
       */
      public Builder setVesselAverageSpeed(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        vesselAverageSpeed_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string vesselAverageSpeed = 30;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearVesselAverageSpeed() {

        vesselAverageSpeed_ = getDefaultInstance().getVesselAverageSpeed();
        onChanged();
        return this;
      }
      /**
       * <code>string vesselAverageSpeed = 30;</code>
       *
       * @param value The bytes for vesselAverageSpeed to set.
       * @return This builder for chaining.
       */
      public Builder setVesselAverageSpeedBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        vesselAverageSpeed_ = value;
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

      // @@protoc_insertion_point(builder_scope:LoadableQuantity)
    }

    // @@protoc_insertion_point(class_scope:LoadableQuantity)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadableQuantity> PARSER =
        new com.google.protobuf.AbstractParser<LoadableQuantity>() {
          @java.lang.Override
          public LoadableQuantity parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadableQuantity(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadableQuantity> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadableQuantity> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadableQuantity
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface CargoToppingOffSequenceOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoToppingOffSequence)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 cargoXId = 1;</code>
     *
     * @return The cargoXId.
     */
    long getCargoXId();

    /**
     * <code>string fillingRatio = 2;</code>
     *
     * @return The fillingRatio.
     */
    java.lang.String getFillingRatio();
    /**
     * <code>string fillingRatio = 2;</code>
     *
     * @return The bytes for fillingRatio.
     */
    com.google.protobuf.ByteString getFillingRatioBytes();

    /**
     * <code>bool isActive = 3;</code>
     *
     * @return The isActive.
     */
    boolean getIsActive();

    /**
     * <code>int32 orderNumber = 4;</code>
     *
     * @return The orderNumber.
     */
    int getOrderNumber();

    /**
     * <code>string remarks = 5;</code>
     *
     * @return The remarks.
     */
    java.lang.String getRemarks();
    /**
     * <code>string remarks = 5;</code>
     *
     * @return The bytes for remarks.
     */
    com.google.protobuf.ByteString getRemarksBytes();

    /**
     * <code>int64 tankXId = 6;</code>
     *
     * @return The tankXId.
     */
    long getTankXId();

    /**
     * <code>string ullage = 7;</code>
     *
     * @return The ullage.
     */
    java.lang.String getUllage();
    /**
     * <code>string ullage = 7;</code>
     *
     * @return The bytes for ullage.
     */
    com.google.protobuf.ByteString getUllageBytes();

    /**
     * <code>string volume = 8;</code>
     *
     * @return The volume.
     */
    java.lang.String getVolume();
    /**
     * <code>string volume = 8;</code>
     *
     * @return The bytes for volume.
     */
    com.google.protobuf.ByteString getVolumeBytes();

    /**
     * <code>string weight = 9;</code>
     *
     * @return The weight.
     */
    java.lang.String getWeight();
    /**
     * <code>string weight = 9;</code>
     *
     * @return The bytes for weight.
     */
    com.google.protobuf.ByteString getWeightBytes();

    /**
     * <code>int64 loadablePatternId = 10;</code>
     *
     * @return The loadablePatternId.
     */
    long getLoadablePatternId();
  }
  /** Protobuf type {@code CargoToppingOffSequence} */
  public static final class CargoToppingOffSequence extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoToppingOffSequence)
      CargoToppingOffSequenceOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoToppingOffSequence.newBuilder() to construct.
    private CargoToppingOffSequence(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoToppingOffSequence() {
      fillingRatio_ = "";
      remarks_ = "";
      ullage_ = "";
      volume_ = "";
      weight_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoToppingOffSequence();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoToppingOffSequence(
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
                cargoXId_ = input.readInt64();
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                fillingRatio_ = s;
                break;
              }
            case 24:
              {
                isActive_ = input.readBool();
                break;
              }
            case 32:
              {
                orderNumber_ = input.readInt32();
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                remarks_ = s;
                break;
              }
            case 48:
              {
                tankXId_ = input.readInt64();
                break;
              }
            case 58:
              {
                java.lang.String s = input.readStringRequireUtf8();

                ullage_ = s;
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                volume_ = s;
                break;
              }
            case 74:
              {
                java.lang.String s = input.readStringRequireUtf8();

                weight_ = s;
                break;
              }
            case 80:
              {
                loadablePatternId_ = input.readInt64();
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
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_CargoToppingOffSequence_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_CargoToppingOffSequence_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                  .class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                  .Builder.class);
    }

    public static final int CARGOXID_FIELD_NUMBER = 1;
    private long cargoXId_;
    /**
     * <code>int64 cargoXId = 1;</code>
     *
     * @return The cargoXId.
     */
    public long getCargoXId() {
      return cargoXId_;
    }

    public static final int FILLINGRATIO_FIELD_NUMBER = 2;
    private volatile java.lang.Object fillingRatio_;
    /**
     * <code>string fillingRatio = 2;</code>
     *
     * @return The fillingRatio.
     */
    public java.lang.String getFillingRatio() {
      java.lang.Object ref = fillingRatio_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        fillingRatio_ = s;
        return s;
      }
    }
    /**
     * <code>string fillingRatio = 2;</code>
     *
     * @return The bytes for fillingRatio.
     */
    public com.google.protobuf.ByteString getFillingRatioBytes() {
      java.lang.Object ref = fillingRatio_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        fillingRatio_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ISACTIVE_FIELD_NUMBER = 3;
    private boolean isActive_;
    /**
     * <code>bool isActive = 3;</code>
     *
     * @return The isActive.
     */
    public boolean getIsActive() {
      return isActive_;
    }

    public static final int ORDERNUMBER_FIELD_NUMBER = 4;
    private int orderNumber_;
    /**
     * <code>int32 orderNumber = 4;</code>
     *
     * @return The orderNumber.
     */
    public int getOrderNumber() {
      return orderNumber_;
    }

    public static final int REMARKS_FIELD_NUMBER = 5;
    private volatile java.lang.Object remarks_;
    /**
     * <code>string remarks = 5;</code>
     *
     * @return The remarks.
     */
    public java.lang.String getRemarks() {
      java.lang.Object ref = remarks_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        remarks_ = s;
        return s;
      }
    }
    /**
     * <code>string remarks = 5;</code>
     *
     * @return The bytes for remarks.
     */
    public com.google.protobuf.ByteString getRemarksBytes() {
      java.lang.Object ref = remarks_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        remarks_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TANKXID_FIELD_NUMBER = 6;
    private long tankXId_;
    /**
     * <code>int64 tankXId = 6;</code>
     *
     * @return The tankXId.
     */
    public long getTankXId() {
      return tankXId_;
    }

    public static final int ULLAGE_FIELD_NUMBER = 7;
    private volatile java.lang.Object ullage_;
    /**
     * <code>string ullage = 7;</code>
     *
     * @return The ullage.
     */
    public java.lang.String getUllage() {
      java.lang.Object ref = ullage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        ullage_ = s;
        return s;
      }
    }
    /**
     * <code>string ullage = 7;</code>
     *
     * @return The bytes for ullage.
     */
    public com.google.protobuf.ByteString getUllageBytes() {
      java.lang.Object ref = ullage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        ullage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int VOLUME_FIELD_NUMBER = 8;
    private volatile java.lang.Object volume_;
    /**
     * <code>string volume = 8;</code>
     *
     * @return The volume.
     */
    public java.lang.String getVolume() {
      java.lang.Object ref = volume_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        volume_ = s;
        return s;
      }
    }
    /**
     * <code>string volume = 8;</code>
     *
     * @return The bytes for volume.
     */
    public com.google.protobuf.ByteString getVolumeBytes() {
      java.lang.Object ref = volume_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        volume_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int WEIGHT_FIELD_NUMBER = 9;
    private volatile java.lang.Object weight_;
    /**
     * <code>string weight = 9;</code>
     *
     * @return The weight.
     */
    public java.lang.String getWeight() {
      java.lang.Object ref = weight_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        weight_ = s;
        return s;
      }
    }
    /**
     * <code>string weight = 9;</code>
     *
     * @return The bytes for weight.
     */
    public com.google.protobuf.ByteString getWeightBytes() {
      java.lang.Object ref = weight_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        weight_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOADABLEPATTERNID_FIELD_NUMBER = 10;
    private long loadablePatternId_;
    /**
     * <code>int64 loadablePatternId = 10;</code>
     *
     * @return The loadablePatternId.
     */
    public long getLoadablePatternId() {
      return loadablePatternId_;
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
      if (cargoXId_ != 0L) {
        output.writeInt64(1, cargoXId_);
      }
      if (!getFillingRatioBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, fillingRatio_);
      }
      if (isActive_ != false) {
        output.writeBool(3, isActive_);
      }
      if (orderNumber_ != 0) {
        output.writeInt32(4, orderNumber_);
      }
      if (!getRemarksBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, remarks_);
      }
      if (tankXId_ != 0L) {
        output.writeInt64(6, tankXId_);
      }
      if (!getUllageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, ullage_);
      }
      if (!getVolumeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, volume_);
      }
      if (!getWeightBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 9, weight_);
      }
      if (loadablePatternId_ != 0L) {
        output.writeInt64(10, loadablePatternId_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (cargoXId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, cargoXId_);
      }
      if (!getFillingRatioBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, fillingRatio_);
      }
      if (isActive_ != false) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(3, isActive_);
      }
      if (orderNumber_ != 0) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(4, orderNumber_);
      }
      if (!getRemarksBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, remarks_);
      }
      if (tankXId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(6, tankXId_);
      }
      if (!getUllageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, ullage_);
      }
      if (!getVolumeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, volume_);
      }
      if (!getWeightBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, weight_);
      }
      if (loadablePatternId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(10, loadablePatternId_);
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
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence) obj;

      if (getCargoXId() != other.getCargoXId()) return false;
      if (!getFillingRatio().equals(other.getFillingRatio())) return false;
      if (getIsActive() != other.getIsActive()) return false;
      if (getOrderNumber() != other.getOrderNumber()) return false;
      if (!getRemarks().equals(other.getRemarks())) return false;
      if (getTankXId() != other.getTankXId()) return false;
      if (!getUllage().equals(other.getUllage())) return false;
      if (!getVolume().equals(other.getVolume())) return false;
      if (!getWeight().equals(other.getWeight())) return false;
      if (getLoadablePatternId() != other.getLoadablePatternId()) return false;
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
      hash = (37 * hash) + CARGOXID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoXId());
      hash = (37 * hash) + FILLINGRATIO_FIELD_NUMBER;
      hash = (53 * hash) + getFillingRatio().hashCode();
      hash = (37 * hash) + ISACTIVE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsActive());
      hash = (37 * hash) + ORDERNUMBER_FIELD_NUMBER;
      hash = (53 * hash) + getOrderNumber();
      hash = (37 * hash) + REMARKS_FIELD_NUMBER;
      hash = (53 * hash) + getRemarks().hashCode();
      hash = (37 * hash) + TANKXID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getTankXId());
      hash = (37 * hash) + ULLAGE_FIELD_NUMBER;
      hash = (53 * hash) + getUllage().hashCode();
      hash = (37 * hash) + VOLUME_FIELD_NUMBER;
      hash = (53 * hash) + getVolume().hashCode();
      hash = (37 * hash) + WEIGHT_FIELD_NUMBER;
      hash = (53 * hash) + getWeight().hashCode();
      hash = (37 * hash) + LOADABLEPATTERNID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadablePatternId());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
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
    /** Protobuf type {@code CargoToppingOffSequence} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoToppingOffSequence)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequenceOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_CargoToppingOffSequence_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_CargoToppingOffSequence_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                    .class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                    .Builder.class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence.newBuilder()
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
        cargoXId_ = 0L;

        fillingRatio_ = "";

        isActive_ = false;

        orderNumber_ = 0;

        remarks_ = "";

        tankXId_ = 0L;

        ullage_ = "";

        volume_ = "";

        weight_ = "";

        loadablePatternId_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_CargoToppingOffSequence_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
          build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence result =
            new com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence(
                this);
        result.cargoXId_ = cargoXId_;
        result.fillingRatio_ = fillingRatio_;
        result.isActive_ = isActive_;
        result.orderNumber_ = orderNumber_;
        result.remarks_ = remarks_;
        result.tankXId_ = tankXId_;
        result.ullage_ = ullage_;
        result.volume_ = volume_;
        result.weight_ = weight_;
        result.loadablePatternId_ = loadablePatternId_;
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
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                .getDefaultInstance()) return this;
        if (other.getCargoXId() != 0L) {
          setCargoXId(other.getCargoXId());
        }
        if (!other.getFillingRatio().isEmpty()) {
          fillingRatio_ = other.fillingRatio_;
          onChanged();
        }
        if (other.getIsActive() != false) {
          setIsActive(other.getIsActive());
        }
        if (other.getOrderNumber() != 0) {
          setOrderNumber(other.getOrderNumber());
        }
        if (!other.getRemarks().isEmpty()) {
          remarks_ = other.remarks_;
          onChanged();
        }
        if (other.getTankXId() != 0L) {
          setTankXId(other.getTankXId());
        }
        if (!other.getUllage().isEmpty()) {
          ullage_ = other.ullage_;
          onChanged();
        }
        if (!other.getVolume().isEmpty()) {
          volume_ = other.volume_;
          onChanged();
        }
        if (!other.getWeight().isEmpty()) {
          weight_ = other.weight_;
          onChanged();
        }
        if (other.getLoadablePatternId() != 0L) {
          setLoadablePatternId(other.getLoadablePatternId());
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long cargoXId_;
      /**
       * <code>int64 cargoXId = 1;</code>
       *
       * @return The cargoXId.
       */
      public long getCargoXId() {
        return cargoXId_;
      }
      /**
       * <code>int64 cargoXId = 1;</code>
       *
       * @param value The cargoXId to set.
       * @return This builder for chaining.
       */
      public Builder setCargoXId(long value) {

        cargoXId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 cargoXId = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoXId() {

        cargoXId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object fillingRatio_ = "";
      /**
       * <code>string fillingRatio = 2;</code>
       *
       * @return The fillingRatio.
       */
      public java.lang.String getFillingRatio() {
        java.lang.Object ref = fillingRatio_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          fillingRatio_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string fillingRatio = 2;</code>
       *
       * @return The bytes for fillingRatio.
       */
      public com.google.protobuf.ByteString getFillingRatioBytes() {
        java.lang.Object ref = fillingRatio_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          fillingRatio_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string fillingRatio = 2;</code>
       *
       * @param value The fillingRatio to set.
       * @return This builder for chaining.
       */
      public Builder setFillingRatio(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        fillingRatio_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string fillingRatio = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearFillingRatio() {

        fillingRatio_ = getDefaultInstance().getFillingRatio();
        onChanged();
        return this;
      }
      /**
       * <code>string fillingRatio = 2;</code>
       *
       * @param value The bytes for fillingRatio to set.
       * @return This builder for chaining.
       */
      public Builder setFillingRatioBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        fillingRatio_ = value;
        onChanged();
        return this;
      }

      private boolean isActive_;
      /**
       * <code>bool isActive = 3;</code>
       *
       * @return The isActive.
       */
      public boolean getIsActive() {
        return isActive_;
      }
      /**
       * <code>bool isActive = 3;</code>
       *
       * @param value The isActive to set.
       * @return This builder for chaining.
       */
      public Builder setIsActive(boolean value) {

        isActive_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool isActive = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearIsActive() {

        isActive_ = false;
        onChanged();
        return this;
      }

      private int orderNumber_;
      /**
       * <code>int32 orderNumber = 4;</code>
       *
       * @return The orderNumber.
       */
      public int getOrderNumber() {
        return orderNumber_;
      }
      /**
       * <code>int32 orderNumber = 4;</code>
       *
       * @param value The orderNumber to set.
       * @return This builder for chaining.
       */
      public Builder setOrderNumber(int value) {

        orderNumber_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 orderNumber = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearOrderNumber() {

        orderNumber_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object remarks_ = "";
      /**
       * <code>string remarks = 5;</code>
       *
       * @return The remarks.
       */
      public java.lang.String getRemarks() {
        java.lang.Object ref = remarks_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          remarks_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string remarks = 5;</code>
       *
       * @return The bytes for remarks.
       */
      public com.google.protobuf.ByteString getRemarksBytes() {
        java.lang.Object ref = remarks_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          remarks_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string remarks = 5;</code>
       *
       * @param value The remarks to set.
       * @return This builder for chaining.
       */
      public Builder setRemarks(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        remarks_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string remarks = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearRemarks() {

        remarks_ = getDefaultInstance().getRemarks();
        onChanged();
        return this;
      }
      /**
       * <code>string remarks = 5;</code>
       *
       * @param value The bytes for remarks to set.
       * @return This builder for chaining.
       */
      public Builder setRemarksBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        remarks_ = value;
        onChanged();
        return this;
      }

      private long tankXId_;
      /**
       * <code>int64 tankXId = 6;</code>
       *
       * @return The tankXId.
       */
      public long getTankXId() {
        return tankXId_;
      }
      /**
       * <code>int64 tankXId = 6;</code>
       *
       * @param value The tankXId to set.
       * @return This builder for chaining.
       */
      public Builder setTankXId(long value) {

        tankXId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 tankXId = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTankXId() {

        tankXId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object ullage_ = "";
      /**
       * <code>string ullage = 7;</code>
       *
       * @return The ullage.
       */
      public java.lang.String getUllage() {
        java.lang.Object ref = ullage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          ullage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string ullage = 7;</code>
       *
       * @return The bytes for ullage.
       */
      public com.google.protobuf.ByteString getUllageBytes() {
        java.lang.Object ref = ullage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          ullage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string ullage = 7;</code>
       *
       * @param value The ullage to set.
       * @return This builder for chaining.
       */
      public Builder setUllage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        ullage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string ullage = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearUllage() {

        ullage_ = getDefaultInstance().getUllage();
        onChanged();
        return this;
      }
      /**
       * <code>string ullage = 7;</code>
       *
       * @param value The bytes for ullage to set.
       * @return This builder for chaining.
       */
      public Builder setUllageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        ullage_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object volume_ = "";
      /**
       * <code>string volume = 8;</code>
       *
       * @return The volume.
       */
      public java.lang.String getVolume() {
        java.lang.Object ref = volume_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          volume_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string volume = 8;</code>
       *
       * @return The bytes for volume.
       */
      public com.google.protobuf.ByteString getVolumeBytes() {
        java.lang.Object ref = volume_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          volume_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string volume = 8;</code>
       *
       * @param value The volume to set.
       * @return This builder for chaining.
       */
      public Builder setVolume(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        volume_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string volume = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearVolume() {

        volume_ = getDefaultInstance().getVolume();
        onChanged();
        return this;
      }
      /**
       * <code>string volume = 8;</code>
       *
       * @param value The bytes for volume to set.
       * @return This builder for chaining.
       */
      public Builder setVolumeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        volume_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object weight_ = "";
      /**
       * <code>string weight = 9;</code>
       *
       * @return The weight.
       */
      public java.lang.String getWeight() {
        java.lang.Object ref = weight_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          weight_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string weight = 9;</code>
       *
       * @return The bytes for weight.
       */
      public com.google.protobuf.ByteString getWeightBytes() {
        java.lang.Object ref = weight_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          weight_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string weight = 9;</code>
       *
       * @param value The weight to set.
       * @return This builder for chaining.
       */
      public Builder setWeight(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        weight_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string weight = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearWeight() {

        weight_ = getDefaultInstance().getWeight();
        onChanged();
        return this;
      }
      /**
       * <code>string weight = 9;</code>
       *
       * @param value The bytes for weight to set.
       * @return This builder for chaining.
       */
      public Builder setWeightBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        weight_ = value;
        onChanged();
        return this;
      }

      private long loadablePatternId_;
      /**
       * <code>int64 loadablePatternId = 10;</code>
       *
       * @return The loadablePatternId.
       */
      public long getLoadablePatternId() {
        return loadablePatternId_;
      }
      /**
       * <code>int64 loadablePatternId = 10;</code>
       *
       * @param value The loadablePatternId to set.
       * @return This builder for chaining.
       */
      public Builder setLoadablePatternId(long value) {

        loadablePatternId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 loadablePatternId = 10;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadablePatternId() {

        loadablePatternId_ = 0L;
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

      // @@protoc_insertion_point(builder_scope:CargoToppingOffSequence)
    }

    // @@protoc_insertion_point(class_scope:CargoToppingOffSequence)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .CargoToppingOffSequence
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoToppingOffSequence> PARSER =
        new com.google.protobuf.AbstractParser<CargoToppingOffSequence>() {
          @java.lang.Override
          public CargoToppingOffSequence parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoToppingOffSequence(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoToppingOffSequence> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoToppingOffSequence> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadingPlanSyncDetailsOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadingPlanSyncDetails)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.LoadingInformationDetail loadingInformationDetail = 1;</code>
     *
     * @return Whether the loadingInformationDetail field is set.
     */
    boolean hasLoadingInformationDetail();
    /**
     * <code>.LoadingInformationDetail loadingInformationDetail = 1;</code>
     *
     * @return The loadingInformationDetail.
     */
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        getLoadingInformationDetail();
    /** <code>.LoadingInformationDetail loadingInformationDetail = 1;</code> */
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetailOrBuilder
        getLoadingInformationDetailOrBuilder();

    /**
     * <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code>
     *
     * @return Whether the loadablePlanDetailsReply field is set.
     */
    boolean hasLoadablePlanDetailsReply();
    /**
     * <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code>
     *
     * @return The loadablePlanDetailsReply.
     */
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply getLoadablePlanDetailsReply();
    /** <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code> */
    com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReplyOrBuilder
        getLoadablePlanDetailsReplyOrBuilder();

    /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
    java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence>
        getCargoToppingOffSequencesList();
    /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        getCargoToppingOffSequences(int index);
    /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
    int getCargoToppingOffSequencesCount();
    /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
    java.util.List<
            ? extends
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .CargoToppingOffSequenceOrBuilder>
        getCargoToppingOffSequencesOrBuilderList();
    /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequenceOrBuilder
        getCargoToppingOffSequencesOrBuilder(int index);
  }
  /** Protobuf type {@code LoadingPlanSyncDetails} */
  public static final class LoadingPlanSyncDetails extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadingPlanSyncDetails)
      LoadingPlanSyncDetailsOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadingPlanSyncDetails.newBuilder() to construct.
    private LoadingPlanSyncDetails(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadingPlanSyncDetails() {
      cargoToppingOffSequences_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadingPlanSyncDetails();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadingPlanSyncDetails(
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
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                        .Builder
                    subBuilder = null;
                if (loadingInformationDetail_ != null) {
                  subBuilder = loadingInformationDetail_.toBuilder();
                }
                loadingInformationDetail_ =
                    input.readMessage(
                        com.cpdss.common.generated.loading_plan.LoadingPlanModels
                            .LoadingInformationDetail.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(loadingInformationDetail_);
                  loadingInformationDetail_ = subBuilder.buildPartial();
                }

                break;
              }
            case 18:
              {
                com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder
                    subBuilder = null;
                if (loadablePlanDetailsReply_ != null) {
                  subBuilder = loadablePlanDetailsReply_.toBuilder();
                }
                loadablePlanDetailsReply_ =
                    input.readMessage(
                        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(loadablePlanDetailsReply_);
                  loadablePlanDetailsReply_ = subBuilder.buildPartial();
                }

                break;
              }
            case 26:
              {
                if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                  cargoToppingOffSequences_ =
                      new java.util.ArrayList<
                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .CargoToppingOffSequence>();
                  mutable_bitField0_ |= 0x00000001;
                }
                cargoToppingOffSequences_.add(
                    input.readMessage(
                        com.cpdss.common.generated.loading_plan.LoadingPlanModels
                            .CargoToppingOffSequence.parser(),
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
          cargoToppingOffSequences_ =
              java.util.Collections.unmodifiableList(cargoToppingOffSequences_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadingPlanSyncDetails_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadingPlanSyncDetails_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
                  .class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
                  .Builder.class);
    }

    public static final int LOADINGINFORMATIONDETAIL_FIELD_NUMBER = 1;
    private com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        loadingInformationDetail_;
    /**
     * <code>.LoadingInformationDetail loadingInformationDetail = 1;</code>
     *
     * @return Whether the loadingInformationDetail field is set.
     */
    public boolean hasLoadingInformationDetail() {
      return loadingInformationDetail_ != null;
    }
    /**
     * <code>.LoadingInformationDetail loadingInformationDetail = 1;</code>
     *
     * @return The loadingInformationDetail.
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
        getLoadingInformationDetail() {
      return loadingInformationDetail_ == null
          ? com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
              .getDefaultInstance()
          : loadingInformationDetail_;
    }
    /** <code>.LoadingInformationDetail loadingInformationDetail = 1;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingInformationDetailOrBuilder
        getLoadingInformationDetailOrBuilder() {
      return getLoadingInformationDetail();
    }

    public static final int LOADABLEPLANDETAILSREPLY_FIELD_NUMBER = 2;
    private com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply
        loadablePlanDetailsReply_;
    /**
     * <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code>
     *
     * @return Whether the loadablePlanDetailsReply field is set.
     */
    public boolean hasLoadablePlanDetailsReply() {
      return loadablePlanDetailsReply_ != null;
    }
    /**
     * <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code>
     *
     * @return The loadablePlanDetailsReply.
     */
    public com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply
        getLoadablePlanDetailsReply() {
      return loadablePlanDetailsReply_ == null
          ? com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.getDefaultInstance()
          : loadablePlanDetailsReply_;
    }
    /** <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code> */
    public com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReplyOrBuilder
        getLoadablePlanDetailsReplyOrBuilder() {
      return getLoadablePlanDetailsReply();
    }

    public static final int CARGOTOPPINGOFFSEQUENCES_FIELD_NUMBER = 3;
    private java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence>
        cargoToppingOffSequences_;
    /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
    public java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence>
        getCargoToppingOffSequencesList() {
      return cargoToppingOffSequences_;
    }
    /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
    public java.util.List<
            ? extends
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .CargoToppingOffSequenceOrBuilder>
        getCargoToppingOffSequencesOrBuilderList() {
      return cargoToppingOffSequences_;
    }
    /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
    public int getCargoToppingOffSequencesCount() {
      return cargoToppingOffSequences_.size();
    }
    /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
        getCargoToppingOffSequences(int index) {
      return cargoToppingOffSequences_.get(index);
    }
    /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .CargoToppingOffSequenceOrBuilder
        getCargoToppingOffSequencesOrBuilder(int index) {
      return cargoToppingOffSequences_.get(index);
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
      if (loadingInformationDetail_ != null) {
        output.writeMessage(1, getLoadingInformationDetail());
      }
      if (loadablePlanDetailsReply_ != null) {
        output.writeMessage(2, getLoadablePlanDetailsReply());
      }
      for (int i = 0; i < cargoToppingOffSequences_.size(); i++) {
        output.writeMessage(3, cargoToppingOffSequences_.get(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (loadingInformationDetail_ != null) {
        size +=
            com.google.protobuf.CodedOutputStream.computeMessageSize(
                1, getLoadingInformationDetail());
      }
      if (loadablePlanDetailsReply_ != null) {
        size +=
            com.google.protobuf.CodedOutputStream.computeMessageSize(
                2, getLoadablePlanDetailsReply());
      }
      for (int i = 0; i < cargoToppingOffSequences_.size(); i++) {
        size +=
            com.google.protobuf.CodedOutputStream.computeMessageSize(
                3, cargoToppingOffSequences_.get(i));
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
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails) obj;

      if (hasLoadingInformationDetail() != other.hasLoadingInformationDetail()) return false;
      if (hasLoadingInformationDetail()) {
        if (!getLoadingInformationDetail().equals(other.getLoadingInformationDetail()))
          return false;
      }
      if (hasLoadablePlanDetailsReply() != other.hasLoadablePlanDetailsReply()) return false;
      if (hasLoadablePlanDetailsReply()) {
        if (!getLoadablePlanDetailsReply().equals(other.getLoadablePlanDetailsReply()))
          return false;
      }
      if (!getCargoToppingOffSequencesList().equals(other.getCargoToppingOffSequencesList()))
        return false;
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
      if (hasLoadingInformationDetail()) {
        hash = (37 * hash) + LOADINGINFORMATIONDETAIL_FIELD_NUMBER;
        hash = (53 * hash) + getLoadingInformationDetail().hashCode();
      }
      if (hasLoadablePlanDetailsReply()) {
        hash = (37 * hash) + LOADABLEPLANDETAILSREPLY_FIELD_NUMBER;
        hash = (53 * hash) + getLoadablePlanDetailsReply().hashCode();
      }
      if (getCargoToppingOffSequencesCount() > 0) {
        hash = (37 * hash) + CARGOTOPPINGOFFSEQUENCES_FIELD_NUMBER;
        hash = (53 * hash) + getCargoToppingOffSequencesList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
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
    /** Protobuf type {@code LoadingPlanSyncDetails} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadingPlanSyncDetails)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetailsOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanSyncDetails_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanSyncDetails_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
                    .class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
                    .Builder.class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
          getCargoToppingOffSequencesFieldBuilder();
        }
      }

      @java.lang.Override
      public Builder clear() {
        super.clear();
        if (loadingInformationDetailBuilder_ == null) {
          loadingInformationDetail_ = null;
        } else {
          loadingInformationDetail_ = null;
          loadingInformationDetailBuilder_ = null;
        }
        if (loadablePlanDetailsReplyBuilder_ == null) {
          loadablePlanDetailsReply_ = null;
        } else {
          loadablePlanDetailsReply_ = null;
          loadablePlanDetailsReplyBuilder_ = null;
        }
        if (cargoToppingOffSequencesBuilder_ == null) {
          cargoToppingOffSequences_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          cargoToppingOffSequencesBuilder_.clear();
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanSyncDetails_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
          build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails result =
            new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails(
                this);
        int from_bitField0_ = bitField0_;
        if (loadingInformationDetailBuilder_ == null) {
          result.loadingInformationDetail_ = loadingInformationDetail_;
        } else {
          result.loadingInformationDetail_ = loadingInformationDetailBuilder_.build();
        }
        if (loadablePlanDetailsReplyBuilder_ == null) {
          result.loadablePlanDetailsReply_ = loadablePlanDetailsReply_;
        } else {
          result.loadablePlanDetailsReply_ = loadablePlanDetailsReplyBuilder_.build();
        }
        if (cargoToppingOffSequencesBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            cargoToppingOffSequences_ =
                java.util.Collections.unmodifiableList(cargoToppingOffSequences_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.cargoToppingOffSequences_ = cargoToppingOffSequences_;
        } else {
          result.cargoToppingOffSequences_ = cargoToppingOffSequencesBuilder_.build();
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
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
                .getDefaultInstance()) return this;
        if (other.hasLoadingInformationDetail()) {
          mergeLoadingInformationDetail(other.getLoadingInformationDetail());
        }
        if (other.hasLoadablePlanDetailsReply()) {
          mergeLoadablePlanDetailsReply(other.getLoadablePlanDetailsReply());
        }
        if (cargoToppingOffSequencesBuilder_ == null) {
          if (!other.cargoToppingOffSequences_.isEmpty()) {
            if (cargoToppingOffSequences_.isEmpty()) {
              cargoToppingOffSequences_ = other.cargoToppingOffSequences_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureCargoToppingOffSequencesIsMutable();
              cargoToppingOffSequences_.addAll(other.cargoToppingOffSequences_);
            }
            onChanged();
          }
        } else {
          if (!other.cargoToppingOffSequences_.isEmpty()) {
            if (cargoToppingOffSequencesBuilder_.isEmpty()) {
              cargoToppingOffSequencesBuilder_.dispose();
              cargoToppingOffSequencesBuilder_ = null;
              cargoToppingOffSequences_ = other.cargoToppingOffSequences_;
              bitField0_ = (bitField0_ & ~0x00000001);
              cargoToppingOffSequencesBuilder_ =
                  com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                      ? getCargoToppingOffSequencesFieldBuilder()
                      : null;
            } else {
              cargoToppingOffSequencesBuilder_.addAllMessages(other.cargoToppingOffSequences_);
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails)
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

      private com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
          loadingInformationDetail_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                  .Builder,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingInformationDetailOrBuilder>
          loadingInformationDetailBuilder_;
      /**
       * <code>.LoadingInformationDetail loadingInformationDetail = 1;</code>
       *
       * @return Whether the loadingInformationDetail field is set.
       */
      public boolean hasLoadingInformationDetail() {
        return loadingInformationDetailBuilder_ != null || loadingInformationDetail_ != null;
      }
      /**
       * <code>.LoadingInformationDetail loadingInformationDetail = 1;</code>
       *
       * @return The loadingInformationDetail.
       */
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
          getLoadingInformationDetail() {
        if (loadingInformationDetailBuilder_ == null) {
          return loadingInformationDetail_ == null
              ? com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                  .getDefaultInstance()
              : loadingInformationDetail_;
        } else {
          return loadingInformationDetailBuilder_.getMessage();
        }
      }
      /** <code>.LoadingInformationDetail loadingInformationDetail = 1;</code> */
      public Builder setLoadingInformationDetail(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
              value) {
        if (loadingInformationDetailBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          loadingInformationDetail_ = value;
          onChanged();
        } else {
          loadingInformationDetailBuilder_.setMessage(value);
        }

        return this;
      }
      /** <code>.LoadingInformationDetail loadingInformationDetail = 1;</code> */
      public Builder setLoadingInformationDetail(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail.Builder
              builderForValue) {
        if (loadingInformationDetailBuilder_ == null) {
          loadingInformationDetail_ = builderForValue.build();
          onChanged();
        } else {
          loadingInformationDetailBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /** <code>.LoadingInformationDetail loadingInformationDetail = 1;</code> */
      public Builder mergeLoadingInformationDetail(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
              value) {
        if (loadingInformationDetailBuilder_ == null) {
          if (loadingInformationDetail_ != null) {
            loadingInformationDetail_ =
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                    .newBuilder(loadingInformationDetail_)
                    .mergeFrom(value)
                    .buildPartial();
          } else {
            loadingInformationDetail_ = value;
          }
          onChanged();
        } else {
          loadingInformationDetailBuilder_.mergeFrom(value);
        }

        return this;
      }
      /** <code>.LoadingInformationDetail loadingInformationDetail = 1;</code> */
      public Builder clearLoadingInformationDetail() {
        if (loadingInformationDetailBuilder_ == null) {
          loadingInformationDetail_ = null;
          onChanged();
        } else {
          loadingInformationDetail_ = null;
          loadingInformationDetailBuilder_ = null;
        }

        return this;
      }
      /** <code>.LoadingInformationDetail loadingInformationDetail = 1;</code> */
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
              .Builder
          getLoadingInformationDetailBuilder() {

        onChanged();
        return getLoadingInformationDetailFieldBuilder().getBuilder();
      }
      /** <code>.LoadingInformationDetail loadingInformationDetail = 1;</code> */
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationDetailOrBuilder
          getLoadingInformationDetailOrBuilder() {
        if (loadingInformationDetailBuilder_ != null) {
          return loadingInformationDetailBuilder_.getMessageOrBuilder();
        } else {
          return loadingInformationDetail_ == null
              ? com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                  .getDefaultInstance()
              : loadingInformationDetail_;
        }
      }
      /** <code>.LoadingInformationDetail loadingInformationDetail = 1;</code> */
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                  .Builder,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingInformationDetailOrBuilder>
          getLoadingInformationDetailFieldBuilder() {
        if (loadingInformationDetailBuilder_ == null) {
          loadingInformationDetailBuilder_ =
              new com.google.protobuf.SingleFieldBuilderV3<
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .LoadingInformationDetail,
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail
                      .Builder,
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .LoadingInformationDetailOrBuilder>(
                  getLoadingInformationDetail(), getParentForChildren(), isClean());
          loadingInformationDetail_ = null;
        }
        return loadingInformationDetailBuilder_;
      }

      private com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply
          loadablePlanDetailsReply_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply,
              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder,
              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReplyOrBuilder>
          loadablePlanDetailsReplyBuilder_;
      /**
       * <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code>
       *
       * @return Whether the loadablePlanDetailsReply field is set.
       */
      public boolean hasLoadablePlanDetailsReply() {
        return loadablePlanDetailsReplyBuilder_ != null || loadablePlanDetailsReply_ != null;
      }
      /**
       * <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code>
       *
       * @return The loadablePlanDetailsReply.
       */
      public com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply
          getLoadablePlanDetailsReply() {
        if (loadablePlanDetailsReplyBuilder_ == null) {
          return loadablePlanDetailsReply_ == null
              ? com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply
                  .getDefaultInstance()
              : loadablePlanDetailsReply_;
        } else {
          return loadablePlanDetailsReplyBuilder_.getMessage();
        }
      }
      /** <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code> */
      public Builder setLoadablePlanDetailsReply(
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply value) {
        if (loadablePlanDetailsReplyBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          loadablePlanDetailsReply_ = value;
          onChanged();
        } else {
          loadablePlanDetailsReplyBuilder_.setMessage(value);
        }

        return this;
      }
      /** <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code> */
      public Builder setLoadablePlanDetailsReply(
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder
              builderForValue) {
        if (loadablePlanDetailsReplyBuilder_ == null) {
          loadablePlanDetailsReply_ = builderForValue.build();
          onChanged();
        } else {
          loadablePlanDetailsReplyBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /** <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code> */
      public Builder mergeLoadablePlanDetailsReply(
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply value) {
        if (loadablePlanDetailsReplyBuilder_ == null) {
          if (loadablePlanDetailsReply_ != null) {
            loadablePlanDetailsReply_ =
                com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.newBuilder(
                        loadablePlanDetailsReply_)
                    .mergeFrom(value)
                    .buildPartial();
          } else {
            loadablePlanDetailsReply_ = value;
          }
          onChanged();
        } else {
          loadablePlanDetailsReplyBuilder_.mergeFrom(value);
        }

        return this;
      }
      /** <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code> */
      public Builder clearLoadablePlanDetailsReply() {
        if (loadablePlanDetailsReplyBuilder_ == null) {
          loadablePlanDetailsReply_ = null;
          onChanged();
        } else {
          loadablePlanDetailsReply_ = null;
          loadablePlanDetailsReplyBuilder_ = null;
        }

        return this;
      }
      /** <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code> */
      public com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder
          getLoadablePlanDetailsReplyBuilder() {

        onChanged();
        return getLoadablePlanDetailsReplyFieldBuilder().getBuilder();
      }
      /** <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code> */
      public com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReplyOrBuilder
          getLoadablePlanDetailsReplyOrBuilder() {
        if (loadablePlanDetailsReplyBuilder_ != null) {
          return loadablePlanDetailsReplyBuilder_.getMessageOrBuilder();
        } else {
          return loadablePlanDetailsReply_ == null
              ? com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply
                  .getDefaultInstance()
              : loadablePlanDetailsReply_;
        }
      }
      /** <code>.LoadablePlanDetailsReply loadablePlanDetailsReply = 2;</code> */
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply,
              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder,
              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReplyOrBuilder>
          getLoadablePlanDetailsReplyFieldBuilder() {
        if (loadablePlanDetailsReplyBuilder_ == null) {
          loadablePlanDetailsReplyBuilder_ =
              new com.google.protobuf.SingleFieldBuilderV3<
                  com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply,
                  com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder,
                  com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReplyOrBuilder>(
                  getLoadablePlanDetailsReply(), getParentForChildren(), isClean());
          loadablePlanDetailsReply_ = null;
        }
        return loadablePlanDetailsReplyBuilder_;
      }

      private java.util.List<
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence>
          cargoToppingOffSequences_ = java.util.Collections.emptyList();

      private void ensureCargoToppingOffSequencesIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          cargoToppingOffSequences_ =
              new java.util.ArrayList<
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .CargoToppingOffSequence>(cargoToppingOffSequences_);
          bitField0_ |= 0x00000001;
        }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                  .Builder,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .CargoToppingOffSequenceOrBuilder>
          cargoToppingOffSequencesBuilder_;

      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public java.util.List<
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence>
          getCargoToppingOffSequencesList() {
        if (cargoToppingOffSequencesBuilder_ == null) {
          return java.util.Collections.unmodifiableList(cargoToppingOffSequences_);
        } else {
          return cargoToppingOffSequencesBuilder_.getMessageList();
        }
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public int getCargoToppingOffSequencesCount() {
        if (cargoToppingOffSequencesBuilder_ == null) {
          return cargoToppingOffSequences_.size();
        } else {
          return cargoToppingOffSequencesBuilder_.getCount();
        }
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
          getCargoToppingOffSequences(int index) {
        if (cargoToppingOffSequencesBuilder_ == null) {
          return cargoToppingOffSequences_.get(index);
        } else {
          return cargoToppingOffSequencesBuilder_.getMessage(index);
        }
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public Builder setCargoToppingOffSequences(
          int index,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence value) {
        if (cargoToppingOffSequencesBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureCargoToppingOffSequencesIsMutable();
          cargoToppingOffSequences_.set(index, value);
          onChanged();
        } else {
          cargoToppingOffSequencesBuilder_.setMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public Builder setCargoToppingOffSequences(
          int index,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence.Builder
              builderForValue) {
        if (cargoToppingOffSequencesBuilder_ == null) {
          ensureCargoToppingOffSequencesIsMutable();
          cargoToppingOffSequences_.set(index, builderForValue.build());
          onChanged();
        } else {
          cargoToppingOffSequencesBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public Builder addCargoToppingOffSequences(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence value) {
        if (cargoToppingOffSequencesBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureCargoToppingOffSequencesIsMutable();
          cargoToppingOffSequences_.add(value);
          onChanged();
        } else {
          cargoToppingOffSequencesBuilder_.addMessage(value);
        }
        return this;
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public Builder addCargoToppingOffSequences(
          int index,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence value) {
        if (cargoToppingOffSequencesBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureCargoToppingOffSequencesIsMutable();
          cargoToppingOffSequences_.add(index, value);
          onChanged();
        } else {
          cargoToppingOffSequencesBuilder_.addMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public Builder addCargoToppingOffSequences(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence.Builder
              builderForValue) {
        if (cargoToppingOffSequencesBuilder_ == null) {
          ensureCargoToppingOffSequencesIsMutable();
          cargoToppingOffSequences_.add(builderForValue.build());
          onChanged();
        } else {
          cargoToppingOffSequencesBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public Builder addCargoToppingOffSequences(
          int index,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence.Builder
              builderForValue) {
        if (cargoToppingOffSequencesBuilder_ == null) {
          ensureCargoToppingOffSequencesIsMutable();
          cargoToppingOffSequences_.add(index, builderForValue.build());
          onChanged();
        } else {
          cargoToppingOffSequencesBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public Builder addAllCargoToppingOffSequences(
          java.lang.Iterable<
                  ? extends
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .CargoToppingOffSequence>
              values) {
        if (cargoToppingOffSequencesBuilder_ == null) {
          ensureCargoToppingOffSequencesIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(values, cargoToppingOffSequences_);
          onChanged();
        } else {
          cargoToppingOffSequencesBuilder_.addAllMessages(values);
        }
        return this;
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public Builder clearCargoToppingOffSequences() {
        if (cargoToppingOffSequencesBuilder_ == null) {
          cargoToppingOffSequences_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          cargoToppingOffSequencesBuilder_.clear();
        }
        return this;
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public Builder removeCargoToppingOffSequences(int index) {
        if (cargoToppingOffSequencesBuilder_ == null) {
          ensureCargoToppingOffSequencesIsMutable();
          cargoToppingOffSequences_.remove(index);
          onChanged();
        } else {
          cargoToppingOffSequencesBuilder_.remove(index);
        }
        return this;
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
              .Builder
          getCargoToppingOffSequencesBuilder(int index) {
        return getCargoToppingOffSequencesFieldBuilder().getBuilder(index);
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .CargoToppingOffSequenceOrBuilder
          getCargoToppingOffSequencesOrBuilder(int index) {
        if (cargoToppingOffSequencesBuilder_ == null) {
          return cargoToppingOffSequences_.get(index);
        } else {
          return cargoToppingOffSequencesBuilder_.getMessageOrBuilder(index);
        }
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public java.util.List<
              ? extends
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .CargoToppingOffSequenceOrBuilder>
          getCargoToppingOffSequencesOrBuilderList() {
        if (cargoToppingOffSequencesBuilder_ != null) {
          return cargoToppingOffSequencesBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(cargoToppingOffSequences_);
        }
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
              .Builder
          addCargoToppingOffSequencesBuilder() {
        return getCargoToppingOffSequencesFieldBuilder()
            .addBuilder(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                    .getDefaultInstance());
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
              .Builder
          addCargoToppingOffSequencesBuilder(int index) {
        return getCargoToppingOffSequencesFieldBuilder()
            .addBuilder(
                index,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                    .getDefaultInstance());
      }
      /** <code>repeated .CargoToppingOffSequence cargoToppingOffSequences = 3;</code> */
      public java.util.List<
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                  .Builder>
          getCargoToppingOffSequencesBuilderList() {
        return getCargoToppingOffSequencesFieldBuilder().getBuilderList();
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                  .Builder,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .CargoToppingOffSequenceOrBuilder>
          getCargoToppingOffSequencesFieldBuilder() {
        if (cargoToppingOffSequencesBuilder_ == null) {
          cargoToppingOffSequencesBuilder_ =
              new com.google.protobuf.RepeatedFieldBuilderV3<
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence,
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence
                      .Builder,
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .CargoToppingOffSequenceOrBuilder>(
                  cargoToppingOffSequences_,
                  ((bitField0_ & 0x00000001) != 0),
                  getParentForChildren(),
                  isClean());
          cargoToppingOffSequences_ = null;
        }
        return cargoToppingOffSequencesBuilder_;
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

      // @@protoc_insertion_point(builder_scope:LoadingPlanSyncDetails)
    }

    // @@protoc_insertion_point(class_scope:LoadingPlanSyncDetails)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingPlanSyncDetails
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadingPlanSyncDetails> PARSER =
        new com.google.protobuf.AbstractParser<LoadingPlanSyncDetails>() {
          @java.lang.Override
          public LoadingPlanSyncDetails parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadingPlanSyncDetails(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadingPlanSyncDetails> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadingPlanSyncDetails> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadingPlanSyncReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadingPlanSyncReply)
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
  /** Protobuf type {@code LoadingPlanSyncReply} */
  public static final class LoadingPlanSyncReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadingPlanSyncReply)
      LoadingPlanSyncReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadingPlanSyncReply.newBuilder() to construct.
    private LoadingPlanSyncReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadingPlanSyncReply() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadingPlanSyncReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadingPlanSyncReply(
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
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadingPlanSyncReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .internal_static_LoadingPlanSyncReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply.class,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply.Builder
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
      if (!(obj
          instanceof
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply other =
          (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply) obj;

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

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseFrom(java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseFrom(
            java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseFrom(com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseDelimitedFrom(
            java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply prototype) {
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
    /** Protobuf type {@code LoadingPlanSyncReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadingPlanSyncReply)
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanSyncReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanSyncReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
                    .class,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
                    .Builder.class);
      }

      // Construct using
      // com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply.newBuilder()
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
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .internal_static_LoadingPlanSyncReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
            .getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
          build() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply result =
            buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
          buildPartial() {
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply result =
            new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply(
                this);
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
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply) {
          return mergeFrom(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply)
                  other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply other) {
        if (other
            == com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
                .getDefaultInstance()) return this;
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
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
            parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply)
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

      // @@protoc_insertion_point(builder_scope:LoadingPlanSyncReply)
    }

    // @@protoc_insertion_point(class_scope:LoadingPlanSyncReply)
    private static final com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingPlanSyncReply
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE =
          new com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply();
    }

    public static com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadingPlanSyncReply> PARSER =
        new com.google.protobuf.AbstractParser<LoadingPlanSyncReply>() {
          @java.lang.Override
          public LoadingPlanSyncReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadingPlanSyncReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadingPlanSyncReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadingPlanSyncReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
        getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadingPlanRequest_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadingPlanRequest_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadingPlanResponse_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadingPlanResponse_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadingInformationDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadingInformationDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadablePlanBallastDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadablePlanBallastDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadablePlanCommingleDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadablePlanCommingleDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadablePlanQuantity_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadablePlanQuantity_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadablePlanStowageDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadablePlanStowageDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadableQuantity_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadableQuantity_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoToppingOffSequence_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoToppingOffSequence_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadingPlanSyncDetails_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadingPlanSyncDetails_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadingPlanSyncReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadingPlanSyncReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n&loading_plan/loading_plan_models.proto"
          + "\032\014common.proto\032\024loadable_study.proto\"K\n\022"
          + "LoadingPlanRequest\022\021\n\tcompanyId\030\001 \001(\003\022\020\n"
          + "\010vesselId\030\002 \001(\003\022\020\n\010voyageId\030\003 \001(\003\">\n\023Loa"
          + "dingPlanResponse\022\'\n\016responseStatus\030\001 \001(\013"
          + "2\017.ResponseStatus\"r\n\030LoadingInformationD"
          + "etail\022\031\n\021loadablePatternId\030\001 \001(\003\022\016\n\006port"
          + "Id\030\002 \001(\003\022\031\n\021synopticalTableId\030\003 \001(\003\022\020\n\010v"
          + "esselId\030\004 \001(\003\"\314\002\n\031LoadablePlanBallastDet"
          + "ail\022\n\n\002id\030\001 \001(\003\022\021\n\tcolorCode\030\002 \001(\t\022\026\n\016co"
          + "rrectedLevel\030\003 \001(\t\022\030\n\020correctionFactor\030\004"
          + " \001(\t\022\022\n\ncubicMeter\030\005 \001(\t\022\017\n\007inertia\030\006 \001("
          + "\t\022\020\n\010isActive\030\007 \001(\010\022\013\n\003lcg\030\010 \001(\t\022\031\n\021load"
          + "ablePatternId\030\t \001(\003\022\021\n\tmetricTon\030\n \001(\t\022\022"
          + "\n\npercentage\030\013 \001(\t\022\020\n\010rdgLevel\030\014 \001(\t\022\n\n\002"
          + "sg\030\r \001(\t\022\016\n\006tankId\030\016 \001(\003\022\020\n\010tankName\030\017 \001"
          + "(\t\022\013\n\003tcg\030\020 \001(\t\022\013\n\003vcg\030\021 \001(\t\"\330\005\n\033Loadabl"
          + "ePlanCommingleDetail\022\n\n\002id\030\001 \001(\003\022\013\n\003api\030"
          + "\002 \001(\t\022\032\n\022cargo1Abbreviation\030\003 \001(\t\022\025\n\rcar"
          + "go1Bbls60f\030\004 \001(\t\022\025\n\rcargo1BblsDbs\030\005 \001(\t\022"
          + "\020\n\010cargo1Kl\030\006 \001(\t\022\020\n\010cargo1Lt\030\007 \001(\t\022\020\n\010c"
          + "argo1Mt\030\010 \001(\t\022\030\n\020cargo1Percentage\030\t \001(\t\022"
          + "\032\n\022cargo2Abbreviation\030\n \001(\t\022\025\n\rcargo2Bbl"
          + "s60f\030\013 \001(\t\022\025\n\rcargo2BblsDbs\030\014 \001(\t\022\020\n\010car"
          + "go2Kl\030\r \001(\t\022\020\n\010cargo2Lt\030\016 \001(\t\022\020\n\010cargo2M"
          + "t\030\017 \001(\t\022\030\n\020cargo2Percentage\030\020 \001(\t\022\027\n\017cor"
          + "rectedUllage\030\021 \001(\t\022\030\n\020correctionFactor\030\022"
          + " \001(\t\022\024\n\014fillingRatio\030\023 \001(\t\022\r\n\005grade\030\024 \001("
          + "\t\022\020\n\010isActive\030\025 \001(\010\022\031\n\021loadablePatternId"
          + "\030\026 \001(\003\022\026\n\016loadablePlanId\030\027 \001(\003\022\024\n\014loadin"
          + "gOrder\030\030 \001(\005\022\025\n\rorderQuantity\030\031 \001(\t\022\020\n\010p"
          + "riority\030\032 \001(\005\022\020\n\010quantity\030\033 \001(\t\022\021\n\trdgUl"
          + "lage\030\034 \001(\t\022\024\n\014slopQuantity\030\035 \001(\t\022\016\n\006tank"
          + "Id\030\036 \001(\003\022\020\n\010tankName\030\037 \001(\t\022\023\n\013temperatur"
          + "e\030  \001(\t\022\036\n\026timeRequiredForLoading\030! \001(\t\""
          + "\221\005\n\024LoadablePlanQuantity\022\031\n\021cargoAbbrevi"
          + "ation\030\001 \001(\t\022\022\n\ncargoColor\030\002 \001(\t\022\031\n\021cargo"
          + "NominationId\030\003 \001(\003\022\017\n\007cargoId\030\004 \001(\003\022\027\n\017d"
          + "ifferenceColor\030\005 \001(\t\022\034\n\024differencePercen"
          + "tage\030\006 \001(\t\022\024\n\014estimatedApi\030\007 \001(\t\022\034\n\024esti"
          + "matedTemperature\030\010 \001(\t\022\r\n\005grade\030\t \001(\t\022\n\n"
          + "\002id\030\n \001(\003\022\020\n\010isActive\030\013 \001(\010\022\027\n\017loadableB"
          + "bls60f\030\014 \001(\t\022\027\n\017loadableBblsDbs\030\r \001(\t\022\022\n"
          + "\nloadableKl\030\016 \001(\t\022\022\n\nloadableLt\030\017 \001(\t\022\022\n"
          + "\nloadableMt\030\020 \001(\t\022\031\n\021loadablePatternId\030\021"
          + " \001(\003\022\026\n\016loadablePlanId\030\022 \001(\003\022\030\n\020loadable"
          + "Quantity\030\023 \001(\t\022\024\n\014loadingOrder\030\024 \001(\005\022\024\n\014"
          + "maxTolerence\030\025 \001(\t\022\024\n\014minTolerence\030\026 \001(\t"
          + "\022\024\n\014orderBbls60f\030\027 \001(\t\022\024\n\014orderBblsDbs\030\030"
          + " \001(\t\022\025\n\rorderQuantity\030\031 \001(\t\022\020\n\010priority\030"
          + "\032 \001(\005\022\024\n\014slopQuantity\030\033 \001(\t\022\036\n\026timeRequi"
          + "redForLoading\030\034 \001(\t\"\250\003\n\031LoadablePlanStow"
          + "ageDetail\022\024\n\014abbreviation\030\001 \001(\t\022\013\n\003api\030\002"
          + " \001(\t\022\031\n\021cargoNominationId\030\003 \001(\003\022\017\n\007cargo"
          + "Id\030\004 \001(\003\022\021\n\tcolorCode\030\005 \001(\t\022\027\n\017corrected"
          + "Ullage\030\006 \001(\t\022\030\n\020correctionFactor\030\007 \001(\t\022\031"
          + "\n\021fillingPercentage\030\010 \001(\t\022\n\n\002id\030\t \001(\003\022\020\n"
          + "\010isActive\030\n \001(\010\022\031\n\021loadablePatternId\030\013 \001"
          + "(\003\022\027\n\017observedBarrels\030\014 \001(\t\022\033\n\023observedB"
          + "arrelsAt60\030\r \001(\t\022\022\n\nobservedM3\030\016 \001(\t\022\021\n\t"
          + "rdgUllage\030\017 \001(\t\022\016\n\006tankId\030\020 \001(\003\022\020\n\010tankn"
          + "ame\030\021 \001(\t\022\023\n\013temperature\030\022 \001(\t\022\016\n\006weight"
          + "\030\023 \001(\t\"\355\005\n\020LoadableQuantity\022\017\n\007ballast\030\001"
          + " \001(\t\022\032\n\022boilerWaterOnBoard\030\002 \001(\t\022\020\n\010cons"
          + "tant\030\003 \001(\t\022\022\n\ndeadWeight\030\004 \001(\t\022&\n\036displa"
          + "cementAtDraftRestriction\030\005 \001(\t\022\034\n\024distan"
          + "ceFromLastPort\030\006 \001(\t\022\030\n\020draftRestriction"
          + "\030\007 \001(\t\022\032\n\022estimatedDOOnBoard\030\010 \001(\t\022\032\n\022es"
          + "timatedFOOnBoard\030\t \001(\t\022\032\n\022estimatedFWOnB"
          + "oard\030\n \001(\t\022\030\n\020estimatedSagging\030\013 \001(\t\022\033\n\023"
          + "estimatedSeaDensity\030\014 \001(\t\022\031\n\021foConsumpti"
          + "onInSZ\030\r \001(\t\022\033\n\023foConsumptionPerDay\030\016 \001("
          + "\t\022\n\n\002id\030\017 \001(\003\022\020\n\010isActive\030\020 \001(\010\022\023\n\013light"
          + "Weight\030\021 \001(\t\022#\n\033loadableStudyPortRotatio"
          + "nId\030\022 \001(\003\022\027\n\017loadableStudyId\030\023 \001(\003\022\022\n\not"
          + "herIfAny\030\024 \001(\t\022\016\n\006portId\030\025 \001(\t\022\023\n\013runnin"
          + "gDays\030\026 \001(\t\022\024\n\014runningHours\030\027 \001(\t\022\030\n\020sag"
          + "gingDeduction\030\030 \001(\t\022\024\n\014sgCorrection\030\031 \001("
          + "\t\022\020\n\010subTotal\030\032 \001(\t\022\032\n\022totalFoConsumptio"
          + "n\030\033 \001(\t\022\025\n\rtotalQuantity\030\034 \001(\t\022\022\n\ntpcatD"
          + "raft\030\035 \001(\t\022\032\n\022vesselAverageSpeed\030\036 \001(\t\"\325"
          + "\001\n\027CargoToppingOffSequence\022\020\n\010cargoXId\030\001"
          + " \001(\003\022\024\n\014fillingRatio\030\002 \001(\t\022\020\n\010isActive\030\003"
          + " \001(\010\022\023\n\013orderNumber\030\004 \001(\005\022\017\n\007remarks\030\005 \001"
          + "(\t\022\017\n\007tankXId\030\006 \001(\003\022\016\n\006ullage\030\007 \001(\t\022\016\n\006v"
          + "olume\030\010 \001(\t\022\016\n\006weight\030\t \001(\t\022\031\n\021loadableP"
          + "atternId\030\n \001(\003\"\316\001\n\026LoadingPlanSyncDetail"
          + "s\022;\n\030loadingInformationDetail\030\001 \001(\0132\031.Lo"
          + "adingInformationDetail\022;\n\030loadablePlanDe"
          + "tailsReply\030\002 \001(\0132\031.LoadablePlanDetailsRe"
          + "ply\022:\n\030cargoToppingOffSequences\030\003 \003(\0132\030."
          + "CargoToppingOffSequence\"?\n\024LoadingPlanSy"
          + "ncReply\022\'\n\016responseStatus\030\001 \001(\0132\017.Respon"
          + "seStatusB+\n\'com.cpdss.common.generated.l"
          + "oading_planP\000b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
              com.cpdss.common.generated.LoadableStudy.getDescriptor(),
            });
    internal_static_LoadingPlanRequest_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_LoadingPlanRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadingPlanRequest_descriptor,
            new java.lang.String[] {
              "CompanyId", "VesselId", "VoyageId",
            });
    internal_static_LoadingPlanResponse_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_LoadingPlanResponse_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadingPlanResponse_descriptor,
            new java.lang.String[] {
              "ResponseStatus",
            });
    internal_static_LoadingInformationDetail_descriptor = getDescriptor().getMessageTypes().get(2);
    internal_static_LoadingInformationDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadingInformationDetail_descriptor,
            new java.lang.String[] {
              "LoadablePatternId", "PortId", "SynopticalTableId", "VesselId",
            });
    internal_static_LoadablePlanBallastDetail_descriptor = getDescriptor().getMessageTypes().get(3);
    internal_static_LoadablePlanBallastDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadablePlanBallastDetail_descriptor,
            new java.lang.String[] {
              "Id",
              "ColorCode",
              "CorrectedLevel",
              "CorrectionFactor",
              "CubicMeter",
              "Inertia",
              "IsActive",
              "Lcg",
              "LoadablePatternId",
              "MetricTon",
              "Percentage",
              "RdgLevel",
              "Sg",
              "TankId",
              "TankName",
              "Tcg",
              "Vcg",
            });
    internal_static_LoadablePlanCommingleDetail_descriptor =
        getDescriptor().getMessageTypes().get(4);
    internal_static_LoadablePlanCommingleDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadablePlanCommingleDetail_descriptor,
            new java.lang.String[] {
              "Id",
              "Api",
              "Cargo1Abbreviation",
              "Cargo1Bbls60F",
              "Cargo1BblsDbs",
              "Cargo1Kl",
              "Cargo1Lt",
              "Cargo1Mt",
              "Cargo1Percentage",
              "Cargo2Abbreviation",
              "Cargo2Bbls60F",
              "Cargo2BblsDbs",
              "Cargo2Kl",
              "Cargo2Lt",
              "Cargo2Mt",
              "Cargo2Percentage",
              "CorrectedUllage",
              "CorrectionFactor",
              "FillingRatio",
              "Grade",
              "IsActive",
              "LoadablePatternId",
              "LoadablePlanId",
              "LoadingOrder",
              "OrderQuantity",
              "Priority",
              "Quantity",
              "RdgUllage",
              "SlopQuantity",
              "TankId",
              "TankName",
              "Temperature",
              "TimeRequiredForLoading",
            });
    internal_static_LoadablePlanQuantity_descriptor = getDescriptor().getMessageTypes().get(5);
    internal_static_LoadablePlanQuantity_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadablePlanQuantity_descriptor,
            new java.lang.String[] {
              "CargoAbbreviation",
              "CargoColor",
              "CargoNominationId",
              "CargoId",
              "DifferenceColor",
              "DifferencePercentage",
              "EstimatedApi",
              "EstimatedTemperature",
              "Grade",
              "Id",
              "IsActive",
              "LoadableBbls60F",
              "LoadableBblsDbs",
              "LoadableKl",
              "LoadableLt",
              "LoadableMt",
              "LoadablePatternId",
              "LoadablePlanId",
              "LoadableQuantity",
              "LoadingOrder",
              "MaxTolerence",
              "MinTolerence",
              "OrderBbls60F",
              "OrderBblsDbs",
              "OrderQuantity",
              "Priority",
              "SlopQuantity",
              "TimeRequiredForLoading",
            });
    internal_static_LoadablePlanStowageDetail_descriptor = getDescriptor().getMessageTypes().get(6);
    internal_static_LoadablePlanStowageDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadablePlanStowageDetail_descriptor,
            new java.lang.String[] {
              "Abbreviation",
              "Api",
              "CargoNominationId",
              "CargoId",
              "ColorCode",
              "CorrectedUllage",
              "CorrectionFactor",
              "FillingPercentage",
              "Id",
              "IsActive",
              "LoadablePatternId",
              "ObservedBarrels",
              "ObservedBarrelsAt60",
              "ObservedM3",
              "RdgUllage",
              "TankId",
              "Tankname",
              "Temperature",
              "Weight",
            });
    internal_static_LoadableQuantity_descriptor = getDescriptor().getMessageTypes().get(7);
    internal_static_LoadableQuantity_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadableQuantity_descriptor,
            new java.lang.String[] {
              "Ballast",
              "BoilerWaterOnBoard",
              "Constant",
              "DeadWeight",
              "DisplacementAtDraftRestriction",
              "DistanceFromLastPort",
              "DraftRestriction",
              "EstimatedDOOnBoard",
              "EstimatedFOOnBoard",
              "EstimatedFWOnBoard",
              "EstimatedSagging",
              "EstimatedSeaDensity",
              "FoConsumptionInSZ",
              "FoConsumptionPerDay",
              "Id",
              "IsActive",
              "LightWeight",
              "LoadableStudyPortRotationId",
              "LoadableStudyId",
              "OtherIfAny",
              "PortId",
              "RunningDays",
              "RunningHours",
              "SaggingDeduction",
              "SgCorrection",
              "SubTotal",
              "TotalFoConsumption",
              "TotalQuantity",
              "TpcatDraft",
              "VesselAverageSpeed",
            });
    internal_static_CargoToppingOffSequence_descriptor = getDescriptor().getMessageTypes().get(8);
    internal_static_CargoToppingOffSequence_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoToppingOffSequence_descriptor,
            new java.lang.String[] {
              "CargoXId",
              "FillingRatio",
              "IsActive",
              "OrderNumber",
              "Remarks",
              "TankXId",
              "Ullage",
              "Volume",
              "Weight",
              "LoadablePatternId",
            });
    internal_static_LoadingPlanSyncDetails_descriptor = getDescriptor().getMessageTypes().get(9);
    internal_static_LoadingPlanSyncDetails_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadingPlanSyncDetails_descriptor,
            new java.lang.String[] {
              "LoadingInformationDetail", "LoadablePlanDetailsReply", "CargoToppingOffSequences",
            });
    internal_static_LoadingPlanSyncReply_descriptor = getDescriptor().getMessageTypes().get(10);
    internal_static_LoadingPlanSyncReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadingPlanSyncReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus",
            });
    com.cpdss.common.generated.Common.getDescriptor();
    com.cpdss.common.generated.LoadableStudy.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
