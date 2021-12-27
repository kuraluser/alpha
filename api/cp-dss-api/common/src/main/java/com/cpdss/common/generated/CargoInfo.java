/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

public final class CargoInfo {
  private CargoInfo() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public interface CargoListRequestOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoListRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated int64 id = 1;</code>
     *
     * @return A list containing the id.
     */
    java.util.List<java.lang.Long> getIdList();
    /**
     * <code>repeated int64 id = 1;</code>
     *
     * @return The count of id.
     */
    int getIdCount();
    /**
     * <code>repeated int64 id = 1;</code>
     *
     * @param index The index of the element to return.
     * @return The id at the given index.
     */
    long getId(int index);
  }
  /** Protobuf type {@code CargoListRequest} */
  public static final class CargoListRequest extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoListRequest)
      CargoListRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoListRequest.newBuilder() to construct.
    private CargoListRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoListRequest() {
      id_ = emptyLongList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoListRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoListRequest(
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
                if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                  id_ = newLongList();
                  mutable_bitField0_ |= 0x00000001;
                }
                id_.addLong(input.readInt64());
                break;
              }
            case 10:
              {
                int length = input.readRawVarint32();
                int limit = input.pushLimit(length);
                if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
                  id_ = newLongList();
                  mutable_bitField0_ |= 0x00000001;
                }
                while (input.getBytesUntilLimit() > 0) {
                  id_.addLong(input.readInt64());
                }
                input.popLimit(limit);
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
          id_.makeImmutable(); // C
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoListRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.CargoInfo
          .internal_static_CargoListRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.CargoInfo.CargoListRequest.class,
              com.cpdss.common.generated.CargoInfo.CargoListRequest.Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private com.google.protobuf.Internal.LongList id_;
    /**
     * <code>repeated int64 id = 1;</code>
     *
     * @return A list containing the id.
     */
    public java.util.List<java.lang.Long> getIdList() {
      return id_;
    }
    /**
     * <code>repeated int64 id = 1;</code>
     *
     * @return The count of id.
     */
    public int getIdCount() {
      return id_.size();
    }
    /**
     * <code>repeated int64 id = 1;</code>
     *
     * @param index The index of the element to return.
     * @return The id at the given index.
     */
    public long getId(int index) {
      return id_.getLong(index);
    }

    private int idMemoizedSerializedSize = -1;

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
      getSerializedSize();
      if (getIdList().size() > 0) {
        output.writeUInt32NoTag(10);
        output.writeUInt32NoTag(idMemoizedSerializedSize);
      }
      for (int i = 0; i < id_.size(); i++) {
        output.writeInt64NoTag(id_.getLong(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      {
        int dataSize = 0;
        for (int i = 0; i < id_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream.computeInt64SizeNoTag(id_.getLong(i));
        }
        size += dataSize;
        if (!getIdList().isEmpty()) {
          size += 1;
          size += com.google.protobuf.CodedOutputStream.computeInt32SizeNoTag(dataSize);
        }
        idMemoizedSerializedSize = dataSize;
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
      if (!(obj instanceof com.cpdss.common.generated.CargoInfo.CargoListRequest)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.CargoInfo.CargoListRequest other =
          (com.cpdss.common.generated.CargoInfo.CargoListRequest) obj;

      if (!getIdList().equals(other.getIdList())) return false;
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
      if (getIdCount() > 0) {
        hash = (37 * hash) + ID_FIELD_NUMBER;
        hash = (53 * hash) + getIdList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest parseFrom(
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
        com.cpdss.common.generated.CargoInfo.CargoListRequest prototype) {
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
    /** Protobuf type {@code CargoListRequest} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoListRequest)
        com.cpdss.common.generated.CargoInfo.CargoListRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoListRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.CargoInfo
            .internal_static_CargoListRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.CargoInfo.CargoListRequest.class,
                com.cpdss.common.generated.CargoInfo.CargoListRequest.Builder.class);
      }

      // Construct using com.cpdss.common.generated.CargoInfo.CargoListRequest.newBuilder()
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
        id_ = emptyLongList();
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoListRequest_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoListRequest getDefaultInstanceForType() {
        return com.cpdss.common.generated.CargoInfo.CargoListRequest.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoListRequest build() {
        com.cpdss.common.generated.CargoInfo.CargoListRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoListRequest buildPartial() {
        com.cpdss.common.generated.CargoInfo.CargoListRequest result =
            new com.cpdss.common.generated.CargoInfo.CargoListRequest(this);
        int from_bitField0_ = bitField0_;
        if (((bitField0_ & 0x00000001) != 0)) {
          id_.makeImmutable();
          bitField0_ = (bitField0_ & ~0x00000001);
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
        if (other instanceof com.cpdss.common.generated.CargoInfo.CargoListRequest) {
          return mergeFrom((com.cpdss.common.generated.CargoInfo.CargoListRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.CargoInfo.CargoListRequest other) {
        if (other == com.cpdss.common.generated.CargoInfo.CargoListRequest.getDefaultInstance())
          return this;
        if (!other.id_.isEmpty()) {
          if (id_.isEmpty()) {
            id_ = other.id_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureIdIsMutable();
            id_.addAll(other.id_);
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
        com.cpdss.common.generated.CargoInfo.CargoListRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.CargoInfo.CargoListRequest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int bitField0_;

      private com.google.protobuf.Internal.LongList id_ = emptyLongList();

      private void ensureIdIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          id_ = mutableCopy(id_);
          bitField0_ |= 0x00000001;
        }
      }
      /**
       * <code>repeated int64 id = 1;</code>
       *
       * @return A list containing the id.
       */
      public java.util.List<java.lang.Long> getIdList() {
        return ((bitField0_ & 0x00000001) != 0) ? java.util.Collections.unmodifiableList(id_) : id_;
      }
      /**
       * <code>repeated int64 id = 1;</code>
       *
       * @return The count of id.
       */
      public int getIdCount() {
        return id_.size();
      }
      /**
       * <code>repeated int64 id = 1;</code>
       *
       * @param index The index of the element to return.
       * @return The id at the given index.
       */
      public long getId(int index) {
        return id_.getLong(index);
      }
      /**
       * <code>repeated int64 id = 1;</code>
       *
       * @param index The index to set the value at.
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(int index, long value) {
        ensureIdIsMutable();
        id_.setLong(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int64 id = 1;</code>
       *
       * @param value The id to add.
       * @return This builder for chaining.
       */
      public Builder addId(long value) {
        ensureIdIsMutable();
        id_.addLong(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int64 id = 1;</code>
       *
       * @param values The id to add.
       * @return This builder for chaining.
       */
      public Builder addAllId(java.lang.Iterable<? extends java.lang.Long> values) {
        ensureIdIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, id_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int64 id = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearId() {
        id_ = emptyLongList();
        bitField0_ = (bitField0_ & ~0x00000001);
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

      // @@protoc_insertion_point(builder_scope:CargoListRequest)
    }

    // @@protoc_insertion_point(class_scope:CargoListRequest)
    private static final com.cpdss.common.generated.CargoInfo.CargoListRequest DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.CargoInfo.CargoListRequest();
    }

    public static com.cpdss.common.generated.CargoInfo.CargoListRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoListRequest> PARSER =
        new com.google.protobuf.AbstractParser<CargoListRequest>() {
          @java.lang.Override
          public CargoListRequest parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoListRequest(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoListRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoListRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.CargoInfo.CargoListRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface CargoDetailReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoDetailReply)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.CargoDetail cargoDetail = 1;</code>
     *
     * @return Whether the cargoDetail field is set.
     */
    boolean hasCargoDetail();
    /**
     * <code>.CargoDetail cargoDetail = 1;</code>
     *
     * @return The cargoDetail.
     */
    com.cpdss.common.generated.CargoInfo.CargoDetail getCargoDetail();
    /** <code>.CargoDetail cargoDetail = 1;</code> */
    com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder getCargoDetailOrBuilder();

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
  /** Protobuf type {@code CargoDetailReply} */
  public static final class CargoDetailReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoDetailReply)
      CargoDetailReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoDetailReply.newBuilder() to construct.
    private CargoDetailReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoDetailReply() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoDetailReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoDetailReply(
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
                com.cpdss.common.generated.CargoInfo.CargoDetail.Builder subBuilder = null;
                if (cargoDetail_ != null) {
                  subBuilder = cargoDetail_.toBuilder();
                }
                cargoDetail_ =
                    input.readMessage(
                        com.cpdss.common.generated.CargoInfo.CargoDetail.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(cargoDetail_);
                  cargoDetail_ = subBuilder.buildPartial();
                }

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
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.CargoInfo
          .internal_static_CargoDetailReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.CargoInfo.CargoDetailReply.class,
              com.cpdss.common.generated.CargoInfo.CargoDetailReply.Builder.class);
    }

    public static final int CARGODETAIL_FIELD_NUMBER = 1;
    private com.cpdss.common.generated.CargoInfo.CargoDetail cargoDetail_;
    /**
     * <code>.CargoDetail cargoDetail = 1;</code>
     *
     * @return Whether the cargoDetail field is set.
     */
    public boolean hasCargoDetail() {
      return cargoDetail_ != null;
    }
    /**
     * <code>.CargoDetail cargoDetail = 1;</code>
     *
     * @return The cargoDetail.
     */
    public com.cpdss.common.generated.CargoInfo.CargoDetail getCargoDetail() {
      return cargoDetail_ == null
          ? com.cpdss.common.generated.CargoInfo.CargoDetail.getDefaultInstance()
          : cargoDetail_;
    }
    /** <code>.CargoDetail cargoDetail = 1;</code> */
    public com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder getCargoDetailOrBuilder() {
      return getCargoDetail();
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
      if (cargoDetail_ != null) {
        output.writeMessage(1, getCargoDetail());
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
      if (cargoDetail_ != null) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getCargoDetail());
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
      if (!(obj instanceof com.cpdss.common.generated.CargoInfo.CargoDetailReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.CargoInfo.CargoDetailReply other =
          (com.cpdss.common.generated.CargoInfo.CargoDetailReply) obj;

      if (hasCargoDetail() != other.hasCargoDetail()) return false;
      if (hasCargoDetail()) {
        if (!getCargoDetail().equals(other.getCargoDetail())) return false;
      }
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
      if (hasCargoDetail()) {
        hash = (37 * hash) + CARGODETAIL_FIELD_NUMBER;
        hash = (53 * hash) + getCargoDetail().hashCode();
      }
      if (hasResponseStatus()) {
        hash = (37 * hash) + RESPONSESTATUS_FIELD_NUMBER;
        hash = (53 * hash) + getResponseStatus().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply parseFrom(
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
        com.cpdss.common.generated.CargoInfo.CargoDetailReply prototype) {
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
    /** Protobuf type {@code CargoDetailReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoDetailReply)
        com.cpdss.common.generated.CargoInfo.CargoDetailReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.CargoInfo
            .internal_static_CargoDetailReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.CargoInfo.CargoDetailReply.class,
                com.cpdss.common.generated.CargoInfo.CargoDetailReply.Builder.class);
      }

      // Construct using com.cpdss.common.generated.CargoInfo.CargoDetailReply.newBuilder()
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
        if (cargoDetailBuilder_ == null) {
          cargoDetail_ = null;
        } else {
          cargoDetail_ = null;
          cargoDetailBuilder_ = null;
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
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetailReply getDefaultInstanceForType() {
        return com.cpdss.common.generated.CargoInfo.CargoDetailReply.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetailReply build() {
        com.cpdss.common.generated.CargoInfo.CargoDetailReply result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetailReply buildPartial() {
        com.cpdss.common.generated.CargoInfo.CargoDetailReply result =
            new com.cpdss.common.generated.CargoInfo.CargoDetailReply(this);
        if (cargoDetailBuilder_ == null) {
          result.cargoDetail_ = cargoDetail_;
        } else {
          result.cargoDetail_ = cargoDetailBuilder_.build();
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
        if (other instanceof com.cpdss.common.generated.CargoInfo.CargoDetailReply) {
          return mergeFrom((com.cpdss.common.generated.CargoInfo.CargoDetailReply) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.CargoInfo.CargoDetailReply other) {
        if (other == com.cpdss.common.generated.CargoInfo.CargoDetailReply.getDefaultInstance())
          return this;
        if (other.hasCargoDetail()) {
          mergeCargoDetail(other.getCargoDetail());
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
        com.cpdss.common.generated.CargoInfo.CargoDetailReply parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.CargoInfo.CargoDetailReply) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private com.cpdss.common.generated.CargoInfo.CargoDetail cargoDetail_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.CargoInfo.CargoDetail,
              com.cpdss.common.generated.CargoInfo.CargoDetail.Builder,
              com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder>
          cargoDetailBuilder_;
      /**
       * <code>.CargoDetail cargoDetail = 1;</code>
       *
       * @return Whether the cargoDetail field is set.
       */
      public boolean hasCargoDetail() {
        return cargoDetailBuilder_ != null || cargoDetail_ != null;
      }
      /**
       * <code>.CargoDetail cargoDetail = 1;</code>
       *
       * @return The cargoDetail.
       */
      public com.cpdss.common.generated.CargoInfo.CargoDetail getCargoDetail() {
        if (cargoDetailBuilder_ == null) {
          return cargoDetail_ == null
              ? com.cpdss.common.generated.CargoInfo.CargoDetail.getDefaultInstance()
              : cargoDetail_;
        } else {
          return cargoDetailBuilder_.getMessage();
        }
      }
      /** <code>.CargoDetail cargoDetail = 1;</code> */
      public Builder setCargoDetail(com.cpdss.common.generated.CargoInfo.CargoDetail value) {
        if (cargoDetailBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          cargoDetail_ = value;
          onChanged();
        } else {
          cargoDetailBuilder_.setMessage(value);
        }

        return this;
      }
      /** <code>.CargoDetail cargoDetail = 1;</code> */
      public Builder setCargoDetail(
          com.cpdss.common.generated.CargoInfo.CargoDetail.Builder builderForValue) {
        if (cargoDetailBuilder_ == null) {
          cargoDetail_ = builderForValue.build();
          onChanged();
        } else {
          cargoDetailBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /** <code>.CargoDetail cargoDetail = 1;</code> */
      public Builder mergeCargoDetail(com.cpdss.common.generated.CargoInfo.CargoDetail value) {
        if (cargoDetailBuilder_ == null) {
          if (cargoDetail_ != null) {
            cargoDetail_ =
                com.cpdss.common.generated.CargoInfo.CargoDetail.newBuilder(cargoDetail_)
                    .mergeFrom(value)
                    .buildPartial();
          } else {
            cargoDetail_ = value;
          }
          onChanged();
        } else {
          cargoDetailBuilder_.mergeFrom(value);
        }

        return this;
      }
      /** <code>.CargoDetail cargoDetail = 1;</code> */
      public Builder clearCargoDetail() {
        if (cargoDetailBuilder_ == null) {
          cargoDetail_ = null;
          onChanged();
        } else {
          cargoDetail_ = null;
          cargoDetailBuilder_ = null;
        }

        return this;
      }
      /** <code>.CargoDetail cargoDetail = 1;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetail.Builder getCargoDetailBuilder() {

        onChanged();
        return getCargoDetailFieldBuilder().getBuilder();
      }
      /** <code>.CargoDetail cargoDetail = 1;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder getCargoDetailOrBuilder() {
        if (cargoDetailBuilder_ != null) {
          return cargoDetailBuilder_.getMessageOrBuilder();
        } else {
          return cargoDetail_ == null
              ? com.cpdss.common.generated.CargoInfo.CargoDetail.getDefaultInstance()
              : cargoDetail_;
        }
      }
      /** <code>.CargoDetail cargoDetail = 1;</code> */
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.CargoInfo.CargoDetail,
              com.cpdss.common.generated.CargoInfo.CargoDetail.Builder,
              com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder>
          getCargoDetailFieldBuilder() {
        if (cargoDetailBuilder_ == null) {
          cargoDetailBuilder_ =
              new com.google.protobuf.SingleFieldBuilderV3<
                  com.cpdss.common.generated.CargoInfo.CargoDetail,
                  com.cpdss.common.generated.CargoInfo.CargoDetail.Builder,
                  com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder>(
                  getCargoDetail(), getParentForChildren(), isClean());
          cargoDetail_ = null;
        }
        return cargoDetailBuilder_;
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

      // @@protoc_insertion_point(builder_scope:CargoDetailReply)
    }

    // @@protoc_insertion_point(class_scope:CargoDetailReply)
    private static final com.cpdss.common.generated.CargoInfo.CargoDetailReply DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.CargoInfo.CargoDetailReply();
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailReply getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoDetailReply> PARSER =
        new com.google.protobuf.AbstractParser<CargoDetailReply>() {
          @java.lang.Override
          public CargoDetailReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoDetailReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoDetailReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoDetailReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.CargoInfo.CargoDetailReply getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface CargoRequestOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoRequest)
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

    /**
     * <code>int64 loadableStudyId = 4;</code>
     *
     * @return The loadableStudyId.
     */
    long getLoadableStudyId();

    /**
     * <code>int64 cargoId = 5;</code>
     *
     * @return The cargoId.
     */
    long getCargoId();

    /**
     * <code>int32 page = 6;</code>
     *
     * @return The page.
     */
    int getPage();

    /**
     * <code>int32 pageSize = 7;</code>
     *
     * @return The pageSize.
     */
    int getPageSize();

    /**
     * <code>string sortBy = 8;</code>
     *
     * @return The sortBy.
     */
    java.lang.String getSortBy();
    /**
     * <code>string sortBy = 8;</code>
     *
     * @return The bytes for sortBy.
     */
    com.google.protobuf.ByteString getSortByBytes();

    /**
     * <code>string orderBy = 9;</code>
     *
     * @return The orderBy.
     */
    java.lang.String getOrderBy();
    /**
     * <code>string orderBy = 9;</code>
     *
     * @return The bytes for orderBy.
     */
    com.google.protobuf.ByteString getOrderByBytes();

    /** <code>repeated .Param param = 10;</code> */
    java.util.List<com.cpdss.common.generated.CargoInfo.Param> getParamList();
    /** <code>repeated .Param param = 10;</code> */
    com.cpdss.common.generated.CargoInfo.Param getParam(int index);
    /** <code>repeated .Param param = 10;</code> */
    int getParamCount();
    /** <code>repeated .Param param = 10;</code> */
    java.util.List<? extends com.cpdss.common.generated.CargoInfo.ParamOrBuilder>
        getParamOrBuilderList();
    /** <code>repeated .Param param = 10;</code> */
    com.cpdss.common.generated.CargoInfo.ParamOrBuilder getParamOrBuilder(int index);

    /**
     * <code>repeated int64 cargoXIds = 11;</code>
     *
     * @return A list containing the cargoXIds.
     */
    java.util.List<java.lang.Long> getCargoXIdsList();
    /**
     * <code>repeated int64 cargoXIds = 11;</code>
     *
     * @return The count of cargoXIds.
     */
    int getCargoXIdsCount();
    /**
     * <code>repeated int64 cargoXIds = 11;</code>
     *
     * @param index The index of the element to return.
     * @return The cargoXIds at the given index.
     */
    long getCargoXIds(int index);
  }
  /** Protobuf type {@code CargoRequest} */
  public static final class CargoRequest extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoRequest)
      CargoRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoRequest.newBuilder() to construct.
    private CargoRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoRequest() {
      sortBy_ = "";
      orderBy_ = "";
      param_ = java.util.Collections.emptyList();
      cargoXIds_ = emptyLongList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoRequest(
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
                loadableStudyId_ = input.readInt64();
                break;
              }
            case 40:
              {
                cargoId_ = input.readInt64();
                break;
              }
            case 48:
              {
                page_ = input.readInt32();
                break;
              }
            case 56:
              {
                pageSize_ = input.readInt32();
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                sortBy_ = s;
                break;
              }
            case 74:
              {
                java.lang.String s = input.readStringRequireUtf8();

                orderBy_ = s;
                break;
              }
            case 82:
              {
                if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                  param_ = new java.util.ArrayList<com.cpdss.common.generated.CargoInfo.Param>();
                  mutable_bitField0_ |= 0x00000001;
                }
                param_.add(
                    input.readMessage(
                        com.cpdss.common.generated.CargoInfo.Param.parser(), extensionRegistry));
                break;
              }
            case 88:
              {
                if (!((mutable_bitField0_ & 0x00000002) != 0)) {
                  cargoXIds_ = newLongList();
                  mutable_bitField0_ |= 0x00000002;
                }
                cargoXIds_.addLong(input.readInt64());
                break;
              }
            case 90:
              {
                int length = input.readRawVarint32();
                int limit = input.pushLimit(length);
                if (!((mutable_bitField0_ & 0x00000002) != 0) && input.getBytesUntilLimit() > 0) {
                  cargoXIds_ = newLongList();
                  mutable_bitField0_ |= 0x00000002;
                }
                while (input.getBytesUntilLimit() > 0) {
                  cargoXIds_.addLong(input.readInt64());
                }
                input.popLimit(limit);
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
          param_ = java.util.Collections.unmodifiableList(param_);
        }
        if (((mutable_bitField0_ & 0x00000002) != 0)) {
          cargoXIds_.makeImmutable(); // C
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.CargoInfo.CargoRequest.class,
              com.cpdss.common.generated.CargoInfo.CargoRequest.Builder.class);
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

    public static final int LOADABLESTUDYID_FIELD_NUMBER = 4;
    private long loadableStudyId_;
    /**
     * <code>int64 loadableStudyId = 4;</code>
     *
     * @return The loadableStudyId.
     */
    public long getLoadableStudyId() {
      return loadableStudyId_;
    }

    public static final int CARGOID_FIELD_NUMBER = 5;
    private long cargoId_;
    /**
     * <code>int64 cargoId = 5;</code>
     *
     * @return The cargoId.
     */
    public long getCargoId() {
      return cargoId_;
    }

    public static final int PAGE_FIELD_NUMBER = 6;
    private int page_;
    /**
     * <code>int32 page = 6;</code>
     *
     * @return The page.
     */
    public int getPage() {
      return page_;
    }

    public static final int PAGESIZE_FIELD_NUMBER = 7;
    private int pageSize_;
    /**
     * <code>int32 pageSize = 7;</code>
     *
     * @return The pageSize.
     */
    public int getPageSize() {
      return pageSize_;
    }

    public static final int SORTBY_FIELD_NUMBER = 8;
    private volatile java.lang.Object sortBy_;
    /**
     * <code>string sortBy = 8;</code>
     *
     * @return The sortBy.
     */
    public java.lang.String getSortBy() {
      java.lang.Object ref = sortBy_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        sortBy_ = s;
        return s;
      }
    }
    /**
     * <code>string sortBy = 8;</code>
     *
     * @return The bytes for sortBy.
     */
    public com.google.protobuf.ByteString getSortByBytes() {
      java.lang.Object ref = sortBy_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        sortBy_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ORDERBY_FIELD_NUMBER = 9;
    private volatile java.lang.Object orderBy_;
    /**
     * <code>string orderBy = 9;</code>
     *
     * @return The orderBy.
     */
    public java.lang.String getOrderBy() {
      java.lang.Object ref = orderBy_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        orderBy_ = s;
        return s;
      }
    }
    /**
     * <code>string orderBy = 9;</code>
     *
     * @return The bytes for orderBy.
     */
    public com.google.protobuf.ByteString getOrderByBytes() {
      java.lang.Object ref = orderBy_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        orderBy_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PARAM_FIELD_NUMBER = 10;
    private java.util.List<com.cpdss.common.generated.CargoInfo.Param> param_;
    /** <code>repeated .Param param = 10;</code> */
    public java.util.List<com.cpdss.common.generated.CargoInfo.Param> getParamList() {
      return param_;
    }
    /** <code>repeated .Param param = 10;</code> */
    public java.util.List<? extends com.cpdss.common.generated.CargoInfo.ParamOrBuilder>
        getParamOrBuilderList() {
      return param_;
    }
    /** <code>repeated .Param param = 10;</code> */
    public int getParamCount() {
      return param_.size();
    }
    /** <code>repeated .Param param = 10;</code> */
    public com.cpdss.common.generated.CargoInfo.Param getParam(int index) {
      return param_.get(index);
    }
    /** <code>repeated .Param param = 10;</code> */
    public com.cpdss.common.generated.CargoInfo.ParamOrBuilder getParamOrBuilder(int index) {
      return param_.get(index);
    }

    public static final int CARGOXIDS_FIELD_NUMBER = 11;
    private com.google.protobuf.Internal.LongList cargoXIds_;
    /**
     * <code>repeated int64 cargoXIds = 11;</code>
     *
     * @return A list containing the cargoXIds.
     */
    public java.util.List<java.lang.Long> getCargoXIdsList() {
      return cargoXIds_;
    }
    /**
     * <code>repeated int64 cargoXIds = 11;</code>
     *
     * @return The count of cargoXIds.
     */
    public int getCargoXIdsCount() {
      return cargoXIds_.size();
    }
    /**
     * <code>repeated int64 cargoXIds = 11;</code>
     *
     * @param index The index of the element to return.
     * @return The cargoXIds at the given index.
     */
    public long getCargoXIds(int index) {
      return cargoXIds_.getLong(index);
    }

    private int cargoXIdsMemoizedSerializedSize = -1;

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
      getSerializedSize();
      if (companyId_ != 0L) {
        output.writeInt64(1, companyId_);
      }
      if (vesselId_ != 0L) {
        output.writeInt64(2, vesselId_);
      }
      if (voyageId_ != 0L) {
        output.writeInt64(3, voyageId_);
      }
      if (loadableStudyId_ != 0L) {
        output.writeInt64(4, loadableStudyId_);
      }
      if (cargoId_ != 0L) {
        output.writeInt64(5, cargoId_);
      }
      if (page_ != 0) {
        output.writeInt32(6, page_);
      }
      if (pageSize_ != 0) {
        output.writeInt32(7, pageSize_);
      }
      if (!getSortByBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, sortBy_);
      }
      if (!getOrderByBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 9, orderBy_);
      }
      for (int i = 0; i < param_.size(); i++) {
        output.writeMessage(10, param_.get(i));
      }
      if (getCargoXIdsList().size() > 0) {
        output.writeUInt32NoTag(90);
        output.writeUInt32NoTag(cargoXIdsMemoizedSerializedSize);
      }
      for (int i = 0; i < cargoXIds_.size(); i++) {
        output.writeInt64NoTag(cargoXIds_.getLong(i));
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
      if (loadableStudyId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, loadableStudyId_);
      }
      if (cargoId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(5, cargoId_);
      }
      if (page_ != 0) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(6, page_);
      }
      if (pageSize_ != 0) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(7, pageSize_);
      }
      if (!getSortByBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, sortBy_);
      }
      if (!getOrderByBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, orderBy_);
      }
      for (int i = 0; i < param_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(10, param_.get(i));
      }
      {
        int dataSize = 0;
        for (int i = 0; i < cargoXIds_.size(); i++) {
          dataSize +=
              com.google.protobuf.CodedOutputStream.computeInt64SizeNoTag(cargoXIds_.getLong(i));
        }
        size += dataSize;
        if (!getCargoXIdsList().isEmpty()) {
          size += 1;
          size += com.google.protobuf.CodedOutputStream.computeInt32SizeNoTag(dataSize);
        }
        cargoXIdsMemoizedSerializedSize = dataSize;
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
      if (!(obj instanceof com.cpdss.common.generated.CargoInfo.CargoRequest)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.CargoInfo.CargoRequest other =
          (com.cpdss.common.generated.CargoInfo.CargoRequest) obj;

      if (getCompanyId() != other.getCompanyId()) return false;
      if (getVesselId() != other.getVesselId()) return false;
      if (getVoyageId() != other.getVoyageId()) return false;
      if (getLoadableStudyId() != other.getLoadableStudyId()) return false;
      if (getCargoId() != other.getCargoId()) return false;
      if (getPage() != other.getPage()) return false;
      if (getPageSize() != other.getPageSize()) return false;
      if (!getSortBy().equals(other.getSortBy())) return false;
      if (!getOrderBy().equals(other.getOrderBy())) return false;
      if (!getParamList().equals(other.getParamList())) return false;
      if (!getCargoXIdsList().equals(other.getCargoXIdsList())) return false;
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
      hash = (37 * hash) + LOADABLESTUDYID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLoadableStudyId());
      hash = (37 * hash) + CARGOID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoId());
      hash = (37 * hash) + PAGE_FIELD_NUMBER;
      hash = (53 * hash) + getPage();
      hash = (37 * hash) + PAGESIZE_FIELD_NUMBER;
      hash = (53 * hash) + getPageSize();
      hash = (37 * hash) + SORTBY_FIELD_NUMBER;
      hash = (53 * hash) + getSortBy().hashCode();
      hash = (37 * hash) + ORDERBY_FIELD_NUMBER;
      hash = (53 * hash) + getOrderBy().hashCode();
      if (getParamCount() > 0) {
        hash = (37 * hash) + PARAM_FIELD_NUMBER;
        hash = (53 * hash) + getParamList().hashCode();
      }
      if (getCargoXIdsCount() > 0) {
        hash = (37 * hash) + CARGOXIDS_FIELD_NUMBER;
        hash = (53 * hash) + getCargoXIdsList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.CargoInfo.CargoRequest prototype) {
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
    /** Protobuf type {@code CargoRequest} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoRequest)
        com.cpdss.common.generated.CargoInfo.CargoRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.CargoInfo.CargoRequest.class,
                com.cpdss.common.generated.CargoInfo.CargoRequest.Builder.class);
      }

      // Construct using com.cpdss.common.generated.CargoInfo.CargoRequest.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
          getParamFieldBuilder();
        }
      }

      @java.lang.Override
      public Builder clear() {
        super.clear();
        companyId_ = 0L;

        vesselId_ = 0L;

        voyageId_ = 0L;

        loadableStudyId_ = 0L;

        cargoId_ = 0L;

        page_ = 0;

        pageSize_ = 0;

        sortBy_ = "";

        orderBy_ = "";

        if (paramBuilder_ == null) {
          param_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          paramBuilder_.clear();
        }
        cargoXIds_ = emptyLongList();
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoRequest_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoRequest getDefaultInstanceForType() {
        return com.cpdss.common.generated.CargoInfo.CargoRequest.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoRequest build() {
        com.cpdss.common.generated.CargoInfo.CargoRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoRequest buildPartial() {
        com.cpdss.common.generated.CargoInfo.CargoRequest result =
            new com.cpdss.common.generated.CargoInfo.CargoRequest(this);
        int from_bitField0_ = bitField0_;
        result.companyId_ = companyId_;
        result.vesselId_ = vesselId_;
        result.voyageId_ = voyageId_;
        result.loadableStudyId_ = loadableStudyId_;
        result.cargoId_ = cargoId_;
        result.page_ = page_;
        result.pageSize_ = pageSize_;
        result.sortBy_ = sortBy_;
        result.orderBy_ = orderBy_;
        if (paramBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            param_ = java.util.Collections.unmodifiableList(param_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.param_ = param_;
        } else {
          result.param_ = paramBuilder_.build();
        }
        if (((bitField0_ & 0x00000002) != 0)) {
          cargoXIds_.makeImmutable();
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.cargoXIds_ = cargoXIds_;
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
        if (other instanceof com.cpdss.common.generated.CargoInfo.CargoRequest) {
          return mergeFrom((com.cpdss.common.generated.CargoInfo.CargoRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.CargoInfo.CargoRequest other) {
        if (other == com.cpdss.common.generated.CargoInfo.CargoRequest.getDefaultInstance())
          return this;
        if (other.getCompanyId() != 0L) {
          setCompanyId(other.getCompanyId());
        }
        if (other.getVesselId() != 0L) {
          setVesselId(other.getVesselId());
        }
        if (other.getVoyageId() != 0L) {
          setVoyageId(other.getVoyageId());
        }
        if (other.getLoadableStudyId() != 0L) {
          setLoadableStudyId(other.getLoadableStudyId());
        }
        if (other.getCargoId() != 0L) {
          setCargoId(other.getCargoId());
        }
        if (other.getPage() != 0) {
          setPage(other.getPage());
        }
        if (other.getPageSize() != 0) {
          setPageSize(other.getPageSize());
        }
        if (!other.getSortBy().isEmpty()) {
          sortBy_ = other.sortBy_;
          onChanged();
        }
        if (!other.getOrderBy().isEmpty()) {
          orderBy_ = other.orderBy_;
          onChanged();
        }
        if (paramBuilder_ == null) {
          if (!other.param_.isEmpty()) {
            if (param_.isEmpty()) {
              param_ = other.param_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureParamIsMutable();
              param_.addAll(other.param_);
            }
            onChanged();
          }
        } else {
          if (!other.param_.isEmpty()) {
            if (paramBuilder_.isEmpty()) {
              paramBuilder_.dispose();
              paramBuilder_ = null;
              param_ = other.param_;
              bitField0_ = (bitField0_ & ~0x00000001);
              paramBuilder_ =
                  com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                      ? getParamFieldBuilder()
                      : null;
            } else {
              paramBuilder_.addAllMessages(other.param_);
            }
          }
        }
        if (!other.cargoXIds_.isEmpty()) {
          if (cargoXIds_.isEmpty()) {
            cargoXIds_ = other.cargoXIds_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureCargoXIdsIsMutable();
            cargoXIds_.addAll(other.cargoXIds_);
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
        com.cpdss.common.generated.CargoInfo.CargoRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.CargoInfo.CargoRequest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int bitField0_;

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

      private long loadableStudyId_;
      /**
       * <code>int64 loadableStudyId = 4;</code>
       *
       * @return The loadableStudyId.
       */
      public long getLoadableStudyId() {
        return loadableStudyId_;
      }
      /**
       * <code>int64 loadableStudyId = 4;</code>
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
       * <code>int64 loadableStudyId = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLoadableStudyId() {

        loadableStudyId_ = 0L;
        onChanged();
        return this;
      }

      private long cargoId_;
      /**
       * <code>int64 cargoId = 5;</code>
       *
       * @return The cargoId.
       */
      public long getCargoId() {
        return cargoId_;
      }
      /**
       * <code>int64 cargoId = 5;</code>
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
       * <code>int64 cargoId = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoId() {

        cargoId_ = 0L;
        onChanged();
        return this;
      }

      private int page_;
      /**
       * <code>int32 page = 6;</code>
       *
       * @return The page.
       */
      public int getPage() {
        return page_;
      }
      /**
       * <code>int32 page = 6;</code>
       *
       * @param value The page to set.
       * @return This builder for chaining.
       */
      public Builder setPage(int value) {

        page_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 page = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPage() {

        page_ = 0;
        onChanged();
        return this;
      }

      private int pageSize_;
      /**
       * <code>int32 pageSize = 7;</code>
       *
       * @return The pageSize.
       */
      public int getPageSize() {
        return pageSize_;
      }
      /**
       * <code>int32 pageSize = 7;</code>
       *
       * @param value The pageSize to set.
       * @return This builder for chaining.
       */
      public Builder setPageSize(int value) {

        pageSize_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 pageSize = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPageSize() {

        pageSize_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object sortBy_ = "";
      /**
       * <code>string sortBy = 8;</code>
       *
       * @return The sortBy.
       */
      public java.lang.String getSortBy() {
        java.lang.Object ref = sortBy_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          sortBy_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string sortBy = 8;</code>
       *
       * @return The bytes for sortBy.
       */
      public com.google.protobuf.ByteString getSortByBytes() {
        java.lang.Object ref = sortBy_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          sortBy_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string sortBy = 8;</code>
       *
       * @param value The sortBy to set.
       * @return This builder for chaining.
       */
      public Builder setSortBy(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        sortBy_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string sortBy = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSortBy() {

        sortBy_ = getDefaultInstance().getSortBy();
        onChanged();
        return this;
      }
      /**
       * <code>string sortBy = 8;</code>
       *
       * @param value The bytes for sortBy to set.
       * @return This builder for chaining.
       */
      public Builder setSortByBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        sortBy_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object orderBy_ = "";
      /**
       * <code>string orderBy = 9;</code>
       *
       * @return The orderBy.
       */
      public java.lang.String getOrderBy() {
        java.lang.Object ref = orderBy_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          orderBy_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string orderBy = 9;</code>
       *
       * @return The bytes for orderBy.
       */
      public com.google.protobuf.ByteString getOrderByBytes() {
        java.lang.Object ref = orderBy_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          orderBy_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string orderBy = 9;</code>
       *
       * @param value The orderBy to set.
       * @return This builder for chaining.
       */
      public Builder setOrderBy(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        orderBy_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string orderBy = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearOrderBy() {

        orderBy_ = getDefaultInstance().getOrderBy();
        onChanged();
        return this;
      }
      /**
       * <code>string orderBy = 9;</code>
       *
       * @param value The bytes for orderBy to set.
       * @return This builder for chaining.
       */
      public Builder setOrderByBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        orderBy_ = value;
        onChanged();
        return this;
      }

      private java.util.List<com.cpdss.common.generated.CargoInfo.Param> param_ =
          java.util.Collections.emptyList();

      private void ensureParamIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          param_ = new java.util.ArrayList<com.cpdss.common.generated.CargoInfo.Param>(param_);
          bitField0_ |= 0x00000001;
        }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.CargoInfo.Param,
              com.cpdss.common.generated.CargoInfo.Param.Builder,
              com.cpdss.common.generated.CargoInfo.ParamOrBuilder>
          paramBuilder_;

      /** <code>repeated .Param param = 10;</code> */
      public java.util.List<com.cpdss.common.generated.CargoInfo.Param> getParamList() {
        if (paramBuilder_ == null) {
          return java.util.Collections.unmodifiableList(param_);
        } else {
          return paramBuilder_.getMessageList();
        }
      }
      /** <code>repeated .Param param = 10;</code> */
      public int getParamCount() {
        if (paramBuilder_ == null) {
          return param_.size();
        } else {
          return paramBuilder_.getCount();
        }
      }
      /** <code>repeated .Param param = 10;</code> */
      public com.cpdss.common.generated.CargoInfo.Param getParam(int index) {
        if (paramBuilder_ == null) {
          return param_.get(index);
        } else {
          return paramBuilder_.getMessage(index);
        }
      }
      /** <code>repeated .Param param = 10;</code> */
      public Builder setParam(int index, com.cpdss.common.generated.CargoInfo.Param value) {
        if (paramBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureParamIsMutable();
          param_.set(index, value);
          onChanged();
        } else {
          paramBuilder_.setMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .Param param = 10;</code> */
      public Builder setParam(
          int index, com.cpdss.common.generated.CargoInfo.Param.Builder builderForValue) {
        if (paramBuilder_ == null) {
          ensureParamIsMutable();
          param_.set(index, builderForValue.build());
          onChanged();
        } else {
          paramBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .Param param = 10;</code> */
      public Builder addParam(com.cpdss.common.generated.CargoInfo.Param value) {
        if (paramBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureParamIsMutable();
          param_.add(value);
          onChanged();
        } else {
          paramBuilder_.addMessage(value);
        }
        return this;
      }
      /** <code>repeated .Param param = 10;</code> */
      public Builder addParam(int index, com.cpdss.common.generated.CargoInfo.Param value) {
        if (paramBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureParamIsMutable();
          param_.add(index, value);
          onChanged();
        } else {
          paramBuilder_.addMessage(index, value);
        }
        return this;
      }
      /** <code>repeated .Param param = 10;</code> */
      public Builder addParam(com.cpdss.common.generated.CargoInfo.Param.Builder builderForValue) {
        if (paramBuilder_ == null) {
          ensureParamIsMutable();
          param_.add(builderForValue.build());
          onChanged();
        } else {
          paramBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .Param param = 10;</code> */
      public Builder addParam(
          int index, com.cpdss.common.generated.CargoInfo.Param.Builder builderForValue) {
        if (paramBuilder_ == null) {
          ensureParamIsMutable();
          param_.add(index, builderForValue.build());
          onChanged();
        } else {
          paramBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .Param param = 10;</code> */
      public Builder addAllParam(
          java.lang.Iterable<? extends com.cpdss.common.generated.CargoInfo.Param> values) {
        if (paramBuilder_ == null) {
          ensureParamIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(values, param_);
          onChanged();
        } else {
          paramBuilder_.addAllMessages(values);
        }
        return this;
      }
      /** <code>repeated .Param param = 10;</code> */
      public Builder clearParam() {
        if (paramBuilder_ == null) {
          param_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          paramBuilder_.clear();
        }
        return this;
      }
      /** <code>repeated .Param param = 10;</code> */
      public Builder removeParam(int index) {
        if (paramBuilder_ == null) {
          ensureParamIsMutable();
          param_.remove(index);
          onChanged();
        } else {
          paramBuilder_.remove(index);
        }
        return this;
      }
      /** <code>repeated .Param param = 10;</code> */
      public com.cpdss.common.generated.CargoInfo.Param.Builder getParamBuilder(int index) {
        return getParamFieldBuilder().getBuilder(index);
      }
      /** <code>repeated .Param param = 10;</code> */
      public com.cpdss.common.generated.CargoInfo.ParamOrBuilder getParamOrBuilder(int index) {
        if (paramBuilder_ == null) {
          return param_.get(index);
        } else {
          return paramBuilder_.getMessageOrBuilder(index);
        }
      }
      /** <code>repeated .Param param = 10;</code> */
      public java.util.List<? extends com.cpdss.common.generated.CargoInfo.ParamOrBuilder>
          getParamOrBuilderList() {
        if (paramBuilder_ != null) {
          return paramBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(param_);
        }
      }
      /** <code>repeated .Param param = 10;</code> */
      public com.cpdss.common.generated.CargoInfo.Param.Builder addParamBuilder() {
        return getParamFieldBuilder()
            .addBuilder(com.cpdss.common.generated.CargoInfo.Param.getDefaultInstance());
      }
      /** <code>repeated .Param param = 10;</code> */
      public com.cpdss.common.generated.CargoInfo.Param.Builder addParamBuilder(int index) {
        return getParamFieldBuilder()
            .addBuilder(index, com.cpdss.common.generated.CargoInfo.Param.getDefaultInstance());
      }
      /** <code>repeated .Param param = 10;</code> */
      public java.util.List<com.cpdss.common.generated.CargoInfo.Param.Builder>
          getParamBuilderList() {
        return getParamFieldBuilder().getBuilderList();
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.CargoInfo.Param,
              com.cpdss.common.generated.CargoInfo.Param.Builder,
              com.cpdss.common.generated.CargoInfo.ParamOrBuilder>
          getParamFieldBuilder() {
        if (paramBuilder_ == null) {
          paramBuilder_ =
              new com.google.protobuf.RepeatedFieldBuilderV3<
                  com.cpdss.common.generated.CargoInfo.Param,
                  com.cpdss.common.generated.CargoInfo.Param.Builder,
                  com.cpdss.common.generated.CargoInfo.ParamOrBuilder>(
                  param_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
          param_ = null;
        }
        return paramBuilder_;
      }

      private com.google.protobuf.Internal.LongList cargoXIds_ = emptyLongList();

      private void ensureCargoXIdsIsMutable() {
        if (!((bitField0_ & 0x00000002) != 0)) {
          cargoXIds_ = mutableCopy(cargoXIds_);
          bitField0_ |= 0x00000002;
        }
      }
      /**
       * <code>repeated int64 cargoXIds = 11;</code>
       *
       * @return A list containing the cargoXIds.
       */
      public java.util.List<java.lang.Long> getCargoXIdsList() {
        return ((bitField0_ & 0x00000002) != 0)
            ? java.util.Collections.unmodifiableList(cargoXIds_)
            : cargoXIds_;
      }
      /**
       * <code>repeated int64 cargoXIds = 11;</code>
       *
       * @return The count of cargoXIds.
       */
      public int getCargoXIdsCount() {
        return cargoXIds_.size();
      }
      /**
       * <code>repeated int64 cargoXIds = 11;</code>
       *
       * @param index The index of the element to return.
       * @return The cargoXIds at the given index.
       */
      public long getCargoXIds(int index) {
        return cargoXIds_.getLong(index);
      }
      /**
       * <code>repeated int64 cargoXIds = 11;</code>
       *
       * @param index The index to set the value at.
       * @param value The cargoXIds to set.
       * @return This builder for chaining.
       */
      public Builder setCargoXIds(int index, long value) {
        ensureCargoXIdsIsMutable();
        cargoXIds_.setLong(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int64 cargoXIds = 11;</code>
       *
       * @param value The cargoXIds to add.
       * @return This builder for chaining.
       */
      public Builder addCargoXIds(long value) {
        ensureCargoXIdsIsMutable();
        cargoXIds_.addLong(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int64 cargoXIds = 11;</code>
       *
       * @param values The cargoXIds to add.
       * @return This builder for chaining.
       */
      public Builder addAllCargoXIds(java.lang.Iterable<? extends java.lang.Long> values) {
        ensureCargoXIdsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, cargoXIds_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int64 cargoXIds = 11;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoXIds() {
        cargoXIds_ = emptyLongList();
        bitField0_ = (bitField0_ & ~0x00000002);
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

      // @@protoc_insertion_point(builder_scope:CargoRequest)
    }

    // @@protoc_insertion_point(class_scope:CargoRequest)
    private static final com.cpdss.common.generated.CargoInfo.CargoRequest DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.CargoInfo.CargoRequest();
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoRequest> PARSER =
        new com.google.protobuf.AbstractParser<CargoRequest>() {
          @java.lang.Override
          public CargoRequest parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoRequest(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.CargoInfo.CargoRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface ParamOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:Param)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string key = 1;</code>
     *
     * @return The key.
     */
    java.lang.String getKey();
    /**
     * <code>string key = 1;</code>
     *
     * @return The bytes for key.
     */
    com.google.protobuf.ByteString getKeyBytes();

    /**
     * <code>string value = 2;</code>
     *
     * @return The value.
     */
    java.lang.String getValue();
    /**
     * <code>string value = 2;</code>
     *
     * @return The bytes for value.
     */
    com.google.protobuf.ByteString getValueBytes();
  }
  /** Protobuf type {@code Param} */
  public static final class Param extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:Param)
      ParamOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use Param.newBuilder() to construct.
    private Param(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private Param() {
      key_ = "";
      value_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new Param();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private Param(
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

                key_ = s;
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                value_ = s;
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
      return com.cpdss.common.generated.CargoInfo.internal_static_Param_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.CargoInfo.internal_static_Param_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.CargoInfo.Param.class,
              com.cpdss.common.generated.CargoInfo.Param.Builder.class);
    }

    public static final int KEY_FIELD_NUMBER = 1;
    private volatile java.lang.Object key_;
    /**
     * <code>string key = 1;</code>
     *
     * @return The key.
     */
    public java.lang.String getKey() {
      java.lang.Object ref = key_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        key_ = s;
        return s;
      }
    }
    /**
     * <code>string key = 1;</code>
     *
     * @return The bytes for key.
     */
    public com.google.protobuf.ByteString getKeyBytes() {
      java.lang.Object ref = key_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        key_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int VALUE_FIELD_NUMBER = 2;
    private volatile java.lang.Object value_;
    /**
     * <code>string value = 2;</code>
     *
     * @return The value.
     */
    public java.lang.String getValue() {
      java.lang.Object ref = value_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        value_ = s;
        return s;
      }
    }
    /**
     * <code>string value = 2;</code>
     *
     * @return The bytes for value.
     */
    public com.google.protobuf.ByteString getValueBytes() {
      java.lang.Object ref = value_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        value_ = b;
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
      if (!getKeyBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, key_);
      }
      if (!getValueBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, value_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getKeyBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, key_);
      }
      if (!getValueBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, value_);
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
      if (!(obj instanceof com.cpdss.common.generated.CargoInfo.Param)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.CargoInfo.Param other =
          (com.cpdss.common.generated.CargoInfo.Param) obj;

      if (!getKey().equals(other.getKey())) return false;
      if (!getValue().equals(other.getValue())) return false;
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
      hash = (37 * hash) + KEY_FIELD_NUMBER;
      hash = (53 * hash) + getKey().hashCode();
      hash = (37 * hash) + VALUE_FIELD_NUMBER;
      hash = (53 * hash) + getValue().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseFrom(java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.Param parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.CargoInfo.Param prototype) {
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
    /** Protobuf type {@code Param} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:Param)
        com.cpdss.common.generated.CargoInfo.ParamOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.CargoInfo.internal_static_Param_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.CargoInfo.internal_static_Param_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.CargoInfo.Param.class,
                com.cpdss.common.generated.CargoInfo.Param.Builder.class);
      }

      // Construct using com.cpdss.common.generated.CargoInfo.Param.newBuilder()
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
        key_ = "";

        value_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.CargoInfo.internal_static_Param_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.Param getDefaultInstanceForType() {
        return com.cpdss.common.generated.CargoInfo.Param.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.Param build() {
        com.cpdss.common.generated.CargoInfo.Param result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.Param buildPartial() {
        com.cpdss.common.generated.CargoInfo.Param result =
            new com.cpdss.common.generated.CargoInfo.Param(this);
        result.key_ = key_;
        result.value_ = value_;
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
        if (other instanceof com.cpdss.common.generated.CargoInfo.Param) {
          return mergeFrom((com.cpdss.common.generated.CargoInfo.Param) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.CargoInfo.Param other) {
        if (other == com.cpdss.common.generated.CargoInfo.Param.getDefaultInstance()) return this;
        if (!other.getKey().isEmpty()) {
          key_ = other.key_;
          onChanged();
        }
        if (!other.getValue().isEmpty()) {
          value_ = other.value_;
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
        com.cpdss.common.generated.CargoInfo.Param parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.cpdss.common.generated.CargoInfo.Param) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object key_ = "";
      /**
       * <code>string key = 1;</code>
       *
       * @return The key.
       */
      public java.lang.String getKey() {
        java.lang.Object ref = key_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          key_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string key = 1;</code>
       *
       * @return The bytes for key.
       */
      public com.google.protobuf.ByteString getKeyBytes() {
        java.lang.Object ref = key_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          key_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string key = 1;</code>
       *
       * @param value The key to set.
       * @return This builder for chaining.
       */
      public Builder setKey(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        key_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string key = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearKey() {

        key_ = getDefaultInstance().getKey();
        onChanged();
        return this;
      }
      /**
       * <code>string key = 1;</code>
       *
       * @param value The bytes for key to set.
       * @return This builder for chaining.
       */
      public Builder setKeyBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        key_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object value_ = "";
      /**
       * <code>string value = 2;</code>
       *
       * @return The value.
       */
      public java.lang.String getValue() {
        java.lang.Object ref = value_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          value_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string value = 2;</code>
       *
       * @return The bytes for value.
       */
      public com.google.protobuf.ByteString getValueBytes() {
        java.lang.Object ref = value_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          value_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string value = 2;</code>
       *
       * @param value The value to set.
       * @return This builder for chaining.
       */
      public Builder setValue(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        value_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string value = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearValue() {

        value_ = getDefaultInstance().getValue();
        onChanged();
        return this;
      }
      /**
       * <code>string value = 2;</code>
       *
       * @param value The bytes for value to set.
       * @return This builder for chaining.
       */
      public Builder setValueBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        value_ = value;
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

      // @@protoc_insertion_point(builder_scope:Param)
    }

    // @@protoc_insertion_point(class_scope:Param)
    private static final com.cpdss.common.generated.CargoInfo.Param DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.CargoInfo.Param();
    }

    public static com.cpdss.common.generated.CargoInfo.Param getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Param> PARSER =
        new com.google.protobuf.AbstractParser<Param>() {
          @java.lang.Override
          public Param parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new Param(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<Param> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Param> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.CargoInfo.Param getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface CargoRequestWithPagingOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoRequestWithPaging)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 offset = 1;</code>
     *
     * @return The offset.
     */
    long getOffset();

    /**
     * <code>int64 limit = 2;</code>
     *
     * @return The limit.
     */
    long getLimit();
  }
  /** Protobuf type {@code CargoRequestWithPaging} */
  public static final class CargoRequestWithPaging extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoRequestWithPaging)
      CargoRequestWithPagingOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoRequestWithPaging.newBuilder() to construct.
    private CargoRequestWithPaging(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoRequestWithPaging() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoRequestWithPaging();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoRequestWithPaging(
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
                offset_ = input.readInt64();
                break;
              }
            case 16:
              {
                limit_ = input.readInt64();
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
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoRequestWithPaging_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.CargoInfo
          .internal_static_CargoRequestWithPaging_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging.class,
              com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging.Builder.class);
    }

    public static final int OFFSET_FIELD_NUMBER = 1;
    private long offset_;
    /**
     * <code>int64 offset = 1;</code>
     *
     * @return The offset.
     */
    public long getOffset() {
      return offset_;
    }

    public static final int LIMIT_FIELD_NUMBER = 2;
    private long limit_;
    /**
     * <code>int64 limit = 2;</code>
     *
     * @return The limit.
     */
    public long getLimit() {
      return limit_;
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
      if (offset_ != 0L) {
        output.writeInt64(1, offset_);
      }
      if (limit_ != 0L) {
        output.writeInt64(2, limit_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (offset_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, offset_);
      }
      if (limit_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, limit_);
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
      if (!(obj instanceof com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging other =
          (com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging) obj;

      if (getOffset() != other.getOffset()) return false;
      if (getLimit() != other.getLimit()) return false;
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
      hash = (37 * hash) + OFFSET_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getOffset());
      hash = (37 * hash) + LIMIT_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getLimit());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parseFrom(
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
        com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging prototype) {
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
    /** Protobuf type {@code CargoRequestWithPaging} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoRequestWithPaging)
        com.cpdss.common.generated.CargoInfo.CargoRequestWithPagingOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.CargoInfo
            .internal_static_CargoRequestWithPaging_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.CargoInfo
            .internal_static_CargoRequestWithPaging_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging.class,
                com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging.Builder.class);
      }

      // Construct using com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging.newBuilder()
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
        offset_ = 0L;

        limit_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.CargoInfo
            .internal_static_CargoRequestWithPaging_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging build() {
        com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging buildPartial() {
        com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging result =
            new com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging(this);
        result.offset_ = offset_;
        result.limit_ = limit_;
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
        if (other instanceof com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging) {
          return mergeFrom((com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging other) {
        if (other
            == com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging.getDefaultInstance())
          return this;
        if (other.getOffset() != 0L) {
          setOffset(other.getOffset());
        }
        if (other.getLimit() != 0L) {
          setLimit(other.getLimit());
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
        com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging)
                  e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long offset_;
      /**
       * <code>int64 offset = 1;</code>
       *
       * @return The offset.
       */
      public long getOffset() {
        return offset_;
      }
      /**
       * <code>int64 offset = 1;</code>
       *
       * @param value The offset to set.
       * @return This builder for chaining.
       */
      public Builder setOffset(long value) {

        offset_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 offset = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearOffset() {

        offset_ = 0L;
        onChanged();
        return this;
      }

      private long limit_;
      /**
       * <code>int64 limit = 2;</code>
       *
       * @return The limit.
       */
      public long getLimit() {
        return limit_;
      }
      /**
       * <code>int64 limit = 2;</code>
       *
       * @param value The limit to set.
       * @return This builder for chaining.
       */
      public Builder setLimit(long value) {

        limit_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 limit = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearLimit() {

        limit_ = 0L;
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

      // @@protoc_insertion_point(builder_scope:CargoRequestWithPaging)
    }

    // @@protoc_insertion_point(class_scope:CargoRequestWithPaging)
    private static final com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging();
    }

    public static com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoRequestWithPaging> PARSER =
        new com.google.protobuf.AbstractParser<CargoRequestWithPaging>() {
          @java.lang.Override
          public CargoRequestWithPaging parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoRequestWithPaging(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoRequestWithPaging> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoRequestWithPaging> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface CargoDetailOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoDetail)
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
  /** Protobuf type {@code CargoDetail} */
  public static final class CargoDetail extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoDetail)
      CargoDetailOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoDetail.newBuilder() to construct.
    private CargoDetail(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoDetail() {
      crudeType_ = "";
      abbreviation_ = "";
      api_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoDetail();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoDetail(
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
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetail_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetail_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.CargoInfo.CargoDetail.class,
              com.cpdss.common.generated.CargoInfo.CargoDetail.Builder.class);
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
      if (!(obj instanceof com.cpdss.common.generated.CargoInfo.CargoDetail)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.CargoInfo.CargoDetail other =
          (com.cpdss.common.generated.CargoInfo.CargoDetail) obj;

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

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.CargoInfo.CargoDetail prototype) {
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
    /** Protobuf type {@code CargoDetail} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoDetail)
        com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetail_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetail_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.CargoInfo.CargoDetail.class,
                com.cpdss.common.generated.CargoInfo.CargoDetail.Builder.class);
      }

      // Construct using com.cpdss.common.generated.CargoInfo.CargoDetail.newBuilder()
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
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetail_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetail getDefaultInstanceForType() {
        return com.cpdss.common.generated.CargoInfo.CargoDetail.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetail build() {
        com.cpdss.common.generated.CargoInfo.CargoDetail result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetail buildPartial() {
        com.cpdss.common.generated.CargoInfo.CargoDetail result =
            new com.cpdss.common.generated.CargoInfo.CargoDetail(this);
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
        if (other instanceof com.cpdss.common.generated.CargoInfo.CargoDetail) {
          return mergeFrom((com.cpdss.common.generated.CargoInfo.CargoDetail) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.CargoInfo.CargoDetail other) {
        if (other == com.cpdss.common.generated.CargoInfo.CargoDetail.getDefaultInstance())
          return this;
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
        com.cpdss.common.generated.CargoInfo.CargoDetail parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.CargoInfo.CargoDetail) e.getUnfinishedMessage();
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

      // @@protoc_insertion_point(builder_scope:CargoDetail)
    }

    // @@protoc_insertion_point(class_scope:CargoDetail)
    private static final com.cpdss.common.generated.CargoInfo.CargoDetail DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.CargoInfo.CargoDetail();
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetail getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoDetail> PARSER =
        new com.google.protobuf.AbstractParser<CargoDetail>() {
          @java.lang.Override
          public CargoDetail parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoDetail(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoDetail> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoDetail> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.CargoInfo.CargoDetail getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface CargoReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoReply)
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

    /** <code>repeated .CargoDetail cargos = 2;</code> */
    java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetail> getCargosList();
    /** <code>repeated .CargoDetail cargos = 2;</code> */
    com.cpdss.common.generated.CargoInfo.CargoDetail getCargos(int index);
    /** <code>repeated .CargoDetail cargos = 2;</code> */
    int getCargosCount();
    /** <code>repeated .CargoDetail cargos = 2;</code> */
    java.util.List<? extends com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder>
        getCargosOrBuilderList();
    /** <code>repeated .CargoDetail cargos = 2;</code> */
    com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder getCargosOrBuilder(int index);
  }
  /** Protobuf type {@code CargoReply} */
  public static final class CargoReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoReply)
      CargoReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoReply.newBuilder() to construct.
    private CargoReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoReply() {
      cargos_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoReply(
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
                  cargos_ =
                      new java.util.ArrayList<com.cpdss.common.generated.CargoInfo.CargoDetail>();
                  mutable_bitField0_ |= 0x00000001;
                }
                cargos_.add(
                    input.readMessage(
                        com.cpdss.common.generated.CargoInfo.CargoDetail.parser(),
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
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.CargoInfo.CargoReply.class,
              com.cpdss.common.generated.CargoInfo.CargoReply.Builder.class);
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

    public static final int CARGOS_FIELD_NUMBER = 2;
    private java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetail> cargos_;
    /** <code>repeated .CargoDetail cargos = 2;</code> */
    public java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetail> getCargosList() {
      return cargos_;
    }
    /** <code>repeated .CargoDetail cargos = 2;</code> */
    public java.util.List<? extends com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder>
        getCargosOrBuilderList() {
      return cargos_;
    }
    /** <code>repeated .CargoDetail cargos = 2;</code> */
    public int getCargosCount() {
      return cargos_.size();
    }
    /** <code>repeated .CargoDetail cargos = 2;</code> */
    public com.cpdss.common.generated.CargoInfo.CargoDetail getCargos(int index) {
      return cargos_.get(index);
    }
    /** <code>repeated .CargoDetail cargos = 2;</code> */
    public com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder getCargosOrBuilder(int index) {
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
      if (responseStatus_ != null) {
        output.writeMessage(1, getResponseStatus());
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
      if (responseStatus_ != null) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getResponseStatus());
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
      if (!(obj instanceof com.cpdss.common.generated.CargoInfo.CargoReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.CargoInfo.CargoReply other =
          (com.cpdss.common.generated.CargoInfo.CargoReply) obj;

      if (hasResponseStatus() != other.hasResponseStatus()) return false;
      if (hasResponseStatus()) {
        if (!getResponseStatus().equals(other.getResponseStatus())) return false;
      }
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
      if (hasResponseStatus()) {
        hash = (37 * hash) + RESPONSESTATUS_FIELD_NUMBER;
        hash = (53 * hash) + getResponseStatus().hashCode();
      }
      if (getCargosCount() > 0) {
        hash = (37 * hash) + CARGOS_FIELD_NUMBER;
        hash = (53 * hash) + getCargosList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.CargoInfo.CargoReply prototype) {
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
    /** Protobuf type {@code CargoReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoReply)
        com.cpdss.common.generated.CargoInfo.CargoReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.CargoInfo.CargoReply.class,
                com.cpdss.common.generated.CargoInfo.CargoReply.Builder.class);
      }

      // Construct using com.cpdss.common.generated.CargoInfo.CargoReply.newBuilder()
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
        if (responseStatusBuilder_ == null) {
          responseStatus_ = null;
        } else {
          responseStatus_ = null;
          responseStatusBuilder_ = null;
        }
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
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoReply getDefaultInstanceForType() {
        return com.cpdss.common.generated.CargoInfo.CargoReply.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoReply build() {
        com.cpdss.common.generated.CargoInfo.CargoReply result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoReply buildPartial() {
        com.cpdss.common.generated.CargoInfo.CargoReply result =
            new com.cpdss.common.generated.CargoInfo.CargoReply(this);
        int from_bitField0_ = bitField0_;
        if (responseStatusBuilder_ == null) {
          result.responseStatus_ = responseStatus_;
        } else {
          result.responseStatus_ = responseStatusBuilder_.build();
        }
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
        if (other instanceof com.cpdss.common.generated.CargoInfo.CargoReply) {
          return mergeFrom((com.cpdss.common.generated.CargoInfo.CargoReply) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.CargoInfo.CargoReply other) {
        if (other == com.cpdss.common.generated.CargoInfo.CargoReply.getDefaultInstance())
          return this;
        if (other.hasResponseStatus()) {
          mergeResponseStatus(other.getResponseStatus());
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
        com.cpdss.common.generated.CargoInfo.CargoReply parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.CargoInfo.CargoReply) e.getUnfinishedMessage();
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

      private java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetail> cargos_ =
          java.util.Collections.emptyList();

      private void ensureCargosIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          cargos_ =
              new java.util.ArrayList<com.cpdss.common.generated.CargoInfo.CargoDetail>(cargos_);
          bitField0_ |= 0x00000001;
        }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.CargoInfo.CargoDetail,
              com.cpdss.common.generated.CargoInfo.CargoDetail.Builder,
              com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder>
          cargosBuilder_;

      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetail> getCargosList() {
        if (cargosBuilder_ == null) {
          return java.util.Collections.unmodifiableList(cargos_);
        } else {
          return cargosBuilder_.getMessageList();
        }
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public int getCargosCount() {
        if (cargosBuilder_ == null) {
          return cargos_.size();
        } else {
          return cargosBuilder_.getCount();
        }
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetail getCargos(int index) {
        if (cargosBuilder_ == null) {
          return cargos_.get(index);
        } else {
          return cargosBuilder_.getMessage(index);
        }
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public Builder setCargos(int index, com.cpdss.common.generated.CargoInfo.CargoDetail value) {
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
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public Builder setCargos(
          int index, com.cpdss.common.generated.CargoInfo.CargoDetail.Builder builderForValue) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          cargos_.set(index, builderForValue.build());
          onChanged();
        } else {
          cargosBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public Builder addCargos(com.cpdss.common.generated.CargoInfo.CargoDetail value) {
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
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public Builder addCargos(int index, com.cpdss.common.generated.CargoInfo.CargoDetail value) {
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
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public Builder addCargos(
          com.cpdss.common.generated.CargoInfo.CargoDetail.Builder builderForValue) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          cargos_.add(builderForValue.build());
          onChanged();
        } else {
          cargosBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public Builder addCargos(
          int index, com.cpdss.common.generated.CargoInfo.CargoDetail.Builder builderForValue) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          cargos_.add(index, builderForValue.build());
          onChanged();
        } else {
          cargosBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public Builder addAllCargos(
          java.lang.Iterable<? extends com.cpdss.common.generated.CargoInfo.CargoDetail> values) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(values, cargos_);
          onChanged();
        } else {
          cargosBuilder_.addAllMessages(values);
        }
        return this;
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
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
      /** <code>repeated .CargoDetail cargos = 2;</code> */
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
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetail.Builder getCargosBuilder(int index) {
        return getCargosFieldBuilder().getBuilder(index);
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder getCargosOrBuilder(
          int index) {
        if (cargosBuilder_ == null) {
          return cargos_.get(index);
        } else {
          return cargosBuilder_.getMessageOrBuilder(index);
        }
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public java.util.List<? extends com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder>
          getCargosOrBuilderList() {
        if (cargosBuilder_ != null) {
          return cargosBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(cargos_);
        }
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetail.Builder addCargosBuilder() {
        return getCargosFieldBuilder()
            .addBuilder(com.cpdss.common.generated.CargoInfo.CargoDetail.getDefaultInstance());
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetail.Builder addCargosBuilder(int index) {
        return getCargosFieldBuilder()
            .addBuilder(
                index, com.cpdss.common.generated.CargoInfo.CargoDetail.getDefaultInstance());
      }
      /** <code>repeated .CargoDetail cargos = 2;</code> */
      public java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetail.Builder>
          getCargosBuilderList() {
        return getCargosFieldBuilder().getBuilderList();
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.CargoInfo.CargoDetail,
              com.cpdss.common.generated.CargoInfo.CargoDetail.Builder,
              com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder>
          getCargosFieldBuilder() {
        if (cargosBuilder_ == null) {
          cargosBuilder_ =
              new com.google.protobuf.RepeatedFieldBuilderV3<
                  com.cpdss.common.generated.CargoInfo.CargoDetail,
                  com.cpdss.common.generated.CargoInfo.CargoDetail.Builder,
                  com.cpdss.common.generated.CargoInfo.CargoDetailOrBuilder>(
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

      // @@protoc_insertion_point(builder_scope:CargoReply)
    }

    // @@protoc_insertion_point(class_scope:CargoReply)
    private static final com.cpdss.common.generated.CargoInfo.CargoReply DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.CargoInfo.CargoReply();
    }

    public static com.cpdss.common.generated.CargoInfo.CargoReply getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoReply> PARSER =
        new com.google.protobuf.AbstractParser<CargoReply>() {
          @java.lang.Override
          public CargoReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.CargoInfo.CargoReply getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface CargoDetailedReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoDetailedReply)
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

    /** <code>repeated .CargoDetailed cargos = 2;</code> */
    java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetailed> getCargosList();
    /** <code>repeated .CargoDetailed cargos = 2;</code> */
    com.cpdss.common.generated.CargoInfo.CargoDetailed getCargos(int index);
    /** <code>repeated .CargoDetailed cargos = 2;</code> */
    int getCargosCount();
    /** <code>repeated .CargoDetailed cargos = 2;</code> */
    java.util.List<? extends com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder>
        getCargosOrBuilderList();
    /** <code>repeated .CargoDetailed cargos = 2;</code> */
    com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder getCargosOrBuilder(int index);

    /**
     * <code>int64 totalElements = 3;</code>
     *
     * @return The totalElements.
     */
    long getTotalElements();
  }
  /** Protobuf type {@code CargoDetailedReply} */
  public static final class CargoDetailedReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoDetailedReply)
      CargoDetailedReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoDetailedReply.newBuilder() to construct.
    private CargoDetailedReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoDetailedReply() {
      cargos_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoDetailedReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoDetailedReply(
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
                  cargos_ =
                      new java.util.ArrayList<com.cpdss.common.generated.CargoInfo.CargoDetailed>();
                  mutable_bitField0_ |= 0x00000001;
                }
                cargos_.add(
                    input.readMessage(
                        com.cpdss.common.generated.CargoInfo.CargoDetailed.parser(),
                        extensionRegistry));
                break;
              }
            case 24:
              {
                totalElements_ = input.readInt64();
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
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailedReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.CargoInfo
          .internal_static_CargoDetailedReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.CargoInfo.CargoDetailedReply.class,
              com.cpdss.common.generated.CargoInfo.CargoDetailedReply.Builder.class);
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

    public static final int CARGOS_FIELD_NUMBER = 2;
    private java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetailed> cargos_;
    /** <code>repeated .CargoDetailed cargos = 2;</code> */
    public java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetailed> getCargosList() {
      return cargos_;
    }
    /** <code>repeated .CargoDetailed cargos = 2;</code> */
    public java.util.List<? extends com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder>
        getCargosOrBuilderList() {
      return cargos_;
    }
    /** <code>repeated .CargoDetailed cargos = 2;</code> */
    public int getCargosCount() {
      return cargos_.size();
    }
    /** <code>repeated .CargoDetailed cargos = 2;</code> */
    public com.cpdss.common.generated.CargoInfo.CargoDetailed getCargos(int index) {
      return cargos_.get(index);
    }
    /** <code>repeated .CargoDetailed cargos = 2;</code> */
    public com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder getCargosOrBuilder(
        int index) {
      return cargos_.get(index);
    }

    public static final int TOTALELEMENTS_FIELD_NUMBER = 3;
    private long totalElements_;
    /**
     * <code>int64 totalElements = 3;</code>
     *
     * @return The totalElements.
     */
    public long getTotalElements() {
      return totalElements_;
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
      for (int i = 0; i < cargos_.size(); i++) {
        output.writeMessage(2, cargos_.get(i));
      }
      if (totalElements_ != 0L) {
        output.writeInt64(3, totalElements_);
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
      for (int i = 0; i < cargos_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, cargos_.get(i));
      }
      if (totalElements_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, totalElements_);
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
      if (!(obj instanceof com.cpdss.common.generated.CargoInfo.CargoDetailedReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.CargoInfo.CargoDetailedReply other =
          (com.cpdss.common.generated.CargoInfo.CargoDetailedReply) obj;

      if (hasResponseStatus() != other.hasResponseStatus()) return false;
      if (hasResponseStatus()) {
        if (!getResponseStatus().equals(other.getResponseStatus())) return false;
      }
      if (!getCargosList().equals(other.getCargosList())) return false;
      if (getTotalElements() != other.getTotalElements()) return false;
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
      if (getCargosCount() > 0) {
        hash = (37 * hash) + CARGOS_FIELD_NUMBER;
        hash = (53 * hash) + getCargosList().hashCode();
      }
      hash = (37 * hash) + TOTALELEMENTS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getTotalElements());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply parseFrom(
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
        com.cpdss.common.generated.CargoInfo.CargoDetailedReply prototype) {
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
    /** Protobuf type {@code CargoDetailedReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoDetailedReply)
        com.cpdss.common.generated.CargoInfo.CargoDetailedReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailedReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.CargoInfo
            .internal_static_CargoDetailedReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.CargoInfo.CargoDetailedReply.class,
                com.cpdss.common.generated.CargoInfo.CargoDetailedReply.Builder.class);
      }

      // Construct using com.cpdss.common.generated.CargoInfo.CargoDetailedReply.newBuilder()
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
        if (responseStatusBuilder_ == null) {
          responseStatus_ = null;
        } else {
          responseStatus_ = null;
          responseStatusBuilder_ = null;
        }
        if (cargosBuilder_ == null) {
          cargos_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          cargosBuilder_.clear();
        }
        totalElements_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailedReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetailedReply getDefaultInstanceForType() {
        return com.cpdss.common.generated.CargoInfo.CargoDetailedReply.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetailedReply build() {
        com.cpdss.common.generated.CargoInfo.CargoDetailedReply result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetailedReply buildPartial() {
        com.cpdss.common.generated.CargoInfo.CargoDetailedReply result =
            new com.cpdss.common.generated.CargoInfo.CargoDetailedReply(this);
        int from_bitField0_ = bitField0_;
        if (responseStatusBuilder_ == null) {
          result.responseStatus_ = responseStatus_;
        } else {
          result.responseStatus_ = responseStatusBuilder_.build();
        }
        if (cargosBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            cargos_ = java.util.Collections.unmodifiableList(cargos_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.cargos_ = cargos_;
        } else {
          result.cargos_ = cargosBuilder_.build();
        }
        result.totalElements_ = totalElements_;
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
        if (other instanceof com.cpdss.common.generated.CargoInfo.CargoDetailedReply) {
          return mergeFrom((com.cpdss.common.generated.CargoInfo.CargoDetailedReply) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.CargoInfo.CargoDetailedReply other) {
        if (other == com.cpdss.common.generated.CargoInfo.CargoDetailedReply.getDefaultInstance())
          return this;
        if (other.hasResponseStatus()) {
          mergeResponseStatus(other.getResponseStatus());
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
        if (other.getTotalElements() != 0L) {
          setTotalElements(other.getTotalElements());
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
        com.cpdss.common.generated.CargoInfo.CargoDetailedReply parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.CargoInfo.CargoDetailedReply) e.getUnfinishedMessage();
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

      private java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetailed> cargos_ =
          java.util.Collections.emptyList();

      private void ensureCargosIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          cargos_ =
              new java.util.ArrayList<com.cpdss.common.generated.CargoInfo.CargoDetailed>(cargos_);
          bitField0_ |= 0x00000001;
        }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.CargoInfo.CargoDetailed,
              com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder,
              com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder>
          cargosBuilder_;

      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetailed> getCargosList() {
        if (cargosBuilder_ == null) {
          return java.util.Collections.unmodifiableList(cargos_);
        } else {
          return cargosBuilder_.getMessageList();
        }
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public int getCargosCount() {
        if (cargosBuilder_ == null) {
          return cargos_.size();
        } else {
          return cargosBuilder_.getCount();
        }
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetailed getCargos(int index) {
        if (cargosBuilder_ == null) {
          return cargos_.get(index);
        } else {
          return cargosBuilder_.getMessage(index);
        }
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public Builder setCargos(
          int index, com.cpdss.common.generated.CargoInfo.CargoDetailed value) {
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
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public Builder setCargos(
          int index, com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder builderForValue) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          cargos_.set(index, builderForValue.build());
          onChanged();
        } else {
          cargosBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public Builder addCargos(com.cpdss.common.generated.CargoInfo.CargoDetailed value) {
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
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public Builder addCargos(
          int index, com.cpdss.common.generated.CargoInfo.CargoDetailed value) {
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
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public Builder addCargos(
          com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder builderForValue) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          cargos_.add(builderForValue.build());
          onChanged();
        } else {
          cargosBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public Builder addCargos(
          int index, com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder builderForValue) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          cargos_.add(index, builderForValue.build());
          onChanged();
        } else {
          cargosBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public Builder addAllCargos(
          java.lang.Iterable<? extends com.cpdss.common.generated.CargoInfo.CargoDetailed> values) {
        if (cargosBuilder_ == null) {
          ensureCargosIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(values, cargos_);
          onChanged();
        } else {
          cargosBuilder_.addAllMessages(values);
        }
        return this;
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
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
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
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
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder getCargosBuilder(
          int index) {
        return getCargosFieldBuilder().getBuilder(index);
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder getCargosOrBuilder(
          int index) {
        if (cargosBuilder_ == null) {
          return cargos_.get(index);
        } else {
          return cargosBuilder_.getMessageOrBuilder(index);
        }
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public java.util.List<? extends com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder>
          getCargosOrBuilderList() {
        if (cargosBuilder_ != null) {
          return cargosBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(cargos_);
        }
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder addCargosBuilder() {
        return getCargosFieldBuilder()
            .addBuilder(com.cpdss.common.generated.CargoInfo.CargoDetailed.getDefaultInstance());
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder addCargosBuilder(
          int index) {
        return getCargosFieldBuilder()
            .addBuilder(
                index, com.cpdss.common.generated.CargoInfo.CargoDetailed.getDefaultInstance());
      }
      /** <code>repeated .CargoDetailed cargos = 2;</code> */
      public java.util.List<com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder>
          getCargosBuilderList() {
        return getCargosFieldBuilder().getBuilderList();
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
              com.cpdss.common.generated.CargoInfo.CargoDetailed,
              com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder,
              com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder>
          getCargosFieldBuilder() {
        if (cargosBuilder_ == null) {
          cargosBuilder_ =
              new com.google.protobuf.RepeatedFieldBuilderV3<
                  com.cpdss.common.generated.CargoInfo.CargoDetailed,
                  com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder,
                  com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder>(
                  cargos_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
          cargos_ = null;
        }
        return cargosBuilder_;
      }

      private long totalElements_;
      /**
       * <code>int64 totalElements = 3;</code>
       *
       * @return The totalElements.
       */
      public long getTotalElements() {
        return totalElements_;
      }
      /**
       * <code>int64 totalElements = 3;</code>
       *
       * @param value The totalElements to set.
       * @return This builder for chaining.
       */
      public Builder setTotalElements(long value) {

        totalElements_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 totalElements = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTotalElements() {

        totalElements_ = 0L;
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

      // @@protoc_insertion_point(builder_scope:CargoDetailedReply)
    }

    // @@protoc_insertion_point(class_scope:CargoDetailedReply)
    private static final com.cpdss.common.generated.CargoInfo.CargoDetailedReply DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.CargoInfo.CargoDetailedReply();
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailedReply getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoDetailedReply> PARSER =
        new com.google.protobuf.AbstractParser<CargoDetailedReply>() {
          @java.lang.Override
          public CargoDetailedReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoDetailedReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoDetailedReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoDetailedReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.CargoInfo.CargoDetailedReply getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface CargoByIdDetailedReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoByIdDetailedReply)
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
     * <code>.CargoDetailed cargo = 2;</code>
     *
     * @return Whether the cargo field is set.
     */
    boolean hasCargo();
    /**
     * <code>.CargoDetailed cargo = 2;</code>
     *
     * @return The cargo.
     */
    com.cpdss.common.generated.CargoInfo.CargoDetailed getCargo();
    /** <code>.CargoDetailed cargo = 2;</code> */
    com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder getCargoOrBuilder();
  }
  /** Protobuf type {@code CargoByIdDetailedReply} */
  public static final class CargoByIdDetailedReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoByIdDetailedReply)
      CargoByIdDetailedReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoByIdDetailedReply.newBuilder() to construct.
    private CargoByIdDetailedReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoByIdDetailedReply() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoByIdDetailedReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoByIdDetailedReply(
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
                com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder subBuilder = null;
                if (cargo_ != null) {
                  subBuilder = cargo_.toBuilder();
                }
                cargo_ =
                    input.readMessage(
                        com.cpdss.common.generated.CargoInfo.CargoDetailed.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(cargo_);
                  cargo_ = subBuilder.buildPartial();
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
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoByIdDetailedReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.CargoInfo
          .internal_static_CargoByIdDetailedReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply.class,
              com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply.Builder.class);
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

    public static final int CARGO_FIELD_NUMBER = 2;
    private com.cpdss.common.generated.CargoInfo.CargoDetailed cargo_;
    /**
     * <code>.CargoDetailed cargo = 2;</code>
     *
     * @return Whether the cargo field is set.
     */
    public boolean hasCargo() {
      return cargo_ != null;
    }
    /**
     * <code>.CargoDetailed cargo = 2;</code>
     *
     * @return The cargo.
     */
    public com.cpdss.common.generated.CargoInfo.CargoDetailed getCargo() {
      return cargo_ == null
          ? com.cpdss.common.generated.CargoInfo.CargoDetailed.getDefaultInstance()
          : cargo_;
    }
    /** <code>.CargoDetailed cargo = 2;</code> */
    public com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder getCargoOrBuilder() {
      return getCargo();
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
      if (cargo_ != null) {
        output.writeMessage(2, getCargo());
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
      if (cargo_ != null) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getCargo());
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
      if (!(obj instanceof com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply other =
          (com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply) obj;

      if (hasResponseStatus() != other.hasResponseStatus()) return false;
      if (hasResponseStatus()) {
        if (!getResponseStatus().equals(other.getResponseStatus())) return false;
      }
      if (hasCargo() != other.hasCargo()) return false;
      if (hasCargo()) {
        if (!getCargo().equals(other.getCargo())) return false;
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
      if (hasCargo()) {
        hash = (37 * hash) + CARGO_FIELD_NUMBER;
        hash = (53 * hash) + getCargo().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parseFrom(
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
        com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply prototype) {
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
    /** Protobuf type {@code CargoByIdDetailedReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoByIdDetailedReply)
        com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.CargoInfo
            .internal_static_CargoByIdDetailedReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.CargoInfo
            .internal_static_CargoByIdDetailedReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply.class,
                com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply.Builder.class);
      }

      // Construct using com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply.newBuilder()
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
        if (cargoBuilder_ == null) {
          cargo_ = null;
        } else {
          cargo_ = null;
          cargoBuilder_ = null;
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.CargoInfo
            .internal_static_CargoByIdDetailedReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply
          getDefaultInstanceForType() {
        return com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply build() {
        com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply buildPartial() {
        com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply result =
            new com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply(this);
        if (responseStatusBuilder_ == null) {
          result.responseStatus_ = responseStatus_;
        } else {
          result.responseStatus_ = responseStatusBuilder_.build();
        }
        if (cargoBuilder_ == null) {
          result.cargo_ = cargo_;
        } else {
          result.cargo_ = cargoBuilder_.build();
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
        if (other instanceof com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply) {
          return mergeFrom((com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply other) {
        if (other
            == com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply.getDefaultInstance())
          return this;
        if (other.hasResponseStatus()) {
          mergeResponseStatus(other.getResponseStatus());
        }
        if (other.hasCargo()) {
          mergeCargo(other.getCargo());
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
        com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply)
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

      private com.cpdss.common.generated.CargoInfo.CargoDetailed cargo_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.CargoInfo.CargoDetailed,
              com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder,
              com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder>
          cargoBuilder_;
      /**
       * <code>.CargoDetailed cargo = 2;</code>
       *
       * @return Whether the cargo field is set.
       */
      public boolean hasCargo() {
        return cargoBuilder_ != null || cargo_ != null;
      }
      /**
       * <code>.CargoDetailed cargo = 2;</code>
       *
       * @return The cargo.
       */
      public com.cpdss.common.generated.CargoInfo.CargoDetailed getCargo() {
        if (cargoBuilder_ == null) {
          return cargo_ == null
              ? com.cpdss.common.generated.CargoInfo.CargoDetailed.getDefaultInstance()
              : cargo_;
        } else {
          return cargoBuilder_.getMessage();
        }
      }
      /** <code>.CargoDetailed cargo = 2;</code> */
      public Builder setCargo(com.cpdss.common.generated.CargoInfo.CargoDetailed value) {
        if (cargoBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          cargo_ = value;
          onChanged();
        } else {
          cargoBuilder_.setMessage(value);
        }

        return this;
      }
      /** <code>.CargoDetailed cargo = 2;</code> */
      public Builder setCargo(
          com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder builderForValue) {
        if (cargoBuilder_ == null) {
          cargo_ = builderForValue.build();
          onChanged();
        } else {
          cargoBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /** <code>.CargoDetailed cargo = 2;</code> */
      public Builder mergeCargo(com.cpdss.common.generated.CargoInfo.CargoDetailed value) {
        if (cargoBuilder_ == null) {
          if (cargo_ != null) {
            cargo_ =
                com.cpdss.common.generated.CargoInfo.CargoDetailed.newBuilder(cargo_)
                    .mergeFrom(value)
                    .buildPartial();
          } else {
            cargo_ = value;
          }
          onChanged();
        } else {
          cargoBuilder_.mergeFrom(value);
        }

        return this;
      }
      /** <code>.CargoDetailed cargo = 2;</code> */
      public Builder clearCargo() {
        if (cargoBuilder_ == null) {
          cargo_ = null;
          onChanged();
        } else {
          cargo_ = null;
          cargoBuilder_ = null;
        }

        return this;
      }
      /** <code>.CargoDetailed cargo = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder getCargoBuilder() {

        onChanged();
        return getCargoFieldBuilder().getBuilder();
      }
      /** <code>.CargoDetailed cargo = 2;</code> */
      public com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder getCargoOrBuilder() {
        if (cargoBuilder_ != null) {
          return cargoBuilder_.getMessageOrBuilder();
        } else {
          return cargo_ == null
              ? com.cpdss.common.generated.CargoInfo.CargoDetailed.getDefaultInstance()
              : cargo_;
        }
      }
      /** <code>.CargoDetailed cargo = 2;</code> */
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.CargoInfo.CargoDetailed,
              com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder,
              com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder>
          getCargoFieldBuilder() {
        if (cargoBuilder_ == null) {
          cargoBuilder_ =
              new com.google.protobuf.SingleFieldBuilderV3<
                  com.cpdss.common.generated.CargoInfo.CargoDetailed,
                  com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder,
                  com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder>(
                  getCargo(), getParentForChildren(), isClean());
          cargo_ = null;
        }
        return cargoBuilder_;
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

      // @@protoc_insertion_point(builder_scope:CargoByIdDetailedReply)
    }

    // @@protoc_insertion_point(class_scope:CargoByIdDetailedReply)
    private static final com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply
        DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply();
    }

    public static com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoByIdDetailedReply> PARSER =
        new com.google.protobuf.AbstractParser<CargoByIdDetailedReply>() {
          @java.lang.Override
          public CargoByIdDetailedReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoByIdDetailedReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoByIdDetailedReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoByIdDetailedReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface CargoDetailedOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoDetailed)
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
     * <code>string type = 5;</code>
     *
     * @return The type.
     */
    java.lang.String getType();
    /**
     * <code>string type = 5;</code>
     *
     * @return The bytes for type.
     */
    com.google.protobuf.ByteString getTypeBytes();

    /**
     * <code>string assayDate = 6;</code>
     *
     * @return The assayDate.
     */
    java.lang.String getAssayDate();
    /**
     * <code>string assayDate = 6;</code>
     *
     * @return The bytes for assayDate.
     */
    com.google.protobuf.ByteString getAssayDateBytes();

    /**
     * <code>string temp = 7;</code>
     *
     * @return The temp.
     */
    java.lang.String getTemp();
    /**
     * <code>string temp = 7;</code>
     *
     * @return The bytes for temp.
     */
    com.google.protobuf.ByteString getTempBytes();

    /**
     * <code>string reidVapourPressure = 8;</code>
     *
     * @return The reidVapourPressure.
     */
    java.lang.String getReidVapourPressure();
    /**
     * <code>string reidVapourPressure = 8;</code>
     *
     * @return The bytes for reidVapourPressure.
     */
    com.google.protobuf.ByteString getReidVapourPressureBytes();

    /**
     * <code>string gas = 9;</code>
     *
     * @return The gas.
     */
    java.lang.String getGas();
    /**
     * <code>string gas = 9;</code>
     *
     * @return The bytes for gas.
     */
    com.google.protobuf.ByteString getGasBytes();

    /**
     * <code>string totalWax = 10;</code>
     *
     * @return The totalWax.
     */
    java.lang.String getTotalWax();
    /**
     * <code>string totalWax = 10;</code>
     *
     * @return The bytes for totalWax.
     */
    com.google.protobuf.ByteString getTotalWaxBytes();

    /**
     * <code>string pourPoint = 11;</code>
     *
     * @return The pourPoint.
     */
    java.lang.String getPourPoint();
    /**
     * <code>string pourPoint = 11;</code>
     *
     * @return The bytes for pourPoint.
     */
    com.google.protobuf.ByteString getPourPointBytes();

    /**
     * <code>string cloudPoint = 12;</code>
     *
     * @return The cloudPoint.
     */
    java.lang.String getCloudPoint();
    /**
     * <code>string cloudPoint = 12;</code>
     *
     * @return The bytes for cloudPoint.
     */
    com.google.protobuf.ByteString getCloudPointBytes();

    /**
     * <code>string viscosity = 13;</code>
     *
     * @return The viscosity.
     */
    java.lang.String getViscosity();
    /**
     * <code>string viscosity = 13;</code>
     *
     * @return The bytes for viscosity.
     */
    com.google.protobuf.ByteString getViscosityBytes();

    /**
     * <code>string cowCodes = 14;</code>
     *
     * @return The cowCodes.
     */
    java.lang.String getCowCodes();
    /**
     * <code>string cowCodes = 14;</code>
     *
     * @return The bytes for cowCodes.
     */
    com.google.protobuf.ByteString getCowCodesBytes();

    /**
     * <code>string hydrogenSulfideOil = 15;</code>
     *
     * @return The hydrogenSulfideOil.
     */
    java.lang.String getHydrogenSulfideOil();
    /**
     * <code>string hydrogenSulfideOil = 15;</code>
     *
     * @return The bytes for hydrogenSulfideOil.
     */
    com.google.protobuf.ByteString getHydrogenSulfideOilBytes();

    /**
     * <code>string hydrogenSulfideVapour = 16;</code>
     *
     * @return The hydrogenSulfideVapour.
     */
    java.lang.String getHydrogenSulfideVapour();
    /**
     * <code>string hydrogenSulfideVapour = 16;</code>
     *
     * @return The bytes for hydrogenSulfideVapour.
     */
    com.google.protobuf.ByteString getHydrogenSulfideVapourBytes();

    /**
     * <code>string benzene = 17;</code>
     *
     * @return The benzene.
     */
    java.lang.String getBenzene();
    /**
     * <code>string benzene = 17;</code>
     *
     * @return The bytes for benzene.
     */
    com.google.protobuf.ByteString getBenzeneBytes();

    /**
     * <code>string specialInstrictionsRemark = 18;</code>
     *
     * @return The specialInstrictionsRemark.
     */
    java.lang.String getSpecialInstrictionsRemark();
    /**
     * <code>string specialInstrictionsRemark = 18;</code>
     *
     * @return The bytes for specialInstrictionsRemark.
     */
    com.google.protobuf.ByteString getSpecialInstrictionsRemarkBytes();
  }
  /** Protobuf type {@code CargoDetailed} */
  public static final class CargoDetailed extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoDetailed)
      CargoDetailedOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoDetailed.newBuilder() to construct.
    private CargoDetailed(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoDetailed() {
      name_ = "";
      abbreviation_ = "";
      api_ = "";
      type_ = "";
      assayDate_ = "";
      temp_ = "";
      reidVapourPressure_ = "";
      gas_ = "";
      totalWax_ = "";
      pourPoint_ = "";
      cloudPoint_ = "";
      viscosity_ = "";
      cowCodes_ = "";
      hydrogenSulfideOil_ = "";
      hydrogenSulfideVapour_ = "";
      benzene_ = "";
      specialInstrictionsRemark_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoDetailed();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoDetailed(
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

                name_ = s;
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
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                type_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                assayDate_ = s;
                break;
              }
            case 58:
              {
                java.lang.String s = input.readStringRequireUtf8();

                temp_ = s;
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                reidVapourPressure_ = s;
                break;
              }
            case 74:
              {
                java.lang.String s = input.readStringRequireUtf8();

                gas_ = s;
                break;
              }
            case 82:
              {
                java.lang.String s = input.readStringRequireUtf8();

                totalWax_ = s;
                break;
              }
            case 90:
              {
                java.lang.String s = input.readStringRequireUtf8();

                pourPoint_ = s;
                break;
              }
            case 98:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cloudPoint_ = s;
                break;
              }
            case 106:
              {
                java.lang.String s = input.readStringRequireUtf8();

                viscosity_ = s;
                break;
              }
            case 114:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cowCodes_ = s;
                break;
              }
            case 122:
              {
                java.lang.String s = input.readStringRequireUtf8();

                hydrogenSulfideOil_ = s;
                break;
              }
            case 130:
              {
                java.lang.String s = input.readStringRequireUtf8();

                hydrogenSulfideVapour_ = s;
                break;
              }
            case 138:
              {
                java.lang.String s = input.readStringRequireUtf8();

                benzene_ = s;
                break;
              }
            case 146:
              {
                java.lang.String s = input.readStringRequireUtf8();

                specialInstrictionsRemark_ = s;
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
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailed_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailed_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.CargoInfo.CargoDetailed.class,
              com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder.class);
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

    public static final int TYPE_FIELD_NUMBER = 5;
    private volatile java.lang.Object type_;
    /**
     * <code>string type = 5;</code>
     *
     * @return The type.
     */
    public java.lang.String getType() {
      java.lang.Object ref = type_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        type_ = s;
        return s;
      }
    }
    /**
     * <code>string type = 5;</code>
     *
     * @return The bytes for type.
     */
    public com.google.protobuf.ByteString getTypeBytes() {
      java.lang.Object ref = type_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        type_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ASSAYDATE_FIELD_NUMBER = 6;
    private volatile java.lang.Object assayDate_;
    /**
     * <code>string assayDate = 6;</code>
     *
     * @return The assayDate.
     */
    public java.lang.String getAssayDate() {
      java.lang.Object ref = assayDate_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        assayDate_ = s;
        return s;
      }
    }
    /**
     * <code>string assayDate = 6;</code>
     *
     * @return The bytes for assayDate.
     */
    public com.google.protobuf.ByteString getAssayDateBytes() {
      java.lang.Object ref = assayDate_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        assayDate_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TEMP_FIELD_NUMBER = 7;
    private volatile java.lang.Object temp_;
    /**
     * <code>string temp = 7;</code>
     *
     * @return The temp.
     */
    public java.lang.String getTemp() {
      java.lang.Object ref = temp_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        temp_ = s;
        return s;
      }
    }
    /**
     * <code>string temp = 7;</code>
     *
     * @return The bytes for temp.
     */
    public com.google.protobuf.ByteString getTempBytes() {
      java.lang.Object ref = temp_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        temp_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int REIDVAPOURPRESSURE_FIELD_NUMBER = 8;
    private volatile java.lang.Object reidVapourPressure_;
    /**
     * <code>string reidVapourPressure = 8;</code>
     *
     * @return The reidVapourPressure.
     */
    public java.lang.String getReidVapourPressure() {
      java.lang.Object ref = reidVapourPressure_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        reidVapourPressure_ = s;
        return s;
      }
    }
    /**
     * <code>string reidVapourPressure = 8;</code>
     *
     * @return The bytes for reidVapourPressure.
     */
    public com.google.protobuf.ByteString getReidVapourPressureBytes() {
      java.lang.Object ref = reidVapourPressure_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        reidVapourPressure_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int GAS_FIELD_NUMBER = 9;
    private volatile java.lang.Object gas_;
    /**
     * <code>string gas = 9;</code>
     *
     * @return The gas.
     */
    public java.lang.String getGas() {
      java.lang.Object ref = gas_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        gas_ = s;
        return s;
      }
    }
    /**
     * <code>string gas = 9;</code>
     *
     * @return The bytes for gas.
     */
    public com.google.protobuf.ByteString getGasBytes() {
      java.lang.Object ref = gas_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        gas_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TOTALWAX_FIELD_NUMBER = 10;
    private volatile java.lang.Object totalWax_;
    /**
     * <code>string totalWax = 10;</code>
     *
     * @return The totalWax.
     */
    public java.lang.String getTotalWax() {
      java.lang.Object ref = totalWax_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        totalWax_ = s;
        return s;
      }
    }
    /**
     * <code>string totalWax = 10;</code>
     *
     * @return The bytes for totalWax.
     */
    public com.google.protobuf.ByteString getTotalWaxBytes() {
      java.lang.Object ref = totalWax_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        totalWax_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int POURPOINT_FIELD_NUMBER = 11;
    private volatile java.lang.Object pourPoint_;
    /**
     * <code>string pourPoint = 11;</code>
     *
     * @return The pourPoint.
     */
    public java.lang.String getPourPoint() {
      java.lang.Object ref = pourPoint_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        pourPoint_ = s;
        return s;
      }
    }
    /**
     * <code>string pourPoint = 11;</code>
     *
     * @return The bytes for pourPoint.
     */
    public com.google.protobuf.ByteString getPourPointBytes() {
      java.lang.Object ref = pourPoint_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        pourPoint_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CLOUDPOINT_FIELD_NUMBER = 12;
    private volatile java.lang.Object cloudPoint_;
    /**
     * <code>string cloudPoint = 12;</code>
     *
     * @return The cloudPoint.
     */
    public java.lang.String getCloudPoint() {
      java.lang.Object ref = cloudPoint_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cloudPoint_ = s;
        return s;
      }
    }
    /**
     * <code>string cloudPoint = 12;</code>
     *
     * @return The bytes for cloudPoint.
     */
    public com.google.protobuf.ByteString getCloudPointBytes() {
      java.lang.Object ref = cloudPoint_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cloudPoint_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int VISCOSITY_FIELD_NUMBER = 13;
    private volatile java.lang.Object viscosity_;
    /**
     * <code>string viscosity = 13;</code>
     *
     * @return The viscosity.
     */
    public java.lang.String getViscosity() {
      java.lang.Object ref = viscosity_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        viscosity_ = s;
        return s;
      }
    }
    /**
     * <code>string viscosity = 13;</code>
     *
     * @return The bytes for viscosity.
     */
    public com.google.protobuf.ByteString getViscosityBytes() {
      java.lang.Object ref = viscosity_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        viscosity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int COWCODES_FIELD_NUMBER = 14;
    private volatile java.lang.Object cowCodes_;
    /**
     * <code>string cowCodes = 14;</code>
     *
     * @return The cowCodes.
     */
    public java.lang.String getCowCodes() {
      java.lang.Object ref = cowCodes_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cowCodes_ = s;
        return s;
      }
    }
    /**
     * <code>string cowCodes = 14;</code>
     *
     * @return The bytes for cowCodes.
     */
    public com.google.protobuf.ByteString getCowCodesBytes() {
      java.lang.Object ref = cowCodes_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cowCodes_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int HYDROGENSULFIDEOIL_FIELD_NUMBER = 15;
    private volatile java.lang.Object hydrogenSulfideOil_;
    /**
     * <code>string hydrogenSulfideOil = 15;</code>
     *
     * @return The hydrogenSulfideOil.
     */
    public java.lang.String getHydrogenSulfideOil() {
      java.lang.Object ref = hydrogenSulfideOil_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        hydrogenSulfideOil_ = s;
        return s;
      }
    }
    /**
     * <code>string hydrogenSulfideOil = 15;</code>
     *
     * @return The bytes for hydrogenSulfideOil.
     */
    public com.google.protobuf.ByteString getHydrogenSulfideOilBytes() {
      java.lang.Object ref = hydrogenSulfideOil_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        hydrogenSulfideOil_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int HYDROGENSULFIDEVAPOUR_FIELD_NUMBER = 16;
    private volatile java.lang.Object hydrogenSulfideVapour_;
    /**
     * <code>string hydrogenSulfideVapour = 16;</code>
     *
     * @return The hydrogenSulfideVapour.
     */
    public java.lang.String getHydrogenSulfideVapour() {
      java.lang.Object ref = hydrogenSulfideVapour_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        hydrogenSulfideVapour_ = s;
        return s;
      }
    }
    /**
     * <code>string hydrogenSulfideVapour = 16;</code>
     *
     * @return The bytes for hydrogenSulfideVapour.
     */
    public com.google.protobuf.ByteString getHydrogenSulfideVapourBytes() {
      java.lang.Object ref = hydrogenSulfideVapour_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        hydrogenSulfideVapour_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int BENZENE_FIELD_NUMBER = 17;
    private volatile java.lang.Object benzene_;
    /**
     * <code>string benzene = 17;</code>
     *
     * @return The benzene.
     */
    public java.lang.String getBenzene() {
      java.lang.Object ref = benzene_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        benzene_ = s;
        return s;
      }
    }
    /**
     * <code>string benzene = 17;</code>
     *
     * @return The bytes for benzene.
     */
    public com.google.protobuf.ByteString getBenzeneBytes() {
      java.lang.Object ref = benzene_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        benzene_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SPECIALINSTRICTIONSREMARK_FIELD_NUMBER = 18;
    private volatile java.lang.Object specialInstrictionsRemark_;
    /**
     * <code>string specialInstrictionsRemark = 18;</code>
     *
     * @return The specialInstrictionsRemark.
     */
    public java.lang.String getSpecialInstrictionsRemark() {
      java.lang.Object ref = specialInstrictionsRemark_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        specialInstrictionsRemark_ = s;
        return s;
      }
    }
    /**
     * <code>string specialInstrictionsRemark = 18;</code>
     *
     * @return The bytes for specialInstrictionsRemark.
     */
    public com.google.protobuf.ByteString getSpecialInstrictionsRemarkBytes() {
      java.lang.Object ref = specialInstrictionsRemark_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        specialInstrictionsRemark_ = b;
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
      if (!getNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, name_);
      }
      if (!getAbbreviationBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, abbreviation_);
      }
      if (!getApiBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, api_);
      }
      if (!getTypeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, type_);
      }
      if (!getAssayDateBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, assayDate_);
      }
      if (!getTempBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, temp_);
      }
      if (!getReidVapourPressureBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, reidVapourPressure_);
      }
      if (!getGasBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 9, gas_);
      }
      if (!getTotalWaxBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 10, totalWax_);
      }
      if (!getPourPointBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 11, pourPoint_);
      }
      if (!getCloudPointBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 12, cloudPoint_);
      }
      if (!getViscosityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 13, viscosity_);
      }
      if (!getCowCodesBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 14, cowCodes_);
      }
      if (!getHydrogenSulfideOilBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 15, hydrogenSulfideOil_);
      }
      if (!getHydrogenSulfideVapourBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 16, hydrogenSulfideVapour_);
      }
      if (!getBenzeneBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 17, benzene_);
      }
      if (!getSpecialInstrictionsRemarkBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 18, specialInstrictionsRemark_);
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
      if (!getAbbreviationBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, abbreviation_);
      }
      if (!getApiBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, api_);
      }
      if (!getTypeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, type_);
      }
      if (!getAssayDateBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, assayDate_);
      }
      if (!getTempBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, temp_);
      }
      if (!getReidVapourPressureBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, reidVapourPressure_);
      }
      if (!getGasBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, gas_);
      }
      if (!getTotalWaxBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(10, totalWax_);
      }
      if (!getPourPointBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(11, pourPoint_);
      }
      if (!getCloudPointBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(12, cloudPoint_);
      }
      if (!getViscosityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(13, viscosity_);
      }
      if (!getCowCodesBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(14, cowCodes_);
      }
      if (!getHydrogenSulfideOilBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(15, hydrogenSulfideOil_);
      }
      if (!getHydrogenSulfideVapourBytes().isEmpty()) {
        size +=
            com.google.protobuf.GeneratedMessageV3.computeStringSize(16, hydrogenSulfideVapour_);
      }
      if (!getBenzeneBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(17, benzene_);
      }
      if (!getSpecialInstrictionsRemarkBytes().isEmpty()) {
        size +=
            com.google.protobuf.GeneratedMessageV3.computeStringSize(
                18, specialInstrictionsRemark_);
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
      if (!(obj instanceof com.cpdss.common.generated.CargoInfo.CargoDetailed)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.CargoInfo.CargoDetailed other =
          (com.cpdss.common.generated.CargoInfo.CargoDetailed) obj;

      if (getId() != other.getId()) return false;
      if (!getName().equals(other.getName())) return false;
      if (!getAbbreviation().equals(other.getAbbreviation())) return false;
      if (!getApi().equals(other.getApi())) return false;
      if (!getType().equals(other.getType())) return false;
      if (!getAssayDate().equals(other.getAssayDate())) return false;
      if (!getTemp().equals(other.getTemp())) return false;
      if (!getReidVapourPressure().equals(other.getReidVapourPressure())) return false;
      if (!getGas().equals(other.getGas())) return false;
      if (!getTotalWax().equals(other.getTotalWax())) return false;
      if (!getPourPoint().equals(other.getPourPoint())) return false;
      if (!getCloudPoint().equals(other.getCloudPoint())) return false;
      if (!getViscosity().equals(other.getViscosity())) return false;
      if (!getCowCodes().equals(other.getCowCodes())) return false;
      if (!getHydrogenSulfideOil().equals(other.getHydrogenSulfideOil())) return false;
      if (!getHydrogenSulfideVapour().equals(other.getHydrogenSulfideVapour())) return false;
      if (!getBenzene().equals(other.getBenzene())) return false;
      if (!getSpecialInstrictionsRemark().equals(other.getSpecialInstrictionsRemark()))
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
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      hash = (37 * hash) + ABBREVIATION_FIELD_NUMBER;
      hash = (53 * hash) + getAbbreviation().hashCode();
      hash = (37 * hash) + API_FIELD_NUMBER;
      hash = (53 * hash) + getApi().hashCode();
      hash = (37 * hash) + TYPE_FIELD_NUMBER;
      hash = (53 * hash) + getType().hashCode();
      hash = (37 * hash) + ASSAYDATE_FIELD_NUMBER;
      hash = (53 * hash) + getAssayDate().hashCode();
      hash = (37 * hash) + TEMP_FIELD_NUMBER;
      hash = (53 * hash) + getTemp().hashCode();
      hash = (37 * hash) + REIDVAPOURPRESSURE_FIELD_NUMBER;
      hash = (53 * hash) + getReidVapourPressure().hashCode();
      hash = (37 * hash) + GAS_FIELD_NUMBER;
      hash = (53 * hash) + getGas().hashCode();
      hash = (37 * hash) + TOTALWAX_FIELD_NUMBER;
      hash = (53 * hash) + getTotalWax().hashCode();
      hash = (37 * hash) + POURPOINT_FIELD_NUMBER;
      hash = (53 * hash) + getPourPoint().hashCode();
      hash = (37 * hash) + CLOUDPOINT_FIELD_NUMBER;
      hash = (53 * hash) + getCloudPoint().hashCode();
      hash = (37 * hash) + VISCOSITY_FIELD_NUMBER;
      hash = (53 * hash) + getViscosity().hashCode();
      hash = (37 * hash) + COWCODES_FIELD_NUMBER;
      hash = (53 * hash) + getCowCodes().hashCode();
      hash = (37 * hash) + HYDROGENSULFIDEOIL_FIELD_NUMBER;
      hash = (53 * hash) + getHydrogenSulfideOil().hashCode();
      hash = (37 * hash) + HYDROGENSULFIDEVAPOUR_FIELD_NUMBER;
      hash = (53 * hash) + getHydrogenSulfideVapour().hashCode();
      hash = (37 * hash) + BENZENE_FIELD_NUMBER;
      hash = (53 * hash) + getBenzene().hashCode();
      hash = (37 * hash) + SPECIALINSTRICTIONSREMARK_FIELD_NUMBER;
      hash = (53 * hash) + getSpecialInstrictionsRemark().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.CargoInfo.CargoDetailed prototype) {
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
    /** Protobuf type {@code CargoDetailed} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoDetailed)
        com.cpdss.common.generated.CargoInfo.CargoDetailedOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailed_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailed_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.CargoInfo.CargoDetailed.class,
                com.cpdss.common.generated.CargoInfo.CargoDetailed.Builder.class);
      }

      // Construct using com.cpdss.common.generated.CargoInfo.CargoDetailed.newBuilder()
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

        abbreviation_ = "";

        api_ = "";

        type_ = "";

        assayDate_ = "";

        temp_ = "";

        reidVapourPressure_ = "";

        gas_ = "";

        totalWax_ = "";

        pourPoint_ = "";

        cloudPoint_ = "";

        viscosity_ = "";

        cowCodes_ = "";

        hydrogenSulfideOil_ = "";

        hydrogenSulfideVapour_ = "";

        benzene_ = "";

        specialInstrictionsRemark_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.CargoInfo.internal_static_CargoDetailed_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetailed getDefaultInstanceForType() {
        return com.cpdss.common.generated.CargoInfo.CargoDetailed.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetailed build() {
        com.cpdss.common.generated.CargoInfo.CargoDetailed result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.CargoInfo.CargoDetailed buildPartial() {
        com.cpdss.common.generated.CargoInfo.CargoDetailed result =
            new com.cpdss.common.generated.CargoInfo.CargoDetailed(this);
        result.id_ = id_;
        result.name_ = name_;
        result.abbreviation_ = abbreviation_;
        result.api_ = api_;
        result.type_ = type_;
        result.assayDate_ = assayDate_;
        result.temp_ = temp_;
        result.reidVapourPressure_ = reidVapourPressure_;
        result.gas_ = gas_;
        result.totalWax_ = totalWax_;
        result.pourPoint_ = pourPoint_;
        result.cloudPoint_ = cloudPoint_;
        result.viscosity_ = viscosity_;
        result.cowCodes_ = cowCodes_;
        result.hydrogenSulfideOil_ = hydrogenSulfideOil_;
        result.hydrogenSulfideVapour_ = hydrogenSulfideVapour_;
        result.benzene_ = benzene_;
        result.specialInstrictionsRemark_ = specialInstrictionsRemark_;
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
        if (other instanceof com.cpdss.common.generated.CargoInfo.CargoDetailed) {
          return mergeFrom((com.cpdss.common.generated.CargoInfo.CargoDetailed) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.CargoInfo.CargoDetailed other) {
        if (other == com.cpdss.common.generated.CargoInfo.CargoDetailed.getDefaultInstance())
          return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
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
        if (!other.getType().isEmpty()) {
          type_ = other.type_;
          onChanged();
        }
        if (!other.getAssayDate().isEmpty()) {
          assayDate_ = other.assayDate_;
          onChanged();
        }
        if (!other.getTemp().isEmpty()) {
          temp_ = other.temp_;
          onChanged();
        }
        if (!other.getReidVapourPressure().isEmpty()) {
          reidVapourPressure_ = other.reidVapourPressure_;
          onChanged();
        }
        if (!other.getGas().isEmpty()) {
          gas_ = other.gas_;
          onChanged();
        }
        if (!other.getTotalWax().isEmpty()) {
          totalWax_ = other.totalWax_;
          onChanged();
        }
        if (!other.getPourPoint().isEmpty()) {
          pourPoint_ = other.pourPoint_;
          onChanged();
        }
        if (!other.getCloudPoint().isEmpty()) {
          cloudPoint_ = other.cloudPoint_;
          onChanged();
        }
        if (!other.getViscosity().isEmpty()) {
          viscosity_ = other.viscosity_;
          onChanged();
        }
        if (!other.getCowCodes().isEmpty()) {
          cowCodes_ = other.cowCodes_;
          onChanged();
        }
        if (!other.getHydrogenSulfideOil().isEmpty()) {
          hydrogenSulfideOil_ = other.hydrogenSulfideOil_;
          onChanged();
        }
        if (!other.getHydrogenSulfideVapour().isEmpty()) {
          hydrogenSulfideVapour_ = other.hydrogenSulfideVapour_;
          onChanged();
        }
        if (!other.getBenzene().isEmpty()) {
          benzene_ = other.benzene_;
          onChanged();
        }
        if (!other.getSpecialInstrictionsRemark().isEmpty()) {
          specialInstrictionsRemark_ = other.specialInstrictionsRemark_;
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
        com.cpdss.common.generated.CargoInfo.CargoDetailed parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.CargoInfo.CargoDetailed) e.getUnfinishedMessage();
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

      private java.lang.Object type_ = "";
      /**
       * <code>string type = 5;</code>
       *
       * @return The type.
       */
      public java.lang.String getType() {
        java.lang.Object ref = type_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          type_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string type = 5;</code>
       *
       * @return The bytes for type.
       */
      public com.google.protobuf.ByteString getTypeBytes() {
        java.lang.Object ref = type_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          type_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string type = 5;</code>
       *
       * @param value The type to set.
       * @return This builder for chaining.
       */
      public Builder setType(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        type_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string type = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearType() {

        type_ = getDefaultInstance().getType();
        onChanged();
        return this;
      }
      /**
       * <code>string type = 5;</code>
       *
       * @param value The bytes for type to set.
       * @return This builder for chaining.
       */
      public Builder setTypeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        type_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object assayDate_ = "";
      /**
       * <code>string assayDate = 6;</code>
       *
       * @return The assayDate.
       */
      public java.lang.String getAssayDate() {
        java.lang.Object ref = assayDate_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          assayDate_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string assayDate = 6;</code>
       *
       * @return The bytes for assayDate.
       */
      public com.google.protobuf.ByteString getAssayDateBytes() {
        java.lang.Object ref = assayDate_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          assayDate_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string assayDate = 6;</code>
       *
       * @param value The assayDate to set.
       * @return This builder for chaining.
       */
      public Builder setAssayDate(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        assayDate_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string assayDate = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearAssayDate() {

        assayDate_ = getDefaultInstance().getAssayDate();
        onChanged();
        return this;
      }
      /**
       * <code>string assayDate = 6;</code>
       *
       * @param value The bytes for assayDate to set.
       * @return This builder for chaining.
       */
      public Builder setAssayDateBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        assayDate_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object temp_ = "";
      /**
       * <code>string temp = 7;</code>
       *
       * @return The temp.
       */
      public java.lang.String getTemp() {
        java.lang.Object ref = temp_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          temp_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string temp = 7;</code>
       *
       * @return The bytes for temp.
       */
      public com.google.protobuf.ByteString getTempBytes() {
        java.lang.Object ref = temp_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          temp_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string temp = 7;</code>
       *
       * @param value The temp to set.
       * @return This builder for chaining.
       */
      public Builder setTemp(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        temp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string temp = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTemp() {

        temp_ = getDefaultInstance().getTemp();
        onChanged();
        return this;
      }
      /**
       * <code>string temp = 7;</code>
       *
       * @param value The bytes for temp to set.
       * @return This builder for chaining.
       */
      public Builder setTempBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        temp_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object reidVapourPressure_ = "";
      /**
       * <code>string reidVapourPressure = 8;</code>
       *
       * @return The reidVapourPressure.
       */
      public java.lang.String getReidVapourPressure() {
        java.lang.Object ref = reidVapourPressure_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          reidVapourPressure_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string reidVapourPressure = 8;</code>
       *
       * @return The bytes for reidVapourPressure.
       */
      public com.google.protobuf.ByteString getReidVapourPressureBytes() {
        java.lang.Object ref = reidVapourPressure_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          reidVapourPressure_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string reidVapourPressure = 8;</code>
       *
       * @param value The reidVapourPressure to set.
       * @return This builder for chaining.
       */
      public Builder setReidVapourPressure(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        reidVapourPressure_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string reidVapourPressure = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearReidVapourPressure() {

        reidVapourPressure_ = getDefaultInstance().getReidVapourPressure();
        onChanged();
        return this;
      }
      /**
       * <code>string reidVapourPressure = 8;</code>
       *
       * @param value The bytes for reidVapourPressure to set.
       * @return This builder for chaining.
       */
      public Builder setReidVapourPressureBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        reidVapourPressure_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object gas_ = "";
      /**
       * <code>string gas = 9;</code>
       *
       * @return The gas.
       */
      public java.lang.String getGas() {
        java.lang.Object ref = gas_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          gas_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string gas = 9;</code>
       *
       * @return The bytes for gas.
       */
      public com.google.protobuf.ByteString getGasBytes() {
        java.lang.Object ref = gas_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          gas_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string gas = 9;</code>
       *
       * @param value The gas to set.
       * @return This builder for chaining.
       */
      public Builder setGas(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        gas_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string gas = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearGas() {

        gas_ = getDefaultInstance().getGas();
        onChanged();
        return this;
      }
      /**
       * <code>string gas = 9;</code>
       *
       * @param value The bytes for gas to set.
       * @return This builder for chaining.
       */
      public Builder setGasBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        gas_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object totalWax_ = "";
      /**
       * <code>string totalWax = 10;</code>
       *
       * @return The totalWax.
       */
      public java.lang.String getTotalWax() {
        java.lang.Object ref = totalWax_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          totalWax_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string totalWax = 10;</code>
       *
       * @return The bytes for totalWax.
       */
      public com.google.protobuf.ByteString getTotalWaxBytes() {
        java.lang.Object ref = totalWax_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          totalWax_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string totalWax = 10;</code>
       *
       * @param value The totalWax to set.
       * @return This builder for chaining.
       */
      public Builder setTotalWax(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        totalWax_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string totalWax = 10;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTotalWax() {

        totalWax_ = getDefaultInstance().getTotalWax();
        onChanged();
        return this;
      }
      /**
       * <code>string totalWax = 10;</code>
       *
       * @param value The bytes for totalWax to set.
       * @return This builder for chaining.
       */
      public Builder setTotalWaxBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        totalWax_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object pourPoint_ = "";
      /**
       * <code>string pourPoint = 11;</code>
       *
       * @return The pourPoint.
       */
      public java.lang.String getPourPoint() {
        java.lang.Object ref = pourPoint_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          pourPoint_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string pourPoint = 11;</code>
       *
       * @return The bytes for pourPoint.
       */
      public com.google.protobuf.ByteString getPourPointBytes() {
        java.lang.Object ref = pourPoint_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          pourPoint_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string pourPoint = 11;</code>
       *
       * @param value The pourPoint to set.
       * @return This builder for chaining.
       */
      public Builder setPourPoint(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        pourPoint_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string pourPoint = 11;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPourPoint() {

        pourPoint_ = getDefaultInstance().getPourPoint();
        onChanged();
        return this;
      }
      /**
       * <code>string pourPoint = 11;</code>
       *
       * @param value The bytes for pourPoint to set.
       * @return This builder for chaining.
       */
      public Builder setPourPointBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        pourPoint_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cloudPoint_ = "";
      /**
       * <code>string cloudPoint = 12;</code>
       *
       * @return The cloudPoint.
       */
      public java.lang.String getCloudPoint() {
        java.lang.Object ref = cloudPoint_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cloudPoint_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cloudPoint = 12;</code>
       *
       * @return The bytes for cloudPoint.
       */
      public com.google.protobuf.ByteString getCloudPointBytes() {
        java.lang.Object ref = cloudPoint_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cloudPoint_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cloudPoint = 12;</code>
       *
       * @param value The cloudPoint to set.
       * @return This builder for chaining.
       */
      public Builder setCloudPoint(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cloudPoint_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cloudPoint = 12;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCloudPoint() {

        cloudPoint_ = getDefaultInstance().getCloudPoint();
        onChanged();
        return this;
      }
      /**
       * <code>string cloudPoint = 12;</code>
       *
       * @param value The bytes for cloudPoint to set.
       * @return This builder for chaining.
       */
      public Builder setCloudPointBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cloudPoint_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object viscosity_ = "";
      /**
       * <code>string viscosity = 13;</code>
       *
       * @return The viscosity.
       */
      public java.lang.String getViscosity() {
        java.lang.Object ref = viscosity_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          viscosity_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string viscosity = 13;</code>
       *
       * @return The bytes for viscosity.
       */
      public com.google.protobuf.ByteString getViscosityBytes() {
        java.lang.Object ref = viscosity_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          viscosity_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string viscosity = 13;</code>
       *
       * @param value The viscosity to set.
       * @return This builder for chaining.
       */
      public Builder setViscosity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        viscosity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string viscosity = 13;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearViscosity() {

        viscosity_ = getDefaultInstance().getViscosity();
        onChanged();
        return this;
      }
      /**
       * <code>string viscosity = 13;</code>
       *
       * @param value The bytes for viscosity to set.
       * @return This builder for chaining.
       */
      public Builder setViscosityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        viscosity_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cowCodes_ = "";
      /**
       * <code>string cowCodes = 14;</code>
       *
       * @return The cowCodes.
       */
      public java.lang.String getCowCodes() {
        java.lang.Object ref = cowCodes_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cowCodes_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cowCodes = 14;</code>
       *
       * @return The bytes for cowCodes.
       */
      public com.google.protobuf.ByteString getCowCodesBytes() {
        java.lang.Object ref = cowCodes_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cowCodes_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cowCodes = 14;</code>
       *
       * @param value The cowCodes to set.
       * @return This builder for chaining.
       */
      public Builder setCowCodes(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cowCodes_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cowCodes = 14;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCowCodes() {

        cowCodes_ = getDefaultInstance().getCowCodes();
        onChanged();
        return this;
      }
      /**
       * <code>string cowCodes = 14;</code>
       *
       * @param value The bytes for cowCodes to set.
       * @return This builder for chaining.
       */
      public Builder setCowCodesBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cowCodes_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object hydrogenSulfideOil_ = "";
      /**
       * <code>string hydrogenSulfideOil = 15;</code>
       *
       * @return The hydrogenSulfideOil.
       */
      public java.lang.String getHydrogenSulfideOil() {
        java.lang.Object ref = hydrogenSulfideOil_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          hydrogenSulfideOil_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string hydrogenSulfideOil = 15;</code>
       *
       * @return The bytes for hydrogenSulfideOil.
       */
      public com.google.protobuf.ByteString getHydrogenSulfideOilBytes() {
        java.lang.Object ref = hydrogenSulfideOil_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          hydrogenSulfideOil_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string hydrogenSulfideOil = 15;</code>
       *
       * @param value The hydrogenSulfideOil to set.
       * @return This builder for chaining.
       */
      public Builder setHydrogenSulfideOil(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        hydrogenSulfideOil_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string hydrogenSulfideOil = 15;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearHydrogenSulfideOil() {

        hydrogenSulfideOil_ = getDefaultInstance().getHydrogenSulfideOil();
        onChanged();
        return this;
      }
      /**
       * <code>string hydrogenSulfideOil = 15;</code>
       *
       * @param value The bytes for hydrogenSulfideOil to set.
       * @return This builder for chaining.
       */
      public Builder setHydrogenSulfideOilBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        hydrogenSulfideOil_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object hydrogenSulfideVapour_ = "";
      /**
       * <code>string hydrogenSulfideVapour = 16;</code>
       *
       * @return The hydrogenSulfideVapour.
       */
      public java.lang.String getHydrogenSulfideVapour() {
        java.lang.Object ref = hydrogenSulfideVapour_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          hydrogenSulfideVapour_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string hydrogenSulfideVapour = 16;</code>
       *
       * @return The bytes for hydrogenSulfideVapour.
       */
      public com.google.protobuf.ByteString getHydrogenSulfideVapourBytes() {
        java.lang.Object ref = hydrogenSulfideVapour_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          hydrogenSulfideVapour_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string hydrogenSulfideVapour = 16;</code>
       *
       * @param value The hydrogenSulfideVapour to set.
       * @return This builder for chaining.
       */
      public Builder setHydrogenSulfideVapour(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        hydrogenSulfideVapour_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string hydrogenSulfideVapour = 16;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearHydrogenSulfideVapour() {

        hydrogenSulfideVapour_ = getDefaultInstance().getHydrogenSulfideVapour();
        onChanged();
        return this;
      }
      /**
       * <code>string hydrogenSulfideVapour = 16;</code>
       *
       * @param value The bytes for hydrogenSulfideVapour to set.
       * @return This builder for chaining.
       */
      public Builder setHydrogenSulfideVapourBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        hydrogenSulfideVapour_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object benzene_ = "";
      /**
       * <code>string benzene = 17;</code>
       *
       * @return The benzene.
       */
      public java.lang.String getBenzene() {
        java.lang.Object ref = benzene_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          benzene_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string benzene = 17;</code>
       *
       * @return The bytes for benzene.
       */
      public com.google.protobuf.ByteString getBenzeneBytes() {
        java.lang.Object ref = benzene_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          benzene_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string benzene = 17;</code>
       *
       * @param value The benzene to set.
       * @return This builder for chaining.
       */
      public Builder setBenzene(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        benzene_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string benzene = 17;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearBenzene() {

        benzene_ = getDefaultInstance().getBenzene();
        onChanged();
        return this;
      }
      /**
       * <code>string benzene = 17;</code>
       *
       * @param value The bytes for benzene to set.
       * @return This builder for chaining.
       */
      public Builder setBenzeneBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        benzene_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object specialInstrictionsRemark_ = "";
      /**
       * <code>string specialInstrictionsRemark = 18;</code>
       *
       * @return The specialInstrictionsRemark.
       */
      public java.lang.String getSpecialInstrictionsRemark() {
        java.lang.Object ref = specialInstrictionsRemark_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          specialInstrictionsRemark_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string specialInstrictionsRemark = 18;</code>
       *
       * @return The bytes for specialInstrictionsRemark.
       */
      public com.google.protobuf.ByteString getSpecialInstrictionsRemarkBytes() {
        java.lang.Object ref = specialInstrictionsRemark_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          specialInstrictionsRemark_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string specialInstrictionsRemark = 18;</code>
       *
       * @param value The specialInstrictionsRemark to set.
       * @return This builder for chaining.
       */
      public Builder setSpecialInstrictionsRemark(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        specialInstrictionsRemark_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string specialInstrictionsRemark = 18;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSpecialInstrictionsRemark() {

        specialInstrictionsRemark_ = getDefaultInstance().getSpecialInstrictionsRemark();
        onChanged();
        return this;
      }
      /**
       * <code>string specialInstrictionsRemark = 18;</code>
       *
       * @param value The bytes for specialInstrictionsRemark to set.
       * @return This builder for chaining.
       */
      public Builder setSpecialInstrictionsRemarkBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        specialInstrictionsRemark_ = value;
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

      // @@protoc_insertion_point(builder_scope:CargoDetailed)
    }

    // @@protoc_insertion_point(class_scope:CargoDetailed)
    private static final com.cpdss.common.generated.CargoInfo.CargoDetailed DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.CargoInfo.CargoDetailed();
    }

    public static com.cpdss.common.generated.CargoInfo.CargoDetailed getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoDetailed> PARSER =
        new com.google.protobuf.AbstractParser<CargoDetailed>() {
          @java.lang.Override
          public CargoDetailed parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoDetailed(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoDetailed> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoDetailed> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.CargoInfo.CargoDetailed getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoListRequest_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoListRequest_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoDetailReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoDetailReply_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoRequest_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoRequest_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor internal_static_Param_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Param_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoRequestWithPaging_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoRequestWithPaging_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoDetail_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoDetail_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoReply_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoDetailedReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoDetailedReply_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoByIdDetailedReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoByIdDetailedReply_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoDetailed_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoDetailed_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\020cargo_info.proto\032\014common.proto\"\036\n\020Carg"
          + "oListRequest\022\n\n\002id\030\001 \003(\003\"^\n\020CargoDetailR"
          + "eply\022!\n\013cargoDetail\030\001 \001(\0132\014.CargoDetail\022"
          + "\'\n\016responseStatus\030\002 \001(\0132\017.ResponseStatus"
          + "\"\332\001\n\014CargoRequest\022\021\n\tcompanyId\030\001 \001(\003\022\020\n\010"
          + "vesselId\030\002 \001(\003\022\020\n\010voyageId\030\003 \001(\003\022\027\n\017load"
          + "ableStudyId\030\004 \001(\003\022\017\n\007cargoId\030\005 \001(\003\022\014\n\004pa"
          + "ge\030\006 \001(\005\022\020\n\010pageSize\030\007 \001(\005\022\016\n\006sortBy\030\010 \001"
          + "(\t\022\017\n\007orderBy\030\t \001(\t\022\025\n\005param\030\n \003(\0132\006.Par"
          + "am\022\021\n\tcargoXIds\030\013 \003(\003\"#\n\005Param\022\013\n\003key\030\001 "
          + "\001(\t\022\r\n\005value\030\002 \001(\t\"7\n\026CargoRequestWithPa"
          + "ging\022\016\n\006offset\030\001 \001(\003\022\r\n\005limit\030\002 \001(\003\"\177\n\013C"
          + "argoDetail\022\n\n\002id\030\001 \001(\003\022\021\n\tcrudeType\030\002 \001("
          + "\t\022\024\n\014abbreviation\030\003 \001(\t\022\013\n\003api\030\004 \001(\t\022\031\n\021"
          + "isCondensateCargo\030\005 \001(\010\022\023\n\013isHrvpCargo\030\006"
          + " \001(\010\"S\n\nCargoReply\022\'\n\016responseStatus\030\001 \001"
          + "(\0132\017.ResponseStatus\022\034\n\006cargos\030\002 \003(\0132\014.Ca"
          + "rgoDetail\"t\n\022CargoDetailedReply\022\'\n\016respo"
          + "nseStatus\030\001 \001(\0132\017.ResponseStatus\022\036\n\006carg"
          + "os\030\002 \003(\0132\016.CargoDetailed\022\025\n\rtotalElement"
          + "s\030\003 \001(\003\"`\n\026CargoByIdDetailedReply\022\'\n\016res"
          + "ponseStatus\030\001 \001(\0132\017.ResponseStatus\022\035\n\005ca"
          + "rgo\030\002 \001(\0132\016.CargoDetailed\"\361\002\n\rCargoDetai"
          + "led\022\n\n\002id\030\001 \001(\003\022\014\n\004name\030\002 \001(\t\022\024\n\014abbrevi"
          + "ation\030\003 \001(\t\022\013\n\003api\030\004 \001(\t\022\014\n\004type\030\005 \001(\t\022\021"
          + "\n\tassayDate\030\006 \001(\t\022\014\n\004temp\030\007 \001(\t\022\032\n\022reidV"
          + "apourPressure\030\010 \001(\t\022\013\n\003gas\030\t \001(\t\022\020\n\010tota"
          + "lWax\030\n \001(\t\022\021\n\tpourPoint\030\013 \001(\t\022\022\n\ncloudPo"
          + "int\030\014 \001(\t\022\021\n\tviscosity\030\r \001(\t\022\020\n\010cowCodes"
          + "\030\016 \001(\t\022\032\n\022hydrogenSulfideOil\030\017 \001(\t\022\035\n\025hy"
          + "drogenSulfideVapour\030\020 \001(\t\022\017\n\007benzene\030\021 \001"
          + "(\t\022!\n\031specialInstrictionsRemark\030\022 \001(\t2\356\003"
          + "\n\020CargoInfoService\022,\n\014GetCargoInfo\022\r.Car"
          + "goRequest\032\013.CargoReply\"\000\022>\n\024GetCargoInfo"
          + "ByPaging\022\027.CargoRequestWithPaging\032\013.Carg"
          + "oReply\"\000\0226\n\020GetCargoInfoById\022\r.CargoRequ"
          + "est\032\021.CargoDetailReply\"\000\022;\n\027GetCargoInfo"
          + "sByCargoIds\022\021.CargoListRequest\032\013.CargoRe"
          + "ply\"\000\022<\n\024GetCargoInfoDetailed\022\r.CargoReq"
          + "uest\032\023.CargoDetailedReply\"\000\022D\n\030GetCargoI"
          + "nfoDetailedById\022\r.CargoRequest\032\027.CargoBy"
          + "IdDetailedReply\"\000\022;\n\017DeleteCargoById\022\r.C"
          + "argoRequest\032\027.CargoByIdDetailedReply\"\000\0226"
          + "\n\tSaveCargo\022\016.CargoDetailed\032\027.CargoByIdD"
          + "etailedReply\"\000B\036\n\032com.cpdss.common.gener"
          + "atedP\000b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
            });
    internal_static_CargoListRequest_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_CargoListRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoListRequest_descriptor,
            new java.lang.String[] {
              "Id",
            });
    internal_static_CargoDetailReply_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_CargoDetailReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoDetailReply_descriptor,
            new java.lang.String[] {
              "CargoDetail", "ResponseStatus",
            });
    internal_static_CargoRequest_descriptor = getDescriptor().getMessageTypes().get(2);
    internal_static_CargoRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoRequest_descriptor,
            new java.lang.String[] {
              "CompanyId",
              "VesselId",
              "VoyageId",
              "LoadableStudyId",
              "CargoId",
              "Page",
              "PageSize",
              "SortBy",
              "OrderBy",
              "Param",
              "CargoXIds",
            });
    internal_static_Param_descriptor = getDescriptor().getMessageTypes().get(3);
    internal_static_Param_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_Param_descriptor,
            new java.lang.String[] {
              "Key", "Value",
            });
    internal_static_CargoRequestWithPaging_descriptor = getDescriptor().getMessageTypes().get(4);
    internal_static_CargoRequestWithPaging_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoRequestWithPaging_descriptor,
            new java.lang.String[] {
              "Offset", "Limit",
            });
    internal_static_CargoDetail_descriptor = getDescriptor().getMessageTypes().get(5);
    internal_static_CargoDetail_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoDetail_descriptor,
            new java.lang.String[] {
              "Id", "CrudeType", "Abbreviation", "Api", "IsCondensateCargo", "IsHrvpCargo",
            });
    internal_static_CargoReply_descriptor = getDescriptor().getMessageTypes().get(6);
    internal_static_CargoReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus", "Cargos",
            });
    internal_static_CargoDetailedReply_descriptor = getDescriptor().getMessageTypes().get(7);
    internal_static_CargoDetailedReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoDetailedReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus", "Cargos", "TotalElements",
            });
    internal_static_CargoByIdDetailedReply_descriptor = getDescriptor().getMessageTypes().get(8);
    internal_static_CargoByIdDetailedReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoByIdDetailedReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus", "Cargo",
            });
    internal_static_CargoDetailed_descriptor = getDescriptor().getMessageTypes().get(9);
    internal_static_CargoDetailed_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoDetailed_descriptor,
            new java.lang.String[] {
              "Id",
              "Name",
              "Abbreviation",
              "Api",
              "Type",
              "AssayDate",
              "Temp",
              "ReidVapourPressure",
              "Gas",
              "TotalWax",
              "PourPoint",
              "CloudPoint",
              "Viscosity",
              "CowCodes",
              "HydrogenSulfideOil",
              "HydrogenSulfideVapour",
              "Benzene",
              "SpecialInstrictionsRemark",
            });
    com.cpdss.common.generated.Common.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
