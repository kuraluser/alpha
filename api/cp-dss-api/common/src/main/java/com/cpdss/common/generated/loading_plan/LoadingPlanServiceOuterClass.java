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
          + "an_models.proto\032\024loadable_study.proto2\345\006"
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
          + "\"\000\022A\n\023GenerateLoadingPlan\022\027.LoadingInfoA"
          + "lgoRequest\032\017.ResponseStatus\"\0002\216\003\n\031Loadin"
          + "gInstructionService\022R\n\026GetLoadingInstruc"
          + "tions\022\032.LoadingInstructionRequest\032\032.Load"
          + "ingInstructionDetails\"\000\022D\n\025AddLoadingIns"
          + "truction\022\030.LoadingInstructionsSave\032\017.Res"
          + "ponseStatus\"\000\022J\n\031UpdateLoadingInstructio"
          + "ns\022\032.LoadingInstructionsUpdate\032\017.Respons"
          + "eStatus\"\000\022I\n\031DeleteLoadingInstructions\022\031"
          + ".LoadingInstructionStatus\032\017.ResponseStat"
          + "us\"\000\022@\n\020EditInstructions\022\031.LoadingInstru"
          + "ctionStatus\032\017.ResponseStatus\"\0002\212\003\n\022Loadi"
          + "ngPlanService\022N\n\032LoadingPlanSynchronizat"
          + "ion\022\027.LoadingPlanSyncDetails\032\025.LoadingPl"
          + "anSyncReply\"\000\022F\n\017SaveLoadingPlan\022\027.Loadi"
          + "ngPlanSaveRequest\032\030.LoadingPlanSaveRespo"
          + "nse\"\000\022A\n\016GetLoadingPlan\022\032.LoadingInforma"
          + "tionRequest\032\021.LoadingPlanReply\"\000\022P\n\034GetO"
          + "rSaveRulesForLoadingPlan\022\027.LoadingPlanRu"
          + "leRequest\032\025.LoadingPlanRuleReply\"\000\022G\n\023Ge"
          + "tLoadingSequences\022\027.LoadingSequenceReque"
          + "st\032\025.LoadingSequenceReply\"\000B+\n\'com.cpdss"
          + ".common.generated.loading_planP\000b\006proto3"
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
