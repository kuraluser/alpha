/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code CargoForCow} */
public final class CargoForCow extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:CargoForCow)
    CargoForCowOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use CargoForCow.newBuilder() to construct.
  private CargoForCow(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private CargoForCow() {
    tankIds_ = emptyLongList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new CargoForCow();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private CargoForCow(
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
              cargoId_ = input.readInt64();
              break;
            }
          case 16:
            {
              cargoNominationId_ = input.readInt64();
              break;
            }
          case 24:
            {
              washingCargoId_ = input.readInt64();
              break;
            }
          case 32:
            {
              washingCargoNominationId_ = input.readInt64();
              break;
            }
          case 40:
            {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                tankIds_ = newLongList();
                mutable_bitField0_ |= 0x00000001;
              }
              tankIds_.addLong(input.readInt64());
              break;
            }
          case 42:
            {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
                tankIds_ = newLongList();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                tankIds_.addLong(input.readInt64());
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
        tankIds_.makeImmutable(); // C
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_CargoForCow_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_CargoForCow_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.CargoForCow.class,
            com.cpdss.common.generated.discharge_plan.CargoForCow.Builder.class);
  }

  public static final int CARGOID_FIELD_NUMBER = 1;
  private long cargoId_;
  /**
   * <code>int64 cargoId = 1;</code>
   *
   * @return The cargoId.
   */
  public long getCargoId() {
    return cargoId_;
  }

  public static final int CARGONOMINATIONID_FIELD_NUMBER = 2;
  private long cargoNominationId_;
  /**
   * <code>int64 cargoNominationId = 2;</code>
   *
   * @return The cargoNominationId.
   */
  public long getCargoNominationId() {
    return cargoNominationId_;
  }

  public static final int WASHINGCARGOID_FIELD_NUMBER = 3;
  private long washingCargoId_;
  /**
   * <code>int64 washingCargoId = 3;</code>
   *
   * @return The washingCargoId.
   */
  public long getWashingCargoId() {
    return washingCargoId_;
  }

  public static final int WASHINGCARGONOMINATIONID_FIELD_NUMBER = 4;
  private long washingCargoNominationId_;
  /**
   * <code>int64 washingCargoNominationId = 4;</code>
   *
   * @return The washingCargoNominationId.
   */
  public long getWashingCargoNominationId() {
    return washingCargoNominationId_;
  }

  public static final int TANKIDS_FIELD_NUMBER = 5;
  private com.google.protobuf.Internal.LongList tankIds_;
  /**
   * <code>repeated int64 tankIds = 5;</code>
   *
   * @return A list containing the tankIds.
   */
  public java.util.List<java.lang.Long> getTankIdsList() {
    return tankIds_;
  }
  /**
   * <code>repeated int64 tankIds = 5;</code>
   *
   * @return The count of tankIds.
   */
  public int getTankIdsCount() {
    return tankIds_.size();
  }
  /**
   * <code>repeated int64 tankIds = 5;</code>
   *
   * @param index The index of the element to return.
   * @return The tankIds at the given index.
   */
  public long getTankIds(int index) {
    return tankIds_.getLong(index);
  }

  private int tankIdsMemoizedSerializedSize = -1;

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
    if (cargoId_ != 0L) {
      output.writeInt64(1, cargoId_);
    }
    if (cargoNominationId_ != 0L) {
      output.writeInt64(2, cargoNominationId_);
    }
    if (washingCargoId_ != 0L) {
      output.writeInt64(3, washingCargoId_);
    }
    if (washingCargoNominationId_ != 0L) {
      output.writeInt64(4, washingCargoNominationId_);
    }
    if (getTankIdsList().size() > 0) {
      output.writeUInt32NoTag(42);
      output.writeUInt32NoTag(tankIdsMemoizedSerializedSize);
    }
    for (int i = 0; i < tankIds_.size(); i++) {
      output.writeInt64NoTag(tankIds_.getLong(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (cargoId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, cargoId_);
    }
    if (cargoNominationId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, cargoNominationId_);
    }
    if (washingCargoId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, washingCargoId_);
    }
    if (washingCargoNominationId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, washingCargoNominationId_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < tankIds_.size(); i++) {
        dataSize +=
            com.google.protobuf.CodedOutputStream.computeInt64SizeNoTag(tankIds_.getLong(i));
      }
      size += dataSize;
      if (!getTankIdsList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream.computeInt32SizeNoTag(dataSize);
      }
      tankIdsMemoizedSerializedSize = dataSize;
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.CargoForCow)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.CargoForCow other =
        (com.cpdss.common.generated.discharge_plan.CargoForCow) obj;

    if (getCargoId() != other.getCargoId()) return false;
    if (getCargoNominationId() != other.getCargoNominationId()) return false;
    if (getWashingCargoId() != other.getWashingCargoId()) return false;
    if (getWashingCargoNominationId() != other.getWashingCargoNominationId()) return false;
    if (!getTankIdsList().equals(other.getTankIdsList())) return false;
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
    hash = (37 * hash) + CARGOID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoId());
    hash = (37 * hash) + CARGONOMINATIONID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoNominationId());
    hash = (37 * hash) + WASHINGCARGOID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getWashingCargoId());
    hash = (37 * hash) + WASHINGCARGONOMINATIONID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getWashingCargoNominationId());
    if (getTankIdsCount() > 0) {
      hash = (37 * hash) + TANKIDS_FIELD_NUMBER;
      hash = (53 * hash) + getTankIdsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow parseFrom(
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
      com.cpdss.common.generated.discharge_plan.CargoForCow prototype) {
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
  /** Protobuf type {@code CargoForCow} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:CargoForCow)
      com.cpdss.common.generated.discharge_plan.CargoForCowOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CargoForCow_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CargoForCow_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.CargoForCow.class,
              com.cpdss.common.generated.discharge_plan.CargoForCow.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.CargoForCow.newBuilder()
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
      cargoId_ = 0L;

      cargoNominationId_ = 0L;

      washingCargoId_ = 0L;

      washingCargoNominationId_ = 0L;

      tankIds_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CargoForCow_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CargoForCow getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.CargoForCow.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CargoForCow build() {
      com.cpdss.common.generated.discharge_plan.CargoForCow result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CargoForCow buildPartial() {
      com.cpdss.common.generated.discharge_plan.CargoForCow result =
          new com.cpdss.common.generated.discharge_plan.CargoForCow(this);
      int from_bitField0_ = bitField0_;
      result.cargoId_ = cargoId_;
      result.cargoNominationId_ = cargoNominationId_;
      result.washingCargoId_ = washingCargoId_;
      result.washingCargoNominationId_ = washingCargoNominationId_;
      if (((bitField0_ & 0x00000001) != 0)) {
        tankIds_.makeImmutable();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.tankIds_ = tankIds_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.CargoForCow) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.CargoForCow) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.CargoForCow other) {
      if (other == com.cpdss.common.generated.discharge_plan.CargoForCow.getDefaultInstance())
        return this;
      if (other.getCargoId() != 0L) {
        setCargoId(other.getCargoId());
      }
      if (other.getCargoNominationId() != 0L) {
        setCargoNominationId(other.getCargoNominationId());
      }
      if (other.getWashingCargoId() != 0L) {
        setWashingCargoId(other.getWashingCargoId());
      }
      if (other.getWashingCargoNominationId() != 0L) {
        setWashingCargoNominationId(other.getWashingCargoNominationId());
      }
      if (!other.tankIds_.isEmpty()) {
        if (tankIds_.isEmpty()) {
          tankIds_ = other.tankIds_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureTankIdsIsMutable();
          tankIds_.addAll(other.tankIds_);
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
      com.cpdss.common.generated.discharge_plan.CargoForCow parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.CargoForCow) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int bitField0_;

    private long cargoId_;
    /**
     * <code>int64 cargoId = 1;</code>
     *
     * @return The cargoId.
     */
    public long getCargoId() {
      return cargoId_;
    }
    /**
     * <code>int64 cargoId = 1;</code>
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
     * <code>int64 cargoId = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCargoId() {

      cargoId_ = 0L;
      onChanged();
      return this;
    }

    private long cargoNominationId_;
    /**
     * <code>int64 cargoNominationId = 2;</code>
     *
     * @return The cargoNominationId.
     */
    public long getCargoNominationId() {
      return cargoNominationId_;
    }
    /**
     * <code>int64 cargoNominationId = 2;</code>
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
     * <code>int64 cargoNominationId = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCargoNominationId() {

      cargoNominationId_ = 0L;
      onChanged();
      return this;
    }

    private long washingCargoId_;
    /**
     * <code>int64 washingCargoId = 3;</code>
     *
     * @return The washingCargoId.
     */
    public long getWashingCargoId() {
      return washingCargoId_;
    }
    /**
     * <code>int64 washingCargoId = 3;</code>
     *
     * @param value The washingCargoId to set.
     * @return This builder for chaining.
     */
    public Builder setWashingCargoId(long value) {

      washingCargoId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 washingCargoId = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearWashingCargoId() {

      washingCargoId_ = 0L;
      onChanged();
      return this;
    }

    private long washingCargoNominationId_;
    /**
     * <code>int64 washingCargoNominationId = 4;</code>
     *
     * @return The washingCargoNominationId.
     */
    public long getWashingCargoNominationId() {
      return washingCargoNominationId_;
    }
    /**
     * <code>int64 washingCargoNominationId = 4;</code>
     *
     * @param value The washingCargoNominationId to set.
     * @return This builder for chaining.
     */
    public Builder setWashingCargoNominationId(long value) {

      washingCargoNominationId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 washingCargoNominationId = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearWashingCargoNominationId() {

      washingCargoNominationId_ = 0L;
      onChanged();
      return this;
    }

    private com.google.protobuf.Internal.LongList tankIds_ = emptyLongList();

    private void ensureTankIdsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        tankIds_ = mutableCopy(tankIds_);
        bitField0_ |= 0x00000001;
      }
    }
    /**
     * <code>repeated int64 tankIds = 5;</code>
     *
     * @return A list containing the tankIds.
     */
    public java.util.List<java.lang.Long> getTankIdsList() {
      return ((bitField0_ & 0x00000001) != 0)
          ? java.util.Collections.unmodifiableList(tankIds_)
          : tankIds_;
    }
    /**
     * <code>repeated int64 tankIds = 5;</code>
     *
     * @return The count of tankIds.
     */
    public int getTankIdsCount() {
      return tankIds_.size();
    }
    /**
     * <code>repeated int64 tankIds = 5;</code>
     *
     * @param index The index of the element to return.
     * @return The tankIds at the given index.
     */
    public long getTankIds(int index) {
      return tankIds_.getLong(index);
    }
    /**
     * <code>repeated int64 tankIds = 5;</code>
     *
     * @param index The index to set the value at.
     * @param value The tankIds to set.
     * @return This builder for chaining.
     */
    public Builder setTankIds(int index, long value) {
      ensureTankIdsIsMutable();
      tankIds_.setLong(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 tankIds = 5;</code>
     *
     * @param value The tankIds to add.
     * @return This builder for chaining.
     */
    public Builder addTankIds(long value) {
      ensureTankIdsIsMutable();
      tankIds_.addLong(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 tankIds = 5;</code>
     *
     * @param values The tankIds to add.
     * @return This builder for chaining.
     */
    public Builder addAllTankIds(java.lang.Iterable<? extends java.lang.Long> values) {
      ensureTankIdsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(values, tankIds_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 tankIds = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTankIds() {
      tankIds_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
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

    // @@protoc_insertion_point(builder_scope:CargoForCow)
  }

  // @@protoc_insertion_point(class_scope:CargoForCow)
  private static final com.cpdss.common.generated.discharge_plan.CargoForCow DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.CargoForCow();
  }

  public static com.cpdss.common.generated.discharge_plan.CargoForCow getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CargoForCow> PARSER =
      new com.google.protobuf.AbstractParser<CargoForCow>() {
        @java.lang.Override
        public CargoForCow parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new CargoForCow(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<CargoForCow> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CargoForCow> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.CargoForCow getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
