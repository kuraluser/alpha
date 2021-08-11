/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.loading_plan;

public final class LoadingPlanServiceOuterClass {
  private LoadingPlanServiceOuterClass() {}

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
      "\n\'loading_plan/loading_plan_service.prot"
          + "o\032\014common.proto\032&loading_plan/loading_pl"
          + "an_models.proto\032\024loadable_study.proto2\377\007"
          + "\n\031LoadingInformationService\022J\n\025GetLoadin"
          + "gInformation\022\032.LoadingInformationRequest"
          + "\032\023.LoadingInformation\"\000\022I\n\026SaveLoadingIn"
          + "formation\022\023.LoadingInformation\032\030.Loading"
          + "InfoSaveResponse\"\000\022G\n\024SaveLoadingInfoRat"
          + "es\022\023.LoadingInformation\032\030.LoadingInfoSav"
          + "eResponse\"\000\022H\n\025SaveLoadingInfoBerths\022\023.L"
          + "oadingInformation\032\030.LoadingInfoSaveRespo"
          + "nse\"\000\022K\n\030SaveLoadingInfoMachinery\022\023.Load"
          + "ingInformation\032\030.LoadingInfoSaveResponse"
          + "\"\000\022H\n\025SaveLoadingInfoDelays\022\023.LoadingInf"
          + "ormation\032\030.LoadingInfoSaveResponse\"\000\022H\n\025"
          + "SaveLoadingInfoStages\022\023.LoadingInformati"
          + "on\032\030.LoadingInfoSaveResponse\"\000\022j\n\034GetLoa"
          + "digInformationByVoyage\022$.LoadingInformat"
          + "ionSynopticalRequest\032\".LoadingInformatio"
          + "nSynopticalReply\"\000\022I\n\014UpdateUllage\022\033.Upd"
          + "ateUllageLoadingRequest\032\032.UpdateUllageLo"
          + "adingReplay\"\000\022C\n\031SaveAlgoLoadingPlanStat"
          + "us\022\022.AlgoStatusRequest\032\020.AlgoStatusReply"
          + "\"\000\022G\n\023GenerateLoadingPlan\022\027.LoadingInfoA"
          + "lgoRequest\032\025.LoadingInfoAlgoReply\"\000\022P\n\030G"
          + "etLoadingInfoAlgoStatus\022\031.LoadingInfoSta"
          + "tusRequest\032\027.LoadingInfoStatusReply\"\000\022@\n"
          + "\030GetLoadingInfoAlgoErrors\022\021.AlgoErrorReq"
          + "uest\032\017.AlgoErrorReply\"\0002\216\003\n\031LoadingInstr"
          + "uctionService\022R\n\026GetLoadingInstructions\022"
          + "\032.LoadingInstructionRequest\032\032.LoadingIns"
          + "tructionDetails\"\000\022D\n\025AddLoadingInstructi"
          + "on\022\030.LoadingInstructionsSave\032\017.ResponseS"
          + "tatus\"\000\022J\n\031UpdateLoadingInstructions\022\032.L"
          + "oadingInstructionsUpdate\032\017.ResponseStatu"
          + "s\"\000\022I\n\031DeleteLoadingInstructions\022\031.Loadi"
          + "ngInstructionStatus\032\017.ResponseStatus\"\000\022@"
          + "\n\020EditInstructions\022\031.LoadingInstructionS"
          + "tatus\032\017.ResponseStatus\"\0002\342\005\n\022LoadingPlan"
          + "Service\022N\n\032LoadingPlanSynchronization\022\027."
          + "LoadingPlanSyncDetails\032\025.LoadingPlanSync"
          + "Reply\"\000\022F\n\017SaveLoadingPlan\022\027.LoadingPlan"
          + "SaveRequest\032\030.LoadingPlanSaveResponse\"\000\022"
          + "A\n\016GetLoadingPlan\022\032.LoadingInformationRe"
          + "quest\032\021.LoadingPlanReply\"\000\022P\n\034GetOrSaveR"
          + "ulesForLoadingPlan\022\027.LoadingPlanRuleRequ"
          + "est\032\025.LoadingPlanRuleReply\"\000\022G\n\023GetLoadi"
          + "ngSequences\022\027.LoadingSequenceRequest\032\025.L"
          + "oadingSequenceReply\"\000\022U\n\026GetUpdateUllage"
          + "Details\022\033.UpdateUllageDetailsRequest\032\034.U"
          + "pdateUllageDetailsResponse\"\000\022V\n\027GetBillO"
          + "fLaddingDetails\022\025.BillOfLaddingRequest\032\""
          + ".LoadingInformationSynopticalReply\"\000\022L\n\035"
          + "GetCargoNominationMaxQuantity\022\023.MaxQuant"
          + "ityRequest\032\024.MaxQuantityResponse\"\000\022Y\n\021Ge"
          + "tLoadicatorData\022!.LoadingInfoLoadicatorD"
          + "ataRequest\032\037.LoadingInfoLoadicatorDataRe"
          + "ply\"\000B+\n\'com.cpdss.common.generated.load"
          + "ing_planP\000b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor(),
              com.cpdss.common.generated.LoadableStudy.getDescriptor(),
            });
    com.cpdss.common.generated.Common.getDescriptor();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor();
    com.cpdss.common.generated.LoadableStudy.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
