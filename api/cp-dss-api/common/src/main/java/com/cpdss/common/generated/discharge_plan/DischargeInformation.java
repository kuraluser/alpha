/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargeInformation} */
public final class DischargeInformation extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargeInformation)
    DischargeInformationOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargeInformation.newBuilder() to construct.
  private DischargeInformation(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargeInformation() {
    berthDetails_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargeInformation();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargeInformation(
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
                      com.cpdss.common.generated.Common.ResponseStatus.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(responseStatus_);
                responseStatus_ = subBuilder.buildPartial();
              }

              break;
            }
          case 18:
            {
              com.cpdss.common.generated.discharge_plan.DischargeDetails.Builder subBuilder = null;
              if (dischargeDetails_ != null) {
                subBuilder = dischargeDetails_.toBuilder();
              }
              dischargeDetails_ =
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.DischargeDetails.parser(),
                      extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(dischargeDetails_);
                dischargeDetails_ = subBuilder.buildPartial();
              }

              break;
            }
          case 26:
            {
              com.cpdss.common.generated.discharge_plan.DischargeRates.Builder subBuilder = null;
              if (dischargeRate_ != null) {
                subBuilder = dischargeRate_.toBuilder();
              }
              dischargeRate_ =
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.DischargeRates.parser(),
                      extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(dischargeRate_);
                dischargeRate_ = subBuilder.buildPartial();
              }

              break;
            }
          case 34:
            {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                berthDetails_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.discharge_plan.DischargeBerths>();
                mutable_bitField0_ |= 0x00000001;
              }
              berthDetails_.add(
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.DischargeBerths.parser(),
                      extensionRegistry));
              break;
            }
          case 42:
            {
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages.Builder
                  subBuilder = null;
              if (dischargeStage_ != null) {
                subBuilder = dischargeStage_.toBuilder();
              }
              dischargeStage_ =
                  input.readMessage(
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages
                          .parser(),
                      extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(dischargeStage_);
                dischargeStage_ = subBuilder.buildPartial();
              }

              break;
            }
          case 50:
            {
              com.cpdss.common.generated.discharge_plan.DischargeDelay.Builder subBuilder = null;
              if (dischargeDelay_ != null) {
                subBuilder = dischargeDelay_.toBuilder();
              }
              dischargeDelay_ =
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.DischargeDelay.parser(),
                      extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(dischargeDelay_);
                dischargeDelay_ = subBuilder.buildPartial();
              }

              break;
            }
          case 58:
            {
              com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.Builder subBuilder =
                  null;
              if (postDischargeStageTime_ != null) {
                subBuilder = postDischargeStageTime_.toBuilder();
              }
              postDischargeStageTime_ =
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.parser(),
                      extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(postDischargeStageTime_);
                postDischargeStageTime_ = subBuilder.buildPartial();
              }

              break;
            }
          case 64:
            {
              dischargeInfoId_ = input.readInt64();
              break;
            }
          case 72:
            {
              synopticTableId_ = input.readInt64();
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
        berthDetails_ = java.util.Collections.unmodifiableList(berthDetails_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeInformation_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargeInformation_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargeInformation.class,
            com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder.class);
  }

  public static final int DISCHARGEINFOID_FIELD_NUMBER = 8;
  private long dischargeInfoId_;
  /**
   * <code>int64 dischargeInfoId = 8;</code>
   *
   * @return The dischargeInfoId.
   */
  public long getDischargeInfoId() {
    return dischargeInfoId_;
  }

  public static final int SYNOPTICTABLEID_FIELD_NUMBER = 9;
  private long synopticTableId_;
  /**
   * <code>int64 synopticTableId = 9;</code>
   *
   * @return The synopticTableId.
   */
  public long getSynopticTableId() {
    return synopticTableId_;
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

  public static final int DISCHARGEDETAILS_FIELD_NUMBER = 2;
  private com.cpdss.common.generated.discharge_plan.DischargeDetails dischargeDetails_;
  /**
   * <code>.DischargeDetails dischargeDetails = 2;</code>
   *
   * @return Whether the dischargeDetails field is set.
   */
  public boolean hasDischargeDetails() {
    return dischargeDetails_ != null;
  }
  /**
   * <code>.DischargeDetails dischargeDetails = 2;</code>
   *
   * @return The dischargeDetails.
   */
  public com.cpdss.common.generated.discharge_plan.DischargeDetails getDischargeDetails() {
    return dischargeDetails_ == null
        ? com.cpdss.common.generated.discharge_plan.DischargeDetails.getDefaultInstance()
        : dischargeDetails_;
  }
  /** <code>.DischargeDetails dischargeDetails = 2;</code> */
  public com.cpdss.common.generated.discharge_plan.DischargeDetailsOrBuilder
      getDischargeDetailsOrBuilder() {
    return getDischargeDetails();
  }

  public static final int DISCHARGERATE_FIELD_NUMBER = 3;
  private com.cpdss.common.generated.discharge_plan.DischargeRates dischargeRate_;
  /**
   * <code>.DischargeRates dischargeRate = 3;</code>
   *
   * @return Whether the dischargeRate field is set.
   */
  public boolean hasDischargeRate() {
    return dischargeRate_ != null;
  }
  /**
   * <code>.DischargeRates dischargeRate = 3;</code>
   *
   * @return The dischargeRate.
   */
  public com.cpdss.common.generated.discharge_plan.DischargeRates getDischargeRate() {
    return dischargeRate_ == null
        ? com.cpdss.common.generated.discharge_plan.DischargeRates.getDefaultInstance()
        : dischargeRate_;
  }
  /** <code>.DischargeRates dischargeRate = 3;</code> */
  public com.cpdss.common.generated.discharge_plan.DischargeRatesOrBuilder
      getDischargeRateOrBuilder() {
    return getDischargeRate();
  }

  public static final int BERTHDETAILS_FIELD_NUMBER = 4;
  private java.util.List<com.cpdss.common.generated.discharge_plan.DischargeBerths> berthDetails_;
  /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
  public java.util.List<com.cpdss.common.generated.discharge_plan.DischargeBerths>
      getBerthDetailsList() {
    return berthDetails_;
  }
  /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
  public java.util.List<
          ? extends com.cpdss.common.generated.discharge_plan.DischargeBerthsOrBuilder>
      getBerthDetailsOrBuilderList() {
    return berthDetails_;
  }
  /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
  public int getBerthDetailsCount() {
    return berthDetails_.size();
  }
  /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
  public com.cpdss.common.generated.discharge_plan.DischargeBerths getBerthDetails(int index) {
    return berthDetails_.get(index);
  }
  /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
  public com.cpdss.common.generated.discharge_plan.DischargeBerthsOrBuilder
      getBerthDetailsOrBuilder(int index) {
    return berthDetails_.get(index);
  }

  public static final int DISCHARGESTAGE_FIELD_NUMBER = 5;
  private com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages dischargeStage_;
  /**
   *
   *
   * <pre>
   * reusing
   * </pre>
   *
   * <code>.LoadingStages dischargeStage = 5;</code>
   *
   * @return Whether the dischargeStage field is set.
   */
  public boolean hasDischargeStage() {
    return dischargeStage_ != null;
  }
  /**
   *
   *
   * <pre>
   * reusing
   * </pre>
   *
   * <code>.LoadingStages dischargeStage = 5;</code>
   *
   * @return The dischargeStage.
   */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages
      getDischargeStage() {
    return dischargeStage_ == null
        ? com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages
            .getDefaultInstance()
        : dischargeStage_;
  }
  /**
   *
   *
   * <pre>
   * reusing
   * </pre>
   *
   * <code>.LoadingStages dischargeStage = 5;</code>
   */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStagesOrBuilder
      getDischargeStageOrBuilder() {
    return getDischargeStage();
  }

  public static final int DISCHARGEDELAY_FIELD_NUMBER = 6;
  private com.cpdss.common.generated.discharge_plan.DischargeDelay dischargeDelay_;
  /**
   * <code>.DischargeDelay dischargeDelay = 6;</code>
   *
   * @return Whether the dischargeDelay field is set.
   */
  public boolean hasDischargeDelay() {
    return dischargeDelay_ != null;
  }
  /**
   * <code>.DischargeDelay dischargeDelay = 6;</code>
   *
   * @return The dischargeDelay.
   */
  public com.cpdss.common.generated.discharge_plan.DischargeDelay getDischargeDelay() {
    return dischargeDelay_ == null
        ? com.cpdss.common.generated.discharge_plan.DischargeDelay.getDefaultInstance()
        : dischargeDelay_;
  }
  /** <code>.DischargeDelay dischargeDelay = 6;</code> */
  public com.cpdss.common.generated.discharge_plan.DischargeDelayOrBuilder
      getDischargeDelayOrBuilder() {
    return getDischargeDelay();
  }

  public static final int POSTDISCHARGESTAGETIME_FIELD_NUMBER = 7;
  private com.cpdss.common.generated.discharge_plan.PostDischargeStageTime postDischargeStageTime_;
  /**
   * <code>.PostDischargeStageTime postDischargeStageTime = 7;</code>
   *
   * @return Whether the postDischargeStageTime field is set.
   */
  public boolean hasPostDischargeStageTime() {
    return postDischargeStageTime_ != null;
  }
  /**
   * <code>.PostDischargeStageTime postDischargeStageTime = 7;</code>
   *
   * @return The postDischargeStageTime.
   */
  public com.cpdss.common.generated.discharge_plan.PostDischargeStageTime
      getPostDischargeStageTime() {
    return postDischargeStageTime_ == null
        ? com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.getDefaultInstance()
        : postDischargeStageTime_;
  }
  /** <code>.PostDischargeStageTime postDischargeStageTime = 7;</code> */
  public com.cpdss.common.generated.discharge_plan.PostDischargeStageTimeOrBuilder
      getPostDischargeStageTimeOrBuilder() {
    return getPostDischargeStageTime();
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
    if (dischargeDetails_ != null) {
      output.writeMessage(2, getDischargeDetails());
    }
    if (dischargeRate_ != null) {
      output.writeMessage(3, getDischargeRate());
    }
    for (int i = 0; i < berthDetails_.size(); i++) {
      output.writeMessage(4, berthDetails_.get(i));
    }
    if (dischargeStage_ != null) {
      output.writeMessage(5, getDischargeStage());
    }
    if (dischargeDelay_ != null) {
      output.writeMessage(6, getDischargeDelay());
    }
    if (postDischargeStageTime_ != null) {
      output.writeMessage(7, getPostDischargeStageTime());
    }
    if (dischargeInfoId_ != 0L) {
      output.writeInt64(8, dischargeInfoId_);
    }
    if (synopticTableId_ != 0L) {
      output.writeInt64(9, synopticTableId_);
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
    if (dischargeDetails_ != null) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getDischargeDetails());
    }
    if (dischargeRate_ != null) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, getDischargeRate());
    }
    for (int i = 0; i < berthDetails_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(4, berthDetails_.get(i));
    }
    if (dischargeStage_ != null) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(5, getDischargeStage());
    }
    if (dischargeDelay_ != null) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(6, getDischargeDelay());
    }
    if (postDischargeStageTime_ != null) {
      size +=
          com.google.protobuf.CodedOutputStream.computeMessageSize(7, getPostDischargeStageTime());
    }
    if (dischargeInfoId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(8, dischargeInfoId_);
    }
    if (synopticTableId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(9, synopticTableId_);
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargeInformation)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargeInformation other =
        (com.cpdss.common.generated.discharge_plan.DischargeInformation) obj;

    if (getDischargeInfoId() != other.getDischargeInfoId()) return false;
    if (getSynopticTableId() != other.getSynopticTableId()) return false;
    if (hasResponseStatus() != other.hasResponseStatus()) return false;
    if (hasResponseStatus()) {
      if (!getResponseStatus().equals(other.getResponseStatus())) return false;
    }
    if (hasDischargeDetails() != other.hasDischargeDetails()) return false;
    if (hasDischargeDetails()) {
      if (!getDischargeDetails().equals(other.getDischargeDetails())) return false;
    }
    if (hasDischargeRate() != other.hasDischargeRate()) return false;
    if (hasDischargeRate()) {
      if (!getDischargeRate().equals(other.getDischargeRate())) return false;
    }
    if (!getBerthDetailsList().equals(other.getBerthDetailsList())) return false;
    if (hasDischargeStage() != other.hasDischargeStage()) return false;
    if (hasDischargeStage()) {
      if (!getDischargeStage().equals(other.getDischargeStage())) return false;
    }
    if (hasDischargeDelay() != other.hasDischargeDelay()) return false;
    if (hasDischargeDelay()) {
      if (!getDischargeDelay().equals(other.getDischargeDelay())) return false;
    }
    if (hasPostDischargeStageTime() != other.hasPostDischargeStageTime()) return false;
    if (hasPostDischargeStageTime()) {
      if (!getPostDischargeStageTime().equals(other.getPostDischargeStageTime())) return false;
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
    hash = (37 * hash) + DISCHARGEINFOID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargeInfoId());
    hash = (37 * hash) + SYNOPTICTABLEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getSynopticTableId());
    if (hasResponseStatus()) {
      hash = (37 * hash) + RESPONSESTATUS_FIELD_NUMBER;
      hash = (53 * hash) + getResponseStatus().hashCode();
    }
    if (hasDischargeDetails()) {
      hash = (37 * hash) + DISCHARGEDETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getDischargeDetails().hashCode();
    }
    if (hasDischargeRate()) {
      hash = (37 * hash) + DISCHARGERATE_FIELD_NUMBER;
      hash = (53 * hash) + getDischargeRate().hashCode();
    }
    if (getBerthDetailsCount() > 0) {
      hash = (37 * hash) + BERTHDETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getBerthDetailsList().hashCode();
    }
    if (hasDischargeStage()) {
      hash = (37 * hash) + DISCHARGESTAGE_FIELD_NUMBER;
      hash = (53 * hash) + getDischargeStage().hashCode();
    }
    if (hasDischargeDelay()) {
      hash = (37 * hash) + DISCHARGEDELAY_FIELD_NUMBER;
      hash = (53 * hash) + getDischargeDelay().hashCode();
    }
    if (hasPostDischargeStageTime()) {
      hash = (37 * hash) + POSTDISCHARGESTAGETIME_FIELD_NUMBER;
      hash = (53 * hash) + getPostDischargeStageTime().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseFrom(
      byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargeInformation prototype) {
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
  /** Protobuf type {@code DischargeInformation} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargeInformation)
      com.cpdss.common.generated.discharge_plan.DischargeInformationOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInformation_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInformation_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargeInformation.class,
              com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.DischargeInformation.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getBerthDetailsFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      dischargeInfoId_ = 0L;

      synopticTableId_ = 0L;

      if (responseStatusBuilder_ == null) {
        responseStatus_ = null;
      } else {
        responseStatus_ = null;
        responseStatusBuilder_ = null;
      }
      if (dischargeDetailsBuilder_ == null) {
        dischargeDetails_ = null;
      } else {
        dischargeDetails_ = null;
        dischargeDetailsBuilder_ = null;
      }
      if (dischargeRateBuilder_ == null) {
        dischargeRate_ = null;
      } else {
        dischargeRate_ = null;
        dischargeRateBuilder_ = null;
      }
      if (berthDetailsBuilder_ == null) {
        berthDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        berthDetailsBuilder_.clear();
      }
      if (dischargeStageBuilder_ == null) {
        dischargeStage_ = null;
      } else {
        dischargeStage_ = null;
        dischargeStageBuilder_ = null;
      }
      if (dischargeDelayBuilder_ == null) {
        dischargeDelay_ = null;
      } else {
        dischargeDelay_ = null;
        dischargeDelayBuilder_ = null;
      }
      if (postDischargeStageTimeBuilder_ == null) {
        postDischargeStageTime_ = null;
      } else {
        postDischargeStageTime_ = null;
        postDischargeStageTimeBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargeInformation_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInformation
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargeInformation.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInformation build() {
      com.cpdss.common.generated.discharge_plan.DischargeInformation result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargeInformation buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargeInformation result =
          new com.cpdss.common.generated.discharge_plan.DischargeInformation(this);
      int from_bitField0_ = bitField0_;
      result.dischargeInfoId_ = dischargeInfoId_;
      result.synopticTableId_ = synopticTableId_;
      if (responseStatusBuilder_ == null) {
        result.responseStatus_ = responseStatus_;
      } else {
        result.responseStatus_ = responseStatusBuilder_.build();
      }
      if (dischargeDetailsBuilder_ == null) {
        result.dischargeDetails_ = dischargeDetails_;
      } else {
        result.dischargeDetails_ = dischargeDetailsBuilder_.build();
      }
      if (dischargeRateBuilder_ == null) {
        result.dischargeRate_ = dischargeRate_;
      } else {
        result.dischargeRate_ = dischargeRateBuilder_.build();
      }
      if (berthDetailsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          berthDetails_ = java.util.Collections.unmodifiableList(berthDetails_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.berthDetails_ = berthDetails_;
      } else {
        result.berthDetails_ = berthDetailsBuilder_.build();
      }
      if (dischargeStageBuilder_ == null) {
        result.dischargeStage_ = dischargeStage_;
      } else {
        result.dischargeStage_ = dischargeStageBuilder_.build();
      }
      if (dischargeDelayBuilder_ == null) {
        result.dischargeDelay_ = dischargeDelay_;
      } else {
        result.dischargeDelay_ = dischargeDelayBuilder_.build();
      }
      if (postDischargeStageTimeBuilder_ == null) {
        result.postDischargeStageTime_ = postDischargeStageTime_;
      } else {
        result.postDischargeStageTime_ = postDischargeStageTimeBuilder_.build();
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargeInformation) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.DischargeInformation) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.DischargeInformation other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargeInformation.getDefaultInstance())
        return this;
      if (other.getDischargeInfoId() != 0L) {
        setDischargeInfoId(other.getDischargeInfoId());
      }
      if (other.getSynopticTableId() != 0L) {
        setSynopticTableId(other.getSynopticTableId());
      }
      if (other.hasResponseStatus()) {
        mergeResponseStatus(other.getResponseStatus());
      }
      if (other.hasDischargeDetails()) {
        mergeDischargeDetails(other.getDischargeDetails());
      }
      if (other.hasDischargeRate()) {
        mergeDischargeRate(other.getDischargeRate());
      }
      if (berthDetailsBuilder_ == null) {
        if (!other.berthDetails_.isEmpty()) {
          if (berthDetails_.isEmpty()) {
            berthDetails_ = other.berthDetails_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureBerthDetailsIsMutable();
            berthDetails_.addAll(other.berthDetails_);
          }
          onChanged();
        }
      } else {
        if (!other.berthDetails_.isEmpty()) {
          if (berthDetailsBuilder_.isEmpty()) {
            berthDetailsBuilder_.dispose();
            berthDetailsBuilder_ = null;
            berthDetails_ = other.berthDetails_;
            bitField0_ = (bitField0_ & ~0x00000001);
            berthDetailsBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getBerthDetailsFieldBuilder()
                    : null;
          } else {
            berthDetailsBuilder_.addAllMessages(other.berthDetails_);
          }
        }
      }
      if (other.hasDischargeStage()) {
        mergeDischargeStage(other.getDischargeStage());
      }
      if (other.hasDischargeDelay()) {
        mergeDischargeDelay(other.getDischargeDelay());
      }
      if (other.hasPostDischargeStageTime()) {
        mergePostDischargeStageTime(other.getPostDischargeStageTime());
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
      com.cpdss.common.generated.discharge_plan.DischargeInformation parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargeInformation)
                e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int bitField0_;

    private long dischargeInfoId_;
    /**
     * <code>int64 dischargeInfoId = 8;</code>
     *
     * @return The dischargeInfoId.
     */
    public long getDischargeInfoId() {
      return dischargeInfoId_;
    }
    /**
     * <code>int64 dischargeInfoId = 8;</code>
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
     * <code>int64 dischargeInfoId = 8;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargeInfoId() {

      dischargeInfoId_ = 0L;
      onChanged();
      return this;
    }

    private long synopticTableId_;
    /**
     * <code>int64 synopticTableId = 9;</code>
     *
     * @return The synopticTableId.
     */
    public long getSynopticTableId() {
      return synopticTableId_;
    }
    /**
     * <code>int64 synopticTableId = 9;</code>
     *
     * @param value The synopticTableId to set.
     * @return This builder for chaining.
     */
    public Builder setSynopticTableId(long value) {

      synopticTableId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 synopticTableId = 9;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearSynopticTableId() {

      synopticTableId_ = 0L;
      onChanged();
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
    public com.cpdss.common.generated.Common.ResponseStatusOrBuilder getResponseStatusOrBuilder() {
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

    private com.cpdss.common.generated.discharge_plan.DischargeDetails dischargeDetails_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DischargeDetails,
            com.cpdss.common.generated.discharge_plan.DischargeDetails.Builder,
            com.cpdss.common.generated.discharge_plan.DischargeDetailsOrBuilder>
        dischargeDetailsBuilder_;
    /**
     * <code>.DischargeDetails dischargeDetails = 2;</code>
     *
     * @return Whether the dischargeDetails field is set.
     */
    public boolean hasDischargeDetails() {
      return dischargeDetailsBuilder_ != null || dischargeDetails_ != null;
    }
    /**
     * <code>.DischargeDetails dischargeDetails = 2;</code>
     *
     * @return The dischargeDetails.
     */
    public com.cpdss.common.generated.discharge_plan.DischargeDetails getDischargeDetails() {
      if (dischargeDetailsBuilder_ == null) {
        return dischargeDetails_ == null
            ? com.cpdss.common.generated.discharge_plan.DischargeDetails.getDefaultInstance()
            : dischargeDetails_;
      } else {
        return dischargeDetailsBuilder_.getMessage();
      }
    }
    /** <code>.DischargeDetails dischargeDetails = 2;</code> */
    public Builder setDischargeDetails(
        com.cpdss.common.generated.discharge_plan.DischargeDetails value) {
      if (dischargeDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        dischargeDetails_ = value;
        onChanged();
      } else {
        dischargeDetailsBuilder_.setMessage(value);
      }

      return this;
    }
    /** <code>.DischargeDetails dischargeDetails = 2;</code> */
    public Builder setDischargeDetails(
        com.cpdss.common.generated.discharge_plan.DischargeDetails.Builder builderForValue) {
      if (dischargeDetailsBuilder_ == null) {
        dischargeDetails_ = builderForValue.build();
        onChanged();
      } else {
        dischargeDetailsBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /** <code>.DischargeDetails dischargeDetails = 2;</code> */
    public Builder mergeDischargeDetails(
        com.cpdss.common.generated.discharge_plan.DischargeDetails value) {
      if (dischargeDetailsBuilder_ == null) {
        if (dischargeDetails_ != null) {
          dischargeDetails_ =
              com.cpdss.common.generated.discharge_plan.DischargeDetails.newBuilder(
                      dischargeDetails_)
                  .mergeFrom(value)
                  .buildPartial();
        } else {
          dischargeDetails_ = value;
        }
        onChanged();
      } else {
        dischargeDetailsBuilder_.mergeFrom(value);
      }

      return this;
    }
    /** <code>.DischargeDetails dischargeDetails = 2;</code> */
    public Builder clearDischargeDetails() {
      if (dischargeDetailsBuilder_ == null) {
        dischargeDetails_ = null;
        onChanged();
      } else {
        dischargeDetails_ = null;
        dischargeDetailsBuilder_ = null;
      }

      return this;
    }
    /** <code>.DischargeDetails dischargeDetails = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeDetails.Builder
        getDischargeDetailsBuilder() {

      onChanged();
      return getDischargeDetailsFieldBuilder().getBuilder();
    }
    /** <code>.DischargeDetails dischargeDetails = 2;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeDetailsOrBuilder
        getDischargeDetailsOrBuilder() {
      if (dischargeDetailsBuilder_ != null) {
        return dischargeDetailsBuilder_.getMessageOrBuilder();
      } else {
        return dischargeDetails_ == null
            ? com.cpdss.common.generated.discharge_plan.DischargeDetails.getDefaultInstance()
            : dischargeDetails_;
      }
    }
    /** <code>.DischargeDetails dischargeDetails = 2;</code> */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DischargeDetails,
            com.cpdss.common.generated.discharge_plan.DischargeDetails.Builder,
            com.cpdss.common.generated.discharge_plan.DischargeDetailsOrBuilder>
        getDischargeDetailsFieldBuilder() {
      if (dischargeDetailsBuilder_ == null) {
        dischargeDetailsBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.DischargeDetails,
                com.cpdss.common.generated.discharge_plan.DischargeDetails.Builder,
                com.cpdss.common.generated.discharge_plan.DischargeDetailsOrBuilder>(
                getDischargeDetails(), getParentForChildren(), isClean());
        dischargeDetails_ = null;
      }
      return dischargeDetailsBuilder_;
    }

    private com.cpdss.common.generated.discharge_plan.DischargeRates dischargeRate_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DischargeRates,
            com.cpdss.common.generated.discharge_plan.DischargeRates.Builder,
            com.cpdss.common.generated.discharge_plan.DischargeRatesOrBuilder>
        dischargeRateBuilder_;
    /**
     * <code>.DischargeRates dischargeRate = 3;</code>
     *
     * @return Whether the dischargeRate field is set.
     */
    public boolean hasDischargeRate() {
      return dischargeRateBuilder_ != null || dischargeRate_ != null;
    }
    /**
     * <code>.DischargeRates dischargeRate = 3;</code>
     *
     * @return The dischargeRate.
     */
    public com.cpdss.common.generated.discharge_plan.DischargeRates getDischargeRate() {
      if (dischargeRateBuilder_ == null) {
        return dischargeRate_ == null
            ? com.cpdss.common.generated.discharge_plan.DischargeRates.getDefaultInstance()
            : dischargeRate_;
      } else {
        return dischargeRateBuilder_.getMessage();
      }
    }
    /** <code>.DischargeRates dischargeRate = 3;</code> */
    public Builder setDischargeRate(
        com.cpdss.common.generated.discharge_plan.DischargeRates value) {
      if (dischargeRateBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        dischargeRate_ = value;
        onChanged();
      } else {
        dischargeRateBuilder_.setMessage(value);
      }

      return this;
    }
    /** <code>.DischargeRates dischargeRate = 3;</code> */
    public Builder setDischargeRate(
        com.cpdss.common.generated.discharge_plan.DischargeRates.Builder builderForValue) {
      if (dischargeRateBuilder_ == null) {
        dischargeRate_ = builderForValue.build();
        onChanged();
      } else {
        dischargeRateBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /** <code>.DischargeRates dischargeRate = 3;</code> */
    public Builder mergeDischargeRate(
        com.cpdss.common.generated.discharge_plan.DischargeRates value) {
      if (dischargeRateBuilder_ == null) {
        if (dischargeRate_ != null) {
          dischargeRate_ =
              com.cpdss.common.generated.discharge_plan.DischargeRates.newBuilder(dischargeRate_)
                  .mergeFrom(value)
                  .buildPartial();
        } else {
          dischargeRate_ = value;
        }
        onChanged();
      } else {
        dischargeRateBuilder_.mergeFrom(value);
      }

      return this;
    }
    /** <code>.DischargeRates dischargeRate = 3;</code> */
    public Builder clearDischargeRate() {
      if (dischargeRateBuilder_ == null) {
        dischargeRate_ = null;
        onChanged();
      } else {
        dischargeRate_ = null;
        dischargeRateBuilder_ = null;
      }

      return this;
    }
    /** <code>.DischargeRates dischargeRate = 3;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeRates.Builder
        getDischargeRateBuilder() {

      onChanged();
      return getDischargeRateFieldBuilder().getBuilder();
    }
    /** <code>.DischargeRates dischargeRate = 3;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeRatesOrBuilder
        getDischargeRateOrBuilder() {
      if (dischargeRateBuilder_ != null) {
        return dischargeRateBuilder_.getMessageOrBuilder();
      } else {
        return dischargeRate_ == null
            ? com.cpdss.common.generated.discharge_plan.DischargeRates.getDefaultInstance()
            : dischargeRate_;
      }
    }
    /** <code>.DischargeRates dischargeRate = 3;</code> */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DischargeRates,
            com.cpdss.common.generated.discharge_plan.DischargeRates.Builder,
            com.cpdss.common.generated.discharge_plan.DischargeRatesOrBuilder>
        getDischargeRateFieldBuilder() {
      if (dischargeRateBuilder_ == null) {
        dischargeRateBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.DischargeRates,
                com.cpdss.common.generated.discharge_plan.DischargeRates.Builder,
                com.cpdss.common.generated.discharge_plan.DischargeRatesOrBuilder>(
                getDischargeRate(), getParentForChildren(), isClean());
        dischargeRate_ = null;
      }
      return dischargeRateBuilder_;
    }

    private java.util.List<com.cpdss.common.generated.discharge_plan.DischargeBerths>
        berthDetails_ = java.util.Collections.emptyList();

    private void ensureBerthDetailsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        berthDetails_ =
            new java.util.ArrayList<com.cpdss.common.generated.discharge_plan.DischargeBerths>(
                berthDetails_);
        bitField0_ |= 0x00000001;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DischargeBerths,
            com.cpdss.common.generated.discharge_plan.DischargeBerths.Builder,
            com.cpdss.common.generated.discharge_plan.DischargeBerthsOrBuilder>
        berthDetailsBuilder_;

    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.DischargeBerths>
        getBerthDetailsList() {
      if (berthDetailsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(berthDetails_);
      } else {
        return berthDetailsBuilder_.getMessageList();
      }
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public int getBerthDetailsCount() {
      if (berthDetailsBuilder_ == null) {
        return berthDetails_.size();
      } else {
        return berthDetailsBuilder_.getCount();
      }
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeBerths getBerthDetails(int index) {
      if (berthDetailsBuilder_ == null) {
        return berthDetails_.get(index);
      } else {
        return berthDetailsBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public Builder setBerthDetails(
        int index, com.cpdss.common.generated.discharge_plan.DischargeBerths value) {
      if (berthDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBerthDetailsIsMutable();
        berthDetails_.set(index, value);
        onChanged();
      } else {
        berthDetailsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public Builder setBerthDetails(
        int index,
        com.cpdss.common.generated.discharge_plan.DischargeBerths.Builder builderForValue) {
      if (berthDetailsBuilder_ == null) {
        ensureBerthDetailsIsMutable();
        berthDetails_.set(index, builderForValue.build());
        onChanged();
      } else {
        berthDetailsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public Builder addBerthDetails(
        com.cpdss.common.generated.discharge_plan.DischargeBerths value) {
      if (berthDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBerthDetailsIsMutable();
        berthDetails_.add(value);
        onChanged();
      } else {
        berthDetailsBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public Builder addBerthDetails(
        int index, com.cpdss.common.generated.discharge_plan.DischargeBerths value) {
      if (berthDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBerthDetailsIsMutable();
        berthDetails_.add(index, value);
        onChanged();
      } else {
        berthDetailsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public Builder addBerthDetails(
        com.cpdss.common.generated.discharge_plan.DischargeBerths.Builder builderForValue) {
      if (berthDetailsBuilder_ == null) {
        ensureBerthDetailsIsMutable();
        berthDetails_.add(builderForValue.build());
        onChanged();
      } else {
        berthDetailsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public Builder addBerthDetails(
        int index,
        com.cpdss.common.generated.discharge_plan.DischargeBerths.Builder builderForValue) {
      if (berthDetailsBuilder_ == null) {
        ensureBerthDetailsIsMutable();
        berthDetails_.add(index, builderForValue.build());
        onChanged();
      } else {
        berthDetailsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public Builder addAllBerthDetails(
        java.lang.Iterable<? extends com.cpdss.common.generated.discharge_plan.DischargeBerths>
            values) {
      if (berthDetailsBuilder_ == null) {
        ensureBerthDetailsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, berthDetails_);
        onChanged();
      } else {
        berthDetailsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public Builder clearBerthDetails() {
      if (berthDetailsBuilder_ == null) {
        berthDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        berthDetailsBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public Builder removeBerthDetails(int index) {
      if (berthDetailsBuilder_ == null) {
        ensureBerthDetailsIsMutable();
        berthDetails_.remove(index);
        onChanged();
      } else {
        berthDetailsBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeBerths.Builder getBerthDetailsBuilder(
        int index) {
      return getBerthDetailsFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeBerthsOrBuilder
        getBerthDetailsOrBuilder(int index) {
      if (berthDetailsBuilder_ == null) {
        return berthDetails_.get(index);
      } else {
        return berthDetailsBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public java.util.List<
            ? extends com.cpdss.common.generated.discharge_plan.DischargeBerthsOrBuilder>
        getBerthDetailsOrBuilderList() {
      if (berthDetailsBuilder_ != null) {
        return berthDetailsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(berthDetails_);
      }
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeBerths.Builder
        addBerthDetailsBuilder() {
      return getBerthDetailsFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.discharge_plan.DischargeBerths.getDefaultInstance());
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeBerths.Builder addBerthDetailsBuilder(
        int index) {
      return getBerthDetailsFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.discharge_plan.DischargeBerths.getDefaultInstance());
    }
    /** <code>repeated .DischargeBerths berthDetails = 4;</code> */
    public java.util.List<com.cpdss.common.generated.discharge_plan.DischargeBerths.Builder>
        getBerthDetailsBuilderList() {
      return getBerthDetailsFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DischargeBerths,
            com.cpdss.common.generated.discharge_plan.DischargeBerths.Builder,
            com.cpdss.common.generated.discharge_plan.DischargeBerthsOrBuilder>
        getBerthDetailsFieldBuilder() {
      if (berthDetailsBuilder_ == null) {
        berthDetailsBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.DischargeBerths,
                com.cpdss.common.generated.discharge_plan.DischargeBerths.Builder,
                com.cpdss.common.generated.discharge_plan.DischargeBerthsOrBuilder>(
                berthDetails_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
        berthDetails_ = null;
      }
      return berthDetailsBuilder_;
    }

    private com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages dischargeStage_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStagesOrBuilder>
        dischargeStageBuilder_;
    /**
     *
     *
     * <pre>
     * reusing
     * </pre>
     *
     * <code>.LoadingStages dischargeStage = 5;</code>
     *
     * @return Whether the dischargeStage field is set.
     */
    public boolean hasDischargeStage() {
      return dischargeStageBuilder_ != null || dischargeStage_ != null;
    }
    /**
     *
     *
     * <pre>
     * reusing
     * </pre>
     *
     * <code>.LoadingStages dischargeStage = 5;</code>
     *
     * @return The dischargeStage.
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages
        getDischargeStage() {
      if (dischargeStageBuilder_ == null) {
        return dischargeStage_ == null
            ? com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages
                .getDefaultInstance()
            : dischargeStage_;
      } else {
        return dischargeStageBuilder_.getMessage();
      }
    }
    /**
     *
     *
     * <pre>
     * reusing
     * </pre>
     *
     * <code>.LoadingStages dischargeStage = 5;</code>
     */
    public Builder setDischargeStage(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages value) {
      if (dischargeStageBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        dischargeStage_ = value;
        onChanged();
      } else {
        dischargeStageBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     *
     *
     * <pre>
     * reusing
     * </pre>
     *
     * <code>.LoadingStages dischargeStage = 5;</code>
     */
    public Builder setDischargeStage(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages.Builder
            builderForValue) {
      if (dischargeStageBuilder_ == null) {
        dischargeStage_ = builderForValue.build();
        onChanged();
      } else {
        dischargeStageBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     *
     *
     * <pre>
     * reusing
     * </pre>
     *
     * <code>.LoadingStages dischargeStage = 5;</code>
     */
    public Builder mergeDischargeStage(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages value) {
      if (dischargeStageBuilder_ == null) {
        if (dischargeStage_ != null) {
          dischargeStage_ =
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages.newBuilder(
                      dischargeStage_)
                  .mergeFrom(value)
                  .buildPartial();
        } else {
          dischargeStage_ = value;
        }
        onChanged();
      } else {
        dischargeStageBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     *
     *
     * <pre>
     * reusing
     * </pre>
     *
     * <code>.LoadingStages dischargeStage = 5;</code>
     */
    public Builder clearDischargeStage() {
      if (dischargeStageBuilder_ == null) {
        dischargeStage_ = null;
        onChanged();
      } else {
        dischargeStage_ = null;
        dischargeStageBuilder_ = null;
      }

      return this;
    }
    /**
     *
     *
     * <pre>
     * reusing
     * </pre>
     *
     * <code>.LoadingStages dischargeStage = 5;</code>
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages.Builder
        getDischargeStageBuilder() {

      onChanged();
      return getDischargeStageFieldBuilder().getBuilder();
    }
    /**
     *
     *
     * <pre>
     * reusing
     * </pre>
     *
     * <code>.LoadingStages dischargeStage = 5;</code>
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStagesOrBuilder
        getDischargeStageOrBuilder() {
      if (dischargeStageBuilder_ != null) {
        return dischargeStageBuilder_.getMessageOrBuilder();
      } else {
        return dischargeStage_ == null
            ? com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages
                .getDefaultInstance()
            : dischargeStage_;
      }
    }
    /**
     *
     *
     * <pre>
     * reusing
     * </pre>
     *
     * <code>.LoadingStages dischargeStage = 5;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStagesOrBuilder>
        getDischargeStageFieldBuilder() {
      if (dischargeStageBuilder_ == null) {
        dischargeStageBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages.Builder,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStagesOrBuilder>(
                getDischargeStage(), getParentForChildren(), isClean());
        dischargeStage_ = null;
      }
      return dischargeStageBuilder_;
    }

    private com.cpdss.common.generated.discharge_plan.DischargeDelay dischargeDelay_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DischargeDelay,
            com.cpdss.common.generated.discharge_plan.DischargeDelay.Builder,
            com.cpdss.common.generated.discharge_plan.DischargeDelayOrBuilder>
        dischargeDelayBuilder_;
    /**
     * <code>.DischargeDelay dischargeDelay = 6;</code>
     *
     * @return Whether the dischargeDelay field is set.
     */
    public boolean hasDischargeDelay() {
      return dischargeDelayBuilder_ != null || dischargeDelay_ != null;
    }
    /**
     * <code>.DischargeDelay dischargeDelay = 6;</code>
     *
     * @return The dischargeDelay.
     */
    public com.cpdss.common.generated.discharge_plan.DischargeDelay getDischargeDelay() {
      if (dischargeDelayBuilder_ == null) {
        return dischargeDelay_ == null
            ? com.cpdss.common.generated.discharge_plan.DischargeDelay.getDefaultInstance()
            : dischargeDelay_;
      } else {
        return dischargeDelayBuilder_.getMessage();
      }
    }
    /** <code>.DischargeDelay dischargeDelay = 6;</code> */
    public Builder setDischargeDelay(
        com.cpdss.common.generated.discharge_plan.DischargeDelay value) {
      if (dischargeDelayBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        dischargeDelay_ = value;
        onChanged();
      } else {
        dischargeDelayBuilder_.setMessage(value);
      }

      return this;
    }
    /** <code>.DischargeDelay dischargeDelay = 6;</code> */
    public Builder setDischargeDelay(
        com.cpdss.common.generated.discharge_plan.DischargeDelay.Builder builderForValue) {
      if (dischargeDelayBuilder_ == null) {
        dischargeDelay_ = builderForValue.build();
        onChanged();
      } else {
        dischargeDelayBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /** <code>.DischargeDelay dischargeDelay = 6;</code> */
    public Builder mergeDischargeDelay(
        com.cpdss.common.generated.discharge_plan.DischargeDelay value) {
      if (dischargeDelayBuilder_ == null) {
        if (dischargeDelay_ != null) {
          dischargeDelay_ =
              com.cpdss.common.generated.discharge_plan.DischargeDelay.newBuilder(dischargeDelay_)
                  .mergeFrom(value)
                  .buildPartial();
        } else {
          dischargeDelay_ = value;
        }
        onChanged();
      } else {
        dischargeDelayBuilder_.mergeFrom(value);
      }

      return this;
    }
    /** <code>.DischargeDelay dischargeDelay = 6;</code> */
    public Builder clearDischargeDelay() {
      if (dischargeDelayBuilder_ == null) {
        dischargeDelay_ = null;
        onChanged();
      } else {
        dischargeDelay_ = null;
        dischargeDelayBuilder_ = null;
      }

      return this;
    }
    /** <code>.DischargeDelay dischargeDelay = 6;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeDelay.Builder
        getDischargeDelayBuilder() {

      onChanged();
      return getDischargeDelayFieldBuilder().getBuilder();
    }
    /** <code>.DischargeDelay dischargeDelay = 6;</code> */
    public com.cpdss.common.generated.discharge_plan.DischargeDelayOrBuilder
        getDischargeDelayOrBuilder() {
      if (dischargeDelayBuilder_ != null) {
        return dischargeDelayBuilder_.getMessageOrBuilder();
      } else {
        return dischargeDelay_ == null
            ? com.cpdss.common.generated.discharge_plan.DischargeDelay.getDefaultInstance()
            : dischargeDelay_;
      }
    }
    /** <code>.DischargeDelay dischargeDelay = 6;</code> */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.DischargeDelay,
            com.cpdss.common.generated.discharge_plan.DischargeDelay.Builder,
            com.cpdss.common.generated.discharge_plan.DischargeDelayOrBuilder>
        getDischargeDelayFieldBuilder() {
      if (dischargeDelayBuilder_ == null) {
        dischargeDelayBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.DischargeDelay,
                com.cpdss.common.generated.discharge_plan.DischargeDelay.Builder,
                com.cpdss.common.generated.discharge_plan.DischargeDelayOrBuilder>(
                getDischargeDelay(), getParentForChildren(), isClean());
        dischargeDelay_ = null;
      }
      return dischargeDelayBuilder_;
    }

    private com.cpdss.common.generated.discharge_plan.PostDischargeStageTime
        postDischargeStageTime_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.PostDischargeStageTime,
            com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.Builder,
            com.cpdss.common.generated.discharge_plan.PostDischargeStageTimeOrBuilder>
        postDischargeStageTimeBuilder_;
    /**
     * <code>.PostDischargeStageTime postDischargeStageTime = 7;</code>
     *
     * @return Whether the postDischargeStageTime field is set.
     */
    public boolean hasPostDischargeStageTime() {
      return postDischargeStageTimeBuilder_ != null || postDischargeStageTime_ != null;
    }
    /**
     * <code>.PostDischargeStageTime postDischargeStageTime = 7;</code>
     *
     * @return The postDischargeStageTime.
     */
    public com.cpdss.common.generated.discharge_plan.PostDischargeStageTime
        getPostDischargeStageTime() {
      if (postDischargeStageTimeBuilder_ == null) {
        return postDischargeStageTime_ == null
            ? com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.getDefaultInstance()
            : postDischargeStageTime_;
      } else {
        return postDischargeStageTimeBuilder_.getMessage();
      }
    }
    /** <code>.PostDischargeStageTime postDischargeStageTime = 7;</code> */
    public Builder setPostDischargeStageTime(
        com.cpdss.common.generated.discharge_plan.PostDischargeStageTime value) {
      if (postDischargeStageTimeBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        postDischargeStageTime_ = value;
        onChanged();
      } else {
        postDischargeStageTimeBuilder_.setMessage(value);
      }

      return this;
    }
    /** <code>.PostDischargeStageTime postDischargeStageTime = 7;</code> */
    public Builder setPostDischargeStageTime(
        com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.Builder builderForValue) {
      if (postDischargeStageTimeBuilder_ == null) {
        postDischargeStageTime_ = builderForValue.build();
        onChanged();
      } else {
        postDischargeStageTimeBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /** <code>.PostDischargeStageTime postDischargeStageTime = 7;</code> */
    public Builder mergePostDischargeStageTime(
        com.cpdss.common.generated.discharge_plan.PostDischargeStageTime value) {
      if (postDischargeStageTimeBuilder_ == null) {
        if (postDischargeStageTime_ != null) {
          postDischargeStageTime_ =
              com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.newBuilder(
                      postDischargeStageTime_)
                  .mergeFrom(value)
                  .buildPartial();
        } else {
          postDischargeStageTime_ = value;
        }
        onChanged();
      } else {
        postDischargeStageTimeBuilder_.mergeFrom(value);
      }

      return this;
    }
    /** <code>.PostDischargeStageTime postDischargeStageTime = 7;</code> */
    public Builder clearPostDischargeStageTime() {
      if (postDischargeStageTimeBuilder_ == null) {
        postDischargeStageTime_ = null;
        onChanged();
      } else {
        postDischargeStageTime_ = null;
        postDischargeStageTimeBuilder_ = null;
      }

      return this;
    }
    /** <code>.PostDischargeStageTime postDischargeStageTime = 7;</code> */
    public com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.Builder
        getPostDischargeStageTimeBuilder() {

      onChanged();
      return getPostDischargeStageTimeFieldBuilder().getBuilder();
    }
    /** <code>.PostDischargeStageTime postDischargeStageTime = 7;</code> */
    public com.cpdss.common.generated.discharge_plan.PostDischargeStageTimeOrBuilder
        getPostDischargeStageTimeOrBuilder() {
      if (postDischargeStageTimeBuilder_ != null) {
        return postDischargeStageTimeBuilder_.getMessageOrBuilder();
      } else {
        return postDischargeStageTime_ == null
            ? com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.getDefaultInstance()
            : postDischargeStageTime_;
      }
    }
    /** <code>.PostDischargeStageTime postDischargeStageTime = 7;</code> */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.PostDischargeStageTime,
            com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.Builder,
            com.cpdss.common.generated.discharge_plan.PostDischargeStageTimeOrBuilder>
        getPostDischargeStageTimeFieldBuilder() {
      if (postDischargeStageTimeBuilder_ == null) {
        postDischargeStageTimeBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.PostDischargeStageTime,
                com.cpdss.common.generated.discharge_plan.PostDischargeStageTime.Builder,
                com.cpdss.common.generated.discharge_plan.PostDischargeStageTimeOrBuilder>(
                getPostDischargeStageTime(), getParentForChildren(), isClean());
        postDischargeStageTime_ = null;
      }
      return postDischargeStageTimeBuilder_;
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

    // @@protoc_insertion_point(builder_scope:DischargeInformation)
  }

  // @@protoc_insertion_point(class_scope:DischargeInformation)
  private static final com.cpdss.common.generated.discharge_plan.DischargeInformation
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargeInformation();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargeInformation
      getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargeInformation> PARSER =
      new com.google.protobuf.AbstractParser<DischargeInformation>() {
        @java.lang.Override
        public DischargeInformation parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargeInformation(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargeInformation> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargeInformation> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargeInformation
      getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
