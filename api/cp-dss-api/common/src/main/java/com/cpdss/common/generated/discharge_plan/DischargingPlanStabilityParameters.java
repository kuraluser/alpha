/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargingPlanStabilityParameters} */
public final class DischargingPlanStabilityParameters extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargingPlanStabilityParameters)
    DischargingPlanStabilityParametersOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargingPlanStabilityParameters.newBuilder() to construct.
  private DischargingPlanStabilityParameters(
      com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargingPlanStabilityParameters() {
    draft_ = "";
    trim_ = "";
    bm_ = "";
    sf_ = "";
    foreDraft_ = "";
    aftDraft_ = "";
    meanDraft_ = "";
    list_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargingPlanStabilityParameters();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargingPlanStabilityParameters(
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

              draft_ = s;
              break;
            }
          case 18:
            {
              java.lang.String s = input.readStringRequireUtf8();

              trim_ = s;
              break;
            }
          case 26:
            {
              java.lang.String s = input.readStringRequireUtf8();

              bm_ = s;
              break;
            }
          case 34:
            {
              java.lang.String s = input.readStringRequireUtf8();

              sf_ = s;
              break;
            }
          case 40:
            {
              conditionType_ = input.readInt32();
              break;
            }
          case 48:
            {
              valueType_ = input.readInt32();
              break;
            }
          case 58:
            {
              java.lang.String s = input.readStringRequireUtf8();

              foreDraft_ = s;
              break;
            }
          case 66:
            {
              java.lang.String s = input.readStringRequireUtf8();

              aftDraft_ = s;
              break;
            }
          case 72:
            {
              time_ = input.readInt32();
              break;
            }
          case 82:
            {
              java.lang.String s = input.readStringRequireUtf8();

              meanDraft_ = s;
              break;
            }
          case 90:
            {
              java.lang.String s = input.readStringRequireUtf8();

              list_ = s;
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
        .internal_static_DischargingPlanStabilityParameters_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargingPlanStabilityParameters_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters.class,
            com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters.Builder
                .class);
  }

  public static final int DRAFT_FIELD_NUMBER = 1;
  private volatile java.lang.Object draft_;
  /**
   * <code>string draft = 1;</code>
   *
   * @return The draft.
   */
  public java.lang.String getDraft() {
    java.lang.Object ref = draft_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      draft_ = s;
      return s;
    }
  }
  /**
   * <code>string draft = 1;</code>
   *
   * @return The bytes for draft.
   */
  public com.google.protobuf.ByteString getDraftBytes() {
    java.lang.Object ref = draft_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      draft_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TRIM_FIELD_NUMBER = 2;
  private volatile java.lang.Object trim_;
  /**
   * <code>string trim = 2;</code>
   *
   * @return The trim.
   */
  public java.lang.String getTrim() {
    java.lang.Object ref = trim_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      trim_ = s;
      return s;
    }
  }
  /**
   * <code>string trim = 2;</code>
   *
   * @return The bytes for trim.
   */
  public com.google.protobuf.ByteString getTrimBytes() {
    java.lang.Object ref = trim_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      trim_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int BM_FIELD_NUMBER = 3;
  private volatile java.lang.Object bm_;
  /**
   * <code>string bm = 3;</code>
   *
   * @return The bm.
   */
  public java.lang.String getBm() {
    java.lang.Object ref = bm_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      bm_ = s;
      return s;
    }
  }
  /**
   * <code>string bm = 3;</code>
   *
   * @return The bytes for bm.
   */
  public com.google.protobuf.ByteString getBmBytes() {
    java.lang.Object ref = bm_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      bm_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SF_FIELD_NUMBER = 4;
  private volatile java.lang.Object sf_;
  /**
   * <code>string sf = 4;</code>
   *
   * @return The sf.
   */
  public java.lang.String getSf() {
    java.lang.Object ref = sf_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      sf_ = s;
      return s;
    }
  }
  /**
   * <code>string sf = 4;</code>
   *
   * @return The bytes for sf.
   */
  public com.google.protobuf.ByteString getSfBytes() {
    java.lang.Object ref = sf_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      sf_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CONDITIONTYPE_FIELD_NUMBER = 5;
  private int conditionType_;
  /**
   * <code>int32 conditionType = 5;</code>
   *
   * @return The conditionType.
   */
  public int getConditionType() {
    return conditionType_;
  }

  public static final int VALUETYPE_FIELD_NUMBER = 6;
  private int valueType_;
  /**
   * <code>int32 valueType = 6;</code>
   *
   * @return The valueType.
   */
  public int getValueType() {
    return valueType_;
  }

  public static final int FOREDRAFT_FIELD_NUMBER = 7;
  private volatile java.lang.Object foreDraft_;
  /**
   * <code>string foreDraft = 7;</code>
   *
   * @return The foreDraft.
   */
  public java.lang.String getForeDraft() {
    java.lang.Object ref = foreDraft_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      foreDraft_ = s;
      return s;
    }
  }
  /**
   * <code>string foreDraft = 7;</code>
   *
   * @return The bytes for foreDraft.
   */
  public com.google.protobuf.ByteString getForeDraftBytes() {
    java.lang.Object ref = foreDraft_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      foreDraft_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int AFTDRAFT_FIELD_NUMBER = 8;
  private volatile java.lang.Object aftDraft_;
  /**
   * <code>string aftDraft = 8;</code>
   *
   * @return The aftDraft.
   */
  public java.lang.String getAftDraft() {
    java.lang.Object ref = aftDraft_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      aftDraft_ = s;
      return s;
    }
  }
  /**
   * <code>string aftDraft = 8;</code>
   *
   * @return The bytes for aftDraft.
   */
  public com.google.protobuf.ByteString getAftDraftBytes() {
    java.lang.Object ref = aftDraft_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      aftDraft_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TIME_FIELD_NUMBER = 9;
  private int time_;
  /**
   * <code>int32 time = 9;</code>
   *
   * @return The time.
   */
  public int getTime() {
    return time_;
  }

  public static final int MEANDRAFT_FIELD_NUMBER = 10;
  private volatile java.lang.Object meanDraft_;
  /**
   * <code>string meanDraft = 10;</code>
   *
   * @return The meanDraft.
   */
  public java.lang.String getMeanDraft() {
    java.lang.Object ref = meanDraft_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      meanDraft_ = s;
      return s;
    }
  }
  /**
   * <code>string meanDraft = 10;</code>
   *
   * @return The bytes for meanDraft.
   */
  public com.google.protobuf.ByteString getMeanDraftBytes() {
    java.lang.Object ref = meanDraft_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      meanDraft_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int LIST_FIELD_NUMBER = 11;
  private volatile java.lang.Object list_;
  /**
   * <code>string list = 11;</code>
   *
   * @return The list.
   */
  public java.lang.String getList() {
    java.lang.Object ref = list_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      list_ = s;
      return s;
    }
  }
  /**
   * <code>string list = 11;</code>
   *
   * @return The bytes for list.
   */
  public com.google.protobuf.ByteString getListBytes() {
    java.lang.Object ref = list_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      list_ = b;
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
    if (!getDraftBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, draft_);
    }
    if (!getTrimBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, trim_);
    }
    if (!getBmBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, bm_);
    }
    if (!getSfBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, sf_);
    }
    if (conditionType_ != 0) {
      output.writeInt32(5, conditionType_);
    }
    if (valueType_ != 0) {
      output.writeInt32(6, valueType_);
    }
    if (!getForeDraftBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 7, foreDraft_);
    }
    if (!getAftDraftBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 8, aftDraft_);
    }
    if (time_ != 0) {
      output.writeInt32(9, time_);
    }
    if (!getMeanDraftBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 10, meanDraft_);
    }
    if (!getListBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 11, list_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getDraftBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, draft_);
    }
    if (!getTrimBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, trim_);
    }
    if (!getBmBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, bm_);
    }
    if (!getSfBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, sf_);
    }
    if (conditionType_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(5, conditionType_);
    }
    if (valueType_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(6, valueType_);
    }
    if (!getForeDraftBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, foreDraft_);
    }
    if (!getAftDraftBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, aftDraft_);
    }
    if (time_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(9, time_);
    }
    if (!getMeanDraftBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(10, meanDraft_);
    }
    if (!getListBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(11, list_);
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
        instanceof com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters other =
        (com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters) obj;

    if (!getDraft().equals(other.getDraft())) return false;
    if (!getTrim().equals(other.getTrim())) return false;
    if (!getBm().equals(other.getBm())) return false;
    if (!getSf().equals(other.getSf())) return false;
    if (getConditionType() != other.getConditionType()) return false;
    if (getValueType() != other.getValueType()) return false;
    if (!getForeDraft().equals(other.getForeDraft())) return false;
    if (!getAftDraft().equals(other.getAftDraft())) return false;
    if (getTime() != other.getTime()) return false;
    if (!getMeanDraft().equals(other.getMeanDraft())) return false;
    if (!getList().equals(other.getList())) return false;
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
    hash = (37 * hash) + DRAFT_FIELD_NUMBER;
    hash = (53 * hash) + getDraft().hashCode();
    hash = (37 * hash) + TRIM_FIELD_NUMBER;
    hash = (53 * hash) + getTrim().hashCode();
    hash = (37 * hash) + BM_FIELD_NUMBER;
    hash = (53 * hash) + getBm().hashCode();
    hash = (37 * hash) + SF_FIELD_NUMBER;
    hash = (53 * hash) + getSf().hashCode();
    hash = (37 * hash) + CONDITIONTYPE_FIELD_NUMBER;
    hash = (53 * hash) + getConditionType();
    hash = (37 * hash) + VALUETYPE_FIELD_NUMBER;
    hash = (53 * hash) + getValueType();
    hash = (37 * hash) + FOREDRAFT_FIELD_NUMBER;
    hash = (53 * hash) + getForeDraft().hashCode();
    hash = (37 * hash) + AFTDRAFT_FIELD_NUMBER;
    hash = (53 * hash) + getAftDraft().hashCode();
    hash = (37 * hash) + TIME_FIELD_NUMBER;
    hash = (53 * hash) + getTime();
    hash = (37 * hash) + MEANDRAFT_FIELD_NUMBER;
    hash = (53 * hash) + getMeanDraft().hashCode();
    hash = (37 * hash) + LIST_FIELD_NUMBER;
    hash = (53 * hash) + getList().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseFrom(java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseFrom(
          java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseFrom(com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseDelimitedFrom(
          java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
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
      com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters prototype) {
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
  /** Protobuf type {@code DischargingPlanStabilityParameters} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargingPlanStabilityParameters)
      com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParametersOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingPlanStabilityParameters_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingPlanStabilityParameters_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters.class,
              com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters.Builder
                  .class);
    }

    // Construct using
    // com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters.newBuilder()
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
      draft_ = "";

      trim_ = "";

      bm_ = "";

      sf_ = "";

      conditionType_ = 0;

      valueType_ = 0;

      foreDraft_ = "";

      aftDraft_ = "";

      time_ = 0;

      meanDraft_ = "";

      list_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingPlanStabilityParameters_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
          .getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters build() {
      com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters result =
          buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
        buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters result =
          new com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters(this);
      result.draft_ = draft_;
      result.trim_ = trim_;
      result.bm_ = bm_;
      result.sf_ = sf_;
      result.conditionType_ = conditionType_;
      result.valueType_ = valueType_;
      result.foreDraft_ = foreDraft_;
      result.aftDraft_ = aftDraft_;
      result.time_ = time_;
      result.meanDraft_ = meanDraft_;
      result.list_ = list_;
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
      if (other
          instanceof com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters) {
        return mergeFrom(
            (com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
        com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
              .getDefaultInstance()) return this;
      if (!other.getDraft().isEmpty()) {
        draft_ = other.draft_;
        onChanged();
      }
      if (!other.getTrim().isEmpty()) {
        trim_ = other.trim_;
        onChanged();
      }
      if (!other.getBm().isEmpty()) {
        bm_ = other.bm_;
        onChanged();
      }
      if (!other.getSf().isEmpty()) {
        sf_ = other.sf_;
        onChanged();
      }
      if (other.getConditionType() != 0) {
        setConditionType(other.getConditionType());
      }
      if (other.getValueType() != 0) {
        setValueType(other.getValueType());
      }
      if (!other.getForeDraft().isEmpty()) {
        foreDraft_ = other.foreDraft_;
        onChanged();
      }
      if (!other.getAftDraft().isEmpty()) {
        aftDraft_ = other.aftDraft_;
        onChanged();
      }
      if (other.getTime() != 0) {
        setTime(other.getTime());
      }
      if (!other.getMeanDraft().isEmpty()) {
        meanDraft_ = other.meanDraft_;
        onChanged();
      }
      if (!other.getList().isEmpty()) {
        list_ = other.list_;
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
      com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters parsedMessage =
          null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters)
                e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object draft_ = "";
    /**
     * <code>string draft = 1;</code>
     *
     * @return The draft.
     */
    public java.lang.String getDraft() {
      java.lang.Object ref = draft_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        draft_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string draft = 1;</code>
     *
     * @return The bytes for draft.
     */
    public com.google.protobuf.ByteString getDraftBytes() {
      java.lang.Object ref = draft_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        draft_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string draft = 1;</code>
     *
     * @param value The draft to set.
     * @return This builder for chaining.
     */
    public Builder setDraft(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      draft_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string draft = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDraft() {

      draft_ = getDefaultInstance().getDraft();
      onChanged();
      return this;
    }
    /**
     * <code>string draft = 1;</code>
     *
     * @param value The bytes for draft to set.
     * @return This builder for chaining.
     */
    public Builder setDraftBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      draft_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object trim_ = "";
    /**
     * <code>string trim = 2;</code>
     *
     * @return The trim.
     */
    public java.lang.String getTrim() {
      java.lang.Object ref = trim_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        trim_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string trim = 2;</code>
     *
     * @return The bytes for trim.
     */
    public com.google.protobuf.ByteString getTrimBytes() {
      java.lang.Object ref = trim_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        trim_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string trim = 2;</code>
     *
     * @param value The trim to set.
     * @return This builder for chaining.
     */
    public Builder setTrim(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      trim_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string trim = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTrim() {

      trim_ = getDefaultInstance().getTrim();
      onChanged();
      return this;
    }
    /**
     * <code>string trim = 2;</code>
     *
     * @param value The bytes for trim to set.
     * @return This builder for chaining.
     */
    public Builder setTrimBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      trim_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object bm_ = "";
    /**
     * <code>string bm = 3;</code>
     *
     * @return The bm.
     */
    public java.lang.String getBm() {
      java.lang.Object ref = bm_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        bm_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string bm = 3;</code>
     *
     * @return The bytes for bm.
     */
    public com.google.protobuf.ByteString getBmBytes() {
      java.lang.Object ref = bm_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        bm_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string bm = 3;</code>
     *
     * @param value The bm to set.
     * @return This builder for chaining.
     */
    public Builder setBm(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      bm_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string bm = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearBm() {

      bm_ = getDefaultInstance().getBm();
      onChanged();
      return this;
    }
    /**
     * <code>string bm = 3;</code>
     *
     * @param value The bytes for bm to set.
     * @return This builder for chaining.
     */
    public Builder setBmBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      bm_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object sf_ = "";
    /**
     * <code>string sf = 4;</code>
     *
     * @return The sf.
     */
    public java.lang.String getSf() {
      java.lang.Object ref = sf_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        sf_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string sf = 4;</code>
     *
     * @return The bytes for sf.
     */
    public com.google.protobuf.ByteString getSfBytes() {
      java.lang.Object ref = sf_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        sf_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string sf = 4;</code>
     *
     * @param value The sf to set.
     * @return This builder for chaining.
     */
    public Builder setSf(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      sf_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string sf = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearSf() {

      sf_ = getDefaultInstance().getSf();
      onChanged();
      return this;
    }
    /**
     * <code>string sf = 4;</code>
     *
     * @param value The bytes for sf to set.
     * @return This builder for chaining.
     */
    public Builder setSfBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      sf_ = value;
      onChanged();
      return this;
    }

    private int conditionType_;
    /**
     * <code>int32 conditionType = 5;</code>
     *
     * @return The conditionType.
     */
    public int getConditionType() {
      return conditionType_;
    }
    /**
     * <code>int32 conditionType = 5;</code>
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
     * <code>int32 conditionType = 5;</code>
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
     * <code>int32 valueType = 6;</code>
     *
     * @return The valueType.
     */
    public int getValueType() {
      return valueType_;
    }
    /**
     * <code>int32 valueType = 6;</code>
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
     * <code>int32 valueType = 6;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearValueType() {

      valueType_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object foreDraft_ = "";
    /**
     * <code>string foreDraft = 7;</code>
     *
     * @return The foreDraft.
     */
    public java.lang.String getForeDraft() {
      java.lang.Object ref = foreDraft_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        foreDraft_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string foreDraft = 7;</code>
     *
     * @return The bytes for foreDraft.
     */
    public com.google.protobuf.ByteString getForeDraftBytes() {
      java.lang.Object ref = foreDraft_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        foreDraft_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string foreDraft = 7;</code>
     *
     * @param value The foreDraft to set.
     * @return This builder for chaining.
     */
    public Builder setForeDraft(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      foreDraft_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string foreDraft = 7;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearForeDraft() {

      foreDraft_ = getDefaultInstance().getForeDraft();
      onChanged();
      return this;
    }
    /**
     * <code>string foreDraft = 7;</code>
     *
     * @param value The bytes for foreDraft to set.
     * @return This builder for chaining.
     */
    public Builder setForeDraftBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      foreDraft_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object aftDraft_ = "";
    /**
     * <code>string aftDraft = 8;</code>
     *
     * @return The aftDraft.
     */
    public java.lang.String getAftDraft() {
      java.lang.Object ref = aftDraft_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        aftDraft_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string aftDraft = 8;</code>
     *
     * @return The bytes for aftDraft.
     */
    public com.google.protobuf.ByteString getAftDraftBytes() {
      java.lang.Object ref = aftDraft_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        aftDraft_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string aftDraft = 8;</code>
     *
     * @param value The aftDraft to set.
     * @return This builder for chaining.
     */
    public Builder setAftDraft(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      aftDraft_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string aftDraft = 8;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearAftDraft() {

      aftDraft_ = getDefaultInstance().getAftDraft();
      onChanged();
      return this;
    }
    /**
     * <code>string aftDraft = 8;</code>
     *
     * @param value The bytes for aftDraft to set.
     * @return This builder for chaining.
     */
    public Builder setAftDraftBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      aftDraft_ = value;
      onChanged();
      return this;
    }

    private int time_;
    /**
     * <code>int32 time = 9;</code>
     *
     * @return The time.
     */
    public int getTime() {
      return time_;
    }
    /**
     * <code>int32 time = 9;</code>
     *
     * @param value The time to set.
     * @return This builder for chaining.
     */
    public Builder setTime(int value) {

      time_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 time = 9;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTime() {

      time_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object meanDraft_ = "";
    /**
     * <code>string meanDraft = 10;</code>
     *
     * @return The meanDraft.
     */
    public java.lang.String getMeanDraft() {
      java.lang.Object ref = meanDraft_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        meanDraft_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string meanDraft = 10;</code>
     *
     * @return The bytes for meanDraft.
     */
    public com.google.protobuf.ByteString getMeanDraftBytes() {
      java.lang.Object ref = meanDraft_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        meanDraft_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string meanDraft = 10;</code>
     *
     * @param value The meanDraft to set.
     * @return This builder for chaining.
     */
    public Builder setMeanDraft(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      meanDraft_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string meanDraft = 10;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearMeanDraft() {

      meanDraft_ = getDefaultInstance().getMeanDraft();
      onChanged();
      return this;
    }
    /**
     * <code>string meanDraft = 10;</code>
     *
     * @param value The bytes for meanDraft to set.
     * @return This builder for chaining.
     */
    public Builder setMeanDraftBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      meanDraft_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object list_ = "";
    /**
     * <code>string list = 11;</code>
     *
     * @return The list.
     */
    public java.lang.String getList() {
      java.lang.Object ref = list_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        list_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string list = 11;</code>
     *
     * @return The bytes for list.
     */
    public com.google.protobuf.ByteString getListBytes() {
      java.lang.Object ref = list_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        list_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string list = 11;</code>
     *
     * @param value The list to set.
     * @return This builder for chaining.
     */
    public Builder setList(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      list_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string list = 11;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearList() {

      list_ = getDefaultInstance().getList();
      onChanged();
      return this;
    }
    /**
     * <code>string list = 11;</code>
     *
     * @param value The bytes for list to set.
     * @return This builder for chaining.
     */
    public Builder setListBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      list_ = value;
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

    // @@protoc_insertion_point(builder_scope:DischargingPlanStabilityParameters)
  }

  // @@protoc_insertion_point(class_scope:DischargingPlanStabilityParameters)
  private static final com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE =
        new com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargingPlanStabilityParameters> PARSER =
      new com.google.protobuf.AbstractParser<DischargingPlanStabilityParameters>() {
        @java.lang.Override
        public DischargingPlanStabilityParameters parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargingPlanStabilityParameters(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargingPlanStabilityParameters> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargingPlanStabilityParameters> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargingPlanStabilityParameters
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
