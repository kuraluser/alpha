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
          + "proto2\354\013\n\033DischargeInformationService\022P\n"
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
          + "veResponse\"\000\022P\n\027SaveCargoToBeDischarged\022"
          + "\025.DischargeInformation\032\034.DischargingInfo"
          + "SaveResponse\"\000\022H\n\026GetCargoToBeDischarged"
          + "\022\025.DischargeInformation\032\025.DischargeInfor"
          + "mation\"\000\022P\n GetOrSaveRulesForDischarging"
          + "Plan\022\025.DischargeRuleRequest\032\023.DischargeR"
          + "uleReply\"\000\022p\n getPortDischargingPlanRobD"
          + "etails\022%.PortDischargingPlanRobDetailsRe"
          + "quest\032#.PortDischargingPlanRobDetailsRep"
          + "ly\"\0002\363\010\n\024DischargePlanService\022U\n\034Dischar"
          + "gePlanSynchronization\022\".DischargeStudyDa"
          + "taTransferRequest\032\017.ResponseStatus\"\000\022^\n\037"
          + "GetDischargeUpdateUllageDetails\022\033.Update"
          + "UllageDetailsRequest\032\034.UpdateUllageDetai"
          + "lsResponse\"\000\022R\n\025generateDischargePlan\022\034."
          + "DischargeInformationRequest\032\031.DischargeP"
          + "lanAlgoRequest\"\000\022F\n\034updateDischargeUllag"
          + "eDetails\022\022.UllageBillRequest\032\020.UllageBil"
          + "lReply\"\000\022R\n\023saveDischargingPlan\022\033.Discha"
          + "rgingPlanSaveRequest\032\034.DischargingPlanSa"
          + "veResponse\"\000\022T\n\030dischargeInfoStatusCheck"
          + "\022\033.DischargeInfoStatusRequest\032\031.Discharg"
          + "eInfoStatusReply\"\000\022G\n\035SaveDischargingPla"
          + "nAlgoStatus\022\022.AlgoStatusRequest\032\020.AlgoSt"
          + "atusReply\"\000\022M\n\027GetDischargingSequences\022\027"
          + ".LoadingSequenceRequest\032\027.DischargeSeque"
          + "nceReply\"\000\022j\n\033dischargePlanStowageDetail"
          + "s\022#.DischargePlanStowageDetailsRequest\032$"
          + ".DischargePlanStowageDetailsResponse\"\000\022a"
          + "\n\021GetLoadicatorData\022%.DischargingInfoLoa"
          + "dicatorDataRequest\032#.DischargingInfoLoad"
          + "icatorDataReply\"\000\022D\n\034GetDischargingInfoA"
          + "lgoErrors\022\021.AlgoErrorRequest\032\017.AlgoError"
          + "Reply\"\000\022P\n\034getDischargePlanCargoHistory\022"
          + "\027.CargoHistoryOpsRequest\032\025.CargoHistoryR"
          + "esponse\"\000\022_\n\037ValidateStowageAndBillOfLad"
          + "ding\022).StowageAndBillOfLaddingValidation"
          + "Request\032\017.ResponseStatus\"\0002\272\003\n\035Dischargi"
          + "ngInstructionService\022^\n\032GetDischargingIn"
          + "structions\022\036.DischargingInstructionReque"
          + "st\032\036.DischargingInstructionDetails\"\000\022L\n\031"
          + "AddDischargingInstruction\022\034.DischargingI"
          + "nstructionsSave\032\017.ResponseStatus\"\000\022R\n\035Up"
          + "dateDischargingInstructions\022\036.Dischargin"
          + "gInstructionsUpdate\032\017.ResponseStatus\"\000\022Q"
          + "\n\035DeleteDischargingInstructions\022\035.Discha"
          + "rgingInstructionStatus\032\017.ResponseStatus\""
          + "\000\022D\n\020EditInstructions\022\035.DischargingInstr"
          + "uctionStatus\032\017.ResponseStatus\"\000B-\n)com.c"
          + "pdss.common.generated.discharge_planP\001b\006"
          + "proto3"
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
