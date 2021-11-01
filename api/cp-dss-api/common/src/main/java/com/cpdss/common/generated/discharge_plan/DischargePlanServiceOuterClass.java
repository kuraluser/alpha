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
          + "veResponse\"\0002\222\010\n\024DischargePlanService\022U\n"
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
          + "\020.AlgoStatusReply\"\000\022M\n\027GetDischargingSeq"
          + "uences\022\027.LoadingSequenceRequest\032\027.Discha"
          + "rgeSequenceReply\"\000\022j\n\033dischargePlanStowa"
          + "geDetails\022#.DischargePlanStowageDetailsR"
          + "equest\032$.DischargePlanStowageDetailsResp"
          + "onse\"\000\022a\n\021GetLoadicatorData\022%.Dischargin"
          + "gInfoLoadicatorDataRequest\032#.Discharging"
          + "InfoLoadicatorDataReply\"\000\022D\n\034GetDischarg"
          + "ingInfoAlgoErrors\022\021.AlgoErrorRequest\032\017.A"
          + "lgoErrorReply\"\000\022P\n\034getDischargePlanCargo"
          + "History\022\027.CargoHistoryOpsRequest\032\025.Cargo"
          + "HistoryResponse\"\0002\272\003\n\035DischargingInstruc"
          + "tionService\022^\n\032GetDischargingInstruction"
          + "s\022\036.DischargingInstructionRequest\032\036.Disc"
          + "hargingInstructionDetails\"\000\022L\n\031AddDischa"
          + "rgingInstruction\022\034.DischargingInstructio"
          + "nsSave\032\017.ResponseStatus\"\000\022R\n\035UpdateDisch"
          + "argingInstructions\022\036.DischargingInstruct"
          + "ionsUpdate\032\017.ResponseStatus\"\000\022Q\n\035DeleteD"
          + "ischargingInstructions\022\035.DischargingInst"
          + "ructionStatus\032\017.ResponseStatus\"\000\022D\n\020Edit"
          + "Instructions\022\035.DischargingInstructionSta"
          + "tus\032\017.ResponseStatus\"\000B-\n)com.cpdss.comm"
          + "on.generated.discharge_planP\001b\006proto3"
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
