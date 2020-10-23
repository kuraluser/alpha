/* Licensed under Apache-2.0 */
package com.cpdss.common.generated;

public final class VesselInfo {
  private VesselInfo() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public interface VesselRequestOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:VesselRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 companyId = 1;</code>
     *
     * @return The companyId.
     */
    long getCompanyId();
  }
  /** Protobuf type {@code VesselRequest} */
  public static final class VesselRequest extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:VesselRequest)
      VesselRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use VesselRequest.newBuilder() to construct.
    private VesselRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private VesselRequest() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new VesselRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private VesselRequest(
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
      return com.cpdss.common.generated.VesselInfo.internal_static_VesselRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.VesselInfo.internal_static_VesselRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.VesselInfo.VesselRequest.class,
              com.cpdss.common.generated.VesselInfo.VesselRequest.Builder.class);
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
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof com.cpdss.common.generated.VesselInfo.VesselRequest)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.VesselInfo.VesselRequest other =
          (com.cpdss.common.generated.VesselInfo.VesselRequest) obj;

      if (getCompanyId() != other.getCompanyId()) return false;
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
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest parseFrom(
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
        com.cpdss.common.generated.VesselInfo.VesselRequest prototype) {
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
    /** Protobuf type {@code VesselRequest} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:VesselRequest)
        com.cpdss.common.generated.VesselInfo.VesselRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.VesselInfo.internal_static_VesselRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.VesselInfo
            .internal_static_VesselRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.VesselInfo.VesselRequest.class,
                com.cpdss.common.generated.VesselInfo.VesselRequest.Builder.class);
      }

      // Construct using com.cpdss.common.generated.VesselInfo.VesselRequest.newBuilder()
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

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.VesselInfo.internal_static_VesselRequest_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.VesselRequest getDefaultInstanceForType() {
        return com.cpdss.common.generated.VesselInfo.VesselRequest.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.VesselRequest build() {
        com.cpdss.common.generated.VesselInfo.VesselRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.VesselRequest buildPartial() {
        com.cpdss.common.generated.VesselInfo.VesselRequest result =
            new com.cpdss.common.generated.VesselInfo.VesselRequest(this);
        result.companyId_ = companyId_;
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
        if (other instanceof com.cpdss.common.generated.VesselInfo.VesselRequest) {
          return mergeFrom((com.cpdss.common.generated.VesselInfo.VesselRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.VesselInfo.VesselRequest other) {
        if (other == com.cpdss.common.generated.VesselInfo.VesselRequest.getDefaultInstance())
          return this;
        if (other.getCompanyId() != 0L) {
          setCompanyId(other.getCompanyId());
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
        com.cpdss.common.generated.VesselInfo.VesselRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.VesselInfo.VesselRequest) e.getUnfinishedMessage();
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

      // @@protoc_insertion_point(builder_scope:VesselRequest)
    }

    // @@protoc_insertion_point(class_scope:VesselRequest)
    private static final com.cpdss.common.generated.VesselInfo.VesselRequest DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.VesselInfo.VesselRequest();
    }

    public static com.cpdss.common.generated.VesselInfo.VesselRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<VesselRequest> PARSER =
        new com.google.protobuf.AbstractParser<VesselRequest>() {
          @java.lang.Override
          public VesselRequest parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new VesselRequest(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<VesselRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<VesselRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.VesselInfo.VesselRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadLineDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadLineDetail)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>string name = 2;</code>
     *
     * @return The name.
     */
    java.lang.String getName();
    /**
     * <code>string name = 2;</code>
     *
     * @return The bytes for name.
     */
    com.google.protobuf.ByteString getNameBytes();

    /**
     * <code>repeated string draftMarks = 3;</code>
     *
     * @return A list containing the draftMarks.
     */
    java.util.List<java.lang.String> getDraftMarksList();
    /**
     * <code>repeated string draftMarks = 3;</code>
     *
     * @return The count of draftMarks.
     */
    int getDraftMarksCount();
    /**
     * <code>repeated string draftMarks = 3;</code>
     *
     * @param index The index of the element to return.
     * @return The draftMarks at the given index.
     */
    java.lang.String getDraftMarks(int index);
    /**
     * <code>repeated string draftMarks = 3;</code>
     *
     * @param index The index of the value to return.
     * @return The bytes of the draftMarks at the given index.
     */
    com.google.protobuf.ByteString getDraftMarksBytes(int index);
  }
  /** Protobuf type {@code LoadLineDetail} */
  public static final class LoadLineDetail extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadLineDetail)
      LoadLineDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadLineDetail.newBuilder() to construct.
    private LoadLineDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadLineDetail() {
      name_ = "";
      draftMarks_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadLineDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadLineDetail(
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
                id_ = input.readInt64();
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                name_ = s;
                break;
              }
            case 26:
              {
                java.lang.String s = input.readStringRequireUtf8();
                if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                  draftMarks_ = new com.google.protobuf.LazyStringArrayList();
                  mutable_bitField0_ |= 0x00000001;
                }
                draftMarks_.add(s);
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
          draftMarks_ = draftMarks_.getUnmodifiableView();
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.VesselInfo.internal_static_LoadLineDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.VesselInfo.internal_static_LoadLineDetail_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.VesselInfo.LoadLineDetail.class,
              com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder.class);
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

    public static final int NAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object name_;
    /**
     * <code>string name = 2;</code>
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
     * <code>string name = 2;</code>
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

    public static final int DRAFTMARKS_FIELD_NUMBER = 3;
    private com.google.protobuf.LazyStringList draftMarks_;
    /**
     * <code>repeated string draftMarks = 3;</code>
     *
     * @return A list containing the draftMarks.
     */
    public com.google.protobuf.ProtocolStringList getDraftMarksList() {
      return draftMarks_;
    }
    /**
     * <code>repeated string draftMarks = 3;</code>
     *
     * @return The count of draftMarks.
     */
    public int getDraftMarksCount() {
      return draftMarks_.size();
    }
    /**
     * <code>repeated string draftMarks = 3;</code>
     *
     * @param index The index of the element to return.
     * @return The draftMarks at the given index.
     */
    public java.lang.String getDraftMarks(int index) {
      return draftMarks_.get(index);
    }
    /**
     * <code>repeated string draftMarks = 3;</code>
     *
     * @param index The index of the value to return.
     * @return The bytes of the draftMarks at the given index.
     */
    public com.google.protobuf.ByteString getDraftMarksBytes(int index) {
      return draftMarks_.getByteString(index);
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
      if (!getNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, name_);
      }
      for (int i = 0; i < draftMarks_.size(); i++) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, draftMarks_.getRaw(i));
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
      if (!getNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, name_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < draftMarks_.size(); i++) {
          dataSize += computeStringSizeNoTag(draftMarks_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getDraftMarksList().size();
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
      if (!(obj instanceof com.cpdss.common.generated.VesselInfo.LoadLineDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.VesselInfo.LoadLineDetail other =
          (com.cpdss.common.generated.VesselInfo.LoadLineDetail) obj;

      if (getId() != other.getId()) return false;
      if (!getName().equals(other.getName())) return false;
      if (!getDraftMarksList().equals(other.getDraftMarksList())) return false;
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
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      if (getDraftMarksCount() > 0) {
        hash = (37 * hash) + DRAFTMARKS_FIELD_NUMBER;
        hash = (53 * hash) + getDraftMarksList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail parseFrom(
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
        com.cpdss.common.generated.VesselInfo.LoadLineDetail prototype) {
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
    /** Protobuf type {@code LoadLineDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadLineDetail)
        com.cpdss.common.generated.VesselInfo.LoadLineDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.VesselInfo.internal_static_LoadLineDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.VesselInfo
            .internal_static_LoadLineDetail_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.VesselInfo.LoadLineDetail.class,
                com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder.class);
      }

      // Construct using com.cpdss.common.generated.VesselInfo.LoadLineDetail.newBuilder()
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

        name_ = "";

        draftMarks_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.VesselInfo.internal_static_LoadLineDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.LoadLineDetail getDefaultInstanceForType() {
        return com.cpdss.common.generated.VesselInfo.LoadLineDetail.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.LoadLineDetail build() {
        com.cpdss.common.generated.VesselInfo.LoadLineDetail result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.LoadLineDetail buildPartial() {
        com.cpdss.common.generated.VesselInfo.LoadLineDetail result =
            new com.cpdss.common.generated.VesselInfo.LoadLineDetail(this);
        int from_bitField0_ = bitField0_;
        result.id_ = id_;
        result.name_ = name_;
        if (((bitField0_ & 0x00000001) != 0)) {
          draftMarks_ = draftMarks_.getUnmodifiableView();
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.draftMarks_ = draftMarks_;
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
        if (other instanceof com.cpdss.common.generated.VesselInfo.LoadLineDetail) {
          return mergeFrom((com.cpdss.common.generated.VesselInfo.LoadLineDetail) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.VesselInfo.LoadLineDetail other) {
        if (other == com.cpdss.common.generated.VesselInfo.LoadLineDetail.getDefaultInstance())
          return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
          onChanged();
        }
        if (!other.draftMarks_.isEmpty()) {
          if (draftMarks_.isEmpty()) {
            draftMarks_ = other.draftMarks_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureDraftMarksIsMutable();
            draftMarks_.addAll(other.draftMarks_);
          }
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
        com.cpdss.common.generated.VesselInfo.LoadLineDetail parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.VesselInfo.LoadLineDetail) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int bitField0_;

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

      private java.lang.Object name_ = "";
      /**
       * <code>string name = 2;</code>
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
       * <code>string name = 2;</code>
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
       * <code>string name = 2;</code>
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
       * <code>string name = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearName() {

        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>string name = 2;</code>
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

      private com.google.protobuf.LazyStringList draftMarks_ =
          com.google.protobuf.LazyStringArrayList.EMPTY;

      private void ensureDraftMarksIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          draftMarks_ = new com.google.protobuf.LazyStringArrayList(draftMarks_);
          bitField0_ |= 0x00000001;
        }
      }
      /**
       * <code>repeated string draftMarks = 3;</code>
       *
       * @return A list containing the draftMarks.
       */
      public com.google.protobuf.ProtocolStringList getDraftMarksList() {
        return draftMarks_.getUnmodifiableView();
      }
      /**
       * <code>repeated string draftMarks = 3;</code>
       *
       * @return The count of draftMarks.
       */
      public int getDraftMarksCount() {
        return draftMarks_.size();
      }
      /**
       * <code>repeated string draftMarks = 3;</code>
       *
       * @param index The index of the element to return.
       * @return The draftMarks at the given index.
       */
      public java.lang.String getDraftMarks(int index) {
        return draftMarks_.get(index);
      }
      /**
       * <code>repeated string draftMarks = 3;</code>
       *
       * @param index The index of the value to return.
       * @return The bytes of the draftMarks at the given index.
       */
      public com.google.protobuf.ByteString getDraftMarksBytes(int index) {
        return draftMarks_.getByteString(index);
      }
      /**
       * <code>repeated string draftMarks = 3;</code>
       *
       * @param index The index to set the value at.
       * @param value The draftMarks to set.
       * @return This builder for chaining.
       */
      public Builder setDraftMarks(int index, java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDraftMarksIsMutable();
        draftMarks_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string draftMarks = 3;</code>
       *
       * @param value The draftMarks to add.
       * @return This builder for chaining.
       */
      public Builder addDraftMarks(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDraftMarksIsMutable();
        draftMarks_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string draftMarks = 3;</code>
       *
       * @param values The draftMarks to add.
       * @return This builder for chaining.
       */
      public Builder addAllDraftMarks(java.lang.Iterable<java.lang.String> values) {
        ensureDraftMarksIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, draftMarks_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string draftMarks = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDraftMarks() {
        draftMarks_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string draftMarks = 3;</code>
       *
       * @param value The bytes of the draftMarks to add.
       * @return This builder for chaining.
       */
      public Builder addDraftMarksBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);
        ensureDraftMarksIsMutable();
        draftMarks_.add(value);
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

      // @@protoc_insertion_point(builder_scope:LoadLineDetail)
    }

    // @@protoc_insertion_point(class_scope:LoadLineDetail)
    private static final com.cpdss.common.generated.VesselInfo.LoadLineDetail DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.VesselInfo.LoadLineDetail();
    }

    public static com.cpdss.common.generated.VesselInfo.LoadLineDetail getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadLineDetail> PARSER =
        new com.google.protobuf.AbstractParser<LoadLineDetail>() {
          @java.lang.Override
          public LoadLineDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadLineDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadLineDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadLineDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.VesselInfo.LoadLineDetail getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface VesselDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:VesselDetail)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>string name = 2;</code>
     *
     * @return The name.
     */
    java.lang.String getName();
    /**
     * <code>string name = 2;</code>
     *
     * @return The bytes for name.
     */
    com.google.protobuf.ByteString getNameBytes();

    /**
     * <code>int64 captainId = 3;</code>
     *
     * @return The captainId.
     */
    long getCaptainId();

    /**
     * <code>int64 cheifOfficerId = 4;</code>
     *
     * @return The cheifOfficerId.
     */
    long getCheifOfficerId();

    /**
     * <code>string imoNumber = 5;</code>
     *
     * @return The imoNumber.
     */
    java.lang.String getImoNumber();
    /**
     * <code>string imoNumber = 5;</code>
     *
     * @return The bytes for imoNumber.
     */
    com.google.protobuf.ByteString getImoNumberBytes();

    /**
     * <code>string flag = 6;</code>
     *
     * @return The flag.
     */
    java.lang.String getFlag();
    /**
     * <code>string flag = 6;</code>
     *
     * @return The bytes for flag.
     */
    com.google.protobuf.ByteString getFlagBytes();

    /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
    java.util.List<com.cpdss.common.generated.VesselInfo.LoadLineDetail> getLoadLinesList();
    /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
    com.cpdss.common.generated.VesselInfo.LoadLineDetail getLoadLines(int index);
    /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
    int getLoadLinesCount();
    /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
    java.util.List<? extends com.cpdss.common.generated.VesselInfo.LoadLineDetailOrBuilder>
        getLoadLinesOrBuilderList();
    /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
    com.cpdss.common.generated.VesselInfo.LoadLineDetailOrBuilder getLoadLinesOrBuilder(int index);
  }
  /** Protobuf type {@code VesselDetail} */
  public static final class VesselDetail extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:VesselDetail)
      VesselDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use VesselDetail.newBuilder() to construct.
    private VesselDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private VesselDetail() {
      name_ = "";
      imoNumber_ = "";
      flag_ = "";
      loadLines_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new VesselDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private VesselDetail(
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
                id_ = input.readInt64();
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                name_ = s;
                break;
              }
            case 24:
              {
                captainId_ = input.readInt64();
                break;
              }
            case 32:
              {
                cheifOfficerId_ = input.readInt64();
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                imoNumber_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                flag_ = s;
                break;
              }
            case 58:
              {
                if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                  loadLines_ =
                      new java.util.ArrayList<
                          com.cpdss.common.generated.VesselInfo.LoadLineDetail>();
                  mutable_bitField0_ |= 0x00000001;
                }
                loadLines_.add(
                    input.readMessage(
                        com.cpdss.common.generated.VesselInfo.LoadLineDetail.parser(),
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
          loadLines_ = java.util.Collections.unmodifiableList(loadLines_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.VesselInfo.internal_static_VesselDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.VesselInfo.internal_static_VesselDetail_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.VesselInfo.VesselDetail.class,
              com.cpdss.common.generated.VesselInfo.VesselDetail.Builder.class);
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

    public static final int NAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object name_;
    /**
     * <code>string name = 2;</code>
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
     * <code>string name = 2;</code>
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

    public static final int CAPTAINID_FIELD_NUMBER = 3;
    private long captainId_;
    /**
     * <code>int64 captainId = 3;</code>
     *
     * @return The captainId.
     */
    public long getCaptainId() {
      return captainId_;
    }

    public static final int CHEIFOFFICERID_FIELD_NUMBER = 4;
    private long cheifOfficerId_;
    /**
     * <code>int64 cheifOfficerId = 4;</code>
     *
     * @return The cheifOfficerId.
     */
    public long getCheifOfficerId() {
      return cheifOfficerId_;
    }

    public static final int IMONUMBER_FIELD_NUMBER = 5;
    private volatile java.lang.Object imoNumber_;
    /**
     * <code>string imoNumber = 5;</code>
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
     * <code>string imoNumber = 5;</code>
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

    public static final int FLAG_FIELD_NUMBER = 6;
    private volatile java.lang.Object flag_;
    /**
     * <code>string flag = 6;</code>
     *
     * @return The flag.
     */
    public java.lang.String getFlag() {
      java.lang.Object ref = flag_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        flag_ = s;
        return s;
      }
    }
    /**
     * <code>string flag = 6;</code>
     *
     * @return The bytes for flag.
     */
    public com.google.protobuf.ByteString getFlagBytes() {
      java.lang.Object ref = flag_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        flag_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int LOADLINES_FIELD_NUMBER = 7;
    private java.util.List<com.cpdss.common.generated.VesselInfo.LoadLineDetail> loadLines_;
    /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
    public java.util.List<com.cpdss.common.generated.VesselInfo.LoadLineDetail> getLoadLinesList() {
      return loadLines_;
    }
    /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
    public java.util.List<? extends com.cpdss.common.generated.VesselInfo.LoadLineDetailOrBuilder>
        getLoadLinesOrBuilderList() {
      return loadLines_;
    }
    /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
    public int getLoadLinesCount() {
      return loadLines_.size();
    }
    /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
    public com.cpdss.common.generated.VesselInfo.LoadLineDetail getLoadLines(int index) {
      return loadLines_.get(index);
    }
    /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
    public com.cpdss.common.generated.VesselInfo.LoadLineDetailOrBuilder getLoadLinesOrBuilder(
        int index) {
      return loadLines_.get(index);
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
      if (!getNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, name_);
      }
      if (captainId_ != 0L) {
        output.writeInt64(3, captainId_);
      }
      if (cheifOfficerId_ != 0L) {
        output.writeInt64(4, cheifOfficerId_);
      }
      if (!getImoNumberBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, imoNumber_);
      }
      if (!getFlagBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, flag_);
      }
      for (int i = 0; i < loadLines_.size(); i++) {
        output.writeMessage(7, loadLines_.get(i));
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
      if (!getNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, name_);
      }
      if (captainId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, captainId_);
      }
      if (cheifOfficerId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, cheifOfficerId_);
      }
      if (!getImoNumberBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, imoNumber_);
      }
      if (!getFlagBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, flag_);
      }
      for (int i = 0; i < loadLines_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(7, loadLines_.get(i));
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
      if (!(obj instanceof com.cpdss.common.generated.VesselInfo.VesselDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.VesselInfo.VesselDetail other =
          (com.cpdss.common.generated.VesselInfo.VesselDetail) obj;

      if (getId() != other.getId()) return false;
      if (!getName().equals(other.getName())) return false;
      if (getCaptainId() != other.getCaptainId()) return false;
      if (getCheifOfficerId() != other.getCheifOfficerId()) return false;
      if (!getImoNumber().equals(other.getImoNumber())) return false;
      if (!getFlag().equals(other.getFlag())) return false;
      if (!getLoadLinesList().equals(other.getLoadLinesList())) return false;
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
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      hash = (37 * hash) + CAPTAINID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCaptainId());
      hash = (37 * hash) + CHEIFOFFICERID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCheifOfficerId());
      hash = (37 * hash) + IMONUMBER_FIELD_NUMBER;
      hash = (53 * hash) + getImoNumber().hashCode();
      hash = (37 * hash) + FLAG_FIELD_NUMBER;
      hash = (53 * hash) + getFlag().hashCode();
      if (getLoadLinesCount() > 0) {
        hash = (37 * hash) + LOADLINES_FIELD_NUMBER;
        hash = (53 * hash) + getLoadLinesList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.VesselInfo.VesselDetail prototype) {
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
    /** Protobuf type {@code VesselDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:VesselDetail)
        com.cpdss.common.generated.VesselInfo.VesselDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.VesselInfo.internal_static_VesselDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.VesselInfo.internal_static_VesselDetail_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.VesselInfo.VesselDetail.class,
                com.cpdss.common.generated.VesselInfo.VesselDetail.Builder.class);
      }

      // Construct using com.cpdss.common.generated.VesselInfo.VesselDetail.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
          getLoadLinesFieldBuilder();
        }
      }

      @java.lang.Override
      public Builder clear() {
        super.clear();
        id_ = 0L;

        name_ = "";

        captainId_ = 0L;

        cheifOfficerId_ = 0L;

        imoNumber_ = "";

        flag_ = "";

        if (loadLinesBuilder_ == null) {
          loadLines_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          loadLinesBuilder_.clear();
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.VesselInfo.internal_static_VesselDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.VesselDetail getDefaultInstanceForType() {
        return com.cpdss.common.generated.VesselInfo.VesselDetail.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.VesselDetail build() {
        com.cpdss.common.generated.VesselInfo.VesselDetail result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.VesselDetail buildPartial() {
        com.cpdss.common.generated.VesselInfo.VesselDetail result =
            new com.cpdss.common.generated.VesselInfo.VesselDetail(this);
        int from_bitField0_ = bitField0_;
        result.id_ = id_;
        result.name_ = name_;
        result.captainId_ = captainId_;
        result.cheifOfficerId_ = cheifOfficerId_;
        result.imoNumber_ = imoNumber_;
        result.flag_ = flag_;
        if (loadLinesBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            loadLines_ = java.util.Collections.unmodifiableList(loadLines_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.loadLines_ = loadLines_;
        } else {
          result.loadLines_ = loadLinesBuilder_.build();
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
        if (other instanceof com.cpdss.common.generated.VesselInfo.VesselDetail) {
          return mergeFrom((com.cpdss.common.generated.VesselInfo.VesselDetail) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.VesselInfo.VesselDetail other) {
        if (other == com.cpdss.common.generated.VesselInfo.VesselDetail.getDefaultInstance())
          return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
          onChanged();
        }
        if (other.getCaptainId() != 0L) {
          setCaptainId(other.getCaptainId());
        }
        if (other.getCheifOfficerId() != 0L) {
          setCheifOfficerId(other.getCheifOfficerId());
        }
        if (!other.getImoNumber().isEmpty()) {
          imoNumber_ = other.imoNumber_;
          onChanged();
        }
        if (!other.getFlag().isEmpty()) {
          flag_ = other.flag_;
          onChanged();
        }
        if (loadLinesBuilder_ == null) {
          if (!other.loadLines_.isEmpty()) {
            if (loadLines_.isEmpty()) {
              loadLines_ = other.loadLines_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureLoadLinesIsMutable();
              loadLines_.addAll(other.loadLines_);
            }
            onChanged();
          }
        } else {
          if (!other.loadLines_.isEmpty()) {
            if (loadLinesBuilder_.isEmpty()) {
              loadLinesBuilder_.dispose();
              loadLinesBuilder_ = null;
              loadLines_ = other.loadLines_;
              bitField0_ = (bitField0_ & ~0x00000001);
              loadLinesBuilder_ =
                  com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                      ? getLoadLinesFieldBuilder()
                      : null;
            } else {
              loadLinesBuilder_.addAllMessages(other.loadLines_);
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
        com.cpdss.common.generated.VesselInfo.VesselDetail parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.VesselInfo.VesselDetail) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int bitField0_;

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

      private java.lang.Object name_ = "";
      /**
       * <code>string name = 2;</code>
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
       * <code>string name = 2;</code>
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
       * <code>string name = 2;</code>
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
       * <code>string name = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearName() {

        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>string name = 2;</code>
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

      private long captainId_;
      /**
       * <code>int64 captainId = 3;</code>
       *
       * @return The captainId.
       */
      public long getCaptainId() {
        return captainId_;
      }
      /**
       * <code>int64 captainId = 3;</code>
       *
       * @param value The captainId to set.
       * @return This builder for chaining.
       */
      public Builder setCaptainId(long value) {

        captainId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 captainId = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCaptainId() {

        captainId_ = 0L;
        onChanged();
        return this;
      }

      private long cheifOfficerId_;
      /**
       * <code>int64 cheifOfficerId = 4;</code>
       *
       * @return The cheifOfficerId.
       */
      public long getCheifOfficerId() {
        return cheifOfficerId_;
      }
      /**
       * <code>int64 cheifOfficerId = 4;</code>
       *
       * @param value The cheifOfficerId to set.
       * @return This builder for chaining.
       */
      public Builder setCheifOfficerId(long value) {

        cheifOfficerId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 cheifOfficerId = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCheifOfficerId() {

        cheifOfficerId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object imoNumber_ = "";
      /**
       * <code>string imoNumber = 5;</code>
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
       * <code>string imoNumber = 5;</code>
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
       * <code>string imoNumber = 5;</code>
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
       * <code>string imoNumber = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearImoNumber() {

        imoNumber_ = getDefaultInstance().getImoNumber();
        onChanged();
        return this;
      }
      /**
       * <code>string imoNumber = 5;</code>
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

      private java.lang.Object flag_ = "";
      /**
       * <code>string flag = 6;</code>
       *
       * @return The flag.
       */
      public java.lang.String getFlag() {
        java.lang.Object ref = flag_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          flag_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string flag = 6;</code>
       *
       * @return The bytes for flag.
       */
      public com.google.protobuf.ByteString getFlagBytes() {
        java.lang.Object ref = flag_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          flag_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string flag = 6;</code>
       *
       * @param value The flag to set.
       * @return This builder for chaining.
       */
      public Builder setFlag(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        flag_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string flag = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearFlag() {

        flag_ = getDefaultInstance().getFlag();
        onChanged();
        return this;
      }
      /**
       * <code>string flag = 6;</code>
       *
       * @param value The bytes for flag to set.
       * @return This builder for chaining.
       */
      public Builder setFlagBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        flag_ = value;
        onChanged();
        return this;
      }

      private java.util.List<com.cpdss.common.generated.VesselInfo.LoadLineDetail> loadLines_ =
          java.util.Collections.emptyList();

      private void ensureLoadLinesIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          loadLines_ =
              new java.util.ArrayList<com.cpdss.common.generated.VesselInfo.LoadLineDetail>(
                  loadLines_);
          bitField0_ |= 0x00000001;
        }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.VesselInfo.LoadLineDetail,
              com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder,
              com.cpdss.common.generated.VesselInfo.LoadLineDetailOrBuilder>
          loadLinesBuilder_;

      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public java.util.List<com.cpdss.common.generated.VesselInfo.LoadLineDetail>
          getLoadLinesList() {
        if (loadLinesBuilder_ == null) {
          return java.util.Collections.unmodifiableList(loadLines_);
        } else {
          return loadLinesBuilder_.getMessageList();
        }
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public int getLoadLinesCount() {
        if (loadLinesBuilder_ == null) {
          return loadLines_.size();
        } else {
          return loadLinesBuilder_.getCount();
        }
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public com.cpdss.common.generated.VesselInfo.LoadLineDetail getLoadLines(int index) {
        if (loadLinesBuilder_ == null) {
          return loadLines_.get(index);
        } else {
          return loadLinesBuilder_.getMessage(index);
        }
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public Builder setLoadLines(
          int index, com.cpdss.common.generated.VesselInfo.LoadLineDetail value) {
        if (loadLinesBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureLoadLinesIsMutable();
          loadLines_.set(index, value);
          onChanged();
        } else {
          loadLinesBuilder_.setMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public Builder setLoadLines(
          int index, com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder builderForValue) {
        if (loadLinesBuilder_ == null) {
          ensureLoadLinesIsMutable();
          loadLines_.set(index, builderForValue.build());
          onChanged();
        } else {
          loadLinesBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public Builder addLoadLines(com.cpdss.common.generated.VesselInfo.LoadLineDetail value) {
        if (loadLinesBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureLoadLinesIsMutable();
          loadLines_.add(value);
          onChanged();
        } else {
          loadLinesBuilder_.addMessage(value);
        }
        return this;
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public Builder addLoadLines(
          int index, com.cpdss.common.generated.VesselInfo.LoadLineDetail value) {
        if (loadLinesBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureLoadLinesIsMutable();
          loadLines_.add(index, value);
          onChanged();
        } else {
          loadLinesBuilder_.addMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public Builder addLoadLines(
          com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder builderForValue) {
        if (loadLinesBuilder_ == null) {
          ensureLoadLinesIsMutable();
          loadLines_.add(builderForValue.build());
          onChanged();
        } else {
          loadLinesBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public Builder addLoadLines(
          int index, com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder builderForValue) {
        if (loadLinesBuilder_ == null) {
          ensureLoadLinesIsMutable();
          loadLines_.add(index, builderForValue.build());
          onChanged();
        } else {
          loadLinesBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public Builder addAllLoadLines(
          java.lang.Iterable<? extends com.cpdss.common.generated.VesselInfo.LoadLineDetail>
              values) {
        if (loadLinesBuilder_ == null) {
          ensureLoadLinesIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(values, loadLines_);
          onChanged();
        } else {
          loadLinesBuilder_.addAllMessages(values);
        }
        return this;
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public Builder clearLoadLines() {
        if (loadLinesBuilder_ == null) {
          loadLines_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          loadLinesBuilder_.clear();
        }
        return this;
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public Builder removeLoadLines(int index) {
        if (loadLinesBuilder_ == null) {
          ensureLoadLinesIsMutable();
          loadLines_.remove(index);
          onChanged();
        } else {
          loadLinesBuilder_.remove(index);
        }
        return this;
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder getLoadLinesBuilder(
          int index) {
        return getLoadLinesFieldBuilder().getBuilder(index);
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public com.cpdss.common.generated.VesselInfo.LoadLineDetailOrBuilder getLoadLinesOrBuilder(
          int index) {
        if (loadLinesBuilder_ == null) {
          return loadLines_.get(index);
        } else {
          return loadLinesBuilder_.getMessageOrBuilder(index);
        }
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public java.util.List<? extends com.cpdss.common.generated.VesselInfo.LoadLineDetailOrBuilder>
          getLoadLinesOrBuilderList() {
        if (loadLinesBuilder_ != null) {
          return loadLinesBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(loadLines_);
        }
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder addLoadLinesBuilder() {
        return getLoadLinesFieldBuilder()
            .addBuilder(com.cpdss.common.generated.VesselInfo.LoadLineDetail.getDefaultInstance());
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder addLoadLinesBuilder(
          int index) {
        return getLoadLinesFieldBuilder()
            .addBuilder(
                index, com.cpdss.common.generated.VesselInfo.LoadLineDetail.getDefaultInstance());
      }
      /** <code>repeated .LoadLineDetail loadLines = 7;</code> */
      public java.util.List<com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder>
          getLoadLinesBuilderList() {
        return getLoadLinesFieldBuilder().getBuilderList();
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.VesselInfo.LoadLineDetail,
              com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder,
              com.cpdss.common.generated.VesselInfo.LoadLineDetailOrBuilder>
          getLoadLinesFieldBuilder() {
        if (loadLinesBuilder_ == null) {
          loadLinesBuilder_ =
              new com.google.protobuf.RepeatedFieldBuilderV3<
                  com.cpdss.common.generated.VesselInfo.LoadLineDetail,
                  com.cpdss.common.generated.VesselInfo.LoadLineDetail.Builder,
                  com.cpdss.common.generated.VesselInfo.LoadLineDetailOrBuilder>(
                  loadLines_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
          loadLines_ = null;
        }
        return loadLinesBuilder_;
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

      // @@protoc_insertion_point(builder_scope:VesselDetail)
    }

    // @@protoc_insertion_point(class_scope:VesselDetail)
    private static final com.cpdss.common.generated.VesselInfo.VesselDetail DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.VesselInfo.VesselDetail();
    }

    public static com.cpdss.common.generated.VesselInfo.VesselDetail getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<VesselDetail> PARSER =
        new com.google.protobuf.AbstractParser<VesselDetail>() {
          @java.lang.Override
          public VesselDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new VesselDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<VesselDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<VesselDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.VesselInfo.VesselDetail getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface VesselReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:VesselReply)
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

    /** <code>repeated .VesselDetail vessels = 2;</code> */
    java.util.List<com.cpdss.common.generated.VesselInfo.VesselDetail> getVesselsList();
    /** <code>repeated .VesselDetail vessels = 2;</code> */
    com.cpdss.common.generated.VesselInfo.VesselDetail getVessels(int index);
    /** <code>repeated .VesselDetail vessels = 2;</code> */
    int getVesselsCount();
    /** <code>repeated .VesselDetail vessels = 2;</code> */
    java.util.List<? extends com.cpdss.common.generated.VesselInfo.VesselDetailOrBuilder>
        getVesselsOrBuilderList();
    /** <code>repeated .VesselDetail vessels = 2;</code> */
    com.cpdss.common.generated.VesselInfo.VesselDetailOrBuilder getVesselsOrBuilder(int index);
  }
  /** Protobuf type {@code VesselReply} */
  public static final class VesselReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:VesselReply)
      VesselReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use VesselReply.newBuilder() to construct.
    private VesselReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private VesselReply() {
      vessels_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new VesselReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private VesselReply(
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
                  vessels_ =
                      new java.util.ArrayList<com.cpdss.common.generated.VesselInfo.VesselDetail>();
                  mutable_bitField0_ |= 0x00000001;
                }
                vessels_.add(
                    input.readMessage(
                        com.cpdss.common.generated.VesselInfo.VesselDetail.parser(),
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
          vessels_ = java.util.Collections.unmodifiableList(vessels_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.VesselInfo.internal_static_VesselReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.VesselInfo.internal_static_VesselReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.VesselInfo.VesselReply.class,
              com.cpdss.common.generated.VesselInfo.VesselReply.Builder.class);
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

    public static final int VESSELS_FIELD_NUMBER = 2;
    private java.util.List<com.cpdss.common.generated.VesselInfo.VesselDetail> vessels_;
    /** <code>repeated .VesselDetail vessels = 2;</code> */
    public java.util.List<com.cpdss.common.generated.VesselInfo.VesselDetail> getVesselsList() {
      return vessels_;
    }
    /** <code>repeated .VesselDetail vessels = 2;</code> */
    public java.util.List<? extends com.cpdss.common.generated.VesselInfo.VesselDetailOrBuilder>
        getVesselsOrBuilderList() {
      return vessels_;
    }
    /** <code>repeated .VesselDetail vessels = 2;</code> */
    public int getVesselsCount() {
      return vessels_.size();
    }
    /** <code>repeated .VesselDetail vessels = 2;</code> */
    public com.cpdss.common.generated.VesselInfo.VesselDetail getVessels(int index) {
      return vessels_.get(index);
    }
    /** <code>repeated .VesselDetail vessels = 2;</code> */
    public com.cpdss.common.generated.VesselInfo.VesselDetailOrBuilder getVesselsOrBuilder(
        int index) {
      return vessels_.get(index);
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
      for (int i = 0; i < vessels_.size(); i++) {
        output.writeMessage(2, vessels_.get(i));
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
      for (int i = 0; i < vessels_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, vessels_.get(i));
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
      if (!(obj instanceof com.cpdss.common.generated.VesselInfo.VesselReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.VesselInfo.VesselReply other =
          (com.cpdss.common.generated.VesselInfo.VesselReply) obj;

      if (hasResponseStatus() != other.hasResponseStatus()) return false;
      if (hasResponseStatus()) {
        if (!getResponseStatus().equals(other.getResponseStatus())) return false;
      }
      if (!getVesselsList().equals(other.getVesselsList())) return false;
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
      if (getVesselsCount() > 0) {
        hash = (37 * hash) + VESSELS_FIELD_NUMBER;
        hash = (53 * hash) + getVesselsList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.VesselInfo.VesselReply prototype) {
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
    /** Protobuf type {@code VesselReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:VesselReply)
        com.cpdss.common.generated.VesselInfo.VesselReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.VesselInfo.internal_static_VesselReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.VesselInfo.internal_static_VesselReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.VesselInfo.VesselReply.class,
                com.cpdss.common.generated.VesselInfo.VesselReply.Builder.class);
      }

      // Construct using com.cpdss.common.generated.VesselInfo.VesselReply.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
          getVesselsFieldBuilder();
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
        if (vesselsBuilder_ == null) {
          vessels_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          vesselsBuilder_.clear();
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.VesselInfo.internal_static_VesselReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.VesselReply getDefaultInstanceForType() {
        return com.cpdss.common.generated.VesselInfo.VesselReply.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.VesselReply build() {
        com.cpdss.common.generated.VesselInfo.VesselReply result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.VesselInfo.VesselReply buildPartial() {
        com.cpdss.common.generated.VesselInfo.VesselReply result =
            new com.cpdss.common.generated.VesselInfo.VesselReply(this);
        int from_bitField0_ = bitField0_;
        if (responseStatusBuilder_ == null) {
          result.responseStatus_ = responseStatus_;
        } else {
          result.responseStatus_ = responseStatusBuilder_.build();
        }
        if (vesselsBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            vessels_ = java.util.Collections.unmodifiableList(vessels_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.vessels_ = vessels_;
        } else {
          result.vessels_ = vesselsBuilder_.build();
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
        if (other instanceof com.cpdss.common.generated.VesselInfo.VesselReply) {
          return mergeFrom((com.cpdss.common.generated.VesselInfo.VesselReply) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.VesselInfo.VesselReply other) {
        if (other == com.cpdss.common.generated.VesselInfo.VesselReply.getDefaultInstance())
          return this;
        if (other.hasResponseStatus()) {
          mergeResponseStatus(other.getResponseStatus());
        }
        if (vesselsBuilder_ == null) {
          if (!other.vessels_.isEmpty()) {
            if (vessels_.isEmpty()) {
              vessels_ = other.vessels_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureVesselsIsMutable();
              vessels_.addAll(other.vessels_);
            }
            onChanged();
          }
        } else {
          if (!other.vessels_.isEmpty()) {
            if (vesselsBuilder_.isEmpty()) {
              vesselsBuilder_.dispose();
              vesselsBuilder_ = null;
              vessels_ = other.vessels_;
              bitField0_ = (bitField0_ & ~0x00000001);
              vesselsBuilder_ =
                  com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                      ? getVesselsFieldBuilder()
                      : null;
            } else {
              vesselsBuilder_.addAllMessages(other.vessels_);
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
        com.cpdss.common.generated.VesselInfo.VesselReply parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.VesselInfo.VesselReply) e.getUnfinishedMessage();
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

      private java.util.List<com.cpdss.common.generated.VesselInfo.VesselDetail> vessels_ =
          java.util.Collections.emptyList();

      private void ensureVesselsIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          vessels_ =
              new java.util.ArrayList<com.cpdss.common.generated.VesselInfo.VesselDetail>(vessels_);
          bitField0_ |= 0x00000001;
        }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.VesselInfo.VesselDetail,
              com.cpdss.common.generated.VesselInfo.VesselDetail.Builder,
              com.cpdss.common.generated.VesselInfo.VesselDetailOrBuilder>
          vesselsBuilder_;

      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public java.util.List<com.cpdss.common.generated.VesselInfo.VesselDetail> getVesselsList() {
        if (vesselsBuilder_ == null) {
          return java.util.Collections.unmodifiableList(vessels_);
        } else {
          return vesselsBuilder_.getMessageList();
        }
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public int getVesselsCount() {
        if (vesselsBuilder_ == null) {
          return vessels_.size();
        } else {
          return vesselsBuilder_.getCount();
        }
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public com.cpdss.common.generated.VesselInfo.VesselDetail getVessels(int index) {
        if (vesselsBuilder_ == null) {
          return vessels_.get(index);
        } else {
          return vesselsBuilder_.getMessage(index);
        }
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public Builder setVessels(
          int index, com.cpdss.common.generated.VesselInfo.VesselDetail value) {
        if (vesselsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureVesselsIsMutable();
          vessels_.set(index, value);
          onChanged();
        } else {
          vesselsBuilder_.setMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public Builder setVessels(
          int index, com.cpdss.common.generated.VesselInfo.VesselDetail.Builder builderForValue) {
        if (vesselsBuilder_ == null) {
          ensureVesselsIsMutable();
          vessels_.set(index, builderForValue.build());
          onChanged();
        } else {
          vesselsBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public Builder addVessels(com.cpdss.common.generated.VesselInfo.VesselDetail value) {
        if (vesselsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureVesselsIsMutable();
          vessels_.add(value);
          onChanged();
        } else {
          vesselsBuilder_.addMessage(value);
        }
        return this;
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public Builder addVessels(
          int index, com.cpdss.common.generated.VesselInfo.VesselDetail value) {
        if (vesselsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureVesselsIsMutable();
          vessels_.add(index, value);
          onChanged();
        } else {
          vesselsBuilder_.addMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public Builder addVessels(
          com.cpdss.common.generated.VesselInfo.VesselDetail.Builder builderForValue) {
        if (vesselsBuilder_ == null) {
          ensureVesselsIsMutable();
          vessels_.add(builderForValue.build());
          onChanged();
        } else {
          vesselsBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public Builder addVessels(
          int index, com.cpdss.common.generated.VesselInfo.VesselDetail.Builder builderForValue) {
        if (vesselsBuilder_ == null) {
          ensureVesselsIsMutable();
          vessels_.add(index, builderForValue.build());
          onChanged();
        } else {
          vesselsBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public Builder addAllVessels(
          java.lang.Iterable<? extends com.cpdss.common.generated.VesselInfo.VesselDetail> values) {
        if (vesselsBuilder_ == null) {
          ensureVesselsIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(values, vessels_);
          onChanged();
        } else {
          vesselsBuilder_.addAllMessages(values);
        }
        return this;
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public Builder clearVessels() {
        if (vesselsBuilder_ == null) {
          vessels_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          vesselsBuilder_.clear();
        }
        return this;
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public Builder removeVessels(int index) {
        if (vesselsBuilder_ == null) {
          ensureVesselsIsMutable();
          vessels_.remove(index);
          onChanged();
        } else {
          vesselsBuilder_.remove(index);
        }
        return this;
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public com.cpdss.common.generated.VesselInfo.VesselDetail.Builder getVesselsBuilder(
          int index) {
        return getVesselsFieldBuilder().getBuilder(index);
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public com.cpdss.common.generated.VesselInfo.VesselDetailOrBuilder getVesselsOrBuilder(
          int index) {
        if (vesselsBuilder_ == null) {
          return vessels_.get(index);
        } else {
          return vesselsBuilder_.getMessageOrBuilder(index);
        }
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public java.util.List<? extends com.cpdss.common.generated.VesselInfo.VesselDetailOrBuilder>
          getVesselsOrBuilderList() {
        if (vesselsBuilder_ != null) {
          return vesselsBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(vessels_);
        }
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public com.cpdss.common.generated.VesselInfo.VesselDetail.Builder addVesselsBuilder() {
        return getVesselsFieldBuilder()
            .addBuilder(com.cpdss.common.generated.VesselInfo.VesselDetail.getDefaultInstance());
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public com.cpdss.common.generated.VesselInfo.VesselDetail.Builder addVesselsBuilder(
          int index) {
        return getVesselsFieldBuilder()
            .addBuilder(
                index, com.cpdss.common.generated.VesselInfo.VesselDetail.getDefaultInstance());
      }
      /** <code>repeated .VesselDetail vessels = 2;</code> */
      public java.util.List<com.cpdss.common.generated.VesselInfo.VesselDetail.Builder>
          getVesselsBuilderList() {
        return getVesselsFieldBuilder().getBuilderList();
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.VesselInfo.VesselDetail,
              com.cpdss.common.generated.VesselInfo.VesselDetail.Builder,
              com.cpdss.common.generated.VesselInfo.VesselDetailOrBuilder>
          getVesselsFieldBuilder() {
        if (vesselsBuilder_ == null) {
          vesselsBuilder_ =
              new com.google.protobuf.RepeatedFieldBuilderV3<
                  com.cpdss.common.generated.VesselInfo.VesselDetail,
                  com.cpdss.common.generated.VesselInfo.VesselDetail.Builder,
                  com.cpdss.common.generated.VesselInfo.VesselDetailOrBuilder>(
                  vessels_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
          vessels_ = null;
        }
        return vesselsBuilder_;
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

      // @@protoc_insertion_point(builder_scope:VesselReply)
    }

    // @@protoc_insertion_point(class_scope:VesselReply)
    private static final com.cpdss.common.generated.VesselInfo.VesselReply DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.VesselInfo.VesselReply();
    }

    public static com.cpdss.common.generated.VesselInfo.VesselReply getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<VesselReply> PARSER =
        new com.google.protobuf.AbstractParser<VesselReply>() {
          @java.lang.Override
          public VesselReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new VesselReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<VesselReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<VesselReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.VesselInfo.VesselReply getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_VesselRequest_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_VesselRequest_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadLineDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadLineDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_VesselDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_VesselDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_VesselReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_VesselReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\021vessel_info.proto\032\014common.proto\"\"\n\rVes"
          + "selRequest\022\021\n\tcompanyId\030\001 \001(\003\">\n\016LoadLin"
          + "eDetail\022\n\n\002id\030\001 \001(\003\022\014\n\004name\030\002 \001(\t\022\022\n\ndra"
          + "ftMarks\030\003 \003(\t\"\230\001\n\014VesselDetail\022\n\n\002id\030\001 \001"
          + "(\003\022\014\n\004name\030\002 \001(\t\022\021\n\tcaptainId\030\003 \001(\003\022\026\n\016c"
          + "heifOfficerId\030\004 \001(\003\022\021\n\timoNumber\030\005 \001(\t\022\014"
          + "\n\004flag\030\006 \001(\t\022\"\n\tloadLines\030\007 \003(\0132\017.LoadLi"
          + "neDetail\"V\n\013VesselReply\022\'\n\016responseStatu"
          + "s\030\001 \001(\0132\017.ResponseStatus\022\036\n\007vessels\030\002 \003("
          + "\0132\r.VesselDetail2M\n\021VesselInfoService\0228\n"
          + "\026GetAllVesselsByCompany\022\016.VesselRequest\032"
          + "\014.VesselReply\"\000B\036\n\032com.cpdss.common.gene"
          + "ratedP\000b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
            });
    internal_static_VesselRequest_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_VesselRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_VesselRequest_descriptor,
            new java.lang.String[] {
              "CompanyId",
            });
    internal_static_LoadLineDetail_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_LoadLineDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadLineDetail_descriptor,
            new java.lang.String[] {
              "Id", "Name", "DraftMarks",
            });
    internal_static_VesselDetail_descriptor = getDescriptor().getMessageTypes().get(2);
    internal_static_VesselDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_VesselDetail_descriptor,
            new java.lang.String[] {
              "Id", "Name", "CaptainId", "CheifOfficerId", "ImoNumber", "Flag", "LoadLines",
            });
    internal_static_VesselReply_descriptor = getDescriptor().getMessageTypes().get(3);
    internal_static_VesselReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_VesselReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus", "Vessels",
            });
    com.cpdss.common.generated.Common.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
