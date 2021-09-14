/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public final class DischargePlanServiceOuterClass {
  private DischargePlanServiceOuterClass() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n+discharge_plan/discharge_plan_service."
          + "proto\032\014common.proto\032*discharge_plan/disc"
          + "harge_plan_models.proto2\372\003\n\033DischargeInf"
          + "ormationService\022P\n\027getDischargeInformati"
          + "on\022\034.DischargeInformationRequest\032\025.Disch"
          + "argeInformation\"\000\022L\n\034getOrSaveRulesForDi"
          + "scharging\022\025.DischargeRuleRequest\032\023.Disch"
          + "argeRuleReply\"\000\022r\n DischargingUploadPort"
          + "TideDetails\022#.DischargingUploadTideDetai"
          + "lRequest\032\'.DischargingUploadTideDetailSt"
          + "atusReply\"\000\022z\n\"DischargingDownloadPortTi"
          + "deDetails\022%.DischargingDownloadTideDetai"
          + "lRequest\032).DischargingDownloadTideDetail"
          + "StatusReply\"\0000\001\022K\n\022GetDischargingPlan\022\034."
          + "DischargeInformationRequest\032\025.Dischargin"
          + "gPlanReply\"\0002\301\001\n\024DischargePlanService\022U\n"
          + "\034DischargePlanSynchronization\022\".Discharg"
          + "eStudyDataTransferRequest\032\017.ResponseStat"
          + "us\"\000\022R\n\025generateDischargePlan\022\034.Discharg"
          + "eInformationRequest\032\031.DischargePlanAlgoR"
          + "equest\"\0002\272\003\n\035DischargingInstructionServi"
          + "ce\022^\n\032GetDischargingInstructions\022\036.Disch"
          + "argingInstructionRequest\032\036.DischargingIn"
          + "structionDetails\"\000\022L\n\031AddDischargingInst"
          + "ruction\022\034.DischargingInstructionsSave\032\017."
          + "ResponseStatus\"\000\022R\n\035UpdateDischargingIns"
          + "tructions\022\036.DischargingInstructionsUpdat"
          + "e\032\017.ResponseStatus\"\000\022Q\n\035DeleteDischargin"
          + "gInstructions\022\035.DischargingInstructionSt"
          + "atus\032\017.ResponseStatus\"\000\022D\n\020EditInstructi"
          + "ons\022\035.DischargingInstructionStatus\032\017.Res"
          + "ponseStatus\"\000B-\n)com.cpdss.common.genera"
          + "ted.discharge_planP\001b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
              com.cpdss.common.generated.discharge_plan.DischargePlanModels.getDescriptor(),
            });
    com.cpdss.common.generated.Common.getDescriptor();
    com.cpdss.common.generated.discharge_plan.DischargePlanModels.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
