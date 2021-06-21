/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

public final class Common {
  private Common() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /** Protobuf enum {@code PLANNING_TYPE} */
  public enum PLANNING_TYPE implements com.google.protobuf.ProtocolMessageEnum {
    /** <code>VALUE_A = 0;</code> */
    VALUE_A(0),
    /** <code>LOADABLE_STUDY = 1;</code> */
    LOADABLE_STUDY(1),
    /** <code>DISCHARGE_STUDY = 2;</code> */
    DISCHARGE_STUDY(2),
    UNRECOGNIZED(-1),
    ;

    /** <code>VALUE_A = 0;</code> */
    public static final int VALUE_A_VALUE = 0;
    /** <code>LOADABLE_STUDY = 1;</code> */
    public static final int LOADABLE_STUDY_VALUE = 1;
    /** <code>DISCHARGE_STUDY = 2;</code> */
    public static final int DISCHARGE_STUDY_VALUE = 2;

    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static PLANNING_TYPE valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static PLANNING_TYPE forNumber(int value) {
      switch (value) {
        case 0:
          return VALUE_A;
        case 1:
          return LOADABLE_STUDY;
        case 2:
          return DISCHARGE_STUDY;
        default:
          return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<PLANNING_TYPE> internalGetValueMap() {
      return internalValueMap;
    }

    private static final com.google.protobuf.Internal.EnumLiteMap<PLANNING_TYPE> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<PLANNING_TYPE>() {
          public PLANNING_TYPE findValueByNumber(int number) {
            return PLANNING_TYPE.forNumber(number);
          }
        };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor getValueDescriptor() {
      return getDescriptor().getValues().get(ordinal());
    }

    public final com.google.protobuf.Descriptors.EnumDescriptor getDescriptorForType() {
      return getDescriptor();
    }

    public static final com.google.protobuf.Descriptors.EnumDescriptor getDescriptor() {
      return com.cpdss.common.generated.Common.getDescriptor().getEnumTypes().get(0);
    }

    private static final PLANNING_TYPE[] VALUES = values();

    public static PLANNING_TYPE valueOf(com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException("EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private PLANNING_TYPE(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:PLANNING_TYPE)
  }

  public interface ResponseStatusOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:ResponseStatus)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string status = 1;</code>
     *
     * @return The status.
     */
    java.lang.String getStatus();
    /**
     * <code>string status = 1;</code>
     *
     * @return The bytes for status.
     */
    com.google.protobuf.ByteString getStatusBytes();

    /**
     * <code>string message = 2;</code>
     *
     * @return The message.
     */
    java.lang.String getMessage();
    /**
     * <code>string message = 2;</code>
     *
     * @return The bytes for message.
     */
    com.google.protobuf.ByteString getMessageBytes();

    /**
     * <code>string code = 3;</code>
     *
     * @return The code.
     */
    java.lang.String getCode();
    /**
     * <code>string code = 3;</code>
     *
     * @return The bytes for code.
     */
    com.google.protobuf.ByteString getCodeBytes();

    /**
     * <code>int32 httpStatusCode = 4;</code>
     *
     * @return The httpStatusCode.
     */
    int getHttpStatusCode();
  }
  /** Protobuf type {@code ResponseStatus} */
  public static final class ResponseStatus extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:ResponseStatus)
      ResponseStatusOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use ResponseStatus.newBuilder() to construct.
    private ResponseStatus(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private ResponseStatus() {
      status_ = "";
      message_ = "";
      code_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new ResponseStatus();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private ResponseStatus(
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

                status_ = s;
                break;
              }
            case 18:
              {
                java.lang.String s = input.readStringRequireUtf8();

                message_ = s;
                break;
              }
            case 26:
              {
                java.lang.String s = input.readStringRequireUtf8();

                code_ = s;
                break;
              }
            case 32:
              {
                httpStatusCode_ = input.readInt32();
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
      return com.cpdss.common.generated.Common.internal_static_ResponseStatus_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.Common.internal_static_ResponseStatus_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.Common.ResponseStatus.class,
              com.cpdss.common.generated.Common.ResponseStatus.Builder.class);
    }

    public static final int STATUS_FIELD_NUMBER = 1;
    private volatile java.lang.Object status_;
    /**
     * <code>string status = 1;</code>
     *
     * @return The status.
     */
    public java.lang.String getStatus() {
      java.lang.Object ref = status_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        status_ = s;
        return s;
      }
    }
    /**
     * <code>string status = 1;</code>
     *
     * @return The bytes for status.
     */
    public com.google.protobuf.ByteString getStatusBytes() {
      java.lang.Object ref = status_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        status_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int MESSAGE_FIELD_NUMBER = 2;
    private volatile java.lang.Object message_;
    /**
     * <code>string message = 2;</code>
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
     * <code>string message = 2;</code>
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

    public static final int CODE_FIELD_NUMBER = 3;
    private volatile java.lang.Object code_;
    /**
     * <code>string code = 3;</code>
     *
     * @return The code.
     */
    public java.lang.String getCode() {
      java.lang.Object ref = code_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        code_ = s;
        return s;
      }
    }
    /**
     * <code>string code = 3;</code>
     *
     * @return The bytes for code.
     */
    public com.google.protobuf.ByteString getCodeBytes() {
      java.lang.Object ref = code_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        code_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int HTTPSTATUSCODE_FIELD_NUMBER = 4;
    private int httpStatusCode_;
    /**
     * <code>int32 httpStatusCode = 4;</code>
     *
     * @return The httpStatusCode.
     */
    public int getHttpStatusCode() {
      return httpStatusCode_;
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
      if (!getStatusBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, status_);
      }
      if (!getMessageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, message_);
      }
      if (!getCodeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, code_);
      }
      if (httpStatusCode_ != 0) {
        output.writeInt32(4, httpStatusCode_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getStatusBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, status_);
      }
      if (!getMessageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, message_);
      }
      if (!getCodeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, code_);
      }
      if (httpStatusCode_ != 0) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(4, httpStatusCode_);
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
      if (!(obj instanceof com.cpdss.common.generated.Common.ResponseStatus)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.Common.ResponseStatus other =
          (com.cpdss.common.generated.Common.ResponseStatus) obj;

      if (!getStatus().equals(other.getStatus())) return false;
      if (!getMessage().equals(other.getMessage())) return false;
      if (!getCode().equals(other.getCode())) return false;
      if (getHttpStatusCode() != other.getHttpStatusCode()) return false;
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
      hash = (37 * hash) + STATUS_FIELD_NUMBER;
      hash = (53 * hash) + getStatus().hashCode();
      hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
      hash = (53 * hash) + getMessage().hashCode();
      hash = (37 * hash) + CODE_FIELD_NUMBER;
      hash = (53 * hash) + getCode().hashCode();
      hash = (37 * hash) + HTTPSTATUSCODE_FIELD_NUMBER;
      hash = (53 * hash) + getHttpStatusCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Common.ResponseStatus parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.Common.ResponseStatus prototype) {
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
    /** Protobuf type {@code ResponseStatus} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:ResponseStatus)
        com.cpdss.common.generated.Common.ResponseStatusOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.Common.internal_static_ResponseStatus_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.Common.internal_static_ResponseStatus_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.Common.ResponseStatus.class,
                com.cpdss.common.generated.Common.ResponseStatus.Builder.class);
      }

      // Construct using com.cpdss.common.generated.Common.ResponseStatus.newBuilder()
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
        status_ = "";

        message_ = "";

        code_ = "";

        httpStatusCode_ = 0;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.Common.internal_static_ResponseStatus_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Common.ResponseStatus getDefaultInstanceForType() {
        return com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.Common.ResponseStatus build() {
        com.cpdss.common.generated.Common.ResponseStatus result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Common.ResponseStatus buildPartial() {
        com.cpdss.common.generated.Common.ResponseStatus result =
            new com.cpdss.common.generated.Common.ResponseStatus(this);
        result.status_ = status_;
        result.message_ = message_;
        result.code_ = code_;
        result.httpStatusCode_ = httpStatusCode_;
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
        if (other instanceof com.cpdss.common.generated.Common.ResponseStatus) {
          return mergeFrom((com.cpdss.common.generated.Common.ResponseStatus) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.Common.ResponseStatus other) {
        if (other == com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance())
          return this;
        if (!other.getStatus().isEmpty()) {
          status_ = other.status_;
          onChanged();
        }
        if (!other.getMessage().isEmpty()) {
          message_ = other.message_;
          onChanged();
        }
        if (!other.getCode().isEmpty()) {
          code_ = other.code_;
          onChanged();
        }
        if (other.getHttpStatusCode() != 0) {
          setHttpStatusCode(other.getHttpStatusCode());
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
        com.cpdss.common.generated.Common.ResponseStatus parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.Common.ResponseStatus) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object status_ = "";
      /**
       * <code>string status = 1;</code>
       *
       * @return The status.
       */
      public java.lang.String getStatus() {
        java.lang.Object ref = status_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          status_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string status = 1;</code>
       *
       * @return The bytes for status.
       */
      public com.google.protobuf.ByteString getStatusBytes() {
        java.lang.Object ref = status_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          status_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string status = 1;</code>
       *
       * @param value The status to set.
       * @return This builder for chaining.
       */
      public Builder setStatus(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        status_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string status = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearStatus() {

        status_ = getDefaultInstance().getStatus();
        onChanged();
        return this;
      }
      /**
       * <code>string status = 1;</code>
       *
       * @param value The bytes for status to set.
       * @return This builder for chaining.
       */
      public Builder setStatusBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        status_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object message_ = "";
      /**
       * <code>string message = 2;</code>
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
       * <code>string message = 2;</code>
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
       * <code>string message = 2;</code>
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
       * <code>string message = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearMessage() {

        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }
      /**
       * <code>string message = 2;</code>
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

      private java.lang.Object code_ = "";
      /**
       * <code>string code = 3;</code>
       *
       * @return The code.
       */
      public java.lang.String getCode() {
        java.lang.Object ref = code_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          code_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string code = 3;</code>
       *
       * @return The bytes for code.
       */
      public com.google.protobuf.ByteString getCodeBytes() {
        java.lang.Object ref = code_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          code_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string code = 3;</code>
       *
       * @param value The code to set.
       * @return This builder for chaining.
       */
      public Builder setCode(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        code_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string code = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCode() {

        code_ = getDefaultInstance().getCode();
        onChanged();
        return this;
      }
      /**
       * <code>string code = 3;</code>
       *
       * @param value The bytes for code to set.
       * @return This builder for chaining.
       */
      public Builder setCodeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        code_ = value;
        onChanged();
        return this;
      }

      private int httpStatusCode_;
      /**
       * <code>int32 httpStatusCode = 4;</code>
       *
       * @return The httpStatusCode.
       */
      public int getHttpStatusCode() {
        return httpStatusCode_;
      }
      /**
       * <code>int32 httpStatusCode = 4;</code>
       *
       * @param value The httpStatusCode to set.
       * @return This builder for chaining.
       */
      public Builder setHttpStatusCode(int value) {

        httpStatusCode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 httpStatusCode = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearHttpStatusCode() {

        httpStatusCode_ = 0;
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

      // @@protoc_insertion_point(builder_scope:ResponseStatus)
    }

    // @@protoc_insertion_point(class_scope:ResponseStatus)
    private static final com.cpdss.common.generated.Common.ResponseStatus DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.Common.ResponseStatus();
    }

    public static com.cpdss.common.generated.Common.ResponseStatus getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ResponseStatus> PARSER =
        new com.google.protobuf.AbstractParser<ResponseStatus>() {
          @java.lang.Override
          public ResponseStatus parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new ResponseStatus(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<ResponseStatus> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ResponseStatus> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.Common.ResponseStatus getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface BillOfLaddingOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:BillOfLadding)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>int64 portId = 2;</code>
     *
     * @return The portId.
     */
    long getPortId();

    /**
     * <code>int64 cargoId = 3;</code>
     *
     * @return The cargoId.
     */
    long getCargoId();

    /**
     * <code>string quantityBbls = 4;</code>
     *
     * @return The quantityBbls.
     */
    java.lang.String getQuantityBbls();
    /**
     * <code>string quantityBbls = 4;</code>
     *
     * @return The bytes for quantityBbls.
     */
    com.google.protobuf.ByteString getQuantityBblsBytes();

    /**
     * <code>string quantityMt = 5;</code>
     *
     * @return The quantityMt.
     */
    java.lang.String getQuantityMt();
    /**
     * <code>string quantityMt = 5;</code>
     *
     * @return The bytes for quantityMt.
     */
    com.google.protobuf.ByteString getQuantityMtBytes();

    /**
     * <code>string quantityKl = 6;</code>
     *
     * @return The quantityKl.
     */
    java.lang.String getQuantityKl();
    /**
     * <code>string quantityKl = 6;</code>
     *
     * @return The bytes for quantityKl.
     */
    com.google.protobuf.ByteString getQuantityKlBytes();

    /**
     * <code>string api = 7;</code>
     *
     * @return The api.
     */
    java.lang.String getApi();
    /**
     * <code>string api = 7;</code>
     *
     * @return The bytes for api.
     */
    com.google.protobuf.ByteString getApiBytes();

    /**
     * <code>string temperature = 8;</code>
     *
     * @return The temperature.
     */
    java.lang.String getTemperature();
    /**
     * <code>string temperature = 8;</code>
     *
     * @return The bytes for temperature.
     */
    com.google.protobuf.ByteString getTemperatureBytes();
  }
  /** Protobuf type {@code BillOfLadding} */
  public static final class BillOfLadding extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:BillOfLadding)
      BillOfLaddingOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use BillOfLadding.newBuilder() to construct.
    private BillOfLadding(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private BillOfLadding() {
      quantityBbls_ = "";
      quantityMt_ = "";
      quantityKl_ = "";
      api_ = "";
      temperature_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new BillOfLadding();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private BillOfLadding(
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
            case 16:
              {
                portId_ = input.readInt64();
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

                quantityBbls_ = s;
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                quantityMt_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                quantityKl_ = s;
                break;
              }
            case 58:
              {
                java.lang.String s = input.readStringRequireUtf8();

                api_ = s;
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                temperature_ = s;
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
      return com.cpdss.common.generated.Common.internal_static_BillOfLadding_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.Common.internal_static_BillOfLadding_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.Common.BillOfLadding.class,
              com.cpdss.common.generated.Common.BillOfLadding.Builder.class);
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

    public static final int QUANTITYBBLS_FIELD_NUMBER = 4;
    private volatile java.lang.Object quantityBbls_;
    /**
     * <code>string quantityBbls = 4;</code>
     *
     * @return The quantityBbls.
     */
    public java.lang.String getQuantityBbls() {
      java.lang.Object ref = quantityBbls_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        quantityBbls_ = s;
        return s;
      }
    }
    /**
     * <code>string quantityBbls = 4;</code>
     *
     * @return The bytes for quantityBbls.
     */
    public com.google.protobuf.ByteString getQuantityBblsBytes() {
      java.lang.Object ref = quantityBbls_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        quantityBbls_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int QUANTITYMT_FIELD_NUMBER = 5;
    private volatile java.lang.Object quantityMt_;
    /**
     * <code>string quantityMt = 5;</code>
     *
     * @return The quantityMt.
     */
    public java.lang.String getQuantityMt() {
      java.lang.Object ref = quantityMt_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        quantityMt_ = s;
        return s;
      }
    }
    /**
     * <code>string quantityMt = 5;</code>
     *
     * @return The bytes for quantityMt.
     */
    public com.google.protobuf.ByteString getQuantityMtBytes() {
      java.lang.Object ref = quantityMt_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        quantityMt_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int QUANTITYKL_FIELD_NUMBER = 6;
    private volatile java.lang.Object quantityKl_;
    /**
     * <code>string quantityKl = 6;</code>
     *
     * @return The quantityKl.
     */
    public java.lang.String getQuantityKl() {
      java.lang.Object ref = quantityKl_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        quantityKl_ = s;
        return s;
      }
    }
    /**
     * <code>string quantityKl = 6;</code>
     *
     * @return The bytes for quantityKl.
     */
    public com.google.protobuf.ByteString getQuantityKlBytes() {
      java.lang.Object ref = quantityKl_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        quantityKl_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int API_FIELD_NUMBER = 7;
    private volatile java.lang.Object api_;
    /**
     * <code>string api = 7;</code>
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
     * <code>string api = 7;</code>
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

    public static final int TEMPERATURE_FIELD_NUMBER = 8;
    private volatile java.lang.Object temperature_;
    /**
     * <code>string temperature = 8;</code>
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
     * <code>string temperature = 8;</code>
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
      if (portId_ != 0L) {
        output.writeInt64(2, portId_);
      }
      if (cargoId_ != 0L) {
        output.writeInt64(3, cargoId_);
      }
      if (!getQuantityBblsBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, quantityBbls_);
      }
      if (!getQuantityMtBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, quantityMt_);
      }
      if (!getQuantityKlBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, quantityKl_);
      }
      if (!getApiBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, api_);
      }
      if (!getTemperatureBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, temperature_);
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
      if (portId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, portId_);
      }
      if (cargoId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, cargoId_);
      }
      if (!getQuantityBblsBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, quantityBbls_);
      }
      if (!getQuantityMtBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, quantityMt_);
      }
      if (!getQuantityKlBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, quantityKl_);
      }
      if (!getApiBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, api_);
      }
      if (!getTemperatureBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, temperature_);
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
      if (!(obj instanceof com.cpdss.common.generated.Common.BillOfLadding)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.Common.BillOfLadding other =
          (com.cpdss.common.generated.Common.BillOfLadding) obj;

      if (getId() != other.getId()) return false;
      if (getPortId() != other.getPortId()) return false;
      if (getCargoId() != other.getCargoId()) return false;
      if (!getQuantityBbls().equals(other.getQuantityBbls())) return false;
      if (!getQuantityMt().equals(other.getQuantityMt())) return false;
      if (!getQuantityKl().equals(other.getQuantityKl())) return false;
      if (!getApi().equals(other.getApi())) return false;
      if (!getTemperature().equals(other.getTemperature())) return false;
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
      hash = (37 * hash) + PORTID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortId());
      hash = (37 * hash) + CARGOID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoId());
      hash = (37 * hash) + QUANTITYBBLS_FIELD_NUMBER;
      hash = (53 * hash) + getQuantityBbls().hashCode();
      hash = (37 * hash) + QUANTITYMT_FIELD_NUMBER;
      hash = (53 * hash) + getQuantityMt().hashCode();
      hash = (37 * hash) + QUANTITYKL_FIELD_NUMBER;
      hash = (53 * hash) + getQuantityKl().hashCode();
      hash = (37 * hash) + API_FIELD_NUMBER;
      hash = (53 * hash) + getApi().hashCode();
      hash = (37 * hash) + TEMPERATURE_FIELD_NUMBER;
      hash = (53 * hash) + getTemperature().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Common.BillOfLadding parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.Common.BillOfLadding prototype) {
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
    /** Protobuf type {@code BillOfLadding} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:BillOfLadding)
        com.cpdss.common.generated.Common.BillOfLaddingOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.Common.internal_static_BillOfLadding_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.Common.internal_static_BillOfLadding_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.Common.BillOfLadding.class,
                com.cpdss.common.generated.Common.BillOfLadding.Builder.class);
      }

      // Construct using com.cpdss.common.generated.Common.BillOfLadding.newBuilder()
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

        portId_ = 0L;

        cargoId_ = 0L;

        quantityBbls_ = "";

        quantityMt_ = "";

        quantityKl_ = "";

        api_ = "";

        temperature_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.Common.internal_static_BillOfLadding_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Common.BillOfLadding getDefaultInstanceForType() {
        return com.cpdss.common.generated.Common.BillOfLadding.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.Common.BillOfLadding build() {
        com.cpdss.common.generated.Common.BillOfLadding result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Common.BillOfLadding buildPartial() {
        com.cpdss.common.generated.Common.BillOfLadding result =
            new com.cpdss.common.generated.Common.BillOfLadding(this);
        result.id_ = id_;
        result.portId_ = portId_;
        result.cargoId_ = cargoId_;
        result.quantityBbls_ = quantityBbls_;
        result.quantityMt_ = quantityMt_;
        result.quantityKl_ = quantityKl_;
        result.api_ = api_;
        result.temperature_ = temperature_;
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
        if (other instanceof com.cpdss.common.generated.Common.BillOfLadding) {
          return mergeFrom((com.cpdss.common.generated.Common.BillOfLadding) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.Common.BillOfLadding other) {
        if (other == com.cpdss.common.generated.Common.BillOfLadding.getDefaultInstance())
          return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (other.getPortId() != 0L) {
          setPortId(other.getPortId());
        }
        if (other.getCargoId() != 0L) {
          setCargoId(other.getCargoId());
        }
        if (!other.getQuantityBbls().isEmpty()) {
          quantityBbls_ = other.quantityBbls_;
          onChanged();
        }
        if (!other.getQuantityMt().isEmpty()) {
          quantityMt_ = other.quantityMt_;
          onChanged();
        }
        if (!other.getQuantityKl().isEmpty()) {
          quantityKl_ = other.quantityKl_;
          onChanged();
        }
        if (!other.getApi().isEmpty()) {
          api_ = other.api_;
          onChanged();
        }
        if (!other.getTemperature().isEmpty()) {
          temperature_ = other.temperature_;
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
        com.cpdss.common.generated.Common.BillOfLadding parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.Common.BillOfLadding) e.getUnfinishedMessage();
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

      private java.lang.Object quantityBbls_ = "";
      /**
       * <code>string quantityBbls = 4;</code>
       *
       * @return The quantityBbls.
       */
      public java.lang.String getQuantityBbls() {
        java.lang.Object ref = quantityBbls_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          quantityBbls_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string quantityBbls = 4;</code>
       *
       * @return The bytes for quantityBbls.
       */
      public com.google.protobuf.ByteString getQuantityBblsBytes() {
        java.lang.Object ref = quantityBbls_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          quantityBbls_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string quantityBbls = 4;</code>
       *
       * @param value The quantityBbls to set.
       * @return This builder for chaining.
       */
      public Builder setQuantityBbls(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        quantityBbls_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string quantityBbls = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearQuantityBbls() {

        quantityBbls_ = getDefaultInstance().getQuantityBbls();
        onChanged();
        return this;
      }
      /**
       * <code>string quantityBbls = 4;</code>
       *
       * @param value The bytes for quantityBbls to set.
       * @return This builder for chaining.
       */
      public Builder setQuantityBblsBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        quantityBbls_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object quantityMt_ = "";
      /**
       * <code>string quantityMt = 5;</code>
       *
       * @return The quantityMt.
       */
      public java.lang.String getQuantityMt() {
        java.lang.Object ref = quantityMt_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          quantityMt_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string quantityMt = 5;</code>
       *
       * @return The bytes for quantityMt.
       */
      public com.google.protobuf.ByteString getQuantityMtBytes() {
        java.lang.Object ref = quantityMt_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          quantityMt_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string quantityMt = 5;</code>
       *
       * @param value The quantityMt to set.
       * @return This builder for chaining.
       */
      public Builder setQuantityMt(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        quantityMt_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string quantityMt = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearQuantityMt() {

        quantityMt_ = getDefaultInstance().getQuantityMt();
        onChanged();
        return this;
      }
      /**
       * <code>string quantityMt = 5;</code>
       *
       * @param value The bytes for quantityMt to set.
       * @return This builder for chaining.
       */
      public Builder setQuantityMtBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        quantityMt_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object quantityKl_ = "";
      /**
       * <code>string quantityKl = 6;</code>
       *
       * @return The quantityKl.
       */
      public java.lang.String getQuantityKl() {
        java.lang.Object ref = quantityKl_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          quantityKl_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string quantityKl = 6;</code>
       *
       * @return The bytes for quantityKl.
       */
      public com.google.protobuf.ByteString getQuantityKlBytes() {
        java.lang.Object ref = quantityKl_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          quantityKl_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string quantityKl = 6;</code>
       *
       * @param value The quantityKl to set.
       * @return This builder for chaining.
       */
      public Builder setQuantityKl(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        quantityKl_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string quantityKl = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearQuantityKl() {

        quantityKl_ = getDefaultInstance().getQuantityKl();
        onChanged();
        return this;
      }
      /**
       * <code>string quantityKl = 6;</code>
       *
       * @param value The bytes for quantityKl to set.
       * @return This builder for chaining.
       */
      public Builder setQuantityKlBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        quantityKl_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object api_ = "";
      /**
       * <code>string api = 7;</code>
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
       * <code>string api = 7;</code>
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
       * <code>string api = 7;</code>
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
       * <code>string api = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearApi() {

        api_ = getDefaultInstance().getApi();
        onChanged();
        return this;
      }
      /**
       * <code>string api = 7;</code>
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
       * <code>string temperature = 8;</code>
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
       * <code>string temperature = 8;</code>
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
       * <code>string temperature = 8;</code>
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
       * <code>string temperature = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTemperature() {

        temperature_ = getDefaultInstance().getTemperature();
        onChanged();
        return this;
      }
      /**
       * <code>string temperature = 8;</code>
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

      // @@protoc_insertion_point(builder_scope:BillOfLadding)
    }

    // @@protoc_insertion_point(class_scope:BillOfLadding)
    private static final com.cpdss.common.generated.Common.BillOfLadding DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.Common.BillOfLadding();
    }

    public static com.cpdss.common.generated.Common.BillOfLadding getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<BillOfLadding> PARSER =
        new com.google.protobuf.AbstractParser<BillOfLadding>() {
          @java.lang.Override
          public BillOfLadding parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new BillOfLadding(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<BillOfLadding> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<BillOfLadding> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.Common.BillOfLadding getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_ResponseStatus_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ResponseStatus_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_BillOfLadding_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_BillOfLadding_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\014common.proto\"W\n\016ResponseStatus\022\016\n\006stat"
          + "us\030\001 \001(\t\022\017\n\007message\030\002 \001(\t\022\014\n\004code\030\003 \001(\t\022"
          + "\026\n\016httpStatusCode\030\004 \001(\005\"\234\001\n\rBillOfLaddin"
          + "g\022\n\n\002id\030\001 \001(\003\022\016\n\006portId\030\002 \001(\003\022\017\n\007cargoId"
          + "\030\003 \001(\003\022\024\n\014quantityBbls\030\004 \001(\t\022\022\n\nquantity"
          + "Mt\030\005 \001(\t\022\022\n\nquantityKl\030\006 \001(\t\022\013\n\003api\030\007 \001("
          + "\t\022\023\n\013temperature\030\010 \001(\t*E\n\rPLANNING_TYPE\022"
          + "\013\n\007VALUE_A\020\000\022\022\n\016LOADABLE_STUDY\020\001\022\023\n\017DISC"
          + "HARGE_STUDY\020\002B\036\n\032com.cpdss.common.genera"
          + "tedP\000b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData, new com.google.protobuf.Descriptors.FileDescriptor[] {});
    internal_static_ResponseStatus_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_ResponseStatus_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_ResponseStatus_descriptor,
            new java.lang.String[] {
              "Status", "Message", "Code", "HttpStatusCode",
            });
    internal_static_BillOfLadding_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_BillOfLadding_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_BillOfLadding_descriptor,
            new java.lang.String[] {
              "Id",
              "PortId",
              "CargoId",
              "QuantityBbls",
              "QuantityMt",
              "QuantityKl",
              "Api",
              "Temperature",
            });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
