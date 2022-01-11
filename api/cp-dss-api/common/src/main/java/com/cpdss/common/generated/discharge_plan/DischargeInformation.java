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
    machineInUse_ = java.util.Collections.emptyList();
    dischargeStudyProcessId_ = "";
    dischargeQuantityCargoDetails_ = java.util.Collections.emptyList();
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
          case 82:
            {
              if (!((mutable_bitField0_ & 0x00000002) != 0)) {
                machineInUse_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.loading_plan.LoadingPlanModels
                            .LoadingMachinesInUse>();
                mutable_bitField0_ |= 0x00000002;
              }
              machineInUse_.add(
                  input.readMessage(
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse
                          .parser(),
                      extensionRegistry));
              break;
            }
          case 90:
            {
              com.cpdss.common.generated.discharge_plan.CowPlan.Builder subBuilder = null;
              if (cowPlan_ != null) {
                subBuilder = cowPlan_.toBuilder();
              }
              cowPlan_ =
                  input.readMessage(
                      com.cpdss.common.generated.discharge_plan.CowPlan.parser(),
                      extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(cowPlan_);
                cowPlan_ = subBuilder.buildPartial();
              }

              break;
            }
          case 96:
            {
              dischargingInfoStatusId_ = input.readInt64();
              break;
            }
          case 104:
            {
              dischargingPlanArrStatusId_ = input.readInt64();
              break;
            }
          case 112:
            {
              dischargingPlanDepStatusId_ = input.readInt64();
              break;
            }
          case 120:
            {
              dischargePatternId_ = input.readInt64();
              break;
            }
          case 128:
            {
              isDischargingInstructionsComplete_ = input.readBool();
              break;
            }
          case 136:
            {
              isDischargingSequenceGenerated_ = input.readBool();
              break;
            }
          case 144:
            {
              isDischargingPlanGenerated_ = input.readBool();
              break;
            }
          case 152:
            {
              portId_ = input.readInt64();
              break;
            }
          case 160:
            {
              vesselId_ = input.readInt64();
              break;
            }
          case 168:
            {
              voyageId_ = input.readInt64();
              break;
            }
          case 176:
            {
              portRotationId_ = input.readInt64();
              break;
            }
          case 186:
            {
              java.lang.String s = input.readStringRequireUtf8();

              dischargeStudyProcessId_ = s;
              break;
            }
          case 192:
            {
              isDischargingInfoComplete_ = input.readBool();
              break;
            }
          case 200:
            {
              dischargeSlopTanksFirst_ = input.readBool();
              break;
            }
          case 208:
            {
              dischargeCommingledCargoSeparately_ = input.readBool();
              break;
            }
          case 216:
            {
              isDischargeInfoComplete_ = input.readBool();
              break;
            }
          case 226:
            {
              if (!((mutable_bitField0_ & 0x00000004) != 0)) {
                dischargeQuantityCargoDetails_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails>();
                mutable_bitField0_ |= 0x00000004;
              }
              dischargeQuantityCargoDetails_.add(
                  input.readMessage(
                      com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails
                          .parser(),
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
        berthDetails_ = java.util.Collections.unmodifiableList(berthDetails_);
      }
      if (((mutable_bitField0_ & 0x00000002) != 0)) {
        machineInUse_ = java.util.Collections.unmodifiableList(machineInUse_);
      }
      if (((mutable_bitField0_ & 0x00000004) != 0)) {
        dischargeQuantityCargoDetails_ =
            java.util.Collections.unmodifiableList(dischargeQuantityCargoDetails_);
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

  public static final int MACHINEINUSE_FIELD_NUMBER = 10;
  private java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse>
      machineInUse_;
  /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
  public java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse>
      getMachineInUseList() {
    return machineInUse_;
  }
  /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
  public java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingMachinesInUseOrBuilder>
      getMachineInUseOrBuilderList() {
    return machineInUse_;
  }
  /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
  public int getMachineInUseCount() {
    return machineInUse_.size();
  }
  /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse
      getMachineInUse(int index) {
    return machineInUse_.get(index);
  }
  /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUseOrBuilder
      getMachineInUseOrBuilder(int index) {
    return machineInUse_.get(index);
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

  public static final int COWPLAN_FIELD_NUMBER = 11;
  private com.cpdss.common.generated.discharge_plan.CowPlan cowPlan_;
  /**
   * <code>.CowPlan cowPlan = 11;</code>
   *
   * @return Whether the cowPlan field is set.
   */
  public boolean hasCowPlan() {
    return cowPlan_ != null;
  }
  /**
   * <code>.CowPlan cowPlan = 11;</code>
   *
   * @return The cowPlan.
   */
  public com.cpdss.common.generated.discharge_plan.CowPlan getCowPlan() {
    return cowPlan_ == null
        ? com.cpdss.common.generated.discharge_plan.CowPlan.getDefaultInstance()
        : cowPlan_;
  }
  /** <code>.CowPlan cowPlan = 11;</code> */
  public com.cpdss.common.generated.discharge_plan.CowPlanOrBuilder getCowPlanOrBuilder() {
    return getCowPlan();
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

  public static final int DISCHARGINGINFOSTATUSID_FIELD_NUMBER = 12;
  private long dischargingInfoStatusId_;
  /**
   * <code>int64 dischargingInfoStatusId = 12;</code>
   *
   * @return The dischargingInfoStatusId.
   */
  public long getDischargingInfoStatusId() {
    return dischargingInfoStatusId_;
  }

  public static final int DISCHARGINGPLANARRSTATUSID_FIELD_NUMBER = 13;
  private long dischargingPlanArrStatusId_;
  /**
   * <code>int64 dischargingPlanArrStatusId = 13;</code>
   *
   * @return The dischargingPlanArrStatusId.
   */
  public long getDischargingPlanArrStatusId() {
    return dischargingPlanArrStatusId_;
  }

  public static final int DISCHARGINGPLANDEPSTATUSID_FIELD_NUMBER = 14;
  private long dischargingPlanDepStatusId_;
  /**
   * <code>int64 dischargingPlanDepStatusId = 14;</code>
   *
   * @return The dischargingPlanDepStatusId.
   */
  public long getDischargingPlanDepStatusId() {
    return dischargingPlanDepStatusId_;
  }

  public static final int DISCHARGEPATTERNID_FIELD_NUMBER = 15;
  private long dischargePatternId_;
  /**
   * <code>int64 dischargePatternId = 15;</code>
   *
   * @return The dischargePatternId.
   */
  public long getDischargePatternId() {
    return dischargePatternId_;
  }

  public static final int ISDISCHARGINGINSTRUCTIONSCOMPLETE_FIELD_NUMBER = 16;
  private boolean isDischargingInstructionsComplete_;
  /**
   * <code>bool isDischargingInstructionsComplete = 16;</code>
   *
   * @return The isDischargingInstructionsComplete.
   */
  public boolean getIsDischargingInstructionsComplete() {
    return isDischargingInstructionsComplete_;
  }

  public static final int ISDISCHARGINGSEQUENCEGENERATED_FIELD_NUMBER = 17;
  private boolean isDischargingSequenceGenerated_;
  /**
   * <code>bool isDischargingSequenceGenerated = 17;</code>
   *
   * @return The isDischargingSequenceGenerated.
   */
  public boolean getIsDischargingSequenceGenerated() {
    return isDischargingSequenceGenerated_;
  }

  public static final int ISDISCHARGINGPLANGENERATED_FIELD_NUMBER = 18;
  private boolean isDischargingPlanGenerated_;
  /**
   * <code>bool isDischargingPlanGenerated = 18;</code>
   *
   * @return The isDischargingPlanGenerated.
   */
  public boolean getIsDischargingPlanGenerated() {
    return isDischargingPlanGenerated_;
  }

  public static final int PORTID_FIELD_NUMBER = 19;
  private long portId_;
  /**
   * <code>int64 portId = 19;</code>
   *
   * @return The portId.
   */
  public long getPortId() {
    return portId_;
  }

  public static final int VESSELID_FIELD_NUMBER = 20;
  private long vesselId_;
  /**
   * <code>int64 vesselId = 20;</code>
   *
   * @return The vesselId.
   */
  public long getVesselId() {
    return vesselId_;
  }

  public static final int VOYAGEID_FIELD_NUMBER = 21;
  private long voyageId_;
  /**
   * <code>int64 voyageId = 21;</code>
   *
   * @return The voyageId.
   */
  public long getVoyageId() {
    return voyageId_;
  }

  public static final int PORTROTATIONID_FIELD_NUMBER = 22;
  private long portRotationId_;
  /**
   * <code>int64 portRotationId = 22;</code>
   *
   * @return The portRotationId.
   */
  public long getPortRotationId() {
    return portRotationId_;
  }

  public static final int DISCHARGESTUDYPROCESSID_FIELD_NUMBER = 23;
  private volatile java.lang.Object dischargeStudyProcessId_;
  /**
   * <code>string dischargeStudyProcessId = 23;</code>
   *
   * @return The dischargeStudyProcessId.
   */
  public java.lang.String getDischargeStudyProcessId() {
    java.lang.Object ref = dischargeStudyProcessId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      dischargeStudyProcessId_ = s;
      return s;
    }
  }
  /**
   * <code>string dischargeStudyProcessId = 23;</code>
   *
   * @return The bytes for dischargeStudyProcessId.
   */
  public com.google.protobuf.ByteString getDischargeStudyProcessIdBytes() {
    java.lang.Object ref = dischargeStudyProcessId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      dischargeStudyProcessId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ISDISCHARGINGINFOCOMPLETE_FIELD_NUMBER = 24;
  private boolean isDischargingInfoComplete_;
  /**
   * <code>bool isDischargingInfoComplete = 24;</code>
   *
   * @return The isDischargingInfoComplete.
   */
  public boolean getIsDischargingInfoComplete() {
    return isDischargingInfoComplete_;
  }

  public static final int DISCHARGESLOPTANKSFIRST_FIELD_NUMBER = 25;
  private boolean dischargeSlopTanksFirst_;
  /**
   *
   *
   * <pre>
   * Cargo Section
   * </pre>
   *
   * <code>bool dischargeSlopTanksFirst = 25;</code>
   *
   * @return The dischargeSlopTanksFirst.
   */
  public boolean getDischargeSlopTanksFirst() {
    return dischargeSlopTanksFirst_;
  }

  public static final int DISCHARGECOMMINGLEDCARGOSEPARATELY_FIELD_NUMBER = 26;
  private boolean dischargeCommingledCargoSeparately_;
  /**
   * <code>bool dischargeCommingledCargoSeparately = 26;</code>
   *
   * @return The dischargeCommingledCargoSeparately.
   */
  public boolean getDischargeCommingledCargoSeparately() {
    return dischargeCommingledCargoSeparately_;
  }

  public static final int ISDISCHARGEINFOCOMPLETE_FIELD_NUMBER = 27;
  private boolean isDischargeInfoComplete_;
  /**
   * <code>bool isDischargeInfoComplete = 27;</code>
   *
   * @return The isDischargeInfoComplete.
   */
  public boolean getIsDischargeInfoComplete() {
    return isDischargeInfoComplete_;
  }

  public static final int DISCHARGEQUANTITYCARGODETAILS_FIELD_NUMBER = 28;
  private java.util.List<com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails>
      dischargeQuantityCargoDetails_;
  /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
  public java.util.List<com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails>
      getDischargeQuantityCargoDetailsList() {
    return dischargeQuantityCargoDetails_;
  }
  /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
  public java.util.List<
          ? extends com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsOrBuilder>
      getDischargeQuantityCargoDetailsOrBuilderList() {
    return dischargeQuantityCargoDetails_;
  }
  /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
  public int getDischargeQuantityCargoDetailsCount() {
    return dischargeQuantityCargoDetails_.size();
  }
  /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
  public com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails
      getDischargeQuantityCargoDetails(int index) {
    return dischargeQuantityCargoDetails_.get(index);
  }
  /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
  public com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsOrBuilder
      getDischargeQuantityCargoDetailsOrBuilder(int index) {
    return dischargeQuantityCargoDetails_.get(index);
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
    for (int i = 0; i < machineInUse_.size(); i++) {
      output.writeMessage(10, machineInUse_.get(i));
    }
    if (cowPlan_ != null) {
      output.writeMessage(11, getCowPlan());
    }
    if (dischargingInfoStatusId_ != 0L) {
      output.writeInt64(12, dischargingInfoStatusId_);
    }
    if (dischargingPlanArrStatusId_ != 0L) {
      output.writeInt64(13, dischargingPlanArrStatusId_);
    }
    if (dischargingPlanDepStatusId_ != 0L) {
      output.writeInt64(14, dischargingPlanDepStatusId_);
    }
    if (dischargePatternId_ != 0L) {
      output.writeInt64(15, dischargePatternId_);
    }
    if (isDischargingInstructionsComplete_ != false) {
      output.writeBool(16, isDischargingInstructionsComplete_);
    }
    if (isDischargingSequenceGenerated_ != false) {
      output.writeBool(17, isDischargingSequenceGenerated_);
    }
    if (isDischargingPlanGenerated_ != false) {
      output.writeBool(18, isDischargingPlanGenerated_);
    }
    if (portId_ != 0L) {
      output.writeInt64(19, portId_);
    }
    if (vesselId_ != 0L) {
      output.writeInt64(20, vesselId_);
    }
    if (voyageId_ != 0L) {
      output.writeInt64(21, voyageId_);
    }
    if (portRotationId_ != 0L) {
      output.writeInt64(22, portRotationId_);
    }
    if (!getDischargeStudyProcessIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 23, dischargeStudyProcessId_);
    }
    if (isDischargingInfoComplete_ != false) {
      output.writeBool(24, isDischargingInfoComplete_);
    }
    if (dischargeSlopTanksFirst_ != false) {
      output.writeBool(25, dischargeSlopTanksFirst_);
    }
    if (dischargeCommingledCargoSeparately_ != false) {
      output.writeBool(26, dischargeCommingledCargoSeparately_);
    }
    if (isDischargeInfoComplete_ != false) {
      output.writeBool(27, isDischargeInfoComplete_);
    }
    for (int i = 0; i < dischargeQuantityCargoDetails_.size(); i++) {
      output.writeMessage(28, dischargeQuantityCargoDetails_.get(i));
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
    for (int i = 0; i < machineInUse_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(10, machineInUse_.get(i));
    }
    if (cowPlan_ != null) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(11, getCowPlan());
    }
    if (dischargingInfoStatusId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(12, dischargingInfoStatusId_);
    }
    if (dischargingPlanArrStatusId_ != 0L) {
      size +=
          com.google.protobuf.CodedOutputStream.computeInt64Size(13, dischargingPlanArrStatusId_);
    }
    if (dischargingPlanDepStatusId_ != 0L) {
      size +=
          com.google.protobuf.CodedOutputStream.computeInt64Size(14, dischargingPlanDepStatusId_);
    }
    if (dischargePatternId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(15, dischargePatternId_);
    }
    if (isDischargingInstructionsComplete_ != false) {
      size +=
          com.google.protobuf.CodedOutputStream.computeBoolSize(
              16, isDischargingInstructionsComplete_);
    }
    if (isDischargingSequenceGenerated_ != false) {
      size +=
          com.google.protobuf.CodedOutputStream.computeBoolSize(
              17, isDischargingSequenceGenerated_);
    }
    if (isDischargingPlanGenerated_ != false) {
      size +=
          com.google.protobuf.CodedOutputStream.computeBoolSize(18, isDischargingPlanGenerated_);
    }
    if (portId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(19, portId_);
    }
    if (vesselId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(20, vesselId_);
    }
    if (voyageId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(21, voyageId_);
    }
    if (portRotationId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(22, portRotationId_);
    }
    if (!getDischargeStudyProcessIdBytes().isEmpty()) {
      size +=
          com.google.protobuf.GeneratedMessageV3.computeStringSize(23, dischargeStudyProcessId_);
    }
    if (isDischargingInfoComplete_ != false) {
      size += com.google.protobuf.CodedOutputStream.computeBoolSize(24, isDischargingInfoComplete_);
    }
    if (dischargeSlopTanksFirst_ != false) {
      size += com.google.protobuf.CodedOutputStream.computeBoolSize(25, dischargeSlopTanksFirst_);
    }
    if (dischargeCommingledCargoSeparately_ != false) {
      size +=
          com.google.protobuf.CodedOutputStream.computeBoolSize(
              26, dischargeCommingledCargoSeparately_);
    }
    if (isDischargeInfoComplete_ != false) {
      size += com.google.protobuf.CodedOutputStream.computeBoolSize(27, isDischargeInfoComplete_);
    }
    for (int i = 0; i < dischargeQuantityCargoDetails_.size(); i++) {
      size +=
          com.google.protobuf.CodedOutputStream.computeMessageSize(
              28, dischargeQuantityCargoDetails_.get(i));
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
    if (!getMachineInUseList().equals(other.getMachineInUseList())) return false;
    if (hasDischargeStage() != other.hasDischargeStage()) return false;
    if (hasDischargeStage()) {
      if (!getDischargeStage().equals(other.getDischargeStage())) return false;
    }
    if (hasDischargeDelay() != other.hasDischargeDelay()) return false;
    if (hasDischargeDelay()) {
      if (!getDischargeDelay().equals(other.getDischargeDelay())) return false;
    }
    if (hasCowPlan() != other.hasCowPlan()) return false;
    if (hasCowPlan()) {
      if (!getCowPlan().equals(other.getCowPlan())) return false;
    }
    if (hasPostDischargeStageTime() != other.hasPostDischargeStageTime()) return false;
    if (hasPostDischargeStageTime()) {
      if (!getPostDischargeStageTime().equals(other.getPostDischargeStageTime())) return false;
    }
    if (getDischargingInfoStatusId() != other.getDischargingInfoStatusId()) return false;
    if (getDischargingPlanArrStatusId() != other.getDischargingPlanArrStatusId()) return false;
    if (getDischargingPlanDepStatusId() != other.getDischargingPlanDepStatusId()) return false;
    if (getDischargePatternId() != other.getDischargePatternId()) return false;
    if (getIsDischargingInstructionsComplete() != other.getIsDischargingInstructionsComplete())
      return false;
    if (getIsDischargingSequenceGenerated() != other.getIsDischargingSequenceGenerated())
      return false;
    if (getIsDischargingPlanGenerated() != other.getIsDischargingPlanGenerated()) return false;
    if (getPortId() != other.getPortId()) return false;
    if (getVesselId() != other.getVesselId()) return false;
    if (getVoyageId() != other.getVoyageId()) return false;
    if (getPortRotationId() != other.getPortRotationId()) return false;
    if (!getDischargeStudyProcessId().equals(other.getDischargeStudyProcessId())) return false;
    if (getIsDischargingInfoComplete() != other.getIsDischargingInfoComplete()) return false;
    if (getDischargeSlopTanksFirst() != other.getDischargeSlopTanksFirst()) return false;
    if (getDischargeCommingledCargoSeparately() != other.getDischargeCommingledCargoSeparately())
      return false;
    if (getIsDischargeInfoComplete() != other.getIsDischargeInfoComplete()) return false;
    if (!getDischargeQuantityCargoDetailsList()
        .equals(other.getDischargeQuantityCargoDetailsList())) return false;
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
    if (getMachineInUseCount() > 0) {
      hash = (37 * hash) + MACHINEINUSE_FIELD_NUMBER;
      hash = (53 * hash) + getMachineInUseList().hashCode();
    }
    if (hasDischargeStage()) {
      hash = (37 * hash) + DISCHARGESTAGE_FIELD_NUMBER;
      hash = (53 * hash) + getDischargeStage().hashCode();
    }
    if (hasDischargeDelay()) {
      hash = (37 * hash) + DISCHARGEDELAY_FIELD_NUMBER;
      hash = (53 * hash) + getDischargeDelay().hashCode();
    }
    if (hasCowPlan()) {
      hash = (37 * hash) + COWPLAN_FIELD_NUMBER;
      hash = (53 * hash) + getCowPlan().hashCode();
    }
    if (hasPostDischargeStageTime()) {
      hash = (37 * hash) + POSTDISCHARGESTAGETIME_FIELD_NUMBER;
      hash = (53 * hash) + getPostDischargeStageTime().hashCode();
    }
    hash = (37 * hash) + DISCHARGINGINFOSTATUSID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargingInfoStatusId());
    hash = (37 * hash) + DISCHARGINGPLANARRSTATUSID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargingPlanArrStatusId());
    hash = (37 * hash) + DISCHARGINGPLANDEPSTATUSID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargingPlanDepStatusId());
    hash = (37 * hash) + DISCHARGEPATTERNID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getDischargePatternId());
    hash = (37 * hash) + ISDISCHARGINGINSTRUCTIONSCOMPLETE_FIELD_NUMBER;
    hash =
        (53 * hash)
            + com.google.protobuf.Internal.hashBoolean(getIsDischargingInstructionsComplete());
    hash = (37 * hash) + ISDISCHARGINGSEQUENCEGENERATED_FIELD_NUMBER;
    hash =
        (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsDischargingSequenceGenerated());
    hash = (37 * hash) + ISDISCHARGINGPLANGENERATED_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsDischargingPlanGenerated());
    hash = (37 * hash) + PORTID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortId());
    hash = (37 * hash) + VESSELID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVesselId());
    hash = (37 * hash) + VOYAGEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getVoyageId());
    hash = (37 * hash) + PORTROTATIONID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortRotationId());
    hash = (37 * hash) + DISCHARGESTUDYPROCESSID_FIELD_NUMBER;
    hash = (53 * hash) + getDischargeStudyProcessId().hashCode();
    hash = (37 * hash) + ISDISCHARGINGINFOCOMPLETE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsDischargingInfoComplete());
    hash = (37 * hash) + DISCHARGESLOPTANKSFIRST_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getDischargeSlopTanksFirst());
    hash = (37 * hash) + DISCHARGECOMMINGLEDCARGOSEPARATELY_FIELD_NUMBER;
    hash =
        (53 * hash)
            + com.google.protobuf.Internal.hashBoolean(getDischargeCommingledCargoSeparately());
    hash = (37 * hash) + ISDISCHARGEINFOCOMPLETE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsDischargeInfoComplete());
    if (getDischargeQuantityCargoDetailsCount() > 0) {
      hash = (37 * hash) + DISCHARGEQUANTITYCARGODETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getDischargeQuantityCargoDetailsList().hashCode();
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
        getMachineInUseFieldBuilder();
        getDischargeQuantityCargoDetailsFieldBuilder();
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
      if (machineInUseBuilder_ == null) {
        machineInUse_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        machineInUseBuilder_.clear();
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
      if (cowPlanBuilder_ == null) {
        cowPlan_ = null;
      } else {
        cowPlan_ = null;
        cowPlanBuilder_ = null;
      }
      if (postDischargeStageTimeBuilder_ == null) {
        postDischargeStageTime_ = null;
      } else {
        postDischargeStageTime_ = null;
        postDischargeStageTimeBuilder_ = null;
      }
      dischargingInfoStatusId_ = 0L;

      dischargingPlanArrStatusId_ = 0L;

      dischargingPlanDepStatusId_ = 0L;

      dischargePatternId_ = 0L;

      isDischargingInstructionsComplete_ = false;

      isDischargingSequenceGenerated_ = false;

      isDischargingPlanGenerated_ = false;

      portId_ = 0L;

      vesselId_ = 0L;

      voyageId_ = 0L;

      portRotationId_ = 0L;

      dischargeStudyProcessId_ = "";

      isDischargingInfoComplete_ = false;

      dischargeSlopTanksFirst_ = false;

      dischargeCommingledCargoSeparately_ = false;

      isDischargeInfoComplete_ = false;

      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        dischargeQuantityCargoDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
      } else {
        dischargeQuantityCargoDetailsBuilder_.clear();
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
      if (machineInUseBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0)) {
          machineInUse_ = java.util.Collections.unmodifiableList(machineInUse_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.machineInUse_ = machineInUse_;
      } else {
        result.machineInUse_ = machineInUseBuilder_.build();
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
      if (cowPlanBuilder_ == null) {
        result.cowPlan_ = cowPlan_;
      } else {
        result.cowPlan_ = cowPlanBuilder_.build();
      }
      if (postDischargeStageTimeBuilder_ == null) {
        result.postDischargeStageTime_ = postDischargeStageTime_;
      } else {
        result.postDischargeStageTime_ = postDischargeStageTimeBuilder_.build();
      }
      result.dischargingInfoStatusId_ = dischargingInfoStatusId_;
      result.dischargingPlanArrStatusId_ = dischargingPlanArrStatusId_;
      result.dischargingPlanDepStatusId_ = dischargingPlanDepStatusId_;
      result.dischargePatternId_ = dischargePatternId_;
      result.isDischargingInstructionsComplete_ = isDischargingInstructionsComplete_;
      result.isDischargingSequenceGenerated_ = isDischargingSequenceGenerated_;
      result.isDischargingPlanGenerated_ = isDischargingPlanGenerated_;
      result.portId_ = portId_;
      result.vesselId_ = vesselId_;
      result.voyageId_ = voyageId_;
      result.portRotationId_ = portRotationId_;
      result.dischargeStudyProcessId_ = dischargeStudyProcessId_;
      result.isDischargingInfoComplete_ = isDischargingInfoComplete_;
      result.dischargeSlopTanksFirst_ = dischargeSlopTanksFirst_;
      result.dischargeCommingledCargoSeparately_ = dischargeCommingledCargoSeparately_;
      result.isDischargeInfoComplete_ = isDischargeInfoComplete_;
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        if (((bitField0_ & 0x00000004) != 0)) {
          dischargeQuantityCargoDetails_ =
              java.util.Collections.unmodifiableList(dischargeQuantityCargoDetails_);
          bitField0_ = (bitField0_ & ~0x00000004);
        }
        result.dischargeQuantityCargoDetails_ = dischargeQuantityCargoDetails_;
      } else {
        result.dischargeQuantityCargoDetails_ = dischargeQuantityCargoDetailsBuilder_.build();
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
      if (machineInUseBuilder_ == null) {
        if (!other.machineInUse_.isEmpty()) {
          if (machineInUse_.isEmpty()) {
            machineInUse_ = other.machineInUse_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureMachineInUseIsMutable();
            machineInUse_.addAll(other.machineInUse_);
          }
          onChanged();
        }
      } else {
        if (!other.machineInUse_.isEmpty()) {
          if (machineInUseBuilder_.isEmpty()) {
            machineInUseBuilder_.dispose();
            machineInUseBuilder_ = null;
            machineInUse_ = other.machineInUse_;
            bitField0_ = (bitField0_ & ~0x00000002);
            machineInUseBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getMachineInUseFieldBuilder()
                    : null;
          } else {
            machineInUseBuilder_.addAllMessages(other.machineInUse_);
          }
        }
      }
      if (other.hasDischargeStage()) {
        mergeDischargeStage(other.getDischargeStage());
      }
      if (other.hasDischargeDelay()) {
        mergeDischargeDelay(other.getDischargeDelay());
      }
      if (other.hasCowPlan()) {
        mergeCowPlan(other.getCowPlan());
      }
      if (other.hasPostDischargeStageTime()) {
        mergePostDischargeStageTime(other.getPostDischargeStageTime());
      }
      if (other.getDischargingInfoStatusId() != 0L) {
        setDischargingInfoStatusId(other.getDischargingInfoStatusId());
      }
      if (other.getDischargingPlanArrStatusId() != 0L) {
        setDischargingPlanArrStatusId(other.getDischargingPlanArrStatusId());
      }
      if (other.getDischargingPlanDepStatusId() != 0L) {
        setDischargingPlanDepStatusId(other.getDischargingPlanDepStatusId());
      }
      if (other.getDischargePatternId() != 0L) {
        setDischargePatternId(other.getDischargePatternId());
      }
      if (other.getIsDischargingInstructionsComplete() != false) {
        setIsDischargingInstructionsComplete(other.getIsDischargingInstructionsComplete());
      }
      if (other.getIsDischargingSequenceGenerated() != false) {
        setIsDischargingSequenceGenerated(other.getIsDischargingSequenceGenerated());
      }
      if (other.getIsDischargingPlanGenerated() != false) {
        setIsDischargingPlanGenerated(other.getIsDischargingPlanGenerated());
      }
      if (other.getPortId() != 0L) {
        setPortId(other.getPortId());
      }
      if (other.getVesselId() != 0L) {
        setVesselId(other.getVesselId());
      }
      if (other.getVoyageId() != 0L) {
        setVoyageId(other.getVoyageId());
      }
      if (other.getPortRotationId() != 0L) {
        setPortRotationId(other.getPortRotationId());
      }
      if (!other.getDischargeStudyProcessId().isEmpty()) {
        dischargeStudyProcessId_ = other.dischargeStudyProcessId_;
        onChanged();
      }
      if (other.getIsDischargingInfoComplete() != false) {
        setIsDischargingInfoComplete(other.getIsDischargingInfoComplete());
      }
      if (other.getDischargeSlopTanksFirst() != false) {
        setDischargeSlopTanksFirst(other.getDischargeSlopTanksFirst());
      }
      if (other.getDischargeCommingledCargoSeparately() != false) {
        setDischargeCommingledCargoSeparately(other.getDischargeCommingledCargoSeparately());
      }
      if (other.getIsDischargeInfoComplete() != false) {
        setIsDischargeInfoComplete(other.getIsDischargeInfoComplete());
      }
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        if (!other.dischargeQuantityCargoDetails_.isEmpty()) {
          if (dischargeQuantityCargoDetails_.isEmpty()) {
            dischargeQuantityCargoDetails_ = other.dischargeQuantityCargoDetails_;
            bitField0_ = (bitField0_ & ~0x00000004);
          } else {
            ensureDischargeQuantityCargoDetailsIsMutable();
            dischargeQuantityCargoDetails_.addAll(other.dischargeQuantityCargoDetails_);
          }
          onChanged();
        }
      } else {
        if (!other.dischargeQuantityCargoDetails_.isEmpty()) {
          if (dischargeQuantityCargoDetailsBuilder_.isEmpty()) {
            dischargeQuantityCargoDetailsBuilder_.dispose();
            dischargeQuantityCargoDetailsBuilder_ = null;
            dischargeQuantityCargoDetails_ = other.dischargeQuantityCargoDetails_;
            bitField0_ = (bitField0_ & ~0x00000004);
            dischargeQuantityCargoDetailsBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getDischargeQuantityCargoDetailsFieldBuilder()
                    : null;
          } else {
            dischargeQuantityCargoDetailsBuilder_.addAllMessages(
                other.dischargeQuantityCargoDetails_);
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

    private java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse>
        machineInUse_ = java.util.Collections.emptyList();

    private void ensureMachineInUseIsMutable() {
      if (!((bitField0_ & 0x00000002) != 0)) {
        machineInUse_ =
            new java.util.ArrayList<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse>(
                machineInUse_);
        bitField0_ |= 0x00000002;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUseOrBuilder>
        machineInUseBuilder_;

    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse>
        getMachineInUseList() {
      if (machineInUseBuilder_ == null) {
        return java.util.Collections.unmodifiableList(machineInUse_);
      } else {
        return machineInUseBuilder_.getMessageList();
      }
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public int getMachineInUseCount() {
      if (machineInUseBuilder_ == null) {
        return machineInUse_.size();
      } else {
        return machineInUseBuilder_.getCount();
      }
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse
        getMachineInUse(int index) {
      if (machineInUseBuilder_ == null) {
        return machineInUse_.get(index);
      } else {
        return machineInUseBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public Builder setMachineInUse(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse value) {
      if (machineInUseBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMachineInUseIsMutable();
        machineInUse_.set(index, value);
        onChanged();
      } else {
        machineInUseBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public Builder setMachineInUse(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse.Builder
            builderForValue) {
      if (machineInUseBuilder_ == null) {
        ensureMachineInUseIsMutable();
        machineInUse_.set(index, builderForValue.build());
        onChanged();
      } else {
        machineInUseBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public Builder addMachineInUse(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse value) {
      if (machineInUseBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMachineInUseIsMutable();
        machineInUse_.add(value);
        onChanged();
      } else {
        machineInUseBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public Builder addMachineInUse(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse value) {
      if (machineInUseBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMachineInUseIsMutable();
        machineInUse_.add(index, value);
        onChanged();
      } else {
        machineInUseBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public Builder addMachineInUse(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse.Builder
            builderForValue) {
      if (machineInUseBuilder_ == null) {
        ensureMachineInUseIsMutable();
        machineInUse_.add(builderForValue.build());
        onChanged();
      } else {
        machineInUseBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public Builder addMachineInUse(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse.Builder
            builderForValue) {
      if (machineInUseBuilder_ == null) {
        ensureMachineInUseIsMutable();
        machineInUse_.add(index, builderForValue.build());
        onChanged();
      } else {
        machineInUseBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public Builder addAllMachineInUse(
        java.lang.Iterable<
                ? extends
                    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse>
            values) {
      if (machineInUseBuilder_ == null) {
        ensureMachineInUseIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, machineInUse_);
        onChanged();
      } else {
        machineInUseBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public Builder clearMachineInUse() {
      if (machineInUseBuilder_ == null) {
        machineInUse_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        machineInUseBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public Builder removeMachineInUse(int index) {
      if (machineInUseBuilder_ == null) {
        ensureMachineInUseIsMutable();
        machineInUse_.remove(index);
        onChanged();
      } else {
        machineInUseBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse.Builder
        getMachineInUseBuilder(int index) {
      return getMachineInUseFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUseOrBuilder
        getMachineInUseOrBuilder(int index) {
      if (machineInUseBuilder_ == null) {
        return machineInUse_.get(index);
      } else {
        return machineInUseBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public java.util.List<
            ? extends
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingMachinesInUseOrBuilder>
        getMachineInUseOrBuilderList() {
      if (machineInUseBuilder_ != null) {
        return machineInUseBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(machineInUse_);
      }
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse.Builder
        addMachineInUseBuilder() {
      return getMachineInUseFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse
                  .getDefaultInstance());
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse.Builder
        addMachineInUseBuilder(int index) {
      return getMachineInUseFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse
                  .getDefaultInstance());
    }
    /** <code>repeated .LoadingMachinesInUse machineInUse = 10;</code> */
    public java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse.Builder>
        getMachineInUseBuilderList() {
      return getMachineInUseFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUseOrBuilder>
        getMachineInUseFieldBuilder() {
      if (machineInUseBuilder_ == null) {
        machineInUseBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingMachinesInUse
                    .Builder,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingMachinesInUseOrBuilder>(
                machineInUse_, ((bitField0_ & 0x00000002) != 0), getParentForChildren(), isClean());
        machineInUse_ = null;
      }
      return machineInUseBuilder_;
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

    private com.cpdss.common.generated.discharge_plan.CowPlan cowPlan_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.CowPlan,
            com.cpdss.common.generated.discharge_plan.CowPlan.Builder,
            com.cpdss.common.generated.discharge_plan.CowPlanOrBuilder>
        cowPlanBuilder_;
    /**
     * <code>.CowPlan cowPlan = 11;</code>
     *
     * @return Whether the cowPlan field is set.
     */
    public boolean hasCowPlan() {
      return cowPlanBuilder_ != null || cowPlan_ != null;
    }
    /**
     * <code>.CowPlan cowPlan = 11;</code>
     *
     * @return The cowPlan.
     */
    public com.cpdss.common.generated.discharge_plan.CowPlan getCowPlan() {
      if (cowPlanBuilder_ == null) {
        return cowPlan_ == null
            ? com.cpdss.common.generated.discharge_plan.CowPlan.getDefaultInstance()
            : cowPlan_;
      } else {
        return cowPlanBuilder_.getMessage();
      }
    }
    /** <code>.CowPlan cowPlan = 11;</code> */
    public Builder setCowPlan(com.cpdss.common.generated.discharge_plan.CowPlan value) {
      if (cowPlanBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        cowPlan_ = value;
        onChanged();
      } else {
        cowPlanBuilder_.setMessage(value);
      }

      return this;
    }
    /** <code>.CowPlan cowPlan = 11;</code> */
    public Builder setCowPlan(
        com.cpdss.common.generated.discharge_plan.CowPlan.Builder builderForValue) {
      if (cowPlanBuilder_ == null) {
        cowPlan_ = builderForValue.build();
        onChanged();
      } else {
        cowPlanBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /** <code>.CowPlan cowPlan = 11;</code> */
    public Builder mergeCowPlan(com.cpdss.common.generated.discharge_plan.CowPlan value) {
      if (cowPlanBuilder_ == null) {
        if (cowPlan_ != null) {
          cowPlan_ =
              com.cpdss.common.generated.discharge_plan.CowPlan.newBuilder(cowPlan_)
                  .mergeFrom(value)
                  .buildPartial();
        } else {
          cowPlan_ = value;
        }
        onChanged();
      } else {
        cowPlanBuilder_.mergeFrom(value);
      }

      return this;
    }
    /** <code>.CowPlan cowPlan = 11;</code> */
    public Builder clearCowPlan() {
      if (cowPlanBuilder_ == null) {
        cowPlan_ = null;
        onChanged();
      } else {
        cowPlan_ = null;
        cowPlanBuilder_ = null;
      }

      return this;
    }
    /** <code>.CowPlan cowPlan = 11;</code> */
    public com.cpdss.common.generated.discharge_plan.CowPlan.Builder getCowPlanBuilder() {

      onChanged();
      return getCowPlanFieldBuilder().getBuilder();
    }
    /** <code>.CowPlan cowPlan = 11;</code> */
    public com.cpdss.common.generated.discharge_plan.CowPlanOrBuilder getCowPlanOrBuilder() {
      if (cowPlanBuilder_ != null) {
        return cowPlanBuilder_.getMessageOrBuilder();
      } else {
        return cowPlan_ == null
            ? com.cpdss.common.generated.discharge_plan.CowPlan.getDefaultInstance()
            : cowPlan_;
      }
    }
    /** <code>.CowPlan cowPlan = 11;</code> */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.cpdss.common.generated.discharge_plan.CowPlan,
            com.cpdss.common.generated.discharge_plan.CowPlan.Builder,
            com.cpdss.common.generated.discharge_plan.CowPlanOrBuilder>
        getCowPlanFieldBuilder() {
      if (cowPlanBuilder_ == null) {
        cowPlanBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.cpdss.common.generated.discharge_plan.CowPlan,
                com.cpdss.common.generated.discharge_plan.CowPlan.Builder,
                com.cpdss.common.generated.discharge_plan.CowPlanOrBuilder>(
                getCowPlan(), getParentForChildren(), isClean());
        cowPlan_ = null;
      }
      return cowPlanBuilder_;
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

    private long dischargingInfoStatusId_;
    /**
     * <code>int64 dischargingInfoStatusId = 12;</code>
     *
     * @return The dischargingInfoStatusId.
     */
    public long getDischargingInfoStatusId() {
      return dischargingInfoStatusId_;
    }
    /**
     * <code>int64 dischargingInfoStatusId = 12;</code>
     *
     * @param value The dischargingInfoStatusId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargingInfoStatusId(long value) {

      dischargingInfoStatusId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 dischargingInfoStatusId = 12;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargingInfoStatusId() {

      dischargingInfoStatusId_ = 0L;
      onChanged();
      return this;
    }

    private long dischargingPlanArrStatusId_;
    /**
     * <code>int64 dischargingPlanArrStatusId = 13;</code>
     *
     * @return The dischargingPlanArrStatusId.
     */
    public long getDischargingPlanArrStatusId() {
      return dischargingPlanArrStatusId_;
    }
    /**
     * <code>int64 dischargingPlanArrStatusId = 13;</code>
     *
     * @param value The dischargingPlanArrStatusId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargingPlanArrStatusId(long value) {

      dischargingPlanArrStatusId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 dischargingPlanArrStatusId = 13;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargingPlanArrStatusId() {

      dischargingPlanArrStatusId_ = 0L;
      onChanged();
      return this;
    }

    private long dischargingPlanDepStatusId_;
    /**
     * <code>int64 dischargingPlanDepStatusId = 14;</code>
     *
     * @return The dischargingPlanDepStatusId.
     */
    public long getDischargingPlanDepStatusId() {
      return dischargingPlanDepStatusId_;
    }
    /**
     * <code>int64 dischargingPlanDepStatusId = 14;</code>
     *
     * @param value The dischargingPlanDepStatusId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargingPlanDepStatusId(long value) {

      dischargingPlanDepStatusId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 dischargingPlanDepStatusId = 14;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargingPlanDepStatusId() {

      dischargingPlanDepStatusId_ = 0L;
      onChanged();
      return this;
    }

    private long dischargePatternId_;
    /**
     * <code>int64 dischargePatternId = 15;</code>
     *
     * @return The dischargePatternId.
     */
    public long getDischargePatternId() {
      return dischargePatternId_;
    }
    /**
     * <code>int64 dischargePatternId = 15;</code>
     *
     * @param value The dischargePatternId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargePatternId(long value) {

      dischargePatternId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 dischargePatternId = 15;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargePatternId() {

      dischargePatternId_ = 0L;
      onChanged();
      return this;
    }

    private boolean isDischargingInstructionsComplete_;
    /**
     * <code>bool isDischargingInstructionsComplete = 16;</code>
     *
     * @return The isDischargingInstructionsComplete.
     */
    public boolean getIsDischargingInstructionsComplete() {
      return isDischargingInstructionsComplete_;
    }
    /**
     * <code>bool isDischargingInstructionsComplete = 16;</code>
     *
     * @param value The isDischargingInstructionsComplete to set.
     * @return This builder for chaining.
     */
    public Builder setIsDischargingInstructionsComplete(boolean value) {

      isDischargingInstructionsComplete_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool isDischargingInstructionsComplete = 16;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearIsDischargingInstructionsComplete() {

      isDischargingInstructionsComplete_ = false;
      onChanged();
      return this;
    }

    private boolean isDischargingSequenceGenerated_;
    /**
     * <code>bool isDischargingSequenceGenerated = 17;</code>
     *
     * @return The isDischargingSequenceGenerated.
     */
    public boolean getIsDischargingSequenceGenerated() {
      return isDischargingSequenceGenerated_;
    }
    /**
     * <code>bool isDischargingSequenceGenerated = 17;</code>
     *
     * @param value The isDischargingSequenceGenerated to set.
     * @return This builder for chaining.
     */
    public Builder setIsDischargingSequenceGenerated(boolean value) {

      isDischargingSequenceGenerated_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool isDischargingSequenceGenerated = 17;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearIsDischargingSequenceGenerated() {

      isDischargingSequenceGenerated_ = false;
      onChanged();
      return this;
    }

    private boolean isDischargingPlanGenerated_;
    /**
     * <code>bool isDischargingPlanGenerated = 18;</code>
     *
     * @return The isDischargingPlanGenerated.
     */
    public boolean getIsDischargingPlanGenerated() {
      return isDischargingPlanGenerated_;
    }
    /**
     * <code>bool isDischargingPlanGenerated = 18;</code>
     *
     * @param value The isDischargingPlanGenerated to set.
     * @return This builder for chaining.
     */
    public Builder setIsDischargingPlanGenerated(boolean value) {

      isDischargingPlanGenerated_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool isDischargingPlanGenerated = 18;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearIsDischargingPlanGenerated() {

      isDischargingPlanGenerated_ = false;
      onChanged();
      return this;
    }

    private long portId_;
    /**
     * <code>int64 portId = 19;</code>
     *
     * @return The portId.
     */
    public long getPortId() {
      return portId_;
    }
    /**
     * <code>int64 portId = 19;</code>
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
     * <code>int64 portId = 19;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPortId() {

      portId_ = 0L;
      onChanged();
      return this;
    }

    private long vesselId_;
    /**
     * <code>int64 vesselId = 20;</code>
     *
     * @return The vesselId.
     */
    public long getVesselId() {
      return vesselId_;
    }
    /**
     * <code>int64 vesselId = 20;</code>
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
     * <code>int64 vesselId = 20;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearVesselId() {

      vesselId_ = 0L;
      onChanged();
      return this;
    }

    private long voyageId_;
    /**
     * <code>int64 voyageId = 21;</code>
     *
     * @return The voyageId.
     */
    public long getVoyageId() {
      return voyageId_;
    }
    /**
     * <code>int64 voyageId = 21;</code>
     *
     * @param value The voyageId to set.
     * @return This builder for chaining.
     */
    public Builder setVoyageId(long value) {

      voyageId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 voyageId = 21;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearVoyageId() {

      voyageId_ = 0L;
      onChanged();
      return this;
    }

    private long portRotationId_;
    /**
     * <code>int64 portRotationId = 22;</code>
     *
     * @return The portRotationId.
     */
    public long getPortRotationId() {
      return portRotationId_;
    }
    /**
     * <code>int64 portRotationId = 22;</code>
     *
     * @param value The portRotationId to set.
     * @return This builder for chaining.
     */
    public Builder setPortRotationId(long value) {

      portRotationId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 portRotationId = 22;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPortRotationId() {

      portRotationId_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object dischargeStudyProcessId_ = "";
    /**
     * <code>string dischargeStudyProcessId = 23;</code>
     *
     * @return The dischargeStudyProcessId.
     */
    public java.lang.String getDischargeStudyProcessId() {
      java.lang.Object ref = dischargeStudyProcessId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        dischargeStudyProcessId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string dischargeStudyProcessId = 23;</code>
     *
     * @return The bytes for dischargeStudyProcessId.
     */
    public com.google.protobuf.ByteString getDischargeStudyProcessIdBytes() {
      java.lang.Object ref = dischargeStudyProcessId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        dischargeStudyProcessId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string dischargeStudyProcessId = 23;</code>
     *
     * @param value The dischargeStudyProcessId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargeStudyProcessId(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      dischargeStudyProcessId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string dischargeStudyProcessId = 23;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargeStudyProcessId() {

      dischargeStudyProcessId_ = getDefaultInstance().getDischargeStudyProcessId();
      onChanged();
      return this;
    }
    /**
     * <code>string dischargeStudyProcessId = 23;</code>
     *
     * @param value The bytes for dischargeStudyProcessId to set.
     * @return This builder for chaining.
     */
    public Builder setDischargeStudyProcessIdBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      dischargeStudyProcessId_ = value;
      onChanged();
      return this;
    }

    private boolean isDischargingInfoComplete_;
    /**
     * <code>bool isDischargingInfoComplete = 24;</code>
     *
     * @return The isDischargingInfoComplete.
     */
    public boolean getIsDischargingInfoComplete() {
      return isDischargingInfoComplete_;
    }
    /**
     * <code>bool isDischargingInfoComplete = 24;</code>
     *
     * @param value The isDischargingInfoComplete to set.
     * @return This builder for chaining.
     */
    public Builder setIsDischargingInfoComplete(boolean value) {

      isDischargingInfoComplete_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool isDischargingInfoComplete = 24;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearIsDischargingInfoComplete() {

      isDischargingInfoComplete_ = false;
      onChanged();
      return this;
    }

    private boolean dischargeSlopTanksFirst_;
    /**
     *
     *
     * <pre>
     * Cargo Section
     * </pre>
     *
     * <code>bool dischargeSlopTanksFirst = 25;</code>
     *
     * @return The dischargeSlopTanksFirst.
     */
    public boolean getDischargeSlopTanksFirst() {
      return dischargeSlopTanksFirst_;
    }
    /**
     *
     *
     * <pre>
     * Cargo Section
     * </pre>
     *
     * <code>bool dischargeSlopTanksFirst = 25;</code>
     *
     * @param value The dischargeSlopTanksFirst to set.
     * @return This builder for chaining.
     */
    public Builder setDischargeSlopTanksFirst(boolean value) {

      dischargeSlopTanksFirst_ = value;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Cargo Section
     * </pre>
     *
     * <code>bool dischargeSlopTanksFirst = 25;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargeSlopTanksFirst() {

      dischargeSlopTanksFirst_ = false;
      onChanged();
      return this;
    }

    private boolean dischargeCommingledCargoSeparately_;
    /**
     * <code>bool dischargeCommingledCargoSeparately = 26;</code>
     *
     * @return The dischargeCommingledCargoSeparately.
     */
    public boolean getDischargeCommingledCargoSeparately() {
      return dischargeCommingledCargoSeparately_;
    }
    /**
     * <code>bool dischargeCommingledCargoSeparately = 26;</code>
     *
     * @param value The dischargeCommingledCargoSeparately to set.
     * @return This builder for chaining.
     */
    public Builder setDischargeCommingledCargoSeparately(boolean value) {

      dischargeCommingledCargoSeparately_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool dischargeCommingledCargoSeparately = 26;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDischargeCommingledCargoSeparately() {

      dischargeCommingledCargoSeparately_ = false;
      onChanged();
      return this;
    }

    private boolean isDischargeInfoComplete_;
    /**
     * <code>bool isDischargeInfoComplete = 27;</code>
     *
     * @return The isDischargeInfoComplete.
     */
    public boolean getIsDischargeInfoComplete() {
      return isDischargeInfoComplete_;
    }
    /**
     * <code>bool isDischargeInfoComplete = 27;</code>
     *
     * @param value The isDischargeInfoComplete to set.
     * @return This builder for chaining.
     */
    public Builder setIsDischargeInfoComplete(boolean value) {

      isDischargeInfoComplete_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool isDischargeInfoComplete = 27;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearIsDischargeInfoComplete() {

      isDischargeInfoComplete_ = false;
      onChanged();
      return this;
    }

    private java.util.List<com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails>
        dischargeQuantityCargoDetails_ = java.util.Collections.emptyList();

    private void ensureDischargeQuantityCargoDetailsIsMutable() {
      if (!((bitField0_ & 0x00000004) != 0)) {
        dischargeQuantityCargoDetails_ =
            new java.util.ArrayList<
                com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails>(
                dischargeQuantityCargoDetails_);
        bitField0_ |= 0x00000004;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails,
            com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder,
            com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsOrBuilder>
        dischargeQuantityCargoDetailsBuilder_;

    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public java.util.List<com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails>
        getDischargeQuantityCargoDetailsList() {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(dischargeQuantityCargoDetails_);
      } else {
        return dischargeQuantityCargoDetailsBuilder_.getMessageList();
      }
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public int getDischargeQuantityCargoDetailsCount() {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        return dischargeQuantityCargoDetails_.size();
      } else {
        return dischargeQuantityCargoDetailsBuilder_.getCount();
      }
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails
        getDischargeQuantityCargoDetails(int index) {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        return dischargeQuantityCargoDetails_.get(index);
      } else {
        return dischargeQuantityCargoDetailsBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public Builder setDischargeQuantityCargoDetails(
        int index, com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails value) {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDischargeQuantityCargoDetailsIsMutable();
        dischargeQuantityCargoDetails_.set(index, value);
        onChanged();
      } else {
        dischargeQuantityCargoDetailsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public Builder setDischargeQuantityCargoDetails(
        int index,
        com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder
            builderForValue) {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        ensureDischargeQuantityCargoDetailsIsMutable();
        dischargeQuantityCargoDetails_.set(index, builderForValue.build());
        onChanged();
      } else {
        dischargeQuantityCargoDetailsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public Builder addDischargeQuantityCargoDetails(
        com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails value) {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDischargeQuantityCargoDetailsIsMutable();
        dischargeQuantityCargoDetails_.add(value);
        onChanged();
      } else {
        dischargeQuantityCargoDetailsBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public Builder addDischargeQuantityCargoDetails(
        int index, com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails value) {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDischargeQuantityCargoDetailsIsMutable();
        dischargeQuantityCargoDetails_.add(index, value);
        onChanged();
      } else {
        dischargeQuantityCargoDetailsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public Builder addDischargeQuantityCargoDetails(
        com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder
            builderForValue) {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        ensureDischargeQuantityCargoDetailsIsMutable();
        dischargeQuantityCargoDetails_.add(builderForValue.build());
        onChanged();
      } else {
        dischargeQuantityCargoDetailsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public Builder addDischargeQuantityCargoDetails(
        int index,
        com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder
            builderForValue) {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        ensureDischargeQuantityCargoDetailsIsMutable();
        dischargeQuantityCargoDetails_.add(index, builderForValue.build());
        onChanged();
      } else {
        dischargeQuantityCargoDetailsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public Builder addAllDischargeQuantityCargoDetails(
        java.lang.Iterable<
                ? extends com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails>
            values) {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        ensureDischargeQuantityCargoDetailsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, dischargeQuantityCargoDetails_);
        onChanged();
      } else {
        dischargeQuantityCargoDetailsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public Builder clearDischargeQuantityCargoDetails() {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        dischargeQuantityCargoDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
      } else {
        dischargeQuantityCargoDetailsBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public Builder removeDischargeQuantityCargoDetails(int index) {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        ensureDischargeQuantityCargoDetailsIsMutable();
        dischargeQuantityCargoDetails_.remove(index);
        onChanged();
      } else {
        dischargeQuantityCargoDetailsBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder
        getDischargeQuantityCargoDetailsBuilder(int index) {
      return getDischargeQuantityCargoDetailsFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsOrBuilder
        getDischargeQuantityCargoDetailsOrBuilder(int index) {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        return dischargeQuantityCargoDetails_.get(index);
      } else {
        return dischargeQuantityCargoDetailsBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public java.util.List<
            ? extends
                com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsOrBuilder>
        getDischargeQuantityCargoDetailsOrBuilderList() {
      if (dischargeQuantityCargoDetailsBuilder_ != null) {
        return dischargeQuantityCargoDetailsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(dischargeQuantityCargoDetails_);
      }
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder
        addDischargeQuantityCargoDetailsBuilder() {
      return getDischargeQuantityCargoDetailsFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails
                  .getDefaultInstance());
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder
        addDischargeQuantityCargoDetailsBuilder(int index) {
      return getDischargeQuantityCargoDetailsFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails
                  .getDefaultInstance());
    }
    /** <code>repeated .DischargeQuantityCargoDetails dischargeQuantityCargoDetails = 28;</code> */
    public java.util.List<
            com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder>
        getDischargeQuantityCargoDetailsBuilderList() {
      return getDischargeQuantityCargoDetailsFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails,
            com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder,
            com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsOrBuilder>
        getDischargeQuantityCargoDetailsFieldBuilder() {
      if (dischargeQuantityCargoDetailsBuilder_ == null) {
        dischargeQuantityCargoDetailsBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails,
                com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetails.Builder,
                com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsOrBuilder>(
                dischargeQuantityCargoDetails_,
                ((bitField0_ & 0x00000004) != 0),
                getParentForChildren(),
                isClean());
        dischargeQuantityCargoDetails_ = null;
      }
      return dischargeQuantityCargoDetailsBuilder_;
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
