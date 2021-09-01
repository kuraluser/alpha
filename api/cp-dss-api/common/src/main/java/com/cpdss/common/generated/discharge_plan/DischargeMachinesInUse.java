/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargeMachinesInUse} */
public final class DischargeMachinesInUse extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargeMachinesInUse)
    DischargeMachinesInUseOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargeMachinesInUse.newBuilder() to construct.
  private DischargeMachinesInUse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargeMachinesInUse() {
    capacity_ = "";
    machineType_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargeMachinesInUse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargeMachinesInUse(
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
              dischargeInfoId_ = input.readInt64();
              break;
            }
          case 24:
            {
              machineId_ = input.readInt64();
              break;
            }
          case 34:
            {
              java.lang.String s = input.readStringRequireUtf8();

              capacity_ = s;
              break;
            }
          case 40:
            {
              isUsing_ = input.readBool();
              break;
            }
          case 48:
            {
              int rawValue = input.readEnum();

              machineType_ = rawValue;
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
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeMachinesInUse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeMachinesInUse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse.class,
            com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse.Builder.class);
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

  public static final int MACHINEID_FIELD_NUMBER = 3;
  private long machineId_;
  /**
   * <code>int64 machineId = 3;</code>
   *
   * @return The machineId.
   */
  public long getMachineId() {
    return machineId_;
  }

  public static final int CAPACITY_FIELD_NUMBER = 4;
  private volatile java.lang.Object capacity_;
  /**
   * <code>string capacity = 4;</code>
   *
   * @return The capacity.
   */
  public java.lang.String getCapacity() {
    java.lang.Object ref = capacity_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      capacity_ = s;
      return s;
    }
  }
  /**
   * <code>string capacity = 4;</code>
   *
   * @return The bytes for capacity.
   */
  public com.google.protobuf.ByteString getCapacityBytes() {
    java.lang.Object ref = capacity_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      capacity_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ISUSING_FIELD_NUMBER = 5;
  private boolean isUsing_;
  /**
   * <code>bool isUsing = 5;</code>
   *
   * @return The isUsing.
   */
  public boolean getIsUsing() {
    return isUsing_;
  }

  public static final int MACHINETYPE_FIELD_NUMBER = 6;
  private int machineType_;
  /**
   * <code>.MachineType machineType = 6;</code>
   *
   * @return The enum numeric value on the wire for machineType.
   */
  public int getMachineTypeValue() {
    return machineType_;
  }
  /**
   * <code>.MachineType machineType = 6;</code>
   *
   * @return The machineType.
   */
  public com.cpdss.common.generated.Common.MachineType getMachineType() {
    @SuppressWarnings("deprecation")
    com.cpdss.common.generated.Common.MachineType result =
        com.cpdss.common.generated.Common.MachineType.valueOf(machineType_);
    return result == null ? com.cpdss.common.generated.Common.MachineType.UNRECOGNIZED : result;
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
    if (dischargeInfoId_ != 0L) {
      output.writeInt64(2, dischargeInfoId_);
    }
    if (machineId_ != 0L) {
      output.writeInt64(3, machineId_);
    }
    if (!getCapacityBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, capacity_);
    }
    if (isUsing_ != false) {
      output.writeBool(5, isUsing_);
    }
    if (machineType_ != com.cpdss.common.generated.Common.MachineType.EMPTY.getNumber()) {
      output.writeEnum(6, machineType_);
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
    if (machineId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, machineId_);
    }
    if (!getCapacityBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, capacity_);
    }
    if (isUsing_ != false) {
      size += com.google.protobuf.CodedOutputStream.computeBoolSize(5, isUsing_);
    }
    if (machineType_ != com.cpdss.common.generated.Common.MachineType.EMPTY.getNumber()) {
      size += com.google.protobuf.CodedOutputStream.computeEnumSize(6, machineType_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse other =
        (com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse) obj;

    if (getId() != other.getId()) return false;
    if (getDischargeInfoId() != other.getDischargeInfoId()) return false;
    if (getMachineId() != other.getMachineId()) return false;
    if (!getCapacity().equals(other.getCapacity())) return false;
    if (getIsUsing() != other.getIsUsing()) return false;
    if (machineType_ != other.machineType_) return false;
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
    hash = (37 * hash) + MACHINEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getMachineId());
    hash = (37 * hash) + CAPACITY_FIELD_NUMBER;
    hash = (53 * hash) + getCapacity().hashCode();
    hash = (37 * hash) + ISUSING_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsUsing());
    hash = (37 * hash) + MACHINETYPE_FIELD_NUMBER;
    hash = (53 * hash) + machineType_;
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseFrom(
      byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse prototype) {
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
  /** Protobuf type {@code DischargeMachinesInUse} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargeMachinesInUse)
      com.cpdss.common.generated.discharge_plan.DischargeMachinesInUseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeMachinesInUse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeMachinesInUse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse.class,
              com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse.newBuilder()
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

      machineId_ = 0L;

      capacity_ = "";

      isUsing_ = false;

      machineType_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeMachinesInUse_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse build() {
      com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse result =
          new com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse(this);
      result.id_ = id_;
      result.dischargeInfoId_ = dischargeInfoId_;
      result.machineId_ = machineId_;
      result.capacity_ = capacity_;
      result.isUsing_ = isUsing_;
      result.machineType_ = machineType_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse.getDefaultInstance())
        return this;
      if (other.getId() != 0L) {
        setId(other.getId());
      }
      if (other.getDischargeInfoId() != 0L) {
        setDischargeInfoId(other.getDischargeInfoId());
      }
      if (other.getMachineId() != 0L) {
        setMachineId(other.getMachineId());
      }
      if (!other.getCapacity().isEmpty()) {
        capacity_ = other.capacity_;
        onChanged();
      }
      if (other.getIsUsing() != false) {
        setIsUsing(other.getIsUsing());
      }
      if (other.machineType_ != 0) {
        setMachineTypeValue(other.getMachineTypeValue());
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
      com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse)
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

    private long machineId_;
    /**
     * <code>int64 machineId = 3;</code>
     *
     * @return The machineId.
     */
    public long getMachineId() {
      return machineId_;
    }
    /**
     * <code>int64 machineId = 3;</code>
     *
     * @param value The machineId to set.
     * @return This builder for chaining.
     */
    public Builder setMachineId(long value) {

      machineId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 machineId = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearMachineId() {

      machineId_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object capacity_ = "";
    /**
     * <code>string capacity = 4;</code>
     *
     * @return The capacity.
     */
    public java.lang.String getCapacity() {
      java.lang.Object ref = capacity_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        capacity_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string capacity = 4;</code>
     *
     * @return The bytes for capacity.
     */
    public com.google.protobuf.ByteString getCapacityBytes() {
      java.lang.Object ref = capacity_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        capacity_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string capacity = 4;</code>
     *
     * @param value The capacity to set.
     * @return This builder for chaining.
     */
    public Builder setCapacity(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      capacity_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string capacity = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCapacity() {

      capacity_ = getDefaultInstance().getCapacity();
      onChanged();
      return this;
    }
    /**
     * <code>string capacity = 4;</code>
     *
     * @param value The bytes for capacity to set.
     * @return This builder for chaining.
     */
    public Builder setCapacityBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      capacity_ = value;
      onChanged();
      return this;
    }

    private boolean isUsing_;
    /**
     * <code>bool isUsing = 5;</code>
     *
     * @return The isUsing.
     */
    public boolean getIsUsing() {
      return isUsing_;
    }
    /**
     * <code>bool isUsing = 5;</code>
     *
     * @param value The isUsing to set.
     * @return This builder for chaining.
     */
    public Builder setIsUsing(boolean value) {

      isUsing_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool isUsing = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearIsUsing() {

      isUsing_ = false;
      onChanged();
      return this;
    }

    private int machineType_ = 0;
    /**
     * <code>.MachineType machineType = 6;</code>
     *
     * @return The enum numeric value on the wire for machineType.
     */
    public int getMachineTypeValue() {
      return machineType_;
    }
    /**
     * <code>.MachineType machineType = 6;</code>
     *
     * @param value The enum numeric value on the wire for machineType to set.
     * @return This builder for chaining.
     */
    public Builder setMachineTypeValue(int value) {
      machineType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.MachineType machineType = 6;</code>
     *
     * @return The machineType.
     */
    public com.cpdss.common.generated.Common.MachineType getMachineType() {
      @SuppressWarnings("deprecation")
      com.cpdss.common.generated.Common.MachineType result =
          com.cpdss.common.generated.Common.MachineType.valueOf(machineType_);
      return result == null ? com.cpdss.common.generated.Common.MachineType.UNRECOGNIZED : result;
    }
    /**
     * <code>.MachineType machineType = 6;</code>
     *
     * @param value The machineType to set.
     * @return This builder for chaining.
     */
    public Builder setMachineType(com.cpdss.common.generated.Common.MachineType value) {
      if (value == null) {
        throw new NullPointerException();
      }

      machineType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.MachineType machineType = 6;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearMachineType() {

      machineType_ = 0;
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

    // @@protoc_insertion_point(builder_scope:DischargeMachinesInUse)
  }

  // @@protoc_insertion_point(class_scope:DischargeMachinesInUse)
  private static final com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargeMachinesInUse> PARSER =
      new com.google.protobuf.AbstractParser<DischargeMachinesInUse>() {
        @java.lang.Override
        public DischargeMachinesInUse parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargeMachinesInUse(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargeMachinesInUse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargeMachinesInUse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargeMachinesInUse
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
