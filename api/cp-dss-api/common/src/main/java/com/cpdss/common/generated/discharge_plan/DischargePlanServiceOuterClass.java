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
          + "ading_plan_models.proto\032\024loadable_study."
          + "proto2\214\t\n\033DischargeInformationService\022P\n"
          + "\027getDischargeInformation\022\034.DischargeInfo"
          + "rmationRequest\032\025.DischargeInformation\"\000\022"
          + "L\n\034getOrSaveRulesForDischarging\022\025.Discha"
          + "rgeRuleRequest\032\023.DischargeRuleReply\"\000\022r\n"
          + " DischargingUploadPortTideDetails\022#.Disc"
          + "hargingUploadTideDetailRequest\032\'.Dischar"
          + "gingUploadTideDetailStatusReply\"\000\022z\n\"Dis"
          + "chargingDownloadPortTideDetails\022%.Discha"
          + "rgingDownloadTideDetailRequest\032).Dischar"
          + "gingDownloadTideDetailStatusReply\"\0000\001\022K\n"
          + "\022GetDischargingPlan\022\034.DischargeInformati"
          + "onRequest\032\025.DischargingPlanReply\"\000\022S\n\032Sa"
          + "veDischargingInformation\022\025.DischargeInfo"
          + "rmation\032\034.DischargingInfoSaveResponse\"\000\022"
          + "Q\n\030SaveDischargingInfoRates\022\025.DischargeI"
          + "nformation\032\034.DischargingInfoSaveResponse"
          + "\"\000\022R\n\031SaveDischargingInfoBerths\022\025.Discha"
          + "rgeInformation\032\034.DischargingInfoSaveResp"
          + "onse\"\000\022U\n\034SaveDischargingInfoMachinery\022\025"
          + ".DischargeInformation\032\034.DischargingInfoS"
          + "aveResponse\"\000\022R\n\031SaveDischargingInfoDela"
          + "ys\022\025.DischargeInformation\032\034.DischargingI"
          + "nfoSaveResponse\"\000\022R\n\031SaveDischargingInfo"
          + "Stages\022\025.DischargeInformation\032\034.Discharg"
          + "ingInfoSaveResponse\"\000\022D\n\013saveCowPlan\022\025.D"
          + "ischargeInformation\032\034.DischargingInfoSav"
          + "eResponse\"\000\022O\n\026savePostDischargeStage\022\025."
          + "DischargeInformation\032\034.DischargingInfoSa"
          + "veResponse\"\0002\310\005\n\024DischargePlanService\022U\n"
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
          + "ngPlanSaveResponse\"\000\022T\n\030dischargeInfoSta"
          + "tusCheck\022\033.DischargeInfoStatusRequest\032\031."
          + "DischargeInfoStatusReply\"\000\022G\n\035SaveDischa"
          + "rgingPlanAlgoStatus\022\022.AlgoStatusRequest\032"
          + "\020.AlgoStatusReply\"\000\022j\n\033dischargePlanStow"
          + "ageDetails\022#.DischargePlanStowageDetails"
          + "Request\032$.DischargePlanStowageDetailsRes"
          + "ponse\"\0002\272\003\n\035DischargingInstructionServic"
          + "e\022^\n\032GetDischargingInstructions\022\036.Discha"
          + "rgingInstructionRequest\032\036.DischargingIns"
          + "tructionDetails\"\000\022L\n\031AddDischargingInstr"
          + "uction\022\034.DischargingInstructionsSave\032\017.R"
          + "esponseStatus\"\000\022R\n\035UpdateDischargingInst"
          + "ructions\022\036.DischargingInstructionsUpdate"
          + "\032\017.ResponseStatus\"\000\022Q\n\035DeleteDischarging"
          + "Instructions\022\035.DischargingInstructionSta"
          + "tus\032\017.ResponseStatus\"\000\022D\n\020EditInstructio"
          + "ns\022\035.DischargingInstructionStatus\032\017.Resp"
          + "onseStatus\"\000B-\n)com.cpdss.common.generat"
          + "ed.discharge_planP\001b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
              com.cpdss.common.generated.discharge_plan.DischargePlanModels.getDescriptor(),
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor(),
              com.cpdss.common.generated.LoadableStudy.getDescriptor(),
            });
    com.cpdss.common.generated.Common.getDescriptor();
    com.cpdss.common.generated.discharge_plan.DischargePlanModels.getDescriptor();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor();
    com.cpdss.common.generated.LoadableStudy.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
