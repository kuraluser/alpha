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
          + "proto2\320\n\n\033DischargeInformationService\022P\n"
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
          + "veResponse\"\000\022P\n GetOrSaveRulesForDischar"
          + "gingPlan\022\025.DischargeRuleRequest\032\023.Discha"
          + "rgeRuleReply\"\000\022p\n getPortDischargingPlan"
          + "RobDetails\022%.PortDischargingPlanRobDetai"
          + "lsRequest\032#.PortDischargingPlanRobDetail"
          + "sReply\"\0002\222\010\n\024DischargePlanService\022U\n\034Dis"
          + "chargePlanSynchronization\022\".DischargeStu"
          + "dyDataTransferRequest\032\017.ResponseStatus\"\000"
          + "\022^\n\037GetDischargeUpdateUllageDetails\022\033.Up"
          + "dateUllageDetailsRequest\032\034.UpdateUllageD"
          + "etailsResponse\"\000\022R\n\025generateDischargePla"
          + "n\022\034.DischargeInformationRequest\032\031.Discha"
          + "rgePlanAlgoRequest\"\000\022F\n\034updateDischargeU"
          + "llageDetails\022\022.UllageBillRequest\032\020.Ullag"
          + "eBillReply\"\000\022R\n\023saveDischargingPlan\022\033.Di"
          + "schargingPlanSaveRequest\032\034.DischargingPl"
          + "anSaveResponse\"\000\022T\n\030dischargeInfoStatusC"
          + "heck\022\033.DischargeInfoStatusRequest\032\031.Disc"
          + "hargeInfoStatusReply\"\000\022G\n\035SaveDischargin"
          + "gPlanAlgoStatus\022\022.AlgoStatusRequest\032\020.Al"
          + "goStatusReply\"\000\022M\n\027GetDischargingSequenc"
          + "es\022\027.LoadingSequenceRequest\032\027.DischargeS"
          + "equenceReply\"\000\022j\n\033dischargePlanStowageDe"
          + "tails\022#.DischargePlanStowageDetailsReque"
          + "st\032$.DischargePlanStowageDetailsResponse"
          + "\"\000\022a\n\021GetLoadicatorData\022%.DischargingInf"
          + "oLoadicatorDataRequest\032#.DischargingInfo"
          + "LoadicatorDataReply\"\000\022D\n\034GetDischargingI"
          + "nfoAlgoErrors\022\021.AlgoErrorRequest\032\017.AlgoE"
          + "rrorReply\"\000\022P\n\034getDischargePlanCargoHist"
          + "ory\022\027.CargoHistoryOpsRequest\032\025.CargoHist"
          + "oryResponse\"\0002\272\003\n\035DischargingInstruction"
          + "Service\022^\n\032GetDischargingInstructions\022\036."
          + "DischargingInstructionRequest\032\036.Discharg"
          + "ingInstructionDetails\"\000\022L\n\031AddDischargin"
          + "gInstruction\022\034.DischargingInstructionsSa"
          + "ve\032\017.ResponseStatus\"\000\022R\n\035UpdateDischargi"
          + "ngInstructions\022\036.DischargingInstructions"
          + "Update\032\017.ResponseStatus\"\000\022Q\n\035DeleteDisch"
          + "argingInstructions\022\035.DischargingInstruct"
          + "ionStatus\032\017.ResponseStatus\"\000\022D\n\020EditInst"
          + "ructions\022\035.DischargingInstructionStatus\032"
          + "\017.ResponseStatus\"\000B-\n)com.cpdss.common.g"
          + "enerated.discharge_planP\001b\006proto3"
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
