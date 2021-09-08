/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

/** Protobuf type {@code DischargingSequence} */
public final class DischargingSequence extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:DischargingSequence)
    DischargingSequenceOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use DischargingSequence.newBuilder() to construct.
  private DischargingSequence(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private DischargingSequence() {
    stageName_ = "";
    cargoValves_ = java.util.Collections.emptyList();
    ballastValves_ = java.util.Collections.emptyList();
    loadingRates_ = java.util.Collections.emptyList();
    deBallastingRates_ = java.util.Collections.emptyList();
    loadingPlanPortWiseDetails_ = java.util.Collections.emptyList();
    cargoLoadingRate1_ = "";
    cargoLoadingRate2_ = "";
    ballastOperations_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new DischargingSequence();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
    return this.unknownFields;
  }

  private DischargingSequence(
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
              cargoNominationId_ = input.readInt64();
              break;
            }
          case 16:
            {
              startTime_ = input.readInt32();
              break;
            }
          case 24:
            {
              endTime_ = input.readInt32();
              break;
            }
          case 32:
            {
              portId_ = input.readInt64();
              break;
            }
          case 40:
            {
              sequenceNumber_ = input.readInt32();
              break;
            }
          case 50:
            {
              java.lang.String s = input.readStringRequireUtf8();

              stageName_ = s;
              break;
            }
          case 56:
            {
              toLoadicator_ = input.readBool();
              break;
            }
          case 66:
            {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                cargoValves_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>();
                mutable_bitField0_ |= 0x00000001;
              }
              cargoValves_.add(
                  input.readMessage(
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.parser(),
                      extensionRegistry));
              break;
            }
          case 74:
            {
              if (!((mutable_bitField0_ & 0x00000002) != 0)) {
                ballastValves_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>();
                mutable_bitField0_ |= 0x00000002;
              }
              ballastValves_.add(
                  input.readMessage(
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.parser(),
                      extensionRegistry));
              break;
            }
          case 82:
            {
              if (!((mutable_bitField0_ & 0x00000004) != 0)) {
                loadingRates_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate>();
                mutable_bitField0_ |= 0x00000004;
              }
              loadingRates_.add(
                  input.readMessage(
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate
                          .parser(),
                      extensionRegistry));
              break;
            }
          case 90:
            {
              if (!((mutable_bitField0_ & 0x00000008) != 0)) {
                deBallastingRates_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.loading_plan.LoadingPlanModels
                            .DeBallastingRate>();
                mutable_bitField0_ |= 0x00000008;
              }
              deBallastingRates_.add(
                  input.readMessage(
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate
                          .parser(),
                      extensionRegistry));
              break;
            }
          case 98:
            {
              if (!((mutable_bitField0_ & 0x00000010) != 0)) {
                loadingPlanPortWiseDetails_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.loading_plan.LoadingPlanModels
                            .LoadingPlanPortWiseDetails>();
                mutable_bitField0_ |= 0x00000010;
              }
              loadingPlanPortWiseDetails_.add(
                  input.readMessage(
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingPlanPortWiseDetails.parser(),
                      extensionRegistry));
              break;
            }
          case 106:
            {
              java.lang.String s = input.readStringRequireUtf8();

              cargoLoadingRate1_ = s;
              break;
            }
          case 114:
            {
              java.lang.String s = input.readStringRequireUtf8();

              cargoLoadingRate2_ = s;
              break;
            }
          case 122:
            {
              if (!((mutable_bitField0_ & 0x00000020) != 0)) {
                ballastOperations_ =
                    new java.util.ArrayList<
                        com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation>();
                mutable_bitField0_ |= 0x00000020;
              }
              ballastOperations_.add(
                  input.readMessage(
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation
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
        cargoValves_ = java.util.Collections.unmodifiableList(cargoValves_);
      }
      if (((mutable_bitField0_ & 0x00000002) != 0)) {
        ballastValves_ = java.util.Collections.unmodifiableList(ballastValves_);
      }
      if (((mutable_bitField0_ & 0x00000004) != 0)) {
        loadingRates_ = java.util.Collections.unmodifiableList(loadingRates_);
      }
      if (((mutable_bitField0_ & 0x00000008) != 0)) {
        deBallastingRates_ = java.util.Collections.unmodifiableList(deBallastingRates_);
      }
      if (((mutable_bitField0_ & 0x00000010) != 0)) {
        loadingPlanPortWiseDetails_ =
            java.util.Collections.unmodifiableList(loadingPlanPortWiseDetails_);
      }
      if (((mutable_bitField0_ & 0x00000020) != 0)) {
        ballastOperations_ = java.util.Collections.unmodifiableList(ballastOperations_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargingSequence_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.cpdss.common.generated.discharge_plan.DischargePlanModels
        .internal_static_DischargingSequence_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.cpdss.common.generated.discharge_plan.DischargingSequence.class,
            com.cpdss.common.generated.discharge_plan.DischargingSequence.Builder.class);
  }

  public static final int CARGONOMINATIONID_FIELD_NUMBER = 1;
  private long cargoNominationId_;
  /**
   * <code>int64 cargoNominationId = 1;</code>
   *
   * @return The cargoNominationId.
   */
  public long getCargoNominationId() {
    return cargoNominationId_;
  }

  public static final int STARTTIME_FIELD_NUMBER = 2;
  private int startTime_;
  /**
   * <code>int32 startTime = 2;</code>
   *
   * @return The startTime.
   */
  public int getStartTime() {
    return startTime_;
  }

  public static final int ENDTIME_FIELD_NUMBER = 3;
  private int endTime_;
  /**
   * <code>int32 endTime = 3;</code>
   *
   * @return The endTime.
   */
  public int getEndTime() {
    return endTime_;
  }

  public static final int PORTID_FIELD_NUMBER = 4;
  private long portId_;
  /**
   * <code>int64 portId = 4;</code>
   *
   * @return The portId.
   */
  public long getPortId() {
    return portId_;
  }

  public static final int SEQUENCENUMBER_FIELD_NUMBER = 5;
  private int sequenceNumber_;
  /**
   * <code>int32 sequenceNumber = 5;</code>
   *
   * @return The sequenceNumber.
   */
  public int getSequenceNumber() {
    return sequenceNumber_;
  }

  public static final int STAGENAME_FIELD_NUMBER = 6;
  private volatile java.lang.Object stageName_;
  /**
   * <code>string stageName = 6;</code>
   *
   * @return The stageName.
   */
  public java.lang.String getStageName() {
    java.lang.Object ref = stageName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      stageName_ = s;
      return s;
    }
  }
  /**
   * <code>string stageName = 6;</code>
   *
   * @return The bytes for stageName.
   */
  public com.google.protobuf.ByteString getStageNameBytes() {
    java.lang.Object ref = stageName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      stageName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TOLOADICATOR_FIELD_NUMBER = 7;
  private boolean toLoadicator_;
  /**
   * <code>bool toLoadicator = 7;</code>
   *
   * @return The toLoadicator.
   */
  public boolean getToLoadicator() {
    return toLoadicator_;
  }

  public static final int CARGOVALVES_FIELD_NUMBER = 8;
  private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
      cargoValves_;
  /** <code>repeated .Valve cargoValves = 8;</code> */
  public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
      getCargoValvesList() {
    return cargoValves_;
  }
  /** <code>repeated .Valve cargoValves = 8;</code> */
  public java.util.List<
          ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>
      getCargoValvesOrBuilderList() {
    return cargoValves_;
  }
  /** <code>repeated .Valve cargoValves = 8;</code> */
  public int getCargoValvesCount() {
    return cargoValves_.size();
  }
  /** <code>repeated .Valve cargoValves = 8;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve getCargoValves(int index) {
    return cargoValves_.get(index);
  }
  /** <code>repeated .Valve cargoValves = 8;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder
      getCargoValvesOrBuilder(int index) {
    return cargoValves_.get(index);
  }

  public static final int BALLASTVALVES_FIELD_NUMBER = 9;
  private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
      ballastValves_;
  /** <code>repeated .Valve ballastValves = 9;</code> */
  public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
      getBallastValvesList() {
    return ballastValves_;
  }
  /** <code>repeated .Valve ballastValves = 9;</code> */
  public java.util.List<
          ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>
      getBallastValvesOrBuilderList() {
    return ballastValves_;
  }
  /** <code>repeated .Valve ballastValves = 9;</code> */
  public int getBallastValvesCount() {
    return ballastValves_.size();
  }
  /** <code>repeated .Valve ballastValves = 9;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve getBallastValves(
      int index) {
    return ballastValves_.get(index);
  }
  /** <code>repeated .Valve ballastValves = 9;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder
      getBallastValvesOrBuilder(int index) {
    return ballastValves_.get(index);
  }

  public static final int LOADINGRATES_FIELD_NUMBER = 10;
  private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate>
      loadingRates_;
  /** <code>repeated .LoadingRate loadingRates = 10;</code> */
  public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate>
      getLoadingRatesList() {
    return loadingRates_;
  }
  /** <code>repeated .LoadingRate loadingRates = 10;</code> */
  public java.util.List<
          ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRateOrBuilder>
      getLoadingRatesOrBuilderList() {
    return loadingRates_;
  }
  /** <code>repeated .LoadingRate loadingRates = 10;</code> */
  public int getLoadingRatesCount() {
    return loadingRates_.size();
  }
  /** <code>repeated .LoadingRate loadingRates = 10;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate getLoadingRates(
      int index) {
    return loadingRates_.get(index);
  }
  /** <code>repeated .LoadingRate loadingRates = 10;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRateOrBuilder
      getLoadingRatesOrBuilder(int index) {
    return loadingRates_.get(index);
  }

  public static final int DEBALLASTINGRATES_FIELD_NUMBER = 11;
  private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate>
      deBallastingRates_;
  /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
  public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate>
      getDeBallastingRatesList() {
    return deBallastingRates_;
  }
  /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
  public java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRateOrBuilder>
      getDeBallastingRatesOrBuilderList() {
    return deBallastingRates_;
  }
  /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
  public int getDeBallastingRatesCount() {
    return deBallastingRates_.size();
  }
  /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate
      getDeBallastingRates(int index) {
    return deBallastingRates_.get(index);
  }
  /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRateOrBuilder
      getDeBallastingRatesOrBuilder(int index) {
    return deBallastingRates_.get(index);
  }

  public static final int LOADINGPLANPORTWISEDETAILS_FIELD_NUMBER = 12;
  private java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails>
      loadingPlanPortWiseDetails_;
  /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
  public java.util.List<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails>
      getLoadingPlanPortWiseDetailsList() {
    return loadingPlanPortWiseDetails_;
  }
  /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
  public java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingPlanPortWiseDetailsOrBuilder>
      getLoadingPlanPortWiseDetailsOrBuilderList() {
    return loadingPlanPortWiseDetails_;
  }
  /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
  public int getLoadingPlanPortWiseDetailsCount() {
    return loadingPlanPortWiseDetails_.size();
  }
  /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
      getLoadingPlanPortWiseDetails(int index) {
    return loadingPlanPortWiseDetails_.get(index);
  }
  /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels
          .LoadingPlanPortWiseDetailsOrBuilder
      getLoadingPlanPortWiseDetailsOrBuilder(int index) {
    return loadingPlanPortWiseDetails_.get(index);
  }

  public static final int CARGOLOADINGRATE1_FIELD_NUMBER = 13;
  private volatile java.lang.Object cargoLoadingRate1_;
  /**
   * <code>string cargoLoadingRate1 = 13;</code>
   *
   * @return The cargoLoadingRate1.
   */
  public java.lang.String getCargoLoadingRate1() {
    java.lang.Object ref = cargoLoadingRate1_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      cargoLoadingRate1_ = s;
      return s;
    }
  }
  /**
   * <code>string cargoLoadingRate1 = 13;</code>
   *
   * @return The bytes for cargoLoadingRate1.
   */
  public com.google.protobuf.ByteString getCargoLoadingRate1Bytes() {
    java.lang.Object ref = cargoLoadingRate1_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      cargoLoadingRate1_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CARGOLOADINGRATE2_FIELD_NUMBER = 14;
  private volatile java.lang.Object cargoLoadingRate2_;
  /**
   * <code>string cargoLoadingRate2 = 14;</code>
   *
   * @return The cargoLoadingRate2.
   */
  public java.lang.String getCargoLoadingRate2() {
    java.lang.Object ref = cargoLoadingRate2_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      cargoLoadingRate2_ = s;
      return s;
    }
  }
  /**
   * <code>string cargoLoadingRate2 = 14;</code>
   *
   * @return The bytes for cargoLoadingRate2.
   */
  public com.google.protobuf.ByteString getCargoLoadingRate2Bytes() {
    java.lang.Object ref = cargoLoadingRate2_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      cargoLoadingRate2_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int BALLASTOPERATIONS_FIELD_NUMBER = 15;
  private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation>
      ballastOperations_;
  /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
  public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation>
      getBallastOperationsList() {
    return ballastOperations_;
  }
  /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
  public java.util.List<
          ? extends
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperationOrBuilder>
      getBallastOperationsOrBuilderList() {
    return ballastOperations_;
  }
  /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
  public int getBallastOperationsCount() {
    return ballastOperations_.size();
  }
  /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation
      getBallastOperations(int index) {
    return ballastOperations_.get(index);
  }
  /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperationOrBuilder
      getBallastOperationsOrBuilder(int index) {
    return ballastOperations_.get(index);
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
    if (cargoNominationId_ != 0L) {
      output.writeInt64(1, cargoNominationId_);
    }
    if (startTime_ != 0) {
      output.writeInt32(2, startTime_);
    }
    if (endTime_ != 0) {
      output.writeInt32(3, endTime_);
    }
    if (portId_ != 0L) {
      output.writeInt64(4, portId_);
    }
    if (sequenceNumber_ != 0) {
      output.writeInt32(5, sequenceNumber_);
    }
    if (!getStageNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, stageName_);
    }
    if (toLoadicator_ != false) {
      output.writeBool(7, toLoadicator_);
    }
    for (int i = 0; i < cargoValves_.size(); i++) {
      output.writeMessage(8, cargoValves_.get(i));
    }
    for (int i = 0; i < ballastValves_.size(); i++) {
      output.writeMessage(9, ballastValves_.get(i));
    }
    for (int i = 0; i < loadingRates_.size(); i++) {
      output.writeMessage(10, loadingRates_.get(i));
    }
    for (int i = 0; i < deBallastingRates_.size(); i++) {
      output.writeMessage(11, deBallastingRates_.get(i));
    }
    for (int i = 0; i < loadingPlanPortWiseDetails_.size(); i++) {
      output.writeMessage(12, loadingPlanPortWiseDetails_.get(i));
    }
    if (!getCargoLoadingRate1Bytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 13, cargoLoadingRate1_);
    }
    if (!getCargoLoadingRate2Bytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 14, cargoLoadingRate2_);
    }
    for (int i = 0; i < ballastOperations_.size(); i++) {
      output.writeMessage(15, ballastOperations_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (cargoNominationId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, cargoNominationId_);
    }
    if (startTime_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(2, startTime_);
    }
    if (endTime_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(3, endTime_);
    }
    if (portId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream.computeInt64Size(4, portId_);
    }
    if (sequenceNumber_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(5, sequenceNumber_);
    }
    if (!getStageNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, stageName_);
    }
    if (toLoadicator_ != false) {
      size += com.google.protobuf.CodedOutputStream.computeBoolSize(7, toLoadicator_);
    }
    for (int i = 0; i < cargoValves_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(8, cargoValves_.get(i));
    }
    for (int i = 0; i < ballastValves_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(9, ballastValves_.get(i));
    }
    for (int i = 0; i < loadingRates_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(10, loadingRates_.get(i));
    }
    for (int i = 0; i < deBallastingRates_.size(); i++) {
      size +=
          com.google.protobuf.CodedOutputStream.computeMessageSize(11, deBallastingRates_.get(i));
    }
    for (int i = 0; i < loadingPlanPortWiseDetails_.size(); i++) {
      size +=
          com.google.protobuf.CodedOutputStream.computeMessageSize(
              12, loadingPlanPortWiseDetails_.get(i));
    }
    if (!getCargoLoadingRate1Bytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(13, cargoLoadingRate1_);
    }
    if (!getCargoLoadingRate2Bytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(14, cargoLoadingRate2_);
    }
    for (int i = 0; i < ballastOperations_.size(); i++) {
      size +=
          com.google.protobuf.CodedOutputStream.computeMessageSize(15, ballastOperations_.get(i));
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
    if (!(obj instanceof com.cpdss.common.generated.discharge_plan.DischargingSequence)) {
      return super.equals(obj);
    }
    com.cpdss.common.generated.discharge_plan.DischargingSequence other =
        (com.cpdss.common.generated.discharge_plan.DischargingSequence) obj;

    if (getCargoNominationId() != other.getCargoNominationId()) return false;
    if (getStartTime() != other.getStartTime()) return false;
    if (getEndTime() != other.getEndTime()) return false;
    if (getPortId() != other.getPortId()) return false;
    if (getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!getStageName().equals(other.getStageName())) return false;
    if (getToLoadicator() != other.getToLoadicator()) return false;
    if (!getCargoValvesList().equals(other.getCargoValvesList())) return false;
    if (!getBallastValvesList().equals(other.getBallastValvesList())) return false;
    if (!getLoadingRatesList().equals(other.getLoadingRatesList())) return false;
    if (!getDeBallastingRatesList().equals(other.getDeBallastingRatesList())) return false;
    if (!getLoadingPlanPortWiseDetailsList().equals(other.getLoadingPlanPortWiseDetailsList()))
      return false;
    if (!getCargoLoadingRate1().equals(other.getCargoLoadingRate1())) return false;
    if (!getCargoLoadingRate2().equals(other.getCargoLoadingRate2())) return false;
    if (!getBallastOperationsList().equals(other.getBallastOperationsList())) return false;
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
    hash = (37 * hash) + CARGONOMINATIONID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getCargoNominationId());
    hash = (37 * hash) + STARTTIME_FIELD_NUMBER;
    hash = (53 * hash) + getStartTime();
    hash = (37 * hash) + ENDTIME_FIELD_NUMBER;
    hash = (53 * hash) + getEndTime();
    hash = (37 * hash) + PORTID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(getPortId());
    hash = (37 * hash) + SEQUENCENUMBER_FIELD_NUMBER;
    hash = (53 * hash) + getSequenceNumber();
    hash = (37 * hash) + STAGENAME_FIELD_NUMBER;
    hash = (53 * hash) + getStageName().hashCode();
    hash = (37 * hash) + TOLOADICATOR_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getToLoadicator());
    if (getCargoValvesCount() > 0) {
      hash = (37 * hash) + CARGOVALVES_FIELD_NUMBER;
      hash = (53 * hash) + getCargoValvesList().hashCode();
    }
    if (getBallastValvesCount() > 0) {
      hash = (37 * hash) + BALLASTVALVES_FIELD_NUMBER;
      hash = (53 * hash) + getBallastValvesList().hashCode();
    }
    if (getLoadingRatesCount() > 0) {
      hash = (37 * hash) + LOADINGRATES_FIELD_NUMBER;
      hash = (53 * hash) + getLoadingRatesList().hashCode();
    }
    if (getDeBallastingRatesCount() > 0) {
      hash = (37 * hash) + DEBALLASTINGRATES_FIELD_NUMBER;
      hash = (53 * hash) + getDeBallastingRatesList().hashCode();
    }
    if (getLoadingPlanPortWiseDetailsCount() > 0) {
      hash = (37 * hash) + LOADINGPLANPORTWISEDETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getLoadingPlanPortWiseDetailsList().hashCode();
    }
    hash = (37 * hash) + CARGOLOADINGRATE1_FIELD_NUMBER;
    hash = (53 * hash) + getCargoLoadingRate1().hashCode();
    hash = (37 * hash) + CARGOLOADINGRATE2_FIELD_NUMBER;
    hash = (53 * hash) + getCargoLoadingRate2().hashCode();
    if (getBallastOperationsCount() > 0) {
      hash = (37 * hash) + BALLASTOPERATIONS_FIELD_NUMBER;
      hash = (53 * hash) + getBallastOperationsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence parseFrom(
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
      com.cpdss.common.generated.discharge_plan.DischargingSequence prototype) {
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
  /** Protobuf type {@code DischargingSequence} */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:DischargingSequence)
      com.cpdss.common.generated.discharge_plan.DischargingSequenceOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingSequence_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingSequence_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cpdss.common.generated.discharge_plan.DischargingSequence.class,
              com.cpdss.common.generated.discharge_plan.DischargingSequence.Builder.class);
    }

    // Construct using com.cpdss.common.generated.discharge_plan.DischargingSequence.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getCargoValvesFieldBuilder();
        getBallastValvesFieldBuilder();
        getLoadingRatesFieldBuilder();
        getDeBallastingRatesFieldBuilder();
        getLoadingPlanPortWiseDetailsFieldBuilder();
        getBallastOperationsFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      cargoNominationId_ = 0L;

      startTime_ = 0;

      endTime_ = 0;

      portId_ = 0L;

      sequenceNumber_ = 0;

      stageName_ = "";

      toLoadicator_ = false;

      if (cargoValvesBuilder_ == null) {
        cargoValves_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        cargoValvesBuilder_.clear();
      }
      if (ballastValvesBuilder_ == null) {
        ballastValves_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        ballastValvesBuilder_.clear();
      }
      if (loadingRatesBuilder_ == null) {
        loadingRates_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
      } else {
        loadingRatesBuilder_.clear();
      }
      if (deBallastingRatesBuilder_ == null) {
        deBallastingRates_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000008);
      } else {
        deBallastingRatesBuilder_.clear();
      }
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        loadingPlanPortWiseDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000010);
      } else {
        loadingPlanPortWiseDetailsBuilder_.clear();
      }
      cargoLoadingRate1_ = "";

      cargoLoadingRate2_ = "";

      if (ballastOperationsBuilder_ == null) {
        ballastOperations_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000020);
      } else {
        ballastOperationsBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanModels
          .internal_static_DischargingSequence_descriptor;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingSequence
        getDefaultInstanceForType() {
      return com.cpdss.common.generated.discharge_plan.DischargingSequence.getDefaultInstance();
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingSequence build() {
      com.cpdss.common.generated.discharge_plan.DischargingSequence result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.cpdss.common.generated.discharge_plan.DischargingSequence buildPartial() {
      com.cpdss.common.generated.discharge_plan.DischargingSequence result =
          new com.cpdss.common.generated.discharge_plan.DischargingSequence(this);
      int from_bitField0_ = bitField0_;
      result.cargoNominationId_ = cargoNominationId_;
      result.startTime_ = startTime_;
      result.endTime_ = endTime_;
      result.portId_ = portId_;
      result.sequenceNumber_ = sequenceNumber_;
      result.stageName_ = stageName_;
      result.toLoadicator_ = toLoadicator_;
      if (cargoValvesBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          cargoValves_ = java.util.Collections.unmodifiableList(cargoValves_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.cargoValves_ = cargoValves_;
      } else {
        result.cargoValves_ = cargoValvesBuilder_.build();
      }
      if (ballastValvesBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0)) {
          ballastValves_ = java.util.Collections.unmodifiableList(ballastValves_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.ballastValves_ = ballastValves_;
      } else {
        result.ballastValves_ = ballastValvesBuilder_.build();
      }
      if (loadingRatesBuilder_ == null) {
        if (((bitField0_ & 0x00000004) != 0)) {
          loadingRates_ = java.util.Collections.unmodifiableList(loadingRates_);
          bitField0_ = (bitField0_ & ~0x00000004);
        }
        result.loadingRates_ = loadingRates_;
      } else {
        result.loadingRates_ = loadingRatesBuilder_.build();
      }
      if (deBallastingRatesBuilder_ == null) {
        if (((bitField0_ & 0x00000008) != 0)) {
          deBallastingRates_ = java.util.Collections.unmodifiableList(deBallastingRates_);
          bitField0_ = (bitField0_ & ~0x00000008);
        }
        result.deBallastingRates_ = deBallastingRates_;
      } else {
        result.deBallastingRates_ = deBallastingRatesBuilder_.build();
      }
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        if (((bitField0_ & 0x00000010) != 0)) {
          loadingPlanPortWiseDetails_ =
              java.util.Collections.unmodifiableList(loadingPlanPortWiseDetails_);
          bitField0_ = (bitField0_ & ~0x00000010);
        }
        result.loadingPlanPortWiseDetails_ = loadingPlanPortWiseDetails_;
      } else {
        result.loadingPlanPortWiseDetails_ = loadingPlanPortWiseDetailsBuilder_.build();
      }
      result.cargoLoadingRate1_ = cargoLoadingRate1_;
      result.cargoLoadingRate2_ = cargoLoadingRate2_;
      if (ballastOperationsBuilder_ == null) {
        if (((bitField0_ & 0x00000020) != 0)) {
          ballastOperations_ = java.util.Collections.unmodifiableList(ballastOperations_);
          bitField0_ = (bitField0_ & ~0x00000020);
        }
        result.ballastOperations_ = ballastOperations_;
      } else {
        result.ballastOperations_ = ballastOperationsBuilder_.build();
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
      if (other instanceof com.cpdss.common.generated.discharge_plan.DischargingSequence) {
        return mergeFrom((com.cpdss.common.generated.discharge_plan.DischargingSequence) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.cpdss.common.generated.discharge_plan.DischargingSequence other) {
      if (other
          == com.cpdss.common.generated.discharge_plan.DischargingSequence.getDefaultInstance())
        return this;
      if (other.getCargoNominationId() != 0L) {
        setCargoNominationId(other.getCargoNominationId());
      }
      if (other.getStartTime() != 0) {
        setStartTime(other.getStartTime());
      }
      if (other.getEndTime() != 0) {
        setEndTime(other.getEndTime());
      }
      if (other.getPortId() != 0L) {
        setPortId(other.getPortId());
      }
      if (other.getSequenceNumber() != 0) {
        setSequenceNumber(other.getSequenceNumber());
      }
      if (!other.getStageName().isEmpty()) {
        stageName_ = other.stageName_;
        onChanged();
      }
      if (other.getToLoadicator() != false) {
        setToLoadicator(other.getToLoadicator());
      }
      if (cargoValvesBuilder_ == null) {
        if (!other.cargoValves_.isEmpty()) {
          if (cargoValves_.isEmpty()) {
            cargoValves_ = other.cargoValves_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureCargoValvesIsMutable();
            cargoValves_.addAll(other.cargoValves_);
          }
          onChanged();
        }
      } else {
        if (!other.cargoValves_.isEmpty()) {
          if (cargoValvesBuilder_.isEmpty()) {
            cargoValvesBuilder_.dispose();
            cargoValvesBuilder_ = null;
            cargoValves_ = other.cargoValves_;
            bitField0_ = (bitField0_ & ~0x00000001);
            cargoValvesBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getCargoValvesFieldBuilder()
                    : null;
          } else {
            cargoValvesBuilder_.addAllMessages(other.cargoValves_);
          }
        }
      }
      if (ballastValvesBuilder_ == null) {
        if (!other.ballastValves_.isEmpty()) {
          if (ballastValves_.isEmpty()) {
            ballastValves_ = other.ballastValves_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureBallastValvesIsMutable();
            ballastValves_.addAll(other.ballastValves_);
          }
          onChanged();
        }
      } else {
        if (!other.ballastValves_.isEmpty()) {
          if (ballastValvesBuilder_.isEmpty()) {
            ballastValvesBuilder_.dispose();
            ballastValvesBuilder_ = null;
            ballastValves_ = other.ballastValves_;
            bitField0_ = (bitField0_ & ~0x00000002);
            ballastValvesBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getBallastValvesFieldBuilder()
                    : null;
          } else {
            ballastValvesBuilder_.addAllMessages(other.ballastValves_);
          }
        }
      }
      if (loadingRatesBuilder_ == null) {
        if (!other.loadingRates_.isEmpty()) {
          if (loadingRates_.isEmpty()) {
            loadingRates_ = other.loadingRates_;
            bitField0_ = (bitField0_ & ~0x00000004);
          } else {
            ensureLoadingRatesIsMutable();
            loadingRates_.addAll(other.loadingRates_);
          }
          onChanged();
        }
      } else {
        if (!other.loadingRates_.isEmpty()) {
          if (loadingRatesBuilder_.isEmpty()) {
            loadingRatesBuilder_.dispose();
            loadingRatesBuilder_ = null;
            loadingRates_ = other.loadingRates_;
            bitField0_ = (bitField0_ & ~0x00000004);
            loadingRatesBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getLoadingRatesFieldBuilder()
                    : null;
          } else {
            loadingRatesBuilder_.addAllMessages(other.loadingRates_);
          }
        }
      }
      if (deBallastingRatesBuilder_ == null) {
        if (!other.deBallastingRates_.isEmpty()) {
          if (deBallastingRates_.isEmpty()) {
            deBallastingRates_ = other.deBallastingRates_;
            bitField0_ = (bitField0_ & ~0x00000008);
          } else {
            ensureDeBallastingRatesIsMutable();
            deBallastingRates_.addAll(other.deBallastingRates_);
          }
          onChanged();
        }
      } else {
        if (!other.deBallastingRates_.isEmpty()) {
          if (deBallastingRatesBuilder_.isEmpty()) {
            deBallastingRatesBuilder_.dispose();
            deBallastingRatesBuilder_ = null;
            deBallastingRates_ = other.deBallastingRates_;
            bitField0_ = (bitField0_ & ~0x00000008);
            deBallastingRatesBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getDeBallastingRatesFieldBuilder()
                    : null;
          } else {
            deBallastingRatesBuilder_.addAllMessages(other.deBallastingRates_);
          }
        }
      }
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        if (!other.loadingPlanPortWiseDetails_.isEmpty()) {
          if (loadingPlanPortWiseDetails_.isEmpty()) {
            loadingPlanPortWiseDetails_ = other.loadingPlanPortWiseDetails_;
            bitField0_ = (bitField0_ & ~0x00000010);
          } else {
            ensureLoadingPlanPortWiseDetailsIsMutable();
            loadingPlanPortWiseDetails_.addAll(other.loadingPlanPortWiseDetails_);
          }
          onChanged();
        }
      } else {
        if (!other.loadingPlanPortWiseDetails_.isEmpty()) {
          if (loadingPlanPortWiseDetailsBuilder_.isEmpty()) {
            loadingPlanPortWiseDetailsBuilder_.dispose();
            loadingPlanPortWiseDetailsBuilder_ = null;
            loadingPlanPortWiseDetails_ = other.loadingPlanPortWiseDetails_;
            bitField0_ = (bitField0_ & ~0x00000010);
            loadingPlanPortWiseDetailsBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getLoadingPlanPortWiseDetailsFieldBuilder()
                    : null;
          } else {
            loadingPlanPortWiseDetailsBuilder_.addAllMessages(other.loadingPlanPortWiseDetails_);
          }
        }
      }
      if (!other.getCargoLoadingRate1().isEmpty()) {
        cargoLoadingRate1_ = other.cargoLoadingRate1_;
        onChanged();
      }
      if (!other.getCargoLoadingRate2().isEmpty()) {
        cargoLoadingRate2_ = other.cargoLoadingRate2_;
        onChanged();
      }
      if (ballastOperationsBuilder_ == null) {
        if (!other.ballastOperations_.isEmpty()) {
          if (ballastOperations_.isEmpty()) {
            ballastOperations_ = other.ballastOperations_;
            bitField0_ = (bitField0_ & ~0x00000020);
          } else {
            ensureBallastOperationsIsMutable();
            ballastOperations_.addAll(other.ballastOperations_);
          }
          onChanged();
        }
      } else {
        if (!other.ballastOperations_.isEmpty()) {
          if (ballastOperationsBuilder_.isEmpty()) {
            ballastOperationsBuilder_.dispose();
            ballastOperationsBuilder_ = null;
            ballastOperations_ = other.ballastOperations_;
            bitField0_ = (bitField0_ & ~0x00000020);
            ballastOperationsBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getBallastOperationsFieldBuilder()
                    : null;
          } else {
            ballastOperationsBuilder_.addAllMessages(other.ballastOperations_);
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
      com.cpdss.common.generated.discharge_plan.DischargingSequence parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage =
            (com.cpdss.common.generated.discharge_plan.DischargingSequence)
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

    private long cargoNominationId_;
    /**
     * <code>int64 cargoNominationId = 1;</code>
     *
     * @return The cargoNominationId.
     */
    public long getCargoNominationId() {
      return cargoNominationId_;
    }
    /**
     * <code>int64 cargoNominationId = 1;</code>
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
     * <code>int64 cargoNominationId = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCargoNominationId() {

      cargoNominationId_ = 0L;
      onChanged();
      return this;
    }

    private int startTime_;
    /**
     * <code>int32 startTime = 2;</code>
     *
     * @return The startTime.
     */
    public int getStartTime() {
      return startTime_;
    }
    /**
     * <code>int32 startTime = 2;</code>
     *
     * @param value The startTime to set.
     * @return This builder for chaining.
     */
    public Builder setStartTime(int value) {

      startTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 startTime = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearStartTime() {

      startTime_ = 0;
      onChanged();
      return this;
    }

    private int endTime_;
    /**
     * <code>int32 endTime = 3;</code>
     *
     * @return The endTime.
     */
    public int getEndTime() {
      return endTime_;
    }
    /**
     * <code>int32 endTime = 3;</code>
     *
     * @param value The endTime to set.
     * @return This builder for chaining.
     */
    public Builder setEndTime(int value) {

      endTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 endTime = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearEndTime() {

      endTime_ = 0;
      onChanged();
      return this;
    }

    private long portId_;
    /**
     * <code>int64 portId = 4;</code>
     *
     * @return The portId.
     */
    public long getPortId() {
      return portId_;
    }
    /**
     * <code>int64 portId = 4;</code>
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
     * <code>int64 portId = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPortId() {

      portId_ = 0L;
      onChanged();
      return this;
    }

    private int sequenceNumber_;
    /**
     * <code>int32 sequenceNumber = 5;</code>
     *
     * @return The sequenceNumber.
     */
    public int getSequenceNumber() {
      return sequenceNumber_;
    }
    /**
     * <code>int32 sequenceNumber = 5;</code>
     *
     * @param value The sequenceNumber to set.
     * @return This builder for chaining.
     */
    public Builder setSequenceNumber(int value) {

      sequenceNumber_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 sequenceNumber = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearSequenceNumber() {

      sequenceNumber_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object stageName_ = "";
    /**
     * <code>string stageName = 6;</code>
     *
     * @return The stageName.
     */
    public java.lang.String getStageName() {
      java.lang.Object ref = stageName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        stageName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string stageName = 6;</code>
     *
     * @return The bytes for stageName.
     */
    public com.google.protobuf.ByteString getStageNameBytes() {
      java.lang.Object ref = stageName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        stageName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string stageName = 6;</code>
     *
     * @param value The stageName to set.
     * @return This builder for chaining.
     */
    public Builder setStageName(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      stageName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string stageName = 6;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearStageName() {

      stageName_ = getDefaultInstance().getStageName();
      onChanged();
      return this;
    }
    /**
     * <code>string stageName = 6;</code>
     *
     * @param value The bytes for stageName to set.
     * @return This builder for chaining.
     */
    public Builder setStageNameBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      stageName_ = value;
      onChanged();
      return this;
    }

    private boolean toLoadicator_;
    /**
     * <code>bool toLoadicator = 7;</code>
     *
     * @return The toLoadicator.
     */
    public boolean getToLoadicator() {
      return toLoadicator_;
    }
    /**
     * <code>bool toLoadicator = 7;</code>
     *
     * @param value The toLoadicator to set.
     * @return This builder for chaining.
     */
    public Builder setToLoadicator(boolean value) {

      toLoadicator_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool toLoadicator = 7;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearToLoadicator() {

      toLoadicator_ = false;
      onChanged();
      return this;
    }

    private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
        cargoValves_ = java.util.Collections.emptyList();

    private void ensureCargoValvesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        cargoValves_ =
            new java.util.ArrayList<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>(cargoValves_);
        bitField0_ |= 0x00000001;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>
        cargoValvesBuilder_;

    /** <code>repeated .Valve cargoValves = 8;</code> */
    public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
        getCargoValvesList() {
      if (cargoValvesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(cargoValves_);
      } else {
        return cargoValvesBuilder_.getMessageList();
      }
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public int getCargoValvesCount() {
      if (cargoValvesBuilder_ == null) {
        return cargoValves_.size();
      } else {
        return cargoValvesBuilder_.getCount();
      }
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve getCargoValves(
        int index) {
      if (cargoValvesBuilder_ == null) {
        return cargoValves_.get(index);
      } else {
        return cargoValvesBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public Builder setCargoValves(
        int index, com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve value) {
      if (cargoValvesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCargoValvesIsMutable();
        cargoValves_.set(index, value);
        onChanged();
      } else {
        cargoValvesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public Builder setCargoValves(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder builderForValue) {
      if (cargoValvesBuilder_ == null) {
        ensureCargoValvesIsMutable();
        cargoValves_.set(index, builderForValue.build());
        onChanged();
      } else {
        cargoValvesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public Builder addCargoValves(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve value) {
      if (cargoValvesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCargoValvesIsMutable();
        cargoValves_.add(value);
        onChanged();
      } else {
        cargoValvesBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public Builder addCargoValves(
        int index, com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve value) {
      if (cargoValvesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureCargoValvesIsMutable();
        cargoValves_.add(index, value);
        onChanged();
      } else {
        cargoValvesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public Builder addCargoValves(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder builderForValue) {
      if (cargoValvesBuilder_ == null) {
        ensureCargoValvesIsMutable();
        cargoValves_.add(builderForValue.build());
        onChanged();
      } else {
        cargoValvesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public Builder addCargoValves(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder builderForValue) {
      if (cargoValvesBuilder_ == null) {
        ensureCargoValvesIsMutable();
        cargoValves_.add(index, builderForValue.build());
        onChanged();
      } else {
        cargoValvesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public Builder addAllCargoValves(
        java.lang.Iterable<
                ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
            values) {
      if (cargoValvesBuilder_ == null) {
        ensureCargoValvesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, cargoValves_);
        onChanged();
      } else {
        cargoValvesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public Builder clearCargoValves() {
      if (cargoValvesBuilder_ == null) {
        cargoValves_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        cargoValvesBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public Builder removeCargoValves(int index) {
      if (cargoValvesBuilder_ == null) {
        ensureCargoValvesIsMutable();
        cargoValves_.remove(index);
        onChanged();
      } else {
        cargoValvesBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder
        getCargoValvesBuilder(int index) {
      return getCargoValvesFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder
        getCargoValvesOrBuilder(int index) {
      if (cargoValvesBuilder_ == null) {
        return cargoValves_.get(index);
      } else {
        return cargoValvesBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public java.util.List<
            ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>
        getCargoValvesOrBuilderList() {
      if (cargoValvesBuilder_ != null) {
        return cargoValvesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(cargoValves_);
      }
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder
        addCargoValvesBuilder() {
      return getCargoValvesFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.getDefaultInstance());
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder
        addCargoValvesBuilder(int index) {
      return getCargoValvesFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.getDefaultInstance());
    }
    /** <code>repeated .Valve cargoValves = 8;</code> */
    public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder>
        getCargoValvesBuilderList() {
      return getCargoValvesFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>
        getCargoValvesFieldBuilder() {
      if (cargoValvesBuilder_ == null) {
        cargoValvesBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>(
                cargoValves_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
        cargoValves_ = null;
      }
      return cargoValvesBuilder_;
    }

    private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
        ballastValves_ = java.util.Collections.emptyList();

    private void ensureBallastValvesIsMutable() {
      if (!((bitField0_ & 0x00000002) != 0)) {
        ballastValves_ =
            new java.util.ArrayList<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>(ballastValves_);
        bitField0_ |= 0x00000002;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>
        ballastValvesBuilder_;

    /** <code>repeated .Valve ballastValves = 9;</code> */
    public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
        getBallastValvesList() {
      if (ballastValvesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(ballastValves_);
      } else {
        return ballastValvesBuilder_.getMessageList();
      }
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public int getBallastValvesCount() {
      if (ballastValvesBuilder_ == null) {
        return ballastValves_.size();
      } else {
        return ballastValvesBuilder_.getCount();
      }
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve getBallastValves(
        int index) {
      if (ballastValvesBuilder_ == null) {
        return ballastValves_.get(index);
      } else {
        return ballastValvesBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public Builder setBallastValves(
        int index, com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve value) {
      if (ballastValvesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBallastValvesIsMutable();
        ballastValves_.set(index, value);
        onChanged();
      } else {
        ballastValvesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public Builder setBallastValves(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder builderForValue) {
      if (ballastValvesBuilder_ == null) {
        ensureBallastValvesIsMutable();
        ballastValves_.set(index, builderForValue.build());
        onChanged();
      } else {
        ballastValvesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public Builder addBallastValves(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve value) {
      if (ballastValvesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBallastValvesIsMutable();
        ballastValves_.add(value);
        onChanged();
      } else {
        ballastValvesBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public Builder addBallastValves(
        int index, com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve value) {
      if (ballastValvesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBallastValvesIsMutable();
        ballastValves_.add(index, value);
        onChanged();
      } else {
        ballastValvesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public Builder addBallastValves(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder builderForValue) {
      if (ballastValvesBuilder_ == null) {
        ensureBallastValvesIsMutable();
        ballastValves_.add(builderForValue.build());
        onChanged();
      } else {
        ballastValvesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public Builder addBallastValves(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder builderForValue) {
      if (ballastValvesBuilder_ == null) {
        ensureBallastValvesIsMutable();
        ballastValves_.add(index, builderForValue.build());
        onChanged();
      } else {
        ballastValvesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public Builder addAllBallastValves(
        java.lang.Iterable<
                ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve>
            values) {
      if (ballastValvesBuilder_ == null) {
        ensureBallastValvesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, ballastValves_);
        onChanged();
      } else {
        ballastValvesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public Builder clearBallastValves() {
      if (ballastValvesBuilder_ == null) {
        ballastValves_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        ballastValvesBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public Builder removeBallastValves(int index) {
      if (ballastValvesBuilder_ == null) {
        ensureBallastValvesIsMutable();
        ballastValves_.remove(index);
        onChanged();
      } else {
        ballastValvesBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder
        getBallastValvesBuilder(int index) {
      return getBallastValvesFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder
        getBallastValvesOrBuilder(int index) {
      if (ballastValvesBuilder_ == null) {
        return ballastValves_.get(index);
      } else {
        return ballastValvesBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public java.util.List<
            ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>
        getBallastValvesOrBuilderList() {
      if (ballastValvesBuilder_ != null) {
        return ballastValvesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(ballastValves_);
      }
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder
        addBallastValvesBuilder() {
      return getBallastValvesFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.getDefaultInstance());
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder
        addBallastValvesBuilder(int index) {
      return getBallastValvesFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.getDefaultInstance());
    }
    /** <code>repeated .Valve ballastValves = 9;</code> */
    public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder>
        getBallastValvesBuilderList() {
      return getBallastValvesFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>
        getBallastValvesFieldBuilder() {
      if (ballastValvesBuilder_ == null) {
        ballastValvesBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve.Builder,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.ValveOrBuilder>(
                ballastValves_,
                ((bitField0_ & 0x00000002) != 0),
                getParentForChildren(),
                isClean());
        ballastValves_ = null;
      }
      return ballastValvesBuilder_;
    }

    private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate>
        loadingRates_ = java.util.Collections.emptyList();

    private void ensureLoadingRatesIsMutable() {
      if (!((bitField0_ & 0x00000004) != 0)) {
        loadingRates_ =
            new java.util.ArrayList<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate>(
                loadingRates_);
        bitField0_ |= 0x00000004;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRateOrBuilder>
        loadingRatesBuilder_;

    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate>
        getLoadingRatesList() {
      if (loadingRatesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(loadingRates_);
      } else {
        return loadingRatesBuilder_.getMessageList();
      }
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public int getLoadingRatesCount() {
      if (loadingRatesBuilder_ == null) {
        return loadingRates_.size();
      } else {
        return loadingRatesBuilder_.getCount();
      }
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate getLoadingRates(
        int index) {
      if (loadingRatesBuilder_ == null) {
        return loadingRates_.get(index);
      } else {
        return loadingRatesBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public Builder setLoadingRates(
        int index, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate value) {
      if (loadingRatesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLoadingRatesIsMutable();
        loadingRates_.set(index, value);
        onChanged();
      } else {
        loadingRatesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public Builder setLoadingRates(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate.Builder
            builderForValue) {
      if (loadingRatesBuilder_ == null) {
        ensureLoadingRatesIsMutable();
        loadingRates_.set(index, builderForValue.build());
        onChanged();
      } else {
        loadingRatesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public Builder addLoadingRates(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate value) {
      if (loadingRatesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLoadingRatesIsMutable();
        loadingRates_.add(value);
        onChanged();
      } else {
        loadingRatesBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public Builder addLoadingRates(
        int index, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate value) {
      if (loadingRatesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLoadingRatesIsMutable();
        loadingRates_.add(index, value);
        onChanged();
      } else {
        loadingRatesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public Builder addLoadingRates(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate.Builder
            builderForValue) {
      if (loadingRatesBuilder_ == null) {
        ensureLoadingRatesIsMutable();
        loadingRates_.add(builderForValue.build());
        onChanged();
      } else {
        loadingRatesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public Builder addLoadingRates(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate.Builder
            builderForValue) {
      if (loadingRatesBuilder_ == null) {
        ensureLoadingRatesIsMutable();
        loadingRates_.add(index, builderForValue.build());
        onChanged();
      } else {
        loadingRatesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public Builder addAllLoadingRates(
        java.lang.Iterable<
                ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate>
            values) {
      if (loadingRatesBuilder_ == null) {
        ensureLoadingRatesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, loadingRates_);
        onChanged();
      } else {
        loadingRatesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public Builder clearLoadingRates() {
      if (loadingRatesBuilder_ == null) {
        loadingRates_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
      } else {
        loadingRatesBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public Builder removeLoadingRates(int index) {
      if (loadingRatesBuilder_ == null) {
        ensureLoadingRatesIsMutable();
        loadingRates_.remove(index);
        onChanged();
      } else {
        loadingRatesBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate.Builder
        getLoadingRatesBuilder(int index) {
      return getLoadingRatesFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRateOrBuilder
        getLoadingRatesOrBuilder(int index) {
      if (loadingRatesBuilder_ == null) {
        return loadingRates_.get(index);
      } else {
        return loadingRatesBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public java.util.List<
            ? extends
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRateOrBuilder>
        getLoadingRatesOrBuilderList() {
      if (loadingRatesBuilder_ != null) {
        return loadingRatesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(loadingRates_);
      }
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate.Builder
        addLoadingRatesBuilder() {
      return getLoadingRatesFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate
                  .getDefaultInstance());
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate.Builder
        addLoadingRatesBuilder(int index) {
      return getLoadingRatesFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate
                  .getDefaultInstance());
    }
    /** <code>repeated .LoadingRate loadingRates = 10;</code> */
    public java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate.Builder>
        getLoadingRatesBuilderList() {
      return getLoadingRatesFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRateOrBuilder>
        getLoadingRatesFieldBuilder() {
      if (loadingRatesBuilder_ == null) {
        loadingRatesBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate.Builder,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRateOrBuilder>(
                loadingRates_, ((bitField0_ & 0x00000004) != 0), getParentForChildren(), isClean());
        loadingRates_ = null;
      }
      return loadingRatesBuilder_;
    }

    private java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate>
        deBallastingRates_ = java.util.Collections.emptyList();

    private void ensureDeBallastingRatesIsMutable() {
      if (!((bitField0_ & 0x00000008) != 0)) {
        deBallastingRates_ =
            new java.util.ArrayList<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate>(
                deBallastingRates_);
        bitField0_ |= 0x00000008;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRateOrBuilder>
        deBallastingRatesBuilder_;

    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate>
        getDeBallastingRatesList() {
      if (deBallastingRatesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(deBallastingRates_);
      } else {
        return deBallastingRatesBuilder_.getMessageList();
      }
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public int getDeBallastingRatesCount() {
      if (deBallastingRatesBuilder_ == null) {
        return deBallastingRates_.size();
      } else {
        return deBallastingRatesBuilder_.getCount();
      }
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate
        getDeBallastingRates(int index) {
      if (deBallastingRatesBuilder_ == null) {
        return deBallastingRates_.get(index);
      } else {
        return deBallastingRatesBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public Builder setDeBallastingRates(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate value) {
      if (deBallastingRatesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDeBallastingRatesIsMutable();
        deBallastingRates_.set(index, value);
        onChanged();
      } else {
        deBallastingRatesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public Builder setDeBallastingRates(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate.Builder
            builderForValue) {
      if (deBallastingRatesBuilder_ == null) {
        ensureDeBallastingRatesIsMutable();
        deBallastingRates_.set(index, builderForValue.build());
        onChanged();
      } else {
        deBallastingRatesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public Builder addDeBallastingRates(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate value) {
      if (deBallastingRatesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDeBallastingRatesIsMutable();
        deBallastingRates_.add(value);
        onChanged();
      } else {
        deBallastingRatesBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public Builder addDeBallastingRates(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate value) {
      if (deBallastingRatesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDeBallastingRatesIsMutable();
        deBallastingRates_.add(index, value);
        onChanged();
      } else {
        deBallastingRatesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public Builder addDeBallastingRates(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate.Builder
            builderForValue) {
      if (deBallastingRatesBuilder_ == null) {
        ensureDeBallastingRatesIsMutable();
        deBallastingRates_.add(builderForValue.build());
        onChanged();
      } else {
        deBallastingRatesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public Builder addDeBallastingRates(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate.Builder
            builderForValue) {
      if (deBallastingRatesBuilder_ == null) {
        ensureDeBallastingRatesIsMutable();
        deBallastingRates_.add(index, builderForValue.build());
        onChanged();
      } else {
        deBallastingRatesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public Builder addAllDeBallastingRates(
        java.lang.Iterable<
                ? extends
                    com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate>
            values) {
      if (deBallastingRatesBuilder_ == null) {
        ensureDeBallastingRatesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, deBallastingRates_);
        onChanged();
      } else {
        deBallastingRatesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public Builder clearDeBallastingRates() {
      if (deBallastingRatesBuilder_ == null) {
        deBallastingRates_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000008);
        onChanged();
      } else {
        deBallastingRatesBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public Builder removeDeBallastingRates(int index) {
      if (deBallastingRatesBuilder_ == null) {
        ensureDeBallastingRatesIsMutable();
        deBallastingRates_.remove(index);
        onChanged();
      } else {
        deBallastingRatesBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate.Builder
        getDeBallastingRatesBuilder(int index) {
      return getDeBallastingRatesFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRateOrBuilder
        getDeBallastingRatesOrBuilder(int index) {
      if (deBallastingRatesBuilder_ == null) {
        return deBallastingRates_.get(index);
      } else {
        return deBallastingRatesBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public java.util.List<
            ? extends
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRateOrBuilder>
        getDeBallastingRatesOrBuilderList() {
      if (deBallastingRatesBuilder_ != null) {
        return deBallastingRatesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(deBallastingRates_);
      }
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate.Builder
        addDeBallastingRatesBuilder() {
      return getDeBallastingRatesFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate
                  .getDefaultInstance());
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate.Builder
        addDeBallastingRatesBuilder(int index) {
      return getDeBallastingRatesFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate
                  .getDefaultInstance());
    }
    /** <code>repeated .DeBallastingRate deBallastingRates = 11;</code> */
    public java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate.Builder>
        getDeBallastingRatesBuilderList() {
      return getDeBallastingRatesFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRateOrBuilder>
        getDeBallastingRatesFieldBuilder() {
      if (deBallastingRatesBuilder_ == null) {
        deBallastingRatesBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate.Builder,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .DeBallastingRateOrBuilder>(
                deBallastingRates_,
                ((bitField0_ & 0x00000008) != 0),
                getParentForChildren(),
                isClean());
        deBallastingRates_ = null;
      }
      return deBallastingRatesBuilder_;
    }

    private java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails>
        loadingPlanPortWiseDetails_ = java.util.Collections.emptyList();

    private void ensureLoadingPlanPortWiseDetailsIsMutable() {
      if (!((bitField0_ & 0x00000010) != 0)) {
        loadingPlanPortWiseDetails_ =
            new java.util.ArrayList<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingPlanPortWiseDetails>(loadingPlanPortWiseDetails_);
        bitField0_ |= 0x00000010;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
                .Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingPlanPortWiseDetailsOrBuilder>
        loadingPlanPortWiseDetailsBuilder_;

    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails>
        getLoadingPlanPortWiseDetailsList() {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(loadingPlanPortWiseDetails_);
      } else {
        return loadingPlanPortWiseDetailsBuilder_.getMessageList();
      }
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public int getLoadingPlanPortWiseDetailsCount() {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        return loadingPlanPortWiseDetails_.size();
      } else {
        return loadingPlanPortWiseDetailsBuilder_.getCount();
      }
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
        getLoadingPlanPortWiseDetails(int index) {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        return loadingPlanPortWiseDetails_.get(index);
      } else {
        return loadingPlanPortWiseDetailsBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public Builder setLoadingPlanPortWiseDetails(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
            value) {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLoadingPlanPortWiseDetailsIsMutable();
        loadingPlanPortWiseDetails_.set(index, value);
        onChanged();
      } else {
        loadingPlanPortWiseDetailsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public Builder setLoadingPlanPortWiseDetails(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
            builderForValue) {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        ensureLoadingPlanPortWiseDetailsIsMutable();
        loadingPlanPortWiseDetails_.set(index, builderForValue.build());
        onChanged();
      } else {
        loadingPlanPortWiseDetailsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public Builder addLoadingPlanPortWiseDetails(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
            value) {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLoadingPlanPortWiseDetailsIsMutable();
        loadingPlanPortWiseDetails_.add(value);
        onChanged();
      } else {
        loadingPlanPortWiseDetailsBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public Builder addLoadingPlanPortWiseDetails(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
            value) {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLoadingPlanPortWiseDetailsIsMutable();
        loadingPlanPortWiseDetails_.add(index, value);
        onChanged();
      } else {
        loadingPlanPortWiseDetailsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public Builder addLoadingPlanPortWiseDetails(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
            builderForValue) {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        ensureLoadingPlanPortWiseDetailsIsMutable();
        loadingPlanPortWiseDetails_.add(builderForValue.build());
        onChanged();
      } else {
        loadingPlanPortWiseDetailsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public Builder addLoadingPlanPortWiseDetails(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails.Builder
            builderForValue) {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        ensureLoadingPlanPortWiseDetailsIsMutable();
        loadingPlanPortWiseDetails_.add(index, builderForValue.build());
        onChanged();
      } else {
        loadingPlanPortWiseDetailsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public Builder addAllLoadingPlanPortWiseDetails(
        java.lang.Iterable<
                ? extends
                    com.cpdss.common.generated.loading_plan.LoadingPlanModels
                        .LoadingPlanPortWiseDetails>
            values) {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        ensureLoadingPlanPortWiseDetailsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, loadingPlanPortWiseDetails_);
        onChanged();
      } else {
        loadingPlanPortWiseDetailsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public Builder clearLoadingPlanPortWiseDetails() {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        loadingPlanPortWiseDetails_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000010);
        onChanged();
      } else {
        loadingPlanPortWiseDetailsBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public Builder removeLoadingPlanPortWiseDetails(int index) {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        ensureLoadingPlanPortWiseDetailsIsMutable();
        loadingPlanPortWiseDetails_.remove(index);
        onChanged();
      } else {
        loadingPlanPortWiseDetailsBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
            .Builder
        getLoadingPlanPortWiseDetailsBuilder(int index) {
      return getLoadingPlanPortWiseDetailsFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingPlanPortWiseDetailsOrBuilder
        getLoadingPlanPortWiseDetailsOrBuilder(int index) {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        return loadingPlanPortWiseDetails_.get(index);
      } else {
        return loadingPlanPortWiseDetailsBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public java.util.List<
            ? extends
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingPlanPortWiseDetailsOrBuilder>
        getLoadingPlanPortWiseDetailsOrBuilderList() {
      if (loadingPlanPortWiseDetailsBuilder_ != null) {
        return loadingPlanPortWiseDetailsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(loadingPlanPortWiseDetails_);
      }
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
            .Builder
        addLoadingPlanPortWiseDetailsBuilder() {
      return getLoadingPlanPortWiseDetailsFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
                  .getDefaultInstance());
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
            .Builder
        addLoadingPlanPortWiseDetailsBuilder(int index) {
      return getLoadingPlanPortWiseDetailsFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
                  .getDefaultInstance());
    }
    /** <code>repeated .LoadingPlanPortWiseDetails loadingPlanPortWiseDetails = 12;</code> */
    public java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
                .Builder>
        getLoadingPlanPortWiseDetailsBuilderList() {
      return getLoadingPlanPortWiseDetailsFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
                .Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingPlanPortWiseDetailsOrBuilder>
        getLoadingPlanPortWiseDetailsFieldBuilder() {
      if (loadingPlanPortWiseDetailsBuilder_ == null) {
        loadingPlanPortWiseDetailsBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingPlanPortWiseDetails,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails
                    .Builder,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingPlanPortWiseDetailsOrBuilder>(
                loadingPlanPortWiseDetails_,
                ((bitField0_ & 0x00000010) != 0),
                getParentForChildren(),
                isClean());
        loadingPlanPortWiseDetails_ = null;
      }
      return loadingPlanPortWiseDetailsBuilder_;
    }

    private java.lang.Object cargoLoadingRate1_ = "";
    /**
     * <code>string cargoLoadingRate1 = 13;</code>
     *
     * @return The cargoLoadingRate1.
     */
    public java.lang.String getCargoLoadingRate1() {
      java.lang.Object ref = cargoLoadingRate1_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargoLoadingRate1_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string cargoLoadingRate1 = 13;</code>
     *
     * @return The bytes for cargoLoadingRate1.
     */
    public com.google.protobuf.ByteString getCargoLoadingRate1Bytes() {
      java.lang.Object ref = cargoLoadingRate1_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargoLoadingRate1_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string cargoLoadingRate1 = 13;</code>
     *
     * @param value The cargoLoadingRate1 to set.
     * @return This builder for chaining.
     */
    public Builder setCargoLoadingRate1(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      cargoLoadingRate1_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string cargoLoadingRate1 = 13;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCargoLoadingRate1() {

      cargoLoadingRate1_ = getDefaultInstance().getCargoLoadingRate1();
      onChanged();
      return this;
    }
    /**
     * <code>string cargoLoadingRate1 = 13;</code>
     *
     * @param value The bytes for cargoLoadingRate1 to set.
     * @return This builder for chaining.
     */
    public Builder setCargoLoadingRate1Bytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      cargoLoadingRate1_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object cargoLoadingRate2_ = "";
    /**
     * <code>string cargoLoadingRate2 = 14;</code>
     *
     * @return The cargoLoadingRate2.
     */
    public java.lang.String getCargoLoadingRate2() {
      java.lang.Object ref = cargoLoadingRate2_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cargoLoadingRate2_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string cargoLoadingRate2 = 14;</code>
     *
     * @return The bytes for cargoLoadingRate2.
     */
    public com.google.protobuf.ByteString getCargoLoadingRate2Bytes() {
      java.lang.Object ref = cargoLoadingRate2_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        cargoLoadingRate2_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string cargoLoadingRate2 = 14;</code>
     *
     * @param value The cargoLoadingRate2 to set.
     * @return This builder for chaining.
     */
    public Builder setCargoLoadingRate2(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      cargoLoadingRate2_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string cargoLoadingRate2 = 14;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCargoLoadingRate2() {

      cargoLoadingRate2_ = getDefaultInstance().getCargoLoadingRate2();
      onChanged();
      return this;
    }
    /**
     * <code>string cargoLoadingRate2 = 14;</code>
     *
     * @param value The bytes for cargoLoadingRate2 to set.
     * @return This builder for chaining.
     */
    public Builder setCargoLoadingRate2Bytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      cargoLoadingRate2_ = value;
      onChanged();
      return this;
    }

    private java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation>
        ballastOperations_ = java.util.Collections.emptyList();

    private void ensureBallastOperationsIsMutable() {
      if (!((bitField0_ & 0x00000020) != 0)) {
        ballastOperations_ =
            new java.util.ArrayList<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation>(
                ballastOperations_);
        bitField0_ |= 0x00000020;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperationOrBuilder>
        ballastOperationsBuilder_;

    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public java.util.List<com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation>
        getBallastOperationsList() {
      if (ballastOperationsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(ballastOperations_);
      } else {
        return ballastOperationsBuilder_.getMessageList();
      }
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public int getBallastOperationsCount() {
      if (ballastOperationsBuilder_ == null) {
        return ballastOperations_.size();
      } else {
        return ballastOperationsBuilder_.getCount();
      }
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation
        getBallastOperations(int index) {
      if (ballastOperationsBuilder_ == null) {
        return ballastOperations_.get(index);
      } else {
        return ballastOperationsBuilder_.getMessage(index);
      }
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public Builder setBallastOperations(
        int index, com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation value) {
      if (ballastOperationsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBallastOperationsIsMutable();
        ballastOperations_.set(index, value);
        onChanged();
      } else {
        ballastOperationsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public Builder setBallastOperations(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation.Builder
            builderForValue) {
      if (ballastOperationsBuilder_ == null) {
        ensureBallastOperationsIsMutable();
        ballastOperations_.set(index, builderForValue.build());
        onChanged();
      } else {
        ballastOperationsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public Builder addBallastOperations(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation value) {
      if (ballastOperationsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBallastOperationsIsMutable();
        ballastOperations_.add(value);
        onChanged();
      } else {
        ballastOperationsBuilder_.addMessage(value);
      }
      return this;
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public Builder addBallastOperations(
        int index, com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation value) {
      if (ballastOperationsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBallastOperationsIsMutable();
        ballastOperations_.add(index, value);
        onChanged();
      } else {
        ballastOperationsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public Builder addBallastOperations(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation.Builder
            builderForValue) {
      if (ballastOperationsBuilder_ == null) {
        ensureBallastOperationsIsMutable();
        ballastOperations_.add(builderForValue.build());
        onChanged();
      } else {
        ballastOperationsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public Builder addBallastOperations(
        int index,
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation.Builder
            builderForValue) {
      if (ballastOperationsBuilder_ == null) {
        ensureBallastOperationsIsMutable();
        ballastOperations_.add(index, builderForValue.build());
        onChanged();
      } else {
        ballastOperationsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public Builder addAllBallastOperations(
        java.lang.Iterable<
                ? extends com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation>
            values) {
      if (ballastOperationsBuilder_ == null) {
        ensureBallastOperationsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, ballastOperations_);
        onChanged();
      } else {
        ballastOperationsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public Builder clearBallastOperations() {
      if (ballastOperationsBuilder_ == null) {
        ballastOperations_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000020);
        onChanged();
      } else {
        ballastOperationsBuilder_.clear();
      }
      return this;
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public Builder removeBallastOperations(int index) {
      if (ballastOperationsBuilder_ == null) {
        ensureBallastOperationsIsMutable();
        ballastOperations_.remove(index);
        onChanged();
      } else {
        ballastOperationsBuilder_.remove(index);
      }
      return this;
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation.Builder
        getBallastOperationsBuilder(int index) {
      return getBallastOperationsFieldBuilder().getBuilder(index);
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperationOrBuilder
        getBallastOperationsOrBuilder(int index) {
      if (ballastOperationsBuilder_ == null) {
        return ballastOperations_.get(index);
      } else {
        return ballastOperationsBuilder_.getMessageOrBuilder(index);
      }
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public java.util.List<
            ? extends
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperationOrBuilder>
        getBallastOperationsOrBuilderList() {
      if (ballastOperationsBuilder_ != null) {
        return ballastOperationsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(ballastOperations_);
      }
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation.Builder
        addBallastOperationsBuilder() {
      return getBallastOperationsFieldBuilder()
          .addBuilder(
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation
                  .getDefaultInstance());
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation.Builder
        addBallastOperationsBuilder(int index) {
      return getBallastOperationsFieldBuilder()
          .addBuilder(
              index,
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation
                  .getDefaultInstance());
    }
    /** <code>repeated .PumpOperation ballastOperations = 15;</code> */
    public java.util.List<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation.Builder>
        getBallastOperationsBuilderList() {
      return getBallastOperationsFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation.Builder,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperationOrBuilder>
        getBallastOperationsFieldBuilder() {
      if (ballastOperationsBuilder_ == null) {
        ballastOperationsBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation.Builder,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperationOrBuilder>(
                ballastOperations_,
                ((bitField0_ & 0x00000020) != 0),
                getParentForChildren(),
                isClean());
        ballastOperations_ = null;
      }
      return ballastOperationsBuilder_;
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

    // @@protoc_insertion_point(builder_scope:DischargingSequence)
  }

  // @@protoc_insertion_point(class_scope:DischargingSequence)
  private static final com.cpdss.common.generated.discharge_plan.DischargingSequence
      DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.cpdss.common.generated.discharge_plan.DischargingSequence();
  }

  public static com.cpdss.common.generated.discharge_plan.DischargingSequence getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DischargingSequence> PARSER =
      new com.google.protobuf.AbstractParser<DischargingSequence>() {
        @java.lang.Override
        public DischargingSequence parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          return new DischargingSequence(input, extensionRegistry);
        }
      };

  public static com.google.protobuf.Parser<DischargingSequence> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DischargingSequence> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.cpdss.common.generated.discharge_plan.DischargingSequence getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
