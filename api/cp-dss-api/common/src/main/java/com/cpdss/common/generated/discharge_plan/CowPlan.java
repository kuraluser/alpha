/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code CowPlan} */
public final class CowPlan extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:CowPlan)
    CowPlanOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use CowPlan.newBuilder() to construct.
  private CowPlan(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private CowPlan() {
    cowPlan_ = "";
    noOfTanksForCow_ = "";
    cowStartTime_ = "";
    cowEndTime_ = "";
    estCowDuration_ = "";
    trimCowMin_ = "";
    trimCowMax_ = "";
    cowTankDetails_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new CowPlan();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private CowPlan(
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
              java.lang.String s = input.readStringRequireUtf8();

              cowPlan_ = s;
              break;
            }
          case 18:
            {
              java.lang.String s = input.readStringRequireUtf8();

              noOfTanksForCow_ = s;
              break;
            }
          case 26:
            {
              java.lang.String s = input.readStringRequireUtf8();

              cowStartTime_ = s;
              break;
            }
          case 34:
            {
              java.lang.String s = input.readStringRequireUtf8();

              cowEndTime_ = s;
              break;
            }
          case 42:
            {
              java.lang.String s = input.readStringRequireUtf8();

              estCowDuration_ = s;
              break;
            }
          case 50:
            {
              java.lang.String s = input.readStringRequireUtf8();

              trimCowMin_ = s;
              break;
            }
          case 58:
            {
              java.lang.String s = input.readStringRequireUtf8();

              trimCowMax_ = s;
              break;
            }
          case 64:
            {
              needFreshCrudeStorage_ = input.readBool();
              break;
            }
          case 72:
            {
              needFlushingOil_ = input.readBool();
              break;
            }
          case 82:
            {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                cowTankDetails_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.discharge_plan.CowTankDetails>();
                mutable_bitField0_ |= 0x00000001;
              }
              cowTankDetails_.add(
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.CowTankDetails.parser(),
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
        cowTankDetails_ = java.util.Collections.unmodifiableList(cowTankDetails_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_CowPlan_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_CowPlan_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.CowPlan.class,
            com.cpdss.common.generated.discharge_plan.CowPlan.Builder.class);
  }

  public static final int COWPLAN_FIELD_NUMBER = 1;
  private volatile java.lang.Object cowPlan_;
  /**
   * <code>string cowPlan = 1;</code>
   *
   * @return The cowPlan.
   */
  public java.lang.String getCowPlan() {
    java.lang.Object ref = cowPlan_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      cowPlan_ = s;
      return s;
    }
  }
  /**
   * <code>string cowPlan = 1;</code>
   *
   * @return The bytes for cowPlan.
   */
  public com.google.protobuf.ByteString getCowPlanBytes() {
    java.lang.Object ref = cowPlan_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      cowPlan_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int NOOFTANKSFORCOW_FIELD_NUMBER = 2;
  private volatile java.lang.Object noOfTanksForCow_;
  /**
   * <code>string noOfTanksForCow = 2;</code>
   *
   * @return The noOfTanksForCow.
   */
  public java.lang.String getNoOfTanksForCow() {
    java.lang.Object ref = noOfTanksForCow_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      noOfTanksForCow_ = s;
      return s;
    }
  }
  /**
   * <code>string noOfTanksForCow = 2;</code>
   *
   * @return The bytes for noOfTanksForCow.
   */
  public com.google.protobuf.ByteString getNoOfTanksForCowBytes() {
    java.lang.Object ref = noOfTanksForCow_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      noOfTanksForCow_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int COWSTARTTIME_FIELD_NUMBER = 3;
  private volatile java.lang.Object cowStartTime_;
  /**
   * <code>string cowStartTime = 3;</code>
   *
   * @return The cowStartTime.
   */
  public java.lang.String getCowStartTime() {
    java.lang.Object ref = cowStartTime_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      cowStartTime_ = s;
      return s;
    }
  }
  /**
   * <code>string cowStartTime = 3;</code>
   *
   * @return The bytes for cowStartTime.
   */
  public com.google.protobuf.ByteString getCowStartTimeBytes() {
    java.lang.Object ref = cowStartTime_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      cowStartTime_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int COWENDTIME_FIELD_NUMBER = 4;
  private volatile java.lang.Object cowEndTime_;
  /**
   * <code>string cowEndTime = 4;</code>
   *
   * @return The cowEndTime.
   */
  public java.lang.String getCowEndTime() {
    java.lang.Object ref = cowEndTime_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      cowEndTime_ = s;
      return s;
    }
  }
  /**
   * <code>string cowEndTime = 4;</code>
   *
   * @return The bytes for cowEndTime.
   */
  public com.google.protobuf.ByteString getCowEndTimeBytes() {
    java.lang.Object ref = cowEndTime_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      cowEndTime_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ESTCOWDURATION_FIELD_NUMBER = 5;
  private volatile java.lang.Object estCowDuration_;
  /**
   * <code>string estCowDuration = 5;</code>
   *
   * @return The estCowDuration.
   */
  public java.lang.String getEstCowDuration() {
    java.lang.Object ref = estCowDuration_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      estCowDuration_ = s;
      return s;
    }
  }
  /**
   * <code>string estCowDuration = 5;</code>
   *
   * @return The bytes for estCowDuration.
   */
  public com.google.protobuf.ByteString getEstCowDurationBytes() {
    java.lang.Object ref = estCowDuration_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      estCowDuration_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TRIMCOWMIN_FIELD_NUMBER = 6;
  private volatile java.lang.Object trimCowMin_;
  /**
   * <code>string trimCowMin = 6;</code>
   *
   * @return The trimCowMin.
   */
  public java.lang.String getTrimCowMin() {
    java.lang.Object ref = trimCowMin_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      trimCowMin_ = s;
      return s;
    }
  }
  /**
   * <code>string trimCowMin = 6;</code>
   *
   * @return The bytes for trimCowMin.
   */
  public com.google.protobuf.ByteString getTrimCowMinBytes() {
    java.lang.Object ref = trimCowMin_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      trimCowMin_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TRIMCOWMAX_FIELD_NUMBER = 7;
  private volatile java.lang.Object trimCowMax_;
  /**
   * <code>string trimCowMax = 7;</code>
   *
   * @return The trimCowMax.
   */
  public java.lang.String getTrimCowMax() {
    java.lang.Object ref = trimCowMax_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      trimCowMax_ = s;
      return s;
    }
  }
  /**
   * <code>string trimCowMax = 7;</code>
   *
   * @return The bytes for trimCowMax.
   */
  public com.google.protobuf.ByteString getTrimCowMaxBytes() {
    java.lang.Object ref = trimCowMax_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      trimCowMax_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int NEEDFRESHCRUDESTORAGE_FIELD_NUMBER = 8;
  private boolean needFreshCrudeStorage_;
  /**
   * <code>bool needFreshCrudeStorage = 8;</code>
   *
   * @return The needFreshCrudeStorage.
   */
  public boolean getNeedFreshCrudeStorage() {
    return needFreshCrudeStorage_;
  }

  public static final int NEEDFLUSHINGOIL_FIELD_NUMBER = 9;
  private boolean needFlushingOil_;
  /**
   * <code>bool needFlushingOil = 9;</code>
   *
   * @return The needFlushingOil.
   */
  public boolean getNeedFlushingOil() {
    return needFlushingOil_;
  }

  public static final int COWTANKDETAILS_FIELD_NUMBER = 10;
  private java.util.List<com.cpdss.common.generated.discharge_plan.CowTankDetails> cowTankDetails_;
  /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
  public java.util.List<com.cpdss.common.generated.discharge_plan.CowTankDetails>
      getCowTankDetailsList() {
    return cowTankDetails_;
  }
  /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
  public java.util.List<? extends com.cpdss.common.generated.discharge_plan.CowTankDetailsOrBuilder>
      getCowTankDetailsOrBuilderList() {
    return cowTankDetails_;
  }
  /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
  public int getCowTankDetailsCount() {
    return cowTankDetails_.size();
  }
  /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
  public com.cpdss.common.generated.discharge_plan.CowTankDetails getCowTankDetails(int index) {
    return cowTankDetails_.get(index);
  }
  /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
  public com.cpdss.common.generated.discharge_plan.CowTankDetailsOrBuilder
      getCowTankDetailsOrBuilder(int index) {
    return cowTankDetails_.get(index);
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
    if (!getCowPlanBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, cowPlan_);
    }
    if (!getNoOfTanksForCowBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, noOfTanksForCow_);
    }
    if (!getCowStartTimeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, cowStartTime_);
    }
    if (!getCowEndTimeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, cowEndTime_);
    }
    if (!getEstCowDurationBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, estCowDuration_);
    }
    if (!getTrimCowMinBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, trimCowMin_);
    }
    if (!getTrimCowMaxBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 7, trimCowMax_);
    }
    if (needFreshCrudeStorage_ != false) {
      output.writeBool(8, needFreshCrudeStorage_);
    }
    if (needFlushingOil_ != false) {
      output.writeBool(9, needFlushingOil_);
    }
    for (int i = 0; i < cowTankDetails_.size(); i++) {
      output.writeMessage(10, cowTankDetails_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getCowPlanBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, cowPlan_);
    }
    if (!getNoOfTanksForCowBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, noOfTanksForCow_);
    }
    if (!getCowStartTimeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, cowStartTime_);
    }
    if (!getCowEndTimeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, cowEndTime_);
    }
    if (!getEstCowDurationBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, estCowDuration_);
    }
    if (!getTrimCowMinBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, trimCowMin_);
    }
    if (!getTrimCowMaxBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, trimCowMax_);
    }
    if (needFreshCrudeStorage_ != false) {
      size += com.google.protobuf.CodedOutputStream.computeBoolSize(8, needFreshCrudeStorage_);
    }
    if (needFlushingOil_ != false) {
      size += com.google.protobuf.CodedOutputStream.computeBoolSize(9, needFlushingOil_);
    }
    for (int i = 0; i < cowTankDetails_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(10, cowTankDetails_.get(i));
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.CowPlan)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.CowPlan other =
        (com.cpdss.common.generated.discharge_plan.CowPlan) obj;

    if (!getCowPlan().equals(other.getCowPlan())) return false;
    if (!getNoOfTanksForCow().equals(other.getNoOfTanksForCow())) return false;
    if (!getCowStartTime().equals(other.getCowStartTime())) return false;
    if (!getCowEndTime().equals(other.getCowEndTime())) return false;
    if (!getEstCowDuration().equals(other.getEstCowDuration())) return false;
    if (!getTrimCowMin().equals(other.getTrimCowMin())) return false;
    if (!getTrimCowMax().equals(other.getTrimCowMax())) return false;
    if (getNeedFreshCrudeStorage() != other.getNeedFreshCrudeStorage()) return false;
    if (getNeedFlushingOil() != other.getNeedFlushingOil()) return false;
    if (!getCowTankDetailsList().equals(other.getCowTankDetailsList())) return false;
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
    hash = (37 * hash) + COWPLAN_FIELD_NUMBER;
    hash = (53 * hash) + getCowPlan().hashCode();
    hash = (37 * hash) + NOOFTANKSFORCOW_FIELD_NUMBER;
    hash = (53 * hash) + getNoOfTanksForCow().hashCode();
    hash = (37 * hash) + COWSTARTTIME_FIELD_NUMBER;
    hash = (53 * hash) + getCowStartTime().hashCode();
    hash = (37 * hash) + COWENDTIME_FIELD_NUMBER;
    hash = (53 * hash) + getCowEndTime().hashCode();
    hash = (37 * hash) + ESTCOWDURATION_FIELD_NUMBER;
    hash = (53 * hash) + getEstCowDuration().hashCode();
    hash = (37 * hash) + TRIMCOWMIN_FIELD_NUMBER;
    hash = (53 * hash) + getTrimCowMin().hashCode();
    hash = (37 * hash) + TRIMCOWMAX_FIELD_NUMBER;
    hash = (53 * hash) + getTrimCowMax().hashCode();
    hash = (37 * hash) + NEEDFRESHCRUDESTORAGE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getNeedFreshCrudeStorage());
    hash = (37 * hash) + NEEDFLUSHINGOIL_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getNeedFlushingOil());
    if (getCowTankDetailsCount() > 0) {
      hash = (37 * hash) + COWTANKDETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getCowTankDetailsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan parseFrom(
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

  public static Builder newBuilder(com.cpdss.common.generated.discharge_plan.CowPlan prototype) {
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
  /** Protobuf type {@code CowPlan} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:CowPlan)
      com.cpdss.common.generated.discharge_plan.CowPlanOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CowPlan_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CowPlan_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.CowPlan.class,
              com.cpdss.common.generated.discharge_plan.CowPlan.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.CowPlan.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getCowTankDetailsFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      cowPlan_ = "";

      noOfTanksForCow_ = "";

      cowStartTime_ = "";

      cowEndTime_ = "";

      estCowDuration_ = "";

      trimCowMin_ = "";

      trimCowMax_ = "";

      needFreshCrudeStorage_ = false;

      needFlushingOil_ = false;

      if (cowTankDetailsBuilder_ == null) {
        cowTankDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        cowTankDetailsBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_CowPlan_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CowPlan getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.CowPlan.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CowPlan build() {
      com.cpdss.common.generated.discharge_plan.CowPlan result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.CowPlan buildPartial() {
      com.cpdss.common.generated.discharge_plan.CowPlan result =
          new com.cpdss.common.generated.discharge_plan.CowPlan(this);
      int from_bitField0_ = bitField0_;
      result.cowPlan_ = cowPlan_;
      result.noOfTanksForCow_ = noOfTanksForCow_;
      result.cowStartTime_ = cowStartTime_;
      result.cowEndTime_ = cowEndTime_;
      result.estCowDuration_ = estCowDuration_;
      result.trimCowMin_ = trimCowMin_;
      result.trimCowMax_ = trimCowMax_;
      result.needFreshCrudeStorage_ = needFreshCrudeStorage_;
      result.needFlushingOil_ = needFlushingOil_;
      if (cowTankDetailsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          cowTankDetails_ = java.util.Collections.unmodifiableList(cowTankDetails_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.cowTankDetails_ = cowTankDetails_;
      } else {
        result.cowTankDetails_ = cowTankDetailsBuilder_.build();
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.CowPlan) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.CowPlan) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.CowPlan other) {
      if (other == com.cpdss.common.generated.discharge_plan.CowPlan.getDefaultInstance())
        return this;
      if (!other.getCowPlan().isEmpty()) {
        cowPlan_ = other.cowPlan_;
        onChanged();
      }
      if (!other.getNoOfTanksForCow().isEmpty()) {
        noOfTanksForCow_ = other.noOfTanksForCow_;
        onChanged();
      }
      if (!other.getCowStartTime().isEmpty()) {
        cowStartTime_ = other.cowStartTime_;
        onChanged();
      }
      if (!other.getCowEndTime().isEmpty()) {
        cowEndTime_ = other.cowEndTime_;
        onChanged();
      }
      if (!other.getEstCowDuration().isEmpty()) {
        estCowDuration_ = other.estCowDuration_;
        onChanged();
      }
      if (!other.getTrimCowMin().isEmpty()) {
        trimCowMin_ = other.trimCowMin_;
        onChanged();
      }
      if (!other.getTrimCowMax().isEmpty()) {
        trimCowMax_ = other.trimCowMax_;
        onChanged();
      }
      if (other.getNeedFreshCrudeStorage() != false) {
        setNeedFreshCrudeStorage(other.getNeedFreshCrudeStorage());
      }
      if (other.getNeedFlushingOil() != false) {
        setNeedFlushingOil(other.getNeedFlushingOil());
      }
      if (cowTankDetailsBuilder_ == null) {
        if (!other.cowTankDetails_.isEmpty()) {
          if (cowTankDetails_.isEmpty()) {
            cowTankDetails_ = other.cowTankDetails_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureCowTankDetailsIsMutable();
            cowTankDetails_.addAll(other.cowTankDetails_);
          }
          onChanged();
        }
      } else {
        if (!other.cowTankDetails_.isEmpty()) {
          if (cowTankDetailsBuilder_.isEmpty()) {
            cowTankDetailsBuilder_.dispose();
            cowTankDetailsBuilder_ = null;
            cowTankDetails_ = other.cowTankDetails_;
            bitField0_ = (bitField0_ & ~0x00000001);
            cowTankDetailsBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getCowTankDetailsFieldBuilder()
                    : null;
          } else {
            cowTankDetailsBuilder_.addAllMessages(other.cowTankDetails_);
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
      com.cpdss.common.generated.discharge_plan.CowPlan parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.CowPlan) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int bitField0_;

    private java.lang.Object cowPlan_ = "";
    /**
     * <code>string cowPlan = 1;</code>
     *
     * @return The cowPlan.
     */
    public java.lang.String getCowPlan() {
      java.lang.Object ref = cowPlan_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cowPlan_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string cowPlan = 1;</code>
     *
     * @return The bytes for cowPlan.
     */
    public com.google.protobuf.ByteString getCowPlanBytes() {
      java.lang.Object ref = cowPlan_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cowPlan_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string cowPlan = 1;</code>
     *
     * @param value The cowPlan to set.
     * @return This builder for chaining.
     */
    public Builder setCowPlan(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      cowPlan_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string cowPlan = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCowPlan() {

      cowPlan_ = getDefaultInstance().getCowPlan();
      onChanged();
      return this;
    }
    /**
     * <code>string cowPlan = 1;</code>
     *
     * @param value The bytes for cowPlan to set.
     * @return This builder for chaining.
     */
    public Builder setCowPlanBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      cowPlan_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object noOfTanksForCow_ = "";
    /**
     * <code>string noOfTanksForCow = 2;</code>
     *
     * @return The noOfTanksForCow.
     */
    public java.lang.String getNoOfTanksForCow() {
      java.lang.Object ref = noOfTanksForCow_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        noOfTanksForCow_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string noOfTanksForCow = 2;</code>
     *
     * @return The bytes for noOfTanksForCow.
     */
    public com.google.protobuf.ByteString getNoOfTanksForCowBytes() {
      java.lang.Object ref = noOfTanksForCow_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        noOfTanksForCow_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string noOfTanksForCow = 2;</code>
     *
     * @param value The noOfTanksForCow to set.
     * @return This builder for chaining.
     */
    public Builder setNoOfTanksForCow(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      noOfTanksForCow_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string noOfTanksForCow = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearNoOfTanksForCow() {

      noOfTanksForCow_ = getDefaultInstance().getNoOfTanksForCow();
      onChanged();
      return this;
    }
    /**
     * <code>string noOfTanksForCow = 2;</code>
     *
     * @param value The bytes for noOfTanksForCow to set.
     * @return This builder for chaining.
     */
    public Builder setNoOfTanksForCowBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      noOfTanksForCow_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object cowStartTime_ = "";
    /**
     * <code>string cowStartTime = 3;</code>
     *
     * @return The cowStartTime.
     */
    public java.lang.String getCowStartTime() {
      java.lang.Object ref = cowStartTime_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cowStartTime_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string cowStartTime = 3;</code>
     *
     * @return The bytes for cowStartTime.
     */
    public com.google.protobuf.ByteString getCowStartTimeBytes() {
      java.lang.Object ref = cowStartTime_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cowStartTime_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string cowStartTime = 3;</code>
     *
     * @param value The cowStartTime to set.
     * @return This builder for chaining.
     */
    public Builder setCowStartTime(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      cowStartTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string cowStartTime = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCowStartTime() {

      cowStartTime_ = getDefaultInstance().getCowStartTime();
      onChanged();
      return this;
    }
    /**
     * <code>string cowStartTime = 3;</code>
     *
     * @param value The bytes for cowStartTime to set.
     * @return This builder for chaining.
     */
    public Builder setCowStartTimeBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      cowStartTime_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object cowEndTime_ = "";
    /**
     * <code>string cowEndTime = 4;</code>
     *
     * @return The cowEndTime.
     */
    public java.lang.String getCowEndTime() {
      java.lang.Object ref = cowEndTime_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cowEndTime_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string cowEndTime = 4;</code>
     *
     * @return The bytes for cowEndTime.
     */
    public com.google.protobuf.ByteString getCowEndTimeBytes() {
      java.lang.Object ref = cowEndTime_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cowEndTime_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string cowEndTime = 4;</code>
     *
     * @param value The cowEndTime to set.
     * @return This builder for chaining.
     */
    public Builder setCowEndTime(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      cowEndTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string cowEndTime = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCowEndTime() {

      cowEndTime_ = getDefaultInstance().getCowEndTime();
      onChanged();
      return this;
    }
    /**
     * <code>string cowEndTime = 4;</code>
     *
     * @param value The bytes for cowEndTime to set.
     * @return This builder for chaining.
     */
    public Builder setCowEndTimeBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      cowEndTime_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object estCowDuration_ = "";
    /**
     * <code>string estCowDuration = 5;</code>
     *
     * @return The estCowDuration.
     */
    public java.lang.String getEstCowDuration() {
      java.lang.Object ref = estCowDuration_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        estCowDuration_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string estCowDuration = 5;</code>
     *
     * @return The bytes for estCowDuration.
     */
    public com.google.protobuf.ByteString getEstCowDurationBytes() {
      java.lang.Object ref = estCowDuration_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        estCowDuration_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string estCowDuration = 5;</code>
     *
     * @param value The estCowDuration to set.
     * @return This builder for chaining.
     */
    public Builder setEstCowDuration(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      estCowDuration_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string estCowDuration = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearEstCowDuration() {

      estCowDuration_ = getDefaultInstance().getEstCowDuration();
      onChanged();
      return this;
    }
    /**
     * <code>string estCowDuration = 5;</code>
     *
     * @param value The bytes for estCowDuration to set.
     * @return This builder for chaining.
     */
    public Builder setEstCowDurationBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      estCowDuration_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object trimCowMin_ = "";
    /**
     * <code>string trimCowMin = 6;</code>
     *
     * @return The trimCowMin.
     */
    public java.lang.String getTrimCowMin() {
      java.lang.Object ref = trimCowMin_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        trimCowMin_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string trimCowMin = 6;</code>
     *
     * @return The bytes for trimCowMin.
     */
    public com.google.protobuf.ByteString getTrimCowMinBytes() {
      java.lang.Object ref = trimCowMin_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        trimCowMin_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string trimCowMin = 6;</code>
     *
     * @param value The trimCowMin to set.
     * @return This builder for chaining.
     */
    public Builder setTrimCowMin(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      trimCowMin_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string trimCowMin = 6;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTrimCowMin() {

      trimCowMin_ = getDefaultInstance().getTrimCowMin();
      onChanged();
      return this;
    }
    /**
     * <code>string trimCowMin = 6;</code>
     *
     * @param value The bytes for trimCowMin to set.
     * @return This builder for chaining.
     */
    public Builder setTrimCowMinBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      trimCowMin_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object trimCowMax_ = "";
    /**
     * <code>string trimCowMax = 7;</code>
     *
     * @return The trimCowMax.
     */
    public java.lang.String getTrimCowMax() {
      java.lang.Object ref = trimCowMax_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        trimCowMax_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string trimCowMax = 7;</code>
     *
     * @return The bytes for trimCowMax.
     */
    public com.google.protobuf.ByteString getTrimCowMaxBytes() {
      java.lang.Object ref = trimCowMax_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        trimCowMax_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string trimCowMax = 7;</code>
     *
     * @param value The trimCowMax to set.
     * @return This builder for chaining.
     */
    public Builder setTrimCowMax(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      trimCowMax_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string trimCowMax = 7;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTrimCowMax() {

      trimCowMax_ = getDefaultInstance().getTrimCowMax();
      onChanged();
      return this;
    }
    /**
     * <code>string trimCowMax = 7;</code>
     *
     * @param value The bytes for trimCowMax to set.
     * @return This builder for chaining.
     */
    public Builder setTrimCowMaxBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      trimCowMax_ = value;
      onChanged();
      return this;
    }

    private boolean needFreshCrudeStorage_;
    /**
     * <code>bool needFreshCrudeStorage = 8;</code>
     *
     * @return The needFreshCrudeStorage.
     */
    public boolean getNeedFreshCrudeStorage() {
      return needFreshCrudeStorage_;
    }
    /**
     * <code>bool needFreshCrudeStorage = 8;</code>
     *
     * @param value The needFreshCrudeStorage to set.
     * @return This builder for chaining.
     */
    public Builder setNeedFreshCrudeStorage(boolean value) {

      needFreshCrudeStorage_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool needFreshCrudeStorage = 8;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearNeedFreshCrudeStorage() {

      needFreshCrudeStorage_ = false;
      onChanged();
      return this;
    }

    private boolean needFlushingOil_;
    /**
     * <code>bool needFlushingOil = 9;</code>
     *
     * @return The needFlushingOil.
     */
    public boolean getNeedFlushingOil() {
      return needFlushingOil_;
    }
    /**
     * <code>bool needFlushingOil = 9;</code>
     *
     * @param value The needFlushingOil to set.
     * @return This builder for chaining.
     */
    public Builder setNeedFlushingOil(boolean value) {

      needFlushingOil_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool needFlushingOil = 9;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearNeedFlushingOil() {

      needFlushingOil_ = false;
      onChanged();
      return this;
    }

    private java.util.List<com.cpdss.common.generated.discharge_plan.CowTankDetails>
        cowTankDetails_ = java.util.Collections.emptyList();

    private void ensureCowTankDetailsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        cowTankDetails_ =
            new java.util.ArrayList<com.cpdss.common.generated.discharge_plan.CowTankDetails>(
                cowTankDetails_);
        bitField0_ |= 0x00000001;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.CowTankDetails,
            com.cpdss.common.generated.discharge_plan.CowTankDetails.Builder,
            com.cpdss.common.generated.discharge_plan.CowTankDetailsOrBuilder>
        cowTankDetailsBuilder_;

    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.CowTankDetails>
        getCowTankDetailsList() {
      if (cowTankDetailsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(cowTankDetails_);
      } else {
        return cowTankDetailsBuilder_.getMessageList();
      }
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public int getCowTankDetailsCount() {
      if (cowTankDetailsBuilder_ == null) {
        return cowTankDetails_.size();
      } else {
        return cowTankDetailsBuilder_.getCount();
      }
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public com.cpdss.common.generated.discharge_plan.CowTankDetails getCowTankDetails(int index) {
      if (cowTankDetailsBuilder_ == null) {
        return cowTankDetails_.get(index);
      } else {
        return cowTankDetailsBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public Builder setCowTankDetails(
        int index, com.cpdss.common.generated.discharge_plan.CowTankDetails value) {
      if (cowTankDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCowTankDetailsIsMutable();
        cowTankDetails_.set(index, value);
        onChanged();
      } else {
        cowTankDetailsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public Builder setCowTankDetails(
        int index,
        com.cpdss.common.generated.discharge_plan.CowTankDetails.Builder builderForValue) {
      if (cowTankDetailsBuilder_ == null) {
        ensureCowTankDetailsIsMutable();
        cowTankDetails_.set(index, builderForValue.build());
        onChanged();
      } else {
        cowTankDetailsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public Builder addCowTankDetails(
        com.cpdss.common.generated.discharge_plan.CowTankDetails value) {
      if (cowTankDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCowTankDetailsIsMutable();
        cowTankDetails_.add(value);
        onChanged();
      } else {
        cowTankDetailsBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public Builder addCowTankDetails(
        int index, com.cpdss.common.generated.discharge_plan.CowTankDetails value) {
      if (cowTankDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCowTankDetailsIsMutable();
        cowTankDetails_.add(index, value);
        onChanged();
      } else {
        cowTankDetailsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public Builder addCowTankDetails(
        com.cpdss.common.generated.discharge_plan.CowTankDetails.Builder builderForValue) {
      if (cowTankDetailsBuilder_ == null) {
        ensureCowTankDetailsIsMutable();
        cowTankDetails_.add(builderForValue.build());
        onChanged();
      } else {
        cowTankDetailsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public Builder addCowTankDetails(
        int index,
        com.cpdss.common.generated.discharge_plan.CowTankDetails.Builder builderForValue) {
      if (cowTankDetailsBuilder_ == null) {
        ensureCowTankDetailsIsMutable();
        cowTankDetails_.add(index, builderForValue.build());
        onChanged();
      } else {
        cowTankDetailsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public Builder addAllCowTankDetails(
        java.lang.Iterable<? extends com.cpdss.common.generated.discharge_plan.CowTankDetails>
            values) {
      if (cowTankDetailsBuilder_ == null) {
        ensureCowTankDetailsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, cowTankDetails_);
        onChanged();
      } else {
        cowTankDetailsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public Builder clearCowTankDetails() {
      if (cowTankDetailsBuilder_ == null) {
        cowTankDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        cowTankDetailsBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public Builder removeCowTankDetails(int index) {
      if (cowTankDetailsBuilder_ == null) {
        ensureCowTankDetailsIsMutable();
        cowTankDetails_.remove(index);
        onChanged();
      } else {
        cowTankDetailsBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public com.cpdss.common.generated.discharge_plan.CowTankDetails.Builder
        getCowTankDetailsBuilder(int index) {
      return getCowTankDetailsFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public com.cpdss.common.generated.discharge_plan.CowTankDetailsOrBuilder
        getCowTankDetailsOrBuilder(int index) {
      if (cowTankDetailsBuilder_ == null) {
        return cowTankDetails_.get(index);
      } else {
        return cowTankDetailsBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public java.util.List<
            ? extends com.cpdss.common.generated.discharge_plan.CowTankDetailsOrBuilder>
        getCowTankDetailsOrBuilderList() {
      if (cowTankDetailsBuilder_ != null) {
        return cowTankDetailsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(cowTankDetails_);
      }
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public com.cpdss.common.generated.discharge_plan.CowTankDetails.Builder
        addCowTankDetailsBuilder() {
      return getCowTankDetailsFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.discharge_plan.CowTankDetails.getDefaultInstance());
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public com.cpdss.common.generated.discharge_plan.CowTankDetails.Builder
        addCowTankDetailsBuilder(int index) {
      return getCowTankDetailsFieldBuilder()
          .addBuilder(
              index, com.cpdss.common.generated.discharge_plan.CowTankDetails.getDefaultInstance());
    }
    /** <code>repeated .CowTankDetails cowTankDetails = 10;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.CowTankDetails.Builder>
        getCowTankDetailsBuilderList() {
      return getCowTankDetailsFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.CowTankDetails,
            com.cpdss.common.generated.discharge_plan.CowTankDetails.Builder,
            com.cpdss.common.generated.discharge_plan.CowTankDetailsOrBuilder>
        getCowTankDetailsFieldBuilder() {
      if (cowTankDetailsBuilder_ == null) {
        cowTankDetailsBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.CowTankDetails,
                com.cpdss.common.generated.discharge_plan.CowTankDetails.Builder,
                com.cpdss.common.generated.discharge_plan.CowTankDetailsOrBuilder>(
                cowTankDetails_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        cowTankDetails_ = null;
      }
      return cowTankDetailsBuilder_;
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

    // @@protoc_insertion_point(builder_scope:CowPlan)
  }

  // @@protoc_insertion_point(class_scope:CowPlan)
  private static final com.cpdss.common.generated.discharge_plan.CowPlan DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.CowPlan();
  }

  public static com.cpdss.common.generated.discharge_plan.CowPlan getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CowPlan> PARSER =
      new com.google.protobuf.AbstractParser<CowPlan>() {
        @java.lang.Override
        public CowPlan parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new CowPlan(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<CowPlan> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CowPlan> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.CowPlan getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}