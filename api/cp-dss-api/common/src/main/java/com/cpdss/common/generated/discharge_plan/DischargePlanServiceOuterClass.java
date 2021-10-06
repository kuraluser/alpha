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
          + "harge_plan_models.proto\032&loading_plan/lo"
          + "ading_plan_models.proto2\372\003\n\033DischargeInf"
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
          + "gPlanReply\"\0002\275\003\n\024DischargePlanService\022U\n"
          + "\034DischargePlanSynchronization\022\".Discharg"
          + "eStudyDataTransferRequest\032\017.ResponseStat"
          + "us\"\000\022^\n\037GetDischargeUpdateUllageDetails\022"
          + "\033.UpdateUllageDetailsRequest\032\034.UpdateUll"
          + "ageDetailsResponse\"\000\022R\n\025generateDischarg"
          + "ePlan\022\034.DischargeInformationRequest\032\031.Di"
          + "schargePlanAlgoRequest\"\000\022F\n\034updateDischa"
          + "rgeUllageDetails\022\022.UllageBillRequest\032\020.U"
          + "llageBillReply\"\000\022R\n\023saveDischargingPlan\022"
          + "\033.DischargingPlanSaveRequest\032\034.Dischargi"
          + "ngPlanSaveResponse\"\0002\272\003\n\035DischargingInst"
          + "ructionService\022^\n\032GetDischargingInstruct"
          + "ions\022\036.DischargingInstructionRequest\032\036.D"
          + "ischargingInstructionDetails\"\000\022L\n\031AddDis"
          + "chargingInstruction\022\034.DischargingInstruc"
          + "tionsSave\032\017.ResponseStatus\"\000\022R\n\035UpdateDi"
          + "schargingInstructions\022\036.DischargingInstr"
          + "uctionsUpdate\032\017.ResponseStatus\"\000\022Q\n\035Dele"
          + "teDischargingInstructions\022\035.DischargingI"
          + "nstructionStatus\032\017.ResponseStatus\"\000\022D\n\020E"
          + "ditInstructions\022\035.DischargingInstruction"
          + "Status\032\017.ResponseStatus\"\000B-\n)com.cpdss.c"
          + "ommon.generated.discharge_planP\001b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
              com.cpdss.common.generated.discharge_plan.DischargePlanModels.getDescriptor(),
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor(),
            });
    com.cpdss.common.generated.Common.getDescriptor();
    com.cpdss.common.generated.discharge_plan.DischargePlanModels.getDescriptor();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
