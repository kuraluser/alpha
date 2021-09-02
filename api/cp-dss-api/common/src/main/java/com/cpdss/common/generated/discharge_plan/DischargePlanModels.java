/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public final class DischargePlanModels {
  private DischargePlanModels() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeInformationRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeInformationRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeInformation_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeInformation_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeDetails_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeDetails_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor internal_static_DischargeRates_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeRates_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeBerths_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeBerths_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeMachinesInUse_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeMachinesInUse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor internal_static_DischargeDelay_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeDelay_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeDelays_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeDelays_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_PostDischargeStageTime_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_PostDischargeStageTime_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor internal_static_CowPlan_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CowPlan_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor internal_static_CowTankDetails_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CowTankDetails_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor internal_static_CargoForCow_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CargoForCow_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeRuleRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeRuleRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeRuleReply_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeRuleReply_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeStudyDataTransferRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeStudyDataTransferRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor internal_static_PortData_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_PortData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n*discharge_plan/discharge_plan_models.p"
          + "roto\032\014common.proto\032&loading_plan/loading"
          + "_plan_models.proto\"\272\001\n\033DischargeInformat"
          + "ionRequest\022\021\n\tcompanyId\030\001 \001(\003\022\020\n\010vesselI"
          + "d\030\002 \001(\003\022\020\n\010voyageId\030\003 \001(\003\022\027\n\017dischargeIn"
          + "foId\030\004 \001(\003\022\032\n\022dischargePatternId\030\005 \001(\003\022\026"
          + "\n\016portRotationId\030\006 \001(\003\022\027\n\017synopticTableI"
          + "d\030\007 \001(\003\"\300\003\n\024DischargeInformation\022\027\n\017disc"
          + "hargeInfoId\030\010 \001(\003\022\027\n\017synopticTableId\030\t \001"
          + "(\003\022\'\n\016responseStatus\030\001 \001(\0132\017.ResponseSta"
          + "tus\022+\n\020dischargeDetails\030\002 \001(\0132\021.Discharg"
          + "eDetails\022&\n\rdischargeRate\030\003 \001(\0132\017.Discha"
          + "rgeRates\022&\n\014berthDetails\030\004 \003(\0132\020.Dischar"
          + "geBerths\022+\n\014machineInUse\030\n \003(\0132\025.Loading"
          + "MachinesInUse\022&\n\016dischargeStage\030\005 \001(\0132\016."
          + "LoadingStages\022\'\n\016dischargeDelay\030\006 \001(\0132\017."
          + "DischargeDelay\022\031\n\007cowPlan\030\013 \001(\0132\010.CowPla"
          + "n\0227\n\026postDischargeStageTime\030\007 \001(\0132\027.Post"
          + "DischargeStageTime\"\223\001\n\020DischargeDetails\022"
          + "\n\n\002id\030\001 \001(\003\022\025\n\rtimeOfSunrise\030\002 \001(\t\022\024\n\014ti"
          + "meOfSunset\030\003 \001(\t\022\021\n\tstartTime\030\004 \001(\t\022!\n\013t"
          + "rimAllowed\030\005 \001(\0132\014.TrimAllowed\022\020\n\010voyage"
          + "Id\030\006 \001(\003\"x\n\016DischargeRates\022\034\n\024initialDis"
          + "chargeRate\030\001 \001(\t\022\030\n\020maxDischargeRate\030\002 \001"
          + "(\t\022\026\n\016minBallastRate\030\003 \001(\t\022\026\n\016maxBallast"
          + "Rate\030\004 \001(\t\"\350\002\n\017DischargeBerths\022\n\n\002id\030\001 \001"
          + "(\003\022\027\n\017dischargeInfoId\030\002 \001(\003\022\017\n\007berthId\030\003"
          + " \001(\003\022\r\n\005depth\030\004 \001(\t\022\031\n\021maxManifoldHeight"
          + "\030\005 \001(\t\022\033\n\023maxManifoldPressure\030\006 \001(\t\022\027\n\017h"
          + "oseConnections\030\007 \001(\t\022\032\n\022seaDraftLimitati"
          + "on\030\010 \001(\t\022\032\n\022airDraftLimitation\030\t \001(\t\022\020\n\010"
          + "airPurge\030\n \001(\010\022\030\n\020cargoCirculation\030\013 \001(\010"
          + "\022\030\n\020lineDisplacement\030\014 \001(\t\022$\n\034specialReg"
          + "ulationRestriction\030\r \001(\t\022\033\n\023itemsToBeAgr"
          + "eedWith\030\016 \001(\t\"\226\001\n\026DischargeMachinesInUse"
          + "\022\n\n\002id\030\001 \001(\003\022\027\n\017dischargeInfoId\030\002 \001(\003\022\021\n"
          + "\tmachineId\030\003 \001(\003\022\020\n\010capacity\030\004 \001(\t\022\017\n\007is"
          + "Using\030\005 \001(\010\022!\n\013machineType\030\006 \001(\0162\014.Machi"
          + "neType\"R\n\016DischargeDelay\022\036\n\007reasons\030\001 \003("
          + "\0132\r.DelayReasons\022 \n\006delays\030\002 \003(\0132\020.Disch"
          + "argeDelays\"\241\001\n\017DischargeDelays\022\n\n\002id\030\001 \001"
          + "(\003\022\027\n\017dischargeInfoId\030\002 \001(\003\022\031\n\021reasonFor"
          + "DelayIds\030\003 \003(\003\022\020\n\010duration\030\004 \001(\t\022\017\n\007carg"
          + "oId\030\005 \001(\003\022\020\n\010quantity\030\006 \001(\t\022\031\n\021cargoNomi"
          + "nationId\030\007 \001(\003\"{\n\026PostDischargeStageTime"
          + "\022\027\n\017timeForDryCheck\030\001 \001(\t\022\027\n\017slopDischar"
          + "ging\030\002 \001(\t\022\026\n\016finalStripping\030\003 \001(\t\022\027\n\017fr"
          + "eshOilWashing\030\004 \001(\t\"\225\002\n\007CowPlan\022\'\n\rcowOp"
          + "tionType\030\001 \001(\0162\020.COW_OPTION_TYPE\022\026\n\016cowT"
          + "ankPercent\030\002 \001(\t\022\024\n\014cowStartTime\030\003 \001(\t\022\022"
          + "\n\ncowEndTime\030\004 \001(\t\022\026\n\016estCowDuration\030\005 \001"
          + "(\t\022\022\n\ntrimCowMin\030\006 \001(\t\022\022\n\ntrimCowMax\030\007 \001"
          + "(\t\022\035\n\025needFreshCrudeStorage\030\010 \001(\010\022\027\n\017nee"
          + "dFlushingOil\030\t \001(\010\022\'\n\016cowTankDetails\030\n \003"
          + "(\0132\017.CowTankDetails\"`\n\016CowTankDetails\022\032\n"
          + "\007cowType\030\001 \001(\0162\t.COW_TYPE\022\017\n\007tankIds\030\002 \003"
          + "(\003\022!\n\013cargoForCow\030\003 \003(\0132\014.CargoForCow\"\204\001"
          + "\n\013CargoForCow\022\017\n\007cargoId\030\001 \001(\003\022\031\n\021cargoN"
          + "ominationId\030\002 \001(\003\022\026\n\016washingCargoId\030\003 \001("
          + "\003\022 \n\030washingCargoNominationId\030\004 \001(\003\022\017\n\007t"
          + "ankIds\030\005 \003(\003\"\213\001\n\024DischargeRuleRequest\022\020\n"
          + "\010vesselId\030\001 \001(\003\022\021\n\tsectionId\030\002 \001(\003\022\034\n\010ru"
          + "lePlan\030\003 \003(\0132\n.RulePlans\022\027\n\017dischargeInf"
          + "oId\030\004 \001(\003\022\027\n\017isNoDefaultRule\030\005 \001(\010\"[\n\022Di"
          + "schargeRuleReply\022\'\n\016responseStatus\030\001 \001(\013"
          + "2\017.ResponseStatus\022\034\n\010rulePlan\030\002 \003(\0132\n.Ru"
          + "lePlans\"\200\001\n!DischargeStudyDataTransferRe"
          + "quest\022\033\n\010portData\030\001 \003(\0132\t.PortData\022\032\n\022di"
          + "schargePatternId\030\002 \001(\003\022\020\n\010voyageId\030\003 \001(\003"
          + "\022\020\n\010vesselId\030\004 \001(\003\";\n\010PortData\022\026\n\016portRo"
          + "tationId\030\001 \001(\003\022\027\n\017synopticTableId\030\002 \001(\003B"
          + "-\n)com.cpdss.common.generated.discharge_"
          + "planP\001b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor(),
            });
    internal_static_DischargeInformationRequest_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_DischargeInformationRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeInformationRequest_descriptor,
            new java.lang.String[] {
              "CompanyId",
              "VesselId",
              "VoyageId",
              "DischargeInfoId",
              "DischargePatternId",
              "PortRotationId",
              "SynopticTableId",
            });
    internal_static_DischargeInformation_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_DischargeInformation_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeInformation_descriptor,
            new java.lang.String[] {
              "DischargeInfoId",
              "SynopticTableId",
              "ResponseStatus",
              "DischargeDetails",
              "DischargeRate",
              "BerthDetails",
              "MachineInUse",
              "DischargeStage",
              "DischargeDelay",
              "CowPlan",
              "PostDischargeStageTime",
            });
    internal_static_DischargeDetails_descriptor = getDescriptor().getMessageTypes().get(2);
    internal_static_DischargeDetails_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeDetails_descriptor,
            new java.lang.String[] {
              "Id", "TimeOfSunrise", "TimeOfSunset", "StartTime", "TrimAllowed", "VoyageId",
            });
    internal_static_DischargeRates_descriptor = getDescriptor().getMessageTypes().get(3);
    internal_static_DischargeRates_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeRates_descriptor,
            new java.lang.String[] {
              "InitialDischargeRate", "MaxDischargeRate", "MinBallastRate", "MaxBallastRate",
            });
    internal_static_DischargeBerths_descriptor = getDescriptor().getMessageTypes().get(4);
    internal_static_DischargeBerths_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeBerths_descriptor,
            new java.lang.String[] {
              "Id",
              "DischargeInfoId",
              "BerthId",
              "Depth",
              "MaxManifoldHeight",
              "MaxManifoldPressure",
              "HoseConnections",
              "SeaDraftLimitation",
              "AirDraftLimitation",
              "AirPurge",
              "CargoCirculation",
              "LineDisplacement",
              "SpecialRegulationRestriction",
              "ItemsToBeAgreedWith",
            });
    internal_static_DischargeMachinesInUse_descriptor = getDescriptor().getMessageTypes().get(5);
    internal_static_DischargeMachinesInUse_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeMachinesInUse_descriptor,
            new java.lang.String[] {
              "Id", "DischargeInfoId", "MachineId", "Capacity", "IsUsing", "MachineType",
            });
    internal_static_DischargeDelay_descriptor = getDescriptor().getMessageTypes().get(6);
    internal_static_DischargeDelay_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeDelay_descriptor,
            new java.lang.String[] {
              "Reasons", "Delays",
            });
    internal_static_DischargeDelays_descriptor = getDescriptor().getMessageTypes().get(7);
    internal_static_DischargeDelays_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeDelays_descriptor,
            new java.lang.String[] {
              "Id",
              "DischargeInfoId",
              "ReasonForDelayIds",
              "Duration",
              "CargoId",
              "Quantity",
              "CargoNominationId",
            });
    internal_static_PostDischargeStageTime_descriptor = getDescriptor().getMessageTypes().get(8);
    internal_static_PostDischargeStageTime_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_PostDischargeStageTime_descriptor,
            new java.lang.String[] {
              "TimeForDryCheck", "SlopDischarging", "FinalStripping", "FreshOilWashing",
            });
    internal_static_CowPlan_descriptor = getDescriptor().getMessageTypes().get(9);
    internal_static_CowPlan_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CowPlan_descriptor,
            new java.lang.String[] {
              "CowOptionType",
              "CowTankPercent",
              "CowStartTime",
              "CowEndTime",
              "EstCowDuration",
              "TrimCowMin",
              "TrimCowMax",
              "NeedFreshCrudeStorage",
              "NeedFlushingOil",
              "CowTankDetails",
            });
    internal_static_CowTankDetails_descriptor = getDescriptor().getMessageTypes().get(10);
    internal_static_CowTankDetails_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CowTankDetails_descriptor,
            new java.lang.String[] {
              "CowType", "TankIds", "CargoForCow",
            });
    internal_static_CargoForCow_descriptor = getDescriptor().getMessageTypes().get(11);
    internal_static_CargoForCow_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_CargoForCow_descriptor,
            new java.lang.String[] {
              "CargoId",
              "CargoNominationId",
              "WashingCargoId",
              "WashingCargoNominationId",
              "TankIds",
            });
    internal_static_DischargeRuleRequest_descriptor = getDescriptor().getMessageTypes().get(12);
    internal_static_DischargeRuleRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeRuleRequest_descriptor,
            new java.lang.String[] {
              "VesselId", "SectionId", "RulePlan", "DischargeInfoId", "IsNoDefaultRule",
            });
    internal_static_DischargeRuleReply_descriptor = getDescriptor().getMessageTypes().get(13);
    internal_static_DischargeRuleReply_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeRuleReply_descriptor,
            new java.lang.String[] {
              "ResponseStatus", "RulePlan",
            });
    internal_static_DischargeStudyDataTransferRequest_descriptor =
        getDescriptor().getMessageTypes().get(14);
    internal_static_DischargeStudyDataTransferRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeStudyDataTransferRequest_descriptor,
            new java.lang.String[] {
              "PortData", "DischargePatternId", "VoyageId", "VesselId",
            });
    internal_static_PortData_descriptor = getDescriptor().getMessageTypes().get(15);
    internal_static_PortData_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_PortData_descriptor,
            new java.lang.String[] {
              "PortRotationId", "SynopticTableId",
            });
    com.cpdss.common.generated.Common.getDescriptor();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
