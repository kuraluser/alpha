/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargingPlanTankDetails} */
public final class DischargingPlanTankDetails extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargingPlanTankDetails)
    DischargingPlanTankDetailsOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargingPlanTankDetails.newBuilder() to construct.
  private DischargingPlanTankDetails(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargingPlanTankDetails() {
    api_ = "";
    quantity_ = "";
    temperature_ = "";
    ullage_ = "";
    quantityM3_ = "";
    sounding_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargingPlanTankDetails();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargingPlanTankDetails(
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

              api_ = s;
              break;
            }
          case 16:
            {
              cargoNominationId_ = input.readInt64();
              break;
            }
          case 26:
            {
              java.lang.String s = input.readStringRequireUtf8();

              quantity_ = s;
              break;
            }
          case 32:
            {
              tankId_ = input.readInt64();
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

              ullage_ = s;
              break;
            }
          case 58:
            {
              java.lang.String s = input.readStringRequireUtf8();

              quantityM3_ = s;
              break;
            }
          case 66:
            {
              java.lang.String s = input.readStringRequireUtf8();

              sounding_ = s;
              break;
            }
          case 72:
            {
              conditionType_ = input.readInt32();
              break;
            }
          case 80:
            {
              valueType_ = input.readInt32();
              break;
            }
          case 88:
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
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargingPlanTankDetails_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargingPlanTankDetails_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails.class,
            com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails.Builder.class);
  }

  public static final int API_FIELD_NUMBER = 1;
  private volatile java.lang.Object api_;
  /**
   * <code>string api = 1;</code>
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
   * <code>string api = 1;</code>
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

  public static final int QUANTITY_FIELD_NUMBER = 3;
  private volatile java.lang.Object quantity_;
  /**
   * <code>string quantity = 3;</code>
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
   * <code>string quantity = 3;</code>
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

  public static final int TANKID_FIELD_NUMBER = 4;
  private long tankId_;
  /**
   * <code>int64 tankId = 4;</code>
   *
   * @return The tankId.
   */
  public long getTankId() {
    return tankId_;
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

  public static final int ULLAGE_FIELD_NUMBER = 6;
  private volatile java.lang.Object ullage_;
  /**
   * <code>string ullage = 6;</code>
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
   * <code>string ullage = 6;</code>
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

  public static final int QUANTITYM3_FIELD_NUMBER = 7;
  private volatile java.lang.Object quantityM3_;
  /**
   * <code>string quantityM3 = 7;</code>
   *
   * @return The quantityM3.
   */
  public java.lang.String getQuantityM3() {
    java.lang.Object ref = quantityM3_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      quantityM3_ = s;
      return s;
    }
  }
  /**
   * <code>string quantityM3 = 7;</code>
   *
   * @return The bytes for quantityM3.
   */
  public com.google.protobuf.ByteString getQuantityM3Bytes() {
    java.lang.Object ref = quantityM3_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      quantityM3_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SOUNDING_FIELD_NUMBER = 8;
  private volatile java.lang.Object sounding_;
  /**
   * <code>string sounding = 8;</code>
   *
   * @return The sounding.
   */
  public java.lang.String getSounding() {
    java.lang.Object ref = sounding_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      sounding_ = s;
      return s;
    }
  }
  /**
   * <code>string sounding = 8;</code>
   *
   * @return The bytes for sounding.
   */
  public com.google.protobuf.ByteString getSoundingBytes() {
    java.lang.Object ref = sounding_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      sounding_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CONDITIONTYPE_FIELD_NUMBER = 9;
  private int conditionType_;
  /**
   * <code>int32 conditionType = 9;</code>
   *
   * @return The conditionType.
   */
  public int getConditionType() {
    return conditionType_;
  }

  public static final int VALUETYPE_FIELD_NUMBER = 10;
  private int valueType_;
  /**
   * <code>int32 valueType = 10;</code>
   *
   * @return The valueType.
   */
  public int getValueType() {
    return valueType_;
  }

  public static final int ID_FIELD_NUMBER = 11;
  private long id_;
  /**
   * <code>int64 id = 11;</code>
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
    if (!getApiBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, api_);
    }
    if (cargoNominationId_ != 0L) {
      output.writeInt64(2, cargoNominationId_);
    }
    if (!getQuantityBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, quantity_);
    }
    if (tankId_ != 0L) {
      output.writeInt64(4, tankId_);
    }
    if (!getTemperatureBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, temperature_);
    }
    if (!getUllageBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, ullage_);
    }
    if (!getQuantityM3Bytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 7, quantityM3_);
    }
    if (!getSoundingBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 8, sounding_);
    }
    if (conditionType_ != 0) {
      output.writeInt32(9, conditionType_);
    }
    if (valueType_ != 0) {
      output.writeInt32(10, valueType_);
    }
    if (id_ != 0L) {
      output.writeInt64(11, id_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getApiBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, api_);
    }
    if (cargoNominationId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, cargoNominationId_);
    }
    if (!getQuantityBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, quantity_);
    }
    if (tankId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, tankId_);
    }
    if (!getTemperatureBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, temperature_);
    }
    if (!getUllageBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, ullage_);
    }
    if (!getQuantityM3Bytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, quantityM3_);
    }
    if (!getSoundingBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, sounding_);
    }
    if (conditionType_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(9, conditionType_);
    }
    if (valueType_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(10, valueType_);
    }
    if (id_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(11, id_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails other =
        (com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails) obj;

    if (!getApi().equals(other.getApi())) return false;
    if (getCargoNominationId() != other.getCargoNominationId()) return false;
    if (!getQuantity().equals(other.getQuantity())) return false;
    if (getTankId() != other.getTankId()) return false;
    if (!getTemperature().equals(other.getTemperature())) return false;
    if (!getUllage().equals(other.getUllage())) return false;
    if (!getQuantityM3().equals(other.getQuantityM3())) return false;
    if (!getSounding().equals(other.getSounding())) return false;
    if (getConditionType() != other.getConditionType()) return false;
    if (getValueType() != other.getValueType()) return false;
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
    hash = (37 * hash) + API_FIELD_NUMBER;
    hash = (53 * hash) + getApi().hashCode();
    hash = (37 * hash) + CARGONOMINATIONID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoNominationId());
    hash = (37 * hash) + QUANTITY_FIELD_NUMBER;
    hash = (53 * hash) + getQuantity().hashCode();
    hash = (37 * hash) + TANKID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getTankId());
    hash = (37 * hash) + TEMPERATURE_FIELD_NUMBER;
    hash = (53 * hash) + getTemperature().hashCode();
    hash = (37 * hash) + ULLAGE_FIELD_NUMBER;
    hash = (53 * hash) + getUllage().hashCode();
    hash = (37 * hash) + QUANTITYM3_FIELD_NUMBER;
    hash = (53 * hash) + getQuantityM3().hashCode();
    hash = (37 * hash) + SOUNDING_FIELD_NUMBER;
    hash = (53 * hash) + getSounding().hashCode();
    hash = (37 * hash) + CONDITIONTYPE_FIELD_NUMBER;
    hash = (53 * hash) + getConditionType();
    hash = (37 * hash) + VALUETYPE_FIELD_NUMBER;
    hash = (53 * hash) + getValueType();
    hash = (37 * hash) + ID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getId());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parseFrom(
      byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails
      parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails
      parseDelimitedFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails prototype) {
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
  /** Protobuf type {@code DischargingPlanTankDetails} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargingPlanTankDetails)
      com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetailsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingPlanTankDetails_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingPlanTankDetails_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails.class,
              com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails.Builder.class);
    }

    // Construct using
    // com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails.newBuilder()
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
      api_ = "";

      cargoNominationId_ = 0L;

      quantity_ = "";

      tankId_ = 0L;

      temperature_ = "";

      ullage_ = "";

      quantityM3_ = "";

      sounding_ = "";

      conditionType_ = 0;

      valueType_ = 0;

      id_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingPlanTankDetails_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails
          .getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails build() {
      com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails result =
          new com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails(this);
      result.api_ = api_;
      result.cargoNominationId_ = cargoNominationId_;
      result.quantity_ = quantity_;
      result.tankId_ = tankId_;
      result.temperature_ = temperature_;
      result.ullage_ = ullage_;
      result.quantityM3_ = quantityM3_;
      result.sounding_ = sounding_;
      result.conditionType_ = conditionType_;
      result.valueType_ = valueType_;
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails) {
        return mergeFrom(
            (com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails
              .getDefaultInstance()) return this;
      if (!other.getApi().isEmpty()) {
        api_ = other.api_;
        onChanged();
      }
      if (other.getCargoNominationId() != 0L) {
        setCargoNominationId(other.getCargoNominationId());
      }
      if (!other.getQuantity().isEmpty()) {
        quantity_ = other.quantity_;
        onChanged();
      }
      if (other.getTankId() != 0L) {
        setTankId(other.getTankId());
      }
      if (!other.getTemperature().isEmpty()) {
        temperature_ = other.temperature_;
        onChanged();
      }
      if (!other.getUllage().isEmpty()) {
        ullage_ = other.ullage_;
        onChanged();
      }
      if (!other.getQuantityM3().isEmpty()) {
        quantityM3_ = other.quantityM3_;
        onChanged();
      }
      if (!other.getSounding().isEmpty()) {
        sounding_ = other.sounding_;
        onChanged();
      }
      if (other.getConditionType() != 0) {
        setConditionType(other.getConditionType());
      }
      if (other.getValueType() != 0) {
        setValueType(other.getValueType());
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
      com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails)
                e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object api_ = "";
    /**
     * <code>string api = 1;</code>
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
     * <code>string api = 1;</code>
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
     * <code>string api = 1;</code>
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
     * <code>string api = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearApi() {

      api_ = getDefaultInstance().getApi();
      onChanged();
      return this;
    }
    /**
     * <code>string api = 1;</code>
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

    private java.lang.Object quantity_ = "";
    /**
     * <code>string quantity = 3;</code>
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
     * <code>string quantity = 3;</code>
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
     * <code>string quantity = 3;</code>
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
     * <code>string quantity = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearQuantity() {

      quantity_ = getDefaultInstance().getQuantity();
      onChanged();
      return this;
    }
    /**
     * <code>string quantity = 3;</code>
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
     * <code>int64 tankId = 4;</code>
     *
     * @return The tankId.
     */
    public long getTankId() {
      return tankId_;
    }
    /**
     * <code>int64 tankId = 4;</code>
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
     * <code>int64 tankId = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTankId() {

      tankId_ = 0L;
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

    private java.lang.Object ullage_ = "";
    /**
     * <code>string ullage = 6;</code>
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
     * <code>string ullage = 6;</code>
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
     * <code>string ullage = 6;</code>
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
     * <code>string ullage = 6;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearUllage() {

      ullage_ = getDefaultInstance().getUllage();
      onChanged();
      return this;
    }
    /**
     * <code>string ullage = 6;</code>
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

    private java.lang.Object quantityM3_ = "";
    /**
     * <code>string quantityM3 = 7;</code>
     *
     * @return The quantityM3.
     */
    public java.lang.String getQuantityM3() {
      java.lang.Object ref = quantityM3_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        quantityM3_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string quantityM3 = 7;</code>
     *
     * @return The bytes for quantityM3.
     */
    public com.google.protobuf.ByteString getQuantityM3Bytes() {
      java.lang.Object ref = quantityM3_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        quantityM3_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string quantityM3 = 7;</code>
     *
     * @param value The quantityM3 to set.
     * @return This builder for chaining.
     */
    public Builder setQuantityM3(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      quantityM3_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string quantityM3 = 7;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearQuantityM3() {

      quantityM3_ = getDefaultInstance().getQuantityM3();
      onChanged();
      return this;
    }
    /**
     * <code>string quantityM3 = 7;</code>
     *
     * @param value The bytes for quantityM3 to set.
     * @return This builder for chaining.
     */
    public Builder setQuantityM3Bytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      quantityM3_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object sounding_ = "";
    /**
     * <code>string sounding = 8;</code>
     *
     * @return The sounding.
     */
    public java.lang.String getSounding() {
      java.lang.Object ref = sounding_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        sounding_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string sounding = 8;</code>
     *
     * @return The bytes for sounding.
     */
    public com.google.protobuf.ByteString getSoundingBytes() {
      java.lang.Object ref = sounding_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        sounding_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string sounding = 8;</code>
     *
     * @param value The sounding to set.
     * @return This builder for chaining.
     */
    public Builder setSounding(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      sounding_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string sounding = 8;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearSounding() {

      sounding_ = getDefaultInstance().getSounding();
      onChanged();
      return this;
    }
    /**
     * <code>string sounding = 8;</code>
     *
     * @param value The bytes for sounding to set.
     * @return This builder for chaining.
     */
    public Builder setSoundingBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      sounding_ = value;
      onChanged();
      return this;
    }

    private int conditionType_;
    /**
     * <code>int32 conditionType = 9;</code>
     *
     * @return The conditionType.
     */
    public int getConditionType() {
      return conditionType_;
    }
    /**
     * <code>int32 conditionType = 9;</code>
     *
     * @param value The conditionType to set.
     * @return This builder for chaining.
     */
    public Builder setConditionType(int value) {

      conditionType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 conditionType = 9;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearConditionType() {

      conditionType_ = 0;
      onChanged();
      return this;
    }

    private int valueType_;
    /**
     * <code>int32 valueType = 10;</code>
     *
     * @return The valueType.
     */
    public int getValueType() {
      return valueType_;
    }
    /**
     * <code>int32 valueType = 10;</code>
     *
     * @param value The valueType to set.
     * @return This builder for chaining.
     */
    public Builder setValueType(int value) {

      valueType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 valueType = 10;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearValueType() {

      valueType_ = 0;
      onChanged();
      return this;
    }

    private long id_;
    /**
     * <code>int64 id = 11;</code>
     *
     * @return The id.
     */
    public long getId() {
      return id_;
    }
    /**
     * <code>int64 id = 11;</code>
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
     * <code>int64 id = 11;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearId() {

      id_ = 0L;
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

    // @@protoc_insertion_point(builder_scope:DischargingPlanTankDetails)
  }

  // @@protoc_insertion_point(class_scope:DischargingPlanTankDetails)
  private static final com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargingPlanTankDetails> PARSER =
      new com.google.protobuf.AbstractParser<DischargingPlanTankDetails>() {
        @java.lang.Override
        public DischargingPlanTankDetails parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargingPlanTankDetails(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargingPlanTankDetails> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargingPlanTankDetails> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargingPlanTankDetails
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
