/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public interface DischargeBerthsOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:DischargeBerths)
    com.google.protobuf.MessageOrBuilder {

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
  long getId();

  /**
   * <code>int64 dischargeInfoId = 2;</code>
   *
   * @return The dischargeInfoId.
   */
  long getDischargeInfoId();

  /**
   * <code>int64 berthId = 3;</code>
   *
   * @return The berthId.
   */
  long getBerthId();

  /**
   * <code>string depth = 4;</code>
   *
   * @return The depth.
   */
  java.lang.String getDepth();
  /**
   * <code>string depth = 4;</code>
   *
   * @return The bytes for depth.
   */
  com.google.protobuf.ByteString getDepthBytes();

  /**
   * <code>string maxManifoldHeight = 5;</code>
   *
   * @return The maxManifoldHeight.
   */
  java.lang.String getMaxManifoldHeight();
  /**
   * <code>string maxManifoldHeight = 5;</code>
   *
   * @return The bytes for maxManifoldHeight.
   */
  com.google.protobuf.ByteString getMaxManifoldHeightBytes();

  /**
   * <code>string maxManifoldPressure = 6;</code>
   *
   * @return The maxManifoldPressure.
   */
  java.lang.String getMaxManifoldPressure();
  /**
   * <code>string maxManifoldPressure = 6;</code>
   *
   * @return The bytes for maxManifoldPressure.
   */
  com.google.protobuf.ByteString getMaxManifoldPressureBytes();

  /**
   * <code>string hoseConnections = 7;</code>
   *
   * @return The hoseConnections.
   */
  java.lang.String getHoseConnections();
  /**
   * <code>string hoseConnections = 7;</code>
   *
   * @return The bytes for hoseConnections.
   */
  com.google.protobuf.ByteString getHoseConnectionsBytes();

  /**
   * <code>string seaDraftLimitation = 8;</code>
   *
   * @return The seaDraftLimitation.
   */
  java.lang.String getSeaDraftLimitation();
  /**
   * <code>string seaDraftLimitation = 8;</code>
   *
   * @return The bytes for seaDraftLimitation.
   */
  com.google.protobuf.ByteString getSeaDraftLimitationBytes();

  /**
   * <code>string airDraftLimitation = 9;</code>
   *
   * @return The airDraftLimitation.
   */
  java.lang.String getAirDraftLimitation();
  /**
   * <code>string airDraftLimitation = 9;</code>
   *
   * @return The bytes for airDraftLimitation.
   */
  com.google.protobuf.ByteString getAirDraftLimitationBytes();

  /**
   * <code>bool airPurge = 10;</code>
   *
   * @return The airPurge.
   */
  boolean getAirPurge();

  /**
   * <code>bool cargoCirculation = 11;</code>
   *
   * @return The cargoCirculation.
   */
  boolean getCargoCirculation();

  /**
   * <code>string lineDisplacement = 12;</code>
   *
   * @return The lineDisplacement.
   */
  java.lang.String getLineDisplacement();
  /**
   * <code>string lineDisplacement = 12;</code>
   *
   * @return The bytes for lineDisplacement.
   */
  com.google.protobuf.ByteString getLineDisplacementBytes();

  /**
   * <code>string specialRegulationRestriction = 13;</code>
   *
   * @return The specialRegulationRestriction.
   */
  java.lang.String getSpecialRegulationRestriction();
  /**
   * <code>string specialRegulationRestriction = 13;</code>
   *
   * @return The bytes for specialRegulationRestriction.
   */
  com.google.protobuf.ByteString getSpecialRegulationRestrictionBytes();

  /**
   * <code>string itemsToBeAgreedWith = 14;</code>
   *
   * @return The itemsToBeAgreedWith.
   */
  java.lang.String getItemsToBeAgreedWith();
  /**
   * <code>string itemsToBeAgreedWith = 14;</code>
   *
   * @return The bytes for itemsToBeAgreedWith.
   */
  com.google.protobuf.ByteString getItemsToBeAgreedWithBytes();

  /**
   * <code>string displacement = 15;</code>
   *
   * @return The displacement.
   */
  java.lang.String getDisplacement();
  /**
   * <code>string displacement = 15;</code>
   *
   * @return The bytes for displacement.
   */
  com.google.protobuf.ByteString getDisplacementBytes();
}
