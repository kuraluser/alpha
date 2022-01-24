/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargeDelays} */
public final class DischargeDelays extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargeDelays)
    DischargeDelaysOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargeDelays.newBuilder() to construct.
  private DischargeDelays(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargeDelays() {
    reasonForDelayIds_ = emptyLongList();
    duration_ = "";
    quantity_ = "";
    dischargingRate_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargeDelays();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargeDelays(
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
          case 16:
            {
              dischargeInfoId_ = input.readInt64();
              break;
            }
          case 24:
            {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                reasonForDelayIds_ = newLongList();
                mutable_bitField0_ |= 0x00000001;
              }
              reasonForDelayIds_.addLong(input.readInt64());
              break;
            }
          case 26:
            {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
                reasonForDelayIds_ = newLongList();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                reasonForDelayIds_.addLong(input.readInt64());
              }
              input.popLimit(limit);
              break;
            }
          case 34:
            {
              java.lang.String s = input.readStringRequireUtf8();

              duration_ = s;
              break;
            }
          case 40:
            {
              cargoId_ = input.readInt64();
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
              cargoNominationId_ = input.readInt64();
              break;
            }
          case 64:
            {
              sequenceNo_ = input.readInt64();
              break;
            }
          case 74:
            {
              java.lang.String s = input.readStringRequireUtf8();

              dischargingRate_ = s;
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
        reasonForDelayIds_.makeImmutable(); // C
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeDelays_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeDelays_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargeDelays.class,
            com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder.class);
  }

  public static final int ID_FIELD_NUMBER = 1;
  private long id_;
  /**
   *
   *
   * <pre>
   * primary key
   * </pre>
   *
   * <code>int64 id = 1;</code>
   *
   * @return The id.
   */
  public long getId() {
    return id_;
  }

  public static final int DISCHARGEINFOID_FIELD_NUMBER = 2;
  private long dischargeInfoId_;
  /**
   * <code>int64 dischargeInfoId = 2;</code>
   *
   * @return The dischargeInfoId.
   */
  public long getDischargeInfoId() {
    return dischargeInfoId_;
  }

  public static final int REASONFORDELAYIDS_FIELD_NUMBER = 3;
  private com.google.protobuf.Internal.LongList reasonForDelayIds_;
  /**
   * <code>repeated int64 reasonForDelayIds = 3;</code>
   *
   * @return A list containing the reasonForDelayIds.
   */
  public java.util.List<java.lang.Long> getReasonForDelayIdsList() {
    return reasonForDelayIds_;
  }
  /**
   * <code>repeated int64 reasonForDelayIds = 3;</code>
   *
   * @return The count of reasonForDelayIds.
   */
  public int getReasonForDelayIdsCount() {
    return reasonForDelayIds_.size();
  }
  /**
   * <code>repeated int64 reasonForDelayIds = 3;</code>
   *
   * @param index The index of the element to return.
   * @return The reasonForDelayIds at the given index.
   */
  public long getReasonForDelayIds(int index) {
    return reasonForDelayIds_.getLong(index);
  }

  private int reasonForDelayIdsMemoizedSerializedSize = -1;

  public static final int DURATION_FIELD_NUMBER = 4;
  private volatile java.lang.Object duration_;
  /**
   * <code>string duration = 4;</code>
   *
   * @return The duration.
   */
  public java.lang.String getDuration() {
    java.lang.Object ref = duration_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      duration_ = s;
      return s;
    }
  }
  /**
   * <code>string duration = 4;</code>
   *
   * @return The bytes for duration.
   */
  public com.google.protobuf.ByteString getDurationBytes() {
    java.lang.Object ref = duration_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      duration_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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

  public static final int CARGONOMINATIONID_FIELD_NUMBER = 7;
  private long cargoNominationId_;
  /**
   * <code>int64 cargoNominationId = 7;</code>
   *
   * @return The cargoNominationId.
   */
  public long getCargoNominationId() {
    return cargoNominationId_;
  }

  public static final int SEQUENCENO_FIELD_NUMBER = 8;
  private long sequenceNo_;
  /**
   * <code>int64 sequenceNo = 8;</code>
   *
   * @return The sequenceNo.
   */
  public long getSequenceNo() {
    return sequenceNo_;
  }

  public static final int DISCHARGINGRATE_FIELD_NUMBER = 9;
  private volatile java.lang.Object dischargingRate_;
  /**
   * <code>string dischargingRate = 9;</code>
   *
   * @return The dischargingRate.
   */
  public java.lang.String getDischargingRate() {
    java.lang.Object ref = dischargingRate_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      dischargingRate_ = s;
      return s;
    }
  }
  /**
   * <code>string dischargingRate = 9;</code>
   *
   * @return The bytes for dischargingRate.
   */
  public com.google.protobuf.ByteString getDischargingRateBytes() {
    java.lang.Object ref = dischargingRate_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      dischargingRate_ = b;
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
    getSerializedSize();
    if (id_ != 0L) {
      output.writeInt64(1, id_);
    }
    if (dischargeInfoId_ != 0L) {
      output.writeInt64(2, dischargeInfoId_);
    }
    if (getReasonForDelayIdsList().size() > 0) {
      output.writeUInt32NoTag(26);
      output.writeUInt32NoTag(reasonForDelayIdsMemoizedSerializedSize);
    }
    for (int i = 0; i < reasonForDelayIds_.size(); i++) {
      output.writeInt64NoTag(reasonForDelayIds_.getLong(i));
    }
    if (!getDurationBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, duration_);
    }
    if (cargoId_ != 0L) {
      output.writeInt64(5, cargoId_);
    }
    if (!getQuantityBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, quantity_);
    }
    if (cargoNominationId_ != 0L) {
      output.writeInt64(7, cargoNominationId_);
    }
    if (sequenceNo_ != 0L) {
      output.writeInt64(8, sequenceNo_);
    }
    if (!getDischargingRateBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 9, dischargingRate_);
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
    if (dischargeInfoId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, dischargeInfoId_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < reasonForDelayIds_.size(); i++) {
        dataSize +=
            com.google.protobuf.CodedOutputStream.computeInt64SizeNoTag(
                reasonForDelayIds_.getLong(i));
      }
      size += dataSize;
      if (!getReasonForDelayIdsList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream.computeInt32SizeNoTag(dataSize);
      }
      reasonForDelayIdsMemoizedSerializedSize = dataSize;
    }
    if (!getDurationBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, duration_);
    }
    if (cargoId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(5, cargoId_);
    }
    if (!getQuantityBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, quantity_);
    }
    if (cargoNominationId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(7, cargoNominationId_);
    }
    if (sequenceNo_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(8, sequenceNo_);
    }
    if (!getDischargingRateBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, dischargingRate_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargeDelays)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargeDelays other =
        (com.cpdss.common.generated.discharge_plan.DischargeDelays) obj;

    if (getId() != other.getId()) return false;
    if (getDischargeInfoId() != other.getDischargeInfoId()) return false;
    if (!getReasonForDelayIdsList().equals(other.getReasonForDelayIdsList())) return false;
    if (!getDuration().equals(other.getDuration())) return false;
    if (getCargoId() != other.getCargoId()) return false;
    if (!getQuantity().equals(other.getQuantity())) return false;
    if (getCargoNominationId() != other.getCargoNominationId()) return false;
    if (getSequenceNo() != other.getSequenceNo()) return false;
    if (!getDischargingRate().equals(other.getDischargingRate())) return false;
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
    hash = (37 * hash) + DISCHARGEINFOID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargeInfoId());
    if (getReasonForDelayIdsCount() > 0) {
      hash = (37 * hash) + REASONFORDELAYIDS_FIELD_NUMBER;
      hash = (53 * hash) + getReasonForDelayIdsList().hashCode();
    }
    hash = (37 * hash) + DURATION_FIELD_NUMBER;
    hash = (53 * hash) + getDuration().hashCode();
    hash = (37 * hash) + CARGOID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoId());
    hash = (37 * hash) + QUANTITY_FIELD_NUMBER;
    hash = (53 * hash) + getQuantity().hashCode();
    hash = (37 * hash) + CARGONOMINATIONID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoNominationId());
    hash = (37 * hash) + SEQUENCENO_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getSequenceNo());
    hash = (37 * hash) + DISCHARGINGRATE_FIELD_NUMBER;
    hash = (53 * hash) + getDischargingRate().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargeDelays prototype) {
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
  /** Protobuf type {@code DischargeDelays} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargeDelays)
      com.cpdss.common.generated.discharge_plan.DischargeDelaysOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeDelays_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeDelays_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargeDelays.class,
              com.cpdss.common.generated.discharge_plan.DischargeDelays.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.DischargeDelays.newBuilder()
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

      dischargeInfoId_ = 0L;

      reasonForDelayIds_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000001);
      duration_ = "";

      cargoId_ = 0L;

      quantity_ = "";

      cargoNominationId_ = 0L;

      sequenceNo_ = 0L;

      dischargingRate_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeDelays_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeDelays getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargeDelays.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeDelays build() {
      com.cpdss.common.generated.discharge_plan.DischargeDelays result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeDelays buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargeDelays result =
          new com.cpdss.common.generated.discharge_plan.DischargeDelays(this);
      int from_bitField0_ = bitField0_;
      result.id_ = id_;
      result.dischargeInfoId_ = dischargeInfoId_;
      if (((bitField0_ & 0x00000001) != 0)) {
        reasonForDelayIds_.makeImmutable();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.reasonForDelayIds_ = reasonForDelayIds_;
      result.duration_ = duration_;
      result.cargoId_ = cargoId_;
      result.quantity_ = quantity_;
      result.cargoNominationId_ = cargoNominationId_;
      result.sequenceNo_ = sequenceNo_;
      result.dischargingRate_ = dischargingRate_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargeDelays) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.DischargeDelays) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.DischargeDelays other) {
      if (other == com.cpdss.common.generated.discharge_plan.DischargeDelays.getDefaultInstance())
        return this;
      if (other.getId() != 0L) {
        setId(other.getId());
      }
      if (other.getDischargeInfoId() != 0L) {
        setDischargeInfoId(other.getDischargeInfoId());
      }
      if (!other.reasonForDelayIds_.isEmpty()) {
        if (reasonForDelayIds_.isEmpty()) {
          reasonForDelayIds_ = other.reasonForDelayIds_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureReasonForDelayIdsIsMutable();
          reasonForDelayIds_.addAll(other.reasonForDelayIds_);
        }
        onChanged();
      }
      if (!other.getDuration().isEmpty()) {
        duration_ = other.duration_;
        onChanged();
      }
      if (other.getCargoId() != 0L) {
        setCargoId(other.getCargoId());
      }
      if (!other.getQuantity().isEmpty()) {
        quantity_ = other.quantity_;
        onChanged();
      }
      if (other.getCargoNominationId() != 0L) {
        setCargoNominationId(other.getCargoNominationId());
      }
      if (other.getSequenceNo() != 0L) {
        setSequenceNo(other.getSequenceNo());
      }
      if (!other.getDischargingRate().isEmpty()) {
        dischargingRate_ = other.dischargingRate_;
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
      com.cpdss.common.generated.discharge_plan.DischargeDelays parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargeDelays) e.getUnfinishedMessage();
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
     *
     *
     * <pre>
     * primary key
     * </pre>
     *
     * <code>int64 id = 1;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
    }
    /**
     *
     *
     * <pre>
     * primary key
     * </pre>
     *
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
     *
     *
     * <pre>
     * primary key
     * </pre>
     *
     * <code>int64 id = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearId() {

      id_ = 0L;
      onChanged();
      return this;
    }

    private long dischargeInfoId_;
    /**
     * <code>int64 dischargeInfoId = 2;</code>
     *
     * @return The dischargeInfoId.
     */
    public long getDischargeInfoId() {
      return dischargeInfoId_;
    }
    /**
     * <code>int64 dischargeInfoId = 2;</code>
     *
     * @param value The dischargeInfoId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargeInfoId(long value) {

      dischargeInfoId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 dischargeInfoId = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargeInfoId() {

      dischargeInfoId_ = 0L;
      onChanged();
      return this;
    }

    private com.google.protobuf.Internal.LongList reasonForDelayIds_ = emptyLongList();

    private void ensureReasonForDelayIdsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        reasonForDelayIds_ = mutableCopy(reasonForDelayIds_);
        bitField0_ |= 0x00000001;
      }
    }
    /**
     * <code>repeated int64 reasonForDelayIds = 3;</code>
     *
     * @return A list containing the reasonForDelayIds.
     */
    public java.util.List<java.lang.Long> getReasonForDelayIdsList() {
      return ((bitField0_ & 0x00000001) != 0)
          ? java.util.Collections.unmodifiableList(reasonForDelayIds_)
          : reasonForDelayIds_;
    }
    /**
     * <code>repeated int64 reasonForDelayIds = 3;</code>
     *
     * @return The count of reasonForDelayIds.
     */
    public int getReasonForDelayIdsCount() {
      return reasonForDelayIds_.size();
    }
    /**
     * <code>repeated int64 reasonForDelayIds = 3;</code>
     *
     * @param index The index of the element to return.
     * @return The reasonForDelayIds at the given index.
     */
    public long getReasonForDelayIds(int index) {
      return reasonForDelayIds_.getLong(index);
    }
    /**
     * <code>repeated int64 reasonForDelayIds = 3;</code>
     *
     * @param index The index to set the value at.
     * @param value The reasonForDelayIds to set.
     * @return This builder for chaining.
     */
    public Builder setReasonForDelayIds(int index, long value) {
      ensureReasonForDelayIdsIsMutable();
      reasonForDelayIds_.setLong(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 reasonForDelayIds = 3;</code>
     *
     * @param value The reasonForDelayIds to add.
     * @return This builder for chaining.
     */
    public Builder addReasonForDelayIds(long value) {
      ensureReasonForDelayIdsIsMutable();
      reasonForDelayIds_.addLong(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 reasonForDelayIds = 3;</code>
     *
     * @param values The reasonForDelayIds to add.
     * @return This builder for chaining.
     */
    public Builder addAllReasonForDelayIds(java.lang.Iterable<? extends java.lang.Long> values) {
      ensureReasonForDelayIdsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(values, reasonForDelayIds_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 reasonForDelayIds = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearReasonForDelayIds() {
      reasonForDelayIds_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }

    private java.lang.Object duration_ = "";
    /**
     * <code>string duration = 4;</code>
     *
     * @return The duration.
     */
    public java.lang.String getDuration() {
      java.lang.Object ref = duration_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        duration_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string duration = 4;</code>
     *
     * @return The bytes for duration.
     */
    public com.google.protobuf.ByteString getDurationBytes() {
      java.lang.Object ref = duration_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        duration_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string duration = 4;</code>
     *
     * @param value The duration to set.
     * @return This builder for chaining.
     */
    public Builder setDuration(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      duration_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string duration = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDuration() {

      duration_ = getDefaultInstance().getDuration();
      onChanged();
      return this;
    }
    /**
     * <code>string duration = 4;</code>
     *
     * @param value The bytes for duration to set.
     * @return This builder for chaining.
     */
    public Builder setDurationBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      duration_ = value;
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

    private long cargoNominationId_;
    /**
     * <code>int64 cargoNominationId = 7;</code>
     *
     * @return The cargoNominationId.
     */
    public long getCargoNominationId() {
      return cargoNominationId_;
    }
    /**
     * <code>int64 cargoNominationId = 7;</code>
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
     * <code>int64 cargoNominationId = 7;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCargoNominationId() {

      cargoNominationId_ = 0L;
      onChanged();
      return this;
    }

    private long sequenceNo_;
    /**
     * <code>int64 sequenceNo = 8;</code>
     *
     * @return The sequenceNo.
     */
    public long getSequenceNo() {
      return sequenceNo_;
    }
    /**
     * <code>int64 sequenceNo = 8;</code>
     *
     * @param value The sequenceNo to set.
     * @return This builder for chaining.
     */
    public Builder setSequenceNo(long value) {

      sequenceNo_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 sequenceNo = 8;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearSequenceNo() {

      sequenceNo_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object dischargingRate_ = "";
    /**
     * <code>string dischargingRate = 9;</code>
     *
     * @return The dischargingRate.
     */
    public java.lang.String getDischargingRate() {
      java.lang.Object ref = dischargingRate_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        dischargingRate_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string dischargingRate = 9;</code>
     *
     * @return The bytes for dischargingRate.
     */
    public com.google.protobuf.ByteString getDischargingRateBytes() {
      java.lang.Object ref = dischargingRate_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        dischargingRate_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string dischargingRate = 9;</code>
     *
     * @param value The dischargingRate to set.
     * @return This builder for chaining.
     */
    public Builder setDischargingRate(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      dischargingRate_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string dischargingRate = 9;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargingRate() {

      dischargingRate_ = getDefaultInstance().getDischargingRate();
      onChanged();
      return this;
    }
    /**
     * <code>string dischargingRate = 9;</code>
     *
     * @param value The bytes for dischargingRate to set.
     * @return This builder for chaining.
     */
    public Builder setDischargingRateBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      dischargingRate_ = value;
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

    // @@protoc_insertion_point(builder_scope:DischargeDelays)
  }

  // @@protoc_insertion_point(class_scope:DischargeDelays)
  private static final com.cpdss.common.generated.discharge_plan.DischargeDelays DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargeDelays();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeDelays getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargeDelays> PARSER =
      new com.google.protobuf.AbstractParser<DischargeDelays>() {
        @java.lang.Override
        public DischargeDelays parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargeDelays(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargeDelays> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargeDelays> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargeDelays getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
