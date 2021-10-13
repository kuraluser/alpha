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
          + "ading_plan_models.proto2\365\007\n\033DischargeInf"
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
          + "gPlanReply\"\000\022S\n\032SaveDischargingInformati"
          + "on\022\025.DischargeInformation\032\034.DischargingI"
          + "nfoSaveResponse\"\000\022Q\n\030SaveDischargingInfo"
          + "Rates\022\025.DischargeInformation\032\034.Dischargi"
          + "ngInfoSaveResponse\"\000\022R\n\031SaveDischargingI"
          + "nfoBerths\022\025.DischargeInformation\032\034.Disch"
          + "argingInfoSaveResponse\"\000\022U\n\034SaveDischarg"
          + "ingInfoMachinery\022\025.DischargeInformation\032"
          + "\034.DischargingInfoSaveResponse\"\000\022R\n\031SaveD"
          + "ischargingInfoDelays\022\025.DischargeInformat"
          + "ion\032\034.DischargingInfoSaveResponse\"\000\022R\n\031S"
          + "aveDischargingInfoStages\022\025.DischargeInfo"
          + "rmation\032\034.DischargingInfoSaveResponse\"\0002"
          + "\351\002\n\024DischargePlanService\022U\n\034DischargePla"
          + "nSynchronization\022\".DischargeStudyDataTra"
          + "nsferRequest\032\017.ResponseStatus\"\000\022^\n\037GetDi"
          + "schargeUpdateUllageDetails\022\033.UpdateUllag"
          + "eDetailsRequest\032\034.UpdateUllageDetailsRes"
          + "ponse\"\000\022R\n\025generateDischargePlan\022\034.Disch"
          + "argeInformationRequest\032\031.DischargePlanAl"
          + "goRequest\"\000\022F\n\034updateDischargeUllageDeta"
          + "ils\022\022.UllageBillRequest\032\020.UllageBillRepl"
          + "y\"\0002\272\003\n\035DischargingInstructionService\022^\n"
          + "\032GetDischargingInstructions\022\036.Dischargin"
          + "gInstructionRequest\032\036.DischargingInstruc"
          + "tionDetails\"\000\022L\n\031AddDischargingInstructi"
          + "on\022\034.DischargingInstructionsSave\032\017.Respo"
          + "nseStatus\"\000\022R\n\035UpdateDischargingInstruct"
          + "ions\022\036.DischargingInstructionsUpdate\032\017.R"
          + "esponseStatus\"\000\022Q\n\035DeleteDischargingInst"
          + "ructions\022\035.DischargingInstructionStatus\032"
          + "\017.ResponseStatus\"\000\022D\n\020EditInstructions\022\035"
          + ".DischargingInstructionStatus\032\017.Response"
          + "Status\"\000B-\n)com.cpdss.common.generated.d"
          + "ischarge_planP\001b\006proto3"
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
