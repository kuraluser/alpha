/* Licensed under Apache-2.0 */
package com.cpdss.common.generated;

public final class Loadicator {
  private Loadicator() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public interface LoadicatorRequestOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadicatorRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.StowagePlanDetails stowagePlanDetails = 1;</code>
     *
     * @return Whether the stowagePlanDetails field is set.
     */
    boolean hasStowagePlanDetails();
    /**
     * <code>.StowagePlanDetails stowagePlanDetails = 1;</code>
     *
     * @return The stowagePlanDetails.
     */
    com.cpdss.common.generated.Loadicator.StowagePlanDetails getStowagePlanDetails();
    /** <code>.StowagePlanDetails stowagePlanDetails = 1;</code> */
    com.cpdss.common.generated.Loadicator.StowagePlanDetailsOrBuilder
        getStowagePlanDetailsOrBuilder();

    /**
     * <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code>
     *
     * @return Whether the stowageDetailsInfo field is set.
     */
    boolean hasStowageDetailsInfo();
    /**
     * <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code>
     *
     * @return The stowageDetailsInfo.
     */
    com.cpdss.common.generated.Loadicator.StowageDetailsInfo getStowageDetailsInfo();
    /** <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code> */
    com.cpdss.common.generated.Loadicator.StowageDetailsInfoOrBuilder
        getStowageDetailsInfoOrBuilder();

    /**
     * <code>.CargoInfo cargoInfo = 3;</code>
     *
     * @return Whether the cargoInfo field is set.
     */
    boolean hasCargoInfo();
    /**
     * <code>.CargoInfo cargoInfo = 3;</code>
     *
     * @return The cargoInfo.
     */
    com.cpdss.common.generated.Loadicator.CargoInfo getCargoInfo();
    /** <code>.CargoInfo cargoInfo = 3;</code> */
    com.cpdss.common.generated.Loadicator.CargoInfoOrBuilder getCargoInfoOrBuilder();

    /**
     * <code>.OtherTankInfo otherTankInfo = 4;</code>
     *
     * @return Whether the otherTankInfo field is set.
     */
    boolean hasOtherTankInfo();
    /**
     * <code>.OtherTankInfo otherTankInfo = 4;</code>
     *
     * @return The otherTankInfo.
     */
    com.cpdss.common.generated.Loadicator.OtherTankInfo getOtherTankInfo();
    /** <code>.OtherTankInfo otherTankInfo = 4;</code> */
    com.cpdss.common.generated.Loadicator.OtherTankInfoOrBuilder getOtherTankInfoOrBuilder();
  }
  /** Protobuf type {@code LoadicatorRequest} */
  public static final class LoadicatorRequest extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadicatorRequest)
      LoadicatorRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadicatorRequest.newBuilder() to construct.
    private LoadicatorRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadicatorRequest() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadicatorRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadicatorRequest(
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
                com.cpdss.common.generated.Loadicator.StowagePlanDetails.Builder subBuilder = null;
                if (stowagePlanDetails_ != null) {
                  subBuilder = stowagePlanDetails_.toBuilder();
                }
                stowagePlanDetails_ =
                    input.readMessage(
                        com.cpdss.common.generated.Loadicator.StowagePlanDetails.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(stowagePlanDetails_);
                  stowagePlanDetails_ = subBuilder.buildPartial();
                }

                break;
              }
            case 18:
              {
                com.cpdss.common.generated.Loadicator.StowageDetailsInfo.Builder subBuilder = null;
                if (stowageDetailsInfo_ != null) {
                  subBuilder = stowageDetailsInfo_.toBuilder();
                }
                stowageDetailsInfo_ =
                    input.readMessage(
                        com.cpdss.common.generated.Loadicator.StowageDetailsInfo.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(stowageDetailsInfo_);
                  stowageDetailsInfo_ = subBuilder.buildPartial();
                }

                break;
              }
            case 26:
              {
                com.cpdss.common.generated.Loadicator.CargoInfo.Builder subBuilder = null;
                if (cargoInfo_ != null) {
                  subBuilder = cargoInfo_.toBuilder();
                }
                cargoInfo_ =
                    input.readMessage(
                        com.cpdss.common.generated.Loadicator.CargoInfo.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(cargoInfo_);
                  cargoInfo_ = subBuilder.buildPartial();
                }

                break;
              }
            case 34:
              {
                com.cpdss.common.generated.Loadicator.OtherTankInfo.Builder subBuilder = null;
                if (otherTankInfo_ != null) {
                  subBuilder = otherTankInfo_.toBuilder();
                }
                otherTankInfo_ =
                    input.readMessage(
                        com.cpdss.common.generated.Loadicator.OtherTankInfo.parser(),
                        extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(otherTankInfo_);
                  otherTankInfo_ = subBuilder.buildPartial();
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
      return com.cpdss.common.generated.Loadicator.internal_static_LoadicatorRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.Loadicator
          .internal_static_LoadicatorRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.Loadicator.LoadicatorRequest.class,
              com.cpdss.common.generated.Loadicator.LoadicatorRequest.Builder.class);
    }

    public static final int STOWAGEPLANDETAILS_FIELD_NUMBER = 1;
    private com.cpdss.common.generated.Loadicator.StowagePlanDetails stowagePlanDetails_;
    /**
     * <code>.StowagePlanDetails stowagePlanDetails = 1;</code>
     *
     * @return Whether the stowagePlanDetails field is set.
     */
    public boolean hasStowagePlanDetails() {
      return stowagePlanDetails_ != null;
    }
    /**
     * <code>.StowagePlanDetails stowagePlanDetails = 1;</code>
     *
     * @return The stowagePlanDetails.
     */
    public com.cpdss.common.generated.Loadicator.StowagePlanDetails getStowagePlanDetails() {
      return stowagePlanDetails_ == null
          ? com.cpdss.common.generated.Loadicator.StowagePlanDetails.getDefaultInstance()
          : stowagePlanDetails_;
    }
    /** <code>.StowagePlanDetails stowagePlanDetails = 1;</code> */
    public com.cpdss.common.generated.Loadicator.StowagePlanDetailsOrBuilder
        getStowagePlanDetailsOrBuilder() {
      return getStowagePlanDetails();
    }

    public static final int STOWAGEDETAILSINFO_FIELD_NUMBER = 2;
    private com.cpdss.common.generated.Loadicator.StowageDetailsInfo stowageDetailsInfo_;
    /**
     * <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code>
     *
     * @return Whether the stowageDetailsInfo field is set.
     */
    public boolean hasStowageDetailsInfo() {
      return stowageDetailsInfo_ != null;
    }
    /**
     * <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code>
     *
     * @return The stowageDetailsInfo.
     */
    public com.cpdss.common.generated.Loadicator.StowageDetailsInfo getStowageDetailsInfo() {
      return stowageDetailsInfo_ == null
          ? com.cpdss.common.generated.Loadicator.StowageDetailsInfo.getDefaultInstance()
          : stowageDetailsInfo_;
    }
    /** <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code> */
    public com.cpdss.common.generated.Loadicator.StowageDetailsInfoOrBuilder
        getStowageDetailsInfoOrBuilder() {
      return getStowageDetailsInfo();
    }

    public static final int CARGOINFO_FIELD_NUMBER = 3;
    private com.cpdss.common.generated.Loadicator.CargoInfo cargoInfo_;
    /**
     * <code>.CargoInfo cargoInfo = 3;</code>
     *
     * @return Whether the cargoInfo field is set.
     */
    public boolean hasCargoInfo() {
      return cargoInfo_ != null;
    }
    /**
     * <code>.CargoInfo cargoInfo = 3;</code>
     *
     * @return The cargoInfo.
     */
    public com.cpdss.common.generated.Loadicator.CargoInfo getCargoInfo() {
      return cargoInfo_ == null
          ? com.cpdss.common.generated.Loadicator.CargoInfo.getDefaultInstance()
          : cargoInfo_;
    }
    /** <code>.CargoInfo cargoInfo = 3;</code> */
    public com.cpdss.common.generated.Loadicator.CargoInfoOrBuilder getCargoInfoOrBuilder() {
      return getCargoInfo();
    }

    public static final int OTHERTANKINFO_FIELD_NUMBER = 4;
    private com.cpdss.common.generated.Loadicator.OtherTankInfo otherTankInfo_;
    /**
     * <code>.OtherTankInfo otherTankInfo = 4;</code>
     *
     * @return Whether the otherTankInfo field is set.
     */
    public boolean hasOtherTankInfo() {
      return otherTankInfo_ != null;
    }
    /**
     * <code>.OtherTankInfo otherTankInfo = 4;</code>
     *
     * @return The otherTankInfo.
     */
    public com.cpdss.common.generated.Loadicator.OtherTankInfo getOtherTankInfo() {
      return otherTankInfo_ == null
          ? com.cpdss.common.generated.Loadicator.OtherTankInfo.getDefaultInstance()
          : otherTankInfo_;
    }
    /** <code>.OtherTankInfo otherTankInfo = 4;</code> */
    public com.cpdss.common.generated.Loadicator.OtherTankInfoOrBuilder
        getOtherTankInfoOrBuilder() {
      return getOtherTankInfo();
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
      if (stowagePlanDetails_ != null) {
        output.writeMessage(1, getStowagePlanDetails());
      }
      if (stowageDetailsInfo_ != null) {
        output.writeMessage(2, getStowageDetailsInfo());
      }
      if (cargoInfo_ != null) {
        output.writeMessage(3, getCargoInfo());
      }
      if (otherTankInfo_ != null) {
        output.writeMessage(4, getOtherTankInfo());
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (stowagePlanDetails_ != null) {
        size +=
            com.google.protobuf.CodedOutputStream.computeMessageSize(1, getStowagePlanDetails());
      }
      if (stowageDetailsInfo_ != null) {
        size +=
            com.google.protobuf.CodedOutputStream.computeMessageSize(2, getStowageDetailsInfo());
      }
      if (cargoInfo_ != null) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, getCargoInfo());
      }
      if (otherTankInfo_ != null) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(4, getOtherTankInfo());
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
      if (!(obj instanceof com.cpdss.common.generated.Loadicator.LoadicatorRequest)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.Loadicator.LoadicatorRequest other =
          (com.cpdss.common.generated.Loadicator.LoadicatorRequest) obj;

      if (hasStowagePlanDetails() != other.hasStowagePlanDetails()) return false;
      if (hasStowagePlanDetails()) {
        if (!getStowagePlanDetails().equals(other.getStowagePlanDetails())) return false;
      }
      if (hasStowageDetailsInfo() != other.hasStowageDetailsInfo()) return false;
      if (hasStowageDetailsInfo()) {
        if (!getStowageDetailsInfo().equals(other.getStowageDetailsInfo())) return false;
      }
      if (hasCargoInfo() != other.hasCargoInfo()) return false;
      if (hasCargoInfo()) {
        if (!getCargoInfo().equals(other.getCargoInfo())) return false;
      }
      if (hasOtherTankInfo() != other.hasOtherTankInfo()) return false;
      if (hasOtherTankInfo()) {
        if (!getOtherTankInfo().equals(other.getOtherTankInfo())) return false;
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
      if (hasStowagePlanDetails()) {
        hash = (37 * hash) + STOWAGEPLANDETAILS_FIELD_NUMBER;
        hash = (53 * hash) + getStowagePlanDetails().hashCode();
      }
      if (hasStowageDetailsInfo()) {
        hash = (37 * hash) + STOWAGEDETAILSINFO_FIELD_NUMBER;
        hash = (53 * hash) + getStowageDetailsInfo().hashCode();
      }
      if (hasCargoInfo()) {
        hash = (37 * hash) + CARGOINFO_FIELD_NUMBER;
        hash = (53 * hash) + getCargoInfo().hashCode();
      }
      if (hasOtherTankInfo()) {
        hash = (37 * hash) + OTHERTANKINFO_FIELD_NUMBER;
        hash = (53 * hash) + getOtherTankInfo().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest parseFrom(
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
        com.cpdss.common.generated.Loadicator.LoadicatorRequest prototype) {
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
    /** Protobuf type {@code LoadicatorRequest} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadicatorRequest)
        com.cpdss.common.generated.Loadicator.LoadicatorRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.Loadicator.internal_static_LoadicatorRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.Loadicator
            .internal_static_LoadicatorRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.Loadicator.LoadicatorRequest.class,
                com.cpdss.common.generated.Loadicator.LoadicatorRequest.Builder.class);
      }

      // Construct using com.cpdss.common.generated.Loadicator.LoadicatorRequest.newBuilder()
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
        if (stowagePlanDetailsBuilder_ == null) {
          stowagePlanDetails_ = null;
        } else {
          stowagePlanDetails_ = null;
          stowagePlanDetailsBuilder_ = null;
        }
        if (stowageDetailsInfoBuilder_ == null) {
          stowageDetailsInfo_ = null;
        } else {
          stowageDetailsInfo_ = null;
          stowageDetailsInfoBuilder_ = null;
        }
        if (cargoInfoBuilder_ == null) {
          cargoInfo_ = null;
        } else {
          cargoInfo_ = null;
          cargoInfoBuilder_ = null;
        }
        if (otherTankInfoBuilder_ == null) {
          otherTankInfo_ = null;
        } else {
          otherTankInfo_ = null;
          otherTankInfoBuilder_ = null;
        }
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.Loadicator.internal_static_LoadicatorRequest_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.LoadicatorRequest getDefaultInstanceForType() {
        return com.cpdss.common.generated.Loadicator.LoadicatorRequest.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.LoadicatorRequest build() {
        com.cpdss.common.generated.Loadicator.LoadicatorRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.LoadicatorRequest buildPartial() {
        com.cpdss.common.generated.Loadicator.LoadicatorRequest result =
            new com.cpdss.common.generated.Loadicator.LoadicatorRequest(this);
        if (stowagePlanDetailsBuilder_ == null) {
          result.stowagePlanDetails_ = stowagePlanDetails_;
        } else {
          result.stowagePlanDetails_ = stowagePlanDetailsBuilder_.build();
        }
        if (stowageDetailsInfoBuilder_ == null) {
          result.stowageDetailsInfo_ = stowageDetailsInfo_;
        } else {
          result.stowageDetailsInfo_ = stowageDetailsInfoBuilder_.build();
        }
        if (cargoInfoBuilder_ == null) {
          result.cargoInfo_ = cargoInfo_;
        } else {
          result.cargoInfo_ = cargoInfoBuilder_.build();
        }
        if (otherTankInfoBuilder_ == null) {
          result.otherTankInfo_ = otherTankInfo_;
        } else {
          result.otherTankInfo_ = otherTankInfoBuilder_.build();
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
        if (other instanceof com.cpdss.common.generated.Loadicator.LoadicatorRequest) {
          return mergeFrom((com.cpdss.common.generated.Loadicator.LoadicatorRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.Loadicator.LoadicatorRequest other) {
        if (other == com.cpdss.common.generated.Loadicator.LoadicatorRequest.getDefaultInstance())
          return this;
        if (other.hasStowagePlanDetails()) {
          mergeStowagePlanDetails(other.getStowagePlanDetails());
        }
        if (other.hasStowageDetailsInfo()) {
          mergeStowageDetailsInfo(other.getStowageDetailsInfo());
        }
        if (other.hasCargoInfo()) {
          mergeCargoInfo(other.getCargoInfo());
        }
        if (other.hasOtherTankInfo()) {
          mergeOtherTankInfo(other.getOtherTankInfo());
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
        com.cpdss.common.generated.Loadicator.LoadicatorRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.Loadicator.LoadicatorRequest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private com.cpdss.common.generated.Loadicator.StowagePlanDetails stowagePlanDetails_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.Loadicator.StowagePlanDetails,
              com.cpdss.common.generated.Loadicator.StowagePlanDetails.Builder,
              com.cpdss.common.generated.Loadicator.StowagePlanDetailsOrBuilder>
          stowagePlanDetailsBuilder_;
      /**
       * <code>.StowagePlanDetails stowagePlanDetails = 1;</code>
       *
       * @return Whether the stowagePlanDetails field is set.
       */
      public boolean hasStowagePlanDetails() {
        return stowagePlanDetailsBuilder_ != null || stowagePlanDetails_ != null;
      }
      /**
       * <code>.StowagePlanDetails stowagePlanDetails = 1;</code>
       *
       * @return The stowagePlanDetails.
       */
      public com.cpdss.common.generated.Loadicator.StowagePlanDetails getStowagePlanDetails() {
        if (stowagePlanDetailsBuilder_ == null) {
          return stowagePlanDetails_ == null
              ? com.cpdss.common.generated.Loadicator.StowagePlanDetails.getDefaultInstance()
              : stowagePlanDetails_;
        } else {
          return stowagePlanDetailsBuilder_.getMessage();
        }
      }
      /** <code>.StowagePlanDetails stowagePlanDetails = 1;</code> */
      public Builder setStowagePlanDetails(
          com.cpdss.common.generated.Loadicator.StowagePlanDetails value) {
        if (stowagePlanDetailsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          stowagePlanDetails_ = value;
          onChanged();
        } else {
          stowagePlanDetailsBuilder_.setMessage(value);
        }

        return this;
      }
      /** <code>.StowagePlanDetails stowagePlanDetails = 1;</code> */
      public Builder setStowagePlanDetails(
          com.cpdss.common.generated.Loadicator.StowagePlanDetails.Builder builderForValue) {
        if (stowagePlanDetailsBuilder_ == null) {
          stowagePlanDetails_ = builderForValue.build();
          onChanged();
        } else {
          stowagePlanDetailsBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /** <code>.StowagePlanDetails stowagePlanDetails = 1;</code> */
      public Builder mergeStowagePlanDetails(
          com.cpdss.common.generated.Loadicator.StowagePlanDetails value) {
        if (stowagePlanDetailsBuilder_ == null) {
          if (stowagePlanDetails_ != null) {
            stowagePlanDetails_ =
                com.cpdss.common.generated.Loadicator.StowagePlanDetails.newBuilder(
                        stowagePlanDetails_)
                    .mergeFrom(value)
                    .buildPartial();
          } else {
            stowagePlanDetails_ = value;
          }
          onChanged();
        } else {
          stowagePlanDetailsBuilder_.mergeFrom(value);
        }

        return this;
      }
      /** <code>.StowagePlanDetails stowagePlanDetails = 1;</code> */
      public Builder clearStowagePlanDetails() {
        if (stowagePlanDetailsBuilder_ == null) {
          stowagePlanDetails_ = null;
          onChanged();
        } else {
          stowagePlanDetails_ = null;
          stowagePlanDetailsBuilder_ = null;
        }

        return this;
      }
      /** <code>.StowagePlanDetails stowagePlanDetails = 1;</code> */
      public com.cpdss.common.generated.Loadicator.StowagePlanDetails.Builder
          getStowagePlanDetailsBuilder() {

        onChanged();
        return getStowagePlanDetailsFieldBuilder().getBuilder();
      }
      /** <code>.StowagePlanDetails stowagePlanDetails = 1;</code> */
      public com.cpdss.common.generated.Loadicator.StowagePlanDetailsOrBuilder
          getStowagePlanDetailsOrBuilder() {
        if (stowagePlanDetailsBuilder_ != null) {
          return stowagePlanDetailsBuilder_.getMessageOrBuilder();
        } else {
          return stowagePlanDetails_ == null
              ? com.cpdss.common.generated.Loadicator.StowagePlanDetails.getDefaultInstance()
              : stowagePlanDetails_;
        }
      }
      /** <code>.StowagePlanDetails stowagePlanDetails = 1;</code> */
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.Loadicator.StowagePlanDetails,
              com.cpdss.common.generated.Loadicator.StowagePlanDetails.Builder,
              com.cpdss.common.generated.Loadicator.StowagePlanDetailsOrBuilder>
          getStowagePlanDetailsFieldBuilder() {
        if (stowagePlanDetailsBuilder_ == null) {
          stowagePlanDetailsBuilder_ =
              new com.google.protobuf.SingleFieldBuilderV3<
                  com.cpdss.common.generated.Loadicator.StowagePlanDetails,
                  com.cpdss.common.generated.Loadicator.StowagePlanDetails.Builder,
                  com.cpdss.common.generated.Loadicator.StowagePlanDetailsOrBuilder>(
                  getStowagePlanDetails(), getParentForChildren(), isClean());
          stowagePlanDetails_ = null;
        }
        return stowagePlanDetailsBuilder_;
      }

      private com.cpdss.common.generated.Loadicator.StowageDetailsInfo stowageDetailsInfo_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.Loadicator.StowageDetailsInfo,
              com.cpdss.common.generated.Loadicator.StowageDetailsInfo.Builder,
              com.cpdss.common.generated.Loadicator.StowageDetailsInfoOrBuilder>
          stowageDetailsInfoBuilder_;
      /**
       * <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code>
       *
       * @return Whether the stowageDetailsInfo field is set.
       */
      public boolean hasStowageDetailsInfo() {
        return stowageDetailsInfoBuilder_ != null || stowageDetailsInfo_ != null;
      }
      /**
       * <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code>
       *
       * @return The stowageDetailsInfo.
       */
      public com.cpdss.common.generated.Loadicator.StowageDetailsInfo getStowageDetailsInfo() {
        if (stowageDetailsInfoBuilder_ == null) {
          return stowageDetailsInfo_ == null
              ? com.cpdss.common.generated.Loadicator.StowageDetailsInfo.getDefaultInstance()
              : stowageDetailsInfo_;
        } else {
          return stowageDetailsInfoBuilder_.getMessage();
        }
      }
      /** <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code> */
      public Builder setStowageDetailsInfo(
          com.cpdss.common.generated.Loadicator.StowageDetailsInfo value) {
        if (stowageDetailsInfoBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          stowageDetailsInfo_ = value;
          onChanged();
        } else {
          stowageDetailsInfoBuilder_.setMessage(value);
        }

        return this;
      }
      /** <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code> */
      public Builder setStowageDetailsInfo(
          com.cpdss.common.generated.Loadicator.StowageDetailsInfo.Builder builderForValue) {
        if (stowageDetailsInfoBuilder_ == null) {
          stowageDetailsInfo_ = builderForValue.build();
          onChanged();
        } else {
          stowageDetailsInfoBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /** <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code> */
      public Builder mergeStowageDetailsInfo(
          com.cpdss.common.generated.Loadicator.StowageDetailsInfo value) {
        if (stowageDetailsInfoBuilder_ == null) {
          if (stowageDetailsInfo_ != null) {
            stowageDetailsInfo_ =
                com.cpdss.common.generated.Loadicator.StowageDetailsInfo.newBuilder(
                        stowageDetailsInfo_)
                    .mergeFrom(value)
                    .buildPartial();
          } else {
            stowageDetailsInfo_ = value;
          }
          onChanged();
        } else {
          stowageDetailsInfoBuilder_.mergeFrom(value);
        }

        return this;
      }
      /** <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code> */
      public Builder clearStowageDetailsInfo() {
        if (stowageDetailsInfoBuilder_ == null) {
          stowageDetailsInfo_ = null;
          onChanged();
        } else {
          stowageDetailsInfo_ = null;
          stowageDetailsInfoBuilder_ = null;
        }

        return this;
      }
      /** <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code> */
      public com.cpdss.common.generated.Loadicator.StowageDetailsInfo.Builder
          getStowageDetailsInfoBuilder() {

        onChanged();
        return getStowageDetailsInfoFieldBuilder().getBuilder();
      }
      /** <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code> */
      public com.cpdss.common.generated.Loadicator.StowageDetailsInfoOrBuilder
          getStowageDetailsInfoOrBuilder() {
        if (stowageDetailsInfoBuilder_ != null) {
          return stowageDetailsInfoBuilder_.getMessageOrBuilder();
        } else {
          return stowageDetailsInfo_ == null
              ? com.cpdss.common.generated.Loadicator.StowageDetailsInfo.getDefaultInstance()
              : stowageDetailsInfo_;
        }
      }
      /** <code>.StowageDetailsInfo stowageDetailsInfo = 2;</code> */
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.Loadicator.StowageDetailsInfo,
              com.cpdss.common.generated.Loadicator.StowageDetailsInfo.Builder,
              com.cpdss.common.generated.Loadicator.StowageDetailsInfoOrBuilder>
          getStowageDetailsInfoFieldBuilder() {
        if (stowageDetailsInfoBuilder_ == null) {
          stowageDetailsInfoBuilder_ =
              new com.google.protobuf.SingleFieldBuilderV3<
                  com.cpdss.common.generated.Loadicator.StowageDetailsInfo,
                  com.cpdss.common.generated.Loadicator.StowageDetailsInfo.Builder,
                  com.cpdss.common.generated.Loadicator.StowageDetailsInfoOrBuilder>(
                  getStowageDetailsInfo(), getParentForChildren(), isClean());
          stowageDetailsInfo_ = null;
        }
        return stowageDetailsInfoBuilder_;
      }

      private com.cpdss.common.generated.Loadicator.CargoInfo cargoInfo_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.Loadicator.CargoInfo,
              com.cpdss.common.generated.Loadicator.CargoInfo.Builder,
              com.cpdss.common.generated.Loadicator.CargoInfoOrBuilder>
          cargoInfoBuilder_;
      /**
       * <code>.CargoInfo cargoInfo = 3;</code>
       *
       * @return Whether the cargoInfo field is set.
       */
      public boolean hasCargoInfo() {
        return cargoInfoBuilder_ != null || cargoInfo_ != null;
      }
      /**
       * <code>.CargoInfo cargoInfo = 3;</code>
       *
       * @return The cargoInfo.
       */
      public com.cpdss.common.generated.Loadicator.CargoInfo getCargoInfo() {
        if (cargoInfoBuilder_ == null) {
          return cargoInfo_ == null
              ? com.cpdss.common.generated.Loadicator.CargoInfo.getDefaultInstance()
              : cargoInfo_;
        } else {
          return cargoInfoBuilder_.getMessage();
        }
      }
      /** <code>.CargoInfo cargoInfo = 3;</code> */
      public Builder setCargoInfo(com.cpdss.common.generated.Loadicator.CargoInfo value) {
        if (cargoInfoBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          cargoInfo_ = value;
          onChanged();
        } else {
          cargoInfoBuilder_.setMessage(value);
        }

        return this;
      }
      /** <code>.CargoInfo cargoInfo = 3;</code> */
      public Builder setCargoInfo(
          com.cpdss.common.generated.Loadicator.CargoInfo.Builder builderForValue) {
        if (cargoInfoBuilder_ == null) {
          cargoInfo_ = builderForValue.build();
          onChanged();
        } else {
          cargoInfoBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /** <code>.CargoInfo cargoInfo = 3;</code> */
      public Builder mergeCargoInfo(com.cpdss.common.generated.Loadicator.CargoInfo value) {
        if (cargoInfoBuilder_ == null) {
          if (cargoInfo_ != null) {
            cargoInfo_ =
                com.cpdss.common.generated.Loadicator.CargoInfo.newBuilder(cargoInfo_)
                    .mergeFrom(value)
                    .buildPartial();
          } else {
            cargoInfo_ = value;
          }
          onChanged();
        } else {
          cargoInfoBuilder_.mergeFrom(value);
        }

        return this;
      }
      /** <code>.CargoInfo cargoInfo = 3;</code> */
      public Builder clearCargoInfo() {
        if (cargoInfoBuilder_ == null) {
          cargoInfo_ = null;
          onChanged();
        } else {
          cargoInfo_ = null;
          cargoInfoBuilder_ = null;
        }

        return this;
      }
      /** <code>.CargoInfo cargoInfo = 3;</code> */
      public com.cpdss.common.generated.Loadicator.CargoInfo.Builder getCargoInfoBuilder() {

        onChanged();
        return getCargoInfoFieldBuilder().getBuilder();
      }
      /** <code>.CargoInfo cargoInfo = 3;</code> */
      public com.cpdss.common.generated.Loadicator.CargoInfoOrBuilder getCargoInfoOrBuilder() {
        if (cargoInfoBuilder_ != null) {
          return cargoInfoBuilder_.getMessageOrBuilder();
        } else {
          return cargoInfo_ == null
              ? com.cpdss.common.generated.Loadicator.CargoInfo.getDefaultInstance()
              : cargoInfo_;
        }
      }
      /** <code>.CargoInfo cargoInfo = 3;</code> */
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.Loadicator.CargoInfo,
              com.cpdss.common.generated.Loadicator.CargoInfo.Builder,
              com.cpdss.common.generated.Loadicator.CargoInfoOrBuilder>
          getCargoInfoFieldBuilder() {
        if (cargoInfoBuilder_ == null) {
          cargoInfoBuilder_ =
              new com.google.protobuf.SingleFieldBuilderV3<
                  com.cpdss.common.generated.Loadicator.CargoInfo,
                  com.cpdss.common.generated.Loadicator.CargoInfo.Builder,
                  com.cpdss.common.generated.Loadicator.CargoInfoOrBuilder>(
                  getCargoInfo(), getParentForChildren(), isClean());
          cargoInfo_ = null;
        }
        return cargoInfoBuilder_;
      }

      private com.cpdss.common.generated.Loadicator.OtherTankInfo otherTankInfo_;
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.Loadicator.OtherTankInfo,
              com.cpdss.common.generated.Loadicator.OtherTankInfo.Builder,
              com.cpdss.common.generated.Loadicator.OtherTankInfoOrBuilder>
          otherTankInfoBuilder_;
      /**
       * <code>.OtherTankInfo otherTankInfo = 4;</code>
       *
       * @return Whether the otherTankInfo field is set.
       */
      public boolean hasOtherTankInfo() {
        return otherTankInfoBuilder_ != null || otherTankInfo_ != null;
      }
      /**
       * <code>.OtherTankInfo otherTankInfo = 4;</code>
       *
       * @return The otherTankInfo.
       */
      public com.cpdss.common.generated.Loadicator.OtherTankInfo getOtherTankInfo() {
        if (otherTankInfoBuilder_ == null) {
          return otherTankInfo_ == null
              ? com.cpdss.common.generated.Loadicator.OtherTankInfo.getDefaultInstance()
              : otherTankInfo_;
        } else {
          return otherTankInfoBuilder_.getMessage();
        }
      }
      /** <code>.OtherTankInfo otherTankInfo = 4;</code> */
      public Builder setOtherTankInfo(com.cpdss.common.generated.Loadicator.OtherTankInfo value) {
        if (otherTankInfoBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          otherTankInfo_ = value;
          onChanged();
        } else {
          otherTankInfoBuilder_.setMessage(value);
        }

        return this;
      }
      /** <code>.OtherTankInfo otherTankInfo = 4;</code> */
      public Builder setOtherTankInfo(
          com.cpdss.common.generated.Loadicator.OtherTankInfo.Builder builderForValue) {
        if (otherTankInfoBuilder_ == null) {
          otherTankInfo_ = builderForValue.build();
          onChanged();
        } else {
          otherTankInfoBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /** <code>.OtherTankInfo otherTankInfo = 4;</code> */
      public Builder mergeOtherTankInfo(com.cpdss.common.generated.Loadicator.OtherTankInfo value) {
        if (otherTankInfoBuilder_ == null) {
          if (otherTankInfo_ != null) {
            otherTankInfo_ =
                com.cpdss.common.generated.Loadicator.OtherTankInfo.newBuilder(otherTankInfo_)
                    .mergeFrom(value)
                    .buildPartial();
          } else {
            otherTankInfo_ = value;
          }
          onChanged();
        } else {
          otherTankInfoBuilder_.mergeFrom(value);
        }

        return this;
      }
      /** <code>.OtherTankInfo otherTankInfo = 4;</code> */
      public Builder clearOtherTankInfo() {
        if (otherTankInfoBuilder_ == null) {
          otherTankInfo_ = null;
          onChanged();
        } else {
          otherTankInfo_ = null;
          otherTankInfoBuilder_ = null;
        }

        return this;
      }
      /** <code>.OtherTankInfo otherTankInfo = 4;</code> */
      public com.cpdss.common.generated.Loadicator.OtherTankInfo.Builder getOtherTankInfoBuilder() {

        onChanged();
        return getOtherTankInfoFieldBuilder().getBuilder();
      }
      /** <code>.OtherTankInfo otherTankInfo = 4;</code> */
      public com.cpdss.common.generated.Loadicator.OtherTankInfoOrBuilder
          getOtherTankInfoOrBuilder() {
        if (otherTankInfoBuilder_ != null) {
          return otherTankInfoBuilder_.getMessageOrBuilder();
        } else {
          return otherTankInfo_ == null
              ? com.cpdss.common.generated.Loadicator.OtherTankInfo.getDefaultInstance()
              : otherTankInfo_;
        }
      }
      /** <code>.OtherTankInfo otherTankInfo = 4;</code> */
      private com.google.protobuf.SingleFieldBuilderV3<
              com.cpdss.common.generated.Loadicator.OtherTankInfo,
              com.cpdss.common.generated.Loadicator.OtherTankInfo.Builder,
              com.cpdss.common.generated.Loadicator.OtherTankInfoOrBuilder>
          getOtherTankInfoFieldBuilder() {
        if (otherTankInfoBuilder_ == null) {
          otherTankInfoBuilder_ =
              new com.google.protobuf.SingleFieldBuilderV3<
                  com.cpdss.common.generated.Loadicator.OtherTankInfo,
                  com.cpdss.common.generated.Loadicator.OtherTankInfo.Builder,
                  com.cpdss.common.generated.Loadicator.OtherTankInfoOrBuilder>(
                  getOtherTankInfo(), getParentForChildren(), isClean());
          otherTankInfo_ = null;
        }
        return otherTankInfoBuilder_;
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

      // @@protoc_insertion_point(builder_scope:LoadicatorRequest)
    }

    // @@protoc_insertion_point(class_scope:LoadicatorRequest)
    private static final com.cpdss.common.generated.Loadicator.LoadicatorRequest DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.Loadicator.LoadicatorRequest();
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadicatorRequest> PARSER =
        new com.google.protobuf.AbstractParser<LoadicatorRequest>() {
          @java.lang.Override
          public LoadicatorRequest parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadicatorRequest(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadicatorRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadicatorRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.Loadicator.LoadicatorRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface StowagePlanDetailsOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:StowagePlanDetails)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    long getId();

    /**
     * <code>int64 vesselId = 2;</code>
     *
     * @return The vesselId.
     */
    long getVesselId();

    /**
     * <code>string imoNumber = 3;</code>
     *
     * @return The imoNumber.
     */
    java.lang.String getImoNumber();
    /**
     * <code>string imoNumber = 3;</code>
     *
     * @return The bytes for imoNumber.
     */
    com.google.protobuf.ByteString getImoNumberBytes();

    /**
     * <code>int64 companyId = 4;</code>
     *
     * @return The companyId.
     */
    long getCompanyId();

    /**
     * <code>int64 shipType = 5;</code>
     *
     * @return The shipType.
     */
    long getShipType();

    /**
     * <code>string vesselCode = 6;</code>
     *
     * @return The vesselCode.
     */
    java.lang.String getVesselCode();
    /**
     * <code>string vesselCode = 6;</code>
     *
     * @return The bytes for vesselCode.
     */
    com.google.protobuf.ByteString getVesselCodeBytes();

    /**
     * <code>int64 bookingListId = 7;</code>
     *
     * @return The bookingListId.
     */
    long getBookingListId();

    /**
     * <code>int64 stowageId = 8;</code>
     *
     * @return The stowageId.
     */
    long getStowageId();

    /**
     * <code>int64 portId = 9;</code>
     *
     * @return The portId.
     */
    long getPortId();

    /**
     * <code>string portCode = 10;</code>
     *
     * @return The portCode.
     */
    java.lang.String getPortCode();
    /**
     * <code>string portCode = 10;</code>
     *
     * @return The bytes for portCode.
     */
    com.google.protobuf.ByteString getPortCodeBytes();

    /**
     * <code>int64 status = 11;</code>
     *
     * @return The status.
     */
    long getStatus();

    /**
     * <code>int64 deadweightConstant = 12;</code>
     *
     * @return The deadweightConstant.
     */
    long getDeadweightConstant();

    /**
     * <code>int64 provisionalConstant = 13;</code>
     *
     * @return The provisionalConstant.
     */
    long getProvisionalConstant();

    /**
     * <code>int64 calCount = 14;</code>
     *
     * @return The calCount.
     */
    long getCalCount();

    /**
     * <code>string saveStatus = 15;</code>
     *
     * @return The saveStatus.
     */
    java.lang.String getSaveStatus();
    /**
     * <code>string saveStatus = 15;</code>
     *
     * @return The bytes for saveStatus.
     */
    com.google.protobuf.ByteString getSaveStatusBytes();

    /**
     * <code>string saveMessage = 16;</code>
     *
     * @return The saveMessage.
     */
    java.lang.String getSaveMessage();
    /**
     * <code>string saveMessage = 16;</code>
     *
     * @return The bytes for saveMessage.
     */
    com.google.protobuf.ByteString getSaveMessageBytes();

    /**
     * <code>bool damageCal = 17;</code>
     *
     * @return The damageCal.
     */
    boolean getDamageCal();

    /**
     * <code>bool dataSave = 18;</code>
     *
     * @return The dataSave.
     */
    boolean getDataSave();
  }
  /** Protobuf type {@code StowagePlanDetails} */
  public static final class StowagePlanDetails extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:StowagePlanDetails)
      StowagePlanDetailsOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use StowagePlanDetails.newBuilder() to construct.
    private StowagePlanDetails(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private StowagePlanDetails() {
      imoNumber_ = "";
      vesselCode_ = "";
      portCode_ = "";
      saveStatus_ = "";
      saveMessage_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new StowagePlanDetails();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private StowagePlanDetails(
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
                vesselId_ = input.readInt64();
                break;
              }
            case 26:
              {
                java.lang.String s = input.readStringRequireUtf8();

                imoNumber_ = s;
                break;
              }
            case 32:
              {
                companyId_ = input.readInt64();
                break;
              }
            case 40:
              {
                shipType_ = input.readInt64();
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                vesselCode_ = s;
                break;
              }
            case 56:
              {
                bookingListId_ = input.readInt64();
                break;
              }
            case 64:
              {
                stowageId_ = input.readInt64();
                break;
              }
            case 72:
              {
                portId_ = input.readInt64();
                break;
              }
            case 82:
              {
                java.lang.String s = input.readStringRequireUtf8();

                portCode_ = s;
                break;
              }
            case 88:
              {
                status_ = input.readInt64();
                break;
              }
            case 96:
              {
                deadweightConstant_ = input.readInt64();
                break;
              }
            case 104:
              {
                provisionalConstant_ = input.readInt64();
                break;
              }
            case 112:
              {
                calCount_ = input.readInt64();
                break;
              }
            case 122:
              {
                java.lang.String s = input.readStringRequireUtf8();

                saveStatus_ = s;
                break;
              }
            case 130:
              {
                java.lang.String s = input.readStringRequireUtf8();

                saveMessage_ = s;
                break;
              }
            case 136:
              {
                damageCal_ = input.readBool();
                break;
              }
            case 144:
              {
                dataSave_ = input.readBool();
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
      return com.cpdss.common.generated.Loadicator.internal_static_StowagePlanDetails_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.Loadicator
          .internal_static_StowagePlanDetails_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.Loadicator.StowagePlanDetails.class,
              com.cpdss.common.generated.Loadicator.StowagePlanDetails.Builder.class);
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

    public static final int IMONUMBER_FIELD_NUMBER = 3;
    private volatile java.lang.Object imoNumber_;
    /**
     * <code>string imoNumber = 3;</code>
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
     * <code>string imoNumber = 3;</code>
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

    public static final int COMPANYID_FIELD_NUMBER = 4;
    private long companyId_;
    /**
     * <code>int64 companyId = 4;</code>
     *
     * @return The companyId.
     */
    public long getCompanyId() {
      return companyId_;
    }

    public static final int SHIPTYPE_FIELD_NUMBER = 5;
    private long shipType_;
    /**
     * <code>int64 shipType = 5;</code>
     *
     * @return The shipType.
     */
    public long getShipType() {
      return shipType_;
    }

    public static final int VESSELCODE_FIELD_NUMBER = 6;
    private volatile java.lang.Object vesselCode_;
    /**
     * <code>string vesselCode = 6;</code>
     *
     * @return The vesselCode.
     */
    public java.lang.String getVesselCode() {
      java.lang.Object ref = vesselCode_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        vesselCode_ = s;
        return s;
      }
    }
    /**
     * <code>string vesselCode = 6;</code>
     *
     * @return The bytes for vesselCode.
     */
    public com.google.protobuf.ByteString getVesselCodeBytes() {
      java.lang.Object ref = vesselCode_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        vesselCode_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int BOOKINGLISTID_FIELD_NUMBER = 7;
    private long bookingListId_;
    /**
     * <code>int64 bookingListId = 7;</code>
     *
     * @return The bookingListId.
     */
    public long getBookingListId() {
      return bookingListId_;
    }

    public static final int STOWAGEID_FIELD_NUMBER = 8;
    private long stowageId_;
    /**
     * <code>int64 stowageId = 8;</code>
     *
     * @return The stowageId.
     */
    public long getStowageId() {
      return stowageId_;
    }

    public static final int PORTID_FIELD_NUMBER = 9;
    private long portId_;
    /**
     * <code>int64 portId = 9;</code>
     *
     * @return The portId.
     */
    public long getPortId() {
      return portId_;
    }

    public static final int PORTCODE_FIELD_NUMBER = 10;
    private volatile java.lang.Object portCode_;
    /**
     * <code>string portCode = 10;</code>
     *
     * @return The portCode.
     */
    public java.lang.String getPortCode() {
      java.lang.Object ref = portCode_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        portCode_ = s;
        return s;
      }
    }
    /**
     * <code>string portCode = 10;</code>
     *
     * @return The bytes for portCode.
     */
    public com.google.protobuf.ByteString getPortCodeBytes() {
      java.lang.Object ref = portCode_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        portCode_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int STATUS_FIELD_NUMBER = 11;
    private long status_;
    /**
     * <code>int64 status = 11;</code>
     *
     * @return The status.
     */
    public long getStatus() {
      return status_;
    }

    public static final int DEADWEIGHTCONSTANT_FIELD_NUMBER = 12;
    private long deadweightConstant_;
    /**
     * <code>int64 deadweightConstant = 12;</code>
     *
     * @return The deadweightConstant.
     */
    public long getDeadweightConstant() {
      return deadweightConstant_;
    }

    public static final int PROVISIONALCONSTANT_FIELD_NUMBER = 13;
    private long provisionalConstant_;
    /**
     * <code>int64 provisionalConstant = 13;</code>
     *
     * @return The provisionalConstant.
     */
    public long getProvisionalConstant() {
      return provisionalConstant_;
    }

    public static final int CALCOUNT_FIELD_NUMBER = 14;
    private long calCount_;
    /**
     * <code>int64 calCount = 14;</code>
     *
     * @return The calCount.
     */
    public long getCalCount() {
      return calCount_;
    }

    public static final int SAVESTATUS_FIELD_NUMBER = 15;
    private volatile java.lang.Object saveStatus_;
    /**
     * <code>string saveStatus = 15;</code>
     *
     * @return The saveStatus.
     */
    public java.lang.String getSaveStatus() {
      java.lang.Object ref = saveStatus_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        saveStatus_ = s;
        return s;
      }
    }
    /**
     * <code>string saveStatus = 15;</code>
     *
     * @return The bytes for saveStatus.
     */
    public com.google.protobuf.ByteString getSaveStatusBytes() {
      java.lang.Object ref = saveStatus_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        saveStatus_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SAVEMESSAGE_FIELD_NUMBER = 16;
    private volatile java.lang.Object saveMessage_;
    /**
     * <code>string saveMessage = 16;</code>
     *
     * @return The saveMessage.
     */
    public java.lang.String getSaveMessage() {
      java.lang.Object ref = saveMessage_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        saveMessage_ = s;
        return s;
      }
    }
    /**
     * <code>string saveMessage = 16;</code>
     *
     * @return The bytes for saveMessage.
     */
    public com.google.protobuf.ByteString getSaveMessageBytes() {
      java.lang.Object ref = saveMessage_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        saveMessage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DAMAGECAL_FIELD_NUMBER = 17;
    private boolean damageCal_;
    /**
     * <code>bool damageCal = 17;</code>
     *
     * @return The damageCal.
     */
    public boolean getDamageCal() {
      return damageCal_;
    }

    public static final int DATASAVE_FIELD_NUMBER = 18;
    private boolean dataSave_;
    /**
     * <code>bool dataSave = 18;</code>
     *
     * @return The dataSave.
     */
    public boolean getDataSave() {
      return dataSave_;
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
      if (vesselId_ != 0L) {
        output.writeInt64(2, vesselId_);
      }
      if (!getImoNumberBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, imoNumber_);
      }
      if (companyId_ != 0L) {
        output.writeInt64(4, companyId_);
      }
      if (shipType_ != 0L) {
        output.writeInt64(5, shipType_);
      }
      if (!getVesselCodeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, vesselCode_);
      }
      if (bookingListId_ != 0L) {
        output.writeInt64(7, bookingListId_);
      }
      if (stowageId_ != 0L) {
        output.writeInt64(8, stowageId_);
      }
      if (portId_ != 0L) {
        output.writeInt64(9, portId_);
      }
      if (!getPortCodeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 10, portCode_);
      }
      if (status_ != 0L) {
        output.writeInt64(11, status_);
      }
      if (deadweightConstant_ != 0L) {
        output.writeInt64(12, deadweightConstant_);
      }
      if (provisionalConstant_ != 0L) {
        output.writeInt64(13, provisionalConstant_);
      }
      if (calCount_ != 0L) {
        output.writeInt64(14, calCount_);
      }
      if (!getSaveStatusBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 15, saveStatus_);
      }
      if (!getSaveMessageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 16, saveMessage_);
      }
      if (damageCal_ != false) {
        output.writeBool(17, damageCal_);
      }
      if (dataSave_ != false) {
        output.writeBool(18, dataSave_);
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
      if (vesselId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, vesselId_);
      }
      if (!getImoNumberBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, imoNumber_);
      }
      if (companyId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, companyId_);
      }
      if (shipType_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(5, shipType_);
      }
      if (!getVesselCodeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, vesselCode_);
      }
      if (bookingListId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(7, bookingListId_);
      }
      if (stowageId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(8, stowageId_);
      }
      if (portId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(9, portId_);
      }
      if (!getPortCodeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(10, portCode_);
      }
      if (status_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(11, status_);
      }
      if (deadweightConstant_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(12, deadweightConstant_);
      }
      if (provisionalConstant_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(13, provisionalConstant_);
      }
      if (calCount_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(14, calCount_);
      }
      if (!getSaveStatusBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(15, saveStatus_);
      }
      if (!getSaveMessageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(16, saveMessage_);
      }
      if (damageCal_ != false) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(17, damageCal_);
      }
      if (dataSave_ != false) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(18, dataSave_);
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
      if (!(obj instanceof com.cpdss.common.generated.Loadicator.StowagePlanDetails)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.Loadicator.StowagePlanDetails other =
          (com.cpdss.common.generated.Loadicator.StowagePlanDetails) obj;

      if (getId() != other.getId()) return false;
      if (getVesselId() != other.getVesselId()) return false;
      if (!getImoNumber().equals(other.getImoNumber())) return false;
      if (getCompanyId() != other.getCompanyId()) return false;
      if (getShipType() != other.getShipType()) return false;
      if (!getVesselCode().equals(other.getVesselCode())) return false;
      if (getBookingListId() != other.getBookingListId()) return false;
      if (getStowageId() != other.getStowageId()) return false;
      if (getPortId() != other.getPortId()) return false;
      if (!getPortCode().equals(other.getPortCode())) return false;
      if (getStatus() != other.getStatus()) return false;
      if (getDeadweightConstant() != other.getDeadweightConstant()) return false;
      if (getProvisionalConstant() != other.getProvisionalConstant()) return false;
      if (getCalCount() != other.getCalCount()) return false;
      if (!getSaveStatus().equals(other.getSaveStatus())) return false;
      if (!getSaveMessage().equals(other.getSaveMessage())) return false;
      if (getDamageCal() != other.getDamageCal()) return false;
      if (getDataSave() != other.getDataSave()) return false;
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
      hash = (37 * hash) + VESSELID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVesselId());
      hash = (37 * hash) + IMONUMBER_FIELD_NUMBER;
      hash = (53 * hash) + getImoNumber().hashCode();
      hash = (37 * hash) + COMPANYID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCompanyId());
      hash = (37 * hash) + SHIPTYPE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getShipType());
      hash = (37 * hash) + VESSELCODE_FIELD_NUMBER;
      hash = (53 * hash) + getVesselCode().hashCode();
      hash = (37 * hash) + BOOKINGLISTID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getBookingListId());
      hash = (37 * hash) + STOWAGEID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getStowageId());
      hash = (37 * hash) + PORTID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortId());
      hash = (37 * hash) + PORTCODE_FIELD_NUMBER;
      hash = (53 * hash) + getPortCode().hashCode();
      hash = (37 * hash) + STATUS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getStatus());
      hash = (37 * hash) + DEADWEIGHTCONSTANT_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDeadweightConstant());
      hash = (37 * hash) + PROVISIONALCONSTANT_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getProvisionalConstant());
      hash = (37 * hash) + CALCOUNT_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCalCount());
      hash = (37 * hash) + SAVESTATUS_FIELD_NUMBER;
      hash = (53 * hash) + getSaveStatus().hashCode();
      hash = (37 * hash) + SAVEMESSAGE_FIELD_NUMBER;
      hash = (53 * hash) + getSaveMessage().hashCode();
      hash = (37 * hash) + DAMAGECAL_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getDamageCal());
      hash = (37 * hash) + DATASAVE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getDataSave());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails parseFrom(
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
        com.cpdss.common.generated.Loadicator.StowagePlanDetails prototype) {
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
    /** Protobuf type {@code StowagePlanDetails} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:StowagePlanDetails)
        com.cpdss.common.generated.Loadicator.StowagePlanDetailsOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.Loadicator.internal_static_StowagePlanDetails_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.Loadicator
            .internal_static_StowagePlanDetails_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.Loadicator.StowagePlanDetails.class,
                com.cpdss.common.generated.Loadicator.StowagePlanDetails.Builder.class);
      }

      // Construct using com.cpdss.common.generated.Loadicator.StowagePlanDetails.newBuilder()
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

        vesselId_ = 0L;

        imoNumber_ = "";

        companyId_ = 0L;

        shipType_ = 0L;

        vesselCode_ = "";

        bookingListId_ = 0L;

        stowageId_ = 0L;

        portId_ = 0L;

        portCode_ = "";

        status_ = 0L;

        deadweightConstant_ = 0L;

        provisionalConstant_ = 0L;

        calCount_ = 0L;

        saveStatus_ = "";

        saveMessage_ = "";

        damageCal_ = false;

        dataSave_ = false;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.Loadicator.internal_static_StowagePlanDetails_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.StowagePlanDetails getDefaultInstanceForType() {
        return com.cpdss.common.generated.Loadicator.StowagePlanDetails.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.StowagePlanDetails build() {
        com.cpdss.common.generated.Loadicator.StowagePlanDetails result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.StowagePlanDetails buildPartial() {
        com.cpdss.common.generated.Loadicator.StowagePlanDetails result =
            new com.cpdss.common.generated.Loadicator.StowagePlanDetails(this);
        result.id_ = id_;
        result.vesselId_ = vesselId_;
        result.imoNumber_ = imoNumber_;
        result.companyId_ = companyId_;
        result.shipType_ = shipType_;
        result.vesselCode_ = vesselCode_;
        result.bookingListId_ = bookingListId_;
        result.stowageId_ = stowageId_;
        result.portId_ = portId_;
        result.portCode_ = portCode_;
        result.status_ = status_;
        result.deadweightConstant_ = deadweightConstant_;
        result.provisionalConstant_ = provisionalConstant_;
        result.calCount_ = calCount_;
        result.saveStatus_ = saveStatus_;
        result.saveMessage_ = saveMessage_;
        result.damageCal_ = damageCal_;
        result.dataSave_ = dataSave_;
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
        if (other instanceof com.cpdss.common.generated.Loadicator.StowagePlanDetails) {
          return mergeFrom((com.cpdss.common.generated.Loadicator.StowagePlanDetails) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.Loadicator.StowagePlanDetails other) {
        if (other == com.cpdss.common.generated.Loadicator.StowagePlanDetails.getDefaultInstance())
          return this;
        if (other.getId() != 0L) {
          setId(other.getId());
        }
        if (other.getVesselId() != 0L) {
          setVesselId(other.getVesselId());
        }
        if (!other.getImoNumber().isEmpty()) {
          imoNumber_ = other.imoNumber_;
          onChanged();
        }
        if (other.getCompanyId() != 0L) {
          setCompanyId(other.getCompanyId());
        }
        if (other.getShipType() != 0L) {
          setShipType(other.getShipType());
        }
        if (!other.getVesselCode().isEmpty()) {
          vesselCode_ = other.vesselCode_;
          onChanged();
        }
        if (other.getBookingListId() != 0L) {
          setBookingListId(other.getBookingListId());
        }
        if (other.getStowageId() != 0L) {
          setStowageId(other.getStowageId());
        }
        if (other.getPortId() != 0L) {
          setPortId(other.getPortId());
        }
        if (!other.getPortCode().isEmpty()) {
          portCode_ = other.portCode_;
          onChanged();
        }
        if (other.getStatus() != 0L) {
          setStatus(other.getStatus());
        }
        if (other.getDeadweightConstant() != 0L) {
          setDeadweightConstant(other.getDeadweightConstant());
        }
        if (other.getProvisionalConstant() != 0L) {
          setProvisionalConstant(other.getProvisionalConstant());
        }
        if (other.getCalCount() != 0L) {
          setCalCount(other.getCalCount());
        }
        if (!other.getSaveStatus().isEmpty()) {
          saveStatus_ = other.saveStatus_;
          onChanged();
        }
        if (!other.getSaveMessage().isEmpty()) {
          saveMessage_ = other.saveMessage_;
          onChanged();
        }
        if (other.getDamageCal() != false) {
          setDamageCal(other.getDamageCal());
        }
        if (other.getDataSave() != false) {
          setDataSave(other.getDataSave());
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
        com.cpdss.common.generated.Loadicator.StowagePlanDetails parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.Loadicator.StowagePlanDetails) e.getUnfinishedMessage();
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

      private java.lang.Object imoNumber_ = "";
      /**
       * <code>string imoNumber = 3;</code>
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
       * <code>string imoNumber = 3;</code>
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
       * <code>string imoNumber = 3;</code>
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
       * <code>string imoNumber = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearImoNumber() {

        imoNumber_ = getDefaultInstance().getImoNumber();
        onChanged();
        return this;
      }
      /**
       * <code>string imoNumber = 3;</code>
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

      private long companyId_;
      /**
       * <code>int64 companyId = 4;</code>
       *
       * @return The companyId.
       */
      public long getCompanyId() {
        return companyId_;
      }
      /**
       * <code>int64 companyId = 4;</code>
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
       * <code>int64 companyId = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCompanyId() {

        companyId_ = 0L;
        onChanged();
        return this;
      }

      private long shipType_;
      /**
       * <code>int64 shipType = 5;</code>
       *
       * @return The shipType.
       */
      public long getShipType() {
        return shipType_;
      }
      /**
       * <code>int64 shipType = 5;</code>
       *
       * @param value The shipType to set.
       * @return This builder for chaining.
       */
      public Builder setShipType(long value) {

        shipType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 shipType = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearShipType() {

        shipType_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object vesselCode_ = "";
      /**
       * <code>string vesselCode = 6;</code>
       *
       * @return The vesselCode.
       */
      public java.lang.String getVesselCode() {
        java.lang.Object ref = vesselCode_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          vesselCode_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string vesselCode = 6;</code>
       *
       * @return The bytes for vesselCode.
       */
      public com.google.protobuf.ByteString getVesselCodeBytes() {
        java.lang.Object ref = vesselCode_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          vesselCode_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string vesselCode = 6;</code>
       *
       * @param value The vesselCode to set.
       * @return This builder for chaining.
       */
      public Builder setVesselCode(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        vesselCode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string vesselCode = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearVesselCode() {

        vesselCode_ = getDefaultInstance().getVesselCode();
        onChanged();
        return this;
      }
      /**
       * <code>string vesselCode = 6;</code>
       *
       * @param value The bytes for vesselCode to set.
       * @return This builder for chaining.
       */
      public Builder setVesselCodeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        vesselCode_ = value;
        onChanged();
        return this;
      }

      private long bookingListId_;
      /**
       * <code>int64 bookingListId = 7;</code>
       *
       * @return The bookingListId.
       */
      public long getBookingListId() {
        return bookingListId_;
      }
      /**
       * <code>int64 bookingListId = 7;</code>
       *
       * @param value The bookingListId to set.
       * @return This builder for chaining.
       */
      public Builder setBookingListId(long value) {

        bookingListId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 bookingListId = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearBookingListId() {

        bookingListId_ = 0L;
        onChanged();
        return this;
      }

      private long stowageId_;
      /**
       * <code>int64 stowageId = 8;</code>
       *
       * @return The stowageId.
       */
      public long getStowageId() {
        return stowageId_;
      }
      /**
       * <code>int64 stowageId = 8;</code>
       *
       * @param value The stowageId to set.
       * @return This builder for chaining.
       */
      public Builder setStowageId(long value) {

        stowageId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 stowageId = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearStowageId() {

        stowageId_ = 0L;
        onChanged();
        return this;
      }

      private long portId_;
      /**
       * <code>int64 portId = 9;</code>
       *
       * @return The portId.
       */
      public long getPortId() {
        return portId_;
      }
      /**
       * <code>int64 portId = 9;</code>
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
       * <code>int64 portId = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPortId() {

        portId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object portCode_ = "";
      /**
       * <code>string portCode = 10;</code>
       *
       * @return The portCode.
       */
      public java.lang.String getPortCode() {
        java.lang.Object ref = portCode_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          portCode_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string portCode = 10;</code>
       *
       * @return The bytes for portCode.
       */
      public com.google.protobuf.ByteString getPortCodeBytes() {
        java.lang.Object ref = portCode_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          portCode_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string portCode = 10;</code>
       *
       * @param value The portCode to set.
       * @return This builder for chaining.
       */
      public Builder setPortCode(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        portCode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string portCode = 10;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearPortCode() {

        portCode_ = getDefaultInstance().getPortCode();
        onChanged();
        return this;
      }
      /**
       * <code>string portCode = 10;</code>
       *
       * @param value The bytes for portCode to set.
       * @return This builder for chaining.
       */
      public Builder setPortCodeBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        portCode_ = value;
        onChanged();
        return this;
      }

      private long status_;
      /**
       * <code>int64 status = 11;</code>
       *
       * @return The status.
       */
      public long getStatus() {
        return status_;
      }
      /**
       * <code>int64 status = 11;</code>
       *
       * @param value The status to set.
       * @return This builder for chaining.
       */
      public Builder setStatus(long value) {

        status_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 status = 11;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearStatus() {

        status_ = 0L;
        onChanged();
        return this;
      }

      private long deadweightConstant_;
      /**
       * <code>int64 deadweightConstant = 12;</code>
       *
       * @return The deadweightConstant.
       */
      public long getDeadweightConstant() {
        return deadweightConstant_;
      }
      /**
       * <code>int64 deadweightConstant = 12;</code>
       *
       * @param value The deadweightConstant to set.
       * @return This builder for chaining.
       */
      public Builder setDeadweightConstant(long value) {

        deadweightConstant_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 deadweightConstant = 12;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDeadweightConstant() {

        deadweightConstant_ = 0L;
        onChanged();
        return this;
      }

      private long provisionalConstant_;
      /**
       * <code>int64 provisionalConstant = 13;</code>
       *
       * @return The provisionalConstant.
       */
      public long getProvisionalConstant() {
        return provisionalConstant_;
      }
      /**
       * <code>int64 provisionalConstant = 13;</code>
       *
       * @param value The provisionalConstant to set.
       * @return This builder for chaining.
       */
      public Builder setProvisionalConstant(long value) {

        provisionalConstant_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 provisionalConstant = 13;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearProvisionalConstant() {

        provisionalConstant_ = 0L;
        onChanged();
        return this;
      }

      private long calCount_;
      /**
       * <code>int64 calCount = 14;</code>
       *
       * @return The calCount.
       */
      public long getCalCount() {
        return calCount_;
      }
      /**
       * <code>int64 calCount = 14;</code>
       *
       * @param value The calCount to set.
       * @return This builder for chaining.
       */
      public Builder setCalCount(long value) {

        calCount_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 calCount = 14;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCalCount() {

        calCount_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object saveStatus_ = "";
      /**
       * <code>string saveStatus = 15;</code>
       *
       * @return The saveStatus.
       */
      public java.lang.String getSaveStatus() {
        java.lang.Object ref = saveStatus_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          saveStatus_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string saveStatus = 15;</code>
       *
       * @return The bytes for saveStatus.
       */
      public com.google.protobuf.ByteString getSaveStatusBytes() {
        java.lang.Object ref = saveStatus_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          saveStatus_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string saveStatus = 15;</code>
       *
       * @param value The saveStatus to set.
       * @return This builder for chaining.
       */
      public Builder setSaveStatus(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        saveStatus_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string saveStatus = 15;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSaveStatus() {

        saveStatus_ = getDefaultInstance().getSaveStatus();
        onChanged();
        return this;
      }
      /**
       * <code>string saveStatus = 15;</code>
       *
       * @param value The bytes for saveStatus to set.
       * @return This builder for chaining.
       */
      public Builder setSaveStatusBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        saveStatus_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object saveMessage_ = "";
      /**
       * <code>string saveMessage = 16;</code>
       *
       * @return The saveMessage.
       */
      public java.lang.String getSaveMessage() {
        java.lang.Object ref = saveMessage_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          saveMessage_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string saveMessage = 16;</code>
       *
       * @return The bytes for saveMessage.
       */
      public com.google.protobuf.ByteString getSaveMessageBytes() {
        java.lang.Object ref = saveMessage_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          saveMessage_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string saveMessage = 16;</code>
       *
       * @param value The saveMessage to set.
       * @return This builder for chaining.
       */
      public Builder setSaveMessage(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        saveMessage_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string saveMessage = 16;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSaveMessage() {

        saveMessage_ = getDefaultInstance().getSaveMessage();
        onChanged();
        return this;
      }
      /**
       * <code>string saveMessage = 16;</code>
       *
       * @param value The bytes for saveMessage to set.
       * @return This builder for chaining.
       */
      public Builder setSaveMessageBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        saveMessage_ = value;
        onChanged();
        return this;
      }

      private boolean damageCal_;
      /**
       * <code>bool damageCal = 17;</code>
       *
       * @return The damageCal.
       */
      public boolean getDamageCal() {
        return damageCal_;
      }
      /**
       * <code>bool damageCal = 17;</code>
       *
       * @param value The damageCal to set.
       * @return This builder for chaining.
       */
      public Builder setDamageCal(boolean value) {

        damageCal_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool damageCal = 17;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDamageCal() {

        damageCal_ = false;
        onChanged();
        return this;
      }

      private boolean dataSave_;
      /**
       * <code>bool dataSave = 18;</code>
       *
       * @return The dataSave.
       */
      public boolean getDataSave() {
        return dataSave_;
      }
      /**
       * <code>bool dataSave = 18;</code>
       *
       * @param value The dataSave to set.
       * @return This builder for chaining.
       */
      public Builder setDataSave(boolean value) {

        dataSave_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool dataSave = 18;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDataSave() {

        dataSave_ = false;
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

      // @@protoc_insertion_point(builder_scope:StowagePlanDetails)
    }

    // @@protoc_insertion_point(class_scope:StowagePlanDetails)
    private static final com.cpdss.common.generated.Loadicator.StowagePlanDetails DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.Loadicator.StowagePlanDetails();
    }

    public static com.cpdss.common.generated.Loadicator.StowagePlanDetails getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<StowagePlanDetails> PARSER =
        new com.google.protobuf.AbstractParser<StowagePlanDetails>() {
          @java.lang.Override
          public StowagePlanDetails parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new StowagePlanDetails(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<StowagePlanDetails> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<StowagePlanDetails> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.Loadicator.StowagePlanDetails getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface StowageDetailsInfoOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:StowageDetailsInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 stowagePlanId = 1;</code>
     *
     * @return The stowagePlanId.
     */
    long getStowagePlanId();

    /**
     * <code>int64 cargoId = 2;</code>
     *
     * @return The cargoId.
     */
    long getCargoId();

    /**
     * <code>int64 cargoBookId = 3;</code>
     *
     * @return The cargoBookId.
     */
    long getCargoBookId();

    /**
     * <code>string cargoName = 4;</code>
     *
     * @return The cargoName.
     */
    java.lang.String getCargoName();
    /**
     * <code>string cargoName = 4;</code>
     *
     * @return The bytes for cargoName.
     */
    com.google.protobuf.ByteString getCargoNameBytes();

    /**
     * <code>string specificGravity = 5;</code>
     *
     * @return The specificGravity.
     */
    java.lang.String getSpecificGravity();
    /**
     * <code>string specificGravity = 5;</code>
     *
     * @return The bytes for specificGravity.
     */
    com.google.protobuf.ByteString getSpecificGravityBytes();

    /**
     * <code>string quantity = 6;</code>
     *
     * @return The quantity.
     */
    java.lang.String getQuantity();
    /**
     * <code>string quantity = 6;</code>
     *
     * @return The bytes for quantity.
     */
    com.google.protobuf.ByteString getQuantityBytes();

    /**
     * <code>int64 tankId = 7;</code>
     *
     * @return The tankId.
     */
    long getTankId();

    /**
     * <code>string shortName = 8;</code>
     *
     * @return The shortName.
     */
    java.lang.String getShortName();
    /**
     * <code>string shortName = 8;</code>
     *
     * @return The bytes for shortName.
     */
    com.google.protobuf.ByteString getShortNameBytes();

    /**
     * <code>string tankName = 9;</code>
     *
     * @return The tankName.
     */
    java.lang.String getTankName();
    /**
     * <code>string tankName = 9;</code>
     *
     * @return The bytes for tankName.
     */
    com.google.protobuf.ByteString getTankNameBytes();
  }
  /** Protobuf type {@code StowageDetailsInfo} */
  public static final class StowageDetailsInfo extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:StowageDetailsInfo)
      StowageDetailsInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use StowageDetailsInfo.newBuilder() to construct.
    private StowageDetailsInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private StowageDetailsInfo() {
      cargoName_ = "";
      specificGravity_ = "";
      quantity_ = "";
      shortName_ = "";
      tankName_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new StowageDetailsInfo();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private StowageDetailsInfo(
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
                stowagePlanId_ = input.readInt64();
                break;
              }
            case 16:
              {
                cargoId_ = input.readInt64();
                break;
              }
            case 24:
              {
                cargoBookId_ = input.readInt64();
                break;
              }
            case 34:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargoName_ = s;
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                specificGravity_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                quantity_ = s;
                break;
              }
            case 56:
              {
                tankId_ = input.readInt64();
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                shortName_ = s;
                break;
              }
            case 74:
              {
                java.lang.String s = input.readStringRequireUtf8();

                tankName_ = s;
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
      return com.cpdss.common.generated.Loadicator.internal_static_StowageDetailsInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.Loadicator
          .internal_static_StowageDetailsInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.Loadicator.StowageDetailsInfo.class,
              com.cpdss.common.generated.Loadicator.StowageDetailsInfo.Builder.class);
    }

    public static final int STOWAGEPLANID_FIELD_NUMBER = 1;
    private long stowagePlanId_;
    /**
     * <code>int64 stowagePlanId = 1;</code>
     *
     * @return The stowagePlanId.
     */
    public long getStowagePlanId() {
      return stowagePlanId_;
    }

    public static final int CARGOID_FIELD_NUMBER = 2;
    private long cargoId_;
    /**
     * <code>int64 cargoId = 2;</code>
     *
     * @return The cargoId.
     */
    public long getCargoId() {
      return cargoId_;
    }

    public static final int CARGOBOOKID_FIELD_NUMBER = 3;
    private long cargoBookId_;
    /**
     * <code>int64 cargoBookId = 3;</code>
     *
     * @return The cargoBookId.
     */
    public long getCargoBookId() {
      return cargoBookId_;
    }

    public static final int CARGONAME_FIELD_NUMBER = 4;
    private volatile java.lang.Object cargoName_;
    /**
     * <code>string cargoName = 4;</code>
     *
     * @return The cargoName.
     */
    public java.lang.String getCargoName() {
      java.lang.Object ref = cargoName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargoName_ = s;
        return s;
      }
    }
    /**
     * <code>string cargoName = 4;</code>
     *
     * @return The bytes for cargoName.
     */
    public com.google.protobuf.ByteString getCargoNameBytes() {
      java.lang.Object ref = cargoName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargoName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SPECIFICGRAVITY_FIELD_NUMBER = 5;
    private volatile java.lang.Object specificGravity_;
    /**
     * <code>string specificGravity = 5;</code>
     *
     * @return The specificGravity.
     */
    public java.lang.String getSpecificGravity() {
      java.lang.Object ref = specificGravity_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        specificGravity_ = s;
        return s;
      }
    }
    /**
     * <code>string specificGravity = 5;</code>
     *
     * @return The bytes for specificGravity.
     */
    public com.google.protobuf.ByteString getSpecificGravityBytes() {
      java.lang.Object ref = specificGravity_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        specificGravity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int QUANTITY_FIELD_NUMBER = 6;
    private volatile java.lang.Object quantity_;
    /**
     * <code>string quantity = 6;</code>
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
     * <code>string quantity = 6;</code>
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

    public static final int TANKID_FIELD_NUMBER = 7;
    private long tankId_;
    /**
     * <code>int64 tankId = 7;</code>
     *
     * @return The tankId.
     */
    public long getTankId() {
      return tankId_;
    }

    public static final int SHORTNAME_FIELD_NUMBER = 8;
    private volatile java.lang.Object shortName_;
    /**
     * <code>string shortName = 8;</code>
     *
     * @return The shortName.
     */
    public java.lang.String getShortName() {
      java.lang.Object ref = shortName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        shortName_ = s;
        return s;
      }
    }
    /**
     * <code>string shortName = 8;</code>
     *
     * @return The bytes for shortName.
     */
    public com.google.protobuf.ByteString getShortNameBytes() {
      java.lang.Object ref = shortName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        shortName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TANKNAME_FIELD_NUMBER = 9;
    private volatile java.lang.Object tankName_;
    /**
     * <code>string tankName = 9;</code>
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
     * <code>string tankName = 9;</code>
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
      if (stowagePlanId_ != 0L) {
        output.writeInt64(1, stowagePlanId_);
      }
      if (cargoId_ != 0L) {
        output.writeInt64(2, cargoId_);
      }
      if (cargoBookId_ != 0L) {
        output.writeInt64(3, cargoBookId_);
      }
      if (!getCargoNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, cargoName_);
      }
      if (!getSpecificGravityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, specificGravity_);
      }
      if (!getQuantityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, quantity_);
      }
      if (tankId_ != 0L) {
        output.writeInt64(7, tankId_);
      }
      if (!getShortNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, shortName_);
      }
      if (!getTankNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 9, tankName_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (stowagePlanId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, stowagePlanId_);
      }
      if (cargoId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, cargoId_);
      }
      if (cargoBookId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, cargoBookId_);
      }
      if (!getCargoNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, cargoName_);
      }
      if (!getSpecificGravityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, specificGravity_);
      }
      if (!getQuantityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, quantity_);
      }
      if (tankId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(7, tankId_);
      }
      if (!getShortNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, shortName_);
      }
      if (!getTankNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, tankName_);
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
      if (!(obj instanceof com.cpdss.common.generated.Loadicator.StowageDetailsInfo)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.Loadicator.StowageDetailsInfo other =
          (com.cpdss.common.generated.Loadicator.StowageDetailsInfo) obj;

      if (getStowagePlanId() != other.getStowagePlanId()) return false;
      if (getCargoId() != other.getCargoId()) return false;
      if (getCargoBookId() != other.getCargoBookId()) return false;
      if (!getCargoName().equals(other.getCargoName())) return false;
      if (!getSpecificGravity().equals(other.getSpecificGravity())) return false;
      if (!getQuantity().equals(other.getQuantity())) return false;
      if (getTankId() != other.getTankId()) return false;
      if (!getShortName().equals(other.getShortName())) return false;
      if (!getTankName().equals(other.getTankName())) return false;
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
      hash = (37 * hash) + STOWAGEPLANID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getStowagePlanId());
      hash = (37 * hash) + CARGOID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoId());
      hash = (37 * hash) + CARGOBOOKID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoBookId());
      hash = (37 * hash) + CARGONAME_FIELD_NUMBER;
      hash = (53 * hash) + getCargoName().hashCode();
      hash = (37 * hash) + SPECIFICGRAVITY_FIELD_NUMBER;
      hash = (53 * hash) + getSpecificGravity().hashCode();
      hash = (37 * hash) + QUANTITY_FIELD_NUMBER;
      hash = (53 * hash) + getQuantity().hashCode();
      hash = (37 * hash) + TANKID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getTankId());
      hash = (37 * hash) + SHORTNAME_FIELD_NUMBER;
      hash = (53 * hash) + getShortName().hashCode();
      hash = (37 * hash) + TANKNAME_FIELD_NUMBER;
      hash = (53 * hash) + getTankName().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo parseFrom(
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
        com.cpdss.common.generated.Loadicator.StowageDetailsInfo prototype) {
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
    /** Protobuf type {@code StowageDetailsInfo} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:StowageDetailsInfo)
        com.cpdss.common.generated.Loadicator.StowageDetailsInfoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.Loadicator.internal_static_StowageDetailsInfo_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.Loadicator
            .internal_static_StowageDetailsInfo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.Loadicator.StowageDetailsInfo.class,
                com.cpdss.common.generated.Loadicator.StowageDetailsInfo.Builder.class);
      }

      // Construct using com.cpdss.common.generated.Loadicator.StowageDetailsInfo.newBuilder()
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
        stowagePlanId_ = 0L;

        cargoId_ = 0L;

        cargoBookId_ = 0L;

        cargoName_ = "";

        specificGravity_ = "";

        quantity_ = "";

        tankId_ = 0L;

        shortName_ = "";

        tankName_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.Loadicator.internal_static_StowageDetailsInfo_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.StowageDetailsInfo getDefaultInstanceForType() {
        return com.cpdss.common.generated.Loadicator.StowageDetailsInfo.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.StowageDetailsInfo build() {
        com.cpdss.common.generated.Loadicator.StowageDetailsInfo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.StowageDetailsInfo buildPartial() {
        com.cpdss.common.generated.Loadicator.StowageDetailsInfo result =
            new com.cpdss.common.generated.Loadicator.StowageDetailsInfo(this);
        result.stowagePlanId_ = stowagePlanId_;
        result.cargoId_ = cargoId_;
        result.cargoBookId_ = cargoBookId_;
        result.cargoName_ = cargoName_;
        result.specificGravity_ = specificGravity_;
        result.quantity_ = quantity_;
        result.tankId_ = tankId_;
        result.shortName_ = shortName_;
        result.tankName_ = tankName_;
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
        if (other instanceof com.cpdss.common.generated.Loadicator.StowageDetailsInfo) {
          return mergeFrom((com.cpdss.common.generated.Loadicator.StowageDetailsInfo) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.Loadicator.StowageDetailsInfo other) {
        if (other == com.cpdss.common.generated.Loadicator.StowageDetailsInfo.getDefaultInstance())
          return this;
        if (other.getStowagePlanId() != 0L) {
          setStowagePlanId(other.getStowagePlanId());
        }
        if (other.getCargoId() != 0L) {
          setCargoId(other.getCargoId());
        }
        if (other.getCargoBookId() != 0L) {
          setCargoBookId(other.getCargoBookId());
        }
        if (!other.getCargoName().isEmpty()) {
          cargoName_ = other.cargoName_;
          onChanged();
        }
        if (!other.getSpecificGravity().isEmpty()) {
          specificGravity_ = other.specificGravity_;
          onChanged();
        }
        if (!other.getQuantity().isEmpty()) {
          quantity_ = other.quantity_;
          onChanged();
        }
        if (other.getTankId() != 0L) {
          setTankId(other.getTankId());
        }
        if (!other.getShortName().isEmpty()) {
          shortName_ = other.shortName_;
          onChanged();
        }
        if (!other.getTankName().isEmpty()) {
          tankName_ = other.tankName_;
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
        com.cpdss.common.generated.Loadicator.StowageDetailsInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.Loadicator.StowageDetailsInfo) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long stowagePlanId_;
      /**
       * <code>int64 stowagePlanId = 1;</code>
       *
       * @return The stowagePlanId.
       */
      public long getStowagePlanId() {
        return stowagePlanId_;
      }
      /**
       * <code>int64 stowagePlanId = 1;</code>
       *
       * @param value The stowagePlanId to set.
       * @return This builder for chaining.
       */
      public Builder setStowagePlanId(long value) {

        stowagePlanId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 stowagePlanId = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearStowagePlanId() {

        stowagePlanId_ = 0L;
        onChanged();
        return this;
      }

      private long cargoId_;
      /**
       * <code>int64 cargoId = 2;</code>
       *
       * @return The cargoId.
       */
      public long getCargoId() {
        return cargoId_;
      }
      /**
       * <code>int64 cargoId = 2;</code>
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
       * <code>int64 cargoId = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoId() {

        cargoId_ = 0L;
        onChanged();
        return this;
      }

      private long cargoBookId_;
      /**
       * <code>int64 cargoBookId = 3;</code>
       *
       * @return The cargoBookId.
       */
      public long getCargoBookId() {
        return cargoBookId_;
      }
      /**
       * <code>int64 cargoBookId = 3;</code>
       *
       * @param value The cargoBookId to set.
       * @return This builder for chaining.
       */
      public Builder setCargoBookId(long value) {

        cargoBookId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 cargoBookId = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoBookId() {

        cargoBookId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object cargoName_ = "";
      /**
       * <code>string cargoName = 4;</code>
       *
       * @return The cargoName.
       */
      public java.lang.String getCargoName() {
        java.lang.Object ref = cargoName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargoName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargoName = 4;</code>
       *
       * @return The bytes for cargoName.
       */
      public com.google.protobuf.ByteString getCargoNameBytes() {
        java.lang.Object ref = cargoName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargoName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargoName = 4;</code>
       *
       * @param value The cargoName to set.
       * @return This builder for chaining.
       */
      public Builder setCargoName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargoName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargoName = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoName() {

        cargoName_ = getDefaultInstance().getCargoName();
        onChanged();
        return this;
      }
      /**
       * <code>string cargoName = 4;</code>
       *
       * @param value The bytes for cargoName to set.
       * @return This builder for chaining.
       */
      public Builder setCargoNameBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargoName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object specificGravity_ = "";
      /**
       * <code>string specificGravity = 5;</code>
       *
       * @return The specificGravity.
       */
      public java.lang.String getSpecificGravity() {
        java.lang.Object ref = specificGravity_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          specificGravity_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string specificGravity = 5;</code>
       *
       * @return The bytes for specificGravity.
       */
      public com.google.protobuf.ByteString getSpecificGravityBytes() {
        java.lang.Object ref = specificGravity_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          specificGravity_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string specificGravity = 5;</code>
       *
       * @param value The specificGravity to set.
       * @return This builder for chaining.
       */
      public Builder setSpecificGravity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        specificGravity_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string specificGravity = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearSpecificGravity() {

        specificGravity_ = getDefaultInstance().getSpecificGravity();
        onChanged();
        return this;
      }
      /**
       * <code>string specificGravity = 5;</code>
       *
       * @param value The bytes for specificGravity to set.
       * @return This builder for chaining.
       */
      public Builder setSpecificGravityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        specificGravity_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object quantity_ = "";
      /**
       * <code>string quantity = 6;</code>
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
       * <code>string quantity = 6;</code>
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
       * <code>string quantity = 6;</code>
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
       * <code>string quantity = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearQuantity() {

        quantity_ = getDefaultInstance().getQuantity();
        onChanged();
        return this;
      }
      /**
       * <code>string quantity = 6;</code>
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

      private long tankId_;
      /**
       * <code>int64 tankId = 7;</code>
       *
       * @return The tankId.
       */
      public long getTankId() {
        return tankId_;
      }
      /**
       * <code>int64 tankId = 7;</code>
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
       * <code>int64 tankId = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTankId() {

        tankId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object shortName_ = "";
      /**
       * <code>string shortName = 8;</code>
       *
       * @return The shortName.
       */
      public java.lang.String getShortName() {
        java.lang.Object ref = shortName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          shortName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string shortName = 8;</code>
       *
       * @return The bytes for shortName.
       */
      public com.google.protobuf.ByteString getShortNameBytes() {
        java.lang.Object ref = shortName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          shortName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string shortName = 8;</code>
       *
       * @param value The shortName to set.
       * @return This builder for chaining.
       */
      public Builder setShortName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        shortName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string shortName = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearShortName() {

        shortName_ = getDefaultInstance().getShortName();
        onChanged();
        return this;
      }
      /**
       * <code>string shortName = 8;</code>
       *
       * @param value The bytes for shortName to set.
       * @return This builder for chaining.
       */
      public Builder setShortNameBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        shortName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object tankName_ = "";
      /**
       * <code>string tankName = 9;</code>
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
       * <code>string tankName = 9;</code>
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
       * <code>string tankName = 9;</code>
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
       * <code>string tankName = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTankName() {

        tankName_ = getDefaultInstance().getTankName();
        onChanged();
        return this;
      }
      /**
       * <code>string tankName = 9;</code>
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

      // @@protoc_insertion_point(builder_scope:StowageDetailsInfo)
    }

    // @@protoc_insertion_point(class_scope:StowageDetailsInfo)
    private static final com.cpdss.common.generated.Loadicator.StowageDetailsInfo DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.Loadicator.StowageDetailsInfo();
    }

    public static com.cpdss.common.generated.Loadicator.StowageDetailsInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<StowageDetailsInfo> PARSER =
        new com.google.protobuf.AbstractParser<StowageDetailsInfo>() {
          @java.lang.Override
          public StowageDetailsInfo parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new StowageDetailsInfo(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<StowageDetailsInfo> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<StowageDetailsInfo> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.Loadicator.StowageDetailsInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface CargoInfoOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:CargoInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 stowagePlanId = 1;</code>
     *
     * @return The stowagePlanId.
     */
    long getStowagePlanId();

    /**
     * <code>int64 cargoId = 2;</code>
     *
     * @return The cargoId.
     */
    long getCargoId();

    /**
     * <code>string cargoName = 3;</code>
     *
     * @return The cargoName.
     */
    java.lang.String getCargoName();
    /**
     * <code>string cargoName = 3;</code>
     *
     * @return The bytes for cargoName.
     */
    com.google.protobuf.ByteString getCargoNameBytes();

    /**
     * <code>string cargoAbbrev = 4;</code>
     *
     * @return The cargoAbbrev.
     */
    java.lang.String getCargoAbbrev();
    /**
     * <code>string cargoAbbrev = 4;</code>
     *
     * @return The bytes for cargoAbbrev.
     */
    com.google.protobuf.ByteString getCargoAbbrevBytes();

    /**
     * <code>string standardTemp = 5;</code>
     *
     * @return The standardTemp.
     */
    java.lang.String getStandardTemp();
    /**
     * <code>string standardTemp = 5;</code>
     *
     * @return The bytes for standardTemp.
     */
    com.google.protobuf.ByteString getStandardTempBytes();

    /**
     * <code>string grade = 6;</code>
     *
     * @return The grade.
     */
    java.lang.String getGrade();
    /**
     * <code>string grade = 6;</code>
     *
     * @return The bytes for grade.
     */
    com.google.protobuf.ByteString getGradeBytes();

    /**
     * <code>string density = 7;</code>
     *
     * @return The density.
     */
    java.lang.String getDensity();
    /**
     * <code>string density = 7;</code>
     *
     * @return The bytes for density.
     */
    com.google.protobuf.ByteString getDensityBytes();

    /**
     * <code>string api = 8;</code>
     *
     * @return The api.
     */
    java.lang.String getApi();
    /**
     * <code>string api = 8;</code>
     *
     * @return The bytes for api.
     */
    com.google.protobuf.ByteString getApiBytes();

    /**
     * <code>string degf = 9;</code>
     *
     * @return The degf.
     */
    java.lang.String getDegf();
    /**
     * <code>string degf = 9;</code>
     *
     * @return The bytes for degf.
     */
    com.google.protobuf.ByteString getDegfBytes();

    /**
     * <code>string degc = 10;</code>
     *
     * @return The degc.
     */
    java.lang.String getDegc();
    /**
     * <code>string degc = 10;</code>
     *
     * @return The bytes for degc.
     */
    com.google.protobuf.ByteString getDegcBytes();
  }
  /** Protobuf type {@code CargoInfo} */
  public static final class CargoInfo extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:CargoInfo)
      CargoInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use CargoInfo.newBuilder() to construct.
    private CargoInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private CargoInfo() {
      cargoName_ = "";
      cargoAbbrev_ = "";
      standardTemp_ = "";
      grade_ = "";
      density_ = "";
      api_ = "";
      degf_ = "";
      degc_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new CargoInfo();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private CargoInfo(
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
                stowagePlanId_ = input.readInt64();
                break;
              }
            case 16:
              {
                cargoId_ = input.readInt64();
                break;
              }
            case 26:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargoName_ = s;
                break;
              }
            case 34:
              {
                java.lang.String s = input.readStringRequireUtf8();

                cargoAbbrev_ = s;
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                standardTemp_ = s;
                break;
              }
            case 50:
              {
                java.lang.String s = input.readStringRequireUtf8();

                grade_ = s;
                break;
              }
            case 58:
              {
                java.lang.String s = input.readStringRequireUtf8();

                density_ = s;
                break;
              }
            case 66:
              {
                java.lang.String s = input.readStringRequireUtf8();

                api_ = s;
                break;
              }
            case 74:
              {
                java.lang.String s = input.readStringRequireUtf8();

                degf_ = s;
                break;
              }
            case 82:
              {
                java.lang.String s = input.readStringRequireUtf8();

                degc_ = s;
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
      return com.cpdss.common.generated.Loadicator.internal_static_CargoInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.Loadicator.internal_static_CargoInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.Loadicator.CargoInfo.class,
              com.cpdss.common.generated.Loadicator.CargoInfo.Builder.class);
    }

    public static final int STOWAGEPLANID_FIELD_NUMBER = 1;
    private long stowagePlanId_;
    /**
     * <code>int64 stowagePlanId = 1;</code>
     *
     * @return The stowagePlanId.
     */
    public long getStowagePlanId() {
      return stowagePlanId_;
    }

    public static final int CARGOID_FIELD_NUMBER = 2;
    private long cargoId_;
    /**
     * <code>int64 cargoId = 2;</code>
     *
     * @return The cargoId.
     */
    public long getCargoId() {
      return cargoId_;
    }

    public static final int CARGONAME_FIELD_NUMBER = 3;
    private volatile java.lang.Object cargoName_;
    /**
     * <code>string cargoName = 3;</code>
     *
     * @return The cargoName.
     */
    public java.lang.String getCargoName() {
      java.lang.Object ref = cargoName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargoName_ = s;
        return s;
      }
    }
    /**
     * <code>string cargoName = 3;</code>
     *
     * @return The bytes for cargoName.
     */
    public com.google.protobuf.ByteString getCargoNameBytes() {
      java.lang.Object ref = cargoName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargoName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int CARGOABBREV_FIELD_NUMBER = 4;
    private volatile java.lang.Object cargoAbbrev_;
    /**
     * <code>string cargoAbbrev = 4;</code>
     *
     * @return The cargoAbbrev.
     */
    public java.lang.String getCargoAbbrev() {
      java.lang.Object ref = cargoAbbrev_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargoAbbrev_ = s;
        return s;
      }
    }
    /**
     * <code>string cargoAbbrev = 4;</code>
     *
     * @return The bytes for cargoAbbrev.
     */
    public com.google.protobuf.ByteString getCargoAbbrevBytes() {
      java.lang.Object ref = cargoAbbrev_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargoAbbrev_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int STANDARDTEMP_FIELD_NUMBER = 5;
    private volatile java.lang.Object standardTemp_;
    /**
     * <code>string standardTemp = 5;</code>
     *
     * @return The standardTemp.
     */
    public java.lang.String getStandardTemp() {
      java.lang.Object ref = standardTemp_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        standardTemp_ = s;
        return s;
      }
    }
    /**
     * <code>string standardTemp = 5;</code>
     *
     * @return The bytes for standardTemp.
     */
    public com.google.protobuf.ByteString getStandardTempBytes() {
      java.lang.Object ref = standardTemp_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        standardTemp_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int GRADE_FIELD_NUMBER = 6;
    private volatile java.lang.Object grade_;
    /**
     * <code>string grade = 6;</code>
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
     * <code>string grade = 6;</code>
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

    public static final int DENSITY_FIELD_NUMBER = 7;
    private volatile java.lang.Object density_;
    /**
     * <code>string density = 7;</code>
     *
     * @return The density.
     */
    public java.lang.String getDensity() {
      java.lang.Object ref = density_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        density_ = s;
        return s;
      }
    }
    /**
     * <code>string density = 7;</code>
     *
     * @return The bytes for density.
     */
    public com.google.protobuf.ByteString getDensityBytes() {
      java.lang.Object ref = density_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        density_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int API_FIELD_NUMBER = 8;
    private volatile java.lang.Object api_;
    /**
     * <code>string api = 8;</code>
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
     * <code>string api = 8;</code>
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

    public static final int DEGF_FIELD_NUMBER = 9;
    private volatile java.lang.Object degf_;
    /**
     * <code>string degf = 9;</code>
     *
     * @return The degf.
     */
    public java.lang.String getDegf() {
      java.lang.Object ref = degf_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        degf_ = s;
        return s;
      }
    }
    /**
     * <code>string degf = 9;</code>
     *
     * @return The bytes for degf.
     */
    public com.google.protobuf.ByteString getDegfBytes() {
      java.lang.Object ref = degf_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        degf_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DEGC_FIELD_NUMBER = 10;
    private volatile java.lang.Object degc_;
    /**
     * <code>string degc = 10;</code>
     *
     * @return The degc.
     */
    public java.lang.String getDegc() {
      java.lang.Object ref = degc_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        degc_ = s;
        return s;
      }
    }
    /**
     * <code>string degc = 10;</code>
     *
     * @return The bytes for degc.
     */
    public com.google.protobuf.ByteString getDegcBytes() {
      java.lang.Object ref = degc_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        degc_ = b;
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
      if (stowagePlanId_ != 0L) {
        output.writeInt64(1, stowagePlanId_);
      }
      if (cargoId_ != 0L) {
        output.writeInt64(2, cargoId_);
      }
      if (!getCargoNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, cargoName_);
      }
      if (!getCargoAbbrevBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, cargoAbbrev_);
      }
      if (!getStandardTempBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, standardTemp_);
      }
      if (!getGradeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, grade_);
      }
      if (!getDensityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 7, density_);
      }
      if (!getApiBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, api_);
      }
      if (!getDegfBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 9, degf_);
      }
      if (!getDegcBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 10, degc_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (stowagePlanId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, stowagePlanId_);
      }
      if (cargoId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, cargoId_);
      }
      if (!getCargoNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, cargoName_);
      }
      if (!getCargoAbbrevBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, cargoAbbrev_);
      }
      if (!getStandardTempBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, standardTemp_);
      }
      if (!getGradeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, grade_);
      }
      if (!getDensityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, density_);
      }
      if (!getApiBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, api_);
      }
      if (!getDegfBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, degf_);
      }
      if (!getDegcBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(10, degc_);
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
      if (!(obj instanceof com.cpdss.common.generated.Loadicator.CargoInfo)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.Loadicator.CargoInfo other =
          (com.cpdss.common.generated.Loadicator.CargoInfo) obj;

      if (getStowagePlanId() != other.getStowagePlanId()) return false;
      if (getCargoId() != other.getCargoId()) return false;
      if (!getCargoName().equals(other.getCargoName())) return false;
      if (!getCargoAbbrev().equals(other.getCargoAbbrev())) return false;
      if (!getStandardTemp().equals(other.getStandardTemp())) return false;
      if (!getGrade().equals(other.getGrade())) return false;
      if (!getDensity().equals(other.getDensity())) return false;
      if (!getApi().equals(other.getApi())) return false;
      if (!getDegf().equals(other.getDegf())) return false;
      if (!getDegc().equals(other.getDegc())) return false;
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
      hash = (37 * hash) + STOWAGEPLANID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getStowagePlanId());
      hash = (37 * hash) + CARGOID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoId());
      hash = (37 * hash) + CARGONAME_FIELD_NUMBER;
      hash = (53 * hash) + getCargoName().hashCode();
      hash = (37 * hash) + CARGOABBREV_FIELD_NUMBER;
      hash = (53 * hash) + getCargoAbbrev().hashCode();
      hash = (37 * hash) + STANDARDTEMP_FIELD_NUMBER;
      hash = (53 * hash) + getStandardTemp().hashCode();
      hash = (37 * hash) + GRADE_FIELD_NUMBER;
      hash = (53 * hash) + getGrade().hashCode();
      hash = (37 * hash) + DENSITY_FIELD_NUMBER;
      hash = (53 * hash) + getDensity().hashCode();
      hash = (37 * hash) + API_FIELD_NUMBER;
      hash = (53 * hash) + getApi().hashCode();
      hash = (37 * hash) + DEGF_FIELD_NUMBER;
      hash = (53 * hash) + getDegf().hashCode();
      hash = (37 * hash) + DEGC_FIELD_NUMBER;
      hash = (53 * hash) + getDegc().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo parseFrom(
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

    public static Builder newBuilder(com.cpdss.common.generated.Loadicator.CargoInfo prototype) {
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
    /** Protobuf type {@code CargoInfo} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:CargoInfo)
        com.cpdss.common.generated.Loadicator.CargoInfoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.Loadicator.internal_static_CargoInfo_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.Loadicator.internal_static_CargoInfo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.Loadicator.CargoInfo.class,
                com.cpdss.common.generated.Loadicator.CargoInfo.Builder.class);
      }

      // Construct using com.cpdss.common.generated.Loadicator.CargoInfo.newBuilder()
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
        stowagePlanId_ = 0L;

        cargoId_ = 0L;

        cargoName_ = "";

        cargoAbbrev_ = "";

        standardTemp_ = "";

        grade_ = "";

        density_ = "";

        api_ = "";

        degf_ = "";

        degc_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.Loadicator.internal_static_CargoInfo_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.CargoInfo getDefaultInstanceForType() {
        return com.cpdss.common.generated.Loadicator.CargoInfo.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.CargoInfo build() {
        com.cpdss.common.generated.Loadicator.CargoInfo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.CargoInfo buildPartial() {
        com.cpdss.common.generated.Loadicator.CargoInfo result =
            new com.cpdss.common.generated.Loadicator.CargoInfo(this);
        result.stowagePlanId_ = stowagePlanId_;
        result.cargoId_ = cargoId_;
        result.cargoName_ = cargoName_;
        result.cargoAbbrev_ = cargoAbbrev_;
        result.standardTemp_ = standardTemp_;
        result.grade_ = grade_;
        result.density_ = density_;
        result.api_ = api_;
        result.degf_ = degf_;
        result.degc_ = degc_;
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
        if (other instanceof com.cpdss.common.generated.Loadicator.CargoInfo) {
          return mergeFrom((com.cpdss.common.generated.Loadicator.CargoInfo) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.Loadicator.CargoInfo other) {
        if (other == com.cpdss.common.generated.Loadicator.CargoInfo.getDefaultInstance())
          return this;
        if (other.getStowagePlanId() != 0L) {
          setStowagePlanId(other.getStowagePlanId());
        }
        if (other.getCargoId() != 0L) {
          setCargoId(other.getCargoId());
        }
        if (!other.getCargoName().isEmpty()) {
          cargoName_ = other.cargoName_;
          onChanged();
        }
        if (!other.getCargoAbbrev().isEmpty()) {
          cargoAbbrev_ = other.cargoAbbrev_;
          onChanged();
        }
        if (!other.getStandardTemp().isEmpty()) {
          standardTemp_ = other.standardTemp_;
          onChanged();
        }
        if (!other.getGrade().isEmpty()) {
          grade_ = other.grade_;
          onChanged();
        }
        if (!other.getDensity().isEmpty()) {
          density_ = other.density_;
          onChanged();
        }
        if (!other.getApi().isEmpty()) {
          api_ = other.api_;
          onChanged();
        }
        if (!other.getDegf().isEmpty()) {
          degf_ = other.degf_;
          onChanged();
        }
        if (!other.getDegc().isEmpty()) {
          degc_ = other.degc_;
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
        com.cpdss.common.generated.Loadicator.CargoInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.Loadicator.CargoInfo) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long stowagePlanId_;
      /**
       * <code>int64 stowagePlanId = 1;</code>
       *
       * @return The stowagePlanId.
       */
      public long getStowagePlanId() {
        return stowagePlanId_;
      }
      /**
       * <code>int64 stowagePlanId = 1;</code>
       *
       * @param value The stowagePlanId to set.
       * @return This builder for chaining.
       */
      public Builder setStowagePlanId(long value) {

        stowagePlanId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 stowagePlanId = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearStowagePlanId() {

        stowagePlanId_ = 0L;
        onChanged();
        return this;
      }

      private long cargoId_;
      /**
       * <code>int64 cargoId = 2;</code>
       *
       * @return The cargoId.
       */
      public long getCargoId() {
        return cargoId_;
      }
      /**
       * <code>int64 cargoId = 2;</code>
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
       * <code>int64 cargoId = 2;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoId() {

        cargoId_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object cargoName_ = "";
      /**
       * <code>string cargoName = 3;</code>
       *
       * @return The cargoName.
       */
      public java.lang.String getCargoName() {
        java.lang.Object ref = cargoName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargoName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargoName = 3;</code>
       *
       * @return The bytes for cargoName.
       */
      public com.google.protobuf.ByteString getCargoNameBytes() {
        java.lang.Object ref = cargoName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargoName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargoName = 3;</code>
       *
       * @param value The cargoName to set.
       * @return This builder for chaining.
       */
      public Builder setCargoName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargoName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargoName = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoName() {

        cargoName_ = getDefaultInstance().getCargoName();
        onChanged();
        return this;
      }
      /**
       * <code>string cargoName = 3;</code>
       *
       * @param value The bytes for cargoName to set.
       * @return This builder for chaining.
       */
      public Builder setCargoNameBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargoName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object cargoAbbrev_ = "";
      /**
       * <code>string cargoAbbrev = 4;</code>
       *
       * @return The cargoAbbrev.
       */
      public java.lang.String getCargoAbbrev() {
        java.lang.Object ref = cargoAbbrev_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cargoAbbrev_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cargoAbbrev = 4;</code>
       *
       * @return The bytes for cargoAbbrev.
       */
      public com.google.protobuf.ByteString getCargoAbbrevBytes() {
        java.lang.Object ref = cargoAbbrev_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          cargoAbbrev_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cargoAbbrev = 4;</code>
       *
       * @param value The cargoAbbrev to set.
       * @return This builder for chaining.
       */
      public Builder setCargoAbbrev(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        cargoAbbrev_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cargoAbbrev = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearCargoAbbrev() {

        cargoAbbrev_ = getDefaultInstance().getCargoAbbrev();
        onChanged();
        return this;
      }
      /**
       * <code>string cargoAbbrev = 4;</code>
       *
       * @param value The bytes for cargoAbbrev to set.
       * @return This builder for chaining.
       */
      public Builder setCargoAbbrevBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        cargoAbbrev_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object standardTemp_ = "";
      /**
       * <code>string standardTemp = 5;</code>
       *
       * @return The standardTemp.
       */
      public java.lang.String getStandardTemp() {
        java.lang.Object ref = standardTemp_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          standardTemp_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string standardTemp = 5;</code>
       *
       * @return The bytes for standardTemp.
       */
      public com.google.protobuf.ByteString getStandardTempBytes() {
        java.lang.Object ref = standardTemp_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          standardTemp_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string standardTemp = 5;</code>
       *
       * @param value The standardTemp to set.
       * @return This builder for chaining.
       */
      public Builder setStandardTemp(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        standardTemp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string standardTemp = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearStandardTemp() {

        standardTemp_ = getDefaultInstance().getStandardTemp();
        onChanged();
        return this;
      }
      /**
       * <code>string standardTemp = 5;</code>
       *
       * @param value The bytes for standardTemp to set.
       * @return This builder for chaining.
       */
      public Builder setStandardTempBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        standardTemp_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object grade_ = "";
      /**
       * <code>string grade = 6;</code>
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
       * <code>string grade = 6;</code>
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
       * <code>string grade = 6;</code>
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
       * <code>string grade = 6;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearGrade() {

        grade_ = getDefaultInstance().getGrade();
        onChanged();
        return this;
      }
      /**
       * <code>string grade = 6;</code>
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

      private java.lang.Object density_ = "";
      /**
       * <code>string density = 7;</code>
       *
       * @return The density.
       */
      public java.lang.String getDensity() {
        java.lang.Object ref = density_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          density_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string density = 7;</code>
       *
       * @return The bytes for density.
       */
      public com.google.protobuf.ByteString getDensityBytes() {
        java.lang.Object ref = density_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          density_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string density = 7;</code>
       *
       * @param value The density to set.
       * @return This builder for chaining.
       */
      public Builder setDensity(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        density_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string density = 7;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDensity() {

        density_ = getDefaultInstance().getDensity();
        onChanged();
        return this;
      }
      /**
       * <code>string density = 7;</code>
       *
       * @param value The bytes for density to set.
       * @return This builder for chaining.
       */
      public Builder setDensityBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        density_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object api_ = "";
      /**
       * <code>string api = 8;</code>
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
       * <code>string api = 8;</code>
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
       * <code>string api = 8;</code>
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
       * <code>string api = 8;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearApi() {

        api_ = getDefaultInstance().getApi();
        onChanged();
        return this;
      }
      /**
       * <code>string api = 8;</code>
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

      private java.lang.Object degf_ = "";
      /**
       * <code>string degf = 9;</code>
       *
       * @return The degf.
       */
      public java.lang.String getDegf() {
        java.lang.Object ref = degf_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          degf_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string degf = 9;</code>
       *
       * @return The bytes for degf.
       */
      public com.google.protobuf.ByteString getDegfBytes() {
        java.lang.Object ref = degf_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          degf_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string degf = 9;</code>
       *
       * @param value The degf to set.
       * @return This builder for chaining.
       */
      public Builder setDegf(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        degf_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string degf = 9;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDegf() {

        degf_ = getDefaultInstance().getDegf();
        onChanged();
        return this;
      }
      /**
       * <code>string degf = 9;</code>
       *
       * @param value The bytes for degf to set.
       * @return This builder for chaining.
       */
      public Builder setDegfBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        degf_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object degc_ = "";
      /**
       * <code>string degc = 10;</code>
       *
       * @return The degc.
       */
      public java.lang.String getDegc() {
        java.lang.Object ref = degc_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          degc_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string degc = 10;</code>
       *
       * @return The bytes for degc.
       */
      public com.google.protobuf.ByteString getDegcBytes() {
        java.lang.Object ref = degc_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          degc_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string degc = 10;</code>
       *
       * @param value The degc to set.
       * @return This builder for chaining.
       */
      public Builder setDegc(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        degc_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string degc = 10;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearDegc() {

        degc_ = getDefaultInstance().getDegc();
        onChanged();
        return this;
      }
      /**
       * <code>string degc = 10;</code>
       *
       * @param value The bytes for degc to set.
       * @return This builder for chaining.
       */
      public Builder setDegcBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        degc_ = value;
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

      // @@protoc_insertion_point(builder_scope:CargoInfo)
    }

    // @@protoc_insertion_point(class_scope:CargoInfo)
    private static final com.cpdss.common.generated.Loadicator.CargoInfo DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.Loadicator.CargoInfo();
    }

    public static com.cpdss.common.generated.Loadicator.CargoInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<CargoInfo> PARSER =
        new com.google.protobuf.AbstractParser<CargoInfo>() {
          @java.lang.Override
          public CargoInfo parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new CargoInfo(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<CargoInfo> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CargoInfo> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.Loadicator.CargoInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface OtherTankInfoOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:OtherTankInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 stowagePlanId = 1;</code>
     *
     * @return The stowagePlanId.
     */
    long getStowagePlanId();

    /**
     * <code>int64 tankId = 2;</code>
     *
     * @return The tankId.
     */
    long getTankId();

    /**
     * <code>string tankName = 3;</code>
     *
     * @return The tankName.
     */
    java.lang.String getTankName();
    /**
     * <code>string tankName = 3;</code>
     *
     * @return The bytes for tankName.
     */
    com.google.protobuf.ByteString getTankNameBytes();

    /**
     * <code>string quantity = 4;</code>
     *
     * @return The quantity.
     */
    java.lang.String getQuantity();
    /**
     * <code>string quantity = 4;</code>
     *
     * @return The bytes for quantity.
     */
    com.google.protobuf.ByteString getQuantityBytes();

    /**
     * <code>string shortName = 5;</code>
     *
     * @return The shortName.
     */
    java.lang.String getShortName();
    /**
     * <code>string shortName = 5;</code>
     *
     * @return The bytes for shortName.
     */
    com.google.protobuf.ByteString getShortNameBytes();
  }
  /** Protobuf type {@code OtherTankInfo} */
  public static final class OtherTankInfo extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:OtherTankInfo)
      OtherTankInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use OtherTankInfo.newBuilder() to construct.
    private OtherTankInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private OtherTankInfo() {
      tankName_ = "";
      quantity_ = "";
      shortName_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new OtherTankInfo();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private OtherTankInfo(
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
                stowagePlanId_ = input.readInt64();
                break;
              }
            case 16:
              {
                tankId_ = input.readInt64();
                break;
              }
            case 26:
              {
                java.lang.String s = input.readStringRequireUtf8();

                tankName_ = s;
                break;
              }
            case 34:
              {
                java.lang.String s = input.readStringRequireUtf8();

                quantity_ = s;
                break;
              }
            case 42:
              {
                java.lang.String s = input.readStringRequireUtf8();

                shortName_ = s;
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
      return com.cpdss.common.generated.Loadicator.internal_static_OtherTankInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.Loadicator.internal_static_OtherTankInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.Loadicator.OtherTankInfo.class,
              com.cpdss.common.generated.Loadicator.OtherTankInfo.Builder.class);
    }

    public static final int STOWAGEPLANID_FIELD_NUMBER = 1;
    private long stowagePlanId_;
    /**
     * <code>int64 stowagePlanId = 1;</code>
     *
     * @return The stowagePlanId.
     */
    public long getStowagePlanId() {
      return stowagePlanId_;
    }

    public static final int TANKID_FIELD_NUMBER = 2;
    private long tankId_;
    /**
     * <code>int64 tankId = 2;</code>
     *
     * @return The tankId.
     */
    public long getTankId() {
      return tankId_;
    }

    public static final int TANKNAME_FIELD_NUMBER = 3;
    private volatile java.lang.Object tankName_;
    /**
     * <code>string tankName = 3;</code>
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
     * <code>string tankName = 3;</code>
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

    public static final int QUANTITY_FIELD_NUMBER = 4;
    private volatile java.lang.Object quantity_;
    /**
     * <code>string quantity = 4;</code>
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
     * <code>string quantity = 4;</code>
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

    public static final int SHORTNAME_FIELD_NUMBER = 5;
    private volatile java.lang.Object shortName_;
    /**
     * <code>string shortName = 5;</code>
     *
     * @return The shortName.
     */
    public java.lang.String getShortName() {
      java.lang.Object ref = shortName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        shortName_ = s;
        return s;
      }
    }
    /**
     * <code>string shortName = 5;</code>
     *
     * @return The bytes for shortName.
     */
    public com.google.protobuf.ByteString getShortNameBytes() {
      java.lang.Object ref = shortName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        shortName_ = b;
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
      if (stowagePlanId_ != 0L) {
        output.writeInt64(1, stowagePlanId_);
      }
      if (tankId_ != 0L) {
        output.writeInt64(2, tankId_);
      }
      if (!getTankNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, tankName_);
      }
      if (!getQuantityBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, quantity_);
      }
      if (!getShortNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, shortName_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (stowagePlanId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, stowagePlanId_);
      }
      if (tankId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, tankId_);
      }
      if (!getTankNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, tankName_);
      }
      if (!getQuantityBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, quantity_);
      }
      if (!getShortNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, shortName_);
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
      if (!(obj instanceof com.cpdss.common.generated.Loadicator.OtherTankInfo)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.Loadicator.OtherTankInfo other =
          (com.cpdss.common.generated.Loadicator.OtherTankInfo) obj;

      if (getStowagePlanId() != other.getStowagePlanId()) return false;
      if (getTankId() != other.getTankId()) return false;
      if (!getTankName().equals(other.getTankName())) return false;
      if (!getQuantity().equals(other.getQuantity())) return false;
      if (!getShortName().equals(other.getShortName())) return false;
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
      hash = (37 * hash) + STOWAGEPLANID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getStowagePlanId());
      hash = (37 * hash) + TANKID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getTankId());
      hash = (37 * hash) + TANKNAME_FIELD_NUMBER;
      hash = (53 * hash) + getTankName().hashCode();
      hash = (37 * hash) + QUANTITY_FIELD_NUMBER;
      hash = (53 * hash) + getQuantity().hashCode();
      hash = (37 * hash) + SHORTNAME_FIELD_NUMBER;
      hash = (53 * hash) + getShortName().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo parseFrom(
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
        com.cpdss.common.generated.Loadicator.OtherTankInfo prototype) {
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
    /** Protobuf type {@code OtherTankInfo} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:OtherTankInfo)
        com.cpdss.common.generated.Loadicator.OtherTankInfoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.Loadicator.internal_static_OtherTankInfo_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.Loadicator
            .internal_static_OtherTankInfo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.Loadicator.OtherTankInfo.class,
                com.cpdss.common.generated.Loadicator.OtherTankInfo.Builder.class);
      }

      // Construct using com.cpdss.common.generated.Loadicator.OtherTankInfo.newBuilder()
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
        stowagePlanId_ = 0L;

        tankId_ = 0L;

        tankName_ = "";

        quantity_ = "";

        shortName_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return com.cpdss.common.generated.Loadicator.internal_static_OtherTankInfo_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.OtherTankInfo getDefaultInstanceForType() {
        return com.cpdss.common.generated.Loadicator.OtherTankInfo.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.OtherTankInfo build() {
        com.cpdss.common.generated.Loadicator.OtherTankInfo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.OtherTankInfo buildPartial() {
        com.cpdss.common.generated.Loadicator.OtherTankInfo result =
            new com.cpdss.common.generated.Loadicator.OtherTankInfo(this);
        result.stowagePlanId_ = stowagePlanId_;
        result.tankId_ = tankId_;
        result.tankName_ = tankName_;
        result.quantity_ = quantity_;
        result.shortName_ = shortName_;
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
        if (other instanceof com.cpdss.common.generated.Loadicator.OtherTankInfo) {
          return mergeFrom((com.cpdss.common.generated.Loadicator.OtherTankInfo) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.Loadicator.OtherTankInfo other) {
        if (other == com.cpdss.common.generated.Loadicator.OtherTankInfo.getDefaultInstance())
          return this;
        if (other.getStowagePlanId() != 0L) {
          setStowagePlanId(other.getStowagePlanId());
        }
        if (other.getTankId() != 0L) {
          setTankId(other.getTankId());
        }
        if (!other.getTankName().isEmpty()) {
          tankName_ = other.tankName_;
          onChanged();
        }
        if (!other.getQuantity().isEmpty()) {
          quantity_ = other.quantity_;
          onChanged();
        }
        if (!other.getShortName().isEmpty()) {
          shortName_ = other.shortName_;
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
        com.cpdss.common.generated.Loadicator.OtherTankInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.Loadicator.OtherTankInfo) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long stowagePlanId_;
      /**
       * <code>int64 stowagePlanId = 1;</code>
       *
       * @return The stowagePlanId.
       */
      public long getStowagePlanId() {
        return stowagePlanId_;
      }
      /**
       * <code>int64 stowagePlanId = 1;</code>
       *
       * @param value The stowagePlanId to set.
       * @return This builder for chaining.
       */
      public Builder setStowagePlanId(long value) {

        stowagePlanId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 stowagePlanId = 1;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearStowagePlanId() {

        stowagePlanId_ = 0L;
        onChanged();
        return this;
      }

      private long tankId_;
      /**
       * <code>int64 tankId = 2;</code>
       *
       * @return The tankId.
       */
      public long getTankId() {
        return tankId_;
      }
      /**
       * <code>int64 tankId = 2;</code>
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
       * <code>int64 tankId = 2;</code>
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
       * <code>string tankName = 3;</code>
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
       * <code>string tankName = 3;</code>
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
       * <code>string tankName = 3;</code>
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
       * <code>string tankName = 3;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearTankName() {

        tankName_ = getDefaultInstance().getTankName();
        onChanged();
        return this;
      }
      /**
       * <code>string tankName = 3;</code>
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

      private java.lang.Object quantity_ = "";
      /**
       * <code>string quantity = 4;</code>
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
       * <code>string quantity = 4;</code>
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
       * <code>string quantity = 4;</code>
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
       * <code>string quantity = 4;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearQuantity() {

        quantity_ = getDefaultInstance().getQuantity();
        onChanged();
        return this;
      }
      /**
       * <code>string quantity = 4;</code>
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

      private java.lang.Object shortName_ = "";
      /**
       * <code>string shortName = 5;</code>
       *
       * @return The shortName.
       */
      public java.lang.String getShortName() {
        java.lang.Object ref = shortName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          shortName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string shortName = 5;</code>
       *
       * @return The bytes for shortName.
       */
      public com.google.protobuf.ByteString getShortNameBytes() {
        java.lang.Object ref = shortName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
          shortName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string shortName = 5;</code>
       *
       * @param value The shortName to set.
       * @return This builder for chaining.
       */
      public Builder setShortName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }

        shortName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string shortName = 5;</code>
       *
       * @return This builder for chaining.
       */
      public Builder clearShortName() {

        shortName_ = getDefaultInstance().getShortName();
        onChanged();
        return this;
      }
      /**
       * <code>string shortName = 5;</code>
       *
       * @param value The bytes for shortName to set.
       * @return This builder for chaining.
       */
      public Builder setShortNameBytes(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        checkByteStringIsUtf8(value);

        shortName_ = value;
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

      // @@protoc_insertion_point(builder_scope:OtherTankInfo)
    }

    // @@protoc_insertion_point(class_scope:OtherTankInfo)
    private static final com.cpdss.common.generated.Loadicator.OtherTankInfo DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.Loadicator.OtherTankInfo();
    }

    public static com.cpdss.common.generated.Loadicator.OtherTankInfo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<OtherTankInfo> PARSER =
        new com.google.protobuf.AbstractParser<OtherTankInfo>() {
          @java.lang.Override
          public OtherTankInfo parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new OtherTankInfo(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<OtherTankInfo> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<OtherTankInfo> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.Loadicator.OtherTankInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  public interface LoadicatorReplyOrBuilder
      extends
      // @@protoc_insertion_point(interface_extends:LoadicatorReply)
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
  /** Protobuf type {@code LoadicatorReply} */
  public static final class LoadicatorReply extends com.google.protobuf.GeneratedMessageV3
      implements
      // @@protoc_insertion_point(message_implements:LoadicatorReply)
      LoadicatorReplyOrBuilder {
    private static final long serialVersionUID = 0L;
    // Use LoadicatorReply.newBuilder() to construct.
    private LoadicatorReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }

    private LoadicatorReply() {}

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
      return new LoadicatorReply();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
    }

    private LoadicatorReply(
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
      return com.cpdss.common.generated.Loadicator.internal_static_LoadicatorReply_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.Loadicator
          .internal_static_LoadicatorReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.Loadicator.LoadicatorReply.class,
              com.cpdss.common.generated.Loadicator.LoadicatorReply.Builder.class);
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
      if (!(obj instanceof com.cpdss.common.generated.Loadicator.LoadicatorReply)) {
        return super.equals(obj);
      }
      com.cpdss.common.generated.Loadicator.LoadicatorReply other =
          (com.cpdss.common.generated.Loadicator.LoadicatorReply) obj;

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

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseFrom(
        java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseFrom(
        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseFrom(
        byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseDelimitedFrom(
        java.io.InputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseDelimitedFrom(
        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
          PARSER, input, extensionRegistry);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseFrom(
        com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply parseFrom(
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
        com.cpdss.common.generated.Loadicator.LoadicatorReply prototype) {
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
    /** Protobuf type {@code LoadicatorReply} */
    public static final class Builder
        extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
        implements
        // @@protoc_insertion_point(builder_implements:LoadicatorReply)
        com.cpdss.common.generated.Loadicator.LoadicatorReplyOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.cpdss.common.generated.Loadicator.internal_static_LoadicatorReply_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cpdss.common.generated.Loadicator
            .internal_static_LoadicatorReply_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cpdss.common.generated.Loadicator.LoadicatorReply.class,
                com.cpdss.common.generated.Loadicator.LoadicatorReply.Builder.class);
      }

      // Construct using com.cpdss.common.generated.Loadicator.LoadicatorReply.newBuilder()
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
        return com.cpdss.common.generated.Loadicator.internal_static_LoadicatorReply_descriptor;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.LoadicatorReply getDefaultInstanceForType() {
        return com.cpdss.common.generated.Loadicator.LoadicatorReply.getDefaultInstance();
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.LoadicatorReply build() {
        com.cpdss.common.generated.Loadicator.LoadicatorReply result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cpdss.common.generated.Loadicator.LoadicatorReply buildPartial() {
        com.cpdss.common.generated.Loadicator.LoadicatorReply result =
            new com.cpdss.common.generated.Loadicator.LoadicatorReply(this);
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
        if (other instanceof com.cpdss.common.generated.Loadicator.LoadicatorReply) {
          return mergeFrom((com.cpdss.common.generated.Loadicator.LoadicatorReply) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cpdss.common.generated.Loadicator.LoadicatorReply other) {
        if (other == com.cpdss.common.generated.Loadicator.LoadicatorReply.getDefaultInstance())
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
        com.cpdss.common.generated.Loadicator.LoadicatorReply parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage =
              (com.cpdss.common.generated.Loadicator.LoadicatorReply) e.getUnfinishedMessage();
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

      // @@protoc_insertion_point(builder_scope:LoadicatorReply)
    }

    // @@protoc_insertion_point(class_scope:LoadicatorReply)
    private static final com.cpdss.common.generated.Loadicator.LoadicatorReply DEFAULT_INSTANCE;

    static {
      DEFAULT_INSTANCE = new com.cpdss.common.generated.Loadicator.LoadicatorReply();
    }

    public static com.cpdss.common.generated.Loadicator.LoadicatorReply getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<LoadicatorReply> PARSER =
        new com.google.protobuf.AbstractParser<LoadicatorReply>() {
          @java.lang.Override
          public LoadicatorReply parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws com.google.protobuf.InvalidProtocolBufferException {
            return new LoadicatorReply(input, extensionRegistry);
          }
        };

    public static com.google.protobuf.Parser<LoadicatorReply> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<LoadicatorReply> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cpdss.common.generated.Loadicator.LoadicatorReply getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }
  }

  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadicatorRequest_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadicatorRequest_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_StowagePlanDetails_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_StowagePlanDetails_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_StowageDetailsInfo_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_StowageDetailsInfo_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_CargoInfo_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoInfo_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_OtherTankInfo_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_OtherTankInfo_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
      internal_static_LoadicatorReply_descriptor;
  private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_LoadicatorReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\020loadicator.proto\032\014common.proto\"\273\001\n\021Loa"
          + "dicatorRequest\022/\n\022stowagePlanDetails\030\001 \001"
          + "(\0132\023.StowagePlanDetails\022/\n\022stowageDetail"
          + "sInfo\030\002 \001(\0132\023.StowageDetailsInfo\022\035\n\tcarg"
          + "oInfo\030\003 \001(\0132\n.CargoInfo\022%\n\rotherTankInfo"
          + "\030\004 \001(\0132\016.OtherTankInfo\"\363\002\n\022StowagePlanDe"
          + "tails\022\n\n\002id\030\001 \001(\003\022\020\n\010vesselId\030\002 \001(\003\022\021\n\ti"
          + "moNumber\030\003 \001(\t\022\021\n\tcompanyId\030\004 \001(\003\022\020\n\010shi"
          + "pType\030\005 \001(\003\022\022\n\nvesselCode\030\006 \001(\t\022\025\n\rbooki"
          + "ngListId\030\007 \001(\003\022\021\n\tstowageId\030\010 \001(\003\022\016\n\006por"
          + "tId\030\t \001(\003\022\020\n\010portCode\030\n \001(\t\022\016\n\006status\030\013 "
          + "\001(\003\022\032\n\022deadweightConstant\030\014 \001(\003\022\033\n\023provi"
          + "sionalConstant\030\r \001(\003\022\020\n\010calCount\030\016 \001(\003\022\022"
          + "\n\nsaveStatus\030\017 \001(\t\022\023\n\013saveMessage\030\020 \001(\t\022"
          + "\021\n\tdamageCal\030\021 \001(\010\022\020\n\010dataSave\030\022 \001(\010\"\304\001\n"
          + "\022StowageDetailsInfo\022\025\n\rstowagePlanId\030\001 \001"
          + "(\003\022\017\n\007cargoId\030\002 \001(\003\022\023\n\013cargoBookId\030\003 \001(\003"
          + "\022\021\n\tcargoName\030\004 \001(\t\022\027\n\017specificGravity\030\005"
          + " \001(\t\022\020\n\010quantity\030\006 \001(\t\022\016\n\006tankId\030\007 \001(\003\022\021"
          + "\n\tshortName\030\010 \001(\t\022\020\n\010tankName\030\t \001(\t\"\272\001\n\t"
          + "CargoInfo\022\025\n\rstowagePlanId\030\001 \001(\003\022\017\n\007carg"
          + "oId\030\002 \001(\003\022\021\n\tcargoName\030\003 \001(\t\022\023\n\013cargoAbb"
          + "rev\030\004 \001(\t\022\024\n\014standardTemp\030\005 \001(\t\022\r\n\005grade"
          + "\030\006 \001(\t\022\017\n\007density\030\007 \001(\t\022\013\n\003api\030\010 \001(\t\022\014\n\004"
          + "degf\030\t \001(\t\022\014\n\004degc\030\n \001(\t\"m\n\rOtherTankInf"
          + "o\022\025\n\rstowagePlanId\030\001 \001(\003\022\016\n\006tankId\030\002 \001(\003"
          + "\022\020\n\010tankName\030\003 \001(\t\022\020\n\010quantity\030\004 \001(\t\022\021\n\t"
          + "shortName\030\005 \001(\t\":\n\017LoadicatorReply\022\'\n\016re"
          + "sponseStatus\030\001 \001(\0132\017.ResponseStatus2Q\n\021L"
          + "oadicatorService\022<\n\022saveLoadicatorInfo\022\022"
          + ".LoadicatorRequest\032\020.LoadicatorReply\"\000B\036"
          + "\n\032com.cpdss.common.generatedP\000b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
            });
    internal_static_LoadicatorRequest_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_LoadicatorRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadicatorRequest_descriptor,
            new java.lang.String[] {
              "StowagePlanDetails", "StowageDetailsInfo", "CargoInfo", "OtherTankInfo",
            });
    internal_static_StowagePlanDetails_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_StowagePlanDetails_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_StowagePlanDetails_descriptor,
            new java.lang.String[] {
              "Id",
              "VesselId",
              "ImoNumber",
              "CompanyId",
              "ShipType",
              "VesselCode",
              "BookingListId",
              "StowageId",
              "PortId",
              "PortCode",
              "Status",
              "DeadweightConstant",
              "ProvisionalConstant",
              "CalCount",
              "SaveStatus",
              "SaveMessage",
              "DamageCal",
              "DataSave",
            });
    internal_static_StowageDetailsInfo_descriptor = getDescriptor().getMessageTypes().get(2);
    internal_static_StowageDetailsInfo_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_StowageDetailsInfo_descriptor,
            new java.lang.String[] {
              "StowagePlanId",
              "CargoId",
              "CargoBookId",
              "CargoName",
              "SpecificGravity",
              "Quantity",
              "TankId",
              "ShortName",
              "TankName",
            });
    internal_static_CargoInfo_descriptor = getDescriptor().getMessageTypes().get(3);
    internal_static_CargoInfo_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoInfo_descriptor,
            new java.lang.String[] {
              "StowagePlanId",
              "CargoId",
              "CargoName",
              "CargoAbbrev",
              "StandardTemp",
              "Grade",
              "Density",
              "Api",
              "Degf",
              "Degc",
            });
    internal_static_OtherTankInfo_descriptor = getDescriptor().getMessageTypes().get(4);
    internal_static_OtherTankInfo_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_OtherTankInfo_descriptor,
            new java.lang.String[] {
              "StowagePlanId", "TankId", "TankName", "Quantity", "ShortName",
            });
    internal_static_LoadicatorReply_descriptor = getDescriptor().getMessageTypes().get(5);
    internal_static_LoadicatorReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_LoadicatorReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus",
            });
    com.cpdss.common.generated.Common.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
