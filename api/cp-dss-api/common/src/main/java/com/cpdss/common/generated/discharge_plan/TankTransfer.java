/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code TankTransfer} */
public final class TankTransfer extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:TankTransfer)
    TankTransferOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use TankTransfer.newBuilder() to construct.
  private TankTransfer(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private TankTransfer() {
    fromTankIds_ = emptyLongList();
    purpose_ = "";
    tankTransferDetails_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new TankTransfer();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private TankTransfer(
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
                fromTankIds_ = newLongList();
                mutable_bitField0_ |= 0x00000001;
              }
              fromTankIds_.addLong(input.readInt64());
              break;
            }
          case 10:
            {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
                fromTankIds_ = newLongList();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                fromTankIds_.addLong(input.readInt64());
              }
              input.popLimit(limit);
              break;
            }
          case 16:
            {
              toTankId_ = input.readInt64();
              break;
            }
          case 24:
            {
              timeStart_ = input.readInt32();
              break;
            }
          case 32:
            {
              timeEnd_ = input.readInt32();
              break;
            }
          case 40:
            {
              cargoNominationId_ = input.readInt64();
              break;
            }
          case 50:
            {
              java.lang.String s = input.readStringRequireUtf8();

              purpose_ = s;
              break;
            }
          case 58:
            {
              if (!((mutable_bitField0_ & 0x00000002) != 0)) {
                tankTransferDetails_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.discharge_plan.TankTransferDetail>();
                mutable_bitField0_ |= 0x00000002;
              }
              tankTransferDetails_.add(
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.TankTransferDetail.parser(),
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
        fromTankIds_.makeImmutable(); // C
      }
      if (((mutable_bitField0_ & 0x00000002) != 0)) {
        tankTransferDetails_ = java.util.Collections.unmodifiableList(tankTransferDetails_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_TankTransfer_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_TankTransfer_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.TankTransfer.class,
            com.cpdss.common.generated.discharge_plan.TankTransfer.Builder.class);
  }

  public static final int FROMTANKIDS_FIELD_NUMBER = 1;
  private com.google.protobuf.Internal.LongList fromTankIds_;
  /**
   * <code>repeated int64 fromTankIds = 1;</code>
   *
   * @return A list containing the fromTankIds.
   */
  public java.util.List<java.lang.Long> getFromTankIdsList() {
    return fromTankIds_;
  }
  /**
   * <code>repeated int64 fromTankIds = 1;</code>
   *
   * @return The count of fromTankIds.
   */
  public int getFromTankIdsCount() {
    return fromTankIds_.size();
  }
  /**
   * <code>repeated int64 fromTankIds = 1;</code>
   *
   * @param index The index of the element to return.
   * @return The fromTankIds at the given index.
   */
  public long getFromTankIds(int index) {
    return fromTankIds_.getLong(index);
  }

  private int fromTankIdsMemoizedSerializedSize = -1;

  public static final int TOTANKID_FIELD_NUMBER = 2;
  private long toTankId_;
  /**
   * <code>int64 toTankId = 2;</code>
   *
   * @return The toTankId.
   */
  public long getToTankId() {
    return toTankId_;
  }

  public static final int TIMESTART_FIELD_NUMBER = 3;
  private int timeStart_;
  /**
   * <code>int32 timeStart = 3;</code>
   *
   * @return The timeStart.
   */
  public int getTimeStart() {
    return timeStart_;
  }

  public static final int TIMEEND_FIELD_NUMBER = 4;
  private int timeEnd_;
  /**
   * <code>int32 timeEnd = 4;</code>
   *
   * @return The timeEnd.
   */
  public int getTimeEnd() {
    return timeEnd_;
  }

  public static final int CARGONOMINATIONID_FIELD_NUMBER = 5;
  private long cargoNominationId_;
  /**
   * <code>int64 cargoNominationId = 5;</code>
   *
   * @return The cargoNominationId.
   */
  public long getCargoNominationId() {
    return cargoNominationId_;
  }

  public static final int PURPOSE_FIELD_NUMBER = 6;
  private volatile java.lang.Object purpose_;
  /**
   * <code>string purpose = 6;</code>
   *
   * @return The purpose.
   */
  public java.lang.String getPurpose() {
    java.lang.Object ref = purpose_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      purpose_ = s;
      return s;
    }
  }
  /**
   * <code>string purpose = 6;</code>
   *
   * @return The bytes for purpose.
   */
  public com.google.protobuf.ByteString getPurposeBytes() {
    java.lang.Object ref = purpose_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      purpose_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TANKTRANSFERDETAILS_FIELD_NUMBER = 7;
  private java.util.List<com.cpdss.common.generated.discharge_plan.TankTransferDetail>
      tankTransferDetails_;
  /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
  public java.util.List<com.cpdss.common.generated.discharge_plan.TankTransferDetail>
      getTankTransferDetailsList() {
    return tankTransferDetails_;
  }
  /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
  public java.util.List<
          ? extends com.cpdss.common.generated.discharge_plan.TankTransferDetailOrBuilder>
      getTankTransferDetailsOrBuilderList() {
    return tankTransferDetails_;
  }
  /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
  public int getTankTransferDetailsCount() {
    return tankTransferDetails_.size();
  }
  /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
  public com.cpdss.common.generated.discharge_plan.TankTransferDetail getTankTransferDetails(
      int index) {
    return tankTransferDetails_.get(index);
  }
  /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
  public com.cpdss.common.generated.discharge_plan.TankTransferDetailOrBuilder
      getTankTransferDetailsOrBuilder(int index) {
    return tankTransferDetails_.get(index);
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
    getSerializedSize();
    if (getFromTankIdsList().size() > 0) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(fromTankIdsMemoizedSerializedSize);
    }
    for (int i = 0; i < fromTankIds_.size(); i++) {
      output.writeInt64NoTag(fromTankIds_.getLong(i));
    }
    if (toTankId_ != 0L) {
      output.writeInt64(2, toTankId_);
    }
    if (timeStart_ != 0) {
      output.writeInt32(3, timeStart_);
    }
    if (timeEnd_ != 0) {
      output.writeInt32(4, timeEnd_);
    }
    if (cargoNominationId_ != 0L) {
      output.writeInt64(5, cargoNominationId_);
    }
    if (!getPurposeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, purpose_);
    }
    for (int i = 0; i < tankTransferDetails_.size(); i++) {
      output.writeMessage(7, tankTransferDetails_.get(i));
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
      for (int i = 0; i < fromTankIds_.size(); i++) {
        dataSize +=
            com.google.protobuf.CodedOutputStream.computeInt64SizeNoTag(fromTankIds_.getLong(i));
      }
      size += dataSize;
      if (!getFromTankIdsList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream.computeInt32SizeNoTag(dataSize);
      }
      fromTankIdsMemoizedSerializedSize = dataSize;
    }
    if (toTankId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, toTankId_);
    }
    if (timeStart_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(3, timeStart_);
    }
    if (timeEnd_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(4, timeEnd_);
    }
    if (cargoNominationId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(5, cargoNominationId_);
    }
    if (!getPurposeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, purpose_);
    }
    for (int i = 0; i < tankTransferDetails_.size(); i++) {
      size +=
          com.google.protobuf.CodedOutputStream.computeMessageSize(7, tankTransferDetails_.get(i));
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.TankTransfer)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.TankTransfer other =
        (com.cpdss.common.generated.discharge_plan.TankTransfer) obj;

    if (!getFromTankIdsList().equals(other.getFromTankIdsList())) return false;
    if (getToTankId() != other.getToTankId()) return false;
    if (getTimeStart() != other.getTimeStart()) return false;
    if (getTimeEnd() != other.getTimeEnd()) return false;
    if (getCargoNominationId() != other.getCargoNominationId()) return false;
    if (!getPurpose().equals(other.getPurpose())) return false;
    if (!getTankTransferDetailsList().equals(other.getTankTransferDetailsList())) return false;
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
    if (getFromTankIdsCount() > 0) {
      hash = (37 * hash) + FROMTANKIDS_FIELD_NUMBER;
      hash = (53 * hash) + getFromTankIdsList().hashCode();
    }
    hash = (37 * hash) + TOTANKID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getToTankId());
    hash = (37 * hash) + TIMESTART_FIELD_NUMBER;
    hash = (53 * hash) + getTimeStart();
    hash = (37 * hash) + TIMEEND_FIELD_NUMBER;
    hash = (53 * hash) + getTimeEnd();
    hash = (37 * hash) + CARGONOMINATIONID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoNominationId());
    hash = (37 * hash) + PURPOSE_FIELD_NUMBER;
    hash = (53 * hash) + getPurpose().hashCode();
    if (getTankTransferDetailsCount() > 0) {
      hash = (37 * hash) + TANKTRANSFERDETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getTankTransferDetailsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer parseFrom(
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
      com.cpdss.common.generated.discharge_plan.TankTransfer prototype) {
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
  /** Protobuf type {@code TankTransfer} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:TankTransfer)
      com.cpdss.common.generated.discharge_plan.TankTransferOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_TankTransfer_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_TankTransfer_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.TankTransfer.class,
              com.cpdss.common.generated.discharge_plan.TankTransfer.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.TankTransfer.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getTankTransferDetailsFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      fromTankIds_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000001);
      toTankId_ = 0L;

      timeStart_ = 0;

      timeEnd_ = 0;

      cargoNominationId_ = 0L;

      purpose_ = "";

      if (tankTransferDetailsBuilder_ == null) {
        tankTransferDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        tankTransferDetailsBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_TankTransfer_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.TankTransfer getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.TankTransfer.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.TankTransfer build() {
      com.cpdss.common.generated.discharge_plan.TankTransfer result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.TankTransfer buildPartial() {
      com.cpdss.common.generated.discharge_plan.TankTransfer result =
          new com.cpdss.common.generated.discharge_plan.TankTransfer(this);
      int from_bitField0_ = bitField0_;
      if (((bitField0_ & 0x00000001) != 0)) {
        fromTankIds_.makeImmutable();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.fromTankIds_ = fromTankIds_;
      result.toTankId_ = toTankId_;
      result.timeStart_ = timeStart_;
      result.timeEnd_ = timeEnd_;
      result.cargoNominationId_ = cargoNominationId_;
      result.purpose_ = purpose_;
      if (tankTransferDetailsBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0)) {
          tankTransferDetails_ = java.util.Collections.unmodifiableList(tankTransferDetails_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.tankTransferDetails_ = tankTransferDetails_;
      } else {
        result.tankTransferDetails_ = tankTransferDetailsBuilder_.build();
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.TankTransfer) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.TankTransfer) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.TankTransfer other) {
      if (other == com.cpdss.common.generated.discharge_plan.TankTransfer.getDefaultInstance())
        return this;
      if (!other.fromTankIds_.isEmpty()) {
        if (fromTankIds_.isEmpty()) {
          fromTankIds_ = other.fromTankIds_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureFromTankIdsIsMutable();
          fromTankIds_.addAll(other.fromTankIds_);
        }
        onChanged();
      }
      if (other.getToTankId() != 0L) {
        setToTankId(other.getToTankId());
      }
      if (other.getTimeStart() != 0) {
        setTimeStart(other.getTimeStart());
      }
      if (other.getTimeEnd() != 0) {
        setTimeEnd(other.getTimeEnd());
      }
      if (other.getCargoNominationId() != 0L) {
        setCargoNominationId(other.getCargoNominationId());
      }
      if (!other.getPurpose().isEmpty()) {
        purpose_ = other.purpose_;
        onChanged();
      }
      if (tankTransferDetailsBuilder_ == null) {
        if (!other.tankTransferDetails_.isEmpty()) {
          if (tankTransferDetails_.isEmpty()) {
            tankTransferDetails_ = other.tankTransferDetails_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureTankTransferDetailsIsMutable();
            tankTransferDetails_.addAll(other.tankTransferDetails_);
          }
          onChanged();
        }
      } else {
        if (!other.tankTransferDetails_.isEmpty()) {
          if (tankTransferDetailsBuilder_.isEmpty()) {
            tankTransferDetailsBuilder_.dispose();
            tankTransferDetailsBuilder_ = null;
            tankTransferDetails_ = other.tankTransferDetails_;
            bitField0_ = (bitField0_ & ~0x00000002);
            tankTransferDetailsBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getTankTransferDetailsFieldBuilder()
                    : null;
          } else {
            tankTransferDetailsBuilder_.addAllMessages(other.tankTransferDetails_);
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
      com.cpdss.common.generated.discharge_plan.TankTransfer parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.TankTransfer) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int bitField0_;

    private com.google.protobuf.Internal.LongList fromTankIds_ = emptyLongList();

    private void ensureFromTankIdsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        fromTankIds_ = mutableCopy(fromTankIds_);
        bitField0_ |= 0x00000001;
      }
    }
    /**
     * <code>repeated int64 fromTankIds = 1;</code>
     *
     * @return A list containing the fromTankIds.
     */
    public java.util.List<java.lang.Long> getFromTankIdsList() {
      return ((bitField0_ & 0x00000001) != 0)
          ? java.util.Collections.unmodifiableList(fromTankIds_)
          : fromTankIds_;
    }
    /**
     * <code>repeated int64 fromTankIds = 1;</code>
     *
     * @return The count of fromTankIds.
     */
    public int getFromTankIdsCount() {
      return fromTankIds_.size();
    }
    /**
     * <code>repeated int64 fromTankIds = 1;</code>
     *
     * @param index The index of the element to return.
     * @return The fromTankIds at the given index.
     */
    public long getFromTankIds(int index) {
      return fromTankIds_.getLong(index);
    }
    /**
     * <code>repeated int64 fromTankIds = 1;</code>
     *
     * @param index The index to set the value at.
     * @param value The fromTankIds to set.
     * @return This builder for chaining.
     */
    public Builder setFromTankIds(int index, long value) {
      ensureFromTankIdsIsMutable();
      fromTankIds_.setLong(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 fromTankIds = 1;</code>
     *
     * @param value The fromTankIds to add.
     * @return This builder for chaining.
     */
    public Builder addFromTankIds(long value) {
      ensureFromTankIdsIsMutable();
      fromTankIds_.addLong(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 fromTankIds = 1;</code>
     *
     * @param values The fromTankIds to add.
     * @return This builder for chaining.
     */
    public Builder addAllFromTankIds(java.lang.Iterable<? extends java.lang.Long> values) {
      ensureFromTankIdsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(values, fromTankIds_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 fromTankIds = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearFromTankIds() {
      fromTankIds_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }

    private long toTankId_;
    /**
     * <code>int64 toTankId = 2;</code>
     *
     * @return The toTankId.
     */
    public long getToTankId() {
      return toTankId_;
    }
    /**
     * <code>int64 toTankId = 2;</code>
     *
     * @param value The toTankId to set.
     * @return This builder for chaining.
     */
    public Builder setToTankId(long value) {

      toTankId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 toTankId = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearToTankId() {

      toTankId_ = 0L;
      onChanged();
      return this;
    }

    private int timeStart_;
    /**
     * <code>int32 timeStart = 3;</code>
     *
     * @return The timeStart.
     */
    public int getTimeStart() {
      return timeStart_;
    }
    /**
     * <code>int32 timeStart = 3;</code>
     *
     * @param value The timeStart to set.
     * @return This builder for chaining.
     */
    public Builder setTimeStart(int value) {

      timeStart_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 timeStart = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTimeStart() {

      timeStart_ = 0;
      onChanged();
      return this;
    }

    private int timeEnd_;
    /**
     * <code>int32 timeEnd = 4;</code>
     *
     * @return The timeEnd.
     */
    public int getTimeEnd() {
      return timeEnd_;
    }
    /**
     * <code>int32 timeEnd = 4;</code>
     *
     * @param value The timeEnd to set.
     * @return This builder for chaining.
     */
    public Builder setTimeEnd(int value) {

      timeEnd_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 timeEnd = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTimeEnd() {

      timeEnd_ = 0;
      onChanged();
      return this;
    }

    private long cargoNominationId_;
    /**
     * <code>int64 cargoNominationId = 5;</code>
     *
     * @return The cargoNominationId.
     */
    public long getCargoNominationId() {
      return cargoNominationId_;
    }
    /**
     * <code>int64 cargoNominationId = 5;</code>
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
     * <code>int64 cargoNominationId = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCargoNominationId() {

      cargoNominationId_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object purpose_ = "";
    /**
     * <code>string purpose = 6;</code>
     *
     * @return The purpose.
     */
    public java.lang.String getPurpose() {
      java.lang.Object ref = purpose_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        purpose_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string purpose = 6;</code>
     *
     * @return The bytes for purpose.
     */
    public com.google.protobuf.ByteString getPurposeBytes() {
      java.lang.Object ref = purpose_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        purpose_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string purpose = 6;</code>
     *
     * @param value The purpose to set.
     * @return This builder for chaining.
     */
    public Builder setPurpose(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      purpose_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string purpose = 6;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPurpose() {

      purpose_ = getDefaultInstance().getPurpose();
      onChanged();
      return this;
    }
    /**
     * <code>string purpose = 6;</code>
     *
     * @param value The bytes for purpose to set.
     * @return This builder for chaining.
     */
    public Builder setPurposeBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      purpose_ = value;
      onChanged();
      return this;
    }

    private java.util.List<com.cpdss.common.generated.discharge_plan.TankTransferDetail>
        tankTransferDetails_ = java.util.Collections.emptyList();

    private void ensureTankTransferDetailsIsMutable() {
      if (!((bitField0_ & 0x00000002) != 0)) {
        tankTransferDetails_ =
            new java.util.ArrayList<com.cpdss.common.generated.discharge_plan.TankTransferDetail>(
                tankTransferDetails_);
        bitField0_ |= 0x00000002;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.TankTransferDetail,
            com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder,
            com.cpdss.common.generated.discharge_plan.TankTransferDetailOrBuilder>
        tankTransferDetailsBuilder_;

    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.TankTransferDetail>
        getTankTransferDetailsList() {
      if (tankTransferDetailsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(tankTransferDetails_);
      } else {
        return tankTransferDetailsBuilder_.getMessageList();
      }
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public int getTankTransferDetailsCount() {
      if (tankTransferDetailsBuilder_ == null) {
        return tankTransferDetails_.size();
      } else {
        return tankTransferDetailsBuilder_.getCount();
      }
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public com.cpdss.common.generated.discharge_plan.TankTransferDetail getTankTransferDetails(
        int index) {
      if (tankTransferDetailsBuilder_ == null) {
        return tankTransferDetails_.get(index);
      } else {
        return tankTransferDetailsBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public Builder setTankTransferDetails(
        int index, com.cpdss.common.generated.discharge_plan.TankTransferDetail value) {
      if (tankTransferDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTankTransferDetailsIsMutable();
        tankTransferDetails_.set(index, value);
        onChanged();
      } else {
        tankTransferDetailsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public Builder setTankTransferDetails(
        int index,
        com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder builderForValue) {
      if (tankTransferDetailsBuilder_ == null) {
        ensureTankTransferDetailsIsMutable();
        tankTransferDetails_.set(index, builderForValue.build());
        onChanged();
      } else {
        tankTransferDetailsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public Builder addTankTransferDetails(
        com.cpdss.common.generated.discharge_plan.TankTransferDetail value) {
      if (tankTransferDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTankTransferDetailsIsMutable();
        tankTransferDetails_.add(value);
        onChanged();
      } else {
        tankTransferDetailsBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public Builder addTankTransferDetails(
        int index, com.cpdss.common.generated.discharge_plan.TankTransferDetail value) {
      if (tankTransferDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTankTransferDetailsIsMutable();
        tankTransferDetails_.add(index, value);
        onChanged();
      } else {
        tankTransferDetailsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public Builder addTankTransferDetails(
        com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder builderForValue) {
      if (tankTransferDetailsBuilder_ == null) {
        ensureTankTransferDetailsIsMutable();
        tankTransferDetails_.add(builderForValue.build());
        onChanged();
      } else {
        tankTransferDetailsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public Builder addTankTransferDetails(
        int index,
        com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder builderForValue) {
      if (tankTransferDetailsBuilder_ == null) {
        ensureTankTransferDetailsIsMutable();
        tankTransferDetails_.add(index, builderForValue.build());
        onChanged();
      } else {
        tankTransferDetailsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public Builder addAllTankTransferDetails(
        java.lang.Iterable<? extends com.cpdss.common.generated.discharge_plan.TankTransferDetail>
            values) {
      if (tankTransferDetailsBuilder_ == null) {
        ensureTankTransferDetailsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, tankTransferDetails_);
        onChanged();
      } else {
        tankTransferDetailsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public Builder clearTankTransferDetails() {
      if (tankTransferDetailsBuilder_ == null) {
        tankTransferDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        tankTransferDetailsBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public Builder removeTankTransferDetails(int index) {
      if (tankTransferDetailsBuilder_ == null) {
        ensureTankTransferDetailsIsMutable();
        tankTransferDetails_.remove(index);
        onChanged();
      } else {
        tankTransferDetailsBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder
        getTankTransferDetailsBuilder(int index) {
      return getTankTransferDetailsFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public com.cpdss.common.generated.discharge_plan.TankTransferDetailOrBuilder
        getTankTransferDetailsOrBuilder(int index) {
      if (tankTransferDetailsBuilder_ == null) {
        return tankTransferDetails_.get(index);
      } else {
        return tankTransferDetailsBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public java.util.List<
            ? extends com.cpdss.common.generated.discharge_plan.TankTransferDetailOrBuilder>
        getTankTransferDetailsOrBuilderList() {
      if (tankTransferDetailsBuilder_ != null) {
        return tankTransferDetailsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(tankTransferDetails_);
      }
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder
        addTankTransferDetailsBuilder() {
      return getTankTransferDetailsFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.discharge_plan.TankTransferDetail.getDefaultInstance());
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder
        addTankTransferDetailsBuilder(int index) {
      return getTankTransferDetailsFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.discharge_plan.TankTransferDetail.getDefaultInstance());
    }
    /** <code>repeated .TankTransferDetail tankTransferDetails = 7;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder>
        getTankTransferDetailsBuilderList() {
      return getTankTransferDetailsFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.TankTransferDetail,
            com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder,
            com.cpdss.common.generated.discharge_plan.TankTransferDetailOrBuilder>
        getTankTransferDetailsFieldBuilder() {
      if (tankTransferDetailsBuilder_ == null) {
        tankTransferDetailsBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.TankTransferDetail,
                com.cpdss.common.generated.discharge_plan.TankTransferDetail.Builder,
                com.cpdss.common.generated.discharge_plan.TankTransferDetailOrBuilder>(
                tankTransferDetails_,
                ((bitField0_ & 0x00000002) != 0),
                getParentForChildren(),
                isClean());
        tankTransferDetails_ = null;
      }
      return tankTransferDetailsBuilder_;
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

    // @@protoc_insertion_point(builder_scope:TankTransfer)
  }

  // @@protoc_insertion_point(class_scope:TankTransfer)
  private static final com.cpdss.common.generated.discharge_plan.TankTransfer DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.TankTransfer();
  }

  public static com.cpdss.common.generated.discharge_plan.TankTransfer getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<TankTransfer> PARSER =
      new com.google.protobuf.AbstractParser<TankTransfer>() {
        @java.lang.Override
        public TankTransfer parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new TankTransfer(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<TankTransfer> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<TankTransfer> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.TankTransfer getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
